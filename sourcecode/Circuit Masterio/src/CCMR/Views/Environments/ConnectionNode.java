package CCMR.Views.Environments;

import CCMR.Controls.Utilities.MDebug;
import CCMR.Views.Bases.*;
import CCMR.Models.Types.*;
import CCMR.Models.Values.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class ConnectionNode extends Circle
{
	private BaseVisualElement _element;
	private WireLine _wireLine;
	
	public ConnectionNode(BaseVisualElement element, double x, double y)
	{
		super(x, y, 7.5);
		
		_element = element;
		
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
    	Vector2 thisCenter = new Vector2(this.getCenterX(), this.getCenterY()).Add(_element.Transform.Position.Multiply(Config.CellSize)).Subtract(Data.GridOffset);
    	Vector2 endCenter = new Vector2(endNode.getCenterX(), endNode.getCenterY()).Add(endNode._element.Transform.Position.Multiply(Config.CellSize)).Subtract(Data.GridOffset);
    	
    	MDebug.Log(String.format("%s | %s", thisCenter.toString(), endCenter.toString()));
    	
    	_wireLine = new WireLine(thisCenter, endCenter);
    }
}
