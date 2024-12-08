package CCMR.Controls.Bases;

import CCMR.Models.Definitions.*;

public abstract class CircuitElement 
{
	public Transform Transform;
	public double voltage; // Điện áp qua phần tử
    protected double current; // Dòng điện qua phần tử

    // Đầu vào và đầu ra
    protected CircuitElement input;
    protected CircuitElement output;

    public CircuitElement() {}

    // Các phương thức cơ bản
    public abstract void calculateCurrent(); // Tính dòng điện
    public abstract void calculateVoltage(); // Tính điện áp

    // Setter và Getter cho đầu vào/đầu ra
    public void setInput(CircuitElement input) {
        this.input = input;
    }

    public void setOutput(CircuitElement output) {
        this.output = output;
    }

    public CircuitElement getInput() {
        return input;
    }

    public CircuitElement getOutput() {
        return output;
    }
	public abstract void Perform();
}
