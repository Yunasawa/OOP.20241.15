package CCMR.Views.Bases;

import CCMR.Models.Types.*;
import CCMR.Models.Values.Config;
import CCMR.Models.Values.Data;
import CCMR.Models.Values.Global;
import CCMR.Views.Environments.WireLine;
import javafx.scene.Node;
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
        Global.GridPane.getChildren().add(Global.GridCanvas);

        CreateGraphicsContext();
        
        AddElementSelectionHandler();
        
        AddDragManipulator();
        AddZoomManipulator();   

        DrawView();

        return Global.GridPane;
    }

    private void AddElementSelectionHandler() 
    {
        Global.SceneNode.setOnMousePressed(event -> 
        {
        	System.out.println(event.getTarget());
        	if (event.getTarget() instanceof Canvas || event.getTarget() instanceof Pane) 
            {
	            Data.LastMousePressedTime = System.currentTimeMillis();
	            Data.LastMousePressedPosition = new Vector2(event.getX(), event.getY());
	            
	            if (Global.SelectedNode != null)
	            {
	            	Global.SelectedNode.SetColor(Config.ElementColor);
	            	Global.SelectedNode = null;
	            }
	            
	            if (Global.CurrentWire != null)
	            {
	            	Global.CurrentWire.SelectWire(false);
	            }
            }
        });
        
        Global.SceneNode.setOnMouseReleased(event -> 
        {
            if (event.getTarget() instanceof Canvas || event.getTarget() instanceof Pane || event.getTarget() instanceof Node) 
            {
                double elapsedTime = System.currentTimeMillis() - Data.LastMousePressedTime;

                Vector2 releasePosition = new Vector2(event.getX(), event.getY());
                double distance = releasePosition.Distance(Data.LastMousePressedPosition);

                if (elapsedTime < 200 && distance < 5)
                {
                	RemoveAllSelected();
                }
            }
            
            if (Global.SelectedNode != null) Global.SelectedNode.RemoveWire();
        });
    }
    
    protected void DrawView() {}

    private void CreateGraphicsContext() 
    {
        Global.GridContext.clearRect(0, 0, Global.GridCanvas.getWidth(), Global.GridCanvas.getHeight());
        Global.GridContext.setFill(Config.BackgroundColor);
        Global.GridContext.setStroke(Config.GridLineColor);
        Global.GridContext.setLineWidth(1.0);
    }

    public void RemoveSelectedElement()
    {
    	for (int i = Global.SelectedElement.size() - 1; i >= 0; i--) 
        {
    		if (Global.SelectedElement.get(i) instanceof BaseVisualElement)
    		{
        		BaseVisualElement element = (BaseVisualElement)Global.SelectedElement.get(i);
        		
        		Global.GridPane.getChildren().removeAll(element.Shapes);
        		Global.GridView.Elements.remove(element);
        		Global.SelectedElement.remove(i);	
    		}
    		else if (Global.SelectedElement.get(i) instanceof WireLine)
    		{
    			WireLine wire = (WireLine)Global.SelectedElement.get(i);
    			
        		Global.GridPane.getChildren().removeAll(wire);
        		Global.SelectedElement.remove(i);
    		}
        }
    }
    public void RemoveAllSelected()
    {
    	if (!Global.SelectedElement.IsEmpty()) 
        {
            for (int i = Global.SelectedElement.size() - 1; i >= 0; i--) 
            {
                Global.SelectedElement.get(i).OnDeselected();
                Global.SelectedElement.remove(i);
            }
        }
    }
    
    private void AddDragManipulator() 
    {
        Global.GridPane.setOnMousePressed(event -> 
        {
            if (event.isMiddleButtonDown()) 
            {
                Data.MouseCoordinate.Set(event.getSceneX(), event.getSceneY());
            }
            OnDragMousePressed();
        });

        Global.GridPane.setOnMouseDragged(event -> 
        {
            if (event.isMiddleButtonDown()) 
            {
                Data.MouseDelta.Set(event.getSceneX() - Data.MouseCoordinate.X, event.getSceneY() - Data.MouseCoordinate.Y);
                Data.GridOffset.X -= Data.MouseDelta.X / Data.ScaleValue;
                Data.GridOffset.Y -= Data.MouseDelta.Y / Data.ScaleValue;

                for (BaseVisualElement element : Elements) element.UpdatePosition();

                //View.WireLine.updateOffset(Data.GridOffset);
                
                for (WireLine wire : Global.WireList) wire.UpdateOffset();
                
                Data.MouseCoordinate.Set(event.getSceneX(), event.getSceneY());
                OnDragMouseDragged();
            }
        });
    }
    private void AddZoomManipulator() 
    {
        Global.GridPane.setOnScroll(event -> 
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

                _dx = event.getSceneX() - (Global.GridPane.getBoundsInParent().getWidth() / 2 + Global.GridPane.getBoundsInParent().getMinX());
                _dy = event.getSceneY() - (Global.GridPane.getBoundsInParent().getHeight() / 2 + Global.GridPane.getBoundsInParent().getMinY());

                if (_zoomCenteredOnMouse) 
                {
                    Global.GridPane.setTranslateX(Global.GridPane.getTranslateX() - _f * _dx);
                    Global.GridPane.setTranslateY(Global.GridPane.getTranslateY() - _f * _dy);

                    Data.GridOffset.X -= _f * _dx / Data.ScaleValue;
                    Data.GridOffset.Y -= _f * _dy / Data.ScaleValue;
                }

                Data.StrokeWidth = Config.StrokeWidth * Data.ScaleValue;
                for (BaseVisualElement element : Elements) 
                {
                    element.UpdateScale();
                    element.UpdatePosition();
                }
                
                //View.WireLine.updateScale(Data.ScaleValue);
                
                for (WireLine wire : Global.WireList) wire.UpdateScale();
                
                OnZoomMouseScrolled();
            }
        });
    }

    protected void OnDragMousePressed() {}
    protected void OnDragMouseDragged() {}
    protected void OnZoomMouseScrolled() {}
}
