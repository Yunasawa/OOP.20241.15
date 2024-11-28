package CCMR.Views.Environments;

import CCMR.Models.Types.*;
import CCMR.Models.Values.View;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

public class WireLine
{
	private Vector2 _startNode;
	private Vector2 _endNode;
	private Polyline _wire = new Polyline();
	
	public WireLine(Vector2 startNode, Vector2 endNode)
	{
		this._startNode = startNode;
		this._endNode = endNode;
		
		_wire.setStroke(Color.GRAY);
		_wire.setStrokeWidth(5);
		
		ComputeGridPath();
		
		View.GridPane.getChildren().add(_wire);
	}
	
	private void ComputeGridPath()
	{
		_wire.getPoints().addAll(_startNode.X, _startNode.Y);
		_wire.getPoints().addAll(_endNode.X, _endNode.Y);
		
		//if (_startNode.X != _endNode.X) _wire.getPoints().addAll(_endNode.X, _startNode.Y);
		//if (_startNode.Y != _endNode.Y) _wire.getPoints().addAll(_endNode.X, _endNode.Y);
	}
}
