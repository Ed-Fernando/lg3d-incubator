package org.jdesktop.lg3d.apps.archviz3d.geons.components;

import org.jdesktop.lg3d.sg.Node;
import org.jdesktop.lg3d.wg.AnimationGroup;
import org.jdesktop.lg3d.wg.Component3D;

import org.jdesktop.lg3d.apps.archviz3d.geons.components.GeonAnimator;

/**
 * Esta clase permite agregar animaciones a un componente.
 * Se utiliza para que las vistas de las arquitecturas y los frames 
 * puedan correrse cuando se muestra un frame nuevo.
 * Esta clase podria considerarse como un Decorator.  
 * 
 * @see GeonAnimator   
 * @author Juan Feldman
 *
 */
public class GeonAnimation extends Component3D {
	/** Contenedor de la animacion.*/
	private AnimationGroup animationGroup = new AnimationGroup();
	/** Componente a animar.*/
	private Component3D component;

	/**
	 * Instancia el GeonAnimation.
	 */
	public GeonAnimation() {
		super.addChild(animationGroup);
		animationGroup.setCapability(AnimationGroup.ALLOW_CHILDREN_EXTEND);
		animationGroup.setCapability(AnimationGroup.ALLOW_CHILDREN_WRITE);
		animationGroup.setCapability(AnimationGroup.ALLOW_CHILDREN_READ);
	}

	/**
	 * Instancia el GeonAnimation.
	 */
	public GeonAnimation(Component3D component) {
		this();
		setComponent(component);
	}

	/**
	 * Retorna el contenedor de la animacion.
	 * @return AnimationGroup Contenedor de la animacion.
	 */
	public AnimationGroup getAnimationGroup () {
		return animationGroup;
	}

	/**
	 * Setea el componente a animar.
	 * @param component Componente a animar. 
	 */
	public void setComponent(Component3D component) {
		animationGroup.addChild(component);
		this.component = component;
	}

	/**
	 * Retorna el componente que se esta animando.
	 * @return Component3D Componente que se esta animando.
	 */
	public Component3D getComponent() {
		return component; 
	}
	
	/**
	 * Elimina el componente a animar. 
	 */
	public void removeComponent() {
		if (component != null) 
			animationGroup.removeChild(component);
		component = null;
	}

	/**
	 * No se pueden agregar hijos a esta clase.
	 * @param child Hijo a agregar.
	 */
	@Deprecated
	public void addChild(Node child) {
	}
	
}
