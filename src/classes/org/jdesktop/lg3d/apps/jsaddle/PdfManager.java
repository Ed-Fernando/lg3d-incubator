/*
 * PdfManager.java
 *
 * Created on 2007/01/02, 0:24
 *
 * PdfManager is a adapter class between apps and JPedal
 *
 */

package org.jdesktop.lg3d.apps.jsaddle;

import java.awt.Toolkit;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import org.jpedal.PdfDecoder;

/**
 *
 * @author Yasuhiro Fujitsuki(thaniwa)
 */
public class PdfManager{
    
    private PdfDecoder pdfDecoder;
    private String currentFile;
    private int currentPage = 1;

    private int panelHeight;
    private int panelWidth;

    private int pdfHeight;
    private int pdfWidth;
    
    private float scale;
    
    /** Creates a new instance of PdfViewerPanel */
    public PdfManager(){
        setPanelSize(0,0);
        initialize();
    }
    
    public PdfManager(int width, int height){
        setPanelSize(width, height);
        initialize();
    }

    private void initialize(){
        pdfDecoder = new PdfDecoder();
    }

    public BufferedImage getImage(){
        return getImage(currentPage);
    }

    public BufferedImage getImage(int number){
        return getImage(number,this.scale);
    }

    public synchronized BufferedImage getImage(int number,float scale){
        
        // resize pdf panel
        if(this.scale != scale)
            pdfDecoder.setPageParameters( scale ,1 );
        
        BufferedImage image = null;
        if( number > 0 && number <= pdfDecoder.getPageCount() ){
            try{
                image = pdfDecoder.getPageAsImage(number);
            } catch( Exception e ){
                e.printStackTrace();
            }
        }

        // resize pdf panel
        if(this.scale != scale)
            pdfDecoder.setPageParameters( this.scale ,1 );

        return image;
    }

    public BufferedImage getThumbnailImage(){
        return getThumbnailImage(currentPage);
    }

    public BufferedImage getThumbnailImage(int number){
        //return getThumbnailImage(number,200,150);
        return getThumbnailImage(number,256,256);
    }

    public BufferedImage getThumbnailImage(int number, int width, int height){
        BufferedImage image = null;
        if( number > 0 && number <= pdfDecoder.getPageCount() ){
            if(width <= 0) width = 200;
            if(height <= 0) height = 150;
            float scale = 0.25f;
            if(pdfHeight > 0 && pdfWidth > 0 ){
                float scale_height = (float) height / pdfHeight;
                float scale_width = (float) width / pdfWidth;
                if( scale_height < scale_width )
                    scale = scale_height;
                else scale = scale_width;
            
                if(scale == 0.0f) scale = 0.25f;
            }

            image = this.getImage(number,scale);
            
        }
        return image;
    }
    
    public void setPanelSize(int width, int height){
        panelHeight = height;
        panelWidth = width;
    }
    
    public void decodeResourceURL(String name){
        currentFile = name;
        ClassLoader loader = this.getClass().getClassLoader();
        java.io.InputStream is = loader.getResourceAsStream(name);
        byte[] data = new byte[2097152];
        try{
            int b = is.read();
            int i = 0;
            java.util.Vector<Byte> vector = new java.util.Vector<Byte>();
            while( b >= 0){
                data[i] = (byte)b;
                i++;
                b = is.read();
                if(b == -1){
                    data[i]=(byte)b;
                    break;
                }
            }
            is.close();
        } catch(java.io.IOException e){
            e.printStackTrace();
        }
        
        try{
            // pdf file open and read
            pdfDecoder.openPdfArray(data);

            // decode page
            pdfDecoder.decodePage(currentPage);

            // values scaling (1=100%). page number
            pdfDecoder.setPageParameters( 1 ,1 );

            // get PDF's height and width
            pdfHeight = pdfDecoder.getPDFHeight();
            pdfWidth = pdfDecoder.getPDFWidth();
            
            // System.out.println(height +" , " + width);
            // System.out.println("panel = " + panelHeight + " , " + panelWidth);
            
            scale = 1.0f;
            if( pdfHeight != 0 && pdfWidth != 0){
                float scale_height = (float) panelHeight / pdfHeight;
                float scale_width = (float) panelWidth / pdfWidth;
                if( scale_height < scale_width )
                    scale = scale_height;
                else scale = scale_width;
            }
            
            if(scale == 0.0f) scale = 1.0f;

            // resize pdf panel
            pdfDecoder.setPageParameters( scale ,1 );
            
        }catch(Exception e){
                e.printStackTrace();
        }
    }
    
    public void decodeFile(String name) {
        currentFile = name;//store file name for use in page changer

        try{
            // pdf file open and read
            pdfDecoder.openPdfFile(currentFile);

            // decode page
            pdfDecoder.decodePage(currentPage);

            // values scaling (1=100%). page number
            pdfDecoder.setPageParameters( 1 ,1 );

            // get PDF's height and width
            pdfHeight = pdfDecoder.getPDFHeight();
            pdfWidth = pdfDecoder.getPDFWidth();
            
            // System.out.println(height +" , " + width);
            // System.out.println("panel = " + panelHeight + " , " + panelWidth);
            
            scale = 1.0f;
            if( pdfHeight != 0 && pdfWidth != 0){
                float scale_height = (float) panelHeight / pdfHeight;
                float scale_width = (float) panelWidth / pdfWidth;
                if( scale_height < scale_width )
                    scale = scale_height;
                else scale = scale_width;
            }
            
            if(scale == 0.0f) scale = 1.0f;

            // resize pdf panel
            pdfDecoder.setPageParameters( scale ,1 );
            
        }catch(Exception e){
                e.printStackTrace();
        }
    }
    
    public void setPage(int number){
        if(number > 0 && number <= pdfDecoder.getPageCount() ){
            currentPage = number;
            /*
             * PdfDecorder.decodePage() is decode PDF and make an image,
             * but this apps does not need this function.
            try{
                pdfDecoder.decodePage(currentPage);
            } catch(Exception e){
                e.printStackTrace();
            }
             */
        }
    }
    
    public void next(){
        setPage(currentPage + 1);
    }

    public void prev(){
        setPage(currentPage - 1);
    }
    
    public int getCurrentPageNumber(){ return currentPage; }
    public int getLastPageNumber(){ return pdfDecoder.getPageCount(); }

}
