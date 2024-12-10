package CCMR.Controls.Elements;

import CCMR.Controls.Bases.LogicGate;

public class NotGate extends LogicGate
{
	@Override
	public boolean Operate(boolean... inputs) 
	{
		return !inputs[0];
	}
}
