/*
 * Created on Aug 22, 2005
 *
 * Some license info should go here!
 */
package org.jdesktop.lg3d.apps.browser;


/**
 * Simple bean used to track the brower history.  Bean contains a url and a title.
 * 
 * TODO: in the future the webpage fav.ico and notes should be added.
 * 
 * @author demers
 */
public class HistoryItem
{
  
  private String url;
  private String title;
  
  /**
   * Creates a new HistoryItem
   * 
   * @param url web page URL
   * @param title web page title
   */
  public HistoryItem( String url, String title )
  {
    this.url = url;
    this.title = title;
  }// end HistoryItem
  
  /**
   * gets the web page title
   * @return web page title
   */
  public String getTitle()
  {
    return title;
  }

  /**
   * sets the webpage title
   * @param title webpage title
   */
  public void setTitle(String title)
  {
    this.title = title;
  }

  /**
   * gets the webpage URL
   * @return the webpage URL
   */
  public String getUrl()
  {
    return url;
  }

  /**
   * sets the webpage URL
   * @param url the wepabe URL
   */
  public void setUrl(String url)
  {
    this.url = url;
  }
  
}
