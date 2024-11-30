package CCMR.Controls.Utilities;

import CCMR.Models.Types.*;
import CCMR.Models.Values.Config;
import CCMR.Models.Values.Data;
import CCMR.Views.Environments.*;
import javafx.scene.shape.*;

public class MShape 
{
	public static Row<Double> GetProperties(Shape shape)
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
	public static void SetRotate(Shape shape, Vector2 pivot, Row<Double> row, int rotation)
	{
		if (shape instanceof Circle) 
		{ 
			Circle circle = (Circle)shape;
			
			pivot = pivot.Multiply(Config.CellSize);
			
			Vector2 distance = new Vector2(row.get(1), row.get(2)).Multiply(Data.ScaleValue);
			
			MDebug.Log(new Vector2(row.get(1), row.get(2)) + " | " + pivot + " | " + distance);
			
			if (rotation == 0)
			{
				circle.setCenterX(row.get(1) * Data.ScaleValue);
				circle.setCenterY(row.get(2) * Data.ScaleValue);
			}
			else if (rotation == 1)
			{
				circle.setCenterX(pivot.X - distance.Y);
				circle.setCenterY(pivot.Y + distance.X);
			}
			else if (rotation == 2)
			{
				circle.setCenterX(pivot.X - distance.X);
				circle.setCenterY(pivot.Y - distance.Y);
			}
			else if (rotation == 3)
			{
				circle.setCenterX(pivot.X + distance.Y);
				circle.setCenterY(pivot.Y - distance.X);
			}
		}
		else if (shape instanceof Rectangle)
		{ 
			Rectangle rectangle = (Rectangle)shape;
			
			Vector2 distance = new Vector2(row.get(0), row.get(1)).Subtract(pivot).Multiply(Data.ScaleValue);
			
			if (rotation == 0)
			{
				rectangle.setX(row.get(0) * Data.ScaleValue);
				rectangle.setY(row.get(1) * Data.ScaleValue);
				rectangle.setWidth(row.get(2) * Data.ScaleValue);
				rectangle.setHeight(row.get(3) * Data.ScaleValue);
			}
			else if (rotation == 1)
			{
				rectangle.setX(pivot.X - distance.Y - row.get(3) * Data.ScaleValue);
				rectangle.setY(pivot.Y + distance.X);
				rectangle.setWidth(row.get(3) * Data.ScaleValue);
				rectangle.setHeight(row.get(2) * Data.ScaleValue);
			}
			else if (rotation == 2)
			{
				rectangle.setX(pivot.X - distance.X - row.get(2) * Data.ScaleValue);
				rectangle.setY(pivot.Y - distance.Y - row.get(3) * Data.ScaleValue);
				rectangle.setWidth(row.get(2) * Data.ScaleValue);
				rectangle.setHeight(row.get(3) * Data.ScaleValue);
			}
			else if (rotation == 3)
			{
				rectangle.setX(pivot.X + distance.Y);
				rectangle.setY(pivot.Y - distance.X - row.get(2) * Data.ScaleValue);
				rectangle.setWidth(row.get(3) * Data.ScaleValue);
				rectangle.setHeight(row.get(2) * Data.ScaleValue);
			}
		}
		else if (shape instanceof Line)
		{ 
			Line line = (Line)shape;
			
			Vector2 start = new Vector2(row.get(0), row.get(1)).Subtract(pivot).Multiply(Data.ScaleValue);
			Vector2 end = new Vector2(row.get(2), row.get(3)).Subtract(pivot).Multiply(Data.ScaleValue);
			
			if (rotation == 0)
			{
				line.setStartX(row.get(0) * Data.ScaleValue);
				line.setStartY(row.get(1) * Data.ScaleValue);
				line.setEndX(row.get(2) * Data.ScaleValue);
				line.setEndY(row.get(3) * Data.ScaleValue);
			}
			else if (rotation == 1)
			{
				line.setStartX(pivot.X - start.Y); 
				line.setStartY(pivot.Y + start.X); 
				line.setEndX(pivot.X - end.Y); 
				line.setEndY(pivot.Y + end.X);
			}
			else if (rotation == 2)
			{
				line.setStartX(pivot.X - start.X); 
				line.setStartY(pivot.Y - start.Y); 
				line.setEndX(pivot.X - end.X); 
				line.setEndY(pivot.Y - end.Y);
			}
			else if (rotation == 3)
			{
				line.setStartX(pivot.X + start.Y); 
				line.setStartY(pivot.Y - start.X); 
				line.setEndX(pivot.X + end.Y); 
				line.setEndY(pivot.Y - end.X);
			}
		}
	}
}
