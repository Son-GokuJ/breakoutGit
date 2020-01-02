package break_out.model;

import java.util.ArrayList;

public interface IBall {

    // Exercise 1
    public void updatePosition();
    public void reactOnBorder();
    public Position getPosition();
    public Vector2D getDirection();

    // Exercise 2
    public boolean hitsPaddle(Paddle paddle);
    public void reflectOnPaddle(Paddle paddle);

    // Exercise 4
    public boolean hitsStone(ArrayList<Stone> stones);
    public Stone getHitStone();
}

