package org.jdesktop.lg3d.apps.physics;
import java.lang.Thread;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;
import java.util.PriorityQueue;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Frame3D;
import java.util.HashSet;
/*
 * PhysicsThread.java
 *
 * Created on den 26 juni 2006, 18:18
 *
 */



/**
 * A thread that handles the simulation of all physical objects in a physical space. 
 *
 * @author Tobias Evert
 */
public class PhysicsThread extends Thread {
    
    
    
    private Vector<PhysicsObject> objects;
    private Vector<Collideable> collideables;
    private float fps; 
    private Vector<Contact> contacts;
    private CollisionResolver contactResolver;
    private Component3D app;
    private Vector<Effector> effectors;
    private HashSet<Contact> unresolved;
    private Frame3D applicationFrame;
    private int numIters;
    
    
    /** Creates a new instance of PhysicsThread 
     *  The argument numIters is the number of times the simulation iterates through all contacts in each frame, gradually solving them. 
     *  More iterations results in a slower but better simulation. Try around 15 first.
     *
     *  The Frame3D argument is the container for the application using this thread. 
     *  This was the only way I could find to stop the thread when an application was closed, there is probably a better way.
     *
     *  @param numIters the number of iterations through the contacts in each frame.
     *  @param Frame3D the root container for the application using this thread for simulations.
     */
    
    public PhysicsThread(int numIters, Frame3D applicationFrame) {
        objects=new Vector<PhysicsObject>();
        collideables=new Vector<Collideable>();
        contacts=new Vector<Contact>();
        fps=200.0f;
        contactResolver=new ImpulseResolver();
        effectors=new Vector<Effector>();
        unresolved=new HashSet<Contact>();
        this.applicationFrame=applicationFrame;
        this.numIters=numIters;
    }
    
    
    
    /**
     * Sets the desired maximum frames per second.
     * If adaptive time step is used this determines the minumum time step that will be allowed.
     */
    
    void setMaxFPS(float fps) {
        this.fps=fps;
    }
    
    public void run() {
        boolean running=true;
        while(running) {
            Date before;
            Date after;
            float timeStep=0.01f;
            
            before=new Date();
            contacts.clear();
            unresolved.clear();
            for (int i=0; i<collideables.size()-1;i++) 
                for (int j=i+1;j<collideables.size();j++)  
                            collideables.get(i).collide(collideables.get(j), contacts);
            for(int i=0;i<contacts.size();i++)
               contacts.get(i).flagSeparating();
            for(int j=0;j<numIters;j++) {
                for(int i=0;i<contacts.size();i++) {
                          if(!contacts.get(i).isSeparating())
                              contactResolver.resolve(contacts.get(i));
                    
                }
                for(int i=0;i<contacts.size();i++) 
                    contacts.get(i).updateFlag();
                
                
                
                    
            }
            
            Iterator <Effector> effector;
            for (effector=effectors.iterator();effector.hasNext();)
                effector.next().apply();
            for(int i=0;i<objects.size();i++) 
                 objects.get(i).updateVelocity(timeStep);
                
            
            for(int i=0;i<contacts.size();i++) 
                    contacts.get(i).flagSeparating();
            //Put remaining contacts to rest
            for (int j=1;j<11;j++) 
                for(int i=0;i<contacts.size();i++)
                    if(!contacts.get(i).isSeparating())
                        contactResolver.putToRest(contacts.get(i),0.1f*j);
                                           
            for(int i=0;i<objects.size();i++)
                //if(!objects.get(i).isAtRest())
                    objects.get(i).updatePosition(timeStep);
           
            
           for(int i=0;i<contacts.size();i++)
               unresolved.add(contacts.get(i));
           Contact contact;
           for(int i=1;i<11;i++) {
                for(Iterator<Contact> iterator=unresolved.iterator();iterator.hasNext();){
                    contact=iterator.next();
                    if(contact.update())
                        //iterator.remove();      
                    //else
                       contactResolver.separate(contact,i*0.1f);
                }
           }
           for(int i=0;i<objects.size();i++)
                objects.get(i).updateGraphics();
              
             
            after=new Date();
            long millis=after.getTime()-before.getTime();
            timeStep=millis/100000000.0f;
            running=applicationFrame.isLive(); //There is probably som better way to do this with events.
              
            if (millis<1000/fps) {
                try{
                  sleep((long)(1000/fps) - millis);
                }
                catch (InterruptedException e) {}
                timeStep=.1f/fps;
               
                
            }
        
        }
        
   
    }
    
    public void addCollideable(Collideable collideable, PhysicsObject  object) {
        collideables.addElement(collideable);
        objects.addElement(object);
    }
    
    public void addEffector(Effector e) {
        effectors.addElement(e);
    }
}
