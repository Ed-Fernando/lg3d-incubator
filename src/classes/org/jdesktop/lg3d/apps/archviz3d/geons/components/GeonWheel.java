package org.jdesktop.lg3d.apps.archviz3d.geons.components;

import java.util.Enumeration;

import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.utils.action.ActionNoArg;
import org.jdesktop.lg3d.utils.c3danimation.NaturalMotionAnimation;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.wg.LayoutManager3D;
import org.jdesktop.lg3d.wg.Toolkit3D;
import org.jdesktop.lg3d.wg.event.LgEventSource;
import org.jdesktop.lg3d.wg.event.MouseEvent3D.ButtonId;

import org.jdesktop.lg3d.apps.archviz3d.abstractors.Abstractor;
import org.jdesktop.lg3d.apps.archviz3d.geons.builders.GeonBuilderViews;
import org.jdesktop.lg3d.apps.archviz3d.geons.components.GeonWheelLayout;
import org.jdesktop.lg3d.apps.archviz3d.geons.frames.GeonFrame;
import org.jdesktop.lg3d.apps.archviz3d.geons.menu.GeonMenu;
import org.jdesktop.lg3d.apps.archviz3d.geons.menu.GeonMenuStateViewVisible;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.Factory3D;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DCompositeComponent;


/**
 * Esta clase se encarga del comportamiento de la rueda
 * para las tres vistas de una arquitectura.
 * 
 * @author Juan Feldman
 *
 */
public class GeonWheel extends Component3D {
	/** Valor de la separacion entre visats.*/
	public static final float UNIT_TRANS_FACTOR = 0.0254f / 72.0f;
	/** Para calcular el tamaño de la rueda.*/
	private static final float THUMBNAIL_SIZE = 64.0f * UNIT_TRANS_FACTOR;
	/** Para calcular el tamaño de la rueda.*/
	private static final float DECO_SIZE = 5.0f * UNIT_TRANS_FACTOR;
	/** Rueda que contiene las tres vista de una arquitectura.*/
	private Container3D wheel = null;
	/** Eje principal.*/
	private float majorAxis;  
	/** Eje secudnario.*/
	private float minorAxis;
	/** Menu de la aplicacion.*/
	private GeonMenu geonMenu = null;
	/** Frame de seleccion de layouts.*/
	private GeonFrame geonFrameLayoutSelection = null;
	
	/**
	 * Instancia GeonWheel.
	 */
	public GeonWheel(GeonMenu geonMenu) {
		float height = Toolkit3D.getToolkit3D().getScreenHeight() * 0.8f;
		majorAxis = height / 1.5f;  
		minorAxis = majorAxis / 2.0f;
		this.geonMenu = geonMenu;

		// Creo el layout para la rueda
		wheel = new Container3D();
		wheel.setCapability(Container3D.ALLOW_CHILDREN_WRITE);
		wheel.setCapability(Container3D.ALLOW_CHILDREN_READ);
		wheel.setCapability(Container3D.ALLOW_CHILDREN_EXTEND);
		LayoutManager3D layout = new GeonWheelLayout(majorAxis, minorAxis);
		wheel.setLayout(layout);

		// Agrego la rueda al grafo
		super.addChild(wheel);

		// Seteo el tamaño de la rueda
		setPreferredSize(new Vector3f(THUMBNAIL_SIZE + DECO_SIZE * 2, majorAxis * 2, minorAxis * 2));
	}

	/**
	 * Se acomodan los componentes de la rueda. 
	 * @param selectedComp Componente seleccionado.
	 */
	private void rearrange(Component3D selectedComp) {
		wheel.rearrangeChildLayout(selectedComp, null);
	}

	/**
	 * Se acomodan los componentes de la rueda. 
	 * @param angle Angulo del componente seleccionado.
	 */
//	private void rearrange(int angle) {
//		wheel.rearrangeChildLayout((Component3D)wheel.getChild(0), Integer.valueOf(angle));
//	}

	/**
	 * Se agrega el componente a la rueda.
	 * @param comp Componente a agregar.
	 */
	public void addChild(Component3D comp) {
		comp.setAnimation(new NaturalMotionAnimation(2000));
		wheel.addChild(comp);

		// Agrego el listener para el zoom
		Factory3D.instance().addListenerZoom(comp);

		// Agrego el listener para la traslacion
		Factory3D.instance().addListenerTranslation(comp);

		// Agrego el listener para la rotacion
		Factory3D.instance().addListenerRotation(comp);
		
		// Agrego el listener del click izquierdo (gira la rueda y setea visibilidad)
		comp.addListener(new MouseClickedEventAdapter(ButtonId.BUTTON3, new ActionNoArg() {
			public void performAction(LgEventSource source) {
				if (source != null) {
					for (Enumeration enume = wheel.getAllChildren(); enume.hasMoreElements();) {
						Component3D component = (Component3D)enume.nextElement();
						if (!component.equals(source))   
							component.setVisible(!component.isVisible());
					}
					rearrange((Component3D)source);
					
					// Analizo si debo mostrar el frame de seleccion de layouts
					geonFrameLayoutSelection.setVisible(getNumberViewsVisible() == GeonBuilderViews.VIEWS_NUMBER);

			    	// Actualizo el menu
			    	geonMenu.changeState(new GeonMenuStateViewVisible(geonMenu));
				}
			}
		}));

		// Agrego el listener del click derecho (gira la rueda)
		comp.addListener(new MouseClickedEventAdapter(new ActionNoArg() {
			public void performAction(LgEventSource source) {
				if (source != null) 
					rearrange((Component3D)source);
			}
		}));

//		NOTA: Saco el listener de la rueda del mouse pq los otros layouts
//		no hacen nada con este listener, de esta forma todos quedan iguales.
//		Ademas la rueda del mouse se utiliza para el Zoom.		
//		// Agrego el listener de la rueda del mouse (gira la rueda)
//		comp.addListener(new MouseWheelEventAdapter(new ActionInt() {
//			public void performAction(LgEventSource source, int value) {
//				if (source != null) {
//					rearrange(value);
//				}
//			}
//		}));
	}
	
	/**
	 * Retorna la cantidad de vistas que hay visibles.
	 * @return int Cantidad de vistas que hay visibles.
	 */
	public int getNumberViewsVisible() {
		Enumeration enume = wheel.getAllChildren();
		int number = 0;
		while (enume.hasMoreElements()) {
			Component3D component = (Component3D)enume.nextElement();
			if (component.isVisible())
				number++;
		}
		return number;
	}

	/**
	 * Retorna el contenedor de la vista deseada.
	 * @param viewName Nombre de la vista deseada.
	 * @return Component3D Container de la vista deseada.
	 */
	public Component3D getViewContainer(String viewName) {
		Enumeration enume = wheel.getAllChildren();
		while (enume.hasMoreElements()) {
			Component3D component = (Component3D)enume.nextElement();
			if (viewName.equals(component.getName()))
				return component; 
		}
		return null;
	}
	
	/**
	 * Aumenta el nivel de abstraccion de cada una de las vistas.
	 */
	public void increaseAbstractionLevel() {
		Enumeration enume = wheel.getAllChildren();
		while (enume.hasMoreElements()) {
			J3DCompositeComponent view = (J3DCompositeComponent)((Component3D)enume.nextElement()).getChild(0);
			((Abstractor)view.getSubject()).increaseAbstractionLevel();
		}
	}

	/**
	 * Disminuye el nivel de abstraccion de cada una de las vistas.
	 */
	public void decreaseAbstractionLevel() {
		Enumeration enume = wheel.getAllChildren();
		while (enume.hasMoreElements()) {
			J3DCompositeComponent view = (J3DCompositeComponent)((Component3D)enume.nextElement()).getChild(0);
			((Abstractor)view.getSubject()).decreaseAbstractionLevel();
		}
	}

	/**
	 * Agrego el frame de seleccion de layouts.
	 * @param geonFrame Frame de seleccion de layouts.
	 */
	public void addFrame(GeonFrame geonFrame) {
		super.addChild(geonFrame);
		geonFrameLayoutSelection = geonFrame; 
	}
	
	/**
	 * Retorna el contenedor de la rueda.
	 * @return Container3D Contenedor de la rueda.
	 */
	public Container3D getWheel() {
		return wheel;
	}
	
	/**
	 * Retorna el valor del eje principal. 
	 * @return float Valor del eje principal.
	 */
	public float getMajorAxis() {
		return majorAxis;
	}
	
	/**
	 * Retorna el valor del eje secundario. 
	 * @return float Valor del eje secundario.
	 */
	public float getMinorAxis() {
		return minorAxis;
	}
	
}
