/*
 * ChannelListener.java
 *
 * Created on June 23, 2005, 11:23 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package org.jdesktop.lg3d.apps.orgchart.framework;

import java.util.EventObject;
/**
 *
 * @author cc144453
 */
public interface ChannelListener {
    
    void notify(Channel channel, EventObject event);
    
}
