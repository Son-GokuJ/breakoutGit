package break_out.model;

import break_out.Constants;
import break_out.model.Position;

/**
 * This class represent a two dimensional vector.
 * 
 * @author I. Schumacher
 * @author modified by 224
 */
public class Vector2D implements IVector2D{

	/**
	 * The x part of the vector
	 */
	private double dx;

	/**
	 * The y part of the vector
	 */
	private double dy;

	/**
	 * This constructor creates a new vector with the given x and y parts.
	 * @param dx the delta x part for the new vector
	 * @param dy the delty y part for the new vector
	 */
	public Vector2D(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}

	
	/**
	 * This constructor creates a new vector with two given positions on the field.
	 * @param pos1 the starting position
	 * @param pos2 the end position
	 */
	public Vector2D(Position pos1, Position pos2) {
		this.dx = pos2.getX() - pos1.getX(); 
		this.dy = pos2.getY() - pos1.getY(); 
	}
	
	/**
	 * Getter for the dx-part
	 * @return dx The dx part of this vector
	 */
	public double getDx() {
		return dx;
	}

	/**
	 * Setter for the dx-part
	 * @param dx The new dx part of this vector
	 */
	public void setDx(double dx) {
		this.dx = dx;
	}

	/**
	 * Getter for the dy-part
	 * @return dy The dy part of this vector
	 */
	public double getDy() {
		return dy;
	}

	/**
	 * Setter for the dy-part
	 * @param dy The new dy part of this vector
	 */
	public void setDy(double dy) {
		this.dy = dy;
	}
	
	/**
	 * Rescales the vector so that length = 1
	 * and then scales vector according to BALL_SPEED
	 */
	public void rescale() {
		double oldX = this.dx;
		double oldY = this.dy;
		this.dx = (oldX / (Math.sqrt(oldX * oldX + oldY * oldY))) 
				* Constants.BALL_SPEED;
		this.dy = (oldY / (Math.sqrt(oldX * oldX + oldY + oldY))) 
				* Constants.BALL_SPEED;
	}

}
