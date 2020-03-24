package nu.koidelab.cosmo.wg.nodes;

import java.util.TimerTask;

import nu.koidelab.cosmo.manager.CSDGeneralManager;
import nu.koidelab.cosmo.manager.CosmoConfig;
import nu.koidelab.cosmo.manager.eventmanager.CSDEvent;
import nu.koidelab.cosmo.util.schedule.Plan;
import nu.koidelab.cosmo.wg.Satellite;
import nu.koidelab.cosmo.wg.nodes.group.DayGroup;
import nu.koidelab.cosmo.wg.shape.NameTag;

import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.sg.Geometry;
import org.jdesktop.lg3d.sg.Material;
import org.jdesktop.lg3d.sg.Shape3D;
import org.jdesktop.lg3d.utils.eventadapter.EventAdapter;
import org.jdesktop.lg3d.utils.shape.Primitive;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.utils.shape.Sphere;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.event.LgEvent;
import org.jdesktop.lg3d.wg.event.LgEventConnector;
import org.jdesktop.lg3d.wg.event.LgEventSource;
import org.jdesktop.lg3d.wg.event.MouseButtonEvent3D;
import org.jdesktop.lg3d.wg.event.MouseEnteredEvent3D;
import org.jdesktop.lg3d.wg.event.MouseEvent3D;

public class Planet extends EditableOrbiter{
    private static Geometry bodyGeom;
    static{
        // shared geometry data 
        Sphere sp = new Sphere(CosmoConfig.PLANET_RADIUS, Primitive.GENERATE_NORMALS
                | Primitive.GENERATE_TEXTURE_COORDS, null);
        bodyGeom = sp.getShape().getGeometry();
        LgEventConnector.getLgEventConnector().addListener(
//                Planet.Body.class, new PlanetEventHandler());
        		Planet.class, new PlanetEventHandler());
    }
    
    private Appearance app;
    private Material mat;
    
    public Planet(Plan p){
    	super(p, new Body());
        initialize();
    }
    
    private void initialize(){
        /* Initialize planet body  */
        Shape3D bodyShape = new Shape3D(bodyGeom);        
        app = new SimpleAppearance(1.0f, 1.0f, 1.0f, 1.0f,
                SimpleAppearance.ENABLE_TEXTURE);
        app.setCapability(Appearance.ALLOW_TEXTURE_WRITE);
        app.setTexture(CosmoConfig.getConfig().getPlanetTexture(plan.getPriority()));
        mat = app.getMaterial();
        mat.setCapability(Material.ALLOW_COMPONENT_WRITE);
        mat.setAmbientColor(0.08f, 0.08f, 0.08f);
        mat.setSpecularColor(0.2f, 0.2f, 0.2f);
        mat.setShininess(16.0f);        
        app.setMaterial(mat);
        bodyShape.setAppearance(app);        
        bodyComp.addChild(bodyShape);
        
        
        // make satelite(s). these represent files referenced by this plan.
        float radius = plan.getPriority() * CosmoConfig.PLANET_RADIUS *1.1f;
        if (!plan.getReference().equals("")) {
            addChild(new Satellite(radius, plan.getReference()));
        }        
       setNameTag(true);        
    }  
        
    @Override
    public boolean addPriority(int i) {
        boolean result = super.addPriority(i);
        if(result)
            app.setTexture(CosmoConfig.getConfig().getPlanetTexture(plan.getPriority()));
        return result;
    }    
    
    @Override
    public void setNameTag(boolean visible) {
        if(nameTag == null){
            float radius = plan.getPriority() * CosmoConfig.PLANET_RADIUS *1.1f;
            nameTag = new NameTag(radius, plan.getName(), plan.getPriority());
            addChild(nameTag);
        } else {
        	nameTag.setVisible(visible);
        }
    }
    
    @Override
    protected void resetAppearance() {
        app.setTexture(CosmoConfig.getConfig().getPlanetTexture(plan.getPriority()));
    }
    
    private RotateAction rotateAction;
    @Override
    public void setGhostMode(boolean isRotating){
        // if you switch mode of operation, this planet starts rotating. 
        super.setGhostMode(isRotating);
        if(isRotating){
            if(rotateAction == null){
                rotateAction = new RotateAction(bodyComp);
                CSDGeneralManager.getInstance().getTimer().scheduleAtFixedRate(rotateAction, 0, 50);
                mat.setSpecularColor(1, 1, 1);
                mat.setEmissiveColor(0.3f, 0.3f, 0.33f);
            }            
        } else {
            if(rotateAction != null){
              rotateAction.cancel();
              rotateAction = null;
              mat.setSpecularColor(0.2f, 0.2f, 0.2f);
              mat.setEmissiveColor(0, 0, 0);
            }
        }
    }
    
    private static class RotateAction extends TimerTask{
        private static final float SPEED = ((float) Math.toRadians(1));
        private static final float MAX_ANGLE = ((float) Math.toRadians(360));
        private Component3D body;
        private float angle = 0;
        
        RotateAction(Component3D body){
            this.body = body; 
            body.setRotationAxis(0.0f, 1.0f, 0.0f);
        }
        
        public void run(){
            angle += SPEED;
            if (angle > MAX_ANGLE){
                angle = angle - MAX_ANGLE;
            }
            body.setRotationAngle(angle);
        }
    }
    
    private static class PlanetEventHandler implements EventAdapter{     
        private Class[] targets = {MouseButtonEvent3D.class, MouseEnteredEvent3D.class};
        private Thread thread;
        
        public Class[] getTargetEventClasses() {
            return targets;
        };
        
        public void processEvent(LgEvent evt) {        	
            MouseEvent3D me = (MouseEvent3D) evt;            
            Planet p = (Planet)(me.getIntersectedComponent3D(0, Planet.class));            
            if(p == null || p.panel != null) return;
                        
            if (evt instanceof MouseButtonEvent3D) {               
                MouseButtonEvent3D mbe = (MouseButtonEvent3D) evt;
                if (mbe.isClicked() && mbe.getClickCount() == 2) {
                    thread = null;
                    /* Double clicked event  process*/
                    /* ======== Do Nothing ====== */
                } else if (mbe.isClicked() && mbe.getClickCount() == 1) {
                    startSingleClickTimer(mbe);
                }
                return;
            }
            
            if (evt instanceof MouseEnteredEvent3D) {
                MouseEnteredEvent3D mee = (MouseEnteredEvent3D) evt;
                /* change planet size bigger when mouse enters. */
                if (mee.isEntered()) {
                    if(!p.isGhostMode()){
                    	/* --- display cursor --- */
                        CSDGeneralManager.getInstance().getCursorManager().addCursor(
                                new OrbiterTargettingCursor(p));
                        p.bodyComp.changeScale(p.plan.getPriority() * 1.1f, 200);
                        p.mat.setAmbientColor(0.65f, 0.8f, 0.8f);
                    }
                    p.setNameTag(false);
                } else {
                	if(p != null){
                		p.bodyComp.changeScale(p.plan.getPriority(), 200);
						p.mat.setAmbientColor(0.08f, 0.08f, 0.08f);
						CSDGeneralManager.getInstance().getCursorManager().removeCursors(p, OrbiterTargettingCursor.class);
						p.setNameTag(true);
					}
                }
            }
        }
        
        private void startSingleClickTimer(MouseButtonEvent3D mbe){
            thread = new Thread(new SingleClickEventHandler(mbe));
            thread.start();
        }
        
        /* Do event processing only when single-click events occur. */
        private class SingleClickEventHandler implements Runnable{
            MouseButtonEvent3D mbe;
            private SingleClickEventHandler(MouseButtonEvent3D mbe){
                this.mbe = mbe;
            }
            
            public void run() {                
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }               
                if(thread == null)/* Do nothing if null is assigned to thread by double-click events  */
                    return;
                                
                /* Process when single-click events occur */                                
                Planet p = ((Planet)(mbe.getIntersectedComponent3D(0, Planet.class)));
                if(p.isRepresentative){
                    CSDEvent evt = new RearrangeEvent(DayGroup.OrbiterLayout.TATE, p);
                    CSDGeneralManager.getInstance().postEvent(evt);
                }
            }
        }
    }
    
    public static class RearrangeEvent extends CSDEvent{
        private Object constraints;

        private RearrangeEvent(Object newConstraints, LgEventSource src) {
            super(src);
            constraints = newConstraints;
        }
        
        public Object getConstraints() {
            return constraints;
        }        
    }        
}
