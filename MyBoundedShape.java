import java.awt.Color;

/**
 * This abstract class implements the state and behaviour of all bounded shapes.
 * it extends from MyShape
 * @author Lior Sabri, Ben Biton
 */
public abstract class MyBoundedShape extends MyShape
{
	private boolean isFilled; // If the shape is filled or not
	   
	/**
     * MyBoundedShape default constructor, calls default super constructor and 
     * initializes variables with default values
     */
	public MyBoundedShape()
	{
	} 
	
	/**
	 * MyBoundedShape argument constructor, calls super argument constructor and
	 * initializes a new MyRectangle with recieved values.
	 * will recieve the value 0.
	 * @param x1 first x coordinate
	 * @param y1 first y coordinate
	 * @param x2 second x coordinate
	 * @param y2 second y coordinate
	 * @param myColor chosen color to draw the shape
	 * @param isFilled If the shape is filled or not
	 */
	public MyBoundedShape(int x1, int y1, int x2, int y2, Color myColor,
		   					 boolean isFilled)
	{
		   super(x1, y1, x2, y2, myColor);
		   this.isFilled = isFilled;
	} 
	   
	/**
	 * Sets whether this shape is filled or not.
	 * @param isFilled If the shape is filled or not
	 */
	public void setFilled(boolean isFilled)
	{
	   this.isFilled = isFilled;
	}

	/**
	 * Determines whether this shape is filled.
	 * @return If the shape is filled or not
	 */
	public boolean getIsFilled()
	{
		return isFilled;
	} 
	
	/**
	 * Get upper left x coordinate
	 * @return math operation for upper left x coordinate
	 */
	public int getUpperLeftX()
	{
	   return Math.min(getX1(), getX2());
	}
	
	/**
	 * Get upper left y coordinate
	 * @return math operation for upper left y coordinate
	 */
	public int getUpperLeftY()
	{
	   return Math.min(getY1(), getY2());
	} 
	
	/**
	 * Get shape width
	 * @return math operation for width
	 */
	public int getWidth()
	{
	   return Math.abs(getX2() - getX1());
	}
	
	/**
	 * Get shape height
	 * @return math operation for height
	 */
	public int getHeight()
	{
	   return Math.abs(getY2() - getY1());
	} 
}