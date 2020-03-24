package nu.koidelab.cosmo.wg.nodes.group;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import nu.koidelab.cosmo.manager.CSDGeneralManager;
import nu.koidelab.cosmo.manager.eventmanager.CSDEvent;
import nu.koidelab.cosmo.manager.eventmanager.CSDEventListener;
import nu.koidelab.cosmo.util.schedule.Plan;
import nu.koidelab.cosmo.wg.nodes.EditableOrbiter;
import nu.koidelab.cosmo.wg.nodes.Planet.RearrangeEvent;

import org.jdesktop.lg3d.wg.Container3D;

public class TimeRoot extends Container3D {
    static final int YEAR_UNVISIBLE = 0x10;
    static final int MONTH_UNVISIBLE = 0x11;
    private List<YearGroup> yearGroups = new ArrayList<YearGroup>(3);
    private List<Plan> plans = new LinkedList<Plan>();
    
    /* arguments for Test implementation*/
    int currentYear = 0;                
    int currentMonth = 0;                

    
    public TimeRoot(){
        CSDGeneralManager.getInstance().addListener(new FocusChangeEventHandler());        
        CSDGeneralManager.getInstance().addListener(new RearrangeEventHandler());        
    }
    
    public void addPlans(List<Plan> target){
        for (int i = target.size() - 1; i >= 0; i--) {
            addPlan(target.get(i));
        }
    }        
            
    private int getIndexOf(int year){
        int index = isExist(year);
        if(index < 0){
            YearGroup y = new YearGroup(year, this, plans);
            yearGroups.add(y);
            addChild(y);
            index = yearGroups.size() - 1;
        } 
        return index;
    }
    
    public void setActiveOnly(int[] selectedYears){
        int[] selectedIndices = yearsToIndices(selectedYears);
        
        for (int i = yearGroups.size() - 1; i >= 0; i--) {
            boolean isExist = false;
            for (int j = selectedIndices.length - 1; j >= 0; j--) {
                if(i == selectedIndices[j])
                    isExist = true;
            }
            if(isExist){
                yearGroups.get(i).setMode(Calendar.YEAR);
            } else {
                yearGroups.get(i).setMode(TimeRoot.YEAR_UNVISIBLE);
            }
        }
    }
    
    private int[] yearsToIndices(int[] years){
        int[] indices = new int[years.length];        
        for (int i = years.length - 1; i >= 0; i--) {
            indices[i] = getIndexOf(years[i]);
        }
        return indices;
    }
    
    public void setActiveOnly(int[] selectedYears, int[][] selectedmonths, int mode){
        int[] selectedIndices = yearsToIndices(selectedYears);
        
        for (int i = yearGroups.size() - 1; i >= 0; i--) {
            int index = -1;
            for (int j = selectedIndices.length - 1; j >= 0; j--) {
                if(i == selectedIndices[j])
                    index = j;
            }
            if(index >= 0){                
                yearGroups.get(i).setActiveOnly(selectedmonths[index], mode);
            } else {
                yearGroups.get(i).setMode(TimeRoot.YEAR_UNVISIBLE);
            }
        }
    }
    
    public List<Plan> getPlans() {
        List<Plan> plans = new LinkedList<Plan>();
        for (int i = yearGroups.size() - 1; i >= 0; i--) {
            plans.addAll(yearGroups.get(i).getPlans());
        }
        plans.addAll(this.plans);
        System.err.println("return Time  / " + plans.size());
        return plans;
    }
        
    /** if not exist then return -1 */
    public int isExist(int year){
        int index = -1;
        for (int i = yearGroups.size() - 1; i >= 0; i--) {
            if(yearGroups.get(i).getYear() == year){
                index = i;
            }
        }
        return index;
    }
    
    /** if not exist then return -1 */
    public int isExist(long msec){
        int index = -1;
        for (int i = yearGroups.size() - 1; i >= 0; i--) {
            if(yearGroups.get(i).isInRange(msec)){
                index = i;
            }
        }
        return index;
    }
    
    public void addPlan(Plan newPlan){
        long time = newPlan.getTime();
        int index = isExist(time);
        if(index >= 0){
            yearGroups.get(index).addPlan(newPlan);
        } else {
            plans.add(newPlan);
        }
    }
    
    public void removePlan(Plan plan){
        long time = plan.getTime();
        int index = isExist(time);
        if(index >= 0){
            yearGroups.get(index).removePlan(plan);
        } else {
            plans.remove(plan);
        }
    }
            
    
    private class RearrangeEventHandler implements CSDEventListener{
        private Class[] targets = {RearrangeEvent.class}; 
        public Class[] getTargetEventClasses() {
            return targets;
        }
        public void processEvent(CSDEvent evt) {
            assert(evt instanceof RearrangeEvent);
            
            EditableOrbiter eo = (EditableOrbiter)(evt.getSource());
            Object constraints = ((RearrangeEvent)evt).getConstraints();
            
            long time = eo.getPlan().getTime();
            int yearIdx = isExist(time);
            if(yearIdx >= 0){
                YearGroup year = yearGroups.get(yearIdx);
                int monthIdx = year.isExist(time);
                if(monthIdx >= 0){
                    MonthGroup month = year.getMonthGroup(monthIdx);
                    int dayIdx = month.getIndexOf(time);
                    if(dayIdx >= 0){
                        DayGroup day = month.getDayGroup(dayIdx);
                        day.rearrangeChildLayout(eo, constraints);
                        getLayout().rearrangeLayoutComponent(eo, constraints);
                    }
                }
            }
        }
    }
            
    private class FocusChangeEventHandler implements CSDEventListener{
        private Class[] targets = {CSDGeneralManager.RepaintPlanetsEvent.class};
        private Calendar cal = Calendar.getInstance();
        
        public Class<CSDEvent>[] getTargetEventClasses() {
            return targets;
        }
        
        public void processEvent(CSDEvent evt) {
            CSDGeneralManager.RepaintPlanetsEvent rpe = (CSDGeneralManager.RepaintPlanetsEvent)evt;            
            if(rpe.isFocusChanged()){
                changeFocus(rpe.getFocusedTime());
            } else {
                update();
            }
        }
        
        private void update() {
            select(currentYear, currentMonth, CSDGeneralManager.getInstance().getFunction().getMode());
        }
        
        private void changeFocus(long msec){           
            int mode = CSDGeneralManager.getInstance().getFunction().getMode();
            cal.setTimeInMillis(msec);
            int year = cal.get(Calendar.YEAR);        
            int month = cal.get(Calendar.MONTH);
            
            if(mode == Calendar.YEAR){
                if(currentYear == year){
                    return;
                }else{
                    currentYear = year;
                    currentMonth = month;
                }
            }else{
                if(currentYear == year && currentMonth == month){
                    return;                
                }else{
                    currentYear = year;
                    currentMonth = month;
                }
            }
            select(year, month, mode);
        }
        
        private void select(int year, int month, int mode){
            /* === YEAR Mode === */
            if(mode == Calendar.YEAR){
                int[] items = {year - 1, year, year + 1};
                setActiveOnly(items);
                return;
            }
            
            /* === MONTH Mode ===*/
            if(mode == Calendar.MONTH){
                visible(3, year, month, mode);
                return;
            }
            
            /* === DAY_OF_MONTH Mode and WEEK_OF_MONTH Mode === */  
            visible(1, year, month, mode);
        }
        
        
        private void visible(int displayLength, int year, int month, int mode){
            if(displayLength >= 7)
                throw new IllegalArgumentException("Not implemented for dispLayLength more than 7"); 

            if(month - displayLength < 0){
                int[] years = {year-1, year};
                int[][] months = new int[2][];
                months[0] = new int[displayLength - month];
                for(int i = 0; i < months[0].length; i++){
                    months[0][i] = month - displayLength + i + 12;                       
                }
                months[1] = new int[month+1+displayLength];
                for(int i = 0; i < months[1].length; i++){
                    months[1][i] = i;                       
                }
                
                setActiveOnly(years, months, mode);
                return;
            }        
            
            if(month + displayLength > 11){            
                int[] years = {year, year+1};  
                int[][] months = new int[2][];
                months[0] = new int[displayLength + 1 + 11 - month];
                for(int i = 0; i < months[0].length; i++){
                    months[0][i] = month - displayLength + i ;                       
                }                        
                months[1] = new int[-11 + month + displayLength];
                for(int i = 0; i < months[1].length; i++){
                    months[1][i] = i;                       
                }
                
                setActiveOnly(years, months, mode);
                return;
            }
            
            int[] years = {year};
            int[][] months = new int[1][displayLength * 2 + 1];
            for(int i = 0; i < months[0].length; i++){
                months[0][i] = month - displayLength + i;
            }            
            setActiveOnly(years, months, mode);
        }
    }
                
}


