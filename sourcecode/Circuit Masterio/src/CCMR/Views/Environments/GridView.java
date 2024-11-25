package CCMR.Views.Environments;

import CCMR.Models.Types.*;
import CCMR.Models.Definitions.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class GridView 
{
	///
    private Vector2 _mouseCoordinate = new Vector2(0, 0); // Track the mouse coordinates when mouse events occur.
    private Vector2 _startPosition = new Vector2(0, 0); // Start position of grid lines on the offset values.
    private Vector2 _dotPosition = new Vector2(0, 0); // Calculate the position of the dot on the grid.
    private Vector2 _mouseDelta = new Vector2(0, 0); // Represent the change in mouse coordinates during a mouse drag event.
    private Vector2 _gridOffset = new Vector2(0, 0); // Track the offset values used for panning the view of the grid.

    private double _scaleValue = 1.0;
    private final double _zoomIntensity = 0.001;  // Lowered zoom speed for smoother zoom
    private final double _minScale = 0.4; // Minimum zoom scale
    private final double _maxScale = 2.0; // Maximum zoom scale
    private double _zoomFactor, _newScale, _oldScale, _f, _dx, _dy;
    private boolean _zoomCenteredOnMouse = false;  // Boolean flag to enable/disable zoom centered on mouse

    private Transform _dotLogicalPosition = new Transform(0, 0);  // Dot position in logical grid coordinates
    private boolean _isHovered = false;  // Hover state of the dot
    
    private Pane _gridPane = new Pane();
    private Canvas _canvas = new Canvas(1920, 1080);
    private GraphicsContext _gc = _canvas.getGraphicsContext2D();
    
    private GridType _gridType = GridType.Line;
    
    public Pane CreateView() 
    {
        _gridPane.getChildren().add(_canvas);

        CreateGraphicsContext();
        
        DrawGrid(_canvas, Data.GridLineColor);

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
        
        _gc.setFill(Data.BackgroundColor);
        _gc.setStroke(Data.GridLineColor);
        _gc.setLineWidth(1.0);
        if (_gridType == GridType.Dash) 
        {
            _gc.setLineDashes(5);
        }
    }
    
    private void DrawGrid(Canvas canvas, Color color) 
    {
        _gc.save();
        _gc.scale(_scaleValue, _scaleValue);

        _gc.fillRect(0, 0, _canvas.getWidth() / _scaleValue, _canvas.getHeight() / _scaleValue);

        _startPosition.X = _gridOffset.X % Data.CellSize;
        _startPosition.Y = _gridOffset.Y % Data.CellSize;

        if (_gridType == GridType.Dot) 
        {
            for (double x = -_startPosition.X; x < _canvas.getWidth() / _scaleValue; x += Data.CellSize) 
            {
                for (double y = -_startPosition.Y; y < _canvas.getHeight() / _scaleValue; y += Data.CellSize) 
                {
                    _gc.setFill(color);
                    _gc.fillOval(x - 2.5, y - 2.5, 5, 5);
                }
            }
        } 
        else 
        {
            for (double x = -_startPosition.X; x < _canvas.getWidth() / _scaleValue; x += Data.CellSize) 
            {
                _gc.strokeLine(x, 0, x, _canvas.getHeight() / _scaleValue);
            }
            for (double y = -_startPosition.Y; y < _canvas.getHeight() / _scaleValue; y += Data.CellSize) 
            {
                _gc.strokeLine(0, y, _canvas.getWidth() / _scaleValue, y);
            }
        }

        _dotPosition.X = (_dotLogicalPosition.X * Data.CellSize) - _gridOffset.X;
        _dotPosition.Y = (_dotLogicalPosition.Y * Data.CellSize) - _gridOffset.Y;
        
        _gc.setFill(_isHovered ? Color.YELLOW : Color.RED);
        _gc.fillOval(_dotPosition.X - 10, _dotPosition.Y - 10, 20, 20);

        _gc.restore();
    }
    
    private void AddDragManipulator(Pane pane, Canvas canvas) 
    {
        pane.setOnMousePressed(event -> 
        {
            _mouseCoordinate.Set(event.getSceneX(), event.getSceneY());
        });

        pane.setOnMouseDragged(event -> 
        {
            _mouseDelta.Set(event.getSceneX() - _mouseCoordinate.X, event.getSceneY() - _mouseCoordinate.Y);

            _gridOffset.X -= _mouseDelta.X / _scaleValue;
            _gridOffset.Y -= _mouseDelta.Y / _scaleValue;

            DrawGrid(canvas, Data.GridLineColor);

            _mouseCoordinate.Set(event.getSceneX(), event.getSceneY());
        });
    }

    private void AddZoomManipulator(Pane pane, Canvas canvas) 
    {
        pane.setOnScroll(event -> 
        {
            if (event.getDeltaY() != 0) 
            {
                _zoomFactor = Math.exp(event.getDeltaY() * _zoomIntensity);

                _newScale = _scaleValue * _zoomFactor;
                
                if (_newScale < _minScale) _newScale = _minScale;
                else if (_newScale > _maxScale) _newScale = _maxScale;

                _oldScale = _scaleValue;
                _scaleValue = _newScale;

                _f = (_scaleValue / _oldScale) - 1;

                _dx = event.getSceneX() - (pane.getBoundsInParent().getWidth() / 2 + pane.getBoundsInParent().getMinX());
                _dy = event.getSceneY() - (pane.getBoundsInParent().getHeight() / 2 + pane.getBoundsInParent().getMinY());

                if (_zoomCenteredOnMouse) 
                {
                    pane.setTranslateX(pane.getTranslateX() - _f * _dx);
                    pane.setTranslateY(pane.getTranslateY() - _f * _dy);

                    _gridOffset.X -= _f * _dx / _scaleValue;
                    _gridOffset.Y -= _f * _dy / _scaleValue;
                }

                DrawGrid(canvas, Data.GridLineColor);
            }
        });
    }

    private void AddHoverManipulator(Canvas canvas) 
    {
        canvas.setOnMouseMoved(event -> 
        {
            _dotPosition.X = (_dotLogicalPosition.X * Data.CellSize) - _gridOffset.X;
            _dotPosition.Y = (_dotLogicalPosition.Y * Data.CellSize) - _gridOffset.Y;

            _mouseCoordinate.Set(event.getX() / _scaleValue, event.getY() / _scaleValue);

            if (Math.pow(_mouseCoordinate.X - _dotPosition.X, 2) + Math.pow(_mouseCoordinate.Y - _dotPosition.Y, 2) <= Math.pow(5, 2)) 
            {
                _isHovered = true;
            } 
            else 
            {
                _isHovered = false;
            }

            DrawGrid(canvas, Data.GridLineColor);
        });
    }
}
