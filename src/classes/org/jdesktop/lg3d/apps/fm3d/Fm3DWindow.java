/*
 * Copyright (c) 2005, 2006 John Maltby
 *
 * Portions of code based upon:
 * Ls3D copyright (c) 2005 ENDO Yasuyuki
 * PingPong copyright (c) 2004, Johann Glaser
 * Folder and file images Ls3D copyright (c) 2005 ENDO Yasuyuki 
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
 */

package org.jdesktop.lg3d.apps.fm3d;

/**
 * Fm3DWindow.java
 *
 * @author John Maltby
 *
 * This project uses and requires the org.apache.commons.io.FileUtils library
 * commons-io-1.0.jar
 */

import java.awt.*;
import java.io.File;
import java.util.LinkedList;
import java.util.Vector;
import java.awt.event.*;
import org.jdesktop.lg3d.wg.*;
import org.jdesktop.lg3d.wg.event.*;
import org.jdesktop.lg3d.sg.*;
import org.jdesktop.lg3d.utils.action.*;
import org.jdesktop.lg3d.utils.shape.*;
import org.jdesktop.lg3d.utils.c3danimation.NaturalMotionAnimation;
import org.jdesktop.lg3d.utils.eventadapter.*;
import org.apache.commons.io.FileUtils;
import javax.vecmath.Vector3f;

/** View displaying window containing directories and files */
public class Fm3DWindow extends Frame3D implements WindowConstants {
    private Fm3DMain fm3dMain;
    private Frame3D desktop;
    private ControlBar controlBar;
    private Component3D window;
    private GlassyPanel windowPanel;
    private Appearance opaqueAppearance = new Appearance();
    private Appearance activeOpaqueAppearance = new Appearance(); 
    private SimpleAppearance transparentAppearance = new SimpleAppearance(0.5f,0.5f,0.9f,0.5f);
    private SimpleAppearance activeTransparentAppearance = new SimpleAppearance(0.0f,0.0f,0.9f,0.8f);    
    private File[] files;
    private IconAppearance[] currentPageIconAppearance = new IconAppearance[MAX_ICONS];
    private IconAppearance[] nextPageIconAppearance = new IconAppearance[MAX_ICONS];    
    private IconWithText[] currentPageFileIcon = new IconWithText[MAX_ICONS];
    private IconWithText[] nextPageFileIcon = new IconWithText[MAX_ICONS];    
    private MouseClickedEventAdapter[] singleClickAdapter = new MouseClickedEventAdapter[MAX_ICONS];
    private MouseDraggedEventAdapter[] leftDragAdapter = new MouseDraggedEventAdapter[MAX_ICONS];
    private MouseDraggedEventAdapter[] rightDragAdapter = new MouseDraggedEventAdapter[MAX_ICONS];       
    private MousePressedEventAdapter[] leftReleaseAdapter = new MousePressedEventAdapter[MAX_ICONS];
    private MousePressedEventAdapter[] rightReleaseAdapter = new MousePressedEventAdapter[MAX_ICONS];       
    private File thisDir;
    private int currentPageFirstIcon;
    private int nextPageFirstIcon;
    private int iconsInCurrentWindow;
    private int iconsInNextWindow;    
    private int numberIcons;
    private int page;
    private int numberPages;
    private float width;
    private float height;
    private int maxCols;
    private int maxRows;
    private float y_rotation = 0.0f;
    private float prev_x = 0.0f;
    private boolean billboardingOn;
    private boolean transparencyOn;
    private boolean pathOn;
    private boolean stickyOn;
    private Color textColour;
    private IconText pageText;
            
    boolean dragging = false;
    float iconOrigRot;
    float origRot;
    Fm3DWindow target;
    
    private final String[] iconFilename = {
        "resources/images/icon/folder.png",
        "resources/images/icon/file.png",
        "resources/images/icon/compress_files.png",
        "resources/images/icon/executable.png"
    };
    
    /** Generate the window size from the number of icons to be displayed.
     *  Try to maintain a 4:3 apsect ratio.
     */
    private void generateWindowSize() {        
        if(numberIcons > MAX_ICONS)
            iconsInCurrentWindow = MAX_ICONS;
        else
            iconsInCurrentWindow = numberIcons;
      
        maxCols = (int)Math.sqrt(4*iconsInCurrentWindow/3);	// try to maintain 4:3 ratio
        if(maxCols == 0)
            maxCols = 1;
        maxRows = iconsInCurrentWindow/maxCols + 1;            // round up
        width = maxCols*ICON_OFF_SIZE + (maxCols+1)*ICON_COL_GAP - ICON_COL_GAP/2;
        height = maxRows*ICON_OFF_SIZE + (maxRows+1)*ICON_ROW_GAP + ICON_ROW_GAP/2;            
    }
    
    /** build an icon for each directory or file in the File array */
    private void buildIcons(IconWithText[] fileIcon, IconAppearance[] iconAppearance, int start, int finish) {        
        int j = 0;
        for(int i = start; i < finish; i++) {
            // build vector of IconWithText objects
            fileIcon[j] = buildIcon(iconAppearance, i, j);
            j++;
        }
    }
   
    private void addIcons(IconWithText[] fileIcon, int start, int finish, float z_position) {
        // determine start position
        float x = -width/2 + ICON_COL_PERIOD/2 + 0.001f; // edge correction
        float y = height/2 - ICON_OFF_SIZE/2;
        int cols = 0;
        int rows = 0;
        Vector3f position;
                
        int j = 0;
        for(int i = start; i < finish; i++) { 
            // calculate position and display
            position = new Vector3f(x+cols*ICON_COL_PERIOD, y-rows*ICON_ROW_PERIOD, z_position);
            fileIcon[j].setPositionInWindow(position);
            // check for max cols
            if(++cols >= maxCols) {
                cols = 0;
                ++rows;
            }
            window.addChild(fileIcon[j++]);
        }
    }
    
    private void addPageInformation() {
        // add page number out of total to bottom centre of window
        String pageInfo = new String(page + ":" + numberPages);
        int iconPointsWidth = (int)(POINTS_PER_METRE * ICONTEXT_WIDTH);
        int iconPointsHeight = (int)(POINTS_PER_METRE * ICONTEXT_HEIGHT);
        pageText = new IconText(ICONTEXT_WIDTH, ICONTEXT_HEIGHT, iconPointsWidth,
           iconPointsHeight, new Font("SansSerif", Font.BOLD, 10), pageInfo, Color.BLACK);
        pageText.setPositionInWindow(new Vector3f(0.0f, 0.005f-height/2, 0.0f));
        pageText.setRotationAxis(0.0f, 1.0f, 0.0f);
        window.addChild(pageText);        
    }
    
    private void removeIcons() {        
        int j = 0;
        for(int i = currentPageFirstIcon; i < currentPageFirstIcon + iconsInCurrentWindow; i++) 
            window.removeChild(currentPageFileIcon[j++]);
    }    
    
    private void addControl(float rotation) {
        String dirName;
        if(pathOn)
            dirName = thisDir.getAbsolutePath();
        else
            dirName = thisDir.getName();            
        controlBar = new ControlBar(fm3dMain, this, height, dirName, textColour, transparencyOn);
        orientControl(rotation);
        window.addChild(controlBar);
    }
    
    /** Billboards all icons in window about the y-axis */
    private void billboardIcons(IconWithText[] fileIcon, int start, int finish, float rotation) {
        Vector3f iconPosition;
        Vector3f returnPosition;
        Vector3f modifiedPosition;
        
        int j = 0;
        for(int i = start; i < finish; i++) {
            iconPosition = fileIcon[j].getPositionInWindow();
            returnPosition = new Vector3f(-iconPosition.x, -iconPosition.y, -iconPosition.z);
            // translate back to origin
            fileIcon[j].changeTranslation(returnPosition);
            // compensate for rotation of parent
            fileIcon[j].setRotationAngle(-rotation);
            // translate back to original position
            modifiedPosition = new Vector3f(iconPosition.x, iconPosition.y, iconPosition.z);
            fileIcon[j++].changeTranslation(modifiedPosition);
        }
        
        // billboard page info
        iconPosition = pageText.getPositionInWindow();
        returnPosition = new Vector3f(-iconPosition.x, -iconPosition.y, -iconPosition.z);
        // translate back to origin
        pageText.changeTranslation(returnPosition);
        // compensate for rotation of parent
        pageText.setRotationAngle(-rotation);
        // translate back to original position
        modifiedPosition = new Vector3f(iconPosition.x, iconPosition.y, iconPosition.z);
        pageText.changeTranslation(modifiedPosition);        
    }
    
    private void addIconListeners() {   
        // add listeners to visible directories and files for single click
        int j = 0;
        for(int i = currentPageFirstIcon; i < currentPageFirstIcon + iconsInCurrentWindow; i++) {
            singleClickAdapter[j] = new MouseClickedEventAdapter(new DirectoryAction(fm3dMain, files[i]));  
            currentPageFileIcon[j].addListener(singleClickAdapter[j]);
            j++;
        }

        // add listeners to visible directories and files for left drag
        j = 0;
        for(int i = currentPageFirstIcon; i < currentPageFirstIcon + iconsInCurrentWindow; i++) {           
            leftDragAdapter[j] = new MouseDraggedEventAdapter(MouseEvent3D.ButtonId.BUTTON1,
                new LeftButtonDragAction(fm3dMain, this, i, j, files[i], currentPageIconAppearance)); 
            currentPageFileIcon[j].addListener(leftDragAdapter[j]);
            j++;
        }
        
        // add listeners to visible directories and files for right drag
        j = 0;
        for(int i = currentPageFirstIcon; i < currentPageFirstIcon + iconsInCurrentWindow; i++) {
            rightDragAdapter[j] = new MouseDraggedEventAdapter(MouseEvent3D.ButtonId.BUTTON3,
                new RightButtonDragAction(fm3dMain, this, currentPageFileIcon[j], files[i]));
            currentPageFileIcon[j].addListener(rightDragAdapter[j]);
            j++;
        }

        // add listeners to visible directories and files for left button drag release
        j = 0;
        for(int i = currentPageFirstIcon; i < currentPageFirstIcon + iconsInCurrentWindow; i++) {
            leftReleaseAdapter[j] = new MousePressedEventAdapter(MouseEvent3D.ButtonId.BUTTON1,
                new LeftButtonDragEnd(fm3dMain, this, files[i])); 
            currentPageFileIcon[j].addListener(leftReleaseAdapter[j]);
            j++;
        }        
        
        // add listeners to visible directories and files for right button drag release
        j = 0;
        for(int i = currentPageFirstIcon; i < currentPageFirstIcon + iconsInCurrentWindow; i++) {
            rightReleaseAdapter[j] = new MousePressedEventAdapter(MouseEvent3D.ButtonId.BUTTON3,
                new RightButtonDragEnd(fm3dMain, this, currentPageFileIcon[j], files[i]));
            currentPageFileIcon[j].addListener(rightReleaseAdapter[j]);
            j++;
        }
    }    
        
    private void removeIconListeners() {
        int j = 0;
        for(int i = currentPageFirstIcon; i < currentPageFirstIcon + iconsInCurrentWindow; i++) {
            currentPageFileIcon[j].removeListener(singleClickAdapter[j]);
            currentPageFileIcon[j].removeListener(leftDragAdapter[j]);
            currentPageFileIcon[j].removeListener(rightDragAdapter[j]);
            currentPageFileIcon[j].removeListener(leftReleaseAdapter[j]);
            currentPageFileIcon[j].removeListener(rightReleaseAdapter[j]);            
            j++;
        }
    }
    
    private void orientControl(float y_rotation) {
        float delta_x = -width/2.0f*(float)Math.sin(Math.toRadians(y_rotation));
        controlBar.setTranslation(delta_x, 0.0f, 0.0f);
        controlBar.setRotationAngle(-(float)Math.toRadians(y_rotation));
    }    
    
    public Fm3DWindow(Fm3DMain main, Frame3D desk, File dir, Color col, 
            boolean billboard, boolean transparent, boolean path, boolean sticky) {
        final Fm3DWindow thisWindow = this;
        desktop = desk;
        fm3dMain = main;
        thisDir = dir;
        textColour = col;
        billboardingOn = billboard;
        transparencyOn = transparent;
        pathOn = path;
        stickyOn = sticky;
        page = 1;
        currentPageFirstIcon = 0;
        
        setPreferredSize(new Vector3f(width, height, 0.01f));
        buildWindow(y_rotation);
        
        opaqueAppearance.setCapability(Appearance.ALLOW_COLORING_ATTRIBUTES_WRITE
                | Appearance.ALLOW_TRANSPARENCY_ATTRIBUTES_WRITE);
        ColoringAttributes colAttr = new ColoringAttributes(0.5f,0.5f,0.5f, ColoringAttributes.FASTEST);        
        colAttr.setCapability(ColoringAttributes.ALLOW_COLOR_WRITE);
        colAttr.setColor(0.5f,0.5f,0.5f);
        
        opaqueAppearance.setColoringAttributes(colAttr);
        opaqueAppearance.setTransparencyAttributes(new TransparencyAttributes(TransparencyAttributes.NONE, 0.0f));        
        
        activeOpaqueAppearance.setColoringAttributes(new ColoringAttributes(0.75f,0.75f,0.75f, ColoringAttributes.FASTEST));        
        activeOpaqueAppearance.setTransparencyAttributes(new TransparencyAttributes(TransparencyAttributes.NONE, 0.0f));
        
        // increase size of window by a factor of 1.05 when active
        addListener(new MouseEnteredEventAdapter(new ScaleActionBoolean(this, 1.05f)));
        // set active window
        addListener(new MouseEnteredEventAdapter(
                new ActionBoolean() {
            public void performAction(LgEventSource event, boolean state) {
                setPanelAppearance(true);
                fm3dMain.setActiveWindow(thisWindow);
            }
        }));
                
        // rotate window
        addListener(new MouseDraggedEventAdapter(MouseEvent3D.ButtonId.BUTTON3,
                new ActionFloat2() {
            public void performAction(LgEventSource source, float x, float y) {
                if(x > prev_x)
                    y_rotation += DELTA_ROT;
                else
                    y_rotation -= DELTA_ROT;
                window.setRotationAngle((float)Math.toRadians(y_rotation));
                if(billboardingOn)
                    billboardIcons(currentPageFileIcon, currentPageFirstIcon, 
                       currentPageFirstIcon + iconsInCurrentWindow, (float)Math.toRadians(y_rotation));
                else
                    billboardIcons(currentPageFileIcon, currentPageFirstIcon, 
                       currentPageFirstIcon + iconsInCurrentWindow, 0.0f);
                // oscilate and billboard control bar
                orientControl(y_rotation);                                
                prev_x = x;
                if(y_rotation > 360.0f)
                    y_rotation = 0.0f;
                if(y_rotation < 0.0f)
                    y_rotation = 360.0f;
            }
        }));
    }

    public IconWithText buildIcon(IconAppearance[] iconAppearance, int fileIndex, int windowIndex) {
        TextureAttributes texattr = new TextureAttributes();
        texattr.setTextureMode(TextureAttributes.REPLACE);
        String textureFilename;
        IconWithText fileIcon;
        
        if(files[fileIndex].isDirectory())
            textureFilename = iconFilename[0];
        else
            textureFilename = iconFilename[1];
            
        // loads file and sets appearance to texture
        IconAppearance iconApp = new IconAppearance(textureFilename, false, false);
        iconAppearance[windowIndex] = iconApp;
        iconApp.setTextureAttributes(texattr);
            
        // create icon with texture for appearance and string for text
        fileIcon = new IconWithText(ICON_OFF_SIZE, iconApp, ICON_ON_SIZE, iconApp, 
            files[fileIndex].getName(), textColour);
            
        fileIcon.setRotationAxis(0.0f, 1.0f, 0.0f);
        
        return fileIcon;
    }    
    
    public void buildWindow(float rotation) {
        Component3D oldWindow = window;
        files = thisDir.listFiles();
        numberIcons = files.length;
        numberPages = numberIcons/MAX_ICONS + 1;
        
        generateWindowSize();
        
        if(page == numberPages)
            iconsInCurrentWindow = numberIcons%MAX_ICONS;
        
        buildIcons(currentPageFileIcon, currentPageIconAppearance, 
            currentPageFirstIcon, currentPageFirstIcon + iconsInCurrentWindow);
        
        // create a new window
        window = new Component3D();
        window.setAnimation(new NaturalMotionAnimation(500));
        window.setRotationAxis(0.0f, 1.0f, 0.0f);
        window.setMouseEventPropagatable(true);        
        
        addIcons(currentPageFileIcon, currentPageFirstIcon, 
            currentPageFirstIcon + iconsInCurrentWindow, ICON_Z_COORD);
        addIconListeners();
        addPageInformation();
        
        if(windowPanel != null)
            window.removeChild(windowPanel);
        if(transparencyOn)
            windowPanel = new GlassyPanel(width, height, 0.002f, 0.001f, transparentAppearance);
        else
            windowPanel = new GlassyPanel(width, height, 0.002f, 0.001f, opaqueAppearance);        
        
        windowPanel.setCapability(Shape3D.ALLOW_APPEARANCE_WRITE);        
        window.addChild(windowPanel);
        setYRotation(rotation);
        addChild(window);
        
        if(oldWindow != null)
            removeChild(oldWindow);
        
        addControl(rotation);   
    }    
    
    public void resetPosition(final int duration) {
        if(duration < 0)
            throw new IllegalArgumentException("duration cannot be negative");
        if(y_rotation > 0.0f) {
            Runnable rotateThread = new Runnable() {
                public void run() {
                    while(y_rotation > 0.0f) {
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            return;
                        }
                        if(y_rotation < 180.0f) {
                            y_rotation -= 1.0f;
                            if(y_rotation < 0.0f)
                                y_rotation = 0.0f;
                        } else {
                            y_rotation += 1.0f;
                            if(y_rotation > 360.0f)
                                y_rotation = 0.0f;
                        }
                        window.setRotationAngle((float)Math.toRadians(y_rotation));
                        billboardIcons(currentPageFileIcon, currentPageFirstIcon, 
                            currentPageFirstIcon + iconsInCurrentWindow, (float)Math.toRadians(y_rotation));
                        controlBar.setRotationAngle(-(float)Math.toRadians(y_rotation));
                    }
                }
            };
            new Thread(rotateThread).start();
        }
        changeTranslation(0.0f, 0.0f, 0.0f, duration);
        controlBar.changeTranslation(0.0f, 0.0f, 0.0f, duration);
    }
    
    /** Toggles billboarding for active window */
    public void toggleBillboarding(boolean billboarding) {
       billboardingOn = billboarding;
       if(billboardingOn)
           billboardIcons(currentPageFileIcon, currentPageFirstIcon, 
                currentPageFirstIcon + iconsInCurrentWindow, (float)Math.toRadians(y_rotation)); 
       else
           billboardIcons(currentPageFileIcon, currentPageFirstIcon, 
                currentPageFirstIcon + iconsInCurrentWindow, 0.0f);
    }    

    /** Toggles transparency for active window */
    public void toggleTransparency(boolean transparency) {
       transparencyOn = transparency;
       setPanelAppearance(true);
    }      

    /** Toggles full path for active window */
    public void togglePath(boolean path) {
       pathOn = path;
       setPanelAppearance(true);
    }      

    /** Toggles sticky for active window */
    public void toggleSticky(boolean sticky) {
       stickyOn = sticky;
       setPanelAppearance(true);
    }     
    
    public void scrollOut() {
        Runnable zoomThread = new Runnable() {
            public void run() {
                // determine start and finish for current page of icons
                Vector3f currentStartPosition;
                Vector3f currentFinishPosition = new Vector3f();        
                currentStartPosition = currentPageFileIcon[0].getPositionInWindow(); // all icons have same z coordinate
                currentFinishPosition.x = currentStartPosition.x;
                currentFinishPosition.y = currentStartPosition.y;       
                currentFinishPosition.z = currentStartPosition.z - SCROLL_INC; 
                // determine start and finish for next page of icons                
                Vector3f nextStartPosition  = new Vector3f();
                Vector3f nextFinishPosition = currentPageFileIcon[0].getPositionInWindow();  
                nextStartPosition.x = currentStartPosition.x;
                nextStartPosition.y = currentStartPosition.y;
                nextStartPosition.z = currentStartPosition.z + SCROLL_INC;
                
                nextPageFirstIcon = currentPageFirstIcon + MAX_ICONS;
                if(numberIcons - nextPageFirstIcon < MAX_ICONS)
                    iconsInNextWindow = numberIcons - nextPageFirstIcon;
                else
                    iconsInNextWindow = MAX_ICONS; 
                // build next page icons
                buildIcons(nextPageFileIcon, nextPageIconAppearance, nextPageFirstIcon, 
                    nextPageFirstIcon + iconsInNextWindow);
                // add next page icons
                addIcons(nextPageFileIcon, nextPageFirstIcon, 
                    nextPageFirstIcon + iconsInNextWindow, nextStartPosition.z);
                if(billboardingOn)
                    billboardIcons(nextPageFileIcon, nextPageFirstIcon, 
                        nextPageFirstIcon + iconsInNextWindow, (float)Math.toRadians(y_rotation));
                
                // zoom pages
                while(currentStartPosition.z > currentFinishPosition.z) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        return;
                    }
                    int j = 0;
                    for(int i = currentPageFirstIcon; i < currentPageFirstIcon + iconsInCurrentWindow; i++) {
                        currentStartPosition = currentPageFileIcon[j].getPositionInWindow();
                        currentStartPosition.z -= ZOOM_INC;
                        currentPageFileIcon[j].setPositionInWindow(currentStartPosition);
                        j++;
                    }  
                    j = 0;
                    for(int i = nextPageFirstIcon; i < nextPageFirstIcon + iconsInNextWindow; i++) {
                        nextStartPosition = nextPageFileIcon[j].getPositionInWindow();
                        nextStartPosition.z -= ZOOM_INC;
                        nextPageFileIcon[j].setPositionInWindow(nextStartPosition);
                        j++;                        
                    }
                }
                if(currentStartPosition.z <= currentFinishPosition.z) {
                    // remove current page icon listeners
                    removeIconListeners();
                    // remove displaced current page icons
                    removeIcons();
                    // update page data
                    currentPageFirstIcon = nextPageFirstIcon;
                    iconsInCurrentWindow = iconsInNextWindow;
                    page++;          
                    String pageInfo = new String(page + ":" + numberPages);
                    pageText.drawText(pageInfo);
                    // copy arrays
                    for(int i = 0; i < iconsInNextWindow; i++) {
                        currentPageFileIcon[i] = nextPageFileIcon[i];
                        currentPageIconAppearance[i] = nextPageIconAppearance[i]; 
                    }
                    // add next page icon listeners
                    addIconListeners();         
                    // ensure correct z-coord clearance of icons from window
                    int j = 0;
                    for(int i = currentPageFirstIcon; i < currentPageFirstIcon + iconsInCurrentWindow; i++) {
                        currentStartPosition = currentPageFileIcon[j].getPositionInWindow();
                        currentStartPosition.z = ICON_Z_COORD;
                        currentPageFileIcon[j].setPositionInWindow(currentStartPosition);
                        j++;
                    }                    
                }
            }
        };
        if(numberIcons > MAX_ICONS && currentPageFirstIcon < numberIcons - MAX_ICONS) { 
            new Thread(zoomThread).start();
        }
    }
    
    public void scrollIn() {
        Runnable zoomThread = new Runnable() {
            public void run() {
                // determine start and finish for current page of icons
                Vector3f currentStartPosition;
                Vector3f currentFinishPosition = new Vector3f();        
                currentStartPosition = currentPageFileIcon[0].getPositionInWindow(); // all icons have same z coordinate
                currentFinishPosition.x = currentStartPosition.x;
                currentFinishPosition.y = currentStartPosition.y;       
                currentFinishPosition.z = currentStartPosition.z + SCROLL_INC; 
                // determine start and finish for next page of icons                
                Vector3f nextStartPosition  = new Vector3f();
                Vector3f nextFinishPosition = currentPageFileIcon[0].getPositionInWindow();  
                nextStartPosition.x = currentStartPosition.x;
                nextStartPosition.y = currentStartPosition.y;
                nextStartPosition.z = currentStartPosition.z - SCROLL_INC;
                
                nextPageFirstIcon = currentPageFirstIcon - MAX_ICONS;
                if(numberIcons - nextPageFirstIcon < MAX_ICONS)
                    iconsInNextWindow = numberIcons - nextPageFirstIcon;
                else
                    iconsInNextWindow = MAX_ICONS; 
                // build next page icons
                buildIcons(nextPageFileIcon, nextPageIconAppearance, nextPageFirstIcon, 
                    nextPageFirstIcon + iconsInNextWindow);
                // add next page icons
                addIcons(nextPageFileIcon, nextPageFirstIcon, 
                    nextPageFirstIcon + iconsInNextWindow, nextStartPosition.z);
                if(billboardingOn)
                    billboardIcons(nextPageFileIcon, nextPageFirstIcon, 
                        nextPageFirstIcon + iconsInNextWindow, (float)Math.toRadians(y_rotation));
                
                // zoom pages    
                while(currentStartPosition.z < currentFinishPosition.z) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        return;
                    }
                    int j = 0;
                    for(int i = currentPageFirstIcon; i < currentPageFirstIcon + iconsInCurrentWindow; i++) {
                        currentStartPosition = currentPageFileIcon[j].getPositionInWindow();
                        currentStartPosition.z += ZOOM_INC;
                        currentPageFileIcon[j].setPositionInWindow(currentStartPosition);
                        j++;
                    }  
                    j = 0;
                    for(int i = nextPageFirstIcon; i < nextPageFirstIcon + iconsInNextWindow; i++) {
                        nextStartPosition = nextPageFileIcon[j].getPositionInWindow();
                        nextStartPosition.z += ZOOM_INC;
                        nextPageFileIcon[j].setPositionInWindow(nextStartPosition);
                        j++;                        
                    }
                }
                if(currentStartPosition.z >= currentFinishPosition.z) {
                    // remove current page icon listeners
                    removeIconListeners();
                    // remove displaced current page icons
                    removeIcons();
                    // update page data
                    currentPageFirstIcon = nextPageFirstIcon;
                    iconsInCurrentWindow = iconsInNextWindow;
                    page--;          
                    String pageInfo = new String(page + ":" + numberPages);
                    pageText.drawText(pageInfo);
                    // copy arrays
                    for(int i = 0; i < iconsInNextWindow; i++) {
                        currentPageFileIcon[i] = nextPageFileIcon[i];
                        currentPageIconAppearance[i] = nextPageIconAppearance[i]; 
                    }
                    // add next page icon listeners
                    addIconListeners();         
                    // ensure correct z-coord clearance of icons from window
                    int j = 0;
                    for(int i = currentPageFirstIcon; i < currentPageFirstIcon + iconsInCurrentWindow; i++) {
                        currentStartPosition = currentPageFileIcon[j].getPositionInWindow();
                        currentStartPosition.z = ICON_Z_COORD;
                        currentPageFileIcon[j].setPositionInWindow(currentStartPosition);
                        j++;
                    }                    
                }
            }
        };
        if(numberIcons > MAX_ICONS && currentPageFirstIcon >= MAX_ICONS)           
            new Thread(zoomThread).start();
    }
     
    public void setYRotation(float y_rot) {
        y_rotation = y_rot;
        window.setRotationAngle((float)Math.toRadians(y_rotation));    
        if(billboardingOn)
            billboardIcons(currentPageFileIcon, currentPageFirstIcon, 
                currentPageFirstIcon + iconsInCurrentWindow, (float)Math.toRadians(y_rotation));
        else
            billboardIcons(currentPageFileIcon, currentPageFirstIcon, 
                currentPageFirstIcon + iconsInCurrentWindow, 0.0f);
    }    

    public void setPanelAppearance(boolean active) {
       if(active)
            windowPanel.setAppearance(transparencyOn ? activeTransparentAppearance : activeOpaqueAppearance);
       else
            windowPanel.setAppearance(transparencyOn ? transparentAppearance : opaqueAppearance);
    }
    
    public void setTextColour(Color color) {
        textColour = color;
        IconWithText icon; 
        int j = 0;
        for(int i = currentPageFirstIcon; i < currentPageFirstIcon + iconsInCurrentWindow; i++) {
            icon = currentPageFileIcon[j];
            icon.setTextColour(color);
            j++;
        }
        pageText.setTextColour(color);
    }
    
    public void togglePathText() {
        String dirName;
        if(pathOn)
            dirName = thisDir.getAbsolutePath();
        else
            dirName = thisDir.getName();
        IconText pathText = getControlBar().getIconIext();
        pathText.drawText(dirName);        
    }
    
    public File getThisDir() {
        return thisDir;
    }
    
    public GlassyPanel getWindowPanel() {
        return windowPanel;
    }
    
    public Component3D getWindow() {
        return window;
    }    
    
    public Frame3D getDesktop() {
        return desktop;
    }

    public ControlBar getControlBar() {
        return controlBar;
    }    
    
    public GlassyPanel getControlPanel() {
        return controlBar.getControlPanel();
    }
    
    public float getRotationY() {
        return y_rotation;
    }
    
    public float getWidth() {
        return width;
    }
    
    public float getHeight() {
        return height;
    }
    
    public float getYRotation() {
        return y_rotation;
    }
    
    public boolean getBillboarding() {
        return billboardingOn;
    }

    public boolean getPath() {
        return pathOn;
    }    
    
    public boolean getSticky() {
        return stickyOn;
    }
    
    public Color getTextColour() {
        return textColour;
    }
}

/** Action for moving down a directory */
class DirectoryAction implements ActionNoArg {
    Fm3DMain fm3dMain;
    File file;
    
    DirectoryAction(Fm3DMain main, File f) {
        fm3dMain = main;
        file = f;
    }
    
    public void performAction(LgEventSource source) {
        if(file.isDirectory())
            fm3dMain.changeDirDown(file);
    }
}

/** Copy file action by dragging an icon with the left button pressed */
class LeftButtonDragAction implements ActionFloat2, WindowConstants {
    Fm3DMain fm3dMain;
    Fm3DWindow frame;
    IconWithText copyIcon;
    IconAppearance[] iconAppearance;
    File file;
    int fileIndex;
    int windowIndex;
    
    LeftButtonDragAction(Fm3DMain main, Fm3DWindow frame, int fileIndex, int windowIndex, 
            File file, IconAppearance[] iconAppearance) {
        fm3dMain = main;
        this.frame = frame;
        this.file = file;
        this.fileIndex = fileIndex;
        this.windowIndex = windowIndex;
        this.iconAppearance = iconAppearance;
    }
    
    public void performAction(LgEventSource source, float cursor_x, float cursor_y) {
        if(!frame.dragging) {
            // create new icon to copy
            copyIcon = frame.buildIcon(iconAppearance, fileIndex, windowIndex);
            frame.getDesktop().addChild(copyIcon);
            frame.dragging = true;
        }
        copyIcon.setTranslation(ICON_ZOOM_FACTOR*cursor_x, ICON_ZOOM_FACTOR*cursor_y, 0.0f);
        // search for target window
        frame.target = fm3dMain.findTargetWindow(frame, cursor_x, cursor_y);
        if(frame.target != null & frame.target != frame) {   
            frame.target.origRot = frame.target.getYRotation();
            frame.target.setPanelAppearance(true);
        }
        else
           fm3dMain.resetWindowAppearances(frame);
    }
}

/** Move file action by dragging an icon with the right button pressed */
class RightButtonDragAction implements ActionFloat2, WindowConstants {
    Fm3DMain fm3dMain;
    Fm3DWindow frame; 
    IconWithText fileIcon; 
    File file;
       
    RightButtonDragAction(Fm3DMain main, Fm3DWindow frame, IconWithText icon, File file) {
        fm3dMain = main;
        this.frame = frame;
        fileIcon  = icon;
        this.file = file;
    }
    
    public void performAction(LgEventSource source, float cursor_x, float cursor_y) {
        if(!frame.dragging) {
            frame.origRot = frame.getYRotation();
            frame.getWindow().removeChild(fileIcon);
            frame.getDesktop().addChild(fileIcon);
            fileIcon.changeRotationAngle(0.0f); 
            frame.dragging = true;
        }
        fileIcon.setTranslation(ICON_ZOOM_FACTOR*cursor_x, ICON_ZOOM_FACTOR*cursor_y, 0.0f);
        // search for target window
        frame.target = fm3dMain.findTargetWindow(frame, cursor_x, cursor_y);
        if(frame.target != null & frame.target != frame) {
            frame.target.origRot = frame.target.getYRotation();
            frame.target.setPanelAppearance(true);
        }
        else
           fm3dMain.resetWindowAppearances(frame);
    }
}
    
/** Copy action for left button drag end */
class LeftButtonDragEnd implements ActionBoolean {
    Fm3DMain fm3dMain;
    Fm3DWindow frame;    
    File file;
    
    LeftButtonDragEnd(Fm3DMain main, Fm3DWindow frame, File file) {
        fm3dMain = main;
        this.frame = frame;
        this.file = file;
    }
    
    private void copyDirectoryAndContents(File dir, String destinationPath) {
        File[] dirContents = dir.listFiles();
        destinationPath += "/" + dir.getName();
        File destinationDir = new File(destinationPath);
        destinationDir.mkdir();
        for(int i = 0; i < dirContents.length; i++) {
            try {
                if(dirContents[i].isDirectory())
                    copyDirectoryAndContents(dirContents[i], destinationPath); // recursive copy
                else
                    FileUtils.copyFileToDirectory(dirContents[i], destinationDir);
            }
            catch(java.io.IOException e) {
                System.out.println("CANNOT COPY FILE");
            }  
        }
    }
    
    public void performAction(LgEventSource source, boolean state) {
        if(!state) {
            if(frame.dragging) {
                frame.getDesktop().removeAllChildren(); // remove copy from desktop
                if(frame.target != null & frame.target != frame) {
                    fm3dMain.setActiveWindow(frame.target);
                    try {
                        if(file.isDirectory()) {
                            String destinationPath = frame.target.getThisDir().getAbsolutePath();
                            copyDirectoryAndContents(file, destinationPath);
                        }
                        else    
                            FileUtils.copyFileToDirectory(file, frame.target.getThisDir());
                    }
                    catch(java.io.IOException e) {
                        System.out.println("CANNOT COPY FILE");
                    }                
                    frame.target.buildWindow(frame.target.origRot); // rebuild target after addition
                    // maintain window as active
                    frame.target.setPanelAppearance(true);
                }
                frame.dragging = false;
            }
        }
    }
}

/** Move action for right button drag end */
class RightButtonDragEnd implements ActionBoolean {
    Fm3DMain fm3dMain;
    Fm3DWindow frame;    
    IconWithText fileIcon;    
    File file;
    
    RightButtonDragEnd(Fm3DMain main, Fm3DWindow frame, IconWithText icon, File file) {
        fm3dMain = main;
        this.frame = frame;
        fileIcon = icon;        
        this.file = file;
    }
    
    private void copyDirectoryAndContents(File dir, String destinationPath) {
        File[] dirContents = dir.listFiles();
        destinationPath += "/" + dir.getName();
        File destinationDir = new File(destinationPath);
        destinationDir.mkdir();
        for(int i = 0; i < dirContents.length; i++) {
            try {
                if(dirContents[i].isDirectory())
                    copyDirectoryAndContents(dirContents[i], destinationPath); // recursive copy
                else
                    FileUtils.copyFileToDirectory(dirContents[i], destinationDir);
            }
            catch(java.io.IOException e) {
                System.out.println("CANNOT COPY FILE");
            }  
        }
    }    
    
    public void performAction(LgEventSource source, boolean state) {
        if(!state) {
            if(frame.dragging) {
                frame.getDesktop().removeChild(fileIcon);
                if(frame.target != null & frame.target != frame) {
                    fm3dMain.setActiveWindow(frame.target);
                    try {
                        if(file.isDirectory()) {
                            String destinationPath = frame.target.getThisDir().getAbsolutePath();
                            copyDirectoryAndContents(file, destinationPath);
                        }
                        else    
                            FileUtils.copyFileToDirectory(file, frame.target.getThisDir());
                    }
                    catch(java.io.IOException e) {
                        System.out.println("CANNOT COPY TO TARGET");
                    }                
                    try {
                        FileUtils.forceDelete(file);
                    }
                    catch(java.io.IOException e) {
                        frame.getWindow().addChild(fileIcon);
                        System.out.println("CANNOT DELETE SOURCE");
                    }                   
                    //frame.setYRotation(frame.origRot);
                    frame.buildWindow(frame.origRot);               // rebuild source after deletion
                    frame.target.buildWindow(frame.target.origRot); // rebuild target after addition
                    // maintain window as active
                    frame.target.setPanelAppearance(true);
                }
                else { // no target, redeposit icon in source or delete
                    Menu deleteMenu = new Menu(frame, fileIcon, file);
                    Vector3f iconLoc = new Vector3f();
                    deleteMenu.changeTranslation(fileIcon.getTranslation(iconLoc));
                    frame.getDesktop().addChild(deleteMenu);
                    // maintain window as active
                    frame.setPanelAppearance(true);                    
                }               
                frame.dragging = false;
            }
        }
    }
}

