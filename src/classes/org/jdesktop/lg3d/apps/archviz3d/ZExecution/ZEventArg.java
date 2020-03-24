package org.jdesktop.lg3d.apps.archviz3d.ZExecution;

import org.jdesktop.lg3d.apps.archviz3d.ZExecution.ZEvent;

public class ZEventArg extends ZEvent {
    protected Object command;
    
    public ZEventArg(Object source, String id) {
        super(source, id);
    }

    public ZEventArg(Object source, String id, Object arg) {
        super(source, id);
        this.command = arg;
    }

    public Object arg() {
        return command;
    }

    public void arg(Object arg) {
        this.command = arg;
    }

    public String toString() {
        return "event(" + id + "," + arg() + ")";
    }

}



