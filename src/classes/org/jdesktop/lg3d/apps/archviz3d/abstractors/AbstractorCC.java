package org.jdesktop.lg3d.apps.archviz3d.abstractors;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javax.vecmath.Color3f;


import org.jdesktop.lg3d.apps.archviz3d.ZExecution.ArqComponent;
import org.jdesktop.lg3d.apps.archviz3d.ZExecution.PROComponent;
import org.jdesktop.lg3d.apps.archviz3d.ZExecution.ZAnimat;
import org.jdesktop.lg3d.apps.archviz3d.abstractors.AbstractionLevel;
import org.jdesktop.lg3d.apps.archviz3d.abstractors.Abstractor;
import org.jdesktop.lg3d.apps.archviz3d.abstractors.AbstractorCC;


/**
 * Clase padre de todos los abstractores de la vista de Componentes y Conectores.
 * 
 * @author Teyseyre
 *
 */
public abstract class AbstractorCC extends Abstractor implements PROComponent, Observer {
	/** Nivel de abstraccion.*/
	protected int level;
	/** Nivel de abstraccion.*/
	protected AbstractionLevel abstractionLevel;
	/** Abstractores hijos del abstractor.*/
	protected Vector<AbstractorCC> abstractors;
	/** Text.pl asociado con el abstractor (ArqComponent).*/
	protected PROComponent subject;
	/** Nombre del abstractor.*/
	protected String name;
	/** Contenedor del abstractor.*/
	protected AbstractorCC container;
	/** Indica si este abstractor esta habilitado.*/
	private boolean enabled;
	
    /** Clase del Wrapper para el componente de la arquitectura asociado con este abstractor.**/
    private Class wrapperClass  = null;
    /** Mappea el nombre de un Evento con el nombre del Geon que lo representa.*/
    private Hashtable<String, String> eventToGeon = new Hashtable<String, String>();
    /** Mappea el nombre de un Evento con el color que posee.*/
    private Hashtable<String, Color3f> eventToColor = new Hashtable<String, Color3f>();
    /** Geones que pueden representar a los eventos de la arquitectura asociado con este abstractor.*/
    private Vector<String> geonsEventList  = null;

    /**
     * Se instancia el Abstractor.
     * @param subject Text.pl asociado con el abstractor 
     */
	public AbstractorCC(PROComponent subject) {
		this(subject, 0);
	}

    /**
     * Se instancia el Abstractor.
     * @param subject Text.pl asociado con el abstractor 
     * @param level Nivel de abstraccion. 
     */
	public AbstractorCC(PROComponent subject, int level) {
		this(subject, level, null);
	}

    /**
     * Se instancia el Abstractor.
     * @param subject Text.pl asociado con el abstractor 
     * @param level Nivel de abstraccion. 
     * @param container Abstractor que contiene a este abstractor. 
     */
	public AbstractorCC(PROComponent subject, int level, AbstractorCC container) {
		super(null);
		geonsEventList = new Vector<String>();
		abstractors = new Vector<AbstractorCC>();
		this.level = level;
		this.container = container;
		enabled = true;
		setSubject(subject);

		// Inicializo los geones y los colores que representan a los eventos 
		if (subject instanceof ArqComponent) {
			Vector events = ((ArqComponent)subject).getEvents();
			
			for (Enumeration e = events.elements(); e.hasMoreElements(); ) {
				String eventName = (String)e.nextElement();
				eventName = eventName.toLowerCase();
				
				eventToGeon.put(eventName, "handle");
				eventToColor.put(eventName, new Color3f(1.0f,0.78f,0.0f));
			}
		}
	}

	/**
	 * Se agrega un abstractor hijo a este abstractor.
	 * @param abstractor Abstractor hijo. 
	 */
	public void addAbstractor(AbstractorCC abstractor) {
		abstractors.add(abstractor);
	}

	/**
	 * Retorna el abstractor del componente que se recibe como parametro.
	 * @param component Componente al cual se le buscara su abstractor. 
	 * @return PROComponent
	 */
	public PROComponent findAbstractorOf(PROComponent component) {
		for (Iterator i = abstractors.iterator(); i.hasNext();) {
			AbstractorCC abstractor = (AbstractorCC)i.next();
			if (abstractor.getSubject() == component)
				return abstractor;
		}
		return null;
	}

	/**
	 * Setea el nivel de abstraccion.
	 * @param abstractionLevel Nivel de abstraccccion.
	 */
	public void setAbstractionLevel(AbstractionLevel abstractionLevel) {
		this.abstractionLevel = abstractionLevel;
		for (Iterator i = abstractors.iterator(); i.hasNext();) {
			if (abstractionLevel != null)
				((AbstractorCC) i.next()).setAbstractionLevel(abstractionLevel.clone());
			else
				((AbstractorCC) i.next()).setAbstractionLevel(null);
		}
		abstractionLevelChanged();
	}

	/**
	 * Retorna el nivel de abstraccion. 
	 * @return AbstractionLevel Nivel de abstraccion.
	 */
	public AbstractionLevel getAbstractionLevel() {
		return abstractionLevel;
	}

	/**
	 * Setea el nivel de abstracion.
	 * @param level Nivel de abstraccccion.
	 */
	public void setAbstractionLevel(int level) {
		abstractionLevel.setLevel(level);
		for (Iterator i = abstractors.iterator(); i.hasNext();) {
			((AbstractorCC) i.next()).setAbstractionLevel(level);
		}
		abstractionLevelChanged();
	}

	/**
	 * Seta la habilitacion del abstractor.
	 * @param enabled Habilitacion del abstractor.
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		abstractionLevel.setEnabled(enabled);
		for (Iterator i = abstractors.iterator(); i.hasNext();)
			((AbstractorCC) i.next()).setEnabled(enabled);
	}

	/**
	 * Retorna la habilitacion del abstractor.
	 * @return boolean Habilitacion del abstractor.
	 */
	public boolean getEnabled() {
		return enabled;
	}

	/**
	 * Setea el abstractor que contiene a este abstractor. 
	 * @param container Abstractor que contiene a este abstractor. 
	 */
	public void setContainer(AbstractorCC container) {
		this.container = container;
	}

	/**
	 * Retorna el abstractor que contiene a este abstractor. 
	 * @return AbstractorCAndC Abstractor que contiene a este abstractor.
	 */
	public AbstractorCC getContainer() {
		return container;
	}

	/**
	 * Se setea el sujeto del abstractor.
	 * @param subject Sujeto del abstractor.
	 */
	public void setSubject(PROComponent subject) {
		this.subject = subject;

		if (subject instanceof AbstractorCC)
			((AbstractorCC)subject).setContainer(this);
		
		((Observable)subject).addObserver(this);
		configureAbstractor();
	}

	/**
	 * Retorna el sujeto del abstractor.
	 * @return PROComponent Sujeto del abstractor.
	 */
	public PROComponent getSubject() {
		return subject;
	}

	/**
	 * Configura el abstractor.
	 */
	public abstract void configureAbstractor();

	/**
	 * Retorna la representacion del abstractor.
	 * Dicha representacion es:
	 * a) "Module" para AbstractorComponent.
	 * b) "Configuration" + nivel de abstracion para AbstractorConfiguration.
	 * @return Object Representacion del abstractor.
	 */
	public abstract Object abstractionRepresented();

	/**
	 * Se cambia el nivel de abstraccion. 
	 */
	public void abstractionLevelChanged() {
		regenerateVisualPresentation();
	}

	/**
	 * Se reconstruye la visualizacion.  
	 */
	public void regenerateVisualPresentation() {
		if (getComponentView() != null) 
			getComponentView().rebuild();
	}

	/**
	 * Se aumenta el nivel de abstraccion.
	 */
	public void increaseAbstractionLevel() {
		abstractionLevel.incrementLevelBy(1);
		for (Iterator i = abstractors.iterator(); i.hasNext();) {
			((AbstractorCC) i.next()).increaseAbstractionLevel();
		}
		abstractionLevelChanged();
	}

	/**
	 * Se disminuye el nivel de abstraccion.
	 */
	public void decreaseAbstractionLevel() {
		abstractionLevel.incrementLevelBy(-1);
		for (Iterator i = abstractors.iterator(); i.hasNext();) {
			((AbstractorCC) i.next()).decreaseAbstractionLevel();
		}
		abstractionLevelChanged();
	}

	/**
	 * Retorna el iterador para los abstractores hijos.
	 * @return Iterator Iterador para los abstractores hijos.
	 */
	public Iterator getComponentsIterator() {
		return getComponents().iterator();
	}

	/**
	 * Retorna el iterador para los links.
	 * @return Iterator Iterador para los links.
	 */
	public Iterator getLinksIterator() {
		return getLinks().iterator();
	}

	/**
	 * Retorna los abstractores hijos que cumplan con el nivel 
	 * de abstraccion.
	 * @return Vector Abstractores hijos que cumplen con el nivel 
	 * de abstraccion.
	 */
	public Vector getComponents() {
		if (abstractionLevel.moreThan(abstractionRepresented()))
			return getAbstractComponents();
		else
			return getEmptyComponents();
	}

	/**
	 * Retorna los abstractores hijos. 
	 * @return Vector Abstractores hijos. 
	 */
	public Vector getAbstractComponents() {
		return abstractors;
	}

	/**
	 * Retorna un vector vacio. 
	 * @return Vector Vector vacio. 
	 */
	public Vector getEmptyComponents() {
		return new Vector();
	}

	public Vector getFullComponents() {
		return subject.getComponents();
	}

	public Vector getLinks() {
		// Los links estan dentro del componente, si el componente esta habiliatdo 
		// los links tambien
		return getFullLinks(); 
	}

	public Vector getFullLinks() {
		return subject.getLinks();
	}

	public Vector getEmptyLinks() {
		return new Vector();
	}

	public void update(Observable o, Object arg) {
		if (arg.equals("enable"))
			this.setEnabled(true);
		else {
			setChanged();
			notifyObservers(arg);
		}
	}

	/**
	 * Retorna el nombre del Absractor
	 * @return nombre del Absractor
	 */
	public String getName() {
		if (name != null)
			return name;
		else
			return subject.getName();
	}

	/**
	 * Setea el nombre del Absractor
	 * @param name nombre del Absractor
	 */
	public void setName(String name) {
		this.name = name;
	}

    /**
     * Seteo la clase del Wrapper para el componente de la arquitectura 
     * asociado con este abstractor  
     * @param wrapperClass clase del Wrapper para el componente de la arquitectura  
     * asociado con este abstractor  
     */
    public void setWrapperClass(Class wrapperClass) {
        this.wrapperClass = wrapperClass;
    }

    /**
     * Retorna la clase del Wrapper para el componente de la arquitectura 
     * asociado con este abstractor 
     * @return clase del Wrapper para el componente de la arquitectura 
     * asociado con este abstractor 
     */
    public Class getWrapperClass() {
        return wrapperClass;
    }

    /**
     * Retorna el nombre del Geon que reprensenta al evento que se
     * recibe como parametro
     * @param eventName nombre del evento 
     * @return geonName Nombre del Geon que reprensenta al  
     * evento que se recibe como parametro
     */
    public String getGeonEventName(String eventName) {
    	eventName = eventName.toLowerCase(); 
    	
    	if (eventToGeon.containsKey(eventName))
    		return eventToGeon.get(eventName);
    	else
    		return "handle";
    }

    /**
     * Setea el nombre del Geon que reprensenta al evento que se
     * recibe como parametro
     * @param eventName nombre del evento 
     * @param geonName nombre del Geon que representa al evento 
     */
    public void setGeonEventName(String eventName, String geonName) {
    	eventName = eventName.toLowerCase(); 

    	eventToGeon.put(eventName, geonName); 
    }
    
    /**
     * Setea el color del evento que se recibe como parametro
     * @param eventName nombre del evento 
     * @param color color del evento que se recibe como parametro 
     */
    public void setEventColor(String eventName, Color3f color) {
    	eventName = eventName.toLowerCase(); 

    	eventToColor.put(eventName, color); 
    }

    /**
     * Retorno el color del evento que se recibe como parametro
     * @param eventName nombre del evento 
     * @return color del evento que se recibe como parametro
     */
    public Color3f getEventColor(String eventName) {
    	eventName = eventName.toLowerCase(); 
    	
    	if (eventToColor.containsKey(eventName))
    		return eventToColor.get(eventName);
    	else
    		return new Color3f(0.8f, 0f, 0f);
    }

    /**
     * Setea la lista de geones que pueden representar a los 
     * eventos de la arquitectura asociado con este abstractor
     * @param geonsEventList Lista de geones que pueden representar a los 
     * eventos de la arquitectura asociado con este abstractor
     */
    public void setGeonsEventList(String[] geonsEventList) {
    	this.geonsEventList.removeAllElements();        
    	
    	for (int i = 0; i < geonsEventList.length; i++)
    		this.geonsEventList.add(geonsEventList[i]);
    }
      
    /**
     * Retorna la lista de geones que pueden representar a los 
     * eventos de la arquitectura asociado con este abstractor
     * @return Lista de geones que pueden representar a los 
     * eventos de la arquitectura asociado con este abstractor
     */
    public Vector<String> getGeonsEventList() {
    	return geonsEventList;
    }
    
    /**
     * Retorna todas las propiedades del componente.
     * @return propiedades del componente.
     */
	public HashMap<String, String> getProperties() {
    	return ((ZAnimat)subject).getProperties();
	}    
}
