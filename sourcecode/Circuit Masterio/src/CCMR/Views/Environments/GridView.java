package CCMR.Views.Environments;

import CCMR.Models.Types.*;
import CCMR.Models.Values.Value;
import CCMR.Models.Values.Runtime;
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
    
    private Pane _gridPane = new Pane();
    private Canvas _canvas = new Canvas(1920, 1080);
    private GraphicsContext _gc = _canvas.getGraphicsContext2D();
    
    private GridType _gridType = GridType.Line;
    
    private Vector2 _circleLogicalPosition = new Vector2(100, 100);  // Initial logical position of the circle
    
    private VisualElement _visualElement;
    
    public Pane CreateView() 
    {
        _gridPane.getChildren().add(_canvas);

        CreateGraphicsContext();
        
        _visualElement = new VisualElement(_circleLogicalPosition);
        _visualElement.UpdatePosition();
        
        _gridPane.getChildren().add(_visualElement.Circle); // Add the circle to the pane

        
        DrawGrid(_canvas, Value.GridLineColor);

        AddDragManipulator(_gridPane, _canvas);
        AddZoomManipulator(_gridPane, _canvas);
        AddHoverManipulator(_canvas);
        
        return _gridPane;
    }
    
    public GridView SetGridType(GridType type) 
    {
        _gridType = type;
        return this;
    }
    
    private void CreateGraphicsContext() 
    {
        _gc.clearRect(0, 0, _canvas.getWidth(), _canvas.getHeight());
        
        _gc.setFill(Value.BackgroundColor);
        _gc.setStroke(Value.GridLineColor);
        _gc.setLineWidth(1.0);
        if (_gridType == GridType.Dash) 
        {
            _gc.setLineDashes(5);
        }
    }
    
    private void DrawGrid(Canvas canvas, Color color) 
    {
        _gc.save();
        _gc.scale(Runtime.ScaleValue, Runtime.ScaleValue);

        _gc.fillRect(0, 0, _canvas.getWidth() / Runtime.ScaleValue, _canvas.getHeight() / Runtime.ScaleValue);

        _startPosition.X = Runtime.GridOffset.X % Value.CellSize;
        _startPosition.Y = Runtime.GridOffset.Y % Value.CellSize;

        if (_gridType == GridType.Dot) 
        {
            for (double x = -_startPosition.X; x < _canvas.getWidth() / Runtime.ScaleValue; x += Value.CellSize) 
            {
                for (double y = -_startPosition.Y; y < _canvas.getHeight() / Runtime.ScaleValue; y += Value.CellSize) 
                {
                    _gc.setFill(color);
                    _gc.fillOval(x - 2.5, y - 2.5, 5, 5);
                }
            }
        } 
        else 
        {
            for (double x = -_startPosition.X; x < _canvas.getWidth() / Runtime.ScaleValue; x += Value.CellSize) 
            {
                _gc.strokeLine(x, 0, x, _canvas.getHeight() / Runtime.ScaleValue);
            }
            for (double y = -_startPosition.Y; y < _canvas.getHeight() / Runtime.ScaleValue; y += Value.CellSize) 
            {
                _gc.strokeLine(0, y, _canvas.getWidth() / Runtime.ScaleValue, y);
            }
        }

        _dotPosition.X = (_dotLogicalPosition.Position.X * Value.CellSize) - Runtime.GridOffset.X;
        _dotPosition.Y = (_dotLogicalPosition.Position.Y * Value.CellSize) - Runtime.GridOffset.Y;
        
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
                Runtime.MouseCoordinate.Set(event.getSceneX(), event.getSceneY());
            }
        });

        pane.setOnMouseDragged(event -> 
        {
            // Only drag if the middle mouse button is pressed
            if (event.isMiddleButtonDown()) 
            {
                Runtime.MouseDelta.Set(event.getSceneX() - Runtime.MouseCoordinate.X, event.getSceneY() - Runtime.MouseCoordinate.Y);

                Runtime.GridOffset.X -= Runtime.MouseDelta.X / Runtime.ScaleValue;
                Runtime.GridOffset.Y -= Runtime.MouseDelta.Y / Runtime.ScaleValue;

                // Update the visual position of the circle
                _visualElement.UpdatePosition();

                DrawGrid(canvas, Value.GridLineColor);

                Runtime.MouseCoordinate.Set(event.getSceneX(), event.getSceneY());
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

                _newScale = Runtime.ScaleValue * _zoomFactor;
                    
                if (_newScale < _minScale) _newScale = _minScale;
                else if (_newScale > _maxScale) _newScale = _maxScale;

                // Check if the scale has reached its limits
                if (_newScale == Runtime.ScaleValue) 
                {
                    return; // Do nothing if the scale hasn't changed
                }

                _oldScale = Runtime.ScaleValue;
                Runtime.ScaleValue = _newScale;

                _f = (Runtime.ScaleValue / _oldScale) - 1;

                _dx = event.getSceneX() - (pane.getBoundsInParent().getWidth() / 2 + pane.getBoundsInParent().getMinX());
                _dy = event.getSceneY() - (pane.getBoundsInParent().getHeight() / 2 + pane.getBoundsInParent().getMinY());

                if (_zoomCenteredOnMouse) 
                {
                    pane.setTranslateX(pane.getTranslateX() - _f * _dx);
                    pane.setTranslateY(pane.getTranslateY() - _f * _dy);

                    Runtime.GridOffset.X -= _f * _dx / Runtime.ScaleValue;
                    Runtime.GridOffset.Y -= _f * _dy / Runtime.ScaleValue;
                }

                // Update the visual element's scale and position
                Runtime.StrokeWidth = Value.StrokeWidth * Runtime.ScaleValue;
                _visualElement.UpdateScaleValue(Runtime.ScaleValue);
                _visualElement.UpdatePosition();

                DrawGrid(canvas, Value.GridLineColor);
            }
        });
    }

    private void AddHoverManipulator(Canvas canvas) 
    {
        canvas.setOnMouseMoved(event -> 
        {
            _dotPosition.X = (_dotLogicalPosition.Position.X * Value.CellSize) - Runtime.GridOffset.X;
            _dotPosition.Y = (_dotLogicalPosition.Position.Y * Value.CellSize) - Runtime.GridOffset.Y;

            Runtime.MouseCoordinate.Set(event.getX() / Runtime.ScaleValue, event.getY() / Runtime.ScaleValue);

            if (Math.pow(Runtime.MouseCoordinate.X - _dotPosition.X, 2) + Math.pow(Runtime.MouseCoordinate.Y - _dotPosition.Y, 2) <= Math.pow(5, 2)) 
            {
                _isHovered = true;
            } 
            else 
            {
                _isHovered = false;
            }

            DrawGrid(canvas, Value.GridLineColor);
        });
    }

}
