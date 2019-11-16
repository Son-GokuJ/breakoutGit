package break_out.model;

public interface IVector2D {
	 // Exercise 1
    public double getDx();
    public void setDx(double dx);
    public double getDy();
    public void setDy(double dy);

    public void rescale();
}
