package CCMR.Controls.Utilities;

import java.util.*;

public class MDebug 
{
	public static void Log(Object object) { System.out.println(object.toString()); }
	public static void Random() { System.out.println(new Random().nextInt(10000)); }
}
