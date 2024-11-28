package CCMR.Controls.Utilities;

import java.sql.DatabaseMetaData;

import CCMR.Models.Types.*;
import CCMR.Models.Values.Data;
import CCMR.Views.Environments.*;
import javafx.scene.shape.*;

public class MShape 
{
	public static void SetScale(Shape shape, Row<Double> row, double scale)
	{
		if (shape instanceof Circle) 
		{ 
			Circle circle = (Circle)shape;
			circle.setRadius(row.get(0) * scale);
			circle.setCenterX(row.get(1) * scale);
			circle.setCenterY(row.get(2) * scale);
		}
		else if (shape instanceof Rectangle)
		{ 
			Rectangle rectangle = (Rectangle)shape;
			rectangle.setX(row.get(0) * scale);
			rectangle.setY(row.get(1) * scale);
			rectangle.setWidth(row.get(2) * scale);
			rectangle.setHeight(row.get(3) * scale);
		}
		else if (shape instanceof Line)
		{ 
			Line line = (Line)shape;
			line.setStartX(row.get(0) * scale);
			line.setStartY(row.get(1) * scale);
			line.setEndX(row.get(2) * scale);
			line.setEndY(row.get(3) * scale);
		}
		else if (shape instanceof WireLine)
		{
			WireLine wire = (WireLine)shape;
			wire.getPoints().clear();
			for (Double point : row) wire.getPoints().add(point + Data.GridOffset.X);
		}
	}
	public static Row<Double> GetScale(Shape shape)
	{
		Row<Double> row = new Row<>();
		
		if (shape instanceof Circle) 
		{ 
			Circle circle = (Circle)shape;
			row.Add(circle.getRadius(), circle.getCenterX(), circle.getCenterY());
		}
		else if (shape instanceof Rectangle)
		{ 
			Rectangle rectangle = (Rectangle)shape;
			row.Add(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
		}
		else if (shape instanceof Line)
		{ 
			Line line = (Line)shape;
			row.Add(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
		}
		else if (shape instanceof WireLine)
		{
			WireLine wire = (WireLine)shape;
			for (Double point : wire.getPoints()) row.Add(point);
		}
		
		return row;
	}

}
