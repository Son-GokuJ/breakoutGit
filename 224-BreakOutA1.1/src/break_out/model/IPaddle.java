package break_out.model;

import java.awt.*;

public interface IPaddle {

    // Exercise 2
    public Position getPosition();
    public void setPosition(Position position);
    public Color getColor();
    public void setColor(Color color);
    public double getWidth();
    public void setWidth(double width);
    public double getHeight();
    public void setHeight(double height);
    
}