/*
 * Button.java
 *
 * Created on May 23, 2005, 10:12 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package org.jdesktop.lg3d.apps.orgchart.ui.common;

import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.vecmath.Vector3f;
import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.utils.action.ActionBoolean;
import org.jdesktop.lg3d.utils.action.ActionNoArg;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MouseEnteredEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MousePressedEventAdapter;
import org.jdesktop.lg3d.utils.shape.Disc;
import org.jdesktop.lg3d.utils.shape.GlassyPanel;
import org.jdesktop.lg3d.utils.shape.OriginTranslation;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.utils.shape.Sphere;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.event.LgEventSource;

/**
 *
 * @author cc144453
 */
public class Button extends Component3D {
    
    public static final float DEFAULT_PRESSEDDEPTH = 0.01f;
    
    private static final int DISC_VERTICES = 10;
    private static final float TIPTEXT_X = 0.005f;
    private static final float TIPTEXT_Y = 0.002f;
    private static final float TIPTEXT_DEPTH = 0.02f;
    private static final Color DISABLED_TEXT_COLOR = new Color(0.7f, 0.7f, 0.7f, 1.0f);
    private static final Color DISABLED_BG_COLOR = new Color(0.3f, 0.3f, 0.3f, 1.0f);
    private static final int ANIMATION_DURATION = 150;
    
    private SimpleAppearance discApp;
    private Component3D tipTextPanel;
    private Component3D disabledTipTextPanel;
    private boolean enabled;
    private MouseClickedEventAdapter mouseClickListener;
    private ActionNoArg buttonListener;
    
    public Button(BufferedImage icon, float size,
            String tipText, String disabledTipText,
            ActionNoArg buttonListener,
            boolean enabled,
            Appearance buttonApp) {
        this.buttonListener = buttonListener;
        
        // add the disc and sphere defining the shape
        discApp = new SimpleAppearance(1.0f, 1.0f, 1.0f, 0.95f,
                SimpleAppearance.ENABLE_TEXTURE);
        Disc disc = new Disc(size, DISC_VERTICES, discApp);
        UIUtil.setBoundingBox(disc, size, size, 0.04f);
        UIUtil.setTexture(disc, icon);
        addChild(new OriginTranslation(disc, new Vector3f(0.0f, 0.0f, 0.001f)));
        Sphere sphere =
                new Sphere(size * 0.5f,
                (Sphere.GENERATE_NORMALS | Sphere.GENERATE_TEXTURE_COORDS),
                buttonApp);
        UIUtil.setBoundingBox(sphere.getShape(), size, size, 0.04f);
        addChild(sphere);
        
        // create tip text component
        TextRectangle tipTextShape = new TextRectangle(tipText);
        UIUtil.setBoundingBox(tipTextShape, tipTextShape.getWidth(), tipTextShape.getHeight(), 0.04f);
        tipTextPanel = new Component3D();
        tipTextPanel.addChild(tipTextShape);
        tipTextPanel.setTranslation(TIPTEXT_X, TIPTEXT_Y, TIPTEXT_DEPTH);
        tipTextPanel.setVisible(false);
        addChild(tipTextPanel);
        if (disabledTipText != null) {
            disabledTipTextPanel = new Component3D();
            disabledTipTextPanel.addChild(
                    new TextRectangle(disabledTipText,
                    DISABLED_TEXT_COLOR,
                    DISABLED_BG_COLOR));
            disabledTipTextPanel.setTranslation(TIPTEXT_X, TIPTEXT_Y, TIPTEXT_DEPTH);
            disabledTipTextPanel.setVisible(false);
            addChild(disabledTipTextPanel);
        } else {
            disabledTipTextPanel = tipTextPanel;
        }
        addListener(new MouseEnteredEventAdapter(
                new ActionBoolean() {
            public void performAction(LgEventSource source, boolean state) {
                if (isEnabled()) {
                    tipTextPanel.changeVisible(state);
                } else {
                    disabledTipTextPanel.setVisible(state);
                }
            }
        }));
        
        // setup button press listeners
        addListener(new MousePressedEventAdapter(
                new ButtonPressActionBoolean(this)));
        
        // update the state
        setEnabled(enabled);
        setVisible(true);
    }
    
    public synchronized void setEnabled(boolean enabled) {
        if (mouseClickListener == null) {
            mouseClickListener = new MouseClickedEventAdapter(buttonListener);
        }
        if (enabled) {
            discApp.setColor(1.0f, 1.0f, 1.0f, 0.95f);
            addListener(mouseClickListener);
            //setTransparency(1.0f);
        } else {
            discApp.setColor(0.0f, 0.0f, 0.0f, 0.0f);
            removeListener(mouseClickListener);
            //setTransparency(0.0f);
        }
        this.enabled = enabled;
    }
    
    public boolean isEnabled() {
        return enabled;
    }
    
    private class ButtonPressActionBoolean implements ActionBoolean {
        private Component3D target;
        private Vector3f baseTranslation;
        
        public ButtonPressActionBoolean(Component3D target) {
            this.target = target;
            this.baseTranslation = null;
        }
        
        public void performAction(LgEventSource source, boolean state) {
            if (enabled) {
                float newScale;
                Vector3f newTrans = null;
                if (state) {
                    if (baseTranslation == null) {
                        baseTranslation = new Vector3f();
                    }
                    baseTranslation = target.getFinalTranslation(baseTranslation);
                    newTrans = new Vector3f(baseTranslation.x,
                            baseTranslation.y,
                            baseTranslation.z - DEFAULT_PRESSEDDEPTH);
                } else {
                    if (baseTranslation == null) {
                        return;
                    }
                    newTrans = baseTranslation;
                }
                target.changeTranslation(newTrans, ANIMATION_DURATION);
            }
        }
        
    }
    
}
