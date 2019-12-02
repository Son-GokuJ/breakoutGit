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
	 * @param position The new position of the paddle
	 */
	public Paddle(Position position) {
		this.position = position;
	}
	
	/**
	 * get method for the paddle position
	 * @return position The position of the paddle
	 */
	@Override
	public Position getPosition() {
		return this.position;
	}

	/**
	 * set method for the position
	 * @param position The new position of the paddle
	 */
	@Override
	public void setPosition(Position position) {
		this.position = position;
	}
	
	/**
	 * get method for the paddle color
	 * @return color The color of the paddle
	 */
	@Override
	public Color getColor() {
		return this.color;
	}

	
	/**
	 * set method for the paddle color
	 * @param color The new color of the paddle
	 */
	@Override
	public void setColor(Color color) {
		this.color = color;
	}
	
	/**
	 * get method for the paddle width
	 * @return width The width of the paddle
	 */
	@Override
	public int getWidth() {
		return this.width;
	}

	
	/**
	 * set method for the paddle width
	 * @param width The new width of the paddle
	 */
	@Override
	public void setWidth(int width) {
		this.width = width;
	}
	
	/**
	 * get method for the paddle height
	 * @return height The height of the paddle
	 */
	@Override
	public int getHeight() {
		return this.height;
	}

	
	/**
	 * set method for the paddle height
	 * @param height The new height of the paddle
	 */
	@Override
	public void setHeight(int height) {
		this.height = height;
	}

}
