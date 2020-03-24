package org.jdesktop.lg3d.apps.blackgoat.draw.letter;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.jdesktop.lg3d.apps.blackgoat.component.letter.LetterComponent3D;
import org.jdesktop.lg3d.utils.shape.FuzzyEdgePanel;
/**
 * @author Dai Odahara
 */
/**
 * Abstract Class in creating message images.
 */
public abstract class AbstractLetter implements Letter {

	protected int messageId;
	
	protected float letterWidth;
	
	protected float letterHeight;
	
	protected float letterDepth;
	
	private ArrayList<BufferedImage> biList;
	
	/**
	 * Returns array of letterComponent3Ds. They have already been drawn
	 * about message, sender, date, subject and content.
	 * @reurn LetterComponent3D array. 
	 * About return type, see also 
	 * <code>public abstract LetterComponent3D[] createLetterComponent3D(FuzzyEdgePanel[] fep);</code>  
	 */
	public LetterComponent3D[] createLetterComponent3DImage(){
		biList = new ArrayList();

		/** add Messages to ArrayList */
		getContentImage(biList);

		FuzzyEdgePanel[] fep = new FuzzyEdgePanel[biList.size()];
		for (int i = 0; i < fep.length; i++) {
			fep[i] = createTexturedPanel(biList.get(i));
		}
		
		return createLetterComponent3D(fep);
	}
	
	/**
	 * Creates message BufferedImages after reading message.
	 * Each image is added to ArrayList.
	 * @param biList
	 */
	public abstract void getContentImage(ArrayList<BufferedImage> biList);
	
	/**
	 * Creates FuzzyEdgePanels after creating BufferedImages above.
	 * <code>public abstract void getContentImage(ArrayList<BufferedImage> biList);</code>
	 * @param bi
	 * @return FuzzyEdgePanel
	 */
	public abstract FuzzyEdgePanel createTexturedPanel(BufferedImage bi);
	
	/**
	 * Creates LetterComponent3D after creating FuzzyEdgePanels above.
	 * <code>public abstract FuzzyEdgePanel createTexturedPanel(BufferedImage bi);</code>
	 * Each LetterComponent3D adds child FuzzyEdgePanel and are interactive lists.
	 * @param fep
	 * @return array of LetterComponent3D
	 */
	public abstract LetterComponent3D[] createLetterComponent3D(FuzzyEdgePanel[] fep);
}
