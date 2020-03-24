package org.jdesktop.lg3d.apps.blackgoat.component.folder;

import org.jdesktop.lg3d.apps.blackgoat.component.letter.HeaderComponent3D;
import org.jdesktop.lg3d.apps.blackgoat.component.letter.LetterComponent3D;
import org.jdesktop.lg3d.apps.blackgoat.component.letter.ReplyForwardComponent3D;
import org.jdesktop.lg3d.apps.blackgoat.component.letter.SmallLetterComponent3D;
import org.jdesktop.lg3d.wg.Container3D;

/**
 * This class has ReplyRorward, HeaderComponent3D 
 * and SmallLetterComponent3D, arrary of LetterComponent3D. 
 * @author dai
 *
 */
public class ALetterContainer3D extends Container3D {

	private int MessageId;
	
	private boolean selected = false;

	private ReplyForwardComponent3D rfComp;
	
	private LetterComponent3D[] letterComp;
	
	private HeaderComponent3D headerComp;
	
	private SmallLetterComponent3D smallLetterComponent;
	
	private int letterArrayIndex;
	
	private int numPages;
	
	private int numPostits;
	
	private boolean isCreatedLetter;

	public ALetterContainer3D(){}
	
	/**
	 * constructs constructs LetterComponent and their length. The length goes to
	 * a number of message pages.
	 * @param letterComp
	 */
	public ALetterContainer3D(LetterComponent3D[] letterComp){
		this.letterComp = letterComp;
		this.numPages = letterComp.length;
	}
	
	/**
	 * returns unique message id thruogh the HederCompnent3D
	 * @return headerComp.getMessageId();
	 */
	public int getMessageId() {
		return headerComp.getMessageId();
	}

	/**
	 * sets unique message Id throught ghe HeaderComponent3D
	 * @param messageId
	 */
	public void setMessageId(int messageId) {
		headerComp.setMessageId(messageId);
	}

	/**
	 * returns array of LetterComponent3D. 
	 * @return letterComp
	 */
	public LetterComponent3D[] getAllLetterComponent3D() {
		return letterComp;
	}

	/**
	 * sets array of LetterComponent3D
	 * @param letterComp
	 */
	public void setAllLetterComponent3D(LetterComponent3D[] letterComp) {
		this.letterComp = letterComp;
		this.numPages = letterComp.length;		
		
	}	
	
	/**
	 * returns one LetterComponent3D specified index number.
	 * @param index
	 * @return letterComp
	 */
	public LetterComponent3D getALetterComponent3D(int index) {
		return letterComp[index];
	}

	/**
	 * sets one LetterComponent3D specified index number
	 * @param index
	 * @param aLetterComp
	 */
	public void setALetterComponent3D(int index, LetterComponent3D aLetterComp) {
		this.letterComp[index] = aLetterComp;
	}

	/**
	 * returns boolean whether this class is selected on the MessageViewer class
	 * @return selected
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * sets this class is selected
	 * @param selected
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	/**
	 * returns HeaderComponent3D
	 * @return headerComp
	 */
	public HeaderComponent3D getHeaderComponent3D() {
		return headerComp;
	}

	/**
	 * sets HeaderComponent3D
	 * @param headerComp
	 */
	public void setHeaderComponent3D(HeaderComponent3D headerComp) {
		this.headerComp = headerComp;
	}

	/**
	 * returns how many these messages are, page number.
	 * @return numPages
	 */
	public int getNumPages() {
		return numPages;
	}

	/**
	 * sets how many these messages are, page number
	 * @param numPages
	 */
	public void setNumPages(int numPages) {
		this.numPages = numPages;
	}

	/**
	 * returns the current shown order on the MessageViewer. this value shows that 
	 * where this class is from top on the header list.
	 * @return headerComp.getLetterArrayIndex()
	 */
	public int getLetterArrayIndex() {
		return headerComp.getLetterArrayIndex();
	}

	/**
	 * sets the current shown order on the MessageViewer. this value show that
	 * where this class is from top on the header list.
	 * @param letterArrayIndex
	 */
	public void setLetterArrayIndex(int letterArrayIndex) {
		headerComp.setLetterArrayIndex(letterArrayIndex);
	}

	/**
	 * returns SmallLetterComponent3D which appears in a message folder.
	 * @return smallLetterComponent
	 */
	public SmallLetterComponent3D getSmallLetterComponent3D() {
		return smallLetterComponent;
	}

	/**
	 * sets SmallLetterComponent3D which appears in a message folder.
	 * @param smallLetterComponent
	 */
	public void setSmallLetterComponent3D(SmallLetterComponent3D smallLetterComponent) {
		this.smallLetterComponent = smallLetterComponent;
	}

	/**
	 * returns boolean whether an array of LetterComponent3D is created. 
	 * @return letterIsCreated
	 */
	public boolean hasCreatedLetter() {
		return isCreatedLetter;
	}

	/**
	 * sets whether an array of LetterComponent3D is created.
	 * @param letterIsCreated
	 */
	public void setLetterIsCreated(boolean letterIsCreated) {
		this.isCreatedLetter = letterIsCreated;
	}
	
	/**
	 * sets postit to SmalleLetterComponent3D.
	 *
	 */
	private void setPostitToSmallLetter(){
			this.smallLetterComponent.doPostit();
	}

	/**
	 * sets SmallLetterComponent's postit color.
	 * @param r
	 * @param g
	 * @param b
	 */
	public void setPostitColorToSmallLetter(float r, float g, float b){
		if( null != this.smallLetterComponent ) {
			this.smallLetterComponent.setPostitColor(r, g, b);
		}
	}

	/**
	 * decrements a number of SmallLetterComponent3D's postits 
	 *
	 */
	public void decNumPostits() {
		this.numPostits--;
		if( 0 == this.numPostits ) {
			setPostitToSmallLetter();
			this.smallLetterComponent.resetPostit();
		}
	//	System.out.println("Current Postit Num" + this.postitNum);
		
	}

	/**
	 * increments a number of SmallLetterComponent3D's postits
	 *
	 */
	public void incNumPostit() {
		if( 0 == this.numPostits ) {
			setPostitToSmallLetter();
		}
		this.numPostits++;
	//	System.out.println("Current Postit Num" + this.postitNum);

	}

	/**
	 * creates ReplyForwardComponent3D if it is null.
	 * @param aLetterCon
	 * @param currentPageNumber
	 * @return rfComp
	 */
	public ReplyForwardComponent3D createReplyForwardComponent3D(ALetterContainer3D aLetterCon, int currentPageNumber) {
		if( null == rfComp ) {
			rfComp = new ReplyForwardComponent3D(aLetterCon, currentPageNumber);
			addChild(rfComp);
		}
		return rfComp;
	}
	
	/**
	 * returns ReplyForwardComponent3D.
	 * @return rfComp
	 */
	public ReplyForwardComponent3D getReplyForwardComponent3D() {		
		return this.rfComp;
	}
	
	/**
	 * sets ReplyForwardComponent3D.
	 * @param rfComp
	 */
	public void setReplyForwardComponent3D(ReplyForwardComponent3D rfComp) {
		this.rfComp = rfComp;
	}
}
