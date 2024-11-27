package CCMR.Controls.Utilities;

import javafx.scene.paint.Color;

public class Utilities
{
    public static int HexToInt(String hex)
    {
        int output = 0;
        try
        {
            output = Integer.parseInt(hex, 16);
        }
        catch (NumberFormatException e)
        {
        	System.out.println("Format Exception: Invalid HEX Format.");
        }
        return output;
    }
	public static Color ToColor(String hex)
	{
		Color color = Color.WHITE;

		hex = hex.replace("#", "");
		
		try
		{
			if (hex.length() != 6 && hex.length() != 8)
			{
				System.out.println("Format Exception: Invalid HEX Color Format!");
				return color;
			}
			else if (hex.length() == 6)
			{
				float r = HexToInt(hex.substring(0, 2)) / 255f;
				float g = HexToInt(hex.substring(2, 4)) / 255f;
				float b = HexToInt(hex.substring(4, 6)) / 255f;
				color = new Color(r, g, b, 1);
			}
			else if (hex.length() == 8)
			{
				float r = HexToInt(hex.substring(0, 2)) / 255f;
				float g = HexToInt(hex.substring(2, 4)) / 255f;
				float b = HexToInt(hex.substring(4, 6)) / 255f;
				float a = HexToInt(hex.substring(6, 8)) / 255f;
				color = new Color(r, g, b, a);
			}
		}
		catch (Exception e)
		{
			System.out.println("Format Exception: Invalid HEX Format.");
		}
		
		return color;
	}
}
