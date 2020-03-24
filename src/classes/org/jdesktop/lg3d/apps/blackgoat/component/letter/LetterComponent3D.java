package org.jdesktop.lg3d.apps.blackgoat.component.letter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;

//import org.jdesktop.lg3d.apps.blackgoat.action.PopupAction;
import org.jdesktop.lg3d.apps.blackgoat.component.folder.ALetterContainer3D;
import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.sg.BranchGroup;
import org.jdesktop.lg3d.sg.ColoringAttributes;
import org.jdesktop.lg3d.sg.Texture;
import org.jdesktop.lg3d.sg.TextureAttributes;
import org.jdesktop.lg3d.sg.TransparencyAttributes;
import org.jdesktop.lg3d.utils.action.ActionBoolean;
import org.jdesktop.lg3d.utils.action.ActionNoArg;
import org.jdesktop.lg3d.utils.action.TransparencyActionBoolean;
import org.jdesktop.lg3d.utils.action.TransparencyActionNoArg;
import org.jdesktop.lg3d.utils.c3danimation.NaturalMotionAnimation;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MouseEnteredEventAdapter;
import org.jdesktop.lg3d.utils.shape.FuzzyEdgePanel;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.event.LgEvent;
import org.jdesktop.lg3d.wg.event.LgEventListener;
import org.jdesktop.lg3d.wg.event.LgEventSource;
import org.jdesktop.lg3d.wg.event.MouseEvent3D;
import org.jdesktop.lg3d.wg.event.MouseWheelEvent3D;
import org.jdesktop.lg3d.wg.event.MouseEvent3D.ButtonId;

/**
 * @author dai
 */
/**
 * this class represent one message page. 
 */
public class LetterComponent3D extends Component3D {

	private int pageNumber;

	// private int doodledId;

	private Vector3f position;

	private ArrayList<BranchGroup> detachableBG = new ArrayList();

	private Vector3f size;

	private boolean isPostit;

	private boolean doodled = false;

	private PostitComponent3D postit;

	private HeaderComponent3D headerComp;

	private ALetterContainer3D aLetterCon;

	private int readCount;

	private int currentDoodleOrder;

	private int totalDoodleNum;

	private ColoringAttributes previousDoodleColorAttr;

	private FuzzyEdgePanel body;

	private Appearance bodyApp;

	private Texture texture;

	private TextureAttributes ta;

	private float transparencyValue;

	private float dTransparencyValue = 0.46f;

	private static int wheelUp = 1;

	private static int wheelDown = -1;

	private boolean wheelAllowance;

	private LetterComponent3D nextLetterComponent3D;

	private LetterComponent3D prevLetterComponent3D;

	private int SCALE_DURATION = 1000;
	
	private String messageContent;

	public LetterComponent3D() {
	}

	/**
	 * constructs message information.
	 * @param pageNumber
	 * @param body
	 * @param width
	 * @param height
	 * @param depth
	 */
	public LetterComponent3D(int pageNumber, FuzzyEdgePanel body, float width,
			float height, float depth) {
		// System.out.println("Constructor");
		this.pageNumber = pageNumber;
		// this.letterArrayIndex = messageId;
		this.body = body;
		this.bodyApp = body.getAppearance();
		this.texture = body.getAppearance().getTexture();
		this.ta = body.getAppearance().getTextureAttributes();
		// this.previousDoodleColorAttr = new ColoringAttributes();
		this.transparencyValue = body.getAppearance()
				.getTransparencyAttributes().getTransparency();
		// this.dTransparencyValue = transparencyValue/4.0f;
		size = new Vector3f(width, height, depth);
		addChild(body);
		initEventAdapter();
	}

	/*
	public LetterComponent3D(int pageNumber, FuzzyEdgePanel body) {
		this.pageNumber = pageNumber;
		this.body = body;
		this.texture = body.getAppearance().getTexture();
		this.ta = body.getAppearance().getTextureAttributes();
		// body.getAppearance().getTexture().ge
		initEventAdapter();
	}
*/
	/**
	 * set event actions. when clicked with Button1, attached a postit.
	 * when mouse wheeled, change its transparency
	 */
	private void initEventAdapter() {
		setAnimation(new NaturalMotionAnimation(SCALE_DURATION));
		/** set for Postit On and Off */
		addListener(new MouseClickedEventAdapter(ButtonId.BUTTON1,
				new ActionNoArg() {
					public void performAction(LgEventSource source) {
						// System.out.println("Letter Pressed!");
						if (null == source)
							return;

						// smallComp.doPostit();
						// aLetterCon.incPostitNum();
						if (isPostit) {
							// System.out.println("Letter Pressed!");

							unAttachPostit();
							// postit.changeVisible(false);
							// postit.detach();
							removeChild(postit);
							aLetterCon.decNumPostits();
						} else {
							/** if smallletterComp's postit is null, create it */
							aLetterCon.incNumPostit();
							// aLetterCon.setPostitColorToSmallLetter(postit.getColor().x,
							// postit.getColor().y, postit.getColor().z);

							/**
							 * Positit First Time Creating
							 */
							if (null == postit) {
								postit = new PostitComponent3D(size.x / 4.0f,
										size.y / 6.0f);
								postit.setTranslation(size.x / 2.0f, size.y
										/ 2.0f - ((pageNumber % 9) + 3)
										* size.y / (9 + 2), size.z + 0.001f);
								// aLetterCon.setPostitColorToSmallLetter(postit.getColor().x,
								// postit.getColor().y, postit.getColor().z);
								// smallComp.setPostitColor(postit.getColor().x,
								// postit.getColor().y, postit.getColor().z);
								// addChild(postit);
							}
							aLetterCon.setPostitColorToSmallLetter(postit
									.getColor().x, postit.getColor().y, postit
									.getColor().z);

							attachPostit();
							// postit.changeVisible(true);
							addChild(postit);
						}
					}
				}));

		/** set for watermark */
		addListener(new LgEventListener() {
			public void processEvent(LgEvent evt) {
				// System.out.println("Source Class3 : " +
				// evt.getClass().toString());
				assert (evt instanceof MouseEvent3D);

				MouseEvent3D me = (MouseEvent3D) evt;

				if (me instanceof MouseWheelEvent3D) {
					MouseWheelEvent3D mwe = (MouseWheelEvent3D) me;
					/**
					 * Wheel down getWheelRotation return 1 Wheel up
					 * getWheelRotation return -1
					 */
					// System.out.println("Wheel Color " +
					// mwe.getWheelRotation());
					/** Neet to be modified */
					if (wheelDown == mwe.getWheelRotation()) {
						// System.out.println("UP" + transparencyValue);
						// ((TransparencyAttributes)body.getAppearance().getTransparencyAttributes()).setTransparency(transparencyValue);
						if (transparencyValue <= 0.0f)
							return;

						transparencyValue -= dTransparencyValue;

						// setTransparency(transparencyValue);
						changeTransparency(transparencyValue);

						if (transparencyValue <= 0.1f) {
							if (null != prevLetterComponent3D) {
								// System.out.println("Height = " + position.z);
								prevLetterComponent3D.changeTranslation(
										position, 100);

							}
						}
					}

					if (wheelUp == mwe.getWheelRotation()) {
						// System.out.println("Down" + transparencyValue);

						if (transparencyValue >= 1.0f)
							return;
						transparencyValue += dTransparencyValue;

						setTransparency(transparencyValue);
						/**
						 * TODO not use magic number : letter height is 0.0015
						 */
						//						
						if (transparencyValue >= 0.9f) {
							if (null != nextLetterComponent3D) {
								// changeTranslation(0.0f, 0.0f, 0.001f, 100);
								setTransparency(0.95f);
								changeTranslation(0.0f, 0.0f, 0.0f, 100);
							} else {
								// System.out.println("Last Page");
								changeTranslation(0.0f, 0.0f, 0.001f, 100);
							}
						}
					}
					// body.getAppearance().getMaterial().setAmbientColor(red,
					// green, blue);

				}

				// System.out.println("Wheel X " + mwe.getImagePlateX());
				// System.out.println("Wheel Y " + mwe.getImagePlateY());

				// System.out.println("Wheel Color " +
				// (MouseWheelEvent3D)(mwe).getWheelRotation());

			}

			public Class<LgEvent>[] getTargetEventClasses() {
				return new Class[] { MouseEvent3D.class };
			}
		});

		// addListener(new MouseEnteredEventAdapter(new
		// TransparencyActionBoolean(this, 0.0f)));
	}

	/**
	 * make this LetterComponent3D tired as this class is selected.
	 *
	 */
	public void doWear() {
		this.readCount++;
		float wearColor = 1.0f - 0.02f * this.readCount;
		// Color3f color = this.postit.getColor();
		body.getAppearance().getMaterial().setAmbientColor(1.0f, 1.0f,
				wearColor);
		// this.smallComp.doWear(color.x, color.y, color.z);

	}

	/**
	 * returns a number of read counts.
	 * @return readCont
	 */
	public long getIncrementReadCount() {
		return this.readCount;
	}

	/**
	 * attaches a postit.
	 */
	public void attachPostit() {
		this.isPostit = true;
	}

	/**
	 * unattaches a postit
	 *
	 */
	public void unAttachPostit() {
		this.isPostit = false;
	}

	/**
 	* remove postit specified by index.
 	* @param index
 	*/
	/*
	public void removePostit(int index) {
		this.removeChild(index);
	}
*/
	/**
	 * returns boolean whether this has a postit or not.
	 * @return isPostit
	 */
	public boolean hasPostit() {
		return this.isPostit;
	}

	/**
	 * returns this message size.
	 * @return size.
	 */	
	public Vector3f getSize() {
		return this.size;
	}

	/**
	 * sets doodle
	 * @param doodled
	 */
	public void setDoodled(boolean doodled) {
		this.doodled = doodled;
	}

	/**
	 * returns boolean whether this has doodles or not.
	 * @return
	 */
	public boolean isDoodled() {
		return this.doodled;
	}

	/**
	 * sets branchgroup used when doodling.
 	 * @param detachableBG
	 */
	public void setDetachableBrachGroup(BranchGroup detachableBG) {
		this.detachableBG.add(detachableBG);
	}

	/**
	 * returns doodle detachableBG.
	 * @param index
	 * @return detachableBG
	 */
	public BranchGroup getDoodle(int index) {
		return (BranchGroup) detachableBG.get(index);
	}

	/**
	 * removes a doodle specified by index.
	 * @param index
	 */
	public void removeDoodle(int index) {
		// ((BranchGroup)detachableBG.get(i)).detach();
		detachableBG.remove(index);
	}

	/**
	 * removes all doodles on a letter component.
	 *
	 */
	public void removeAllDoodles() {
		for (int i = 0; i < detachableBG.size(); i++) {
			((BranchGroup) detachableBG.get(i)).detach();
		}
		/** Need to modify */
		detachableBG = null;
		detachableBG = new ArrayList();
	}

	/**
	 * returns current doodle index.
	 * @return
	 */
	public int getCurrentDoodleIndex() {
		return this.currentDoodleOrder;
	}

	/**
	 * sets current doodle index.
	 * @param currentDoodleOrder
	 */
	public void setCurrentDoodleIndex(int currentDoodleOrder) {
		this.currentDoodleOrder = currentDoodleOrder;
	}

	/**
	 * returns previous doodle color attributes.
	 * @return previousDoodleColorAttr
	 */
	public ColoringAttributes getPreviousDoodleColorAttr() {
		if (null == this.previousDoodleColorAttr)
			return null;
		return this.previousDoodleColorAttr;
	}

	/**
	 * sets previous doodle color attributes.
	 * @param previousDoodleColorAttr
	 */
	public void setPreviousDoodleColorAttr(
			ColoringAttributes previousDoodleColorAttr) {
		if (null == this.previousDoodleColorAttr) {
			this.previousDoodleColorAttr = new ColoringAttributes();
		}

		this.previousDoodleColorAttr = previousDoodleColorAttr;
	}

	/**
	 * returns total number of doodles.
	 * @return totalDoodleNum
	 */
	public int getTotalDoodleNum() {
		return totalDoodleNum;
	}

	/**
	 * sets total number of doodles.
	 * @param totalDoodleNum
	 */
	public void setTotalDoodleNum(int totalDoodleNum) {
		this.totalDoodleNum = totalDoodleNum;
	}
	
	/**
	 * increments total number of doodles.
	 *
	 */
	public void incrementTotalDoodleNum() {
		this.totalDoodleNum++;
	}

	/**
	 * decrements total number of doodles.
	 *
	 */
	public void decrementTotalDoodleNum() {
		this.totalDoodleNum--;
	}

	/**
	 * returns next letter component.
	 * @return nextLetterComponent3D
	 */
	public LetterComponent3D getNextLetterComponent3D() {
		return nextLetterComponent3D;
	}

	/**
	 * sets next letter component.
	 * @param nextLetterComponent3D
	 */
	public void setNextLetterComponent3D(LetterComponent3D nextLetterComponent3D) {
		this.nextLetterComponent3D = nextLetterComponent3D;
	}

	/**
	 * returns previous letter component.
	 * @return prevLetterComponent3D
	 */
	public LetterComponent3D getPrevLetterComponent3D() {
		return prevLetterComponent3D;
	}

	/**
	 * sets previous page letter component.
	 * @param prevLetterComponent3D
	 */
	public void setPrevLetterComponent3D(LetterComponent3D prevLetterComponent3D) {
		this.prevLetterComponent3D = prevLetterComponent3D;
	}

	/**
	 * returns postion.
	 * @return position
	 */
	public Vector3f getPosition() {
		return position;
	}

	/**
	 * sets position.
	 * @param position
	 */
	public void setPosition(Vector3f position) {
		this.position = position;
	}

	/**
	 * returns a number of message pages.
	 * @return pageNumber
	 */
	public int getPageNumber() {
		return pageNumber;
	}

	/**
	 * sets a number of message pages.
	 * @param pageNumber
	 */
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	/**
	 * returns letter container.
	 * @return aLetterCon
	 */
	public ALetterContainer3D getALetterCon() {
		return aLetterCon;
	}

	/**
	 * sets letter container.
	 * @param aLetterCon
	 */
	public void setALetterContainer3D(ALetterContainer3D aLetterCon) {
		aLetterCon = aLetterCon;
	}

	/**
	 * returns message content.
	 * @return messageContent
	 */
	public String getMessageContent() {
		return this.messageContent;
	}

	/**
	 * sets message content.
	 * @param messageContent
	 */
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
}
