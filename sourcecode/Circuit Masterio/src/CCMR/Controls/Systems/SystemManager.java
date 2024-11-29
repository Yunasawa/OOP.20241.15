// Developed by Yunasawa Studio

package CCMR.Controls.Systems;

import java.util.ArrayList;
import java.util.List;

import CCMR.Controls.Utilities.MDebug;
import CCMR.Models.Interfaces.IKeyPressListenable;
import CCMR.Models.Values.Global;
import CCMR.Views.Elements.*;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class SystemManager extends Application
{
	private List<IKeyPressListenable> _listener = new ArrayList<>();
	
    public static void main(String[] args) 
    {
        launch();
    }
	
	@Override
	public void start(Stage primaryStage) throws Exception
	{		
		Global.SystemManager = this;
		
		Global.GridScene = new Scene(Global.GridView.CreateView(), 1000, 750);
		Global.GridView.AddKeyManipulator();
		
        primaryStage.setScene(Global.GridScene);
        primaryStage.getIcons().add(new Image("file:resources/Icons/Application.png"));
        primaryStage.setTitle("Circuit Masterio - Yunasawa Studio");
        primaryStage.setMaximized(true);
        primaryStage.show();
        
        Global.GridScene.setOnKeyPressed(event ->
        {
        	MDebug.Log("HEHE");
        	InvokeEvent(event.getCode());
        });
        
        Bulb bulb1 = new Bulb();
        Bulb bulb2 = new Bulb();
        DCVoltage dcVoltage = new DCVoltage();
	}

	public void RegisterListener(IKeyPressListenable listener)
	{
		_listener.add(listener);
	}
	public void InvokeEvent(KeyCode key)
	{
		for (IKeyPressListenable listener : _listener) listener.OnKeyPressed(key);
	}
}