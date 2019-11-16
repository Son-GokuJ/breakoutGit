package break_out;

import java.awt.Color;

/**
 * A class that contains all constant values to configure the game
 *
 * @author dmlux, modified by I. Schumacher
 *
 */
public class Constants {

    /**
     * The screen width in pixels
     */
    public static final Integer SCREEN_WIDTH = 880;

    /**
     * The screen height in pixels
     */
    public static final Integer SCREEN_HEIGHT = 750;

    /**
     * the application name
     */
    public static final String APP_TITLE = "BreakOut";

    /**
     * Debugging flag for special rendering hints
     */
    public static final boolean DEBUG_MODE = false;

    /**
     * The background color for the game menu
     */
    public static final Color BACKGROUND = new Color(52, 152, 219);

    /**
     * Amount of columns for blocks
     */
    public static final Integer SQUARES_X = 22;

    /**
     * Amount of the rows
     */
    public static final Integer SQUARES_Y = 30;

    /**
     * The paddle width in pixels
     */
    public static final Integer PADDLE_WIDTH = 70;

    /**
     * The paddle height in pixels
     */
    public static final Integer PADDLE_HEIGHT = 15;

    /**
     * The distance between paddle and the lower reflection offset.
     */
    public static final Double REFLECTION_OFFSET = 25.0;

    /**
     * The ball diameter in pixels
     */
    public static final Integer BALL_DIAMETER = 15;

    /**
     * The paddle speed
     */
    public static final Double DX_MOVEMENT = 4.5;

    /**
     * The ball speed
     */
    public static final Double BALL_SPEED = 1.20;

}