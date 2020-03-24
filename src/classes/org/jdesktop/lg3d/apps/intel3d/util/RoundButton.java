/*
 * RoundButton.java
 *
 * Created on November 25, 2003, 1:24 PM
 */

package org.jdesktop.lg3d.apps.intel3d.util;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;


/**
 *
 * @author  asrivast
 */
public class RoundButton extends JToggleButton {
    
    /** Creates a new instance of RoundButton */
    public RoundButton () {
        super();
        setDimension();
    }
    
    public RoundButton(String label, Icon imageIcon,  boolean selected) {
        super(label, imageIcon, selected);
        setDimension();
    }
    
    public RoundButton(String label, Icon imageIcon) {
        super(label, imageIcon);
        setDimension();
    }
    
    public RoundButton (String label) {
        super (label);
        setDimension();
    }
    
    private void setDimension() {
        // These statements enlarge the button so that it
        // becomes a circle rather than an oval.
        Dimension size = new Dimension(60, 60); // getPreferredSize();
        size.width = size.height = Math.max (size.width, size.height);
        setPreferredSize (size);
        
        // This call causes the JButton not to paint
        // the background.
        // This allows us to paint a round background.
        setContentAreaFilled (true);
    }
    
    // Paint the round background and label.
    protected void paintComponent (Graphics g) {
        if (getModel ().isArmed ()) {
            // You might want to make the highlight color
            // a property of the RoundButton class.
            g.setColor (Color.blue);
        } else {
            g.setColor (Color.GREEN);
        }
        g.fillOval (0, 0, getSize ().width-1, getSize ().height-1);
        
        // This call will paint the label and the
        // focus rectangle.
        super.paintComponent (g);
    }
    
    // Paint the border of the button using a simple stroke.
    protected void paintBorder (Graphics g) {
        g.setColor (getForeground ());
        g.drawOval (0, 0, getSize ().width-1,
        getSize ().height-1);
    }
    
    // Hit detection.
    Shape shape;
    public boolean contains (int x, int y) {
        // If the button has changed size,
        // make a new shape object.
        if (shape == null ||
        !shape.getBounds ().equals (getBounds ())) {
            shape = new Ellipse2D.Float (0, 0,
            getWidth (), getHeight ());
        }
        return shape.contains (x, y);
    }
    
}
