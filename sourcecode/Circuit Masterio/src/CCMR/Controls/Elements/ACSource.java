package CCMR.Controls.Elements;

import CCMR.Controls.Bases.BaseCircuitElement;
import CCMR.Models.Types.PropertyType;

public class ACSource extends BaseCircuitElement
{
    public double Voltage;
    public double Frequency;

    public ACSource(double voltage, double frequency) 
    {
    	Voltage = voltage;
    	Frequency = frequency;
    }

    @Override
    public double GetImpedance(double frequency) { return 0; }

	@Override
	public void AssignValue(PropertyType property, double value)
	{
		if (property == PropertyType.Voltage) Voltage = value;
		else if (property == PropertyType.Frequency) Frequency = value;
	}
}
