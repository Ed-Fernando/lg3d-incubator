/***************************************************************************
 *   Project Looking Glass                                                 *
 *   Incubator Project - 3D Start Menu                                     *
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 *   This program is distributed in the hope that it will be useful,       *
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of        *
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the         *
 *   GNU General Public License for more details.                          *
 *                                                                         *
 *   Author: Colin M. Bullock                                              *
 *   cmbullock@gmail.com                                                   *
 *                                                                         *
 *   You should have received a copy of the GNU General Public License     *
 *   along with this program; if not, write to the                         *
 *   Free Software Foundation, Inc.,                                       *
 *   59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.             *
 ***************************************************************************/
package org.jdesktop.lg3d.apps.blackgoat.component;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.sg.TextureAttributes;
import org.jdesktop.lg3d.sg.utils.image.TextureLoader;
import org.jdesktop.lg3d.utils.c3danimation.NaturalMotionAnimation;
import org.jdesktop.lg3d.utils.shape.FuzzyEdgePanel;
import org.jdesktop.lg3d.utils.shape.OrientedFuzzyEdgePanel;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Toolkit3D;

/**
 * Quick hack for testing start menu
 */
public class PopupTextComponent extends Component3D {
    
    private OrientedFuzzyEdgePanel panel;
    
    private SimpleAppearance app;
 
    private int fontSize;
    
    private Font textFont;
    
    private FontMetrics fm;
    
    private String text;
    
    public PopupTextComponent(int fontSize) {
        this.fontSize= fontSize;
        textFont= new Font("Serif", Font.PLAIN, fontSize);
        TextureAttributes textureAttr= new TextureAttributes();
        textureAttr.setTextureMode(TextureAttributes.REPLACE);
        app= new SimpleAppearance(0.6f, 0.8f, 0.6f, 0.5f,
     //   app= new SimpleAppearance(1.0f, 1.0f, 1.0f, 1.0f,
                SimpleAppearance.ENABLE_TEXTURE | SimpleAppearance.DISABLE_CULLING);
        app.setTextureAttributes(textureAttr);
        app.setCapability(Appearance.ALLOW_TEXTURE_READ);
        app.setCapability(Appearance.ALLOW_TEXTURE_WRITE);
        
        BufferedImage image= new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g= (Graphics2D)image.getGraphics();
        g.setFont(textFont);
        fm= g.getFontMetrics();
        g.dispose();
        setAnimation(new NaturalMotionAnimation(750));
       
    }
    
    public PopupTextComponent(String text, int fontSize) {
        this(fontSize);
        setText(text);
    }

    /**
     * @return Returns the text.
     */
    public String getText() {
        return text;
    }

    /**
     * @param text The text to set.
     */
    public void setText(String text) {
        this.text = text;
        
        String[] lines= text.split("\n");
        int maxLength= 0;
        for(String line : lines)
            if(fm.stringWidth(line) > maxLength) maxLength= fm.stringWidth(line);
        int width= 60 + maxLength;
        int height= 60 + (lines.length * (fm.getHeight() + 2));
        
        BufferedImage image= new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g= (Graphics2D)image.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setColor(Color.WHITE);
        g.setFont(textFont);
        for(int i= 0; i < lines.length; i++) {
           g.drawString(lines[i], 20, 30 + (i * (fontSize + 2)));
        }
        g.dispose();
        
        if(panel != null) {
            removeChild(panel);
        }
        
        Toolkit3D toolkit3d = Toolkit3D.getToolkit3D();
        Vector3f size= new Vector3f(toolkit3d.widthNativeToPhysical(image.getWidth()),
                toolkit3d.heightNativeToPhysical(image.getHeight()), 0.001f);
        panel= new OrientedFuzzyEdgePanel(size.x, size.y, size.z, app);
        addChild(panel);
        
        TextureLoader textureLoader= new TextureLoader(image);
        app.setTexture(textureLoader.getTexture());
        
        setPreferredSize(size);
    }
    
    public SimpleAppearance getImageApperance() {
    	return this.app;
    }
    
    
}
