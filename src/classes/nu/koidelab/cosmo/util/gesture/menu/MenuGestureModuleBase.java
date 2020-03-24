package nu.koidelab.cosmo.util.gesture.menu;

import static org.jdesktop.lg3d.wg.event.MouseEvent3D.ButtonId.BUTTON3;

import javax.vecmath.Point3f;

import nu.koidelab.cosmo.manager.CSDGeneralManager;

import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.wg.LayoutManager3D;
import org.jdesktop.lg3d.wg.event.LgEvent;
import org.jdesktop.lg3d.wg.event.LgEventListener;
import org.jdesktop.lg3d.wg.event.MouseButtonEvent3D;
import org.jdesktop.lg3d.wg.event.MouseDraggedEvent3D;

public abstract class MenuGestureModuleBase implements LgEventListener, LayoutManager3D{
	protected static final Class[] targetClasses = {MouseButtonEvent3D.class, MouseDraggedEvent3D.class};
	protected static final float ACTIVE_RADIUS = 0.03f*0.03f;
	protected MenuList menu;
	protected boolean active = false;
	protected AllowMenu target;
	protected MenuContainer container;	
	private Point3f orgLoc = new Point3f();
        private Point3f tmpP3f = new Point3f();

	public void setContainer(Container3D cont) {
		if (cont instanceof MenuContainer) {
			MenuContainer mc = (MenuContainer) cont;
			this.container = mc; 			
			cont.setVisible(false);
		} else {
			throw new IllegalArgumentException("MenuGestureModule can be set only to the MenuContainer.");
		}
	}
	
	protected abstract void recognizeGesture(float x, float y);
	protected abstract void processGesture();
	
	protected void stop(int duration){
		try {
			Thread.sleep(duration);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	protected void setActive(boolean arg){
		active = arg;
		if(active){
			setMenuList(target.getMenuList());
			container.setTarget((Component3D) target);
			CSDGeneralManager.getInstance().getCursorManager().removeCursors((Component3D)target);
			CSDGeneralManager.getInstance().getCursorManager().addCursor(container);
		}
		layoutContainer();
		if(!active)
			CSDGeneralManager.getInstance().getCursorManager().removeCursor(container);
	}
	
	public Class[] getTargetEventClasses() {
		return targetClasses;
	}
	
	public void processEvent(LgEvent evt) {
        if(active){
            if (evt instanceof MouseDraggedEvent3D) {
                MouseDraggedEvent3D mm3d = (MouseDraggedEvent3D) evt;
				Point3f pos = new Point3f(mm3d.getCursorPosition(tmpP3f));
				pos.sub(orgLoc);
				recognizeGesture(pos.x, pos.y);
            } else if(evt instanceof MouseButtonEvent3D){
                MouseButtonEvent3D mb3d = (MouseButtonEvent3D) evt;
                if(mb3d.isReleased() && mb3d.getButton().equals(BUTTON3) && active){
                    setActive(false);
                    processGesture();
                    target = null;
                }                
            }
		} else {
            /* Trigger for display pie menus. */
            if (evt instanceof MouseButtonEvent3D){
                MouseButtonEvent3D mb3d = (MouseButtonEvent3D) evt;                         
                if (mb3d.getSource() instanceof AllowMenu) {
                    if(mb3d.isPressed() && mb3d.getButton().equals(BUTTON3)){
                        target = (AllowMenu) mb3d.getSource();
                        if(target.getMenuList().size() == 0){
                            target = null;
                            return;
                        }                
                        orgLoc = mb3d.getCursorPosition(orgLoc);              
                        setActive(true);
                        return;
                    }
                }
            }
        }
	}
		
	protected void setMenuList(MenuList list){
		if(list == null)
			throw new IllegalArgumentException("MenuList cannot be null.");
		if(menu != null)
			if(menu.equals(list)) return;
		container.removeAllChildren();
		menu = list;		
		for (int i = 0, n = menu.size(); i < n; i++) 
			container.addChild(menu.get(i));
	}		
}
