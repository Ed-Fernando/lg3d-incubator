package nu.koidelab.cosmo.util.gesture.menu;

import java.awt.Color;
import java.awt.Font;
import java.io.InputStream;

import nu.koidelab.cosmo.manager.CosmoConfig;
import nu.koidelab.cosmo.util.TextureUtil;
import nu.koidelab.cosmo.wg.shape.Panel;
import nu.koidelab.cosmo.wg.shape.TextFieldPanel;

import org.jdesktop.lg3d.utils.c3danimation.NaturalMotionAnimation;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.Component3D;

public class MenuIcon extends Component3D{
	static final float ICON_SIZE = CosmoConfig.SCREEN_HEIGHT * 0.09f;
    private static final String ACTION_DIR = "resources/cosmo/images/default/icons/actions/";
    public static final MenuIcon NULL_MENU = new MenuIcon("", "null");
    
    public static final MenuIcon SAVE = new MenuIcon("save", "filesave.png");
    public static final MenuIcon SAVE_AS = new MenuIcon("save-as", "filesaveas.png");
    public static final MenuIcon OPEN = new MenuIcon("open", "fileopen.png");
    public static final MenuIcon NEW = new MenuIcon("new", "filenew.png");     
    public static final MenuIcon EDIT = new MenuIcon("edit", "edit.png");
    public static final MenuIcon PROPERTIES = new MenuIcon("properties", "configure.png");

    public static final MenuIcon DELETE = new MenuIcon("delete", "editdelete.png");
    public static final MenuIcon APPLY = new MenuIcon("apply", "button_ok.png");
    public static final MenuIcon CANCEL = new MenuIcon("cancel", "button_cancel.png");
    
    public static final MenuIcon COPY = new MenuIcon("copy", "editcopy.png");
    public static final MenuIcon CUT = new MenuIcon("cut", "editcut.png");
    public static final MenuIcon PASTE = new MenuIcon("paste", "editpaste.png");
    
    public static final MenuIcon REFRESH = new MenuIcon("refresh", "rebuild.png"); 
    public static final MenuIcon IMPORT = new MenuIcon("import", "fileimport.png");
    
    public static final MenuIcon UP = new MenuIcon("up", "up.png");
    public static final MenuIcon DOWN = new MenuIcon("down", "down.png");
    public static final MenuIcon BACK = new MenuIcon("prev", "back.png");
    public static final MenuIcon NEXT = new MenuIcon("next", "next.png");


	
	private Panel imagePanel;

	public MenuIcon(String name, String imageFile){		
		setName(name);
		setAnimation(new NaturalMotionAnimation(500));
		
		imagePanel = new Panel(ICON_SIZE, ICON_SIZE, true);
		imagePanel.setAppearance(new SimpleAppearance(1, 1, 1, 1, SimpleAppearance.ENABLE_TEXTURE));
		addChild(imagePanel);
        setImage(imageFile);
        
        if(!name.equals("")){
    		TextFieldPanel text = new TextFieldPanel(name, true, true, new Font("Vera Serif", Font.BOLD, 18), new Color(1f, 1f, 1f), new Color(0, 0, 0, 0), 1f); 		
    		text.setOffset(0, 0, 0.002f);
    		text.setScale(0.9f);
    		addChild(text);
        }
	}
	
    private void setImage(String name){
        String filepath =  ACTION_DIR + name;
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(filepath);
        if(is == null || name.equals("null")){
            setTexture("resources/cosmo/images/nullImage.png");
        } else {
            setTexture(filepath);
        }
    }
    
	private void setTexture(String filename){
         ((SimpleAppearance)imagePanel.getAppearance()).setTexture(TextureUtil.getInstance().getTexture(filename, 32, 32));
	}
		
}
