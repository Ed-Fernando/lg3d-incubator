package org.jdesktop.lg3d.apps.archviz3d.geons.components;

import javax.vecmath.Point3f;

import org.jdesktop.lg3d.sg.BoundingSphere;

import org.jdesktop.lg3d.apps.archviz3d.geons.GeonCluster;
import org.jdesktop.lg3d.apps.archviz3d.geons.GeonSimple;
import org.jdesktop.lg3d.apps.archviz3d.geons.components.GeonRepaired;
import org.jdesktop.lg3d.apps.archviz3d.geons.wrappers.GeonWrapper;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DComponent;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DCompositeComponent;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.layouts.LayoutStrategy3D;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.layouts.SphericalLayout3D;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.objects.LabeledObject3D;


/**
 * Esta clase repara la visualizacion de los componentes de la
 * Arquitectura.
 *  
 * @author Lucas Vigneau
 *
 */
public class GeonRepaired {
	/** Unica instancia de esta clase.*/
	private static GeonRepaired instance;
	
	/**
	 * Crea un GeonRepaired.
	 */
	private GeonRepaired() {
	}
	
	/**
	 * Retorna la unica instancia de esta clase.
	 * @return GeonRepaired Unica instancia de esta clase.
	 */
	public synchronized static GeonRepaired instance() {
		if (instance == null)
			instance = new GeonRepaired();
		return instance;
	}
	
	/**
	 * Repara la visualizacion del wrapper que recibe como parametro.
 	 * @param componentName Nombre de un componente de la arquitectura. 
 	 * @param wrapper Wrapper cuya visualizacion sera reparada.
	 */
 	public synchronized void repairWrapper(String componentName, J3DComponent wrapper)  {
 	    if (componentName.equals("LibrarySystem")){
 			wrapper.setScale(4.5f);
 			wrapper.setTranslation(wrapper.getTranslation().x - 1.45f, wrapper.getTranslation().y + 1.0f, wrapper.getTranslation().z);
 	    }
 	    if (componentName.equals("WebContainer")){
 			wrapper.setScale(1.16f);
 	    	wrapper.setTranslation(wrapper.getTranslation().x + 0.05f, wrapper.getTranslation().y - 0.16f, wrapper.getTranslation().z);
 	    }
 	    if (componentName.equals("LibraryServer")){
 			wrapper.setScale(2.15f);
 	    	wrapper.setTranslation(wrapper.getTranslation().x - 0.35f, wrapper.getTranslation().y + 0.1f, wrapper.getTranslation().z);
 	    }
 	    if (componentName.equals("ApplicationServer")){
 			wrapper.setScale(5.6f);
 	    	wrapper.setTranslation(wrapper.getTranslation().x - 5.3f, wrapper.getTranslation().y, wrapper.getTranslation().z);
 	    }
 	    if (componentName.equals("PrintServer")){
 			wrapper.setScale(5.7f);
 			wrapper.setTranslation(wrapper.getTranslation().x - 6.0f, wrapper.getTranslation().y - 3.0f, wrapper.getTranslation().z + 2.0f);
 	    }
 	    if (componentName.equals("PrintSystem")){
 			wrapper.setScale(9.0f);
 	    	wrapper.setTranslation(wrapper.getTranslation().x - 9.5f, wrapper.getTranslation().y - 3.0f, wrapper.getTranslation().z);
 	    }
 	    if (componentName.equals("Client")){
 			wrapper.setScale(2.3f);
 	    }
 	}

	/**
	 * Repara la visualizacion del label que recibe como parametro.
 	 * @param componentName Nombre de un componente de la arquitectura. 
 	 * @param label Label cuya visualizacion sera reparada.
	 */
 	public synchronized void repairLabel(String componentName, J3DComponent label)  {
 	    if (componentName.equals("LibrarySystem")){
 	    	label.setTranslation(label.getTranslation().x - 1.4f, label.getTranslation().y + 1.8f, label.getTranslation().z);
 	    }
 	    if (componentName.equals("WebContainer")){
 			label.setTranslation(label.getTranslation().x - 0.25f, label.getTranslation().y + 0.42f, label.getTranslation().z);
 	    }
 	    if (componentName.equals("LibraryServer")){
 			label.setTranslation(label.getTranslation().x - 0.6f, label.getTranslation().y + 0.33f, label.getTranslation().z);
 	    }
 	    if (componentName.equals("ApplicationServer")){
 			label.setTranslation(label.getTranslation().x - 6.0f, label.getTranslation().y + 2.8f, label.getTranslation().z);
 	    }
 	    if (componentName.equals("PrintServer")){
 			label.setTranslation(label.getTranslation().x - 6.0f, label.getTranslation().y, label.getTranslation().z);
 	    }
 	    if (componentName.equals("PrintSystem")){
 			label.setTranslation(label.getTranslation().x - 10.0f, label.getTranslation().y + 1.5f, label.getTranslation().z);
 	    }
 	    if (componentName.equals("Client")){
 			label.setTranslation(label.getTranslation().x, label.getTranslation().y + 1.3f, label.getTranslation().z);
 	    }
 	}

	/**
	 * Repara la visualizacion de los eventos que recibe como parametro.
 	 * @param componentName Nombre de un componente de la arquitectura. 
 	 * @param events Eventos cuya visualizacion sera reparada.
	 */
 	public synchronized void repairEvent(String componentName, J3DCompositeComponent events)  {
 		if (componentName.equals("LibraryServer")){
 			events.setTranslation(events.getTranslation().x + 0.9f, events.getTranslation().y + 0.25f, events.getTranslation().z);
 		}
 		if (componentName.equals("WebContainer")){
 			events.setTranslation(events.getTranslation().x + 1.45f, events.getTranslation().y - 0.03f, events.getTranslation().z);
		}
 		if (componentName.equals("PrintServer")){
 			events.setTranslation(events.getTranslation().x - 1.3f, events.getTranslation().y - 2.8f, events.getTranslation().z + 2.0f);
		}
 		if (componentName.equals("Client")){
 			events.setTranslation(events.getTranslation().x, events.getTranslation().y + 0.07f, events.getTranslation().z);
		}
		if (componentName.equals("pipeline")) {
			events.setTranslation(0.0f, -0.2f, -0.2f);
		}
 	}

	/**
	 * Repara la visualizacion de los eventos que recibe como parametro.
 	 * @param componentName Nombre de un componente de la arquitectura. 
 	 * @param events Eventos cuya visualizacion sera reparada.
 	 * @param layout Layout asociado al componente de la arquitectura.
 	 * @param center Punto central del componente de la arquitectura.
 	 * @param geonCluster Cluster asociado al componente de la arquitectura.
	 */
 	public synchronized void repairEvent(String componentName, J3DCompositeComponent events, LayoutStrategy3D layout, Point3f center, GeonCluster geonCluster)  {
		if (componentName.equals("pipeline")){
			events.setTranslation(0.0f, center.y , 0.0f);
		}
		if (!componentName.equals("pipeline")){
			if (layout instanceof SphericalLayout3D) {
				BoundingSphere boundSphere = new BoundingSphere();
				boundSphere.set(geonCluster.getWrapper().getBounds());
				boundSphere.getCenter(center);
				events.setTranslation(center.x, center.y, 0.0f);
			}
			else{
				events.setTranslation(2.5f, center.y + 1.0f, 0.0f);
			}
		}
 	}

	/**
	 * Repara la visualizacion de los eventos que recibe como parametro.
 	 * @param componentName Nombre de un componente de la arquitectura.
 	 * @param events Eventos cuya visualizacion sera reparada.
 	 * @param center Punto central del componente de la arquitectura.
	 */
 	public synchronized void repairEvent(String componentName, J3DCompositeComponent events, Point3f center)  {
		if (componentName.equals("BankServer"))
			if (events.getLayout() instanceof SphericalLayout3D){
				events.setTranslation(center.x + 2.4f, center.y + 2.4f, 0.0f);
			}
			else{
				events.setTranslation(2.5f, center.y + 1.0f, 0.0f);
			}
 	}

 	/**
	 * Repara la visualizacion del componente que recibe como parametro.
 	 * @param componentName Nombre de un componente de la arquitectura. 
	 * @param geon Geon que vamos a reparar.
	 * @param compositeGeon Componente de la arquitectura que vamos a reparar.
	 */
 	public synchronized void repairComponent(String componentName, GeonSimple geon, J3DCompositeComponent compositeGeon)  {
 		boolean modify = false;
 		
 		if (componentName.equals("Firewall")){
 			compositeGeon.setTranslation(compositeGeon.getTranslation().x + 0.2f, compositeGeon.getTranslation().y - 0.05f, compositeGeon.getTranslation().z);
 	        geon.setCuerpo(new LabeledObject3D (compositeGeon, geon.getName(), new Point3f(1.4f, -0.4f, 0.0f)));
 			modify = true;
		}
 		if (componentName.equals("DynamicPageGenerator")){
 			compositeGeon.setTranslation(compositeGeon.getTranslation().x + 0.2f, compositeGeon.getTranslation().y + 0.2f, compositeGeon.getTranslation().z);
 			geon.setCuerpo(new LabeledObject3D (compositeGeon, "DynPageGen", new Point3f(3.5f, 0.9f, 0.0f)));
 			modify = true;
		}
 		if (componentName.equals("WAPFormatter")){
 			compositeGeon.setTranslation(compositeGeon.getTranslation().x + 0.2f, compositeGeon.getTranslation().y + 0.45f, compositeGeon.getTranslation().z);
 			geon.setCuerpo(new LabeledObject3D (compositeGeon, "WAPFormat", new Point3f(3.3f, 2.3f, 0.0f)));
 			modify = true;
		}
 		if (componentName.equals("HTMLFormatter")){
 			compositeGeon.setTranslation(compositeGeon.getTranslation().x + 0.2f, compositeGeon.getTranslation().y + 0.7f, compositeGeon.getTranslation().z);
 			geon.setCuerpo(new LabeledObject3D (compositeGeon, "HTMLFormat", new Point3f(3.3f, 4.1f, 0.0f)));
 			modify = true;
		}
 		if (componentName.equals("SessionManager")){
 			compositeGeon.setTranslation(compositeGeon.getTranslation().x + 0.2f, compositeGeon.getTranslation().y + 0.95f, compositeGeon.getTranslation().z);
 			geon.setCuerpo(new LabeledObject3D (compositeGeon, "SesManager", new Point3f(3.3f, 5.5f, 0.0f)));
 			modify = true;
		}
 		if (componentName.equals("Printer")){
 			geon.setCuerpo(new LabeledObject3D (compositeGeon, geon.getName(), new Point3f(-0.5f, 1.0f, 0.0f)));
 			geon.getCuerpo().setTranslation(geon.getCuerpo().getTranslation().x - 0.5f, geon.getCuerpo().getTranslation().y - 3.0f, geon.getCuerpo().getTranslation().z + 1.5f);
 			modify = true;
		}
 		
 		//Seteo el texto de los geones que no rehubicamos
 		if (!modify)
 	        geon.setCuerpo(new LabeledObject3D (compositeGeon, geon.getName(), new Point3f(-0.5f, 1.0f, 0.0f)));
  	}

 	/**
	 * Setea el radio del objeto que representa el componente recibido como parametro.
 	 * @param componentName Nombre de un componente de la arquitectura. 
 	 * @param geonWrapper Wrapper asociado al componente de la arquitectura.
 	 * @param radius Radio que se va a setear.
	 */
 	public synchronized void setRadius(String componentName, GeonWrapper geonWrapper, float radius)  {
		if (componentName.equals("printserver")){
			geonWrapper.setRadius(radius * 4.4f);
		}
		if (!componentName.equals("printserver")){
			geonWrapper.setRadius(radius * 1.6f);
		}
 	}
 	
}
