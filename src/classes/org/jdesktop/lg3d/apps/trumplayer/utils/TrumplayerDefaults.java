/*
 * TrumplayerDefaults.java
 *
 * Created on 2007/05/21, 11:17
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.jdesktop.lg3d.apps.trumplayer.utils;

import java.io.File;

/**
 *
 * @author yasuhiro
 */
public class TrumplayerDefaults {

    public static String HOME_DIR = java.lang.System.getProperty("user.home");
    public static String TRUMPLAYER_XML = "trumplayer.xml";
    public static String TRUMPLAYER_DIR = "trumplayer";

    public static String TRUMPLAYER_LOCALHOME = HOME_DIR + File.separator + TRUMPLAYER_DIR;
    public static String TRUMPLAYER_CONFIG_PATH = HOME_DIR + File.separator + TRUMPLAYER_XML;
    
}
