package org.jdesktop.lg3d.apps.blackgoat;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;

import javax.mail.Message;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

//import org.jdesktop.lg3d.apps.blackgoat.action.PopupAction;
import org.jdesktop.lg3d.apps.blackgoat.button.Button;
import org.jdesktop.lg3d.apps.blackgoat.component.folder.ALetterContainer3D;
import org.jdesktop.lg3d.apps.blackgoat.component.folder.FolderContainer3D;
import org.jdesktop.lg3d.apps.blackgoat.component.letter.HeaderComponent3D;
import org.jdesktop.lg3d.apps.blackgoat.component.letter.LetterComponent3D;
import org.jdesktop.lg3d.apps.blackgoat.component.letter.PostitComponent3D;
import org.jdesktop.lg3d.apps.blackgoat.component.letter.ReplyForwardComponent3D;
import org.jdesktop.lg3d.apps.blackgoat.component.letter.SmallLetterComponent3D;
import org.jdesktop.lg3d.apps.blackgoat.draw.letter.ExtendableLineForDoodle;
import org.jdesktop.lg3d.apps.blackgoat.draw.letter.Letter;
import org.jdesktop.lg3d.apps.blackgoat.layout.CubeInfo;
import org.jdesktop.lg3d.apps.blackgoat.utils.DesendingSortComparator;
import org.jdesktop.lg3d.scenemanager.utils.decoration.TextPanel;
import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.sg.BranchGroup;
import org.jdesktop.lg3d.sg.ColoringAttributes;
import org.jdesktop.lg3d.sg.Geometry;
import org.jdesktop.lg3d.sg.Shape3D;
import org.jdesktop.lg3d.sg.Texture;
import org.jdesktop.lg3d.sg.TextureAttributes;
import org.jdesktop.lg3d.sg.utils.image.TextureLoader;
import org.jdesktop.lg3d.utils.action.ActionNoArg;
import org.jdesktop.lg3d.utils.action.RotateActionBoolean;
import org.jdesktop.lg3d.utils.action.ScaleActionBoolean;
import org.jdesktop.lg3d.utils.actionadapter.ToggleAdapter;
import org.jdesktop.lg3d.utils.c3danimation.NaturalMotionAnimation;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MouseEnteredEventAdapter;
//import org.jdesktop.lg3d.utils.shape.ExtendableLine;
import org.jdesktop.lg3d.utils.shape.FuzzyEdgePanel;
import org.jdesktop.lg3d.utils.shape.GlassyPanel;
import org.jdesktop.lg3d.utils.shape.ImagePanel;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.wg.Cursor3D;
import org.jdesktop.lg3d.wg.event.LgEvent;
import org.jdesktop.lg3d.wg.event.LgEventListener;
import org.jdesktop.lg3d.wg.event.LgEventSource;
import org.jdesktop.lg3d.wg.event.MouseButtonEvent3D;
import org.jdesktop.lg3d.wg.event.MouseDraggedEvent3D;
import org.jdesktop.lg3d.wg.event.MouseEvent3D;
import org.jdesktop.lg3d.wg.event.MouseEvent3D.ButtonId;

/**
 * @author dai
 */
/**
 * This class is in charge of right-side displaying messages.
 */
public class MessageViewer extends Container3D implements Runnable {

	private URL noMessageImage = getClass().getResource("resources/images/blackgoat/util/nomail.png");

	private static float buttonSize = 0.005f;

	private static float buttonOnSize = buttonSize * 1.15f;

	private static final float bodyDepth = 0.005f;

	private static final float decoWidth = 0.005f;

	private static final int SCALE_DURATION = 1800;

	private Component3D backImage;

	private Container3D letterCon;

	private int rorationAngle = 0;
	
	private boolean isShown;
	
	private boolean gestureStarted = false;

	private float deli;

	private ALetterContainer3D currentShownALetterContainer;

	private ImagePanel headerPanel = null;

	private FuzzyEdgePanel helpPanel = null;

	private Component3D helpComp;

	private int HEADER_SCALE_DURATION = 2000;

	private final URL headerImage = getClass().getResource("resources/images/blackgoat/util/header.png");

	private final URL helpImage = getClass().getResource("resources/images/blackgoat/util/helpImage.png");

	private boolean desend;

	private float gestureDeli = 0.001f;

	private float width;

	private float height;

	private float xLoc;

	private float oldx = 0.0f;

	private float newx = 0.0f;

	private Letter[] letters;

	// private SubjectList sl;

	private Container3D headerCon;

	private BranchGroup gestureBG;

	private BranchGroup detachableBG;

	private ExtendableLineForDoodle doodleLine;

	private float mailWidth;

	private float mailHeight;

	private int currentShownMessageId;

	private int currentShownPageNumber;

	private int initialShowinType;

	private boolean isCreated;

	private Component3D titleComp;

	private boolean initilize;

	public void setCreated(boolean created) {
		this.isCreated = created;
	}

	public boolean getCreated() {
		return this.isCreated;
	}

	private/* static */Appearance bodyApp = new SimpleAppearance(0.5f, 0.5f,
			1.0f, 1.0f, SimpleAppearance.DISABLE_CULLING);

	private/* static */Appearance secondBodyApp = new SimpleAppearance(0.6f,
			0.6f, 1.0f, 1.0f, SimpleAppearance.DISABLE_CULLING);

	public MessageViewer(float width, float heigt) {
		this.width = width * 1.2f;// * 0.85f;
		this.height = heigt * 1.05f;// * 0.85f;
		this.deli = this.height / 12;
		this.mailWidth = this.width * 0.97f;
		this.mailHeight = this.height * 0.78f;

		init();
	}

	public void init() {
		setPreferredSize(new Vector3f(width, height, bodyDepth));
		xLoc = width / 5.5f;
		changeTranslation(xLoc, 0.0f, 0.0f);

		backImage = new Component3D();
		letterCon = new Container3D();
		letterCon.setMouseEventPropagatable(true);
		addChild(letterCon);

		headerCon = new Container3D();
		headerCon.setMouseEventPropagatable(true);

		addChild(headerCon);

		// sl = new SubjectList();
		// addChild(sl);

		GlassyPanel bodyGlass = new GlassyPanel(width, height, bodyDepth,
				bodyDepth * 0.1f, bodyApp);

		Component3D bodyComp = new Component3D();
		bodyComp.addChild(bodyGlass);

		initBodyEventAdapter(bodyComp);
		addChild(bodyComp);

		/** ***** */
		SimpleAppearance lineClearOff = new SimpleAppearance(1.0f, 1.0f, 1.0f,
				0.0f, SimpleAppearance.DISABLE_CULLING);

		SimpleAppearance lineClearOn = new SimpleAppearance(1.0f, 1.0f, 1.0f,
				0.0f, SimpleAppearance.DISABLE_CULLING);

		/** Message Reply or Forward Button * */
		Component3D messageReplyButton = new Button(buttonSize * 2,
				lineClearOff, buttonOnSize * 2, lineClearOn);
		TextPanel messageReplyText = new TextPanel("Message Reply", 1.0f, 0.03f,
				0.02f, 1, 1, false);

		messageReplyButton.addChild(messageReplyText);
		messageReplyButton.setCursor(Cursor3D.SMALL_CURSOR);
		messageReplyButton.setTranslation(- width / 3.0f, -height / 2.0f, 0.001f);
		messageReplyButton.addListener(new MouseClickedEventAdapter(
				new ActionNoArg() {
					public void performAction(LgEventSource source) {
						if (null == source)
							return;
						if (null == currentShownALetterContainer)
							return;
						
					//	System.out.println("Reply Button Pressed!!! " + currentShownPageNumber);				
					//	System.out.println("Current Shown message Id!!! " + currentShownALetterContainer.getMessageId());
						
						rorationAngle -= 180;
						
						
						LetterComponent3D letterComp = currentShownALetterContainer.getALetterComponent3D(currentShownPageNumber);
						letterComp.changeVisible(isShown);
		
						/** NOT use change* because of just doing initilization 
						 * See also changeHeaerPostion function.
						 * */
						//letterComp.setRotationAngle((float)Math.toRadians(0));
				
						ReplyForwardComponent3D rfComp =  
								currentShownALetterContainer.createReplyForwardComponent3D(currentShownALetterContainer, currentShownPageNumber);
						
						if(rfComp.isNew()) {
							//System.out.println("NEW33");
							isShown = false;
							rorationAngle = -180;
							rfComp.setNew(false);
							rfComp.setRotationAngle((float)Math.toRadians(0));
						}
						letterComp.changeRotationAngle((float)Math.toRadians(rorationAngle), SCALE_DURATION);
						
						//System.out.println("Roratoin angle is " + rorationAngle);
						rfComp.changeVisible(!isShown);
								//getReplyForwardComponent3D();
						Vector3f letterCompVec = new Vector3f();
						letterComp.getTranslation(letterCompVec);
						rfComp.changeTranslation(letterCompVec.x, letterCompVec.y, letterCompVec.z * 1.02f);
						/** NOT use change* because of just doing initilization 
						 * See also ReplyForward Action Button.
						 * */
					//	rfComp.setRotationAngle((float)Math.toRadians(180));
				
						rfComp.changeRotationAngle((float)Math.toRadians(180 + rorationAngle), SCALE_DURATION);	        

						/** Nesessary */
						isShown = !isShown;
					 	
									
					}
				}
				));
			
		addChild(messageReplyButton);

		
		/** Doodle All Clear * */
		Component3D lineClearAllButton = new Button(buttonSize * 2,
				lineClearOff, buttonOnSize * 2, lineClearOn);
		TextPanel clearAllText = new TextPanel("All Doodle Erase", 1.0f, 0.03f,
				0.02f, 1, 1, false);

		lineClearAllButton.addChild(clearAllText);
		lineClearAllButton.setCursor(Cursor3D.SMALL_CURSOR);
		lineClearAllButton.setTranslation(width / 4.0f, -height / 2.0f, 0.001f);
		lineClearAllButton.addListener(new MouseClickedEventAdapter(
				new ActionNoArg() {
					public void performAction(LgEventSource source) {
						// System.out.println("Clear Button Pressed!!!");
						if (null == source)
							return;
						if (0 == letterCon.numChildren())
							return;
						if (null == currentShownALetterContainer)
							return;
						if (-1 == currentShownMessageId)
							return;
						if (-1 == currentShownPageNumber)
							return;

						LetterComponent3D aLetterComp = currentShownALetterContainer
								.getALetterComponent3D(currentShownPageNumber);

						if (!aLetterComp.isDoodled())
							return;

						// System.out.println("LetterCompChild : " +
						// aLetterComp.numChildren());

						// System.out.println("Current Doodle Num : " +
						// aLetterComp.getTotalDoodleNum());

						int currentNumChildren = aLetterComp.numChildren();
						int curretnNumDoodle = aLetterComp.getTotalDoodleNum();

						aLetterComp.removeAllDoodles();
						int temp = 0;
						for (int i = currentNumChildren - 1; i >= currentNumChildren
								- curretnNumDoodle - temp; i--) {
							// System.out.println("Now i = ; " + i);
							if (aLetterComp.getChild(i) instanceof PostitComponent3D) {
								temp = 1;
								continue;
							}
							aLetterComp.removeChild(i);
							aLetterComp.setPreviousDoodleColorAttr(null);
							// System.out.println("Sub class is : " +
							// letterComp.getChild(i).getClass().toString());
							// ExtendableLineForDoodle bg =
							// ((ExtendableLineForDoodle)letterComp.getChild(i));

						}
						aLetterComp.setCurrentDoodleIndex(0);
						aLetterComp.setDoodled(false);

						aLetterComp.setTotalDoodleNum(0);
						// letterComp.removeAllDoodle();

						// System.out.println("LetterComp " +
						// currentShownPageNumber + " : " +
						// aLetterComp.numChildren());
						// System.out.println("MwgId : " +
						// letterComp[0].getMessageId());
					}
				}));
		addChild(lineClearAllButton);

		/** Doodle Search * */
		Component3D lineSearchButton = new Button(buttonSize * 2, lineClearOff,
				buttonOnSize * 2, lineClearOn);
		TextPanel searchText = new TextPanel("Doodle Search", 1.0f, 0.03f,
				0.02f, 1, 1, false);
		lineSearchButton.addChild(searchText);
		lineSearchButton.setCursor(Cursor3D.SMALL_CURSOR);
		lineSearchButton.setTranslation(width / 17.0f, -height / 2.0f, 0.001f);
		lineSearchButton.addListener(new MouseClickedEventAdapter(
				new ActionNoArg() {
					public void performAction(LgEventSource source) {
						// System.out.println("Doodle Search Button
						// Pressed!!!");
						if (null == source)
							return;
						if (0 == letterCon.numChildren())
							return;
						if (null == currentShownALetterContainer)
							return;
						if (-1 == currentShownMessageId)
							return;
						if (-1 == currentShownPageNumber)
							return;

						LetterComponent3D aLetterComp = currentShownALetterContainer
								.getALetterComponent3D(currentShownPageNumber);
						// System.out.println("MwgId : " +
						// currentShownALetterContainer.getMessageId());

						if (!aLetterComp.isDoodled())
							return;

						int currentOrder = aLetterComp.getCurrentDoodleIndex();

						// if( 0 == letterComp.getTotalDoodleNum() ) return;

						// System.out.println("CurrentOrder : " + currentOrder);
						// System.out.println("LetterComp has : " +
						// aLetterComp.numChildren());
						// System.out.println("currentShownPageNumber : " +
						// currentShownPageNumber);

						// System.out.println("Current Doodle Num : " +
						// aLetterComp.getTotalDoodleNum());

						if (0 == currentOrder
								|| currentOrder == aLetterComp
										.getTotalDoodleNum()) {
							BranchGroup lastDoodleBranch = aLetterComp
									.getDoodle(aLetterComp.getTotalDoodleNum() - 1);

							ExtendableLineForDoodle db = ((ExtendableLineForDoodle) lastDoodleBranch
									.getChild(0));
							Color3f newColor3f = new Color3f();

							if (null != aLetterComp
									.getPreviousDoodleColorAttr()) {
								// System.out.println("0 != " +
								// aLetterComp.getTotalDoodleNum());

								aLetterComp.getPreviousDoodleColorAttr()
										.getColor(newColor3f);
								db.changeColorAttr(newColor3f.x, newColor3f.y,
										newColor3f.z);
							}
							if (currentOrder == aLetterComp.getTotalDoodleNum()) {
								// System.out.println("HERE CurrentOrder is " +
								// currentOrder);
								aLetterComp.setCurrentDoodleIndex(0);
								return;
							}
						}

						if (0 < currentOrder) {
							BranchGroup prevDoodleBranch = aLetterComp
									.getDoodle(currentOrder - 1);

							ExtendableLineForDoodle db = ((ExtendableLineForDoodle) prevDoodleBranch
									.getChild(0));
							Color3f newColor3f = new Color3f();
							aLetterComp.getPreviousDoodleColorAttr().getColor(
									newColor3f);
							db.changeColorAttr(newColor3f.x, newColor3f.y,
									newColor3f.z);

						}

						// System.out.println("2 - CurrentOrder : " +
						// currentOrder);
						// System.out.println("2 - BranchSize : " +
						// aLetterComp.getBranchSize());

						// System.out.println("2 - Current Doodle Num : " +
						// aLetterComp.getTotalDoodleNum());

						BranchGroup doodleBranch = aLetterComp
								.getDoodle(currentOrder);

						ExtendableLineForDoodle db = ((ExtendableLineForDoodle) doodleBranch
								.getChild(0));

						// System.out.println("line num " + db.numGeometries());

						Color3f newColor3f = new Color3f();
						db.getColorAttr().getColor(newColor3f);

						ColoringAttributes newColor = new ColoringAttributes(
								newColor3f.x, newColor3f.y, newColor3f.z,
								ColoringAttributes.SHADE_FLAT);

						aLetterComp.setPreviousDoodleColorAttr(newColor);
						db.changeColorAttr(1.0f, 0.1f, 0.1f);

						aLetterComp.getPreviousDoodleColorAttr().getColor(
								newColor3f);

						currentOrder++;
						/*
						 * if( currentOrder == aLetterComp.getTotalDoodleNum()){
						 * currentOrder = 0; }
						 */
						aLetterComp.setCurrentDoodleIndex(currentOrder);

						// System.out.println("Doodle Search Button Pressed
						// End!!!");

					}
				}));
		addChild(lineSearchButton);

		/** Doodle A Line Clear * */
		Component3D lineClearButton = new Button(buttonSize * 2, lineClearOff,
				buttonOnSize * 2, lineClearOn);
		TextPanel clearALilneText = new TextPanel("A Doodle Clear", 1.0f,
				0.03f, 0.02f, 1, 1, false);

		lineClearButton.addChild(clearALilneText);
		lineClearButton.setCursor(Cursor3D.SMALL_CURSOR);
		lineClearButton.setTranslation(-width / 6.0f, -height / 2.0f, 0.001f);
		lineClearButton.addListener(new MouseClickedEventAdapter(
				new ActionNoArg() {
					public void performAction(LgEventSource source) {
						// / System.out.println("Doodle Clear Button
						// Pressed!!!");
						if (null == source)
							return;
						if (0 == letterCon.numChildren())
							return;

						if (-1 == currentShownMessageId)
							return;

						LetterComponent3D[] letterComp = letters[currentShownMessageId]
								.getLetterComponent3D();
						if (!letterComp[0].isDoodled())
							return;

						int currentOrder = letterComp[0]
								.getCurrentDoodleIndex();

						// System.out.println("CurrentOrder : " + currentOrder);

						// System.out.println("LetterComp has : " +
						// letterComp[0].numChildren());

						// System.out.println("Current Doodle Num : " +
						// letterComp[0].getTotalDoodleNum());

						// if( 0 == currentOrder && letterComp.isDoodled() ==
						// false) return;
						// if(letterComp.isDoodled() == false) return;
						/**
						 * currentOrder = 1 because after Search currentOrder is
						 * incremented And when continuing erasing doodle, in
						 * case to avoid current - 1 below. so in advance,
						 * currentOrder is 1;
						 */
						if (0 == currentOrder)
							currentOrder = 1;
						int currentNumChildren = letterComp[0].numChildren();
						int curretnNumDoodle = letterComp[0]
								.getTotalDoodleNum();

						/**
						 * currentOrder - 1 because after Search currentOrder is
						 * incremented
						 */
						letterComp[0].removeChild(currentNumChildren
								- curretnNumDoodle + currentOrder - 1);
						letterComp[0].decrementTotalDoodleNum();

						/*
						 * BranchGroup doodleBranch =
						 * letterComp.getDoodle(currentOrder - 1); //
						 * ExtendableLineForDoodle db =
						 * ((ExtendableLineForDoodle)doodleBranch.getChild(0));
						 * 
						 * //db.removeAllGeometries(); doodleBranch.detach();
						 * //letterComp.decrementTotalDoodleNum();
						 */
						// currentOrder++;

						if (currentOrder > letterComp[0].getTotalDoodleNum()) {
							// System.out.println("Currentorder goes to 0 ");
							currentOrder = 0;

							if (0 == letterComp[0].getTotalDoodleNum()) {
								letterComp[0].setDoodled(false);
							}
						}

						// letterComp.setCurrentDoodleOrder(letterComp.getCurrentDoodleOrder()
						// + 1);

						letterComp[0].setCurrentDoodleIndex(currentOrder);
						// letterComp.decrementTotalDoodleNum();

						if (0 == letterComp[0].getTotalDoodleNum()) {
							// System.out.println("Current Doodle Num Must be 0
							// : " + letterComp[0].getTotalDoodleNum());
							letterComp[0].setCurrentDoodleIndex(0);
							letterComp[0].setDoodled(false);
						}

						// System.out.println("CurrentOrder : " + currentOrder);

						// System.out.println("Current Doodle Num : " +
						// letterComp[0].getTotalDoodleNum());

					}
				}));
		// addChild(lineClearButton);

	}

	private int angle = 180;

	private void initBodyEventAdapter(final Component3D body) {
		body.setAnimation(new NaturalMotionAnimation(SCALE_DURATION));

		body.setMouseEventPropagatable(true);
		body.addListener(new MouseClickedEventAdapter(ButtonId.BUTTON3,
				new ActionNoArg() {
					public void performAction(LgEventSource source) {
						body.changeRotationAngle((float) Math.toRadians(angle),
								500);
						angle += 180;
					}
				}));
	}

	public void setBackGroundImage() {

		SimpleAppearance backApp = new SimpleAppearance(1.0f, 1.0f, 1.0f,
				SimpleAppearance.ENABLE_TEXTURE
						| SimpleAppearance.DISABLE_CULLING);

		try {
			backApp.setTexture(noMessageImage);
		} catch (Exception e) {
			System.out.println("Error Message is in Setting Goat Image:\n"
					+ e.toString());
			throw new RuntimeException(e);
		}
		FuzzyEdgePanel noMailBody = new FuzzyEdgePanel(width * 0.5f,
				height * 0.5f, decoWidth * 0.5f, backApp);

		backImage.addChild(noMailBody);
		// backImage.setTranslation(width * 0.35f, - height * 0.35f, 0.001f);
		addChild(backImage);

	}

	public void removeBackGoundImage() {
		if (0 != backImage.numChildren())
			removeChild(backImage);
	}

	/**
	 * unable to display messages
	 */
	public void removeLetters() {

		if (null != letters) {
			for (int i = 0; i < letters.length; i++) {
				letters[i] = null;
			}
		}

		if (0 != headerCon.numChildren()) {
			headerCon.removeAllChildren();
		}

		if (null != titleComp) {
			removeChild(titleComp);
		}

		if (null != helpComp) {
			removeChild(helpComp);
		}

		if (0 != letterCon.numChildren()) {
			letterCon.removeAllChildren();
		}
	}

	/**
	 * before ruuning thread
	 */
	public void runThread(Letter[] letters, int initialShowinType) {
		// System.out.println("In Run Thread");
		this.initialShowinType = initialShowinType;
		this.letters = letters;

		new Thread(this).start();
	}

	private void initHeaderPosition(int index, HeaderComponent3D headerComp) {
		// System.out.println("Init Header");
		headerComp.setRotationAxis(1.0f, 0.0f, 0.0f);
		/**
		 * i + 2 because of setting header "From & Subject" setTranslation &&
		 * changeTranslation are needed. Because it enables to show message flow
		 * from top to bottom.
		 */
		headerComp.setTranslation(0.0f, height / 2 - deli * 2, 0.003f);
		headerComp.changeTranslation(0.0f, height / 2 - deli * (index + 2),
				0.001f, SCALE_DURATION);
		/** set for header position */
		headerComp.setPosition(new Vector3f(0.0f, height / 2 - deli
				* (index + 2), 0.001f));

	}

	/**
	 * 
	 * @param index
	 * @param headerComp
	 * @return
	 */
	private void createLetterImage(final int index,
			ALetterContainer3D aLetterCon) {
		
		aLetterCon.setAllLetterComponent3D(letters[index].createLetterComponent3DImage());
		/** Need to modify */
		aLetterCon.setSmallLetterComponent3D(letters[index]
				.getSmallLetterComponent3D());

		for (int i = 0; i < aLetterCon.getNumPages(); i++) {
			initTurnOverEventAdapter(i, aLetterCon);
			initDoodleEventAdapter(aLetterCon.getALetterComponent3D(i));
			aLetterCon.addChild(aLetterCon.getALetterComponent3D(i));
			aLetterCon.getALetterComponent3D(i).setALetterContainer3D(aLetterCon);
		}
		letterCon.addChild(aLetterCon);
	}

	private void changeHeaderPosition(final int index,
			ALetterContainer3D aLetterCon) {
		HeaderComponent3D headerComp = aLetterCon.getHeaderComponent3D();
		// System.out.println(aLetterCon.isSelected());
		// System.out.println("Current Message Id " +
		// aLetterCon.getMessageId());

		/**
		 * Make wearing
		 */
		// aLetterCon.getALetterComp(index).doWear();
		// aLetterCon.getSmallLetterComponent().doWear();
		// System.out.println("Letter Comp is selected" +
		// letterComp.isSelected());
		if (false == aLetterCon.isSelected()) {
			// System.out.println("Letter goes selected");

			/** Watermark */

			if (-1 != currentShownMessageId) {

				// System.out.println("Here2 : " + currentShownMessageId);
				if (null != this.currentShownALetterContainer) {
					// System.out.println("Water mark");

					ALetterContainer3D currentALetterCon = this.currentShownALetterContainer;
					
					currentALetterCon.changeTransparency(0.0f);
					currentALetterCon.setSelected(false);

					for (int i = 0; i < currentALetterCon.getNumPages(); i++) {
						// System.out.println("Page is ??" +
						// aLetterCon.getNumPages());
						// currentALetterCon.getALetterComp(i).changeTranslation(0.0f,
						// 0.0f, 0.006f - i * 0.001f);
						currentALetterCon.getALetterComponent3D(i)
								.changeVisible(false);
					}

					currentALetterCon.getHeaderComponent3D().changeTranslation(
							currentALetterCon.getHeaderComponent3D().getPosition().x,
							currentALetterCon.getHeaderComponent3D().getPosition().y,
							currentALetterCon.getHeaderComponent3D().getPosition().z,
							SCALE_DURATION);

					currentALetterCon.getSmallLetterComponent3D().changeScale(
							1.0f, SCALE_DURATION);

				}
			}

			headerComp.changeTranslation(0.002f, height / 2 - deli, 0.008f,
					SCALE_DURATION);
			headerComp.setRotationAxis(1.0f, 0.0f, 0.0f);
			headerComp.changeScale(1.2f);

			for (int i = 0; i < aLetterCon.getNumPages(); i++) {
				// System.out.println("Page is ??" + aLetterCon.getNumPages());
				aLetterCon.getALetterComponent3D(i).setTranslation(0.0f, 0.0f,
						0.007f - i * 0.001f);
				aLetterCon.getALetterComponent3D(i).setPosition(
						new Vector3f(0.0f, 0.0f, 0.007f - i * 0.001f));
				aLetterCon.getALetterComponent3D(i).changeVisible(true);
				/** NOT use change* because of just doing initilization 
				 * See also ReplyForward Action Button.
				 * */
		}

			/** Make wearing page 0 */
			aLetterCon.getALetterComponent3D(0).doWear();
			aLetterCon.getSmallLetterComponent3D().doWear();

			aLetterCon.getSmallLetterComponent3D().changeScale(1.2f,
					SCALE_DURATION);
			// aLetterCon.changeVisible(true);
			titleComp.changeVisible(false);
			aLetterCon.setSelected(true);

			/*ReplyForwardComponent3D rfComp = aLetterCon.getReplyForwardComponent3D();
			
			if(rfComp.isVisible()){
				
			}*/

			this.currentShownALetterContainer = aLetterCon;

			/** Need to re-confirm */
			currentShownMessageId = aLetterCon.getLetterArrayIndex();
			currentShownPageNumber = 0;
			// System.out.println(aLetterCon.isSelected());
			// System.out.println("Current Shown Id " + currentShownMessageId);
			// System.out.println("Now is : " + aLetterCon.getMessageId());

		} else {
			/** true == aLetterCon.isSelected()) */
			// System.out.println("Letter goes not selected");
			headerComp.changeTranslation(headerComp.getPosition().x, headerComp
					.getPosition().y, headerComp.getPosition().z,
					SCALE_DURATION);
			// System.out.println(aHeaderComp.getPosition().x + " " +
			
			headerComp.setRotationAxis(1.0f, 0.0f, 0.0f);
			headerComp.changeScale(1.0f);

			aLetterCon.getSmallLetterComponent3D().changeScale(1.0f,
					SCALE_DURATION);

			titleComp.changeVisible(true);
			aLetterCon.setSelected(false);
			for (int i = 0; i < aLetterCon.getNumPages(); i++) {
				aLetterCon.getALetterComponent3D(i).changeVisible(false);
				aLetterCon.getALetterComponent3D(i).changeRotationAngle((float)Math.toRadians(0));
				
			}
			this.currentShownALetterContainer = null;

			ReplyForwardComponent3D rfComp = aLetterCon.getReplyForwardComponent3D();
			if(null != rfComp){
				rfComp.changeVisible(false);
				rfComp.changeRotationAngle((float)Math.toRadians(180));
			}
			/** Need to re-confirm */
			currentShownMessageId = -1;
			currentShownPageNumber = -1;
			rorationAngle = 0;
			isShown = false;
			// System.out.println(aLetterCon.isSelected());
			// System.out.println("Current Shown Id " + currentShownMessageId);

		}
	}

	private void initTurnOverEventAdapter(final int ind,
			final ALetterContainer3D aLetterCon) {
		/** for Letter events */
		/** Turning over mail */
		aLetterCon.getALetterComponent3D(ind).addListener(new LgEventListener() {
			public void processEvent(LgEvent evt) {
				Point3f p3f = new Point3f();
				assert (evt instanceof MouseEvent3D);
				if (null == evt)
					return;

				MouseButtonEvent3D mevt = (MouseButtonEvent3D) evt;

				int pageNum = aLetterCon.getNumPages();

				HeaderComponent3D headerComp = aLetterCon.getHeaderComponent3D();
				LetterComponent3D aLetterComp = aLetterCon.getALetterComponent3D(ind);

				if (mevt.isPressed()
						&& mevt.getButton() == MouseButtonEvent3D.ButtonId.BUTTON3) {
					// System.out.println("Here Pressed index : " + index);
					gestureStarted = true;
					oldx = mevt.getCursorPosition(p3f).x;

				} else if (gestureStarted
						&& mevt.isReleased()
						&& mevt.getButton() == MouseButtonEvent3D.ButtonId.BUTTON3) {

					// System.out.println("Mail Turning Over");
					gestureStarted = false;
					newx = mevt.getCursorPosition(p3f).x;

					if (oldx > (newx + gestureDeli)
							|| newx > (oldx + gestureDeli)) {
						/** Header changes */
						aLetterCon.getSmallLetterComponent3D().changeScale(1.0f,
								SCALE_DURATION);

						/** Mouse draggin to Left */
						if (oldx > (newx + gestureDeli)) {
							// System.out.println("-- oldx > new index : " +
							// index);

							if (ind < (pageNum - 1)) {
								currentShownPageNumber = aLetterCon
										.getALetterComponent3D(ind + 1)
										.getPageNumber();
								aLetterCon.getSmallLetterComponent3D()
										.changeScale(1.2f, SCALE_DURATION);
								aLetterCon.getSmallLetterComponent3D().doWear();
								/** When message changes */

								aLetterComp.setTranslation(0.0f, 0.0f,
										aLetterComp.getPosition().z);

								aLetterCon.getALetterComponent3D(ind + 1).doWear();

								aLetterComp.changeTranslation(-1.5f * xLoc,
										0.0f, aLetterComp.getPosition().z, 200);
								/**
								 * TODO MODIFY when changeVisible(boolean,
								 * DURATION) is implemented
								 */
								if (initilize) {
									try {
										Thread.sleep(40);
									} catch (InterruptedException ex) {
										System.out
												.println("Sleep interupted!!");
									}
								}
								aLetterComp.changeVisible(false);

								/** Need to re-confirm */
								currentShownMessageId = aLetterCon
										.getMessageId();
								// System.out.println("SetId : " +
								// currentShownMessageId);
							}

							if ((pageNum - 1) == ind) {
								aLetterComp.changeVisible(false);

								aLetterCon.setSelected(false);
								headerComp.changeTranslation(headerComp
										.getPosition().x, headerComp
										.getPosition().y, headerComp
										.getPosition().z, SCALE_DURATION);
								// titleComp = getTitleComp();
								titleComp.changeVisible(true);

								currentShownMessageId = -1;
								currentShownPageNumber = -1;
							}
						
							/** Mouse draggin to Right */
						} else if (newx > (oldx + gestureDeli)) {

							// System.out.println("++");
							if (ind > 0) {

								/** New Message */
								aLetterCon.getSmallLetterComponent3D()
										.changeScale(1.2f, SCALE_DURATION);
								aLetterCon.getSmallLetterComponent3D().doWear();

								aLetterComp = aLetterCon
										.getALetterComponent3D(ind - 1);
								currentShownPageNumber = aLetterComp
										.getPageNumber();

								aLetterComp.changeVisible(true);
								aLetterCon.getALetterComponent3D(ind - 1).doWear();

								aLetterComp.setTranslation(-xLoc / 2, 0.0f,
										aLetterComp.getPosition().z);
								aLetterComp.changeTranslation(0.0f, 0.0f,
										aLetterComp.getPosition().z, 300);

								/** Need to re-confirm */
								// currentShownMessageId =
								// letterComp[0].getMessageId();
							}

							if (0 == ind) {
								for (int i = 0; i < aLetterCon.getNumPages(); i++) {
									aLetterCon.getALetterComponent3D(i).changeVisible(
											false);
								}
								aLetterCon.setSelected(false);

								headerComp.changeTranslation(headerComp
										.getPosition().x, headerComp
										.getPosition().y, headerComp
										.getPosition().z, SCALE_DURATION);

								titleComp.changeVisible(true);
								currentShownMessageId = -1;
								currentShownPageNumber = -1;
							}
							// System.out.println("Current Page Number " +
							// currentShownPageNumber);
						}
					}
				}
			}

			public Class<LgEvent>[] getTargetEventClasses() {
				return new Class[] { MouseButtonEvent3D.class };
			}
		});
	}

	/**
	 * This function is doodle (drawing) adapter. Enables to draw line while
	 * mouse ButtonId1 is dragged.
	 * 
	 * @param letterComp
	 */
	private void initDoodleEventAdapter(final LetterComponent3D aLetterComp) {

		/** Doodling on mail */
		doodleLine = new ExtendableLineForDoodle();
		doodleLine.setCapability(BranchGroup.ALLOW_DETACH);
		aLetterComp.addChild(doodleLine);

		BranchGroup gestureRoot = new BranchGroup();
		gestureRoot.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
		gestureRoot.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
		gestureRoot.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
		gestureRoot.setPickable(false);
		gestureBG = gestureRoot;

		aLetterComp.addListener(new LgEventListener() {
			public void processEvent(LgEvent evt) {
				// System.out.println("Here you are");
				assert (evt instanceof MouseDraggedEvent3D);
				Point3f p3f = new Point3f();
                                if (null == evt)
					return;

				MouseDraggedEvent3D mevt = (MouseDraggedEvent3D) evt;

				if (mevt.getButton() == MouseButtonEvent3D.ButtonId.BUTTON1) {
					if (Math.abs(mailWidth / 2) + xLoc * 0 > Math
							.abs(((MouseDraggedEvent3D) mevt)
									.getLocalCursorPosition(p3f).x)
							&& Math.abs(mailHeight / 2) + 0.01f > Math
									.abs(((MouseDraggedEvent3D) mevt)
											.getLocalCursorPosition(p3f).y)) {

						if (null != doodleLine) {
							// doodleLine.addVertex(((MouseDraggedEvent3D)mevt).getDragPoint(new
							// Point3f()).x,
							// ((MouseDraggedEvent3D)mevt).getDragPoint(new
							// Point3f()).y,
							// ((MouseDraggedEvent3D)mevt).getDragPoint(new
							// Point3f()).z);
							// doodleLine.addVertex(((MouseDraggedEvent3D)mevt).getLocalCursorPosition().x
							// + xLoc * 2.0f,
							// ((MouseDraggedEvent3D)mevt).getLocalCursorPosition().y
							// - 0.01f,
							// ((MouseDraggedEvent3D)mevt).getLocalCursorPosition().z);
							/**
							 * TODO why - 0.0f is fit??
							 */
							// System.out.println("Doodle added?!");
							// doodleLine.addVertex(comp.getTranslation(new
							// Vector3f()).x, comp.getTranslation(new
							// Vector3f()).y, comp.getTranslation(new
							// Vector3f()).z);
							// System.out.println("Mouse Z Positon : " +
							// ((MouseDraggedEvent3D)mevt).getCursorPosition().y);
							// System.out.println("LetterZ Positon : " +
							// aLetterComp.getPosition().z);
							doodleLine.addVertex(((MouseDraggedEvent3D) mevt)
									.getCursorPosition(p3f).x
									+ xLoc, ((MouseDraggedEvent3D) mevt)
									.getCursorPosition(p3f).y
									- deli * 0 - 0.0f, aLetterComp
									.getPosition().z * 9 / 10);

							// doodleLine.addVertex(((MouseDraggedEvent3D)mevt).getLocalCursorPosition().x
							// + xLoc * 2.0f,
							// ((MouseDraggedEvent3D)mevt).getLocalCursorPosition().y
							// - deli * 2 - 0.00f, 0.0051f);
							// doodleLine.addVertex(((MouseDraggedEvent3D)mevt).getCursorPosition().x,
							// ((MouseDraggedEvent3D)mevt).getCursorPosition().y,
							// ((MouseDraggedEvent3D)mevt).getCursorPosition().z);
						}
					}
				}

			}

			public Class<LgEvent>[] getTargetEventClasses() {
				return new Class[] { MouseDraggedEvent3D.class };
			}
		});

		aLetterComp.addListener(new LgEventListener() {
			public void processEvent(LgEvent evt) {
				// System.out.println("Here you are");
				assert (evt instanceof MouseButtonEvent3D);
				if (null == evt)
					return;

				MouseButtonEvent3D mevt = (MouseButtonEvent3D) evt;

		
				if (mevt.isPressed()
						&& mevt.getButton() == MouseButtonEvent3D.ButtonId.BUTTON1) {
					// System.out.println("Pressed");
					startGesture(mevt, aLetterComp);
				} else if (mevt.isReleased()
						&& mevt.getButton() == MouseButtonEvent3D.ButtonId.BUTTON1) {
					// System.out.println("Released");
					endGesture();
				}
			}

			public Class<LgEvent>[] getTargetEventClasses() {
				return new Class[] { MouseButtonEvent3D.class };
			}
		});

	}

	/**
	 * This Function is used with initDoodleEventAdatper
	 * 
	 * @param evt
	 * @param comp
	 */
	private void startGesture(MouseButtonEvent3D evt,
			LetterComponent3D aLetterComp) {
		if (gestureBG != null) {
			// System.out.println("New");
			gestureBG = new BranchGroup();
			gestureBG.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
			gestureBG.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
			gestureBG.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
			gestureBG.setCapability(BranchGroup.ALLOW_DETACH);
			gestureBG.setPickable(false);

			detachableBG = new BranchGroup();
			detachableBG.setCapability(BranchGroup.ALLOW_DETACH);
			detachableBG.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
			detachableBG
					.setCapability(Appearance.ALLOW_TRANSPARENCY_ATTRIBUTES_READ);
			/** UGLY?? Yes ugly :) */
			aLetterComp.setDetachableBrachGroup(detachableBG);
			// comp.setDoodledId(comp.numChildren() - 1);

			doodleLine = new ExtendableLineForDoodle();
			doodleLine.changeColorAttr((float) Math.random() * 0.8f,
					(float) Math.random(), (float) Math.random());
			detachableBG.addChild(doodleLine);
			gestureBG.addChild(detachableBG);
			aLetterComp.addChild(gestureBG);
			// doodleBaseComp.addChild(gestureBG);
			if (!aLetterComp.isDoodled())
				aLetterComp.setDoodled(true);
			aLetterComp.incrementTotalDoodleNum();
			currentShownPageNumber = aLetterComp.getPageNumber();
		}
	}

	/**
	 * This Function is used with initDoodleEventAdatper
	 */
	private void endGesture() {

		if (gestureBG != null) {
			// System.out.println("Discarding");
			// detachableBG.detach();
			detachableBG = null;
			doodleLine = null;
		}
	}

	public void run() {

		doSort();
		isCreated = true;
		setTitle();

	}

	public void doSortIF(int initialShowinType) {
		this.initialShowinType = initialShowinType;
		doSort();
	}

	public void doSort() {
		if (0 == letters.length)
			return;

		currentShownMessageId = -1;
		Map<String, Letter> tree = null;
		// tree = new TreeMap(new DesendingSortComparator());
		tree = new TreeMap();
		for (int i = 0; i < letters.length; i++) {
			/**
			 * letters.length - i - 1 because of the latest order.
			 */

			HeaderComponent3D headerComp = null;
			if (!isCreated) {
				headerComp = letters[i].createHeaderComponent3D(width);
			} else {
				headerComp = letters[i].getHeaderComponent3D();
			}
	
			LetterComponent3D[] letterComp = letters[i].getLetterComponent3D();

			// System.out.println("B From " + letterComp.getFrom());
			if (CubeInfo.DATE_ASPECT == initialShowinType) {
				// System.out.println("Sort by DATE");
				tree.put(headerComp.getDate().toString()
						+ headerComp.getMessageId(), letters[i]);
			}
			if (CubeInfo.FROM_ASPECT == initialShowinType) {
				// System.out.println("Sort by FROM");
				tree.put(headerComp.getFrom() + headerComp.getMessageId(),
						letters[i]);
			}
			if (CubeInfo.SUBJECT_ASPECT == initialShowinType) {
				// System.out.println("Sort by SUBJECT");
				tree.put(headerComp.getSubject() + headerComp.getMessageId(),
						letters[i]);
			}

			// System.out.println("Num is : " + tree.size() + " : " +
			
			for (int j = 0; j < letterComp.length; j++) {
				letterComp[j].changeVisible(false);
				//ReplyForwardComponent3D rfComp = headerComp.get
				// letterComp[j].detach();
			}
				if (null != titleComp)
				titleComp.changeVisible(true);
		}
		after(tree);
	}

	//float xdim = 0.009f;

	//float dim = xdim * 1.5f;

	public void after(Map<String, Letter> tree) {
		float xdim = 0.009f;

		float dim = xdim * 1.5f;

		Set set = tree.keySet();
		Iterator it = set.iterator();

		int index = 0;
		int sortOrder;
		while (it.hasNext()) {
			Letter letter = tree.get(it.next());
		
			Vector3f tempPosition;

			HeaderComponent3D headerComp = letter.getHeaderComponent3D();
			// System.out.println("ID : " + headerComp.getMessageId());
			if (desend) {
				sortOrder = index;
				tempPosition = new Vector3f(0.0f, height / 2 - deli
						* (sortOrder + 2), 0.0011f);
			} else {
				sortOrder = letters.length - index - 1;
				tempPosition = new Vector3f(0.0f, height / 2 - deli
						* (sortOrder + 2), 0.0011f);
			}
			headerComp.changeTranslation(tempPosition,
					HEADER_SCALE_DURATION / 2);
		//	headerComp.setTranslation(tempPosition.x, tempPosition.y, tempPosition.z);
						
			headerComp.setLetterArrayIndex(index);
			headerComp.setPosition(tempPosition);

			if( null != this.currentShownALetterContainer ) {
				ReplyForwardComponent3D rfComp = this.currentShownALetterContainer.getReplyForwardComponent3D();
				if( null != rfComp ) {
					rfComp.changeVisible(false);
				}
			}
			/**
			 * Vector3f position = new Vector3f(0.0f, 0.0f, -dim / 2 + deli * (i +
			 * 1));
			 */

			SmallLetterComponent3D smallComp = letter
					.getSmallLetterComponent3D();
			if (desend) {
				sortOrder = index;
				tempPosition = new Vector3f(0.0f, 0.0f, -dim / 2
						+ (dim / (letters.length + 2)) * (sortOrder + 1));
			} else {
				sortOrder = letters.length - index - 1;
				tempPosition = new Vector3f(0.0f, 0.0f, -dim / 2
						+ (dim / (letters.length + 2)) * (sortOrder + 1));
			}

			smallComp
					.changeTranslation(tempPosition, HEADER_SCALE_DURATION / 2);
			smallComp.setPosition(tempPosition);
		
			index++;
		}
		desend = !desend;
	}

	public void setTitle() {

		SimpleAppearance decoApp = new SimpleAppearance(0.8f, 1.0f, 0.8f,
				SimpleAppearance.ENABLE_TEXTURE
						| SimpleAppearance.DISABLE_CULLING);

		GlassyPanel bodyDeco = new GlassyPanel(width * 0.95f, 0.20f, 0.002f,
				0.0f, decoApp);

		SimpleAppearance helpApp = new SimpleAppearance(1.0f, 1.0f, 1.0f,
				SimpleAppearance.ENABLE_TEXTURE
						| SimpleAppearance.DISABLE_CULLING);

		try {
			helpApp.setTexture(helpImage);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		titleComp = new Component3D();
		helpComp = new Component3D();

		titleComp.setAnimation(new NaturalMotionAnimation(800));
		helpComp.setAnimation(new NaturalMotionAnimation(800));

		titleComp.setTranslation(0.0f, height / 2 - deli * 2, 0.05f);
		helpComp.setTranslation(0.0f, height / 2 - deli * 2, 0.05f);

		// i + 2 because of setting header "From & Subject"
		titleComp.changeTranslation(0.0f, height / 2 - deli, 0.001f, 800);
		helpComp.changeTranslation(0.0f, height / 2 - deli, 0.001f, 800);

		/*
		 * headerComp.addListener(new MouseEnteredEventAdapter( new
		 * ScaleActionBoolean(headerComp, 1.1f, 700)));
		 */
		helpComp.addListener(new MouseEnteredEventAdapter(
				new ScaleActionBoolean(helpComp, 1.01f, 700)));

		try {
			headerPanel = new ImagePanel(headerImage, width * 0.9f, 0.015f);
			helpPanel = new FuzzyEdgePanel(width * 0.93f, 0.20f, 0.01f, helpApp);
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		titleComp.addChild(headerPanel);
		titleComp.changeVisible(true);

		helpComp.addChild(helpPanel);
		helpComp.changeVisible(false);

		titleComp.addListener(new MouseClickedEventAdapter(
				MouseButtonEvent3D.ButtonId.BUTTON1, new ToggleAdapter(
						new RotateActionBoolean(helpComp,
								(float) Math.PI * 4.0f, SCALE_DURATION))));

		helpComp.addListener(new MouseClickedEventAdapter(
				MouseButtonEvent3D.ButtonId.BUTTON1, new ToggleAdapter(
						new RotateActionBoolean(titleComp,
								(float) Math.PI * 4.0f, SCALE_DURATION))));

		titleComp.addListener(new MouseClickedEventAdapter(ButtonId.BUTTON1,
				new ActionNoArg() {
					public void performAction(LgEventSource source) {
						titleComp.changeVisible(false);

						helpComp.changeVisible(true);
						helpComp.changeTranslation(0.0f, 0.0f, 0.003f,
								SCALE_DURATION);
					}
				}));

		helpComp.addListener(new MouseClickedEventAdapter(ButtonId.BUTTON1,
				new ActionNoArg() {
					public void performAction(LgEventSource source) {
						helpComp.changeVisible(false);

						titleComp.changeVisible(true);
						titleComp.setTranslation(0.0f, 0.0f, 0.003f);
						helpComp.changeTranslation(0.0f, height / 2 - deli,
								0.001f, 800);
						titleComp.changeTranslation(0.0f, height / 2 - deli,
								0.001f, 800);
					}
				}));

		helpComp.addChild(bodyDeco);

		titleComp.changeVisible(true);

		addChild(titleComp);
		addChild(helpComp);

		listLetter();
	}

	private void listLetter() {
		/**
		 * Remove BackGoundImage
		 */
		// System.out.println("Letter Size : " + letters.length);
		for (int i = 0; i < letters.length; i++) {

			// if( null == letterComp ) System.out.println("letter is null");
			ALetterContainer3D aLetterCon = new ALetterContainer3D();

			HeaderComponent3D headerComp = letters[i].getHeaderComponent3D();// createHeaderComponent3D(width);

			if (null == headerComp)
				System.out.println("header is null");
			/** set HeaderComp to ALetterContainer */
			aLetterCon.setHeaderComponent3D(headerComp);

			/** need for visible */
			headerComp.changeVisible(true);

			/** initialise */
			initHeaderPosition(i, headerComp);
			initEventAdapter(i, aLetterCon);

			/** set ALetterContainer to HeaderContainer */
			headerCon.addChild(headerComp);

		}
		initilize = true;
	}

	/**
	 * This function initialises header eventAdapter. if header does not have
	 * its mail content, this creates the mail content and set header's position
	 * when clicked with mouse ButtonId1. if the mail content is done, this set
	 * header's position only.
	 * 
	 * @param index
	 * @param headerComp
	 */
	private void initEventAdapter(final int index,
			final ALetterContainer3D aLetterCon) {
		aLetterCon.getHeaderComponent3D().addListener(
				new MouseClickedEventAdapter(
						MouseButtonEvent3D.ButtonId.BUTTON1, new ActionNoArg() {
							public void performAction(LgEventSource source) {
								if (null == source)
									return;

								if (false == aLetterCon.hasCreatedLetter()) {
									createLetterImage(index, aLetterCon);
									aLetterCon.setLetterIsCreated(true);
								}
								changeHeaderPosition(index, aLetterCon);

							}
						}));

	}
}
