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
        	if (Global.SelectedNode != this) SetColor(Config.HoverColor);
        }); 
        
        this.setOnMouseExited(event -> 
        { 
        	if (Global.SelectedNode != this) SetColor(Config.ElementColor);
        });
        
        this.setOnMouseClicked(event -> 
        {
        	if (Global.SelectedNode != null && Global.SelectedNode != this)
        	{
        		Global.CurrentWire.ConnectNode(this);
        		Global.WireList.add(Global.CurrentWire);
        		
        		Global.CurrentWire.AssignWire();
        		
        		IsDraggingWire = false;
        		
        		Global.SelectedNode.SetColor(Config.ElementColor);
        		Global.SelectedNode = null;
        	}
        	else
        	{
	        	Global.SelectedNode = this;
	        	SetColor(Config.NodeColor);
	        	
	        	WireLine wire = new WireLine(this);
	        	Global.CurrentWire = wire;
	        	
	        	IsDraggingWire = true;
        	}
        });
    }
    
    public void RemoveWire()
    {
    	if (IsDraggingWire && Global.CurrentWire != null)
		{
			Global.GridView.RemoveShapes(Global.CurrentWire);
			Global.CurrentWire = null;
		}
    }

    public void UpdateNodePotition()
    {
    	if (WireLine != null) WireLine.SyncPositions();
    }
}
