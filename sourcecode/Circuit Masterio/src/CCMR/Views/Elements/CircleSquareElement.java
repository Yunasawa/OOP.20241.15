package CCMR.Views.Elements;

import CCMR.Models.Types.Vector2;
import CCMR.Models.Values.Data;
import CCMR.Views.Environments.VisualElement;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class CircleSquareElement extends VisualElement<Shape> 
{
    @Override
    protected void CreateShapes() 
    {
        Circle circle = new Circle(50); // Example circle
        Rectangle square = new Rectangle(100, 100); // Example square

        shapes.add(circle);
        shapes.add(square);

        Transform.Size = new Vector2(2, 2); // Size in grid units
    }

    @Override
    protected void UpdateShapeSize(Shape shape) 
    {
        if (shape instanceof Circle) 
        {
            ((Circle)shape).setRadius(50 * Data.ScaleValue); // Adjust radius for circle
        } 
        else if (shape instanceof Rectangle) 
        {
            ((Rectangle)shape).setWidth(100 * Data.ScaleValue); // Adjust width for square
            ((Rectangle)shape).setHeight(100 * Data.ScaleValue); // Adjust height for square
        }
    }
}