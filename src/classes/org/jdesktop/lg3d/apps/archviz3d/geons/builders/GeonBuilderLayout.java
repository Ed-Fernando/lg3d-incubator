package org.jdesktop.lg3d.apps.archviz3d.geons.builders;

import javax.vecmath.Vector3f;


import org.jdesktop.lg3d.apps.archviz3d.geons.builders.GeonBuilderLayout;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.layouts.LayoutStrategy3D;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.layouts.LinealLayout3D;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.layouts.SphericalLayout3D;


/**
 * Esta clase se encarga de construir los layouts. 
 * 
 * @author Juan Feldman - Lucas Vigneau
 *
 */
public class GeonBuilderLayout {
    /** Esta clase es un Singleton.*/
    private static GeonBuilderLayout geonLayoutBuilder;
   
    /**
     * Se intancia la clase.
     */
    private GeonBuilderLayout() {
    }
    
    /**
     * Retorna la unica instancia de esta clase.
     * @return GeonBuilderLayout Unica instancia de esta clase.
     */
    public static GeonBuilderLayout instance() {
        if (geonLayoutBuilder == null)
            geonLayoutBuilder = new GeonBuilderLayout();
        return geonLayoutBuilder;
    }
    
    /**
     * Construye un layout para el geon cuyo nombre se recibe como parametro.
     * @param geonName Nombre del Geon para el cual se construye el layout.
     * @return LayoutStrategy3D Estrategia de layout construida.
     */    
    private LayoutStrategy3D buildLayout(String geonName) {
    	if (geonName.equals("barrel")) 
    		return new SphericalLayout3D();
    	else
    	if ( (geonName.equals("cube")) || (geonName.equals("cylinder")) ) { 
			LinealLayout3D layout = new LinealLayout3D();
			layout.setSeparation(new Vector3f(0.0f, 0.5f, 0.0f));
			return layout;
		}
		else {
			LinealLayout3D layout = new LinealLayout3D();
			layout.setDirection(new Vector3f(1.0f,0.0f,0.0f));
			return layout;
		}
    }
    
    /**
     * Retorna un layout para el geon cuyo nombre se recibe como parametro.
     * @param geonName Nombre del Geon para el cual se construye el layout.
     * @return LayoutStrategy3D Estrategia de layout.
     */
    public LayoutStrategy3D getLayout(String geonName) {
    	if (geonName == null) //para el bank system
    		return buildLayout("cube");
    	else
    		return buildLayout(geonName); 
    }
}
