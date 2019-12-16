package break_out.model;

import break_out.Constants;

/**
 * This class contains the information about the balls characteristics and
 * behavior
 * 
 * @author iSchumacher
 * @author modified by 224
 *
 */
public class Ball implements IBall {

	/**
	 * The balls position on the playground
	 */
	private Position position;

	/**
	 * The balls direction
	 */
	private Vector2D direction;

	/**
	 * The constructor of a ball The balls position and direction are initialized
	 * here. Startingpoint is the center on the bottom paddle. Direction is affected
	 * by BALL_SPEED through direction.rescale()
	 */
	public Ball() {
		this.position = new Position(
				((double)Constants.SCREEN_WIDTH - (double)Constants.BALL_DIAMETER) / 2,
				(double)Constants.SCREEN_HEIGHT - (double)Constants.PADDLE_HEIGHT - 
				(double)Constants.BALL_DIAMETER);
		this.direction = new Vector2D(5.0, -5.0);
		direction.rescale();
	}

	/**
	 * The getter for the balls position
	 * 
	 * @return position The balls current position
	 */
	public Position getPosition() {
		return this.position;
	}

	/**
	 * The getter for the balls direction
	 * 
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
	 * Determining border contact and reversing vector direction accordingly
	 */
	public void reactOnBorder() {
		if (position.getX() >= Constants.SCREEN_WIDTH - Constants.BALL_DIAMETER) {
			direction.setDx(-1 * direction.getDx());
			position.setX(Constants.SCREEN_WIDTH - Constants.BALL_DIAMETER);
		} else if (position.getY() <= 0) {
			direction.setDy(-1 * direction.getDy());
			position.setY(0);
		} else if (position.getX() <= 0) {
			direction.setDx(-1 * direction.getDx());
			position.setX(0);
		} else if (position.getY() >= Constants.SCREEN_HEIGHT - Constants.BALL_DIAMETER) {
			direction.setDy(-1 * direction.getDy());
			position.setY(Constants.SCREEN_HEIGHT - Constants.BALL_DIAMETER);
		}

	}

	/**
	 * Determining the nearest paddle and wether it's hit
	 * @param p the top or bottom paddle
	 * @return boolean Value wether the paddle is hit or not
	 */
	public boolean hitsPaddle(Paddle p) {
		boolean hitsPaddle = false;
		//test for top paddle
		if (position.getY() < (double)Constants.SCREEN_HEIGHT / 2 - 
				(double)Constants.BALL_DIAMETER / 2 && p.getPosition().getY() == 0) {
			
			if (position.getY() <= Constants.PADDLE_HEIGHT &&
				p.getPosition().getX() + (double)Constants.PADDLE_WIDTH >= position.getX()
				&& position.getX() + (double)Constants.BALL_DIAMETER >= 
				p.getPosition().getX()) {
						return hitsPaddle = true;
			}
				
	
			//test for bottom paddle
		} else if (position.getY() > (double)Constants.SCREEN_HEIGHT / 2 - 
				(double)Constants.BALL_DIAMETER / 2 && p.getPosition().getY() != 0) {
			
			if (p.getPosition().getY() <= position.getY() + Constants.BALL_DIAMETER &&
				p.getPosition().getX() + (double)Constants.PADDLE_WIDTH >= position.getX()
				&& position.getX() + (double)Constants.BALL_DIAMETER >= 
				p.getPosition().getX()) {
						return hitsPaddle = true;
			}
		}
		
		return hitsPaddle;
	}
	
	/**
	 * Ball behavior when hitting the paddle
	 * @param p the top or bottom paddle
	 */
	public void reflectOnPaddle(Paddle p) {
		if (p.getPosition().getY() == 0) {
			Position offset = new Position(p.getPosition().getX() + 
					(double)Constants.PADDLE_WIDTH / 2, (Constants.PADDLE_HEIGHT - 
					Constants.REFLECTION_OFFSET));
			
			Position current = new Position(this.position.getX() + 
					(double) Constants.BALL_DIAMETER / 2, this.position.getY() + 
					(double) Constants.BALL_DIAMETER / 2);
			
			this.direction = new Vector2D(offset, current);
			direction.rescale();
		}else if (p.getPosition().getY() != 0) {
			Position offset = new Position(p.getPosition().getX() + 
					(double)Constants.PADDLE_WIDTH / 2, (Constants.SCREEN_HEIGHT -
					Constants.PADDLE_HEIGHT + Constants.REFLECTION_OFFSET));
			
			Position current = new Position(this.position.getX() + 
					(double) Constants.BALL_DIAMETER / 2, this.position.getY() + 
					(double) Constants.BALL_DIAMETER / 2);
			
			this.direction = new Vector2D(offset, current);
			direction.rescale();
		}
	}

}
