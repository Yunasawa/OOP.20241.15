package CCMR.Views.Environments;

import CCMR.Models.Types.*;
import CCMR.Models.Values.*;
import CCMR.Views.Elements.CircleElement;
import CCMR.Views.Elements.SquareElement;

import java.util.ArrayList;
import CCMR.Models.Definitions.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class GridView 
{
    private Vector2 _startPosition = new Vector2(0, 0); // Start position of grid lines on the offset values.
    private Vector2 _dotPosition = new Vector2(0, 0); // Calculate the position of the dot on the grid.

    private final double _zoomIntensity = 0.001;  // Lowered zoom speed for smoother zoom
    private final double _minScale = 0.4; // Minimum zoom scale
    private final double _maxScale = 2.0; // Maximum zoom scale
    private double _zoomFactor, _newScale, _oldScale, _f, _dx, _dy;
    private boolean _zoomCenteredOnMouse = false;  // Boolean flag to enable/disable zoom centered on mouse

    private Transform _dotLogicalPosition = new Transform(new Vector2Int());  // Dot position in logical grid coordinates
    private boolean _isHovered = false;  // Hover state of the dot
    
    private Canvas _canvas = new Canvas(1920, 1080);
    private GraphicsContext _gc = _canvas.getGraphicsContext2D();
    
    private GridType _gridType = GridType.Line;
    
    public ArrayList<VisualElement<?>> Elements = new ArrayList<>();

    public Pane CreateView() 
    {
        View.GridPane.getChildren().add(_canvas);

        CreateGraphicsContext();
       
        Elements.add(new CircleElement());
        Elements.add(new SquareElement());
        for (VisualElement<?> element : Elements) 
        {
            element.UpdatePosition();
            element.AddToPane();
        }

        // Add a click handler for the grid pane
        View.GridPane.setOnMouseClicked(event -> 
        {
            if (event.getTarget() instanceof Canvas)
            {
                if (View.SelectedElement != null) 
                {
                    View.SelectedElement.RevertToElementColor();
                    View.SelectedElement = null;
                }
            }
        });
        
        DrawGrid(_canvas, Config.GridLineColor);

        AddDragManipulator(View.GridPane, _canvas);
        AddZoomManipulator(View.GridPane, _canvas);
        AddHoverManipulator(_canvas);
        
        return View.GridPane;
    }

    public GridView SetGridType(GridType type) 
    {
        _gridType = type;
        return this;
    }
    
    private void CreateGraphicsContext() 
    {
        _gc.clearRect(0, 0, _canvas.getWidth(), _canvas.getHeight());
        
        _gc.setFill(Config.BackgroundColor);
        _gc.setStroke(Config.GridLineColor);
        _gc.setLineWidth(1.0);
        if (_gridType == GridType.Dash) 
        {
            _gc.setLineDashes(5);
        }
    }
    
    private void DrawGrid(Canvas canvas, Color color) 
    {
        _gc.save();
        _gc.scale(Data.ScaleValue, Data.ScaleValue);

        _gc.fillRect(0, 0, _canvas.getWidth() / Data.ScaleValue, _canvas.getHeight() / Data.ScaleValue);

        _startPosition.X = Data.GridOffset.X % Config.CellSize;
        _startPosition.Y = Data.GridOffset.Y % Config.CellSize;

        if (_gridType == GridType.Dot) 
        {
            for (double x = -_startPosition.X; x < _canvas.getWidth() / Data.ScaleValue; x += Config.CellSize) 
            {
                for (double y = -_startPosition.Y; y < _canvas.getHeight() / Data.ScaleValue; y += Config.CellSize) 
                {
                    _gc.setFill(color);
                    _gc.fillOval(x - 2.5, y - 2.5, 5, 5);
                }
            }
        } 
        else 
        {
            for (double x = -_startPosition.X; x < _canvas.getWidth() / Data.ScaleValue; x += Config.CellSize) 
            {
                _gc.strokeLine(x, 0, x, _canvas.getHeight() / Data.ScaleValue);
            }
            for (double y = -_startPosition.Y; y < _canvas.getHeight() / Data.ScaleValue; y += Config.CellSize) 
            {
                _gc.strokeLine(0, y, _canvas.getWidth() / Data.ScaleValue, y);
            }
        }

        _dotPosition.X = (_dotLogicalPosition.Position.X * Config.CellSize) - Data.GridOffset.X;
        _dotPosition.Y = (_dotLogicalPosition.Position.Y * Config.CellSize) - Data.GridOffset.Y;
        
        _gc.setFill(_isHovered ? Color.YELLOW : Color.RED);
        _gc.fillOval(_dotPosition.X - 10, _dotPosition.Y - 10, 20, 20);

        _gc.restore();
    }
    
    private void AddDragManipulator(Pane pane, Canvas canvas) 
    {
        pane.setOnMousePressed(event -> 
        {
            // Only start dragging if the middle mouse button is pressed
            if (event.isMiddleButtonDown()) 
            {
                Data.MouseCoordinate.Set(event.getSceneX(), event.getSceneY());
            }
        });

        pane.setOnMouseDragged(event -> 
        {
            // Only drag if the middle mouse button is pressed
            if (event.isMiddleButtonDown()) 
            {
                Data.MouseDelta.Set(event.getSceneX() - Data.MouseCoordinate.X, event.getSceneY() - Data.MouseCoordinate.Y);

                Data.GridOffset.X -= Data.MouseDelta.X / Data.ScaleValue;
                Data.GridOffset.Y -= Data.MouseDelta.Y / Data.ScaleValue;

                // Update the visual position of the circle
                for (VisualElement element : Elements) element.UpdatePosition();

                DrawGrid(canvas, Config.GridLineColor);

                Data.MouseCoordinate.Set(event.getSceneX(), event.getSceneY());
            }
        });
    }

    private void AddZoomManipulator(Pane pane, Canvas canvas) 
    {
        pane.setOnScroll(event -> 
        {
            if (event.getDeltaY() != 0) 
            {
                _zoomFactor = Math.exp(event.getDeltaY() * _zoomIntensity);

                _newScale = Data.ScaleValue * _zoomFactor;
                    
                if (_newScale < _minScale) _newScale = _minScale;
                else if (_newScale > _maxScale) _newScale = _maxScale;

                // Check if the scale has reached its limits
                if (_newScale == Data.ScaleValue) 
                {
                    return; // Do nothing if the scale hasn't changed
                }

                _oldScale = Data.ScaleValue;
                Data.ScaleValue = _newScale;

                _f = (Data.ScaleValue / _oldScale) - 1;

                _dx = event.getSceneX() - (pane.getBoundsInParent().getWidth() / 2 + pane.getBoundsInParent().getMinX());
                _dy = event.getSceneY() - (pane.getBoundsInParent().getHeight() / 2 + pane.getBoundsInParent().getMinY());

                if (_zoomCenteredOnMouse) 
                {
                    pane.setTranslateX(pane.getTranslateX() - _f * _dx);
                    pane.setTranslateY(pane.getTranslateY() - _f * _dy);

                    Data.GridOffset.X -= _f * _dx / Data.ScaleValue;
                    Data.GridOffset.Y -= _f * _dy / Data.ScaleValue;
                }

                // Update the visual element's scale and position
                Data.StrokeWidth = Config.StrokeWidth * Data.ScaleValue;
                for (VisualElement element : Elements) element.UpdateScaleValue(Data.ScaleValue);
                for (VisualElement element : Elements) element.UpdatePosition();

                DrawGrid(canvas, Config.GridLineColor);
            }
        });
    }

    private void AddHoverManipulator(Canvas canvas) 
    {
        canvas.setOnMouseMoved(event -> 
        {
            _dotPosition.X = (_dotLogicalPosition.Position.X * Config.CellSize) - Data.GridOffset.X;
            _dotPosition.Y = (_dotLogicalPosition.Position.Y * Config.CellSize) - Data.GridOffset.Y;

            Data.MouseCoordinate.Set(event.getX() / Data.ScaleValue, event.getY() / Data.ScaleValue);

            if (Math.pow(Data.MouseCoordinate.X - _dotPosition.X, 2) + Math.pow(Data.MouseCoordinate.Y - _dotPosition.Y, 2) <= Math.pow(5, 2)) 
            {
                _isHovered = true;
            } 
            else 
            {
                _isHovered = false;
            }

            DrawGrid(canvas, Config.GridLineColor);
        });
    }

}
