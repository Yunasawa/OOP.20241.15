package CCMR.Models.Definitions;

import CCMR.Models.Types.*;

public class Transform
{
	public Vector2 Position;
	public byte Rotation;
	//public Vector2 Size;
	
	public Transform(Vector2Int position, byte rotation, Vector2Int size) { Position = position.ToVector2(); Rotation = rotation; }//Size = size.ToVector2(); }
	public Transform(Vector2Int position, byte rotation) { this(position, rotation, new Vector2Int()); }
	public Transform(Vector2Int position) { this(position, (byte)0); }
	public Transform() { this(new Vector2Int()); }
}