package CCMR.Views.Elements;

import CCMR.Models.Types.*;
import CCMR.Views.Bases.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class DCVoltage extends BaseVisualElement
{
	@Override
	protected void CreateShapes() 
	{
		Line line1 = new Line(0, 50, 100, 50);
		Line line2 = new Line(25, 88, 75, 88);
		Line line3 = new Line(0, 117, 100, 117);
		Line line4 = new Line(25, 150, 75, 150);
        Line wire1 = new Line(50, 0, 50, 50);
        Line wire2 = new Line(50, 150, 50, 200);
        Circle dot1 = new Circle(50, 0, 5);
        Circle dot2 = new Circle(50, 200, 5);
        
        AddShapes(line1, line2, line3, line4, wire1, wire2, dot1, dot2);
        
        Transform.Position = new Vector2(2, 2);
        Transform.Size = new Vector2(2, 4);	
	}
}
