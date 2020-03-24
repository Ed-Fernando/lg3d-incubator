
/**
 * 05-631 Software Architecture for User Interfaces
 * Carnegie Mellon University
 * Project 4 Final Project
 * @author Jack Wu
 * @date 12/1/04
 *
 * This program is a prototype 3D Aol Aim client 
 * for Sun's open source Project Looking Glass (lg3d).
 *
 * This program requires the following libraries:
 * JAIM
 * J3D
 * lg3d-core
 *
 *
 *
 */
package org.jdesktop.lg3d.apps.jw_aim3d;

import com.wilko.jaim.Buddy;
import com.wilko.jaim.BuddyUpdateTocResponse;
import com.wilko.jaim.ConfigTocResponse;
import com.wilko.jaim.ConnectionLostTocResponse;
import com.wilko.jaim.ErrorTocResponse;
import com.wilko.jaim.EvilTocResponse;
import com.wilko.jaim.GotoTocResponse;
import com.wilko.jaim.IMTocResponse;
import com.wilko.jaim.JaimConnection;
import com.wilko.jaim.JaimEvent;
import com.wilko.jaim.JaimEventListener;
import com.wilko.jaim.JaimException;
import com.wilko.jaim.LoginCompleteTocResponse;
import com.wilko.jaim.TocResponse;
import com.wilko.jaim.Utils;
import org.jdesktop.lg3d.wg.*;
import org.jdesktop.lg3d.wg.event.Frame3DToFrontEvent;
import org.jdesktop.lg3d.utils.actionadapter.*;
import org.jdesktop.lg3d.utils.eventadapter.*;
import org.jdesktop.lg3d.utils.eventaction.*;
import org.jdesktop.lg3d.utils.component.*;
import org.jdesktop.lg3d.utils.shape.*;

import org.jdesktop.lg3d.wg.event.LgEventSource;
import org.jdesktop.lg3d.utils.action.*;

//import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.media.j3d.*;
import javax.vecmath.*;
import java.io.*;
import java.util.*;

//Needed for input / output for console
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 * The main class for the Aim Client
 *
 * Frame3D is the base class for all the 3D application.  You can
 * instantiate and add children to it, or you can extend it.
 *
 */
public class Aim3d extends Frame3D implements JaimEventListener{
    private static int numDiscs = 10;
    private static final int numImages = 6;
    private static final float discSize = 0.05f;
    private static final float fanSizeMax = discSize * 2;
    private static final float fanSizeMin = discSize;
    private static final float frontRad = (float)(Math.PI * 3 / 2);
    private static final float stackSpacing = 0.00125f;
    private static final float bodyAngle = (float)Math.toRadians(-65);
    private static final float imWindowAngle = (float)Math.toRadians(-50);
    
    private String aimLogin = "smallsaui";
    private String aimPassword = "saui";

    private Container3D mainContainer;
    private Container3D top;
    private static Aim3d app;
    private CD currentCD;
    private JaimConnection c;

    private boolean focused = false;
    private boolean quit = false;
    private boolean currentCDActivity;
    private int imageCounter = 0;
    private int counter = 0;    

    //linked list of ongoing messages with buddies
    //keys are screen name string, values are CD's corresponding to the buddy
    private Hashtable currMessagesList;

    public static void main(String[] args) {
	BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	String oneLine;
	StringTokenizer str;
	String UserName="";
	String Password="";
	
	try
	    {
		System.out.println("Enter Username:");
		oneLine = in.readLine();
		if (oneLine== null)
		    return;
		UserName=oneLine;
		System.out.println("Enter Password:");
		oneLine = in.readLine();
		if (oneLine == null)
		    return;
		Password = oneLine;
	    }
	catch (IOException e)
	    {
		System.err.println("Unexpected IO error");
	    }

	app = new Aim3d(UserName, Password);
        
        // When the Frame3D object created, it is not live -- it is not
        // a part of the scenegraph.  The setActive() call actually adds
        // the given 3D app to the scenegraph and make it viewable. 
        // More precisely, the call initiates interaction with the 
        // SceneManager and the manager deals with the details of policy
        // for presenting the application (e.g, location, size, etc).
        app.setActive(true);
        
        // By default, a Frame3D object is invisible even after being
        // added to the scenegraph.  The following call makes it finally
        // visible to the user.  Note that setVisible() is fairly lightweight
        // compared to the setActive() call.
        app.setVisible(true);
    }
    
    /**
     * Constructs a aim3d application.
     */
    public Aim3d(String SN, String PW) {
	currMessagesList = new Hashtable();
	currentCD = null;
	aimLogin = SN;
	aimPassword = PW;

	setName("3d AIM Client");
        // Set the cursor for this application. Several predefined cursors
        // are provided.  See the Cursor3D class for details.  As for 
        // development of your own cursor, the implementation of the 
        // predefined cursors may be helpful.  It can be found under the
        // .../lg/utils/cursor directory.
        setCursor(Cursor3D.MOVE_CURSOR);
        
        // This application has the following graph:
        //
        //   this -> top -> mainContainer -*-> CD
        // 
        // The top container is introduced to deal with visual effects like
        // swinging motion.  Since the scenemanager is free to change the
        // public properties (e.g. location, rotation, scale) of "this"
        // object for management of this application, the top container is
        // introduced in order to avoid conflicts with the manager's use.
        top = new Container3D();
        addChild(top);
        
        // The mainContainer is where all the CD objects go in.
        // The mainContainer will arrange the CDs leveraging a LayoutManager3D
        // which will be explained later.
        mainContainer = new Container3D();
        top.addChild(mainContainer);
        
        // The mainContainer is responsible for positioning the set of CDs in 
        // a nicely angled way.
        mainContainer.setRotationAxis(1.0f, 0.0f, 0.0f);
        mainContainer.setRotationAngle(bodyAngle);
        mainContainer.setTranslation(0.0f, 0.0f, -fanSizeMax * 0.25f);
        
        // This is a utility eventaction class that implements behavior
        // to move a component by mouse.  The first argument is the component
        // that represents the tag area for moving.  The second argument is 
        // the root node where actual coordinate change should happen.
        // Since in this application the entire application visual is 
        // grabbable, we use the top object for both of the arguments.
        new ComponentMover(top, top);
        
        // The following lines achieves the wiggling motion when the 
        // application is dragged.  First we set the rotation axis, and
        // use ResilientRotateAction() to actually wiggle the app.
        // Multiple actionadapters are used to convert and adjust the
        // x and y coordinates MouseDraggedEventAdapter() generates into
        // a float value that can be used as input for the rotation action.
        top.setRotationAxis(0.0f, 1.0f, 0.0f);
        new MouseDraggedEventAdapter(top,
            new Float2Splitter(
                new FloatDiffer(
                    new FloatScaler(500f, (float)Math.toRadians(30),
                        new ResilientRotateAction(top, 1500))),
                null));
        
        // The following lines are to bring the application to the top
        // when it is clicked.  More precisely, a Frame3DToFrontEvent
        // is posted when the application (the components under the top
        // object) is clicked.  The Frame3DToFrontEvent is just a hint to 
        // the scenemanager.  A scenemanager may listen to the event and
        // perform the requested action following policies the manager
        // defines.
        new MouseClickedEventAdapter(top, 
            new GenericEventPostAction(Frame3DToFrontEvent.class, this));

	//Can do something when one of the CD's is clicked!!!
        new MouseClickedEventAdapter(top, 
            new ActionNoArg() {
                public void performAction(LgEventSource source) {
		    //System.out.println("Something clicked!\n");
                }
            });
        
        // The following implements the behavior to fan out the CDs when
        // mouse entered any part of the application.  It changes the 
        // "focused" instance variable based on mouse enter of exit,
        // then requests the mainContainer to re-layout the contents.
        new MouseEnteredEventAdapter(top,
            new ActionBoolean() {
                public void performAction(LgEventSource source, boolean state) {
                    focused = state;
                    mainContainer.doLayout();
                }
            });
               
        // Sets a layoutmanager for the mainContainer.  This also results in 
        // performing initial layout once.
        mainContainer.setLayout(new CDLayout());
        
        setSize(fanSizeMax * 1.6f, fanSizeMax * 1.6f, fanSizeMax * 1.6f);
        
        // Be sure to invoke setCapabilities.  This call makes sure that 
        // all the necessary capabilities for picking are set correctly for
        // the entire sub scenegraph which this frame3d object provides.
	setCapabilities();
	
        System.out.println("Signing on to AIM with account "+ aimLogin+" and password "+aimPassword);
	signOnAim(aimLogin,aimPassword);
    }

    //The imWindow, for each buddy.  
    //The imWindow keeps track of the last four messages in the message history.
    //In the future, this should be dynamic, and based on the number of lines available in the window.  This
    //requires the functionality of a sizable window, which is yet to be implemented.
    //Also at this time, there is no supported text output or input.  So for output, what is done is that
    //a string is painted onto an image buffer, and then this is applied as a texture to the appearance
    //component.  Once text output functionality is added to lg3d, this should be changed.
    //Also for text input, there is no support for input in lg3d.  The way that input is currently handled
    //is that there are five buttons, numbered 1 through 5.  each button corresponds to a predefined
    //IM message.
    //For each window, there is also a close window button in the upper right, and a tilt/transparency effect button
    //in the lower left.
    public class imWindow extends Frame3D
    {
	private final float bodyDepth = 0.005f;
	private final float decoWidth = 0.005f;
	private org.jdesktop.lg3d.sg.Appearance bodyApp;
	private float buttonSize = 0.005f;
	private float buttonOnSize = buttonSize * 1.15f;
	private SimpleAppearance closeButtonOffAppearance;
	private SimpleAppearance closeButtonOnAppearance;
	private SimpleAppearance tiltButtonOffAppearance;
	private SimpleAppearance tiltButtonOnAppearance;

	private SimpleAppearance oneButtonOffAppearance;
	private SimpleAppearance oneButtonOnAppearance;
	private SimpleAppearance twoButtonOffAppearance;
	private SimpleAppearance twoButtonOnAppearance;
	private SimpleAppearance threeButtonOffAppearance;
	private SimpleAppearance threeButtonOnAppearance;
	private SimpleAppearance fourButtonOffAppearance;
	private SimpleAppearance fourButtonOnAppearance;
	private SimpleAppearance fiveButtonOffAppearance;
	private SimpleAppearance fiveButtonOnAppearance;	
	
	private float imMessageButtonSize = 0.010f;
	private float imMessageButtonOnSize = imMessageButtonSize * 1.15f;

	private SimpleAppearance currentWindow;

	//Window size must be power of 2, for mip mapping
	private int windowWidth = 64;
	private int windowHeight = 64;
	private int textureScaleRatio = 4;

	private boolean tilted = false;
	private float currentWindowAlpha = 0.7f;

	//Message history.  4 is oldest, 1 is newest message
	String messageHistory1 = "";
	String messageHistory2 = "";
	String messageHistory3 = "";
	String messageHistory4 = "";	
	
	public imWindow(String screenName, String message)
	{
	    currentWindow = null;
	    int iType = 0;
	    bodyApp = new SimpleAppearance(0.6f, 1.0f, 0.6f, 1.0f, SimpleAppearance.DISABLE_CULLING | org.jdesktop.lg3d.sg.Shape3D.ALLOW_APPEARANCE_WRITE);
	    bodyApp.setCapability(SimpleAppearance.ALLOW_TRANSPARENCY_ATTRIBUTES_WRITE);

	    closeButtonOffAppearance = new ButtonAppearance("resources/images/jw_aim3d/button/window-close.png", false);
	    closeButtonOnAppearance = new ButtonAppearance("resources/images/jw_aim3d/button/window-close.png", true);

	    tiltButtonOffAppearance = new ButtonAppearance("resources/images/jw_aim3d/button/window-maximize.png", false);
	    tiltButtonOnAppearance = new ButtonAppearance("resources/images/jw_aim3d/button/window-maximize.png", true);

	    oneButtonOffAppearance = new ButtonAppearance("resources/images/jw_aim3d/button/1.png", false);
	    oneButtonOnAppearance = new ButtonAppearance("resources/images/jw_aim3d/button/1.png", true);
	    twoButtonOffAppearance = new ButtonAppearance("resources/images/jw_aim3d/button/2.png", false);
	    twoButtonOnAppearance = new ButtonAppearance("resources/images/jw_aim3d/button/2.png", true);
	    threeButtonOffAppearance = new ButtonAppearance("resources/images/jw_aim3d/button/3.png", false);
	    threeButtonOnAppearance = new ButtonAppearance("resources/images/jw_aim3d/button/3.png", true);
	    fourButtonOffAppearance = new ButtonAppearance("resources/images/jw_aim3d/button/4.png", false);
	    fourButtonOnAppearance = new ButtonAppearance("resources/images/jw_aim3d/button/4.png", true);
	    fiveButtonOffAppearance = new ButtonAppearance("resources/images/jw_aim3d/button/5.png", false);
	    fiveButtonOnAppearance = new ButtonAppearance("resources/images/jw_aim3d/button/5.png", true);

	    setName("IM Window");
        
	    float height = getScreenHeight() * 0.5f;
	    float width = height;
	    setSize(width, height, bodyDepth);
	    
	    Container3D top = new Container3D();
        
	    GlassyPanel bodyDeco
		= new GlassyPanel(width + decoWidth * 2, height + decoWidth * 2, bodyDepth, bodyDepth * 0.1f, bodyApp);

	    currentWindow = new SimpleAppearance(1.0f, 1.0f, 1.0f, 0.7f,SimpleAppearance.ENABLE_TEXTURE | SimpleAppearance.DISABLE_CULLING);
	    currentWindowAlpha = 0.7f;
	    currentWindow.setCapability(SimpleAppearance.ALLOW_TEXTURE_WRITE);
	    currentWindow.setCapability(SimpleAppearance.ALLOW_TRANSPARENCY_ATTRIBUTES_WRITE);
	    draw(screenName, message, "");

	    org.jdesktop.lg3d.sg.TransparencyAttributes at = currentWindow.getTransparencyAttributes();
        
	    new MouseEnteredEventAdapter(this,new ColorAlphaChangeAction(this, at, 0.7f, 1.0f, 500));
                    
	    new MouseEnteredEventAdapter(this,new ActionBoolean() {
                    public void performAction(LgEventSource source, 
                        boolean flag) 
                    {
                        requestHighlight(flag);
                    }
                });

	    FuzzyEdgePanel body = new FuzzyEdgePanel(width, height*0.75f, decoWidth * 0.5f, currentWindow);

	    Component3D comp = new Component3D();
	    comp.addChild(body);
	    comp.addChild(bodyDeco);
	    top.addChild(comp);

	    
	    Component3D closeButton = new Button(buttonSize, closeButtonOffAppearance,buttonOnSize, closeButtonOnAppearance);
	    closeButton.setCursor(Cursor3D.SMALL_CURSOR);
	    closeButton.setTranslation(width * 0.5f, height*0.5f, 0.001f);
	    new MouseClickedEventAdapter(closeButton, new ActionNoArg() {
                public void performAction(LgEventSource source) {
                    changeActive(false);
		    currentCDActivity = false;
                }
		});
	    top.addChild(closeButton);
	    
	    final String SN = screenName;

	    //The imSend buttons are at the bottom of the window.
	    //The center of the window is: width = 0
	    //The botom of the window is -height * 0.5f
	    Component3D imButton1 = new Button(imMessageButtonSize, oneButtonOffAppearance, 
					       imMessageButtonOnSize, oneButtonOnAppearance);
	    imButton1.setCursor(Cursor3D.SMALL_CURSOR);
	    imButton1.setTranslation(-width*0.5f+width*0.1f, -height * 0.5f+height*0.03f, 0.001f);
	    new MouseClickedEventAdapter(imButton1, new ActionNoArg() {
                public void performAction(LgEventSource source) {
                    try {
			c.sendIM(SN,"Hello "+SN,false);
			redraw(SN, "", "Hello "+SN);
		    }
		    catch (IOException e) {
		    }
                }
		});
	    top.addChild(imButton1);
	    
	    Component3D imButton2 = new Button(imMessageButtonSize, twoButtonOffAppearance, 
					       imMessageButtonOnSize, twoButtonOnAppearance);
	    imButton2.setCursor(Cursor3D.SMALL_CURSOR);
	    imButton2.setTranslation(-width*0.5f+width*0.3f, -height * 0.5f+height*0.03f, 0.001f);
	    new MouseClickedEventAdapter(imButton2, new ActionNoArg() {
                public void performAction(LgEventSource source) {
                    try {
			c.sendIM(SN,"Whats up?",false);         
			redraw(SN,"", "Whats up?");
		    }
		    catch (IOException e) {
		    }
                }
		});
	    top.addChild(imButton2);

	    Component3D imButton3 = new Button(imMessageButtonSize, threeButtonOffAppearance, 
					       imMessageButtonOnSize, threeButtonOnAppearance);
	    imButton3.setCursor(Cursor3D.SMALL_CURSOR);
	    imButton3.setTranslation(0, -height * 0.5f+height*0.03f, 0.001f);
	    new MouseClickedEventAdapter(imButton3, new ActionNoArg() {
                public void performAction(LgEventSource source) {
                    try {
			c.sendIM(SN,"This is my SAUI 3D Aim client demo. isn't it so cool?!?",false);
			redraw(SN,"","This is my SAUI 3D Aim client demo. isn't it so cool?!?");            
		    }
		    catch (IOException e) {
		    }
                }
		});
	    top.addChild(imButton3);

	    Component3D imButton4 = new Button(imMessageButtonSize, fourButtonOffAppearance, 
					       imMessageButtonOnSize, fourButtonOnAppearance);
	    imButton4.setCursor(Cursor3D.SMALL_CURSOR);
	    imButton4.setTranslation(width*0.5f-width*0.3f, -height * 0.5f+height*0.03f, 0.001f);
	    new MouseClickedEventAdapter(imButton4, new ActionNoArg() {
                public void performAction(LgEventSource source) {
                    try {
			c.sendIM(SN,"Check it out! I'm in 3D!!!!",false);
			redraw(SN,"","Check it out! I'm in 3D!!!!");            
		    }
		    catch (IOException e) {
		    }
                }
		});
	    top.addChild(imButton4);

	    Component3D imButton5 = new Button(imMessageButtonSize, fiveButtonOffAppearance, 
					       imMessageButtonOnSize, fiveButtonOnAppearance);
	    imButton5.setCursor(Cursor3D.SMALL_CURSOR);
	    imButton5.setTranslation(width*0.5f-width*0.1f, -height * 0.5f+height*0.03f, 0.001f);
	    new MouseClickedEventAdapter(imButton5, new ActionNoArg() {
                public void performAction(LgEventSource source) {
                    try {
			c.sendIM(SN,"Bye Bye "+SN+". ttyl",false);
			redraw(SN,"","Bye Bye "+SN+". ttyl");        
		    }
		    catch (IOException e) {
		    }
                }
		});
	    top.addChild(imButton5);
	    
	    
	    top.setRotationAxis(0.0f, 1.0f, 0.0f);
	    setCursor(Cursor3D.MOVE_CURSOR);
	    new ComponentMover(this);
	    
	    setRotationAxis(0.0f, 1.0f, 0.0f);
	    new MouseDraggedEventAdapter(this, new Float2Splitter(new FloatDiffer(new FloatScaler(500f, (float)Math.toRadians(30),
                            new ResilientRotateAction(top, 1500))),null));
        
	    new MouseClickedEventAdapter(this, new GenericEventPostAction(Frame3DToFrontEvent.class, this));

	    org.jdesktop.lg3d.wg.event.LgEvent lg = new org.jdesktop.lg3d.wg.event.LgEvent();
	    

	    //3d Button tilts the pane and increases size of window width, for a Star Wars Credits look.
	    Component3D tiltButton = new Button(buttonSize, tiltButtonOffAppearance,buttonOnSize, tiltButtonOnAppearance);
	    tiltButton.setCursor(Cursor3D.SMALL_CURSOR);
	    tiltButton.setTranslation(-width * 0.5f, -height*0.5f, 0.001f);
	    new MouseClickedEventAdapter(tiltButton, new ActionNoArg() {
                public void performAction(LgEventSource source) {
		    if (tilted == false)
		    {
			setRotationAngle(imWindowAngle);
			setRotationAxis(1.0f, 0.0f, 0.0f);
			tilted = true;

			org.jdesktop.lg3d.sg.TransparencyAttributes trans = new org.jdesktop.lg3d.sg.TransparencyAttributes(org.jdesktop.lg3d.sg.TransparencyAttributes.NICEST, 1.0f);
			org.jdesktop.lg3d.sg.TransparencyAttributes trans2 = new org.jdesktop.lg3d.sg.TransparencyAttributes(org.jdesktop.lg3d.sg.TransparencyAttributes.NICEST, 0.6f);
			bodyApp.setTransparencyAttributes(trans);
			currentWindow.setTransparencyAttributes(trans2);
			//currentWindow.setAlpha(0.1f);
			currentWindowAlpha = 0.1f;
		    }
		    else
		    {
		        setRotationAngle(0);
			setRotationAxis(0.0f, 1.0f, 0.0f);   
			tilted = false;
			org.jdesktop.lg3d.sg.TransparencyAttributes trans = new org.jdesktop.lg3d.sg.TransparencyAttributes(org.jdesktop.lg3d.sg.TransparencyAttributes.NICEST, 0.1f);
			org.jdesktop.lg3d.sg.TransparencyAttributes trans2 = new org.jdesktop.lg3d.sg.TransparencyAttributes(org.jdesktop.lg3d.sg.TransparencyAttributes.NICEST, 0.1f);
			bodyApp.setTransparencyAttributes(trans);
			currentWindow.setTransparencyAttributes(trans2);
			currentWindowAlpha = 0.7f;
		    }
		}
		});
	    top.addChild(tiltButton);
	    addChild(top);
        
	    setCapabilities();
	}
	
	//Implement my own text box / text output field
	//Can fit 23 characters wide, and 15 lines high
	//In AIM, either message or sendMessage is "".  You can't receive and send at the same time!!!
	public void draw(String screenName, String message, String sendMessage)
	{
	    int beg = 0;
	    int end = 0;
	    org.jdesktop.lg3d.sg.Texture2D tex = new org.jdesktop.lg3d.sg.Texture2D(org.jdesktop.lg3d.sg.Texture2D.BASE_LEVEL, org.jdesktop.lg3d.sg.Texture2D.RGB, windowWidth, windowHeight);
	    tex.setCapability(SimpleAppearance.ALLOW_TRANSPARENCY_ATTRIBUTES_WRITE);
	    boolean byRef = true;

	    //The following code will take the string input and make it into a texture, and paint it onto the frame
	    BufferedImage textOutput=new BufferedImage(windowWidth*textureScaleRatio, 
						       windowHeight*textureScaleRatio, BufferedImage.TYPE_BYTE_INDEXED);
	    Graphics2D g = (Graphics2D)textOutput.getGraphics();	    
	    Font strFont = new Font("SansSerif", Font.PLAIN, 18);
	    g.setFont(strFont);

	    messageHistory4 = messageHistory3;
	    messageHistory3 = messageHistory2;
	    messageHistory2 = messageHistory1;

	    if (message.equals(""))
		messageHistory1 = "me: "+sendMessage;
	    else
		messageHistory1 = screenName+": "+message;

	    int lineCounter = 1;
	    for (int i = 1; i < 15; i++)
	    {
		if (messageHistory4.length() > 23*(i-1))
		{
		    beg = 23*(i-1);
		    end = 23*(i);
		    if (end > messageHistory4.length())
			end = messageHistory4.length();
		    lineCounter++;
		    g.drawString(messageHistory4.substring(beg,end), 0, g.getFontMetrics().getAscent()*(lineCounter));
		}
		else
		    break;
	    }
	    for (int i = 1; i < 15; i++)
	    {
		if (messageHistory3.length() > 23*(i-1))
		{
		    beg = 23*(i-1);
		    end = 23*(i);
		    if (end > messageHistory3.length())
			end = messageHistory3.length();
		    lineCounter++;
		    g.drawString(messageHistory3.substring(beg,end), 0, g.getFontMetrics().getAscent()*(lineCounter));
		}
		else
		    break;
	    }
	    for (int i = 1; i < 15; i++)
	    {
		if (messageHistory2.length() > 23*(i-1))
		{
		    beg = 23*(i-1);
		    end = 23*(i);
		    if (end > messageHistory2.length())
			end = messageHistory2.length();
		    lineCounter++;
		    g.drawString(messageHistory2.substring(beg,end), 0, g.getFontMetrics().getAscent()*(lineCounter));
		}
		else
		    break;
	    }
	    for (int i = 1; i < 15; i++)
	    {
		if (messageHistory1.length() > 23*(i-1))
		{
		    beg = 23*(i-1);
		    end = 23*(i);
		    if (end > messageHistory1.length())
			end = messageHistory1.length();
		    lineCounter++;
		    g.drawString(messageHistory1.substring(beg,end), 0, g.getFontMetrics().getAscent()*(lineCounter));
		}
		else
		    break;
	    }


	    //The is the message to be sent
	    //Need to wrap the message if it is > 23char
	    String sendOutStr = "To "+screenName+": "+sendMessage;

	    for (int i = 1; i < 5; i++)
	    {
		if (sendOutStr.length() > 23*(i-1))
		{
		    beg = 23*(i-1);
		    end = 23*i;
		    if (end > sendOutStr.length())
			end = sendOutStr.length();
		    g.drawString(sendOutStr.substring(beg, end), 0, g.getFontMetrics().getAscent()*(11+i));
		    //System.out.println("printing this shit: "+sendOutStr.substring(beg,end)+"  b:"+beg+"  e:"+end);
		}
		else
		    break;
	    }

	    org.jdesktop.lg3d.sg.ImageComponent2D imgcmp = new org.jdesktop.lg3d.sg.ImageComponent2D(org.jdesktop.lg3d.sg.ImageComponent2D.FORMAT_RGB, textOutput, byRef, false);
	    imgcmp.setCapability(SimpleAppearance.ALLOW_TRANSPARENCY_ATTRIBUTES_WRITE);
	    tex.setImage(0, imgcmp);

	    try 
		{
		currentWindow.setTexture(tex);
	    } catch (Exception e) 
		{
		throw new RuntimeException(e);
	    }
	}

	public void redraw(String screenName, String message, String sendMessage)
	{
	    draw(screenName, message, sendMessage);
      	}

	private class ButtonAppearance extends SimpleAppearance {
	    private ButtonAppearance(String filename, boolean on)
	    {
		super(0.0f, 0.0f, 0.0f, 0.0f,SimpleAppearance.DISABLE_CULLING | SimpleAppearance.ENABLE_TEXTURE);

		if (on)
		    setColor(1.0f, 0.6f, 0.6f, 0.8f);
		else
		    setColor(0.6f, 1.0f, 0.6f, 0.6f);

		try {
		    setTexture(filename);
		} catch (Exception e) {
		    throw new RuntimeException("failed to initilaze window button: " + e);
		}
	    }
	}

	private class Button extends Component3D {
	    private Button(float size, org.jdesktop.lg3d.sg.Appearance app) {
		this(size, app, size, app);
	    }

	    private Button(float sizeOff, org.jdesktop.lg3d.sg.Appearance appOff,float sizeOn, org.jdesktop.lg3d.sg.Appearance appOn)
	    {
		org.jdesktop.lg3d.sg.Shape3D shape = new ImagePanel(sizeOff, sizeOff);
		shape.setAppearance(appOff);
		addChild(shape);
		if (appOff != appOn)
		    new MouseEnteredEventAdapter(this, new AppearanceChangeAction(shape, appOn));
		if (sizeOff != sizeOn)
		    new MouseEnteredEventAdapter(this, new ScaleAction(this, sizeOn/sizeOff, 100));
	    }
	}
    }

    public class imMessage extends Frame3D
    {
	private final float bodyDepth = 0.005f;

	public imMessage(String screenName, String message)
	{
	    Font strFont = new Font("SansSerif", Font.PLAIN, 18);
	    Font3D theFont = new Font3D(strFont, new FontExtrusion());

	    Text3D theMessage = new Text3D(theFont, message, new Point3f(30, 60, 90));
	    
	    setName("IM 3D Message");
        
	    float height = getScreenHeight() * 0.5f;
	    float width = height;
	    setSize(width, height, bodyDepth);
	    
	    org.jdesktop.lg3d.wg.Container3D top = new org.jdesktop.lg3d.wg.Container3D();
	    
	    top.setRotationAxis(0.0f, 1.0f, 0.0f);
	    setCursor(Cursor3D.MOVE_CURSOR);
	    new ComponentMover(this);
	    
	    setRotationAxis(0.0f, 1.0f, 0.0f);
	    new MouseDraggedEventAdapter(this, new Float2Splitter(new FloatDiffer(new FloatScaler(500f, (float)Math.toRadians(30),
                            new ResilientRotateAction(top, 1500))),null));
        
	    new MouseClickedEventAdapter(this, new GenericEventPostAction(Frame3DToFrontEvent.class, this));

	    addChild(top);
        
	    setCapabilities();
	}
    }

    /**
     * Rearrange the order of CD objects in the mainContainer so that
     * the given CD comes to the front.  See the comments in the
     * CDLayout class.
     */
    private void rearrangeCDs(CD selectedCD) {
        int idx = mainContainer.indexOfChild(selectedCD);
        int num = mainContainer.numChildren();

        if (idx > num / 2) {
            for (int i = idx; i <= num - 1; i++) {
                org.jdesktop.lg3d.sg.Node cd = mainContainer.getChild(num - 1);
                mainContainer.removeChild(num - 1);
                mainContainer.insertChild(cd, 0);
            }
        } else {
            for (int i = idx; i > 0; i--) {
                org.jdesktop.lg3d.sg.Node cd = mainContainer.getChild(0);
                mainContainer.removeChild(0);
                mainContainer.addChild(cd);
            }
        }

	//System.out.println("Buddy " + selectedCD.screenName +" selected");

	if (currentCD != selectedCD)
	    {
		//The following allows only the current window to be displayed, rather than multiple chat windows
		/*
		for(int i = 0; i < mainContainer.numChildren(); i++)
		    {
			closeChatWindowWith((CD)(mainContainer.getChild(i)));
		    }
		*/

		bringUpChatWindowWith(selectedCD);
		currentCD = selectedCD;
		currentCDActivity = true;
	    }
	/*else if (currentCDActivity == false)
	    {
		//bringBackChatWindowWith(selectedCD);
		closeChatWindowWith(selectedCD);
		bringUpChatWindowWith(selectedCD);
		}*/
	else
	    {
		//selectedCD.chatWindow = null;
		//bringUpChatWindowWith(selectedCD);
		redrawWindow(selectedCD);
	    }
    }
    
    /**
     * The CDInner class is a helper class to identify a selected CD from
     * mouse click event via the MouseClickedLeafComp3DEventAdapter.
     * As for NaturalMotionComponent3D, see the comments for the CD class.
     */
    private class CDInner extends NaturalMotionComponent3D {
        private CD cdOuter;
        
        private CDInner(CD cdOuter) {
            this.cdOuter = cdOuter;
        }
        
        private CD getCD() {
            return cdOuter;
        }
    }

    /**
     * This class defines a CD's visual and part of the behavior.
     * It extends NaturalMotionContainer3D, which is a utility class
     * that implements smooth transition when changeTranslate/RotationAngle/
     * and Scale methods are invoked.  Together with usage of
     * NaturalMotionComponent3D for the CDInner class, it his simplifies to
     * achieve smooth motion of CDs in this application.
     */
    public class CD extends NaturalMotionContainer3D {
        Component3D inner = new CDInner(this);
	public String screenName = "";
	public String message = "";
	public imWindow chatWindow;
	public imMessage chatMessage;
	public String sendMessage = "";
        
        private CD(String filename) {
	    //For the construction, these are both empty strings
	    chatWindow = new imWindow(screenName,message);
	    chatMessage = new imMessage(screenName, message);

            // SimpleAppearance class is a utility class to simplify
            // appearance configuration.  For CD class, we need to enable
            // texture, and we disable culling so that CD won't disappear
            // when it showed the backside.  The right way to deal with such
            // a situation is to provide a proper backside (a shiny CD 
            // surface), but for our use, disabling culling serves well since
            // there is not many occasion to see the backside in normal use
            // of the demo.
            SimpleAppearance app
                = new SimpleAppearance(
                    1.3f, 1.3f, 1.3f, 1.3f,
                    SimpleAppearance.ENABLE_TEXTURE
                    | SimpleAppearance.DISABLE_CULLING
                    );
            try {
                // SimpleAppearance also supports a simplified way of
                // loading texture.
                app.setTexture(filename);
            } catch (Exception e) {
                e.printStackTrace();
            }

            inner.addChild(new Disc(discSize, 42, app));
	    
            inner.setTranslation(0.0f, discSize, 0.0f);
            inner.setRotationAxis(-1.3f, 0.0f, 0.0f);   

            addChild(inner);

            // The following lines are to identify a CD the user clicked up,
            // and to bring the CD in front of the user.
            new MouseClickedEventAdapter(this, 
                new ActionNoArg() {
                    public void performAction(LgEventSource source) {
                        rearrangeCDs((CD)source);
                    }
                });
	    //Used in branch 0-6-1
            //setMouseEventPropagatable(true);
        }
        
        /**
         * Moves this CD to the upright position if the argument is true.
         */
        private void raise(boolean raised) {
            if (raised) {
                inner.changeRotationAngle(bodyAngle);
                inner.changeTranslation(0.0f, discSize * 0.8f, discSize * 0.4f);
                inner.changeScale(1.2f);
            } else {
                inner.changeRotationAngle(0.0f);
                inner.changeTranslation(0.0f, discSize, 0.0f);
                inner.changeScale(1.0f);
            }
        }
    }
    
    /**
     * A layout manager class that positions the list of CDs in
     * a circular manner starting from the front and going counter clockwise.
     */
    private class CDLayout implements LayoutManager3D {
        private Container3D cont;
        
        public void setContainer(Container3D cont) {
            this.cont = cont;
        }
        
        public void layoutContainer() {
            int numDiscs = cont.numChildren();
            float radSeg = (float)(Math.PI / numDiscs);
            int dir = 1;
            float z = 0.0f;
            float fanSize = (focused)?(fanSizeMax):(fanSizeMin);

            for (int i = numDiscs - 1; i >= 0; i--) {
                Component3D comp = (Component3D)cont.getChild(i);
                float r = frontRad + (float)Math.PI * 2 / numDiscs * i;
                float x = fanSize * (float)Math.cos(r);
                float y = fanSize * (float)Math.sin(r);
                comp.changeTranslation(x, y, z);
                
                float prevAngle = comp.getRotationAngle();
                float newAngle = r - frontRad;
                float diff = newAngle - prevAngle;
                if (diff > Math.PI) {
                    comp.setRotationAngle(prevAngle + (float)Math.PI * 2.0f);
                } else if (diff < -Math.PI) {
                    comp.setRotationAngle(prevAngle - (float)Math.PI * 2.0f);
                }
                
                comp.changeRotationAngle(r - frontRad);
                if (i > numDiscs / 2) {
                    z -= stackSpacing;
                } else if (i == numDiscs / 2) {
                    z += stackSpacing * 0.5f;
                } else {
                    z += stackSpacing;
                }
                ((CD)comp).raise((i == 0) && focused);
            }
        }
        
        public void addLayoutComponent(Component3D comp, Object constraints) {
            comp.setRotationAxis(0.0f, 0.0f, 1.0f);
        }
        
        public void removeLayoutComponent(Component3D comp) {
        }
        
        public boolean rearrangeLayoutComponent(Component3D comp, Object obj) {
            return false;
        }
    }



    //***************JAIM methods********************

        public void signOnAim(String username, String password) {

        try {
            c=new JaimConnection("toc.oscar.aol.com",9898);
            c.setDebug(true);               // Send debugging to standard output
            c.connect();
            
            c.addEventListener(this);
            c.watchBuddy("unknownbuddy1212");         // Must watch at least one buddy or you will not appear on buddy listings
            
            c.logIn(username,password,50000);
            c.addBlock("");     // Set Deny None
            
            c.setInfo("This buddy is using <a href=\"http://jaimlib.sourceforge.net\">Jaim</a>.");
            
            
            c.setIdle(60);      // Pretend we have been idle for a minute
            c.setAway("I am away right now");

	                
            try {
                Thread.sleep(1000);       //Wait for 10 second
            }
            catch (InterruptedException ie) {
            }
            
	    
            c.setIdle(0);      // Pretend we have been idle for a minute
            c.setAway("");

	    updateBuddiesList(c, false);
        }
        
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (JaimException je) {
            je.printStackTrace();
        }
        
    }

    /** Receive an event and process it according to its content
     *@param event - The JaimEvent to be processed
     */
    public void receiveEvent(JaimEvent event) {
        TocResponse tr=event.getTocResponse();
        String responseType=tr.getResponseType();
	System.out.println("Received a JAIM event!!!, responseType is: "+responseType);

        if (responseType.equalsIgnoreCase(BuddyUpdateTocResponse.RESPONSE_TYPE)) {
            receiveBuddyUpdate((BuddyUpdateTocResponse)tr);
	    System.out.println("Received a buddy update");
	}
        else
            if (responseType.equalsIgnoreCase(IMTocResponse.RESPONSE_TYPE)) {
                receiveIM((IMTocResponse)tr);
            }
            else
                if (responseType.equalsIgnoreCase(EvilTocResponse.RESPONSE_TYPE)) {
                    receiveEvil((EvilTocResponse)tr);
                }
                else
                    if (responseType.equalsIgnoreCase(GotoTocResponse.RESPONSE_TYPE)) {
                        receiveGoto((GotoTocResponse)tr);
                    }
                    else
                        if (responseType.equalsIgnoreCase(ConfigTocResponse.RESPONSE_TYPE)) {
                            receiveConfig();
                        }
                        else
                            if (responseType.equalsIgnoreCase(ErrorTocResponse.RESPONSE_TYPE)) {
                                receiveError((ErrorTocResponse)tr);
                            }
                            else
                                if (responseType.equalsIgnoreCase(LoginCompleteTocResponse.RESPONSE_TYPE)) {
                                    System.out.println("Login is complete");
                                }
                                else
                                    if (responseType.equalsIgnoreCase(ConnectionLostTocResponse.RESPONSE_TYPE)) {
                                        System.out.println("Connection lost!");
                                    }
                                    else {
                                        System.out.println("Unknown TOC Response:"+tr.toString());
                                    }
    }
    
    private void receiveError(ErrorTocResponse et) {
        System.out.println("Error: "+et.getErrorDescription());
    }
    
    //can use a search on MainContainer's CD elements, or use a hashtable
    public void receiveIM(IMTocResponse im) {
        System.out.println(im.getFrom()+"->"+Utils.stripHTML(im.getMsg()));

	CD selectedCD = (CD)currMessagesList.get((String)Utils.normalise(im.getFrom()));
	
	selectedCD.message = Utils.stripHTML(im.getMsg());
	rearrangeCDs(selectedCD);

	//Return a dummy message back to sender immediately after reception.
	/*
        try {
            c.sendIM(im.getFrom(),"Hello "+im.getFrom(),false);            
        }
        catch (IOException e) {
        }
	*/
    }
    
    /*
     * This function is called any time a buddy signs on/off, or any other buddy update information.
     * This can be useful for making a dynamic buddies list.
     *
     */
    private void receiveBuddyUpdate(BuddyUpdateTocResponse bu) {
	System.out.println("Buddy update: "+bu.getBuddy());
        if (bu.isOnline()) {
            System.out.println("Online");
	    //buddy signs online, add to dynamic buddies list if not already in the list
	    if (!currMessagesList.containsKey(Utils.normalise(bu.getBuddy())))
		{
		    CD cd = new CD("org/jdesktop/lg3d/apps/aim3d/resources/images/jw_aim3d/CD" 
			   + (imageCounter % numImages + 1) + ".png"); // reuse the same image...
		    cd.screenName = Utils.normalise(bu.getBuddy());
		    cd.setCapabilities();

		    mainContainer.addChild(cd);
		    currMessagesList.put(cd.screenName, cd);
		    imageCounter++;
		}
        }
        else {
            System.out.println("Offline");
	    //buddy signs offline, remove from dynamic buddies list
	    if (currMessagesList.containsKey(Utils.normalise(bu.getBuddy())))
		{
		    CD tempCD = (CD)currMessagesList.get((String)(Utils.normalise(bu.getBuddy())).toString());
	            currMessagesList.remove((String)(Utils.normalise(bu.getBuddy())).toString());
		    tempCD.setVisible(false);
		    tempCD = null;
		}
	}
        
        if (bu.isAway()) {
            System.out.println("Away");
        }
        
        System.out.println("evil: "+bu.getEvil());
        
        System.out.println("Idle: "+bu.getIdleTime());
        
        System.out.println("On since "+bu.getSignonTime().toString());
	
    }
    
    private void receiveEvil(EvilTocResponse er) {
        if (er.isAnonymous()) {
            System.out.println("We have been warned anonymously!");
        }
        else {
            System.out.println("We have been warned by "+er.getEvilBy());
            try {
                c.sendEvil(er.getEvilBy(),false);     // Let's warn them back
                c.addBlock(er.getEvilBy());          // And block them
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        System.out.println("New warning level is:"+er.getEvilAmount());
    }
    
    private void receiveGoto(GotoTocResponse gr) {
        System.out.println("Attempting to access "+gr.getURL());
        try {
            InputStream is=c.getURL(gr.getURL());
            if (is!=null) {
                InputStreamReader r=new InputStreamReader(is);
                int chr=0;
                while (chr!=-1) {
                    chr=r.read();
                    System.out.print((char)chr);
                }
                
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void receiveConfig() {
        System.out.println("Config is now valid.");
        
        try {
            Iterator it=c.getGroups().iterator();
            while (it.hasNext()) {
                com.wilko.jaim.Group g=(com.wilko.jaim.Group)it.next();
                System.out.println("Group: "+g.getName());
                Enumeration e=g.enumerateBuddies();
                while (e.hasMoreElements()) {
                    Buddy b =(Buddy)e.nextElement();
                    b.setDeny(false);
                    b.setPermit(false);
                    c.watchBuddy(b.getName());
                    if (b.getDeny()) {
                        c.addBlock(b.getName());
                    }
                    if (b.getPermit()) {
                        c.addPermit(b.getName());
                    }
                }
            }
            c.saveConfig();
        }
        catch (Exception je) {
            je.printStackTrace();
        }
    }

    /*
     * This counts the number of buddies in the hashtable, the number of static buddies of the AIM account
     *
     * @return int the number of buddies in the list
     */
    public int countNumBuddies()
    {
	System.out.println("CurrMessagesList has " + currMessagesList.size()+ " buddies!!!!");
	return currMessagesList.size();
    }

    /**
     * This creates the buddies list.  The function grabs the number of buddies for 
     * the user.  Currently this is implemented as a static list.  Online and offline
     * buddies are added to this list.  Each buddy is represented as a CD and added
     * to the list.
     *
     * @param c, the JAIM connection.
     */
    public void updateBuddiesList(JaimConnection c, boolean staticBuddiesList)
    {
	if (staticBuddiesList == true)
	{
	    mainContainer.removeAllChildren();
	    int i = 0;

	    java.util.Collection myAimGroups = new LinkedList();
	    myAimGroups = c.getGroups();
	    //System.out.println("***Linked list has " + myAimGroups.size() + " groups.");
	    Iterator iterator = myAimGroups.iterator();
	    while (iterator.hasNext()){
		Object element = iterator.next();
		com.wilko.jaim.Group currentGroup = (com.wilko.jaim.Group)element;
		//		    com.wilko.jaim.Group currentGroup = iterator.next();
	          
		//System.out.println("***Printing group: "+ currentGroup.getName());
	           
		java.util.Collection buddiesInGroup = new LinkedList();
		buddiesInGroup = currentGroup.getBuddies();
		//System.out.println("***Buddies group has "+buddiesInGroup.size() + " buddies");
		Iterator buddiesIterator = buddiesInGroup.iterator();
		while (buddiesIterator.hasNext())
		    {
			Buddy buddy = (Buddy)buddiesIterator.next();
			CD cd = new CD("org/jdesktop/lg3d/apps/aim3d/resources/images/CD" 
				       + (i % numImages + 1) + ".png"); // reuse the same image...
			cd.screenName = Utils.normalise(buddy.getName());
			cd.setCapabilities();

			mainContainer.addChild(cd);
			currMessagesList.put(cd.screenName, cd);
			i++;
		    }
	    }

	    mainContainer.setLayout(new CDLayout());
	    //setSize(fanSizeMax * 1.6f, fanSizeMax * 1.6f, fanSizeMax * 1.6f);
	}
	else
	    {
		
	    }
	//This calls setCapabilities on the frame3D buddies list component.
	//This is for a statically drawn buddies list.  To be dynamic, 
	//this needs to be changed so that setCapabilities is drawn for each CD,
	//rather than for the top level frame.
	//setCapabilities();
    }

    /**
     * This function brings up a chat window with the current buddy defined in curr.
     * It works by creating a new imWindow.
     *
     * @param curr, the current buddy in focus.
     */
    public void bringUpChatWindowWith (CD curr)
    {
	if (curr == null)
	    return;
	curr.chatWindow = new imWindow(curr.screenName, curr.message);
        curr.chatWindow.setActive(true);
        curr.chatWindow.setVisible(true);
	currentCDActivity = true;

	//bringUpChatMessageWith(curr);
    }
    
    /*
     * This closes the current chat window by setting the window to being inactive and not visible.
     *
     * @param curr, the current buddy in focus.
     */
    public void closeChatWindowWith (CD curr)
    {
	if (curr == null)
	    return;
	curr.chatWindow.setActive(false);
	curr.chatWindow.setVisible(false);
	currentCDActivity = false;
    }

    /**
     * This brings back the IM window, if it has been minimized or closed.
     *
     * @param curr, the current buddy in focus.
     */
    public void bringBackChatWindowWith (CD curr)
    {
	if (curr == null)
	    return;
	curr.chatWindow.changeActive(true);
	currentCDActivity = true;
    }
    
    /**
     * This redraws the IM window.  We are assuming that the window has already been constructed
     * and viewed at least once.  This function calls the redraw function which basicaly
     * creates a new texture to paint onto the window, thus updating the window view.
     *
     * @param curr, the current buddy in focus.
     */
    public void redrawWindow(CD curr)
    {
	curr.chatWindow.redraw(curr.screenName, curr.message, curr.sendMessage);
    }

    /**
     * Brings up a chat.  The chat message is to implemented for the message only
     * i.e. 3d text or textpanel.  This does not handle the window boundary or text inputs.
     *
     * @param curr, the current buddy to be talked to.
     */
    public void bringUpChatMessageWith (CD curr)
    {
	curr.chatMessage = new imMessage(curr.screenName, curr.message);
	curr.chatMessage.setActive(true);
	curr.chatMessage.setVisible(true);
    }
}
