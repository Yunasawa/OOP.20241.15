package CCMR.Models.Types;

public class Vector2 
{
	public double X;
	public double Y;
	
	public Vector2(double x, double y) { X = x; Y = y; }
	public Vector2() { this(0, 0); }
	
	public void Set(double x, double y) { X = x; Y = y; }
}
