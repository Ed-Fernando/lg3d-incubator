package nu.koidelab.cosmo.util.gesture.menu;

import nu.koidelab.cosmo.util.cursor.CursorManager;

import org.jdesktop.lg3d.utils.shape.Box;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Frame3D;
import org.jdesktop.lg3d.wg.event.MouseButtonEvent3D;

public class MenuContainer extends CursorManager.Cursor{
	
	public MenuContainer(){
		setLayout(MenuGestureModule.getModule());		
	}	
	
	@Override
	public void startAnimation() {

	}
		
	public static void main(String[] args) {
		Frame3D f = new Frame3D();
		MenuableComponent c = new MenuableComponent();
		Box b = new Box(0.02f, 0.02f, 0.02f, new SimpleAppearance(0.8f, 1f, 1f, 0.4f));
		c.addChild(b);
		f.addChild(c);
		c.setTranslation(0, 0.03f, 0);
		
		MenuableComponent2 c2 = new MenuableComponent2();
		Box b2 = new Box(0.02f, 0.02f, 0.02f, new SimpleAppearance(0.8f, 1f, 1f, 0.4f));
		c2.addChild(b2);
		f.addChild(c2);
		c2.setTranslation(0, -0.03f, 0);
		
		MenuContainer mc = new MenuContainer();
		f.addChild(mc);
		
		f.setVisible(true);
		f.setEnabled(true);
	}
	
	private static class MenuableComponent extends Component3D implements AllowMenu{
		private static final MenuList list;
		static{
			list= new MenuList();
			list.add(MenuIcon.SAVE);
			list.add(MenuIcon.OPEN);
			list.add(MenuIcon.NEW);
		}
		
		public MenuableComponent() {
			setMouseEventSource(MouseButtonEvent3D.class, true);
		}
		
		public MenuList getMenuList() {
			return list;
		}
		
		public void processMenu(MenuIcon menu) {
			if(menu.getName().equals("save")){
				System.err.println("SAVE");
			}
			
		}
	}
	
	private static class MenuableComponent2 extends Component3D implements AllowMenu{
		private static final MenuList list;
		static{
			list= new MenuList();
			list.add(MenuIcon.SAVE);
			list.add(MenuIcon.OPEN);	
			list.add(MenuIcon.COPY);
			list.add(MenuIcon.PASTE);	
		}
		
		public MenuableComponent2() {
			setMouseEventSource(MouseButtonEvent3D.class, true);
		}
		
		public MenuList getMenuList() {
			return list;
		}
		
		public void processMenu(MenuIcon menu) {
			if(menu.getName().equals("save")){
				System.err.println("SAVE2");
			}
			
		}
	}
}
