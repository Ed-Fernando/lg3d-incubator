/*
 * LG3D Incubator Project - LgScope
 *
 * $RCSfile: LgScope.java,v $
 *
 * Copyright (c) 2006, INoX Software Development Group, All Rights Reserved
 *
 * Redistributions in source code form must reproduce the above
 * copyright and this condition.
 *
 * The contents of this file are subject to the GNU General Public
 * License, Version 2 (the "License"); you may not use this file
 * except in compliance with the License. A copy of the License is
 * available at http://www.opensource.org/licenses/gpl-license.php.
 *
 * $Revision: 1.4 $
 * $Date: 2006-10-24 10:11:26 $
 * Author: E_INOUE
 */

package org.jdesktop.lg3d.apps.lgscope;


import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.Enumeration;
import java.util.Vector;

import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.apps.lgscope.util.LgUtil;
import org.jdesktop.lg3d.utils.action.ActionBooleanInt;
import org.jdesktop.lg3d.utils.action.ActionFloat3;
import org.jdesktop.lg3d.utils.eventadapter.KeyPressedEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.wg.*;
import org.jdesktop.lg3d.wg.event.LgEventListener;
import org.jdesktop.lg3d.wg.event.LgEventSource;


/**
 * LgScope main class.
 */
public class LgScope extends Frame3D {
            
    private Container3D fileContainer;
    private FileActionListener selectedListener;
           
    private Component3D[] fileComponentList;
    private File[] fileList;
    
    
    // Displayed directory. 
    public File dir;

    private LgToolbar lgToolbar;    
    private Layout layout;    
    private Sorter sorter;
    
    /**
     * value: Component3D
     */
    private Vector selectedList = new Vector();
    
    private FileCreator[] fileCreators;
    private int fileCreatorIndex = 0;        
    
    private LgEventListener selectedActionListener;
    private LgEventListener executeActionListener;
    
        
    /**
     * The displayed directory is constructed specifying it. 
     * 
     * @param dir displayed directory.
     * @param tr Initial display position. It decides it automatically for null. 
     *            
     */
    public LgScope(File dir, Vector3f tr) {
        
        fileContainer = new Container3D();
        addChild(fileContainer);
        
        createFileCreators();
        
        selectedListener = new DefaultSelectedListener(this);
        
        // setTranslation(tr);      
        
        // Sorter is generated. 
        Sorter[] sorters = {
                new DefaultSorter("Filename:Ascend", true), 
                new DefaultSorter("Filename:Descend", false)};
        
        // Layout is generated. 
        Layout[] layouts = {
                new DefaultLayout(), 
                new RandomLayout(),
                new BookshelfLayout(),
                new SkewBookshelfLayout()};
        
        lgToolbar = new LgToolbar(this, tr, sorters, layouts);
        addChild(lgToolbar);       
        
        // thumbnail is added. 
        createThumbnail(lgToolbar); 

        selectedActionListener = new MouseClickedEventAdapter(
            new Boolean(false),  // It reacts by the single click.  
            new SelectedAction());
        
        // ExecuteAction is registered. 
        executeActionListener = new MouseClickedEventAdapter( 
                new Boolean(true),  // It reacts by double-clicking. 
                new ExecuteAction());
                    
        addListener(new KeyPressedEventAdapter(new KeyListener()));
        
        scope(dir);      

        changeEnabled(true);
        changeVisible(true);   
    }
    
    
    private void createFileCreators() {
        
        Vector v = new Vector();
        
        // Standard display. 
        v.add(new DefaultFileCreator());
        
        // It half transparently displays it. 
        DefaultFileCreator d1 = new DefaultFileCreator();
        d1.bgColor = new Color(255, 255, 255, 100);
        v.add(d1);
        
        // Red of zeal      
        DefaultFileCreator d2 = new DefaultFileCreator();
        d2.bgColor = new Color(255, 50, 50, 100);
        d2.filenameColor = Color.WHITE;
        d2.dirnameColor = Color.GREEN;
        v.add(d2);
        
        // Cube style      
        DefaultFileCreator d3 = new DefaultFileCreator();
        d3.imageWidth = 128;
        d3.imageHeight = 128;
        v.add(d3);
        
        // "java.net" style
        DefaultFileCreator d4 = new DefaultFileCreator();
        d4.bgColor = new Color(89, 79, 181, 240);
        d4.filenameColor = Color.WHITE;
        d4.dirnameColor = Color.WHITE;
        v.add(d4);
        
        // Transparency
        DefaultFileCreator d5 = new DefaultFileCreator();
        d5.bgColor = new Color(255, 255, 255, 10);
        v.add(d5);
        
        // Blue style
        DefaultFileCreator d6 = new DefaultFileCreator();
        d6.bgColor = new Color(186, 214, 239, 80);
        d6.filenameColor = new Color(255, 255, 255, 200);
        v.add(d6);
        
        // Green style
        DefaultFileCreator d7 = new DefaultFileCreator();
        d7.bgColor = new Color(3, 148, 151, 80);
        d7.filenameColor = new Color(255, 255, 255, 200);
        d7.dirnameColor = new Color(255, 255, 255, 150);
        v.add(d7);
        
        // "slashdot" style
        DefaultFileCreator d8 = new DefaultFileCreator();
        d8.bgColor = new Color(0, 102, 102);
        d8.filenameColor = new Color(255, 255, 255);
        d8.dirnameColor = new Color(255, 255, 255);
        v.add(d8);
        
        // Windows style
        DefaultFileCreator d9 = new DefaultFileCreator();
        d9.bgColor = new Color(192, 192, 192);
        d9.filenameColor = Color.BLACK;
        d9.dirnameColor = Color.BLACK;
        v.add(d9);
        
        fileCreators = (FileCreator[]) v.toArray(new FileCreator[v.size()]);
    }

    
    private void createThumbnail(LgToolbar lgToolbar) {
        
        Thumbnail thumbnail = new Thumbnail();
        
//        Component3D c = lgToolbar.makeMover(0.008f);
        Component3D c = lgToolbar.makeMover(0.006f, true);
        
        thumbnail.addChild(c);
        thumbnail.setPreferredSize(c.getPreferredSize(new Vector3f()));
        
        setThumbnail(thumbnail);        
    }


    public void setSorter(Sorter sorter) {
        this.sorter = sorter;
    }
    
    public void setLayout(Layout layout) {
        this.layout = layout;
    }
    
    
    /**
     * FileCreator is changed.
     *  
     * @param direction Changed direction. (1 or -1)
     */
    public void changeFileCreator(int direction) {
        
        if (fileCreators.length == 1) {
            return;
        }
        
        fileCreatorIndex += direction;        
        if (fileCreatorIndex == fileCreators.length) {
            fileCreatorIndex = 0;
        }
        if (fileCreatorIndex < 0) {
            fileCreatorIndex = fileCreators.length - 1;
        }
        
        scope(dir);
    }
    

    /**
     * It sorts it. 
     * 
     */
    public void sort() {
        
        if (fileList == null) {
            return;
        }
        
        // Sorter does not exist. 
        if (sorter == null) {
            // It lays it out. 
            layout();
            return;
        }
        
        // The list of present is preserved. 
        File[] tmp = new File[fileList.length]; 
        System.arraycopy(fileList, 0, tmp, 0, fileList.length);
        
        File[] newList = sorter.sort(tmp);
        Component3D[] cmpList = new Component3D[newList.length]; 
        
        // The component is permuted. 
        for (int i = 0; i < newList.length; i++) {
            
            for (int j = 0; j < newList.length; j++) {
                
                if (newList[i].equals(fileList[j])) {
                    cmpList[i] = fileComponentList[j];
                    break;
                }
            }
        }
        
        fileList = newList;
        fileComponentList = cmpList;       
        
        // It lays it out. 
        layout();
    }
    
    
    /**
     * The file being displayed now is removed. 
     * 
     * @param next Next, directory which should be displayed. 
     */
    private void removeFiles(File next) {
        
        // Because the selected event is delayed, only the clicked file makes it not delete. 
        Component3D now = null;
        for (int i = 0; i < fileList.length; i++) {
            if (fileList[i] == next) {
                now = fileComponentList[i]; 
            }
        }
        
        // Temporary measures. The opened chance is given. 
        for (Enumeration e = fileContainer.getAllChildren(); e.hasMoreElements(); ) {
            
            Component3D c = (Component3D) e.nextElement();
            c.setVisible(false);
            
            // The listener is released. 
            c.removeListener(executeActionListener);
            c.removeListener(selectedActionListener);
            
            // Removal from container.
            fileContainer.removeChild(c);
            // Deletion from selection list.
            if (selectedList.contains(c) && c != now) {
                selectedList.remove(c);
            }             
        }
        
        fileList = null;
    }
    
    
//    
//    class RemoveFileThread extends Thread {
//        
//        File nextDir;
//        
//        RemoveFileThread(File nextDir) {
//            this.nextDir = nextDir;
//        }
//        
//        public void run() {
//            
//            
//            Component3D now = null;
//            for (int i = 0; i < fileList.length; i++) {
//                if (fileList[i] == nextDir) {
//                    now = fileComponentList[i]; 
//                }
//            }
//            
//            
//            for (Enumeration e = fileContainer.getAllChildren(); e.hasMoreElements(); ) {
//                
//                Component3D c = (Component3D) e.nextElement();
//                //c.setVisible(false);
//                
//                
//                c.removeListener((LgEventListener) executeActionTable.get(c));
//                executeActionTable.remove(c);
//                c.removeListener((LgEventListener) selectedActionTable.get(c));
//                selectedActionTable.remove(c);
//                
//                
//                fileContainer.removeChild(c);
//                
//                if (selectedList.contains(c) && c != now) {
//                    selectedList.remove(c);
//                }  
//                
//                try{
//                    sleep(10);
//                }catch (Exception ex) {
//                    // TODO: handle exception
//                }
//                
//                c.setVisible(false);
//                           
//            }        
//        }
//    }
    
    

    /**
     * The file list of the specified directory is displayed. 
     * 
     * @param dir Displayed directory. 
     */
    public void scope(File dir) {
        
        this.dir = dir;
        
        // setVisible(false);
                
        if (fileList != null && fileList.length > 0) {
            removeFiles(dir);       
            // new RemoveFileThread(dir).start();         
        }
                
        fileList = dir.listFiles();
                                
        // Component3D corresponding to File is generated. 
        fileComponentList = new Component3D[fileList.length];
        
        for (int i = 0; i < fileList.length; i++) {
            
            fileComponentList[i] = fileCreators[fileCreatorIndex].create(fileList[i]);
            
            // ExecuteAction is registered. 
            fileComponentList[i].addListener(executeActionListener);
                
            // SelectedAction is registered. 
            fileComponentList[i].addListener(selectedActionListener);
            
            fileContainer.addChild(fileComponentList[i]);
        }

        // Sorting is executed.         
        sort();
        
        // setVisible(true);
        
//        Component3D c = new Component3D();
//        Box shape=new Box(0.01f, 0.01f, 0.01f, null);
//        c.addChild(shape);                
//        /*fileContainer.*/addChild(c);
    }
    
    
    /**
     * The file list is arranged. 
     * 
     */
    public void layout() {  
        
        if (layout != null && lgToolbar != null) {           
            layout.layout(
                fileComponentList, 
                Toolkit3D.getToolkit3D().getScreenWidth(), 
                Toolkit3D.getToolkit3D().getScreenHeight(), 
                lgToolbar.getFileAlignment(), lgToolbar.getFileAbsAlignment()); 
        }    
    }
    
    
    /**
     * The entire list is moved. 
     * 
     * 
     * @param dx X amount of movement. 
     * @param dy Y amount of movement. 
     * @param dz Z amount of movement. 
     */
    public void move(float dx, float dy, float dz) {        
        moveComponent(fileContainer, dx, dy, dz);
    }
    
    
    /**
     * The entire component is moved. 
     * 
     * @param c component.
     * @param dx X amount of movement.
     * @param dy Y amount of movement. 
     * @param dz Z amount of movement. 
     */
    public void moveComponent(Component3D c, float dx, float dy, float dz) {
        
        Vector3f v = LgUtil.getTranslation(c); 
        v.x += dx;
        v.y += dy;
        v.z += dz;                   
        c.setTranslation(v.x, v.y, v.z);
    }
    
    
    /**
     * The entire list is rotated. 
     * 
     * 
     * @param rx X RotationAxis.
     * @param ry Y RotationAxis.
     * @param rz Z RotationAxis.
     * @param r RotationAngle.
     */
    public void rotate(float rx, float ry, float rz, float r) {
        
        for (int i=0; i < fileList.length; i++) {
            
            Vector3f v = LgUtil.getRotationAxis(fileComponentList[i]); 
            v.x += rx;
            v.y += ry;
            v.z += rz;             
            fileComponentList[i].setRotationAxis(v.x, v.y, v.z);
            fileComponentList[i].setRotationAngle(fileComponentList[i].getRotationAngle() + r);
        }    
        
        /*
        Vector3f v = LgUtil.getRotationAxis(fileContainer); 
        fileContainer.setRotationAxis(v.x + rx, v.y + ry, v.z + rz);
        fileContainer.setRotationAngle(fileContainer.getRotationAngle() + r);
        */
    }


    public void rotateComponent(Component3D c, float rx, float ry, float rz, float r) {
        
        Vector3f v = LgUtil.getRotationAxis(c);     
        // System.out.println(v);
        v.x += rx;
        v.y += ry;
        v.z += rz;             
        c.setRotationAxis(v.x, v.y, v.z);
        c.setRotationAngle(c.getRotationAngle() + r);
    }
    
    
    /**
     * ExecuteEvent is issued. 
     */
    void fireExecuteEvent(
    Component3D component, File file,
    LgEventSource source, float x, float y, float z) {
        
        try {
            selectedListener.execute(component, file, source, x, y, z);
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
    }
    

    class ExecuteAction implements ActionFloat3 {
        
        public void performAction(LgEventSource source, float x, float y, float z) {    
    
            try {                
                for (int i = 0; i < fileComponentList.length; i++) {
                    
                    if (fileComponentList[i] == source) {
                        fireExecuteEvent((Component3D) source, fileList[i], source, x, y, z);
                        break;
                    }
                }
            }
            catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    
    class SelectedAction implements ActionFloat3 {
        
        public void performAction(LgEventSource source, float x, float y, float z) {
            
            boolean b;
            
            if (selectedList.contains(source)) {
                b = false;
                selectedList.remove(source);
            }
            else {
                b = true;
                selectedList.add(source);
            }
            
            fileCreators[fileCreatorIndex].changeSelected((Component3D) source, b);
        }
    }
    
    
    class KeyListener implements ActionBooleanInt {
        
        private int preKey = -1;    // Key immediately before  
        private final float dr = 0.1f; // Rotation corner 
        private float px, py;   // Rotation axis immediately before 
        
        public void performAction(LgEventSource source, boolean state, int value) {
                        
            switch (value) {
                case KeyEvent.VK_UP:
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_LEFT:
                    rotate(value);
                    break;
                
                case KeyEvent.VK_S:
                    if (!state) {   // !state==keyup
                        selectAll();
                    }
                    
                    break;
            }
        }

        // The selection of all files is switched.        
        void selectAll() {
            
            for (int i = 0; i < fileComponentList.length; i++) {
                
                boolean selected;
                // It is selecting it. 
                if (selectedList.contains(fileComponentList[i])) {
                    selected = false;
                    selectedList.remove(fileComponentList[i]);
                }
                else {
                    selected = true;
                    selectedList.add(fileComponentList[i]);
                }
                
                fileCreators[fileCreatorIndex].changeSelected(fileComponentList[i], selected);
            }            
        }
        
        void rotate(int value) {
            
            float rotate = 0;
            float x = 0, y = 0;
            
            switch (value) {
                case KeyEvent.VK_UP:
                    rotate = dr * -1;
                    x = 0.01f;
                    y = 0;                    
                    break;
                    
                case KeyEvent.VK_DOWN:
                    rotate = dr;
                    x = 0.01f;
                    y = 0;
                    break;
                    
                case KeyEvent.VK_RIGHT:
                    rotate = dr;
                    x = 0;
                    y = 0.01f;
                    break;
                    
                case KeyEvent.VK_LEFT:
                    rotate = dr * -1;
                    x = 0;
                    y = 0.01f;
                    break;
            }
            
            
            for (int i = 0; i < selectedList.size(); i++) {
                
                Component3D component = (Component3D) selectedList.get(i);
                
                // There was a change in the rotation axis. 
                if (px != x) {
                    component.changeRotationAngle(0, 200);  // It turns it to the front once. With animation.  
                }
                // Direction of the same rotation
                else {
                    float r = component.getRotationAngle();                                
                    component.setRotationAxis(x, y, 0);
                    component.setRotationAngle(r + rotate);
                    
                                                    
                }
            }
            
            preKey = value;
            px = x;
            py = y;    
        }
    }
    
    
    public static void main(String[] args) {
        
        String dir = new File("").getAbsolutePath();
        if (args.length == 1) {
            dir = args[0];
        }
        
        new LgScope(new File(dir).getAbsoluteFile(), null);
        //LgScope.test();
    }
    
    
//    private static void test() {
//        Frame3D frame = new Frame3D();
//        Appearance app = new SimpleAppearance(0.6f,0.6f,1.0f,1.0f);
//        Box box = new Box(0.01f,0.01f,0.01f,app);   
//        Component3D c = new Component3D();
//        c.addChild(box);
//        frame.addChild(c);
//        frame.setPreferredSize(new Vector3f(0.01f,0.01f,0.01f));
//        frame.changeEnabled(true);
//        frame.changeVisible(true);
//    }
}


