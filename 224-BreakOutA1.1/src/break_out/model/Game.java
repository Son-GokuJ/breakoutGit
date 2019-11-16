package break_out.model;

import java.util.List;
import java.util.ArrayList;

import break_out.controller.Controller;
import break_out.view.View;

/**
 * This class contains information about the game (the model in MVC)
 * 
 * @author dmlux, modified by I. Schumacher
 * 
 */
public class Game {

	/**
	 * A list of observer objects
	 */
	private List<View> observers = new ArrayList<View>();

	/**
	 * The controller of the game
	 */
	private Controller controller;

	/**
	 * The current level
	 */
	private Level level;

	/**
	 * The first levelnumber
	 */
	private int firstLevel = 1;

	/**
	 * The last levelnumber
	 */
	private int maxLevel = 1;

	/**
	 * The total score of the game
	 */
	private int score = 0;

	/**
	 * The constructor creates a new game instance with the given Controller
	 * 
	 * @param controller
	 *            The controller to manage this instance (MVC-patter)
	 */
	public Game(Controller controller) {
		this.controller = controller;
		createLevel(firstLevel, 0);
	}

	// The three methods of the mvc pattern ----------------
	public void addObserver(View observer) {
		observers.add(observer);
	}

	public void removeObserver(View observer) {
		observers.remove(observer);
	}

	public void notifyObservers() {
		for (View observer : observers)
			observer.modelChanged(this);
	}
	// -------------------------------------------------------

	/**
	 * Getter for the Controller
	 * 
	 * @return controller The controller of this game
	 */
	public Controller getController() {
		return controller;
	}

	/**
	 * Getter for the current Level
	 * 
	 * @return level The current level of the game
	 */
	public Level getLevel() {
		return level;
	}

	/**
	 * Getter for the total score
	 * 
	 * @return score The current score of the game
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Creates the first or the next level, if the level number is less or equal
	 * maxLevel. If the current level is higher than maxLevel the view will be
	 * switched to the startScreen.
	 * 
	 * @param levelnr
	 *            The number for the next level
	 * @param score
	 *            The current players score after finishing the previous level.
	 */
	public void createLevel(int levelnr, int score) {
		this.score = score;
		if (levelnr <= maxLevel) {
			// Creates a new level instance
			level = new Level(this, levelnr, score);
			// calls the run method to start the new level
			level.start();
			// tells the controller to switch to the field view which displays the playground
			controller.toPlayground();
		} else {
			// tells the controller to switch to the startScreen of the game
			controller.toStartScreen();

		}

	}

}
