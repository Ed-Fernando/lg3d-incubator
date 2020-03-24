/*
 * Channel.java
 *
 * Created on June 23, 2005, 11:21 AM
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
public interface Channel {
    void post(EventObject event);
    void addListener(ChannelListener listener);
    void removeListener(ChannelListener listener);
    void removeAllListeners();
}
