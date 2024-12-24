// Developed by Yunasawa Studio

package CCMR.Controls.Systems;

import CCMR.Controls.Bases.BaseCircuitElement;
import CCMR.Controls.Elements.ACSource;
import CCMR.Controls.Elements.Capacitor;
import CCMR.Controls.Elements.Inductor;
import CCMR.Controls.Elements.Resistor;
import CCMR.Controls.Utilities.MDebug;
import CCMR.Models.Types.CircuitType;
import CCMR.Models.Types.PropertyType;
import CCMR.Models.Values.Global;
import CCMR.Views.Bases.BaseVisualElement;
import CCMR.Views.Elements.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
    private TextField capaField;

    @FXML
    private TextField volField;

    @FXML
    private TextField freField;

    @FXML
    private TextField inducField;

    @FXML
    private TextField resField;

    @FXML
    void handleCapaClick(MouseEvent event) {
    	
    }
    @FXML
    void handleBuildClick(MouseEvent event) 
    {
        Global.ACSource.AssignValue(PropertyType.Voltage, Double.parseDouble(volField.getText()));
        Global.ACSource.AssignValue(PropertyType.Frequency, Double.parseDouble(freField.getText()));
        Global.Capacitor.AssignValue(PropertyType.Capacitance, Double.parseDouble(capaField.getText()));
        Global.Inductor.AssignValue(PropertyType.Inductance, Double.parseDouble(inducField.getText()));
        Global.Resistor.AssignValue(PropertyType.Resistance, Double.parseDouble(resField.getText()));
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
        } else if (!option.isVisible()) {
            option.setVisible(true);
        }
    }

    @FXML
    void handleLabelClick(MouseEvent event) {
        Label clickedLabel = (Label) event.getSource();
        if (clickedLabel.getText().equals("Capacitor")) {
            Global.CircuitSystem.CreateCircuitElement(CircuitType.Capacitor);

        } else
        if(clickedLabel.getText().equals("Inductor")) {
            Global.CircuitSystem.CreateCircuitElement(CircuitType.Inductor);

        } else
        if(clickedLabel.getText().equals("Resistor")) {
            Global.CircuitSystem.CreateCircuitElement(CircuitType.Resistor);

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