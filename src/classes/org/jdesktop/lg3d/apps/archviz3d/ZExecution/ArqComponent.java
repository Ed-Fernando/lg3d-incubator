package org.jdesktop.lg3d.apps.archviz3d.ZExecution;

import java.util.Iterator;
import java.util.Vector;

import org.jdesktop.lg3d.apps.archviz3d.ZExecution.PROComponent;
import org.jdesktop.lg3d.apps.archviz3d.ZExecution.Port;
import org.jdesktop.lg3d.apps.archviz3d.ZExecution.ZAnimat;

import JavaLog.PlJavaObj;
import JavaLog.PlList;
import JavaLog.PlObject;

/**
 *
 * @author Teyseyre
 */
public class ArqComponent extends ZAnimat implements PROComponent {

	/** Creates a new instance of ArqComponent */
    public ArqComponent(String Filename) {
        super(Filename);
    }
    
    public void initState() {     
        super.initState();
        this.execute("z_init_Arch_" + fileNameOnly());
//        boolean res= this.execute("z_init_Arch_" + fileNameOnly());
//		if (!res) logger.debug("Error de inicializacion de ArqComponent en :" + fileName());
    }
    
    public String getName() {
    	return this.fileNameOnly();
    }
    
    protected Vector toVector(PlList list) {
    	PlObject[] a = list.toArray();
    	Vector res = new Vector();

    	if (a !=null) {
    		for (int i=0 ; i < a.length; i++)
    			res.add( ((PlJavaObj)a[i]).reference());
    	}

    	return res;
    }
    
    protected Vector toVectorString(PlList list) {
    	PlObject[] a = list.toArray();
    	Vector res = new Vector();

    	if (a !=null) {
    		for (int i=0 ; i < a.length; i++)
    			res.add(a[i].toString());
    	}
    	return res;
    }
          
    public Iterator getComponentsIterator() {
        return  getComponents().iterator(); 
    }
    
    public Iterator getConnectorIterator() {
        return  getConnectors().iterator(); 
    }
    
    public Iterator getLinksIterator() {
        return getLinks().iterator(); 
        
    }
    
    public Iterator getPortsIterator() {
        return getPorts().iterator(); 
        
    }
    
    public Vector getComponents() {
    	if (iVarExists("Z_components"))    	
    		return toVector((PlList)iVar("Z_components"));
    	else 
    		return new Vector();
    }
    
    public Vector getConnectors() {
    	if (iVarExists("Z_connectors"))
    		return toVector( (PlList)iVar("Z_connectors"));
    	else 
    		return new Vector();
    }
    
    public Vector getLinks() {
    	Vector res = new Vector();
    	for (Iterator i=this.getPortsIterator(); i.hasNext();) {
    		PROComponent comp=(((Port)i.next()).getTarget());
    		if (comp != null) 
    			res.add(comp);
    	}
    	
    	return res;   
    }
    
    public Vector getPorts() {
    	if (iVarExists("Z_ports"))
    		return toVector( (PlList)iVar("Z_ports"));
    	else 
    		return new Vector();
    }
    
    public Vector getEvents() {
    	if (iVarExists("Z_events"))
   			return toVectorString( (PlList)iVar("Z_events"));
    	else 
    		return new Vector();
    }
}
