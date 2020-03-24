package org.jdesktop.lg3d.apps.blackgoat;

import java.io.IOException;
import java.net.URL;
import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.sg.Shape3D;
import org.jdesktop.lg3d.utils.action.ActionNoArg;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
//import org.jdesktop.lg3d.utils.eventadapter.MouseEnteredEventAdapter;
import org.jdesktop.lg3d.utils.shape.FuzzyEdgePanel;
import org.jdesktop.lg3d.utils.shape.GlassyPanel;
import org.jdesktop.lg3d.utils.shape.RectShadow;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Cursor3D;
import org.jdesktop.lg3d.wg.Frame3D;
import org.jdesktop.lg3d.wg.Thumbnail;
import org.jdesktop.lg3d.wg.Toolkit3D;
import org.jdesktop.lg3d.wg.event.LgEventSource;
//import org.jdesktop.lg3d.apps.blackgoat.action.PopupAction;
import org.jdesktop.lg3d.apps.blackgoat.button.*;
//import org.jdesktop.lg3d.apps.blackgoat.component.PopupTextComponent;

/**
 * Main Functin of Blackgoat.
 * @author Dai Odahara
 */
public class BlackGoat extends Frame3D {

	private URL thumbImage = getClass().getResource("/resources/images/blackgoat/icon/BlackGoatIcon.png");

	private static final float bodyDepth = 0.005f;

	private static final float decoWidth = 0.005f;
        
        private static final float thumbnailScale = 0.01f;
        
	private static Appearance bodyApp = new SimpleAppearance(0.9f, 0.9f, 0.95f,
			0.5f, SimpleAppearance.DISABLE_CULLING);

	private SimpleAppearance bodyGoat = new SimpleAppearance(1.0f, 1.0f, 1.0f,
			SimpleAppearance.ENABLE_TEXTURE | SimpleAppearance.DISABLE_CULLING);

	private float height;

	private float width;

	//private static final int SCALE_DURATION = 1000;

	private static float shadowN = 0.001f;

	private static float shadowE = 0.0015f;

	private static float shadowS = 0.002f;

	private static float shadowW = 0.001f;

	private static float shadowI = 0.001f;

	private static float buttonSize = 0.005f;

	private static float buttonOnSize = buttonSize * 1.15f;

	private static SimpleAppearance closeButtonOffAppearance=null;

	private static SimpleAppearance closeButtonOnAppearance=null;

	private static SimpleAppearance minimizeButtonOffAppearance=null;

	private static SimpleAppearance minimizeButtonOnAppearance=null;

	
	static {
	}

	public static void main(String[] args) {

		Frame3D app = new BlackGoat();
		app.changeEnabled(true);
		app.changeVisible(true);
		
	}
	
	/**
	 * Constractor. Creates MessageViewer Object and FolderViewer Object
	 *
	 */
	public BlackGoat() {
            
            if (closeButtonOffAppearance==null) {
                closeButtonOffAppearance = new ButtonAppearance(
				getClass().getResource("/resources/images/button/window-close.png"), false);
		closeButtonOnAppearance = new ButtonAppearance(
				getClass().getResource("/resources/images/button/window-close.png"), true);
		minimizeButtonOffAppearance = new ButtonAppearance(
				getClass().getResource("/resources/images/button/window-minimize.png"), false);
		minimizeButtonOnAppearance = new ButtonAppearance(
				getClass().getResource("/resources/images/button/window-minimize.png"), true);
            }


		setName("Black Goat");

		height = Toolkit3D.getToolkit3D().getScreenHeight() * 0.83f;
		width = height * 0.9f;

		setPreferredSize(new Vector3f(width, height, bodyDepth));

		initEventAdapter();

		try {
			bodyGoat.setTexture(thumbImage);
		} catch (IOException e) {
			System.out.println("Error Message is in Setting Goat Image:\n"
					+ e.toString());
			throw new RuntimeException(e);
		}

		/** Message Display */
		MessageViewer mv = new MessageViewer(width, height);
		addChild(mv);

		/** MessageBox Viewer */
		FolderViewer fv = new FolderViewer(width, height, mv);
		addChild(fv);

		/** Sample Pop Up text */
		
	   
		
		/** Thunbnail */
		setThumbnail(new HelpThumbnail(width / 2.0f, height/2.0f, bodyGoat));
	}

	/**
	 * Set event apapters, close button and minimusize button.
	 *
	 */
	private void initEventAdapter() {

		float x, y, z;
		x = width * 0.75f;// + width / 9.0f;
		y = height * 0.5f;
		z = 0.001f;

		Component3D windowComp = new Component3D();
		/* Panel Close Button */
		
		Component3D closeButton = new Button(buttonSize,
				closeButtonOffAppearance, buttonOnSize, closeButtonOnAppearance);
		closeButton.setCursor(Cursor3D.SMALL_CURSOR);
		closeButton.setTranslation(x + buttonSize * 1.3f, y, z);
		closeButton.addListener(new MouseClickedEventAdapter(new ActionNoArg() {
			public void performAction(LgEventSource source) {
				changeEnabled(false);
			}
		}));
		windowComp.addChild(closeButton);
		/* Panel Minimize Button */
	
		Component3D minimizeButton = new Button(buttonSize,
				minimizeButtonOffAppearance, buttonOnSize,
				minimizeButtonOnAppearance);
		minimizeButton.setCursor(Cursor3D.SMALL_CURSOR);
		minimizeButton.setTranslation(x, y + buttonSize * 0.2f, z);
		minimizeButton.addListener(new MouseClickedEventAdapter(
				new ActionNoArg() {
					public void performAction(LgEventSource source) {
						changeVisible(false);
					}
				}));
		windowComp.addChild(minimizeButton);
		addChild(windowComp);
	
	}

	/**
	 * HelpTumbnail class.
	 */
	private class HelpThumbnail extends Thumbnail {
		private HelpThumbnail(float width, float height, Appearance app) {
			GlassyPanel thumbnailDeco = new GlassyPanel(width + decoWidth * 2,
					height + decoWidth * 2, bodyDepth * 2, bodyDepth * 0.4f,
					bodyApp);

			Shape3D bodyShadow = new RectShadow(width + decoWidth * 2, height
					+ decoWidth * 2, shadowN * 2, shadowE * 2, shadowS * 2,
					shadowW * 2, shadowI, -bodyDepth * 2, 0.3f);

			FuzzyEdgePanel body = new FuzzyEdgePanel(width, height,
					decoWidth * 0.5f, app);

			Component3D top = new Component3D();
			top.addChild(thumbnailDeco);
			top.addChild(bodyShadow);
			top.addChild(body);
                        top.setScale(thumbnailScale);
			addChild(top);
			setPreferredSize(new Vector3f(
                                width * thumbnailScale, 
                                height * thumbnailScale, 
                                bodyDepth * thumbnailScale));

		}
	}
}
