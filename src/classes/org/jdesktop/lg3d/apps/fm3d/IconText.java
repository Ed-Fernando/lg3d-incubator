/*
 * Copyright (c) 2005, 2006 John Maltby
 *
 * Portions of code based upon:
 * Ls3D copyright (c) 2005 ENDO Yasuyuki
 * PingPong copyright (c) 2004, Johann Glaser
 * Folder and file images Ls3D copyright (c) 2005 ENDO Yasuyuki 
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */

package org.jdesktop.lg3d.apps.fm3d;

/**
 * Icon.java
 *
 * @author John Maltby
 */

import java.awt.*;
import java.awt.image.*;
import org.jdesktop.lg3d.wg.*;
import org.jdesktop.lg3d.sg.*;
import org.jdesktop.lg3d.sg.utils.image.TextureLoader;
import org.jdesktop.lg3d.utils.shape.*;
import javax.vecmath.Vector3f;

public class IconText extends Component3D {
    private Vector3f positionInWindow;    
    private String text;
    private FontMetrics fm;
    private float panelMetricWidth;
    private float panelMetricHeight;
    private int panelPointsWidth;
    private int panelPointsHeight; 
    private Font font;
    private TextureLoader loader;
    private Texture texture;
    private Shape3D textShape;
    private BufferedImage image;
    private SimpleAppearance textApp
            = new SimpleAppearance(0.0f, 0.0f, 0.0f, 1.0f, SimpleAppearance.DISABLE_CULLING | SimpleAppearance.ENABLE_TEXTURE);
    
    public IconText(float panelMetricWidth, float panelMetricHeight, int panelPointsWidth,
            int panelPointsHeight, Font font, String text, Color color) {
        this.text = text;
        this.panelMetricWidth = panelMetricWidth;
        this.panelMetricHeight = panelMetricHeight;
        this.panelPointsWidth = panelPointsWidth;
        this.panelPointsHeight = panelPointsHeight;        
        this.font = font;
        textShape = new ImagePanel(panelMetricWidth, panelMetricHeight);
        textApp.setCapability(SimpleAppearance.ALLOW_MATERIAL_WRITE);
        textShape.setCapability(Shape3D.ALLOW_APPEARANCE_WRITE);
        setTextColour(color);
        drawText(text);
        addChild(textShape);
    }
    
    public void drawText(String text) {
        image = new BufferedImage(panelPointsWidth, panelPointsHeight, BufferedImage.TYPE_INT_ARGB);         
        Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setFont(font);
        fm = g.getFontMetrics();
        
        // centre text
        int xstart = (panelPointsWidth - fm.stringWidth(text))/2;
        int ystart = (panelPointsHeight - fm.getHeight())/2 + fm.getAscent() + fm.getLeading();
        
        g.drawString(text, xstart, ystart); // pixel coords of bottom left pixel of first letter of string
        g.dispose();        
        
        loader = new TextureLoader(image);
        texture = loader.getTexture();
        textApp.setTexture(texture);
        textShape.setAppearance(textApp);
    }
    
    public void setTextColour(Color color) {
        textApp.setColor(color.getRed(), color.getGreen(), color.getBlue(), 1.0f);
    }  
    
    public void setPositionInWindow(Vector3f pos) {
        positionInWindow = pos;
        changeTranslation(positionInWindow);
    }    
    
    public Vector3f getPositionInWindow() {
        return positionInWindow;
    }    
}


