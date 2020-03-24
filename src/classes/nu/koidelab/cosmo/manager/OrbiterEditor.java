package nu.koidelab.cosmo.manager;

import java.util.ArrayList;
import java.util.LinkedList;

import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import nu.koidelab.cosmo.util.bind2d.LgExplorerSwing;
import nu.koidelab.cosmo.util.bind2d.swing.AddPlanPanel;
import nu.koidelab.cosmo.util.gesture.EditNewPlanGestureModule;
import nu.koidelab.cosmo.util.gesture.menu.AllowMenu;
import nu.koidelab.cosmo.util.gesture.menu.ExtraCSDMenu;
import nu.koidelab.cosmo.util.gesture.menu.MenuIcon;
import nu.koidelab.cosmo.util.gesture.menu.MenuList;
import nu.koidelab.cosmo.wg.Satellite;
import nu.koidelab.cosmo.wg.nodes.group.DayGroup;

import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.sg.Transform3D;
import org.jdesktop.lg3d.utils.c3danimation.NaturalMotionAnimation;
import org.jdesktop.lg3d.utils.eventaction.Component3DMover;
import org.jdesktop.lg3d.utils.shape.Primitive;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.utils.shape.Sphere;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.wg.Cursor3D;

public class OrbiterEditor extends Container3D {
    private LinkedList<BabyPlanet> childrens;
	
	public OrbiterEditor(){
		super();
		init();        
	}
	    
	private void init(){
		childrens = new LinkedList<BabyPlanet>();
		float x = CosmoConfig.SCREEN_WIDTH;
		float y = CosmoConfig.SCREEN_HEIGHT;
        setTranslation(-3*x/8, -y/4, 0);
	}
	
    public void createPlanetChild(String name, AddPlanPanel panel){
    	float[] tmpVec = getNextBirthPosition();
        
        /* Move panel to next birth position. */
        Point3f pos = new Point3f(tmpVec);
        Transform3D trans = new Transform3D();
        getLocalToVworld(trans);
        trans.transform(pos);
        panel.changeTranslation(pos.x, pos.y, pos.z, 800);
        
        panel.setRotationAngle(0);
        panel.setRotationAxis(-1, 1, 0);
        panel.changeRotationAngle((float)Math.PI*2, 800);
        panel.changeTransparency(0.9f, 800);                
        panel.changeScale(0.02f, 800);            
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setAnimation(null);
        panel.setTransparency(1);                
        panel.setScale(0);
        panel.setVisible(false);
        panel.setEnabled(false);
        
        birthBabyPlanet(new BabyPlanet(name, panel.getIdPanel()), tmpVec);
    }
    

    private void birthBabyPlanet(BabyPlanet babyPlanet, float[] location){
        childrens.addLast(babyPlanet);
        addChild(babyPlanet);
        babyPlanet.setTranslation(location[0], location[1], location[2]);
    }
    
    private float[] getNextBirthPosition(){
        int num = childrens.size();        
        float[] tmp = {num*0.01f, num*-0.015f, 0};
        return tmp;
    }
    
    public void addPlanetChild(BabyPlanet child, DayGroup.DateIDPanel panel){    	
        Vector3f diff = panel.getTranslationTo(child, new Vector3f());
        child.setTranslation(diff.x, diff.y, diff.z);        
        child.changeTransparency(0.9f, 200);            
        panel.addNewPlan(child.getName(), child.getPriority(), child.getReference());
        try {
            Thread.sleep(400);                
        } catch (InterruptedException e) {
            e.printStackTrace();
        }                  
        removePlanetChild(child);
    }
        
    public void removePlanetChild(BabyPlanet child){
        childrens.remove(child);
        removeChild(child);
    }
    
    public void launchNewPanel(){
    	AddPlanPanel panel = new AddPlanPanel();
   }
    
    public void launchNewPanel(DayGroup.DateIDPanel idPanel){
    	AddPlanPanel panel = new AddPlanPanel(idPanel);
    }
	                
    public static class BabyPlanet extends Component3D implements AllowMenu{
        private MenuList menu;
        private Component3D body = new Component3D();
        private Appearance planetApp = new SimpleAppearance(1.0f, 0.5f, 0.5f, 0.7f);
        private String name;
        private int priority = 1;
        
        private ArrayList<String> referenceList=new ArrayList<String>();
        private LgExplorerSwing explorer;
        
        private DayGroup.DateIDPanel idPanel;

        private BabyPlanet(String name){
        	this(name, null);
        }
        
        private BabyPlanet(String name, DayGroup.DateIDPanel idPanel){
			super();
            this.name = name;
            this.idPanel = idPanel;
            planetApp.setCapability(Appearance.ALLOW_TEXTURE_WRITE);
            planetApp.setTexture(CosmoConfig.getConfig().getPlanetTexture(priority));			
            body.addChild(new Sphere(CosmoConfig.PLANET_RADIUS,
                    Primitive.GENERATE_TEXTURE_COORDS
                            | Primitive.GENERATE_NORMALS, planetApp));
            body.setAnimation(new NaturalMotionAnimation(400));
			addChild(body);
			setCursor(Cursor3D.SMALL_CURSOR);
            
            menu = new MenuList();
            if(idPanel != null){
                // if this is created from DateID panel, 
            	menu.add(MenuIcon.APPLY);
                menu.add(ExtraCSDMenu.INC_PRIORITY);
                menu.add(MenuIcon.CANCEL);
                menu.add(ExtraCSDMenu.DEC_PRIORITY);
                menu.add(ExtraCSDMenu.ATTACH);
                
                // if you want to enable Gesture, uncomment this,
                // and edit EditNewPlanGestureModule.
//                addListener(new EditNewPlanGestureModule(BabyPlanet.class));
            }else {
                menu.add(ExtraCSDMenu.INC_PRIORITY);
                menu.add(MenuIcon.CANCEL);
                menu.add(ExtraCSDMenu.DEC_PRIORITY);
                menu.add(ExtraCSDMenu.ATTACH);
                addListener(new EditNewPlanGestureModule(BabyPlanet.class));
            }
            addListener(new Component3DMover());
        }        
        
        public String getName(){
            return name;
        }
        
        public int getPriority(){
            return priority;
        }
        
        public void attachTo(DayGroup.DateIDPanel idPanel){
        	if(idPanel == null) throw new IllegalArgumentException("idPanel cannot be null.");
        	this.idPanel = idPanel;
        	CSDGeneralManager.getInstance().getEditor().addPlanetChild(this, idPanel);
        }
        
        public void attachTo(){
            if(idPanel != null)
                CSDGeneralManager.getInstance().getEditor().addPlanetChild(this, idPanel);
            else 
                throw new IllegalStateException("idPanel cannot be null.");
        }

        /*
         * called from LgExplorer.
         */
        public void setFilename(String name){
            explorer.setVisible(false);
            removeChild(explorer);
            addSatelite(name);
        }
        /*
         * called from LgExplorer.
         */
        public void cancel(){
            explorer.setVisible(false);
            removeChild(explorer);
        }
		
        private void addSatelite(String path) {
            System.err.println("CosmoEditor: " + path + " is added.");            
            referenceList.add(path);
            addChild(new Satellite(getPriority() * CosmoConfig.PLANET_RADIUS
                    * 1.1f, path));
        }
        
        public void addPriority(int i){
            if(1 <= priority+i && priority+i <= 5){
                priority = priority + i;
                body.changeScale(priority,500);
                planetApp.setTexture(CosmoConfig.getConfig().getPlanetTexture(priority));
            }
        }
        
        private String getReference(){
            if(referenceList.size()!=0){
                StringBuffer tmp=new StringBuffer();
                for(int i=0;i<referenceList.size();i++){
                    tmp.append(referenceList.get(i));
                    tmp.append(',');
                }
                return tmp.substring(0,tmp.length()-1);
            }
            return "";
        }
        
        public MenuList getMenuList() {        	
        	return menu;
        }
        
        public void processMenu(MenuIcon menu) {        
        	if(menu == ExtraCSDMenu.INC_PRIORITY){
        		addPriority(1);
        	} else if(menu == ExtraCSDMenu.DEC_PRIORITY){
        		addPriority(-1);
        	} else if(menu == MenuIcon.CANCEL){
        		CSDGeneralManager.getInstance().getEditor().removePlanetChild(this);
        	} else if(menu == ExtraCSDMenu.ATTACH){
                explorer = new LgExplorerSwing(this);
                addChild(explorer);
        	} else if(menu == MenuIcon.APPLY){
        		attachTo();
        	}        		
        }                
	}    
}
