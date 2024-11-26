package CCMR.Views.Environments;

import CCMR.Models.Definitions.*;
import CCMR.Models.Types.*;
import CCMR.Models.Values.Config;
import CCMR.Models.Values.Data;
import CCMR.Models.Values.View;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

public abstract class VisualElement<T extends Shape> 
{
    public Transform Transform = new Transform();
    protected List<T> shapes;

    public VisualElement()
    {
        shapes = new ArrayList<>();
        CreateShapes();
        for (T shape : shapes) {
            InitializeShape(shape);
            AddShapeEventHandlers(shape);
            AddHoverEventHandlers(shape);
        }
    }

    protected abstract void CreateShapes();

    protected void InitializeShape(T shape) 
    {
        shape.setStroke(Config.ElementColor);
        shape.setFill(Color.TRANSPARENT);
        shape.setStrokeWidth(Data.StrokeWidth);
    }

    private void AddShapeEventHandlers(T shape) 
    { 
        shape.setOnMousePressed(event -> 
        { 
            if (event.isPrimaryButtonDown()) 
            { 
                Data.MouseCoordinate.Set(event.getSceneX(), event.getSceneY()); 
                Data.MouseDelta.Set(event.getSceneX() - shape.getTranslateX(), event.getSceneY() - shape.getTranslateY()); 
            } 
        }); 
        
        shape.setOnMouseDragged(event -> 
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
    
    private void AddHoverEventHandlers(T shape) 
    { 
        shape.setOnMouseEntered(event -> 
        { 
            for (T s : shapes) {
                s.setStroke(Config.HoverColor);
            }
        }); 
        
        shape.setOnMouseExited(event -> 
        { 
            for (T s : shapes) {
                s.setStroke(Config.ElementColor);
            }
        });
    }

    public void UpdateScaleValue(double newScaleValue) 
    {
        Data.ScaleValue = newScaleValue;
        for (T shape : shapes) {
            shape.setStrokeWidth(Data.StrokeWidth);
            UpdateShapeSize(shape);
        }
    }

    protected abstract void UpdateShapeSize(T shape);

    public void UpdatePosition()
    {
        double adjustedCenterX = (Transform.Position.X - Data.GridOffset.X) * Data.ScaleValue;
        double adjustedCenterY = (Transform.Position.Y - Data.GridOffset.Y) * Data.ScaleValue;

        for (T shape : shapes) {
            shape.setTranslateX(adjustedCenterX);
            shape.setTranslateY(adjustedCenterY);
        }
    }

    public void AddToPane() 
    {
        View.GridPane.getChildren().addAll(shapes);
    }
}