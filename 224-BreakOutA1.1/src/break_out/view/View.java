package break_out.view;

import java.awt.CardLayout;

import javax.swing.JFrame;

import break_out.Constants;
import break_out.model.Game;

/**
 * The view class manages the depiction of the components inside the JFrames. It
 * gets the components from the game which is connected to this class
 * 
 * @author dmlux
 * 
 */
public class View extends JFrame  {

	/**
	 * Automatic generated serial version UID
	 */
	private static final long serialVersionUID = -1850986636132660133L;

	/**
	 * THe layout
	 */
	private CardLayout cardLayout;

	/**
	 * The connected game
	 */
	private Game game;

	/**
	 * The start screen of this application
	 */
	private StartScreen startScreen;

	/**
	 * The playground
	 */
	private Field field;

	
	/**
	 * The constructor of the view
	 */
	public View() {
		super(Constants.APP_TITLE);

		// sets the default close operation
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //DISPOSE_ON_CLOSE);

		// adds a layout to the view
		cardLayout = new CardLayout();
		getContentPane().setLayout(cardLayout);

		// adding screens to the view
		startScreen = new StartScreen(this);
		field = new Field(this);

		getContentPane().add(startScreen, StartScreen.class.getName());
		getContentPane().add(field, Field.class.getName());

		// show start screen first
		cardLayout.show(getContentPane(), StartScreen.class.getName());

		// set the start position of the frame
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
		pack();
	}

	/**
	 * Getter for the start screen
	 * @return startScreen
	 */
	public StartScreen getStartScreen() {
		return startScreen;
	}

	/**
	 * Getter for the playground
	 * @return field
	 */
	public Field getField() {
		return field;
	}

	/**
	 * Getter for the game
	 * @return game
	 */
	public Game getGame() {
		return game;
	}
	
	/**
	 * Setter for the game
	 * @param game The current game
	 */
	public void setGame(Game game) {
		// set the game as model
		this.game = game;
		game.addObserver(this);
	}
	
	/**
	 * Shows a given screen if the card layout contains this screen
	 * @param screenName The screen to be shown
	 */
	public void showScreen(String screenName) {
		cardLayout.show(getContentPane(), screenName);
	}

	/**
	 * Called by game.notifyObservers() in the run()-method of Level class
	 * to repaint the playground
	 * @param game The game to observe
	 */
	public void modelChanged(Game game) {
		this.game = game;
		// Calls the method paintComponents() in the Field class
		field.repaint();
	}
}
