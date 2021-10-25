// MyRectangle.java
// Declaration of class MyRectangle.
import java.awt.Color;
import java.awt.Graphics;

/**
 * This class implements the state and behaviour of a rectangle.
 * It extends from MyBoundedShape
 * @author Lior Sabri, Ben Biton
 */
public class MyRectangle extends MyBoundedShape
{
   /**
    * MyRectangle default constructor, calls default super constructor and 
    * initializes variables with default values
    */
   public MyRectangle()
   {
   } 

   /**
	* MyRectangle argument constructor, calls super argument constructor and
	* initializes a new MyRectangle with recieved values.
	* will recieve the value 0.
	* @param x1 first x coordinate
	* @param y1 first y coordinate
	* @param x2 second x coordinate
	* @param y2 second y coordinate
	* @param myColor chosen color to draw the shape
	* @param isFilled If the shape is filled or not
	*/
   public MyRectangle(int x1, int y1, int x2, int y2, Color myColor, boolean isFilled)
   {
   	   super(x1, y1, x2, y2, myColor, isFilled);
   }

   /** 
    * Draws a rectangle in the specified color.
    * @param g the graphic panel
    */
   public void draw(Graphics g)
   {
      g.setColor(getColor());
      
      if (getIsFilled())
      {  
      	  g.fillRect(getUpperLeftX(), getUpperLeftY(), getWidth(), getHeight());
      }
      else
      {
      	  g.drawRect(getUpperLeftX(), getUpperLeftY(), getWidth(), getHeight());
      }
   } 
} // end class MyRectangle