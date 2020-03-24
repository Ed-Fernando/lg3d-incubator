package org.jdesktop.lg3d.apps.bgmanager;

import java.net.URL;
import org.jdesktop.lg3d.scenemanager.utils.background.Background;
import org.jdesktop.lg3d.scenemanager.utils.background.LayeredImageBackground;
import org.jdesktop.lg3d.scenemanager.utils.background.PanoImageBackground;
import org.jdesktop.lg3d.scenemanager.utils.background.SwayingSimpleImageBackground;
import org.jdesktop.lg3d.scenemanager.utils.event.BackgroundChangeRequestEvent;
import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.utils.c3danimation.NaturalMotionAnimation;
import org.jdesktop.lg3d.utils.shape.GlassyPanel;
import org.jdesktop.lg3d.utils.shape.ImagePanel;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Cursor3D;


public class BgLgComponent extends Component3D {
    private String author;
    private String name;
    private String date;
    private String description;
    private String thumbURL;
    private BgTypes bgType;
    private String configURL;
    private String[] panoImages;
    private int numImages;
    private int initialBackground;
    
    private String simpleFileName;
    
    private int numLayers;
    private String [] layerFileNames;
    private float [][] transCord;
    
    private boolean isFirstInit = true;
    
    public ImagePanel earth = null;
    
    public BgLgComponent (String name,String author, String thumbURL, String date ,
	    String description, String [] panoImages,int numImages,BgTypes bgType,int initialBackround,String configURL) {
	this.configURL = configURL;
	this.author = author;
	this.name = name;
	this.date = date;
	this.description = description;
	this.thumbURL = thumbURL;
	this.numImages = numImages;
	this.panoImages = new String[numImages];
	System.arraycopy (panoImages,0,this.panoImages,0,numImages);
	this.bgType = bgType;
	this.initialBackground = initialBackround;	
    }
    public BgLgComponent (String name,String author, String thumbURL, String date ,
	    String description ,String simpleFileName,BgTypes bgType,String configURL) {
	this.configURL = configURL;
	this.author = author;
	this.name = name;
	this.date = date;
	this.description = description;
	this.thumbURL = thumbURL;
	this.simpleFileName = simpleFileName;
	this.bgType = bgType;
		
    }
    public BgLgComponent (String name,String author, String thumbURL, String date ,
	    String description,int numLayers,String[] layerFileNames,float [][] transCord,
	    BgTypes bgType,String configURL) {
	this.configURL = configURL;
	this.author = author;
	this.name = name;
	this.date = date;
	this.description = description;
	this.thumbURL = thumbURL;
	this.numLayers = numLayers;
	this.layerFileNames = layerFileNames;
	this.transCord = transCord;
	this.bgType = bgType;	
    }
    
    public void setConfigURL (String configURL) {
	this.configURL = configURL;
    }
    public void setAuthor (String author) {
	this.author = author;
    }
    public void setName (String name) {
	this.name = name;
    }
    public void setDate (String date) {
	this.date = date;
    }
    public void setDescription (String description) {
	this.description = description;
    }
    public void setThumbURL (String thumbURL) {
	this.thumbURL = thumbURL;
    }
    public void setPanoImages (String[] panoImages) {
	this.panoImages = panoImages;
    }
    public void setSipleFileName (String simpleFileName) {
	this.simpleFileName = simpleFileName;
    }
    public void setBgType (BgTypes bgType) {
	this.bgType = bgType;
    }
    public void setLayerFileNames (String[] layerFileNames) {
	this.layerFileNames = layerFileNames;
    }
    public void setTransCord (float[][] transCord) {
	this.transCord = transCord;
    }
    public void setInitBg (int initialBackground) {
	this.initialBackground = initialBackground;
    }
    public int getInitBg () {
	return this.initialBackground;
    }
    public float[][] getTransCord () {
	return this.transCord;
    }
    public URL [] getLayerFileNames () {
	URL urls[] = new URL[layerFileNames.length];
	for(int i=0; i<urls.length; i++)
	    urls[i] = getClass ().getClassLoader ().getResource (layerFileNames[i]);
	return urls;
    }
    public BgTypes getBgTpe () {
	return this.bgType;
    }
    public URL getSipleFileName () {
	return  getClass ().getClassLoader ().getResource (simpleFileName);
    }
    public int getNumImages () {
	return this.numImages;
    }
    public URL[] getPanoImages () {
	URL urls[] = new URL[panoImages.length];
	for(int i=0; i<urls.length; i++)
	    urls[i] = getClass ().getClassLoader ().getResource (panoImages[i]);
	return urls;
    }
    public String getAuthor () {
	return this.author;
    }
    public String getName () {
	return this.name;
    }
    public String getDate () {
	return this.date;
    }
    public String getDescription () {
	return this.description;
    }
    public URL getThumbURL () {
	return this.getClass ().getClassLoader ().getResource (thumbURL);
    }
    public String getConfigURL () {
	return this.configURL;
    }
    
    public void init () {
	
	if (isFirstInit) {
	    this.setAnimation (new NaturalMotionAnimation (100));
	    
	    GlassyPanel panel = null;
	    Appearance appearance = new SimpleAppearance (0.6f, 1.0f, 0.6f, 1.0f,
		    SimpleAppearance.DISABLE_CULLING);
	    try {
		earth = new ImagePanel ( getClass ().getClassLoader ().getResource (thumbURL),0.025f,0.02f,true);
		panel = new GlassyPanel (0.027f,0.022f,0.001f,0.0001f, appearance);
		this.addChild (earth);
		this.addChild (panel);
	    } catch (Exception e) {
		e.printStackTrace ();
		
	    }
	    this.setCursor (Cursor3D.SMALL_CURSOR);
	    isFirstInit = false;   
	}	
    }
    public void setBackground (){	
	this.postEvent (new BackgroundChangeRequestEvent (getBackground ()));
    }
    public Background getBackground (){
	Background trueBg = null;
	if (getBgTpe () == BgTypes.SIMPLEBG) {
	    trueBg = new SwayingSimpleImageBackground (getSipleFileName ());
	} else if (getBgTpe () == BgTypes.PANORAMABG) {
	    trueBg = new PanoImageBackground (getPanoImages (),getInitBg ());
	} else if (getBgTpe () == BgTypes.LAYEREDBG) {
	    trueBg = new LayeredImageBackground (getLayerFileNames (),getTransCord ());	    
	}
	return trueBg;
    }
}
