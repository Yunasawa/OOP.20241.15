package CCMR.Controls.Elements;

import CCMR.Controls.Bases.*;

public class Resistor extends CircuitElement 
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
}
