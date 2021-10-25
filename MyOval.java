// MyOval.java
// Declaration of class MyOval.
import java.awt.Color;
import java.awt.Graphics;

/**
 * This class implements the state and behaviour of an oval.
 * It extends from MyBoundedShape
 * @author Lior Sabri, Ben Biton
 */
public class MyOval extends MyBoundedShape
{
   /**
    * MyOval default constructor, calls default super constructor and 
    * initializes variables with default values
    */
   public MyOval()
   {
   }

   /**
	* MyOval argument constructor, calls super argument constructor and
	* initializes a new MyOval with recieved values.
	* will recieve the value 0.
	* @param x1 first x coordinate
	* @param y1 first y coordinate
	* @param x2 second x coordinate
	* @param y2 second y coordinate
	* @param myColor chosen color to draw the shape
	* @param isFilled If the shape is filled or not
	*/
   public MyOval(int x1, int y1, int x2, int y2, Color myColor, boolean isFilled)
   {
     super(x1, y1, x2, y2, myColor, isFilled);
   } 
   
   /** 
    * Draws an oval in the specified color.
    * @param g the graphic panel
    */
   public void draw(Graphics g)
   {
      g.setColor(getColor());
      
      if (getIsFilled())
      {
         g.fillOval(getUpperLeftX(), getUpperLeftY(), getWidth(), getHeight());
      }
      else
      {
         g.drawOval(getUpperLeftX(), getUpperLeftY(), getWidth(), getHeight());
      }
   }
} // end class MyOval