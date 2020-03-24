package org.jdesktop.lg3d.apps.blackgoat.draw.letter;

import com.sun.jndi.cosnaming.IiopUrl.Address;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

import org.jdesktop.lg3d.apps.blackgoat.button.ButtonAppearance;
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
public final class DummyLetterContentDrawer extends AbstractLetter {

	private final static int Image_X = 620;

	private final static int Image_Y = 300;

	private String from;

	private String subject;

	private String content;

	private Date date;

	private Address[] letter;

	private LetterComponent3D[] letterComp;
	
	private HeaderComponent3D headerComp;
	
	private SmallLetterComponent3D smallComp;

	private int currentLine;
	
	private static int incNum = 0;
//	For Dummy
	private static final int INITIAL_BODY_LENGTH = 2000;
//	private static final String DUMMY_FILES_DIRECTORY = "../lg3d-incubator/build/classes/resources/images/blackgoat/util/";
	private static final String DUMMY_FILES_DIRECTORY = "resources/images/blackgoat/util/";
	//private static final String DUMMY_FILES_DIRECTORY = "org/jdesktop/lg3d/apps/blackgoat/draw/letter/dummy/";
	//private static final String DUMMY_FILES_DIRECTORY = "org/jdesktop/lg3d/apps/blackgoat/util/";
	//private static final String DUMMY_FILES_DIRECTORY = "./dummy/";
	
	private static final String CONFIG_FILE_NAME = "dummycfg.cfg";
	
	public DummyLetterContentDrawer(int messageId, float letterWidth,
			float letterHeight, float letterDepth) {
		super.messageId = messageId;
		super.letterHeight = letterHeight;
		super.letterWidth = letterWidth;
		super.letterDepth = letterDepth;

		StringBuffer strbuf = new StringBuffer(INITIAL_BODY_LENGTH);
		try{
			SimpleAppearance popOffAppearance = new ButtonAppearance(
					new URL("resource:///resources/images/blackgoat/util/pop.png"), false, 0);
		
			String msgFilePath = DUMMY_FILES_DIRECTORY + 
			//	getMessageNumberToRead() + ".msg";
			incNum + ".msg";
			incNum++;
			if( incNum > 8 ) incNum = 0;
			File dir = new File(DUMMY_FILES_DIRECTORY);
		//	String AbsPath = dir.getAbsolutePath();
		//	String msgFilePath = "resources/images/blackgoat/util/pop.png";

			BufferedReader br = new BufferedReader(new InputStreamReader( this.getClass().getClassLoader().getResourceAsStream(msgFilePath)));
			
			this.from = br.readLine();
			
			DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, Locale.JAPAN);
			this.date = df.parse(br.readLine());
			
			this.subject = br.readLine();
			
			while(br.ready()){
				strbuf.append(br.readLine());
				strbuf.append("\n");
			}
			this.content = strbuf.toString();

			//System.out.println(body);
			br.close();
		}
		catch(IOException e){
			e.printStackTrace();
			System.err.println("File can not be opened");
		}
		catch(ParseException ex) {
			ex.printStackTrace();
			System.err.println("File can not be opened");
			
		}
	
	}


	
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

	public LetterComponent3D[] createLetterComponent3D(FuzzyEdgePanel[] fep) {
		//System.out.println("Fep Size is >>>>" + fep.length);
		this.letterComp = new LetterComponent3D[fep.length];
		for( int i = 0; i < fep.length; i++ ) {
			//System.out.println("For Fep  Loop Size is " + i);
			letterComp[i] = new LetterComponent3D(messageId, fep[i], super.letterWidth,
				super.letterHeight, super.letterDepth);
			letterComp[i].setPageNumber(i);
			letterComp[i].setMessageContent(this.content);
			/** set Linked List */
			if( i > 0 ) {
				letterComp[i - 1].setNextLetterComponent3D(letterComp[i]);
				letterComp[i].setPrevLetterComponent3D(letterComp[i - 1]);
			}
		}
		return letterComp;
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

	/* Draw letter Header (From & Subject) */
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

	/* Draw letter Content */
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
				//System.out.println("Create New Image");
				image =new BufferedImage(Image_X, Image_Y,
						BufferedImage.TYPE_INT_ARGB);				
					g = (Graphics2D) image.getGraphics();
					g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
							RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
					g.setColor(LetterFormat.getBlackColor());
					g.setFont(LetterFormat.getSentence());
					
			}
			//System.out.println("Count : " + j);
			lines[j] = new StringBuffer(st.nextToken());
			
			while(lines[j].length() > deli){
				String str = lines[j].substring(0, deli);
		//		System.out.println(str);
				temp = LetterFormat.FONT_HEIGHT + currentLine
				* LetterFormat.FONT_HEIGHT;
		
				g.setColor(LetterFormat.getBlackColor());				
				g.drawString(( page * LetterFormat.A_LETTER_LINE_NUM) + currentLine + " : " 
						+ str, LetterFormat.LEFT_ALIGN, temp);
			
			
				
				lines[j].delete(0, deli);
				currentLine++;
				if (0 == (currentLine % LetterFormat.A_LETTER_LINE_NUM) ){
					//		System.out.println(" Now " + currentLine % LetterFormat.A_LETTER_LINE_NUM);
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
			
		//	System.out.println("Current " + currentLine);
			
			/** goes below when page is more than one.
			 * Also means lines are more than 15.
			 */
			if (0 == (currentLine % LetterFormat.A_LETTER_LINE_NUM) ){
		//		System.out.println(" Now " + currentLine % LetterFormat.A_LETTER_LINE_NUM);
				g.dispose();
				biList.add(image);
				image = null;
				currentLine = 0;
				page++;
			}
		}
		/** Just One page or Last page whose lines are less than 
		 * LetterFormat.A_LETTER_LINE_NUM ( 15 )
		 * */
		g.dispose();
		biList.add(image);
		image = null;
		//System.out.println("Does this work??");
	}

	public LetterComponent3D[] getLetterComponent3D() {
		if (null == this.letterComp) {
			this.letterComp = new LetterComponent3D[1];
			this.letterComp[0] = new LetterComponent3D();
		}
		return this.letterComp;
	}
	
	private static int Id;
	
	public HeaderComponent3D createHeaderComponent3D(float width){
		headerComp = new HeaderComponent3D(getHeaderImage(), width);
		headerComp.setDate(this.date);
		headerComp.setSubject(this.subject);
		headerComp.setFrom(this.from);
		headerComp.setMessageId(Id);
		Id++;
		return headerComp;
	}

	public void setLetterComponent3D(LetterComponent3D[] letterComp) {
		this.letterComp = letterComp;
	}

	public HeaderComponent3D getHeaderComponent3D() {
		return headerComp;
	}

	public SmallLetterComponent3D getSmallLetterComponent3D() {
		return smallComp;
	}

	public void setSmallLetterComponent3D(SmallLetterComponent3D smallComp) {
		this.smallComp = smallComp;
	}

}
