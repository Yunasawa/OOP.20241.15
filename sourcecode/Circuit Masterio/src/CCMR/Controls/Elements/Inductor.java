package CCMR.Controls.Elements;

import CCMR.Controls.Bases.*;
import CCMR.Models.Types.PropertyType;

public class Inductor extends BaseCircuitElement 
{
    private double _inductance;

    public Inductor(double inductance) 
    {
        _inductance = inductance;
    }

    @Override
    public double GetImpedance(double frequency) 
    {
        return 2 * Math.PI * frequency * _inductance;
    }
    
	@Override
	public void AssignValue(PropertyType property, double value)
	{
		if (property == PropertyType.Inductance) _inductance = value;
	}
}
