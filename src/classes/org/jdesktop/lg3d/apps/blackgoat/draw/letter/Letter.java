package org.jdesktop.lg3d.apps.blackgoat.draw.letter;

import org.jdesktop.lg3d.apps.blackgoat.component.letter.HeaderComponent3D;
import org.jdesktop.lg3d.apps.blackgoat.component.letter.LetterComponent3D;
import org.jdesktop.lg3d.apps.blackgoat.component.letter.SmallLetterComponent3D;

/**
 * @author dai
 */
/**
 * All interface methosds are regarding letter content, letter header and 
 * small letter in a message folder.
 * TODO review, maybe all go to ALtterContainer3D.
 */
public interface Letter {
	/**
	 * sets array of LetterComponent3D
	 * @param letterComp
	 */
	public void setLetterComponent3D(LetterComponent3D[] letterComp);
	
	/**
	 * creates array of LetterComponent3DImages and sets images to them 
	 * and returns them.
	 * @return array of LetterComponent3D
	 */
	public LetterComponent3D[] createLetterComponent3DImage();
	
	/**
	 * returns array of LetterComponent3D
	 * @return array of LetterComponent3D
	 */
	public LetterComponent3D[] getLetterComponent3D();
	
	/**
	 * creates HeaderComponent and sets image to it and return it.
	 * @param width
	 * @return
	 */
	public HeaderComponent3D createHeaderComponent3D(float width);
	
	/**
	 * returns HeaderComponent3D
	 * @return HeaderComponent3D
	 */
	public HeaderComponent3D getHeaderComponent3D();
	
	/**
	 * sets SmallLetterComponent3D
	 * @param smallComp
	 */
	public void setSmallLetterComponent3D(SmallLetterComponent3D smallComp);
	
	/**
	 * returns SmallLetterComponent3D
	 * @return
	 */
	public SmallLetterComponent3D getSmallLetterComponent3D();
	
}
