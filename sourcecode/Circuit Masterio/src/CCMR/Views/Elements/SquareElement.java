package CCMR.Views.Elements;

import CCMR.Models.Types.Vector2;
import CCMR.Views.Environments.VisualElement;
import javafx.scene.shape.Rectangle;

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
}