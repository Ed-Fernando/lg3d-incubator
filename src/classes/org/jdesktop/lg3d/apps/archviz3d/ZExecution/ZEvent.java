package org.jdesktop.lg3d.apps.archviz3d.ZExecution;

import java.util.EventObject;

public class ZEvent extends EventObject {
    //repetidas en ZEventConst
    public static final String Z_ALL = "all";
    public static final String START_OPERATION = "startOperation";
    public static final String END_OPERATION = "endOperation";
    public static final String FAIL_OPERATION = "failOperation";
    public static final String STATE_CHANGED = "stateChanged";

    protected String id;

    public ZEvent(Object source, String id) {
        super(source);
        this.id = id;
    }

    public String id() {
        return id;
    }

    public Object arg() {
        return null;
    }

    public void arg(Object arg) {
    }

    public String toString() {
        return getClass().getName() + " " + id + " " + arg();
    }
}



