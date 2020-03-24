/**
 * This code was written during my participation in Google's Summer
 * of Code 2005. Thanks to Google for bringing out such a program
 * and there commitment to open-source.
 */
package org.jdesktop.lg3d.apps.nlc;

import java.io.IOException;
import java.util.logging.Logger;

import org.jdesktop.lg3d.apps.nlc.events.ShutDownNLCEvent;
import org.jdesktop.lg3d.apps.nlc.knowledge.CurrentScene;
import org.jdesktop.lg3d.apps.nlc.knowledge.SceneFactory;
import org.jdesktop.lg3d.apps.nlc.listeners.F12Listener;
import org.jdesktop.lg3d.apps.nlc.mascot.BasicMascot;
import org.jdesktop.lg3d.apps.nlc.mascot.Mascot;
import org.jdesktop.lg3d.displayserver.AppConnectorPrivate;
import org.jdesktop.lg3d.wg.event.LgEvent;
import org.jdesktop.lg3d.wg.event.LgEventConnector;
import org.jdesktop.lg3d.wg.event.LgEventListener;
import org.jdesktop.lg3d.wg.event.LgEventSource;

import nanoxml.XMLParseException;

/**
 * 
 * @author Harsh Jain &lt;harsh@cse.iitb.ac.in&rt;
 */
public class Main {
	
	private static Logger logger = Logger.getLogger("lg.nlc");
	
	private static boolean done;
	
	public static void main(String[] args) throws XMLParseException, IOException {
		logger.info("Starting Natural Language Control");
		done = false;
		SceneFactory.getInstance().init();
		CurrentScene scene = SceneFactory.getInstance().getCurrentScene();
		
		final Nlc nlc = new Nlc(scene, "/etc/lg3d/knowledge.xml");
		nlc.init();
		final Mascot mascot = new BasicMascot(nlc);
		logger.fine("Adding listener for F12");
		LgEventConnector.getLgEventConnector().addListener(
				LgEventSource.ALL_SOURCES, new F12Listener((BasicMascot)mascot));
		LgEventConnector.getLgEventConnector().addListener(
				LgEventSource.ALL_SOURCES, new LgEventListener() {
					public Class<LgEvent>[] getTargetEventClasses() {
						return new Class[]{
							ShutDownNLCEvent.class	
						};
					}
					public void processEvent(LgEvent evt) {
						//Time to shut down
						mascot.stop();
						nlc.close();
						done = true;
					}
				});
		//nothing to do let's loop while done
		while (!done){
			//TODO Can someone suggest a better way :-) ?
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				logger.warning("Thread interrupted");
			}
		}
		logger.info("NLC Quitting cleanly");
	}
}
