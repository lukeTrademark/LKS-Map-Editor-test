package GUI;

import java.awt.Color;

public class Line
{
	WallPoint Pos1;
	WallPoint Pos2;
	Color color;
	public Line(WallPoint start, WallPoint end, Color coloring)
	{
		Pos1 = start;
		Pos2 = end;
		start.addReferenceLine(this, 1);
		end.addReferenceLine(this, 0);
		color = coloring;
	}
	public WallPoint getStartPoint() 
	{
		return Pos1;
	}
	public WallPoint getEndPoint() 
	{
		return Pos2;
	}
	public static Line  cutMidPoint()
	{
		//TODO
		return null;
	}
	public int[] getL2Vector() 
	{
		return new int[] {Pos2.x-Pos1.x,(Pos2.y-Pos1.y)*-1};
	}
	public int[] getL1Vector() 
	{
		return new int[] {Pos1.x-Pos2.x,(Pos1.y-Pos2.y)*-1};
	}
}
