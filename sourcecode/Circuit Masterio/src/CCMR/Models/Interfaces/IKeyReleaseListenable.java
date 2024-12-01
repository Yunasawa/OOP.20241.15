package CCMR.Models.Interfaces;

import CCMR.Models.Values.*;
import javafx.scene.input.KeyCode;

public interface IKeyReleaseListenable
{
	void OnKeyReleased(KeyCode key);
	
	default void RegisterListener()
	{
		Global.EventManager.IKeyReleaseListeners.Add(this);
	}
}
