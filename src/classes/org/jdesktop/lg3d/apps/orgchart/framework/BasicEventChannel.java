/*
 * BasicEventChannel.java
 *
 * Created on June 23, 2005, 11:27 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package org.jdesktop.lg3d.apps.orgchart.framework;

import java.util.EventObject;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cc144453
 */
public class BasicEventChannel implements Channel {
    
    private Logger logger = Logger.getLogger(getClass().getName());
    private CopyOnWriteArrayList<ChannelListener> listeners =
            new CopyOnWriteArrayList<ChannelListener>();
    
    public void post(EventObject event) {
        for (ChannelListener listener : listeners) {
            listener.notify(this, event);
        }
    }
    
    public void addListener(ChannelListener listener) {
        listeners.addIfAbsent(listener);
    }
    
    public void removeListener(ChannelListener listener) {
        listeners.remove(listener);
    }
    
    public void removeAllListeners() {
        listeners.clear();
    }
}
