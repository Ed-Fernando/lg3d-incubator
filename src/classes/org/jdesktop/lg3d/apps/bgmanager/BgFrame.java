package org.jdesktop.lg3d.apps.bgmanager;

import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.prefs.Preferences;
import org.jdesktop.lg3d.utils.action.ActionBooleanInt;
import org.jdesktop.lg3d.utils.eventadapter.KeyPressedEventAdapter;
import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.wg.Frame3D;
import org.jdesktop.lg3d.wg.LayoutManager3D;
import org.jdesktop.lg3d.wg.Thumbnail;
import org.jdesktop.lg3d.wg.event.InputEvent3D;
import org.jdesktop.lg3d.wg.event.LgEventSource;
import javax.vecmath.Vector3f;
import org.jdesktop.lg3d.utils.prefs.LgPreferencesHelper;

public class BgFrame extends Frame3D {
    public enum  BgViews{ ELLIPSE,TILLED,MOUSE_TRANSLATION,CUSTOM };
    private static final float BGFRAME_SIZE = 0.08f;
    private BgTypes bgtype;
    private BgSorting sort;
    private ArrayList <BgLgComponent>bgCompList;
    private String modelDir;
    private Preferences prefs;
    private BgSorting nextCont;
    private SharedMenuContainer mc;
    private BgViews currentView;
    private BgTypes currentBgType;
    public BgFrame(ArrayList bgCompList){
        
        this.bgCompList=bgCompList;
        setPreferredSize(new Vector3f(BGFRAME_SIZE, BGFRAME_SIZE, BGFRAME_SIZE));
        prefs = LgPreferencesHelper.userNodeForPackage(getClass());
        
        prefs(true);
        
        
        changeEnabled(true);
        changeVisible(true);
        
        
        this.addListener(
                new KeyPressedEventAdapter(InputEvent3D.ModifierId.ALT,
                new ActionBooleanInt() {
            public void performAction(LgEventSource source,
                    boolean pressed, int key) {
                if (pressed && key == KeyEvent.VK_F4) {
                    close();
                }
            }
        }));
        
        showView(currentView);
    }
    public void showView(BgViews view ){
        
        if(view != currentView){
                detachAll();
                nextCont.changeEnableContainer(true);
            switch (view) {
             
                case ELLIPSE:{                    
                       
                        nextCont = new EllipseContainer(bgCompList, prefs);
                      
                    break;
                } case TILLED:{
                      
                        nextCont = new TilleContainer(bgCompList, prefs);
                    
                    break;
                } case MOUSE_TRANSLATION:{
                    
                       nextCont = new TilleContainer(bgCompList, prefs);
                       
                    break;
                }
            }
                 
                    addChild((Container3D)nextCont);
                    nextCont.changeEnableContainer(false);
                    nextCont.showBg(currentBgType);
                    //nextCont = null;
            if(mc == null){
                mc = new SharedMenuContainer((BgSorting)nextCont,this);
                addChild(mc);
               mc.changeComponentTranslation((BgSorting)nextCont);
            }else {
                mc.changeComponentTranslation((BgSorting)nextCont);
            }
            
            
        
        }else if(view == currentView){
            detachAll();
            nextCont = new EllipseContainer(bgCompList, prefs);
            addChild((Container3D)nextCont);
            nextCont.showBg(currentBgType);
               if(mc == null){
                mc = new SharedMenuContainer((BgSorting)nextCont,this);
                addChild(mc);
            }else {
                mc.changeComponentTranslation((BgSorting)nextCont);
            }
            
            
        }
        
        currentView = view;
    }
    private void detachAll(){ //simlar method 
      //nextCont = null;
        int size = bgCompList.size();
        for(int i = 0; i < size;i++){
            if(bgCompList.get(i).isLive()){
                bgCompList.get(i).detach();
             //usuwanie listenerow
            }
        }
    }
    public void close(){
        prefs(false);
        this.changeEnabled(false);
        
    }
    
    private class BgThumbnail extends Thumbnail {
        
        public  BgThumbnail(URL fileName) {
            
        }
    }
    public void setBgType(BgTypes currentBgType){
        this.currentBgType = currentBgType;
    }
    
    //REMINDER  sholud use more toString() methods
    
    private void prefs(boolean isRead){
        //BGTYPE,LAYOUT
        if (isRead){
            String  initialView = prefs.get("BgView" , "ELLIPSE");
            
            if( initialView.equals("TILLED")){
                currentView = BgViews.TILLED;
            }else if ( initialView.equals("ELLIPSE")){
                currentView = BgViews.ELLIPSE;
            }else if ( initialView.equals("MOUSE_TRANSLATION")){
                currentView = BgViews.MOUSE_TRANSLATION;
            } else {
                currentView = BgViews.ELLIPSE; //Default
            }
            
            String  initialSort = prefs.get("BgSort" , "ALLBG");
            
            if( initialSort.equals( "ALLBG")){
                currentBgType = BgTypes.ALLBG;
            }else if ( initialSort.equals("PANORAMABG")){
                currentBgType = BgTypes.PANORAMABG;
            }else if ( initialSort.equals("LAYEREDBG")){
                currentBgType = BgTypes.LAYEREDBG;
            } else if ( initialSort.equals("SIMPLEBG")){
                currentBgType = BgTypes.SIMPLEBG;
            } else {
                currentBgType = BgTypes.ALLBG; //Default
            }
            
        }else {
            
            if(currentView.equals(BgViews.ELLIPSE)){
                prefs.put("BgView","ELLIPSE");
            }else if(currentView.equals(BgViews.TILLED)){
                prefs.put("BgView","TILLED");
            }else if(currentView.equals(BgViews.MOUSE_TRANSLATION)){
                prefs.put("BgView","MOUSE_TRANSLATION");
            } else {
                prefs.put("BgView","ELLIPSE"); //Default
            }
            
            if( currentBgType.equals(BgTypes.ALLBG)) {
                prefs.put("BgSort","ALLBG");
            }else if(currentBgType.equals(BgTypes.PANORAMABG)){
                prefs.put("BgSort","PANORAMABG");
            }else if ( currentBgType.equals(BgTypes.LAYEREDBG)){
                prefs.put("BgSort","LAYEREDBG");
            }else if (currentBgType.equals(BgTypes.SIMPLEBG)){
                prefs.put("BgSort","SIMPLEBG");
            }else {
                prefs.put("BgSort","ALLBG"); //Default
            }
        }
    }
    
}
