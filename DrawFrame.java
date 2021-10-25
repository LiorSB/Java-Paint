import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 * The class extends from JFrame {@link javax.swing.JFrame} and implements
 * ItemListener {@link java.awt.event}, ActionListener {@link java.awt.event},
 * ChangeListener {@link javax.swing.event}.
 * <br> This Class implements the DrawFrame main window (GUI arrangement).
 * <br> It creates 4 different panels in the fame.
 * <br> The panels created are DrawingPanel {@link DrawPanel} to hold all drawings
 * made, selectionPanel which holds all JButtons, JComboBox, JCheckBox and
 * lastly a sliderPanel which holds another panel that shows the current used
 * color and all JSlider with matching JLabels for them.
 * @author Lior Sabri, Ben Biton
 */
public class DrawFrame extends JFrame
					   implements ItemListener, ActionListener, ChangeListener
{ 
	private JButton[] buttons; // Holds all three buttons need: Undo, Redo, Clear.
	
	private JComboBox<String> colorsBox; // Combobox which shows available colors.
	private JComboBox<String> shapesBox; // Combobox which show available shapes.
	
	private JCheckBox filledBox; // When checked or unchecked the shape will be filled accordingly.
	private JCheckBox unlimitDrawingsBox; // This gives the option to unlimit the amount of shapes to be drawn.
	
	private JSlider[] rgbSlider; // Shows red, green, blue sliders to control the color.
	
	private JPanel showCurrentColorPanel; // Displays the current color being used.
	
	private DrawPanel graphicsPanel; // Holds all drawings made by the user.
	
	private JLabel statusLabel; // A label to show mouse coordinates and how many shapes were drawn.
	private JLabel[] rgbLabel; // Red, green, blue labels for each rgb slider.
	
	// Array of Strings for the rgbLabels.
	private static final String[] rgbString = {"Red", "Green", "Blue"};
	
	// Color arrray to hold colors for the colorBox.
	private static final Color[] allColors = {Color.BLACK, Color.BLUE, Color.CYAN,
		Color.DARK_GRAY, Color.GRAY, Color.GREEN, Color.LIGHT_GRAY, Color.MAGENTA,
		Color.ORANGE, Color.PINK, Color.RED, Color.WHITE, Color.YELLOW};
	
	// Array of Colors for the rgbLabel.
	private static final Color[] rgbColor = {allColors[10], allColors[5],
	allColors[1]};
	
	/**
	 * DrawFrame Constructor - creates all panels and file bar and adds them all
	 * to the JFrame.
	 * <br> see all methods used: {@link #createFileMenu}, {@link #createSelectionPanel},
	 * {@link #createSliderPanel}, {@link #createGraphicsPanel}.
	 */
	public DrawFrame()
	{
		// Create JLabel for mouse coordinates.
		statusLabel = new JLabel("Mouse outside panel, Shapes Drawn: 0");
		add(statusLabel, BorderLayout.SOUTH);
		
		// Create set and add all GUI components 
		createFileMenu();
		createSelectionPanel();
		createSliderPanel();
		createGraphicsPanel();
	}
	
	/**
	 * The method initializes all JButtons, JComboBox and JCheckBox needed,
	 * and add thems to the selectionPanel in a GridLayout which will finally
	 * add the JPanel to the JFrame.
	 * <br> See the class GridLayout: {@link java.awt.GridLayout}.
	 */
	private void createSelectionPanel()
	{
		// Creating names for the buttons and combobox.
		final String[] buttonsNames = {"Undo", "Redo", "Clear"};
		final String[] allColorsString = {"Black", "Blue", "Cyan", 
		"Dark Gray", "Gray", "Green", "Light Gray", "Magenta", "Orange", "Pink",
		"Red", "White", "Yellow", "Make Color"};
		final String[] shapesToDraw = {"Line", "Oval", "Rectangle", "Pen"};
		
		JPanel selectionPanel = new JPanel();
		// Setting a GridLayout to stretch all components for a better look.
		selectionPanel.setLayout(new GridLayout(1, 7));
		
		buttons = new JButton[buttonsNames.length]; 
		
		filledBox = new JCheckBox("Filled");
		unlimitDrawingsBox = new JCheckBox("Unlimited Drawing");
		
		colorsBox = new JComboBox<>(allColorsString);
		shapesBox = new JComboBox<>(shapesToDraw);
		
		colorsBox.setMaximumRowCount(8);
		shapesBox.setMaximumRowCount(4);
		
		// This loop initializes each button and adds them to the selectionPanel
		for (int i = 0; i < buttons.length; i++)
		{
			buttons[i] = new JButton(buttonsNames[i]);
			buttons[i].addActionListener(this); 
			selectionPanel.add(buttons[i]);	
		}
		
		buttons[0].setMnemonic('U');
		buttons[1].setMnemonic('R');
		buttons[2].setMnemonic('C');
		
		colorsBox.addItemListener(this);
		shapesBox.addItemListener(this);
		filledBox.addItemListener(this);
		unlimitDrawingsBox.addItemListener(this);
		
		selectionPanel.add(colorsBox);
		selectionPanel.add(shapesBox);
		selectionPanel.add(filledBox);
		selectionPanel.add(unlimitDrawingsBox);
		
		add(selectionPanel, BorderLayout.NORTH);
	}
	
	/**
	 * The method sets the background color for the showCurrentColorPanel and
	 * calls the setDrawingColor method in the DrawingPanel class.
	 * <br> See setDrawingColor method: {@link DrawPanel#setDrawingColor}.
	 * @param newColor recieved from combobox/sliders.
	 */
	private void passColor(Color newColor)
	{
		showCurrentColorPanel.setBackground(newColor);
		graphicsPanel.setDrawingColor(newColor);
	}
	
	/**
	 * The method initializes all JSlider's, JLabel's and the JPanel needed,
	 * and add thems to the sliderPanel in a GridLayout which will finally
	 * add the JPanel to the JFrame.
	 * <br> It will also set the text and color for the rgbLabels.
	 * <br> See the class GridLayout: {@link java.awt.GridLayout}.
	 * <br> See the setForeground method to set rgbLabels color: 
	 * {@link javax.swing.JComponent#setForeground}.
	 * <br> See other methods used: {@link #initializeSlider}, {@link #setColorLabelText},
	 * {@link #setAllSlidersEnabled}.
	 */
	private void createSliderPanel()
	{
		JPanel sliderPanel = new JPanel();
		// Setting a GridLayout to stretch all components for a better look.
		sliderPanel.setLayout(new GridLayout(8, 1));
		
		JLabel showColorLabel = new JLabel("Current Color:", SwingConstants.CENTER);
		showCurrentColorPanel = new JPanel();
		
		showCurrentColorPanel.setBackground(Color.BLACK); 
		
		sliderPanel.add(showColorLabel);
		sliderPanel.add(showCurrentColorPanel);
		
		rgbSlider = new JSlider[3];
		rgbLabel = new JLabel[3];
		
		// Initialize sliders and labels set color for labels and set the visibility
		// of the sliders to false.
		initializeSlider();
		setColorLabelText();
		setAllSlidersEnabled(false);
		
		// Sets the forground for each label and adds them to the sliderPanel 
		for (int i = 0; i < rgbSlider.length; i++)
		{
			rgbLabel[i].setForeground(rgbColor[i]);
			sliderPanel.add(rgbLabel[i]);
			sliderPanel.add(rgbSlider[i]);
		}
		
		add(sliderPanel, BorderLayout.EAST);
	}
	
	/**
	 * Initialize the sliders and labels and add a change listener to the sliders
	 */
	private void initializeSlider()
	{
		for (int i = 0; i < rgbSlider.length; i++)
		{
			rgbSlider[i] = new JSlider(0, 255);
			rgbLabel[i] = new JLabel("");
			rgbSlider[i].addChangeListener(this);
		}
	}
	
	/**
	 * This method sets visibility of all sliders.
	 * <br> See the addChangeListener method: {@link java.awt.Component#setEnabled}.
	 * <br> See setDrawingColor method: {@link DrawPanel#setDrawingColor}.
	 * @param isEnabled Enable or disable the sliders.
	 */
	private void setAllSlidersEnabled(boolean isEnabled)
	{
		for (int i = 0; i < rgbSlider.length; i++)
		{
			rgbSlider[i].setEnabled(isEnabled);
		}
		
		if (isEnabled)
		{
			graphicsPanel.setDrawingColor(new Color (rgbSlider[0].getValue(),
				rgbSlider[1].getValue(), rgbSlider[2].getValue()));
		}
	}
	
	/**
	 * This method sets the text for the rgbLabels.
	 * <br> See the setText method: {@link javax.swing.JLabel#setText}.
	 */
	private void setColorLabelText()
	{
		for (int i = 0; i < rgbLabel.length; i++)
		{
			rgbLabel[i].setText(String.format("%s %d", rgbString[i], 
				rgbSlider[i].getValue()));
		}
	}
	
	/**
	 * The method initializes the JMenu, JMenu bar and two JMenuItem's.
	 * The menu items are save and load which will be used to save and load the
	 * drawing. There is a mnemonic to each of them.
	 * <br> See the setMnemonic method: {@link javax.swing.AbstractButton#setMnemonic}.
	 */
	private void createFileMenu()
	{
		JMenuBar fileBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		
		JMenuItem saveItem = new JMenuItem("Save");
		JMenuItem loadItem = new JMenuItem("Load");
		
		// Setting Mnemonics which will let us be able to access these items with
		// alt + the fitting key.
		fileMenu.setMnemonic('F');
		saveItem.setMnemonic('S');
		loadItem.setMnemonic('L');
		
		fileMenu.add(saveItem);
		fileMenu.add(loadItem);
		add(fileMenu);
		fileBar.add(fileMenu);
		
		setJMenuBar(fileBar);
		
		saveItem.addActionListener(new ActionListener() 
        {  
        	/**
        	 * Invokes the saveItem JMenuItem when pressed and calls a fitting
        	 * method.
        	 * <br> See saveDrawing method: {@link DrawPanel#saveDrawing}.
        	 * @param event Holds the current event.
        	 */
        	@Override
            public void actionPerformed(ActionEvent event)
            {
               graphicsPanel.saveDrawing();
            } 
        }); 
        
        loadItem.addActionListener(new ActionListener() 
        {  
        	/**
        	 * Invokes the loadItem JMenuItem when pressed and calls a fitting
        	 * method.
        	 * <br> See loadDrawing method: {@link DrawPanel#loadDrawing}.
        	 * @param event Holds the current event.
        	 */
        	@Override
            public void actionPerformed(ActionEvent event)
            {
               graphicsPanel.loadDrawing();
            } 
        });
	}
	
	/**
	 * This methods calls the DrawPanel constructor to enable drawing on the
	 * graphicsPanel and then adds it to the JFrame.
	 * <br> See DrawPanel class: {@link DrawPanel}.
	 */
	private void createGraphicsPanel()
	{
		graphicsPanel = new DrawPanel(this, statusLabel);
		add(graphicsPanel);
	}
	
	/**
	 * Simply invokes the corresponding button in the button array and calls
	 * a fitting method from the class DrawPanel.
	 * <br> It is possible to undo, redo and clear a shape with the right actions. 
	 * <br> See DrawPanel button methods: {@link DrawPanel#undoShape}, 
	 * {@link DrawPanel#redoShape}, {@link DrawPanel#clearDrawing}.
	 * @param event Holds the current event.
	 */
	@Override
	public void actionPerformed(ActionEvent event) 
	{
		Object source = event.getSource();
		
		if (source == buttons[0])
		{
			graphicsPanel.undoShape();
			return;
		}
		
		if (source == buttons[1])
		{
			graphicsPanel.redoShape();
			return;
		}
		else
		{
			graphicsPanel.clearDrawing();
		}
	}
   
	/**
	 * Simply invokes the corresponding item from the JComboBox and JCheckBox
	 * and call a fitting method from the class DrawPanel.
	 * <br> It is possible to set the color, fill, shape type and drawing limit with
	 * the right actions.
	 * @param event Holds the current event.
	 */
	@Override
	public void itemStateChanged(ItemEvent event)
	{
		Object source = event.getSource();
		
		if (source == colorsBox) 
		{
			// 13 equals the index of MakeColor in the combobox
			if (colorsBox.getSelectedIndex() != 13) 
			{
				// sends the fitting color to the index to the DrawPanel class
				passColor(allColors[colorsBox.getSelectedIndex()]);
				// disables visability to all sliders 
				setAllSlidersEnabled(false);
			}
			else
			{
				// enables visability to all sliders because MakeColor was chosen
				// in the colorBox
				setAllSlidersEnabled(true);
			}
			
			return;
		}
		
		if (source == shapesBox)
		{
			graphicsPanel.setDrawingShape(shapesBox.getSelectedIndex());
			return;
		}
		
		if (source == filledBox)
		{
			graphicsPanel.setDrawingFilled(filledBox.isSelected());
			return;
		}
		else
		{
			graphicsPanel.setUnlimitDrawings(unlimitDrawingsBox.isSelected());
		}
	}
	
	/**
	 * Simply invokes the corresponding slider, set the rgbLabels text and call
	 * the passColor method if the colorBox is set to MakeColor
	 * @param event Holds the current event.
	 */
	@Override
	public void stateChanged(ChangeEvent event)
	{
		// 13 equals the index of MakeColor in the combobox
		if(colorsBox.getSelectedIndex() == 13)
		{
			setColorLabelText();
			passColor(new Color (rgbSlider[0].getValue(), rgbSlider[1].getValue(),
				rgbSlider[2].getValue()));
		}
	}
}