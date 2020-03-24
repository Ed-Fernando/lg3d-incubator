/***************************************************************************
 *   Copyright (C) 2005 by Juan Gonzalez                                   *
 *   juan@aga-system.com                                                   *
 *                                                                         *
 *   This program has been developed under Google's "Summer of Code 2005". *
 *   Special thanks goes to all people from Google and Sun Microsystems    *
 *   who made this cool experience a kind of success.                      *
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 *   This program is distributed in the hope that it will be useful,       *
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of        *
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the         *
 *   GNU General Public License for more details.                          *
 *                                                                         *
 *   You should have received a copy of the GNU General Public License     *
 *   along with this program; if not, write to the                         *
 *   Free Software Foundation, Inc.,                                       *
 *   59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.             *
 ***************************************************************************
 *
 * Created on July 16, 2005, 4:19 PM
 */

package org.jdesktop.lg3d.apps.googler.gui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import org.jdesktop.lg3d.sg.TextureAttributes;
import org.jdesktop.lg3d.sg.utils.image.TextureLoader;
import org.jdesktop.lg3d.utils.shape.ImagePanel;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Container3D;


/**
 * This is a generic text panel that allow to write text in a matrix of letters, providing an halo effect borrowed from jim zaun's kwebdemo1.
 * You can indicate Halocolor, Fontcolor and BackgroundColor for each line/letter
 * @author opsi
 */

public class TextPanel3D extends Container3D {
    public static enum ALIGNMENT {LEFT,CENTER,RIGHT};
    /**
     * Matrix of LetterPanel(s) to access each letter indepently
     */
    protected LetterPanel matrix[][];
    
    /**
     * Size of each letter
     */
    protected float letterSize;
    
    /**
     * Creates a new TextPanel3D with the given parameters
     * @param nrows Number of rows for this panel
     * @param ncols Number of culmns for this panel
     * @param letterSize Size of each letter
     */
    public TextPanel3D(int nrows,int ncols,float letterSize) {
        if(nrows<=0 || ncols<=0)
            throw new IllegalArgumentException("Number of rows and columns must be positive");
        this.letterSize = letterSize;
        
        float horJump = (ncols * letterSize)/2;
        float verJump = (nrows * letterSize)/2;
        
        matrix = new LetterPanel[nrows][ncols];
        
        for(int i = 0; i < nrows;i++) {
            for(int j = 0;j < ncols;j++) {
                Component3D comp = new Component3D();
                matrix[i][j] = new LetterPanel(letterSize);
                comp.addChild(matrix[i][j]);
                comp.setTranslation(j*letterSize-horJump, -i*letterSize-verJump, 0);
                addChild(comp);
            }
        }
        
        removeRows();
    }
    public int setTextAfter(int firstRow,String text, Color haloColor, Color fontColor, Color backColor, ALIGNMENT alignment) {
        String tmp = new String(text);
        int i = firstRow,d;
        int nextFreeRow = firstRow;
        ALIGNMENT alig = alignment;
        while(tmp.length()>0 && i < getNrows()) {
            d = getNcols() >= tmp.length() ? tmp.length() : getNcols();
            setRow(i,tmp.substring(0, d), haloColor, fontColor, backColor, alig);
            tmp = new String(tmp.substring(d));
            if(tmp.length()<getNcols() && alignment == ALIGNMENT.CENTER)
                alig = ALIGNMENT.LEFT;
            i++;
            nextFreeRow++;
        }
        return nextFreeRow;
    }
    /**
     * This method is used to write one only letter in a concrete row/column
     * @param row Row of the matrix where we want to write
     * @param col Column of the matrix where we want to write
     * @param c Character to write
     * @param haloColor Color of the text halo
     * @param fontColor Inside font color
     * @param backColor Color of the background around each letter
     * @param fontStyle Font style from java.awt.Font
     * @return True if the letter is set correctly, false else
     */
    public boolean setLetter(int row,int col,char c, Color haloColor, Color fontColor, Color backColor, int fontStyle) {
        if(row >= 0 && row < matrix.length && col >= 0 && col < matrix[0].length) {
            matrix[row][col].setLetter(c, haloColor,fontColor, backColor, fontStyle);
            return true;
        } else
            return false;
    }
    /**
     * Calls removeRowsAfter(0) to clear all rows up
     */
    public void removeRows() {
        removeRowsAfter(0);
    }
    /**
     * Clean-up all the rows after the parameter. It call cleanRow for each row after first (included)
     * @param first First row to remove
     */
    public void removeRowsAfter(int first){
        if(first<0 || first > getNrows())
            throw new IllegalArgumentException("You tried to remove an unexistant row");
        for(int i = first; i< matrix.length;i++)
            cleanRow(i);
    }
    /**
     * Writes an empty string to the parameter row, mnaking it transparent this way.
     * @param row Row to clean-up
     */
    public void cleanRow(int row) {
        addString(row, 0, matrix[0].length, "", Color.BLACK,Color.BLACK, null, Font.BOLD);
    }
    /**
     * Stablish given text value to the named row starting at column 0, with a fixed font size
     * @param row Row of the matrix where we want to write
     * @param rowText Text to write
     * @param haloColor Color of the text halo
     * @param fontColor Inside font color
     * @param backColor Color of the background around each letter
     */
    public void setRow(int row,String rowText, Color haloColor, Color fontColor, Color backColor, ALIGNMENT alignment) {
        setRow(row, rowText, haloColor, fontColor, backColor, Font.BOLD,alignment);
    }
    /**
     * Stablish given text value to the named row starting at column 0
     * @param row Row of the matrix where we want to write
     * @param rowText Text to write
     * @param haloColor Color of the text halo
     * @param fontColor Inside font color
     * @param backColor Color of the background around each letter
     * @param fontStyle Font style from java.awt.Font
     */
    public void setRow(int row,String rowText, Color haloColor, Color fontColor,Color backColor, int fontStyle, ALIGNMENT alignment) {
        int firstI = 0;
        switch(alignment) {
            case LEFT:
                firstI = 0;
                break;
            case CENTER:
                if(rowText.length() <= getNcols())
                    firstI = (getNcols() - rowText.length())/2;
                break;
            case RIGHT:
                if(rowText.length() <= getNcols())
                    firstI = getNcols() - rowText.length();
                
        }
        addString(row, firstI, matrix[0].length, rowText, haloColor, fontColor, backColor, fontStyle);
    }
    /**
     * Stablish given text value to the named interval of columns in the given row
     * @param row Row of the matrix where we want to write
     * @param firstCol The starting column for inserted text
     * @param lastCol The ending cloumnf for inserted text
     * @param newText Text to write
     * @param haloColor Color of the text halo
     * @param fontColor Inside font color
     * @param backColor Color of the background around each letter
     * @param fontStyle Font style from java.awt.Font
     */
    public void addString(int row, int firstCol, int lastCol, String newText, Color haloColor, Color fontColor, Color backColor, int fontStyle) {
        String toAdd = newText;
        assert(newText != null);
        assert(row >= 0);
        assert(row < matrix.length);
        if(newText.length() > lastCol-firstCol)
            toAdd = newText.substring(0, lastCol-firstCol);
        for(int i = 0; i < lastCol;i++)
            if(i >= firstCol && i < lastCol && toAdd.length() > (i-firstCol))
                setLetter(row, i, toAdd.charAt(i-firstCol), haloColor, fontColor, backColor, fontStyle);
            else
                setLetter(row, i,' ', haloColor, fontColor, backColor, fontStyle);
    }
    
    /**
     * Obtains the number of rows for this textpanel
     * @return number of rows
     */
    public int getNrows() {
        return matrix.length;
    }
    
    /**
     * Obtains the number of columns for this textpanel
     * @return number of columns
     */
    public int getNcols() {
        return matrix[0].length;
    }
}
/**
 * This is a single letter that is manipulated by TextPanel3D to create the matrix where developer can write.
 * It extends ImagePanel to automaticly create a square geometry, ans so is a Component3D
 */
class LetterPanel extends ImagePanel {
    /**
     * Attributes for this letter's texture. It reused each time we write a new letter on the panel
     */
    private TextureAttributes texattr;
    private AlphaComposite composite;
    private String fontName = "SansSerif";
    /**
     * Creates a new panel
     * @param size Font/panel size
     */
    public LetterPanel(float size) {
        super(size,size, true);
        float[] texCoords = {
            0.0f, 1.0f,
            1.0f, 1.0f,
            1.0f, 0.0f,
            0.0f, 0.0f,
        };
        geom.setTextureCoordinates(0, 0, texCoords);
        setAppearance(new SimpleAppearance(0.0f,0.0f,0.0f, 0.5f));
        setCapability(LetterPanel.ALLOW_APPEARANCE_WRITE);
        texattr = new TextureAttributes();
        texattr.setTextureMode(TextureAttributes.REPLACE);
        String[] names;
        GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
        genv.preferProportionalFonts();
        names = genv.getAvailableFontFamilyNames();
        for (int i = 0; i < names.length; i++) {
            if (names[i].equals("Aharoni CLM")) fontName =  names[i];
            else if (names[i].equals("Century Gothic")) fontName =  names[i];
            else if (names[i].equals("URW Gothic L")) fontName =  names[i];
        }
        composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
    }
    /**
     * Writes the given letter, with the given attributes on this panel
     * @param c Character to write
     * @param haloColor Color of the text halo
     * @param fontColor Inside font color
     * @param backColor Color of the background around each letter
     * @param fontStyle Font size in pixels
     */
    public void setLetter(char c,Color haloColor,Color textColor, Color backColor, int fontStyle) {
        String text = c+"";        
        
        BufferedImage image;        
        Graphics2D g;        
        TextureLoader loader;
        SimpleAppearance app;
                
        image = new BufferedImage(32, 32,
                BufferedImage.TYPE_INT_ARGB);
        g = (Graphics2D) image.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setFont(new Font(fontName, fontStyle, 22));
        if(backColor!=null) {
            g.setPaint(backColor);
            g.fillRect(0,0, 32, 32);
        }
        g.setColor(haloColor);
        
        g.setComposite(composite);
        drawStringAsHalo(g, text, 2);
        composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
        g.setComposite(composite);
        g.setColor(textColor);
        g.drawString(text, 4, 24);
        g.dispose();
        loader = new TextureLoader(image);
        app = new SimpleAppearance(0.0f,0.0f,0.0f, 0.5f);
        app.setCapability(
                SimpleAppearance.ALLOW_TEXTURE_ATTRIBUTES_WRITE |
                SimpleAppearance.ENABLE_TEXTURE |
                SimpleAppearance.DISABLE_CULLING);
        app.setTexture(loader.getTexture());
        app.setTextureAttributes(texattr);
        setAppearance(app);
    }
    /**
     * IMPORTANT : About this code, it's covered by GPL 2 (http://www.opensource.org/licenses/gpl-license.php)
     *  , copyright:
     *   Joint Copyright (c) 2005 by
     *   James A. Zaun, Consultant,
     *   The Burke Institute,
     *   Sun Microsystems, Inc.
     * ALL RIGHTS RESERVED.
     */
    private void drawStringAsHalo(Graphics2D g, String text, int offset) {
        int offset2 = (int) Math.round((float) offset / Math.sqrt(2.0));
        g.drawString(text, 4-offset, 24);
        g.drawString(text, 4-offset2, 24-offset2);
        g.drawString(text, 4, 24-offset);
        g.drawString(text, 4+offset2, 24-offset2);
        g.drawString(text, 4+offset, 24);
        g.drawString(text, 4+offset2, 24+offset2);
        g.drawString(text, 4, 24+offset);
        g.drawString(text, 4-offset2, 24+offset2);
    }
}