package CCMR.Controls.Utilities;

import CCMR.Models.Types.*;
import CCMR.Models.Values.*;

public class MFormula 
{
	public static Vector2 GetWorldPosition(Vector2 position, Vector2 parent)
	{
		return position.Add(parent.Multiply(Config.CellSize)).Subtract(Data.GridOffset);
	}
}
