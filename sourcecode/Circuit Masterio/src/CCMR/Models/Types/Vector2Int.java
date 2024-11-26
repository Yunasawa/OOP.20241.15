package CCMR.Models.Types;

public class Vector2Int
{
	public int X;
	public int Y;
	
	public Vector2Int(int x, int y) { X = x; Y = y; }
	public Vector2Int() { this(0, 0); }
	
	public void Set(int x, int y) { X = x; Y = y; }
	public Vector2 ToVector2() { return new Vector2(X, Y); }
	
	@Override
	public String toString() { return "(" + X + ", " + Y + ")"; }
}
