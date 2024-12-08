package CCMR.Controls.Elements;

import CCMR.Controls.Bases.CircuitElement;

public class Resistor extends CircuitElement {
    private double resistance; // Điện trở (Ohm)

    public Resistor(double resistance) {
        this.resistance = resistance;
    }

    @Override
    public void calculateCurrent() {
        if (voltage != 0 && resistance != 0) {
            current = voltage / resistance; // Định luật Ohm
        }
    }

    @Override
    public void calculateVoltage() {
        if (input != null) {
            voltage = input.voltage - (current * resistance); // Điện áp giảm
        }
    }

	@Override
	public void Perform() {
		// TODO Auto-generated method stub
		
	}
}
