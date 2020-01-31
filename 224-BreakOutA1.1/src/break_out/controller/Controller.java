package break_out.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import break_out.Constants;
import break_out.model.Game;
import break_out.view.Field;
import break_out.view.StartScreen;
import break_out.view.View;

/**
 * The controller takes care of the input events and reacts on those events by
 * manipulating the view and updates the model.
 * 
 * @author dmlux, modified by I. Schumacher and I. Traupe modified by 224
 * 
 */
public class Controller extends Thread implements ActionListener, KeyListener {

	/**
	 * The game as model that is connected to this controller
	 */
	private Game game;

	/**
	 * The view that is connected to this controller
	 */
	private View view;
	
	/**
	 * the mode variable is 2 for coop and 1 for single player
	 */
	private int mode;
	
	/**
	 * status variables needed for the thread running in coop
	 */
	private boolean leftbottom = false;
	private boolean rightbottom = false;
	private boolean lefttop = false;
	private boolean righttop = false;
	private boolean finished = false;

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
		view.getStartScreen().addActionListenerToCoopButton(this);
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
			mode = 1; //single player
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
		// new block for the coop mode
		else if(startScreen.getCoop().equals(e.getSource())){
			finished = false; // TODO : Variable gibt Startsignal für Thread
			
			mode = 2; // multiplayer mode
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
			//TODO : Versucht neuen Thread zu starten (falls schon vorhanden -> tue nichts)
			try {
				start();	
			} catch(Exception ex) {

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
	 * space bar starts and stops the ball
	 * q and esc exit the level
	 * in single player mode:
	 * a and left arrow moves the nearest paddle to the left 
	 * d and right arrow moves the nearest paddle to the right
	 * in coop:
	 * a and d control the top paddle
	 * left and right arrow control the bottom paddle 
	 * @param e The key event
	 */
	@Override
	public synchronized void keyPressed(KeyEvent e) {
		int kp = e.getKeyCode();
		boolean ap = true; // active paddle true = bottom
		//test for bottom 
		if(game.getLevel().getBall().getPosition().getY() > 
			(double)Constants.SCREEN_HEIGHT / 2 - (double)Constants.BALL_DIAMETER / 2) {
				ap = true;
		}else {
				ap = false;
		}
		
		if(kp == KeyEvent.VK_SPACE) {
			if(game.getLevel().ballWasStarted()) {
				game.getLevel().stopBall();
			}else {
				game.getLevel().startBall();
			}
		// single player mode 
		}else if((kp == KeyEvent.VK_RIGHT || kp == KeyEvent.VK_D) && mode == 1){
			if(ap) {
				game.getLevel().getPaddleBottom().setDirection(1);
				game.getLevel().getPaddleTop().setDirection(0);
			}else {
				game.getLevel().getPaddleTop().setDirection(1);
				game.getLevel().getPaddleBottom().setDirection(0);
			}
		}else if((kp == KeyEvent.VK_LEFT || kp == KeyEvent.VK_A) && mode == 1){
			if(ap) {
				game.getLevel().getPaddleBottom().setDirection(-1);
				game.getLevel().getPaddleTop().setDirection(0);
			}else {
				game.getLevel().getPaddleTop().setDirection(-1);
				game.getLevel().getPaddleBottom().setDirection(0);
			}
		}else if(kp == KeyEvent.VK_ESCAPE || kp == KeyEvent.VK_Q) {
			game.getLevel().setFinished(true);
			finished = true;
			toStartScreen(game.getLevel().getScore()); 
		// coop mode sets variables for the thread to react on
		}else if(mode == 2){
			if(kp == KeyEvent.VK_LEFT){
				leftbottom = true;
			}else if(kp == KeyEvent.VK_RIGHT){
				rightbottom = true;
			}else if(kp == KeyEvent.VK_A){
				lefttop = true;
			}else if(kp == KeyEvent.VK_D){
				righttop = true;
			}
		}
	}

	/**
	 * This method will be called, after a key was released.
	 * If any of the direction keys is released the paddles stop moving
	 * @param e The key event
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		int kp = e.getKeyCode();
		if(mode == 2) {
			if (kp == KeyEvent.VK_LEFT) {
				leftbottom = false;
			} else if (kp == KeyEvent.VK_RIGHT) {
				rightbottom = false;
			} else if (kp == KeyEvent.VK_A) {
				lefttop = false;
			} else if (kp == KeyEvent.VK_D) {
				righttop = false;
			}
		}else{
			if(kp == KeyEvent.VK_RIGHT || kp == KeyEvent.VK_D || kp == KeyEvent.VK_LEFT ||
					kp == KeyEvent.VK_A) {

				game.getLevel().getPaddleTop().setDirection(0);
				game.getLevel().getPaddleBottom().setDirection(0);
			}
		}

		
	}

	// TODO
	public void run(){
		try{
			while(true){
				finished = view.getGame().getLevel().getFinished(); // TODO : Regelmäßiges Update der Thread-Status-Variablen

				if(lefttop && leftbottom){
					game.getLevel().getPaddleTop().setDirection(-1);
					game.getLevel().getPaddleBottom().setDirection(-1);
				}else if(lefttop && rightbottom){
					game.getLevel().getPaddleTop().setDirection(-1);
					game.getLevel().getPaddleBottom().setDirection(1);
				}else if(righttop && leftbottom){
					game.getLevel().getPaddleTop().setDirection(1);
					game.getLevel().getPaddleBottom().setDirection(-1);
				}else if(righttop && rightbottom){
					game.getLevel().getPaddleTop().setDirection(1);
					game.getLevel().getPaddleBottom().setDirection(1);
				}else if(righttop){
					game.getLevel().getPaddleTop().setDirection(1);
					game.getLevel().getPaddleBottom().setDirection(0);
				}else if(lefttop){
					game.getLevel().getPaddleTop().setDirection(-1);
					game.getLevel().getPaddleBottom().setDirection(0);
				}else if(rightbottom){
					game.getLevel().getPaddleTop().setDirection(0);
					game.getLevel().getPaddleBottom().setDirection(1);
				}else if(leftbottom){
					game.getLevel().getPaddleTop().setDirection(0);
					game.getLevel().getPaddleBottom().setDirection(-1);
				}else{
					try {
						game.getLevel().getPaddleTop().setDirection(0);
						game.getLevel().getPaddleBottom().setDirection(0);
					}catch(Exception e){

					}
				}
				Thread.sleep(2);
				// TODO : Thread wird schlafen geschickt, bis sich Thread-Status-Variable ändert
				/*
				* äußere while darf nie beendet werden, da damit der Thread stirbt
				* -> keine Argumente die einen break hervorrufen können
				* -> "Standby"-Status muss in while erfolgen
				* -> Problem: im Thread kann der Status nicht mehr aktualisiert werden
				* -> Änderung der Variablen muss bei Buttondruck erfolgen
				* */
				while(finished){
					Thread.sleep(100);
					// System.out.print("s"); TODO : Test wann Standby-Status aktiv
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * This method switches the view to the StartScreen view
	 * and shows the new score.
	 */
	public void toStartScreen(int score) {
		view.getStartScreen().loadScore(score);
		view.showScreen(StartScreen.class.getName());
		view.getStartScreen().requestFocusInWindow();

		finished = true;
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
