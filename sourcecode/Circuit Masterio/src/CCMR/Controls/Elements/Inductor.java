package CCMR.Controls.Elements;

import CCMR.Controls.Bases.*;

public class Inductor extends CircuitElement 
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
}
