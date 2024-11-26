package CCMR.Models.Values;

import javafx.scene.layout.Pane;
import CCMR.Views.Environments.*;

public class View 
{
	// Base
	public static Pane GridPane = new Pane();
	public static GridViewDemo GridViewDemo = new GridViewDemo();
	public static GridView GridView = new GridView();
	
	// Runtime
	public static VisualElement<?> SelectedElement;
}
