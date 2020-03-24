package org.jdesktop.lg3d.apps.archviz3d.ZExecution;

import org.jdesktop.lg3d.apps.archviz3d.ZExecution.ZEventArg;

/**
 * @author teyseyre
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ZEventArch extends ZEventArg {
	protected Object[] args;
	
	ZEventArch(Object source, String id, Object arg,Object[] args) {
        super(source, id,arg);
        this.args=args;
    }
	
	public String getArg(int i) {
		if (args != null) return args[i].toString();
		else return null;
	}
	
	public String toString() {
        return "event(" + id + "," + arg() + "ev: " + getArg(3) + ")";
    }
	
	public String getEvent() {
		return getArg(3); 
	}
	
}
