package break_out.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import break_out.Constants;
import net.miginfocom.swing.MigLayout;

/**
 * The field represents the board of the game. All components are on the board
 * 
 * @author dmlux, modified by iSchumacher modified by 224 
 */
public class Field extends JPanel {

	/**
	 * Automatic generated serial version UID
	 */
	private static final long serialVersionUID = 2434478741721823327L;

	/**
	 * The connected view object
	 */
	private View view;

	/**
	 * The background color
	 */
	private Color background;

	/**
	 * The constructor needs a view
	 * @param view The view of this board
	 */
	public Field(View view) {
		super();

		this.view = view;
		this.background = new Color(177, 92, 107);

		setFocusable(true);

		// Load settings
		initialize();
	}

	/**
	 * Drawing the grid layout
	 * @param g2 the graphics object from Graphics2D
	 */
	private void drawGrid(Graphics2D g2) {
		for(int y = Constants.SCREEN_HEIGHT / Constants.SQUARES_Y; 
				y < Constants.SCREEN_HEIGHT; 
				y = y + (Constants.SCREEN_HEIGHT / Constants.SQUARES_Y)) {
			g2.drawLine(0, y, Constants.SCREEN_WIDTH, y);
		}
		for(int x = Constants.SCREEN_WIDTH / Constants.SQUARES_X; 
				x < Constants.SCREEN_WIDTH; 
				x = x + (Constants.SCREEN_WIDTH / Constants.SQUARES_X)) {
			g2.drawLine(x, 0, x, Constants.SCREEN_HEIGHT);
		}
	}
	
	
	/**
	 * Initializes the settings for the board
	 */
	private void initialize() {
		// creates a layout
		setLayout(new MigLayout("", "0[grow, fill]0", "0[grow, fill]0"));
	}

	/**
	 * Change the background color
	 * @param color The new color
	 */
	public void changeBackground(Color color) {
		background = color;
		repaint();
	}
	
	/**
	 * This method is called when painting/repainting the playground
	 * @param g the graphics object
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		double w = Constants.SCREEN_WIDTH;
		double h = Constants.SCREEN_HEIGHT;

		// Setting the dimensions of the playground
		setPreferredSize(new Dimension((int) w, (int) h));
		setMaximumSize(new Dimension((int) w, (int) h));
		setMinimumSize(new Dimension((int) w, (int) h));

		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		// Setting the background color
		g2.setColor(background);
		g2.fillRect(0, 0, getWidth(), getHeight());
		
		// Setting the color for the following components
		g2.setColor(new Color(200, 200, 200));
		
		// Calls the method for drawing the ball
		drawBall(g2);
		
		//Calls the method for drawing the grid
		drawGrid(g2);
		
		g2.setColor(view.getGame().getLevel().getPaddleTop().getColor());
		//Calls the method for drawing the top paddle
		drawPaddleTop(g2);

		g2.setColor(view.getGame().getLevel().getPaddleBottom().getColor());
		//Calls the method for drawing the bottom paddle
		drawPaddleBottom(g2);
	}

	/**
	 * Draws the ball
	 * @param g2 The graphics object
	 */
	private void drawBall(Graphics2D g2) {
		g2.fillOval((int) view.getGame().getLevel().getBall().getPosition().getX(),
				(int) view.getGame().getLevel().getBall().getPosition().getY(),
				Constants.BALL_DIAMETER,
				Constants.BALL_DIAMETER);
	}
	
	
	/**
	 * Draws the bottom paddle
	 * @param g2 The graphics object
	 */
	private void drawPaddleBottom(Graphics2D g2) {
		g2.fillRoundRect(
				(int) view.getGame().getLevel().getPaddleBottom().getPosition().getX(),
				(int) view.getGame().getLevel().getPaddleBottom().getPosition().getY(),
				(int) view.getGame().getLevel().getPaddleBottom().getWidth(),
				(int) view.getGame().getLevel().getPaddleBottom().getHeight(),15,15);
	}
	
	
	/**
	 * Draws the top paddle
	 * @param g2 The graphics object
	 */
	private void drawPaddleTop(Graphics2D g2) {
		g2.fillRoundRect(
				(int) view.getGame().getLevel().getPaddleTop().getPosition().getX(),
				(int) view.getGame().getLevel().getPaddleTop().getPosition().getY(),
				(int) view.getGame().getLevel().getPaddleTop().getWidth(),
				(int) view.getGame().getLevel().getPaddleTop().getHeight(),15,15);
	}

}
