package CCMR.Views.Bases;

import CCMR.Controls.Utilities.MShape;
import CCMR.Models.Definitions.*;
import CCMR.Models.Types.Row;
import CCMR.Models.Types.Vector2;
import CCMR.Models.Values.*;
import javafx.scene.shape.Shape;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseVisualElement
{
    public Transform Transform = new Transform();
    protected List<Shape> _shapes;

    private Vector2 _oldPosition;
    private Vector2 _distancePosition = new Vector2();
    
    private Map<Shape, Row<Double>> _maps = new HashMap<>();

    public BaseVisualElement()
    {
        _shapes = new ArrayList<>();
        CreateShapes();
        InitializeShapes();
        
        View.GridView.Elements.add(this);
        this.UpdatePosition();
        this.AddToPane();
    }

    protected abstract void CreateShapes();
    
    protected void AddShapes(Shape... shapes)
    {
    	for (Shape shape : shapes) 
    	{
    		_shapes.add(shape);
    		_maps.put(shape, MShape.GetScale(shape));
    	}
    }

    private void InitializeShapes() 
    {
        for (Shape shape : _shapes) 
        {
        	StyleShape(shape);
        	
            AddShapeEventHandlers(shape);
            AddHoverEventHandlers(shape);
        }
    }
    
    protected void StyleShape(Shape shape)
    {
        shape.setStroke(Config.ElementColor);
        shape.setFill(Color.TRANSPARENT);
        shape.setStrokeWidth(Data.StrokeWidth);
    }

    private void AddShapeEventHandlers(Shape shape) 
    { 
        shape.setOnMousePressed(event -> 
        { 
            if (event.isPrimaryButtonDown()) 
            { 
            	Data.IsDraggingElement = true;
            	
                Data.MouseCoordinate.Set(event.getSceneX(), event.getSceneY()); 
                Data.MouseDelta.Set(event.getSceneX() - shape.getTranslateX(), event.getSceneY() - shape.getTranslateY()); 
                _oldPosition = new Vector2(Transform.Position.X, Transform.Position.Y);
                
                if (View.SelectedElement.Count() != 1)
                {
                	for (BaseVisualElement element : View.SelectedElement)
	                {
	                	element._oldPosition = new Vector2(element.Transform.Position.X, element.Transform.Position.Y);
	                	element._distancePosition = element.Transform.Position.Subtract(this.Transform.Position);
	                }
                }
                
                if (View.SelectedElement.Count() == 1) View.GridView.RemoveAllSelected();
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

                if (View.SelectedElement.Count() != 1)
                {
	                for (BaseVisualElement element : View.SelectedElement)
	                {
	                	if (element == this) continue;
	                	
	                	element.Transform.Position = this.Transform.Position.Add(element._distancePosition);
	                	element.UpdatePosition();
	                }
                }
                
                System.out.println(View.SelectedElement.Count());
                
                UpdatePosition();

                boolean collisionDetected = false;
                for (BaseVisualElement other : View.GridView.Elements) 
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
            } 
        });

        shape.setOnMouseReleased(event -> 
        {
        	Data.IsDraggingElement = false;
        	
            HandleElementSelection();

            boolean collisionDetected = false;
            for (BaseVisualElement other : View.GridView.Elements) 
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

    private void AddHoverEventHandlers(Shape shape) 
    {     	
        shape.setOnMouseEntered(event ->
        { 
        	if (View.SelectedElement.IsEmpty() || !View.SelectedElement.contains(this)) SetStrokeColor(Config.HoverColor);
        }); 
        
        shape.setOnMouseExited(event -> 
        { 
        	if (View.SelectedElement.IsEmpty() || !View.SelectedElement.contains(this)) SetStrokeColor(Config.ElementColor);
            else SetStrokeColor(Config.SelectedColor);
        });
    }

    public void UpdateScaleValue() 
    {
        for (Shape shape : _shapes) 
        {
            shape.setStrokeWidth(Data.StrokeWidth);
            MShape.SetScale(shape, _maps.get(shape), Data.ScaleValue);
        }
    }

    public void UpdatePosition()
    {
        double adjustedCenterX = (Transform.Position.X * Config.CellSize - Data.GridOffset.X) * Data.ScaleValue;
        double adjustedCenterY = (Transform.Position.Y * Config.CellSize - Data.GridOffset.Y) * Data.ScaleValue;

        for (Shape shape : _shapes) 
        {
            shape.setTranslateX(adjustedCenterX);
            shape.setTranslateY(adjustedCenterY);
        }
    }
    
    public void AddToPane() 
    {
        View.GridPane.getChildren().addAll(_shapes);
    }

    private void HandleElementSelection()
    {
    	if (View.SelectedElement.Count() == 1)
    	{
	        if (View.SelectedElement.get(0) != this) 
	        {
	            View.SelectedElement.get(0).SetStrokeColor(Config.ElementColor);
	            View.SelectedElement.remove(0);
	        }
        }
        View.SelectedElement.Add(this);
    }

    public boolean CheckCollision(BaseVisualElement other) 
    {
        double thisLeft = Transform.Position.X * Config.CellSize - Transform.Size.X * Config.CellSize / 2;
        double thisRight = thisLeft + Transform.Size.X * Config.CellSize;
        double thisTop = Transform.Position.Y * Config.CellSize - Transform.Size.Y * Config.CellSize / 2;
        double thisBottom = thisTop + Transform.Size.Y * Config.CellSize;

        double otherLeft = other.Transform.Position.X * Config.CellSize - other.Transform.Size.X * Config.CellSize / 2;
        double otherRight = otherLeft + other.Transform.Size.X * Config.CellSize;
        double otherTop = other.Transform.Position.Y * Config.CellSize - other.Transform.Size.Y * Config.CellSize / 2;
        double otherBottom = otherTop + other.Transform.Size.Y * Config.CellSize;

        return !(thisRight <= otherLeft || thisLeft >= otherRight || thisBottom <= otherTop || thisTop >= otherBottom);
    }

    public void SetStrokeColor(Color color)
    {
    	for (Shape shape : _shapes) shape.setStroke(color);
    }
}
