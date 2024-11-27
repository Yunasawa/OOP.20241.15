package CCMR.Views.Elements;

import CCMR.Models.Types.Vector2;
import CCMR.Models.Values.Data;
import CCMR.Views.Environments.VisualElement;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class SquareElement extends VisualElement 
{
    @Override
    protected void CreateShapes() 
    {
        Rectangle outerSquare = new Rectangle(0, 0, 100, 100); // Outer square
        Rectangle innerSquare = new Rectangle(10, 10, 80, 80); // Inner square
        
        AddShapes(outerSquare, innerSquare);
        
        Transform.Size = new Vector2(2, 2);
    }

    @Override
    protected void UpdateShapeSize(Shape shape) 
    {
        // Use the index to identify the outer and inner squares
        int index = _shapes.indexOf(shape);
        if (index == 0) 
        {
            ((Rectangle)shape).setWidth(100 * Data.ScaleValue); // Adjust width of outer square
            ((Rectangle)shape).setHeight(100 * Data.ScaleValue); // Adjust height of outer square
        } 
        else 
        {
        	((Rectangle)shape).setX(10 * Data.ScaleValue);
        	((Rectangle)shape).setY(10 * Data.ScaleValue);
        	((Rectangle)shape).setWidth(80 * Data.ScaleValue); // Adjust width of inner square
        	((Rectangle)shape).setHeight(80 * Data.ScaleValue); // Adjust height of inner square
        }
    }
}