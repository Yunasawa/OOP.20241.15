// Developed by Yunasawa Studio

package CCMR.Controls.Systems;

import CCMR.Controls.Bases.BaseCircuitElement;
import CCMR.Models.Types.CircuitType;
import CCMR.Models.Values.Global;
import CCMR.Views.Bases.BaseVisualElement;
import CCMR.Views.Elements.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.control.Label;

import java.awt.*;
import java.util.Map;

public class SystemManager extends Application
{
    // controller
    @FXML
    private HBox capacitance;

    @FXML
    private HBox inductor;

    @FXML
    private HBox resistance;

    @FXML
    private AnchorPane option;

    @FXML
    private BorderPane borderPane;

    @FXML
    private Label label;

    @FXML
    private Label outputLabel;
    @FXML
    private Button buildBtn;

    @FXML
    void handleBuildClick(MouseEvent event) {
        // Gọi các phương thức tính toán
        double current = Global.CircuitSystem.CalculateCurrent();
        double frequency = Global.CircuitSystem.CalculateCurrent();
        double totalImpedance = Global.CircuitSystem.CalculateTotalImpedance();
        double totalCurrent = Global.CircuitSystem.CalculateCurrent();

        StringBuilder result = new StringBuilder();
        result.append(String.format("Total Impedance (Series): %.2f ohms\n", totalImpedance));
        result.append(String.format("Total Current (Series): %.2f A\n", totalCurrent));

        for (Map.Entry<BaseVisualElement, BaseCircuitElement> element : Global.CircuitPairs.entrySet()) {
            double voltage = current * element.getValue().GetImpedance(frequency);
            result.append(String.format("Voltage across %s: %.2f V\n", element.getValue().getClass().getSimpleName(), voltage));
            result.append(String.format("Current through %s: %.2f A\n", element.getValue().getClass().getSimpleName(), current));
        }

        outputLabel.setText(result.toString());
    }

    @FXML
    void handleMouseClick(MouseEvent event) {
        if (option.isVisible() && !option.contains(event.getX(), event.getY())) {
            option.setVisible(false);
            capacitance.setVisible(false);
            inductor.setVisible(false);
            resistance.setVisible(false);
        } else if (!option.isVisible()) {
            option.setVisible(true);
        }
    }

    @FXML
    void handleLabelClick(MouseEvent event) {
        Label clickedLabel = (Label) event.getSource();
        if (clickedLabel.getText().equals("Capacitor")) {
            Global.CircuitSystem.CreateCircuitElement(CircuitType.Capacitor);
            if(capacitance.isVisible() && capacitance.contains(event.getX(), event.getY())) {
                capacitance.setVisible(false);
            } else {
                capacitance.setVisible(true);
            }
        } else
        if(clickedLabel.getText().equals("Inductor")) {
            Global.CircuitSystem.CreateCircuitElement(CircuitType.Inductor);
            if(inductor.isVisible() && inductor.contains(event.getX(), event.getY())) {
                inductor.setVisible(false);
            } else {
                inductor.setVisible(true);
            }
        } else
        if(clickedLabel.getText().equals("Resistor")) {
            Global.CircuitSystem.CreateCircuitElement(CircuitType.Resistor);
            if(resistance.isVisible() && resistance.contains(event.getX(), event.getY())) {
                resistance.setVisible(false);
            } else {
                resistance.setVisible(true);
            }
        }
        event.consume();
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
                
        Global.CircuitSystem = new CircuitSystem();
        Global.CircuitSystem.CreateCircuitElement(CircuitType.ACSource);
	}
}