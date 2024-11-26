package CCMR.Views.Bases;

import java.util.ArrayList;

import CCMR.Models.Types.Vector2;
import CCMR.Models.Values.Config;
import CCMR.Models.Values.Data;
import CCMR.Models.Values.View;
import CCMR.Views.Elements.CircleElement;
import CCMR.Views.Elements.SquareElement;
import CCMR.Views.Environments.VisualElement;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

public abstract class BasePaneView 
{
	protected Pane _basePane;
	protected Canvas _canvas = new Canvas(1920, 1080);
	protected GraphicsContext _gc = _canvas.getGraphicsContext2D();
	
	protected Vector2 _startPosition = new Vector2(0, 0); // Start position of grid lines on the offset values.
	
	protected final double _zoomIntensity = 0.001;  // Lowered zoom speed for smoother zoom
	protected final double _minScale = 0.4; // Minimum zoom scale
	protected final double _maxScale = 2.0; // Maximum zoom scale
	protected double _zoomFactor, _newScale, _oldScale, _f, _dx, _dy;
	protected boolean _zoomCenteredOnMouse = false;  // Boolean flag to enable/disable zoom centered on mouse
	
    public ArrayList<VisualElement<?>> Elements = new ArrayList<>();
	
	public Pane CreateView()
	{
		_basePane = new Pane();
		_basePane.getChildren().add(_canvas);
		
		View.GridPane = _basePane;
		
		CreateGraphicsContext();
		
        Elements.add(new CircleElement());
        Elements.add(new SquareElement());
        for (VisualElement<?> element : Elements) 
        {
            element.UpdatePosition();
            element.AddToPane();
        }

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
		
        AddDragManipulator(_basePane, _canvas);
        AddZoomManipulator(_basePane, _canvas);	
		
		return _basePane;
	}
	
    private void CreateGraphicsContext() 
    {
        _gc.clearRect(0, 0, _canvas.getWidth(), _canvas.getHeight());
        
        _gc.setFill(Config.BackgroundColor);
        _gc.setStroke(Config.GridLineColor);
        _gc.setLineWidth(1.0);
    }
    
    private void AddDragManipulator(Pane pane, Canvas canvas) 
    {
        pane.setOnMousePressed(event -> 
        {
            if (event.isMiddleButtonDown()) 
            {
                Data.MouseCoordinate.Set(event.getSceneX(), event.getSceneY());
            }
            
            OnDragMousePressed();
        });

        pane.setOnMouseDragged(event -> 
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

                if (_newScale == Data.ScaleValue) return;

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

                Data.StrokeWidth = Config.StrokeWidth * Data.ScaleValue;
                for (VisualElement element : Elements) element.UpdateScaleValue(Data.ScaleValue);
                for (VisualElement element : Elements) element.UpdatePosition();
                
                OnZoomMouseScrolled();
            }
        });
    }
    
    protected void OnDragMousePressed() {}
    protected void OnDragMouseDragged() {}
    protected void OnZoomMouseScrolled() {}
}
