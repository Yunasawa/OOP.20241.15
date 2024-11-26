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
    
    private Vector2 _circleLogicalPosition = new Vector2(100, 100);
    
    public VisualElement(Vector2 mouseCoordinate, Vector2 mouseDelta, double scaleValue, Vector2 circleLogicalPosition)
    {
        _mouseCoordinate = mouseCoordinate;
        _mouseDelta = mouseDelta;
        _scaleValue = scaleValue;
        _circleLogicalPosition = circleLogicalPosition;
        
        Circle = new Circle(50);
        Circle.setStroke(Color.RED);
        Circle.setFill(Color.TRANSPARENT);
        Circle.setStrokeWidth(5);
        
        AddManipulator();
    }
    
    private void AddManipulator()
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
                Circle.setCenterX(event.getSceneX() - _mouseDelta.X); 
                Circle.setCenterY(event.getSceneY() - _mouseDelta.Y); 

                _circleLogicalPosition.X = (int)(Math.round((Circle.getCenterX() / _scaleValue) / Data.CellSize) * Data.CellSize);
                _circleLogicalPosition.Y = (int)(Math.round((Circle.getCenterY() / _scaleValue) / Data.CellSize) * Data.CellSize);

                UpdatePosition();
            } 
        });
    }
    
    public void UpdateScaleValue(double newScaleValue) 
    {
        _scaleValue = newScaleValue;
    }
    
    public void UpdatePosition()
    {
        Circle.setCenterX(_circleLogicalPosition.X * _scaleValue);
        Circle.setCenterY(_circleLogicalPosition.Y * _scaleValue);
        System.out.println(_circleLogicalPosition.toString());
    }
}