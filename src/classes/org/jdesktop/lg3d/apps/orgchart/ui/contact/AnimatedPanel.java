/*
 *AnimatedPanel.java
 *
 * Created on May 25, 2005, 10:38 AM
 *
 */

package org.jdesktop.lg3d.apps.orgchart.ui.contact;
import org.jdesktop.lg3d.utils.c3danimation.NaturalMotionAnimation;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Frame3D;
import org.jdesktop.lg3d.wg.Toolkit3D;
import org.jdesktop.lg3d.apps.orgchart.ui.common.FramedPanel;

/**
 *
 * @author cc144453
 */
public abstract class AnimatedPanel extends Component3D {
    
    private static final float DEPTH = 0.05f;
    private static final float ROTATION_HIDDEN = (float)Math.PI;
    private static final float ROTATION_SHOWN = 0.0f;
    
    protected Frame3D parent;
    private boolean showState = false;
    private int oldDuration = -1;
    
    public AnimatedPanel(Frame3D parent) {
        super();
        this.parent = parent;
    }
    
    protected void createUI(Object param, float width, float height) {
        float halfScreen = Toolkit3D.getToolkit3D().getScreenWidth() * 0.5f;
        
        // create frame
        FramedPanel frame =
                new FramedPanel(width,
                height,
                new SimpleAppearance(1.0f, 1.0f, 1.0f, 1.0f,
                SimpleAppearance.ENABLE_TEXTURE | SimpleAppearance.DISABLE_CULLING),
                new SimpleAppearance(1.0f, 1.0f, 1.0f, 1.0f,
                SimpleAppearance.ENABLE_TEXTURE | SimpleAppearance.DISABLE_CULLING));
        
        // create and add body
        Component3D body = new Component3D();
        initBody(body, param, width, height);
        frame.addChild(body);
        addChild(frame);
        frame.setTranslation(halfScreen, 0.0f, 0.0f);
        
        // move axis and set rotation
        setTranslation(-halfScreen, 0.0f, DEPTH);
        setRotationAxis(0.0f, -1.0f, 0.0f);
        setRotationAngle(ROTATION_HIDDEN);
        setVisible(false);
    }
    
    public abstract void initBody(Component3D body, Object param, float width, float height);
    
    public synchronized void show(boolean show, int duration) {
        if (show != showState) {
            if (duration != oldDuration) {
                setAnimation(new NaturalMotionAnimation(duration));
            }
            changeRotationAngle(show ? ROTATION_SHOWN : ROTATION_HIDDEN);
            changeVisible(show);
            showState = show;
        }
    }
}
