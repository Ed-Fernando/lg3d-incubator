package org.jdesktop.lg3d.apps.blackgoat.component.letter;

import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.utils.shape.FuzzyEdgePanel;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.Component3D;
/**
 * @author dai
 */
/**
 * this class show one letter in a message folder.
 */
public class SmallLetterComponent3D extends Component3D {
	private Vector3f pos;

	private boolean isPostit;

	private PostitComponent3D postit;

	private int messageId;

	private float width;

	private float height;

	private int readCount;

	private FuzzyEdgePanel body;

	/**
	 * constructs SmallLetterComponent. sets its messageId, width and height.
	 * @param messageId
	 * TODO does this class need messageId??
	 * @param width
	 * @param height
	 */
	public SmallLetterComponent3D(int messageId, float width, float height) {
		this.messageId = messageId;
		this.width = width;
		this.height = height;
		SimpleAppearance app = new SimpleAppearance(0.9f, 0.9f, 1.0f, 1.0f, SimpleAppearance.DISABLE_CULLING);
		body = new FuzzyEdgePanel(width, height, 0.0f, app);
		addChild(body);
	}

	/**
	 * set a postit.
	 *
	 */
	public void doPostit() {

		if (isPostit) {
			// System.out.println("Letter Pressed!");

			unAttachPostit();
			postit.changeVisible(false);
		} else {
			/**
			 * Positit First Time Creating
			 */
			if (null == postit) {
				postit = new PostitComponent3D(width / 3.0f, height / 4.0f);
				postit.setTranslation(-width / 2.0f, height / 2.0f
						- ((messageId % 9) + 1) * height / (9 + 2), 0.0f);
				addChild(postit);
			}
			attachPostit();
			postit.changeVisible(true);

		}

	}

	/**
	 * wears message by changing its color.
	 *
	 */
	public void doWear() {
		this.readCount++;
		float wearColor = 1.0f - 0.01f * this.readCount;
		body.getAppearance().getMaterial().setAmbientColor(1.0f, 1.0f,
				wearColor);
	}

	/**
	 * sets postit
	 *
	 */
	public void attachPostit() {
		this.isPostit = true;
	}

	/**
	 * resets postit.
	 *
	 */
	public void resetPostit() {
		this.postit = null;
	}

	/**
	 * sets postit color.
	 * @param red
	 * @param green
	 * @param blue
	 */
	public void setPostitColor(float red, float green, float blue) {
		this.postit.setColor(new Color3f(red, green, blue));
	}

	/**
	 * unattach postit.
	 *
	 */
	public void unAttachPostit() {
		this.isPostit = false;
	}

	/**
	 * remove postit
	 * @param index
	 */
	public void removePostit(int index) {
		this.removeChild(index);
	}

	/**
	 * returns boolean whether this has postit or not.
	 * @return isPostit
	 */
	public boolean hasPostit() {
		return this.isPostit;
	}

	/**
	 * returns position in a message folder.
	 * @return pos
	 */
	public Vector3f getPosition() {
		return this.pos;
	}

	/**
	 * sets position in a message folder.
	 * @param pos
	 */
	public void setPosition(Vector3f pos) {
		this.pos = pos;
	}

}
