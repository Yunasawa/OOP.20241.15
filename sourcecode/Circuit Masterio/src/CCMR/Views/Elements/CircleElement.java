package CCMR.Views.Elements;

import CCMR.Models.Types.Vector2;
import CCMR.Models.Values.Data;
import CCMR.Views.Environments.VisualElement;
import javafx.scene.shape.Circle;

public class CircleElement extends VisualElement<Circle> 
{
    @Override
    protected void CreateShapes() 
    {
        Circle outerCircle = new Circle(50, 50, 50); // Outer circle
        Circle innerCircle = new Circle(50, 50, 40); // Inner circle
        shapes.add(outerCircle);
        shapes.add(innerCircle);
        
        Transform.Size = new Vector2(2, 2); // Size in grid units
    }

    @Override
    protected void UpdateShapeSize(Circle shape) 
    {
        // Use the index to identify the outer and inner circles
        int index = shapes.indexOf(shape);
        if (index == 0) 
        {
        	shape.setCenterX(50 * Data.ScaleValue);
        	shape.setCenterY(50 * Data.ScaleValue);
            shape.setRadius(50 * Data.ScaleValue); // Adjust radius of outer circle
        } 
        else 
        {
        	shape.setCenterX(50 * Data.ScaleValue);
        	shape.setCenterY(50 * Data.ScaleValue);
            shape.setRadius(40 * Data.ScaleValue); // Adjust radius of inner circle
        }
    }
}