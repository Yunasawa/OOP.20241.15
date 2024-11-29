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
	
	public static boolean IsDraggingWire = false;
	
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
        		
        		View.CurrentWire.AssignWire();
        		
        		IsDraggingWire = false;
        		
        		View.SelectedNode.SetColor(Config.ElementColor);
        		View.SelectedNode = null;
        	}
        	else
        	{
	        	View.SelectedNode = this;
	        	SetColor(Config.NodeColor);
	        	
	        	WireLine wire = new WireLine(this);
	        	View.CurrentWire = wire;
	        	
	        	IsDraggingWire = true;
        	}
        });
    }
    
    public void RemoveWire()
    {
    	if (IsDraggingWire && View.CurrentWire != null)
		{
			View.GridView.RemoveShapes(View.CurrentWire);
			View.CurrentWire = null;
		}
    }

    public void UpdateNodePotition()
    {
    	if (WireLine != null) WireLine.SyncPositions();
    }
}
