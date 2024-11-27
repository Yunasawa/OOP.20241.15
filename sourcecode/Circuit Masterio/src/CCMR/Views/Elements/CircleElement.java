package CCMR.Views.Elements;

import CCMR.Models.Types.Vector2;
import CCMR.Views.Environments.VisualElement;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class CircleElement extends VisualElement
{
    @Override
    protected void CreateShapes() 
    {
        Circle outerCircle = new Circle(50, 50, 50); // Outer circle
        Circle innerCircle = new Circle(50, 50, 40); // Inner circle
        Line firstLine = new Line(50, -50, 50, 0);
        Line secondLine = new Line(50, 150, 50, 100);
        
        AddShapes(outerCircle, innerCircle, firstLine, secondLine);
        
        Transform.Size = new Vector2(2, 4); // Size in grid units
    }
}