package break_out.model;


/**
 * This class represents a position within the board in pixel coordinates
 * 
 * @author dmlux
 * 
 */
public class Position {

	/**
	 * X coordinate
	 */
	private double x;

	/**
	 * Y coordinate
	 */
	private double y;
	
	/**
	 * The constructor needs a x and y coordinate to be called
	 * 
	 * @param x The x position of the object on the board
	 * @param y The y position of the object on the board
	 */
	public Position(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Getter for the x-coordinate
	 * 
	 * @return x The x value of this position
	 */
	public double getX() {
		return x;
	}

	/**
	 * Setter for the x-coordinate
	 * @param x The new x-coordinate
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * Getter for y-coordinate
	 * 
	 * @return y The y value of the position
	 */
	public double getY() {
		return y;
	}

	/**
	 * Setter for the y-coordinate
	 * @param y The new y-coordinate
	 */
	public void setY(double y) {
		this.y = y;
	}
	
}
