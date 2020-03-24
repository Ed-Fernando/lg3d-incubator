package org.jdesktop.lg3d.apps.blackgoat.component.letter;

import java.io.IOException;

import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.apps.blackgoat.component.folder.ALetterContainer3D;
import org.jdesktop.lg3d.apps.blackgoat.component.preference.MessageContentPanel;
import org.jdesktop.lg3d.apps.blackgoat.emessage.write.EMessageWritable;
import org.jdesktop.lg3d.apps.blackgoat.emessage.write.EMessageWriter;
import org.jdesktop.lg3d.apps.blackgoat.utils.UserInfo;
import org.jdesktop.lg3d.scenemanager.utils.decoration.TextPanel;
import org.jdesktop.lg3d.utils.action.ActionNoArg;
import org.jdesktop.lg3d.utils.action.ScaleActionBoolean;
import org.jdesktop.lg3d.utils.c3danimation.NaturalMotionAnimation;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MouseEnteredEventAdapter;
import org.jdesktop.lg3d.utils.shape.FuzzyEdgePanel;
import org.jdesktop.lg3d.utils.shape.GlassyPanel;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.SwingNode;
import org.jdesktop.lg3d.wg.event.LgEvent;
import org.jdesktop.lg3d.wg.event.LgEventListener;
import org.jdesktop.lg3d.wg.event.LgEventSource;
import org.jdesktop.lg3d.wg.event.MouseButtonEvent3D;
import org.jdesktop.lg3d.wg.event.MouseEvent3D.ButtonId;

/**
 * @author Dai Odahara
 */
/**
 * this class is created when replying or forwarding message.
 */
public class ReplyForwardComponent3D extends Component3D {
	 
	 private ALetterContainer3D aLetterCon;
	 private LetterComponent3D[] letterComps;
	private MessageContentPanel mcp;
	private float width;
	private float height;
//	private float depth;
	private int SCALE_DURATION = 1800;
	private int rorationAngle = 0;
	private int currentPageNumber;
	
	private boolean isNew;

	/**
	 * changes this object's visible false at first.
	 */
	public ReplyForwardComponent3D(ALetterContainer3D aLetterCon, int currentPageNumber){
		
		//System.out.println("NEW");
		this.aLetterCon = aLetterCon;
		letterComps = aLetterCon.getAllLetterComponent3D();
		this.currentPageNumber = currentPageNumber;

		/**
		 * TODO modify
		 */
		
		this.width = letterComps[0].getSize().x;
		this.height = letterComps[0].getSize().y;
		//this.depth = letterComp[0].

		/**
		 * TODO Does following apps be nesessary?
		 */
		SimpleAppearance bodyApp
		= new SimpleAppearance(
		    0.7f, 0.7f, 1.0f, 0.7f, SimpleAppearance.ENABLE_TEXTURE
			| SimpleAppearance.DISABLE_CULLING);

		
		SimpleAppearance writeBodyApp
		= new SimpleAppearance(
		    0.6f, 1.0f, 0.6f, 1.0f, SimpleAppearance.ENABLE_TEXTURE
			| SimpleAppearance.DISABLE_CULLING);

		
		isNew = true;
		changeVisible(true);		
		setMouseEventPropagatable(true);
	 	setRotationAxis(0.0f, 1.0f, 0.0f);
	 	changeRotationAngle((float)Math.toRadians(180));	        
	 	setAnimation(new NaturalMotionAnimation(SCALE_DURATION));
		
	 	 FuzzyEdgePanel body 
         = new FuzzyEdgePanel(width - 0.0002f, height - 0.0002f, 0.0001f, bodyApp);
		
	 	
	 	GlassyPanel bodyDeco
        = new GlassyPanel(width, height, 0.002f, writeBodyApp);
		  	
	 	Component3D bodyComp = new Component3D();
	 	bodyComp.addChild(bodyDeco);
		//addChild(bodyComp);
		addChild(body);
		
		createMessagePanel();
		createMessageButton();
		initEventAdapter();
	}
	
	/**
	 * creates message swing panel.
	 *
	 */
	private void createMessagePanel(){
		SimpleAppearance bodyApp
		= new SimpleAppearance(
		    0.7f, 0.7f, 1.0f, 0.7f, SimpleAppearance.ENABLE_TEXTURE
			| SimpleAppearance.DISABLE_CULLING);

			
		  FuzzyEdgePanel body 
         = new FuzzyEdgePanel(width, height, 0.0001f, bodyApp);
		 // 	initFuzzyCapability(body);
		  	
		  SwingNode contentComp = new SwingNode();
    	  contentComp.setPreferredSize(new Vector3f(0.03f, 0.03f, 0.03f));
	  	
    	  	mcp = new MessageContentPanel();
    	  //	String messageContent;
    	  StringBuffer sb = new StringBuffer();
    	//  LetterComponent3D[] letterComp = this.aLetterCon.getLetterComp();
    	  
    	  
    	  for(int i = 0; i < letterComps.length; i++) {
    		 // System.out.println(letterComps[i].getMessageContent());
    		  sb.append(letterComps[i].getMessageContent());
    	  }
    	  	if( null == sb.toString()) {
    	  		System.err.println("Original Messag is Null");
    	  	}
    	  	
    	  	mcp.setContentAsReply(sb.toString());
    	  	
	  		//contentComp.setSize(100, 100);
    	  	contentComp.setPanel(mcp);
	   		
	   		contentComp.changeTranslation(0.0f, 0.0f, 0.001f);
	   		contentComp.setAnimation(new NaturalMotionAnimation(SCALE_DURATION));		
	  
	  		addChild(contentComp);	
	  		
	}

	/**
	 * creates message send button.
	 *
	 */
	private void createMessageButton() {
		Component3D send = new Component3D();
		//send.setMouseEventPropagatable(true);
		
		SimpleAppearance app
			= new SimpleAppearance(1.0f, 0.5f, 0.5f, 1.0f, SimpleAppearance.ENABLE_TEXTURE
										| SimpleAppearance.DISABLE_CULLING);

		 GlassyPanel body
         = new GlassyPanel(0.02f, 0.01f, 0.02f, app);
		  	
		 TextPanel sendText = new TextPanel("Send", 0.5f, 0.03f,
					0.02f, 1, 1, false);
		 
		 send.addChild(sendText);
		 //send.addChild(body);
		 
		// System.out.println(" X = " + width + " Y = " + height);
		 send.changeTranslation(width * ( -7.3f/16.0f), height * ( 5.0f/16.0f), 0.005f);
	
		 send.addListener(new MouseEnteredEventAdapter(
		             new ScaleActionBoolean(send, 1.1f, SCALE_DURATION)));
		 send.addListener(new MouseClickedEventAdapter(ButtonId.BUTTON1,
					new ActionNoArg() {
			 	public void performAction(LgEventSource source) {
			 		if(null == source) return;
			 		
			 		sendMessage();
				
			 	}
			 }));
		 addChild(send);
	}
	
	/**
	 * sends message and turns over message panel.
	 *
	 */
	private void sendMessage(){
		/*
		System.out.println("To : " + mcp.getTo());
		System.out.println("Cc : " + mcp.getCc());
		System.out.println("Bcc : " + mcp.getBcc());
		System.out.println("Content : " + mcp.getContent());
	*/
		String aSmtpServerIp = "133.87.134.3";
		String aSmtpServerName = "poplar.complex.eng.hokudai.ac.jp";
		String aSenderAccount = "d_odahara@complex.eng.hokudai.ac.jp";
		String aSenderName = "Dai Odahara";
		
		UserInfo info = UserInfo.getInstance();

		//EMessageWritable ew = new EMessageWriter(aSmtpServerIp, aSmtpServerName, aSenderAccount, aSenderName);
		EMessageWritable ew = new EMessageWriter(info.getSmtpServer(), info.getSmtpUserName());
	//	System.out.println(info.getSmtpServer());
		
	//	System.out.println(info.getSmtpUserName());
		//String to = "d_odahara@poplar.complex.eng.hokudai.ac.jp";
	//	String to = "gameldar@gmail.com";
	//	String subject = "Hello";
	//	String content = "Does it work well??? Really????";
		
		if(ew.send(mcp.getTo(), mcp.getCc(), mcp.getBcc(), mcp.getSubject(), mcp.getContent())){
	//		System.out.println("message sent sucess");
				changeVisible(false);
			
		//		float angle = letterComps[currentPageNumber].getRotationAngle() % 360;
			//	letterComps[currentPageNumber].setRotationAngle((float)Math.toRadians(angle));
				
				letterComps[currentPageNumber].changeRotationAngle((float)Math.toRadians(0), SCALE_DURATION);
				letterComps[currentPageNumber].changeVisible(true);
				aLetterCon.setReplyForwardComponent3D(null);
				
		} else {
			System.err.println("Message Send Fail...");
		}
	
	}
	
	/**
	 * sets event action.
	 * TODO review.
	 *
	 */
	private void initEventAdapter() {
		//System.out.println("Done!!!!");
		
		addListener(new LgEventListener() {
			public void processEvent(LgEvent evt) {
				//System.out.println("Here you are");
				assert (evt instanceof MouseButtonEvent3D);
				if( null == evt )
					return;
				
				MouseButtonEvent3D me = (MouseButtonEvent3D)evt;
			//	System.out.println("Here you are");
				
				if( me.isClicked()
						&& me.getButton() == MouseButtonEvent3D.ButtonId.BUTTON1) {
					//System.out.println("Now : " + rorationAngle);
				//		if(!first)angleWrite = 0;
				//	first = true;
				//	writeComp.changeRotationAngle((float)Math.toRadians(angleWrite), SCALE_DURATION);
					//angleWrite -= 180;
					rorationAngle -= 180;
					
					changeRotationAngle((float)Math.toRadians(rorationAngle), SCALE_DURATION);
				
					changeVisible(false);
			//		square.changeVisible(false);
				//	readComp.changeVisible(true);

				}
				
			}

			public Class<LgEvent>[] getTargetEventClasses() {
				return new Class[] { MouseButtonEvent3D.class };
			}
		});

	}

	/**
	 * returns boolean whether this ReplyForwardComponent is new or not.
	 * @return
	 */
	public boolean isNew() {
		return isNew;
	}

	/**
	 * sets boolean whether this ReplyForwardComponent is new or not.
	 * @param isNew
	 */
	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}
	
}
