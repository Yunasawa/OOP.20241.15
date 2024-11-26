package CCMR.Views.Elements;

import CCMR.Models.Types.Vector2;
import CCMR.Models.Values.Data;
import CCMR.Views.Environments.VisualElement;
import javafx.scene.shape.Rectangle;

public class SquareElement extends VisualElement<Rectangle> 
{
    @Override
    protected void CreateShapes() 
    {
        Rectangle outerSquare = new Rectangle(100, 100); // Outer square
        Rectangle innerSquare = new Rectangle(80, 80); // Inner square
        shapes.add(outerSquare);
        shapes.add(innerSquare);
        
        Transform.Size = new Vector2(2, 2);
    }

    @Override
    protected void UpdateShapeSize(Rectangle shape) 
    {
        // Use the index to identify the outer and inner squares
        int index = shapes.indexOf(shape);
        if (index == 0) 
        {
            shape.setWidth(100 * Data.ScaleValue); // Adjust width of outer square
            shape.setHeight(100 * Data.ScaleValue); // Adjust height of outer square
        } 
        else 
        {
            shape.setWidth(80 * Data.ScaleValue); // Adjust width of inner square
            shape.setHeight(80 * Data.ScaleValue); // Adjust height of inner square
        }
    }
}