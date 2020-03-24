/*
 * ºîÀ®Æü: 2005/01/31
 *
 */
package nu.koidelab.cosmo.manager;

import java.util.List;

import nu.koidelab.cosmo.util.bind2d.CosmoXMLParser;
import nu.koidelab.cosmo.util.schedule.Plan;
import nu.koidelab.cosmo.wg.nodes.group.TimeRoot;

/**
 * @author fumi ScheduleManager org.manager
 */
public class ScheduleManager {
	private static final String HOLIDAYS_FILE = "nu/koidelab/cosmo/resources/xml/holidays.xml";
//  private TimeRoot root  = new TimeRoot();
    
    /* Test implementation */
    public ScheduleManager(){}
    
    
    public List<Plan> loadSchedule(String fileName) {
//    public void loadSchedule(String fileName) {
//        List<Plan> plans = CosmoXMLParser.loadPlans(fileName);
//        root.setPlans(plans);
        return CosmoXMLParser.loadPlans(fileName);
    }
    
    /* Test implementation */
    public void saveSchedule(String fileName, List<Plan> plans){
//    public void saveSchedule(String fileName){
//        List<Plan> plans = root.getPlans();
        CosmoXMLParser.savePlans(plans, fileName);
    }
    
    /* Test implementation
    public TimeRoot getTimeRoot(){
        return root;
    }        
    */
}
