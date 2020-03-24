package nu.koidelab.cosmo.wg.shape;

import javax.vecmath.Vector3f;

import nu.koidelab.cosmo.util.TextureUtil;

import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.sg.Shape3D;
import org.jdesktop.lg3d.utils.action.ActionNoArg;
import org.jdesktop.lg3d.utils.action.AppearanceChangeAction;
import org.jdesktop.lg3d.utils.action.ScaleActionBoolean;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MouseEnteredEventAdapter;
import org.jdesktop.lg3d.utils.shape.GlassyPanel;
import org.jdesktop.lg3d.utils.shape.ImagePanel;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Cursor3D;
import org.jdesktop.lg3d.wg.event.LgEventSource;

public abstract class WindowDecoration extends Component3D {
	private static final float BODY_DEPTH = 0.003f;
    private static final float DECO_HEIGHT_SOUTH = 0.001f;
    private static final float DECO_HEIGHT_NORTH = 0.005f;
    private static final float DECO_WIDTH = 0.001f;
	private static float buttonSize = 0.003f;
	private static float buttonOnSize = buttonSize * 1.15f;
	private static float shadowN = 0.001f;
	private static float shadowE = 0.0015f;
	private static float shadowS = 0.002f;
	private static float shadowW = 0.001f;
	private static float shadowI = 0.001f;
	private SimpleAppearance closeButtonOffAppearance;
	private SimpleAppearance closeButtonOnAppearance;
	private SimpleAppearance minimizeButtonOffAppearance;
	private SimpleAppearance minimizeButtonOnAppearance;

	public WindowDecoration(float w, float h, String title) {
		this(w, h, title, true);
	}
	
	public WindowDecoration(float w, float h, String title, boolean minimizable) {
		setName("Window Decoration");
				
		float width = w + DECO_WIDTH*2;
		float height = h + DECO_HEIGHT_SOUTH + DECO_HEIGHT_NORTH;
		float xShift = 0f;
		float yShift = (DECO_HEIGHT_NORTH - DECO_HEIGHT_SOUTH)/2;
		float zShift = -0.0001f;// 1mm
		
		Appearance bodyApp = new SimpleAppearance(0.6f, 1.0f, 0.6f, 1.0f, SimpleAppearance.DISABLE_CULLING);
		GlassyPanel bodyDeco = new GlassyPanel(width, height, BODY_DEPTH, BODY_DEPTH*0.25f, xShift, yShift, zShift, bodyApp);

		Shape3D bodyShadow = new RectShadow(width, height,
                shadowN, shadowE, shadowS, shadowW, shadowI,
				xShift, yShift, -BODY_DEPTH + zShift, 0.2f);
        setPreferredSize(new Vector3f(width, height, BODY_DEPTH - zShift));

		Component3D comp = new Component3D();
		comp.addChild(bodyDeco);
		comp.addChild(bodyShadow);
		addChild(comp);

		
		/* ============ initialize buttons ============ */
		initButtonAppearances();
		
		float buttonTransX = width*0.5f - buttonSize;
		float buttonTransY = height*0.5f - buttonSize + yShift;

		Component3D closeButton = new Button(buttonSize,
				closeButtonOffAppearance, buttonOnSize, closeButtonOnAppearance);
		closeButton.setCursor(Cursor3D.SMALL_CURSOR);
		closeButton.setTranslation(buttonTransX, buttonTransY, 0);
		closeButton.addListener(new MouseClickedEventAdapter(new ActionNoArg() {
			public void performAction(LgEventSource source) {
				closeAction();
			}
		}));
		addChild(closeButton);
		
		if (minimizable) {
			Component3D minimizeButton = new Button(buttonSize,
					minimizeButtonOffAppearance, buttonOnSize,
					minimizeButtonOnAppearance);
			minimizeButton.setCursor(Cursor3D.SMALL_CURSOR);
			minimizeButton.setTranslation(buttonTransX - buttonSize * 1.4f,
					buttonTransY + buttonSize * 0.15f, 0);
			minimizeButton.addListener(new MouseClickedEventAdapter(
					new ActionNoArg() {
						public void performAction(LgEventSource source) {
							minimizeAction();
						}
					}));
			addChild(minimizeButton);
		}
	}
	
	/**
     * This method is called when close button is pressed. 
     * You can describe processes here such as "frame3d#changeEnable(false)".
	 */
    public abstract void closeAction();
    
    /**
     * This method is called when minimize button is pressed. 
     * You can describe processes here such as "frame3d#changeVisble(false)".
     */
	public abstract void minimizeAction();		
	

	private void initButtonAppearances() {
		closeButtonOffAppearance = new ButtonAppearance(
				"resources/images/button/window-close.png", false);
		closeButtonOnAppearance = new ButtonAppearance(
				"resources/images/button/window-close.png", true);
		minimizeButtonOffAppearance = new ButtonAppearance(
				"resources/images/button/window-minimize.png", false);
		minimizeButtonOnAppearance = new ButtonAppearance(
				"resources/images/button/window-minimize.png", true);
	}

	private static class ButtonAppearance extends SimpleAppearance {
		private ButtonAppearance(String filename, boolean on) {
			super(0.0f, 0.0f, 0.0f, 0.0f, SimpleAppearance.DISABLE_CULLING
					| SimpleAppearance.ENABLE_TEXTURE);

			if (on) {
				setColor(1.0f, 0.6f, 0.6f, 0.8f);
			} else {
				setColor(0.6f, 1.0f, 0.6f, 0.6f);
			}
			setTexture(TextureUtil.getInstance().getTexture(filename));
		}
	}

	private class Button extends Component3D {
		private Button(float size, Appearance app) {
			this(size, app, size, app);
		}

		private Button(float sizeOff, Appearance appOff, float sizeOn,
				Appearance appOn) {
			Shape3D shape = new ImagePanel(sizeOff, sizeOff);
			shape.setAppearance(appOff);
			addChild(shape);
			if (appOff != appOn) {
				addListener(new MouseEnteredEventAdapter(
						new AppearanceChangeAction(shape, appOn)));
			}
			if (sizeOff != sizeOn) {
				addListener(new MouseEnteredEventAdapter(
						new ScaleActionBoolean(this, sizeOn / sizeOff, 100)));
			}
		}
	}	
}
