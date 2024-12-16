package CCMR.Controls.Elements;

import CCMR.Controls.Bases.BaseCircuitElement;

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
}