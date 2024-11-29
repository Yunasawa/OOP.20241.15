package CCMR.Views.Environments;

import CCMR.Views.Bases.*;
import CCMR.Models.Values.*;
import CCMR.Controls.Utilities.MDebug;
import CCMR.Controls.Utilities.MFormula;
import CCMR.Models.Types.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class ConnectionNode extends Circle
{
	public BaseVisualElement Element;
	public Vector2 Position;
	public WireLine WireLine;
	
	private static boolean _isDraggingWire = false;
	
	public ConnectionNode(BaseVisualElement element, double x, double y)
	{
		super(x, y, 7.5);
		
		Position = new Vector2(x, y);
		
		Element = element;
		
		SetColor(Config.ElementColor);
        this.setStrokeWidth(Data.StrokeWidth);
        
        AddToggleEventHandlers();
	}
	
	public Vector2 GetWorldPosition()
	{
		return MFormula.GetWorldPosition(Position, Element.Transform.Position);
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
        		View.CurrentWire.ConnectNode(this);
        		View.WireList.add(View.CurrentWire);
        		
        		_isDraggingWire = false;
        		
        		View.SelectedNode.SetColor(Config.ElementColor);
        		View.SelectedNode = null;
        	}
        	else
        	{
	        	View.SelectedNode = this;
	        	SetColor(Config.NodeColor);
	        	
	        	WireLine wire = new WireLine(this);
	        	View.CurrentWire = wire;
	        	
	        	_isDraggingWire = true;
	        	
	        	View.GridCanvas.setOnMouseReleased(clicked -> 
	        	{
	        		MDebug.Log(_isDraggingWire);
	        		if (_isDraggingWire)
	        		{
	        			View.GridView.RemoveShapes(wire);
	        			View.CurrentWire = null;
	        		}
	        	});
        	}
        });
    }
}
