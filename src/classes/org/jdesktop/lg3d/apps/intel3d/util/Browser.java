/*
 * Browser.java
 *
 * Copyright (c) 2003 Sun Microsystems, Inc.
 * 901 San Antonio Road, Palo Alto, California, 94303, U.S.A.
 * All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Sun
 * Microsystems, Inc. ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Sun.
 *
 * Created on November 18, 2003, 5:50 PM
 */

package org.jdesktop.lg3d.apps.intel3d.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.awt.Cursor;
import java.awt.FileDialog;
import java.awt.BorderLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetContext;
import java.awt.dnd.DropTargetListener;
import java.awt.dnd.DropTargetDragEvent;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;

import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JComboBox;
import javax.swing.JMenuItem;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JInternalFrame;

import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import java.net.URL;
import java.net.UnknownHostException;
import java.net.MalformedURLException;
import java.net.NoRouteToHostException;

/**
 *
 * @author  asrivast
 */
public class Browser extends JInternalFrame {
    
    private JEditorPane _view;
    private JScrollPane scrollPane;
    private JComboBox _commandLine;
    private URL defaultURL;
    private JPanel panel;
    private static Browser _this;
    
    class TextAreaTarget implements DropTargetListener {
        
        public void dragEnter(DropTargetDragEvent dtde) {
            dtde.acceptDrag(1);
        }
        
        public void dragExit (DropTargetEvent dte) {
        }
        
        public void dragOver (DropTargetDragEvent dtde) {
            dtde.acceptDrag(1);
        }
        
        public void drop (java.awt.dnd.DropTargetDropEvent dtde) {
            DropTargetContext dtc = dtde.getDropTargetContext();
            boolean flag = false;
            
            if((dtde.getSourceActions() & 0x1) != 0) {
                dtde.acceptDrop(1);
            } else {
                dtde.rejectDrop();
                return;
            }
            
            DataFlavor adataFlavor[] = dtde.getCurrentDataFlavors();
            DataFlavor dataFlavor = null;
            for(int i = 0; i < adataFlavor.length; i++) {
                if(!DataFlavor.plainTextFlavor.equals(adataFlavor[i]))
                    continue;
                dataFlavor = adataFlavor[i];
                break;
            }
            if(dataFlavor != null) {
                Transferable transferable = dtde.getTransferable();
                InputStream inputStream = null;
                try {
                    inputStream = 
                        (InputStream)transferable.getTransferData(dataFlavor);
                } catch(IOException ioExp) {
                    ioExp.printStackTrace();
                    dtc.dropComplete(false);
                    return;
                } catch(UnsupportedFlavorException ufExp) {
                    ufExp.printStackTrace();
                    dtc.dropComplete(false);
                    return;
                }
                if(inputStream != null) {
                    try {
                        InputStreamReader isReader = 
                                          new InputStreamReader(inputStream);
                        StringWriter sWriter = new StringWriter();
                        BufferedReader bReader = new BufferedReader(isReader);
                        boolean flag1 = true;
                        while(flag1) {
                            int j = isReader.read();
                            if(j != -1) {
                                sWriter.write(j);
                            } else {
                                flag1 = false;
                            }
                        }
                        _view.setPage("file://" + sWriter.toString());
                        flag = true;
                    } catch(IOException ioExp) {
                    } catch(Exception exp) {
                        exp.printStackTrace();
                        dtc.dropComplete(false);
                        return;
                    }
                } else {
                    flag = false;
                }
            }
            dtc.dropComplete(flag);
        }
        
        public void dropActionChanged (DropTargetDragEvent dtde) {
        }
        
        public TextAreaTarget(JEditorPane jEditorPane) {
            _view = jEditorPane;
        }
    }
        
    /** Creates a new instance of Browser */
    public Browser () {
        // -- name, resizable, closeable, maximizable, iconifiable
        super("Browser", true, true, true, true);
        _this = this;
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        
        setProxy("webcache.central", "8080");
        
        _view = new JEditorPane();
        _view.setEditable(false);
        
        scrollPane = new JScrollPane(_view);
        
        _commandLine = new JComboBox();
        _commandLine.setEditable(true);
        _commandLine.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    _view.setText("");
                    JComboBox jComboBox = (JComboBox)actionEvent.getSource();
                    String str = (String)jComboBox.getSelectedItem();
                    URL url = new URL(str);
                    setURL(url);
                    _commandLine.addItem(str);
                } catch(MalformedURLException muExp) {
                }
            }
        });
        
        _view.addHyperlinkListener(new HyperlinkListener() {
            public void hyperlinkUpdate(HyperlinkEvent hlEvent) {
                if(hlEvent.getEventType().toString().equals("ENTERED")) {
                    setCursor(Cursor.getPredefinedCursor(12));
                }
                if(hlEvent.getEventType().toString().equals("ACTIVATED")) {
                    setCursor(Cursor.getPredefinedCursor(3));
                    setURL(hlEvent.getURL());
                }
                if(hlEvent.getEventType().toString().equals("EXITED")) {
                    setCursor(Cursor.getPredefinedCursor(0));
                }
            }
        });
        try {
            defaultURL = new URL("http://www.sun.com");
        } catch(MalformedURLException muExp) {
        }
        
        setURL(defaultURL);
        _view.setDropTarget(new DropTarget(_view, new TextAreaTarget(_view)));
        setJMenuBar(createMenuBar());
        panel.add(_commandLine, "North");
        panel.add(scrollPane, "Center");
        _this.getContentPane().add(panel);
        setSize(600, 700);
        setLocation(100, 90);
    }
    
    private JMenuBar createMenuBar() {
        JMenuBar jmBar = new JMenuBar();
        JMenu jMenu = new JMenu("File");
        jMenu.setMnemonic(70);
        JMenuItem jmItem = new JMenuItem("Open");
        jmItem.setMnemonic(79);
        jmItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent aEvent) {
                FileDialog fDialog = new FileDialog(new JFrame(), "Open", 0);
                fDialog.setVisible(true);
                String file = fDialog.getFile();
                String directory = fDialog.getDirectory();
                String fullPath = directory + file;
                try {
                    _view.setPage("file:" + fullPath);
                    _commandLine.addItem("file:" + fullPath);
                } catch(IOException ioExp) {}
            }
        });
        jMenu.add(jmItem);
        jmBar.add(jMenu);
        return jmBar;
    }
    
    
    private void setURL(URL url) {
        try {
            _view.setPage(url);
            _commandLine.addItem(url.toExternalForm().toString());
        } catch(NoRouteToHostException nrthExp) {
            // -- do nothing
        } catch(UnknownHostException uhExp) {
            // -- do nothing
        } catch(IOException ioExp) {
            // -- do nothing
        }
        setCursor(Cursor.getPredefinedCursor(0));
    }
                    
   
    private void setProxy(String hostName, String port) {
        System.getProperties().put("proxySet", "true");
        System.getProperties().put("proxyHost", hostName);
        System.getProperties().put("proxyPort", port);
    }
}
