package CCMR.Views.Bases;

import CCMR.Controls.Utilities.*;
<<<<<<< Updated upstream
import CCMR.Models.Definitions.*;
import CCMR.Models.Interfaces.IKeyPressListenable;
=======
import CCMR.Models.Interfaces.*;
>>>>>>> Stashed changes
import CCMR.Models.Types.*;
import CCMR.Models.Values.*;
import CCMR.Views.Environments.*;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseVisualElement implements IKeyPressListenable
{
    public Transform Transform = new Transform();
    public Collider Collider = new Collider(new Vector2(0, 0), new Vector2(2, 4));
    public List<Shape> Shapes = new ArrayList<>();

    private Vector2 _oldPosition;
    private Vector2 _distancePosition = new Vector2();
    
    private Map<Shape, Row<Double>> _maps = new HashMap<>();

    public BaseVisualElement()
    {
    	RegisterListener();
    	
        CreateShapes();
        InitializeShapes();
        
        AddShapeEventHandlers(Collider);
        AddHoverEventHandlers(Collider);
        
        AddShapes(Collider);
        
        Global.GridView.Elements.add(this);
        this.UpdatePosition();
        this.AddToPane();
    }

    protected abstract void CreateShapes();
    
    public void AddShapes(Shape... shapes)
    {
    	for (Shape shape : shapes)
    	{
    		Shapes.add(shape);
    		if (shape instanceof WireLine) 
    		{
    			//_maps.put(shape, MShape.GetScale(shape));
    		}
    		else _maps.put(shape, MShape.GetScale(shape));
    	}
    }
    private void InitializeShapes() 
    {
        for (Shape shape : Shapes)
        {
        	StyleShape(shape);
        	
        	if (!(shape instanceof ConnectionNode) && !(shape instanceof Collider)) AddShapeEventHandlers(shape);
            if (!(shape instanceof ConnectionNode) && !(shape instanceof Collider)) AddHoverEventHandlers(shape);
        }
    }
    protected void StyleShape(Shape shape)
    {
    	if (shape instanceof ConnectionNode || shape instanceof Collider) return; 
    	
        shape.setStroke(Config.ElementColor);
        shape.setFill(Color.TRANSPARENT);
        shape.setStrokeWidth(Data.StrokeWidth);
    }

    protected void AddShapeEventHandlers(Shape shape) 
    { 
        shape.setOnMousePressed(event -> 
        { 
            if (event.isPrimaryButtonDown()) 
            { 
            	Data.IsDraggingElement = true;
            	
                Data.MouseCoordinate.Set(event.getSceneX(), event.getSceneY()); 
                Data.MouseDelta.Set(event.getSceneX() - shape.getTranslateX(), event.getSceneY() - shape.getTranslateY()); 
                _oldPosition = new Vector2(Transform.Position.X, Transform.Position.Y);
                
                if (Global.SelectedElement.Count() != 1)
                {
                	for (BaseVisualElement element : Global.SelectedElement)
	                {
	                	element._oldPosition = new Vector2(element.Transform.Position.X, element.Transform.Position.Y);
	                	element._distancePosition = element.Transform.Position.Subtract(this.Transform.Position);
	                }
                }
                
                if (Global.SelectedElement.Count() == 1) Global.GridView.RemoveAllSelected();
            } 
        }); 

        shape.setOnMouseDragged(event -> 
        { 
            if (event.isPrimaryButtonDown()) 
            { 
                double newCenterX = (event.getSceneX() - Data.MouseDelta.X) / Data.ScaleValue + Data.GridOffset.X;
                double newCenterY = (event.getSceneY() - Data.MouseDelta.Y) / Data.ScaleValue + Data.GridOffset.Y;

                Transform.Position.X = Math.round(newCenterX / Config.CellSize);
                Transform.Position.Y = Math.round(newCenterY / Config.CellSize);

                if (Global.SelectedElement.Count() != 1)
                {
	                for (BaseVisualElement element : Global.SelectedElement)
	                {
	                	if (element == this) continue;
	                	
	                	element.Transform.Position = this.Transform.Position.Add(element._distancePosition);
	                	element.UpdatePosition();
	                	
	                    for (Shape eachShape : element.Shapes)
	                	{
	                		if (eachShape instanceof ConnectionNode) ((ConnectionNode)eachShape).UpdateNodePotition();
	                	}
	                }
                }
                
                UpdatePosition();

                boolean collisionDetected = false;
                for (BaseVisualElement other : Global.GridView.Elements) 
                {
                    if (other != this && CheckCollision(other)) 
                    {
                        collisionDetected = true;
                        break;
                    }
                }

                // Change color to CollisionColor if collision detected, else set to HoverColor and update old position
                if (collisionDetected) SetStrokeColor(Config.CollisionColor);
                else 
                {
                	SetStrokeColor(Config.HoverColor);
                    _oldPosition = new Vector2(Transform.Position.X, Transform.Position.Y); // Update old position if no collision is detected
                }    
                
                for (Shape eachShape : Shapes)
            	{
            		if (eachShape instanceof ConnectionNode) ((ConnectionNode)eachShape).UpdateNodePotition();
            	}
            } 
        });

        shape.setOnMouseReleased(event -> 
        {
        	Data.IsDraggingElement = false;
        	
            HandleElementSelection();

            boolean collisionDetected = false;
            for (BaseVisualElement other : Global.GridView.Elements) 
            {
                if (other != this && CheckCollision(other)) 
                {
                    collisionDetected = true;
                    break;
                }
            }

            if (collisionDetected) 
            {
                Transform.Position = _oldPosition;
                UpdatePosition();
            }

            SetStrokeColor(Config.SelectedColor);
        });
    }
    protected void AddHoverEventHandlers(Shape shape) 
    {     	
        shape.setOnMouseEntered(event ->
        { 
        	if (Global.SelectedElement.IsEmpty() || !Global.SelectedElement.contains(this)) SetStrokeColor(Config.HoverColor);
        }); 
        
        shape.setOnMouseExited(event -> 
        { 
        	if (Global.SelectedElement.IsEmpty() || !Global.SelectedElement.contains(this)) SetStrokeColor(Config.ElementColor);
            else SetStrokeColor(Config.SelectedColor);
        });
    }
    
    public void UpdateScale() 
    {
        for (Shape shape : Shapes) 
        {
            shape.setStrokeWidth(Data.StrokeWidth);
            MShape.SetScale(shape, _maps.get(shape), Data.ScaleValue);
        }
    }
    public void UpdatePosition()
    {
        double adjustedCenterX = (Transform.Position.X * Config.CellSize - Data.GridOffset.X) * Data.ScaleValue;
        double adjustedCenterY = (Transform.Position.Y * Config.CellSize - Data.GridOffset.Y) * Data.ScaleValue;

        for (Shape shape : Shapes) 
        {
            shape.setTranslateX(adjustedCenterX);
            shape.setTranslateY(adjustedCenterY);
        }
    }
    public void UpdateRotation()
    {
    	Transform.Rotation++;
    	if (Transform.Rotation > 3) Transform.Rotation = 0;

    	Vector2 rotatingPivot = Transform.Position.Add(Collider.Size.Multiply(0.5)).Round();
    }
    
    public void AddToPane() 
    {
        Global.GridPane.getChildren().addAll(Shapes);
    }
    private void HandleElementSelection()
    {
    	if (Global.SelectedElement.Count() == 1)
    	{
	        if (Global.SelectedElement.get(0) != this) 
	        {
	            Global.SelectedElement.get(0).SetStrokeColor(Config.ElementColor);
	            Global.SelectedElement.remove(0);
	        }
        }
        Global.SelectedElement.Add(this);
    }
    public boolean CheckCollision(BaseVisualElement other) 
    {
        double thisLeft = Transform.Position.X * Config.CellSize - Collider.Size.X * Config.CellSize / 2;
        double thisRight = thisLeft + Collider.Size.X * Config.CellSize;
        double thisTop = Transform.Position.Y * Config.CellSize - Collider.Size.Y * Config.CellSize / 2;
        double thisBottom = thisTop + Collider.Size.Y * Config.CellSize;

        double otherLeft = other.Transform.Position.X * Config.CellSize - other.Collider.Size.X * Config.CellSize / 2;
        double otherRight = otherLeft + other.Collider.Size.X * Config.CellSize;
        double otherTop = other.Transform.Position.Y * Config.CellSize - other.Collider.Size.Y * Config.CellSize / 2;
        double otherBottom = otherTop + other.Collider.Size.Y * Config.CellSize;

        return !(thisRight <= otherLeft || thisLeft >= otherRight || thisBottom <= otherTop || thisTop >= otherBottom);
    }
    public void SetStrokeColor(Color color)
    {
    	for (Shape shape : Shapes)
    	{
    		if (shape instanceof ConnectionNode || shape instanceof Collider) continue;
    		
    		shape.setStroke(color);
    	}
    }

    @Override
	public void OnKeyPressed(KeyCode key) 
    {
		if (!Global.SelectedElement.contains(this)) return;
		
		if (key == KeyCode.R)
		{
			UpdateRotation();
		}
	}
}
