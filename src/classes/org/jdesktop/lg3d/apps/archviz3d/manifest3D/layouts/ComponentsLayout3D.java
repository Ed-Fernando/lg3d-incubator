package org.jdesktop.lg3d.apps.archviz3d.manifest3D.layouts;


import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;


import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DComponent;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.layouts.LayoutStrategy3D;


import java.util.Vector;

/**
 * 
 * @author Juan Feldman - Lucas Vigneau
 *
 */
public abstract class ComponentsLayout3D extends LayoutStrategy3D {
	
	/**
	 * Retorna los puntos del layout. 
	 * @return Vector puntos del layout.
	 */
	protected abstract Vector layoutPoints();

	/**
	 * Realiza el layout
	 */
	public void layout() {
		Vector points = layoutPoints();
     	for (int i = 0; i < getClient().numComponents(); i++) {
     		Vector3d vec = (Vector3d) points.get(i);
			((J3DComponent) getClient().getComponent(i)).setTranslation(new Vector3f((float)vec.x, (float)vec.y, (float)vec.z));
		}
	}
}
