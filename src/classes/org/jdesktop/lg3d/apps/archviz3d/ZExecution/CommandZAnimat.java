package org.jdesktop.lg3d.apps.archviz3d.ZExecution;


import org.jdesktop.lg3d.apps.archviz3d.Command;
import org.jdesktop.lg3d.apps.archviz3d.SharedBrain;
import org.jdesktop.lg3d.apps.archviz3d.ZExecution.ZAnimat;

import JavaLog.PlObject;

/**
 * Title:        Requirements Visualization<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Alfredo Teyseyre<p>
 * Company:      GOV - ISISTAN<p>
 * @author Alfredo Teyseyre
 * @version 1.0
 */
public class CommandZAnimat extends Command {
    PlObject args;

    public CommandZAnimat(SharedBrain component, String command, PlObject args) {
        super(component, command);
        this.args = args;
    }

    public void run() {
        ((ZAnimat)component).execute(command,args);
    }
}