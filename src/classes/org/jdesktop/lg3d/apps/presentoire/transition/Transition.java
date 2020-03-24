package org.jdesktop.lg3d.apps.presentoire.transition;

import org.jdesktop.lg3d.wg.Component3D;

/**
 * The base class of every Transition.
 * It implements a thread to run a transition without freezing other parts of Presentoire... 
 * @author Pierre Ducroquet
 *
 */
public class Transition {
	
	/**
	 * Really do the transition. Executed by the thread.
	 * Here it's a no-transition transition :)
	 * Should never be called directly.
	 * @param mainComponent the main Presentoire component3D
	 * @param slideContainer the container for the slide component3D
	 * @param nextSlide the next slide component3D
	 */
	private void runTransition(Component3D mainComponent, Component3D slideContainer, Component3D nextSlide) {
		slideContainer.removeAllChildren(); 
		slideContainer.addChild(nextSlide);
	}
	
	/**
	 * Launch the thread for the transition.
	 * @param mainComponent the main Presentoire component3D
	 * @param slideContainer the container for the slide component3D
	 * @param nextSlide the next slide component3D
	 */
	public void executeTransition(Component3D mainComponent, Component3D slideContainer, Component3D nextSlide) {
		System.out.println("Launching the thread then...");
		new TransitionThread(mainComponent, slideContainer, nextSlide).start();
	}
	
	/**
	 * This is the Thread for transitions. Extremely simple, so no doc needed :)
	 * @author Pierre Ducroquet
	 *
	 */
	private class TransitionThread extends Thread {
		
		private Component3D mainComponent, slideContainer, nextSlide;
		
		public TransitionThread(Component3D _mainComponent, Component3D _slideContainer, Component3D _nextSlide) {
			mainComponent = _mainComponent;
			slideContainer = _slideContainer;
			nextSlide = _nextSlide;
		}
		
		public void run() {
			runTransition(mainComponent, slideContainer, nextSlide);
		}
		
	}
}
