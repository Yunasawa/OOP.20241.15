package CCMR.Models.Definitions;

import CCMR.Models.Types.*;

public class Transform
{
	public Vector2 Position;
	public byte Rotation;
	
	public Transform(Vector2 position, byte rotation) { Position = position; Rotation = rotation; }
	public Transform(Vector2 position) { this(position, (byte)0); }
	public Transform() { this(new Vector2()); }
}