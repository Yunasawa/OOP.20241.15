// Developed by Yunasawa Studio

package CCMR.Controls.Systems;

import CCMR.Controls.Elements.DigitalConstant;
import CCMR.Models.Values.Global;
import CCMR.Views.Elements.*;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class SystemManager extends Application
{
    public static void main(String[] args) { launch(); }
	
	@Override
	public void start(Stage primaryStage) throws Exception
	{		
		Global.SystemManager = this;
		
		Global.GridScene = new Scene(Global.GridView.CreateView(), 1260, 787);
		Global.GridView.AddKeyManipulator();

        Global.GridPane.getChildren().add(FXMLLoader.load(getClass().getResource("example.fxml")));

        primaryStage.setScene(Global.GridScene);
        primaryStage.getIcons().add(new Image("file:resources/Icons/Application.png"));
        primaryStage.setTitle("Circuit Masterio - Yunasawa Studio");
        primaryStage.show();
        
        Global.EventManager = new EventManager();
                
        //Bulb bulb1 = new Bulb();
        //Bulb bulb2 = new Bulb();
        //DCVoltage dcVoltage = new DCVoltage();
        
        DigitalConstant dc1 = new DigitalConstant(true);
        DigitalConstant dc2 = new DigitalConstant(true);
	}
}