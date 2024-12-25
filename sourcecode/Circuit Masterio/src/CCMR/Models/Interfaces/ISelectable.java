package CCMR.Models.Interfaces;

import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Shape;

public interface ISelectable
{
	void OnSelected();
	void OnDeselected();
	
	default void OnMouseSelected(Shape shape, MouseEvent event) {}
	
	default void AddSelectingCallback(Shape shape)
	{
		shape.setOnMouseClicked(event -> 
		{
			OnMouseSelected(shape, event);
		}); 
	}
}
