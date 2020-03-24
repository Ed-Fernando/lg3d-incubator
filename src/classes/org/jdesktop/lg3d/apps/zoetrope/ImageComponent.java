/*
 * LG3D Incubator Project - Zoetrope
 *
 * $RCSfile$
 *
 * Copyright (c) 2004, Zoetrope Project Team, All Rights Reserved
 *
 * Redistributions in source code form must reproduce the above
 * copyright and this condition.
 *
 * The contents of this file are subject to the GNU General Public
 * License, Version 2 (the "License"); you may not use this file
 * except in compliance with the License. A copy of the License is
 * available at http://www.opensource.org/licenses/gpl-license.php.
 *
 * $Revision$
 * $Date$
 * Author: yuichi sakuraba
 */

package org.jdesktop.lg3d.apps.zoetrope;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.scenemanager.utils.decoration.TextPanel;
import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.sg.Shape3D;
import org.jdesktop.lg3d.sg.Texture2D;
import org.jdesktop.lg3d.utils.action.ActionNoArg;
import org.jdesktop.lg3d.utils.action.AppearanceChangeAction;
import org.jdesktop.lg3d.utils.action.ScaleActionBoolean;
import org.jdesktop.lg3d.utils.c3danimation.NaturalMotionAnimation;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MouseEnteredEventAdapter;
import org.jdesktop.lg3d.utils.shape.FuzzyEdgePanel;
import org.jdesktop.lg3d.utils.shape.GlassyPanel;
import org.jdesktop.lg3d.utils.shape.ImagePanel;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.wg.Cursor3D;
import org.jdesktop.lg3d.wg.Frame3D;
import org.jdesktop.lg3d.wg.event.LgEventSource;

public class ImageComponent {
    private static final float BODY_DEPTH = 0.008f;
    private static final float DECO_WIDTH = 0.005f;
    private static final float BUTTON_SIZE = 0.004f;
    private static final float BUTTON_ON_SIZE = BUTTON_SIZE * 1.15f;

    // avoid sharing bodyApp among multiple Zoetrope instances, since it
    // causes issue with the transparency effect the scene manager performs.
    private /*static*/ Appearance bodyApp = new SimpleAppearance(0.6f, 1.0f, 0.6f, 1.0f, 
                                                             SimpleAppearance.DISABLE_CULLING);
    private static Appearance CLOSE_BUTTON_ON_APPEARANCE;
    private static Appearance CLOSE_BUTTON_OFF_APPEARANCE;
    private static Appearance MINIMIZE_BUTTON_ON_APPEARANCE;
    private static Appearance MINIMIZE_BUTTON_OFF_APPEARANCE;
    
    private SimpleAppearance appearance;

    private Frame3D parent;
    private Container3D container;
    private Component3D body;
    private Component3D comp;
    private GlassyPanel bodyDeco;
    private Component3D closeButton;
    private Component3D minimizeButton;
    private SpineTitle leftSpineTitle;
    private SpineTitle rightSpineTitle;
    private FuzzyEdgePanel panel;

    private Component3D backComp = new Component3D();

    private float width;
    private float height;

    static {
        try {
            CLOSE_BUTTON_ON_APPEARANCE
                = new ButtonAppearance(
                    new URL("resource:///resources/images/button/window-close.png"),
                    new Color(1.0f, 0.6f, 0.6f, 0.8f));
            CLOSE_BUTTON_OFF_APPEARANCE
                = new ButtonAppearance(
                    new URL("resource:///resources/images/button/window-close.png"), 
                    new Color(0.6f, 1.0f, 0.6f, 0.6f));
            MINIMIZE_BUTTON_ON_APPEARANCE
                = new ButtonAppearance(
                    new URL("resource:///resources/images/button/window-minimize.png"),
                    new Color(1.0f, 0.6f, 0.6f, 0.8f));
            MINIMIZE_BUTTON_OFF_APPEARANCE
                = new ButtonAppearance(
                    new URL("resource:///resources/images/button/window-minimize.png"), 
                    new Color(0.6f, 1.0f, 0.6f, 0.6f));
        } catch(MalformedURLException mue) {
            mue.printStackTrace();
        }
    }
    
    public ImageComponent(float width, float height, Frame3D parent) {
        this.parent = parent;

        container = new Container3D();
        
        this.width = width;
        this.height = height;

        comp = createBase();
        container.addChild(comp);

        appearance = new SimpleAppearance(1.0f, 1.0f, 1.0f, 1.0f,
                                          SimpleAppearance.ENABLE_TEXTURE 
                                          | SimpleAppearance.DISABLE_CULLING);
        appearance.setCapability(Appearance.ALLOW_TEXTURE_WRITE);
        
        panel = new FuzzyEdgePanel(0.0f, 0.0f, 0.0f, appearance);

        panel.setAppearance(appearance);

        Component3D comp2 = new Component3D();
        comp2.addChild(panel);
        container.addChild(comp2);

        closeButton = createCloseButton();
        container.addChild(closeButton);

        minimizeButton = createMinimizeButton();
        container.addChild(minimizeButton);
        
        container.setPreferredSize(new Vector3f(width, height, 0.0f));
    }
    
    public void changeTexture(Texture2D texture, String title, float w, float h) {
        appearance.setTexture(texture);

        float w2 = w;
        float h2 = h;
        if (w > h) {
            if (w > width) {
                w2 = width;
                h2 = h * width / w;
            }
        } else {
            if (h > height) {
                h2 = height;
                w2 = w * height / h;
            }
        }

        panel.setSize(w2, h2);

        changeSpineTitle(title);
    }

    public Component3D getComponent() {
        return container;
    }

    public Component3D createBase() {
        Shape3D base = new GlassyPanel(width + DECO_WIDTH * 2,
                                   height + DECO_WIDTH * 2, 
                                   BODY_DEPTH, BODY_DEPTH * 0.1f, bodyApp);
        comp = new Component3D();
	comp.setAnimation(new NaturalMotionAnimation(200));
        comp.addChild(base);

        return comp;
    }

    private Component3D createCloseButton() {
        Component3D button = new Button(BUTTON_SIZE, CLOSE_BUTTON_OFF_APPEARANCE,
                                        BUTTON_ON_SIZE, CLOSE_BUTTON_ON_APPEARANCE);
        button.setCursor(Cursor3D.SMALL_CURSOR);
        button.setTranslation(width * 0.5f,
                              height * 0.5f, 0.0001f);

        button.addListener(
            new MouseClickedEventAdapter(
                new ActionNoArg() {
                    public void performAction(LgEventSource source) {
                        parent.changeEnabled(false);
                    }
                }));

        return button;
    }

    private Component3D createMinimizeButton() {
        Component3D button = new Button(BUTTON_SIZE, MINIMIZE_BUTTON_OFF_APPEARANCE,
                                        BUTTON_ON_SIZE, MINIMIZE_BUTTON_ON_APPEARANCE);
        button.setCursor(Cursor3D.SMALL_CURSOR);
        button.setTranslation(width * 0.5f - BUTTON_SIZE * 1.5f,
                              height * 0.5f, 0.0001f);

        button.addListener(
            new MouseClickedEventAdapter(
                new ActionNoArg() {
                    public void performAction(LgEventSource source) {
			parent.changeVisible(false);
                    }
                }));

        return button;
    }

    private void changeSpineTitle(String title) {
        if (leftSpineTitle != null) {
            container.removeChild(leftSpineTitle);
        }

        if (rightSpineTitle != null) {
            container.removeChild(rightSpineTitle);
        }

        leftSpineTitle = new SpineTitle(title, height, BODY_DEPTH);
        leftSpineTitle.setPickable(false);
        leftSpineTitle.setRotationAxis(0.0f, 1.0f, 0.0f);
        leftSpineTitle.setRotationAngle((float)Math.toRadians(90));
        leftSpineTitle.setTranslation(width * 0.5f + DECO_WIDTH, height * 0.5f, 0.0f);
        
        container.addChild(leftSpineTitle);

        rightSpineTitle = new SpineTitle(title, height, BODY_DEPTH);
        rightSpineTitle.setPickable(false);
        rightSpineTitle.setRotationAxis(0.0f, 1.0f, 0.0f);
        rightSpineTitle.setRotationAngle((float)Math.toRadians(-90));
        rightSpineTitle.setTranslation(-width * 0.5f - DECO_WIDTH, height * 0.5f, - BODY_DEPTH);
        
        container.addChild(rightSpineTitle);
    }

    class SpineTitle extends Component3D {
        private TextPanel panel;

        private SpineTitle(String title, float maxWidth, float height) {
            panel = new TextPanel(title, 1.8f, maxWidth, height, 1, -1, true);
            addChild(panel);
        }

        private void setWidth(float maxWidth) {
            panel.setWidth(maxWidth);
        }
    }

    static class ButtonAppearance extends SimpleAppearance {
        private ButtonAppearance(URL filename, Color color) {
            super(0.0f, 0.0f, 0.0f, 0.0f,
                SimpleAppearance.DISABLE_CULLING
                | SimpleAppearance.ENABLE_TEXTURE);

            setColor(color.getRed()/255f, color.getGreen()/255f, 
                     color.getBlue()/255f, color.getAlpha()/255f);

            try {
                setTexture(filename);
            } catch (FileNotFoundException e) {
                throw new IllegalArgumentException(
                    "failed to initilaze window button: " + e);
            } catch (IOException e) {
                throw new IllegalArgumentException(
                    "failed to initilaze window button: " + e);
            }
        }
    }

    class Button extends Component3D {
        private Button(float size, Appearance app) {
            this(size, app, size, app);
        }

        private Button(float sizeOff, Appearance appOff,
		       float sizeOn, Appearance appOn) {
            Shape3D shape = new ImagePanel(sizeOff, sizeOff);
            shape.setAppearance(appOff);
            addChild(shape);
            if (appOff != appOn) {
                addListener(
                    new MouseEnteredEventAdapter(
                        new AppearanceChangeAction(shape, appOn)));
            }
            if (sizeOff != sizeOn) {
                addListener(
                    new MouseEnteredEventAdapter(
                        new ScaleActionBoolean(this, sizeOn/sizeOff, 100)));
            }
        }
    }

}
