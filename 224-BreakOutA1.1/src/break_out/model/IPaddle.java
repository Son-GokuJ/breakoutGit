package break_out.model;

import java.awt.*;

public interface IPaddle {

    // Exercise 2
    public Position getPosition();
    public void setPosition(Position position);
    public Color getColor();
    public void setColor(Color color);
    public int getWidth();
    public void setWidth(int width);
    public int getHeight();
    public void setHeight(int height);

    // Exercise 3
    public int getDirection();
    public void setDirection(int direction);
    public void updatePosition(Ball ball);
}