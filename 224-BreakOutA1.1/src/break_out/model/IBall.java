package break_out.model;

public interface IBall {

    // Exercise 1
    public void updatePosition();
    public void reactOnBorder();
    public Position getPosition();
    public Vector2D getDirection();

    // Exercise 2
    public boolean hitsPaddle(Paddle paddle);
    public void reflectOnPaddle(Paddle paddle);
}