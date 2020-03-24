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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.apps.zoetrope.event.SelectionEvent;
import org.jdesktop.lg3d.apps.zoetrope.event.SelectionListener;
import org.jdesktop.lg3d.sg.Texture2D;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Frame3D;
import org.jdesktop.lg3d.wg.Thumbnail;
import org.jdesktop.lg3d.wg.Toolkit3D;

public class Zoetrope {

    // Transform factor: meter -> pixel 0.0254 [inch/m] / 72.0f [pixel/inch]
    public static final float UNIT_TRANS_FACTOR = 0.0254f / 72.0f;

    private static final float EDGE = 0.0f;
    
    private static final float THUMBNAIL_SCALE = 0.13f;
    
    private static final String DEFAULT_IMAGE_LIST =
	    "org/jdesktop/lg3d/apps/zoetrope/resources/images/sample/images.txt";

    private float width;
    private float height;

    
    private Frame3D frame;
    private ThumbnailWheel selector;
    private ImageComponent viewer;
    private ImageComponent thumbnailViewer;
    private ImageLoaderThread imageLoaderThread = null;

    private Map<Integer, ImageInfo> imageInfos;

    public Zoetrope() {
        this(DEFAULT_IMAGE_LIST);
    }

    public Zoetrope(String imagefile) {
        frame = new Frame3D();

        height = Toolkit3D.getToolkit3D().getScreenHeight() * 0.8f;
        width = height;

        imageInfos = new HashMap<Integer, ImageInfo>();

        selector = new ThumbnailWheel(height/2.0f);
        
        selector.addSelectionListener(new SelectionListener() {
                public void itemSelected(SelectionEvent event) {
                    ImageInfo info = imageInfos.get(selector.getSelectedIndex());
//                    Texture2D img = info.getImage();
                    // TODO load the large image in a seperate, interruptable thread
                    Texture2D img = info.getThumbnail();
                    viewer.changeTexture(img, info.getName(), 
                                        info.getWidth(), info.getHeight());
                    thumbnailViewer.changeTexture(img, info.getName(),
                                        info.getWidth(), info.getHeight());

                    if (imageLoaderThread!=null)
                        imageLoaderThread.interrupt();
                    
                    imageLoaderThread = new ImageLoaderThread(info);
                    
                }
            });
        
        viewer = new ImageComponent(width, height, frame);

	Vector3f v = new Vector3f();
	v = selector.getPreferredSize(v);
        selector.changeTranslation(- width / 2.0f - v.x, 0.0f, 0.0f);

        frame.addChild(selector);
        frame.addChild(viewer.getComponent());

	Vector3f viewerSize = new Vector3f();
	viewerSize = viewer.getComponent().getPreferredSize(viewerSize);
	Vector3f selectorSize = new Vector3f();
	selectorSize = selector.getPreferredSize(selectorSize);
	
	Thumbnail thumbnail = new Thumbnail();
        Component3D scaling = new Component3D();
        thumbnailViewer = new ImageComponent(width, height, frame);
	scaling.addChild(thumbnailViewer.getComponent());
        scaling.setScale(THUMBNAIL_SCALE);
        thumbnail.addChild(scaling);
        thumbnail.setPreferredSize(new Vector3f(viewerSize.x * THUMBNAIL_SCALE, 
                                                viewerSize.y * THUMBNAIL_SCALE, 
                                                0.1f * THUMBNAIL_SCALE));
	frame.setThumbnail(thumbnail);

        frame.setPreferredSize(new Vector3f(
				   viewerSize.x + selectorSize.x,
				   viewerSize.y,
				   0.1f));
	
	frame.changeEnabled(true);
        frame.changeVisible(true);        

        readImagefile(imagefile);
    }

    private void readImagefile(String imagefile) {
        try {
            InputStream stream = this.getClass().getClassLoader().getResourceAsStream(imagefile);
            if (stream == null) {
                stream = new FileInputStream(imagefile);
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            String filename;

            while ((filename = reader.readLine()) != null) {
                int index = filename.lastIndexOf('.');
                if(index > 0 && index < filename.length() - 1) {
                    String ext = filename.substring(index + 1).toLowerCase();
                    if (ext.equals("jpg") || ext.equals("gif") || ext.equals("png")) {
                        ImageInfo info = new ImageInfo(filename);
                        imageInfos.put(selector.getImageCount(), info);

                        selector.addChild(info.getThumbnail(), info.getWidth(), info.getHeight());
                    }
                }
            }
            
            ImageInfo info = imageInfos.get(selector.getSelectedIndex());
            viewer.changeTexture(info.getImage(), info.getName(),
				 info.getWidth(), info.getHeight());
            thumbnailViewer.changeTexture(info.getImage(), info.getName(),
					  info.getWidth(), info.getHeight());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if (args.length == 0 || args[0].length()<1) {
            new Zoetrope();
        } else {
            new Zoetrope(args[0]);
        }
    }
    
    /**
     * Thread to Load large images. The load can be interrupted if a new
     * image is needed
     */
    class ImageLoaderThread extends Thread {
        private ImageInfo info;
        private boolean done = false;
        
        public ImageLoaderThread(ImageInfo info) {
            this.info = info;
            start();
        }
        
        public void interrupt() {
            super.interrupt();
            if (!done) {
                info.abortGetImage();
            }
        }
        
        public void run() {
            try {
                sleep(20);
                Texture2D img=null;
                if (!interrupted())
                    img = info.getImage();

                if (!interrupted())
                    viewer.changeTexture(img, info.getName(), 
                                         info.getWidth(), info.getHeight());
                
            } catch(Exception e) {
            }
            done = true;
       }
    }
}
