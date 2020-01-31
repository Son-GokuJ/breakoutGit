package break_out.model;
import java.awt.AWTException;
import java.awt.Rectangle;
import java.util.ArrayList;
import break_out.Constants;
import break_out.controller.Controller;
import break_out.controller.JSONReader;
import java.awt.Robot;
import java.awt.event.KeyEvent;

/**
 * This class contains information about the running game
 * 
 * @author dmlux
 * @author I. Schumacher modified by 224
 */
public class Level extends Thread implements ILevel  {

    /**
     * The game to which the level belongs 
     */
    private Game game;
    
    /**
     * The list of stones in this level.
     */
    private ArrayList<Stone> stones = new ArrayList<Stone>();
	 
    /**
   	 * The number of the level
   	 */
    private int levelnr;
    
    /**
     * The players lives
     */
    private int lives;
       
    /**
	 * The score of the level
	 */
    private int score;
    
    /**
     * The ball of the level
     */
    private Ball ball;
    
    /**
     * The paddle at the bottom
     */
    private Paddle paddleBottom;
    
    /**
     * The paddle at the top
     */
    private Paddle paddleTop;
    
    /**
     * Flag that shows if the ball was started
     */
    private boolean ballWasStarted = false;
    
    /**
     * finished is set to terminate the current game
     */
    private boolean finished = false;
   
    /**
     * The constructor creates a new level object and needs the current game object, 
     * the number of the level to be created and the current score
     * @param game The game object
     * @param levelnr The number of the new level object
     * @param score The score
     */
    public Level(Game game, int levelnr, int score) {
    	this.game = game;
    	this.levelnr = levelnr;
    	this.score = score;
        this.ball = new Ball();
        this.paddleBottom = new Paddle(new Position(
        		(Constants.SCREEN_WIDTH - Constants.PADDLE_WIDTH)/2, 
        		Constants.SCREEN_HEIGHT - Constants.PADDLE_HEIGHT));
        
        this.paddleTop = new Paddle(new Position(
        		(Constants.SCREEN_WIDTH - Constants.PADDLE_WIDTH)/2, 0));
        
        loadLevelData(levelnr);
    }

    /**
     * The getter for the ball object
     * @return ball The ball of the level
     */
    public Ball getBall() {
    	return this.ball;
    }
    
    /**
     * Sets ballWasStarted to true, the ball is moving
     */
    public void startBall() {
        ballWasStarted = true;
    }

    /**
     * Sets ballWasStarted to false, the ball is stopped
     */
    public void stopBall() {
        ballWasStarted = false;
    }
    
    /**
     * Returns if the ball is moving or stopped
     * @return ballWasStarted True: the ball is moving; false: the ball is stopped
     */
    public boolean ballWasStarted() {
        return ballWasStarted;
    }

    /**
     * The method of the level thread
     */
    public void run() {	
    	game.notifyObservers();
    		
    	// loop depends on state of variable finished 
    	while (!finished) {
    		// if ballWasStarted is true, the ball is moving
	        if (ballWasStarted) {
	                
	        	// Call here the balls method for updating his position on the playground
	        	ball.updatePosition();
	            		            	
	        	// Call here the balls method for reacting on the borders of the playground
		        ball.reactOnBorder();
	            
	            // Test for hitting the bottom paddle
	            if (ball.hitsPaddle(paddleBottom)) {
	        	// Call here the balls method for reacting on hitting the bottom paddle.
	            	ball.reflectOnPaddle(paddleBottom);
	            }
	            
	            // Test for hitting the top paddle
	            if (ball.hitsPaddle(paddleTop)) {
	        	// Call here the balls method for reacting on hitting the top paddle.
	            	ball.reflectOnPaddle(paddleTop);
	            }

	            // Test for hitting any stones
	            if(ball.hitsStone(stones)) {
	            	Rectangle ballRect = new Rectangle((int) ball.getPosition().getX(), (int) ball.getPosition().getY(),
	            										Constants.BALL_DIAMETER, Constants.BALL_DIAMETER);
	            	Rectangle stoneRect = new Rectangle((int) ball.getHitStone().getPosition().getX(), (int) ball.getHitStone().getPosition().getY(),
	            										(Constants.SCREEN_WIDTH / Constants.SQUARES_X) -3,
	            										(Constants.SCREEN_HEIGHT / Constants.SQUARES_Y) -3);
	            	ball.reflectOnStone(ballRect, stoneRect);
	            	updateStonesAndScore();
	            }

	            // Level change occurs when all stones are broken
	            if(allStonesBroken()){
	            	setFinished(true);
	            	game.createLevel(levelnr + 1, score);
				}

	            // decreasing lives when the ball is lost
	            if(ball.isLost()){
	            	decreaseLives();
				}
	           
	            //update paddles to check for keypress
	            paddleTop.updatePosition(ball);
	            paddleBottom.updatePosition(ball);

				// Tells the observer to repaint the components on the playground
				game.notifyObservers();
	        }
	        // The thread pauses for a short time 
	        try {
	            Thread.sleep(4);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	        
    	}   
    }

    
    /**
    * Loads the information for the level from a json-file located in the folder /res of the project
    * with alternative windows folder path
    * @param levelnr The number X for the LevelX.json file
    */
    private void loadLevelData(int levelnr) {
    		JSONReader reader = new JSONReader("./res/Level" + levelnr + ".json");
    		//JSONReader reader = new JSONReader("C:\\Users\\SchnitzelDöner\\IdeaProjects\\breakoutGit\\224-BreakOutA1.1\\res\\Level" + levelnr + ".json");
    		int[][] stoneTypes = reader.getStones2DArray();
    		this.lives = reader.getLifeCounter();
    		for(int y = 0; y < Constants.SQUARES_Y; y++) {
    			for(int x = 0; x < Constants.SQUARES_X; x++) {
    				if(stoneTypes[y][x] != 0) {
    					Position stonePosition = new Position((Constants.SCREEN_WIDTH / Constants.SQUARES_X)*x,
    														  (Constants.SCREEN_HEIGHT / Constants.SQUARES_Y)*y);
    					Stone stone = new Stone(stoneTypes[y][x], stonePosition);
    					stones.add(stone);
    				}
    			}
    		}
    }

    
    /**
     * The getter for the PaddleTop object
     * @return paddleTop The top paddle object
     */
	@Override
	public Paddle getPaddleTop() {
		return this.paddleTop;
	}

	
    /**
     * The getter for the PaddleBottom object
     * @return paddleBottom The bottom paddle objects
     */
	@Override
	public Paddle getPaddleBottom() {
		return this.paddleBottom;
	}
	
	/**
	 * The getter for the list of stones
	 * @return stones The list of stones
	 * */
	public ArrayList<Stone> getStones(){
		ArrayList<Stone> stonescopy = new ArrayList<>();
		stonescopy.addAll(stones);
		return stonescopy;
	}

	/**
	 * This method changes the stone type, when they are hit
	 * and counts the score for all the stones hit
	 */
	private void updateStonesAndScore() {
		this.score += ball.getHitStone().getValue();
		if(ball.getHitStone().getType() -1 > 0) {
			ball.getHitStone().setType(ball.getHitStone().getType() -1);
		}else {
			this.stones.remove(ball.getHitStone());
		}
	}

	/**
	 * This method removes all the stones once they are hit
	 */
	private boolean allStonesBroken() {
		return this.stones.size() ==0;
	}

	/**
	 * set method for the variable finished
	 * @param finished whether the current game is finished or not
	 */
	@Override
	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	/**
	 * Getter for the current score
	 * @return score The score value
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * Getter for the Lives in this Level
	 * @return lives The number of Lives
	 */
	public int getLives() {
		return lives;
	}

	/**
	 * This method decreases the number of lives left
	 * depending on whether the ball is lost
	 */
	public void decreaseLives(){
		if(lives > 1){
			lives--;
			paddleBottom.setPosition(new Position(
					(Constants.SCREEN_WIDTH - Constants.PADDLE_WIDTH)/2,
					Constants.SCREEN_HEIGHT - Constants.PADDLE_HEIGHT));

			paddleTop.setPosition(new Position(
					(Constants.SCREEN_WIDTH - Constants.PADDLE_WIDTH)/2, 0));

			ball = new Ball();
			ballWasStarted = false;
		}else{
			//going back to the startscreen
			game.getController().toStartScreen(score);
			setFinished(true);

		}
	}
}
    


	
