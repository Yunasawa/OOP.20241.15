package CCMR.Controls.Elements;

import CCMR.Controls.Bases.LogicGate;

public class OrGate extends LogicGate
{
	@Override
	public boolean Operate(boolean... inputs) 
	{
		return inputs[0] || inputs[1];
	}
}
