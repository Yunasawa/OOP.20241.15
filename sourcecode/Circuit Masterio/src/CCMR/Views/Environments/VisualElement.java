package CCMR.Views.Environments;

import CCMR.Models.Definitions.*;
import CCMR.Models.Types.*;
import CCMR.Models.Values.Value;
import CCMR.Models.Values.Runtime;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

public class VisualElement 
{
    public Transform Transform = new Transform();
    public Circle Circle;

    private Vector2 _circleLogicalPosition;
    
    public VisualElement(Vector2 circleLogicalPosition)
    {
        _circleLogicalPosition = circleLogicalPosition;
        
        Circle = new Circle(50);
        Circle.setStroke(Color.RED);
        Circle.setFill(Color.TRANSPARENT);
        Circle.setStrokeWidth(Runtime.StrokeWidth);
        
        AddCircleEventHandlers();
    }
    
    private void AddCircleEventHandlers() 
    { 
        Circle.setOnMousePressed(event -> 
        { 
            if (event.isPrimaryButtonDown()) 
            { 
                Runtime.MouseCoordinate.Set(event.getSceneX(), event.getSceneY()); 
                Runtime.MouseDelta.Set(event.getSceneX() - Circle.getCenterX(), event.getSceneY() - Circle.getCenterY()); 
            } 
        }); 
        
        Circle.setOnMouseDragged(event -> 
        { 
            if (event.isPrimaryButtonDown()) 
            { 
                double newCenterX = (event.getSceneX() - Runtime.MouseDelta.X) / Runtime.ScaleValue + Runtime.GridOffset.X;
                double newCenterY = (event.getSceneY() - Runtime.MouseDelta.Y) / Runtime.ScaleValue + Runtime.GridOffset.Y;

                _circleLogicalPosition.X = Math.round(newCenterX / Value.CellSize) * Value.CellSize;
                _circleLogicalPosition.Y = Math.round(newCenterY / Value.CellSize) * Value.CellSize;

                UpdatePosition();
            } 
        });
    }

    public void UpdateScaleValue(double newScaleValue) 
    {
        Runtime.ScaleValue = newScaleValue;
        Circle.setRadius(50 * Runtime.ScaleValue);
        Circle.setStrokeWidth(Runtime.StrokeWidth);
    }
    
    public void UpdatePosition()
    {
        double adjustedCenterX = (_circleLogicalPosition.X - Runtime.GridOffset.X) * Runtime.ScaleValue;
        double adjustedCenterY = (_circleLogicalPosition.Y - Runtime.GridOffset.Y) * Runtime.ScaleValue;

        Circle.setCenterX(adjustedCenterX);
        Circle.setCenterY(adjustedCenterY);
    }
}