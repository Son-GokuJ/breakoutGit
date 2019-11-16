package break_out.model;

import break_out.Constants;

/**
 * This class contains the information about the balls characteristics and behavior
 * 
 * @author iSchumacher
 * testtest
 *
 */
public class Ball implements IBall{

	/**
	 * The balls position on the playground
	 */
	private Position position;
	
	/**
	 * The balls direction
	 */
	private Vector2D direction;
	
	/**
	 * The constructor of a ball
	 * The balls position and direction are initialized here.
	 */
	public Ball() {
		this.position = new Position(0, 0);
		this.direction = new Vector2D(0,0);
	}
	
	/**
	 * The getter for the balls position
	 * @return position The balls current position
	 */
	public Position getPosition() {
		return this.position;
	}
	
	/**
	 * The getter for the balls direction
	 * @return direction The balls current direction
	 */
	public Vector2D getDirection() {
		return this.direction;
	}
	
	/**
	 * 
	 */
	public void updatePosition() {
		
	}
	
	/**
	 * 
	 */
	public void reactOnBorder() {
		
	}
	
}
