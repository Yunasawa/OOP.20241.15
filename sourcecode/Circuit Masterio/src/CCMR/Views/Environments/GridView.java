package CCMR.Views.Environments;

import CCMR.Models.Definitions.*;
import CCMR.Models.Values.*;
import CCMR.Views.Bases.*;
import CCMR.Views.Elements.CircleElement;
import CCMR.Views.Elements.SquareElement;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class GridView extends BasePaneView
{
    private GridType _gridType = GridType.Dot;
	
	@Override
	public Pane CreateView()
	{
		super.CreateView();
		
        if (_gridType == GridType.Dash) _gc.setLineDashes(5);
        
        DrawGrid(_canvas, Config.GridLineColor);
        
		return _basePane;
	}
	
	private void DrawGrid(Canvas canvas, Color color) 
    {
        _gc.save();
        _gc.scale(Data.ScaleValue, Data.ScaleValue);

        _gc.fillRect(0, 0, _canvas.getWidth() / Data.ScaleValue, _canvas.getHeight() / Data.ScaleValue);

        _startPosition.X = Data.GridOffset.X % Config.CellSize;
        _startPosition.Y = Data.GridOffset.Y % Config.CellSize;

        if (_gridType == GridType.Dot) 
        {
            for (double x = -_startPosition.X; x < _canvas.getWidth() / Data.ScaleValue; x += Config.CellSize) 
            {
                for (double y = -_startPosition.Y; y < _canvas.getHeight() / Data.ScaleValue; y += Config.CellSize) 
                {
                    _gc.setFill(color);
                    _gc.fillOval(x - 2.5, y - 2.5, 5, 5);
                }
            }
        } 
        else 
        {
            for (double x = -_startPosition.X; x < _canvas.getWidth() / Data.ScaleValue; x += Config.CellSize) 
            {
                _gc.strokeLine(x, 0, x, _canvas.getHeight() / Data.ScaleValue);
            }
            for (double y = -_startPosition.Y; y < _canvas.getHeight() / Data.ScaleValue; y += Config.CellSize) 
            {
                _gc.strokeLine(0, y, _canvas.getWidth() / Data.ScaleValue, y);
            }
        }

        _gc.restore();
    }

	@Override
	protected void OnDragMouseDragged()  { DrawGrid(_canvas, Config.GridLineColor); }
	@Override
	protected void OnZoomMouseScrolled() { DrawGrid(_canvas, Config.GridLineColor); }
}