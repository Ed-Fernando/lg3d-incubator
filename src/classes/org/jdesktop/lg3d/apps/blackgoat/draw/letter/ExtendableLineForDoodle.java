/**
 * Project Looking Glass
 *
 * $RCSfile: ExtendableLineForDoodle.java,v $
 *
 * Copyright (c) 2004, Sun Microsystems, Inc., All Rights Reserved
 *
 * Redistributions in source code form must reproduce the above
 * copyright and this condition.
 *
 * The contents of this file are subject to the GNU General Public
 * License, Version 2 (the "License"); you may not use this file
 * except in compliance with the License. A copy of the License is
 * available at http://www.opensource.org/licenses/gpl-license.php.
 *
 * $Revision: 1.3 $
 * $Date: 2005-08-29 18:44:56 $
 * $State: Exp $
 */
package org.jdesktop.lg3d.apps.blackgoat.draw.letter;

import java.util.Enumeration;

import javax.vecmath.Point3f;

import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.sg.BranchGroup;
import org.jdesktop.lg3d.sg.ColoringAttributes;
import org.jdesktop.lg3d.sg.LineArray;
import org.jdesktop.lg3d.sg.LineAttributes;
import org.jdesktop.lg3d.sg.Shape3D;
import org.jdesktop.lg3d.sg.TransparencyAttributes;

/**
 * A shape which renders a line to which vertices can be added
 */
public class ExtendableLineForDoodle extends Shape3D implements Cloneable {
    private static final int LINE_SIZE = 50;
    private LineArray currentLine;
    private int currentSize = 0;
    private Appearance app;
    private ColoringAttributes colorAttr;
   
    /**
     * construct doodle line
     *
     */
    public ExtendableLineForDoodle() {
        newLine();

        setCapability( BranchGroup.ALLOW_DETACH );
        setCapability( Shape3D.ALLOW_GEOMETRY_WRITE );
        setCapability( Shape3D.ALLOW_GEOMETRY_READ );
        setCapability( ColoringAttributes.ALLOW_COLOR_WRITE);
        setCapability( ColoringAttributes.ALLOW_COLOR_READ);
        
//      logger.severe("New Extendable Line");
        
        app = new Appearance();
        LineAttributes lineAttr = new LineAttributes();
        lineAttr.setLineWidth(30f);
        app.setLineAttributes(lineAttr);
        colorAttr = new ColoringAttributes();
        
        colorAttr.setColor(0.4f,0.4f,1f);
        colorAttr.setCapability( ColoringAttributes.ALLOW_COLOR_WRITE);
        colorAttr.setCapability( ColoringAttributes.ALLOW_COLOR_READ);
        
        app.setColoringAttributes(colorAttr);
        TransparencyAttributes ta = new TransparencyAttributes(TransparencyAttributes.BLENDED, 0.6f);
        app.setTransparencyAttributes(ta);
        setAppearance(app);
    }

    /**
     * returns doodle color attribute
     * @return colorAttr
     */
    public ColoringAttributes getColorAttr(){
    	return this.colorAttr;
    }
    
    /**
     * changes doodle color attribute
     * @param r
     * @param g
     * @param b
     */
    public void changeColorAttr(float r, float g, float b){
    	this.colorAttr.setColor(r, g, b);
    }
    
    /**
     * change doodle color attribute
     * @param colorAttr
     */
    public void changeColorAttr(ColoringAttributes colorAttr){
    	this.colorAttr = colorAttr;
    }
    
    /**
     * adds vertex to draw doodle, goes to [code]public void addVertex(Point3f vertex) [/code]
     * @param x
     * @param y
     * @param z
     */
    public void addVertex(float x, float y, float z) {
    	this.addVertex(new Point3f(x, y, z));
    }
    
    /**
     * adds vertex to draw doodle.
     * @param vertex
     */
    public void addVertex(Point3f vertex) {
        if (currentSize==LINE_SIZE) newLine();
        
//      logger.severe("Add vertex "+vertex);
        currentLine.setCoordinate( currentSize, vertex);
        currentSize++;
        
        if (currentSize%2==0) {
            currentLine.setValidVertexCount(currentSize);
            
            if (currentSize==LINE_SIZE) newLine();
            currentLine.setCoordinate( currentSize, vertex);
            currentSize++;                
        }
    }

    /**
     * draws new line.
     *
     */
    private void newLine() {
   // 	System.out.println("New line ; " + aaa++);
        currentLine = new LineArray(LINE_SIZE, LineArray.COORDINATES);
        currentLine.setCapability( LineArray.ALLOW_COORDINATE_WRITE );
        currentLine.setCapability( LineArray.ALLOW_COUNT_WRITE );
        currentSize=0;
        currentLine.setValidVertexCount(currentSize);
        this.addGeometry(currentLine);
      //  System.out.println("gemos are " + this.numGeometries());
    }
    
    /**
     * removes all geometries
     */
    public void removeAllGeometries() {
        super.removeAllGeometries();
        newLine();
    }
    
    /**
     * TODO ????
     * @return
     */
    public ExtendableLineForDoodle getClone() {
    		System.out.println("gemos are " + this.numGeometries());
    		for( int i = 0; i < this.numGeometries(); i++ ) {
    			System.out.println("gemos are " + i + " is " + this.
    					getGeometry(i)); 
    		}
    		Enumeration aa = this.getAllGeometries();
    		ExtendableLineForDoodle temp = new ExtendableLineForDoodle();
    		while(aa.hasMoreElements()) {
    			temp.addGeometry((LineArray)aa.nextElement());
    		}
    		return temp;
    }
}
