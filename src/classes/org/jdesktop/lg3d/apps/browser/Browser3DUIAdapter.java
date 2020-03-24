package org.jdesktop.lg3d.apps.browser;

/**
 * Basic interface for Browser events.  A Browser3D object will fire these 
 * events when they occur.  
 */
public interface Browser3DUIAdapter
{
  /**
   * Title of the current webpage.  
   * Example if the URL is 
   * http://www.google.com the title would be Google
   * 
   * @param title the title of the web page. 
   */
  public void titleChanged(String title);

  /**
   * Javascript Status bar text for things like Anchor mouse overs.
   * 
   * @param status text for the status.
   */
  public void statusChanged(String status);

  /**
   * Fired when ever the url is changed.
   * 
   * @param url the new web pages URL
   */
  public void urlChanged(String url);
}
