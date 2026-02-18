package MaterialGUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import GUI.GridHandler;
import WorldChunkManager.Material;

@SuppressWarnings("unused")
public class MaterialPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Material type = null;
	JTextField name = new JTextField();
	JCheckBox grassBox = new JCheckBox("Has Grass", false);
	JPanel bottomHeight = new JPanel();
	JSlider bottomHeightSlider = new JSlider(SwingConstants.HORIZONTAL, -1, 9, 0);
	JLabel bottomHeightLabel = new JLabel("Bottom Height: " + bottomHeightSlider.getValue());
	JPanel topHeight = new JPanel();
	JSlider topHeightSlider = new JSlider(SwingConstants.HORIZONTAL, 0, 10, 1);
	JLabel topHeightLabel = new JLabel("Top Height: " + topHeightSlider.getValue());
	JButton chooseColor = new JButton("Choose Color");
	boolean updatingGUI = false;
	public MaterialPanel()
	{
		//No Material Selected
		addGUI();
	}
	private void addGUI() 
	{
		name.setText("Material Name");
		if(type!=null) name.setText(type.getName());
		add(name);
		name.getDocument().addDocumentListener(new DocumentListener() 
		{

			@Override
			public void insertUpdate(DocumentEvent e) 
			{
				updateName();
			}

			@Override
			public void removeUpdate(DocumentEvent e) 
			{
				updateName();
			}

			@Override
			public void changedUpdate(DocumentEvent e) 
			{
				//updateName();
			}
			
		});
		
		setPreferredSize(new Dimension(250, 100));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		grassBox.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				type.setGrass(grassBox.isSelected());
			}
			
		});
		
		add(grassBox);
		topHeightSlider.addChangeListener(new ChangeListener() 
		{
			public void stateChanged(ChangeEvent e) 
			{
				topHeightLabel.setText("Top Height: " + topHeightSlider.getValue());
				type.setTopHeight( topHeightSlider.getValue());
			}
		});
			
		
		
		topHeight.setLayout(new GridLayout(1,2));
		topHeight.add(topHeightLabel);
		topHeight.add(topHeightSlider);
		
		add(topHeight);
		bottomHeight.setLayout(new GridLayout(1,2));
		bottomHeight.add(bottomHeightLabel);
		bottomHeight.add(bottomHeightSlider);
		bottomHeightSlider.addChangeListener(new ChangeListener() 
		{
			public void stateChanged(ChangeEvent e) 
			{
				bottomHeightLabel.setText("Bottom Height: " + bottomHeightSlider.getValue());
				type.setBottomHeight( bottomHeightSlider.getValue());
			}
		});
		add(bottomHeight);
		chooseColor.setBackground(type.getColor());
		chooseColor.addActionListener(e ->{
			Color newColor = JColorChooser.showDialog(this, "Select a color", type.getColor());
				
			if (newColor != null) 
			{
				type.setColor(newColor);
				chooseColor.setBackground(type.getColor());
				GridHandler.redrawFrame();;
			}
		});
		add(chooseColor);
	}
	private void updateName()
	{
		if(type==null) return;
		type.setName(name.getText());
		if(GridHandler.MaterialsPane==null) return;
		if(updatingGUI) return;
		GridHandler.MaterialsPane.repaint();;
	}
	public void replaceMaterial(Material type)
	{
		this.type = type;
		if(type==null) {nullMaterialReplacement(); return;}
		//System.out.println("sss"+type);	
		name.setText(""+type);
		updateGUI();
	}
	private void nullMaterialReplacement() 
	{
		name.setText("Multiple Materials, changes wont save");
	}
	public MaterialPanel(Material type)
	{
		this.type = type;
		addGUI();
		updateGUI();
		repaint();
	}
	private void updateGUI()
	{
		if(type==null) return;
		updatingGUI = true;
		updateName();
		bottomHeightSlider.setValue(type.getBottomHeight());
		topHeightSlider.setValue(type.getTopHeight());
		grassBox.setSelected(type.hasGrass());
		updatingGUI = false;
	}
	public Material getSelectedMaterial()
	{
		return type;
	}
}
