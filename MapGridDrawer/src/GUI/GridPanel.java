package GUI;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

class GridPanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static final int COLS = 64;
    static final int ROWS = 64;
    
    double zoom = 1.0;
    int baseTileSize = GridHandler.pixelsPerGrid;
	private int panX;
	private int panY;
	Point lastMouse = null;
    public GridPanel()
    {
    	addMouseListener(new MouseAdapter() 
    	{

            @Override
            public void mousePressed(MouseEvent e) 
            {
            	if (SwingUtilities.isLeftMouseButton(e)) 
                {
            		System.out.println("aaa");
            		if(GridHandler.TOOLINUSE==GridHandler.PAINTMODE)
            		{
            			Point intersection = getIntersection(e.getPoint());
            	        GridHandler.gridPressed(intersection);
            		}
            		else if(GridHandler.TOOLINUSE==GridHandler.SELECTMODE)
            		{
            			Point intersection = getIntersection(e.getPoint());
            	        GridHandler.selectPoint(intersection);
            		}
                }
            	if (SwingUtilities.isRightMouseButton(e)) 
                {
        	        GridHandler.gridPressed(null);
                }
            	if (SwingUtilities.isMiddleMouseButton(e)) 
                {
                    lastMouse = e.getPoint();
                    setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
                }
            }
            public void mouseReleased(MouseEvent e) 
            {
                lastMouse = null;
                setCursor(Cursor.getDefaultCursor());
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() 
        {
            @Override 
            public void mouseDragged(MouseEvent e) 
            {
                if (lastMouse == null) return;
                
                int x = e.getX() - lastMouse.x;
                int y = e.getY() - lastMouse.y;
                ((ZoomPanel)getParent().getParent()).setPan(x, y);
                lastMouse = e.getPoint();
            }
        });
        
    }
    
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLS; x++) {
                int px = (int)((x) * baseTileSize);
                int py = (int)((y) * baseTileSize);
                
                g2.setColor(Color.DARK_GRAY);
                g2.drawLine(px, (int)(py+baseTileSize*.5), px+baseTileSize, (int)(py+baseTileSize*.5));
                g2.drawLine((int)(px+baseTileSize*.5), py, (int)(px+baseTileSize*.5), py+baseTileSize);
            }
        }
    }
    public void setZoom(double zoom)
    {
    	this.zoom = zoom;
    	setPreferredSize();
    }
    public void setPreferredSize() 
    {
    	//return null;
    	Dimension size = new Dimension((int)(baseTileSize * COLS * zoom), (int)(baseTileSize * ROWS * zoom));
    	System.out.println(size);
    	setSize(size);
    	setPreferredSize(size);
    	revalidate();
        //return size;
    }
    public Dimension getPreferredSize() 
    {
    	//return null;
    	Dimension size = new Dimension((int)(baseTileSize * COLS * zoom), (int)(baseTileSize * ROWS * zoom));
        return size;
    }
    public Point getIntersection(Point mouse) 
    {
        double size = baseTileSize * zoom;
        //System.out.println(size);
        int gx = (int) Math.ceil((mouse.x + panX) / size);
        int gy = (int) Math.ceil((mouse.y + panY) / size);

        gx = Math.max(1, Math.min(gx, COLS))-1;
        gy = Math.max(1, Math.min(gy, ROWS))-1;

        return new Point(gx, gy);
    }
	public void setPan(int panX, int panY) 
	{
		this.panX = panX;
		this.panY = panY;
	}
}