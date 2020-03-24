/*
 * Luncher.java
 *
 * Created on August 23, 2004, 1:08 PM
 */

package org.jdesktop.lg3d.apps.luncher;
import java.util.Enumeration;
import org.jdesktop.lg3d.wg.Frame3D;
import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.sg.BoundingBox;
import org.jdesktop.lg3d.utils.action.AppLaunchAction;
import org.jdesktop.lg3d.utils.shape.Box;
import org.jdesktop.lg3d.utils.shape.Cylinder;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.utils.shape.RingShadow;
import org.jdesktop.lg3d.utils.shape.GlassyPanel;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.utils.action.ActionNoArg;
import org.jdesktop.lg3d.utils.layoutmanager.HorizontalLayout;
import org.jdesktop.lg3d.utils.component.Pseudo3DIcon;
import org.jdesktop.lg3d.utils.component.Pseudo3DShortcut;
import org.jdesktop.lg3d.wg.event.LgEventSource;
import javax.vecmath.*;


/**
 *
 * @author  Henrik Baastrup
 */
public class Luncher2 extends Frame3D implements GlassyCardMenuHolder {
    private static float luncherWidth = 0.20f;
    private static float luncherHeight = 0.02f;
    private static float luncherDepth = 0.002f;
    private static float iconSpacing = 0.0025f;
    private GlassyCubeMenu menu = null;
    private Container3D deco = new Container3D();

    public static void main(String[] args) {
        new Luncher2();
    }
    
    public Luncher2() {
        menu = new GlassyCubeMenu();
        menu.setVisible(false);

        SimpleAppearance app = new SimpleAppearance(0.6f, 1.0f, 0.6f, 1.0f,SimpleAppearance.DISABLE_CULLING);

        RingShadow ring = new RingShadow(0.06f, 0.03f, 2, 0.1f);
        Box box = new Box(luncherWidth, luncherHeight, luncherDepth, app);
        Cylinder cylinder =new Cylinder(luncherWidth,luncherDepth,app);
	GlassyPanel glassyPanel = new GlassyPanel(luncherWidth, luncherHeight, luncherDepth, luncherDepth*0.1f, app);
        
        Component3D lunchComp = new Component3D();
        lunchComp.setName("Lunch2Comp");
        lunchComp.addChild(glassyPanel);
	lunchComp.setRotationAxis(1.0f, 0.0f, 0.0f);
	lunchComp.setRotationAngle((float)Math.toRadians(-70));
	lunchComp.setTranslation(0.0f, luncherHeight * -0.51f, luncherHeight * -0.1f);
        
      
        menu.initialize();

        Container3D shortcuts = new Container3D();
        shortcuts.setPreferredSize(new Vector3f(luncherWidth, luncherHeight, luncherDepth));
        shortcuts.setLayout(new HorizontalLayout(HorizontalLayout.AlignmentType.LEFT, iconSpacing));
        shortcuts.addChild(menu.getMenuIcon());
        
        
        deco.addChild(menu);
	deco.addChild(lunchComp);
        deco.addChild(shortcuts);
        deco.setName("Deco");
//	deco.setRotationAxis(1.0f, 0.0f, 0.0f);
//	deco.setRotationAngle((float)Math.toRadians(-45));
        setDecoration(deco);
        
        setPreferredSize(new Vector3f(0.06f, 0.06f, 0.02f));
        setEnabled(true);
        setVisible(true);
    }
    
    public void setShortcuts(Container3D shortcuts) {
        Component3D comp = null;
        int i;
        int num = deco.numChildren();
        for (i=0; i<num; i++) {
            comp = (Component3D)deco.getChild(i);
            if (GlassyCardMenu.MENU_NAME.equals(comp.getName())) break;
            else comp = null;
        }
        if (shortcuts!=null) {
            shortcuts.setPreferredSize(new Vector3f(luncherWidth, luncherHeight, luncherDepth));
            shortcuts.setLayout(new HorizontalLayout(HorizontalLayout.AlignmentType.LEFT, iconSpacing));
        }
        if (comp==null) deco.addChild(shortcuts);
        else {
            deco.removeChild(i);
            if (shortcuts!=null) {
                deco.addChild(shortcuts);
            }
        }
    }
    
    private class MenuIcon extends Pseudo3DIcon implements ActionNoArg {
        private Frame3D frame;
        
        private MenuIcon(String filename, Frame3D frame) {
            super(filename);
            this.frame = frame;
            this.addListener(new MouseClickedEventAdapter(this) );
        }
        
        public void performAction(LgEventSource source) {
//            if (menu==null) {
            if (!menu.isVisible()) {
                menu.setVisible(true);
                Vector3f iconLoc = this.getTranslation(new Vector3f());
		Vector3f sz = menu.getPreferredSize(new Vector3f());
                iconLoc.x += sz.x;
                iconLoc.y += sz.y*2f;
//                menu.setTranslation(iconLoc);
                menu.setTranslation(0.0f, 0.0f, 0.0f);
            }
            else {
                menu.setVisible(false);
//                menu.changeActivity(false);
//                menu = null;
            }
        }
        
    }
    
    private class CloseIcon extends Pseudo3DIcon implements ActionNoArg {
        private Frame3D frame;
        private CloseIcon(String filename, Frame3D frame) {
            super(filename);
            this.frame = frame;
            this.addListener( new MouseClickedEventAdapter(this) );
        }
        
        public void performAction(LgEventSource source) {
            frame.changeEnabled(false);
        }
        
    }
    
    private class Luncher3DShortcut extends Pseudo3DIcon implements ActionNoArg {
        private AppLaunchAction appCommand;
        private Frame3D frame;
        
    private Luncher3DShortcut(String filename, String command, Frame3D frame) {
	super(filename);
        appCommand = new AppLaunchAction(command);
        this.frame = frame;
        this.addListener(new MouseClickedEventAdapter(this) );
    }
    
    public void performAction(LgEventSource source) {
        appCommand.performAction(source);
        frame.changeEnabled(false);
    }
    
}

}
