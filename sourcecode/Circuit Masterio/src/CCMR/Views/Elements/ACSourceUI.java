package CCMR.Views.Elements;

import CCMR.Controls.Utilities.MShape;
import CCMR.Views.Bases.*;
import CCMR.Views.Environments.*;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class ACSourceUI extends BaseVisualElement
{
	@Override
	protected void CreateShapes() 
	{
		CircuitType = CircuitType.ACSource;
		
        Circle outerCircle = MShape.Normalize(new Circle(), 50, 100, 50);
        Arc arc1 = MShape.Normalize(new Arc(), 50, 112.5, 12.5, 12.5, 90, 180);
        Arc arc2 = MShape.Normalize(new Arc(), 50, 87.5, 12.5, 12.5, 270, 180);
        Line wire1 = MShape.Normalize(new Line(), 50, 0, 50, 50);
        Line wire2 = MShape.Normalize(new Line(), 50, 150, 50, 200);
        
        AddShapes(outerCircle, arc1, arc2, wire1, wire2, Node1, Node2);
        
        Collider = new Collider(0, 0, 2, 4);	
	}
}