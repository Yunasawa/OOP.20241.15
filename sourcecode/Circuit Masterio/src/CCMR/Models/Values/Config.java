package CCMR.Models.Values;

import CCMR.Controls.Utilities.MFormula;
import javafx.scene.paint.Color;

public class Config
{
    public static final double CellSize = 50;
    
    public static final Color BackgroundColor = new Color(1, 1, 1, 1);
    public static final Color GridLineColor = new Color(0.5, 0.5, 0.5, 1);
    
    public static final double StrokeWidth = 5;
    
    public static final Color ElementColor = MFormula.ToColor("#404040");
    public static final Color HoverColor = MFormula.ToColor("#7a7d42");
    public static final Color CollisionColor = MFormula.ToColor("#bd3333");
    public static final Color SelectedColor = MFormula.ToColor("#33bdb8");
    
    public static final Color NodeColor = MFormula.ToColor("#45ff9f");
    public static final Color WireColor = MFormula.ToColor("#777777");
}
