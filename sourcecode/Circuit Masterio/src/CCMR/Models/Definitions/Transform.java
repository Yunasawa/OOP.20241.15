package CCMR.Models.Definitions;

import CCMR.Models.Types.*;

public class Transform
{
	public Vector2Int Position;
	public byte Rotation;
	public Vector2Int Size;
	
	public Transform(Vector2Int position, byte rotation, Vector2Int size) { Position = position; Rotation = rotation; Size = size; }
	public Transform(Vector2Int position, byte rotation) { Position = position; Rotation = rotation; }
	public Transform(Vector2Int position) { this(position, (byte)0, new Vector2Int()); }
}