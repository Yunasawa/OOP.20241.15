package CCMR.Views.Environments;

import CCMR.Controls.Utilities.MFormula;
import CCMR.Models.Types.*;
import CCMR.Models.Values.View;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

public class WireLine
{
	private ConnectionNode _startNode;
	private ConnectionNode _endNode;
	private Polyline _wire = new Polyline();
	
	public WireLine(ConnectionNode startNode, ConnectionNode endNode)
	{
		this._startNode = startNode;
		this._endNode = endNode;
		
		_wire.setStroke(Color.GRAY);
		_wire.setStrokeWidth(5);
		
		ComputeGridPath();
		
		View.GridPane.getChildren().add(1, _wire);
	}
	
	private void ComputeGridPath()
	{
		Vector2 startPosition = MFormula.GetWorldPosition(new Vector2(_startNode.getCenterX(), _startNode.getCenterY()), _startNode.Element.Transform.Position);
		Vector2 endPosition = MFormula.GetWorldPosition(new Vector2(_endNode.getCenterX(), _endNode.getCenterY()), _endNode.Element.Transform.Position);
		
		_wire.getPoints().addAll(startPosition.X, startPosition.Y);
		
		if (startPosition.Y <= endPosition.Y) _wire.getPoints().addAll(endPosition.X, startPosition.Y);
		else if (startPosition.Y > endPosition.Y) _wire.getPoints().addAll(startPosition.X, endPosition.Y);
		
		_wire.getPoints().addAll(endPosition.X, endPosition.Y);
	}
}
