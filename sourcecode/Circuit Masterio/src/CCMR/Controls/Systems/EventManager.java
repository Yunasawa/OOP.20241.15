package CCMR.Controls.Systems;

import CCMR.Models.Interfaces.*;
import CCMR.Models.Types.*;
import CCMR.Models.Values.Global;
import javafx.scene.input.KeyCode;

public class EventManager 
{
	public Row<IKeyPressListenable> IKeyPressListeners = new Row<>();
	
	public EventManager()
	{
		if (Global.EventManager == null) Global.EventManager = this;
		
        Global.GridScene.setOnKeyPressed(event -> { InvokeOnKeyPressedEvent(event.getCode()); });
	}
	
	public void InvokeOnKeyPressedEvent(KeyCode key)
	{
		try 
		{
			for (IKeyPressListenable listener : IKeyPressListeners) listener.OnKeyPressed(key);
		} 
		catch (Exception e) 
		{
		
		}

	}
}
