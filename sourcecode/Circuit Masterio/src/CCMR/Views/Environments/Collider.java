package CCMR.Views.Environments;

import CCMR.Models.Types.*;
import CCMR.Models.Values.Config;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

public class Collider extends Rectangle
{
	public Vector2 TopLeft = new Vector2();
	public Vector2 BottomRight = new Vector2();
	
	public Vector2 Size;
	
	public Collider(double topLeftX, double topLeftY, double bottomRightX, double bottomRightY) 
	{ 
		this(new Vector2(topLeftX, topLeftY), new Vector2(bottomRightX, bottomRightY)); 
	}
	public Collider(Vector2 topLeft, Vector2 bottomRight)
	{
		TopLeft = topLeft;
		BottomRight = bottomRight;
		
		Size = BottomRight.Subtract(TopLeft);
		
		this.setX(TopLeft.X * Config.CellSize + 10);
		this.setY(TopLeft.Y * Config.CellSize + 10);
		this.setWidth((BottomRight.X - TopLeft.X) * Config.CellSize - 20);
		this.setHeight((BottomRight.Y - TopLeft.Y) * Config.CellSize - 20);
		
		AddStyle();
	}
	
	private void AddStyle()
	{
		this.setStroke(Color.TRANSPARENT);
		this.setFill(Color.TRANSPARENT);
		this.setStrokeWidth(0);
	}
}
