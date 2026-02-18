package MenuBar;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import GUI.GridHandler;

public class ToolBar extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ToolBar()
	{
		JToggleButton PaintToolSelect = new JToggleButton(new ImageIcon("Grid1.png"));
		ImageIcon pressedGrid = new ImageIcon("GridPressed1.png");
		PaintToolSelect.setBorderPainted(false);
		PaintToolSelect.setContentAreaFilled(false);
		PaintToolSelect.setFocusPainted(false);
		PaintToolSelect.setPressedIcon(pressedGrid);
		PaintToolSelect.setSelectedIcon(pressedGrid);
		PaintToolSelect.addActionListener(e -> 
		{
			GridHandler.TOOLINUSE = GridHandler.SELECTMODE;
		});
        add(PaintToolSelect);
		
		
		JToggleButton SelectToolSelect = new JToggleButton(new ImageIcon("Grid1.png"));
		ImageIcon pressedSelect = new ImageIcon("GridPressed1.png");
		SelectToolSelect.setBorderPainted(false);
		SelectToolSelect.setContentAreaFilled(false);
		SelectToolSelect.setFocusPainted(false);
		SelectToolSelect.setPressedIcon(pressedSelect);
		SelectToolSelect.setSelectedIcon(pressedSelect);
		SelectToolSelect.addActionListener(e -> 
		{
			GridHandler.TOOLINUSE = GridHandler.PAINTMODE;
		});
		add(SelectToolSelect);
		
		add(new ExportObjButton());
	}
}
