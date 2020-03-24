/**
 * This code was written during my participation in Google's Summer
 * of Code 2005. Thanks to Google for bringing out such a program
 * and there commitment to open-source.
 */
package org.jdesktop.lg3d.apps.nlc.knowledge;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.jdesktop.lg3d.apps.nlc.data.Application;
import org.jdesktop.lg3d.wg.Frame3D;

public class CurrentScene {
	
	private static final Logger logger = Logger.getLogger("lg3d.sc");
	
	private List<Application> runningApplications;
	
	private String recent;
	
	private Application focussedApplication;
	
	protected CurrentScene() {
		this.runningApplications = new ArrayList<Application>();
	}
	
	public void addApplication(Application application) {
		if (application == null) {
			throw new NullPointerException(
					"Argument to method addApplication is null");
		}
		logger.info("Running Application " + application);
		this.runningApplications.add(application);
	}
	
	public void setFocussedApplication(Application focussedApplication) {
		this.focussedApplication = focussedApplication;
	}
	
	
	/**
	 * Searches the application corresponding to the supplied
	 * frame. If none is found null is returned
	 * 
	 * @param frame
	 * @return
	 */
	public Application searchApplication(Frame3D frame){
		if (frame == null) {
			throw new NullPointerException(
					"Argument to method searchApplication is null");
		}
		logger.info("Searching application for the frame " + frame.getName());
		for (Application app: runningApplications){
			if (frame.equals(app.getFrame())){
				return app;
			}
		}
		return null;
	}
	
	public void removeApplication(Frame3D frame){
		if (frame == null) {
			throw new NullPointerException(
					"Argument to method removeApplication is null");
		}
		logger.info("Removing application " + frame.getName());
		for (Iterator<Application> iter = runningApplications.iterator(); iter.hasNext(); ){
			Application app = iter.next();
			if (app.getFrame().equals(frame))
				iter.remove();
		}
	}
	
	public List<Application> getAllApplications() {
		return Collections.unmodifiableList(runningApplications);
	}
	
	public Application getApplication(int i) {
		return runningApplications.get(i);
	}
	
	public void setRecent(String recent){
		if (recent == null) {
			throw new NullPointerException(
					"Argument to method setRecent is null");
		}
		this.recent = recent;
	}
	
	public String getRecent() {
		return recent;
	}
	
	public void eraseRecent(){
		recent = null;
	}
	
	public int getNumApplications(){
		return runningApplications.size();
	}
	
	//TODO Implement it properly. for now return one of the visible app
	public Application getFocussedApplication(){
		return focussedApplication;
	}
}
