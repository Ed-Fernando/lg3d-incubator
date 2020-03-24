package org.jdesktop.lg3d.apps.orgchart.ui.common;

import java.awt.image.BufferedImage;
import javax.vecmath.Vector3f;
import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.sg.Shape3D;
import org.jdesktop.lg3d.sg.Texture;
import org.jdesktop.lg3d.utils.shape.FuzzyEdgePanel;
import org.jdesktop.lg3d.utils.shape.GlassyPanel;
import org.jdesktop.lg3d.utils.shape.RectShadow;
import org.jdesktop.lg3d.wg.Component3D;

/**
 * Very very preliminary help app whcih just shows the usage guide
 * on the screen.  Intended to become more sophisticated app as we
 * evolve the system...
 */
public class FramedPanel extends Component3D {
    static final float DEFAULT_ALPHA = 1.0f;
    static final float DEFAULT_DEPTH = 0.003f;
    static final float DEFAULT_BORDERWIDTH = 0.001f;
    private static float SHADOWN_N = 0.001f;
    private static float SHADOWN_E = 0.0015f;
    private static float SHADOWN_S = 0.002f;
    private static float SHADOWN_W = 0.001f;
    private static float SHADOWN_I = 0.001f;
    
    private Appearance frameApp;
    private Appearance bodyApp;
    
    public FramedPanel(float width, float height, 
            Appearance frameApp, Appearance bodyApp) {
        this(width, height, DEFAULT_DEPTH, DEFAULT_BORDERWIDTH, 
                frameApp, bodyApp);
    }
    
    public FramedPanel(
            float width, float height, float depth,
            float borderWidth,
            Appearance frameApp,
            Appearance bodyApp) {
        this.frameApp = frameApp;
        this.bodyApp = bodyApp;
        setPreferredSize(new Vector3f(width, height, depth));
        setMouseEventPropagatable(true);
        
        // create frame
        GlassyPanel frameDeco = new GlassyPanel(width + borderWidth * 2,
                height + borderWidth * 2,
                depth,
                depth * 0.1f,
                frameApp);
        //UIUtil.setBoundingBackward(frameDeco, width + borderWidth * 2, height + borderWidth * 2);
        Shape3D frameShadow = new RectShadow(width + borderWidth * 2,
                height + borderWidth * 2,
                SHADOWN_N, SHADOWN_E, SHADOWN_S, SHADOWN_W, SHADOWN_I,
                -depth,
                0.2f);
        //UIUtil.setBoundingBox(frameShadow, width, height, );
        
        // create body
        FuzzyEdgePanel body
                = new FuzzyEdgePanel(width, height, borderWidth * 0.5f, bodyApp);
        // move the bounds of the bottomBar back so that the transparency sorting
        // in Java 3D renders it first and the icons second.
        // This also requires a bug fix which is in Java 3D 1.3.2
        UIUtil.setBoundingBox(body, width, height, +0.01f);
        
        // add to component
        addChild(body);
        addChild(frameDeco);
        addChild(frameShadow);

    }
    
    public void setTexture(BufferedImage image) {
        UIUtil.setTexture(bodyApp, image);
    }
    
    public void setTexture(Texture tex) {
        bodyApp.setTexture(tex);
    }
    
}


