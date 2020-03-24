/*
 * Util.java
 *
 * Created on June 21, 2005, 4:48 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package org.jdesktop.lg3d.apps.orgchart.framework;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author cc144453
 */
public final class Util {
    
    static final String IMAGE_FORMAT = "png";
    
    private static Logger logger = Logger.getLogger(Util.class.getName());
    
    public static final BufferedImage imageFromResource(ServiceContext context, String key)
    throws IOException {
        // may want to cache in the future
        InputStream in = context.getResourceAsStream(key);
        if (in != null) {
            context.getLogger().fine("Loading image from " + key);
            return ImageIO.read(in);
        } else {
            throw new IOException("Unable to find resource=" + key);
        }
    }
    
    public static final byte[] imageToBytes(BufferedImage image) {
        try {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            ImageIO.write(image, IMAGE_FORMAT, bout);
            return bout.toByteArray();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Exception converting image to bytes", ex);
            throw new RuntimeException("Exception converting image to bytes", ex);
        }
    }
    
    public static final BufferedImage imageFromBytes(byte[] bytes) {
        try {
            return ImageIO.read(new ByteArrayInputStream(bytes));
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Exception reading image from bytes", ex);
            throw new RuntimeException("Exception eading image from bytes", ex);
        }
    }
    
    public static final BufferedImage imageFromURL(URL url) {
        try {
            return ImageIO.read(url);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Exception reading image from URL=" + url, ex);
            throw new RuntimeException("Exception reading image from URL=" + url, ex);
        }
    }
    
    public static final void copyResource(ServiceContext context, String rsrc, File outputFile)
    throws IOException {
        byte[] buffer = new byte[1024];
        FileOutputStream out = new FileOutputStream(outputFile);
        InputStream in = context.getResourceAsStream(rsrc);
        int len;
        while ((len = in.read(buffer, 0, buffer.length)) > 0) {
            out.write(buffer, 0, len);
        }
        out.close();
    }
    
    
}
