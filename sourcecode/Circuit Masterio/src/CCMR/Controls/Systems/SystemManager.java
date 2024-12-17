// Developed by Yunasawa Studio

package CCMR.Controls.Systems;

import CCMR.Models.Types.CircuitType;
import CCMR.Models.Values.Global;
import CCMR.Views.Elements.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class SystemManager extends Application
{
    // controller

    @FXML
    private AnchorPane option;

    @FXML
    private BorderPane borderPane;

    @FXML
    void handleMouseClick(MouseEvent event) {
        if (option.isVisible()) {
            option.setVisible(false);
        } else {
            option.setVisible(true);
        }
    }

    @FXML
    private void initialize() {
        borderPane.setOnMouseClicked(this::handleMouseClick);
    }










    // start
    public static void main(String[] args) { launch(); }
	
	@Override
	public void start(Stage primaryStage) throws Exception
	{		
		Global.SceneNode = FXMLLoader.load(getClass().getResource("/CCMR/Controls/Systems/example.fxml"));

		Global.SystemManager = this;
		
		Global.GridScene = new Scene(Global.GridView.CreateView(), 1260, 787);
		Global.GridView.AddKeyManipulator();

        Global.GridPane.getChildren().add(Global.SceneNode);

        primaryStage.setScene(Global.GridScene);
        primaryStage.getIcons().add(new Image("file:resources/Icons/Application.png"));
        primaryStage.setTitle("Circuit Masterio - Yunasawa Studio");
        primaryStage.show();
        
        Global.EventManager = new EventManager();
                
        CircuitSystem circuitSystem = new CircuitSystem();
        circuitSystem.CreateCircuitElement(CircuitType.ACSource);
	}
}