package org.jdesktop.lg3d.apps.blackgoat.component.letter;

import java.awt.image.BufferedImage;
import java.util.Date;

import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.sg.Shape3D;
import org.jdesktop.lg3d.sg.Texture;
import org.jdesktop.lg3d.sg.TextureAttributes;
import org.jdesktop.lg3d.sg.utils.image.TextureLoader;
import org.jdesktop.lg3d.utils.action.ActionNoArg;
import org.jdesktop.lg3d.utils.action.AppearanceChangeAction;
import org.jdesktop.lg3d.utils.action.RotateActionBoolean;
import org.jdesktop.lg3d.utils.action.ScaleActionBoolean;
import org.jdesktop.lg3d.utils.action.TransparencyActionBoolean;
import org.jdesktop.lg3d.utils.action.TransparencyActionFloat;
import org.jdesktop.lg3d.utils.action.TransparencyActionNoArg;
import org.jdesktop.lg3d.utils.actionadapter.ToggleAdapter;
import org.jdesktop.lg3d.utils.c3danimation.NaturalMotionAnimation;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MouseEnteredEventAdapter;
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
 * 
 * @author dai
 *
 */
/**
 * Each message's header component which shows message's sender, subject and date.
 */
public class HeaderComponent3D extends Component3D {
	private Vector3f pos;

	private BufferedImage headerImage;

	private float width;

	private int SCALE_DURATION = 1500;

	private SimpleAppearance focusOn;

	private SimpleAppearance focusOff;

	private String from;

	private String subject;

	private Date date;

	private int messageId;

	private int letterArrayIndex;

	/**
	 * sets HeaderComponent3D image, its width and its event action.
	 * @param headerImage
	 * @param width
	 */
	public HeaderComponent3D(BufferedImage headerImage, float width) {
		this.headerImage = headerImage;
		this.width = width;
		setAnimation(new NaturalMotionAnimation(SCALE_DURATION));

		initAppearance();
		initEventAdapter();
	}

	/**
	 * creates header component image and sets header's background color
	 *
	 */
	private void initAppearance() {
		focusOff = new SimpleAppearance(0.3f, 0.3f, 1.0f, 0.6f,
				SimpleAppearance.ENABLE_TEXTURE
						| SimpleAppearance.DISABLE_CULLING);

		focusOn = new SimpleAppearance(0.4f, 0.4f, 1.0f, 0.8f,
				SimpleAppearance.ENABLE_TEXTURE
						| SimpleAppearance.DISABLE_CULLING);

		TextureLoader loader = new TextureLoader(headerImage);
		Texture texture = loader.getTexture();
		TextureAttributes ta = new TextureAttributes();
		// ta.setTextureBlendColor(0.0f, 0.0f, 0.9f, 0.4f);
		/** DECAL IS Nesessary */
		ta.setTextureMode(TextureAttributes.DECAL);

		focusOff.setTextureAttributes(ta);
		focusOff.setTexture(texture);

		FuzzyEdgePanel body = new FuzzyEdgePanel(width * 0.9f, 0.015f, 0.002f,
				focusOff);
		addChild(body);
	}

	/**
	 * sets event action.
	 *
	 */
	private void initEventAdapter() {
		addListener(new MouseEnteredEventAdapter(new ScaleActionBoolean(this,
				1.05f, 500)));

		addListener(new MouseClickedEventAdapter(
				MouseButtonEvent3D.ButtonId.BUTTON1, new ToggleAdapter(
						new RotateActionBoolean(this, (float) Math.PI * 4.0f,
								1000))));

		addListener(new MouseEnteredEventAdapter(new TransparencyActionBoolean(
				this, 0.1f)));

	}

	/**
	 * returns current position on the MessageViewer.
	 * @return pos
	 */
	public Vector3f getPosition() {
		return this.pos;
	}

	/**
	 * sets current position on the MessageViewer.
	 * @param pos
	 */
	public void setPosition(Vector3f pos) {
		this.pos = pos;
	}

	/**
	 * returns one of message headers, from.
	 * @return from
	 */
	public String getFrom() {
		return from;
	}

	/**
	 * sets one of message headers, from.
	 * @param from
	 */
	public void setFrom(String from) {
		this.from = from;
	}

	/**
	 * return one of message headers, subject.
	 * @return subject.
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * sets one of message headers, subject.
	 * @param subject
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * returns one of message headers, date.
	 * @return date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * sets one of message headers,date.
	 * @param date
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * returns unique message Id.
	 * TODO goes to ALetterContainer3D class.
	 * @return messageId
	 */
	public int getMessageId() {
		return this.messageId;
	}

	/**
	 * sets unique message Id.
	 * TODO goes to ALtterContainer3D class.
	 * @param messageId
	 */
	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	/**
	 * returns current shown message order on the MessageViewer.
	 * TODO goes to ALtterContainer3D
	 * @return
	 */
	public int getLetterArrayIndex() {
		return letterArrayIndex;
	}

	/**
	 * sets current shown message order on the MessageViewer.
	 * TODO goes to ALtterContainer3D
	 * @param letterArrayIndex
	 */
	public void setLetterArrayIndex(int letterArrayIndex) {
		this.letterArrayIndex = letterArrayIndex;
	}
}
