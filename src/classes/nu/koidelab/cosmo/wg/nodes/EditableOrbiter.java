package nu.koidelab.cosmo.wg.nodes;

import nu.koidelab.cosmo.manager.CSDGeneralManager;
import nu.koidelab.cosmo.util.bind2d.swing.EditPlanPanel;
import nu.koidelab.cosmo.util.gesture.menu.ExtraCSDMenu;
import nu.koidelab.cosmo.util.gesture.menu.MenuIcon;
import nu.koidelab.cosmo.util.gesture.menu.MenuList;
import nu.koidelab.cosmo.util.gesture.menu.AllowMenu;
import nu.koidelab.cosmo.util.schedule.Plan;
import nu.koidelab.cosmo.wg.event.OrbiterMover;
import nu.koidelab.cosmo.wg.shape.NameTag;

import org.jdesktop.lg3d.utils.c3danimation.NaturalMotionAnimation;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.event.LgEventConnector;
import org.jdesktop.lg3d.wg.event.MouseButtonEvent3D;
import org.jdesktop.lg3d.wg.event.MouseDraggedEvent3D;
import org.jdesktop.lg3d.wg.event.MouseEnteredEvent3D;

public abstract class EditableOrbiter extends Orbiter implements AllowMenu{
    protected Plan ghostPlan = null;
    protected Plan backUpPlan = null;
    
    protected boolean ghostflg = false; // Ghost mode flag
    protected long ghosttime = 0;    
    protected boolean isRepresentative = false;
    protected Body bodyComp;
    protected NameTag nameTag;
            
    protected static final OrbiterMover orbiterMover = new OrbiterMover();
    static { /* Event handler for Editting Orbit */
        LgEventConnector.getLgEventConnector().addListener(
        		EditableOrbiter.class, orbiterMover);
//        LgEventConnector.getLgEventConnector().addListener(
//        		EditableOrbiter.class, new PlanGestureModule());
    }

    protected EditableOrbiter(Plan p, Body b) {    	
        super();
        this.plan = p;
        setName(plan.getName());

        bodyComp = b;
        addChild(bodyComp);
        bodyComp.setScale(plan.getPriority());
        bodyComp.setAnimation(new NaturalMotionAnimation(500));
        
        setMouseEventSource(MouseButtonEvent3D.class, true);
        setMouseEventSource(MouseEnteredEvent3D.class, true);
        setMouseEventSource(MouseDraggedEvent3D.class, true);
    }
    
    @Override
    public long getTime() {
    	return plan.getTime();
    }
    
    public Plan getPlan(){
    	return plan;
    }
    
    /* TODO : This method should be implemented in a better way */
    public void beRepresentative(boolean be) {
        isRepresentative = be;
    }
    
    public boolean addPriority(int i) {
        if (isGhostMode()) {
            int nextPriority = plan.getPriority() + i;
            if(1 <= nextPriority && nextPriority <= 5){
                plan.setPriority(nextPriority);
                bodyComp.changeScale(nextPriority, 500);
                return true;
            }
        }
        return false;
    }
    
    
    /* TODO Test implementation */
    /** Reset body-component appearance according to it's original plan.
     *   This method is called if editting is canceled. */
    protected abstract void resetAppearance();
    
    public abstract void setNameTag(boolean visible);
    
    public NameTag getNameTag(){
    	return nameTag;
    }

    /* TODO Test implementation */
    public void endGhostMode(boolean isSet) {
        if (isSet) {            
            Plan newPlan = new Plan(plan);
            System.err.println("endGhostMode" + newPlan.getReference());
            if(newPlan.getReference() == null)
                System.exit(0);
            CSDGeneralManager.getInstance().removePlan(backUpPlan);
            CSDGeneralManager.getInstance().addPlan(newPlan);
        } else {
            plan = backUpPlan;
            resetAppearance();
            requestParentToRevalidate();
        }
        backUpPlan = null;
        setGhostMode(false);
    }    

    public void startGhostMode() {
        backUpPlan = plan;
        plan = new Plan(plan);
        setGhostMode(true);
    }

    protected void setGhostMode(boolean flag) {
        ghostflg = flag;
    }

    public boolean isGhostMode() {
        return ghostflg;
    }

    /* TODO : These methods should be implemented in a better way ---> */
    protected EditPlanPanel panel;
	protected Plan plan;
    public void launchEditor(){
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        panel = new EditPlanPanel(this);
    }
    
    /* =========== Do if edit is canceld. ========== */
    public void editCancel(){
        panel.changeEnabled(false);
        panel = null;
        CSDGeneralManager.getInstance().releaseFocus();
    }
    
    /* =========== Do if edit is canceld. ========== */
    public void editOK(Plan newPlan){
        panel.changeEnabled(false);
        panel = null;
        CSDGeneralManager.getInstance().releaseFocus();
        CSDGeneralManager.getInstance().removePlan(this.plan);
        CSDGeneralManager.getInstance().addPlan(newPlan);
    }
    /* <--- TODO : These methods should be implemented in a better way */

    public void setBodyVisible(boolean visible){
    	bodyComp.setVisible(visible);
    }
    
    @Override
    public void changeScale(float scale) {
    	if(getFinalScale() != scale)
    		nameTag.setOrbiterScale(scale);
    	super.changeScale(scale);
    }
    
    @Override
    public void changeScale(float scale, int duration) {
    	if(getFinalScale() != scale)
    		nameTag.setOrbiterScale(scale);
    	super.changeScale(scale, duration);
    }
    
	
    private static MenuList menu;
    private static MenuList ghostMenu;
	static{
		menu = new MenuList();
		menu.add(MenuIcon.EDIT);
		menu.add(MenuIcon.PROPERTIES);
		menu.add(MenuIcon.DELETE);
		ghostMenu = new MenuList();		
		ghostMenu.add(ExtraCSDMenu.INC_PRIORITY);
		ghostMenu.add(MenuIcon.APPLY);
		ghostMenu.add(ExtraCSDMenu.DEC_PRIORITY);
		ghostMenu.add(MenuIcon.CANCEL);
	}
	
	public MenuList getMenuList() {
		if(isGhostMode()) return ghostMenu;
		return menu;
	}
	
	public void processMenu(MenuIcon menu) {
		if(menu == MenuIcon.PROPERTIES){
			CSDGeneralManager.getInstance().getCursorManager().removeCursors(this);
			CSDGeneralManager.getInstance().focusTo(this);            
            launchEditor();
		} else if(menu == MenuIcon.DELETE){
			CSDGeneralManager.getInstance().removePlan(this.plan);
		} else if(menu == MenuIcon.EDIT){
			orbiterMover.startGhostMode(this);
		} else if(menu == MenuIcon.APPLY){
			orbiterMover.endGhostMode(this, true);
		} else if(menu == MenuIcon.CANCEL){
			orbiterMover.endGhostMode(this, false);
		} else if(menu == ExtraCSDMenu.INC_PRIORITY){
			addPriority(1);
		} else if(menu == ExtraCSDMenu.DEC_PRIORITY){
			addPriority(-1);
		}
	}

    public static class Body extends Component3D{
        protected Body(){}
    }    
}
