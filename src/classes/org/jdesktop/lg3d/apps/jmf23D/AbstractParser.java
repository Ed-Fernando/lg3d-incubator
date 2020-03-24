package org.jdesktop.lg3d.apps.jmf23D;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;

import java.net.*;

import javax.media.*;
import javax.media.format.RGBFormat;
import javax.media.protocol.*;


/**
* Base class for both HTMLParser and SVGParser.
**/
public abstract class AbstractParser
    implements Demultiplexer {

    DataSource ds;
    Track[] tracks;
    public static ContentDescriptor[] contentDescriptors;
    static String mimeMediaType;
    static String extension;

    public AbstractParser() {
    }

    public Object getControl(String str) {

        return null;
    }

    public abstract void setSource(javax.media.protocol.DataSource dataSource)
                            throws java.io.IOException, 
                                   IncompatibleSourceException;

    public Time setPosition(Time time, int param) {

        return Time.TIME_UNKNOWN;
    }

    public void stop() {

        try {
            ds.stop();
        } catch (java.io.IOException io) {
        }
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

    public abstract String getName();

    public abstract Track[] getTracks()
                               throws java.io.IOException, BadHeaderException;

    public boolean isPositionable() {

        return false;
    }

    public boolean isRandomAccess() {

        return false;
    }

    public void open()
              throws ResourceUnavailableException {

        try {
            ds.connect();
        } catch (java.io.IOException io) {
        }
    }

    public void reset() {
    }
}

abstract class AbstractTrack
    implements Track {

    BufferedImage buf;
    boolean enabled = true;
    Format format;
    int sequenceNumber;
    int[] data;
    long timeStamp;
    Graphics2D g;
    Time startTime;

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

    public abstract void readFrame(Buffer buffer);

    public void setEnabled(boolean t) {
        enabled = t;
    }

    public void setTrackListener(TrackListener listener) {
    }

    public Time getDuration() {

        return TIME_UNKNOWN;
    }
}