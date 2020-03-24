package org.jdesktop.lg3d.apps.blackgoat.draw.letter.utils;

import java.awt.Color;
import java.awt.Font;

import org.jdesktop.lg3d.utils.shape.FuzzyEdgePanel;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.Component3D;

/**
 * @author dai
 */ 
/**
 * Utility date class for letter format.
 */
public class LetterFormat {
	public static String test;

	public static final int FONT_HEIGHT = 19;

	public static final int MSG_SIZE = 80;

	public static final int LEFT_ALIGN = 5;
	
	public static final int A_LETTER_LINE_NUM = 15;

	private static Color black = new Color(64, 64, 64);

	private static Color gray = new Color(128, 128, 172);

	private static Color blue = new Color(32, 32, 64);

	private static Font title = new Font("Serif", Font.PLAIN, 18);

	private static Font sentence = new Font("Ariel", Font.PLAIN | Font.BOLD
			| Font.TYPE1_FONT, 16);

	private static Font header = new Font("Serif", Font.PLAIN | Font.BOLD, 18);

	private static SimpleAppearance app = new SimpleAppearance(0.7f, 0.8f,
			1.0f, SimpleAppearance.ENABLE_TEXTURE
					| SimpleAppearance.DISABLE_CULLING);

	private static FuzzyEdgePanel body = new FuzzyEdgePanel(0.03f, 0.03f, 0.0f,
			app);

	private static Component3D paper;

	private LetterFormat() {
	}

	/**
	 * returns black color. 
	 * @return black
	 */
	public static Color getBlackColor() {
		return black;
	}

	/**
	 * returns glay color
	 * @return gray
	 */
	public static Color getGrayColor() {
		return gray;
	}

	/**
	 * returns blue color
	 * @return blue
	 */
	public static Color getBlueColor() {
		return blue;
	}

	/**
	 * reurns font on writing title case.
	 * @return title
	 */
	public static Font getTitle() {
		return title;
	}

	/**
	 * returns font on writing sentence case.
	 * @return sentence
	 */
	public static Font getSentence() {
		return sentence;
	}

	/**
	 * returns font on writing header case.
	 * @return header
	 */
	public static Font getHeader() {
		return header;
	}
	/*
	 * public static FuzzyEdgePanel getDummy() { //paper.addChild(body); return
	 * body; }
	 */
}
