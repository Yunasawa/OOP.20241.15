package CCMR.Controls.Bases;

import java.util.ArrayList;
import java.util.List;

public class CircuitConnection 
{
    public BaseCircuitElement Element;
    public List<CircuitConnection> ConnectedNodes = new ArrayList<>();

    public CircuitConnection(BaseCircuitElement element) 
    {
    	Element = element;
    }

    public void ConnectWith(CircuitConnection... connections) 
    {
        for (CircuitConnection connection : connections) 
        {
            if (!ConnectedNodes.contains(connection)) 
            {
            	ConnectedNodes.add(connection);
                connection.ConnectWith(this);
            }
        }
    }
}