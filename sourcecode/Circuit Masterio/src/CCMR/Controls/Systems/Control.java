// Developed by Yunasawa Studio

package CCMR.Controls.Systems;

import CCMR.Models.Values.View;
import CCMR.Views.Elements.Bulb;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class Control extends Application
{
    public static void main(String[] args) 
    {
        launch();
    }
	
	@Override
	public void start(Stage primaryStage) throws Exception
	{		
		View.GridScene = new Scene(View.GridView.CreateView(), 1000, 750);
		View.GridView.AddKeyManipulator();
		
        primaryStage.setScene(View.GridScene);
        primaryStage.getIcons().add(new Image("file:resources/Icons/Application.png"));
        primaryStage.setTitle("Circuit Masterio - Yunasawa Studio");
        primaryStage.setMaximized(true);
        primaryStage.show();
        
        Bulb buld1 = new Bulb();
        Bulb buld2 = new Bulb();
        Bulb buld3 = new Bulb();
	}
}