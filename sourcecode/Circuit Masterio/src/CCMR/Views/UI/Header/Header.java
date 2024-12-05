package CCMR.Views.UI.Header;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Header extends Application {
    public static void main(String[] args) {
        launch(args); // trong args chứa hàm header
    }
    @Override
    // javafx thực hiê giao diện dạng sân khấu
    public void start(Stage primaryStage) {
        // Tạo GridPane
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER); // Căn giữa lưới

        // Đặt các cột có thể co giãn
        for (int i = 0; i < 3; i++) {
            gridPane.getColumnConstraints().add(new javafx.scene.layout.ColumnConstraints());
            gridPane.getColumnConstraints().get(i).setHgrow(Priority.ALWAYS);
        }

        // Đặt các hàng có thể co giãn
        for (int i = 0; i < 3; i++) {
            gridPane.getRowConstraints().add(new javafx.scene.layout.RowConstraints());
            gridPane.getRowConstraints().get(i).setVgrow(Priority.ALWAYS);
        }


        Button btn1 = new Button("Button 1");
        Button btn2 = new Button("Button 2");
        Button btn3 = new Button("Button 3");


        gridPane.add(btn1, 0, 0);
        gridPane.add(btn2, 1, 0);
        gridPane.add(btn3, 2, 0);


        Scene scene = new Scene(gridPane, 400, 400);


        primaryStage.setTitle("GridPane Relative Positioning");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
