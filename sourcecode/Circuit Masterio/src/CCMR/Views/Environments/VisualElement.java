package CCMR.Views.Environments;

import CCMR.Models.Definitions.*;
import CCMR.Models.Types.*;
import CCMR.Models.Values.Value;
import CCMR.Models.Values.Runtime;
import javafx.scene.layout.Pane;
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
        OuterCircle.setStrokeWidth(Runtime.StrokeWidth);
        
        // Initialize the inner ring
        InnerCircle = new Circle(40);  // Slightly smaller radius for inner ring
        InnerCircle.setStroke(Color.RED);
        InnerCircle.setFill(Color.TRANSPARENT);
        InnerCircle.setStrokeWidth(Runtime.StrokeWidth);
        
        AddCircleEventHandlers(OuterCircle);
        AddCircleEventHandlers(InnerCircle);
    }
    
    private void AddCircleEventHandlers(Circle circle) 
    { 
        circle.setOnMousePressed(event -> 
        { 
            if (event.isPrimaryButtonDown()) 
            { 
                Runtime.MouseCoordinate.Set(event.getSceneX(), event.getSceneY()); 
                Runtime.MouseDelta.Set(event.getSceneX() - circle.getCenterX(), event.getSceneY() - circle.getCenterY()); 
            } 
        }); 
        
        circle.setOnMouseDragged(event -> 
        { 
            if (event.isPrimaryButtonDown()) 
            { 
                double newCenterX = (event.getSceneX() - Runtime.MouseDelta.X) / Runtime.ScaleValue + Runtime.GridOffset.X;
                double newCenterY = (event.getSceneY() - Runtime.MouseDelta.Y) / Runtime.ScaleValue + Runtime.GridOffset.Y;

                Transform.Position.X = Math.round(newCenterX / Value.CellSize) * Value.CellSize;
                Transform.Position.Y = Math.round(newCenterY / Value.CellSize) * Value.CellSize;

                UpdatePosition();
            } 
        });
    }

    public void UpdateScaleValue(double newScaleValue) 
    {
        Runtime.ScaleValue = newScaleValue;
        OuterCircle.setRadius(50 * Runtime.ScaleValue);  // Adjust radius based on scale
        InnerCircle.setRadius(40 * Runtime.ScaleValue);  // Adjust radius based on scale
        OuterCircle.setStrokeWidth(Runtime.StrokeWidth);
        InnerCircle.setStrokeWidth(Runtime.StrokeWidth);
    }
    
    public void UpdatePosition()
    {
        double adjustedCenterX = (Transform.Position.X - Runtime.GridOffset.X) * Runtime.ScaleValue;
        double adjustedCenterY = (Transform.Position.Y - Runtime.GridOffset.Y) * Runtime.ScaleValue;

        OuterCircle.setCenterX(adjustedCenterX);
        OuterCircle.setCenterY(adjustedCenterY);
        InnerCircle.setCenterX(adjustedCenterX);
        InnerCircle.setCenterY(adjustedCenterY);
    }

    public void AddToPane(Pane pane) 
    {
        pane.getChildren().addAll(OuterCircle, InnerCircle);
    }
}
