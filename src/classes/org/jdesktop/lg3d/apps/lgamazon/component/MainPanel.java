/*
 * LG3D Incubator Project - LgAmazon
 *
 * $RCSfile: MainPanel.java,v $
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
 * $Revision: 1.1 $
 * $Date: 2007-03-09 09:37:38 $
 * Author: E_INOUE
 */

package org.jdesktop.lg3d.apps.lgamazon.component;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.imageio.ImageIO;
import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.apps.lgamazon.LgAmazon;
import org.jdesktop.lg3d.apps.lgamazon.LgAmazonListener;
import org.jdesktop.lg3d.apps.lgamazon.stub.Items;
import org.jdesktop.lg3d.apps.lgamazon.util.ImageTexture;
import org.jdesktop.lg3d.apps.lgamazon.util.LgUtil;
import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.utils.action.ActionChar;
import org.jdesktop.lg3d.utils.action.ActionFloat3;
import org.jdesktop.lg3d.utils.c3danimation.NaturalMotionAnimation;
import org.jdesktop.lg3d.utils.eventadapter.KeyPressedEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.KeyTypedEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MouseWheelEventAdapter;
import org.jdesktop.lg3d.utils.shape.Box;
import org.jdesktop.lg3d.utils.shape.GlassyPanel;
import org.jdesktop.lg3d.utils.shape.ImagePanel;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Toolkit3D;
import org.jdesktop.lg3d.wg.event.LgEventSource;
import org.jdesktop.lg3d.wg.event.MouseEvent3D;


public class MainPanel extends Component3D 
implements LgAmazonListener {

    private LgAmazon lgAmazon;
    private Component3D logo;
    private Component3D moreButton;
    private Component3D prevButton;
    
    private Component3D exceptionPanel;
    private LabelComponent exceptionLabel;
    
    private SearchIndexComponent searchIndexComponent;
    
    
    private LgEdit keywordEditor;
    private LgEdit searchResult;
    
    private Thread searchingThread;
    
    private Toolkit3D toolkit = Toolkit3D.getToolkit3D(); 
    
    
    private String preSearchIndex = "";
    

    /**
     * 
     */
    private KeyTypedEventAdapter keyListener = new KeyTypedEventAdapter(new KeywordKeyListener());
    
    
    public MainPanel(LgAmazon lgAmazon) throws IOException {
            	
    	this.lgAmazon = lgAmazon;
        lgAmazon.listener = this;
        
        //addListener(new Component3DMover(this)); 
        
        addListener(keyListener);
        
        
        Component3D panel = createPanel();
        addChild(panel);
        Vector3f panelSize = LgUtil.getSize(panel);
        setPreferredSize(panelSize);
        Vector3f panelLeft = new Vector3f(-panelSize.x / 2.0f, panelSize.y / 2.0f, panelSize.z / 2.0f);
        
        logo = createLogo();
        addChild(logo);
        Vector3f logoSize = LgUtil.getSize(logo);
        Vector3f logoPos = new Vector3f(panelLeft.x + (logoSize.x / 2), panelLeft.y - (logoSize.y / 2), 0.001f); 
        logo.setTranslation(logoPos.x, logoPos.y, logoPos.z);
        
        
        createPageButton();        
        Vector3f prevButtonSize = LgUtil.getSize(prevButton);
        Vector3f prevButtonPos = 
            new Vector3f(panelLeft.x + (prevButtonSize.x / 2), panelLeft.y - logoSize.y - (prevButtonSize.y / 2), 0.001f);   
        prevButton.setTranslation(prevButtonPos.x, prevButtonPos.y, prevButtonPos.z);
        prevButton.setVisible(false);
                
        Vector3f moreButtonSize = LgUtil.getSize(moreButton);
        Vector3f moreButtonPos = 
            new Vector3f(panelLeft.x + panelSize.x - (moreButtonSize.x / 2), panelLeft.y - logoSize.y - (moreButtonSize.y / 2), 0.001f);
        moreButton.setTranslation(moreButtonPos.x, moreButtonPos.y, moreButtonPos.z);
        moreButton.setVisible(false);
        
        
        searchIndexComponent = new SearchIndexComponent();
        searchIndexComponent.addIndex(Box.BACK, LgAmazon.BOOKS, ImageIO.read(
                MainPanel.class.getResourceAsStream("/org/jdesktop/lg3d/apps/lgamazon/resources/book-icon.png")));
        
        searchIndexComponent.addIndex(Box.LEFT, LgAmazon.MUSIC, ImageIO.read(
                MainPanel.class.getResourceAsStream("/org/jdesktop/lg3d/apps/lgamazon/resources/cd-icon.png")));
        
        searchIndexComponent.addIndex(Box.RIGHT, LgAmazon.DVD, ImageIO.read(
                MainPanel.class.getResourceAsStream("/org/jdesktop/lg3d/apps/lgamazon/resources/dvd-icon.png")));        
        addChild(searchIndexComponent);
        Vector3f searchIndexSize = LgUtil.getSize(searchIndexComponent);
        Vector3f searchIndexPos = 
            new Vector3f(logoPos.x + (logoSize.x / 2) + (searchIndexSize.x / 2), 
                          panelLeft.y - (searchIndexSize.y / 2), 
                          0.001f);
        searchIndexComponent.setTranslation(searchIndexPos.x, searchIndexPos.y, searchIndexPos.z);
        
        
        Component3D keyword = createKeyword();
        addChild(keyword);
        Vector3f keywordSize = LgUtil.getSize(keyword);
        Vector3f keywordPos = new Vector3f(
                searchIndexPos.x + (searchIndexSize.x / 2) + (keywordSize.x / 2), 
                panelLeft.y - (keywordSize.y / 2), 0.001f);
        keyword.setTranslation(keywordPos.x, keywordPos.y, keywordPos.z);
        
        Component3D go = createGoButton();
        addChild(go);
        Vector3f goSize = LgUtil.getSize(go);
        Vector3f goPos = new Vector3f(keywordPos.x + (keywordSize.x / 2) + (goSize.x / 2), panelLeft.y - (goSize.y / 2), 0.001f);
        go.setTranslation(goPos.x, goPos.y, goPos.z);
        
        
        Component3D result = createSearchResult();
        addChild(result);
        Vector3f resultSize = LgUtil.getSize(result);
        Vector3f resultPos = 
            new Vector3f(prevButtonPos.x + (prevButtonSize.x / 2) + (resultSize.x / 2), 
                         keywordPos.y - (keywordSize.y / 2.0f) - (resultSize.y / 2.0f) - 0.002f, 
                         0.0005f);
        result.setTranslation(resultPos.x, resultPos.y, resultPos.z);
        
        
        exceptionPanel = createExceptionPanel();
        exceptionPanel.setVisible(false);
        addChild(exceptionPanel);
        exceptionPanel.setTranslation(
                0.0f, - (panelSize.y / 2.0f + LgUtil.getSize(exceptionPanel).y / 2.0f), 0.0f);
    }
    
    
    Component3D createSearchResult() {
        
        Appearance appearance = new SimpleAppearance(1.0f, 1.0f, 1.0f, 1.0f,
                SimpleAppearance.ENABLE_TEXTURE | SimpleAppearance.DISABLE_CULLING);
        searchResult = new LgEdit(appearance, 256, 16, null);
        searchResult.editable = false;
        searchResult.multiple = false;
        
        searchResult.topMargin = 0;
        searchResult.bottomMargin = 0;
        searchResult.leftMargin = 0;
        searchResult.rightMargin = 0;
        
        searchResult.setFont(new Font("Dialog", Font.BOLD, 12));
        
        searchResult.background = new Color(0, 0, 0, 0);   
        searchResult.draw();
        
        return searchResult;
    }
    
    
    Component3D createExceptionPanel() {
        
        Component3D c = new Component3D();        
        
        SimpleAppearance appearance = new SimpleAppearance(1.0f, 1.0f, 1.0f, 1.0f,
                SimpleAppearance.ENABLE_TEXTURE | SimpleAppearance.DISABLE_CULLING);
        exceptionLabel = new LabelComponent(appearance);
        exceptionLabel.setSize(toolkit.widthNativeToPhysical(256), toolkit.heightNativeToPhysical(256));
        exceptionLabel.topMargin = exceptionLabel.bottomMargin = 10;  
        exceptionLabel.leftMargin = exceptionLabel.rightMargin = 10;   
        exceptionLabel.ignoreNewline = false;
        
        LabelScroller scroller = new LabelScroller(exceptionLabel);        
        c.addListener(new KeyPressedEventAdapter(scroller));
        c.addListener(new MouseWheelEventAdapter(scroller));           
        
        c.addChild(exceptionLabel);
        
        Appearance app = new SimpleAppearance(1.0f, 0.0f, 0.0f, 0.7f, 
                                              SimpleAppearance.DISABLE_CULLING);
        Vector3f size = LgUtil.getSize(exceptionLabel);
        GlassyPanel panel = new GlassyPanel(size.x + 0.005f,  size.y + 0.005f, 0.001f, 0.005f, app);
                
        c.addChild(panel);
        c.setPreferredSize(size);
        
        
        c.addListener(
                new MouseClickedEventAdapter(MouseEvent3D.ButtonId.BUTTON1, 
                        new ActionFloat3() {
                            
                            public void performAction(
                            LgEventSource source, float x, float y, float z) {
                                exceptionPanel.setVisible(false);
                            }
                        }));
        
        return c;        
    }

    
    Component3D createKeyword() throws IOException {
        
        int width = 256;
        int height = 32;
        
        Appearance appearance = new SimpleAppearance(1.0f, 1.0f, 1.0f, 1.0f,
                SimpleAppearance.ENABLE_TEXTURE | SimpleAppearance.DISABLE_CULLING);
        keywordEditor = new LgEdit(appearance, width, height, null);
        
        keywordEditor.addListener(keyListener);
        
        keywordEditor.topMargin = 7;
        keywordEditor.leftMargin = 7;
        keywordEditor.rightMargin = 7;
        
        keywordEditor.background = new Color(0, 0, 0, 0);        
        keywordEditor.draw();
        
        
        Vector3f size = new Vector3f(LgUtil.pixel2meter(width), LgUtil.pixel2meter(height), 0.0f);         
        
        
//        ImagePanel panel = new ImagePanel(
//                getClass().getResource("/inox/lg3d/amazon/resources/keyword.png"), size.x, size.y);
        
        ImagePanel panel = createImagePanel(size.x, size.y, "/org/jdesktop/lg3d/apps/lgamazon/resources/keyword.png");
        
        keywordEditor.addChild(panel);
        
        return keywordEditor;
    }
    
    
    /**
     * 
     *
     */
    class KeywordKeyListener implements ActionChar {
        
        public void performAction(LgEventSource source, char value) {
            
            if (value == '\n') {
                
                if (source == prevButton || source == moreButton) {
                    turnPage(source);
                }
                else {                
                    search();
                }
            }
        }
    }
    
    
    Component3D createPanel() {
        
        Vector3f size = new Vector3f(0.16f, LgUtil.pixel2meter(64), 0.005f);
        
        Appearance app = new SimpleAppearance(0.6f, 1.0f, 0.6f, 1.0f, 
                                              SimpleAppearance.DISABLE_CULLING);
        GlassyPanel panel = new GlassyPanel(size.x,  size.y, size.z, 0.001f, app);
        
        Component3D c = new Component3D();        
        c.addChild(panel);
        c.setPreferredSize(size);
        
        return c;        
    }
    
    
    /**
     * 
     * @return
     */
    private ImagePanel createImagePanel(float width, float height, String file) 
    throws IOException {

    	ImagePanel panel = new ImagePanel(
    			getClass().getResource(file), width, height);

//        ImagePanel panel = new ImagePanel(width, height);
//        SimpleAppearance appearance = new SimpleAppearance(1.0f, 1.0f, 1.0f, 1.0f,
//                SimpleAppearance.ENABLE_TEXTURE | SimpleAppearance.DISABLE_CULLING);
//        
//        BufferedImage image = ImageIO.read(MainPanel.class.getResourceAsStream(file));
//
//        appearance.setTexture(new ImageTexture(image));
//        panel.setAppearance(appearance);
        
        return panel;
    }
    
    
    Component3D createLogo() throws IOException {
        
        Vector3f size = new Vector3f(LgUtil.pixel2meter(128), LgUtil.pixel2meter(32), 0.0f);         
        
//        ImagePanel panel = new ImagePanel(
//                getClass().getResource("/org/jdesktop/lg3d/apps/lgamazon/resources/amazon.png"), size.x, size.y);
        
        ImagePanel panel = createImagePanel(size.x, size.y, "/org/jdesktop/lg3d/apps/lgamazon/resources/amazon.png");
                
        Component3D c = new Component3D();
        c.addChild(panel);
        c.setPreferredSize(size);
        
        c.setAnimation(new NaturalMotionAnimation(1000));
        
        c.addListener(
            new MouseClickedEventAdapter(MouseEvent3D.ButtonId.BUTTON1, new InterruptAction()));
        
        return c;
    }
    
    
    Component3D createGoButton() throws IOException {
        
        MouseClickedEventAdapter e = new MouseClickedEventAdapter(MouseEvent3D.ButtonId.BUTTON1, new GoAction()); 
        
        Vector3f size = new Vector3f(LgUtil.pixel2meter(32), LgUtil.pixel2meter(32), 0.0f);
        
//        ImagePanel img1 = new ImagePanel(
//                getClass().getResource("/org/jdesktop/lg3d/apps/lgamazon/resources/go.png"), size.x, size.y); 
        
        ImagePanel img1 = createImagePanel(size.x, size.y, "/org/jdesktop/lg3d/apps/lgamazon/resources/go.png");
        
        Component3D c = new Component3D(); 
        c.addChild(img1);
        c.setPreferredSize(size);
        c.addListener(e);
        
        return c;
    }

    
    class GoAction implements ActionFloat3 {
        
        public void performAction(
        LgEventSource source, float x, float y, float z) {
            search();            
        }
    }

    
    /**
     * 
     *
     */
    private void search() {
        
        if (keywordEditor.getText().trim().length() == 0) {
            return;
        }
        
        if (lgAmazon.isSearching()) {
            return;
        }
        
        
        if (!preSearchIndex.equals(searchIndexComponent.getIndex())) {
            lgAmazon.clear(preSearchIndex);
        }
        
        searchingThread = new SearchingThread();
        searchingThread.start();
        
        lgAmazon.keyword = keywordEditor.getText();
        lgAmazon.search(searchIndexComponent.getIndex(), 1);  
        
        preSearchIndex = searchIndexComponent.getIndex();
    }
    
    
    class SearchingThread extends Thread {
        
        public void run() {
            
            try {            
                logo.setRotationAxis(0.0f, 1.0f, 0.0f);
                
                while (true) {
                    logo.changeRotationAngle(logo.getRotationAngle() + (float) Math.PI);
                    sleep(500);
                }
            }
            catch (InterruptedException e) {
            }
            
            logo.changeRotationAngle(0.0f);
        }
    }
    
    
    void createPageButton() throws IOException {
        
        PagingAction action = new PagingAction();
        
        MouseClickedEventAdapter e = 
            new MouseClickedEventAdapter(MouseEvent3D.ButtonId.BUTTON1, action); 
                
        Vector3f size = new Vector3f(LgUtil.pixel2meter(128), LgUtil.pixel2meter(32), 0.0f);
        
//        ImagePanel img1 = new ImagePanel(
//                getClass().getResource("/org/jdesktop/lg3d/apps/lgamazon/resources/more.png"), size.x, size.y); 
        
        ImagePanel img1 = createImagePanel(size.x, size.y, "/org/jdesktop/lg3d/apps/lgamazon/resources/more.png");
        
        moreButton = new Component3D(); 
        moreButton.addChild(img1);
        moreButton.setPreferredSize(size);
        addChild(moreButton);
        moreButton.addListener(e);
        moreButton.addListener(keyListener);
        
//        ImagePanel img2 = new ImagePanel(
//                getClass().getResource("/inox/lg3d/amazon/resources/prev.png"), size.x, size.y);  
        
        ImagePanel img2 = createImagePanel(size.x, size.y, "/org/jdesktop/lg3d/apps/lgamazon/resources/prev.png");
        
        prevButton = new Component3D(); 
        prevButton.addChild(img2);
        prevButton.setPreferredSize(size);
        addChild(prevButton);
        prevButton.addListener(e);
        prevButton.addListener(keyListener);
    }
    
    
    class PagingAction implements ActionFloat3 {
        
        public void performAction(LgEventSource source, float x, float y, float z) {            
            turnPage(source);
        }
    }
     
    
    /**
     * 
     *
     */
    private void turnPage(LgEventSource source) {
        
        if (lgAmazon.isSearching()) {
            return;
        }
        
        if (!preSearchIndex.equals(searchIndexComponent.getIndex())) {
            search();
            return;
        }
                    
        searchingThread = new SearchingThread();
        searchingThread.start();
        
        if (source == moreButton) {
            lgAmazon.nextPage(searchIndexComponent.getIndex());
        }
        else {
            lgAmazon.prevPage(searchIndexComponent.getIndex());
        }
    }
    
    
    class InterruptAction implements ActionFloat3 {
        
        public void performAction(
        LgEventSource source, float x, float y, float z) {
            
            if (!lgAmazon.isSearching()) {
                return;
            }
            
            lgAmazon.interrupt();                       
            searchingThread.interrupt();
        }
    }
    
    
    public void searchResult(Items result) {
        
        searchingThread.interrupt();
        
        moreButton.setVisible(false);
        prevButton.setVisible(false);
        
        if (result == null) {            
            return;
        }
        
        int count = result.getTotalResults().intValue();
        
        String s = (count > 0) ? 
                lgAmazon.currentPage + " / " + result.getTotalPages() + " page (" + count + ")" :         
                "found no results";
        
        searchResult.setText(s);
        searchResult.draw();
        
        if (count == 0) {
            return;
        }
        
        moreButton.setVisible(lgAmazon.currentPage < result.getTotalPages().intValue());
                
        prevButton.setVisible((lgAmazon.currentPage > 1));
    }
    
    
    public void exception(Exception exception) {
        
        exception.printStackTrace();
        
        StringWriter sw = new StringWriter();
        exception.printStackTrace(new PrintWriter(sw));
        
        exceptionLabel.setText("Exception occurred:\n" + sw.toString());
        exceptionLabel.draw();        
        exceptionPanel.setVisible(true);
        
        searchingThread.interrupt();        
    }
}

