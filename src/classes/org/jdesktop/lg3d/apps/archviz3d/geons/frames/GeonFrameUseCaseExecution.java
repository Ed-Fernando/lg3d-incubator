package org.jdesktop.lg3d.apps.archviz3d.geons.frames;

import javax.vecmath.Color3f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.utils.action.ActionBoolean;
import org.jdesktop.lg3d.utils.action.ActionNoArg;
import org.jdesktop.lg3d.utils.c3danimation.NaturalMotionAnimation;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MousePressedEventAdapter;
import org.jdesktop.lg3d.utils.shape.Box;
import org.jdesktop.lg3d.utils.shape.Cylinder;
import org.jdesktop.lg3d.utils.shape.ImagePanel;
import org.jdesktop.lg3d.utils.shape.Primitive;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.event.LgEventSource;
import org.jdesktop.lg3d.wg.event.MouseEvent3D.ButtonId;

import org.jdesktop.lg3d.apps.archviz3d.geons.frames.GeonFrame;
import org.jdesktop.lg3d.apps.archviz3d.geons.usecase.GeonUseCaseManager;


/**
 * Esta clase contiene los controles (play, pause, stop) para poder 
 * ejecutar un caso de uso.
 * 
 * @author Juan Feldman
 *
 */
public class GeonFrameUseCaseExecution extends GeonFrame {
	/** Encargado de ejecutar el caso de uso.*/
	private GeonUseCaseManager geonUseCaseManager = null;
	/** Caso de uso seleccionado.*/
	private String useCaseName = null;

	/**
	 * Instancia el Frame.
	 * 
	 * @param width Ancho del Frame.
	 * @param height Largo del Frame.
	 * @param depth Profundidad del Frame.
	 * @param initialPosition Posicion initial del Frame.
	 * @param geonUseCaseManager Encargado de ejecutar el caso de uso.  
	 * @param useCaseName Nombre de caso de uso a ejecutar.
	 * @param caller Llamador del Frame.
	 */
	public GeonFrameUseCaseExecution(float width, float height, float depth, Point3f initialPosition, GeonUseCaseManager geonUseCaseManager , String useCaseName, Component3D caller) {
        super(width, height, depth, initialPosition, caller);
        this.geonUseCaseManager = geonUseCaseManager; 
        this.useCaseName = useCaseName; 
        createFrame();
		setTitle(useCaseName);
	}
    
	/**
	 * Crea el Frame.
	 */
	private void createFrame() {
		// Creo los botones del Frame
		ImagePanel playFront = null;
		ImagePanel stopFront = null;
		ImagePanel pauseFront = null;
		ImagePanel moreFront = null;
		ImagePanel lessFront = null;
		try {
			playFront = new ImagePanel(getClass().getResource("/resources/images/frame/buttons/play.png"), 0.01f, 0.01f);
			stopFront = new ImagePanel(getClass().getResource("/resources/images/frame/buttons/stop.png"), 0.01f, 0.01f);
			pauseFront = new ImagePanel(getClass().getResource("/resources/images/frame/buttons/pause.png"), 0.01f, 0.01f);
			moreFront = new ImagePanel(getClass().getResource("/resources/images/frame/buttons/up.png"), 0.005f, 0.005f);
			lessFront = new ImagePanel(getClass().getResource("/resources/images/frame/buttons/down.png"), 0.005f, 0.005f);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Boton Play
		Component3D buttonPlay = createButton(playFront, new Color3f(0.6f, 1.0f, 0.6f), true); 
        buttonPlay.addListener(new MouseClickedEventAdapter(new ActionNoArg() {
			public void performAction(LgEventSource source) {
				buttonPlay_click();
			}
		}
		));
        buttonPlay.setTranslation(getInitialPosition().x + 0.1f*getWidth() + 0.002f, getInitialPosition().y - getUpperHeight() - 0.007f, getInitialPosition().z + getDepth());

		// Boton Stop
        Component3D buttonStop = createButton(stopFront, new Color3f(1.0f, 0.6f, 0.6f), true); 
        buttonStop.addListener(new MouseClickedEventAdapter(new ActionNoArg() {
			public void performAction(LgEventSource source) {
				buttonStop_click();
			}
		}
		));
        buttonStop.setTranslation(getInitialPosition().x + 0.1f*getWidth() + 0.022f, getInitialPosition().y - getUpperHeight() - 0.007f, getInitialPosition().z + getDepth());

		// Boton Pause
        Component3D buttonPause = createButton(pauseFront, new Color3f(1.0f, 1.0f, 0.6f), true); 
        buttonPause.addListener(new MouseClickedEventAdapter(new ActionNoArg() {
			public void performAction(LgEventSource source) {
				buttonPause_click();
			}
		}
		));
        buttonPause.setTranslation(getInitialPosition().x + 0.1f*getWidth() + 0.042f, getInitialPosition().y - getUpperHeight() - 0.007f, getInitialPosition().z + getDepth());
        
		// Boton More
		Component3D buttonMore = createButton(moreFront, new Color3f(0.6f, 1.0f, 1.0f), false); 
        buttonMore.addListener(new MouseClickedEventAdapter(new ActionNoArg() {
			public void performAction(LgEventSource source) {
				buttonMore_click();
			}
		}
		));
        buttonMore.setTranslation(getInitialPosition().x + 0.1f*getWidth() + 0.062f, getInitialPosition().y - getUpperHeight() - 0.003f, getInitialPosition().z + getDepth());
        
		// Boton Less
		Component3D buttonLess = createButton(lessFront, new Color3f(0.6f, 1.0f, 1.0f), false); 
        buttonLess.addListener(new MouseClickedEventAdapter(new ActionNoArg() {
			public void performAction(LgEventSource source) {
				buttonLess_click();
			}
		}
		));
        buttonLess.setTranslation(getInitialPosition().x + 0.1f*getWidth() + 0.062f, getInitialPosition().y - getUpperHeight() - 0.01f, getInitialPosition().z + getDepth());

        getFrameContainer().addChild(buttonPlay);
        getFrameContainer().addChild(buttonStop);
        getFrameContainer().addChild(buttonPause);
        getFrameContainer().addChild(buttonMore);
        getFrameContainer().addChild(buttonLess);
	}
	
	/**
	 * Crea el boton. 
	 * @param image Imagen del boton. 
	 * @param color Color del boton.
	 * @param createCylinder True si se debe crear un cilindro 
	 * para el boton a crear, false en caso contrario. 
	 * @return Component3D Boton creado.
	 */
    private Component3D createButton(ImagePanel image, Color3f color, boolean createCylinder){
        Component3D button = new Component3D();

        // Creo la imagen para el boton
        Primitive primitive;
        Component3D component = new Component3D();
        if (createCylinder) {
        	primitive = new Cylinder(0.005f, 0.007f, new SimpleAppearance(color.x, color.y, color.z));
	        component.setRotationAxis(1.0f, 0.0f, 0.0f);
	        component.setRotationAngle((float)Math.PI/2.0f);
        }
        else
        	primitive = new Box(0.005f, 0.003f, 0.0035f, new SimpleAppearance(color.x, color.y, color.z));
        component.addChild(primitive);
        button.addChild(component);

        // Agrego la imagen del boton 
        component = new Component3D();
        component.setTranslation(0.0f, 0.0f, 0.0036f);
        component.addChild(image);
        button.addChild(component);

        // Agrego el efecto para el click
        button.setAnimation(new NaturalMotionAnimation(1000));
        button.addListener(new MousePressedEventAdapter(ButtonId.BUTTON1, new ActionBoolean() {
            public void performAction(LgEventSource source, boolean b){
            	Component3D component = (Component3D)source;
            	Vector3f translation = new Vector3f();
                component.getFinalTranslation(translation);
                
            	if (b) 
                    component.changeTranslation(translation.x, translation.y, translation.z - 0.006f);
                else 
                    component.changeTranslation(translation.x, translation.y, translation.z + 0.006f);
            }
        }));

        return button;
    }

	/**
	 * Cierra el Frame y frena la ejecucion.
	 */
	public void close() {
	    this.detach();
	    buttonStop_click(); 
	    getGeonFrameManager().frameClosed(this);
	}

	/** 
	 * Se ejecuta el caso de uso. 
	 */
	public void buttonPlay_click() {
		geonUseCaseManager.executeUseCase(useCaseName);
	}
	
	/** 
	 * Se termina el caso de uso. 
	 */
	public void buttonStop_click() {
		geonUseCaseManager.stopUseCase();
	}
	
	/** 
	 * Se frena el caso de uso. 
	 */
	public void buttonPause_click() {
		geonUseCaseManager.pauseUseCase();
	}
	
	/** 
	 * Se aumenta la velocidad de la animacion. 
	 */
	public void buttonMore_click() {
		geonUseCaseManager.incrementAnimationSpeed();
	}
	
	/** 
	 * Se disminuye la velocidad de la animacion. 
	 */
	public void buttonLess_click() {
		geonUseCaseManager.decrementAnimationSpeed();
	}
	
}
