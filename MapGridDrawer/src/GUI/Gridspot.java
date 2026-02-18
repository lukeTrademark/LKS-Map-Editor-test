package GUI;

import javax.swing.ImageIcon;
import javax.swing.JToggleButton;

public class Gridspot extends JToggleButton
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int row = -1;
	int col = -1;
	int gridSize = GridHandler.pixelsPerGrid;
	@SuppressWarnings("unused")
	public Gridspot(int row, int col)
	{
		super(new ImageIcon("Grid1.png"));
		ImageIcon normalGrid = new ImageIcon("Grid1.png");
		ImageIcon pressedGrid = new ImageIcon("GridPressed1.png");
		
		this.row = row;
		this.col = col;
		super.setBorderPainted(false);
		super.setContentAreaFilled(false);
		super.setFocusPainted(false);
		super.setPressedIcon(pressedGrid);
		super.setSelectedIcon(pressedGrid);
		
		super.addActionListener(e -> 
		{
		    //GridHandler.gridPressed(getGridPos(), this);
		});
	}
	@SuppressWarnings("unused")
	private int[] getGridPos()
	{
		int x = (int)((row+.5) * gridSize);
		int y = (int)((col+.5) * gridSize);
		return new int[] {x,y};
	}
	
	
}


