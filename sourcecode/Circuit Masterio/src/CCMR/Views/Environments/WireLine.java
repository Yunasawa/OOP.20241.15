package CCMR.Views.Environments;

import CCMR.Models.Values.*;
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
    	Points.Add(MFormula.GetWorldPosition(node1.Position, node1.Element.Transform.Position));
        
        this.setStrokeWidth(Data.StrokeWidth);
        this.setStroke(Config.WireColor);

        View.GridView.AddShapes(1, this);
    }

    public void ConnectNode(ConnectionNode node2)
    {
    	Node2 = node2;
    	Points.add(MFormula.GetWorldPosition(node2.Position, node2.Element.Transform.Position));
    	
    	syncPoints();
    }
    
    private void syncPoints()
    {
        this.getPoints().clear();
        for (Vector2 point : Points)
        {
            double x = (point.X - Data.GridOffset.X) * Data.ScaleValue;//(point.X * Config.CellSize - Data.GridOffset.X) * Data.ScaleValue;
            double y = (point.Y - Data.GridOffset.Y) * Data.ScaleValue;//(point.Y * Config.CellSize - Data.GridOffset.Y) * Data.ScaleValue;
            this.getPoints().addAll(x, y);
        }
    }

    public void updateScale() 
    {
        syncPoints();
        this.setStrokeWidth(Data.StrokeWidth);
    }

    public void updateOffset() 
    {
        syncPoints();
    }
}
