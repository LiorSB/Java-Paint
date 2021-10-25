// MyShape.java
import java.awt.Graphics;
import java.awt.Color;
import java.io.*;

/**
 * This abstract class implements Serializable so the it's variables can be
 * serialized or deserialized, and implements the state and behaviour of all shapes.
 * @author Lior Sabri, Ben Biton
 */
public abstract class MyShape implements Serializable
{
   private int x1; // x coordinate of first point
   private int y1; // y coordinate of first point
   private int x2; // x coordinate of second point
   private int y2; // y coordinate of second point
   private Color myColor; // Color of the shape

   /**
	* MyShape default constructor, initializes a new MyShape with default values
	* by calling the argument cconstructor.
	*/
   public MyShape()
   {
   	  this(0, 0, 0, 0, Color.BLACK);
   }
   
   /**
	* MyShape argument constructor, initializes a new MyShape with recieved values
	* values sent to this function will be checked if they are legal if not they
	* will recieve the value 0.
	* @param x1 first x coordinate
	* @param y1 first y coordinate
	* @param x2 second x coordinate
	* @param y2 second y coordinate
	* @param myColor chosen color to draw the shape
	*/
   public MyShape(int x1, int y1, int x2, int y2, Color myColor)
   {
   	  this.x1 = (x1 >= 0 ? x1 : 0);
      this.y1 = (y1 >= 0 ? y1 : 0);
      this.x2 = (x2 >= 0 ? x2 : 0);
      this.y2 = (y2 >= 0 ? y2 : 0);
      this.myColor = myColor; 
   }
   
   /**
	* Set the x-coordinate of the first point and check if it's a legal value
	* incase it isn't set the value with 0.
	* @param x1 first x coordinate
	*/
   public void setX1(int x1)
   {
      this.x1 = (x1 >= 0 ? x1 : 0);
   } 
   
   /**
	* Set the y-coordinate of the first point and check if it's a legal value
	* incase it isn't set the value with 0.
	* @param y1 first y coordinate
	*/
   public void setY1(int y1)
   {
      this.y1 = (y1 >= 0 ? y1 : 0);
   } 
   
   /**
	* Set the x-coordinate of the second point and check if it's a legal value
	* incase it isn't set the value with 0.
	* @param x2 first x coordinate
	*/
   public void setX2(int x2)
   {
      this.x2 = (x2 >= 0 ? x2 : 0);
   } 
   
   /**
	* Set the y-coordinate of the second point and check if it's a legal value
	* incase it isn't set the value with 0.
	* @param y2 first y coordinate
	*/
   public void setY2(int y2)
   {
      this.y2 = (y2 >= 0 ? y2 : 0);
   } 
   
   /**
	* Set the color
	* @param myColor new color
	*/
   public void setColor(Color myColor)
   {
       this.myColor = myColor;
   } 
   
   /**
	* Get the x-coordinate of the first point.
	* @return the value of the first x-coordinate.
	*/
   public int getX1()
   {
      return x1;
   } 

   /**
	* Get the y-coordinate of the first point.
	* @return the value of the first y-coordinate.
	*/
   public int getY1()
   {
      return y1;
   }
   
   /**
	* Get the x-coordinate of the second point.
	* @return the value of the second x-coordinate.
	*/
   public int getX2()
   {
      return x2;
   } 
   
   /**
	* Get the y-coordinate of the second point.
	* @return the value of the second y-coordinate.
	*/
   public int getY2()
   {
      return y2;
   }

   /**
	* Get the color.
	* @return the color.
	*/
   public Color getColor()
   {
      return myColor;
   }
   
   /**
    * This abstract method is made for polymorphic reason each time a draw method
    * will be called it will draw the matching shape.
    * @param g the graphic panel
    */
   public abstract void draw(Graphics g);
} // end class MyShape