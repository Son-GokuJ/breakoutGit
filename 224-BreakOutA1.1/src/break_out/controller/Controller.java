package break_out.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import break_out.Constants;
import break_out.model.Game;
import break_out.model.Position;
import break_out.model.Stone;
import break_out.model.Vector2D;
import break_out.view.Field;
import break_out.view.StartScreen;
import break_out.view.View;

/**
 * The controller takes care of the input events and reacts on those events by
 * manipulating the view and updates the model.
 *
 * @author dmlux, modified by I. Schumacher and I. Traupe modified by 224
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
     * Activated paddle to react; false = bottom
     */
    private boolean flag = false;

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
     * @param view The view that is connected to this controller
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
            try {
                start();
            } catch (Exception ex) {

            }
            // new block for the coop mode
        } else if (startScreen.getCoop().equals(e.getSource())) {
            finished = false; //Variable gibt "Startsignal" für Thread

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
            //Versucht neuen Thread zu starten (falls schon vorhanden -> tue nichts)
            try {
                start();
            } catch (Exception ex) {

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
     *
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
     *
     * @param e The key event
     */
    @Override
    public synchronized void keyPressed(KeyEvent e) {
        int kp = e.getKeyCode();
        boolean ap = true; // active paddle true = bottom

        if (kp == KeyEvent.VK_SPACE) {
            if (game.getLevel().ballWasStarted()) {
                game.getLevel().stopBall();
            } else {
                game.getLevel().startBall();
            }
        } else if (kp == KeyEvent.VK_ESCAPE || kp == KeyEvent.VK_Q) {
            game.getLevel().setFinished(true);
            finished = true;
            toStartScreen(game.getLevel().getScore());
            // coop mode sets variables for the thread to react on
        }
        //singleplayer mode
        if (mode == 1) {
//            if ((kp == KeyEvent.VK_RIGHT || kp == KeyEvent.VK_D)) {
//                game.getLevel().getPaddleBottom().setDirection(1);
//            } else if ((kp == KeyEvent.VK_LEFT || kp == KeyEvent.VK_A)) {
//                game.getLevel().getPaddleBottom().setDirection(-1);
//            }

        } else if (mode == 2) {
            if (kp == KeyEvent.VK_LEFT) {
                leftbottom = true;
            } else if (kp == KeyEvent.VK_RIGHT) {
                rightbottom = true;
            } else if (kp == KeyEvent.VK_A) {
                lefttop = true;
            } else if (kp == KeyEvent.VK_D) {
                righttop = true;
            }
        }
    }

    /**
     * This method will be called, after a key was released.
     * If any of the direction keys is released the paddles stop moving
     *
     * @param e The key event
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int kp = e.getKeyCode();
        if (mode == 2) {
            if (kp == KeyEvent.VK_LEFT) {
                leftbottom = false;
            } else if (kp == KeyEvent.VK_RIGHT) {
                rightbottom = false;
            } else if (kp == KeyEvent.VK_A) {
                lefttop = false;
            } else if (kp == KeyEvent.VK_D) {
                righttop = false;
            }
        } else {
            if (kp == KeyEvent.VK_RIGHT || kp == KeyEvent.VK_D || kp == KeyEvent.VK_LEFT ||
                    kp == KeyEvent.VK_A) {

                game.getLevel().getPaddleTop().setDirection(0);
                game.getLevel().getPaddleBottom().setDirection(0);
            }
        }
    }

    /*
     *   Paddle soll aus Auftreffpunkt des Balls auf Paddleoberseite(Bottom) bzw. Paddleunterseite(Top)
     *   die optimale Flugbahn berechnen, um den nächstgelegensten Stein zu treffen.
     *   1. Berechnung des nächstgelegen Steins zum Auftreffpunkt
     *   2. Berechnung des Vektors Auftreffpunkt -> Mittelpunkt des Steins (siehe 1.2)
     *   3. Berechnung, um optimale Paddlepostion zu finden, damit Änderungsvektor an Steinvektor angenähert ist
     *
     *   1.1 Berechnung des Auftreffpunktes
     *       Position des Balls weiterrechnen mit aktuellem Vektor (inkl. Speed) bis
     *           a) Paddlehöhe erreicht
     *           b) Außenwand berührt (links bzw. rechts)
     *   1.2 Berechnung des kürzesten Vektors zu Stein
     *       Stein-Array mit Schleife durchlaufen -> Vektor berechnen -> kürzesten speichern -> Zielposition zurückgeben
     *
     * 3. Areas definieren für Vektoren -> Annäherung durch Verschiebung des Paddles in nähere Richtung
     *       -> Rückgabe der Paddleposition -> Bewegung des Paddles auf Position
     * 4. Abfrage, sodass Berechnungen nur durchgeführt werden, wenn sich der Vektor ändert
     *
     * */

    private Position getHitPoint() {
        Position currPos = new Position(game.getLevel().getBall().getPosition());
        Vector2D dir = new Vector2D(game.getLevel().getBall().getDirection());
        int w;
        int h;

        if (dir.getDx() > 0) {
            w = (int) ((Constants.SCREEN_WIDTH - currPos.getX() - Constants.BALL_DIAMETER) / dir.getDx());
        } else {
            w = -(int) (currPos.getX() / dir.getDx());
        }

        if (dir.getDy() > 0) {
            h = (int) ((Constants.SCREEN_HEIGHT - currPos.getY() - Constants.BALL_DIAMETER -
                    Constants.PADDLE_HEIGHT) / dir.getDy());
        } else {
            h = -(int) ((currPos.getY() - Constants.PADDLE_HEIGHT) / dir.getDy());
        }

        System.out.print("x : " + currPos.getX());
        System.out.print(" , y: " + currPos.getY());
        System.out.print(" ,w : " + w);
        System.out.println(" , h: " + h);
        if (h <= w) {
            return new Position(currPos.getX() + dir.getDx() * h, currPos.getY() + dir.getDy() * h);
        } else {
            // return new Position(currPos.getX() + dir.getDx() * w, currPos.getY() + dir.getDy() * w);
            return null;
        }
//        while (true) {
//            currPos.setX(currPos.getX() + dir.getDx());
//            currPos.setY(currPos.getY() + dir.getDy());
//            if (currPos.getX() >= Constants.SCREEN_WIDTH - Constants.BALL_DIAMETER || currPos.getX() <= 0) {
//                //System.out.print(0);
//                return null;
//            } else if (currPos.getY() <= Constants.PADDLE_HEIGHT || currPos.getY() >= (Constants.SCREEN_HEIGHT -
//                    Constants.PADDLE_HEIGHT) - Constants.BALL_DIAMETER) {
//                //System.out.print(1);
//                return currPos;
//            }
//        }
    }

    private Vector2D getClosestStone(Position bp) {
        System.out.println(bp.getX() + " , " + bp.getY());
        Vector2D shortV = null;
        for (Stone s : game.getLevel().getStones()) {
            Position sp = new Position(s.getPosition().getX() + ((double)Constants.SCREEN_WIDTH / Constants.SQUARES_X) / 2,
                    s.getPosition().getY() + ((double)Constants.SCREEN_HEIGHT / Constants.SQUARES_Y) / 2);
            Vector2D tempV = new Vector2D(sp.getX() - bp.getX(), sp.getY() - bp.getY());
            if (shortV == null) {
                shortV = tempV;
            } else if (Math.sqrt(shortV.getDx() * shortV.getDx() + shortV.getDy() * shortV.getDy()) >
                    Math.sqrt(tempV.getDx() * tempV.getDx() + tempV.getDy() * tempV.getDy())) {
                shortV = tempV;
            }
        }
        shortV.rescale();
        return shortV;
    }

    private Position getOptPos(Vector2D shortV, Position bp) {
        if (bp.getY() <= ((double) Constants.SCREEN_HEIGHT + (double) Constants.BALL_DIAMETER) / 2) {
            flag = true;
            int y = (int) (Constants.PADDLE_HEIGHT - Constants.REFLECTION_OFFSET);
            int k = (int) ((y - bp.getY()) / shortV.getDy()); // immer negativ

            Position paddle = new Position(bp.getX() - (double) Constants.PADDLE_WIDTH / 2 + k * shortV.getDx(), 0);

            if (paddle.getX() > Constants.SCREEN_WIDTH - Constants.PADDLE_WIDTH) {
                paddle = new Position(Constants.SCREEN_WIDTH - Constants.PADDLE_WIDTH, 0);
            } else if (paddle.getX() < 0) {
                paddle = new Position(0, 0);
            }
            System.out.println(1 + ": " + paddle.getX() + " , " + paddle.getY());
            return paddle;
        } else {
            flag = false;
            int y = (int) (Constants.SCREEN_HEIGHT - Constants.PADDLE_HEIGHT + Constants.REFLECTION_OFFSET);
            int k = (int) ((y - bp.getY()) / shortV.getDy()); // immer negativ

            Position paddle = new Position(bp.getX() - (double) Constants.PADDLE_WIDTH / 2 + k * shortV.getDx(),
                    Constants.SCREEN_HEIGHT - Constants.PADDLE_HEIGHT);

            if (paddle.getX() > Constants.SCREEN_WIDTH - Constants.PADDLE_WIDTH) {
                paddle = new Position(Constants.SCREEN_WIDTH - Constants.PADDLE_WIDTH,
                        Constants.SCREEN_HEIGHT - Constants.PADDLE_HEIGHT);
            } else if (paddle.getX() < 0) {
                paddle = new Position(0, Constants.SCREEN_HEIGHT - Constants.PADDLE_HEIGHT);
            }

            System.out.println(2 + ": " + paddle.getX() + " , " + paddle.getY());
            return paddle;
        }
    }

    /**
     * This thread is needed in the coop mode
     * to be able to move both paddles at the same time
     */
    public void run() {
        try {
            Vector2D currV = new Vector2D(0, 0);
            int reached = 5;
            Position paddle = new Position(((double) Constants.SCREEN_WIDTH -
                    Constants.PADDLE_WIDTH) / 2, 0);
            while (true) {
                if (mode == 1) {

                    // TODO: Löst nur aus, wenn Ball von Paddle abprallt
                    // TODO: Durchgeführte Berechnungen augenscheinlich korrekt
                    // TODO: Auslösen bei Abprallen an Wand und Steinen
                    // System.out.println(currV.getDx() + " , " + currV.getDy());
                    if (currV.comp(game.getLevel().getBall().getDirection())) {
                        currV = new Vector2D(game.getLevel().getBall().getDirection());
                        Position bp = getHitPoint();
                        if (bp != null) {
                            Vector2D shortV = getClosestStone(bp);
                            paddle = getOptPos(shortV, bp);
                            reached = 0;
                        } else {
                            reached = 5;
                        }
                    }

                    if (flag && reached == 0) {
                        if (game.getLevel().getPaddleTop().getPosition().getX() < paddle.getX()) {
                            game.getLevel().getPaddleTop().setDirection(1);
                        } else {
                            game.getLevel().getPaddleTop().setDirection(-1);
                        }
                        game.getLevel().getPaddleBottom().setDirection(0);
                        if (paddle.getX() == game.getLevel().getPaddleTop().getPosition().getX()) {
                            reached = 1;
                        }
                    } else if (!flag && reached == 0) {
                        if (game.getLevel().getPaddleBottom().getPosition().getX() < paddle.getX()) {
                            game.getLevel().getPaddleBottom().setDirection(1);
                        } else {
                            game.getLevel().getPaddleBottom().setDirection(-1);
                        }
                        game.getLevel().getPaddleTop().setDirection(0);
                        if (paddle.getX() == game.getLevel().getPaddleBottom().getPosition().getX()) {
                            reached = 1;
                        }
                    } else if (game.getLevel().getBall().getPosition().getY() <=
                            ((double) Constants.SCREEN_HEIGHT - (double) Constants.BALL_DIAMETER) / 2 &&
                            reached == 5) {
                        if (game.getLevel().getBall().getPosition().getX() <=
                                game.getLevel().getPaddleTop().getPosition().getX()) {
                            game.getLevel().getPaddleTop().setDirection(-1);
                        } else if (game.getLevel().getBall().getPosition().getX() >=
                                game.getLevel().getPaddleTop().getPosition().getX() +
                                        game.getLevel().getPaddleTop().getWidth()) {
                            game.getLevel().getPaddleTop().setDirection(1);
                        } else {
                            game.getLevel().getPaddleTop().setDirection(0);
                        }
                        game.getLevel().getPaddleBottom().setDirection(0);
                    } else if (game.getLevel().getBall().getPosition().getY() >=
                            ((double) Constants.SCREEN_HEIGHT - (double) Constants.BALL_DIAMETER) / 2 &&
                            reached == 5) {
                        if (game.getLevel().getBall().getPosition().getX() <=
                                game.getLevel().getPaddleBottom().getPosition().getX()) {
                            game.getLevel().getPaddleBottom().setDirection(-1);
                        } else if (game.getLevel().getBall().getPosition().getX() >=
                                game.getLevel().getPaddleBottom().getPosition().getX() +
                                        game.getLevel().getPaddleBottom().getWidth()) {
                            game.getLevel().getPaddleBottom().setDirection(1);
                        } else {
                            game.getLevel().getPaddleBottom().setDirection(0);
                        }
                        game.getLevel().getPaddleTop().setDirection(0);
                    }

                    if (reached == 1) {
                        game.getLevel().getPaddleTop().setDirection(0);
                        game.getLevel().getPaddleBottom().setDirection(0);
                    }

//                    if (game.getLevel().getBall().getPosition().getY() <=
//                            ((double) Constants.SCREEN_HEIGHT - (double) Constants.BALL_DIAMETER) / 2) {
//                        if (game.getLevel().getBall().getPosition().getX() <=
//                                game.getLevel().getPaddleTop().getPosition().getX()) {
//                            game.getLevel().getPaddleTop().setDirection(-1);
//                        } else if (game.getLevel().getBall().getPosition().getX() >=
//                                game.getLevel().getPaddleTop().getPosition().getX() +
//                                       game.getLevel().getPaddleTop().getWidth()) {
//                            game.getLevel().getPaddleTop().setDirection(1);
//                        } else {
//                            game.getLevel().getPaddleTop().setDirection(0);
//                        }
//                        game.getLevel().getPaddleBottom().setDirection(0);
//                    } else {
//                        if (game.getLevel().getBall().getPosition().getX() <=
//                                game.getLevel().getPaddleBottom().getPosition().getX()) {
//                            game.getLevel().getPaddleBottom().setDirection(-1);
//                        } else if (game.getLevel().getBall().getPosition().getX() >=
//                                game.getLevel().getPaddleBottom().getPosition().getX() +
//                                        game.getLevel().getPaddleBottom().getWidth()) {
//                            game.getLevel().getPaddleBottom().setDirection(1);
//                        } else {
//                            game.getLevel().getPaddleBottom().setDirection(0);
//                        }
//                        game.getLevel().getPaddleTop().setDirection(0);
//                    }
                } else if (mode == 2) {
                    if (lefttop && leftbottom) {
                        game.getLevel().getPaddleTop().setDirection(-1);
                        game.getLevel().getPaddleBottom().setDirection(-1);
                    } else if (lefttop && rightbottom) {
                        game.getLevel().getPaddleTop().setDirection(-1);
                        game.getLevel().getPaddleBottom().setDirection(1);
                    } else if (righttop && leftbottom) {
                        game.getLevel().getPaddleTop().setDirection(1);
                        game.getLevel().getPaddleBottom().setDirection(-1);
                    } else if (righttop && rightbottom) {
                        game.getLevel().getPaddleTop().setDirection(1);
                        game.getLevel().getPaddleBottom().setDirection(1);
                    } else if (righttop) {
                        game.getLevel().getPaddleTop().setDirection(1);
                        game.getLevel().getPaddleBottom().setDirection(0);
                    } else if (lefttop) {
                        game.getLevel().getPaddleTop().setDirection(-1);
                        game.getLevel().getPaddleBottom().setDirection(0);
                    } else if (rightbottom) {
                        game.getLevel().getPaddleTop().setDirection(0);
                        game.getLevel().getPaddleBottom().setDirection(1);
                    } else if (leftbottom) {
                        game.getLevel().getPaddleTop().setDirection(0);
                        game.getLevel().getPaddleBottom().setDirection(-1);
                    } else {
                        try {
                            game.getLevel().getPaddleTop().setDirection(0);
                            game.getLevel().getPaddleBottom().setDirection(0);
                        } catch (Exception e) {

                        }
                    }
                }
                Thread.sleep(2);
                // Thread wird "schlafen" geschickt, bis sich Thread-Status-Variable ändert
                /*
                 * äußere while darf nie beendet werden, da damit der Thread stirbt
                 * -> keine Argumente die einen break hervorrufen können
                 * -> "Standby"-Status muss in while erfolgen
                 * -> Problem: im Thread kann der Status nicht mehr aktualisiert werden
                 * -> Änderung der Variablen muss bei Buttondruck erfolgen
                 * */
                while (finished) {
                    Thread.sleep(100);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method switches the view to the StartScreen view
     * and shows the new score.
     *
     * @param score the current score value
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
