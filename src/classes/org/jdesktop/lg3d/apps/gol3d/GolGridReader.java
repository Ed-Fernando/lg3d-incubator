/*
 * GolGridReader.java
 *
 * Created on July 28, 2006, 8:45 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.jdesktop.lg3d.apps.gol3d;


import java.io.*;
import java.util.BitSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mathieu Van der Haegen
 */
public class GolGridReader extends Thread {
        private static Logger logger = Logger.getLogger("lg.gol3d");
        private int dim;
        private GolGridComponent ref;
        private BufferedReader in;
        private String s;
        private String filename;
        char lineData[];
        private BitSet B;
        private float itemWidth;
        private float itemInnerWidth;
        
        public void setFileName(String fn) {
            filename = fn;
        }
        
        public void setGolGridComp(GolGridComponent g) {
            ref = g;
        }
        
        public void setBoolMatrix(BitSet bm)  {
            B=bm;
        }
        

        public void run() {
            logger.log(Level.INFO,"Running GolGridReader Thread!");
            java.net.URL url = getClass().getClassLoader().getResource("org/jdesktop/lg3d/apps/gol3d/configs/" + filename);
            logger.log(Level.WARNING,"filename = " + filename);
            logger.log(Level.WARNING,"PATH: resolved name = " + url);
        
            try {

                InputStream is = url.openStream();
                in = new BufferedReader(new InputStreamReader(is));
                s = in.readLine();
                dim = Integer.parseInt(s);
                ref.setDim(dim);
                ref.setBoolMatrixSize(dim * dim);
                itemWidth = GolGroundComponent.groundInnerWidth / dim;
                ref.setItemWidth(itemWidth);
                ref.setItemInnerWidth(itemWidth * (1.0f - (2.0f * ref.itemBorderRatio)));
                ref.setItemBorderWidth(itemWidth * ref.itemBorderRatio);

                logger.log(Level.INFO,"Dim found : " + dim);

         
                 //all bits are initialized to false
                lineData= new char[dim+1];

                for (int x=0;x<dim;x++) {
                    //+1 because of the \n
                    s = in.readLine();
                    for (int y=0;y<dim;y++) {
                        if (s.toCharArray()[y] == '1') {
                            B.set(((y * dim) + x));
                        }
                    }

                }
                
                ref.build();
                
                logger.log(Level.INFO,"boolMatrix : " + B);   

        
            } catch(IOException Ioe) {            
                logger.log(Level.SEVERE,"Error opening grid config from : " + filename);
            } catch(NumberFormatException Ne) {            
                logger.log(Level.SEVERE,"Error bad number format in config from : " + filename);
            }

        }
        
    }