package org.jdesktop.lg3d.apps.archviz3d.geons.frames;

import java.awt.Font;

import javax.vecmath.Color3f;
import javax.vecmath.Point3f;

import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.utils.action.ActionNoArg;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.utils.shape.GlassyPanel;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.utils.shape.Text2D;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.event.LgEventSource;

import org.jdesktop.lg3d.apps.archviz3d.geons.components.GeonAnimation;
import org.jdesktop.lg3d.apps.archviz3d.geons.frames.GeonFrameButton;
import org.jdesktop.lg3d.apps.archviz3d.geons.frames.GeonFrameManager;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.Factory3D;


/**
 * Clase padre de todos las Frames.
 * 
 * @author Juan Feldman - Lucas Vigneau
 *
 */
public abstract class GeonFrame extends GeonAnimation {
	/** Contiene el width, height y depth que tiene el frame.**/
	private float width;
	private float height;
	private float depth;
	/** Contiene el la altura de cada subframe.**/
	private float upperHeight;
	private float centralHeight;
	private float bottomHeight;
	/** Contiene la posición original del frame.**/
	protected Point3f initialPosition;
	/** Contenedor del frame superior.**/
	private Component3D upperFrame = null;
	/** Contenedor del frame principal.**/
	private Component3D centralFrame = null;
	/** Contenedor del frame para la botonera.**/
	private Component3D bottomFrame = null;
	/** Contenedor de todos los subFrame.**/
	private Component3D frameContainer = null;
	/**Llamador de este frame.*/
	private Component3D caller = null;
	/** Contenedor del titulo.**/
	private Component3D titleFrame = null;
	/** Manager de Frames. */
	private GeonFrameManager geonFrameManager = null; 
	/** Contiene la separationm entre los subframes.**/
	public static final float SUBFRAME_SEPARATION = 0.0015f;
	
	/**
	 * Instancia un Frame.
	 * @param width Ancho del Frame.
	 * @param height Largo del Frame.
	 * @param depth Profundidad del Frame.
	 * @param caller Llamador del Frame.
	 */
	public GeonFrame(float width, float height, float depth, Component3D caller) {
		this(width, height, depth, new Point3f(0.0f, 0.0f, 0.0f), caller);
	}

	/**
	 * Instancia un Frame.
	 * @param width Ancho del Frame.
	 * @param height Largo del Frame.
	 * @param depth Profundidad del Frame.
	 * @param initialPosition Posicion initial del Frame.
	 * @param caller Llamador del Frame.
	 */
	public GeonFrame(float width, float height, float depth, Point3f initialPosition, Component3D caller) {
		this(width, height, depth, 0.007f, 0.011f, initialPosition, caller);
	}

	/**
	 * Instancia un Frame.
	 * @param width Ancho del Frame.
	 * @param height Largo del Frame.
	 * @param depth Profundidad del Frame.
	 * @param upperHeight Largo del Frame superior.
	 * @param bottomHeight Largo del Frame inferior.
	 * @param initialPosition Posicion initial del Frame.
	 * @param caller Llamador del Frame.
	 */
	public GeonFrame(float width, float height, float depth, float upperHeight, float bottomHeight, Point3f initialPosition, Component3D caller) {
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.upperHeight = upperHeight; 
        this.bottomHeight = bottomHeight; 
        this.initialPosition = initialPosition;
        this.caller = caller;
        this.setCentralHeight();
		upperFrame = new Component3D();
        centralFrame = new Component3D();
        bottomFrame = new Component3D();
		frameContainer = createFrame();
		this.setComponent(frameContainer);
		Factory3D.instance().addListenerTranslation(this);
	}
	
	/**
	 * Crea un un frame.
	 * @return Component3D Contenedor del frame.
	 */
	private Component3D createFrame() {
		Component3D container = new Component3D();

		GlassyPanel glassyPanelUpper = null;
		GlassyPanel glassyPanelCentral = null;
		GlassyPanel glassyPanelBottom = null;
		GeonFrameButton cancelButton = null; 
			
		Appearance app = new SimpleAppearance(0f, 0.5f, 0f, 1.0f, SimpleAppearance.DISABLE_CULLING);
	    
		if (getUpperHeight() > 0) {
			glassyPanelUpper = new GlassyPanel(width, getUpperHeight(), depth, app);
			glassyPanelUpper.setCapability(GlassyPanel.ALLOW_APPEARANCE_WRITE);
	        upperFrame.addChild(glassyPanelUpper);
	        upperFrame.setTranslation(initialPosition.x + width/2f , initialPosition.y - getUpperHeight()/2f, initialPosition.z);
			container.addChild(upperFrame);
		}
        
        glassyPanelCentral = new GlassyPanel(width, getCentralHeight(), depth, app);
		glassyPanelCentral.setCapability(GlassyPanel.ALLOW_APPEARANCE_WRITE);
        centralFrame.addChild(glassyPanelCentral);
        centralFrame.setTranslation(initialPosition.x + width/2f, initialPosition.y - getUpperHeight() - getCentralHeight()/2f - SUBFRAME_SEPARATION, initialPosition.z);
        container.addChild(centralFrame);
        
		if (getBottomHeight() > 0) {
			glassyPanelBottom = new GlassyPanel(width, getBottomHeight(), depth, app);
			glassyPanelBottom.setCapability(GlassyPanel.ALLOW_APPEARANCE_WRITE);
	        bottomFrame.addChild(glassyPanelBottom);
	        bottomFrame.setTranslation(initialPosition.x + width/2f, initialPosition.y - getUpperHeight() - getCentralHeight() - getBottomHeight()/2f - 2 * SUBFRAME_SEPARATION, initialPosition.z);
	        container.addChild(bottomFrame);

			// Boton Cancelar
	        cancelButton = new GeonFrameButton("buttons/cancel.png", new Point3f(initialPosition.x + 0.9f * width - 0.003f, initialPosition.y - getUpperHeight() - getCentralHeight() - getBottomHeight()/2f - 2 * SUBFRAME_SEPARATION, initialPosition.z + 0.001f), width/5f, getBottomHeight() - 0.004f);
	        cancelButton.addListener(new MouseClickedEventAdapter(new ActionNoArg() {
				public void performAction(LgEventSource source) {
					close();
				}
			}
			));
	        container.addChild(cancelButton);
		}
        
        titleFrame = new Component3D();
		titleFrame.setScale(0.05f);
		titleFrame.setTranslation(getInitialPosition().x + 0.006f, getInitialPosition().y - getUpperHeight(), getInitialPosition().z + 0.003f);
		container.addChild(titleFrame);
        
        return container;
	}

	/** 
	 * Retorna el contenedor que contiene los componentes del frame.
	 * @return Component3D Contenedor del frame. 
	 */
    public Component3D getFrameContainer(){
	   return frameContainer;	
	}

	/** 
	 * Retorna el ancho del frame.
	 * @return float Ancho del frame.
	 */
    public float getWidth(){
	   return width;	
	}

	/** 
	 * Retorna la altura del frame. 
	 * @return float Altura del frame.
	 */
    public float getHeight(){
	   return height;	
	}

	/** 
	 * Retorna la altura del subframe superior.
	 * @return float Altura del subframe superior.
	 */
    public float getUpperHeight(){
	   return upperHeight;	
	}

	/** 
	 * Retorna la altura del subframe central.
	 * @return float Altura del subframe central.
	 */
    public float getCentralHeight(){
	   return centralHeight;	
	}

	/** 
	 * Retorna la altura del subframe inferior. 
	 * @return float Altura del subframe inferior.
	 */
    public float getBottomHeight(){
	   return bottomHeight;	
	}

	/** 
	 * Setea la altura del central frame. 
	 */
    private void setCentralHeight(){
	   this.centralHeight = height - upperHeight - bottomHeight;	
	}

	/** 
	 * Retorna la profundidad del frame. 
	 * @return float Profundidad del frame.
	 */
    public float getDepth(){
	   return depth;	
	}

    /** 
     * Retorna la posicion initial del frame 
	 * @return float Profundidad del frame.
     */
    public Point3f getInitialPosition(){
	   return initialPosition;	
	}

	/** 
	 * Setea la posicion initial del frame.
	 * @param initialPosition Posicion initial del frame.
	 */
    public void setinitialPosition(Point3f initialPosition){
    	this.initialPosition = initialPosition; 	
 	}
    
    /**
     * Retorna el llamador de este Frame.
     * @return Component3D Llamador de este frame.
     */
    public Component3D getCaller(){
	   return caller;	
	}

	/**
	 * Setea el llamador de este frame.
	 * @param caller Llamador de este frame.
	 */
    public void setCaller(Component3D caller){
    	this.caller = caller; 	
 	}
    
    /**
     * Setea el titulo del frame.
     * @param title Titulo a setear. 
     */
    public  void setTitle(String title){
    	titleFrame.removeAllChildren();
    	Text2D title2D = new Text2D(title, new Color3f(1f, 1f, 1f), new Font("Arial", Font.BOLD, 20));
		titleFrame.addChild(title2D);
    }

    /**
     * Setea el titulo del frame.
     * @param title Titulo a setear. 
     */
    public  void setTitle(String title, Font font){
    	titleFrame.removeAllChildren();
    	Text2D title2D = new Text2D(title, new Color3f(1f, 1f, 1f), font);
		titleFrame.addChild(title2D);
    }

    /**
     * Retorna el titulo del Frame.
     * @return String Titulo de este frame.
     */
    public String getTitle(){
    	return  ((Text2D)titleFrame.getChild(0)).getString();
	}
    
	/**
	 * Cierra el frame.
	 */
	public abstract void close();
	
	/**
	 * Setea el manager de frames.
	 * @param geonFrameManager Manager de frames.
	 */
	public void setGeonFrameManager(GeonFrameManager geonFrameManager) {
		this.geonFrameManager = geonFrameManager; 
	}
	
	/**
	 * Retorna el manager de frames.
	 * @return GeonFrameManager Manager de frames.
	 */
	public GeonFrameManager getGeonFrameManager() {
		return geonFrameManager; 
	}
	
}
