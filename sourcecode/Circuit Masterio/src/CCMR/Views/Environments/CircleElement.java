package CCMR.Views.Environments;

import CCMR.Models.Values.Data;
import javafx.scene.shape.Circle;

public class CircleElement extends VisualElement<Circle> 
{
    @Override
    protected void CreateShapes() 
    {
        Circle outerCircle = new Circle(50); // Outer circle
        Circle innerCircle = new Circle(40); // Inner circle
        shapes.add(outerCircle);
        shapes.add(innerCircle);
    }

    @Override
    protected void UpdateShapeSize(Circle shape) 
    {
        // Use the index to identify the outer and inner circles
        int index = shapes.indexOf(shape);
        if (index == 0) {
            shape.setRadius(50 * Data.ScaleValue); // Adjust radius of outer circle
        } else {
            shape.setRadius(40 * Data.ScaleValue); // Adjust radius of inner circle
        }
    }
}