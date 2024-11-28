package CCMR.Views.Environments;

import CCMR.Views.Bases.*;
import CCMR.Models.Types.*;
import CCMR.Models.Values.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class ConnectionNode extends Circle
{
	public BaseVisualElement Element;
	public WireLine WireLine;
	
	private boolean _isDraggingWire;
	private boolean _nodesConnected;
	
	public ConnectionNode(BaseVisualElement element, double x, double y)
	{
		super(x, y, 7.5);
		
		Element = element;
		
		SetColor(Config.ElementColor);
        this.setStrokeWidth(Data.StrokeWidth);
        
        AddToggleEventHandlers();
	}
	
	public void SetColor(Color color)
	{
        this.setStroke(color);
        this.setFill(color);
	}
	
    private void AddToggleEventHandlers()
    {
        this.setOnMouseEntered(event ->
        {
        	if (View.SelectedNode != this) SetColor(Config.HoverColor);
        }); 
        
        this.setOnMouseExited(event -> 
        { 
        	if (View.SelectedNode != this) SetColor(Config.ElementColor);
        });
        
        this.setOnMouseClicked(event -> 
        {
        	if (View.SelectedNode != null && View.SelectedNode != this)
        	{
        		View.SelectedNode._nodesConnected = true;
        		View.SelectedNode.CreateWireLine(this);
        	}
        	else
        	{
	        	View.SelectedNode = this;
	        	View.SelectedNode.CreateWireLine(new Vector2(event.getX(), event.getY()).Subtract(Data.GridOffset));
	        	SetColor(Config.NodeColor);
	        	
	        	_isDraggingWire = true;
	        	
	        	View.GridCanvas.setOnMouseMoved(eventa ->
	            {
	            	if (_isDraggingWire && WireLine != null && !_nodesConnected)
	            	{
	            		WireLine.ComputeGridPath(new Vector2(eventa.getX(), eventa.getY()));
	            	}
	            });
	        	
	        	View.GridCanvas.setOnMouseReleased(eventb -> 
	            {
	            	if (_isDraggingWire && WireLine != null && !_nodesConnected) 
	            	{
	            		WireLine.RemoveWire();
	            		WireLine = null;
	            		_isDraggingWire = false;
	            	}
	            });
        	}
        });
    }
    
    private void CreateWireLine(ConnectionNode endNode)
    {
    	if (endNode.Element == this.Element) return;
    	
    	WireLine.UpdateNode(endNode);
    	
    	View.SelectedNode.SetColor(Config.ElementColor);
    	View.SelectedNode = null;
    }
    
    private void CreateWireLine(Vector2 endPosition)
    {
    	if (WireLine != null) WireLine.RemoveWire();
    	WireLine = new WireLine(this, endPosition);
    	Element.AddShapes(WireLine);
    }
    
    public void UpdateWirePoint()
    {
    	if (WireLine != null) WireLine.ComputeGridPath();
    }
    
    public void UpdateWireScale()
    {
    	if (WireLine != null) 
    	{
    		WireLine.setStrokeWidth(Data.StrokeWidth);
    		WireLine.ComputeGridPath();
    	}
    }
}
