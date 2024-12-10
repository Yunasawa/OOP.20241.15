package CCMR.Controls.Systems;

import CCMR.Controls.Elements.*;

public class CircuitManager 
{	
	public static void main(String arg[])
	{
		DigitalConstant dc1 = new DigitalConstant(true);
		DigitalConstant dc2 = new DigitalConstant(false);
		
		OrGate orGate = new OrGate();
		AndGate andGate = new AndGate();
		
		Led led = new Led();
		led.Display(orGate.Operate(dc1.Value, dc2.Value));
		led.Display(andGate.Operate(dc1.Value, dc2.Value));
	}
}
