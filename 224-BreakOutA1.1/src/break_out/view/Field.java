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
 * @author dmlux, modified by iSchumacher
 * @author modified by 224 
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
	 * 
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
		int sectionX = Constants.SCREEN_WIDTH / Constants.SQUARES_X;
		int sectionY = Constants.SCREEN_HEIGHT / Constants.SQUARES_Y;
		int a = sectionX;
		while (a < 880 ) {
			g2.drawLine(a, 0, a, Constants.SCREEN_HEIGHT);
			a += sectionX;
		}
		
		int b = sectionY;
		while (b < 750 ) {
			g2.drawLine(0, b, Constants.SCREEN_WIDTH, b);
			b += sectionY;
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

}
