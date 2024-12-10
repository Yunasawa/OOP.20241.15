package CCMR.Views.Elements;

import CCMR.Models.Types.*;
import CCMR.Views.Bases.*;
import CCMR.Views.Environments.*;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class DCVoltage extends BaseVisualElement
{
	protected void CreateShapes() 
	{
		Line line1 = new Line(0, 50, 100, 50);
		Line line2 = new Line(25, 88, 75, 88);
		Line line3 = new Line(0, 117, 100, 117);
		Line line4 = new Line(25, 150, 75, 150);
        Line wire1 = new Line(50, 0, 50, 50);
        Line wire2 = new Line(50, 150, 50, 200);
        ConnectionNode dot1 = new ConnectionNode(this, 50, 0);
        ConnectionNode dot2 = new ConnectionNode(this, 50, 200);
        
        AddShapes(line1, line2, line3, line4, wire1, wire2, dot1, dot2);
        
        Transform.Position = new Vector2(2, 2);
        Collider = new Collider(0, 0, 2, 4);	
	}
}
