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
 * Created on July 12, 2005, 6:25 PM
 */

package org.jdesktop.lg3d.apps.googler.gui;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.jdesktop.lg3d.apps.googler.engine.SearchEngine;
import org.jdesktop.lg3d.apps.googler.engine.GoogleSearchParameters;
import org.jdesktop.lg3d.apps.googler.engine.internal.ResultElement;
import org.jdesktop.lg3d.utils.action.ActionNoArg;
import org.jdesktop.lg3d.utils.c3danimation.NaturalMotionAnimation;
import org.jdesktop.lg3d.utils.c3danimation.ScaleAndRotateChangeVisiblePlugin;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.utils.shape.FuzzyEdgePanel;
import org.jdesktop.lg3d.utils.shape.GlassyPanel;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.wg.Cursor3D;
import org.jdesktop.lg3d.wg.Frame3D;
import org.jdesktop.lg3d.wg.SwingNode;
import org.jdesktop.lg3d.wg.Toolkit3D;
import org.jdesktop.lg3d.wg.event.LgEventSource;


/**
 * This is the main frame for the Googler application. It is enabled to receive
 * the results, and create the appropiate ResultElement3D for each.
 * @author opsi
 */
public class GooglerFrame3D extends Frame3D {
    /**
     * Maximun results to obtain from google
     */
    private final int MAX_RESULTS = 60;
    /**
     * This is a class that encapsulates a text field used for input
     */
    private class TextInputComponent3D extends SwingNode {
        /**
         * The text input component
         */
        private JTextField searchInput;
        /**
         * This is the panel to attach searchInput
         */
        private JPanel panel;
        /**
         * Creates a new TextInput using externalRadius as a size reference.
         * Uses a "magic" number
         * @param externalRadius Reference to calculate the size of this component.
         */
        public TextInputComponent3D(float externalRadius) {
            super();
            panel = new JPanel();
            searchInput = new JTextField();
            searchInput.setColumns((int)(225*externalRadius));
            searchInput.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    showSearchResults(showingResults = !showingResults);
                    if(showingResults)
                        doQuery(input.getText());
                }
            });            
            panel.add(searchInput);
            setPanel(panel);
        }
        /**
         * Returns the text inserted in the searchInput component
         * @return Text in searchInput
         */
        public String getText() {
            return searchInput.getText();
        }
    }
    
    /**
     * For logging purposes
     */
    private Logger logger = Logger.getLogger("org.jdesktop.lg3d.googler");
    
    /**
     * Maximun radius for this application
     */
    private float externalRadius;
    
    /**
     * This is the panel where all the info from each ResultElement is shown to user
     */
    protected ResultsInfoPanel resultPanel;
    
    /**
     * This is a component to hold the resultPanel shape
     */
    private Container3D resultPanelBack;
    
    /**
     * This is the text label that says "Search"
     */
    private TextPanel3D searchTextLabel;
    /**
     * This is a panel that is before the "Search" label, and that is who
     * receives the events as a button
     */
    private Component3D searchTextPanel;
    
    /**
     * This is Swing text input. A native text input should be nice.
     */
    private TextInputComponent3D input;
    
    
    /**
     * Container to move the elementsHash that form the base box together
     */
    private Container3D bubbleCont;
    
    /**
     * This is the container that keeps all the three-dimensional results
     */
    protected ResultsContainer resultsContainer;
    
    
    /**
     * Flag that indicates if the user has been informed that the first results
     * for it's search arrived
     */
    private boolean firstNotified = false;
    /**
     * Flag that indicate if the resultspanel/container is been shown to the user
     */
    private boolean showingResults = false;
    /**
     * Id of the associated SearchThread used to stop it if required.
     */
    private long searchThreadID;
    
    /**
     * Pattern used as regex to detect html tags
     */
    private static Pattern pat = Pattern.compile("<[^<>]*>", Pattern.CASE_INSENSITIVE);
    /**
     * Array of colors used to obtain sequences of different colors
     */
    private Color[] nextColor = new Color[] {Color.YELLOW,Color.MAGENTA,Color.BLUE,Color.CYAN,Color.GREEN};
    /**
     * Index of the nextColor to return by getNextColor
     */
    int colorIndex = 0;
    
    /**
     * Obtains from nextColor the Color indicated by colorIndex and increments it.
     * @return The next Color
     */
    public Color getNextColor() {
        return nextColor[colorIndex=(colorIndex+1)%nextColor.length];
    }
    /** Creates a new instance of GooglerFrame3D */
    public GooglerFrame3D() {
        super();
        externalRadius = Toolkit3D.getToolkit3D().getScreenWidth()/2.5f;
        float letterSize = 0.15f*externalRadius;
        
        resultsContainer = new ResultsContainer(MAX_RESULTS,externalRadius,this);
        resultsContainer.changeTranslation(/*externalRadius/1.9f*/0,externalRadius/8,0);
        resultsContainer.setRotationAxis(1f, 0f,0);
        resultsContainer.setRotationAngle((float)Math.toRadians(-15));
        resultsContainer.setVisible(false);
        
        searchTextLabel = new TextPanel3D(1,6,letterSize);
        searchTextLabel.setTranslation(0.6f*letterSize,0.15f*letterSize, 0.4f*letterSize);
        searchTextLabel.setRow(0, "SEARCH", Color.BLACK, Color.WHITE,null ,22, TextPanel3D.ALIGNMENT.LEFT);
        searchTextLabel.setAnimation(new NaturalMotionAnimation(500));
        searchTextLabel.setPickable(false);
        
        searchTextPanel = new Component3D();
        searchTextPanel.addChild(new FuzzyEdgePanel(6*letterSize,letterSize,letterSize/10,new SimpleAppearance(0.8f,0.0f,0.5f,0.4f)));
        searchTextPanel.setCursor(Cursor3D.SMALL_CURSOR);
        searchTextPanel.setTranslation(0,0.3f*-letterSize, 0.1f*letterSize);
        
        input = new TextInputComponent3D(externalRadius);
        input.setTranslation(0, externalRadius/14,0.1f*letterSize);
        input.setAnimation(new NaturalMotionAnimation(5000,new ScaleAndRotateChangeVisiblePlugin(3000)));
        
        resultPanel = new ResultsInfoPanel(50, Toolkit3D.getToolkit3D().getScreenWidth()/60);
        resultPanel.setTitle("Titulo");
        resultPanelBack = new Container3D();
        resultPanelBack.setVisible(false);
        resultPanelBack.setPickable(false);
        resultPanelBack.addChild(resultPanel);
        resultPanelBack.setAnimation(new NaturalMotionAnimation(5000,new ScaleAndRotateChangeVisiblePlugin(3000)));
        resultPanelBack.changeTranslation(-externalRadius/7, externalRadius/1.2f,0);
        
        Component3D boxComp = new Component3D();
        boxComp.addChild(new GlassyPanel(externalRadius,externalRadius/4,externalRadius/8,new SimpleAppearance(0.7f,0.0f,0.0f,0.5f)));
        
        
        bubbleCont = new Container3D();
        bubbleCont.setAnimation(new NaturalMotionAnimation(5000));
        bubbleCont.addChild(boxComp);
        bubbleCont.addChild(searchTextLabel);
        bubbleCont.addChild(searchTextPanel);
        bubbleCont.addChild(input);
        
        addChild(resultPanelBack);
        addChild(bubbleCont);
        addChild(resultsContainer);
        initEvents();
        parkBubble(false);
    }
    
    /**
     * Initiate global events for this frame
     */
    protected void initEvents() {
        searchTextPanel.setMouseEventPropagatable(true);
        searchTextPanel.addListener(
                new MouseClickedEventAdapter(
                new ActionNoArg() {
            public void performAction(LgEventSource source) {
                showSearchResults(showingResults = !showingResults);
                if(showingResults)
                    doQuery(input.getText());
            }
        }));
    }
    /**
     * Notify user that new results have arrived (it's only run when the first 
     * result of each search is processed)
     */
    protected void notifyFirstArrival() {
        if(!firstNotified) {
            resultPanel.setTitle("We received some results");
            resultPanel.setURL("");
            resultPanel.setSnnipet("Click with mouse on them to see a description");
            resultPanel.setSummary("Double-click to open related documents");
            
            resultsContainer.changeVisible(true);
            firstNotified = true;
        }
    }
    /**
     * This method switch betwen the input mode and the browse mode
     * @param show if true switch to browse mode, else switch to input mode
     */
    protected void showSearchResults(boolean show) {
        if(show) {
            parkBubble(true);
            resultPanel.cleanAll();
            resultPanel.setTitle("Search is running, please wait ...");
            resultPanelBack.changeVisible(true);
            resultsContainer.changeTranslation(0,externalRadius/8,0);
            resultsContainer.setRotationAxis(1f, 0f,0);
            resultsContainer.changeRotationAngle((float)Math.toRadians(-15));
            resultsContainer.changeVisible(true);
            input.changeVisible(false);
            searchTextLabel.setRow(0, " BACK ", Color.WHITE, Color.BLACK, null, TextPanel3D.ALIGNMENT.LEFT);
            
        } else {
            SearchEngine.getEngine().stopSearch(searchThreadID);
            searchThreadID = -1;
            
            resultsContainer.changeVisible(false);
            resultsContainer.removeResults();
            
            resultPanelBack.changeVisible(false);
            
            input.changeVisible(true);
            
            searchTextLabel.setRow(0, "SEARCH", Color.BLACK, Color.WHITE, null, TextPanel3D.ALIGNMENT.LEFT);
            
            firstNotified = false;
            parkBubble(false);
        }
    }
    /**
     * Initiates a query to google
     * @todo : This method will receive more parameters as they become available 
     * on the GUI
     * @param query The string query for Google
     */
    protected void doQuery(String query) {
        if(query.equals("")) {
            resultPanel.cleanAll();
            resultPanel.setTitle("You should introduce a query before hitting search button");
            return;
        }
        GoogleSearchParameters params = new GoogleSearchParameters();
        params.setQuery(query);
        params.setMaxResults(MAX_RESULTS);
        params.setFiltered(true);
        searchThreadID= SearchEngine.getEngine().queryRequest(params,resultsContainer);
    }
    
    /**
     * Displays the received element in the ResultPanel
     * @param element Element to process
     */
    protected void showElementDescription(ResultElement element) {
        if(element == null)
            return;
        resultPanel.setURL(element.getURL());
        resultPanel.setTitle(cleanTags(element.getTitle()));
        resultPanel.setSummary(cleanTags(element.getSummary()));
        resultPanel.setSnnipet(cleanTags(element.getSnippet()));
    }
    /**
     * This method is supposed to eliminate HTML tags from a string
     * @param html The string to cleanup
     * @return The html string with no tags
     */
    public static String cleanTags(String html) {
        if(html.length()==0)
            return "";
        int lastEnd = 0;
        String sal = "";
        String tmp;
        Matcher match = pat.matcher(html);
        if(!match.find())
            sal = html;
        else
            do
            {            
            tmp=html.substring(lastEnd, match.start());
            tmp.replace("&nbsp;"," ");
            // TODO Convert &nbsp; and others to letters
            sal+=tmp;
            lastEnd = match.end();
            }while(match.find());
        if(lastEnd < html.length()-1)
            sal+=html.substring(lastEnd);        
        return sal;
    }
    /**
     * Parks (or unparks) the search box on the left size of the screen.
     * @param park Indicates if it must park (true) of unpark (false) the box.
     */
    public void parkBubble(boolean park) {
        if(park) {
            bubbleCont.changeTranslation(-externalRadius,0,0);
            bubbleCont.setRotationAxis(0,0, 1);
            bubbleCont.changeRotationAngle((float)Math.toRadians(-90));
        } else {
            bubbleCont.changeTranslation(0,0,0);
            bubbleCont.setRotationAxis(0,0, 1);
            bubbleCont.changeRotationAngle((float)Math.toRadians(0));
        }
    }
}