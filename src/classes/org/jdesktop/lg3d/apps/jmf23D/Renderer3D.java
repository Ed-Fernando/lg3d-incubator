/*
 *  @(#)Renderer3D.java    1.2 01/03/13
 *
 *  Copyright (c) 1999-2001 Sun Microsystems, Inc. All Rights Reserved.
 *
 *  Sun grants you ("Licensee") a non-exclusive, royalty free, license to use,
 *  modify and redistribute this software in source and binary code form,
 *  provided that i) this copyright notice and license appear on all copies of
 *  the software; and ii) Licensee does not utilize the software in a manner
 *  which is disparaging to Sun.
 *
 *  This software is provided "AS IS," without a warranty of any kind. ALL
 *  EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING ANY
 *  IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR
 *  NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE
 *  LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING
 *  OR DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS
 *  LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT,
 *  INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER
 *  CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF
 *  OR INABILITY TO USE SOFTWARE, EVEN IF SUN HAS BEEN ADVISED OF THE
 *  POSSIBILITY OF SUCH DAMAGES.
 *
 *  This software is not designed or intended for use in on-line control of
 *  aircraft, air traffic, aircraft navigation or aircraft communications; or in
 *  the design, construction, operation or maintenance of any nuclear
 *  facility. Licensee represents and warrants that it will not use or
 *  redistribute the Software for such purposes.
 */
package org.jdesktop.lg3d.apps.jmf23D;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;

import java.util.Enumeration;
import java.util.Vector;

import javax.media.*;
import javax.media.Format;
import javax.media.format.RGBFormat;
import javax.media.format.VideoFormat;
import javax.media.jai.*;
import javax.media.renderer.VideoRenderer;
import javax.media.util.*;


// java3d
import javax.vecmath.*;

import org.jdesktop.lg3d.scenemanager.utils.event.BackgroundChangeRequestEvent;
import org.jdesktop.lg3d.sg.*;
import org.jdesktop.lg3d.utils.c3danimation.*;
import org.jdesktop.lg3d.utils.eventadapter.*;
import org.jdesktop.lg3d.utils.shape.*;
import org.jdesktop.lg3d.wg.*;
import org.jdesktop.lg3d.wg.event.*;


/**
 *  This is a JMF renderer plugin. It texure maps the incoming
 *  video frame to a 3D surface in the lg3d scenegraph. 
 *
 **/
public final class Renderer3D implements VideoRenderer {

    /**
     *  Variables and Constants
     */

    // The descriptive name of this renderer
    public final static int SS = 512;

    /**
     *  Description of the Field
     */
    private final static String name = "Project Looking Glass 3D Video Renderer";
    protected static RGBFormat supportedRGB = new RGBFormat(null, 
                                                            Format.NOT_SPECIFIED, 
                                                            int[].class, 
                                                            Format.NOT_SPECIFIED, 
                                                            32, 0xff << 16, 
                                                            0xff << 8, 0xff, 1, 
                                                            Format.NOT_SPECIFIED, 
                                                            Format.NOT_SPECIFIED, 
                                                            Format.NOT_SPECIFIED);
    protected RGBFormat inputFormat;
    protected Buffer lastBuffer = null;
    protected int inWidth = 0;
    protected int inHeight = 0;
    protected Rectangle reqBounds = null;
    protected Rectangle bounds = new Rectangle();
    protected Object lastData = null;
    protected BufferedImage bimg;
    protected BufferedImage scaledImage = new BufferedImage(SS, SS, 
                                                            BufferedImage.TYPE_3BYTE_BGR);
    protected Control[] controls;
    ImageComponent2D imgcmp;
    BranchGroup scene = null;
    Appearance app = null;
    Texture2D tex = null;
    int count = 0;
    boolean firstFrame;
    boolean yUp;
    int btype = 0;
    int itype = 0;
    int ttype = 0;
    boolean byRef = true;
    protected TransformGroup objTrans;
    protected Component3D visualComponent;
    AffineTransform af;

    public Renderer3D() {
        System.out.println("new Renderer3D");

        // Prepare supported input formats and preferred format
        firstFrame = true;

        String os = System.getProperty("os.name");
        System.out.println("running on " + os);
        itype = ImageComponent.FORMAT_RGB;
        ttype = Texture.RGB;
        byRef = true;
        inWidth = 512;
        inHeight = 512;
    }

    public Object[] getControls() {

        if (controls == null)
            controls = controls = new Control[] { new Renderer3DControl(this) };

        return (Object[])controls;
    }

    public Object getControl(String controlType) {

        try {

            Class cls = Class.forName(controlType);
            Object[] cs = getControls();

            for (int i = 0; i < cs.length; i++) {

                if (cls.isInstance(cs[i])) {

                    return cs[i];
                }
            }

            return null;
        } catch (Exception e) {

            // no such controlType or such control
            return null;
        }
    }

    public String getName() {

        return name;
    }

    public void open()
              throws ResourceUnavailableException {
        firstFrame = true;
        createAppearance();
    }

    public void createAppearance() {

        if (app == null) {
            app = new Appearance();
            imgcmp = new ImageComponent2D(itype, scaledImage, byRef, true);
            tex = new Texture2D(Texture2D.BASE_LEVEL, ttype, SS, SS);
            tex.setImage(0, imgcmp);
            app.setCapability(Appearance.ALLOW_TRANSPARENCY_ATTRIBUTES_READ);
            app.setCapability(Appearance.ALLOW_TEXTURE_WRITE);
            tex.setCapability(Texture.ALLOW_IMAGE_WRITE);
            app.setTexture(tex);

            TextureAttributes texAttr = new TextureAttributes();
            texAttr.setTextureMode(TextureAttributes.MODULATE);
            app.setTextureAttributes(texAttr);
        }
    }

    public float getAspectRatio() {

        return inHeight / (float)inWidth;
    }

    public void reset() {

        // Nothing to do
    }

    public synchronized void close() {
    }

    public void start() {
    }

    public void stop() {
    }

    public Format[] getSupportedInputFormats() {

        return getSupportedInputFormatsStatic();
    }

    public static Format[] getSupportedInputFormatsStatic() {

        return new VideoFormat[] { supportedRGB };
    }

    public Format setInputFormat(Format format) {
        System.out.println("in setInputFormat = " + format);

        if (format != null && format instanceof RGBFormat && 
            format.matches(supportedRGB)) {
            inputFormat = (RGBFormat)format;

            Dimension size = inputFormat.getSize();
            yUp = (inputFormat.getFlipped() == Format.TRUE);
            inWidth = size.width;
            inHeight = size.height;
            btype = BufferedImage.TYPE_INT_RGB;
            bimg = new BufferedImage(inWidth, inHeight, btype);
            imgcmp = new ImageComponent2D(itype, bimg, byRef, true);

            float xScale = (float)SS / inWidth;
            float yScale = (float)SS / inHeight;
            // yScale = (yUp) ? yScale : -yScale;
            af = AffineTransform.getScaleInstance(xScale, yScale);

            // if (!yUp) af.translate(0.0d, -inHeight);

            return format;
        } else {

            return null;
        }
    }

    public int process(Buffer buffer) {

        if (buffer.getLength() <= 0) {

            return BUFFER_PROCESSED_OK;
        }

        if (count < 0) {
            count++;

            return BUFFER_PROCESSED_OK;
        }

        try {

            int[] rawData = (int[])(buffer.getData());
            int[] intData = ((DataBufferInt)bimg.getRaster().getDataBuffer()).getData();
            System.arraycopy(rawData, 0, intData, 0, rawData.length);

            Graphics2D g = scaledImage.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, 
                               RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawRenderedImage(bimg, af);
            tex.setImage(0, imgcmp);
            app.setTexture(tex);

            return BUFFER_PROCESSED_OK;
        } catch (Exception ex) {

            return BUFFER_PROCESSED_FAILED;
        }
    }

    //---------------------
    // Java3D related methods

    /**
     *  Description of the Method
     *
     *@return    Description of the Return Value
     */
    public Appearance getAppearance() {

        if (app == null)
            createAppearance();

        return app;
    }

    public java.awt.Component getComponent() {

        return null;
    }

    /**
     *  Requests the renderer to draw into a specified AWT component. Returns false
     *  if the renderer cannot draw into the specified component.
     *
     *@param  comp  The new component value
     *@return       Description of the Return Value
     */
    public boolean setComponent(java.awt.Component comp) {

        return false;
    }

    /**
     *  Sets the region in the component where the video is to be rendered to.
     *  Video is to be scaled if necessary. If <code>rect</code> is null, then the
     *  video occupies the entire component.
     *
     *@param  rect  The new bounds value
     */
    public void setBounds(java.awt.Rectangle rect) {
        reqBounds = rect;
    }

    /**
     *  Returns the region in the component where the video will be rendered to.
     *  Returns null if the entire component is being used.
     *
     *@return    The bounds value
     */
    public java.awt.Rectangle getBounds() {

        return reqBounds;
    }

    /**
     *  Local methods
     *
     *@return    The inWidth value
     */
    int getInWidth() {

        return inWidth;
    }

    int getInHeight() {

        return inHeight;
    }
}
