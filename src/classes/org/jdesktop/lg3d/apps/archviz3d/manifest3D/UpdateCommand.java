package org.jdesktop.lg3d.apps.archviz3d.manifest3D;

import java.util.Observable;
import java.util.Observer;

/**
 * Title:        Requirements Visualization<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Alfredo Teyseyre<p>
 * Company:      GOV - ISISTAN<p>
 * @author Alfredo Teyseyre
 * @version 1.0
 */
public class UpdateCommand implements Runnable {
    Observer observer;
    Observable observable;
    Object arg;

    public UpdateCommand(Observable observable, Observer observer, Object arg) {
        this.observer = observer;
        this.observable = observable;
        this.arg = arg;
    }

    public void runThread() {
        new Thread(this).start();
    }

    public void run() {
        observer.update(observable, arg);
    }

}
