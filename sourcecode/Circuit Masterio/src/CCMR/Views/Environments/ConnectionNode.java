package CCMR.Views.Environments;

import CCMR.Views.Bases.*;
import CCMR.Models.Values.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class ConnectionNode extends Circle
{
	public BaseVisualElement Element;
	private WireLine _wireLine;
	
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
        	if (View.SelectedNode != null)
        	{
        		View.SelectedNode.CreateWireLine(this);
        	}
        	else
        	{
	        	View.SelectedNode = this;
	        	SetColor(Config.NodeColor);
        	}
        });
    }
    
    private void CreateWireLine(ConnectionNode endNode)
    {
    	if (endNode.Element == this.Element) return;
    	
    	_wireLine = new WireLine(this, endNode);
    }
    private void UpdateWireLine()
    {
    	
    }
}
