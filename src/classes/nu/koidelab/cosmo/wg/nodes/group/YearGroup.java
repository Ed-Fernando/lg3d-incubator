package nu.koidelab.cosmo.wg.nodes.group;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import nu.koidelab.cosmo.manager.CSDGeneralManager;
import nu.koidelab.cosmo.util.function.OrbitFunction;
import nu.koidelab.cosmo.util.gesture.menu.AllowMenu;
import nu.koidelab.cosmo.util.gesture.menu.ExtraCSDMenu;
import nu.koidelab.cosmo.util.gesture.menu.MenuIcon;
import nu.koidelab.cosmo.util.gesture.menu.MenuList;
import nu.koidelab.cosmo.util.schedule.Plan;
import nu.koidelab.cosmo.wg.nodes.Orbiter;
import nu.koidelab.cosmo.wg.shape.NumberPanel;

import org.jdesktop.lg3d.sg.Shape3D;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.wg.LayoutManager3D;

public class YearGroup extends TimeGroup {
    private int year;
    private List<MonthGroup> months = new ArrayList<MonthGroup>();
    private boolean active = false;
    private TimeRoot parent;
    
    /* for displaying this date */
    private YearIDPanel numPanel;

    private static MenuList menu = new MenuList();
    static{
        menu.add(ExtraCSDMenu.ZOOM_OUT);
        menu.add(MenuIcon.NULL_MENU);
        menu.add(ExtraCSDMenu.ZOOM_IN);
        menu.add(MenuIcon.NULL_MENU);
    }

    
    YearGroup(long msec, TimeRoot tg, List<Plan> planList){
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTimeInMillis(msec);
        cal.set(cal.get(Calendar.YEAR), 0, 1, 0, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        st = cal.getTimeInMillis();
        year = cal.get(Calendar.YEAR);
        System.err.println("*** An instance of YearGroup " + year + "is created.");
        cal.set(cal.get(Calendar.YEAR)+1, 0, 1, 0, 0, 0);
        ed = cal.getTimeInMillis();
        parent = tg;
        pickoutPlans(planList);
        
        numPanel = new YearIDPanel(year);
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
        addChild(numPanel);
        relocateNumPanel();
    }
    
    YearGroup(int year, TimeRoot tg, List<Plan> planList){
        Calendar cal = GregorianCalendar.getInstance();
        cal.set(year, 0, 1, 0, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        st = cal.getTimeInMillis();
        this.year = cal.get(Calendar.YEAR);
        System.err.println("*** An Instance of YearGroup " + year + " is created.");
        cal.set(cal.get(Calendar.YEAR)+1, 0, 1, 0, 0, 0);
        ed = cal.getTimeInMillis();
        parent = tg;
        pickoutPlans(planList);
        
        numPanel = new YearIDPanel(year);
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
        addChild(numPanel);
        relocateNumPanel();
    }
    
    int getYear() {
        return year;
    }
    
    TimeRoot getRoot(){
        return parent;
    }
    
    List<Plan> getPlans() {
        List<Plan> ans = new LinkedList<Plan>();
        for (int i = months.size() - 1; i >= 0; i--) {
            ans.addAll(months.get(i).getPlans());
        }
        ans.addAll(plans);
        System.err.println("return Year :" + year + " / " + ans.size());
        return ans;
    }        
    
    void addPlan(Plan newPlan){
        long time = newPlan.getTime();
        int index = isExist(time);
        if(index >= 0){
            months.get(index).addPlan(newPlan);
        } else {
            plans.add(newPlan);
        }
    }
    
    void removePlan(Plan plan){
        long time = plan.getTime();
        int index = isExist(time);
        if(index >= 0){
            months.get(index).removePlan(plan);
        } else {
            plans.remove(plan);
        }
    }

    /*Test implementation*/
    void setActiveOnly(int[] selectedMonths, int mode){
        int[] selectedIndices = monthsToIndices(selectedMonths);        
        
        setMode(mode);
        for (int i = months.size() - 1; i >= 0; i--) {
            boolean isExist = false;
            for (int j = selectedIndices.length - 1; j >= 0; j--) {
                if(i == selectedIndices[j])
                    isExist = true;
            }
            if(isExist){
                months.get(i).setMode(mode);
            } else {
                months.get(i).setMode(TimeRoot.MONTH_UNVISIBLE);
            }
        }
        
        if(selectedMonths.length >= 0)
            setVisible(true);
    }
    
    private int[] monthsToIndices(int[] months){
        int[] indices = new int[months.length];        
        for (int i = months.length - 1; i >= 0; i--) {
            indices[i] = getIndexOf(months[i]);
        }
        return indices;
    }
    
    private int getIndexOf(int month){
        int index = isExist(month);
        if(index < 0){
            MonthGroup m = new MonthGroup(month, this, plans);
            months.add(m);
            addChild(m);
            index = months.size() - 1;
        } 
        return index;
    }
    
    /* Test implementation */
    int isExist(long msec){
        int index = -1;
        for (int i = months.size() - 1; i >= 0; i--) {
            if(months.get(i).isInRange(msec)){
                index = i;
            }
        }
        return index;
    }
    
    /* Test implementation */
    MonthGroup getMonthGroup(int index){
        return months.get(index);
    }
    
    /** if not exist then return -1 */
    int isExist(int month){
        int index = -1;
        for (int i = months.size() - 1; i >= 0; i--) {
            if(months.get(i).getMonth() == month){
                index = i;
            }
        }
        return index;
    }           
    
    void setMode(int mode){
        relocateNumPanel();
        switch (mode) {
        case TimeRoot.YEAR_UNVISIBLE:
            setVisible(false);
            System.err.println("YearGroup : setMode(int mode) <- set Unvisible" );
            break;
            
        case Calendar.YEAR:
            setVisible(true);
            checkMonths();
            setModeAllChildren(mode);
            break;
            
        case Calendar.MONTH:            
        case Calendar.WEEK_OF_MONTH:            
        case Calendar.DATE:
        case Calendar.HOUR_OF_DAY:
        	setVisible(true);
            System.err.println("YearGroup : setMode(int mode) <- This part is not needed???");
            break;
        default:
            System.err.println("YearGroup : Argument Not suported. ");
            break;
        }
    }
    
    private void checkMonths(){
      if(months.size()  < 12)
          for (int i = 11; i >= 0; i--) 
              if(isExist(i) < 0){
                  MonthGroup m = new MonthGroup(i , this, plans);
                  months.add(m);
                  addChild(m);
              }
    }
    
    private void setModeAllChildren(int mode){
      for (int i = months.size() - 1; i >= 0; i--)
          months.get(i).setMode(mode);
    }
    
    private void relocateNumPanel(){
    	/*
        float[] vec = CSDGeneralManager.getInstance().getFunction().getPosition(st);
        numPanel.changeTranslation(vec[0], vec[1] + 0.003f , vec[2]);
        System.err.println("relocate YEAR_ID_PANEL");
        */
    }
    
    public class YearIDPanel extends IDPanel implements AllowMenu{        
        private Component3D comp;

		private YearIDPanel(int num){        
           Shape3D s = new NumberPanel(num);
        	comp = new Component3D();
        	comp.addChild(s);
        	addChild(comp);
           setScale(2.0f);
       }      
        
        /* ********** Implementation of Orbiter ************* */        
        @Override
        public long getTime() {
        	return st;
        }

        /* ************* Implementation of IDPanel ************* */
        protected void setFocus(boolean focus){
            if(focus){
                comp.changeScale(1.2f);
                comp.changeTranslation(0, 0.0015f, 0);
            } else {
                comp.changeScale(1.0f);
                comp.changeTranslation(0, 0, 0);
            }
        }
        
        protected void doubleClicked(){
        }        
        
        protected void selected(){            
            CSDGeneralManager.getInstance().setCameraPosition(st);
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
