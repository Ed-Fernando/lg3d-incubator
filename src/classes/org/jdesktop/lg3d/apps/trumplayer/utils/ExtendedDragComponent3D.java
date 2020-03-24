/*
 * ExtendedDragComponent3D.java
 *
 * Created on 2007/06/03, 17:01
 *
 * Differences between DragComponent3D and ExtendedDragComponent3D is
 * animation to execute changeVisible and setVisible.
 * DragComponent3D is default action which are prepared by SceanManager,
 * ExtendedDragComponent3D is original action using changeScale.
 * If you have to use setScale method to the object,
 * you should not use ExtendedDragComponent3D, please use DragComponent3D.
 */

package org.jdesktop.lg3d.apps.trumplayer.utils;

import org.jdesktop.lg3d.apps.trumplayer.base.Manager;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.sg.Texture;
import org.jdesktop.lg3d.sg.utils.image.TextureLoader;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.utils.shape.ImagePanel;
import org.jdesktop.lg3d.utils.action.ActionNoArg;
import org.jdesktop.lg3d.wg.event.LgEventSource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 *
 * @author Yasuhiro Fujitsuki(thaniwa)
 */
public class ExtendedDragComponent3D extends DragComponent3D {

    private Component3D exitButton = null;
    
    public ExtendedDragComponent3D(Manager manager){
        this.manager = manager;
        this.setRotationAxis(1.0f,0.0f,0.0f);
        init();
    }
    
    public void changeVisible(boolean b){
        if(b == true){
            this.changeScale(1.0f);
            this.changeRotationAngle(0.0f);
        }
        else {
            this.changeScale(0.0f);
            this.changeRotationAngle((float)Math.PI*2.0f);
            this.changeDefaultTranslation();
        }
    }
    
    public boolean isVisible(){
        boolean result = true;
        if(this.getFinalScale() == 0.0f)
            result = false;
        return result;
    }
    
    public void setVisible(boolean b){
        if(b == true){
            this.setScale(1.0f);
            this.changeRotationAngle(0.0f);
        }
        else {
            this.setScale(0.0f);
            this.changeRotationAngle((float)Math.PI*2.0f);
        }
    }

    public void initializeExitButton(float x, float y, float z){
        if(exitButton == null){
            // Window Close Button
            try{
                BufferedImage closeImage = ImageIO.read(ClassLoader.getSystemResourceAsStream("resources/images/button/window-close.png"));
                Texture closeTexture = new TextureLoader(closeImage).getTexture();
                ImagePanel closePanel = new ImagePanel(0.01f,0.01f);
                SimpleAppearance closeAppearance = new SimpleAppearance(1.0f,1.0f,0.8f,1.0f, SimpleAppearance.ENABLE_TEXTURE);
                closeAppearance.setTexture(closeTexture);
                closePanel.setAppearance(closeAppearance);

                Component3D closeComponent = new Component3D();
                closeComponent.addChild(closePanel);
                closeComponent.addListener(
                    new MouseClickedEventAdapter(
                        new ActionNoArg() {
                            public void performAction(LgEventSource source) {
                                changeVisible(false);
                            }
                    }));
                addChild(closeComponent);
                
                exitButton = closeComponent;
                exitButton.setTranslation(x,y,z);
                
            } catch (Exception e){}
        } else {
            exitButton.setTranslation(x,y,z);
        }
    }
    
}
