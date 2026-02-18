package MaterialGUI;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JComboBox;

import GUI.GridHandler;
import WorldChunkManager.Material;

public class MaterialList extends JComboBox<Material>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8829793805929987276L;
	boolean updatingList = false;
	ArrayList<Material> Materials = new ArrayList<Material>();
	public MaterialList(ArrayList<Material> materials)
	{
		Materials = materials;
		resetList();
		addClickDetector();
	}
	public void resetList()
	{
		updatingList = true;
		removeAllItems();
		for(int i = 0; i < Materials.size(); i++)
		{
			addItem(Materials.get(i));
		}
		updatingList = false;
	}
	public void addClickDetector()
	{
		addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent e) 
			{
				  if (!updatingList&&e.getStateChange() == ItemEvent.SELECTED) 
				  {
					  //System.out.println("aaa");
					 // System.out.println((Material) getSelectedItem());
					  GridHandler.setSelectedMaterial((Material) getSelectedItem());
			      }
			}
			
		});
	}
	public void addMaterial(Material M) 
	{
		addItem(M);
		updatingList = true;
		setSelectedItem(M);
		updatingList = false;
	}
}
