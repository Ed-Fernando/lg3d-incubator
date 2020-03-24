package org.jdesktop.lg3d.apps.archviz3d.geons;

import java.util.HashMap;
import java.util.Observable;
import java.util.Vector;

import javax.vecmath.Color3f;

import org.jdesktop.lg3d.apps.archviz3d.abstractors.Abstractor;
import org.jdesktop.lg3d.apps.archviz3d.abstractors.AbstractorCC;
import org.jdesktop.lg3d.apps.archviz3d.geons.usecase.GeonUseCaseInterface;
import org.jdesktop.lg3d.apps.archviz3d.geons.usecase.GeonUseCaseManager;
import org.jdesktop.lg3d.apps.archviz3d.geons.usecase.GeonUseCaseThreadComponent;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DCompositeComponent;
import org.jdesktop.lg3d.sg.Appearance;



/**
 * Esta clase es la clase padre de todos los Geones, ya sean
 * simples (Geon, GeonEvent, GeonLink) o compuestos (GeonCluster).
 * 
 * @author Juan Feldman
 *
 */
public abstract class GeonAbstract extends J3DCompositeComponent implements GeonUseCaseInterface {
	/** Valor para animar el componente de la arquitectura.*/
	private final float VALUE = 0.15f;
	/** Valor para animar el componente de la arquitectura.*/
	private Color3f originalColor = null;
    
    /**
     * Setea la lista de geones que pueden representar a 
     * este componente.
     * @param geonsList Lista de geones que pueden representar a 
     * este componente.
     */
    public void setGeonsList(String[] geonsList) {
    	((Abstractor)getGeonAbstractor()).setGeonsList(geonsList);
    }
      
    /**
     * Retorna la lista de geones que pueden representar a 
     * este componente.
     * @return Lista de geones que pueden representar a 
     * este componente.
     */
    public Vector<String> getGeonsList() {
    	return ((Abstractor)getGeonAbstractor()).getGeonsList();
    }
    
    /**
     * Setea la apariencia.
     * @param appearance Apariencia a setear.
     */
    public void setAppearance(Appearance appearance) {
    	((Abstractor)getGeonAbstractor()).setAppearance(appearance);
    }

    /**
     * Retorna la apariencia.
     * @return Appearance Apariencia del componente.
     */
    public Appearance getAppearance() {
    	return ((Abstractor)getGeonAbstractor()).getAppearance();
    }

    /**
     * Setea el color. 
     * @param color Color a setear.
     */
    public void setColor(Color3f color) {
    	((Abstractor)getGeonAbstractor()).setColor(color);
    }

    /**
     * Retorna el color del componente. 
     * @return Color3f color del componente.
     */
    public Color3f getColor() {
    	Color3f color = ((Abstractor)getGeonAbstractor()).getColor();

    	if (color == null) 
    		return new Color3f(1f, 0f, 0f);
        
    	return color;
    }

    /**
     * Setea el nombre del Geon que reprensenta a este componente.
     * @param geonName Nombre del Geon que reprensenta a este 
     * componente.
     */
    public void setGeonName(String geonName) {
		((Abstractor)getGeonAbstractor()).setGeonName(geonName);
    }
    
    /**
     * Retorna el nombre del Geon que reprensenta a este componente.
     * @return geonName Nombre del Geon que reprensenta a este 
     * componente.
     */
    public String getGeonName() {
		return ((Abstractor)getGeonAbstractor()).getGeonName();
    }

    /**
     * Este metodo se llama cuando el usuario cambio el Geon o el color que 
     * representan a este componente.
     * @param geonName Nombre del geon que representa a este componente.
     * @param color Color de este componente.
     */
    public void updateImage(String geonName, Color3f color) {
    	setGeonName(geonName);
    	setColor(color);
    }

    /**
     * Setea el nombre del Geon que reprensenta al evento que se
     * recibe como parametro.
     * @param eventName Nombre del evento. 
     * @param geonName Nombre del Geon que representa al evento. 
     */
    public void setGeonEventName(String eventName, String geonName) {
    	((AbstractorCC)getGeonAbstractor()).setGeonEventName(eventName, geonName);
    	eventName = eventName.toLowerCase(); 
    }
    
    /**
     * Retorna el nombre del Geon que reprensenta al evento que se
     * recibe como parametro.
     * @param eventName Nombre del evento. 
     * @return geonName Nombre del Geon que reprensenta al  
     * evento que se recibe como parametro.
     */
    public String getGeonEventName(String eventName) {
		return ((AbstractorCC)getGeonAbstractor()).getGeonEventName(eventName);
    }

    /**
     * Setea el color del evento que se recibe como parametro.
     * @param eventName Nombre del evento. 
     * @param color Color del evento que se recibe como parametro. 
     */
    public void setEventColor(String eventName, Color3f color) {
    	((AbstractorCC)getGeonAbstractor()).setEventColor(eventName, color);
    }

    /**
     * Retorna el color del evento que se recibe como parametro.
     * @param eventName nombre del evento. 
     * @return Color3f Color del evento que se recibe como parametro.
     */
    public Color3f getEventColor(String eventName) {
    	return ((AbstractorCC)getGeonAbstractor()).getEventColor(eventName);
    }

    /**
     * Setea la lista de geones que pueden representar a los 
     * eventos de la arquitectura asociado con este abstractor.
     * @param geonsEventList Lista de geones que pueden representar a los 
     * eventos de la arquitectura asociado con este abstractor.
     */
    public void setGeonsEventList(String[] geonsEventList) {
    	((AbstractorCC)getGeonAbstractor()).setGeonsEventList(geonsEventList);
    }
      
    /**
     * Retorna la lista de geones que pueden representar a los 
     * eventos de la arquitectura asociado con este abstractor.
     * @return Vector<String> Lista de geones que pueden representar a los 
     * eventos de la arquitectura asociado con este abstractor.
     */
    public Vector<String> getGeonsEventList() {
    	return ((AbstractorCC)getGeonAbstractor()).getGeonsEventList();
    }
    
    /**
     * Retorna el abstractor del componente.
     * @return Observable abstractor
     */
    public Observable getGeonAbstractor() {
        // Este metodo es redefinido por la clase Graph3D ya que al ser
    	// un composite su abstractor es el abstractor de un Cluster (dicho 
        // abstractor no puede modificarse porque lo utiliza el Cluster para
        // dibujarse), y los datos de sus componentes estan almacenados en los abstractores
        // de sus componentes.
    	return getSubject();
    }
    
    /**
     * Retorna todas las propiedades del componente.
     * @return HashMap<String, String> Propiedades del componente.
     */
	public HashMap<String, String> getProperties() {
    	return ((Abstractor)getGeonAbstractor()).getProperties();
	}
	
/******************************************************************************************************/
/*					COMPORTAMIENTO PARA DESAPARECER LOS CLUSTERS					  				  */ 
/******************************************************************************************************/
	/**
	 * Setea la visibilidad de todo el geon menos el label.
	 * @param visible Visibilidad de todo el geon menos el label.
	 */
	public void setVisiblePart(boolean visible) {
	}

	/**
	 * Retorna la visibilidad de todo el geon menos el label.
	 * @return boolean Visibilidad de todo el geon menos el label.
	 */
	public boolean isVisiblePart() {
		return true;
	}

/******************************************************************************************************/
/*					INTERFACE PARA EJECUTAR UN CASO DE USO EN UN COMPONENTE  					  	  */ 
/******************************************************************************************************/
	/**
	 * Se salva el valor a modificar durante la animacion del caso de uso,
	 * para que una vez finalizado se restaure el valor original.
	 */
	public void saveOriginalValue() {
		originalColor = new Color3f(); 
		getAppearance().getMaterial().getDiffuseColor(originalColor);
	}
	
    /**
     * Retorna el color del componente.
     * @return Object Color a modificar.
     */
	public Object getValueToChange() {
		Color3f originalColor = new Color3f(); 
		getAppearance().getMaterial().getDiffuseColor(originalColor);
		return originalColor.clone();
	}
	
	/**
	 * Se ejecutan las acciones que correspondan para la animacion
	 * del caso de uso.
	 * @param value Color a cambiar.
	 * @param loopNumber Numero de iteracion.
	 * @return Object Nuevo color.
	 */
	public Object loopAction(Object value, int loopNumber) {
		Color3f color = (Color3f)((Color3f)value).clone();
		if (loopNumber < GeonUseCaseManager.NUMBER_LOOPS/2) {
			color.x += VALUE;
			color.y += VALUE;
			color.z += VALUE;
		}
		else {
			color.x -= VALUE;
			color.y -= VALUE;
			color.z -= VALUE;
		} 
		return color;
	}
	
	/**
	 * Se aplican los cambios realizados en el metodo
	 * <b>loopAction</b>.
	 * @param value Nuevo color.
	 */
	public void applyChange(Object value) {
		setColor((Color3f)value);
	}
	
	/**
	 * Se deshacen los cambios realizados en el metodo
	 * <b>applyChange</b>.
	 */
	public void restore() {
		getAppearance().getMaterial().setDiffuseColor(originalColor);
	}
	
    /**
     * Este metodo se encarga de ejecutar el caso de uso
     * en el componente de la arquitectura.
     * @param geonUseCaseManager Encargado de ejecutar el caso de uso. 
     */
    public void executeUseCase(GeonUseCaseManager geonUseCaseManager) {
        Thread geonUseCaseThread;
    	geonUseCaseThread = new Thread(new GeonUseCaseThreadComponent(this, geonUseCaseManager)); 
        geonUseCaseThread.start();
    }
    
}


