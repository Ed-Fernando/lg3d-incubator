package org.jdesktop.lg3d.apps.archviz3d.geons.frames;

import java.util.Enumeration;

import javax.vecmath.Color3f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.utils.action.ActionNoArg;
import org.jdesktop.lg3d.utils.c3danimation.NaturalMotionAnimation;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.utils.shape.Cylinder;
import org.jdesktop.lg3d.utils.shape.ImagePanel;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.wg.LayoutManager3D;
import org.jdesktop.lg3d.wg.event.LgEventSource;
import org.jdesktop.lg3d.wg.event.MouseEvent3D.ButtonId;

import org.jdesktop.lg3d.apps.archviz3d.geons.components.GeonWheel;
import org.jdesktop.lg3d.apps.archviz3d.geons.components.GeonWheelLayout;
import org.jdesktop.lg3d.apps.archviz3d.geons.components.GeonWheelLineLayout;
import org.jdesktop.lg3d.apps.archviz3d.geons.components.GeonWheelPanoramicLayout;
import org.jdesktop.lg3d.apps.archviz3d.geons.components.GeonWheelStackLayout;
import org.jdesktop.lg3d.apps.archviz3d.geons.frames.GeonFrame;



/**
 * Este frame se utiliza para seleccionar la estrategia de layout
 * de las tres vistas de una arquitectura. 
 * 
 * @author Juan Feldman
 *
 */
public class GeonFrameWheelLayoutSelection extends GeonFrame {
	/** Rueda a la cual se le cambiara el layout.*/
	private GeonWheel geonWheel = null;
	/** Boton clickeado.*/
	private Component3D clickedButtonOld = null;
	/** Constante utilizada para dar el efecto del click.*/
	private final float Z_VALUE = 0.006f;
	
	/**
	 * Instancia un Frame.
	 * @param geonWheel Rueda a la cual se le cambiara el layout.
	 * @param caller Llamador del Frame.
	 */
	public GeonFrameWheelLayoutSelection(GeonWheel geonWheel, Component3D caller) {
		super(0.06f, 0.02f, 0.004f, 0.005f, 0, new Point3f(0.0f, 0.0f, 0.0f), caller);
		setTitle("Layout Selection");
		this.geonWheel = geonWheel;
		createFrame();
	}

	/**
	 * Crea el Frame de seleccion de layouts.
	 */
	private void createFrame() {
		// Creo las imagenes de los botones
		ImagePanel image1 = null;
		ImagePanel image2 = null;
		ImagePanel image3 = null;
		ImagePanel image4 = null;
		try {
			image1 = new ImagePanel(getClass().getResource("/resources/images/frame/buttons/one.png"), 0.01f, 0.01f);
			image2 = new ImagePanel(getClass().getResource("/resources/images/frame/buttons/two.png"), 0.01f, 0.01f);
			image3 = new ImagePanel(getClass().getResource("/resources/images/frame/buttons/three.png"), 0.01f, 0.01f);
			image4 = new ImagePanel(getClass().getResource("/resources/images/frame/buttons/four.png"), 0.01f, 0.01f);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Boton para layout1
		Component3D button1 = createButton(image1, new Color3f(0.6f, 1.0f, 0.6f));
		button1.setTranslation(getInitialPosition().y + 0 * 0.014f + 0.01f, getInitialPosition().y - getUpperHeight() - 0.009f, getInitialPosition().z);
		button1.addListener(new MouseClickedEventAdapter(new ActionNoArg() {
			public void performAction(LgEventSource source) {
				doLayout(new GeonWheelLayout(geonWheel.getMajorAxis(), geonWheel.getMinorAxis()));
			}
		}));
		
		// Boton para layout2
		Component3D button2 = createButton(image2, new Color3f(1.0f, 0.6f, 0.6f));
		button2.setTranslation(getInitialPosition().y + 1 * 0.014f + 0.01f, getInitialPosition().y - getUpperHeight() - 0.009f, getInitialPosition().z + Z_VALUE);
		button2.addListener(new MouseClickedEventAdapter(new ActionNoArg() {
			public void performAction(LgEventSource source) {
				doLayout(new GeonWheelPanoramicLayout());
			}
		}));
		
		// Boton para layout3
		Component3D button3 = createButton(image3, new Color3f(1.0f, 1.0f, 0.6f));
		button3.setTranslation(getInitialPosition().y + 2 * 0.014f + 0.01f, getInitialPosition().y - getUpperHeight() - 0.009f, getInitialPosition().z + Z_VALUE);
		button3.addListener(new MouseClickedEventAdapter(new ActionNoArg() {
			public void performAction(LgEventSource source) {
				doLayout(new GeonWheelStackLayout());
			}
		}));
		
		// Boton para layout4
		Component3D button4 = createButton(image4, new Color3f(0.6f, 1.0f, 1.0f));
		button4.setTranslation(getInitialPosition().y + 3 * 0.014f + 0.01f, getInitialPosition().y - getUpperHeight() - 0.009f, getInitialPosition().z + Z_VALUE);
		button4.addListener(new MouseClickedEventAdapter(new ActionNoArg() {
			public void performAction(LgEventSource source) {
				doLayout(new GeonWheelLineLayout());
			}
		}));
		
		// Agrego los botones al frame
		getFrameContainer().addChild(button1);
		getFrameContainer().addChild(button2);
		getFrameContainer().addChild(button3);
		getFrameContainer().addChild(button4);

		// Salvo el boton clickeado
		clickedButtonOld = button1;
		
		// Ajusto los botones y traslado el frame
		setTranslation(0.05f, -0.07f, 0.0f);
	}
	
	/**
	 * Crea el boton. 
	 * @param image Imagen del boton. 
	 * @param color Color del boton.
	 * @param createCylinder True si se debe crear un cilindro 
	 * para el boton a crear, false en caso contrario. 
	 * @return Component3D Boton creado.
	 */
    private Component3D createButton(ImagePanel image, Color3f color){
        Component3D button = new Component3D();

        // Creo la imagen para el boton
        Component3D component = new Component3D();
        Cylinder primitive = new Cylinder(0.005f, 0.007f, new SimpleAppearance(color.x, color.y, color.z));
        component.setRotationAxis(1.0f, 0.0f, 0.0f);
        component.setRotationAngle((float)Math.PI/2.0f);
        component.addChild(primitive);
        button.addChild(component);

        // Agrego la imagen del boton 
        component = new Component3D();
        component.setTranslation(0.0f, 0.0f, 0.0036f);
        component.addChild(image);
        button.addChild(component);

        // Agrego el efecto para el click
        button.setAnimation(new NaturalMotionAnimation(1000));
        button.addListener(new MouseClickedEventAdapter(ButtonId.BUTTON1, new ActionNoArg() {
            public void performAction(LgEventSource source){
            	adjustButtons((Component3D)source);
            }
        }));

        return button;
    }
 
    /**
     * Ajusta los botones para que el boton clickeado se vea
     * diferente a los botones no clickeado.  
     * @param clickedButton Boton clickeado.
     */
    private void adjustButtons(Component3D clickedButton) {
    	Vector3f translation = new Vector3f();
    	clickedButton.getFinalTranslation(translation);
    	clickedButton.changeTranslation(translation.x, translation.y, translation.z - Z_VALUE);

    	translation = new Vector3f();
    	clickedButtonOld.getFinalTranslation(translation);
    	clickedButtonOld.changeTranslation(translation.x, translation.y, translation.z + Z_VALUE);
    	
    	clickedButtonOld = clickedButton; 
    }
    
    /**
     * Realiza el layout.
     */
    private void doLayout(LayoutManager3D layout) {
		if (layout != null) {
			Container3D wheel = geonWheel.getWheel();
			Enumeration enume = wheel.getAllChildren();
			wheel.removeAllChildren();
			wheel.setLayout(layout);
			while(enume.hasMoreElements())
				wheel.addChild((Component3D)enume.nextElement());
		}
    }
	
    /**
	 * Cierra el Frame.
	 */
	public void close() {
	}

}
