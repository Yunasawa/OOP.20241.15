package CCMR.Views.Environments;

import CCMR.Models.Values.*;
import CCMR.Controls.Utilities.MDebug;
import CCMR.Controls.Utilities.MFormula;
import CCMR.Models.Types.*;
import javafx.scene.shape.Polyline;
import java.util.ArrayList;
import java.util.List;

public class WireLine extends Polyline 
{
	public Row<Vector2> Points = new Row<>();
    public ConnectionNode Node1;
    public ConnectionNode Node2;

    public WireLine(ConnectionNode node1)
    {
    	Node1 = node1;
        
        this.setStrokeWidth(Data.StrokeWidth);
        this.setStroke(Config.WireColor);

        View.GridView.AddShapes(1, this);
    }

    public void ConnectNode(ConnectionNode node2)
    {
    	Node2 = node2;
    	
    	SyncPositions();
    }
    
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
}
