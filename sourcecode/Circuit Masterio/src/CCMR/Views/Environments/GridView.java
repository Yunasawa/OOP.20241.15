package CCMR.Views.Environments;

import CCMR.Models.Types.GridType;
import CCMR.Models.Values.*;
import CCMR.Views.Bases.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;

public class GridView extends BasePaneView
{
    private GridType _gridType = GridType.Dot;
    
    @Override 
    public Pane CreateView() 
    { 
    	Pane viewPane = super.CreateView(); 
    	
    	Global.SelectionBox.InsertBoxToPane();
    	
    	return viewPane; 
	}    
	@Override
	protected void DrawView()
    {
		Global.GridContext.save();
        Global.GridContext.scale(Data.ScaleValue, Data.ScaleValue);

        Global.GridContext.fillRect(0, 0, Global.GridCanvas.getWidth() / Data.ScaleValue, Global.GridCanvas.getHeight() / Data.ScaleValue);

        _startPosition.X = Data.GridOffset.X % Config.CellSize;
        _startPosition.Y = Data.GridOffset.Y % Config.CellSize;

        if (_gridType == GridType.Dash) Global.GridContext.setLineDashes(5);
        else if (_gridType == GridType.Dot) 
        {
            for (double x = -_startPosition.X; x < Global.GridCanvas.getWidth() / Data.ScaleValue; x += Config.CellSize) 
            {
                for (double y = -_startPosition.Y; y < Global.GridCanvas.getHeight() / Data.ScaleValue; y += Config.CellSize) 
                {
                	Global.GridContext.setFill(Config.GridLineColor);
                	Global.GridContext.fillOval(x - 2.5, y - 2.5, 5, 5);
                }
            }
        } 
        else 
        {
            for (double x = -_startPosition.X; x < Global.GridCanvas.getWidth() / Data.ScaleValue; x += Config.CellSize) 
            {
            	Global.GridContext.strokeLine(x, 0, x, Global.GridCanvas.getHeight() / Data.ScaleValue);
            }
            for (double y = -_startPosition.Y; y < Global.GridCanvas.getHeight() / Data.ScaleValue; y += Config.CellSize) 
            {
            	Global.GridContext.strokeLine(0, y, Global.GridCanvas.getWidth() / Data.ScaleValue, y);
            }
        }

        Global.GridContext.restore();
    }	
    public void AddKeyManipulator()
    {
		Global.GridScene.setOnKeyPressed(event ->
		{
			if (event.getCode() == KeyCode.DELETE) 
    		{
    			System.out.println("HOLA");
    			RemoveSelectedElement();
    		}
		});
    }
	
    public void AddShapes(Shape... shapes)
    {
    	for (Shape shape : shapes)
    	{
        	if (Global.GridPane.getChildren().contains(shape)) continue;
        	Global.GridPane.getChildren().add(shape);
    	}
    }
    public void AddShapes(int index, Shape... shapes)
    {
    	for (Shape shape : shapes)
    	{
        	if (Global.GridPane.getChildren().contains(shape)) continue;
        	Global.GridPane.getChildren().add(index, shape);	
    	}
    }
    public void RemoveShapes(Shape...shapes)
    {
    	for (Shape shape : shapes)
    	{
        	if (!Global.GridPane.getChildren().contains(shape)) continue;
        	
        	Global.GridPane.getChildren().remove(shape);	
    	}
    }
    
	@Override
	protected void OnDragMouseDragged()  { DrawView(); }
	@Override
	protected void OnZoomMouseScrolled() { DrawView(); }
}