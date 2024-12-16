package CCMR.Views.Elements;

import CCMR.Controls.Utilities.MShape;
import CCMR.Views.Bases.*;
import CCMR.Views.Environments.*;
import javafx.scene.shape.*;

public class ResistorUI extends BaseVisualElement
{
	@Override
	protected void CreateShapes()
	{
        Line wire1 = MShape.Normalize(new Line(), 50, 0, 50, 50);
        Line wire2 = MShape.Normalize(new Line(), 50, 150, 50, 200);
        Rectangle rec1 = MShape.Normalize(new Rectangle(), 25, 50, 50, 100);
        ConnectionNode dot1 = MShape.Normalize(new ConnectionNode(this), 50, 0);
        ConnectionNode dot2 = MShape.Normalize(new ConnectionNode(this), 50, 200);
        
        AddShapes(wire1, wire2, rec1, dot1, dot2);
        
        Collider = new Collider(0, 0, 2, 4);
	}
}