package CCMR.Views.Environments;

import CCMR.Models.Definitions.*;
import CCMR.Models.Types.Vector2;
import CCMR.Models.Values.*;
import javafx.scene.shape.Shape;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

public abstract class VisualElement
{
    public Transform Transform = new Transform();
    protected List<Shape> _shapes;

    private Vector2 _oldPosition;

    public VisualElement()
    {
        _shapes = new ArrayList<>();
        CreateShapes();
        InitializeShapes();
    }

    protected abstract void CreateShapes();
    
    protected void AddShapes(Shape... shapes)
    {
    	for (Shape shape : shapes) _shapes.add(shape);
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
                Data.MouseCoordinate.Set(event.getSceneX(), event.getSceneY()); 
                Data.MouseDelta.Set(event.getSceneX() - shape.getTranslateX(), event.getSceneY() - shape.getTranslateY()); 
                _oldPosition = new Vector2(Transform.Position.X, Transform.Position.Y); // Clone the old position
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

                UpdatePosition();

                // Check for collisions with other elements
                boolean collisionDetected = false;
                for (VisualElement other : View.GridView.Elements) 
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
            HandleElementSelection();

            // Check for collisions with other elements
            boolean collisionDetected = false;
            for (VisualElement other : View.GridView.Elements) 
            {
                if (other != this && CheckCollision(other)) 
                {
                    collisionDetected = true;
                    break;
                }
            }

            // Restore the old position if dropped inside another element, otherwise set the color to SelectedColor
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
            if (View.SelectedElement != this) SetStrokeColor(Config.HoverColor);
        }); 
        
        shape.setOnMouseExited(event -> 
        { 
            if (View.SelectedElement != this) SetStrokeColor(Config.ElementColor);
            else SetStrokeColor(Config.SelectedColor);
        });
    }

    public void UpdateScaleValue(double newScaleValue) 
    {
        Data.ScaleValue = newScaleValue;
        for (Shape shape : _shapes) 
        {
            shape.setStrokeWidth(Data.StrokeWidth);
            UpdateShapeSize(shape);
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

    protected abstract void UpdateShapeSize(Shape shape);
    
    public void AddToPane() 
    {
        View.GridPane.getChildren().addAll(_shapes);
    }

    private void HandleElementSelection() 
    {
        if (View.SelectedElement != null && View.SelectedElement != this) 
        {
            View.SelectedElement.SetStrokeColor(Config.ElementColor);
        }
        View.SelectedElement = this;
    }

    public boolean CheckCollision(VisualElement other) 
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
