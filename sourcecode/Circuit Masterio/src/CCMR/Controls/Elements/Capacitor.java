package CCMR.Controls.Elements;

import CCMR.Controls.Bases.*;

public class Capacitor extends CircuitElement 
{
    private double _capacitance;

    public Capacitor(double capacitance) 
    {
        _capacitance = capacitance;
    }

    @Override
    public double GetImpedance(double frequency) 
    {
        return 1 / (2 * Math.PI * frequency * _capacitance);
    }
}