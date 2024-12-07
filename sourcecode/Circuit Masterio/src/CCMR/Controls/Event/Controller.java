package CCMR.Controls.Event;

import javafx.fxml.FXML;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

import java.awt.*;

public class Controller {
    @FXML
    private Canvas canvas;

    @FXML
    public void initialize() {
        if (canvas != null) {
            drawGrid();
        } else {
            System.out.println("Loi");

        }
    }

    private void drawGrid() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        double width = canvas.getWidth();
        double height = canvas.getHeight();
        double cellSize = 20;

        gc.setStroke(Color.LIGHTGRAY);

        for (double x = 0; x <= width; x += cellSize) {
            gc.strokeLine(x, 0, x, height);
        }

        for (double y = 0; y <= height; y += cellSize) {
            gc.strokeLine(0, y, width, y);

        }
    }
}
