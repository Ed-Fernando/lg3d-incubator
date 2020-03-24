/*
 * LG3DMP3Player.java
 *
 * Created on 2005/11/05, 15:28
 *
 * MP3 Player for LG3D application.
 * This class throw LG3D Event.
 */

package org.jdesktop.lg3d.apps.trumplayer.base;

//import org.jdesktop.lg3d.apps.trumplayer.mp3player.MP3Player;
import org.jdesktop.lg3d.apps.trumplayer.mp3player.JLayerMP3Player;
import org.jdesktop.lg3d.apps.trumplayer.mp3player.FileInfoBean;

import org.jdesktop.lg3d.wg.event.LgEvent;
import org.jdesktop.lg3d.wg.event.LgEventSource;
import org.jdesktop.lg3d.utils.eventadapter.EventAdapter;
import org.jdesktop.lg3d.utils.action.ActionNoArg;

import org.jdesktop.lg3d.wg.Component3D;

import org.jdesktop.lg3d.displayserver.EventProcessor;

import java.util.AbstractList;

/**
 *
 * @author Yasuhiro Fujitsuki(thaniwa)
 */
//public class LG3DMP3Player extends MP3Player implements LgEventSource {
public class LG3DMP3Player extends JLayerMP3Player implements LgEventSource {
    
    Component3D component;
    
    /** Creates a new instance of LG3DMP3Player */
    public LG3DMP3Player() {
        super();
    }
    
    public LG3DMP3Player(String encode){
        super(encode);
    }
    
    public void play(AbstractList<FileInfoBean> list,int current){
        super.play(list,current);
        fireMusicChangeEvent();
    }
        
    public void postEvent(LgEvent event){
        EventProcessor ep = EventProcessor.processor();
        ep.postEvent(event,this.component);
    }

    private void fireMusicChangeEvent(){
        MusicChangeEvent event = new MusicChangeEvent(this.component);
        postEvent(event);
    }
    
    public MusicChangeEventAdapter getMusicChangeEventAdapter(ActionNoArg action, Component3D component){
        this.component = component;
        return new MusicChangeEventAdapter(action);
    }
    
    // events for LG3DMusicPlayer
    public class MusicChangeEvent extends LgEvent {
        LgEventSource source;        

        public MusicChangeEvent(LgEventSource source){
            this.source = source;
        }
        
        public LgEventSource getSource(){
            return source;
        }

        public Class getSourcesClass(){
            return source.getClass();
        }
       
    }

    // event adapter for LG3DMusicPlayer
    public class MusicChangeEventAdapter implements EventAdapter {
        
        private ActionNoArg action;

        public MusicChangeEventAdapter(ActionNoArg action){
            this.action = action;
        }
        
        public Class[] getTargetEventClasses(){
            Class[] targetEventClasses =
            { MusicChangeEvent.class };
            return targetEventClasses;
        }
        
        public void processEvent(LgEvent event) {
            if ( event instanceof MusicChangeEvent ){
                action.performAction(event.getSource());
            }
        }

    }

}
