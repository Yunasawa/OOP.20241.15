package CCMR.Views.Environments;

import CCMR.Models.Values.Config;
import CCMR.Models.Values.Data;
import CCMR.Models.Values.View;
import CCMR.Models.Types.Vector2;
import javafx.scene.shape.Polyline;
import java.util.ArrayList;
import java.util.List;

public class WireLine extends Polyline 
{
    private List<Vector2> gridPoints;

    public WireLine() 
    {
        this.gridPoints = new ArrayList<>();
        this.gridPoints.add(new Vector2(9, 2));
        this.gridPoints.add(new Vector2(6, 5));
        this.gridPoints.add(new Vector2(8, 10));

        syncPoints();

        this.setStrokeWidth(Data.StrokeWidth);
        this.setStroke(Config.WireColor);

        View.GridPane.getChildren().add(1, this);
    }

    private void syncPoints() 
    {
        this.getPoints().clear();
        for (Vector2 point : gridPoints) 
        {
            double x = (point.X * Config.CellSize - Data.GridOffset.X) * Data.ScaleValue;
            double y = (point.Y * Config.CellSize - Data.GridOffset.Y) * Data.ScaleValue;
            this.getPoints().addAll(x, y);
        }
    }

    public void updateScale(double newScaleValue) 
    {
        Data.ScaleValue = newScaleValue;
        syncPoints();
        this.setStrokeWidth(Data.StrokeWidth);
    }

    public void updateOffset(Vector2 newGridOffset) 
    {
        Data.GridOffset = newGridOffset;
        syncPoints();
    }
}
