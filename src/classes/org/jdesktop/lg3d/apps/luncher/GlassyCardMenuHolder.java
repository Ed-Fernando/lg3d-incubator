/*
 * GlassyCardMenuHolder.java
 *
 * Created on August 25, 2004, 4:07 PM
 */

package org.jdesktop.lg3d.apps.luncher;
import org.jdesktop.lg3d.wg.Container3D;

/**
 *
 * @author  Henrik Baastrup
 */
public interface GlassyCardMenuHolder {
    /**
     * Will change the shortcuts for the object, tipicaly the icons on a taskbar.
     */
    public void setShortcuts(Container3D shortcuts);
    
}
