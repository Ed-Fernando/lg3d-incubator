package org.jdesktop.lg3d.apps.archviz3d.geons.menu;

import java.awt.Font;

import javax.vecmath.Color3f;
import javax.vecmath.Point3f;

import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.sg.Material;
import org.jdesktop.lg3d.utils.action.ActionBoolean;
import org.jdesktop.lg3d.utils.c3danimation.NaturalMotionAnimation;
import org.jdesktop.lg3d.utils.eventadapter.MouseEnteredEventAdapter;
import org.jdesktop.lg3d.utils.shape.GlassyPanel;
import org.jdesktop.lg3d.utils.shape.ImagePanel;
import org.jdesktop.lg3d.utils.shape.Text2D;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Cursor3D;
import org.jdesktop.lg3d.wg.event.LgEventSource;

import org.jdesktop.lg3d.apps.archviz3d.geons.menu.GeonMenuCommand;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.Factory3D;



/**
 * Esta clase reresenta un boton del menu.
 *
 * @author Juan Feldman - Lucas Vigneau
 *
 */
public class GeonMenuButton extends Component3D {
	/** Contenedor de la imagen del boton.*/
	private Component3D buttonContainer = null;
	/** Imagen del boton.*/
	private ImagePanel buttonIP = null;
	/** Texto para el boton.*/
	private String imageName = null;
	/** Contenedor del texto para el boton.*/
	private Component3D text = null;
	/** Panel de fondo del boton.*/
	private GlassyPanel glassyPanel = null;
	/** Comando a ejecutar.*/
	private GeonMenuCommand command = null;

	/**
	 * Instancia esta clase.
	 * @param imageName Nombre de la imagen del boton.
	 * @param label Texto del boton. 
	 * @param position Posicion del boton.
	 */
	public GeonMenuButton(String imageName, String label, Point3f position) {
		this(imageName, label, position, 0.02f, 0.015f, 0.003f);
	}

	/**
	 * Instancia esta clase.
	 * @param imageName Nombre de la imagen del boton.
	 * @param label Texto del boton. 
	 * @param position Posicion del boton.
	 * @param width Ancho del boton.
	 * @param height Largo del boton.
	 * @param deep Profundidad del boton.
	 */
	public GeonMenuButton(String imageName, String label, Point3f position, float width, float height, float deep) {
		this.imageName = imageName;

		text = new Component3D();
		Text2D text2d = new Text2D(label, new Color3f(1f, 1f, 1f), new Font("Arial", Font.BOLD, 12));
		text.addChild(text2d);
		text.setVisible(false);
		text.setScale(0.1f);
		text.setTranslation(0, 0.005f, 0);

		buttonContainer = createIcon(width, height, deep);
		buttonContainer.addChild(text);
		this.addChild(buttonContainer);
		this.setTranslation(position.x, position.y, position.z);
		this.setAnimation(new NaturalMotionAnimation(3000));
		addMouseRolloverListener();
		addMouseClickedListener();
		setMouseEventPropagatable(true);
		setCursor(Cursor3D.SMALL_CURSOR);
	}

	/**
	 * Crea un boton, poniendo una imagen como textura y asignandole un tamaño.
	 * @param width Ancho del boton.
	 * @param height Largo del boton.
	 * @param deep Profundidad del boton.
	 * @return Component3D Contenedor de la imagen para el boton.
	 */
	private Component3D createIcon(float width, float height, float deep) {
		buttonContainer = new Component3D();

		Appearance appearance = Factory3D.instance().createAppearance(null);
		appearance.getMaterial().setSpecularColor(new Color3f(0f, 0.5f, 0f));
		appearance.getMaterial().setShininess(1.0f);
	    glassyPanel = new GlassyPanel(width, height, deep, appearance);
	    glassyPanel.setCapability(GlassyPanel.ALLOW_APPEARANCE_WRITE);
	    buttonContainer.addChild(glassyPanel);
	    
		try {
			buttonIP = new ImagePanel(getClass().getResource("/resources/images/menu/" + imageName), width - 0.005f, height - 0.002f);
			buttonContainer.addChild(buttonIP);
		}
		catch (Exception e) {
			System.err.println("GeonMenuButton createIcon(): " + e.toString());
		}
		return buttonContainer;
	}

	/**
	 * Agrego el comportamiento para que cuando me paro sobre el boton
	 * (state = true) aparezca el texto de ayuda, y cuando salgo del boton  
	 * (state = false) desaparezca.  
	 */
	private void addMouseRolloverListener() {
		addListener(new MouseEnteredEventAdapter(new ActionBoolean() {
			public void performAction(LgEventSource source, boolean state) {
				mouseRollover(state);
			}
		}));
	}
	
	/**
	 * Este metodo se llama cuando se hace un rollover del boton, o cuando
	 * se llama al metodo setEnabled con false. 
	 * @param state Estado del menu.
	 */
	private void mouseRollover(boolean state) {
		text.setVisible(state);
		if (state)
			buttonContainer.setScale(1.3f);
		else
			buttonContainer.setScale(1.0f);
	}
	
	/**
	 * Agrego el comportamiento para que cuando se clickea el boton
	 * se ilumine el boton.
	 */
	protected void addMouseClickedListener() {
//		addListener(new MouseClickedEventAdapter(new ActionFloat2() {
//			public void performAction(LgEventSource source, float x, float y) {
//				Thread thread = new Thread(new MyRunnable());
//				thread.start();
//			}
//		}));
	}
	
	/**
	 * Devuelve el panel de fondo del boton. 
	 * @return GlassyPanel Panel de fondo del boton.
	 */
	public GlassyPanel getGlassyPanel() {
		return glassyPanel;
	}
	
	/**
	 * Setea la habilitacion del boton.
	 * @param enabled True para habilitar, false para deshabilitar.
	 */
	public void setEnabled(boolean enabled) {
		// Habilito el boton
		if (enabled) 
			this.changeRotationAngle(0);
		// Deshabilito el boton (solo si no esta deshabilitado)
		else if (this.isMouseEventEnabled()) {
			this.changeRotationAngle(-(float)Math.PI/2);
			mouseRollover(false);		
		}
		this.setMouseEventEnabled(enabled);
	}
	
	/**
	 * Esta clase se encarga de iluminar un boton cuando 
	 * este es clickeado.
	 * 
	 * @author Juan Feldman
	 *
	 */
	public class MyRunnable implements Runnable {
		
		/**
		 * Ilumina un boton cuando es clickeado.
		 */
		public void run() {
			Color3f originalColor = new Color3f();
			Material material = glassyPanel.getAppearance().getMaterial();
			material.getSpecularColor(originalColor);
			
			// Cambio el color
			material.setSpecularColor(new Color3f(0.5f, 0.0f, 0.5f));
			material.setShininess(3.0f);
			try {
				Thread.sleep(500);
			} catch(Exception e) {
				e.printStackTrace();
			}

			// Vuelvo al color original
			material.setSpecularColor(originalColor);
		}
		
	}

	/**
	 * Setea el comando a ejecutar.
	 * @param command Comando a ejecutar.
	 */
	public void setCommand(GeonMenuCommand command) {
		this.command = command; 
	}
	
	/**
	 * Retorna el comando a ejecutar.
	 * @return GeonCommand Comando a ejecutar.
	 */
	public GeonMenuCommand getCommand() {
		return command; 
	}
	
	/**
	 * Ejecuta el comando.
	 */
	public void execute() {
		command.execute(); 
	}
	
}
