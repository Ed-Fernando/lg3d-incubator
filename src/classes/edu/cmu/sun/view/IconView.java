 /*
  * 3D File Manager - Project Looking Glass 
  * Copyright Sun Microsystems, 2005
  * 
  * Project Course in Human-Computer Interaction
  * Carnegie Mellon University
  * 
  * Aditya Chand, Braden Kowitz, Jake Pierson, Jessica Smith
  */

package edu.cmu.sun.view;


import org.jdesktop.lg3d.wg.Component3D;

import edu.cmu.sun.components.ImageComponent;
import edu.cmu.sun.model.FileNodeModel;
import edu.cmu.sun.model.ItemModel;

/**
 * Belongs to an ItemView and draws the file icons in the scene.
 * 
 * @author Braden Kowitz, Jake Pierson, Jessica Smith
 */
public class IconView extends Component3D {

	static final float SIZE = ItemModel.CONTENT_HEIGHT;
	static final String RESOURCE_DIR = "edu/cmu/sun/resources/icons/";
	
	public IconView(FileNodeModel file)
	{
		String imagePath = getImagePath(file);
		ImageComponent icon = new ImageComponent(imagePath, SIZE, SIZE);
		this.addChild(icon);
	}

	private String getImagePath(FileNodeModel file)
	{
		String extension = file.getExtension();
		String iconFilename;
                
		if (file.isFolder()) {
			iconFilename = "folder.png";
                } else if (extension.equals("txt")) {
			iconFilename = "text2.png";
		} else if ((extension.equals("png")) || (extension.equals("jpg")) || (extension.equals("bmp"))) {
			iconFilename = "image2.png";
		} else if ((extension.equals("wav")) || (extension.equals("mp3")) || (extension.equals("mid"))) {
			iconFilename = "audio.png";
		} else if ((extension.equals("gzip")) || (extension.equals("tar")) || (extension.equals("jar")) || (extension.equals("zip"))) {
			iconFilename = "gzip.png";
		} else if ((extension.equals("exe")) || (extension.equals("bin")) || (extension.equals("bash"))) {
			iconFilename = "exe.png";
		} else  {
			iconFilename = "file2.png";
		}
		return RESOURCE_DIR + iconFilename;
	}
	
}
