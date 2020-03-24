package org.jdesktop.lg3d.apps.jmf23D;

import java.io.*;

import javax.media.*;
import javax.media.protocol.*;

import org.jdesktop.lg3d.scenemanager.utils.event.*;
import org.jdesktop.lg3d.wg.*;


/**
* Users of this API should use methods found in this class to manage 
*
**/
public class VideoManager {

    static Frame3D frame = new Frame3D();

    /**
    * Creates a Player whose video will appear in the Looking Glass desktop.
    * @param mediaURI  a URI formatted String that locates a media file.
    *
    **/
    public static Player createMoviePlayer(String mediaURI) {

        try {

            DataSource ds = Manager.createDataSource(new MediaLocator(mediaURI));

            return AlgeaUtilities.createRealizedPlayer(ds);
        } catch (Exception e) {
        }

        return null;
    }
    /**
    * Sets the video of the Player in the background of the Looking Glass desktop.
    *
    **/
    public static void setAsBackground(Player p) {

        Renderer3DControl r3d = null;

        for (Control cont: p.getControls()) {

            if (cont instanceof Renderer3DControl) {
                r3d = (Renderer3DControl)cont;
            }
        }

        if (r3d == null)
            throw new IllegalArgumentException("Player must have 3D video");

        frame.postEvent(new BackgroundChangeRequestEvent(r3d.getBackground()));
    }

    /**
    * Sets the video of the provided Player in foreground.
    * 
    **/
    public static void setFullScreen(Player p) {

        Renderer3DControl r3d = null;

        for (Control cont: p.getControls()) {

            if (cont instanceof Renderer3DControl) {
                r3d = (Renderer3DControl)cont;
            }
        }

        if (r3d == null)
            throw new IllegalArgumentException("Player must have 3D video");

        r3d.foreground.setAppearance(r3d.renderer3D.getAppearance());
        r3d.foreground.changeVisible(true);
    }
    
    /**
    * Replaces the visual component of the Player with this Component3D. Each
    * Shape3D contained in c3d will have it's appearance set to the video of the Player.
    * The Player's previous component will be detached from scenegraph. This method does not
    * add c3d to the scene.
    **/
    public static void setComponent(Player p, Component3D c3d) {
	    Renderer3DControl r3d = null;

        for (Control cont: p.getControls()) {

            if (cont instanceof Renderer3DControl) {
                r3d = (Renderer3DControl)cont;
            }
        }

        if (r3d == null)
            throw new IllegalArgumentException("Player must have 3D video");
	r3d.setVisualComponent(c3d);
    }
    
    /**
    * Starts a new video conference. A webcam must be installed, and supported by JMF.
    * In addition the JMF performance pack must be installed. For those reasons, it is currently only supported
    * on Windows and Linux platforms.
    * @param conferenceID	A String common to each conference members.
    **/
    public static void startVideoConference(String conferenceID) {
	    MediaLocator vid = (System.getProperty("os.name").startsWith("W"))?new MediaLocator("vfw:0"):new MediaLocator("v4l:0");
	    
	    new Algea3D(new MediaLocator("alg3d:"+conferenceID), vid, new MediaLocator("javasound:44100"));
    }
}
