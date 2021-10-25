// MyLine.java
// Declaration of class MyLine.
import java.awt.Color;
import java.awt.Graphics;

/**
 * This class implements the state and behaviour of a line.
 * It extends from MyShape
 * @author Lior Sabri, Ben Biton
 */
public class MyLine extends MyShape
{
   /**
    * MyLine default constructor, calls default super constructor and 
    * initializes variables with default values
    */
   public MyLine()
   {
   }

   /**
	* MyLine argument constructor, calls super argument constructor and
	* initializes a new MyLine with recieved values.
	* will recieve the value 0.
	* @param x1 first x coordinate
	* @param y1 first y coordinate
	* @param x2 second x coordinate
	* @param y2 second y coordinate
	* @param myColor chosen color to draw the line
	*/
   public MyLine(int x1, int y1, int x2, int y2, Color myColor)
   {
   	   super(x1, y1, x2, y2, myColor);
   } 
   
   /** 
    * Draws a line in the specified color.
    * @param g the graphic panel
    */
   public void draw(Graphics g)
   {
      g.setColor(getColor());
      g.drawLine(getX1(), getY1(), getX2(), getY2());
   } 
} // end class MyLine