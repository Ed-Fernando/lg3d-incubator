package nu.koidelab.cosmo.manager;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.HashMap;

import javax.vecmath.Color4f;

import nu.koidelab.cosmo.util.TextureUtil;

import org.jdesktop.lg3d.sg.Texture;
import org.jdesktop.lg3d.wg.Frame3D;
import org.jdesktop.lg3d.wg.Toolkit3D;

/**
 * @author fumi
 * CosmoConfig
 * nu.koidelab.cosmo.manager
 */
public class CosmoConfig {
    public static final float  SCREEN_WIDTH;
    public static final float  SCREEN_HEIGHT;

    //  meter / pixel = 0.0254 [m/inch] / (ex. 96.0f) [pixel/inch]    
    public static final float UNIT_TRANS_FACTOR;
    static{
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        int dpi = toolkit.getScreenResolution();        
        UNIT_TRANS_FACTOR = 0.0254f / (float)dpi;
        
        Frame3D f = new Frame3D();
        SCREEN_WIDTH = Toolkit3D.getToolkit3D().getScreenWidth();
        SCREEN_HEIGHT = Toolkit3D.getToolkit3D().getScreenHeight();
    }
    

    public static float PLANET_RADIUS = SCREEN_HEIGHT * 0.03f;
    
        
    // Font settings
    public static final Font DEFAULT_FONT = new Font("MS Gothic", Font.BOLD, 25);
    public static final Font CLOCK_FONT = new Font("Vera Serif", Font.ITALIC|Font.BOLD, 17);
    public static final Font SCHEDULE_NAME_FONT = new Font("MS Gothic", Font.BOLD, 25);
    public static final Font NAME_TAG_FONT = new Font("Vera Serif", Font.ITALIC|Font.BOLD, 35);
    // Font color settings
    public static final Color STR_COLOR = Color.WHITE;
    public static final Color4f SCHEDULE_NAME_COLOR = new Color4f(0.75f, 0.85f, 0.75f, 1.0f);
    public static final Color4f STR_COLOR_4F = new Color4f(1.0f, 1.0f, 1.0f, 1.0f);
    public static final Color STR_SHADOW_COLOR = new Color(0.4f, 0.6f, 0.4f);
    public static final Color CONSOLE_FG_COLOR = new Color(0, 0, 0);
    public static final Color CONSOLE_BG_COLOR = new Color(0, 0, 0, 0);
    public static final Color CONSOLE_BG_PANEL_COLOR = new Color(1, 1, 1, 0.9f);
	
	private static CosmoConfig instance = new CosmoConfig();
	private HashMap<Integer, Texture> planetTextures = new HashMap<Integer, Texture>();
	private Texture lightTexture;
    
	private CosmoConfig(){
        init();
    }
	
	public static CosmoConfig getConfig(){
		return instance;
	}
    	
	private void init(){
		lightTexture = TextureUtil.getInstance().getTexture("resources/cosmo/images/light3.bmp");
	}
    
 	public Texture getPlanetTexture(int priority){
		Texture tex = planetTextures.get(priority);
		if(tex == null){
			tex = TextureUtil.getInstance().getTexture("resources/cosmo/images/default/" + "textures/planet"
					+ String.valueOf(priority) + ".png");
			planetTextures.put(priority, tex);
		}
		return tex;
	}
	
	public Texture getLightTexture(){
		return lightTexture;
	}	
}
