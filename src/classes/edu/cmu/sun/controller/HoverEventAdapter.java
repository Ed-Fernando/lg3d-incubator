/*
* 3D File Manager - Project Looking Glass 
* Copyright Sun Microsystems, 2005
* 
* Project Course in Human-Computer Interaction
* Carnegie Mellon University
* 
* Aditya Chand, Braden Kowitz, Jake Pierson, Jessica Smith
*/

package edu.cmu.sun.controller;

import java.util.Timer;
import java.util.TimerTask;

import org.jdesktop.lg3d.wg.event.LgEvent;
import org.jdesktop.lg3d.wg.event.LgEventListener;
import org.jdesktop.lg3d.wg.event.MouseEnteredEvent3D;
import org.jdesktop.lg3d.wg.event.MouseMovedEvent3D;

/**
 * Since Looking glass does not provide hover events,
 * this simple class parses low level LgEvents 
 * an notifies a listener when a hover action occours.
 * 
 * However, hover is done slightly differently than
 * normal.  In this implementation, we start a timer
 * when a mouse enters a component.  If the timer
 * goes off before the mouse has exited the component,
 * then we count it as a hover event.
 * 
 * This is a very different behavior from normal hover
 * actions which look for when the mosue has stopped moving
 * for a set period of time.
 * 
 * @author Braden Kowitz, Jessica Smith
 *
 */
public class HoverEventAdapter implements LgEventListener
{
	
	private static final int DEFAULT_HOVER_TIME = 250;
	
	Listener listener;
	int time;
	Timer timer;

	
	public HoverEventAdapter(Listener listener)
	{
		this(listener, DEFAULT_HOVER_TIME);
	}
	
	public HoverEventAdapter(Listener listener, int msec)
	{
		this.listener = listener;
		this.time = msec;
	}

	
	public interface Listener
	{
		public void handleHoverEvent(int msec);
	}
	
	public synchronized void processEvent(LgEvent e) {
		if (e.getClass() == MouseEnteredEvent3D.class) {
			MouseEnteredEvent3D me = (MouseEnteredEvent3D) e;
			if (me.isEntered()) startTimer();
		    else stopTimer();
		}
	}
	
	private void startTimer()
	{
		stopTimer();
        timer = new Timer();
        timer.schedule(new sendHover(), time);
	}
	
	private void stopTimer()
	{
		if (timer!=null)
		{
			timer.cancel();
			timer = null;
		}
	}
	
	public class sendHover extends TimerTask {
        public void run() {
        	
    		if (listener != null) listener.handleHoverEvent(time);
    		stopTimer();
        }
    }

	@SuppressWarnings("unchecked")
	public Class<LgEvent>[] getTargetEventClasses() {
		Class[] types = new Class[] {MouseEnteredEvent3D.class, MouseMovedEvent3D.class};
		return types;
		
	}


}
