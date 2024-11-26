package CCMR.Views.Environments;

import CCMR.Models.Definitions.*;
import CCMR.Models.Types.*;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

public class VisualElement 
{
    public Transform Transform = new Transform();
    public Circle Circle;

    private Vector2 _mouseCoordinate;
    private Vector2 _mouseDelta;
    private double _scaleValue;
    private Vector2 _gridOffset;
    private Vector2 _circleLogicalPosition;
    
    public VisualElement(Vector2 mouseCoordinate, Vector2 mouseDelta, double scaleValue, Vector2 gridOffset, Vector2 circleLogicalPosition)
    {
        _mouseCoordinate = mouseCoordinate;
        _mouseDelta = mouseDelta;
        _scaleValue = scaleValue;
        _gridOffset = gridOffset;
        _circleLogicalPosition = circleLogicalPosition;
        
        Circle = new Circle(50);
        Circle.setStroke(Color.RED);
        Circle.setFill(Color.TRANSPARENT);
        Circle.setStrokeWidth(5);
        
        AddCircleEventHandlers();
    }
    
    private void AddCircleEventHandlers() 
    { 
        Circle.setOnMousePressed(event -> 
        { 
            if (event.isPrimaryButtonDown()) 
            { 
                _mouseCoordinate.Set(event.getSceneX(), event.getSceneY()); 
                _mouseDelta.Set(event.getSceneX() - Circle.getCenterX(), event.getSceneY() - Circle.getCenterY()); 
            } 
        }); 
        
        Circle.setOnMouseDragged(event -> 
        { 
            if (event.isPrimaryButtonDown()) 
            { 
                double newCenterX = (event.getSceneX() - _mouseDelta.X) / _scaleValue + _gridOffset.X;
                double newCenterY = (event.getSceneY() - _mouseDelta.Y) / _scaleValue + _gridOffset.Y;

                // Snap the logical position to the nearest grid cell
                _circleLogicalPosition.X = Math.round(newCenterX / Data.CellSize) * Data.CellSize;
                _circleLogicalPosition.Y = Math.round(newCenterY / Data.CellSize) * Data.CellSize;

                // Update the visual position based on the snapped logical position
                UpdatePosition();
            } 
        });
    }

    public void UpdateScaleValue(double newScaleValue) 
    {
        _scaleValue = newScaleValue;
        // Update the radius of the circle based on the new scale value
        Circle.setRadius(50 * _scaleValue); // Assuming original radius is 50
    }
    
    public void UpdatePosition()
    {
        double adjustedCenterX = (_circleLogicalPosition.X - _gridOffset.X) * _scaleValue;
        double adjustedCenterY = (_circleLogicalPosition.Y - _gridOffset.Y) * _scaleValue;

        Circle.setCenterX(adjustedCenterX);
        Circle.setCenterY(adjustedCenterY);
    
        //System.out.println(_circleLogicalPosition);
    }
}