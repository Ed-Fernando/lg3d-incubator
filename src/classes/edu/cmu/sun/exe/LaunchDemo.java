 /*
  * 3D File Manager - Project Looking Glass 
  * Copyright Sun Microsystems, 2005
  * 
  * Project Course in Human-Computer Interaction
  * Carnegie Mellon University
  * 
  * Aditya Chand, Braden Kowitz, Jake Pierson, Jessica Smith
  */

package edu.cmu.sun.exe;

import java.io.File;

import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.wg.Frame3D;
import org.jdesktop.lg3d.wg.Toolkit3D;
import org.jdesktop.lg3d.wg.event.Component3DEvent;
import org.jdesktop.lg3d.wg.event.LgEvent;
import org.jdesktop.lg3d.wg.event.LgEventListener;

import edu.cmu.sun.animation.TransitionManager;
import edu.cmu.sun.model.ColumnModel;
import edu.cmu.sun.model.FileNodeModel;
import edu.cmu.sun.model.ListModel;
import edu.cmu.sun.model.SceneModel;
import edu.cmu.sun.model.WindowModel;
import edu.cmu.sun.view.SceneView;


/**
 * This Class bootstraps the entire project, and gets
 * the application up and running.
 * 
 * It can easily be modified to point at a different portion of the filesystem.
 * 
 * TODO: refactor the launchDemo!!!!!!
 * 
 * @author Braden Kowitz, Jessica Smith, Jake Pierson
 */
public class LaunchDemo implements LgEventListener {
	
	private static String BASE_FILE_PATH = "/"; // hideya
	
	SceneModel sceneModel;
	Frame3D frame3d;
	
	public static void main(String[] args) {
		new LaunchDemo(BASE_FILE_PATH);
	}

	public LaunchDemo(String baseFilePath)
	{
		frame3d = new Frame3DWithoutTransparencyAnimation(); // hideya
		
		sceneModel = new SceneModel();
		
		sceneModel.setSize(
                        Toolkit3D.getToolkit3D().getScreenHeight(), 
                        Toolkit3D.getToolkit3D().getScreenWidth());
		
		SceneView sceneView = new SceneView(sceneModel);
		sceneView.setTranslation(0f,SceneModel.BOTTOM_BORDER/2f,0f);
		frame3d.addChild(sceneView);
		
		ColumnModel colModel = new ColumnModel();
		addWindowToColumn(colModel, baseFilePath);
		sceneModel.addColumn(colModel);
		
		TransitionManager.updateViews();
		
		frame3d.addListener(this);
		frame3d.setPreferredSize(new Vector3f(.01f,.01f,.01f));
        frame3d.changeEnabled(true);
        frame3d.changeVisible(true);
        
        frame3d.addListener(this);
	}

	private void addWindowToColumn(ColumnModel colModel, String filePath)
	{
		WindowModel win = getWindowModelForPath(filePath);
		win.setCloseable(false);
		colModel.addWindow(win);
	}
	
	private WindowModel getWindowModelForPath(String path)
	{
		File home = new File(path);
		FileNodeModel fNode = new FileNodeModel(home);
		ListModel listModel = new ListModel(fNode);
		return new WindowModel(listModel);
	}
	
	
	
	public void processEvent(LgEvent e) {
		sceneModel.setSize(
                        Toolkit3D.getToolkit3D().getScreenHeight(), 
                        Toolkit3D.getToolkit3D().getScreenWidth());
	}


	@SuppressWarnings("unchecked")
	public Class<LgEvent>[] getTargetEventClasses() {
		Class[] types = new Class[] {Component3DEvent.class};
		return types;
	}

        // hideya
        private class Frame3DWithoutTransparencyAnimation extends Frame3D {
            public void changeTransparency(float transparency, int duration) {
                // 
            }
        }
}
