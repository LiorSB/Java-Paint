// DrawPanel.java
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

/**
 * DrawPanel class extends from JPanel.
 * <br> It uses classes MyLine, MyOval and MyRectangle to draw shapes.
 * <br> The class will recieve data from mouse, buttons, combobox and checkbox and
 * will draw shapes and save them with Polymorphism into an ArrayList of MyShape.
 * <br> See JPanel class: {@link javax.swing.JPanel}.
 * <br> See ArrayList class: {@link java.util.ArrayList}&lt;{@link MyShape}&gt;.
 * <br> See all shapes class: {@link MyLine}, {@link MyOval}, {@link MyRectangle}.
 * @author Lior Sabri, Ben Biton
 */
public class DrawPanel extends JPanel
{
   private ArrayList<MyShape> shapes;// ArrayList containing all the shapes through polymorphism.  
   private static final int MAX_NUMBER_OF_SHAPES = 100; // Size of the array.
   private int shapesCounter; // Counts the number of shapes on the panel also used as an index.
    
   private enum ShapeType {LINE, OVAL, RECTANGLE, PEN} // enum containing all shapes.
    
   private MyShape currentDrawingShape; // The shape we currently draw.
   private Color currentDrawingColor; // The current color of the shape.
   private boolean isDrawingFilled; // Whether the current shape is filled or not.
   private boolean isDrawingUnlimited; // Decides if we can draw more than max number of shapes.
   private ShapeType currentDrawingShapeType; // What shape we are currently drawing.
   
   private JFrame parentWindow; // JFrame sent from DrawFrame class.
   private JLabel statusLabel; // Label to show mouse coordinates and shape counter.
   
   /**
	* DrawPanel constructor, creates a panel where the shapes are drawn and
	* initializes the class variables while also creating a mouse handler.
	* <br> See MouseHandler class: {@link javax.swing.plaf.basic.BasicTreeUI.MouseHandler}.
	* @param parentWindow Holds the built JFrame.
	* @param statusLabel Holds the JLabel which we will add mouse coordinates and shapesCounter.
	*/
   public DrawPanel(JFrame parentWindow, JLabel statusLabel)
   {
   	  shapes = new ArrayList<MyShape>(MAX_NUMBER_OF_SHAPES);
   	  currentDrawingShapeType = ShapeType.LINE;
	  this.statusLabel = statusLabel;	   	  
   	  this.parentWindow = parentWindow;

      setBackground(Color.WHITE);
      
      MouseHandler mouseHandler = new MouseHandler();
      addMouseListener(mouseHandler);
      addMouseMotionListener(mouseHandler);
   } 
   
   /**
    * Creates a shape according to the currentDrawingShapeType with switch case.
    * @param x1 Holds the first x-axis. 
    * @param y1 Holds the first y-axis.
    * @param x2 Holds the second x-axis.
    * @param y2 Holds the second y-axis.
    * @return A new Shape will be sent back or null if some thing went wrong
    * although this should never happen.
    */
   private MyShape createShape(int x1, int y1, int x2, int y2) 
   {
   	   final int PEN_SIZE = 5;
   	    
   	   switch(currentDrawingShapeType)
   	   {
   	   case LINE:
   	   	   return new MyLine(x1, y1, x2, y2, currentDrawingColor);
   	   	   
   	   case OVAL:
   	   	   return new MyOval(x1, y1, x2, y2, currentDrawingColor, isDrawingFilled);
   	   	   
   	   case RECTANGLE:
   	   	   return new MyRectangle(x1, y1, x2, y2, currentDrawingColor, isDrawingFilled);
   	   	   
   	   case PEN:
   	   	   return new MyOval(x1, y1, x2 + PEN_SIZE,  y2 + PEN_SIZE, currentDrawingColor, true);
   	   	   
   	   default: // this should never happen
   	   	   return null;
   	   }
   }
   
   /**
    * This method will lower the shape counter by 1 and repaint if there is at 
    * least one shape drawn.
    * <br> See the repaint method: {@link java.awt.Component#repaint}.
    */
   public void undoShape()
   {
   	   if (shapesCounter > 0)
   	   {
   	   	   shapesCounter--;
   	   	   repaint();
   	   }
   }
   
   /**
    * This method will check if there is a shape availabe to redo if not an 
    * exception will be thrown the if is a fitting way to show we are looking
    * for a MyShapes variable which is null and getting IndexOutOfBoundsException.
    * <br> If it is possible to redo a shape the shapesCounter will enlarge by 1
    * and repaint.
    * <br> See the repaint method: {@link java.awt.Component#repaint}.
    */
   public void redoShape()
   {
   	   try
   	   {
   	   	   if (shapes.get(shapesCounter) != null)
   	   	   {
   	   	   	   shapesCounter++;
   	   	   	   repaint();
   	   	   }
   	   }
   	   catch (IndexOutOfBoundsException e)
   	   {
   	   }
   }

   /**
    * If there are shapes drawn the method will prompt a dialog window to
    * re-assure the operation if 'Yes' will be the input the ArrayList will clear
    * the shapesCounter will be set to 0 and then it will call the repaint method.
    */
   public void clearDrawing() 
   {
   	  if (shapesCounter == 0)
   	  {
   	  	  return;
   	  }
   	  
   	  int input = JOptionPane.showConfirmDialog(parentWindow,
   	  	  "Are you sure you want to erase all?", "Clear Warning",
   	  	  JOptionPane.YES_NO_OPTION);
   	  
   	  if (input != JOptionPane.YES_OPTION)
   	  {
   	  	  return;
   	  }
   	  
   	  shapes.clear();
   	  shapesCounter = 0;
   	  repaint(); 
   }
   
   /**
    * The method will set the current drawing color.
    * @param currentDrawingColor New color to set to the current drawing.
    */
   public void setDrawingColor(Color currentDrawingColor)
   {
   	   this.currentDrawingColor = currentDrawingColor;
   }
   
   /**
    * The method will set the current drawing shape type.
    * <br> Using values() we get and array of the following enum and then we save
    * the ShapeType in the matching index of the array.
    * @param shapeIndex Index which will be used to find the ShapeType.
    */
   public void setDrawingShape(int shapeIndex)
   {
   	   currentDrawingShapeType = ShapeType.values()[shapeIndex];
   }
   
   /**
    * The method will set if the current drawing is filled or not.
    * @param isDrawingFilled whether the shape is filled or not
    */
   public void setDrawingFilled(boolean isDrawingFilled)
   {
   	   this.isDrawingFilled = isDrawingFilled;
   }
   
   /**
    * The method will set if the the drawing is limited if cancelled it will 
    * check if there more shapes drawn than the MAX_NUMBER_OF_SHAPES if so
    * it will set back the shapesCounter to that limit and remove every shape
    * above that index from the ArrayList.
    * <br> See remove method: {@link java.util.ArrayList}&lt;{@link MyShape}&gt;#remove}
    * @param isDrawingUnlimited whether the ArrayList size is limited or not
    */
   public void setUnlimitDrawings(boolean isDrawingUnlimited)
   {
   	   this.isDrawingUnlimited = isDrawingUnlimited;
   	   
   	   if (!isDrawingUnlimited && shapesCounter > MAX_NUMBER_OF_SHAPES)
   	   {
   	   	   for (int i = shapesCounter - 1; i >= MAX_NUMBER_OF_SHAPES; i--)
   	   	   {
   	   	   	   shapes.remove(i);
   	   	   }
   	   	   
   	   	   shapesCounter = MAX_NUMBER_OF_SHAPES;
   	   	   repaint();
   	   }
   }
   
   /**
    * The method saves the current drawing into a .ser file if the users input 
    * for a file name is valid.
    * <br> See FileOutputStream class: {@link java.io.FileOutputStream}.
    * <br> See ObjectOutputStream class: {@link java.io.ObjectOutputStream}.
    * <br> See writeObject method: {@link java.io.ObjectOutputStream#writeObject}.
    */
   public void saveDrawing()
   {
   	   String userInput = JOptionPane.showInputDialog(parentWindow,
   	   	   "Please type in the following format to save your file:", "FileName.ser"); 
   	   
   	   if (userInput == null)
   	   {
   	   	   return;
   	   }
   	   
   	   if (!userInput.matches("\\w+\\.ser"))
   	   {
   	   	   return;
   	   }
   	   
   	   FileOutputStream fos = null;
   	   ObjectOutputStream oos = null;
   	   
   	   try
   	   {
   	   	   // Create a new file.
   	   	   fos = new FileOutputStream(userInput); 
   	   	   oos = new ObjectOutputStream(fos);
   	   	   // Write object to a file
   	   	   oos.writeObject(shapes);   
   	   	   oos.flush();
   	   }
   	   catch (FileNotFoundException e) 
   	   {
   	   	   e.printStackTrace();
   	   } 
   	   catch (IOException e)
   	   {
			e.printStackTrace();
	   } 
	   finally // Close outputstreams if they exist
	   {
	   	   try
	   	   	{
	   	   	   if (oos != null)
	   	   		{
	   	   			oos.close();
	   	  		}
	   	   
	   	   		if (fos != null)
	   	   		{
	   	   			fos.close();
	   	   		}
	   	   }
	   	   catch (IOException e)
	   	   {
			e.printStackTrace();
		   } 
	   } 
   }
   
   /**
    * The method loads the current drawing of a .ser file if the users input for
    * a file name is valid. If loaded successfuly the shapesCounter will be set
    * to The ArrayList size and then repaint.
    * <br> See FileOutputStream class: {@link java.io.FileInputStream}.
    * <br> See ObjectOutputStream class: {@link java.io.ObjectInputStream}.
    * <br> See writeObject method: {@link java.io.ObjectInputStream#readObject}.
    * <br> See size method: {@link java.util.ArrayList}&lt;{@link MyShape}&gt;#size}.
    */
   public void loadDrawing()
   {
   	   if (shapesCounter != 0)
   	   {
   	   	   int userInput = JOptionPane.showConfirmDialog(parentWindow,
   	   	   	   "You still have things drawn loading a file will erase them,"
   	   	   	   + " would you like to go on?", "Load Warning",
   	   	   	   JOptionPane.YES_NO_OPTION);
   	   	   
   	   	   if (userInput != JOptionPane.YES_OPTION)
   	   	   {
   	   	   	   return;
   	   	   }
   	   }
   	   
   	   String userInput = JOptionPane.showInputDialog(parentWindow,
   	   	   "Please type in the following format to load your file:", "FileName.ser"); 
   	   
   	   if (userInput == null || !userInput.matches("\\w+\\.ser"))
   	   {
   	   	   return;
   	   }
   	   
   	   FileInputStream fis = null; 
   	   ObjectInputStream ois = null;
   	   
   	   try 
   	   {
   	   	   // Open file.
   	   	   fis = new FileInputStream(userInput);
   	   	   ois = new ObjectInputStream(fis);
   	   	   // Read object from a file.
   	   	   // Sadly SuppressWarnings doesnt work because of a bug and it isn't possible 
   	   	   // to ignore the warning. The warning happens because we read an object
   	   	   // from a file which obviously can't be known beforehand and then try
   	   	   // to cast it to a ArrayList<MyShape>, even if we check with instanceof
   	   	   // the warning will presist, so the only option we have is to leave it as is.
   	   	   //@SuppressWarnings("unchecked")
   	   	   shapes = (ArrayList<MyShape>)ois.readObject();
   	   } 
   	   catch (FileNotFoundException e) 
   	   {
   	   	   e.printStackTrace();
   	   } 
   	   catch (IOException e)
   	   {
			e.printStackTrace();
	   } 
	   catch (ClassNotFoundException e)
	   {
	   	   e.printStackTrace();
	   }
	   finally // Close inputstreams if they exist
	   {
	   	   try
	   	   {
	   	   	   if (ois != null)
	   	   	   {
	   	   	   	   ois.close();
	   	   	   }
	   	   
	   	   	   if (fis != null)
	   	   	   {
	   	   	   	   fis.close();
	   	   	   }
	   	   }
	   	   catch (IOException e)
	   	   {
			e.printStackTrace();
		   }
	   }
   	   
   	   shapesCounter = shapes.size();
   	   repaint();
   }
   
   /**
    * This Class extends from MouseAdapter and implements MouseMotionListener.
    * <br> The class will handle the following mouse events: mouseMoved, mousePressed,
    * mouseReleased, mouseDragged and mouseExited. Each of the events will handle
    * drawing or setting the label.
    * <br> See MouseAdapter class: {@link java.awt.event.MouseAdapter}.
    * <br> See MouseMotionListener interface: {@link java.awt.event}.
    */
   private class MouseHandler extends MouseAdapter implements MouseMotionListener
   {
   	  /**
   	   * When the mouse will move the mouse coordinates shapes drawn label will
   	   * update.
   	   * <br> See mouseMoved method: {@link java.awt.event.MouseMotionListener#mouseMoved}.
   	   * @param event Holds the current event.
   	   */
   	  @Override
      public void mouseMoved(MouseEvent event)
      {
      	  statusLabel.setText(String.format(
         	 				"Mouse Coordinates: (%d, %d), Shapes Drawn: %d",
         	 				event.getX(), event.getY(), shapesCounter));
      }
 
      /**
   	   * When the mouse will be pressed only with the left button a new drawing
   	   * will start according to previous settings.
   	   * <br> If max drawing limit has been reached and the unlimit check box
   	   * isn't selected than a message will prompt.
   	   * <br> See mousePressed method: {@link java.awt.event.MouseAdapter#mousePressed}.
   	   * <br> See method used to create new shape: {@link #createShape}.
   	   * @param event Holds the current event.
   	   */
      @Override
      public void mousePressed(MouseEvent event)
   	  {
   	   	    if (event.getButton() != MouseEvent.BUTTON1)
   	   	    {
   	   	    	return;
   	   	    }
   	   	   	
   	   	    int x = event.getX();
   	   	    int y = event.getY();
   	   	    
   	   	    // If the Pen will be chosen in the combobox.
   	   	    if (currentDrawingShapeType == ShapeType.PEN &&
   	   	   		 (shapesCounter < MAX_NUMBER_OF_SHAPES || isDrawingUnlimited))
   	   	   	{
   	   	   	   shapes.add(shapesCounter, createShape(x, y, x, y));
   	   	   	   shapesCounter++;
   	   	   	   repaint();
   	   	   	   return;
   	   	   	}
			
   	   	   	// If any other shape than Pen will be chosen in the combobox.
   	   	   	if (shapesCounter < MAX_NUMBER_OF_SHAPES || isDrawingUnlimited)
   	   	   	{
   	   	   		currentDrawingShape = createShape(x, y, x, y);
   	   	   	}
   	   	   	else // If the Shapes drawn have reached the limit
   	   	   	{
   	   	   		JOptionPane.showMessageDialog(parentWindow, String.format(
   	   	   						"Can't exceed the current number of shapes: %d",
   	   	   						MAX_NUMBER_OF_SHAPES), "Capacity Overload", 
   	   	   						JOptionPane.WARNING_MESSAGE); 
   	   	    }
   	   }
   	   
   	  /**
   	   * When the mouse will be released only with the left button the new drawing
   	   * will set.
   	   * <br> If the drawn shape will be invisible it will be released to the
   	   * garbage collecter.
   	   * <br> See mouseReleased method: {@link java.awt.event.MouseAdapter#mouseReleased}.
   	   * @param event Holds the current event.
   	   */
   	   @Override
   	   public void mouseReleased(MouseEvent event)
   	   {
   	   		if (event.getButton() != MouseEvent.BUTTON1)
   	   		{
   	   			return;
   	   		}

   	   		if (currentDrawingShapeType == ShapeType.PEN)
   	   		{
   	   			return;
   	   		}
   	   		
   	   		currentDrawingShape.setX2(event.getX());
   	   		currentDrawingShape.setY2(event.getY());
   	   		
   	   		int x1 = currentDrawingShape.getX1();
   	   		int y1 = currentDrawingShape.getY1();
   	   		int x2 = currentDrawingShape.getX2();
   	   		int y2 = currentDrawingShape.getY2();
   	   		
   	   		boolean isDot = ((x1 == x2) && (y1 == y2));
   	   		boolean isInvisibleLine = ((x1 == x2) || (y1 == y2)) &&
   	   			isDrawingFilled && (currentDrawingShapeType != ShapeType.LINE);
   	   						
   	   		// If the shape isn't invisible add it to the arraylist
   	   		if (!isDot && !isInvisibleLine)
   	   		{
   	   			shapes.add(shapesCounter, currentDrawingShape);
   	   			shapesCounter++;
   	   		}
   	   			
   	   		currentDrawingShape = null;
   	   }
   	   
   	  /**
   	   * When the mouse will be dragged and it's a Pen new shape will be drawn,
   	   * if it isn't a Pen the x2,y2 will always update and repaint to give a
   	   * filling as the shape follows our mouse.
   	   * <br> See mouseDragged method: {@link java.awt.event.MouseMotionListener#mouseDragged}.
   	   * @param event Holds the current event.
   	   */
   	   @Override
   	   public void mouseDragged(MouseEvent event)
   	   {
   	   	   mouseMoved(event);
   	   	   
   	   	   int x = event.getX();
   	   	   int y = event.getY();
   	   	   
   	   	   if (currentDrawingShapeType == ShapeType.PEN &&
   	   	   	  (shapesCounter < MAX_NUMBER_OF_SHAPES || isDrawingUnlimited))
   	   	   {
   	   	   	   shapes.add(shapesCounter, createShape(x, y, x, y));
   	   	   	   shapesCounter++;
   	   	   	   repaint();
   	   	   	   return;
   	   	   }
   	   	   
   	   	   if (currentDrawingShape != null)
   	   	   {
   	   	   	   currentDrawingShape.setX2(x);
   	   	   	   currentDrawingShape.setY2(y);
   	   	   	   repaint();
   	   	   }
   	   }
   	  
   	  /**
   	   * When the mouse will exit the DrawPanel it will set the status label.
   	   * <br> See mouseExited method: {@link java.awt.event.MouseAdapter#mouseExited}.
   	   * @param event Holds the current event.
   	   */
   	  @Override
      public void mouseExited(MouseEvent event)
      {
         statusLabel.setText(String.format(
         	 "Mouse outside panel, Shapes Drawn: %d", shapesCounter));
      }
   }
   
   /**
	* The method will draw every shape in the array list till the shapeCounter
	* and will also draw the current shape that's initiated by a mouse pressed 
	* event if the currentDrawingShape isn't null.
	* <br> See Graphics class: {@link java.awt.Graphics}.
	* @param g The graphic panel.
	*/
   public void paintComponent(Graphics g)
   {
      super.paintComponent(g);
      
      // Draw the arraylist of shapes.
      for (int i = 0; i < shapesCounter; i++)
      {
      	  shapes.get(i).draw(g);
      }
      
      // With this we draw the shape as it's occuring.
      if (currentDrawingShape != null)
      {
      	  currentDrawingShape.draw(g);
      }
   } 
} // end class DrawPanel