package CCMR.Views.Elements;

import CCMR.Controls.Utilities.MShape;
import CCMR.Views.Bases.*;
import CCMR.Views.Environments.*;
import javafx.scene.shape.*;

public class InductorUI extends BaseVisualElement
{
	@Override
	protected void CreateShapes()
	{
        Line wire1 = MShape.Normalize(new Line(), 50, 0, 50, 50);
        Line wire2 = MShape.Normalize(new Line(), 50, 150, 50, 200);
        
        Arc arc1 = MShape.Normalize(new Arc(), 50, 65, 25, 15, 90, 180);
        Arc arc2 = MShape.Normalize(new Arc(), 50, 88, 25, 15, 90, 180);
        Arc arc3 = MShape.Normalize(new Arc(), 50, 112, 25, 15, 90, 180);
        Arc arc4 = MShape.Normalize(new Arc(), 50, 135, 25, 15, 90, 180);
        
        Arc arc5 = MShape.Normalize(new Arc(), 50, 76.5, 25, 3.5, 270, 180);
        Arc arc6 = MShape.Normalize(new Arc(), 50, 100, 25, 3, 270, 180);
        Arc arc7 = MShape.Normalize(new Arc(), 50, 123, 25, 3.5, 270, 180);
        
        AddShapes(wire1, wire2, arc1, arc2, arc3, arc4, arc5, arc6, arc7, Node1, Node2);
        
        Collider = new Collider(0, 0, 2, 4);
	}
}