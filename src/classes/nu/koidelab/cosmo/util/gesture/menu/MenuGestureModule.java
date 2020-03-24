package nu.koidelab.cosmo.util.gesture.menu;

import javax.vecmath.Vector2f;

import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.event.LgEventConnector;
import org.jdesktop.lg3d.wg.event.LgEventSource;

public class MenuGestureModule extends MenuGestureModuleBase {
	private static final float RADIUS = MenuIcon.ICON_SIZE;
	private static final float ACTIVE_RADIUS = (RADIUS)*(RADIUS);
	private static MenuGestureModule instance = new MenuGestureModule(); 
	private int selectedIndex = -1;
	static{
		LgEventConnector.getLgEventConnector().addListener(LgEventSource.ALL_SOURCES, instance);
	}

	private MenuGestureModule() {}
	
	static MenuGestureModule getModule(){
		return instance;
	}

	public void addLayoutComponent(Component3D comp, Object constraints) {}
	
	public void layoutContainer() {
		if (menu == null)
			return;
		if (active) {
			container.setVisible(true);
			container.changeTransparency(0f, 200);
			float diff = (float) (2 * Math.PI / menu.size());
			for (int i = 0; i < menu.size(); i++) {
				MenuIcon icon = menu.get(i);
				float angle = -diff * i + (float)(Math.PI/2);
				float transZ = 0;
				if(selectedIndex == i){
					icon.changeScale(1.4f, 200);
					transZ = 0.001f;
				} else {
					icon.changeScale(1f, 200);
				}
				
				icon.changeTranslation(
						RADIUS * (float) Math.cos(angle),
						RADIUS * (float) Math.sin(angle),
						transZ, 300);

			}
		} else {
			// if not visible, Don't layout children.
			if((container.isFinalVisible())){
				for (int i = 0, n = menu.size(); i < n; i++) {
					MenuIcon icon = menu.get(i);
					if (selectedIndex == i) {
						icon.changeScale(1.5f, 300);
						icon.changeTranslation(0, 0, 0.01f, 300);
						icon.setRotationAngle(0);
						icon.setRotationAxis(1, 0, 0);
						icon.changeRotationAngle(2 * (float) Math.PI, 300);
					} else {
						icon.setTranslation(0, 0, 0);
						icon.setTransparency(1f);
					}
				}
				try {
					Thread.sleep(400);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				container.setVisible(false);
			}
		}
	}
	
	public boolean rearrangeLayoutComponent(Component3D comp, Object newConstraints) {
		return false;
	}
	
	public void removeLayoutComponent(Component3D comp) {}
	
	@Override	
	protected void recognizeGesture(float x, float y) {		
		float dist = x*x + y*y;
		if(dist < ACTIVE_RADIUS){
			selectedIndex = -1;
		} else {
			Vector2f yAxis = new Vector2f(0, 1);
			Vector2f vec = new Vector2f(x, y);
			float angle = yAxis.angle(vec);
			if(vec.x < 0) angle = -angle + 2*(float)Math.PI;
			int num = menu.size();
			angle += (float)Math.PI/num;
			int index = (int)(angle*num/(2*Math.PI));
			if(index >= num)
				index = 0;
			selectedIndex = index;
//			System.err.println("selected INDEX : " + index + " , angle = "+ angle + " , menu NUM : " + num);
		}		
		layoutContainer();
	}
	
	@Override
	protected void processGesture() {
		if(selectedIndex >= 0){
			/* Process Gesture : Selected */
			
			if(target != null){
				target.processMenu(menu.get(selectedIndex));
//				System.err.println("Process Gesture : " + menu.get(selectedIndex).getName());
			} else {
				new IllegalStateException("Menu Target Object cannot be null.");
			}
		} else {
			/* Process Gesture : Not Selected */
		}
		selectedIndex = -1;
	}
}
