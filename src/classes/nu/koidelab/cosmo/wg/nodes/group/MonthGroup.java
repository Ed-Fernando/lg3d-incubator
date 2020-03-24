package nu.koidelab.cosmo.wg.nodes.group;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import nu.koidelab.cosmo.manager.CSDGeneralManager;
import nu.koidelab.cosmo.util.function.OrbitFunction;
import nu.koidelab.cosmo.util.function.decoration.MonthOrbitDecoration;
import nu.koidelab.cosmo.util.gesture.menu.AllowMenu;
import nu.koidelab.cosmo.util.gesture.menu.ExtraCSDMenu;
import nu.koidelab.cosmo.util.gesture.menu.MenuIcon;
import nu.koidelab.cosmo.util.gesture.menu.MenuList;
import nu.koidelab.cosmo.util.schedule.Plan;
import nu.koidelab.cosmo.wg.decorate.LightDecoration;
import nu.koidelab.cosmo.wg.decorate.OrbitLine;
import nu.koidelab.cosmo.wg.nodes.Orbiter;
import nu.koidelab.cosmo.wg.shape.TextFieldPanel;

import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.wg.LayoutManager3D;

public class MonthGroup extends TimeGroup {
    private static final String[] MONTH_ID = {
        "January", "February", "March",
        "April", "May", "June",
        "July", "August", "September",
        "October", "November", "December", 
     };

    private int month;
    private List<DayGroup> days;
    private YearGroup parent;
    private OrbitLine line;
    private LightDecoration ld;
    private MonthIDPanel idPanel;
    private MonthOrbitDecoration deco;
    
    private static MenuList menu = new MenuList();
    static{
        menu.add(ExtraCSDMenu.ZOOM_OUT);
        menu.add(MenuIcon.NULL_MENU);
        menu.add(ExtraCSDMenu.ZOOM_IN);
        menu.add(MenuIcon.NULL_MENU);
    }
        
    MonthGroup(long msec, YearGroup yg, List<Plan> planList){
        parent = yg;
        
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTimeInMillis(msec);
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1, 0, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        st = cal.getTimeInMillis();
        month = cal.get(Calendar.MONTH);
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, 1, 0, 0, 0);
        ed = cal.getTimeInMillis();
        
        pickoutPlans(planList);
        
        /* Test implementation */
        deco = new MonthOrbitDecoration(st, ed, getMonthColor());
        addChild(deco);
    }
    
    MonthGroup(int month, YearGroup yg, List<Plan> planList){
        parent = yg;
        
        int year = yg.getYear();
        Calendar cal = GregorianCalendar.getInstance();
        cal.set(year, month, 1, 0, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        st = cal.getTimeInMillis();
        this.month = month;
        System.err.println("*** An instanse of MonthGroup " + year + "/" +month + " is created.");
        cal.set(year, month+1, 1, 0, 0, 0);        
        ed = cal.getTimeInMillis();
        
        pickoutPlans(planList);  
                
        setLayout(new LayoutManager3D(){
        	private List<Orbiter> compList = new ArrayList<Orbiter>();
        	public void addLayoutComponent(Component3D comp, Object constraints) {
        		if (comp instanceof Orbiter)
					compList.add((Orbiter) comp);
        	}
        	public void layoutContainer() {
        		OrbitFunction func = CSDGeneralManager.getInstance().getFunction();
        		for(Orbiter o : compList){
        			func.setPosition(o);
        		}
        	}
        	public boolean rearrangeLayoutComponent(Component3D comp, Object newConstraints) {
        		return false;
        	}
        	public void removeLayoutComponent(Component3D comp) {}
        	public void setContainer(Container3D cont) {}
        });
        
        /* Test implementation */ 
        deco = new MonthOrbitDecoration(st, ed, getMonthColor());
        addChild(deco);        
    }
        
    private void initDayGroups(){        
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTimeInMillis(st);
        int max = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        days = new ArrayList<DayGroup>(max);
        for(int i = max; i > 0; i--){
            DayGroup d = new DayGroup(i, this, plans);
            days.add(d);
        }
        for(int i = days.size() - 1; i >= 0; i--){
        	addChild(days.get(i));
        }
    }
        
    int getMonth(){
        return month;
    }
        
    YearGroup getYearGroup(){
        return parent;
    }
    
    List<Plan> getPlans() {
        if(days == null)
            return new LinkedList<Plan>();
        List<Plan> plans = new LinkedList<Plan>();
        for (int i = days.size() - 1; i >= 0; i--) {
            plans.addAll(days.get(i).getPlans());
        }
        System.err.println("return Month:" + month + " / " + plans.size());
        return plans;
    }
    
    void addPlan(Plan newPlan){
        long time = newPlan.getTime();
        int index = getIndexOf(time);
        if(index >= 0){
            days.get(index).addPlan(newPlan);
        } else {
            System.err.println("(EE) MonthGroup#addPlan line : 109");
        }
    }
    
    void removePlan(Plan plan){
        long time = plan.getTime();
        int index = getIndexOf(time);
        if(index >= 0){
            days.get(index).removePlan(plan);
        } else {
            System.err.println("(EE) MonthGroup#removePlan line : 119");
        }
    }
    
    int getIndexOf(long msec){
        int index = -1;
        for (int i = days.size() - 1; i >= 0; i--) {
            if(days.get(i).isInRange(msec)){
                index = i;
            }
        }
        return index;
    }
    
    DayGroup getDayGroup(int index){
        return days.get(index);
    }
      
    
    /* Test implementation */
    void setDecoration(){        
        CSDGeneralManager.getInstance().getFunction().setDecoration(deco);
        revalidate();
    }
    
    
    void setMode(int mode){
        switch (mode) {        
        case Calendar.YEAR:
            System.err.println("MonthGroup : setMode to YEAR-MODE");
            // display only oribitline and month color panel
            setVisible(true);                        
            setDecoration();
            setModeAllChildren(mode);
            setMonthIDPanel(true);/* TODO Test implementation */
            break;
            
        case Calendar.MONTH:            
        case Calendar.WEEK_OF_MONTH:            
        case Calendar.DATE:
        case Calendar.HOUR_OF_DAY:
        	System.err.println("MonthGroup : setMode to " + mode);            
            setVisible(true);            
            setMonthIDPanel(true);/* TODO Test implementation */           
            setDecoration();
            
            if(days == null)
                initDayGroups();
            setModeAllChildren(mode);
            break;
            
        case TimeRoot.MONTH_UNVISIBLE:
            System.err.println("MonthGroup : setMode to UNVISIBLE");
            setVisible(false);
            break;            
        default:
            System.err.println("MonthGroup : Argument Not suported" + mode);
            break;
        }
    }    
    
    /* TODO Test implementation */
    private void setMonthIDPanel(boolean visible){
        if(visible){
            if(idPanel == null){
                idPanel = new MonthIDPanel();
                addChild(idPanel);
            }
            idPanel.setVisible(true);
            /*
            float[] vec = CSDGeneralManager.getInstance().getFunction().getPosition(st);
            idPanel.changeTranslation(vec[0], vec[1] + 0.0035f , vec[2]);
            */
            idPanel.setDefaultScale();
        } else if(idPanel != null){                        
            idPanel.setVisible(false);            
        }
    }
    
    private float[] getMonthColor(){
        float[] color = new float[3];
        //winter - white, spring - pink, summer - blue, fall - orange
        System.err.println("*** debug: getMonthColor() is called.");
        switch (month) {
            //**********  spring  ***********
        case 2:                
        case 3:
        case 4:
            color[0] = 0.9f;
            color[1] = 0.6f;
            color[2] = 0.6f;
            break;
            //*********  summer  **********
        case 5:                
        case 6:
        case 7:
            color[0] = 0.6f;
            color[1] = 0.6f;
            color[2] = 0.9f;
            break;
            //**********  autumn  **********
        case 8:                
        case 9:
        case 10:
            color[0] = 0.8f;
            color[1] = 0.8f;
            color[2] = 0.4f;
            break;
            //**********  winter  ***********
        case 11:                
        case 0:
        case 1:
            color[0] = 0.9f;
            color[1] = 0.9f;
            color[2] = 0.9f;
            break;
        default:
            System.err.println("MonthGroup illegal month number : " + month);
            break;
        }
        return color;
    }
        
    private void setModeAllChildren(int mode){
        if(days == null)return;
      for (int i = days.size() - 1; i >= 0; i--)
          days.get(i).setMode(mode);
    }
     
    /* ** MonthIDPanel *************************************************************** */
    private class MonthIDPanel extends IDPanel implements AllowMenu{
        private float defaultScale = 1f;
		private Component3D comp;
		
        private MonthIDPanel(){
            TextFieldPanel text = new TextFieldPanel(MONTH_ID[month], true);
            comp = new Component3D();
            comp.addChild(text);
            addChild(comp);
            int mode = CSDGeneralManager.getInstance().getFunction().getMode();
        }

        /* ************* an implementation of Orbiter ************* */        
        @Override
        public long getTime() {
        	return st;
        }
        
        protected void setDefaultScale(){
            int mode = CSDGeneralManager.getInstance().getFunction().getMode();
            if(mode == Calendar.YEAR){
                defaultScale = 0.5f;
                comp.changeScale(defaultScale); 
            } else {
                defaultScale = 1f;
                comp.changeScale(defaultScale);
            }
        }
        
        /* ************* Implementation of IDPanel ************* */        
        protected void setFocus(boolean focus){
            if(focus){
                comp.changeScale(defaultScale * 1.15f);
                comp.changeTranslation(0, 0, 0);
            } else {
                comp.changeScale(defaultScale);
                comp.changeTranslation(0, 0, 0);
            }
        }
        
        protected void doubleClicked(){
            /*
            setRotationAngle(0);
            changeRotationAngle((float)(2*Math.PI));
            int mode = CSDGeneralManager.getInstance().getFunction().getMode();
            if(mode == Calendar.MONTH){
                CSDGeneralManager.getInstance().setMode(Calendar.YEAR);
            } else{
                CSDGeneralManager.getInstance().setMode(Calendar.MONTH);
            }
            */   
            CSDGeneralManager.getInstance().setCameraPosition(st);
        }        
        
        
        protected void selected(){
//            CSDGeneralManager.getInstance().setCameraPosition(st);
        }
        /* ************* Implementation of IDPanel ************* */

        /* *************** Menu ************** */
        public MenuList getMenuList() {
            return menu;
        }
        
        public void processMenu(MenuIcon menu) {
            if(menu == ExtraCSDMenu.ZOOM_OUT){
                zoom(false);
            } else if(menu == ExtraCSDMenu.ZOOM_IN){
                zoom(true);
            }
        }
        /* *************** Menu ************** */
        
        private void zoom(boolean in){
            int currentMode = CSDGeneralManager.getInstance().getFunction().getMode();
            int nextMode = -1;
            if(in){
                switch (currentMode) {
                case Calendar.YEAR:
                    nextMode = Calendar.MONTH;
                    break;
                case Calendar.MONTH:
                    nextMode = Calendar.WEEK_OF_MONTH;
                    break;
                case Calendar.WEEK_OF_MONTH:
                    nextMode = Calendar.DAY_OF_MONTH;
                    break;
                case Calendar.DAY_OF_MONTH:
                case Calendar.HOUR_OF_DAY:
                    nextMode = Calendar.HOUR_OF_DAY;
                    break;
                default:
                    /* Do nothing. */
                    break;
                }
            } else {
                switch (currentMode) {
                case Calendar.YEAR:
                case Calendar.MONTH:
                    nextMode = Calendar.YEAR;
                    break;
                case Calendar.WEEK_OF_MONTH:
                    nextMode = Calendar.MONTH;
                    break;
                case Calendar.DAY_OF_MONTH:
                    nextMode = Calendar.WEEK_OF_MONTH;
                    break;
                case Calendar.HOUR_OF_DAY:
                    nextMode = Calendar.DAY_OF_MONTH;
                    break;
                default:
                    /* Do nothing. */
                    break;
                }
            }
            if(nextMode >= 0){
                if(currentMode != nextMode)
                    CSDGeneralManager.getInstance().setMode(nextMode);
            }
        }

    }    
}
