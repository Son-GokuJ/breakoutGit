package break_out.model;

import java.awt.Color;

public interface IStone {

    // Exercise 3
    public int getType();
    public int getValue();
    public Color getColor();
    public Position getPosition();
    public void setType(int type);
    public void setValue(int value);
    public void setColor(Color color);
    public void setPosition(Position position);

}
