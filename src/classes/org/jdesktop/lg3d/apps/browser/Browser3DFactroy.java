/*
 * Created on Jul 9, 2005
 *
 * Some license info should go here!
 */
package org.jdesktop.lg3d.apps.browser;

import java.util.ResourceBundle;

/**
 * 
 * loads Browser3D Objects based on the Browser.properties file
 * 
 * @author demers
 */

public class Browser3DFactroy
{
  private static Class browserClass = null;

  /**
   * returns a Browser3D object based on the Browser.properties file
   * 
   * @return a Browser3D
   * @throws ClassNotFoundException if browser class cannot be loaded
   */
  public static Browser3D getBrowser3D() throws ClassNotFoundException
  {
    try
    {
      if (browserClass == null)
      {
        ResourceBundle properties = ResourceBundle.getBundle("org.jdesktop.lg3d.apps.browser.Browser");
        String className = properties.getString("org.jdesktop.lg3d.apps.browser.Browser3DClassName");

        browserClass = Class.forName(className);
      }// end if
      
      // return browser class
      return (Browser3D) browserClass.newInstance();
    }
    catch (Exception e)
    {
      e.printStackTrace();
      throw new ClassNotFoundException("Could not load BrowserUI class", e);
    }// end catch
    
  }// end getBrowser3D

}// end Browser3DFactroy
