/*
 * UIUtil.java
 *
 * Created on June 21, 2005, 12:48 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package org.jdesktop.lg3d.apps.orgchart.ui.common;

import java.awt.image.BufferedImage;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import javax.vecmath.Point3f;
import org.jdesktop.lg3d.apps.orgchart.framework.ServiceContext;
import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.sg.BoundingBox;
import org.jdesktop.lg3d.sg.Shape3D;
import org.jdesktop.lg3d.sg.Texture;
import org.jdesktop.lg3d.sg.Texture2D;
import org.jdesktop.lg3d.sg.utils.image.TextureLoader;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;


/**
 *
 * @author cc144453
 */
public final class UIUtil {
    
    public final static synchronized void errorDialog(
            ServiceContext context,
            String errorMessage, Throwable t) {
        if (context != null && context.getLogger() != null) {
            context.getLogger().log(Level.SEVERE, "UI exception", t);
        } else {
            t.printStackTrace(System.out);
        }
        
        if (errorMessage == null) {
            errorMessage = t.getMessage();
        }
        
        if (t != null) {
            StringWriter w = new StringWriter();
            t.printStackTrace(new PrintWriter(w));
            errorMessage += "\n\n" + w.toString();
        }
        
        JOptionPane.showMessageDialog(null,
                errorMessage,
                "UI error",
                JOptionPane.ERROR_MESSAGE);
        
    }
    
    public static final void setBoundingBox(Shape3D shape, float width, float height, float depth) {
        shape.setBoundsAutoCompute(false);
        if (depth < 0.0f) {
            shape.setBounds(
                    new BoundingBox(
                    new Point3f(-0.5f * width, -0.5f * height, depth),
                    new Point3f( 0.5f * width,  0.5f * height,  0.0f)));
        } else {
            shape.setBounds(
                    new BoundingBox(
                    new Point3f(-0.5f * width, -0.5f * height, 0.0f),
                    new Point3f( 0.5f * width,  0.5f * height,  depth)));
        }
    }
    
    public static final void setTexture(Shape3D shape, BufferedImage image) {
        Appearance app = shape.getAppearance();
        if (app == null) {
            app = new SimpleAppearance(1.0f, 1.0f, 1.0f, 1.0f,
                    SimpleAppearance.ENABLE_TEXTURE);
            shape.setAppearance(app);
        }
        setTexture(app, image);
    }
    
    public static final void setTexture(Appearance app, BufferedImage image) {
        TextureLoader texLoader = new TextureLoader(image);
        Texture tex = texLoader.getTexture();
        tex.setCapability(Texture2D.ALLOW_IMAGE_READ);
        tex.setMinFilter(Texture2D.MULTI_LEVEL_POINT);
        tex.setMagFilter(Texture2D.LINEAR_SHARPEN);
        app.setTexture(tex);
    }
    
    
}
