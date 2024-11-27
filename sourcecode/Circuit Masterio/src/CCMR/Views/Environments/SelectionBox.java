package CCMR.Views.Environments;

import CCMR.Models.Types.*;
import CCMR.Models.Values.View;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SelectionBox 
{
    private Rectangle _box = new Rectangle(100, 100, 200, 150);
    private Vector2 _startPosition = new Vector2();
    
    public SelectionBox()
    {
    	_box.setStroke(Color.DODGERBLUE); 
    	_box.setStrokeWidth(3);
    	_box.getStrokeDashArray().addAll(10.0, 10.0);
    	_box.setFill(Color.TRANSPARENT);
    	
    	_box.setVisible(false);
    }
    
    public void InsertBoxToPane() { View.GridPane.getChildren().add(_box); }
    
    public void StartSelection(double x, double y)
    {
    	_box.setX(x);
    	_box.setY(y);
    	_box.setWidth(0);
    	_box.setHeight(0);
    	_box.setVisible(true);
    	
    	_startPosition.X = _box.getX();
    	_startPosition.Y = _box.getY();
    }
    
    public void UpdateSelection(double x, double y)
    {    	    	
    	if (x < _startPosition.X) _box.setX(x);
    	_box.setWidth((_startPosition.X - x) * (x < _startPosition.X ? 1 : -1));
    	
    	if (y < _startPosition.Y) _box.setY(y);
    	_box.setHeight((_startPosition.Y - y) * (y < _startPosition.Y ? 1 : -1));
    }
    
    public void HideSelection()
    {
    	_box.setVisible(false);
    }
}