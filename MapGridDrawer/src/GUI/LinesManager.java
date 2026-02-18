package GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JComponent;

public class LinesManager extends JComponent
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<Line> lines = GridHandler.Lines;
	ArrayList<WallPoint> points = GridHandler.points;
	public LinesManager()
	{
		lines = GridHandler.Lines;
		points = GridHandler.points;
		super.setOpaque(false);
	}
	public void addLine(WallPoint pos1, WallPoint pos2) 
	{
	    addLine(pos1, pos2, Color.red);
	}
	public void addLine(WallPoint pos1, WallPoint pos2, Color color) 
	{
		lines = GridHandler.Lines;
		points = GridHandler.points;
	    lines.add(new Line(pos1, pos2, color));   
	    points.add(pos2);
	    repaint();
	}

 	public void clearLines() 
	{
	    lines.clear();
	    repaint();
	}
	public void paintComponent(Graphics g) 
	{
	    super.paintComponent(g);
	    if(lines==null)return;
	    for (int i = 0; i<lines.size(); i++) 
	    {
	    	Line line = lines.get(i);
	    	Color c = null;
	    	try
	    	{
	    		c = line.Pos1.getMaterial().getColor();
	    	}
	    	catch (NullPointerException e)
	    	{
	    		
	    	}
	    	if(c==null) c = Color.red;
	        g.setColor(c);
	        Point pos1 = getGridPos(line.getStartPoint());
	        Point pos2 = getGridPos(line.getEndPoint());
	        g.drawLine(pos1.x, pos1.y, pos2.x, pos2.y);
	    }
	    points = GridHandler.points;
	    //make the interior angles
	    for (int i = 0; i<points.size(); i++) 
	    {
	    	Point point = getGridPos(points.get(i));
	    	if(point != null)
	    	{
	    		g.setColor(Color.cyan);
		        double length = 5;
		        double theta = points.get(i).getAngle()+Math.PI;
		        if(theta!=-999.0)
		        {
		        	g.drawLine((int)(point.x-length*Math.cos(theta)), (int)(point.y+length*Math.sin(theta)), point.x, point.y);
		        }
		        
	    	}
	    }
	    
	    
	    for (int i = 0; i<points.size(); i++) 
	    {
	    	Point point = getGridPos(points.get(i));
	    	if(point != null)
	    	{
	    		g.setColor(points.get(i).getMaterial().getColor());
		        double radius = 2.5;
		        g.fillOval((int)(point.x-radius), (int)(point.y-radius), (int)(radius*2), (int)(radius*2));
	    	}
	    }
	    ArrayList<WallPoint> Selection = GridHandler.Selection;
	    for (int i = 0; i<Selection.size(); i++) 
	    {
	    	Point point = getGridPos(Selection.get(i));
	    	if(point != null)
	    	{
	    		g.setColor(Color.black);
		        double radius = 3.5;
		        g.drawOval((int)(point.x-radius), (int)(point.y-radius), (int)(radius*2), (int)(radius*2));
	    	}
	    }
	}
	private Point getGridPos(WallPoint pos)
	{
		if(pos == null) return null;
		int x = (int)((pos.x+.5) * GridHandler.pixelsPerGrid);
		int y = (int)((pos.y+.5) * GridHandler.pixelsPerGrid);
		return new Point(x,y);
	}
}
