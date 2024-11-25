package CCMR.Models.Types;

public class Vector2Int
{
	public int X;
	public int Y;
	
	public Vector2Int(int x, int y) { X = x; Y = y; }
	public Vector2Int() { this(0, 0); }
	
	public void Set(int x, int y) { X = x; Y = y; }
	
	@Override
	public String toString() { return "(" + X + ", " + Y + ")"; }
}
