package break_out.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import break_out.model.Game;
import break_out.view.Field;
import break_out.view.StartScreen;
import break_out.view.View;

/**
 * The controller takes care of the input events and reacts on those events by
 * manipulating the view and updates the model.
 * 
 * @author dmlux, modified by I. Schumacher and I. Traupe
 * 
 */
public class Controller implements ActionListener, KeyListener {

	/**
	 * The game as model that is connected to this controller
	 */
	private Game game;

	/**
	 * The view that is connected to this controller
	 */
	private View view;

	/**
	 * The constructor expects a view to construct itself.
	 * 
	 * @param view
	 *            The view that is connected to this controller
	 */
	public Controller(View view) {
		this.view = view;

		// Assigning the listeners
		assignActionListener();
		assignKeyListener();
	}

	/**
	 * The controller gets all buttons out of the view with this method and adds
	 * this controller as an action listener. Every time the user pushed a button
	 * the action listener (this controller) gets an action event.
	 */
	private void assignActionListener() {
		// Get the start screen to add this controller as action
		// listener to the buttons.
		view.getStartScreen().addActionListenerToStartButton(this);
		view.getStartScreen().addActionListenerToQuitButton(this);
	}

	/**
	 * With this method the controller adds himself as a KeyListener. Every time the
	 * user pushed a key the KeyListener (this controller) gets an KeyEvent.
	 */
	private void assignKeyListener() {
		// Get the field to add this controller as KeyListener
		view.getField().addKeyListener(this);
	}

	/**
	 * If the user clicks any button this ActionListener will get called. The method
	 * will get an ActionEvent e which held the source of this event.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// We will get the startScreen view from the view
		StartScreen startScreen = view.getStartScreen();

		// The startScreen view has some buttons. We will compare the source of the
		// event e with the startButton to get sure that this button was the event
		// source ... or simple: The user clicked this particular button.
		if (startScreen.getStartButton().equals(e.getSource())) {
			// The players name of the input field in the start window
			String playersName = startScreen.getPlayersName();
			playersName = playersName.trim();
			if (playersName.length() < 1) {
				// If the players name is too short, we won't accept this and display an error
				// message
				startScreen.showError("Der Name ist ungültig");
			} else {
				// If everything is fine we can go on and create a new game.
				game = new Game(this);
				// ... and tell the view to set this new game object.
				view.setGame(game);
			}
		}

		// If the eventSource was the quit button we will exit the whole application.
		else if (startScreen.getQuitButton().equals(e.getSource())) {
			System.exit(0);
		}
	}

	/**
	 * This method will be called, after a key was typed. This means, that the key
	 * was pressed and released, before this method get called.
	 * @param e The key event
	 */
	@Override
	public void keyTyped(KeyEvent e) {

	}

	/**
	 * This method will be called, after a key was pressed down.
	 * @param e The key event
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		
	}

	/**
	 * This method will be called, after a key was released.
	 * @param e The key event
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	/**
	 * This method switches the view to the StartScreen view.
	 */
	public void toStartScreen() {
		view.showScreen(StartScreen.class.getName());
		view.getStartScreen().requestFocusInWindow();
	}

	/**
	 * This method switches the view to the FieldView which will display the
	 * playground.
	 */
	public void toPlayground() {
		view.showScreen(Field.class.getName());
		view.getField().requestFocusInWindow();
	}

}
