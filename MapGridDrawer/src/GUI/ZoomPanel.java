package GUI;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

class ZoomPanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double zoom = 1.0;
    int panX = 0;
    int panY = 0;
	
    public ZoomPanel()
    {
    	setOpaque(false);
    	addMouseWheelListener(e -> 
        {
            double factor = e.getPreciseWheelRotation() < 0 ? 1.1 : 0.9;
            setZoom(getZoom() * factor);
        });
        
    }
    public void setPan(int deltax, int deltay)
    {
    	int dx = deltax;
        int dy = deltay;
        
        panX += dx;
        panY += dy;
        //System.out.println(panX);
        //System.out.println(panY);
        setLocation(panX, panY);
        repaint();
    }
    private void setZoom(double zoom)
    {
        this.zoom = Math.max(0.25, Math.min(zoom, 8.0));
        updateGrids();
        revalidate();
        repaint();
    }
    private void updateGrids()
    {
    	for(Component layPane : this.getComponents())
        {
        	if(layPane instanceof JLayeredPane)
        	{
        		for(Component gridPanel : ((JLayeredPane)layPane).getComponents())
                {
                	if(gridPanel instanceof GridPanel)
                	{
                		((GridPanel) gridPanel).setZoom(zoom);
                		revalidate();
                		//((GridPanel) gridPanel).setPan(panX, panY);
                		//setPreferredSize(((GridPanel) gridPanel).getPreferredSize());
                	}
                }
        		
        	}
        	
        }
    }
    public int getXPan()
    {
    	return panX;
    }
    public int getYPan()
    {
    	return panY;
    }
    public double getZoom() 
    {
        return zoom;
    }
    @Override
    protected void paintComponent(Graphics g) 
    {
        Graphics2D g2 = (Graphics2D) g;
        //g2.translate(-panX, -panY);
        g2.scale(zoom, zoom);
        super.paintComponent(g2);
    }
    
    @Override
    public Dimension getPreferredSize() 
    {
        Dimension d = super.getPreferredSize();
        return new Dimension(
            (int)((d.width+panX) * zoom),
            (int)((d.height+panY) * zoom)
        );
    }
}
