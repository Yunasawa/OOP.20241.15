// Developed by Yunasawa Studio

package CCMR.Controls.Systems;

import CCMR.Models.Values.View;
import CCMR.Views.Elements.Bulb;
import CCMR.Views.Environments.*;
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
		GridView gridView = View.GridView;
		
        primaryStage.setScene(new Scene(gridView.CreateView(), 1000, 750));
        primaryStage.getIcons().add(new Image("file:resources/Icons/Application.png"));
        primaryStage.setTitle("Circuit Masterio - Yunsawa Studio");
        primaryStage.setMaximized(true);
        primaryStage.show();
        
        Bulb buld1 = new Bulb();
        Bulb buld2 = new Bulb();
        Bulb buld3 = new Bulb();
	}
}