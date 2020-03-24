/*
 * LG3D Incubator Project - Zoetrope
 *
 * $RCSfile$
 *
 * Copyright (c) 2004, Zoetrope Project Team, All Rights Reserved
 *
 * Redistributions in source code form must reproduce the above
 * copyright and this condition.
 *
 * The contents of this file are subject to the GNU General Public
 * License, Version 2 (the "License"); you may not use this file
 * except in compliance with the License. A copy of the License is
 * available at http://www.opensource.org/licenses/gpl-license.php.
 *
 * $Revision$
 * $Date$
 * Author: yuichi sakuraba
 */

package org.jdesktop.lg3d.apps.zoetrope;

import java.lang.ref.WeakReference;
import static org.jdesktop.lg3d.apps.zoetrope.Zoetrope.UNIT_TRANS_FACTOR;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.jdesktop.lg3d.sg.ImageComponent2D;
import org.jdesktop.lg3d.sg.Texture;
import org.jdesktop.lg3d.sg.Texture2D;

public class ImageInfo {
    private static final int THUMBNAIL_SIZE = 64;
    private static final int IMAGE_WIDTH = 1024;
    private static final int IMAGE_HEIGHT = 1024;

    private Texture2D thumbnail;
    private WeakReference<Texture2D> fullImage;
    private ImageReader largeReader = null;
    private boolean abort = false;

    private float width;
    private float height;

    private float thumbnailWidth;
    private float thumbnailHeight;

    private String filename;

    public ImageInfo(String filename) throws IOException {
        this.filename = filename;

        Iterator readers = ImageIO.getImageReadersBySuffix(getExtension(filename));
        if (!readers.hasNext()) {
            throw new IOException(filename + " isn't Image file.");
        }

        ImageReader reader = (ImageReader)readers.next();

        InputStream inStream = this.getClass().getClassLoader().getResourceAsStream(filename);
        if (inStream == null) {
            inStream = new FileInputStream(filename);
        }
        ImageInputStream stream = ImageIO.createImageInputStream(inStream);
        reader.setInput(stream);
            
        int w = reader.getWidth(0);
        int h = reader.getHeight(0);

        BufferedImage thumbImage = null;

        if (reader.hasThumbnails(0)) {
            thumbImage = reader.readThumbnail(0, 0);
        } else {
            int sumpling;
            if (w > h) {
                sumpling = w / THUMBNAIL_SIZE;

                thumbnailWidth = THUMBNAIL_SIZE * UNIT_TRANS_FACTOR;
                thumbnailHeight = (float)(THUMBNAIL_SIZE * (double)h / (double)w)
                                      * UNIT_TRANS_FACTOR;
            } else {
                sumpling = h / THUMBNAIL_SIZE;

                thumbnailHeight = THUMBNAIL_SIZE * UNIT_TRANS_FACTOR;
                thumbnailWidth = (float)(THUMBNAIL_SIZE * (double)w / (double)h)
                                      * UNIT_TRANS_FACTOR;
            }
                
            ImageReadParam param = reader.getDefaultReadParam();
            param.setSourceSubsampling(sumpling, sumpling, 0, 0);
            
            thumbImage = reader.read(0, param);
        }

        width = w * UNIT_TRANS_FACTOR;
        height = h * UNIT_TRANS_FACTOR;

        thumbImage = normalize(thumbImage, THUMBNAIL_SIZE, THUMBNAIL_SIZE);
        thumbnail = createTexture(thumbImage);
    }


    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getThumbnailWidth() {
        return thumbnailWidth;
    }

    public float getThumbnailHeight() {
        return thumbnailHeight;
    }

    public Texture2D getThumbnail() {
        return thumbnail;
    }

    public Texture2D getImage() {
        if (fullImage==null || fullImage.get()==null) {
            Iterator readers = ImageIO.getImageReadersBySuffix(getExtension(filename));
            largeReader = (ImageReader)readers.next();
            abort = false;
            BufferedImage image0 = null;
            try {
                InputStream inStream = this.getClass().getClassLoader().getResourceAsStream(filename);
                if (inStream == null) {
                    inStream = new FileInputStream(filename);
                }

                ImageInputStream stream = ImageIO.createImageInputStream(inStream);
                largeReader.setInput(stream);
                image0 = largeReader.read(0);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            if (abort)
                return null;
                       
            image0 = normalize(image0, IMAGE_WIDTH, IMAGE_HEIGHT);

            fullImage = new WeakReference(createTexture(image0));
        }
        
        return fullImage.get();
    }
    
    /**
     * Abort the getImage(). getImage will return null
     */
    public void abortGetImage() {
        if (largeReader!=null) {
            abort = true;
            largeReader.abort();
        }
    }

    public String getName() {
        return filename;
    }

    private BufferedImage normalize(BufferedImage source, int w, int h){
        int srcW = source.getWidth();
        int srcH = source.getHeight();
        float xScale = (float)w/(float)srcW;
        float yScale = (float)h/(float)srcH;

        if (xScale == 1.0f && yScale == 1.0f)
            return source;
        else {
            int scaleW = (int)(source.getWidth() * xScale + 0.5);
            int scaleH = (int)(source.getHeight() * yScale + 0.5);

            BufferedImage destination = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
            Graphics g = destination.getGraphics();
            g.drawImage(source, 0, 0, w, h, null);
            g.dispose();

            return destination;
        }
    }

    private Texture2D createTexture(BufferedImage image) {
        ImageComponent2D imageComp = new ImageComponent2D(ImageComponent2D.FORMAT_RGBA, 
                                                          image, true, true);
        imageComp.set(image);

        Texture2D texture = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA,
                                            imageComp.getWidth(), imageComp.getHeight());
        
        texture.setImage(0, imageComp);
        texture.setCapability(Texture2D.ALLOW_IMAGE_READ);
        texture.setMinFilter(Texture2D.BASE_LEVEL_LINEAR);
        texture.setMagFilter(Texture2D.BASE_LEVEL_LINEAR);

        return texture;
    }

    private String getExtension(String filename) {
        String ext = "";

        int index = filename.lastIndexOf('.');
        if(index > 0 && index < filename.length() - 1) {
            ext = filename.substring(index + 1).toLowerCase();
        }

        return ext;
    }

    public String toString() {
        return "ImageInfo [" + filename + "]";
    }
}
