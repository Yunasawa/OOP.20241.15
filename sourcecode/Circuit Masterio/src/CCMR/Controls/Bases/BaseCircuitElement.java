package CCMR.Controls.Bases;

public abstract class BaseCircuitElement 
{
    public CircuitConnection Connection1 = new CircuitConnection(this);
    public CircuitConnection Connection2 = new CircuitConnection(this);

    public abstract double GetImpedance(double frequency);
}