/*
 * LG3D Incubator Project - LgAmazon
 *
 * $RCSfile: SearchIndexComponent.java,v $
 *
 * Copyright (c) 2006, INoX Software Development Group, All Rights Reserved
 *
 * Redistributions in source code form must reproduce the above
 * copyright and this condition.
 *
 * The contents of this file are subject to the GNU General Public
 * License, Version 2 (the "License"); you may not use this file
 * except in compliance with the License. A copy of the License is
 * available at http://www.opensource.org/licenses/gpl-license.php.
 *
 * $Revision: 1.1 $
 * $Date: 2007-03-09 09:37:39 $
 * Author: E_INOUE
 */

package org.jdesktop.lg3d.apps.lgamazon.component;

import java.awt.image.BufferedImage;
import java.util.Vector;

import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.apps.lgamazon.util.ImageTexture;
import org.jdesktop.lg3d.utils.action.ActionFloat3;
import org.jdesktop.lg3d.utils.c3danimation.NaturalMotionAnimation;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.utils.shape.Box;
import org.jdesktop.lg3d.utils.shape.Primitive;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.wg.event.LgEventSource;
import org.jdesktop.lg3d.wg.event.MouseEvent3D;

public class SearchIndexComponent extends Container3D
implements ActionFloat3 {

    private Box box;
    private Component3D component;
    
    
    private Vector<IndexInfo> indexList = new Vector<IndexInfo>();
    private int indexListIndex = 0;
    
    
    public SearchIndexComponent() {
        
        box = new Box(   
                0.005f, 0.005f, 0.005f,     
                Primitive.GENERATE_NORMALS | Primitive.GENERATE_TEXTURE_COORDS | Primitive.GEOMETRY_NOT_SHARED, 
                null);

        box.setAppearance(createTransparencyAppearance());
        
        setPreferredSize(new Vector3f(0.01f, 0.01f, 0.01f));
        
        
        component = new Component3D();
        component.addChild(box);
        
        component.addListener(
                new MouseClickedEventAdapter(MouseEvent3D.ButtonId.BUTTON1, this));
        
        component.setAnimation(new NaturalMotionAnimation(1000));
        
        addChild(component);
    }
    
    
    public void addIndex(int boxIndex, String index, BufferedImage image) {
        
        
        SimpleAppearance appearance  = new SimpleAppearance(1.0f, 1.0f, 1.0f, 1.0f,
                SimpleAppearance.ENABLE_TEXTURE | SimpleAppearance.DISABLE_CULLING);
        appearance.setTexture(new ImageTexture(image));
        
        box.setAppearance(boxIndex, appearance);        
        
        IndexInfo info = new IndexInfo();
        
        info.index = index;
        
        switch (boxIndex) {
        
            case Box.TOP:
            case Box.BOTTOM:                           
                info.axis = new Vector3f(1.0f, 0.0f, 0.0f);                 
                break;
            
            case Box.RIGHT:
            case Box.LEFT:
            case Box.BACK:
                info.axis = new Vector3f(0.0f, 1.0f, 0.0f);                 
                break;
            
            case Box.FRONT:
                info.axis = new Vector3f(0.0f, 0.0f, 0.0f);
                break;
        }
        
        if (boxIndex == Box.FRONT) {
            info.angle = 0.0f;
        }
        else if (boxIndex == Box.BACK) {
            info.angle = (float) Math.PI;
        }
        else if (boxIndex == Box.TOP || boxIndex == Box.LEFT) {
            info.angle = (float) (Math.PI / 2.0f);
        }
        else if (boxIndex == Box.BOTTOM || boxIndex == Box.RIGHT) {
            info.angle = (float) (-Math.PI / 2.0f);
        }
        
        indexList.add(info);   
        
        changeAngle(0);
    }
    
    
    private SimpleAppearance createTransparencyAppearance() {
        
        SimpleAppearance appearance = new SimpleAppearance(1.0f, 1.0f, 1.0f, 0.0f,
                SimpleAppearance.ENABLE_TEXTURE | SimpleAppearance.DISABLE_CULLING);
    
        return appearance;
    }
    
    
//    private BufferedImage createImage(int width, int height) {
//        
//        return new BufferedImage(
//            ImageTexture.getTextureImageSize(width), ImageTexture.getTextureImageSize(height), 
//            BufferedImage.TYPE_INT_ARGB);        
//    }
    
    
    public void performAction(LgEventSource source, float x, float y, float z) {
        
        indexListIndex++;
        
        if (indexListIndex >= indexList.size()) {
            indexListIndex = 0;
        }
        
        changeAngle(indexListIndex);
    }
 
    
    public String getIndex() {
        return indexList.get(indexListIndex).index;
    }
    
    
    private void changeAngle(int index) {
        
        IndexInfo info = indexList.get(index);
        component.setRotationAxis(info.axis.x, info.axis.y, info.axis.z);        
        component.changeRotationAngle(info.angle);
    }
    
    
    class IndexInfo {
        
        Vector3f axis;
        float angle;
        String index;
    }
}
