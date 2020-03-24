/*
 * LG3D Incubator Project - gol3d
 * A simple Game of Life for Looking Glass 3D
 *
 * $RCSfile: GolController.java,v $
 *
 * The contents of this file are subject to the GNU General Public
 * License, Version 2 (the "License"); you may not use this file
 * except in compliance with the License. A copy of the License is
 * available at http://www.opensource.org/licenses/gpl-license.php.
 *
 * $Revision: 1.5 $
 * $Date: 2006-08-23 17:05:08 $
 * Author: Van der Haegen Mathieu (dwarfy)
 */

package org.jdesktop.lg3d.apps.gol3d;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.beans.ExceptionListener;
import java.beans.XMLDecoder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.vecmath.Vector3f;
import org.jdesktop.lg3d.utils.action.ActionNoArg;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.utils.shape.Box;
import org.jdesktop.lg3d.utils.shape.FuzzyEdgePanel;
import org.jdesktop.lg3d.utils.shape.ImagePanel;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.sg.Texture;
import org.jdesktop.lg3d.sg.TextureAttributes;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Frame3D;
import org.jdesktop.lg3d.wg.event.LgEventSource;
import org.jdesktop.lg3d.sg.utils.image.TextureLoader;
import org.jdesktop.lg3d.sg.utils.transparency.TransparencyOrderedGroup;



public class GolController {
    //private GolThread p;
    private Boolean running;
    private Color GroundColor;
    private Color BarsColor;
    private GolConfig gcfg;
    
    private static Logger logger = Logger.getLogger("lg.gol3d");
    
    private String[] configList;
    private int configPos;
    private GolGridComponent golgrid;
    private TextBox t;
    private RefreshThread p;
    
    
    
    GolController(int minSurvive,int maxSurvive,int born) {
        try {
            getGolConfig();
        }  catch (FileNotFoundException e ) {
            logger.log(Level.SEVERE,"FileNotFoundException : Could not open GolConfig.xml  !");
            return;
        }
        
        configList = getGridConfigsList();
        configPos = 0;
        
        display();
        p = new RefreshThread();
        p.setInterGenTime(gcfg.interGenTime);
        p.setGenData(minSurvive,maxSurvive,born);
        p.setGolGrid(golgrid);
        p.start();
    }
    
    private void getGolConfig() throws FileNotFoundException {        
        ExceptionListener exceptionListener = new ExceptionListener() {
            public void exceptionThrown(Exception e) {
                logger.warning("Problem Reading Config File : "+e.getMessage());
            }            
        };
        
        InputStream is = getClass().getClassLoader().getResourceAsStream("org/jdesktop/lg3d/apps/gol3d/GolConfig.xml");
        XMLDecoder decoder = new XMLDecoder(is, null, exceptionListener, getClass().getClassLoader());
        gcfg = (GolConfig) decoder.readObject();
        
        decoder.close();
    }
    
    private void nextGrid() {
        int len = configList.length;
        if (len <= 1) {
            return;
        } else {
            
            configPos++;
            if (configPos == len) {
                configPos = 0;
            }
            
            if (!(p.isSuspended())) {
                p.toggleState();
            }
            
            t.setText(configList[configPos]);
            golgrid.setGrid(configList[configPos]);
            //golgrid.refresh();
            
        }
    }
    
    private void prevGrid() {
        int len = configList.length;
        if (len <= 1) {
            return;
        } else {
            
            configPos--;
            if (configPos == -1) {
                configPos = len-1;
            }
            
            if (!(p.isSuspended())) {
                p.toggleState();
            }
            
            t.setText(configList[configPos]);
            golgrid.setGrid(configList[configPos]);
            //golgrid.refresh();
            
        }
    }
    
    private void reloadCurrentGrid() {
        if (!(p.isSuspended())) {
            p.toggleState();
        }
        
        golgrid.setGrid(configList[configPos]);
        //golgrid.refresh();
    }
    
    private String[] getGridConfigsList() {
        
        URL url = getClass().getClassLoader().getResource("org/jdesktop/lg3d/apps/gol3d/configs/");
        File dir = null;
        String[] children = null;
        JarFile jf = null;
        if ( url.getProtocol().startsWith("jar") ) {
            try {
		String path = url.getPath();
	        path = path.substring(0, path.indexOf("!"));	
		path = path.substring(path.indexOf(":") + 1);
                jf = new JarFile(new File(path));
                ArrayList<String> alChildren = new ArrayList<String>();
                for ( Enumeration<JarEntry> entries = jf.entries(); entries.hasMoreElements(); ) {
                    JarEntry entry = entries.nextElement();
                    if ( !entry.isDirectory() &&
                            entry.getName().startsWith("org/jdesktop/lg3d/apps/gol3d/configs/") ) {
			String child = entry.getName().substring(entry.getName().lastIndexOf("/") + 1);
                        alChildren.add(child);
                    }
                }
                children = new String[alChildren.size()];
                children = alChildren.toArray(children);
                
            } catch ( Exception ex ){
                ex.printStackTrace();
            }
        } else {
            try {
                dir = new File(url.toURI());
            } catch (URISyntaxException use) {
                // exception handling...
            }
            children = dir.list();
        }
        String[] temp = new String[children.length];
        
        int x=0;
        
        if (children == null) {
            logger.log(Level.SEVERE,"No grid configs files found");
            return null;
        } else {
            for (int i=0; i<children.length; i++) {
                // Get filename of file or directory
                URL url2 = getClass().getClassLoader().getResource("org/jdesktop/lg3d/apps/gol3d/configs/" + children[i]);
                try {
	            if ( url2.getProtocol().startsWith("jar") ) {
			temp[x] = children[i];
			x++;
		    } else {
                    	File f = new File(url2.toURI());
                    	if (f.isFile()) {
                        	temp[x] = children[i];
                        	x++;
                    	}
		    }
		    
                } catch (URISyntaxException use) {
                    // exception handling...
                }
            }
        }
        
        String[] returnS = new String[x];
        
        for (int y=0 ; y<x ; y++) {
            returnS[y] = temp[y];
        }
        
        //for (int y=0;y<returnS.length;y++)
        //    logger.log(Level.INFO,"returnS["+ y + "] : "+ children[y]);
        
        return returnS;
    }
    
    void display() {
        //take the first list item and display it
        
        Frame3D frame = new Frame3D();
        
        ImageButtonComponent nextcomp = new ImageButtonComponent("next.png",0.01f,0.01f,0.005f,gcfg);
        ImageButtonComponent prevcomp = new ImageButtonComponent("previous.png",0.01f,0.01f,0.005f,gcfg);
        GolGroundComponent golground = new GolGroundComponent(gcfg);
        
        nextcomp.setTranslation(0.07f,-0.05f,0.04f);
        prevcomp.setTranslation(-0.07f,-0.05f,0.04f);
        
        golground.setRotationAxis(1.0f,0.0f,0.0f);
        golground.setRotationAngle(45.0f);
        
        nextcomp.addListener(new MouseClickedEventAdapter(new ActionNoArg() {
            public void performAction(LgEventSource source) {
                nextGrid();
            }
        }));
        
        prevcomp.addListener(new MouseClickedEventAdapter(new ActionNoArg() {
            public void performAction(LgEventSource source) {
                prevGrid();
            }
        }));
        
        golground.addListener(new MouseClickedEventAdapter(new ActionNoArg() {
            public void performAction(LgEventSource source) {
                p.toggleState();
                //golgrid.nextGenAndRefresh(2,3,3);
            }
        }));
        
        golground.setMouseEventPropagatable(true);
        prevcomp.setMouseEventPropagatable(true);
        nextcomp.setMouseEventPropagatable(true);
        
        if (configList != null)  {
            golgrid = new GolGridComponent(gcfg);
            golgrid.setGrid(configList[0]);
            //golgrid.refresh();
            golgrid.setRotationAxis(1.0f,0.0f,0.0f);
            golgrid.setRotationAngle(45.0f);
            golgrid.setPreferredSize(new Vector3f(0.1f,0.1f,0.1f)); //?
            
            t = new TextBox(0.05f,0.005f,0.005f,gcfg);
            t.setText(configList[0]);
            t.setTranslation(0.0f,-0.05f,0.04f);
            
            t.addListener(new MouseClickedEventAdapter(new ActionNoArg() {
                public void performAction(LgEventSource source) {
                    reloadCurrentGrid();
                }
            }));
            t.setMouseEventPropagatable(true);
            
        } else {
            t = new TextBox(0.05f,0.005f,0.005f,gcfg);
            t.setText("No grid config file found!");
            t.setTranslation(0.0f,-0.05f,0.04f);
        }
        
        TransparencyOrderedGroup tog = new TransparencyOrderedGroup();
        Component3D c3d = new Component3D();
        
        tog.addChild(golground);
        tog.addChild(golgrid);
        c3d.addChild(tog);
        
        frame.addChild(t);
        frame.addChild(nextcomp);
        frame.addChild(prevcomp);
//        frame.addChild(golground);
//        frame.addChild(golgrid);
        frame.addChild(c3d);
        
        frame.changeEnabled(true);
        frame.changeVisible(true);
        
    }
    
    private class TextBox extends Component3D {
        private Appearance appearance = null;
        private Texture texture;
        private TextureAttributes texattr;
        private BufferedImage image;
        private TextureLoader loader;
        private Graphics2D g;
        private GolConfig cfg;
        
        private float w,h,d;
        
        TextBox(float width,float height,float depth,GolConfig gcfg) {
            w = width;
            h = height;
            d = depth;
            cfg=gcfg;
            
            texture = null;
            
            texattr = new TextureAttributes();
            texattr.setTextureMode(TextureAttributes.REPLACE);
            
            appearance = new SimpleAppearance(0.0f, 0.0f, 0.0f, gcfg.transparency,
                    SimpleAppearance.ENABLE_TEXTURE
                    | SimpleAppearance.DISABLE_CULLING);
            
            appearance.setTextureAttributes(texattr);
            appearance.setCapability(Appearance.ALLOW_TEXTURE_READ);
            appearance.setCapability(Appearance.ALLOW_TEXTURE_WRITE);
            
            Component3D comp = new Component3D();
            FuzzyEdgePanel panel = new FuzzyEdgePanel(width*2, height*2, 0.0f, appearance);
            
            comp.addChild(panel);
            comp.setTranslation(0.0f,0.0f,depth + (depth / 100));
            
            SimpleAppearance app2 = new SimpleAppearance(gcfg.cButtonR,gcfg.cButtonG,gcfg.cButtonB,gcfg.transparency);
            Box box = new Box(width,height,depth,Box.GENERATE_NORMALS,app2);
            
            this.addChild(box);
            this.addChild(comp);
        }
        
        public void setText(String text) {
            Color color = new Color(cfg.cFontR ,cfg.cFontG ,cfg.cFontB);
            int style = Font.BOLD;
            
            
            
            image = new BufferedImage(500, 50, BufferedImage.TYPE_INT_ARGB);
            g = (Graphics2D) image.getGraphics();
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g.setColor(color);
            g.setFont(new Font("Serif", style, 30));
            
            Graphics2D g2d = (Graphics2D)g;
            FontMetrics fontMetrics = g2d.getFontMetrics();
            
            g.drawString(text, (500-fontMetrics.stringWidth(text))/2, 30);
            g.dispose();
            
            loader = new TextureLoader(image);
            texture = loader.getTexture();
            appearance.setTexture(texture);
        }
    }
    
    private class ImageButtonComponent extends Component3D {
        ImageButtonComponent(String fname,float width,float height,float depth,GolConfig gcfg) {
            
            URL imageURL = getClass().getClassLoader().getResource("org/jdesktop/lg3d/apps/gol3d/images/" + fname);
            logger.warning("ImageURL "+imageURL);
            logger.warning("Cfg "+gcfg);
            
            SimpleAppearance app
                    = new SimpleAppearance(gcfg.cButtonR,gcfg.cButtonG, gcfg.cButtonB,gcfg.transparency);
            
            Box body = new Box(width,height,depth,Box.GENERATE_NORMALS, app);
            
            this.addChild(body);
            
            try {
                Component3D c = new Component3D();
                ImagePanel p = new ImagePanel(imageURL,width,height);
                
                c.addChild(p);
                c.setTranslation(0, 0, depth + (depth / 100));
                this.addChild(c);
                
            } catch (FileNotFoundException ex) {
                logger.log(Level.SEVERE,"ImageButtonComponent - FileNotFoundException : " + fname);
            } catch (IOException ec) {
                logger.log(Level.SEVERE,"ImageButtonComponent - IOException : " + fname);
            }
        }
    }
    
    private class RefreshThread extends Thread {
        
        private long interGenTime = 750;
        private boolean threadSuspended = true;
        long minPrime;
        private GolGridComponent golgrid;
        int biSurvive,bsSurvive,born;
        
        public void setInterGenTime(int t) {
            interGenTime = t;
        }
        
        public void setGenData(int biS,int bsS,int bo) {
            biSurvive = biS;
            bsSurvive = bsS;
            born = bo;
        }
        
        private void setGolGrid(GolGridComponent gg) {
            golgrid = gg;
        }
        
        public boolean isSuspended() {
            return threadSuspended;
        }
        
        public synchronized void toggleState() {
            if (threadSuspended) {
                threadSuspended = false;
                notify();
            } else {
                threadSuspended = true;
            }
        }
        public void run() {
            while (true) {
                try {
                    p.sleep(interGenTime);
                    synchronized(this) {
                        while (threadSuspended)
                            p.wait();
                        
                    }
                } catch (InterruptedException e) {
                }
                golgrid.nextGeneration(biSurvive,bsSurvive,born);
            }
        }
        
        
        
    }
}
