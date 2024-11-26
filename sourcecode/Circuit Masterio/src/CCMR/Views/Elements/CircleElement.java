package CCMR.Views.Elements;

import CCMR.Models.Types.Vector2;
import CCMR.Models.Values.Data;
import CCMR.Views.Environments.VisualElement;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

public class CircleElement extends VisualElement<Shape> 
{
    @Override
    protected void CreateShapes() 
    {
        Circle outerCircle = new Circle(50, 50, 50); // Outer circle
        Circle innerCircle = new Circle(50, 50, 40); // Inner circle
        Line firstLine = new Line(50, -50, 50, 0);
        Line secondLine = new Line(50, 150, 50, 100);
        
        shapes.add(outerCircle);
        shapes.add(innerCircle);
        shapes.add(firstLine);
        shapes.add(secondLine);
        
        Transform.Size = new Vector2(2, 4); // Size in grid units
    }

    @Override
    protected void UpdateShapeSize(Shape shape) 
    {
        // Use the index to identify the outer and inner circles
        int index = shapes.indexOf(shape);
        if (index == 0) 
        {
        	((Circle)shape).setCenterX(50 * Data.ScaleValue);
        	((Circle)shape).setCenterY(50 * Data.ScaleValue);
        	((Circle)shape).setRadius(50 * Data.ScaleValue);       
        } 
        else if (index == 1)
        {
        	((Circle)shape).setCenterX(50 * Data.ScaleValue);
        	((Circle)shape).setCenterY(50 * Data.ScaleValue);
        	((Circle)shape).setRadius(40 * Data.ScaleValue); 
        }
        else if (index == 2)
        {
        	((Line)shape).setStartX(50 * Data.ScaleValue);
        	((Line)shape).setStartY(-50 * Data.ScaleValue);
        	((Line)shape).setEndX(50 * Data.ScaleValue);
        	((Line)shape).setEndY(0 * Data.ScaleValue);
        }
        else if (index == 3)
        {
        	((Line)shape).setStartX(50 * Data.ScaleValue);
        	((Line)shape).setStartY(150 * Data.ScaleValue);
        	((Line)shape).setEndX(50 * Data.ScaleValue);
        	((Line)shape).setEndY(100 * Data.ScaleValue);
        }
    }
}