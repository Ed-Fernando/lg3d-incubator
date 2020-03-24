package org.jdesktop.lg3d.apps.jmf23D;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;

import java.net.*;

import javax.media.*;
import javax.media.format.RGBFormat;
import javax.media.protocol.*;

import javax.swing.*;


/**
* A JMF demultiplexer that takes HTML as input and outputs RGB data.
* Because of the lack of a proper open source HTML rendering component in
* java/swing, it currently uses javax.swing.JEditorPane to render html pages,
* and thus can't render most pages correctly.
* There is no system to this day to forward the mouse events to this class. This
* class only serves the purpose of displaying static html content.
**/
public class HTMLParser extends AbstractParser {

    public static ContentDescriptor[] contentDescriptors;

    public HTMLParser() {
    }

    public void setSource(javax.media.protocol.DataSource dataSource)
                   throws java.io.IOException, IncompatibleSourceException {
        ds = dataSource;

        try {
            tracks = new Track[] { new HTMLTrack(ds.getLocator().getURL()) };
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getName() {

        return "HTML Parser";
    }

    public javax.media.protocol.ContentDescriptor[] getSupportedInputContentDescriptors() {

        return getSupportedInputContentDescriptorsStatic();
    }

    public static ContentDescriptor[] getSupportedInputContentDescriptorsStatic() {
        contentDescriptors = new ContentDescriptor[] {
            new ContentDescriptor(ContentDescriptor.CONTENT_UNKNOWN), 
            new FileTypeDescriptor(ContentDescriptor.mimeTypeToPackageName(
                                           "text/html"))
        };

        return contentDescriptors;
    }

    public Track[] getTracks()
                      throws java.io.IOException, BadHeaderException {

        return tracks;
    }
}

class HTMLTrack extends AbstractTrack {

    BufferedImage buf;
    boolean enabled = true;
    Format format;
    int sequenceNumber;
    int[] data;
    int[] imageData;
    long timeStamp;
    Graphics2D g;
    JEditorPane htmlPane;
    long time;
    
    public HTMLTrack(URL url)
              throws MalformedURLException {

        try {
            System.err.println("now loading HTML parser");
            htmlPane = new JEditorPane(url);
        } catch (java.io.IOException io) {
        }
        buf = new BufferedImage(512, 512, BufferedImage.TYPE_INT_RGB);
	data = new int[buf.getWidth()*buf.getHeight()];
        imageData = ((DataBufferInt)buf.getRaster().getDataBuffer()).getData();
        format = new RGBFormat(new Dimension(buf.getWidth(), buf.getHeight()), 
                               buf.getWidth() * buf.getHeight(), int[].class, 
                               25.0f, 32, 0xff << 16, 0xff << 8, 0xff, 1, 
                               buf.getWidth(), // flipped
                               Format.FALSE, Format.NOT_SPECIFIED);
        htmlPane.setBounds(0, 0, buf.getWidth(), buf.getHeight());
    }

    public Format getFormat() {

        return format;
    }

    public Time getStartTime() {

        return new Time(0);
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
	try{
		Thread.sleep(1000);
	} catch(Exception ie){}
        g = (Graphics2D)buf.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                           RenderingHints.VALUE_ANTIALIAS_ON);
        g.setClip(0, 0, buf.getWidth(), buf.getHeight());
        g.clearRect(0, 0, buf.getWidth(), buf.getHeight());
	htmlPane.paint(g);
	System.arraycopy(imageData, 0, data, 0, data.length);
        buffer.setData(data);
        buffer.setOffset(0);
        buffer.setLength(data.length);
        buffer.setFormat(format);
        buffer.setSequenceNumber(++sequenceNumber);
        buffer.setDuration(4000000000l);
    }

    public void setEnabled(boolean t) {
        enabled = t;
    }

    public void setTrackListener(TrackListener listener) {
    }

    public Time getDuration() {

        return TIME_UNKNOWN;
    }
}