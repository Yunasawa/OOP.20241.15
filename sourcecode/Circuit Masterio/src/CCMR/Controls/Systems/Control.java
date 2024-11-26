package CCMR.Controls.Systems;

import CCMR.Models.Definitions.GridType;
import CCMR.Models.Values.View;
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
		//GridView gridView = View.GridView;
		GridViewDemo gridView = View.GridViewDemo.SetGridType(GridType.Dot);
		
        primaryStage.setScene(new Scene(gridView.CreateView(), 1000, 750));
        primaryStage.getIcons().add(new Image("file:resources/Icons/Application.png"));
        primaryStage.setTitle("Circuit Masterio");
        primaryStage.setMaximized(true);
        primaryStage.show();
	}
}