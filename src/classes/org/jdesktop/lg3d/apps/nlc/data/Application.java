/**
 * This code was written during my participation in Google's Summer
 * of Code 2005. Thanks to Google for bringing out such a program
 * and there commitment to open-source.
 */
package org.jdesktop.lg3d.apps.nlc.data;

import org.jdesktop.lg3d.wg.Frame3D;

/**
 * 
 * @author harsh
 *
 */
public class Application {
	
	private Frame3D frame;
	
	private ApplicationConfig appConfig;
	
	public Application(){
		
	}
	
	public Application(Frame3D frame){
		this();
		setFrame(frame);
	}
	
	public Application(Frame3D frame, ApplicationConfig appConfig) {
		this(frame);
		setAppConfig(appConfig);
	}

	/**
	 * Special constructor to create application without a running
	 * frame. Such applications are not running applications, but they
	 * can be launched using the config.
	 * 
	 * @param appConfig
	 */
	public Application(ApplicationConfig appConfig){
		this();
		setAppConfig(appConfig);
	}
	
	public ApplicationConfig getAppConfig() {
		return appConfig;
	}
	
	private boolean matchFrame(Frame3D other){
		if (frame == null || other == null)
			return frame == null && other == null;
		return frame.equals(other);
	}
	
	private boolean matchConfig(ApplicationConfig other){
		if (appConfig == null || other == null){
			return appConfig == null && other == null;
		}
		return appConfig.equals(other);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Application){
			Application other = (Application) obj;
			return matchConfig(other.appConfig)
				&& matchFrame(other.frame);
		}
		return false;
	}

	public void setAppConfig(ApplicationConfig appConfig) {
		if (appConfig == null) {
			throw new NullPointerException(
					"Argument to method setAppConfig is null");
		}
		this.appConfig = appConfig;
	}

	public Frame3D getFrame() {
		return frame;
	}

	public void setFrame(Frame3D frame) {
		if (frame == null) {
			throw new NullPointerException(
					"Argument to method setFrame is null");
		}
		this.frame = frame;
	}
	
	@Override
	public String toString() {
		if (appConfig == null){
			return frame.getName();
		} else {
			return appConfig.getCommand();
		}
	}
	
}
