package CCMR.Models.Definitions;

public class Transform 
{
	public int X;
	public int Y;
	public byte Rotation;
	
	public Transform(int x, int y, byte rotation) { X = x; Y = y; Rotation = rotation; }
	public Transform(int x, int y) { this(x, y, (byte)0); }
}