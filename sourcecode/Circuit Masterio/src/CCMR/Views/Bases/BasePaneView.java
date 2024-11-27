package CCMR.Views.Bases;

import java.util.ArrayList;

import CCMR.Models.Types.Vector2;
import CCMR.Models.Values.Config;
import CCMR.Models.Values.Data;
import CCMR.Models.Values.View;
import CCMR.Views.Elements.*;
import CCMR.Views.Environments.VisualElement;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;

public abstract class BasePaneView 
{
    protected Vector2 _startPosition = new Vector2(0, 0); // Start position of grid lines on the offset values.
    
    protected final double _zoomIntensity = 0.001;  // Lowered zoom speed for smoother zoom
    protected final double _minScale = 0.4; // Minimum zoom scale
    protected final double _maxScale = 2.0; // Maximum zoom scale
    protected double _zoomFactor, _newScale, _oldScale, _f, _dx, _dy;
    protected boolean _zoomCenteredOnMouse = false;  // Boolean flag to enable/disable zoom centered on mouse
    
    public ArrayList<VisualElement> Elements = new ArrayList<>();

    public Pane CreateView()
    {
        View.GridPane.getChildren().add(View.GridCanvas);

        CreateGraphicsContext();
        InitializeElements();
        
        AddElementSelectionHandler();
        
        AddDragManipulator();
        AddZoomManipulator();    

        DrawView();

        return View.GridPane;
    }

    private void InitializeElements()
    {
        Elements.add(new CircleElement());
        Elements.add(new SquareElement());
        Elements.add(new CircleSquareElement());
        for (VisualElement element : Elements) 
        {
            element.UpdatePosition();
            element.AddToPane();
        }
    }

    private void AddElementSelectionHandler()
    {
        View.GridPane.setOnMouseClicked(event -> 
        {
            if (event.getTarget() instanceof Canvas)
            {
                if (View.SelectedElement != null) 
                {
                    View.SelectedElement.SetStrokeColor(Config.ElementColor);
                    View.SelectedElement = null;
                }
            }
        });
    }

    protected void DrawView() {}

    private void CreateGraphicsContext() 
    {
        View.GridContext.clearRect(0, 0, View.GridCanvas.getWidth(), View.GridCanvas.getHeight());
        View.GridContext.setFill(Config.BackgroundColor);
        View.GridContext.setStroke(Config.GridLineColor);
        View.GridContext.setLineWidth(1.0);
    }

    private void AddDragManipulator() 
    {
        View.GridPane.setOnMousePressed(event -> 
        {
            if (event.isMiddleButtonDown()) 
            {
                Data.MouseCoordinate.Set(event.getSceneX(), event.getSceneY());
            }
            OnDragMousePressed();
        });

        View.GridPane.setOnMouseDragged(event -> 
        {
            if (event.isMiddleButtonDown()) 
            {
                Data.MouseDelta.Set(event.getSceneX() - Data.MouseCoordinate.X, event.getSceneY() - Data.MouseCoordinate.Y);
                Data.GridOffset.X -= Data.MouseDelta.X / Data.ScaleValue;
                Data.GridOffset.Y -= Data.MouseDelta.Y / Data.ScaleValue;

                for (VisualElement element : Elements) element.UpdatePosition();

                Data.MouseCoordinate.Set(event.getSceneX(), event.getSceneY());
                OnDragMouseDragged();
            }
        });
    }

    private void AddZoomManipulator() 
    {
        View.GridPane.setOnScroll(event -> 
        {
            if (event.getDeltaY() != 0) 
            {
                _zoomFactor = Math.exp(event.getDeltaY() * _zoomIntensity);
                _newScale = Data.ScaleValue * _zoomFactor;
                
                if (_newScale < _minScale) _newScale = _minScale;
                else if (_newScale > _maxScale) _newScale = _maxScale;

                if (_newScale == Data.ScaleValue) return;

                _oldScale = Data.ScaleValue;
                Data.ScaleValue = _newScale;

                _f = (Data.ScaleValue / _oldScale) - 1;

                _dx = event.getSceneX() - (View.GridPane.getBoundsInParent().getWidth() / 2 + View.GridPane.getBoundsInParent().getMinX());
                _dy = event.getSceneY() - (View.GridPane.getBoundsInParent().getHeight() / 2 + View.GridPane.getBoundsInParent().getMinY());

                if (_zoomCenteredOnMouse) 
                {
                    View.GridPane.setTranslateX(View.GridPane.getTranslateX() - _f * _dx);
                    View.GridPane.setTranslateY(View.GridPane.getTranslateY() - _f * _dy);

                    Data.GridOffset.X -= _f * _dx / Data.ScaleValue;
                    Data.GridOffset.Y -= _f * _dy / Data.ScaleValue;
                }

                Data.StrokeWidth = Config.StrokeWidth * Data.ScaleValue;
                for (VisualElement element : Elements) 
                {
                    element.UpdateScaleValue();
                    element.UpdatePosition();
                }
                
                OnZoomMouseScrolled();
            }
        });
    }

    protected void OnDragMousePressed() {}
    protected void OnDragMouseDragged() {}
    protected void OnZoomMouseScrolled() {}
}
