package CCMR.Views.Elements;

import CCMR.Models.Types.Vector2;
import CCMR.Views.Environments.VisualElement;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class CircleSquareElement extends VisualElement 
{
    @Override
    protected void CreateShapes() 
    {
        Circle circle = new Circle(50, 50, 50); // Example circle
        Rectangle square = new Rectangle(50, 50, 100, 100); // Example square

        AddShapes(circle, square);

        Transform.Size = new Vector2(3, 3); // Size in grid units
    }
}