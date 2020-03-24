package org.jdesktop.lg3d.apps.browser;

import java.util.ArrayList;

import javax.swing.JComponent;

/**
 * Abstract class used for the HTML component.  This class should be used to 
 * wrap any HTMl rendered used in the system.  To configure which Browser3D is 
 * used See Browser3DFactory and Browser.properties
 * 
 * Implementation note:  History for subclasses is managed by this class.
 * 
 */
public abstract class Browser3D
{
  
  /**
   * Object to track the history for forward back navigation
   */
  protected HistoryTracker historyTracker;

  /**
   * a collection of Browser3DUIAdapter
   */
  private ArrayList<Browser3DUIAdapter> adapters = new ArrayList<Browser3DUIAdapter>();

  /**
   * Gets the JComponent used to display HTML
   * TODO: This should be removed and changed to getBrowserComponent3D or something
   * 
   * @return component used to display HTML
   */
  public abstract JComponent getBrowserPanel();
  
  /**
   * Called by setURL, to do implementation spesific code
   * 
   * @param url
   */
  protected abstract void setImplURL( String url );
  
  /**
   * returns browser current URL 
   * 
   * @return current URL
   */
  protected abstract String getURL();
  
  /**
   * returns browser current title
   * 
   * @return current title
   */
  protected abstract String getTitle();
  
  
  // need to add the HistoryTracker to any Browser3D
  {
    historyTracker = new HistoryTracker( this );
    this.addBrowser3DUIAdapter( historyTracker );
  }
  
  
  /**
   * set the browser URL, and render that location
   * 
   * @param url
   */
  final public void setURL( String url )
  {
    this.setImplURL( url );
  }// end setURL
  
  /**
   * Add an adapter
   * @param Browser3DUIAdapter adapter events will be fired when properties change
   */
  public void addBrowser3DUIAdapter(Browser3DUIAdapter adapter)
  {
    this.adapters.add( adapter );
  }
  
  /**
   * remove an adapter
   * @param adapter adapter to be removed
   */
  public void removeBrowser3DUIAdapter(Browser3DUIAdapter adapter)
  {
    this.adapters.remove( adapter );
  }

  /**
   * gets the adapters for the browser
   * @return a Collection of Browser3DUIAdapter
   */
  protected ArrayList<Browser3DUIAdapter> getBrowser3DUIAdapters()
  {
     return this.adapters;
  }// end getBrowser3DUIAdapters


  /**
   * gets the HistoryTracker used to track the browser History
   * @return the browsers HistorTracker
   */
  public HistoryTracker getHistoryTracker()
  {
    return historyTracker;
  }
  
}
