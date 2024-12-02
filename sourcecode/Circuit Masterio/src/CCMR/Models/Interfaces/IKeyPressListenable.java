package CCMR.Models.Interfaces;

import CCMR.Models.Values.*;
import javafx.scene.input.KeyCode;

public interface IKeyPressListenable
{
	void OnKeyPressed(KeyCode key);
	
	default void RegisterListener()
	{
		Global.EventManager.IKeyPressListeners.Add(this);
	}
}