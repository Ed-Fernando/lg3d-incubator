package org.jdesktop.lg3d.apps.archviz3d.geons.builders;

import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.vecmath.Color3f;
import javax.vecmath.Point3f;

import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.sg.BoundingBox;
import org.jdesktop.lg3d.sg.Shape3D;

import org.jdesktop.lg3d.apps.archviz3d.ZExecution.Dispatcher;
import org.jdesktop.lg3d.apps.archviz3d.ZExecution.PROComponent;
import org.jdesktop.lg3d.apps.archviz3d.ZExecution.ZEvent;
import org.jdesktop.lg3d.apps.archviz3d.abstractors.AbstractorCC;
import org.jdesktop.lg3d.apps.archviz3d.geons.GeonAbstract;
import org.jdesktop.lg3d.apps.archviz3d.geons.GeonCluster;
import org.jdesktop.lg3d.apps.archviz3d.geons.GeonEvent;
import org.jdesktop.lg3d.apps.archviz3d.geons.builders.GeonBuilderLayout;
import org.jdesktop.lg3d.apps.archviz3d.geons.builders.GeonBuilderSur;
import org.jdesktop.lg3d.apps.archviz3d.geons.components.GeonRepaired;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.BuildStrategy3D;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.Factory3D;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DComponent;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DCompositeComponent;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DObservable;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.UpdateBlock;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.graph.Graph3D;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.layouts.LayoutStrategy3D;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.layouts.SphericalLayout3D;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.objects.LabeledObject3D;


/**
 * Esta clase se encarga de construir clusters para la vista de C&C.
 * 
 * @author Juan Feldman - Lucas Vigneau
 *
 */
public class GeonBuilderCluster extends BuildStrategy3D {
	/** Logger de mensajes.*/
	private static Logger logger = Logger.getLogger("lg.ArchViz3D");	

	/** Builder para los componentes del cluster.*/
	private BuildStrategy3D builder;
	/** Indica si se debe construir el cluster.*/
	private boolean needCreation = true;
	/** Grafo con los componentes del cluster.*/
	private J3DComponent graph;
	/** Eventos del cluster.*/
	private J3DCompositeComponent events; 
	
	/**
	 * Instancio el Cluster Builder.
	 * @param builder Builder para los componentes del cluster. 
	 */
	public GeonBuilderCluster(BuildStrategy3D builder) {
		this.builder = builder;
	}
	
	/**
	 * Construye el cluster que se recibe como parametro.
	 * @param client Cluster a construir. 
	 */
	public void build(J3DComponent client) {
		if (needCreation) {
			// Construyo los hijos del cluster
			graph = new Graph3D((PROComponent)client.getSubject(), builder);
			graph.addObserver(client);
			((GeonCluster)client).addComponent(graph);
			
			// Construyo los eventos del cluster
			events = buildEvents(client);
			client.addObserver(events);
			((GeonCluster)client).addEvents(events);

			//Reubicamos el componente dentro de la visualizacion
	 	    GeonRepaired.instance().repairEvent(((GeonCluster)client).getName(), events);

	 	    needCreation = false;
		}
	}
	
	/**
	 * Construye los eventos para el cluster que se recibe como parametro.
	 * @param client Cluster al cual se le agregaran los eventos.
	 */
	public J3DCompositeComponent buildEvents(J3DComponent client) {
		GeonCluster geonCluster = (GeonCluster)client;
		J3DCompositeComponent compositeCluster = new J3DCompositeComponent();

		final String clusterName = geonCluster.getName().toLowerCase();
		
		PROComponent ac = (PROComponent)client.getSubject();
		AbstractorCC abstractor = null;
		for (Iterator i2= ac.getComponentsIterator(); i2.hasNext() && abstractor == null;) 	{
			AbstractorCC pc =  (AbstractorCC)i2.next();
			if (pc.getName().endsWith("_Mod")) 
				abstractor = pc;    		
		}
		
		// Aca se setean las imagenes para los eventos.
		// Si abstractor es igual a null significa que este cluster no debe graficarse 
		// por lo tanto elimino todos sus componentes.
		if (abstractor != null) {
			Dispatcher disp= Dispatcher.instance();
			for (Iterator i= abstractor.getComponentsIterator(); i.hasNext(); ) {
				String eventName = (String)i.next();
				
				// Creo el Geon para el evento
				Color3f color = geonCluster.getEventColor(eventName);
				Appearance ap = Factory3D.instance().createAppearance(color);
				Shape3D prop = GeonBuilderSur.instance().buildGeonByName(geonCluster.getGeonEventName(eventName));
				prop.setAppearance(ap);
				
	    		// Agrego el Geon nuevo que representa el evento, con los eventos creados anteriormente
	    		GeonEvent event = new GeonEvent(geonCluster.getSubject(), prop, eventName);
				event.setScale(0.1f);
				
		        // Tengo que correr el evento del pipeline
				GeonRepaired.instance().repairEvent(clusterName, event);
				
				// Le agrego el texto al evento
				disp.addObserver((Observable)ac, event, ZEvent.Z_ALL,false);
				compositeCluster.addComponent(new LabeledObject3D(event, eventName, new Point3f(1.0f, 0.0f, 0.0f), new Color3f(0.6f, 0.04f, 0.04f), Factory3D.rubberBlack));
			}

			// Obtengo el Layout del componente
			final LayoutStrategy3D layout = GeonBuilderLayout.instance().getLayout(geonCluster.getGeonName());
			if (layout instanceof SphericalLayout3D) {
				((SphericalLayout3D)layout).setComplexity(abstractor.getComponents().size());
				GeonRepaired.instance().setRadius(clusterName, geonCluster.getWrapper(), geonCluster.getScale());
				((SphericalLayout3D)layout).setRadius(((GeonCluster)client).getWrapper().getRadius());
			}
				
			compositeCluster.setLayout(layout);
			compositeCluster.setCommand(new UpdateBlock() {
				public void update(Observable observable, Observer observer, Object arg) {
					logger.log(Level.INFO, "Do layout...");
					GeonCluster clu = ((GeonCluster)((J3DObservable)observable).getRealObservable());
					((J3DCompositeComponent)observer).doLayout();
					Point3f cen = new Point3f();
					BoundingBox bounds = clu.getWrapper().getBoundingBox();
					bounds.getLower(cen);

					// Traslado los eventos
					GeonRepaired.instance().repairEvent(clusterName, ((J3DCompositeComponent)observer), layout, cen, clu);
				}
			});

		}
		else   
			client.removeAllChildren();

		return compositeCluster;
	}
	
	/**
	 * Elimina todos los componentes del cluster.
	 * @param client Cluster al cual se le eliminaran los componetes. 
	 */
	public void clearBuild(J3DComponent client) {
		needCreation = true;
		graph.deleteObserver(client);
		((GeonCluster)client).removeComponent(graph);
	}
	
	/**
	 * Reconstruye los eventos del cluster. Este metodo se utiliza cuando
	 * el usario modifica el geon que representa a un componente de la 
	 * arquitectura.
	 * @param client GeonCluster al cual se le reconstuiran los eventos
	 */
	public void rebuildEvents(GeonAbstract client) {
		GeonCluster geonCluster = (GeonCluster)client;

		// Seteo el nuevo layout
		LayoutStrategy3D layout = GeonBuilderLayout.instance().getLayout(client.getGeonName());
		if (layout instanceof SphericalLayout3D) {
			PROComponent ac = (PROComponent)client.getSubject();
			AbstractorCC abstractor = null;
			for (Iterator i2= ac.getComponentsIterator(); i2.hasNext() && abstractor == null;) 	{
				AbstractorCC pc =  (AbstractorCC)i2.next();
				if (pc.getName().endsWith("_Mod")) 
					abstractor = pc;    		
			}
			((SphericalLayout3D)layout).setComplexity(abstractor.getComponents().size());
			GeonRepaired.instance().setRadius(client.getName().toLowerCase(), geonCluster.getWrapper(), geonCluster.getScale());
			((SphericalLayout3D)layout).setRadius(((GeonCluster)client).getWrapper().getRadius());
		}
		events.setLayout(layout);

		// Obtengo la posicion del cluster 
		Point3f cen = new Point3f();
		BoundingBox bounds = ((GeonCluster)client).getWrapper().getBoundingBox();
		bounds.getLower(cen);

		// Ubico los eventos
		GeonRepaired.instance().repairEvent(client.getName(), events, cen);
	}
	
}
