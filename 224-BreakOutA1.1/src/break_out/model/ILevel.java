package break_out.model;

import java.util.ArrayList;

public interface ILevel {
    // Exercise 1
    public Ball getBall();

    // Exercise 2
    public Paddle getPaddleTop();
    public Paddle getPaddleBottom();

    // Exercise 3
    public void setFinished(boolean finished);

    // Exercise 4
    public ArrayList<Stone> getStones();
}