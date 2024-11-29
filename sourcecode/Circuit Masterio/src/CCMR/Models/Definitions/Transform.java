package CCMR.Models.Definitions;

import CCMR.Models.Types.*;

public class Transform
{
	public Vector2 Position;
	public byte Rotation;
	//public Vector2 Size;
	
	public Transform(Vector2 position, byte rotation, Vector2 size) { Position = position; Rotation = rotation; }//Size = size.ToVector2(); }
	public Transform(Vector2 position, byte rotation) { this(position, rotation, new Vector2()); }
	public Transform(Vector2 position) { this(position, (byte)0); }
	public Transform() { this(new Vector2()); }
}