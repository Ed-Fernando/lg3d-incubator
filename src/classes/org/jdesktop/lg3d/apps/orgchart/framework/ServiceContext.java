/*
 * ServiceContext.java
 *
 * Created on June 20, 2005, 11:32 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package org.jdesktop.lg3d.apps.orgchart.framework;

import java.io.InputStream;
import java.io.IOException;
import java.io.Serializable;
import java.security.Key;
import java.security.KeyStore;
import java.util.EventObject;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

/**
 *
 * @author cc144453
 */
public interface ServiceContext extends Map<String,Object>, Serializable {    
    static final String PREF_CLASSNAME = "classname";
    static final String PREF_I18N = "i18n";

    static final String VARCHARS = ":./";
    void init(Preferences prefs) throws ServiceException;
    void shutdown() throws ServiceException;
    Logger getLogger();
    String eval(String expr);
    String eval(String expr, Map map);
    KeyStore getKeyStore();
    Key getKey(String alias, char[] password) throws ServiceException;
    void setResourceRoot(String rsrcBase);
    InputStream getResourceAsStream(String key) throws IOException;
    Service getService(String serviceName) throws ServiceException;
    String i18n(String key);
    String i18n(String key, String def);
    Future<?> execute(Runnable task);
    ScheduledFuture<?> schedule(Runnable task, long time);
    ScheduledFuture<?> scheduleAtFixedRate(Runnable task, long initialDelay, long period);
    Future<?> post(Channel channel, EventObject event);    
    ScheduledFuture<?> schedule(Channel channel, EventObject event, long time);
    ScheduledFuture<?> scheduleAtFixedRate(
            Channel channel, EventObject event, long initialDelay, long period);
}
