/*
 * GlassyCubeMenu.java
 *
 * Created on August 31, 2004, 2:07 PM
 */

package org.jdesktop.lg3d.apps.luncher;
import java.util.ArrayList;
import java.util.Iterator;
import javax.vecmath.Vector3f;
import org.xml.sax.SAXException;
import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.sg.Shape3D;
import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.utils.action.ActionNoArg;
import org.jdesktop.lg3d.utils.action.ActionBooleanFloat3;
import org.jdesktop.lg3d.utils.action.ActionFloat3;
import org.jdesktop.lg3d.utils.shape.GlassyPanel;
import org.jdesktop.lg3d.utils.shape.ImagePanel;
import org.jdesktop.lg3d.utils.component.Pseudo3DIcon;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MouseDraggedEventAdapter;
import org.jdesktop.lg3d.utils.layoutmanager.ZLayeredLayout;
import org.jdesktop.lg3d.wg.event.LgEventSource;
import org.jdesktop.lg3d.wg.event.MouseEvent3D;
import org.jdesktop.lg3d.wg.event.MouseEvent3D.ButtonId;


/**
 *
 * @author  Henrik Baastrup
 */
public class GlassyCubeMenu extends Container3D {
    public static final String MENU_NAME = "GlassyCubeMenuShortcuts";
    private float menuSize = 0.02f;
    private float menuDepth = 0.002f;
    private float menuPanelWidth = 0.1f;
    private float menuPanelHeight = 0.02f;
    private float menuPanelDepth = 0.002f;
    private Container3D cubePanelRGroup = new Container3D();
    private Container3D cubePanelHolder = new Container3D();
    private Container3D menuPanelHolder = new Container3D();
    private GlassyCubeMenuIcon menuIcon = null;
    private String configFileName = "etc/lg3d/MenuConfigFile.xml";
    private MenuSide prevMenuSide;

    private float xRot = 0.0f;
    private float yRot = 0.0f;
    
    public GlassyCubeMenu() {
        super();
    }
    
    public void initialize() {
        MenuSide menu;
	cubePanelRGroup.setRotationAxis(0.0f, 1.0f, 0.0f);
	cubePanelHolder.setPreferredSize(new Vector3f(0.02f, 0.02f, 0.02f));
        cubePanelHolder.setLayout(new CubeLayout());
        
        menuIcon = new GlassyCubeMenuIcon("resources/images/icon/GlassyCardIcon.png", this);
        
        // menu panal
        SimpleAppearance app = new SimpleAppearance(0.6f, 1.0f, 0.6f, 1.0f,SimpleAppearance.DISABLE_CULLING);
        GlassyPanel menuPanel = new GlassyPanel(menuPanelWidth, menuPanelHeight, menuPanelDepth, menuPanelDepth*0.1f, app);
        Component3D menuPanelComp = new Component3D();
        menuPanelComp.addChild(menuPanel);
        menuPanelHolder.addChild(menuPanelComp);
	// add a close button
        ButtonAppearance closeButtonOffAppearance 
	        = new ButtonAppearance("resources/images/button/window-close.png", false);
        app = new SimpleAppearance(0.6f, 1.0f, 0.6f, 1.0f,SimpleAppearance.DISABLE_CULLING);
	Component3D closeButton = new Button(0.005f, new ButtonAppearance("resources/images/button/window-close.png", false), 0.00575f, new ButtonAppearance("resources/images/button/window-close.png", true));
	closeButton.setTranslation(menuPanelWidth * 0.5f, menuPanelHeight * 0.5f, 0.001f);
	menuPanelHolder.addChild(closeButton);
        menuPanelHolder.setVisible(false);
	//interactivity of the close button on the menu panel
	closeButton.addListener( new MouseClickedEventAdapter(
            new ActionNoArg() {
                public void performAction(LgEventSource source) {
                    menuPanelHolder.setVisible(false);
                }
            }) );
        addChild(menuPanelHolder);

        
        // Read the menus from the XML file.
        MenuConfigFileReader reader = new MenuConfigFileReader();
        reader.init();
        try {
            reader.parseConfigFile(configFileName);
        } catch (SAXException ex) {
            throw new RuntimeException(ex);
        }
        ArrayList shortcutArr = reader.getShortcuts();
        Iterator iter = shortcutArr.iterator();
        while (iter.hasNext()) {
            Container3D container = (Container3D)iter.next();
            container.setPreferredSize(new Vector3f(menuPanelWidth, menuPanelHeight, menuPanelDepth));
            menu = new MenuSide(container.getName());
            menu.setShortcuts(container);
            cubePanelHolder.addChild(menu);
            container.setName(MENU_NAME);
        }
        
        int num = cubePanelHolder.numChildren();
        for (int i=num; i<6; i++) {
            menu = new MenuSide("");
            cubePanelHolder.addChild(menu);
        }
        
	cubePanelHolder.setRotationAxis(1.0f, 0.0f, 0.0f);
//	cubePanelHolder.setRotationAngle((float)Math.toRadians(35));
        cubePanelHolder.setName("GlassyCubeMenuPanels");
	cubePanelRGroup.addChild(cubePanelHolder);
        addChild(cubePanelRGroup);
        setName("GlassyCubeMenu");
    }//initialize
    
    private void openMenu(MenuSide menuSide) {
        Vector3f loc = cubePanelHolder.getTranslation(new Vector3f());
        loc.x += menuSize + menuPanelWidth*0.5f;
        menuPanelHolder.setTranslation(loc.x, loc.y, loc.z);
        if (!menuPanelHolder.isVisible()) menuPanelHolder.setVisible(true);
                
        Component3D comp = null;
        int i;
        int num = menuPanelHolder.numChildren();
        Container3D shortcuts = menuSide.getShortcuts();
        for (i=0; i<num; i++) {
            comp = (Component3D)menuPanelHolder.getChild(i);
            if (MENU_NAME.equals(comp.getName())) break;
            else comp = null;
        }
        if (comp != null) {
            menuPanelHolder.removeChild(i);
        }
        if (shortcuts != null) {
            menuPanelHolder.addChild(shortcuts);
        }
    }
    
    public Pseudo3DIcon getMenuIcon() {return menuIcon;}
    
    /*
     * When we grap the object with mouse button 3 we have to rotate
     * the whole container
     */
    private void rotateContainer(float x, float startX, float y, float startY, float z, float startZ) {
        float deltaX = x-startX;
        float deltaY = y-startY;

	if ( (float)Math.abs(deltaX) > (float)Math.abs(deltaY) ) {
	  if ( deltaX > 0 ) {
	    xRot += 5;
	  } else if ( deltaX < 0 ) {
	    xRot -= 5;
	  }
	  cubePanelRGroup.setRotationAngle((float)Math.toRadians(xRot));

	} else {
	  if ( deltaY > 0 ) {
	    yRot += 5;
	  } else if ( deltaY < 0 ) {
	    yRot -= 5;
	  }
	  cubePanelHolder.setRotationAngle((float)Math.toRadians(yRot));
	}
	
    }//rotateContainer

    private class MenuSide extends Container3D {
        private float startX = 0.0f;
        private float startY = 0.0f;
        private float startZ = 0.0f;
        private Container3D shortcuts = null;
        
        MenuSide(String text) {
            SimpleAppearance bodyApp = new SimpleAppearance(0.6f, 1.0f, 0.6f, 1.0f,SimpleAppearance.DISABLE_CULLING);
            GlassyPanel menu = new GlassyPanel(menuSize, menuSize, menuDepth, menuDepth*0.1f, bodyApp);
            MyTextPanel textPanel = new MyTextPanel(text,1.0f,menuSize,menuSize,0,0,false);

            Component3D textComp = new Component3D();
            textComp.addChild(textPanel);
            textComp.setTranslation(-menuSize*0.5f, -menuSize*0.5f, 0.0f);
            Component3D menuComp = new Component3D();
            menuComp.addChild(menu);

            setLayout(new ZLayeredLayout(0.0f));
            addChild(textComp);
            addChild(menuComp);
            
            this.addListener(new MouseClickedEventAdapter(
                    new ActionFloat3() {
                        public void performAction(LgEventSource source, 
                            float x, float y, float z) 
                        {
                            MenuSide menuSide = (MenuSide)source;
                            if (menuPanelHolder.isVisible() 
                                && prevMenuSide == menuSide) 
                            {
                                menuPanelHolder.setVisible(false);
                            } else {
                                startX = x;
                                startY = y;
                                startZ = z;
                                openMenu(menuSide);
                                prevMenuSide = menuSide;
                            }
                        }
                    }));
                    
            this.addListener(new MouseDraggedEventAdapter(
                    new ActionFloat3() {
                        public void performAction(LgEventSource source,
                            float x, float y, float z) 
                        {
                            rotateContainer(x, startX, y, startY, z, startZ);
			    startX = x; startY = y; startZ = z;
                        }
                    }));
        }//MenuSide
        
        public void setSize(float size) {menuSize = size;}

        public float getHeight() {return menuSize;}
        public float getWidth() {return menuSize;}
        public float getDepth() {return menuDepth;}
        
        public Container3D getShortcuts() {return shortcuts;}
        public void setShortcuts(Container3D shortcuts) {this.shortcuts = shortcuts;}
        
    }//MenuSide
    
    /*
     * This object should activate and dis-activate the menu
     */
    private class GlassyCubeMenuIcon extends Pseudo3DIcon implements ActionNoArg {
        GlassyCubeMenu menu = null;
        
        private GlassyCubeMenuIcon(String filename, GlassyCubeMenu menu) {
            super(filename);
            this.menu = menu;
            this.addListener(new MouseClickedEventAdapter(this));
        }
        
        public void performAction(LgEventSource source) {
            if (!menu.isVisible()) {
                menu.setVisible(true);
                // Put the mennu over the icon
                Vector3f iconLoc = getTranslation(new Vector3f());
		Vector3f sz = getPreferredSize(new Vector3f());
                iconLoc.x += sz.x * 2f;
                iconLoc.y += sz.y * 2f;
                menu.setTranslation(iconLoc.x, iconLoc.y, iconLoc.z);
            } else {
                menu.setVisible(false);
            }
        }//GlassyCubeMenuIcon
    }

    private class Button extends Component3D {
	private Button(float size, Appearance app) {
	    this(size, app, size, app);
	}
	
	private Button(float sizeOff, Appearance appOff,
	float sizeOn, Appearance appOn) {
	    Shape3D shape = new ImagePanel(sizeOff, sizeOff);
	    shape.setAppearance(appOff);
	    addChild(shape);
/*	    if (appOff != appOn) {
		new MouseEnteredEventAdapter(this, new AppearanceChangeAction(shape, appOn));
	    }
	    if (sizeOff != sizeOn) {
		new MouseEnteredEventAdapter(this, new ScaleAction(this, sizeOn/sizeOff, 100));
	    }*/
	}
    }

    private static class ButtonAppearance extends SimpleAppearance {
	private ButtonAppearance(String filename, boolean on) {
	    super(0.0f, 0.0f, 0.0f, 0.0f,
	            SimpleAppearance.DISABLE_CULLING
	            | SimpleAppearance.ENABLE_TEXTURE);
	    if (on) {
		setColor(1.0f, 0.6f, 0.6f, 0.8f);
	    } else {
		setColor(0.6f, 1.0f, 0.6f, 0.6f);
	    }
	    try {
		setTexture(filename);
	    } catch (Exception e) {
		throw new RuntimeException("failed to initilaze window button: " + e);
	    }
	}
    }
}
    
