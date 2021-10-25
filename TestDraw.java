// TestDraw.java
import javax.swing.*;
import java.awt.*;
/**
 * This Class contains the main method.
 * The claass that holds the main method invokes the DrawFrame constructor to
 * start the program then sets the title of the frame and it's minimum size to
 * prevent the layout from not being fully displayed.
 * See the setMinimumSize method: {@link java.awt.Window#setMinimumSize}.
 * @author Lior Sabri, Ben Biton
 */
public class TestDraw
{
   /**
	* static main method.
	* Invokes DrawFrame constructor. and sets title, minimum size, close operation
	* and visibility.
	* @param args command line arguments
	* see class {@link DrawFrame}.
	*/
   public static void main(String[] args)
   {
   	   DrawFrame draw;
   	   draw = new DrawFrame();
   	   
   	   draw.setTitle("Shape Drawings");
   	   draw.setMinimumSize(new Dimension(1000, 600)); 
   	   draw.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   	   draw.setVisible(true);
   } // end main(String[] args)
} // end class TestDraw