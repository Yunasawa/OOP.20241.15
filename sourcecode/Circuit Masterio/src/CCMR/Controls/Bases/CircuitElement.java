package CCMR.Controls.Bases;

import java.util.ArrayList;
import java.util.List;

public abstract class CircuitElement {
    protected Connection connection1 = new Connection(this);
    protected Connection connection2 = new Connection(this);

    public abstract double GetImpedance(double frequency);

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