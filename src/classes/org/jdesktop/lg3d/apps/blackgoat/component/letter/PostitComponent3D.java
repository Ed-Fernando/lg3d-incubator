package org.jdesktop.lg3d.apps.blackgoat.component.letter;

import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.sg.Shape3D;
import org.jdesktop.lg3d.sg.Texture;
import org.jdesktop.lg3d.sg.TextureAttributes;
import org.jdesktop.lg3d.utils.action.ActionInt;
import org.jdesktop.lg3d.utils.action.ActionNoArg;
import org.jdesktop.lg3d.utils.action.AppearanceChangeAction;
import org.jdesktop.lg3d.utils.action.ScaleActionBoolean;
import org.jdesktop.lg3d.utils.c3danimation.NaturalMotionAnimation;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MouseEnteredEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MouseWheelEventAdapter;
import org.jdesktop.lg3d.utils.shape.FuzzyEdgePanel;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.Component3D;
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
 * postit component class.
 */
public class PostitComponent3D extends Component3D {

	private FuzzyEdgePanel body;

	private Texture texture;

	private TextureAttributes ta;

	private Appearance bodyApp;

	private int SCALE_DURATION = 1000;

	// private Vector3f size;
	private float red;

	private float green;

	private float blue;

	private static int wheelUp = -1;

	private static int wheelDown = 1;

	private int count;

	/**
	 * constructs postit size and its color. color is set randamly now.
	 * TODO enbles user to decide postit color.
	 * @param width
	 * @param hegith
	 */
	public PostitComponent3D(float width, float height) {
		// this.size = size;

		red = (float) Math.random();
		green = (float) Math.random();
		blue = (float) Math.random();

		/*
		 * red = 0.6f; green = 0.6f; blue = 1.0f;
		 */
		bodyApp = new SimpleAppearance(red, green, blue, 0.9f);
		bodyApp.setCapability(Appearance.ALLOW_TEXTURE_WRITE);
		bodyApp.setCapability(Appearance.ALLOW_TEXGEN_WRITE);
		bodyApp.setCapability(Appearance.ALLOW_TEXTURE_ATTRIBUTES_WRITE);

		body = new FuzzyEdgePanel(width, height, 0.001f, bodyApp);
		body.setCapability(Shape3D.ALLOW_APPEARANCE_WRITE);
		body.setCapability(Shape3D.ALLOW_APPEARANCE_READ);

		initEventAdapter();

		addChild(body);
	}

	/**
	 * sets event action which changes postit color by wheeling.
	 *
	 */
	private void initEventAdapter() {
		setAnimation(new NaturalMotionAnimation(SCALE_DURATION));

		addListener(new MouseEnteredEventAdapter(new ScaleActionBoolean(this,
				1.1f, SCALE_DURATION / 2)));

		addListener(new MouseClickedEventAdapter(ButtonId.BUTTON1,
				new ActionNoArg() {
					public void performAction(LgEventSource source) {
						System.out.println("Source Class1 : "
								+ source.getClass().toString());
					}
				}));

		/**
		 * Change color
		 */

		addListener(new LgEventListener() {
			public void processEvent(LgEvent evt) {
				// System.out.println("Source Class3 : " +
				// evt.getClass().toString());
				assert (evt instanceof MouseEvent3D);

				MouseEvent3D me = (MouseEvent3D) evt;

				if (me instanceof MouseWheelEvent3D) {
					MouseWheelEvent3D mwe = (MouseWheelEvent3D) me;
					count++;
					/**
					 * Wheel down getWheelRotation return 1 Wheel up
					 * getWheelRotation return -1
					 */
					// System.out.println("Wheel Color " +
					// mwe.getWheelRotation());
					if (wheelUp == mwe.getWheelRotation()) {
						// System.out.println("Up");
						if (0 == calcDeli(count))
							red += 0.1f;
						if (1 == calcDeli(count))
							green += 0.1f;
						if (2 == calcDeli(count))
							blue += 0.1f;

					}

					if (wheelDown == mwe.getWheelRotation()) {
						// System.out.println("Down");
						if (0 == calcDeli(count))
							red -= 0.1f;
						if (1 == calcDeli(count))
							green -= 0.1f;
						if (2 == calcDeli(count))
							blue -= 0.1f;

					}
					// body.getAppearance().getMaterial().setAmbientColor(red,
					// green, blue);
					setColor(new Color3f(red, green, blue));
				}
			}

			public Class<LgEvent>[] getTargetEventClasses() {
				return new Class[] { MouseEvent3D.class };
			}
		});

	}

	/**
	 * calculates value count % 3.
	 * @param count
	 * @return count % 3
	 */
	private int calcDeli(int count) {
		return count % 3;
	}

	/**
	 * returns postit color.
	 * @return new Color3f(red, green, blue)
	 */
	public Color3f getColor() {
		return new Color3f(red, green, blue);
	}

	/**
	 * sets postit color.
	 * @param red
	 * @param green
	 * @param blue
	 */
	public void setColor(float red, float green, float blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
		body.getAppearance().getMaterial().setAmbientColor(red, green, blue);
	}
	

	/**
	 * sets postit color.
	 * @param color
	 */
	public void setColor(Color3f color) {
		this.red = color.x;
		this.green = color.y;
		this.blue = color.z;
		body.getAppearance().getMaterial().setAmbientColor(red, green, blue);

	}

}
