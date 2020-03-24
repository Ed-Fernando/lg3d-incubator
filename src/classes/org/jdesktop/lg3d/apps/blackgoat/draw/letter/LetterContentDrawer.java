package org.jdesktop.lg3d.apps.blackgoat.draw.letter;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.internet.MimeUtility;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

import org.jdesktop.lg3d.apps.blackgoat.component.letter.HeaderComponent3D;
import org.jdesktop.lg3d.apps.blackgoat.component.letter.LetterComponent3D;
import org.jdesktop.lg3d.apps.blackgoat.component.letter.SmallLetterComponent3D;
import org.jdesktop.lg3d.apps.blackgoat.draw.letter.utils.LetterFormat;
import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.sg.Shape3D;
import org.jdesktop.lg3d.sg.Texture;
import org.jdesktop.lg3d.sg.TextureAttributes;
import org.jdesktop.lg3d.sg.utils.image.TextureLoader;
import org.jdesktop.lg3d.utils.shape.FuzzyEdgePanel;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;

/**
 * @author dai
 */
/**
 * Letter image create class.
 */
public final class LetterContentDrawer extends AbstractLetter {

	private final static int Image_X = 620;

	private final static int Image_Y = 300;

	private String from;

	private String subject;

	private String content;

	private Date date;

	private Address[] address;

	private LetterComponent3D[] letterComps;
	
	private HeaderComponent3D headerComp;
	
	private SmallLetterComponent3D smallComp;

	private int currentLine;

	/**
	 * Gets message and retreive from, subject, date and content.
	 * @param messageId
	 * @param message
	 * @param letterWidth
	 * @param letterHeight
	 * @param letterDepth
	 */
	public LetterContentDrawer(int messageId, Message message, float letterWidth,
			float letterHeight, float letterDepth) {
		super.messageId = messageId;
		super.letterHeight = letterHeight;
		super.letterWidth = letterWidth;
		super.letterDepth = letterDepth;

		try {
			this.address = message.getFrom();
			this.from = MimeUtility.decodeText(this.address[0].toString());
			this.subject = message.getSubject();
			this.date = message.getSentDate();
			this.content = message.getContent().toString();
			if (null == content) {
				return;
			}
		} catch (java.io.UnsupportedEncodingException ex) {
			ex.printStackTrace();
		} catch (java.io.IOException ex) {
			ex.printStackTrace();
		} catch (javax.mail.MessagingException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Creates FuzzyEdgePanels after creating BufferedImages above.
	 * <code>public abstract void getContentImage(ArrayList<BufferedImage> biList);</code>
	 * @param bi
	 * @return body
	 */
	public FuzzyEdgePanel createTexturedPanel(BufferedImage bi) {
		TextureLoader loader = new TextureLoader(bi);

		Texture texture = loader.getTexture();
		if( null == texture ) {
			System.err.println("Message Texture is null");
		}
		texture.setCapability(Texture.RGBA);

		texture.setCapability(Texture.ALLOW_ENABLE_WRITE);
		texture.setCapability(Texture.ALLOW_ENABLE_READ);

		TextureAttributes ta = new TextureAttributes();
		ta.setTextureBlendColor(0.9f, 0.9f, 0.9f, 1.0f);
		/* express color on each boad */
		/** DECAL IS Nesessary */
		ta.setTextureMode(TextureAttributes.DECAL);
		ta.setCapability(TextureAttributes.ALLOW_MODE_WRITE);
		ta.setCapability(TextureAttributes.ALLOW_BLEND_COLOR_WRITE);

		SimpleAppearance app = new SimpleAppearance(1.0f, 1.0f, 1.0f, 1.0f,
				SimpleAppearance.ENABLE_TEXTURE
						| SimpleAppearance.DISABLE_CULLING);

		app.setCapability(Appearance.ALLOW_TEXTURE_WRITE);
		app.setCapability(Appearance.ALLOW_TEXGEN_WRITE);
		app.setCapability(Appearance.ALLOW_TEXTURE_ATTRIBUTES_WRITE);
		app.setCapability(Appearance.ALLOW_TEXTURE_ATTRIBUTES_READ);

		app.setTextureAttributes(ta);
		app.setTexture(texture);

		FuzzyEdgePanel body = new FuzzyEdgePanel(letterWidth, letterHeight,
				letterDepth, app);
		// body.setCapability(Shape3D.ALLOW_APPEARANCE_OVERRIDE_WRITE);
		body.setCapability(Shape3D.ALLOW_APPEARANCE_WRITE);
		body.setCapability(Shape3D.ALLOW_APPEARANCE_READ);
		body.setCapability(Appearance.ALLOW_TEXTURE_ATTRIBUTES_WRITE);
		body.setCapability(Appearance.ALLOW_TEXTURE_ATTRIBUTES_READ);

		return body;
	}

	/**
	 * Creates LetterComponent3D after creating FuzzyEdgePanels above.
	 * <code>public abstract FuzzyEdgePanel createTexturedPanel(BufferedImage bi);</code>
	 * Each LetterComponent3D adds child FuzzyEdgePanel and are interactive lists.
	 * @param fep
	 * @return letterComp
	 */
	public LetterComponent3D[] createLetterComponent3D(FuzzyEdgePanel[] fep) {
		this.letterComps = new LetterComponent3D[fep.length];
		for( int i = 0; i < fep.length; i++ ) {
			//System.out.println("For Fep  Loop Size is " + i);
			letterComps[i] = new LetterComponent3D(messageId, fep[i], letterWidth,
				letterHeight, letterDepth);
			letterComps[i].setPageNumber(i);
				
			letterComps[i].setMessageContent(this.content);
			/** set Linked List */
			if( i > 0 ) {
				letterComps[i - 1].setNextLetterComponent3D(letterComps[i]);
				letterComps[i].setPrevLetterComponent3D(letterComps[i - 1]);
			}
		}
		return letterComps;
	}

	private BufferedImage getHeaderImage() {

		BufferedImage image = new BufferedImage(Image_X + 50, 46,
				BufferedImage.TYPE_INT_ARGB);

		try {
			Graphics2D g = (Graphics2D) image.getGraphics();

			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

			drawHeader(g);

			g.dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return image;
	}

	/**
	 * Draw letter Header from, date and subject.
	 * @param g 
	 */
	private void drawHeader(Graphics2D g) {
		g.setFont(LetterFormat.getHeader());

		g.setColor(Color.LIGHT_GRAY);
		g.drawString(this.from + " " + this.date.toString(),
				LetterFormat.LEFT_ALIGN / 2 + 1, 20);

		g.setColor(LetterFormat.getBlueColor());
		g.drawString(this.from + " " + this.date.toString(),
				LetterFormat.LEFT_ALIGN / 2, 20);

		// g.setFont(new Font("Serif", Font.PLAIN | Font.BOLD, 12));

		g.setColor(Color.blue);
		g.drawString(this.subject, LetterFormat.LEFT_ALIGN / 2
				+ this.from.length() + 1, 40 + 0);

		g.setColor(Color.BLACK);
		g.drawString(this.subject, LetterFormat.LEFT_ALIGN / 2
				+ this.from.length(), 40);

		// System.out.println("Header : " + this.from + " " + this.subject);
	}
	
	int page = 0;

	/**
	 * Creates message BufferedImages after reading message.
	 * Each image is added to ArrayList.
	 * @param biList
	 */
	public void getContentImage(ArrayList<BufferedImage> biList) {	
		int temp = 0;

		int deli = 65;
		
		if( null == content ) System.err.println("No Message Content");
		
		StringTokenizer st = new StringTokenizer(content, "\n\r");

		currentLine = 0;

		BufferedImage image = null;
		Graphics2D g = null;
		
		StringBuffer[] lines = new StringBuffer[st.countTokens()];;
		int j = 0;
		while (st.hasMoreTokens()) {
		
			if( null == image ) {
				image =new BufferedImage(Image_X, Image_Y,
						BufferedImage.TYPE_INT_ARGB);				
					g = (Graphics2D) image.getGraphics();
					g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
							RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
					g.setColor(LetterFormat.getBlackColor());
					g.setFont(LetterFormat.getSentence());
					
			}
		
			lines[j] = new StringBuffer(st.nextToken());
			
			while(lines[j].length() > deli){
				String str = lines[j].substring(0, deli);
				temp = LetterFormat.FONT_HEIGHT + currentLine
				* LetterFormat.FONT_HEIGHT;
		
				g.setColor(LetterFormat.getBlackColor());				
				g.drawString(( page * LetterFormat.A_LETTER_LINE_NUM) + currentLine + " : " 
						+ str, LetterFormat.LEFT_ALIGN, temp);
						
				lines[j].delete(0, deli);
				currentLine++;
				if (0 == (currentLine % LetterFormat.A_LETTER_LINE_NUM) ){
							g.dispose();
							biList.add(image);
							image = null;
							currentLine = 0;
							page++;
						}
			} 
			
			
			temp = LetterFormat.FONT_HEIGHT + currentLine
					* LetterFormat.FONT_HEIGHT;
			/**
			 * Line Number and message content
			 */
			g.drawString(( page * LetterFormat.A_LETTER_LINE_NUM) + currentLine + " : " 
					+ lines[j], LetterFormat.LEFT_ALIGN, temp);
		
			currentLine++;
			j++;
					
			/** goes below when page is more than one.
			 * Also means lines are more than 15.
			 */
			if (0 == (currentLine % LetterFormat.A_LETTER_LINE_NUM) ){
				g.dispose();
				biList.add(image);
				image = null;
				currentLine = 0;
				page++;
			}
		}
		/** Just One page or Last page whose lines are less than 
		 *  LetterFormat.A_LETTER_LINE_NUM ( 15 )
		 * */
		g.dispose();
		biList.add(image);
		image = null;
		//System.out.println("Does this work??");
	}

	/**
	 * Returns array of LetterComponent3D.
	 * @return letterComp
	 */
	public LetterComponent3D[] getLetterComponent3D() {
		if (null == this.letterComps) {
			this.letterComps = new LetterComponent3D[1];
			this.letterComps[0] = new LetterComponent3D();
		}
		return this.letterComps;
	}
	
	private static int Id;
	
	/**
	 * Creates header component image.
	 * @return headerComp
	 */
	public HeaderComponent3D createHeaderComponent3D(float width){
		headerComp = new HeaderComponent3D(getHeaderImage(), width);
		headerComp.setDate(this.date);
		headerComp.setSubject(this.subject);
		headerComp.setFrom(this.from);
		headerComp.setMessageId(Id);
		Id++;
		return headerComp;
	}

	/**
	 * sets array of LetterComponent3D
	 * @param letterComps
	 */
	public void setLetterComponent3D(LetterComponent3D[] letterComps) {
		this.letterComps = letterComps;
	}

	/**
	 * returns HeaderComponent3D
	 * @return headerComp
	 */
	public HeaderComponent3D getHeaderComponent3D() {
		return headerComp;
	}

	/**
	 * returns SmallLetterComponent3D
	 * @return smallComp
	 */
	public SmallLetterComponent3D getSmallLetterComponent3D() {
		return smallComp;
	}

	/**
	 * sets SmallLetterComponent3D
	 * @param smallComp 
	 */
	public void setSmallLetterComponent3D(SmallLetterComponent3D smallComp) {
		this.smallComp = smallComp;
	}

}
