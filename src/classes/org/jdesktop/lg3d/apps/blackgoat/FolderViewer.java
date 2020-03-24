package org.jdesktop.lg3d.apps.blackgoat;

import java.net.URL;
import java.util.ArrayList;

//import javax.mail.Message;
import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.apps.blackgoat.button.Button;
import org.jdesktop.lg3d.apps.blackgoat.button.ButtonAppearance;
import org.jdesktop.lg3d.apps.blackgoat.component.folder.CubeComponent3D;
import org.jdesktop.lg3d.apps.blackgoat.component.folder.FolderContainer3D;
import org.jdesktop.lg3d.apps.blackgoat.component.letter.SmallLetterComponent3D;
import org.jdesktop.lg3d.apps.blackgoat.component.preference.PreferenceComponent3D;
//import org.jdesktop.lg3d.apps.blackgoat.draw.letter.DummyLetterContentImpl;
import org.jdesktop.lg3d.apps.blackgoat.draw.letter.DummyLetterContentDrawer;
import org.jdesktop.lg3d.apps.blackgoat.draw.letter.Letter;
//import org.jdesktop.lg3d.apps.blackgoat.emessage.read.DummyEMessageReader;
import org.jdesktop.lg3d.apps.blackgoat.emessage.read.EMessageReadable;
import org.jdesktop.lg3d.apps.blackgoat.layout.CubeLayout;
import org.jdesktop.lg3d.apps.blackgoat.layout.CubeInfo;
//import org.jdesktop.lg3d.apps.shapefactory.DummyEMessageReader;
//import org.jdesktop.lg3d.apps.sample.CubeLayout;
//import org.jdesktop.lg3d.apps.sample.CubeSize;
//import org.jdesktop.lg3d.apps.sample.CubeLayout;
//import org.jdesktop.lg3d.scenemanager.utils.decoration.TextPanel;
import org.jdesktop.lg3d.sg.Shape3D;
import org.jdesktop.lg3d.utils.action.ActionBoolean;
import org.jdesktop.lg3d.utils.action.ActionNoArg;
import org.jdesktop.lg3d.utils.action.AppearanceChangeAction;
import org.jdesktop.lg3d.utils.c3danimation.NaturalMotionAnimation;
import org.jdesktop.lg3d.utils.eventadapter.Component3DHighlightEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MouseEnteredEventAdapter;
import org.jdesktop.lg3d.utils.shape.GlassyPanel;
import org.jdesktop.lg3d.utils.shape.ImagePanel;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.wg.LayoutManager3D;
import org.jdesktop.lg3d.wg.event.LgEvent;
import org.jdesktop.lg3d.wg.event.LgEventListener;
import org.jdesktop.lg3d.wg.event.LgEventSource;
import org.jdesktop.lg3d.wg.event.MouseButtonEvent3D;
import org.jdesktop.lg3d.wg.event.MouseEvent3D;
import org.jdesktop.lg3d.wg.event.MouseWheelEvent3D;
import org.jdesktop.lg3d.wg.event.MouseEvent3D.ButtonId;

/**
 * @author dai
 */
/**
 * This class in charge of left-side message folders.
 */
public class FolderViewer extends Container3D {//implements Runnable {
	//private static String popImage = "resources/images/blackgoat/util/pop.png";

	private static final int numFolders = 5;

//	private static final int numImages = 4;

	private static final float discSize = 0.04f;

	private static final float fanSizeMax = discSize * 2;

	private static final float fanSizeMin = discSize;

	private static final float frontRad = (float) (Math.PI * 3 / 2);

	private static final float stackSpacing = 0.00125f;

	private static final float bodyAngle = (float) Math.toRadians(-65);

	private Container3D mainContainer;

	private Container3D farmContainer;

	private boolean focused = false;

	private Vector3f tmpV3f = new Vector3f();

	private float absWidth;

	private float absHeight;

	private float lclWidth;

	private float lclHeight;

	private int SCALE_DURATION = 1000;

//	private SimpleAppearance boxApp;

	private int numMessages = 0;
	
//	private boolean isCreated;

//	private EMessageReadable em;

//	private Message[] messages;

	private MessageViewer mv;
	
	//private static final int DATE_ASPCET = 4;

	private static float buttonSize = 0.005f;

	private static float buttonOnSize = buttonSize * 1.15f;

	private static SimpleAppearance popOffAppearance=null;

	private static SimpleAppearance popOnAppearance=null;

	/**
	 * Constructs a FolderViewer Container object with width, height and MessageViewer 
	 * object. 
	 * @param absWidth
	 * @param absHeight
	 * @param md
	 */
	public FolderViewer(float absWidth, float absHeight, MessageViewer mv) {
		this.absWidth = absWidth;
		this.absHeight = absHeight;
		this.mv = mv;

		this.lclWidth = -2.4f * absWidth / 4.0f;
		this.lclHeight = absHeight / -4.5f;
                
                if (popOffAppearance==null) {                    
                    popOffAppearance = new ButtonAppearance(
                                    getClass().getResource("/resources/images/blackgoat/util/pop.png"), false, 0);
                    popOnAppearance = new ButtonAppearance(
                                    getClass().getResource("/resources/images/blackgoat/util/pop.png"), true, 0);
                }

		setName("FolderViewer");
		setTranslation(lclWidth, lclHeight + 0.01f, 0.02f);

		mainContainer = new Container3D();
		addChild(mainContainer);
		farmContainer = new Container3D();
		addChild(farmContainer);

		mainContainer.setRotationAxis(0.03f, 0.0f, 0.0f);
		mainContainer.setRotationAngle(bodyAngle);

		// Sets a layoutmanager for the mainContainer.
		mainContainer.setLayout(new FolderLayout());

		initFarmContainer();
		initPopEventAdapter();

		for (int i = 0; i < numFolders; i++) {
			SimpleAppearance app = new SimpleAppearance(0.9f, 0.9f,
					1.0f, 0.1f);
			Folder folder = new Folder(app);

			mainContainer.addChild(folder);
		}
		addListener(new Component3DHighlightEventAdapter(new ActionBoolean() {
			public void performAction(LgEventSource source, boolean flag) {
				focused = flag;
				float zBias = (flag) ? (discSize * -0.5f) : (0.0f);
				mainContainer.setTranslation(0.0f, 0.0f, zBias);
				float size = (flag) ? (fanSizeMax) : (fanSizeMin);
				mainContainer.revalidate();
				tmpV3f.set(size, size, size);
				setPreferredSize(tmpV3f);

			}
		}));

		tmpV3f.set(fanSizeMax, fanSizeMax, fanSizeMax);
		setPreferredSize(tmpV3f);

	}

	/**
	 * Farm below
	 */
	Component3D setProperty;

	/**
	 * Sets left side down part container where there 2 goats images are.
	 *
	 */
	private void initFarmContainer() {
		Component3D farm = new Component3D();
		SimpleAppearance app = new SimpleAppearance(0.6f, 1.0f, 0.75f, 1.0f);
		GlassyPanel glass = new GlassyPanel(0.04f, 0.02f, 0.0025f, 0.001f, app);
		farm.addChild(glass);
		farm.setRotationAngle(bodyAngle);

		farmContainer.setTranslation(0.003f, -0.042f, 0.0f);
		farmContainer.addChild(farm);

		/* Set Server Property */
		PreferenceComponent3D pv = new PreferenceComponent3D(buttonSize * 3, buttonOnSize * 3,
				0.015f, 0.006f, -0.004f);

		farmContainer.addChild(pv);

	}

	/**
	 * Sets left side down part container's message pop action listener.
	 */
	private void initPopEventAdapter() {

		Shape3D shape = new ImagePanel(buttonSize * 4, buttonOnSize * 3);
		shape.setAppearance(popOffAppearance);

		Component3D popComp = new Component3D();
		popComp.setTranslation(0.0f, -0.0385f, 0.0f);

		popComp.addChild(shape);
		addChild(popComp);
		if (popOffAppearance != popOnAppearance) {
			addListener(new MouseEnteredEventAdapter(
					new AppearanceChangeAction(shape, popOnAppearance)));
		}

		popComp.addListener(new MouseClickedEventAdapter(ButtonId.BUTTON1,
				new ActionNoArg() {
					public void performAction(LgEventSource source) {

						mv.removeLetters();
						mv.removeBackGoundImage();
						removeMessageInFolder();
						popMessage();
						if (opened) {
							//System.out.println("Fake message creating ");
							createMessageInFolder();
							mv.setCreated(false);
						}
					}
				}));

	}

	/**
	 * Operation for the seleted Folder
	 */
	private int folderNo = 0;//(int) (Math.random() * numFolders);
 
	private SimpleAppearance flapp = new SimpleAppearance(0.9f, 0.9f, 0.9f,
			0.7f);

	private Folder fl = new Folder(flapp);

	/**
	 * create messages in a folder.
	 *
	 */
	private void createMessageInFolder() {

		fl = (Folder) mainContainer.getChild(folderNo);
		fl.setMessageInFolder(STOP);
		for( int i = 0; i < letters.length; i++ ) {
			FolderContainer3D fc = (FolderContainer3D)fl.getFakeCon().getChild(0);
			letters[i].setSmallLetterComponent3D((SmallLetterComponent3D)fc.getChild(i));
		}
		fl.setMessageStacked(true);
		/** set for FolderContainer3D. Masic Number "0" is guilty*/
		//mv.setFolderContainer3D((FolderContainer3D)fl.getFakeCon().getChild(0));
		
	}

	/**
	 * remove message in a folder.
	 *
	 */
	private void removeMessageInFolder() {
		if (null != fl.getFakeCon() && 0 != fl.getFakeCon().numChildren()) {
			fl.getFakeCon().removeAllChildren();
		}
	}

	/**
	 * Pop Messages
	 */
	private int STOP = 9;

	/**
	 * gets a number of message in a folder.
	 */
	public int getNumMessages(){
		return this.STOP;
	}
	
	private Letter[] letters;

	private boolean opened;

	private void popMessage() {
		// System.out.println("You have mails before: ");
		try {
		//	em = new EMessageReader();
			/** Uncomment for creating dummy messages */
			//em = new DummyEMessageReader();
		//	em.open();
			/**
			 * need for Dummy
			 */
			/** Uncomment for creating dummy messages */
			//DummyLetterContentDrawer.resetMessageNumberToRead();
			/*
			if(!em.isOpened()){
				System.err.println("Can not connect Server...");
				System.err.println("Please check <ServerIP>, <Id> and <Password>");
			
				return;
			}
*/			
			opened = true;

	//		messages = em.getMessages();
			numMessages = STOP;
		//	numMessages = em.getNumMessages();
			if (0 == numMessages)
				mv.setBackGroundImage();

			/**
			 * TODO have to rewrite letters size
			 */
			if (STOP > numMessages)
				STOP = numMessages;
			
			letters = new Letter[STOP];
			int messageId = 0;
			

			for (int i = numMessages - 1; i > numMessages - STOP - 1; i--) {
				//letters[messageId] = new LetterContentDrawer(messageId, messages[i], absWidth * 1.2f * 0.97f, absHeight * 1.05f * 0.78f, 0.002f);
				/** Uncomment for creating dummy messages */
				letters[messageId] = new DummyLetterContentDrawer(messageId, absWidth * 1.2f * 0.97f, absHeight * 1.05f * 0.78f, 0.002f);
				messageId++;
				
			}
			
			/** never fail to em.close AFTER creating letter images, diffinetaly */
			//em.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		// System.out.println("You have mails : " + totalEessages);
	}

	/**
	 * shows messages on the main panel thru this function
	 */
	private void showMessages(int index) {
		// System.out.println("Go Seeing Mails");

		if ((null != mv) && (null != letters)) {
			mv.removeLetters();
			mv.removeBackGoundImage();
			mv.runThread(letters, index);
			
		} else {
			// System.out.println("Either is null");
		}
	}

	/**
	 * Rearrange the order of Folder objects in the mainContainer so that the
	 * given Folder comes to the front. See the comments in the FolderLayout
	 * class.
	 */

	private void rearrangeFolders(Folder selectedFolder) {
		mainContainer.rearrangeChildLayout(selectedFolder, null);
	}


	private class Folder extends Container3D { //implements Runnable {
		private Container3D inner;

		private SimpleAppearance app;

		private Container3D messageCon;

		private boolean focused = false;

		private float raisedYPos = absHeight / 2.5f;

		private boolean messageStacked = false;

		//private boolean done = false;

	//	private float vRot = 45;
		
		private Container3D cube;
		
		private int angle;

		private int wheelUp = -1;
		private int wheelDown = 1;
		private boolean first = false;
		
		/**
		 * constructs a cube folder.
		 * @param app 
		 */
		private Folder(SimpleAppearance app) {
			setAnimation(new NaturalMotionAnimation(SCALE_DURATION));
			
			this.app = app;
			inner = new Container3D();
			initEventAdapter();
			
			cubeEventAdapter();
		
			//xdim = ydim = zdim = 0.007f;
		
			inner.setRotationAxis(-1.0f, 0.0f, 0.0f);
			inner.setTranslation(0.0f, -0.003f, 0.0f);
			/**
			 * Cube Create
			 */
			Container3D innerCube = cubeFactory();
			
			inner.addChild(innerCube);
			
			/*
			inner.addListener(new MouseClickedEventAdapter(ButtonId.BUTTON3,
					new ActionNoArg() {
						public void performAction(LgEventSource source) {
							inner.changeRotationAngle((float)Math.toRadians(angle), 1000);
							angle += 45;
						}
			}));
			*/
			
			addChild(inner);
		}
	
		/**
		 * set cube event adapter. This adapts mouse wheel action.
		 *
		 */
		private void cubeEventAdapter() {
			inner.setMouseEventPropagatable(true);
			inner.setAnimation(new NaturalMotionAnimation(800));
			

	    	inner.addListener(new LgEventListener() {
				public void processEvent(LgEvent evt) {
				//	System.out.println("Here you are");
					assert (evt instanceof MouseEvent3D);
					if( null == evt )
						return;
					
					MouseEvent3D me = (MouseEvent3D)evt;
					
					if( me instanceof MouseWheelEvent3D){			
						//	System.out.println("You've got it!");
						MouseWheelEvent3D mwe = (MouseWheelEvent3D)me;
						
							if( wheelDown == mwe.getWheelRotation()){
								if(!first){
									first = true;
								}
								angle -= 45;
								inner.changeRotationAngle((float)Math.toRadians(angle), 1000);
							}
							
							if( wheelUp == mwe.getWheelRotation()){
								if(!first) {
									angle += 90;
									first = true;
								}
								angle += 45;
									
								inner.changeRotationAngle((float)Math.toRadians(angle), 1000);
								}
					}
					
				}

				public Class<LgEvent>[] getTargetEventClasses() {
					return new Class[] { MouseEvent3D.class };
				}
			});
			
		}
		
		/**
		 * creates cube folder. Each apspect has its name, date, subject, from and etc.
		 * @return cube
		 */
		private Container3D cubeFactory() {
			float tempAngle;
			cube = new Container3D();
			cube.setRotationAngle((float)Math.toDegrees(45));
			cube.setRotationAxis(1.0f, 1.0f, 0.0f);
			//cube.setTranslation(0.0f, -discSize, 0.0f);
			
			cube.setAnimation(new NaturalMotionAnimation(SCALE_DURATION));
			//cube.setMouseEventPropagatable(true);
			cube.setLayout(new CubeLayout());
		        
		    //SimpleAppearance app = new SimpleAppearance(0.2f, 0.4f, 0.3f, 0.4f);
			SimpleAppearance popupOff = new SimpleAppearance(1.0f, 1.0f,
					1.0f, 0.0f, SimpleAppearance.DISABLE_CULLING);

			SimpleAppearance popupOn = new SimpleAppearance(1.0f, 1.0f,
					1.0f, 1.0f, SimpleAppearance.DISABLE_CULLING);

			int FONT_SIZE = 18;
			int ratio = 4;
		    for(int i = 0; i < 6; i++) {
		    	
		    	 SimpleAppearance app = new SimpleAppearance(1.0f, 0.6f, 0.6f, 0.2f, 
		    			 SimpleAppearance.DISABLE_CULLING
		    			 | SimpleAppearance.ENABLE_TEXTURE);
//		    	 app.setColor(0.9f, 0.8f + i * 0.002f, 0.9f, 0.9f);
	        		
		    	// 	SimpleAppearance app = new SimpleAppearance(0.6f, 0.8f - 0.1f * i, 0.9f, 0.9f);
		    	CubeComponent3D comp = new CubeComponent3D();
		    	//PopupTextComponent comp = new PopupTextComponent("Date", 16);
	    		
		    	/** UGLY */
		    	if ( CubeInfo.DATE_ASPECT == i ){
		    		initEachAspcetSortEventAdapter(i, comp);	        
	        		app.setColor(0.6f, 0.6f + i * 0.002f, 1.0f, 1.0f);
	        	
	        		/*
		    		PopupTextComponent dateText = new PopupTextComponent("Date", FONT_SIZE);
		    		Component3D popComp = new Button(buttonSize * ratio,
		    				dateText.getImageApperance(), buttonSize * ratio, dateText.getImageApperance());
					comp.addChild(popComp);
					*/
		    	}
		    	if ( CubeInfo.FROM_ASPECT == i ){
		    		initEachAspcetSortEventAdapter(i, comp);	        
	        		app.setColor(0.6f, 0.6f + i * 0.002f, 1.0f, 1.0f);
	        	
	        		/*
		    		PopupTextComponent dateText = new PopupTextComponent("From", FONT_SIZE);
		    		Component3D popComp = new Button(buttonSize * ratio,
		    				dateText.getImageApperance(), buttonSize * ratio, dateText.getImageApperance());
					comp.addChild(popComp);
					*/
		    	}
		    	if ( CubeInfo.SUBJECT_ASPECT == i ){
		    		initEachAspcetSortEventAdapter(i, comp);	        
	        		app.setColor(0.6f, 0.6f + i * 0.002f, 1.0f, 1.0f);
	        	/*
		    		PopupTextComponent dateText = new PopupTextComponent("Subject", FONT_SIZE);
		    		Component3D popComp = new Button(buttonSize * ratio,
		    				dateText.getImageApperance(), buttonSize * ratio, dateText.getImageApperance());
					comp.addChild(popComp);
		    	*/
		    	}
	        	 
	        	GlassyPanel body = new GlassyPanel(CubeInfo.sideLength, CubeInfo.sideLength, 0.002f, app);
	        	
	        	//initEachAspcetEventAdapter(comp);
	        	
	        	comp.addChild(body);
	        	cube.addChild(comp);
	        	tempAngle = comp.getRotationAngle();
	        	
	        	initEachAspcetEventAdapter(comp, tempAngle);
	        	
	        }    
		    return cube;
		}
		
		
		private float aspectRotationAngle;
		private float currentAngle;
		
		/** 
		 * trigers showing messages if a folder is raised.
		 * @param index
		 * @para comp
		 * 
		 */
		private void initEachAspcetSortEventAdapter(final int index, final CubeComponent3D comp) {
			
			comp.addListener(new LgEventListener() {
				public void processEvent(LgEvent evt) {
				//	System.out.println("Here you are");
					assert (evt instanceof MouseButtonEvent3D);
					if( null == evt )
						return;
					
					MouseButtonEvent3D me = (MouseButtonEvent3D)evt;
					
					if( focused && me.isClicked() 
							&& (me.getButton() == MouseButtonEvent3D.ButtonId.BUTTON1) ){			
						//	System.out.println("You've got it!  " + Math.toRadians(aspectRotationAngle));
						//System.out.println("Box aspect # is : " + index);  
						//mv.doSortIF(index);
						if(mv.getCreated()){
						//	System.out.println("Messages are already created");
							mv.doSortIF(index);
						//	mv.setCreated(true);
						} else {
						//	System.out.println("Gonna create messages");
							showMessages(index);
						}
					}
				}
					public Class<LgEvent>[] getTargetEventClasses() {
						return new Class[] { MouseButtonEvent3D.class };
					}
				});
		}
		
		/** 
		 * sets each cube's aspect event adapter.
		 * @param comp
		 * @param angle
		 */
		private void initEachAspcetEventAdapter(final Component3D comp, float angle){
		
			comp.setAnimation(new NaturalMotionAnimation(SCALE_DURATION));
	    	comp.setMouseEventPropagatable(true);
	   
	    //	System.out.println("Rotation Angle ; " + comp.getRotationAngle());
	    	if( angle >= 0.0f ) {
	    		currentAngle = (float)Math.floor(Math.toDegrees(angle));
	    		//System.out.println("currentAngle 1: " + currentAngle);
	    	}
	    	else {
	    		currentAngle = (float)Math.ceil(Math.toDegrees(angle));
	    //		System.out.println("currentAngle 2: " + currentAngle);
	    	}
	    	
	    	aspectRotationAngle = 0.0f;
	    	
	    	if( 0 == currentAngle ) {
	    		aspectRotationAngle = (float)Math.PI * 2.0f;
	    			
	    //	System.out.println("New Angle : " + angle);
	    	} else if ( 180 == currentAngle ){
	    		aspectRotationAngle = (float)Math.PI * 3.0f;
	    		
	    		//aspectRotationAngle = 180;
	    	//comp.setPickable(true);
	    	} else if ( 90 == currentAngle ){
	    		aspectRotationAngle = (float)Math.PI * 2.5f;
	    		//aspectRotationAngle = 450;
	    		
	        	//comp.setPickable(true);
	    	} else if ( -90 == currentAngle ){
	    		aspectRotationAngle = -(float)Math.PI * 2.5f;
	    		//aspectRotationAngle = -450;
	    		
	    		//comp.setPickable(true);
	       } else {
	        	//System.out.println("Notthing??");
	        }
	        
	    	/**
	    	 * TODO re-write rotation way
	    	 */
	    	
	    	//System.out.println("cur\t" + (int)Math.toDegrees(currentAngle));
	    	//System.out.println("angle\t" + (int)Math.toDegrees(angle));
	    	//System.out.println("pi\t" + (int)Math.toDegrees(Math.PI));
	    	
	    	/*
	    	comp.addListener(new LgEventListener() {
				public void processEvent(LgEvent evt) {
				//	System.out.println("Here you are");
					assert (evt instanceof MouseButtonEvent3D);
					if( null == evt )
						return;
					
					MouseButtonEvent3D me = (MouseButtonEvent3D)evt;
					
					if( mailStacked 
							&& (me.getButton() == MouseButtonEvent3D.ButtonId.BUTTON3) ){			
						//	System.out.println("You've got it!  " + Math.toRadians(aspectRotationAngle));
							///aspectRotationAngle += (float)Math.toRadians(Math.PI);
							//aspectRotationAngle += (float)Math.toRadians(Math.PI);
							

							
							comp.changeRotationAngle((float)Math.toRadians(aspectRotationAngle), SCALE_DURATION);
							//float temp = comp.getRotationAngle();
							//comp.changeRotationAngle((float)Math.toDegrees(aspectRotationAngle), SCALE_DURATION);
							//aspectRotationAngle += ;
							
					}
				}
					public Class<LgEvent>[] getTargetEventClasses() {
						return new Class[] { MouseButtonEvent3D.class };
					}
				});
	    	*/
	    	/*
	    	comp.addListener(
	                new MouseClickedEventAdapter(
	                        new ToggleAdapter(
	                        		new RotateActionBoolean(comp, aspectRotationAngle, SCALE_DURATION))));
	   */
			
		}
		
		/**
		 * moves this Folder to the upright position if the argument is true.
		 * @param raised
		 */
		private void raise(boolean raised) {
			if (raised) {
				inner.changeRotationAxis(0.0f, 0.0f, 0.0f, SCALE_DURATION);

				inner.changeRotationAngle(bodyAngle);
				inner.changeTranslation(0.0f, discSize * 0.8f, discSize * 0.4f);
				inner.changeScale(1.2f);
			} else {
				inner.changeRotationAngle(0.0f);
				inner.changeTranslation(0.0f, discSize, 0.0f);
				inner.changeScale(1.0f);
			}
		}

	/**
	 * set messages in a folder. A number of messages is size.
	 * @param size
	 */
		public void setMessageInFolder(int size) {
			FolderContainer3D fc = new FolderContainer3D();
			messageCon = new Container3D();
			messageCon.setMouseEventPropagatable(true);
			messageCon.addChild(fc.createLetter(size));
			
			cube.addChild(messageCon);
		}

		/**
		 * sets a folder event adapter.
		 *
		 */
		private void initEventAdapter() {

			addListener(new MouseClickedEventAdapter(new ActionNoArg() {
				public void performAction(LgEventSource source) {
					rearrangeFolders((Folder) source);
				}
			}));

			
			addListener(new MouseClickedEventAdapter(
					MouseButtonEvent3D.ButtonId.BUTTON1, new ActionNoArg() {
						public void performAction(LgEventSource source) {
							Vector3f temp = getTranslation(new Vector3f());
							
						//	System.out.println("Mail is stacked " + mailStacked);
						//	System.out.println("Y Position " + raisedYPos);
							
							if (null != source
									&& (raisedYPos >= temp.z - 0.002f)
									&& (raisedYPos <= temp.z + 0.002f)
									&& messageStacked) {
							//	System.out.println("Message Show in FolderViewer 647");
							
								//showMessages();

							} else if (null != source) {

							//	System.out.println("Folder Clicekd underneath");
								focused = true;
								changeRotationAxis(new Vector3f(0.0f, 0.0f,
										0.01f));
								changeTranslation(0.0f, -0.05f, raisedYPos,
										2 * SCALE_DURATION / 3);
								changeScale(2.0f);
								
							}
						}
					}));
			
			setMouseEventPropagatable(true);
		}

		/**
		 * sets boolean whether folder has messages or not.
		 * @param messageStacked
		 */
		public void setMessageStacked(boolean mailStacked) {
			this.messageStacked = mailStacked;
		}

		/**
		 * returns boolean whether folder has message or not.
		 * @return messageStacked
		 */
		public boolean isMessageStacked() {
			return this.messageStacked;
		}

		/**
		 * get message folder container.
		 * @return messageCon
		 */
		public Container3D getFakeCon() {
			return this.messageCon;
			//return this.inner;
		}

	}

	/**
	 * Folder layout class.
	 * @author dai
	 *
	 */
	private class FolderLayout implements LayoutManager3D {
		private Container3D cont;
		private ArrayList<Component3D> boxList = new ArrayList<Component3D>();

		private int frontFolderIndex = 0;

		public void setContainer(Container3D cont) {
			this.cont = cont;
		}

		public void layoutContainer() {
			int numDiscs = boxList.size();
			float radSeg = (float) (Math.PI / numDiscs);
			int dir = 0;
			float z = 0.01f;
			float fanSize = (focused) ? (fanSizeMax) : (fanSizeMin);

			for (int i = 0; i < numDiscs; i++) {
		//	for (int i = 0; i < 6; i++) {
				int idx = (frontFolderIndex + i) % numDiscs;
				Component3D comp = boxList.get(idx);
				
				float r = frontRad + (float) Math.PI * 2 / numDiscs * i;
				float x = 1.5f * fanSize * (float) Math.cos(r);
				float y = 1.5f * fanSize * (float) Math.sin(r);
				comp.changeTranslation(x, y, z);

				float prevAngle = comp.getRotationAngle();
				float newAngle = r - frontRad;
				float diff = newAngle - prevAngle;
			
				if (diff > Math.PI) {
					comp.setRotationAngle(prevAngle + (float) Math.PI * 2.0f);
				} else if (diff < -Math.PI) {
					comp.setRotationAngle(prevAngle - (float) Math.PI * 2.0f);
				}

				comp.changeRotationAngle(r - frontRad);
				/**
				 * Necessary for changing the current size to original size
				 */
				comp.changeScale(1.0f);
				focused = false;
				//if(focused) r = 0.0f;

				// .set(fanSizeMax, fanSizeMax, fanSizeMax);
				if (i > numDiscs / 2) {
					z += stackSpacing;
				} else if (i == numDiscs / 2) {
					z -= stackSpacing * 0.5f;
				} else {
					z -= stackSpacing;
				}

				((Folder) comp).raise((i == 0) && focused);

			}
		}

		public void addLayoutComponent(Component3D comp, Object constraints) {
			boxList.add(comp);
			comp.setRotationAxis(0.0f, 0.0f, 1.0f);
		}

		public void removeLayoutComponent(Component3D comp) {
			boxList.remove(comp);
		}

		/**
		 * Move the specified Folder to the front position.
		 */
		public boolean rearrangeLayoutComponent(Component3D selectedFolder,
				Object newConstraints) {
			int idx = boxList.indexOf(selectedFolder);
			assert (idx >= 0);
			if (frontFolderIndex == idx) {
				return false;
			}
			frontFolderIndex = idx;
			return true;
		}
	}

}
