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
 * ResultsInfoPanel.java                                                   *
 *                                                                         *
 * Created on 17 de agosto de 2005, 2:42                                   *
 ***************************************************************************/

package org.jdesktop.lg3d.apps.googler.gui;
import java.awt.Color;
import org.jdesktop.lg3d.wg.Container3D;


/**
 * A multi panel with some convenience methods to display info from search results
 * @author opsi
 */
public class ResultsInfoPanel extends Container3D{        
    /**
     * The textpanels arranged in this
     */
    private TextPanel3D[] panels = new TextPanel3D[4];        
    
    /**
     * Top container for all panels
     */
    private Container3D top;
    /**
     * Number of lines used in each panel
     */
    private int []panelSizes = {0,0,0,0};;
    /**
     * Maximun number of lines per panel
     */
    private int []panelSizesMax = {1,1,5,5};;
    /**
     * Relative size modifier for each panel
     */
    private float []panelMod;
    /**
     * The unscaled letterSize for the panels
     */
    private float baseLetterSize;
    /**
     * Unscaled width of the panels
     */
    private int width;    
    /**
     * Creates a new panel with the given parameters
     * @param width Width of the panel (before scaling)
     * @param baseLetterSize Lettersize of the panels (before scaling)
     * @param panelMod Modifiers to scale each panel
     */
    public ResultsInfoPanel(int width, float baseLetterSize,float [] panelMod)
    {
        assert(panelMod.length == 4):("Panel modifier array must be four elements long");
        this.baseLetterSize = baseLetterSize;                
        this.width = width;
        this.panelMod = panelMod;
        top = new Container3D();
        for(int i = 0; i < panels.length;i++)
        {
            panels[i] = new TextPanel3D(panelSizesMax[i], width, baseLetterSize*panelMod[i]);            
            top.addChild(panels[i]);            
        }                
        addChild(top);
        
    }
    /**
     * Creates a new instance of ResultsInfoPanel
     * @param width Width of the panel (before scaling)
     * @param baseLetterSize Lettersize of the panels (before scaling)
     */
    public ResultsInfoPanel(int width,float baseLetterSize) {
        this(width,baseLetterSize, new float[] {1.0f,0.7f,1.0f,1.0f});
    }
    /**
     * Layout the panels depending on how many lines is using each
     */
    public void layout()
    {
        float acum = 0;        
        for(int i = 0; i < panels.length;i++)
        {                        
            panels[i].setTranslation(0,-acum,0);
            acum+= panelMod[i]*baseLetterSize*panelSizes[i];               
        }
    }
    /**
     * Sablish the first panel
     * @param title Title of the shown result
     */
    public void setTitle(String title)
    {
        panelSizes[0] = panels[0].setTextAfter(0, title, Colors.TRANS_BLACK,Color.BLACK,Colors.TRANS_WHITE,TextPanel3D.ALIGNMENT.CENTER);
        panels[0].removeRowsAfter(panelSizes[0]);
        layout();
    }
    
    /**
     * Stablish the second panel text
     * @param url URL of the shown resultElement
     */
    public void setURL(String url)
    {
        panelSizes[1] = panels[1].setTextAfter(0, url, Colors.TRANS_PALE_YELLOW,Color.WHITE,Color.BLACK, TextPanel3D.ALIGNMENT.CENTER);
        panels[1].removeRowsAfter(panelSizes[1]);
        layout();
    }
    /**
     * Stablish the third panel text
     * @param snnipet Snnipet of the shown result
     */
    public void setSnnipet(String snnipet)
    {        
        panelSizes[2] = panels[2].setTextAfter(0, snnipet, Colors.TRANS_PALE_YELLOW,Color.WHITE,Colors.TRANS_DARK_GRAY, TextPanel3D.ALIGNMENT.CENTER);
        panels[2].removeRowsAfter(panelSizes[2]);
        layout();
    }
    /**
     * Stablish the fourth panel
     * @param sum Summary of the shown result
     */
    public void setSummary(String sum)
    {
        panelSizes[3] = panels[3].setTextAfter(0, sum, Color.DARK_GRAY,Color.BLACK,Colors.TRANS_PALE_YELLOW, TextPanel3D.ALIGNMENT.CENTER);        
        panels[3].removeRowsAfter(panelSizes[3]);
        layout();
    }
    
    /**
     * Remove the text from all panels
     */
    public void cleanAll()
    {
        setTitle("");
        setURL("");
        setSummary("");
        setSnnipet("");
    }
}