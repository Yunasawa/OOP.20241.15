package CCMR.Views.Environments;

import CCMR.Controls.Utilities.MDebug;
import CCMR.Controls.Utilities.MFormula;
import CCMR.Models.Types.*;
import CCMR.Models.Values.Data;
import CCMR.Models.Values.View;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

public class WireLine extends Polyline
{
	private ConnectionNode _startNode;
	private ConnectionNode _endNode;
	private boolean _hasWire = false;
	
	public WireLine(ConnectionNode startNode, Vector2 endPosition)
	{
		this._startNode = startNode;
		
		this.setStroke(Color.GRAY);
		this.setStrokeWidth(5);
		
		ComputeGridPath(endPosition);
	}

	public void UpdateNode(ConnectionNode endNode)
	{
		endNode.WireLine = this;
		this._endNode = endNode;
		
		ComputeGridPath();
	}
	
    public void UpdateScale()
    {
		setStrokeWidth(Data.StrokeWidth);
		ComputeGridPath();
    }
	
	public void RemoveWire()
	{
		View.GridPane.getChildren().remove(this);
	}
	
	public void ComputeGridPath(Vector2 endPosition)
	{
		Vector2 startPosition = MFormula.GetWorldPosition(new Vector2(_startNode.getCenterX(), _startNode.getCenterY()), _startNode.Element.Transform.Position).Multiply(Data.ScaleValue);
		
		this.getPoints().clear();
		this.getPoints().addAll(startPosition.X, startPosition.Y);
		
		if (startPosition.Y <= endPosition.Y) this.getPoints().addAll(endPosition.X, startPosition.Y);
		else if (startPosition.Y > endPosition.Y) this.getPoints().addAll(startPosition.X, endPosition.Y);
		
		this.getPoints().addAll(endPosition.X, endPosition.Y);
		
		if (!_hasWire)
		{
			View.GridPane.getChildren().add(1, this);
			_hasWire = true;
		}
	}
	public void ComputeGridPath()
	{
		if (_endNode == null) return;
		
		ComputeGridPath(MFormula.GetWorldPosition(new Vector2(_endNode.getCenterX(), _endNode.getCenterY()), _endNode.Element.Transform.Position).Multiply(Data.ScaleValue));
	}
}
