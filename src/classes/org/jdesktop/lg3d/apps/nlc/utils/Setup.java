/**
 * This code was written during my participation in Google's Summer
 * of Code 2005. Thanks to Google for bringing out such a program
 * and there commitment to open-source.
 */
package org.jdesktop.lg3d.apps.nlc.utils;


import java.util.logging.Logger;

import org.jdesktop.lg3d.apps.nlc.actions.MouseMonitorAction;
import org.jdesktop.lg3d.apps.nlc.knowledge.CurrentScene;
import org.jdesktop.lg3d.apps.nlc.listeners.AppLaunchListener;
import org.jdesktop.lg3d.apps.nlc.listeners.CloseFrameListener;
import org.jdesktop.lg3d.apps.nlc.listeners.FocusFrameListener;
import org.jdesktop.lg3d.apps.nlc.listeners.Frame3DAddedListener;
import org.jdesktop.lg3d.apps.nlc.listeners.Frame3DRemovedListener;
import org.jdesktop.lg3d.apps.nlc.listeners.HideFrameListener;
import org.jdesktop.lg3d.apps.nlc.listeners.LaunchApplicationListener;
import org.jdesktop.lg3d.apps.nlc.listeners.ParkLeftListener;
import org.jdesktop.lg3d.apps.nlc.listeners.ParkRightListener;
import org.jdesktop.lg3d.apps.nlc.listeners.RotateFrameListener;
import org.jdesktop.lg3d.utils.eventadapter.MouseEnteredEventAdapter;
import org.jdesktop.lg3d.wg.Frame3D;
import org.jdesktop.lg3d.wg.event.LgEventConnector;
import org.jdesktop.lg3d.wg.event.LgEventSource;

public class Setup {
	
	private static Logger logger = Logger.getLogger("lg.nlc");
	
	public static void setupEventListeners(CurrentScene scene){
		if (scene == null) {
			throw new NullPointerException(
					"Argument to method setupEventListeners is null");
		}
		logger.info("Setting up Event listeners");
		LgEventConnector connector = LgEventConnector.getLgEventConnector();
		connector.addListener(LgEventSource.ALL_SOURCES,
				new AppLaunchListener(scene));
		connector.addListener(Frame3D.class, 
				new Frame3DAddedListener(scene));
		connector.addListener(Frame3D.class,
				new Frame3DRemovedListener(scene));
		connector.addListener(Frame3D.class,
				new HideFrameListener());
		connector.addListener(Frame3D.class, 
				new CloseFrameListener());
		connector.addListener(Frame3D.class,
				new FocusFrameListener());
		connector.addListener(Frame3D.class,
				new ParkLeftListener());
		connector.addListener(Frame3D.class,
				new ParkRightListener());
		connector.addListener(Frame3D.class,
				new RotateFrameListener());
		connector.addListener(LgEventSource.ALL_SOURCES,
				new LaunchApplicationListener());
		connector.addListener(Frame3D.class,
				new MouseEnteredEventAdapter(new MouseMonitorAction(scene)));
	}
	
	public static void removeEventListeners(CurrentScene scene){
		if (scene == null) {
			throw new NullPointerException(
					"Argument to method removeEventListeners is null");
		}
		logger.info("Removing Event listeners");
		LgEventConnector connector = LgEventConnector.getLgEventConnector();
		connector.removeListener(LgEventSource.ALL_SOURCES,
				new AppLaunchListener(scene));
		connector.removeListener(Frame3D.class, 
				new Frame3DAddedListener(scene));
		connector.removeListener(Frame3D.class,
				new Frame3DRemovedListener(scene));
		connector.removeListener(Frame3D.class,
				new HideFrameListener());
		connector.removeListener(Frame3D.class, 
				new CloseFrameListener());
		connector.removeListener(Frame3D.class,
				new FocusFrameListener());
		connector.removeListener(Frame3D.class,
				new ParkLeftListener());
		connector.removeListener(Frame3D.class,
				new ParkRightListener());
		connector.removeListener(LgEventSource.ALL_SOURCES,
				new LaunchApplicationListener());
	}
}
