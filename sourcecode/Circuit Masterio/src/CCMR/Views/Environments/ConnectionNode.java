package CCMR.Views.Environments;

import CCMR.Controls.Utilities.Utilities;
import CCMR.Models.Values.Config;
import CCMR.Models.Values.Data;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class ConnectionNode extends Circle
{
	public ConnectionNode(double x, double y)
	{
		super(x, y, 7.5);
		
		SetColor(Config.ElementColor);
        this.setStrokeWidth(Data.StrokeWidth);
        
        AddToggleEventHandlers();
	}
	
	public void SetColor(Color color)
	{
        this.setStroke(color);
        this.setFill(color);
	}
	
    private void AddToggleEventHandlers()
    {
        this.setOnMouseEntered(event ->
        {
        	this.setStroke(Utilities.ToColor("#45ff9f"));
        	this.setFill(Utilities.ToColor("#45ff9f"));
        }); 
        
        this.setOnMouseExited(event -> 
        { 
        	this.setStroke(Config.ElementColor);
        	this.setFill(Config.ElementColor);
        });
    }
}
