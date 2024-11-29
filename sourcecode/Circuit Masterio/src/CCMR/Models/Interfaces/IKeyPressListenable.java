package CCMR.Models.Interfaces;

import CCMR.Controls.Systems.SystemManager;
import javafx.scene.input.KeyCode;

public interface IKeyPressListenable
{
	void OnKeyPressed(KeyCode key);
	
	default void RegisterListener(SystemManager control)
	{
		control.RegisterListener(this);
	}
}
