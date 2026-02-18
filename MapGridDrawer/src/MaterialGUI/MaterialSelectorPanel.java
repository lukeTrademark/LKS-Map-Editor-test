package MaterialGUI;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import GUI.GridHandler;

public class MaterialSelectorPanel extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MaterialList Mats = new MaterialList(GridHandler.Materials);
	public MaterialSelectorPanel()
	{
		setLayout(new GridLayout(1,2));
		setPreferredSize(new Dimension(250,20));
		add(Mats);
		JButton newMat = new JButton("New");
		newMat.addMouseListener(new MouseAdapter() 
    	{

            @Override
            public void mousePressed(MouseEvent e) 
            {
            	if (SwingUtilities.isLeftMouseButton(e)) 
                {
            		GridHandler.addNewMaterial("New Material");
                }
            	
            }
        });
		add(newMat);
	}
	public MaterialList getMaterialListPanel() 
	{
		return Mats;
	}
	
}
