package break_out.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

/**
 * This panel represents the background for special divisions in this application
 * 
 * @author dmlux
 * 
 */
public class SectionPanel extends JPanel {

    /**
     * Automatic generated serial version UID
     */
    private static final long serialVersionUID = -7773487090869704154L;

    /**
     * Color of the panel
     */
    private Color color;

    /**
     * Thickness of the border
     */
    protected int strokeSize = 1;

    /**
     * Color of the shadow
     */
    protected Color shadowColor = new Color(50, 50, 50);

    /**
     * Shadow flag
     */
    protected boolean shady = true;

    /**
     * Double value for the vertical curvature
     */
    protected Dimension arcs = new Dimension(10, 10);

    /**
     * Distance of shadow to the panel border
     */
    protected int shadowGap = 3;

    /**
     * Shadow offset
     */
    protected int shadowOffset = 3;

    /**
     * Shadow transparency 
     */
    protected int shadowAlpha = 200;

    
    /**
     * A constructor for the section panel
     */
    public SectionPanel() {
        super();
        setOpaque(false);

        // set background color
        this.color = new Color(220, 220, 220);
    }

    /**
     * A constructor that expects a background color for this panel
     * 
     * @param background The background color
     */
    public SectionPanel(Color background) {
        super();
        setOpaque(false);

        // set background
        this.color = background;
    }

    
    @Override
    public void setBackground(Color bg) {
        color = bg;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth();
        int height = getHeight();
        int shadowGap = this.shadowGap;

        Color shadowColorA = new Color(shadowColor.getRed(),
                shadowColor.getGreen(), shadowColor.getBlue(), shadowAlpha);
        Graphics2D g2 = (Graphics2D) g;

        // Sets antialiasing
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // Draws shadow borders if any.
        if (shady) {
            g2.setColor(shadowColorA);
            g2.fillRoundRect(shadowOffset, // X position
                    shadowOffset, // Y position
                    width - strokeSize - shadowOffset, // width
                    height - strokeSize - shadowOffset, // height
                    arcs.width, arcs.height); // arc Dimension
        } else
            shadowGap = 1;

        // Draws the rounded opaque panel with borders.
        Color c1 = color;
        int nr = Math.min((color.getRed() + 40), 255);
        int ng = Math.min((color.getGreen() + 40), 255);
        int nb = Math.min((color.getBlue() + 40), 255);
        Color c2 = new Color(nr, ng, nb);
        GradientPaint gradient = new GradientPaint(0, 0, c1, getWidth(),
                getHeight(), c2, true);
        g2.setPaint(gradient);
        g2.fillRoundRect(0, 0, width - shadowGap, height - shadowGap,
                arcs.width, arcs.height);

        g2.setColor(new Color(120, 120, 120));
        g2.setStroke(new BasicStroke(strokeSize));
        g2.drawRoundRect(0, 0, width - shadowGap, height - shadowGap,
                arcs.width, arcs.height);

        // Sets strokes to default, is better.
        g2.setStroke(new BasicStroke());
    }
}
