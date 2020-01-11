package break_out.model;
import java.util.ArrayList;
import break_out.Constants;

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
     * The players lifes
     */
    private int lifes;
       
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
	        	
	                               
	            // Tells the observer to repaint the components on the playground
	            game.notifyObservers();  
	            
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
	           
	            //update paddles to check for keypress
	            paddleTop.updatePosition(ball);
	            paddleBottom.updatePosition(ball);
	            
	            
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
    * @param levelnr The number X for the LevelX.json file
    */
    private void loadLevelData(int levelnr) {
    		JSONReader reader = new JSONReader("res/Level" + levelnr + ".json");
    		int[][] stoneTypes = reader.getStones2DArray();
    		this.lifes = reader.getLifeCounter();
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
	 * set method for the variable finished
	 * @param finished whether the current game is finished or not
	 */
	@Override
	public void setFinished(boolean finished) {
		this.finished = finished;
		
	}
    
}
    


	
