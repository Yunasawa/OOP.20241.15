package CCMR.Models.Values;

import CCMR.Controls.Utilities.Utilities;
import javafx.scene.paint.Color;

public class Config
{
    public static final double CellSize = 50;
    
    public static final Color BackgroundColor = new Color(1, 1, 1, 1);
    public static final Color GridLineColor = new Color(0.5, 0.5, 0.5, 1);
    
    public static final double StrokeWidth = 5;
    
    public static final Color ElementColor = Utilities.ToColor("#404040");
    public static final Color HoverColor = Utilities.ToColor("#7a7d42");
    public static final Color CollisionColor = Utilities.ToColor("#bd3333");
    public static final Color SelectedColor = Utilities.ToColor("#33bdb8");
    
    public static final Color NodeColor = Utilities.ToColor("#45ff9f");
    public static final Color WireColor = Utilities.ToColor("#777777");
}
