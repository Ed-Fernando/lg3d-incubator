package org.jdesktop.lg3d.apps.bgmanager.configReaders;

import java.util.ArrayList;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.jdesktop.lg3d.apps.bgmanager.*;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.io.File;
/**
 * @author Radeczka
 * 
 */
public class BgFileReader extends DefaultHandler {
        private ArrayList findBgList;
	private ArrayList<BgLgComponent> BgComponentList;
	private String current;
	private BgLgComponent bgcomp;
	private StringBuffer bodyContentBuffer = null;
	private boolean LayerTag = false;
        private boolean onlyCurrent = false;
	private static final String NAME = "Name";
	private static final String THUMBNAIL = "Thumbnail";
	private static final String AUTHOR = "Author";
	private static final String DATE = "Date";
	private static final String DESCRIPTION = "Description";
	private static final String BGTYPE = "BGType";
	private static final String BACKGROUND = "Background";
	private static final String LAYER = "Layer";
	private static final String IMAGE = "Image";
	private static final String TRANSLATIONX = "TranslationX";
	private static final String TRANSLATIONY = "TranslationY";
	private static final String TRANSLATIONZ = "TranslationZ";
	private static final String LAYERED_BG = "Layered Background";
	private static final String SIMPLE_BG = "Simple Background";
	private static final String PANORAMA_BG = "Panorama Background";
	private static final String PANO_INIT_BG = "InitialBackground";

	private String parseBgType,configFileURL;
	private String tmpName, tmpDescription, tmpDate, tmpAuthor;
	private String tmpThumb;
	private String tmpTranslationX, tmpTranslationY, tmpTranslationZ;
	private int numLayers, numPanoramaImages, initialPanoBackground;
	private ArrayList ImagesList, translationCordNum;
	private float[] translationCord;
	private String[] listBg;
	int numBgFiles;
	File findFile = new File (System.getProperty("lg.etcdir") + "../resources/backgrounds");
	
	public BgFileReader(String[] listBG, int numBgFiles) {
		this.numBgFiles = numBgFiles;
		listBg = new String[numBgFiles];
		System.arraycopy(listBG, 0, listBg, 0, numBgFiles);
		BgComponentList = new ArrayList<BgLgComponent>();
		translationCordNum = new ArrayList();
		ImagesList = new ArrayList();
		numLayers = 0;
		numPanoramaImages = 0;

		try {
			parseAllBgFiles();
		} catch (SAXException e) {
			e.printStackTrace();
		}

	}
	public BgFileReader(String current) {
	    translationCordNum = new ArrayList();
		numLayers = 0;
		numPanoramaImages = 0;
		ImagesList = new ArrayList();
		this.current = current;
		onlyCurrent = true;
		try {
			parseConfigFile(current);
		} catch (SAXException e) {
			e.printStackTrace();
		}

	}
	 private void listDirectory(File dir ) {              
	    File[] files = dir.listFiles();
                for (int i = 0; i < files.length; i++) {
                    if (files[i].isDirectory()) {
			File [] list = files[i].listFiles ();
                       for(int k =0; k<list.length;k++){		           
			    if (list[k].getName().equals ("LgBgConf.lgbg")){
				findBgList.add ("resources" +"/" +dir.getName () + "/"+files[i].getName() + "/"+list[k].getName ());
	
			    }
		       }
                    }
                }      
	}
	
	public void startDocument() {
	}

	public void endDocument() {

	}

	public void startElement(String namespaceURI, String sName, String qName,
			Attributes attrs) throws SAXException {
		String eName = sName;
		if ("".equals(eName))
			eName = qName;

		if (eName.equals(BACKGROUND)) {
		} else if (eName.equals(NAME)) {

		} else if (eName.equals(THUMBNAIL)) {

		} else if (eName.equals(AUTHOR)) {

		} else if (eName.equals(DATE)) {

		} else if (eName.equals(DESCRIPTION)) {

		} else if (eName.equals(BGTYPE)) {
			
		} else if (eName.equals(PANO_INIT_BG)) {

		}   else if (eName.equals(LAYER)) {
			LayerTag = true;
		} else if (eName.equals(IMAGE)) {

		} else if (eName.equals(TRANSLATIONX)) {

		} else if (eName.equals(TRANSLATIONY)) {

		} else if (eName.equals(TRANSLATIONZ)) {

		} else
			throw new SAXException("Wrong tag: " + eName);
	}

	public void endElement(String namespaceURI, String sName, String qName)
			throws SAXException {
		String eName = sName;
		if ("".equals(eName))
			eName = qName;
		if (eName.equals(BACKGROUND)) {
			this.bGDescriptorBuilder();
		} else if (eName.equals(NAME)) {
			tmpName = bodyContentBuffer.toString().trim();
		} else if (eName.equals(THUMBNAIL)) {
			tmpThumb = bodyContentBuffer.toString().trim();
		} else if (eName.equals(AUTHOR)) {
			tmpAuthor = bodyContentBuffer.toString().trim();
		} else if (eName.equals(DATE)) {
			tmpDate = bodyContentBuffer.toString().trim();
		} else if (eName.equals(DESCRIPTION)) {
			tmpDescription = bodyContentBuffer.toString().trim();
		} else if (eName.equals(BGTYPE)) {
			parseBgType = bodyContentBuffer.toString().trim();
		} else if (eName.equals(PANO_INIT_BG)) {
			initialPanoBackground = Integer.valueOf(
					bodyContentBuffer.toString().trim()).intValue();
		}
		else if (eName.equals(LAYER)) {
			LayerTag = false;
			numLayers++;
			translationCord = new float[3];
			try {
				translationCord[0] = Float.valueOf(tmpTranslationX.trim())
						.floatValue();
				translationCord[1] = Float.valueOf(tmpTranslationY.trim())
						.floatValue();
				translationCord[2] = Float.valueOf(tmpTranslationZ.trim())
						.floatValue();

			} catch (NumberFormatException nfe) {
				System.out
						.println("NumberFormatException: " + nfe.getMessage());
			}
			translationCordNum.add(translationCord);
		}  else if (eName.equals(IMAGE)) {
			ImagesList.add(bodyContentBuffer.toString().trim());
			if (parseBgType.equals(PANORAMA_BG)) {
				numPanoramaImages++;
			}
		} else if (eName.equals(TRANSLATIONX)) {
			tmpTranslationX = bodyContentBuffer.toString().trim();
		} else if (eName.equals(TRANSLATIONY)) {
			tmpTranslationY = bodyContentBuffer.toString().trim();
		} else if (eName.equals(TRANSLATIONZ)) {
			tmpTranslationZ = bodyContentBuffer.toString().trim();

		} else {
			bodyContentBuffer = null;
			throw new SAXException("Undefined Tag: " + eName);
		}
		bodyContentBuffer = null;
	}

	public void characters(char buf[], int offset, int len) throws SAXException {
		if (bodyContentBuffer == null)
			bodyContentBuffer = new StringBuffer(1024);
		    bodyContentBuffer.append(buf, offset, len);
	}

	public void parseConfigFile(String configFile) throws SAXException {
		java.net.URL fileUrl = getClass().getClassLoader().getResource(configFile);
		SAXParserFactory factory = null;
		SAXParser saxParser =null;
		try {
			factory = SAXParserFactory.newInstance();
			
			saxParser = factory.newSAXParser();
			saxParser.parse(fileUrl.toString(),this);
			
		} catch (Exception ex) {
		        System.out.println("Error parsing config "+fileUrl+"   "+configFile);
			ex.printStackTrace ();
			return; 
		}
	}
	public void bGDescriptorBuilder() {
		if (parseBgType.equals(SIMPLE_BG)) {
			String idx = (String) ImagesList.get(0);
			if(onlyCurrent==false) {
			BgComponentList.add(new BgLgComponent(tmpName, tmpAuthor, tmpThumb,
					tmpDate, tmpDescription, idx, BgTypes.SIMPLEBG,configFileURL));
			} else if(onlyCurrent) {
				bgcomp = new BgLgComponent(tmpName, tmpAuthor, tmpThumb,
						tmpDate, tmpDescription, idx, BgTypes.SIMPLEBG,configFileURL);
			}
			resetTmpData();

		} else if (parseBgType.equals(LAYERED_BG)) {
			String[] tabImages = new String[numLayers];
			float[][] tabCords = new float[numLayers][3];
			float[] xyz = new float[3];

			for (int i = 0; i < numLayers; i++) {
				tabImages[i] = (String) ImagesList.get(i);
				xyz = (float[]) translationCordNum.get(i);
				tabCords[i][0] = xyz[0];
				tabCords[i][1] = xyz[1];
				tabCords[i][2] = xyz[2];

			}
			if(onlyCurrent==false) {
			BgComponentList.add(new BgLgComponent(tmpName, tmpAuthor, tmpThumb,
					tmpDate, tmpDescription, numLayers, tabImages, tabCords,
					BgTypes.LAYEREDBG,configFileURL));
			}else if (onlyCurrent) {
				bgcomp = new BgLgComponent(tmpName, tmpAuthor, tmpThumb,
						tmpDate, tmpDescription, numLayers, tabImages, tabCords,
						BgTypes.LAYEREDBG,configFileURL);
			}
			
			resetTmpData();

		} else if (parseBgType.equals(PANORAMA_BG)) {

			String[] tabImages = new String[numPanoramaImages];
			for (int i = 0; i < numPanoramaImages; i++) {
				tabImages[i] = (String) ImagesList.get(i);
			}
			if(onlyCurrent==false) {
			BgComponentList.add(new BgLgComponent(tmpName, tmpAuthor, tmpThumb,
					tmpDate, tmpDescription, tabImages, numPanoramaImages,
					BgTypes.PANORAMABG, initialPanoBackground,configFileURL));
			} else if (onlyCurrent) {
				bgcomp = new BgLgComponent(tmpName, tmpAuthor, tmpThumb,
						tmpDate, tmpDescription, tabImages, numPanoramaImages,
						BgTypes.PANORAMABG, initialPanoBackground,configFileURL);
			}
			resetTmpData();
		} else {
			System.err.print("Error in bGdescriptor - no Defined BG TYPE");
		}
	}
	public void resetTmpData() {
		/*parseBgType = null;
		tmpName = null;
		tmpDescription = null;
		tmpDate = null;
		tmpAuthor = null;
		tmpThumb = null;
		tmpTranslationX = null;
		tmpTranslationY = null;
		tmpTranslationZ = null;*/
		numLayers = 0;
		numPanoramaImages = 0;
		ImagesList.clear();
		translationCordNum.clear();
		//translationCord = null;
		initialPanoBackground = 0;
		//onlyCurrent = false;

	}
	public void parseAllBgFiles() throws SAXException {

		for (int i = 0; i < numBgFiles; i++) {
			try {
				configFileURL = listBg[i].trim();
				parseConfigFile(configFileURL);
				System.out.println(configFileURL);
				
			} catch (Exception e) {
			   i++;
			 e.printStackTrace ();
			}
		}
	}
	  public ArrayList getComponentBgList() {
	        return BgComponentList;
	    }
	  public BgLgComponent getInitialBgComp() {
	        return bgcomp;
	    }
}