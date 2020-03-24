/*
 * DefaultServiceContext.java
 *
 * Created on June 20, 2005, 11:36 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package org.jdesktop.lg3d.apps.orgchart.framework;

import java.io.InputStream;
import java.io.IOException;
import java.security.Key;
import java.security.KeyStore;
import java.util.Date;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;


/**
 *
 * @author cc144453
 */
public class DefaultServiceContext extends ConcurrentHashMap<String,Object>
        implements ServiceContext {
    
    private static final String PREF_VERSION = "version";
    private static final String PREF_THREADS = "threads";
    
    private static final int CURRENT_VERSION = 2;
    private static final String DEFAULT_PREFPATH = "/DefaultServiceContext";
    private static final int DEFAULT_THREADS = 4;
    
    private ClassLoader cl;
    private Preferences prefs;
    private Logger logger;
    private String rsrcBase;
    private ResourceBundle rb;
    private ScheduledExecutorService eventExecutor;
    private ScheduledThreadPoolExecutor taskExecutor;
    
    DefaultServiceContext() {}
    
    public void init(Preferences prefs) throws ServiceException {
        this.logger = Logger.getLogger(getClass().getName());
        
        cl = getClass().getClassLoader();
        if (prefs == null) {
            prefs = Preferences.userRoot().node(DEFAULT_PREFPATH);
        }
        
        // setup the resource path
        String packageName = prefs.get(PREF_I18N, null);
        if (packageName == null) {
            packageName = getClass().getPackage().getName();
        }
        setResourceRoot(packageName.replace('.', '/'));
        
        // initialize resourcebundle
        String bundleName = packageName + ".I18N";
        try {
            rb = ResourceBundle.getBundle(bundleName);
            logger.fine("Resource bundle=" + bundleName);
        } catch (MissingResourceException e) {
            throw new ServiceException("Unable to load resource bundle " +
                    bundleName);
        }
        
        // load default preferences if none found
        if (prefs.getInt(PREF_VERSION, -1) < CURRENT_VERSION) {
            logger.info("Writing default preferences");
            try {
                prefs.removeNode(); // clear the node first
                Preferences.importPreferences(getResourceAsStream("default.xml"));
                prefs = Preferences.userRoot().node(DEFAULT_PREFPATH);
                prefs.flush();
            } catch (Exception e) {
                throw new ServiceException("Exception initializing default preferences", e);
            }
        }
        logger.fine("Preferences path=" + prefs.absolutePath());
        
        // initialize preferenes
        int threads = prefs.getInt(PREF_THREADS, DEFAULT_THREADS);
        taskExecutor = new ScheduledThreadPoolExecutor(threads);
        logger.fine("Max number of threads=" + threads);
        eventExecutor = (ScheduledExecutorService)
        Executors.newSingleThreadScheduledExecutor();
        
        this.prefs = prefs;
    }
    
    public void shutdown() throws ServiceException {
        logger.info("Shutting down DefaultServiceContext");
        
        // shutdown threadpools
        logger.fine("Pending events shutdown size=" +
                eventExecutor.shutdownNow().size());
        logger.fine("Active tasks=" + taskExecutor.getActiveCount());
        logger.fine("Pending tasks shutdown size=" +
                taskExecutor.shutdownNow().size());
        for (int wait = 0; (wait < 8) &&
                (!eventExecutor.isShutdown() || !taskExecutor.isShutdown());
        ++wait) {
            logger.fine("Waiting for thread pools to shutdown=" + wait);
            try {
                Thread.currentThread().sleep(1000);
            } catch (Exception e) {
                logger.log(Level.WARNING, "Shutdown sleep interrupted", e);
            }
        }
        if (!eventExecutor.isShutdown() || !taskExecutor.isShutdown()) {
            logger.log(Level.SEVERE, "Unable to shutdown the threads");
        }
    }
    
    public Logger getLogger() {
        return logger;
    }
    
    public String eval(String expr) {
        return eval(expr, null);
    }
    
    public String eval(String expr, Map map) {
        if (expr == null) {
            return null;
        }
        
        StringBuffer buf = new StringBuffer(expr.length() * 2);
        StringBuffer var = new StringBuffer(48);
        // for variable names
        StringBuffer def = new StringBuffer(48);
        // for expr for variable names
        
        // keep substituting until nothing to change
        String evaluated = null;
        for (;;) {
            buf.setLength(0);
            var.setLength(0);
            def.setLength(0);
            
            /*
             *  Simple parsing that works under these states and writes output to buf.
             *  0 ::= regular char
             *  1 ::= looking for start brace
             *  2 ::= variable char, looking for end brace
             *  3 ::= variable char w/o brace
             */
            int state = 0;
            boolean substitutedSomething = false;
            
            for (int i = 0; i < expr.length(); ) {
                
                boolean processNext = true;
                char c = expr.charAt(i);
                
                // process escape char separately because it is used in all states
                if (c == '\\') {
                    if (i < (expr.length() - 1)) {
                        c = expr.charAt(++i);
                    } else {
                        continue;
                        // ignore the last '\\'
                    }
                }
                
                /*
                 *  now process c based on state
                 */
                switch (state) {
                    case 0:
                        // regular char
                        if (c == '$') {
                            def.append(c);
                            state = 1;
                        } else {
                            if (buf != null) {
                                buf.append(c);
                                substitutedSomething = true;
                            }
                        }
                        break;
                    case 1:
                        // looking for start brace
                        if (c == '{') {
                            def.append(c);
                            // remember the expr in case subst fails
                            state = 2;
                        } else if (Character.isLetterOrDigit(c) || VARCHARS.indexOf(c) >= 0) {
                            def.append(c);
                            // remember the expr in case subst fails
                            var.append(c);
                            // append first character of variable
                            state = 3;
                        } else {
                            state = 0;
                            // go back to regular mode otherwise
                            
                            // and reprocess current character
                            processNext = false;
                            if (buf != null) {
                                buf.append(def.toString());
                            }
                            def.setLength(0);
                        }
                        break;
                    case 2:
                        // variable char, looking for end brace
                        def.append(c);
                        // remember the expr for variable
                        if (c == '}') {
                            state = 0;
                            // back to regular char
                            
                            // expand the variable
                            substitutedSomething |= evalVarBuf(map, buf, var, def);
                            
                        } else {
                            var.append(c);
                        }
                        break;
                    case 3:
                        // variable char w/o brace
                        // we consider a Java identifier char, '.', ':' to be legal
                        if (Character.isLetterOrDigit(c) || VARCHARS.indexOf(c) >= 0) {
                            def.append(c);
                            // remember the expr in case subst fails
                            var.append(c);
                        } else {
                            state = 0;
                            // back to regular char
                            // expand the variable
                            substitutedSomething |= evalVarBuf(map, buf, var, def);
                            
                            // and reprocess current character
                            processNext = false;
                        }
                        break;
                }
                
                // only advance to next character if flag is set
                if (processNext) {
                    ++i;
                }
            }
            
            // now if the state is not 0, we need to finish it
            if (state != 0) {
                substitutedSomething |= evalVarBuf(map, buf, var, def);
            }
            //System.out.println(">>>> " + substitutedSomething + " " + buf.toString());
            
            if (substitutedSomething) {
                evaluated = buf.toString();
                if (evaluated.equals(expr)) {
                    // return if no more changes to the expression
                    break;
                }
            } else {
                // return with whatever value that evaluated had previously
                // can be null if nothing substituted at all
                break;
            }
            // prepare for the next round by passing back the evaluated value from this round
            expr = evaluated;
        }
        
        return evaluated;
    }
    
    private boolean evalVarBuf(
            Map map, StringBuffer buf, StringBuffer varBuf, StringBuffer defBuf) {
        
        boolean substitutedSomething = false;
            /*
             *  Look for the following kind of prefixes.
             *  <model-attribute|map-attribute>
             *  env:<env-variable>
             *  java:<java-property>
             *  key:<keystore-name>:<keystore-key>
             *  <model-name>:<model-attribute>
             *  unknown
             */
        String var = varBuf.toString();
        String varValue = null;
        int pos = var.indexOf(':');
        if (pos >= 0) {
            String prefix = var.substring(0, pos);
            String varName = var.substring(pos + 1).trim();
            if (prefix.equalsIgnoreCase("env")) {
                varValue = System.getenv(varName);
            } else if (prefix.equalsIgnoreCase("java")) {
                varValue = System.getProperty(varName);
            } else if (prefix.equalsIgnoreCase("key")) {
                //varValue = getKey(application.getSubject(), varName);
            } else if (prefix.equalsIgnoreCase("pref")) {
                varValue = prefs.get(varName, null);
            } else {
                varValue = (String)get(varName);
            }
            //System.out.println("##### " + prefix + " " + varName + " " + varValue);
        } else if (map != null) {
            varValue = (String)map.get(var);
        }
        if (varValue != null) {
            substitutedSomething = true;
            buf.append(varValue);
        } else {
            substitutedSomething = false;
            buf.append(defBuf);
        }
        
        // clear the StringBuffers for next variable
        defBuf.setLength(0);
        varBuf.setLength(0);
        
        // return status
        return substitutedSomething;
    }
    
    public KeyStore getKeyStore() {
        return null;
    }
    
    public Key getKey(String alias, char[] password) throws ServiceException {
        return null;
    }
    
    public void setResourceRoot(String rsrcBase) {
        if (!rsrcBase.endsWith("/")) {
            rsrcBase += '/';
        }
        this.rsrcBase = rsrcBase;
    }

    public InputStream getResourceAsStream(String rsrc) throws IOException {
        InputStream in = cl.getResourceAsStream(rsrc);
        if (in == null) {
            // try package path if not found then extend off base
            return cl.getResourceAsStream(rsrcBase + rsrc);
        } else {
            return in;
        }
    }
    
    public synchronized Service getService(String serviceName)
    throws ServiceException {
        Service service = (Service)get(serviceName);
        if (service == null) {
            String nodeName = prefs.get(serviceName, null);
            if (nodeName == null) {
                throw new ServiceException("No definition for service=" + prefs.absolutePath() + " "+
                        serviceName);
            }
            Preferences servicePrefs = prefs.node(nodeName);
            String className = servicePrefs.get(ServiceContext.PREF_CLASSNAME, null);
            if (className == null) {
                throw new ServiceException("No class definition for service=" + serviceName);
            }
            try {
                Class c = cl.loadClass(className);
                service = (Service)c.newInstance();
                service.init(this, servicePrefs);
                put(serviceName, service); // cache service object
            } catch (Exception e) {
                throw new ServiceException("Exception obtaining service=" + className, e);
            }
        }
        return service;
    }
    
    public String i18n(String key) {
        return i18n(key, null);
    }
    
    public String i18n(String key, String def) {
        if (rb != null) {
            try {
                return rb.getString(key);
            } catch (MissingResourceException mre) {
                logger.log(Level.SEVERE,
                        "Error reading I18N value for: " + key,
                        mre);
            }
        }
        logger.warning("Returning default I18N value for: " + key);
        return def;
    }
    
    public Future<?> execute(Runnable task) {
        Future<?> future = taskExecutor.submit(task);
        logger.finest("Executing task=" + task.getClass().getName() +
                " cnt=" + taskExecutor.getActiveCount());
        return future;
    }
    
    public ScheduledFuture<?> schedule(Runnable task, long time) {
        ScheduledFuture<?> future =
                taskExecutor.schedule(task,
                (time - System.currentTimeMillis()),
                TimeUnit.MILLISECONDS);
        logger.finest("Scheduled task=" + task.getClass().getName() +
                " time=" + (new Date(time)));
        return future;
    }
    
    public ScheduledFuture<?> scheduleAtFixedRate(
            Runnable task,
            long initialDelay,
            long period) {
        ScheduledFuture<?> future = taskExecutor.scheduleAtFixedRate(
                task,
                initialDelay,
                period,
                TimeUnit.MILLISECONDS);
        logger.finest("Scheduled task=" + task.getClass().getName() +
                " ms=" + period);
        return future;
    }
    
    public Future<?> post(Channel channel, EventObject event) {
        Future<?> future = eventExecutor.submit(new EventTask(channel, event));
        logger.finest("Posting event=" + event.getClass().getName());
        return future;
    }
    
    public ScheduledFuture<?> schedule(Channel channel, EventObject event, long time) {
        ScheduledFuture<?> future =
                taskExecutor.schedule(
                new EventTask(channel, event),
                (time - System.currentTimeMillis()),
                TimeUnit.MILLISECONDS);
        logger.finest("Scheduled event=" + event.getClass().getName() +
                " time=" + (new Date(time)));
        return future;
    }
    
    public ScheduledFuture<?> scheduleAtFixedRate(
            Channel channel, EventObject event, long initialDelay, long period) {
        ScheduledFuture<?> future = eventExecutor.scheduleAtFixedRate(
                new EventTask(channel, event),
                initialDelay,
                period,
                TimeUnit.MILLISECONDS);
        logger.finest("Scheduled event=" + event.getClass().getName() +
                " ms=" + period);
        return future;
    }
    
    private class EventTask implements Runnable {
        private Channel channel;
        private EventObject event;
        private EventTask(Channel channel, EventObject event) {
            this.channel = channel;
            this.event = event;
        }
        public void run() {
            String channelName = channel.getClass().getName();
            String eventName = event.getClass().getName();
            try {
                logger.fine("Posting channel=" + channelName +
                        " event=" + eventName);
                channel.post(event);
            } catch (Exception e) {
                logger.log(Level.WARNING, "Exception when posting event channel=" +
                        channelName + " event=" + eventName, e);
                
            }
        }
    }
}