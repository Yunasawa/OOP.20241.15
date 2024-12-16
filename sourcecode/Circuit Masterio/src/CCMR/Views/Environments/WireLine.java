package CCMR.Views.Environments;

import CCMR.Models.Values.*;
import CCMR.Controls.Bases.CircuitConnection;
import CCMR.Controls.Utilities.MDebug;
import CCMR.Controls.Utilities.MFormula;
import CCMR.Models.Interfaces.IKeyPressListenable;
import CCMR.Models.Interfaces.ISelectable;
import CCMR.Models.Types.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Shape;

public class WireLine extends Polyline implements ISelectable, IKeyPressListenable
{
	public Row<Vector2> Points = new Row<>();
    public ConnectionNode Node1;
    public ConnectionNode Node2;

    public WireLine(ConnectionNode node1)
    {
    	Node1 = node1;
        
        this.setStrokeWidth(Data.StrokeWidth);
        SetColor(Config.WireColor);

        Global.GridView.AddShapes(3, this);
        
        AddSelectManipulator();
        AddSelectingCallback(this);
        
        RegisterListener();
    }

    public void ConnectNode(ConnectionNode node2)
    {
    	Node2 = node2;
    	
		//MDebug.Log("Connect " + Node1.UID + " to " + Node2.UID);
    	
    	CircuitConnection connection1 = null;
    	CircuitConnection connection2 = null;
    	
    	//MDebug.Log("Node1: " + Node1.UID + " | " + Node1.Element.Node1.UID);
    	//MDebug.Log("Node2: " + Node2.UID + " | " + Node2.Element.Node2.UID);
    	
    	if (Node1.UID == Node1.Element.Node1.UID) connection1 =  Global.CircuitPairs.get(Node1.Element).Connection1;
    	else if (Node1.UID == Node1.Element.Node2.UID) connection1 =  Global.CircuitPairs.get(Node1.Element).Connection2;
    	
    	if (Node2.UID == Node2.Element.Node1.UID) connection2 =  Global.CircuitPairs.get(Node2.Element).Connection1;
    	else if (Node2.UID == Node2.Element.Node2.UID) connection2 =  Global.CircuitPairs.get(Node2.Element).Connection2;
    	
    	connection1.ConnectWith(connection2);
    	
    	SyncPositions();
    }
    
    private void AddSelectManipulator()
    {
    	this.setOnMousePressed(event -> 
    	{
    		if (Global.CurrentWire != null && Global.CurrentWire != this)
    		{
    			Global.CurrentWire.SelectWire(false);
    		}
    		SelectWire(true);
    	});
    }
    
    public void SelectWire(boolean isSelect)
    {
    	if (isSelect)
    	{
    		Global.CurrentWire = this;
    		Global.CurrentWire.SetColor(Config.SelectedColor);
    	}
    	else
    	{
    		Global.CurrentWire.SetColor(Config.WireColor);
    		Global.CurrentWire = null;
    	}
    }
    
    public void SetColor(Color color) { this.setStroke(color); }
    public void AssignWire()
    {
    	if (Node1 != null) Node1.WireLine = this;
    	if (Node2 != null) Node2.WireLine = this;
    }
    private void GetNodePositions()
    {
    	Points.clear();
    	if (Node1 != null) Points.Add(MFormula.GetWorldPosition(Node1.Position, Node1.Element.Transform.Position));
    	if (Node2 != null) Points.add(MFormula.GetWorldPosition(Node2.Position, Node2.Element.Transform.Position));
    }   
    public void SyncPositions()
    {
    	GetNodePositions();
    	
        this.getPoints().clear();
        for (Vector2 point : Points)
        {
            double x = (point.X - Data.GridOffset.X) * Data.ScaleValue;
            double y = (point.Y - Data.GridOffset.Y) * Data.ScaleValue;
            this.getPoints().addAll(x, y);
        }
    }
    public void UpdateScale() 
    {
        SyncPositions();
        this.setStrokeWidth(Data.StrokeWidth);
    }
    public void UpdateOffset()
    {
        SyncPositions();
    }

    @Override
    public void OnMouseSelected(Shape shape, MouseEvent event)
    {
    	if (Global.SelectedElement.contains(this)) return;
    	
    	Global.SelectedElement.add(this);
    	
    	OnSelected();
    }
    
	@Override
	public void OnSelected() 
	{
		SetColor(Config.SelectedColor);
	}
	@Override
	public void OnDeselected() 
	{
		SetColor(Config.WireColor);
	}

	@Override
	public void OnKeyPressed(KeyCode key) 
	{
		if (!Global.SelectedElement.contains(this)) return;
		
		if (key == KeyCode.DELETE) 
		{
			Global.GridView.RemoveShapes(this);
			
			Node1.WireLine = Node2.WireLine = null;
			Node1 = Node2 = null;
		}
	}
}
