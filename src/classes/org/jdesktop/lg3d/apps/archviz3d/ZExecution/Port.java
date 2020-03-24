package org.jdesktop.lg3d.apps.archviz3d.ZExecution;

import org.jdesktop.lg3d.apps.archviz3d.ZExecution.ArqComponent;
import org.jdesktop.lg3d.apps.archviz3d.ZExecution.PROComponent;
import org.jdesktop.lg3d.apps.archviz3d.ZExecution.PROLink;
import org.jdesktop.lg3d.apps.archviz3d.ZExecution.Port;

import JavaLog.PlJavaObj;

/**
 *
 * @author  teyseyre
 */
public class Port extends ArqComponent implements PROLink{
    
    /** Creates a new instance of Link */
    public Port(String s) {
          super(s);
    }
    
    public PROComponent getOrigin() {
		if (iVarExists("Z_element")) 
			return (PROComponent)((PlJavaObj)iVar("Z_element")).reference();
		else 
			return null;
    }
    
    public PROComponent getTarget() {
    	Object vi;
    	
    	if (iVarExists("Z_next")) { 
    		vi=iVar("Z_next");
    		if (vi instanceof PlJavaObj) {
    			Port pt = (Port)((PlJavaObj)iVar("Z_next")).reference();
    			return  pt.getOrigin();
    		}
    		else 
    			return null;
    	}
    	else 
    		return null;	
    }
    
}
