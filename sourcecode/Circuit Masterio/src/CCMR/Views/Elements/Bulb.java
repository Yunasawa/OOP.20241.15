package CCMR.Views.Elements;

import CCMR.Models.Types.Vector2;
import CCMR.Views.Environments.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class Bulb extends VisualElement
{
	@Override
	protected void CreateShapes() 
	{
        Circle outerCircle = new Circle(50, 100, 50);
        Line line1 = new Line(18, 68, 82, 132);
        Line line2 = new Line(18, 132, 82, 68);
        Line wire1 = new Line(50, 0, 50, 50);
        Line wire2 = new Line(50, 150, 50, 200);
        Circle dot1 = new Circle(50, 0, 5);
        Circle dot2 = new Circle(50, 200, 5);
        
        AddShapes(outerCircle, line1, line2, wire1, wire2, dot1, dot2);
        
        Transform.Size = new Vector2(2, 4);	
	}

}