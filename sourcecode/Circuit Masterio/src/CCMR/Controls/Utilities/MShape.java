package CCMR.Controls.Utilities;

import CCMR.Models.Types.*;
import CCMR.Models.Values.Data;
import CCMR.Views.Bases.*;
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
			row.Add(circle.getRadius() / Data.ScaleValue, circle.getCenterX() / Data.ScaleValue, circle.getCenterY() / Data.ScaleValue);
		}
		else if (shape instanceof Rectangle)
		{ 
			Rectangle rectangle = (Rectangle)shape;
			row.Add(rectangle.getX() / Data.ScaleValue, rectangle.getY() / Data.ScaleValue, rectangle.getWidth() / Data.ScaleValue, rectangle.getHeight() / Data.ScaleValue);
		}
		else if (shape instanceof Line)
		{ 
			Line line = (Line)shape;
			row.Add(line.getStartX() / Data.ScaleValue, line.getStartY() / Data.ScaleValue, line.getEndX() / Data.ScaleValue, line.getEndY() / Data.ScaleValue);
		}
		else if (shape instanceof WireLine)
		{
			WireLine wire = (WireLine)shape;
			for (Double point : wire.getPoints()) row.Add(point);
		}
		else if (shape instanceof Arc)
		{
			Arc arc = (Arc)shape;
			row.Add(arc.getCenterX() / Data.ScaleValue, arc.getCenterY() / Data.ScaleValue);
			row.Add(arc.getRadiusX() / Data.ScaleValue, arc.getRadiusY() / Data.ScaleValue);
			row.Add(arc.getStartAngle() / Data.ScaleValue, arc.getLength() / Data.ScaleValue);
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
		else if (shape instanceof Arc) 
		{
			Arc arc = (Arc)shape;
			arc.setCenterX(row.get(0) * scale);
			arc.setCenterY(row.get(1) * scale);
			arc.setRadiusX(row.get(2) * scale);
			arc.setRadiusY(row.get(3) * scale);
		}
	}
	public static void SetRotate(Shape shape, Row<Double> row, int rotation)
	{
		if (shape instanceof ConnectionNode)
		{
			ConnectionNode node = (ConnectionNode)shape;
			
			Vector2 distance = new Vector2(row.get(1), row.get(2)).Multiply(Data.ScaleValue);
			
			if (rotation == 0)
			{
				node.setCenterX(row.get(1) * Data.ScaleValue);
				node.setCenterY(row.get(2) * Data.ScaleValue);
			}
			else if (rotation == 1)
			{
				node.setCenterX(- distance.Y);
				node.setCenterY(+ distance.X);
			}
			else if (rotation == 2)
			{
				node.setCenterX(- distance.X);
				node.setCenterY(- distance.Y);
			}
			else if (rotation == 3)
			{
				node.setCenterX(+ distance.Y);
				node.setCenterY(- distance.X);
			}
			
			node.Position = new Vector2(node.getCenterX(), node.getCenterY());
		}
		else if (shape instanceof Circle) 
		{ 
			Circle circle = (Circle)shape;
			
			Vector2 distance = new Vector2(row.get(1), row.get(2)).Multiply(Data.ScaleValue);
			
			if (rotation == 0)
			{
				circle.setCenterX(row.get(1) * Data.ScaleValue);
				circle.setCenterY(row.get(2) * Data.ScaleValue);
			}
			else if (rotation == 1)
			{
				circle.setCenterX(- distance.Y);
				circle.setCenterY(+ distance.X);
			}
			else if (rotation == 2)
			{
				circle.setCenterX(- distance.X);
				circle.setCenterY(- distance.Y);
			}
			else if (rotation == 3)
			{
				circle.setCenterX(+ distance.Y);
				circle.setCenterY(- distance.X);
			}
		}
		else if (shape instanceof Rectangle)
		{ 
			Rectangle rectangle = (Rectangle)shape;
			
			Vector2 distance = new Vector2(row.get(0), row.get(1)).Multiply(Data.ScaleValue);
			
			if (rotation == 0)
			{
				rectangle.setX(distance.X);
				rectangle.setY(distance.Y);
				rectangle.setWidth(row.get(2) * Data.ScaleValue);
				rectangle.setHeight(row.get(3) * Data.ScaleValue);
			}
			else if (rotation == 1)
			{
				rectangle.setX(- distance.Y - row.get(3) * Data.ScaleValue);
				rectangle.setY(+ distance.X);
				rectangle.setWidth(row.get(3) * Data.ScaleValue);
				rectangle.setHeight(row.get(2) * Data.ScaleValue);
			}
			else if (rotation == 2)
			{
				rectangle.setX(- distance.X - row.get(2) * Data.ScaleValue);
				rectangle.setY(- distance.Y - row.get(3) * Data.ScaleValue);
				rectangle.setWidth(row.get(2) * Data.ScaleValue);
				rectangle.setHeight(row.get(3) * Data.ScaleValue);
			}
			else if (rotation == 3)
			{
				rectangle.setX(+ distance.Y);
				rectangle.setY(- distance.X - row.get(2) * Data.ScaleValue);
				rectangle.setWidth(row.get(3) * Data.ScaleValue);
				rectangle.setHeight(row.get(2) * Data.ScaleValue);
			}
		}
		else if (shape instanceof Line)
		{ 
			Line line = (Line)shape;
			
			Vector2 start = new Vector2(row.get(0), row.get(1)).Multiply(Data.ScaleValue);
			Vector2 end = new Vector2(row.get(2), row.get(3)).Multiply(Data.ScaleValue);
			
			if (rotation == 0)
			{
				line.setStartX(start.X);
				line.setStartY(start.Y);
				line.setEndX(end.X);
				line.setEndY(end.Y);
			}
			else if (rotation == 1)
			{
				line.setStartX(- start.Y); 
				line.setStartY(+ start.X); 
				line.setEndX(- end.Y); 
				line.setEndY(+ end.X);
			}
			else if (rotation == 2)
			{
				line.setStartX(- start.X); 
				line.setStartY(- start.Y); 
				line.setEndX(- end.X); 
				line.setEndY(- end.Y);
			}
			else if (rotation == 3)
			{
				line.setStartX(+ start.Y); 
				line.setStartY(- start.X); 
				line.setEndX(+ end.Y); 
				line.setEndY(- end.X);
			}
		}
		else if (shape instanceof Arc)
		{
			Arc arc = (Arc)shape;
			
			Vector2 distance = new Vector2(row.get(0), row.get(1)).Multiply(Data.ScaleValue);
			
			if (rotation == 0)
			{
				arc.setCenterX(row.get(0) * Data.ScaleValue);
				arc.setCenterY(row.get(1) * Data.ScaleValue);
				arc.setRadiusX(row.get(2) * Data.ScaleValue);
				arc.setRadiusY(row.get(3) * Data.ScaleValue);
			}
			else if (rotation == 1)
			{
				arc.setCenterX(- distance.Y);
				arc.setCenterY(+ distance.X);
				arc.setRadiusX(row.get(3) * Data.ScaleValue);
				arc.setRadiusY(row.get(2) * Data.ScaleValue);
			}
			else if (rotation == 2)
			{
				arc.setCenterX(- distance.X);
				arc.setCenterY(- distance.Y);
				arc.setRadiusX(row.get(2) * Data.ScaleValue);
				arc.setRadiusY(row.get(3) * Data.ScaleValue);
			}
			else if (rotation == 3)
			{
				arc.setCenterX(+ distance.Y);
				arc.setCenterY(- distance.X);
				arc.setRadiusX(row.get(3) * Data.ScaleValue);
				arc.setRadiusY(row.get(2) * Data.ScaleValue);
			}
			arc.setStartAngle(row.get(4) + 90 * rotation);
		}
	}
	
	public static void GetRotatedPivot(BaseVisualElement element, Transform transform, Collider collider)
	{
		Vector2 point = collider.Delta.Multiply(0.5).Round();
		
		Vector2 adder, rotatedTopLeft, rotatedBottomRight, fixedTopLeft, fixedBottomRight;
		
    	if (element.Transform.Rotation == 0)
    	{
    		element.Transform.Position = transform.Position;
    		element.Collider = new Collider(collider.TopLeft, collider.BottomRight);
    	}
    	else if (element.Transform.Rotation == 1)
    	{
    		adder = new Vector2(point.X + point.Y, point.Y - point.X);
    		
    		element.Transform.Position = transform.Position.Add(adder);
    		
    		rotatedTopLeft = collider.TopLeft.Add(adder);
    		rotatedBottomRight = collider.BottomRight.Subtract(adder);
    		fixedTopLeft = rotatedTopLeft.Subtract(new Vector2(collider.Size.Y, 0)).Subtract(adder);
    		fixedBottomRight = rotatedBottomRight.Add(new Vector2(collider.Size.Y, 0)).Subtract(adder);
    		
    		element.Collider = new Collider(fixedTopLeft.X, fixedTopLeft.Y, fixedBottomRight.X, fixedBottomRight.Y);
    	}
    	else if (element.Transform.Rotation == 2)
    	{
    		adder = new Vector2(point.X * 2, point.Y * 2);
    		
    		element.Transform.Position = transform.Position.Add(adder);
    		
    		rotatedTopLeft = collider.TopLeft.Add(adder);
    		rotatedBottomRight = collider.BottomRight.Subtract(adder);
    		fixedTopLeft = rotatedTopLeft.Subtract(new Vector2(collider.Size.X, collider.Size.Y)).Subtract(adder);
    		fixedBottomRight = rotatedBottomRight.Add(new Vector2(collider.Size.X, collider.Size.Y)).Subtract(adder);
    		
    		element.Collider = new Collider(fixedTopLeft.X, fixedTopLeft.Y, fixedBottomRight.X, fixedBottomRight.Y);
 
    	}
    	else if (element.Transform.Rotation == 3)
    	{
    		adder = new Vector2(point.X - point.Y, point.X + point.Y);
    		
    		element.Transform.Position = transform.Position.Add(adder);
    		
    		rotatedTopLeft = collider.TopLeft.Add(adder);
    		rotatedBottomRight = collider.BottomRight.Subtract(adder);
    		fixedTopLeft = rotatedTopLeft.Subtract(new Vector2(0, collider.Size.X)).Subtract(adder);
    		fixedBottomRight = rotatedBottomRight.Add(new Vector2(0	, collider.Size.X)).Subtract(adder);
    		
    		element.Collider = new Collider(fixedTopLeft.X, fixedTopLeft.Y, fixedBottomRight.X, fixedBottomRight.Y);
    	}
	}

	public static <T extends Shape> T Normalize(T shape, double... values)
	{
		if (shape instanceof ConnectionNode)
		{
			ConnectionNode circle = (ConnectionNode)shape; 
			circle.setCenterX(values[0] * Data.ScaleValue);
			circle.setCenterY(values[1] * Data.ScaleValue);
			return (T)circle;
		}
		else if (shape instanceof Circle) 
		{ 
			Circle circle = (Circle)shape;
			circle.setRadius(values[2] * Data.ScaleValue); 
			circle.setCenterX(values[0] * Data.ScaleValue);
			circle.setCenterY(values[1] * Data.ScaleValue);
			return (T)circle;
		}
		else if (shape instanceof Rectangle)
		{ 
			Rectangle rectangle = (Rectangle)shape;
			rectangle.setX(values[0] * Data.ScaleValue);
			rectangle.setY(values[1] * Data.ScaleValue);
			rectangle.setWidth(values[2] * Data.ScaleValue);
			rectangle.setHeight(values[3] * Data.ScaleValue);
			return (T)rectangle;
		}
		else if (shape instanceof Line)
		{ 
			Line line = (Line)shape;
			line.setStartX(values[0] * Data.ScaleValue);
			line.setStartY(values[1] * Data.ScaleValue);
			line.setEndX(values[2] * Data.ScaleValue);
			line.setEndY(values[3] * Data.ScaleValue);
			return (T)line;
		}
		else if (shape instanceof WireLine)
		{
			WireLine wire = (WireLine)shape;
			wire.getPoints().clear();
			for (Double point : values) wire.getPoints().add(point + Data.GridOffset.X);
			return (T)wire;
		}
		else if (shape instanceof Arc)
		{
			Arc arc = (Arc)shape;
			arc.setCenterX(values[0] * Data.ScaleValue);
			arc.setCenterY(values[1] * Data.ScaleValue);
			arc.setRadiusX(values[2] * Data.ScaleValue);
			arc.setRadiusY(values[3] * Data.ScaleValue);
			arc.setStartAngle(values[4]);
			arc.setLength(values[5]);
			return (T)arc;
		}
		return null;
	}
}
