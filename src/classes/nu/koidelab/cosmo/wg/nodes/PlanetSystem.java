package nu.koidelab.cosmo.wg.nodes;

import java.util.List;

import nu.koidelab.cosmo.manager.ScheduleManager;
import nu.koidelab.cosmo.util.schedule.Plan;
import nu.koidelab.cosmo.wg.FixedStar;
import nu.koidelab.cosmo.wg.nodes.group.TimeRoot;

import org.jdesktop.lg3d.wg.Container3D;

/**
 * @author fumi
 * 
 * 
 */
public class PlanetSystem extends Container3D {
    /* for testing */
    public static final String SCHEDULE_FILE = "nu/koidelab/cosmo/resources/xml/schedule.xml";
    public static final String SAVE_SCHEDULE_FILE = "nu/koidelab/cosmo/resources/xml/schedule.xml";
    private ScheduleManager sm = new ScheduleManager();
    private TimeRoot root  = new  TimeRoot();

    PlanetSystem(){
        addChild(new FixedStar());
        addChild(root);
    }
    
    public void loadSchedule(String filename){
        List<Plan> plans = sm.loadSchedule(filename);
        root.addPlans(plans);
    }
            
    /* for Tets implementation 
     * Should the Planet System have these methods? */
    public void addPlan(Plan newPlan){
        root.addPlan(newPlan);
    }
    
    public void removePlan(Plan plan){
        root.removePlan(plan);
    }
    
    public void saveSchedule(String filename){
        sm.saveSchedule(filename, root.getPlans());
    }
}
