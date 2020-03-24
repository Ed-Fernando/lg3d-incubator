package org.jdesktop.lg3d.apps.archviz3d.ZExecution;



import com.objectspace.jgl.HashMap;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jdesktop.lg3d.apps.archviz3d.ZExecution.Dispatcher;
import org.jdesktop.lg3d.apps.archviz3d.ZExecution.ZEventArg;
import org.jdesktop.lg3d.apps.archviz3d.ZExecution.ZEventConst;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.UpdateCommand;


public class Dispatcher implements Observer, ZEventConst {
    protected HashMap map;
    
    protected int speed=0;

    protected static Dispatcher instance;
    
    ExecutorService pool = Executors.newCachedThreadPool();

    public static Dispatcher instance() {
        if (instance == null) instance = new Dispatcher();
        return instance;
    }

    protected Dispatcher() {
        map = new HashMap(true); // allow duplicates
    }
    
    public void pause() {
    	try {
    		Thread.sleep(speed*50);
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }

    public void update(Observable o, Object arg) {
        ZEventArg event = (ZEventArg) arg;
        
        for (Enumeration e = map.values(o); e.hasMoreElements();) {
            ObserverInterest oi;
            oi = (ObserverInterest) e.nextElement();
            
            if (oi.interestedIn(event.id())) {
                pool.execute(new UpdateCommand(o, oi.observer, arg));
            } 
        }

    }

    public void addObserver(Observable o, Observer e) {
    	addObserver(o, e, Z_ALL);
    }

    public void addObserver(Observable o, Observer e, String id, boolean concu) { //tener en cuenta si ya tiene una entrada
        ObserverInterest oi = new ObserverInterest(e, id,concu);
        map.add(o, oi);
        o.addObserver(this);
    }

    public void addObserver(Observable o, Observer e, String id) { 
    	addObserver(o,e,id,true);
    }
    
    void deleteObservers(Observable o) {
        map.remove(o);
    }

    void deleteObservers(Observable o, Observer el) {
        //TODO: verficar si anda
    	for (Enumeration e = map.values(o); e.hasMoreElements();) {
    		ObserverInterest oi;
    		oi = (ObserverInterest) e.nextElement();
    		if (oi.observer==el)  
    			map.remove(e);  
    	}
    }

	/**
	 * @return Returns the speed.
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 * @param speed The speed to set.
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	class ObserverInterest {
		public Observer observer;
		boolean concurrent;
		Set<String> set;

		public ObserverInterest(Observer o, String id, boolean concu) {
			set = new HashSet<String>();
			observer = o;
			addEvent(id);
			concurrent = concu;
		}

		public ObserverInterest(Observer o, String id) {
			this(o,id,true);
		}

		public void observer(Observer o) {
			observer = o;
		}

		public void addEvent(String id) {
			set.add(id);
		}

		public boolean interestedIn(String id) {
			return (set.contains(id) || set.contains(Dispatcher.Z_ALL));
		}

		/**
		 * @return Returns the concurrent.
		 */
		public boolean isConcurrent() {
			return concurrent;
		}

		/**
		 * @param concurrent The concurrent to set.
		 */
		public void setConcurrent(boolean concurrent) {
			this.concurrent = concurrent;
		}
	}

}