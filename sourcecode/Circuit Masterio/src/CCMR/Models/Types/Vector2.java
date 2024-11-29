package CCMR.Models.Types;

public class Vector2 
{
	public double X;
	public double Y;
	
	public Vector2(double x, double y) { X = x; Y = y; }
	public Vector2() { this(0, 0); }
	
	public void Set(double x, double y) { X = x; Y = y; }
	public double Distance(Vector2 other) { return Math.sqrt(Math.pow(this.X - other.X, 2) + Math.pow(this.Y - other.Y, 2)); }
	
	public Vector2 Subtract(Vector2 other) { return new Vector2(this.X - other.X, this.Y - other.Y); } 
	public Vector2 Add(Vector2 other) { return new Vector2(this.X + other.X, this.Y + other.Y); }
	public Vector2 Multiply(double multiplier) { return new Vector2(this.X * multiplier, this.Y * multiplier); }
	
	public Vector2 Round() { return new Vector2(Math.round(this.X), Math.round(this.Y)); }
	
	@Override
	public String toString() { return "(" + X + ", " + Y + ")"; }
}
