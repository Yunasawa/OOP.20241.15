package CCMR.Controls.Elements;

import CCMR.Controls.Bases.*;
import CCMR.Models.Types.PropertyType;

public class Resistor extends BaseCircuitElement 
{
    private double _resistance;

    public Resistor(double resistance) 
    {
    	_resistance = resistance;
    }

    @Override
    public double GetImpedance(double frequency)
    {
        return _resistance;
    }
    
	@Override
	public void AssignValue(PropertyType property, double value)
	{
		if (property == PropertyType.Resistance) _resistance = value;
	}
}
