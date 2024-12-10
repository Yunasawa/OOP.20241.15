package CCMR.Views.Elements;

import CCMR.Views.Bases.*;
import CCMR.Views.Environments.*;
import javafx.scene.shape.*;

public class Bulb extends BaseVisualElement
{
	protected void CreateShapes()
	{
        Circle outerCircle = new Circle(50, 100, 50);
        //Rectangle outerCircle = new Rectangle(50, 100, 100, 150);
		Line line1 = new Line(18, 68, 82, 132);
        Line line2 = new Line(18, 132, 82, 68);
        Line wire1 = new Line(50, 0, 50, 50);
        Line wire2 = new Line(50, 150, 50, 200);
        ConnectionNode dot1 = new ConnectionNode(this, 50, 0);
        ConnectionNode dot2 = new ConnectionNode(this, 50, 200);
        
        AddShapes(outerCircle, line1, line2, wire1, wire2, dot1, dot2);
        
        Collider = new Collider(0, 0, 2, 4);
	}
}