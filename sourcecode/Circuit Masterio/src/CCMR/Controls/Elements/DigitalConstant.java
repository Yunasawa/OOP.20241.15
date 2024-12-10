package CCMR.Controls.Elements;

import CCMR.Models.Interfaces.IElementVisualizable;
import CCMR.Views.Bases.BaseVisualElement;
import CCMR.Views.Environments.Collider;
import CCMR.Views.Environments.ConnectionNode;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class DigitalConstant implements IElementVisualizable
{
	public boolean Value;
	
	public DigitalConstant(boolean value)
	{
		Value = value;
		Visualize();
	}

	@Override
	public BaseVisualElement Visualize() 
	{
		BaseVisualElement element = new BaseVisualElement();
		
        Circle outerCircle = new Circle(50, 100, 50);
		Line line1 = new Line(18, 68, 82, 132);
        Line line2 = new Line(18, 132, 82, 68);
        Line wire1 = new Line(50, 0, 50, 50);
        Line wire2 = new Line(50, 150, 50, 200);
        ConnectionNode dot1 = new ConnectionNode(element, 50, 0);
        ConnectionNode dot2 = new ConnectionNode(element, 50, 200);
        
        element.AddShapes(outerCircle, line1, line2, wire1, wire2, dot1, dot2);
        element.Collider = new Collider(0, 0, 2, 4);
		element.Visualize();
        
		return element;
	}
}
