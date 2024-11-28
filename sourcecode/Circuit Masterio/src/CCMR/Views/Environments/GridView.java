package CCMR.Views.Environments;

import CCMR.Models.Definitions.*;
import CCMR.Models.Values.*;
import CCMR.Views.Bases.*;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

public class GridView extends BasePaneView
{
    private GridType _gridType = GridType.Dot;
    
    @Override 
    public Pane CreateView() 
    { 
    	Pane viewPane = super.CreateView(); 
    	
    	View.SelectionBox.InsertBoxToPane();
    	
    	return viewPane; 
	}
    
	@Override
	protected void DrawView() 
    {
		View.GridContext.save();
        View.GridContext.scale(Data.ScaleValue, Data.ScaleValue);

        View.GridContext.fillRect(0, 0, View.GridCanvas.getWidth() / Data.ScaleValue, View.GridCanvas.getHeight() / Data.ScaleValue);

        _startPosition.X = Data.GridOffset.X % Config.CellSize;
        _startPosition.Y = Data.GridOffset.Y % Config.CellSize;

        if (_gridType == GridType.Dash) View.GridContext.setLineDashes(5);
        else if (_gridType == GridType.Dot) 
        {
            for (double x = -_startPosition.X; x < View.GridCanvas.getWidth() / Data.ScaleValue; x += Config.CellSize) 
            {
                for (double y = -_startPosition.Y; y < View.GridCanvas.getHeight() / Data.ScaleValue; y += Config.CellSize) 
                {
                	View.GridContext.setFill(Config.GridLineColor);
                	View.GridContext.fillOval(x - 2.5, y - 2.5, 5, 5);
                }
            }
        } 
        else 
        {
            for (double x = -_startPosition.X; x < View.GridCanvas.getWidth() / Data.ScaleValue; x += Config.CellSize) 
            {
            	View.GridContext.strokeLine(x, 0, x, View.GridCanvas.getHeight() / Data.ScaleValue);
            }
            for (double y = -_startPosition.Y; y < View.GridCanvas.getHeight() / Data.ScaleValue; y += Config.CellSize) 
            {
            	View.GridContext.strokeLine(0, y, View.GridCanvas.getWidth() / Data.ScaleValue, y);
            }
        }

        View.GridContext.restore();
    }
	
    public void AddKeyManipulator()
    {
		View.GridScene.setOnKeyPressed(event ->
		{
			if (event.getCode() == KeyCode.DELETE) 
    		{
    			System.out.println("HOLA");
    			RemoveSelectedElement();
    		}
		});
    }
	
	@Override
	protected void OnDragMouseDragged()  { DrawView(); }
	@Override
	protected void OnZoomMouseScrolled() { DrawView(); }
}