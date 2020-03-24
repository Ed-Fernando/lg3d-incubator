/*
 * PlayListManagerSwingNode.java
 *
 * Created on 2007/06/03, 15:41
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.jdesktop.lg3d.apps.trumplayer.utils;

import org.jdesktop.lg3d.apps.trumplayer.utils.ExtendedDragComponent3D;
import org.jdesktop.lg3d.apps.trumplayer.base.Manager;

import org.jdesktop.lg3d.wg.SwingNode;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.utils.shape.Box;
import org.jdesktop.lg3d.utils.shape.Cylinder;
import org.jdesktop.lg3d.utils.shape.Cone;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.utils.c3danimation.NaturalMotionAnimation;

/**
 *
 * @author Yasuhiro Fujitsuki(thaniwa)
 */
public class PlayListManagerSwingNode extends ExtendedDragComponent3D {
    
    /** Creates a new instance of PlayListManagerSwingNode */
    public PlayListManagerSwingNode(Manager manager){
        super(manager);
        this.setRotationAxis(0.0f,1.0f,0.0f);
        initializeGUI();
        setAnimation(new NaturalMotionAnimation(1000));
    }

    private SwingNode mainSwingNode = new SwingNode();
    private SwingNode imageSwingNode = new SwingNode();
    private SwingNode m3uSwingNode = new SwingNode();

    private PlayListManagerPanel playListPanel;
    
    private void initializeGUI(){
        this.setDragAndDrop(true);

        // create mainSwingNode        
        playListPanel = new PlayListManagerPanel( this );
        mainSwingNode.setPanel(playListPanel);
        
        float localWidth = mainSwingNode.getLocalWidth();
        float localHeight = mainSwingNode.getLocalHeight();

        // Box box = new Box( 0.095f, 0.075f, 0.005f , new SimpleAppearance(1.0f,0.8f,0.8f,0.6f) );
        Box box = new Box( localWidth/2 + 0.005f, localHeight/2 + 0.005f, 0.005f , new SimpleAppearance(1.0f,0.8f,0.8f,0.6f) );
        Component3D boxComponent = new Component3D();
        boxComponent.addChild(box);
        boxComponent.setTranslation( 0.0f,0.0f, -0.008f );

        addChild(boxComponent);

        Component3D pencil = new Component3D();
        Component3D handle = new Component3D();
        Cylinder handleCylinder = new Cylinder(0.0010f,0.008f,
                new SimpleAppearance(0.6f,0.6f,0.6f,1.0f));
        handle.addChild(handleCylinder);
        handle.setRotationAxis(0.0f,0.0f,1.0f);
        handle.setRotationAngle(-1*(float)Math.PI/4.0f);
        handle.setTranslation((float)(0.001f * Math.sin(Math.PI/4.0f)),
                (float)(0.001f * Math.cos(Math.PI/4.0f)),0.0f);

        Component3D handle2 = new Component3D();
        Cone handleCone = new Cone(0.001f,0.003f,Cone.CAP,20,3,new SimpleAppearance(0.0f,0.0f,0.0f,1.0f));
        handle2.addChild(handleCone);
        handle2.setRotationAxis(0.0f,0.0f,1.0f);
        handle2.setRotationAngle((float)(Math.PI/2.0f+Math.PI/4.0f));
        handle2.setTranslation((float)(-0.004f * Math.sin(Math.PI/4.0f)),
                (float)(-0.004f * Math.cos(Math.PI/4.0f)),0.0f);

        pencil.addChild(handle);
        pencil.addChild(handle2);
        // pencil.setTranslation(- 0.09f, 0.068f, 0.005f);
        pencil.setTranslation(- localWidth/2, localHeight/2, 0.005f);
        addChild(pencil);

        
        // initializeExitButton(0.09f,0.068f,0.005f);
        initializeExitButton(localWidth/2,localHeight/2,0.005f);


        // create FileChooser SwingNodes
        imageSwingNode.setPanel(playListPanel.getImageChooserPanel());
        m3uSwingNode.setPanel(playListPanel.getM3UChooserPanel());
        
        mainSwingNode.setAnimation(new NaturalMotionAnimation(1000));
        imageSwingNode.setAnimation(new NaturalMotionAnimation(1000));
        m3uSwingNode.setAnimation(new NaturalMotionAnimation(1000));
        
        //mainSwingNode.setRotationAxis(1.0f,0.0f,0.0f);
        imageSwingNode.setTranslation(0.0f,0.018f,-0.01f);
        imageSwingNode.setScale(0.0f);
        m3uSwingNode.setTranslation(0.0f,0.018f,-0.01f);
        m3uSwingNode.setScale(0.0f);

        addChild(mainSwingNode);
        addChild(imageSwingNode);
        addChild(m3uSwingNode);

        
        // this.setDefaultTranslation(0.0f,0.0f,-0.005f);
        this.setDefaultTranslation(0.0f,0.0f,0.03f);
        this.changeDefaultTranslation();
        
    }

    public boolean updatedConfigFile(){ return playListPanel.updatedConfigFile(); }    
    
    private enum MODE { MAIN, M3U, IMAGE };
    private MODE currentMode = MODE.MAIN;

    public void switchToMainMode(){
        mainSwingNode.changeTranslation(0.0f,0.0f,0.0f);
        //mainSwingNode.changeRotationAngle(0.0f);

        if(currentMode == MODE.M3U){            
            m3uSwingNode.changeTranslation(0.0f,0.018f,-0.01f);
            m3uSwingNode.changeScale(0.0f);
        } else if(currentMode == MODE.IMAGE){
            imageSwingNode.changeTranslation(0.0f,0.018f,-0.01f);
            imageSwingNode.changeScale(0.0f);
        }

        currentMode = MODE.MAIN;
    }

    public void switchToM3UFileSelectMode(){
        mainSwingNode.changeTranslation(0.0f,0.0f,-0.006f);
        //mainSwingNode.changeRotationAngle((float)Math.PI * -0.02f);

        if( currentMode == MODE.MAIN ){
            m3uSwingNode.changeScale(1.0f);
            m3uSwingNode.changeTranslation(0.0f,0.018f,0.0f);
        }
        currentMode = MODE.M3U;
    }
    
    public void switchToImageSelectMode(){
        mainSwingNode.changeTranslation(0.0f,0.0f,-0.006f);
        //mainSwingNode.changeRotationAngle((float)Math.PI * -0.02f);

        if( currentMode == MODE.MAIN ){
            imageSwingNode.changeTranslation(0.0f,0.018f,0.0f);
            imageSwingNode.changeScale(1.0f);
        }
        currentMode = MODE.IMAGE;
    }
    
}
