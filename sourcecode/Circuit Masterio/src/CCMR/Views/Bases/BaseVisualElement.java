package CCMR.Views.Bases;

import CCMR.Controls.Utilities.*;
import CCMR.Models.Types.*;
import CCMR.Models.Values.*;
import CCMR.Views.Environments.*;
import javafx.scene.shape.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseVisualElement implements IKeyPressListenable, ISelectable
{
	private Transform _stableTransform;
	private Collider _stableCollider;
	
    public Transform Transform = new Transform();
    public Collider Collider = new Collider(new Vector2(0, 0), new Vector2(2, 4));
    public List<Shape> Shapes = new ArrayList<>();

    private Vector2 _oldPosition;
    private Vector2 _distancePosition = new Vector2();
    
    private Map<Shape, Row<Double>> _scaleMap = new HashMap<>();
    private Map<Shape, Row<Double>> _rotateMap = new HashMap<>();
    
    public ConnectionNode Node1;
    public ConnectionNode Node2;

    public BaseVisualElement()
    {
    	RegisterListener();
    	
        Node1 = MShape.Normalize(new ConnectionNode(this), 50, 0);
        Node2 = MShape.Normalize(new ConnectionNode(this), 50, 200);
    	
    	CreateShapes();
    	
        InitializeShapes();
        
        AddShapeEventHandlers(Collider);
        AddHoverEventHandlers(Collider);
        
        AddShapes(Collider);
        
        Global.GridView.Elements.add(this);
        this.UpdatePosition();
        this.AddToPane();
        
        _stableTransform = new Transform(Transform.Position, Transform.Rotation);
        _stableCollider = new Collider(Collider.TopLeft, Collider.BottomRight);
    }
    
	protected abstract void CreateShapes();

    public void AddShapes(Shape... shapes)
    {
    	for (Shape shape : shapes)
    	{
    		Shapes.add(shape);
    		RefreshMap(true, true);
    	}
    }
    private void RefreshMap(boolean refreshScale, boolean refreshRotate)
    {
    	if (refreshScale) _scaleMap.clear();
    	if (refreshRotate) _rotateMap.clear();
    	for (Shape shape : Shapes)
    	{
    		if (refreshScale) _scaleMap.put(shape, MShape.GetProperties(shape));
    		if (refreshRotate) _rotateMap.put(shape, MShape.GetProperties(shape));
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
        shape.setOnMousePressed(event -> OnMousePressed(shape, event));
        shape.setOnMouseDragged(event -> OnMouseDragged(shape, event));
        shape.setOnMouseReleased(event -> OnMouseReleased(shape, event));
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
    
    private void OnMousePressed(Shape shape, MouseEvent event)
    {
    	if (event.isPrimaryButtonDown()) 
        { 
        	Data.IsDraggingElement = true;
        	
            Data.MouseCoordinate.Set(event.getSceneX(), event.getSceneY()); 
            Data.MouseDelta.Set(event.getSceneX() - shape.getTranslateX(), event.getSceneY() - shape.getTranslateY()); 
            _oldPosition = new Vector2(Transform.Position.X, Transform.Position.Y);
            
            if (Global.SelectedElement.Count() != 1)
            {
            	for (ISelectable selectable : Global.SelectedElement)
                {
            		if (!(selectable instanceof BaseVisualElement)) continue;
            		
            		BaseVisualElement element = (BaseVisualElement)selectable;
            		
                	element._oldPosition = new Vector2(element.Transform.Position.X, element.Transform.Position.Y);
                	element._distancePosition = element.Transform.Position.Subtract(this.Transform.Position);
                }
            }
            
            if (Global.SelectedElement.Count() == 1) Global.GridView.RemoveAllSelected();
        }
    }
    private void OnMouseDragged(Shape shape, MouseEvent event)
    {
    	if (event.isPrimaryButtonDown()) 
        { 
    		double newCenterX = (event.getSceneX() - Data.MouseDelta.X) / Data.ScaleValue + Data.GridOffset.X;
            double newCenterY = (event.getSceneY() - Data.MouseDelta.Y) / Data.ScaleValue + Data.GridOffset.Y;

            Transform.Position.X = Math.round(newCenterX / Config.CellSize);
            Transform.Position.Y = Math.round(newCenterY / Config.CellSize);

            if (Global.SelectedElement.Count() != 1)
            {
                for (ISelectable selectable : Global.SelectedElement)
                {
                	if (!(selectable instanceof BaseVisualElement) || selectable == this) continue;
                	
                	BaseVisualElement element = (BaseVisualElement)selectable;
                	
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

            if (collisionDetected) SetStrokeColor(Config.CollisionColor);
            else 
            {
            	SetStrokeColor(Config.HoverColor);
                _oldPosition = new Vector2(Transform.Position.X, Transform.Position.Y);
            }    
            
            for (Shape eachShape : Shapes)
        	{
        		if (eachShape instanceof ConnectionNode) ((ConnectionNode)eachShape).UpdateNodePotition();
        	}
        }
    }
    private void OnMouseReleased(Shape shape, MouseEvent event)
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

        OnSelected();
    }

    public void UpdateScale() 
    {
        for (Shape shape : Shapes) 
        {
            shape.setStrokeWidth(Data.StrokeWidth);
            MShape.SetScale(shape, _scaleMap.get(shape), Data.ScaleValue);
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

        for (Shape shape : Shapes)
        {
            MShape.SetRotate(shape, _rotateMap.get(shape), Transform.Rotation);
        }
        MShape.GetRotatedPivot(this, _stableTransform, _stableCollider);
        UpdatePosition();
        
        for (Shape eachShape : Shapes)
    	{
    		if (eachShape instanceof ConnectionNode) ((ConnectionNode)eachShape).UpdateNodePotition();
    	}
        
        RefreshMap(true, false);
    }
    
    public void AddToPane() { Global.GridPane.getChildren().addAll(Shapes); }
    public void RemoveFromPane() { Global.GridPane.getChildren().removeAll(Shapes); }
    
    private void HandleElementSelection()
    {
    	if (Global.SelectedElement.Count() == 1)
    	{
	        if (Global.SelectedElement.get(0) != this) 
	        {
	        	OnDeselected();
	            Global.SelectedElement.remove(0);
	            
	        }
        }
        Global.SelectedElement.Add(this);
    }
    public boolean CheckCollision(BaseVisualElement other) 
    {
        double thisLeft = Transform.Position.X * Config.CellSize + Collider.TopLeft.X * Config.CellSize;
        double thisRight = Transform.Position.X * Config.CellSize + Collider.BottomRight.X * Config.CellSize;
        double thisTop = Transform.Position.Y * Config.CellSize + Collider.TopLeft.Y * Config.CellSize;
        double thisBottom = Transform.Position.Y * Config.CellSize + Collider.BottomRight.Y * Config.CellSize;

        double otherLeft = other.Transform.Position.X * Config.CellSize + other.Collider.TopLeft.X * Config.CellSize;
        double otherRight = other.Transform.Position.X * Config.CellSize + other.Collider.BottomRight.X * Config.CellSize;
        double otherTop = other.Transform.Position.Y * Config.CellSize + other.Collider.TopLeft.Y * Config.CellSize;
        double otherBottom = other.Transform.Position.Y * Config.CellSize + other.Collider.BottomRight.Y * Config.CellSize;

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
		
		if (key == KeyCode.R) UpdateRotation();
		else if (key == KeyCode.DELETE) RemoveFromPane();
	}

	@Override
	public void OnSelected() 
	{
		SetStrokeColor(Config.SelectedColor);
	}

	@Override
	public void OnDeselected() 
	{
		SetStrokeColor(Config.ElementColor);
	}
}
