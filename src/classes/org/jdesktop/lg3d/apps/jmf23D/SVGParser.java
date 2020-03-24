package org.jdesktop.lg3d.apps.jmf23D;

import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;

import java.net.*;

import javax.media.*;
import javax.media.format.RGBFormat;
import javax.media.protocol.*;

import com.kitfox.svg.SVGDiagram;
import com.kitfox.svg.SVGUniverse;

/**
* A JMF demultiplexer that takes SVG as input and outputs RGB data.
**/

public class SVGParser implements Demultiplexer {
    DataSource ds;
    SVGUniverse univ;
    Track[] tracks;
    public static ContentDescriptor[] contentDescriptors;
    static String mimeMediaType;
    static String extension;
    
    public SVGParser(){
    	ds = null;
        tracks = null;
	univ = null;
    }
    public Object getControl(String str) {
    	return null;
    }
    
    public void setSource(javax.media.protocol.DataSource dataSource)
    throws java.io.IOException, IncompatibleSourceException {
        ds = dataSource;
	univ = new SVGUniverse();
	try{
		tracks = new Track[]{new SVGTrack(univ, new URI(ds.getLocator().toString()))};
	}catch(Exception e){e.printStackTrace();}
    }
    
    public Time setPosition(Time time, int param) {
    	return Time.TIME_UNKNOWN;
    }
    
    public void stop() {
    try{
    	ds.stop();
    	}catch(java.io.IOException io){}
    }
    
    public void start()
    throws java.io.IOException {
    
    	ds.start();
    }
    
    public void close() {
    	ds.disconnect();
    }
    
    public Object[] getControls() {
    	return new Object[0];
    }
    
    public Time getDuration() {
    	return Time.TIME_UNKNOWN;
    }
    
    public Time getMediaTime() {
    	return Time.TIME_UNKNOWN;
    }
    
    public String getName() {
    	return "SVG Parser";
    }
    
    public ContentDescriptor[] getSupportedInputContentDescriptors() {
        return getSupportedInputContentDescriptorsStatic();
    }
    
    public static ContentDescriptor[] getSupportedInputContentDescriptorsStatic () {
	   contentDescriptors = new ContentDescriptor[] {
	    new ContentDescriptor(ContentDescriptor.CONTENT_UNKNOWN),
            new FileTypeDescriptor(ContentDescriptor.mimeTypeToPackageName("image/svg+xml"))
        };
	return contentDescriptors;
    }
    
    public Track[] getTracks()
    throws java.io.IOException, BadHeaderException {
    	return tracks;
    }
    
    public boolean isPositionable() {
        return false;
    }
    
    public boolean isRandomAccess() {
        return false;
    }
    
    public void open()
    throws ResourceUnavailableException {
    try{
    	ds.connect();
	}catch(java.io.IOException io){}
    }
    
    public void reset() {
    }
    
    
}

class SVGTrack implements Track {
    
    BufferedImage buf;
    SVGDiagram diagram;
    boolean enabled = true;
    Format format;
    int sequenceNumber;
    SVGUniverse univ;
    int[] data;
    int[] imageData;
    long timeStamp;
    Time startTime;
    TimeBase timeBase = Manager.getSystemTimeBase();
    Graphics2D g;
    
    public SVGTrack(SVGUniverse univ, URI uri) throws MalformedURLException {
    	this.univ = univ;
	diagram = univ.getDiagram(uri);
        buf = new BufferedImage(512, 512, BufferedImage.TYPE_INT_RGB);
	data = new int[buf.getWidth()*buf.getHeight()];
        imageData = ((DataBufferInt)buf.getRaster().getDataBuffer()).getData();
        format = new RGBFormat (
             new Dimension(buf.getWidth(), buf.getHeight()),
             buf.getWidth()*buf.getHeight(),
             int[].class,
             25.0f,
             32,
             0xff<<16,0xff<<8, 0xff,
             1,
	     buf.getWidth(),
             Format.FALSE, // flipped
             Format.NOT_SPECIFIED
             );
    }
    
    public Format getFormat() {
	return format;
    }
    
    public Time getStartTime() {
        return startTime;
    }
    
    public boolean isEnabled() {
        return enabled;
    }
    
    public Time mapFrameToTime(int frameNumber) {
    	return TIME_UNKNOWN;
    }
    
    public int mapTimeToFrame(Time t) {
    	return FRAME_UNKNOWN;
    }
    
    public void readFrame(Buffer buffer) {
    	if(startTime==null)
	    startTime=timeBase.getTime();
	if(g==null)
	    g = (Graphics2D) buf.createGraphics();
		
	g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	g.setClip(0,0,buf.getWidth(),buf.getHeight());
	g.clearRect(0,0,buf.getWidth(),buf.getHeight());
		
	univ.setCurTime(timeBase.getTime().getSeconds()-startTime.getSeconds());
	univ.updateTime();
	diagram.render(g);
	System.arraycopy(imageData, 0, data, 0, data.length);
	buffer.setData(data);
	buffer.setOffset(0);
	buffer.setLength(data.length);
	buffer.setFormat(format);
	buffer.setSequenceNumber(++sequenceNumber);
	buffer.setDuration(40000000l);
	
    }
    
    public void setEnabled(boolean t) {
        enabled = t;
    }
    
    public void setTrackListener(TrackListener listener) {
    }
    
    public Time getDuration(){
    	return TIME_UNKNOWN;
    }
}