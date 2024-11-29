package CCMR.Views.Bases;

import CCMR.Models.Types.*;
import CCMR.Models.Values.Config;
import CCMR.Models.Values.Data;
import CCMR.Models.Values.View;
import CCMR.Views.Environments.WireLine;
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
    
    public Row<BaseVisualElement> Elements = new Row<>();

    public Pane CreateView()
    {
        View.GridPane.getChildren().add(View.GridCanvas);

        CreateGraphicsContext();
        
        AddElementSelectionHandler();
        
        AddDragManipulator();
        AddZoomManipulator();    

        DrawView();

        return View.GridPane;
    }

    private void AddElementSelectionHandler() 
    {
        View.GridCanvas.setOnMousePressed(event -> 
        {
        	if (event.getTarget() instanceof Canvas || event.getTarget() instanceof Pane) 
            {
	            Data.LastMousePressedTime = System.currentTimeMillis();
	            Data.LastMousePressedPosition = new Vector2(event.getX(), event.getY());
	            
	            if (View.SelectedNode != null)
	            {
	            	View.SelectedNode.SetColor(Config.ElementColor);
	            	View.SelectedNode = null;
	            }
            }
        });
        
        View.GridCanvas.setOnMouseReleased(event -> 
        {
            if (event.getTarget() instanceof Canvas || event.getTarget() instanceof Pane) 
            {
                double elapsedTime = System.currentTimeMillis() - Data.LastMousePressedTime;

                Vector2 releasePosition = new Vector2(event.getX(), event.getY());
                double distance = releasePosition.Distance(Data.LastMousePressedPosition);

                if (elapsedTime < 200 && distance < 5)
                {
                	RemoveAllSelected();
                }
            }
            
            if (View.SelectedNode != null) View.SelectedNode.RemoveWire();
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

    public void RemoveSelectedElement()
    {
    	for (int i = View.SelectedElement.size() - 1; i >= 0; i--) 
        {
    		BaseVisualElement element = View.SelectedElement.get(i);
    		
    		View.GridPane.getChildren().removeAll(element.Shapes);
    		View.GridView.Elements.remove(element);
    		View.SelectedElement.remove(i);
        }
    }
    public void RemoveAllSelected()
    {
    	if (!View.SelectedElement.IsEmpty()) 
        {
            for (int i = View.SelectedElement.size() - 1; i >= 0; i--) 
            {
                View.SelectedElement.get(i).SetStrokeColor(Config.ElementColor);
                View.SelectedElement.remove(i);
            }
        }
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

                for (BaseVisualElement element : Elements) element.UpdatePosition();

                //View.WireLine.updateOffset(Data.GridOffset);
                
                for (WireLine wire : View.WireList) wire.UpdateOffset();
                
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
                for (BaseVisualElement element : Elements) 
                {
                    element.UpdateScaleValue();
                    element.UpdatePosition();
                }
                
                //View.WireLine.updateScale(Data.ScaleValue);
                
                for (WireLine wire : View.WireList) wire.UpdateScale();
                
                OnZoomMouseScrolled();
            }
        });
    }

    protected void OnDragMousePressed() {}
    protected void OnDragMouseDragged() {}
    protected void OnZoomMouseScrolled() {}
}
