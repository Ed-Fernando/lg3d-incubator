/**
 * Project Looking Glass
 *
 * $RCSfile: PingPong.java,v $
 *
 * Copyright (c) 2004, Johann Glaser, All Rights Reserved
 *
 * Redistributions in source code form must reproduce the above
 * copyright and this condition.
 *
 * The contents of this file are subject to the GNU General Public
 * License, Version 2 (the "License"); you may not use this file
 * except in compliance with the License. A copy of the License is
 * available at http://www.opensource.org/licenses/gpl-license.php.
 *
 * Taken from Lg3dHelp.java from Sun Microsystems Inc.
 *   Revision: 1.3
 *   Date: 2004/06/27 04:06:26
 *
 * This is a 3D ping pong demo application example
 *
 */

package org.jdesktop.lg3d.apps.pingpong;

import org.jdesktop.lg3d.sg.*;
import org.jdesktop.lg3d.wg.*;
import org.jdesktop.lg3d.wg.event.LgEventSource;
import org.jdesktop.lg3d.utils.action.*;
import org.jdesktop.lg3d.utils.actionadapter.*;
import org.jdesktop.lg3d.utils.eventadapter.*;
import org.jdesktop.lg3d.utils.eventaction.*;
import org.jdesktop.lg3d.utils.shape.*;
import org.jdesktop.lg3d.utils.component.*;
import org.jdesktop.lg3d.utils.prefs.LgPreferencesHelper;
import javax.vecmath.Vector3f;
import java.util.*;
import java.util.prefs.Preferences;

/**
 * The main PingPong class.
 */
public class PingPong extends Frame3D {
    private static float tableWidth;
    private static float tableHeight;
    private static float tableDepth;
    private static float netHeight;
    private static float ballDiameter;
    private static float markHeight;
    // REMINDER -- make the Appearances non-static so that multiple
    // instances of this class won't share it.  Since the current LG3D
    // runs apps on the same VM, those appearances will be shared.
    // This causes a visual issue when translucency effect is applied.
//    private /*static*/ Appearance tableApp
//            = new SimpleAppearance(0.6f, 1.0f, 0.6f, 1.0f, 
//	            SimpleAppearance.DISABLE_CULLING);
    private /*static*/ Appearance netApp
            = new SimpleAppearance(0.9f, 0.9f, 0.9f, 0.5f,
	            SimpleAppearance.DISABLE_CULLING);
    private /*static*/ Appearance ballApp 
            = new SimpleAppearance(1.0f, 1.0f, 0.0f, 0.5f);
    private /*static*/ Appearance markApp
            = new SimpleAppearance(1.0f, 0.0f, 0.0f, 0.5f);
    private static float buttonSize   = 0.005f;
    private static float buttonOnSize = buttonSize * 1.15f;
    private static SimpleAppearance closeButtonOffAppearance;
    private static SimpleAppearance closeButtonOnAppearance;
    
    static {
	closeButtonOffAppearance 
	        = new ButtonAppearance("resources/images/button/window-close.png", false);
	closeButtonOnAppearance
	        = new ButtonAppearance("resources/images/button/window-close.png", true);
    }
    
    public static void main(String[] args) {
	Frame3D app = new PingPong();
	app.changeEnabled(true);
	app.changeVisible(true);
    }
    
    public PingPong() {
	setName("LG3D PingPong");
	// set table size
	tableWidth   = Toolkit3D.getToolkit3D().getScreenHeight() * 0.5f;
	tableHeight  = tableWidth * 0.6f;
	tableDepth   = tableWidth * 0.01f;
	netHeight    = tableWidth * 0.025f;
	ballDiameter = tableWidth * 0.03f;
	markHeight   = tableWidth * 0.01f;
	setPreferredSize(new Vector3f(tableWidth, tableHeight, tableDepth));
	
	// create the container of the total application
	Container3D top = new Container3D();
	
	//////// create the table //////////////////////////////////////////
        Preferences prefs = LgPreferencesHelper.userNodeForPackage(getClass());
        // If failed to load the default initial preferences, the table becomes red.
        // If successful, it becomes green. 
        float tableAppR = prefs.getFloat("table-color-r", 1.0f);
        float tableAppG = prefs.getFloat("table-color-g", 0.0f);
        float tableAppB = prefs.getFloat("table-color-b", 0.0f);
        Appearance tableApp = new SimpleAppearance(tableAppR, tableAppG, tableAppB);
	GlassyPanel table = new GlassyPanel(
	tableWidth /* + decoWidth * 2*/,
	tableHeight /*+ decoWidth * 2*/, 
	tableDepth, tableDepth * 0.1f, tableApp);
	// a 3D component which holds the Shape3D descendant GlassyPanel
	Component3D tableComp = new Component3D();
	tableComp.addChild(table);
	top.addChild(tableComp); // add to application container
	
	//////// the net ///////////////////////////////////////////////////
	Box net = new Box(0.0003f, tableHeight*0.55f, netHeight, netApp);
	Component3D netComp = new Component3D();
	netComp.addChild(net);
	netComp.setTranslation(0.0f, 0.0f, netHeight/2.0f);
	top.addChild(netComp);
	
	//////// the ball //////////////////////////////////////////////////
	Sphere ballShape = new Sphere(ballDiameter/2.0f, ballApp);
	Component3D ball = new Component3D();
	ball.addChild(ballShape);
	top.addChild(ball);
	
	//////// the target mark ////////////////////////////////////////////
	Primitive markShape = new Cylinder(ballDiameter/2.0f, markHeight/2.0f, markApp);
	Component3D mark = new Component3D();
	mark.setRotationAxis(1.0f, 0.0f, 0.0f);
	mark.setRotationAngle((float)Math.toRadians(90));
	mark.addChild(markShape);
	top.addChild(mark);
	
	// add a timer with 10ms repetition rate to move the ball
	Timer timer = new Timer();
	timer.scheduleAtFixedRate(
	new PingPongAction(ball, mark, tableWidth, tableHeight, netHeight), 0, 10);
	
	// add a close button
	Component3D closeButton = new Button(buttonSize, closeButtonOffAppearance,
	buttonOnSize, closeButtonOnAppearance);
	closeButton.setCursor(Cursor3D.SMALL_CURSOR);
	closeButton.setTranslation(tableWidth * 0.5f, tableHeight * 0.5f, 0.001f);
	top.addChild(closeButton);
	
	//interactivity of the close button 
	closeButton.addListener(
            new MouseClickedEventAdapter(
            new ActionNoArg() {
                public void performAction(LgEventSource source) {
                    changeEnabled(false);
                }
            }));
            
	// rotate the table around the x axis that it looks like a pingpong table
	top.setRotationAxis(1.0f, 0.0f, 0.0f);
	top.setRotationAngle((float)Math.toRadians(-65));
	
	// add the top component to the Frame3D
	addChild(top);
	
	/* There are several problems with the current implementation
	*  - the velocity of the ball is not very natural because a fast ball
	*    is not replied with a fast one
	*  - when the SceneManager moves the window around itself it doesn't 
	*    consider that it was rotated via the X axis (the table is flat like
	*    a normal window afterwards). See how CDViewer.java does that.
	*  - PingPontAction should be called PingPontTask
	*  - one could use Box instead of GlassyPanel for the table
	*  - The ball and mark are not scaled with the total table. The table
	*    otoh is scaled by the screen (or window) size. The ball and mark
	*    should be scaled relative to the table size.
	*  - The ball position should be set +ballDiameter/2 in Z direction as
	*    it is now. Then it will really touch the table instead of dipping
	*    into it.
	*/
    }
    
    // the PingPong task
    private class PingPongAction extends TimerTask {
	private float       velocityX     = 0.1f;
	private float       velocityY     = 0.0f;
	private float       velocityZ     = -0.07f;
	private float       accelerationZ = 0.5f;
	private float       posX          = -0.03f;
	private float       posY          =  0.03f;
	private float       posZ          =  0.05f;
	private long        lastExecuted  = 0;
	private Random      rnd;
	// variables from outside
	private Component3D ball;
	private Component3D mark;
	private float       sizeX;  // absolute value, reaches from -sizeX to +sizeX
	private float       sizeY;  // absolute value, reaches from -sizeY to +sizeY
	private float       netHeight;
	
	// constructor
	PingPongAction(Component3D ballVar, Component3D markVar, float tableWidth, float tableHeight, float netHeightVar) {
	    super();
	    // save params to local variables
	    ball      = ballVar;
	    mark      = markVar;
	    sizeX     = tableWidth  / 2.0f;
	    sizeY     = tableHeight / 2.0f;
	    netHeight = netHeightVar;
	    // create a random number generator
	    rnd = new Random(System.currentTimeMillis());
	}
	
	private float MyRandom(float min, float max) {
	    return min + (max - min) * rnd.nextFloat();
	}
	
	// one player gets the ball and shoots it back to the other one
	private void Serve() {
	    // we choose the point where the ball should hit the table
	    float targetX = -1.0f * Math.signum(posX) * MyRandom(0.15f,1.0f) * sizeX;
	    float targetY = MyRandom(-1.0f,1.0f) * sizeY;
	    // additionally we choose the height of the ball above the net (measured from the table)
	    float height  = MyRandom(0.004f,0.04f) + Math.max(netHeight,posZ*targetX/(targetX-posX));
	    // There is a minimum value of Height. If it was below that the 
	    // result for velocityX would be imaginary.
	    // from the above values we can calculate the X, Y and Z velocity of the ball
	    velocityX = -(float)Math.sqrt(-(2f*height*posX+2f*posZ*targetX-2f*height*targetX)
	            *accelerationZ*posX*targetX*(posX-targetX))
	            /(2f*height*posX+2f*posZ*targetX-2f*height*targetX);
	    velocityY = (posY-targetY)*velocityX/(posX-targetX);
	    velocityZ = -0.5f*(2f*height*velocityX*velocityX-
	            2f*posZ*velocityX*velocityX+
	            accelerationZ*posX*posX)/
	            (velocityX*posX);
	    // set the mark to our target point
	    mark.setTranslation(targetX, targetY, markHeight/2.0f);
	    
	    /*System.out.println("Serve: posX = "+posX+" posY = "+posY+" posZ = "+posZ+
	    " targetX = "+targetX+" targetY = "+targetY+" Height = "+Height+ 
	    " velocityX = "+velocityX+" velocityY = "+velocityY+" velocityZ = "+velocityZ);*/
	}
	
	public void run() {
	    long Now = System.currentTimeMillis();
	    float deltaT = (Now - lastExecuted) * 0.001f;  // milliseconds to seconds
	    if (deltaT > 0.2f) {
		deltaT = 0.2f;
	    }
	    lastExecuted = Now;
	    if (lastExecuted == 0) {
		return;
	    }
	    posX += velocityX * deltaT;
	    posY += velocityY * deltaT;
	    velocityZ -= accelerationZ * deltaT;
	    posZ += velocityZ * deltaT;
	    if (posZ < 0.0f) {
		velocityZ *= -1.0f;
		posZ *= -1.0f;
		// System.out.println("table posX    = "+posX+" posY    = "+posY);
	    }
	    if ((posX >  sizeX) && (velocityX > 0)) { // player B gets the ball
		Serve();
	    }
	    if ((posX < -sizeX) && (velocityX < 0)) { // player A gets the ball
		Serve();
	    }
	    ball.setTranslation(posX, posY, posZ + ballDiameter/2.0f);
	    // System.out.println("PingPongAction.run: Now = "+Now+" deltaT = "+deltaT+
	    //   " velocityX = "+velocityX+" velocityY = "+velocityY+" velocityZ = "+velocityZ+
	    //   " X = "+posX+" Y = "+posY+" Z = "+posZ);
	}
    }
    
    private static class ButtonAppearance extends SimpleAppearance {
	private ButtonAppearance(String filename, boolean on) {
	    super(0.0f, 0.0f, 0.0f, 0.0f,
	            SimpleAppearance.DISABLE_CULLING
	            | SimpleAppearance.ENABLE_TEXTURE);
	    if (on) {
		setColor(1.0f, 0.6f, 0.6f, 0.8f);
	    } else {
		setColor(0.6f, 1.0f, 0.6f, 0.6f);
	    }
	    try {
		setTexture(getClass().getClassLoader().getResource(filename));
	    } catch (Exception e) {
		throw new RuntimeException("failed to initilaze window button: " + e);
	    }
	}
    }
    
    private class Button extends Component3D {
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
