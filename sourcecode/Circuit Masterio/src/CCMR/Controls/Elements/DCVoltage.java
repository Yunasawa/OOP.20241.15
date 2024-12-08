package CCMR.Controls.Elements;

import CCMR.Controls.Bases.CircuitElement;
import CCMR.Controls.Systems.*;

public class DCVoltage extends CircuitElement
{
	private double sourceVoltage; // Điện áp nguồn

    public DCVoltage(double sourceVoltage) {
        this.sourceVoltage = sourceVoltage;
        this.voltage = sourceVoltage; // Điện áp tại nguồn
    }

    @Override
    public void calculateCurrent() {
        if (output != null) {
            output.calculateCurrent(); // Tính dòng điện tại phần tử tiếp theo
        }
    }

    @Override
    public void calculateVoltage() {
        voltage = sourceVoltage; // Điện áp không đổi
    }
	@Override
	public void Perform()
	{
		
	}

}
