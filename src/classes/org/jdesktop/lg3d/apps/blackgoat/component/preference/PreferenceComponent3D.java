package org.jdesktop.lg3d.apps.blackgoat.component.preference;

import java.net.URL;
import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.apps.blackgoat.button.ButtonAppearance;
import org.jdesktop.lg3d.apps.blackgoat.utils.UserInfo;
import org.jdesktop.lg3d.sg.Shape3D;
import org.jdesktop.lg3d.utils.action.ActionNoArg;
import org.jdesktop.lg3d.utils.action.AppearanceChangeAction;
import org.jdesktop.lg3d.utils.action.RotateActionBoolean;
import org.jdesktop.lg3d.utils.actionadapter.ToggleAdapter;
import org.jdesktop.lg3d.utils.c3danimation.NaturalMotionAnimation;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MouseEnteredEventAdapter;
import org.jdesktop.lg3d.utils.shape.FuzzyEdgePanel;
import org.jdesktop.lg3d.utils.shape.GlassyPanel;
import org.jdesktop.lg3d.utils.shape.ImagePanel;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.wg.Cursor3D;
import org.jdesktop.lg3d.wg.SwingNode;
import org.jdesktop.lg3d.wg.event.LgEventSource;
import org.jdesktop.lg3d.wg.event.MouseButtonEvent3D;

/**
 * @author dai
 * 
 */
/**
 * mailer setting class such as smtp saver name, smtp user account, pop server name,
 * pop user account name, pop user password.
 */
public class PreferenceComponent3D extends Component3D {
	private URL okImage = getClass().getResource("/resources/images/blackgoat/util/ok.png");

	private int SCALE_DURATION = 1000;

	//private static float buttonSize = 0.005f;

	//private static float buttonOnSize = buttonSize * 1.15f;

//	private Container3D mainCon;

	private boolean upFlag = false;

	private float posX;

	private float posY;

	private float posZ;
	
	private float sizeOff;
	
	private float sizeOn;

	private Container3D preferenceCon;

	//private int rotationAngle;

	private Component3D popComp;
	
	//private Component3D smtpComp;
	
	private Component3D shapeComp;

	private Component3D okComp;

	private UserInfo info;

	private PreferencePanel pf;

	private static SimpleAppearance panelOffAppearance=null;

	private static SimpleAppearance panelOnAppearance=null;

	static {

	}

	/**
	 * constructs mailer setting panel.
	 * @param sizeOff
	 * @param sizeOn
	 * @param posX
	 * @param posY
	 * @param posZ
	 */
	public PreferenceComponent3D(float sizeOff, float sizeOn, float posX, float posY, float posZ) {

		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		this.sizeOff = sizeOff;
		this.sizeOn = sizeOn;
		
                if (panelOffAppearance==null) {
                    panelOffAppearance = new ButtonAppearance(
				getClass().getResource("/resources/images/blackgoat/util/property.png"), false, 0);
                    panelOnAppearance = new ButtonAppearance(
				getClass().getResource("/resources/images/blackgoat/util/property.png"), true, 0);
                }

                
		setCursor(Cursor3D.SMALL_CURSOR);
		
		preferenceCon = new Container3D();
		preferenceCon.setMouseEventPropagatable(true);
		preferenceCon.setAnimation(new NaturalMotionAnimation(SCALE_DURATION));

		addChild(preferenceCon);
		
		info = UserInfo.getInstance();

		setTranslation(posX, posY, posZ);

		/**
		 * goat with mail image
		 */
		Shape3D shape = new ImagePanel(sizeOff, sizeOff);
		shape.setAppearance(panelOffAppearance);
		shapeComp = new Component3D();

		shapeComp.addChild(shape);
		addChild(shapeComp);
		if (panelOffAppearance != panelOnAppearance) {
			addListener(new MouseEnteredEventAdapter(
					new AppearanceChangeAction(shape, panelOnAppearance)));
		}

		createPopComponent();
	//	createSmtpComponent();
		initEventAdapter();
		
	}
	
	/**
	 * creates 
	 *
	 */
	private void createPopComponent() {
			/**
			 * popComponent3D
			 */
			popComp = new Component3D();
			popComp.setMouseEventPropagatable(true);
			popComp.setAnimation(new NaturalMotionAnimation(SCALE_DURATION));

			SimpleAppearance backApp = new SimpleAppearance(1.0f, 1.0f, 1.0f, 0.9f,
					SimpleAppearance.ENABLE_TEXTURE
							| SimpleAppearance.DISABLE_CULLING);
			GlassyPanel body = new GlassyPanel(sizeOff * 10.0f, sizeOff * 6.0f,
					0.001f, 0.0f, backApp);
			popComp.addChild(body);

			okComp = new Component3D();
			SimpleAppearance okApp = new SimpleAppearance(0.5f, 0.5f, 1.0f, 1.0f,
					SimpleAppearance.ENABLE_TEXTURE
							| SimpleAppearance.DISABLE_CULLING);

			try {
				okApp.setTexture(okImage);
			} catch (Exception e) {
				System.out.println("Error Message is in Setting Goat Image:\n"
						+ e.toString());
				throw new RuntimeException(e);
			}

			FuzzyEdgePanel okBody = new FuzzyEdgePanel(sizeOff * 1.5f,
					sizeOff * 0.5f, 0.001f, okApp);

			okComp.addChild(okBody);
			okComp.setTranslation(0.0f, -0.035f, 0.002f);
			popComp.addChild(okComp);
			// Default
			popComp.changeVisible(false);

			SwingNode swingNode = new SwingNode();
			swingNode.setPreferredSize(new Vector3f(sizeOff * 5.0f, sizeOff * 5.0f,
					0.001f));
			pf = new PreferencePanel();
			swingNode.setPanel(pf);
			popComp.addChild(swingNode);
			preferenceCon.addChild(popComp);

			
		}
/*
		private void createSmtpComponent() {
			
			//  smtpComponent3D
			 

			//System.out.println("HERERE");
			smtpComp = new Component3D();
			smtpComp.setMouseEventPropagatable(true);
			smtpComp.setAnimation(new NaturalMotionAnimation(SCALE_DURATION));

			smtpComp.changeRotationAngle((float)Math.toRadians(180));
			SimpleAppearance backApp = new SimpleAppearance(1.0f, 1.0f, 1.0f, 0.9f,
					SimpleAppearance.ENABLE_TEXTURE
							| SimpleAppearance.DISABLE_CULLING);
			GlassyPanel body = new GlassyPanel(sizeOff * 6.0f, sizeOff * 6.0f,
					0.001f, 0.0f, backApp);
			smtpComp.addChild(body);

			okComp = new Component3D();
			SimpleAppearance okApp = new SimpleAppearance(0.5f, 0.9f, 0.6f, 1.0f,
					SimpleAppearance.ENABLE_TEXTURE
							| SimpleAppearance.DISABLE_CULLING);

			try {
				okApp.setTexture(okImage);
			} catch (Exception e) {
				System.out.println("Error Message is in Setting Goat Image:\n"
						+ e.toString());
				throw new RuntimeException(e);
			}

			FuzzyEdgePanel okBody = new FuzzyEdgePanel(sizeOff * 1.5f,
					sizeOff * 0.5f, 0.001f, okApp);

			okComp.addChild(okBody);
			okComp.setTranslation(0.0f, -0.035f, 0.002f);
			smtpComp.addChild(okComp);
			// Default
			smtpComp.changeVisible(false);

			
			SwingNode swingNode = new SwingNode();
			swingNode.setPreferredSize(new Vector3f(sizeOff * 5.0f, sizeOff * 5.0f,
					0.001f));
			pf = new PreferencePanel();
			swingNode.setPanel(pf);
			smtpComp.addChild(swingNode);
			
			preferenceCon.addChild(smtpComp);	
		}
*/		
	/**
	 * set event action.
	 */
	public void initEventAdapter() {

		setCursor(Cursor3D.SMALL_CURSOR);
		setAnimation(new NaturalMotionAnimation(SCALE_DURATION));
		popComp.setAnimation(new NaturalMotionAnimation(SCALE_DURATION));
		shapeComp.setAnimation(new NaturalMotionAnimation(SCALE_DURATION));

		setMouseEventPropagatable(true);

		shapeComp.addListener(new MouseClickedEventAdapter(
				MouseButtonEvent3D.ButtonId.BUTTON1, new ToggleAdapter(
						new RotateActionBoolean(popComp, (float) Math.PI * 6.0f,
								SCALE_DURATION))));

		shapeComp.addListener(new MouseClickedEventAdapter(new ActionNoArg() {
			public void performAction(LgEventSource source) {
				if (null != source) {
					if (upFlag == false) {

						/*
						info.setPopServer("");
						info.setPopUserName("");
						info.setPopPassword("");
						*/
						changeTranslation(0.1f, 0.06f, 0.005f, SCALE_DURATION);

						shapeComp.changeVisible(false);
						popComp.changeVisible(true);

						upFlag = true;

					}
				}
			}
		}));

		okComp.addListener(new MouseClickedEventAdapter(new ActionNoArg() {
			public void performAction(LgEventSource source) {
				if (null != source) {
					if (upFlag == true) {
						info.setPopServerIp(pf.getPopServerName());
						info.setPopUserName(pf.getPopUserName());
						info.setPopPassword(pf.getPopUserPassword());
						info.setSmtpServer(pf.getSmtpServerIp());
						info.setSmtpUserName(pf.getSmtpUserAccount());
						
						changeScale(1.0f, SCALE_DURATION);
						setMouseEventEnabled(true);
						changeTranslation(posX, posY, posZ, SCALE_DURATION);

						popComp.changeVisible(false);
						shapeComp.changeVisible(true);

						upFlag = false;
/*
						rotationAngle -= 180;
						preferenceCon.changeRotationAngle((float)Math.toRadians(rotationAngle), SCALE_DURATION);
						popComp.changeVisible(false);
						smtpComp.changeVisible(true);
	*/
					}
				}
			}
		}));

		okComp.addListener(new MouseClickedEventAdapter(
				MouseButtonEvent3D.ButtonId.BUTTON1, new ToggleAdapter(
						new RotateActionBoolean(shapeComp,
								(float) Math.PI * 4.0f, SCALE_DURATION))));

	}
}
