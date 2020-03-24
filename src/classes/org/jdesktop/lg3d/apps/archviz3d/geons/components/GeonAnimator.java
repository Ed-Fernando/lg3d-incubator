package org.jdesktop.lg3d.apps.archviz3d.geons.components;

import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.utils.animation.TranslationAnimationBoolean;
import org.jdesktop.lg3d.wg.AnimationGroup;
import org.jdesktop.lg3d.wg.event.LgEvent;

import org.jdesktop.lg3d.apps.archviz3d.geons.components.GeonAnimation;
import org.jdesktop.lg3d.apps.archviz3d.geons.components.GeonAnimator;
import org.jdesktop.lg3d.apps.archviz3d.geons.menu.GeonMenu;


/**
 * Esta clase se encarga de correr los frames o las vistas de las 
 * arquitecturas de la aplicacion cuando se abre o cierra un frame nuevo.
 * 
 * @author Juan Feldman
 *
 */
public class GeonAnimator {
	/** Logger de mensajes.*/
	private static Logger logger = Logger.getLogger("lg.ArchViz3D");	

	/** Unica instancia de esta clase.*/
	private static GeonAnimator geonAnimator = null; 
	
	/** Menu de la aplicacion.*/
	private GeonMenu geonMenu = null; 
	
	/** Componentes que fueron corridos.*/
	private Vector<GeonAnimation> components = null; 
	
	/** Posicion original de los componentes que fueron corridos.*/
	private Vector<Vector3f> originalPositions = null; 
	/** Escala original de los componentes que fueron corridos.*/
	private Vector<Float> originalScales = null; 
	/** Angulo de Rotacion original de los componentes que fueron corridos.*/
	private Vector<Float> originalRotationAngles = null; 
	/** Posicion en el eje X del proximo componente a correr.*/
	private float xPosition;
	
	/** Separacion entre los componentes corridos.*/
	private static final float SEPARATION = 0.2f; 
	/** Duracion de la animacion en milisegundos.*/
	public static final int ANIMATION_DURATION = 2000; 
	
	/**
	 * Instancia el GeonAnimator.
	 */
	private GeonAnimator() {
		components = new Vector<GeonAnimation>(); 
		originalPositions = new Vector<Vector3f>();
		originalScales = new Vector<Float>();
		originalRotationAngles = new Vector<Float>();
		xPosition = 0.5f;
	}
	
	/**
	 * Retorna la unica instancia de GeonAnimator.
	 */
    public static GeonAnimator instance() {
        if (geonAnimator == null)
        	geonAnimator = new GeonAnimator();
        return geonAnimator;
    }
    
    /**
     * Corre el componente hide, que se recibe como parametro..
     * 
     * @param hide Componente a correr.
     * @param show Componente a mostrar. Este parametro se necesita
     * para evitar que el usuario utilice este componente antes de que 
     * termine la animacion. 
     */
    public void push(final GeonAnimation hide, final GeonAnimation show) {
		show.setMouseEventEnabled(false);
		show.setKeyEventSource(false);
		show.changeVisible(false);
		hide.setMouseEventEnabled(false);
    	hide.setKeyEventSource(false);
		geonMenu.setMouseEventEnabled(false);
    	
    	// Salvo el componente
    	components.add(hide);
    	
    	// Salvo la posicion original del componente
    	Vector3f originalPosition = new Vector3f(); 
    	hide.getTranslation(originalPosition);
    	originalPositions.add(originalPosition);

    	// Salvo la escala original del componente
    	originalScales.add(new Float(hide.getScale()));
    	
    	// Salvo el angulo de rotacion original del componente
    	originalRotationAngles.add(new Float(hide.getRotationAngle()));
    	
    	// Normalizo el componente
    	hide.setTranslation(0, 0, 0);
    	hide.setScale(0.2f);
    	hide.setRotationAngle(0f);
    	    	
    	// Corro el componente
    	TranslationAnimationBoolean translationAnimation =
    		new TranslationAnimationBoolean(originalPosition,
    				new Vector3f(xPosition, -0.4f, 0f),
    				ANIMATION_DURATION);
    	AnimationGroup animationGroup = hide.getAnimationGroup();
    	animationGroup.setAnimation(translationAnimation);
// NO USO EL EVENTO DE LG3D PORQUE TIENE UN BUG CUANDO SE EJECUTA VARIAS VECES     	
//        LgEventConnector.getLgEventConnector().addListener(translationAnimation,
//                new GenericEventAdapter(LgEvent.class, new ActionNoArg() {
//					public void performAction(LgEventSource source) {
//						show.setMouseEventEnabled(true);
//						show.setKeyEventSource(true);
//						show.changeVisible(true);
//					}
//                })
//        );
    	translationAnimation.setAnimationFinishedEvent(LgEvent.class);
    	translationAnimation.performAction(null, true);
    	
    	// Thread para el final de la animacion
    	Thread thread = new ThreadPush(show, ANIMATION_DURATION); 
    	thread.start();
    	
    	xPosition = xPosition - SEPARATION;
    }

    /**
     * Vuelve a la posicion original al ultimo componente que
     * fue corrido.
     */
    public void pull() {
    	if (components.size() > 0) {
    		geonMenu.setMouseEventEnabled(false);

    		// Obtengo el componente a ubicar en su posicion original
	    	final GeonAnimation comp = components.lastElement();
	    	
    		// Obtengo la posicion original del componente
	    	Vector3f originalPosition = originalPositions.lastElement(); 
	
    		// Obtengo la escala original del componente
	    	final Float originalScale = originalScales.lastElement();
	    	
	    	// Obtengo el angulo de rotacion original del componente
	    	final Float originalRotationAngle = originalRotationAngles.lastElement();
	    	
    		// Escalo el componente
	    	comp.setScale(originalScale.floatValue());
	    	
    		// Rot el componente
	    	comp.setRotationAngle(originalRotationAngle.floatValue());
	    	
	    	// Corro el componente
	    	xPosition = xPosition + SEPARATION;
	    	TranslationAnimationBoolean translationAnimation =
	    		new TranslationAnimationBoolean(new Vector3f(xPosition, -0.4f, 0f),
	    				new Vector3f(0f, 0f, 0f),
	    				ANIMATION_DURATION);
	    	AnimationGroup animationGroup = comp.getAnimationGroup();
	    	animationGroup.setAnimation(translationAnimation);
//	    	 NO USO EL EVENTO DE LG3D PORQUE TIENE UN BUG CUANDO SE EJECUTA VARIAS VECES     	
//	        LgEventConnector.getLgEventConnector().addListener(translationAnimation,
//	                new GenericEventAdapter(LgEvent.class, new ActionNoArg() {
//						public void performAction(LgEventSource source) {
//							comp.setMouseEventEnabled(true);
//							comp.setKeyEventSource(true);
//						}
//	                })
//	        );
	    	translationAnimation.setAnimationFinishedEvent(LgEvent.class);
	    	translationAnimation.performAction(null, true);
	    	
	    	// Thread para el final de la animacion
	    	Thread thread = new ThreadPull(comp, ANIMATION_DURATION); 
	    	thread.start();

	    	comp.setTranslation(originalPosition.x, originalPosition.y, originalPosition.z);
	    	
	    	// Elimino los datos del componente corrido
	    	components.removeElement(comp);
	    	originalPositions.removeElement(originalPosition);
	    	originalScales.removeElement(originalScale);
	    	originalRotationAngles.removeElement(originalRotationAngle);
    	}
    }
    
    /**
     * Vuelve a la posicion original a todos los componentes que
     * fueron corridos.
     */
    public void pullAll() {
    	int size = components.size();
    	for (int i = 0; i < size; i++) 
    		pull();
    }

    /**
     * Setea el menu de la aplicacion
     * @param geonMenu menu de la aplicacion
     */
    public void setGeonMenu(GeonMenu geonMenu) {
    	this.geonMenu = geonMenu; 
    }
    
    /**
     * Esta clase se encarga de realizar las acciones necesarias
     * cuando termina la animacion para el Push.
     * 
     * @author Juan Feldman - Lucas Vigneau
     *
     */
    public class ThreadPush extends Thread {
    	/** Contador para esperar la finalizacion de la animacion.*/
		private int time = 0;
    	/** Componente a mostrar.*/
		private GeonAnimation geonAnimation;
		
		/**
		 * Instancia el ThreadPush.
		 * @param geonAnimation Componente a mostrar.
		 * @param time Tiempo para esperar la finalizacion de la animacion. 
		 */
		public ThreadPush(GeonAnimation geonAnimation, int time) {
			this.geonAnimation = geonAnimation;
			this.time = time;
		}
		
		/**
		 * Ejeucta este thread.
		 */
		public void run() {
			int cont = (int)time/10;
			while (cont > 0) {  
				cont--;
				try {
					Thread.sleep(10);
				}catch(Exception e) {}
			}

			geonAnimation.setMouseEventEnabled(true);
			geonAnimation.setKeyEventSource(true);
			geonAnimation.changeVisible(true);
			geonMenu.setMouseEventEnabled(true);
			logger.log(Level.INFO, "THREAD PUSH - Showing frame \"" + geonAnimation.getClass().getName() + "\"");
		} 
    }

    /**
     * Esta clase se encarga de realizar las acciones necesarias
     * cuando termina la animacion para el Pull.
     * 
     * @author Juan Feldman - Lucas Vigneau
     *
     */
    public class ThreadPull extends Thread {
    	/** Contador para esperar la finalizacion de la animacion.*/
		private int time = 0;
    	/** Componente a mostrar.*/
		private GeonAnimation geonAnimation;
		
		/**
		 * Instancia el ThreadPull.
		 * @param geonAnimation Componente a mostrar.
		 * @param time Tiempo para esperar la finalizacion de la animacion. 
		 */
		public ThreadPull(GeonAnimation geonAnimation, int time) {
			this.geonAnimation = geonAnimation;
			this.time = time;
		}
		
		/**
		 * Ejeucta este thread.
		 */
		public void run() {
			int cont = (int)time/10;
			while (cont > 0) {  
				cont--;
				try {
					Thread.sleep(10);
				}catch(Exception e) {}
			}

			geonAnimation.setMouseEventEnabled(true);
			geonAnimation.setKeyEventSource(true);
			geonMenu.setMouseEventEnabled(true);
			logger.log(Level.INFO, "THREAD PULL - Showing frame \"" + geonAnimation.getClass().getName() + "\"");
		} 
    }
    
}
