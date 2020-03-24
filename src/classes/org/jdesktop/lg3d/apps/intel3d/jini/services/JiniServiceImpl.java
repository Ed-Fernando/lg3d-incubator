package org.jdesktop.lg3d.apps.intel3d.jini.services;

import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.PrintJob;
import java.awt.FileDialog;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.io.BufferedReader;

import java.util.StringTokenizer;

import org.jdesktop.lg3d.apps.intel3d.jini.services.IService;

public class JiniServiceImpl implements IService, Serializable {
    
    private SerializableObject waitObject = new SerializableObject ();
    private boolean available = false;
    
    public JiniServiceImpl () {}
    
    public void doService (String operand) {
        System.out.println ("Operand is : " + operand);
        
        // -- Lets create an imaginory Frame and Print the String
        Frame vFrame = new Frame ("File to Print");
        
        // -- Lets add a FileDialog, read the file and print it
        FileDialog fileDialog = new FileDialog (vFrame);
        fileDialog.setVisible (true);
        
        String fileName = fileDialog.getFile ();
        String directory = fileDialog.getDirectory ();
        StringBuffer fileContent = new StringBuffer ();
        
        String fullFileName = directory +
                              System.getProperty ("file.separator") +
                              fileName;
        // -- File content read
        startContentPrinter (fileContent, fullFileName, vFrame);
    }
    
    
    
    private void startContentPrinter (StringBuffer fileContent,
                                      String fullFileName,
                                      Frame vFrame) {
        Thread contentLoader = new ContentLoader (fileContent,
                                                  fullFileName,
                                                  vFrame);
        contentLoader.start ();
    }
    
    private class ContentLoader extends Thread {
        StringBuffer fileContent = null;
        String fullFileName = null;
        Frame vFrame = null;
        ContentLoader (StringBuffer fileContent,
                       String fullFileName,
                       Frame vFrame) {
            this.fileContent = fileContent;
            this.fullFileName = fullFileName;
            this.vFrame = vFrame;
        }
        
        public void run () {
            try {
                BufferedReader in = new BufferedReader (
                                    new FileReader (fullFileName));
                String readLine = "";
                while((readLine = in.readLine ()) != null) {
                    fileContent.append (readLine);
                    fileContent.append ("\n");
                }
                startPrinter (fileContent, vFrame);
            } catch(IOException ioExp) {
                ioExp.printStackTrace ();
                // -- do nothing
            }
        }
        
        private void startPrinter (StringBuffer fileContent, Frame vFrame) {
            System.out.println ("Starting the print job...");
            PrintJob pJob =
            vFrame.getToolkit ().getPrintJob (vFrame, "print", null);
            if(pJob != null) {
                int yOffset = 100;
                Graphics graphics = pJob.getGraphics ();
                graphics.setFont (new Font ("Courier", Font.BOLD, 10));
                StringTokenizer tokenizer = new StringTokenizer (fileContent.toString(), "\n");
                int noOfLines = tokenizer.countTokens ();
                double pageHeight = pJob.getPageDimension ().getHeight ();
                while(tokenizer.hasMoreTokens ()) {
                    if(yOffset < pageHeight) {
                        graphics.drawString (tokenizer.nextToken (), 15, yOffset);
                        yOffset += 16;
                    } else {
                        graphics.dispose ();
                        yOffset = 100;
                        graphics = pJob.getGraphics ();
                        graphics.setFont (new Font ("Courier", Font.BOLD, 10));
                    }
                }
                graphics.dispose ();
                pJob.end ();
            }
        }
    }
    
    public String getDescription () {
        return "The print service";
    }
    
    
}
