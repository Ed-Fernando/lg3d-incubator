/*
 * LG3D Incubator Project - gol3d
 * A simple Game of Life for Looking Glass 3D
 *
 * $RCSfile: GolConfig.java,v $
 *
 * The contents of this file are subject to the GNU General Public
 * License, Version 2 (the "License"); you may not use this file
 * except in compliance with the License. A copy of the License is
 * available at http://www.opensource.org/licenses/gpl-license.php.
 *
 * $Revision: 1.2 $
 * $Date: 2005-06-24 19:56:39 $
 * Author: Van der Haegen Mathieu (dwarfy)
 */

package org.jdesktop.lg3d.apps.gol3d;

import java.io.*;
import java.util.*;
import java.awt.Color;


public class GolConfig implements Serializable {
    
    public float cGroundR;
    public float cGroundG;
    public float cGroundB;
    public float cButtonR;
    public float cButtonG;
    public float cButtonB;
    public float cBarsR;
    public float cBarsG;
    public float cBarsB;
    public float cFontR;
    public float cFontG;
    public float cFontB;
    public float transparency;
    public int interGenTime; 
    
    /** No arg constructor */
    public GolConfig() {        
    }        
    
/*
    public GolConfig(float colGroundR,float colGroundG,float colGroundB,float colBarsR,float colBarsG,float colBarsB,float cButtonR,float cButtonG,float cButtonB) {
    } */
           
    // Create the Getters and the Setters
    
    public float getTransparency() {
        return transparency;
    }
    
     public void setTransparency(float x) {
        transparency = x;
    }
    
    public int getInterGenTime() {
        return interGenTime;
    }
    
     public void setInterGenTime(int x) {
        interGenTime = x;
    }
    
    public float getGroundColorR() {
        return cGroundR;
    }
    
    public void setGroundColorR(float f) {
        cGroundR = f;
    }
    
    public float getGroundColorG() {
        return cGroundG;
    }
    
    public void setGroundColorG(float f) {
        cGroundG = f;
    }
    public float getGroundColorB() {
        return cGroundB;
    }
    
    public void setGroundColorB(float f) {
        cGroundB = f;
    }
    
    public float getBarsColorR() {
        return cBarsR;
    }
    
    public void setBarsColorR(float f) {
        cBarsR = f;
    }
    
    public float getBarsColorG() {
        return cBarsG;
    }
    
    public void setBarsColorG(float f) {
        cBarsG = f;
    }
    
    public float getBarsColorB() {
        return cBarsB;
    }
    
    public void setBarsColorB(float f) {
        cBarsB = f;
    }
    
    public float getButtonColorR() {
        return cButtonR;
    }
    
    public void setButtonColorR(float f) {
        cButtonR = f;
    }

    public float getButtonColorG() {
        return cButtonG;
    }
    
    public void setButtonColorG(float f) {
        cButtonG = f;
    }

    public float getButtonColorB() {
        return cButtonB;
    }
    
    public void setButtonColorB(float f) {
        cButtonB = f;
    }
    public void setFontColorB(float f) {
        cFontB = f;
    }    
    public float getFontColorB() {
        return cFontB;
    }    
    public void setFontColorR(float f) {
        cFontR = f;
    }    
    public float getFontColorR() {
        return cFontR;
    }    
    public void setFontColorG(float f) {
        cFontG = f;
    }    
    public float getFontColorG() {
        return cFontG;
    }    
    
    
}
    
