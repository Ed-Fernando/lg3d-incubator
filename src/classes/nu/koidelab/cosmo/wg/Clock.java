package nu.koidelab.cosmo.wg;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import javax.vecmath.Vector3f;

import nu.koidelab.cosmo.manager.CSDGeneralManager;
import nu.koidelab.cosmo.manager.CosmoConfig;

import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.sg.Geometry;
import org.jdesktop.lg3d.sg.GeometryArray;
import org.jdesktop.lg3d.sg.ImageComponent2D;
import org.jdesktop.lg3d.sg.IndexedQuadArray;
import org.jdesktop.lg3d.sg.Shape3D;
import org.jdesktop.lg3d.sg.Texture;
import org.jdesktop.lg3d.sg.Texture2D;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Frame3D;
import org.jdesktop.lg3d.wg.Toolkit3D;

/**
 * @author fumi Clock org.wg
 */
public class Clock extends Component3D {
    private static final float DEPTH = 0.005f;
    private static final String DEFAULT_PATTERN = "yyyy/MM/dd(EE)   HH:mm:ss";
    private static int SHADOW_OFFSET = 1;

    private BufferedImage image;
    private Appearance appearance;
    private SimpleDateFormat formatter;
    private Color color;
    private ImageComponent2D imageComp;
	private Date date;

    public Clock(String pattern, Color color) {
        this.color = color;
        appearance = new SimpleAppearance(1.0f, 1.0f, 1.0f, 1.0f,
                SimpleAppearance.ENABLE_TEXTURE
                        | SimpleAppearance.DISABLE_CULLING);
        appearance.setCapability(Appearance.ALLOW_TEXTURE_WRITE);
        appearance.setCapability(Appearance.ALLOW_TEXTURE_READ);
        
        formatter = new SimpleDateFormat(pattern, Locale.US);
        makePanel();
    }
    
    public  Clock() {       
        this(Clock.DEFAULT_PATTERN, Color.WHITE);
    }

    public void makePanel() {
    	// make Texture
        image = new BufferedImage(2, 2, BufferedImage.TYPE_INT_ARGB);
        
        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setFont(CosmoConfig.CLOCK_FONT);
		 date = new Date();		 
		 date.setTime(System.currentTimeMillis());
        String str = formatter.format(date);

        int strLength = g.getFontMetrics().stringWidth(str);
        int charHeight = g.getFontMetrics().getAscent()
                - g.getFontMetrics().getDescent();
        int width = strLength+2;//add spacer
        int height = charHeight;
        int newW = getClosestPowerOf2(width);
        int newH = getClosestPowerOf2(height);
        
        Toolkit3D toolkit3d = Toolkit3D.getToolkit3D();
        float physicalWidth = toolkit3d.widthNativeToPhysical(width);
        float physicalHeight = toolkit3d.heightNativeToPhysical(height);
        
        Shape3D panel = new Shape3D();
        panel.setGeometry(getImagePanelGeometry(physicalWidth, physicalHeight, false, ((float)width / (float)newW), ((float)height / (float)newH)));
        panel.setAppearance(appearance);
        addChild(panel);        
        setPreferredSize(new Vector3f(physicalWidth, physicalHeight, DEPTH));

        image = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        imageComp = new ImageComponent2D(ImageComponent2D.FORMAT_RGBA,
                image, true, true);
        imageComp.setCapability(ImageComponent2D.ALLOW_IMAGE_WRITE);


        TimerTask task = new TimerTask() {
            private final Color zero = new Color(0, 0, 0, 0);
            private int h = image.getHeight();
            private int w = image.getWidth();
            
            public void run() {
                Graphics2D g = (Graphics2D) image.getGraphics();

                g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                g.setBackground(zero);
                g.clearRect(0, 0, w, h);
                g.setFont(CosmoConfig.CLOCK_FONT);
                
                date.setTime(System.currentTimeMillis());                                
                String str = formatter.format(date);
                
                g.setColor(CosmoConfig.STR_SHADOW_COLOR);
                g.drawString(str, SHADOW_OFFSET, h);                
                g.setColor(color);                                
                g.drawString(str, 0, h-SHADOW_OFFSET);                
                g.dispose();
                                               
                imageComp.set(image);
                Texture2D texture = (Texture2D) appearance.getTexture();
                if(texture == null){
                    texture = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA, w, h);
                    texture.setMinFilter(Texture.BASE_LEVEL_LINEAR);
                    texture.setMagFilter(Texture.BASE_LEVEL_LINEAR);
                    texture.setCapability(Texture.ALLOW_IMAGE_WRITE);
                }                
                texture.setImage(0, imageComp);
                appearance.setTexture(texture);
            }
        };

        Timer timer = CSDGeneralManager.getInstance().getTimer();
        timer.scheduleAtFixedRate(task, 0L, 1000L);
    }
    
    private int getClosestPowerOf2(int value) {
        if (value < 1)
            return value;
        
        int powerValue = 1;
        for (;;) {
            powerValue *= 2;
            if(value < powerValue){
                return powerValue;
            }
        }
    }
    
    protected Geometry getImagePanelGeometry(float width, float height, boolean shiny, float texX, float texY) {
        float w = width / 2.0f;
        float h = height / 2.0f;

        float[] coords = { -w, -h, 0.0f, w, -h, 0.0f, w, h, 0.0f, -w, h, 0.0f, };

        int[] indices = { 0, 1, 2, 3, };

        float[] texCoords = { 0, texY, texX, texY, texX, 0, 0, 0 };

        float[] normals = { 0.0f, 0.0f, 1.0f, };

        int[] normalIndices = { 0, 0, 0, 0, };

        IndexedQuadArray geometry = new IndexedQuadArray(4,
                GeometryArray.COORDINATES | GeometryArray.TEXTURE_COORDINATE_2
                        | ((shiny) ? (GeometryArray.NORMALS) : (0)), 1,
                new int[] { 0 }, 4);

        geometry.setCoordinates(0, coords);
        geometry.setCoordinateIndices(0, indices);
        geometry.setTextureCoordinates(0, 0, texCoords);
        geometry.setTextureCoordinateIndices(0, 0, indices);
        if (shiny) {
            geometry.setNormals(0, normals);
            geometry.setNormalIndices(0, normalIndices);
        }
        
        return geometry;
    }
    
    public static void main(String[] args) {
      Clock clk =  new Clock();
      Frame3D frame3d = new Frame3D();
      frame3d.addChild(clk);
      Vector3f clkSize = new Vector3f();
      clk.getPreferredSize(clkSize);
      frame3d.setPreferredSize(new Vector3f(clkSize.x * 1.2f, clkSize.y * 1.2f, clkSize.z));        
      frame3d.changeEnabled(true);
      frame3d.changeVisible(true);
    }

}
