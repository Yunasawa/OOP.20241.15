package CCMR.Controls.Bases;

import java.util.HashSet;
import java.util.Set;
import CCMR.Controls.Elements.*;

class CircuitBoard {
    private ACSource source;
    private Set<BaseCircuitElement> elements = new HashSet<>();

    public CircuitBoard(ACSource source) {
        this.source = source;
    }

    public void addElement(BaseCircuitElement element) {
        elements.add(element);
    }

    public double calculateTotalImpedance() {
        // Implement a method to traverse the graph and calculate total impedance
        return traverseConnections(source.Connection1, new HashSet<>(), source.Frequency);
    }

    private double traverseConnections(CircuitConnection connection, Set<CircuitConnection> visited, double frequency) {
        if (visited.contains(connection)) {
            return 0;
        }
        visited.add(connection);

        double impedance = connection.Element.GetImpedance(frequency);
        for (CircuitConnection connected : connection.ConnectedNodes) {
            impedance += traverseConnections(connected, visited, frequency);
        }
        return impedance;
    }

    public double calculateCurrent() {
        double totalImpedance = calculateTotalImpedance();
        return source.Voltage / totalImpedance;
    }

    public void calculateVoltagesAndCurrents() {
        double current = calculateCurrent();
        double frequency = source.Frequency;

        for (BaseCircuitElement element : elements) {
            double voltage = current * element.GetImpedance(frequency);
            System.out.printf("Voltage across %s: %.2f V\n", element.getClass().getSimpleName(), voltage);
            System.out.printf("Current through %s: %.2f A\n", element.getClass().getSimpleName(), current);
        }
    }
}
