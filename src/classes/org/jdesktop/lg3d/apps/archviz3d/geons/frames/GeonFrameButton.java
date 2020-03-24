package org.jdesktop.lg3d.apps.archviz3d.geons.frames;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.vecmath.Point3f;

import org.jdesktop.lg3d.sg.BranchGroup;
import org.jdesktop.lg3d.utils.shape.ImagePanel;
import org.jdesktop.lg3d.wg.Component3D;

/**
 * Clase para los botones de los frames. 
 * 
 * @author Juan Feldman - Lucas Vigneau
 *
 */
public class GeonFrameButton extends Component3D {
	/** Contenedor de la imagen del boton.*/
	private BranchGroup buttonBG = null;
	/** Logger de mensajes.*/
	private static Logger logger = Logger.getLogger("lg.ArchViz3D");	

	/**
	 * Instancia esta clase.
	 * @param imageName Nombre de la imagen que le vamos a asignar al boton. 
	 * @param position Posicion del boton.  
	 * @param width Ancho del boton.
	 * @param height Largo del boton. 
	 */
	public GeonFrameButton(String imageName, Point3f position, float width, float height) {
		buttonBG = createIcon(imageName.toLowerCase(), width, height);
		this.addChild(buttonBG);
		this.setTranslation(position.x, position.y, position.z);
	}

	/**
	 * Crea un boton, poniendo una imagen como textura.
	 *
	 * @param imageName Nombre del file que contiene la imagen.
	 * @param width Ancho del boton.
	 * @param height Largo del boton. 
	 * @return BranchGroup Contenedor de la imagen boton.
	 */
	private BranchGroup createIcon(String imageName, float widht, float height) {
		BranchGroup bg = new BranchGroup();
		try
		{
			ImagePanel buttonIP = new ImagePanel(getClass().getResource("/resources/images/frame/" + imageName), widht, height);
			bg.addChild(buttonIP);
		}
		catch (Exception e) {
			logger.log(Level.SEVERE, "File " + imageName + " dosen't exist");	
		}
		return bg;
	}

}
