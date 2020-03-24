/**
 * This code was written during my participation in Google's Summer
 * of Code 2005. Thanks to Google for bringing out such a program
 * and there commitment to open-source.
 */
package org.jdesktop.lg3d.apps.nlc.knowledge;

import java.util.logging.Logger;

/**
 * Scene Factory will support multiple scenes in future. Some work
 * is already going on at Lg3D with this respect. We will integrate
 * later via this class.
 * 
 * @author harsh
 *
 */
public class SceneFactory {
	
	private static Logger logger = Logger.getLogger("lg.nlc");
	
	private static SceneFactory instance;
	
	private CurrentScene scene;
	
	public static SceneFactory getInstance(){
		if (instance == null)
			instance = new SceneFactory();
		return instance;
	}
	
	public void init(){
		logger.info("Initializing current scene");
		scene = new CurrentScene();
	}
	
	public CurrentScene getCurrentScene() {
		return scene;
	}
	
}
