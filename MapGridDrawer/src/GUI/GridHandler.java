package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import MaterialGUI.MaterialGUIPanel;
import MenuBar.ToolBar;
import WorldChunkManager.Material;
@SuppressWarnings("unused")
public class GridHandler
{
	static double zoom = 1.0;
	static LinesManager lines = new LinesManager();
	static GridPanel gridPanel = new GridPanel();
	static WallPoint lastPos = null;
	static Gridspot lastButton = null;
	static final int pixelsPerGrid = 11;
	static JFrame frame;
	static boolean holdingShift = false;
    static final int colSize = 64;
    static final int rowSize = 64;
    public static final int PAINTMODE = 0;
    public static final int SELECTMODE = 1;
    public static int TOOLINUSE = 0;
    public static ArrayList<Material> Materials = new ArrayList<Material>();
    public static Material SELECTEDMATERIAL = new Material("Normal Wall");
    public static MaterialGUIPanel MaterialsPane = new MaterialGUIPanel();
    public static ArrayList<Line> Lines = new ArrayList<Line>();
    public static ArrayList<WallPoint> Selection = new ArrayList<WallPoint>();
    public static ArrayList<WallPoint> points = new ArrayList<WallPoint>();
    public static void main(String[] args) {
    	addNewMaterial(SELECTEDMATERIAL);
    	frame = new JFrame("Map Editor");
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	gridPanel.setLayout(new GridLayout(colSize, rowSize, 0, 0));
    	
        for (int col = 0; col < colSize; col++) 
        {
        	for (int row = 0; row < rowSize; row++)
            {
        		//gridPanel.add(new Gridspot(row, col), JLayeredPane.DEFAULT_LAYER);
            }
        }
        gridPanel.setSize(pixelsPerGrid*rowSize, pixelsPerGrid*colSize);
        //gridPanel.setMaximumSize(new Dimension(pixelsPerGrid*rowSize, pixelsPerGrid*colSize));
        gridPanel.setPreferredSize(new Dimension(pixelsPerGrid*rowSize, pixelsPerGrid*colSize));
        
        lines.setSize(pixelsPerGrid*rowSize, pixelsPerGrid*colSize);
        lines.setBounds(0,0,pixelsPerGrid*rowSize, pixelsPerGrid*colSize);
        //lines.setMaximumSize(new Dimension(pixelsPerGrid*rowSize, pixelsPerGrid*colSize));
        lines.setPreferredSize(new Dimension(pixelsPerGrid*rowSize, pixelsPerGrid*colSize));
        
        //lines.addLine(0,0,100,100);
        
        
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0,0,pixelsPerGrid*rowSize, pixelsPerGrid*colSize);
        layeredPane.add(gridPanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(lines, JLayeredPane.PALETTE_LAYER);
        
        ZoomPanel zoomPanel = new ZoomPanel();
        zoomPanel.setLayout( null);
        zoomPanel.add(layeredPane);
        
        
        JPanel allParts = new JPanel();
        allParts.setLayout(new BorderLayout());
        allParts.add(zoomPanel, BorderLayout.CENTER);
        
        JPanel Options = new JPanel();
        Options.setPreferredSize(new Dimension(250, 500));
        Options.setBackground(Color.GRAY);
        allParts.add(Options, BorderLayout.EAST);
        
        
        
		
		
		Options.add(new ToolBar());
		
		
		
		
		Options.add(MaterialsPane);
		
        
        allParts.setPreferredSize(new Dimension(pixelsPerGrid*colSize+ Options.getWidth(),zoomPanel.getHeight()));
        frame.setLayout(new GridBagLayout());
        
        frame.setContentPane(allParts);
        frame.pack();
        frame.setSize((int)(pixelsPerGrid*(rowSize+1.5)+Options.getWidth()), (int)(pixelsPerGrid*(colSize+.5)+frame.getInsets().top+3));
        frame.setVisible(true);
        
        
        
        
        
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(
            new KeyEventDispatcher() {
                public boolean dispatchKeyEvent(KeyEvent e) {
                holdingShift = e.isShiftDown();
                return false;
                }
            });
    }
    public static void addNewMaterial(String Name)
    {
    	Material Mat = new Material(SELECTEDMATERIAL);
    	Mat.setName(Name);
    	Materials.add(Mat);
    	MaterialsPane.addMaterial(Mat);
    	setSelectedMaterial(Mat);
    }
    public static void addNewMaterial(Material Material)
    {
    	SELECTEDMATERIAL = Material;
    	Materials.add(Material);
    	MaterialsPane.addMaterial(Material);
    }
	public static void gridPressed(Point clickPos) 
	{
		WallPoint gridPos = null;
		if(clickPos!=null)
		{
			gridPos = new WallPoint(clickPos, SELECTEDMATERIAL);
			for(int i = 0; i<points.size(); i++)
			{
				if(points.get(i)!=null && points.get(i).sameCoords(clickPos))
				{
					System.out.println("Point already exists at: " + clickPos);
					gridPos = points.get(i);
					
				}
			}
			
		}
		Selection.clear();
		Selection.add(gridPos);
		
		if(lastPos==gridPos)
		{
			//do nothing
		}
		else if(lastPos==null)
		{
			lastPos = gridPos;
			points.add(gridPos);
			
		}
		else
		{
			if(clickPos!=null)
			{
				lines.addLine(lastPos, gridPos);
			}
			
			
			lastPos = gridPos;
		}
		frame.repaint();
	}
	
	public static void setSelectedMaterial(Material M) 
	{
		SELECTEDMATERIAL = M;
		setSelectionMaterial();
		MaterialsPane.changeSelection(M);
	}
	public static void selectPoint(Point intersection) 
	{
		System.out.println("Looking for Points");
		if(!holdingShift)
		{
			System.out.println("Clearing Selection");
			Selection.removeAll(Selection);
		}
		for(int i = 0; i<points.size(); i++)
		{
			if(points.get(i).sameCoords(intersection))
			{
				
				System.out.println("Adding Point: " + intersection);
				//points.get(i).getAngle();
				Selection.add(points.get(i));
				if(!holdingShift)
				{
					break;
				}
			}
		}
		getMaterialFromSelection();
		frame.repaint();
	}
	public static void getMaterialFromSelection()
	{
		if(Selection == null || Selection.size()==0) return;
		Material currentMat = Selection.get(0).getMaterial();
		for(int i = 0; i<Selection.size(); i++)
		{
			if(currentMat!=Selection.get(i).getMaterial()) 
			{
				currentMat = null;
				break;
			}
		}
		System.out.println("Setting selected material to: " + currentMat);
		setSelectedMaterial(currentMat);
	}
	public static void setSelectionMaterial()
	{
		for(int i = 0; i<Selection.size(); i++)
		{
			if(Selection.get(i)!=null)
			{
				Selection.get(i).setMaterial(SELECTEDMATERIAL);
			}
			
		}
	}
	public static void redrawFrame() 
	{
		frame.repaint();
	}
}
