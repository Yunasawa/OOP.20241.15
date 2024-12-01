package CCMR.Models.Values;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import CCMR.Models.Types.Row;
import CCMR.Views.Bases.BaseVisualElement;
import CCMR.Views.Environments.*;
import CCMR.Controls.Systems.*;

public class Global 
{
	// Control
	public static SystemManager SystemManager;
	public static EventManager EventManager;
	
	// Ground
	public static Pane GridPane = new Pane();
	public static Canvas GridCanvas = new Canvas(1920, 1080);
	public static GraphicsContext GridContext = Global.GridCanvas.getGraphicsContext2D();
	public static GridView GridView = new GridView();
	public static Scene GridScene;
	
	// Function
	public static SelectionBox SelectionBox = new SelectionBox();
	public static Row<BaseVisualElement> SelectedElement = new Row<>();
	public static ConnectionNode SelectedNode;
	
	public static Row<WireLine> WireList = new Row<>();
	public static WireLine CurrentWire;
}
