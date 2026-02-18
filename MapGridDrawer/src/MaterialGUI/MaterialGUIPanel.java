package MaterialGUI;

import java.awt.GridLayout;

import javax.swing.JPanel;

import GUI.GridHandler;
import WorldChunkManager.Material;

@SuppressWarnings("serial")
public class MaterialGUIPanel extends JPanel
{
	MaterialSelectorPanel MaterialSelector = new MaterialSelectorPanel();
	MaterialPanel SelectedMaterial = new MaterialPanel(GridHandler.SELECTEDMATERIAL);
	MaterialList Materials = MaterialSelector.getMaterialListPanel();
	public MaterialGUIPanel()
	{
		setLayout(new GridLayout(2,1));
		add(MaterialSelector);
		add(SelectedMaterial);
	}

	public void addMaterial(Material m) 
	{
		SelectedMaterial.replaceMaterial(m);
		Materials.addMaterial(m);
	}
	public Material getSelectedMaterial()
	{
		return SelectedMaterial.getSelectedMaterial();
				
	}

	public void changeSelection(Material m) 
	{
		SelectedMaterial.replaceMaterial(m);
		Materials.setSelectedItem(m);
	}
}
