package CCMR.Controls.Bases;

import java.util.HashSet;
import java.util.Set;

class Circuit {
    private ACSource source;
    private Set<CircuitElement> elements = new HashSet<>();

    public Circuit(ACSource source) {
        this.source = source;
    }

    public void addElement(CircuitElement element) {
        elements.add(element);
    }

    public double calculateTotalImpedance() {
        // Implement a method to traverse the graph and calculate total impedance
        return traverseConnections(source.getConnection1(), new HashSet<>(), source.getFrequency());
    }

    private double traverseConnections(Connection connection, Set<Connection> visited, double frequency) {
        if (visited.contains(connection)) {
            return 0;
        }
        visited.add(connection);

        double impedance = connection.getElement().getImpedance(frequency);
        for (Connection connected : connection.getConnectedTo()) {
            impedance += traverseConnections(connected, visited, frequency);
        }
        return impedance;
    }

    public double calculateCurrent() {
        double totalImpedance = calculateTotalImpedance();
        return source.getVoltage() / totalImpedance;
    }

    public void calculateVoltagesAndCurrents() {
        double current = calculateCurrent();
        double frequency = source.getFrequency();

        for (CircuitElement element : elements) {
            double voltage = current * element.getImpedance(frequency);
            System.out.printf("Voltage across %s: %.2f V\n", element.getClass().getSimpleName(), voltage);
            System.out.printf("Current through %s: %.2f A\n", element.getClass().getSimpleName(), current);
        }
    }
}
