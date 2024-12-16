package CCMR.Controls.Bases;

import java.util.ArrayList;
import java.util.List;

abstract class CircuitElement {
    protected Connection connection1 = new Connection(this);
    protected Connection connection2 = new Connection(this);

    abstract double getImpedance(double frequency);

    public Connection getConnection1() {
        return connection1;
    }

    public Connection getConnection2() {
        return connection2;
    }
}

class Connection {
    private CircuitElement element;
    private List<Connection> connectedTo = new ArrayList<>();

    public Connection(CircuitElement element) {
        this.element = element;
    }

    public void connectWith(Connection... connections) {
        for (Connection connection : connections) {
            if (!connectedTo.contains(connection)) {
                connectedTo.add(connection);
                connection.connectWith(this); // Ensure bidirectional connection
            }
        }
    }

    public List<Connection> getConnectedTo() {
        return connectedTo;
    }

    public CircuitElement getElement() {
        return element;
    }
}

class ACSource extends CircuitElement {
    private double voltage;
    private double frequency;

    public ACSource(double voltage, double frequency) {
        this.voltage = voltage;
        this.frequency = frequency;
    }

    @Override
    double getImpedance(double frequency) {
        return 0; // ACSource doesn't have impedance
    }

    public double getVoltage() {
        return voltage;
    }

    public double getFrequency() {
        return frequency;
    }
}

class Resistor extends CircuitElement {
    private double resistance;

    public Resistor(double resistance) {
        this.resistance = resistance;
    }

    @Override
    double getImpedance(double frequency) {
        return resistance;
    }
}

class Inductor extends CircuitElement {
    private double inductance;

    public Inductor(double inductance) {
        this.inductance = inductance;
    }

    @Override
    double getImpedance(double frequency) {
        return 2 * Math.PI * frequency * inductance;
    }
}

class Capacitor extends CircuitElement {
    private double capacitance;

    public Capacitor(double capacitance) {
        this.capacitance = capacitance;
    }

    @Override
    double getImpedance(double frequency) {
        return 1 / (2 * Math.PI * frequency * capacitance);
    }
}