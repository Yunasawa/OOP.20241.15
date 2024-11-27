package CCMR.Models.Values;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import CCMR.Views.Environments.*;

public class View 
{
	// Base
	public static Pane GridPane = new Pane();
	public static Canvas GridCanvas = new Canvas(1920, 1080);
	public static GraphicsContext GridContext = View.GridCanvas.getGraphicsContext2D();
	public static GridView GridView = new GridView();
	
	// Runtime
	public static VisualElement SelectedElement;
}
