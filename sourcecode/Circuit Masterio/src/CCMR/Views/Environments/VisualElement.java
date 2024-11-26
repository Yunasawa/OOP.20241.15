package CCMR.Views.Environments;

import CCMR.Models.Definitions.*;
import CCMR.Models.Values.*;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

public class VisualElement 
{
    public Transform Transform = new Transform();
    public Circle OuterCircle;
    public Circle InnerCircle;
    
    public VisualElement()
    {
        // Initialize the outer ring
        OuterCircle = new Circle(50);
        OuterCircle.setStroke(Color.RED);
        OuterCircle.setFill(Color.TRANSPARENT);
        OuterCircle.setStrokeWidth(Data.StrokeWidth);
        
        // Initialize the inner ring
        InnerCircle = new Circle(40);  // Slightly smaller radius for inner ring
        InnerCircle.setStroke(Color.RED);
        InnerCircle.setFill(Color.TRANSPARENT);
        InnerCircle.setStrokeWidth(Data.StrokeWidth);
        
        AddCircleEventHandlers(OuterCircle);
        AddCircleEventHandlers(InnerCircle);
    }
    
    private void AddCircleEventHandlers(Circle circle) 
    { 
        circle.setOnMousePressed(event -> 
        { 
            if (event.isPrimaryButtonDown()) 
            { 
                Data.MouseCoordinate.Set(event.getSceneX(), event.getSceneY()); 
                Data.MouseDelta.Set(event.getSceneX() - circle.getCenterX(), event.getSceneY() - circle.getCenterY()); 
            } 
        }); 
        
        circle.setOnMouseDragged(event -> 
        { 
            if (event.isPrimaryButtonDown()) 
            { 
                double newCenterX = (event.getSceneX() - Data.MouseDelta.X) / Data.ScaleValue + Data.GridOffset.X;
                double newCenterY = (event.getSceneY() - Data.MouseDelta.Y) / Data.ScaleValue + Data.GridOffset.Y;

                Transform.Position.X = Math.round(newCenterX / Config.CellSize) * Config.CellSize;
                Transform.Position.Y = Math.round(newCenterY / Config.CellSize) * Config.CellSize;

                UpdatePosition();
            } 
        });
    }

    public void UpdateScaleValue(double newScaleValue) 
    {
        Data.ScaleValue = newScaleValue;
        OuterCircle.setRadius(50 * Data.ScaleValue);  // Adjust radius based on scale
        InnerCircle.setRadius(40 * Data.ScaleValue);  // Adjust radius based on scale
        OuterCircle.setStrokeWidth(Data.StrokeWidth);
        InnerCircle.setStrokeWidth(Data.StrokeWidth);
    }
    
    public void UpdatePosition()
    {
        double adjustedCenterX = (Transform.Position.X - Data.GridOffset.X) * Data.ScaleValue;
        double adjustedCenterY = (Transform.Position.Y - Data.GridOffset.Y) * Data.ScaleValue;

        OuterCircle.setCenterX(adjustedCenterX);
        OuterCircle.setCenterY(adjustedCenterY);
        InnerCircle.setCenterX(adjustedCenterX);
        InnerCircle.setCenterY(adjustedCenterY);
    }

    public void AddToPane() 
    {
        View.GridPane.getChildren().addAll(OuterCircle, InnerCircle);
    }
}
