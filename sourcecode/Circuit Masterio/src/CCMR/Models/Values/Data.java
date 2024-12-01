package CCMR.Models.Values;

import CCMR.Models.Types.*;

public class Data 
{
	// Grid view values
	public static Vector2 MouseCoordinate = new Vector2();
	public static Vector2 MouseDelta = new Vector2();
	public static Vector2 GridOffset = new Vector2(-900, -500);
	public static double ScaleValue  = 1;
	
	// Visual element values
	public static double StrokeWidth = 5;
	
	// Function values
	public static boolean IsDraggingElement = false;
	public static long LastMousePressedTime = 0;
	public static Vector2 LastMousePressedPosition = new Vector2();
}
