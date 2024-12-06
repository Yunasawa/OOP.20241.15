package CCMR.Views.UI.Header;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("abc.fxml"));
        primaryStage.setTitle("CIRCUIT LAB");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }
}
