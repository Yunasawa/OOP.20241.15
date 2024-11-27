package CCMR.Views.Environments;

import CCMR.Models.Types.Vector2;
import CCMR.Models.Values.Config;
import CCMR.Models.Values.Data;
import CCMR.Models.Values.View;
import CCMR.Views.Bases.BaseVisualElement;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SelectionBox 
{
    private Rectangle _box = new Rectangle();
    private Vector2 _startPosition = new Vector2();
    
    public SelectionBox()
    {
        _box.setStroke(Color.DODGERBLUE); 
        _box.setStrokeWidth(3);
        _box.getStrokeDashArray().addAll(10.0, 10.0);
        _box.setFill(Color.TRANSPARENT);
        _box.setVisible(false);
    }
    
    public void InsertBoxToPane() 
    { 
        View.GridPane.getChildren().add(_box); 
        AddSelectionManipulator();
    }
    
    public void StartSelection(double x, double y)
    {
    	if (Data.IsDraggingElement) return;
    	
        _box.setX(x);
        _box.setY(y);
        _box.setWidth(0);
        _box.setHeight(0);
        
        _startPosition.X = _box.getX();
        _startPosition.Y = _box.getY();
        
        _box.setVisible(true);
    }

    public void UpdateSelection(double x, double y)
    {               
    	if (Data.IsDraggingElement) return;
    	
        if (x < _startPosition.X) _box.setX(x);
        _box.setWidth((_startPosition.X - x) * (x < _startPosition.X ? 1 : -1));
        
        if (y < _startPosition.Y) _box.setY(y);
        _box.setHeight((_startPosition.Y - y) * (y < _startPosition.Y ? 1 : -1));
        
        CheckIntersections();
    }

    public void HideSelection()
    {
        _box.setVisible(false);
    }

    private void AddSelectionManipulator()
    {
        View.GridPane.addEventHandler(MouseEvent.MOUSE_PRESSED, event ->
        {
            if (event.isPrimaryButtonDown()) StartSelection(event.getX(), event.getY());
        });
        
        View.GridPane.addEventHandler(MouseEvent.MOUSE_DRAGGED, event ->
        {
            if (event.isPrimaryButtonDown()) UpdateSelection(event.getX(), event.getY());
        });
        
        View.GridPane.addEventHandler(MouseEvent.MOUSE_RELEASED, event ->
        {            
            HideSelection();
        });
    }

    private void CheckIntersections()
    {
        double selectionX = _box.getX();
        double selectionY = _box.getY();
        double selectionWidth = _box.getWidth();
        double selectionHeight = _box.getHeight();

        for (BaseVisualElement element : View.GridView.Elements)
        {
            double elementLeft = (element.Transform.Position.X * Config.CellSize) * Data.ScaleValue - Data.GridOffset.X * Data.ScaleValue;
            double elementRight = elementLeft + element.Transform.Size.X * Config.CellSize * Data.ScaleValue;
            double elementTop = (element.Transform.Position.Y * Config.CellSize) * Data.ScaleValue - Data.GridOffset.Y * Data.ScaleValue;
            double elementBottom = elementTop + element.Transform.Size.Y * Config.CellSize * Data.ScaleValue;
            
            boolean intersects = selectionX < elementRight &&
                                 selectionX + selectionWidth > elementLeft &&
                                 selectionY < elementBottom &&
                                 selectionY + selectionHeight > elementTop;

            if (intersects) element.SetStrokeColor(Color.RED);
            else element.SetStrokeColor(Config.ElementColor);
        }
    }
}
