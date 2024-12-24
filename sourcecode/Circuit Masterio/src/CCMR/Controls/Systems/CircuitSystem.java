package CCMR.Controls.Systems;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;



import CCMR.Controls.Bases.BaseCircuitElement;
import CCMR.Controls.Bases.*;
import CCMR.Controls.Elements.*;
import CCMR.Models.Interfaces.IKeyPressListenable;
import CCMR.Models.Types.CircuitType;
import CCMR.Models.Values.Global;
import CCMR.Views.Bases.BaseVisualElement;
import CCMR.Views.Elements.*;
import javafx.scene.input.KeyCode;

public class CircuitSystem implements IKeyPressListenable
{
	private ACSource _acSource;
	
	public CircuitSystem()
	{
		RegisterListener();
	}

	@Override
	public void OnKeyPressed(KeyCode key) 
	{
		if (key == KeyCode.DIGIT1) CreateCircuitElement(CircuitType.ACSource);
		else if (key == KeyCode.DIGIT2) CreateCircuitElement(CircuitType.Resistor);
		else if (key == KeyCode.DIGIT3) CreateCircuitElement(CircuitType.Inductor);
		else if (key == KeyCode.DIGIT4) CreateCircuitElement(CircuitType.Capacitor);
		else if (key == KeyCode.ENTER)
		{
	        System.out.println("Total Impedance (Series): " + CalculateTotalImpedance() + " ohms");
	        System.out.println("Total Current (Series): " + CalculateCurrent() + " A");
	        CalculateVoltagesAndCurrents();
		}
	}
	
	public void CreateCircuitElement(CircuitType type)
	{
		BaseVisualElement ui = null;
		BaseCircuitElement el = null;
		
		if (type == CircuitType.ACSource)
		{
			ui = new ACSourceUI();
			el = new ACSource(220, 60);
			if (_acSource == null) _acSource = (ACSource)el;
			Global.ACSource = _acSource;
		}
		else if (type == CircuitType.Resistor)
		{
			ui = new ResistorUI();
			el = new Resistor(10);
			Global.Resistor = (Resistor)el;
		}
		else if (type == CircuitType.Inductor)
		{
			ui = new InductorUI();
			el = new Inductor(0.01);
			Global.Inductor = (Inductor)el;
		}
		else if (type == CircuitType.Capacitor)
		{
			ui = new CapacitorUI();
			el = new Capacitor(0.000001);
			Global.Capacitor = (Capacitor)el;
		}
		
		if (ui != null && el != null) 
		{
			Global.CreatedElement = el;
			Global.CircuitPairs.put(ui, el);
		}
	}



    public double CalculateTotalImpedance() 
    {
        return TraverseConnections(_acSource.Connection1, new HashSet<>(), _acSource.Frequency);
    }
    
    private double TraverseConnections(CircuitConnection connection, Set<CircuitConnection> visited, double frequency) 
    {
        if (visited.contains(connection)) return 0;

        visited.add(connection);

        double impedance = connection.Element.GetImpedance(frequency);
        for (CircuitConnection connected : connection.ConnectedNodes) 
        {
            impedance += TraverseConnections(connected, visited, frequency);
        }
        return impedance;
    }

    public double CalculateCurrent() 
    {
        double totalImpedance = CalculateTotalImpedance();
        return _acSource.Voltage / totalImpedance;
    }

    public void CalculateVoltagesAndCurrents() 
    {
        double current = CalculateCurrent();
        double frequency = _acSource.Frequency;

        for (Map.Entry<BaseVisualElement, BaseCircuitElement> element : Global.CircuitPairs.entrySet()) 
        {
            double voltage = current * element.getValue().GetImpedance(frequency);
            System.out.printf("Voltage across %s: %.2f V\n", element.getClass().getSimpleName(), voltage);
            System.out.printf("Current through %s: %.2f A\n", element.getClass().getSimpleName(), current);
        }
    }
}
