package CCMR.Views.Elements;

import CCMR.Controls.Utilities.MShape;
import CCMR.Views.Bases.*;
import CCMR.Views.Environments.*;
import javafx.scene.shape.*;

public class CapacitorUI extends BaseVisualElement
{
	@Override
	protected void CreateShapes()
	{
		Line line1 = MShape.Normalize(new Line(), 0, 90, 100, 90);
        Line line2 = MShape.Normalize(new Line(), 0, 110, 100, 110);
        Line wire1 = MShape.Normalize(new Line(), 50, 0, 50, 90);
        Line wire2 = MShape.Normalize(new Line(), 50, 110, 50, 200);
        
        AddShapes(line1, line2, wire1, wire2, Node1, Node2);
        
        Collider = new Collider(0, 0, 2, 4);
	}
}