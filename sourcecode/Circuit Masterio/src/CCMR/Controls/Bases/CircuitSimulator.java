package CCMR.Controls.Bases;

public class CircuitSimulator {
    public static void main(String[] args) {
        ACSource acSource = new ACSource(220, 60); // 220V, 60Hz
        Circuit circuit = new Circuit(acSource);
        
        Resistor resistor = new Resistor(10); // 10 ohms
        Inductor inductor = new Inductor(0.01); // 10 mH
        Capacitor capacitor = new Capacitor(0.000001); // 1 uF

        circuit.addElement(resistor);
        circuit.addElement(inductor);
        circuit.addElement(capacitor);

        // Series circuit example
        acSource.getConnection1().connectWith(resistor.getConnection1());
        resistor.getConnection2().connectWith(inductor.getConnection1());
        inductor.getConnection2().connectWith(capacitor.getConnection1());
        capacitor.getConnection2().connectWith(acSource.getConnection2());

        System.out.println("Total Impedance (Series): " + circuit.calculateTotalImpedance() + " ohms");
        System.out.println("Total Current (Series): " + circuit.calculateCurrent() + " A");
        
        circuit.calculateVoltagesAndCurrents();

        System.out.print("\n");
        
        // Reset connections for parallel circuit example
        circuit = new Circuit(acSource);
        circuit.addElement(resistor);
        circuit.addElement(inductor);
        circuit.addElement(capacitor);

        //acSource.getConnection1().connectWith(resistor.getConnection1(), inductor.getConnection1(), capacitor.getConnection1());
        //acSource.getConnection2().connectWith(resistor.getConnection2(), inductor.getConnection2(), capacitor.getConnection2());

        acSource.getConnection1().connectWith(resistor.getConnection1(), inductor.getConnection1());
        acSource.getConnection2().connectWith(resistor.getConnection2(), capacitor.getConnection2());
        inductor.getConnection2().connectWith(capacitor.getConnection1());
        
        System.out.println("Total Impedance (Parallel): " + circuit.calculateTotalImpedance() + " ohms");
        System.out.println("Total Current (Parallel): " + circuit.calculateCurrent() + " A");

        circuit.calculateVoltagesAndCurrents();
    }
}