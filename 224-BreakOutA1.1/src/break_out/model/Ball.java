package break_out.model;

import break_out.Constants;

/**
 * This class contains the information about the balls characteristics and behavior
 * 
 * @author iSchumacher
 * @author modified by 224 
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
	 * Startingpoint is the center bottom of the window. Direction is 
	 * affected by BALL_SPEED through direction.rescale()
	 */
	public Ball() {
		this.position = new Position(Constants.SCREEN_WIDTH /2 -7, Constants.SCREEN_HEIGHT - Constants.BALL_DIAMETER);
		this.direction = new Vector2D(5.0,-5.0);
		direction.rescale();
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
	 * Moving the ball to its new position before it's drawn.
	 */
	public void updatePosition() {
		position.setX(position.getX() + direction.getDx());
		position.setY(position.getY() + direction.getDy());
	}
	
	/**
	 * Determining border contact and reversing vector direction
	 * accordingly
	 */
	public void reactOnBorder() {
		if (position.getX() >= 880 -Constants.BALL_DIAMETER) {
			double newDx = -1 * direction.getDx();
			direction.setDx(newDx);
		}else if (position.getY() <= 0) {
			double newDy = -1 * direction.getDy();
			direction.setDy(newDy);
		}else if (position.getX() <= 0) {
			double newDx = -1 * direction.getDx();
			direction.setDx(newDx);
		}else if (position.getY() >= 750 - Constants.BALL_DIAMETER) {
			double newDy = -1 * direction.getDy();
			direction.setDy(newDy);
		}
		
	}
	
}
