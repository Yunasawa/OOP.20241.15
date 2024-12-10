package CCMR.Controls.Elements;

public class Led 
{
	public void Display(boolean input)
	{
		System.out.println("LED is " + (input ? "ON" : "OFF"));
	}
}
