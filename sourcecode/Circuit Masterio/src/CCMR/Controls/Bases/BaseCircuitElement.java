package CCMR.Controls.Bases;

import CCMR.Models.Types.PropertyType;

public abstract class BaseCircuitElement 
{
    public CircuitConnection Connection1 = new CircuitConnection(this);
    public CircuitConnection Connection2 = new CircuitConnection(this);

    public abstract double GetImpedance(double frequency);
    public abstract void AssignValue(PropertyType property, double value);
}