package CCMR.Controls.Elements;

import CCMR.Controls.Bases.CircuitElement;

public class ACSource extends CircuitElement 
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
}
