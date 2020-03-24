/*
 * Created on Jul 9, 2005
 *
 * Some license info should go here!
 */
package org.jdesktop.lg3d.apps.browser;


/**
 * Basic implementation of Browser3DUIAdapter, prints out the event string to 
 * System.out. Can be used for debugging.
 * 
 * @author demers
 */
public class TestAdapter implements Browser3DUIAdapter
{
    /* (non-Javadoc)
     * @see org.jdesktop.lg3d.apps.browser.Browser3DUIAdapter#titleChanged(java.lang.String)
     */
    public void titleChanged(String title)
    {
      System.out.println("Title: "+ title);
    }

    /* (non-Javadoc)
     * @see org.jdesktop.lg3d.apps.browser.Browser3DUIAdapter#statusChanged(java.lang.String)
     */
    public void statusChanged(String status)
    {
      System.out.println("Status: "+ status);
    }

    /* (non-Javadoc)
     * @see org.jdesktop.lg3d.apps.browser.Browser3DUIAdapter#urlChanged(java.lang.String)
     */
    public void urlChanged(String url)
    {
      System.out.println("URL: "+ url);
    }
  
}
