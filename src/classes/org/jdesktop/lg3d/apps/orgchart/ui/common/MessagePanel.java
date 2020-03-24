/*
 * MessagePanel.java
 *
 * Created on May 25, 2005, 10:38 AM
 *
 */

package org.jdesktop.lg3d.apps.orgchart.ui.common;

import java.awt.Color;
import java.awt.Font;
import org.jdesktop.lg3d.scenemanager.utils.decoration.TextPanel;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Frame3D;
import org.jdesktop.lg3d.apps.orgchart.ui.contact.AnimatedPanel;

/**
 *
 * @author cc144453
 */
public class MessagePanel extends AnimatedPanel {
    
    private static final Font TEXT_FONT = new Font("Serif", Font.PLAIN, 36);
    private static final Color TEXT_COLOR = Color.BLACK;
    private static final float TEXT_HEIGHT = 0.005f;
    private static final Color BG_COLOR = new Color(0.0f, 0.0f, 0.0f, 0.0f);
    private static final float TEXT_MARGIN_X = 0.001f;
    
    private TextPanel textPanel;
    
    public MessagePanel(Frame3D parent, String text,
            float width, float height) {
        super(parent);
        createUI(text, width, height);
    }
    
    public void initBody(Component3D body, Object param, float width, float height) {
        textPanel = new TextPanel((String)param,
                1.0f, // widthScale,
                width - 2.0f * FramedPanel.DEFAULT_BORDERWIDTH, // maxWidth,
                TEXT_HEIGHT, // height
                1, // xShift
                1, // yShift
                false);
        body.addChild(textPanel);
        body.setTranslation(width * -0.5f + TEXT_MARGIN_X, 0.0f, 0.01f);
    }
    
    public void setText(String text) {
        textPanel.setText(text);
    }
    
}
