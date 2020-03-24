package org.jdesktop.lg3d.apps.jmf23D;

import java.awt.event.*;

import java.beans.*;

import java.io.*;

import java.lang.management.*;

import java.net.*;

import static java.util.logging.Level.*;
import java.util.logging.Logger;

import javax.management.*;

import javax.media.*;
import static javax.media.PackageManager.*;
import static javax.media.PlugInManager.*;
import javax.media.control.*;
import javax.media.format.*;
import javax.media.protocol.*;

import javax.swing.*;
import java.util.Vector;
import java.util.Enumeration;

import org.apache.commons.cli.*;


/**
* Main class of the API. Reads multimedia from a URI and displays it in the 3D world.
**/
public class Algea3D implements Algea3DMBean {

    private static Logger logger = Logger.getLogger("lg.JMF23D");
    private static JMFConfig myConfig;
    public static String jxtaGroup;
    int[] waitSync = new int[0];
    boolean stateTransOK = true;
    boolean dispCtrl = true;
    boolean dispMessage = true;
    boolean firstRun = true;

    // The default media locator to start with
    private static MediaLocator DEFAULT_ML = new MediaLocator(
		System.getProperty("lg.appcodebase") + "/GoMonkeyDemo.ogg");

    // The default media source to use for conferencing
    private static MediaLocator DEFAULT_CONF_SOURCE = null;

    static {
	String fn;

	if ((fn = System.getenv("ALG3D_ML")) != null)
	    DEFAULT_ML = new MediaLocator(fn);

	if ((fn = System.getenv("ALG3D_CONF_SOURCE")) != null) {
	    if ("".equals(DEFAULT_CONF_SOURCE))
		DEFAULT_CONF_SOURCE = DEFAULT_ML;
	    else
		DEFAULT_CONF_SOURCE = new MediaLocator(fn);
	}
    }

    public static void main(String[] args)
                     throws Exception {

        BasicParser bp = new BasicParser();
        Options op = new Options();
        op.addOption("m", true, "URI of the media source");
        op.addOption("vin", true, 
                     "URI of your webcam, usually v4l:0 on linux or vfw:0 on windows");
        op.addOption("ain", true, 
                     "URI of your microphone, try javasound:44100");

        CommandLine command = bp.parse(op, args);
        String url = command.getOptionValue("m", null);
        String vin = command.getOptionValue("vin", null);
        String ain = command.getOptionValue("ain", null);

        // if (url == null) url = AlgeaUtilities.getMovieURI();

        logger.info("url = " + url);

        MediaLocator ml    = (url != null) ? new MediaLocator(url) : DEFAULT_ML;
        MediaLocator vinML = (vin != null) ? new MediaLocator(vin) : DEFAULT_CONF_SOURCE;
        MediaLocator ainML = (ain == null) ? null : new MediaLocator(ain);
        Algea3D alg = new Algea3D(ml, vinML, ainML);
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName obName = new ObjectName(
                                    "org.jdesktop.lg3d.apps.jmf23D:type=Algea3D");

        // mbs.registerMBean(alg, obName);
    }

    public Algea3D() {
	this(DEFAULT_ML, null, null);
    }

    /**
    * This method starts the video player. If ml's protocol is "alg3d:", starts a video conference.
    * Otherwise it will render the media corresponding to the mediaLocator in LG3D.
    * @param ml		the input media locator. 
    * @param vin	The video input source, needed for video conferencing, otherwise can be null
    * @param ain	The audio input source, needed for video conferencing, otherwise can be null
    **/
    public Algea3D(MediaLocator ml, MediaLocator vin, MediaLocator ain) {

        Player p = null;

        try {
		if(firstRun=true) {
			registerAll();
			firstRun=false;
		}

            if (ml.getProtocol().equals("alg3d")) {
                new ConferenceThread(ml, vin, ain).start();
                return;
            }

            DataSource ds = Manager.createDataSource(ml);
            p = AlgeaUtilities.createRealizedPlayer(ds);

        } catch (Exception ex) {
            logger.severe("failed to create a processor for movie " + ml);
            ex.printStackTrace();
            printUsage();
        }

        p.start();
    }

    static void printUsage() {

        HelpFormatter hf = new HelpFormatter();
        Options op = new Options();
        op.addOption("m", true, "URI of the media source");
        hf.printUsage(new PrintWriter(System.out), 40, "java Algea3D", op);
        System.out.println("java Algea3D [-m <url-of-the-movie>]");
        System.out.println(
                "e.g. java Algea3D -m file:/home/hsy/media/video/cinepak/gun1.mov");
    }

    /**
    * Register the PlugIns provided by this package to the Java Media Framework.
    **/
    public static void registerAll() {
	    
	    if(CaptureDeviceManager.getDeviceList(new VideoFormat(
	    VideoFormat.RGB)).size()<1) {
		    try{
			    if(System.getProperty("os.name").startsWith("W")){
				Class.forName("VFWAuto").newInstance();
			    } else if(System.getProperty("os.name").startsWith("L")){
				    Class.forName("V4LAuto").newInstance();
			    } else {
				    Class.forName("SunVideoAuto");
				    Class.forName("SunVideoPlusAuto");
			    }
				    
		    } catch(Exception e){}
	    }
	    if(CaptureDeviceManager.getDeviceList(new VideoFormat(
	    VideoFormat.RGB)).size()<1){
		    String alert = "<html>You don't seem to have a"+
		    		"webcam connected. <br>Some parts of this"+
				"application might not be available. <br>"+
				"If you do have a webcam connected, please make sure"+
				"you have correctly downloaded and installed the Java Media Framework at:<br>"+
				"<a href='http://java.sun.com/products/java-media/jmf/2.1.1/download.html'>"+
				"http://java.sun.com/products/java-media/jmf/2.1.1/download.html</a></html>";
				
		    // javax.swing.JOptionPane.showMessageDialog(null, alert, "No webcam found", JOptionPane.WARNING_MESSAGE);
	    }
	    

	com.sun.media.MimeManager.addMimeType("svg", "image/svg+xml");
        com.sun.media.MimeManager.addMimeType("html", "text/html");

	Vector v = getProtocolPrefixList();
        v.add("org.jdesktop.lg3d.apps.jmf23D");
	setProtocolPrefixList(v);

        v = getContentPrefixList();
        v.add("org.jdesktop.lg3d.apps.jmf23D");
	setContentPrefixList(v);

        String svgParser = SVGParser.class.getName();
        String htmlParser = HTMLParser.class.getName();
        String r3d = Renderer3D.class.getName();
        addPlugIn(r3d, Renderer3D.getSupportedInputFormatsStatic(), 
                  new Format[0], RENDERER);
        addPlugIn(svgParser, 
                  SVGParser.getSupportedInputContentDescriptorsStatic(), 
                  getSupportedOutputFormats(svgParser, DEMULTIPLEXER), 
                  DEMULTIPLEXER);
        addPlugIn(htmlParser, 
                  HTMLParser.getSupportedInputContentDescriptorsStatic(), 
                  getSupportedOutputFormats(htmlParser, DEMULTIPLEXER), 
                  DEMULTIPLEXER);

        java.util.Vector renderers = getPlugInList(null, null, RENDERER);
        renderers.remove(r3d);
        renderers.add(0, r3d);
        setPlugInList(renderers, RENDERER);
    }
}
