package CCMR.Controls.Systems;

import CCMR.Models.Interfaces.*;
import CCMR.Models.Types.*;
import CCMR.Models.Values.Global;
import javafx.scene.input.KeyCode;

public class EventManager 
{
	public Row<IKeyPressListenable> IKeyPressListeners = new Row<>();
	public Row<IKeyReleaseListenable> IKeyReleaseListeners = new Row<>();
	
	public EventManager()
	{
        Global.GridScene.setOnKeyPressed(event -> { InvokeOnKeyPressedEvent(event.getCode()); });
        Global.GridScene.setOnKeyReleased(event -> { InvokeOnKeyReleasedEvent(event.getCode()); });
	}
	
	public void InvokeOnKeyPressedEvent(KeyCode key)
	{
		for (IKeyPressListenable listener : Global.EventManager.IKeyPressListeners) listener.OnKeyPressed(key);
	}
	
	public void InvokeOnKeyReleasedEvent(KeyCode key)
	{
		for (IKeyReleaseListenable listener : Global.EventManager.IKeyReleaseListeners) listener.OnKeyReleased(key);
	}
}
