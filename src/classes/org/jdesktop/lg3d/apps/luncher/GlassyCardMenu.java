/*
 * GlassyMenu.java
 *
 * Created on August 24, 2004, 10:58 AM
 */

package org.jdesktop.lg3d.apps.luncher;
import java.util.ArrayList;
import java.util.Iterator;
import javax.vecmath.Vector3f;
import org.xml.sax.SAXException;
import org.jdesktop.lg3d.wg.Frame3D;
import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.sg.Node;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.utils.shape.GlassyPanel;
import org.jdesktop.lg3d.utils.action.ActionNoArg;
import org.jdesktop.lg3d.utils.action.ActionBoolean;
import org.jdesktop.lg3d.utils.action.ActionBooleanFloat3;
import org.jdesktop.lg3d.utils.action.ActionFloat3;
import org.jdesktop.lg3d.utils.component.Pseudo3DIcon;
import org.jdesktop.lg3d.utils.component.Pseudo3DShortcut;
import org.jdesktop.lg3d.utils.eventadapter.MouseEnteredEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MouseDraggedEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MousePressedEventAdapter;
import org.jdesktop.lg3d.utils.layoutmanager.ZLayeredLayout;
import org.jdesktop.lg3d.utils.layoutmanager.HorizontalLayout;
import org.jdesktop.lg3d.wg.event.LgEventSource;
import org.jdesktop.lg3d.wg.event.MouseEvent3D;
import org.jdesktop.lg3d.wg.event.MouseEvent3D.ButtonId;


/**
 *
 * @author  Henrik Baastrup
 *
 * This class will read a menu setup from a XML file (etc/lg3d/GlassyCardMenu.xml),
 * use the setConfigFileName metod before the initialize method to change this.
 * The menu is actual a stack of Glassy Panels, when a panel is activated it will
 * change the icons on the Taskbar. You activate the stack by clicking on the 
 * Glassy Card Menu Icon (a magnifier) and disactivate it by clicking on the
 * icon again. You can replase the single panles by pulling them with the mouse
 * button 1. You can have a better look of the whole menu by rotaing it with mouse
 * button 3.
 */
public class GlassyCardMenu extends Container3D {
    public static final String MENU_NAME = "GlassyCardMenuShortcuts";
    private static float menuWidth = 0.05f;
    private static float menuHeight = 0.02f;
    private static float menuDepth = 0.002f;
    private static float zSpacing = 0.01f;
    private static float iconSpacing = 0.0025f;
    private float iconSize = 0.01f;
    private GlassyCardMenuHolder menuHolder = null;
    private ArrayList shortcuts = new ArrayList();
    private String configFileName = "etc/lg3d/MenuConfigFile.xml";
    private Container3D menuPanelHolder = new Container3D();

    public GlassyCardMenu(GlassyCardMenuHolder menuHolder) {
        super();
        this.menuHolder = menuHolder;
    }
    
    public void initialize() {
        Container3D container = null;
        MenuCard menu;
        ShortcutHolder shortcutHolder;
        
        menuPanelHolder.setLayout(new ZLayeredLayout(zSpacing));
        
        // Read the menus from the XML file.
        MenuConfigFileReader reader = new MenuConfigFileReader();
        reader.setShortcutIconSize(iconSize);
        reader.init();
        try {
            reader.parseConfigFile(configFileName);
        } catch (SAXException ex) {
            throw new RuntimeException(ex);
        }
        ArrayList shortcutArr = reader.getShortcuts();
        // We have to read the menus from behind to get the right visual efect
        for (int i=shortcutArr.size(); i>0; i--) {
            container = (Container3D)shortcutArr.get(i-1);
            menu = new MenuCard(container.getName());
            container.setName(MENU_NAME);
            menuPanelHolder.addChild(menu);
            // The first icon should alwais be the Glassy Card Menu icon
            container.insertChild(new GlassyCardMenuIcon("resources/images/icon/GlassyCardIcon.png", this), 0);
            shortcutHolder = new ShortcutHolder();
            shortcutHolder.menuCard = menu;
            shortcutHolder.shortcuts = container;
            shortcuts.add(shortcutHolder);
        }
        if (menuHolder!=null) menuHolder.setShortcuts(container);

	menuPanelHolder.setRotationAxis(1.0f, 0.0f, 0.0f);
	menuPanelHolder.setRotationAngle((float)Math.toRadians(20));
        menuPanelHolder.setName("GlassyCardMenuPanels");
        addChild(menuPanelHolder);
        setName("GlassyCardMenu");
        
    }//initialize
    
    /*
     * When we click a card in the stack we have to rotate the panals
     * in the stack so the given node will be the front panal.
     */
    private void rotateCards(Node selectedNode) {
        // Set each card to the location where it was borned
        int num = menuPanelHolder.numChildren();
        for (int i=0; i<num; i++) {
            MenuCard nod = (MenuCard)menuPanelHolder.getChild(i);
            nod.setLocationToDefault();
        }
        // Rotate the cards so the right card is in front
        int inx = menuPanelHolder.indexOfChild(selectedNode);
        for (int i = 0; i <= inx; i++) {
            Node nod = (MenuCard)menuPanelHolder.getChild(0);
            menuPanelHolder.removeChild(0);
            menuPanelHolder.addChild(nod);
        }
    }//rotateCards
    
    /*
     * When we grap the object with mouse button 3 we have to rotate
     * the whole container
     */
    private void rotateContainer(float x, float startX, float y, float startY, float z, float startZ) {
        float deltaX = x-startX;
        float deltaY = y-startY;
        float len = (float)Math.abs(deltaX) + (float)Math.abs(deltaY);
//        len *= 10;
        len *= 5000;
//        float ang = (float)Math.atan2(len, 0.05f);
	menuPanelHolder.setRotationAxis(deltaY, -deltaX, 0.0f);
//	menuPanelHolder.setRotationAngle(-ang);
	menuPanelHolder.setRotationAngle(-(float)Math.toRadians(len));
    }//rotateContainer
    
    public void setConfigFileName(String filename) {configFileName = filename;}
    
    public void setIconSize(float iconSize) {this.iconSize = iconSize;}
    
   
    
    /*
     * Helping object there hold all the graphical stuff for each card.
     */
    private class MenuCard extends Container3D {
        private Vector3f defaultLocation;
        private float startX;
        private float startY;
        private float startZ;
        private float prevX;
        private float prevY;
        private float prevZ;
        
        private MenuCard(String text) {
            // A card is a simple appearance GlassyPanel wit a TextPanel inside
            SimpleAppearance bodyApp = new SimpleAppearance(0.6f, 1.0f, 0.6f, 1.0f,SimpleAppearance.DISABLE_CULLING);
            GlassyPanel menu = new GlassyPanel(menuWidth, menuHeight, menuDepth, menuDepth*0.1f, bodyApp);
            MyTextPanel textPanel = new MyTextPanel(text,1.0f,menuWidth,menuHeight,0,0,false);

            Component3D textComp = new Component3D();
            textComp.addChild(textPanel);
            textComp.setTranslation(-menuWidth*0.5f, -menuHeight*0.5f, 0.0f);
            Component3D menuComp = new Component3D();
            menuComp.addChild(menu);

            setLayout(new ZLayeredLayout(0.0f));
            addChild(menuComp);
            addChild(textComp);
            // we need the location where a card is boarn to be able to
            // put it back in the right possion.
            defaultLocation = getTranslation(defaultLocation);
            
            this.addListener(new MouseDraggedEventAdapter(ButtonId.BUTTON1, 
                    new ActionFloat3() {
                        public void performAction(LgEventSource source, 
                            float x, float y, float z) 
                        {
                            requestMove(x, y, z);
                        }
                    }) );
                    
            this.addListener( new MouseClickedEventAdapter( ButtonId.BUTTON1,
                    new ActionNoArg() {
                        public void performAction(LgEventSource source) 
                        {
                            setTranslation(defaultLocation.x, defaultLocation.y, defaultLocation.z);
                            // Rotate the card order
                            requestRotateCards();
                            if (menuHolder!=null) {
                                // Find the shortcuts we want to give to on the taskbar
                                ShortcutHolder holder = null;
                                Iterator iter = shortcuts.iterator();
                                while (iter.hasNext()) {
                                    holder = (ShortcutHolder)iter.next();
                                    if (holder.menuCard.equals(source)) break;
                                    else holder = null;
                                }
                                // Put the founded shortcuts on the taskbar
                                if (holder!=null) menuHolder.setShortcuts(holder.shortcuts);
                            }
                        }
                    }) );
                    
            this.addListener( new MouseDraggedEventAdapter(ButtonId.BUTTON3,
                    new ActionFloat3() {
                        public void performAction(LgEventSource source,
                            float x, float y, float z) 
                        {
                            requestRotateContainer(source, x, y, z);
                        }
                    }) );

            this.addListener( new MousePressedEventAdapter(
                    new ActionBooleanFloat3() {
                        public void performAction(LgEventSource source, boolean state,
                            float x, float y, float z) 
                        {
                            if (state) {
                                // Registrate the start possition of the mouse
                                startX = prevX = x;
                                startY = prevY = y;
                                startZ = prevZ = z;
                            }
                        }
                    }) );
        }
        
        /*
         * Moves a card when it is dragged with muse button 1
         */
        private void requestMove(float x, float y, float z) {
            Vector3f loc = this.getTranslation(new Vector3f());
            loc.x += (x - prevX);
            loc.y += (y - prevY);
//            loc.z += (z - prevZ);
//            loc.z = 0;
            this.setTranslation(loc.x, loc.y, loc.z);            
            prevX = x;
            prevY = y;
            prevZ = z;
        }
        
        /*
         * When a card is clicked we have to put it in front.
         */
        private void requestRotateCards() {
            rotateCards(this);
        }
        
        /*
         * WHen a card is graped, with mouse button 3, we have to rotate the
         * whole container (all the cards).
         */
        private void requestRotateContainer(LgEventSource source, float x, float y, float z) {
            rotateContainer(x, startX, y, startY, z, startZ);
        }
        
        private void setDefaultLocation() {defaultLocation = getTranslation(defaultLocation);}
        
        private void setLocationToDefault() {setTranslation(defaultLocation.x, defaultLocation.y, defaultLocation.z);}
     }//MenuCard
    
    /*
     * A helper object to hold the tow objects there make a menu point (card).
     */
    private class ShortcutHolder {
        public MenuCard menuCard = null;
        public Container3D shortcuts = null;
    }//ShortcutHolder
        
    /*
     * This object should activate and dis-activate the menu
     */
    private class GlassyCardMenuIcon extends Pseudo3DIcon implements ActionNoArg {
        GlassyCardMenu menu = null;
        
        private GlassyCardMenuIcon(String filename, GlassyCardMenu menu) {
            super(filename);
            this.menu = menu;
            this.addListener(new MouseClickedEventAdapter(this));
        }
        
        public void performAction(LgEventSource source) {
            if (!menu.isVisible()) {
                menu.setVisible(true);
                // Put the mennu over the icon
                Vector3f iconLoc = this.getTranslation(new Vector3f());
		Vector3f sz = this.getPreferredSize(new Vector3f());
                iconLoc.x += sz.x*2f;
                iconLoc.y += sz.y*2f;
                menu.setTranslation(iconLoc.x, iconLoc.y, iconLoc.z);
            }
            else menu.setVisible(false);
        }//GlassyCardMenuIcon
    }
        
}
