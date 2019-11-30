package break_out.model;

import java.awt.Color;

import break_out.Constants;

/**
 * This class contains information about the two paddles and 
 * their behavior.
 * 
 * @author Group 224
 * @author Fabian Schinzler
 * @author Jonas Kremmers
 */

public class Paddle implements IPaddle {
	/**
	 * The paddles position on the playground
	 */
	private Position position;
	

	/**
	 * The paddles width, height and color.
	 */
	private int width = Constants.PADDLE_WIDTH;
	private int height = Constants.PADDLE_HEIGHT;
	private Color color = Color.BLACK;
	
	/**
	 * The constructor for the paddle.
	 * @param position The paddles position.
	 */
	public Paddle(Position position) {
		this.position = position;
	}
	
	/**
	 * get method for the paddle position
	 * @return position
	 */
	@Override
	public Position getPosition() {
		return this.position;
	}

	/**
	 * set method for the position
	 * @param position
	 */
	@Override
	public void setPosition(Position position) {
		this.position = position;
	}
	
	/**
	 * get method for the paddle color
	 * @return color
	 */
	@Override
	public Color getColor() {
		return this.color;
	}

	
	/**
	 * set method for the paddle color
	 * @param color
	 */
	@Override
	public void setColor(Color color) {
		this.color = color;
	}
	
	/**
	 * get method for the paddle width
	 * @return width
	 */
	@Override
	public double getWidth() {
		return this.width;
	}

	
	/**
	 * set method for the paddle width
	 * @param width
	 */
	@Override
	public void setWidth(double width) {
		this.width = (int) width;
	}
	
	/**
	 * get method for the paddle height
	 * @return height 
	 */
	@Override
	public double getHeight() {
		return this.height;
	}

	
	/**
	 * set method for the paddle height
	 * @param height
	 */
	@Override
	public void setHeight(double height) {
		this.height = (int) height;
	}

}
