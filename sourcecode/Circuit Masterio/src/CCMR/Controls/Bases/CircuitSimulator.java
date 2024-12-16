package CCMR.Controls.Bases;

import CCMR.Controls.Elements.*;

public class CircuitSimulator 
{
    public static void main(String[] args) 
    {
        ACSource acSource = new ACSource(220, 60); // 220V, 60Hz
        CircuitBoard circuit = new CircuitBoard(acSource);
        
        Resistor resistor = new Resistor(10); // 10 ohms
        Inductor inductor = new Inductor(0.01); // 10 mH
        Capacitor capacitor = new Capacitor(0.000001); // 1 uF

        circuit.addElement(resistor);
        circuit.addElement(inductor);
        circuit.addElement(capacitor);

        // Series circuit example
        acSource.Connection1.ConnectWith(resistor.Connection1);
        resistor.Connection2.ConnectWith(inductor.Connection1);
        inductor.Connection2.ConnectWith(capacitor.Connection1);
        capacitor.Connection2.ConnectWith(acSource.Connection2);

        System.out.println("Total Impedance (Series): " + circuit.calculateTotalImpedance() + " ohms");
        System.out.println("Total Current (Series): " + circuit.calculateCurrent() + " A");
        
        circuit.calculateVoltagesAndCurrents();

        System.out.print("\n");
        
        // Reset connections for parallel circuit example
        circuit = new CircuitBoard(acSource);
        circuit.addElement(resistor);
        circuit.addElement(inductor);
        circuit.addElement(capacitor);

        //acSource.Connection1.ConnectWith(resistor.Connection1, inductor.Connection1, capacitor.Connection1);
        //acSource.Connection2.ConnectWith(resistor.Connection2, inductor.Connection2, capacitor.Connection2);

        acSource.Connection1.ConnectWith(resistor.Connection1, inductor.Connection1);
        acSource.Connection2.ConnectWith(resistor.Connection2, capacitor.Connection2);
        inductor.Connection2.ConnectWith(capacitor.Connection1);
        
        System.out.println("Total Impedance (Parallel): " + circuit.calculateTotalImpedance() + " ohms");
        System.out.println("Total Current (Parallel): " + circuit.calculateCurrent() + " A");

        circuit.calculateVoltagesAndCurrents();
    }
}