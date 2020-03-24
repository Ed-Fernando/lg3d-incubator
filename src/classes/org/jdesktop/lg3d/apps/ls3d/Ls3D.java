/*
 *  Copyright (c) 2005 ENDO Yasuyuki
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * $Id: Ls3D.java,v 1.5 2006-05-04 22:31:52 hideya Exp $
 */

package org.jdesktop.lg3d.apps.ls3d;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.sg.ImageComponent2D;
import org.jdesktop.lg3d.sg.Texture;
import org.jdesktop.lg3d.sg.Texture2D;
import org.jdesktop.lg3d.sg.TextureAttributes;
import org.jdesktop.lg3d.sg.Transform3D;
import org.jdesktop.lg3d.sg.TransformGroup;
import org.jdesktop.lg3d.sg.utils.image.TextureLoader;
import org.jdesktop.lg3d.utils.action.ActionNoArg;
import org.jdesktop.lg3d.utils.action.ScaleActionBoolean;
import org.jdesktop.lg3d.utils.animation.TranslationAnimationBoolean;
import org.jdesktop.lg3d.utils.c3danimation.NaturalMotionAnimation;
import org.jdesktop.lg3d.utils.eventaction.Component3DMover;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MouseEnteredEventAdapter;
import org.jdesktop.lg3d.utils.shape.FuzzyEdgePanel;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.utils.shape.Sphere;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.wg.Cursor3D;
import org.jdesktop.lg3d.wg.Frame3D;
import org.jdesktop.lg3d.wg.Toolkit3D;
import org.jdesktop.lg3d.wg.event.LgEventSource;

public class Ls3D {
    private static final int MAX_FILES = 100;
    private static final float X_POSITION_FACTOR = -0.8f;
    private static final float Y_POSITION_FACTOR = 0.8f;
    private static final float DELTA_Y = 0.0065f;
    private static final float DELTA_Z = 0.3f;
    private static final int WIDTH = 500;
    private static final int HEIGHT = 25;
    private static final int THUMBNAIL_SIZE = 64;
    private static final float UNIT_TRANS_FACTOR = 0.0254f / 72.0f;
    private static final float ADDITIONAL_SCALING_FACTOR = 0.6f;
    private static final float DEPTH = 0.005f;
    private static final int SCALE_DURATION = 200;
    private static final int ZOOM_DURATION = 2000;
    private static Logger logger;

    private Frame3D frame3d = new Frame3D();
    private Container3D top = new Container3D();
    private Component3D handleComp = new Component3D();
    private final FilePanel[] filePanels = new FilePanel[MAX_FILES];
    private float z = -0.05f;
    private File currentDir = null;
    private TranslationAnimationBoolean rootChanger;
    private Vector3f rootLoc;
    private Vector3f newLoc;
    private float topX;
    private float topY;
    private Properties icons = new Properties();
    private String[][] ICON_FILENAMES = {
            {"folder",      "resources/images/icon/Folder.png"},
            {"file",        "resources/images/icon/File.png"},
            {"compress",    "resources/images/icon/CompressFiles.png"},
            {"executable",  "resources/images/icon/executable.png"},
    };

    private Texture createThumbnailTexture(BufferedImage image) {
        ImageComponent2D comp =
            new ImageComponent2D(ImageComponent2D.FORMAT_RGBA, image, true, false);
        comp.set(image);
        Texture2D tex =
            new Texture2D(Texture.BASE_LEVEL, Texture.RGBA,
                        THUMBNAIL_SIZE, THUMBNAIL_SIZE);
        tex.setImage(0, comp);
        tex.setCapability(Texture2D.ALLOW_IMAGE_READ);
        tex.setMinFilter(Texture2D.BASE_LEVEL_LINEAR);
        tex.setMagFilter(Texture2D.BASE_LEVEL_LINEAR);
        return tex;
    }
    

    private void initIcons() {
        for (int i=0; i<ICON_FILENAMES.length; i++) {
            String filename = ICON_FILENAMES[i][1];
            URL imageUrl = getClass().getClassLoader().getResource(filename);
            if (imageUrl == null) {
                logger.warning("icon file not found:"+filename);
            }
            Texture texture = null;
            try {
                BufferedImage image = ImageIO.read(imageUrl);
                texture = createThumbnailTexture(image);
                if (texture == null) {
                    logger.warning("icon file load failed:"+filename);
                }
            } catch (IOException e) {
                logger.log(Level.WARNING, "icon file load failed:", e);
            }
            icons.put(ICON_FILENAMES[i][0], texture);
        }
    }
    
    private File getCurrentDir() {
        return currentDir;
    }

    private void setCurrentDir(File dir) {
        currentDir = dir;
    }

    public static void main(String[] args) {
        Properties props = System.getProperties();
        String dir = props.getProperty("user.dir");
        if (args.length > 0) dir = args[0];
        new Ls3D(dir);
    }

    class FilePanel extends Component3D {
        private TransformGroup iconTrans = new TransformGroup();
        private TransformGroup fileTrans = new TransformGroup();
        private TranslationAnimationBoolean changer;
        private Vector3f origLoc;
        private Vector3f newLoc;
        private File file = null;
        private SimpleAppearance iconAppearance = null;
        private Appearance appearance = null;
        private boolean active = false;
        private BufferedImage image;
        private Graphics2D g;
        private TextureLoader loader;
        private Texture texture;
        private FuzzyEdgePanel iconPanel;
        private FuzzyEdgePanel panel;
        private float width;
        private float height;
        private DataHandler dataHandler;
        private String fileName;

        private File getFile() {
            return file;
        }

        private void setFile(File aFile) {
            file = aFile;
        }
        
        public float getWidth() {
            return width;
        }
        
        public float getHeight() {
            return height;
        }

        public FilePanel(File aFile) {
            this.setAnimation(new NaturalMotionAnimation(SCALE_DURATION));
            this.setMouseEventPropagatable(true);
            file = aFile;
            width = WIDTH * UNIT_TRANS_FACTOR * ADDITIONAL_SCALING_FACTOR;
            height = HEIGHT * UNIT_TRANS_FACTOR * ADDITIONAL_SCALING_FACTOR;
            TextureAttributes texattr = new TextureAttributes();
            texattr.setTextureMode(TextureAttributes.REPLACE);

            Vector3f iconPosition = new Vector3f(-(height+width/2.0f), 0.0f, 0.0f);
            Transform3D iconT3d = new Transform3D();
            iconT3d.set(iconPosition);
            iconTrans.setTransform(iconT3d);

            iconAppearance = new SimpleAppearance(0.0f, 0.0f, 0.0f, 0.0f,
                    SimpleAppearance.ENABLE_TEXTURE
                    | SimpleAppearance.DISABLE_CULLING);
            iconAppearance.setTextureAttributes(texattr);
            iconPanel = new FuzzyEdgePanel(height, height, iconAppearance);
            iconTrans.addChild(iconPanel);
            
            appearance = new SimpleAppearance(0.0f, 0.0f, 0.0f, 0.0f,
                    SimpleAppearance.ENABLE_TEXTURE
                            | SimpleAppearance.DISABLE_CULLING);
            appearance.setTextureAttributes(texattr);
            
            panel = new FuzzyEdgePanel(width, height, appearance);
            fileTrans.addChild(panel);
            
            this.setCursor(Cursor3D.DEFAULT_CURSOR);
            this.addListener(new MouseEnteredEventAdapter(
                    new ScaleActionBoolean(this, 1.1f, 500)));
            new Component3DMover(this);

            origLoc = new Vector3f();
            top.getTranslation(origLoc);
            newLoc = new Vector3f(origLoc.x, origLoc.y, origLoc.z + DELTA_Z);
            this.addListener(new MouseClickedEventAdapter(
                new ActionNoArg() {
                    public void performAction(LgEventSource source) {
                        if (!active) return;
                        if (file == null) return;
                        if (!file.isDirectory()) return;

                        Vector3f frameLoc = new Vector3f();
                        frame3d.getTranslation(frameLoc);
                        //logger.info("frameLoc.x="+frameLoc.x+", frameLoc.y="+frameLoc.y+", frameLoc.z="+frameLoc.z);
                        frame3d.setTranslation(0.0f, 0.0f, frameLoc.z);//TEST
                        
                        origLoc = new Vector3f();
                        top.getFinalTranslation(origLoc);
                        newLoc = new Vector3f(origLoc.x, origLoc.y,
                                          origLoc.z + DELTA_Z);
                        top.changeTranslation(newLoc, ZOOM_DURATION);

                        // declease-z
                        z -= DELTA_Z;

                        handleComp.setTranslation(topX, topY, z);
                        setCurrentDir(file);
                        list(file);
                    }
                }));

            this.addChild(iconTrans);
            this.addChild(fileTrans);
            
            setPanelFile(file);
        }
        
        private boolean isImageFile(String mimeType) {
            return mimeType.startsWith("image/");
        }

        private String getSuffix(File file) {
            String suffix = "";
            int index = file.getName().lastIndexOf('.');
            if (index > 0 && index < file.getName().length() - 1) {
                suffix = file.getName().substring(index + 1).toLowerCase();
            }   
            return suffix;
        }
        
        private Texture readTumbnail(File file) throws IOException {
            Texture texture = null;
            Iterator<ImageReader> readers = ImageIO.getImageReadersBySuffix(getSuffix(file));
            if (readers.hasNext()) {
                ImageReader reader = readers.next();
                InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(file.getAbsolutePath());
                if (inputStream == null) {
                    inputStream = new FileInputStream(file);
                }
                ImageInputStream imageInputStream = ImageIO.createImageInputStream(inputStream);
                reader.setInput(imageInputStream);
                int w = reader.getWidth(0);
                int h = reader.getHeight(0);
                BufferedImage image = null;
                if (reader.hasThumbnails(0)) {
                    image = reader.readThumbnail(0, 0);
                } else {
                    int ratio = 1;
                    int xs = 0; // offset x
                    int ys = 0; // offset y
                    if (w > h) {
                        ratio = w / THUMBNAIL_SIZE;
                    } else {
                        ratio = h / THUMBNAIL_SIZE;
                    }
                    ImageReadParam param = reader.getDefaultReadParam();
                    param.setSourceSubsampling(ratio, ratio, 0, 0);
                    BufferedImage thumbnailImage = reader.read(0, param);
                    if (w > h) {
                        ys = (THUMBNAIL_SIZE - thumbnailImage.getHeight()) / 2;
                    } else {
                        xs = (THUMBNAIL_SIZE - thumbnailImage.getWidth()) / 2;
                    }
                    //logger.info("file="+file+", width="+thumbnailImage.getWidth()+", height="+thumbnailImage.getHeight()+", xs="+xs+", ys="+ys);
                    image = new BufferedImage(THUMBNAIL_SIZE, THUMBNAIL_SIZE,
                                            BufferedImage.TYPE_INT_ARGB);
                    Graphics g = image.getGraphics();
                    g.drawImage(thumbnailImage, xs, ys,
                            thumbnailImage.getWidth(), thumbnailImage.getHeight(), null);
                }
                texture = createThumbnailTexture(image);
            } 
            
            return texture;
        }
        
        private void loadIcon(SimpleAppearance app, File file) {
            Texture iconTexture = null;
            if (file.isDirectory()) {
                iconTexture = (Texture)icons.get("folder");
            } else {
                dataHandler =
                    new DataHandler(new FileDataSource(file));
                if (isImageFile(dataHandler.getContentType())) {
                    try {
                        iconTexture = readTumbnail(file);
                    } catch (IOException e) {
                        logger.log(Level.WARNING, "image load failed:", e);
                    }
                }
                
                if (iconTexture == null) {
                    iconTexture = (Texture)icons.get("file");
                }
            }
            app.setTexture(iconTexture);
        }

        public void setPanelFile(File aFile) {
            file = aFile;
            Color color = Color.BLACK;
            int style = Font.BOLD;

            if (file == null) {
                appearance.setTexture(null);
                ((Appearance)iconAppearance).setTexture(null);
                active = false;
            } else {
                fileName = file.getName();
                if (file.isDirectory()) {
                    active = true;
                    color = Color.BLUE;
                    style = Font.BOLD | Font.ITALIC;
                } else {
                    active = false;
                }

                loadIcon(iconAppearance, file);
                image =
                    new BufferedImage(WIDTH, HEIGHT,
                      BufferedImage.TYPE_INT_ARGB);
                g = (Graphics2D) image.getGraphics();
                g.setColor(new Color(1.0f, 1.0f, 1.0f, 0.25f));
                g.fillRect(0, 0, WIDTH, HEIGHT);
                // the following setting causes visual noises around the text
                // when the it is rendered on a background with translucensy.
//                g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
//                        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                g.setColor(color);
                g.setFont(new Font("Serif", style, 28));
                g.drawString(fileName, 0, 20);
                g.dispose();

                loader = new TextureLoader(image);
                texture = loader.getTexture();
                appearance.setTexture(texture);
            }
        }
    }

    private void list(File dir) {
        if (dir == null) return;
        if (!dir.isDirectory()) return;
        
        final File[] files = dir.listFiles();

        Runnable listThread = new Runnable() {
            public void run() {
                float x = 0.0f;
                float y = topY - DELTA_Y;
                int n = 0;
                for (int i = 0; i < files.length; i++) {
                    if (!files[i].isHidden() && files[i].isDirectory()) {
                        filePanels[n].setScale(1.0f);
                        filePanels[n].setPanelFile(files[i]);
                        x = topX + (filePanels[n].getWidth() / 2.0f);
                        filePanels[n].setTranslation(x, y, z);
                        filePanels[n].setVisible(true);
                        y -= DELTA_Y;
                        n++;
                        if (n >= MAX_FILES) return;
                    }
                }
                for (int i = 0; i < files.length; i++) {
                    if (!files[i].isHidden() && files[i].isFile()) {
                        filePanels[n].setScale(1.0f);
                        filePanels[n].setPanelFile(files[i]);
                        x = topX + (filePanels[n].getWidth() / 2.0f);
                        filePanels[n].setTranslation(x, y, z);
                        filePanels[n].setVisible(true);
                        y -= DELTA_Y;
                        n++;
                        if (n >= MAX_FILES) return;
                    }
                }
                for (; n < MAX_FILES; n++) {
                    filePanels[n].setScale(1.0f);
                    filePanels[n].setPanelFile(null);
                    x = topX + (filePanels[n].getWidth() / 2.0f);
                    filePanels[n].setTranslation(x, y, z);
                    filePanels[n].setVisible(false);
                    y -= DELTA_Y;
                }
            }
        };
        new Thread(listThread).start();
    }

    public Ls3D(String arg) {
        logger = Logger.getLogger(this.getClass().getName()); 
        
        initIcons();

        topX = Toolkit3D.getToolkit3D().getScreenWidth() / 2.0f * X_POSITION_FACTOR;
        topY = Toolkit3D.getToolkit3D().getScreenHeight() / 2.0f * Y_POSITION_FACTOR;
        
        top.setAnimation(new NaturalMotionAnimation(ZOOM_DURATION));
        SimpleAppearance handleApp =
            new SimpleAppearance(1.0f, 0.0f, 0.0f, 0.5f);
        Sphere handle = new Sphere(0.003f, Sphere.GENERATE_NORMALS
                | Sphere.GEOMETRY_NOT_SHARED, handleApp);
        handleComp.setAnimation(new NaturalMotionAnimation(SCALE_DURATION));
        handleComp.addChild(handle);
        handleComp.setTranslation(topX, topY, z);

        new Component3DMover(handleComp);
        handleComp.addListener(new MouseEnteredEventAdapter(
                new ScaleActionBoolean(handleComp, 1.5f, 200)));
        rootLoc = new Vector3f();
        rootLoc = top.getTranslation(rootLoc);
        newLoc = new Vector3f(rootLoc.x, rootLoc.y, rootLoc.z - DELTA_Z);
        handleComp.setMouseEventPropagatable(true);
        handleComp.addListener(new MouseClickedEventAdapter(
            new ActionNoArg() {
                public void performAction(LgEventSource source) {
                    if (getCurrentDir() != null) {
                        File parent = getCurrentDir().getParentFile();
                        if (parent != null) {
                            rootLoc = new Vector3f();
                            top.getTranslation(rootLoc);
                            newLoc = new Vector3f(rootLoc.x, rootLoc.y,
                                              rootLoc.z - DELTA_Z);
                            top.changeTranslation(newLoc, ZOOM_DURATION);

                            // increase-z
                            z += DELTA_Z;

                            handleComp.setTranslation(topX, topY, z);
                            setCurrentDir(parent);
                            list(parent);
                        }
                    }
                }
            }));

        top.addChild(handleComp);

        for (int i = 0; i < MAX_FILES; i++) {
            filePanels[i] = new FilePanel(null);
            top.addChild(filePanels[i]);
        }
        setCurrentDir(new File(arg));
        list(getCurrentDir());

        frame3d.addChild(top);

        frame3d.setPreferredSize(new Vector3f(0.06f, 0.10f, 0.08f));
        frame3d.changeEnabled(true);
        frame3d.changeVisible(true);
    }
}
