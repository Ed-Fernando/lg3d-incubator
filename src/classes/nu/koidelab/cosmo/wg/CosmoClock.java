package nu.koidelab.cosmo.wg;

import javax.vecmath.Vector3f;

import nu.koidelab.cosmo.manager.CosmoConfig;
import nu.koidelab.cosmo.util.TextureUtil;
import nu.koidelab.cosmo.wg.shape.DatePanel;

import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.sg.Shape3D;
import org.jdesktop.lg3d.sg.TriangleArray;
import org.jdesktop.lg3d.utils.action.ActionNoArg;
import org.jdesktop.lg3d.utils.action.AppearanceChangeAction;
import org.jdesktop.lg3d.utils.action.ScaleActionBoolean;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MouseEnteredEventAdapter;
import org.jdesktop.lg3d.utils.shape.ImagePanel;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.wg.Cursor3D;
import org.jdesktop.lg3d.wg.Frame3D;
import org.jdesktop.lg3d.wg.event.LgEventSource;

/**
 * @author fumi nu.koidelab.cosmo.wg.CosmoClock
 */
public class CosmoClock extends Container3D {
	private SimpleAppearance closeButtonOffAppearance;
    private SimpleAppearance closeButtonOnAppearance;
    private SimpleAppearance minimizeButtonOffAppearance;
    private SimpleAppearance minimizeButtonOnAppearance;

	
    private Frame3D frame3d;

    public CosmoClock(Frame3D frame) {
        setName("Cosmo Clock");
        this.frame3d = frame;
        setRotationAxis(1.0f, 0.0f, 0.0f);
//        setTranslation(0.0f, -0.015f, 0.0f);
        
        /* static test */
        closeButtonOffAppearance = new ButtonAppearance(
                "resources/images/button/window-close.png", false);
        closeButtonOnAppearance = new ButtonAppearance(
                "resources/images/button/window-close.png", true);
        minimizeButtonOffAppearance = new ButtonAppearance(
                "resources/images/button/window-minimize.png", false);
        minimizeButtonOnAppearance = new ButtonAppearance(
                "resources/images/button/window-minimize.png", true);
        
        // Temporary variable for component's size.
        Vector3f size = new Vector3f();
        
        // Triangle arrow over this clock
        Arrow arrow = new Arrow();
        arrow.setPickable(false);
        size = arrow.getPreferredSize(size);
        arrow.setTranslation(0.0f, -size.y/2 - 0.002f, 0.0f);
        addChild(arrow);
        

        
        float clkOffsetX = 0;
        float clkOffsetY = -0.02f;
        float buttonSize = CosmoConfig.SCREEN_WIDTH / 40.0f;
        float buttonOnSize = buttonSize * 1.15f;

        // make clock for displaying system current time        
        Clock clk = new Clock();
        clk.setCursor(Cursor3D.MOVE_CURSOR);
        size = clk.getPreferredSize(new Vector3f());
        clk.setScale(buttonSize / size.y);
        size.scale(clk.getScale());
        clk.setPreferredSize(size);
        clk.setTranslation(clkOffsetX, clkOffsetY, 0);  
        addChild(clk);
        
        
        // make buttons
        size = clk.getPreferredSize(size);
        float buttonOffsetX = size.x * 0.5f + buttonSize * 0.5f;
        float buttonOffsetY = buttonSize * 0.5f;

        Component3D closeButton = new Button(buttonSize,
                closeButtonOffAppearance, buttonOnSize, closeButtonOnAppearance);
        closeButton.setCursor(Cursor3D.SMALL_CURSOR);
        closeButton.setTranslation(buttonOffsetX + clkOffsetX, buttonOffsetY + clkOffsetY, 0.0f);
        closeButton.addListener(new MouseClickedEventAdapter(new ActionNoArg() {
            public void performAction(LgEventSource source) {
                frame3d.changeEnabled(false);
            }
        }));
        addChild(closeButton);

        
        Component3D minimizeButton = new Button(buttonSize,
                minimizeButtonOffAppearance, buttonOnSize,
                minimizeButtonOnAppearance);
        minimizeButton.setCursor(Cursor3D.SMALL_CURSOR);
        minimizeButton.setTranslation(buttonOffsetX + clkOffsetX, -buttonOffsetY + clkOffsetY, 0.0f);
        minimizeButton.addListener(new MouseClickedEventAdapter(
                new ActionNoArg() {
                    public void performAction(LgEventSource source) {
                        frame3d.changeVisible(false);
                    }
                }));
        addChild(minimizeButton);
    }

    private static class ButtonAppearance extends SimpleAppearance {
        private ButtonAppearance(String filename, boolean on) {            
            super(0.0f, 0.0f, 0.0f, 0.0f, SimpleAppearance.DISABLE_CULLING
                    | SimpleAppearance.ENABLE_TEXTURE);
            if (on) {
                setColor(1.0f, 0.6f, 0.6f, 0.8f);
            } else {
                setColor(0.65f, 1.0f, 0.65f, 0.7f);
            }
            try {
                setTexture(TextureUtil.getInstance().getTexture(filename));
            } catch (Exception e) {
                throw new RuntimeException(
                        "failed to initilaze window button: " + e);
            }
        }
    }

    private class Button extends Component3D {
        private Button(float size, Appearance app) {
            this(size, app, size, app);
        }

        private Button(float sizeOff, Appearance appOff, float sizeOn,
                Appearance appOn) {
            Shape3D shape = new ImagePanel(sizeOff, sizeOff);
            shape.setAppearance(appOff);
            addChild(shape);
            if (appOff != appOn) {
                addListener(new MouseEnteredEventAdapter(
                        new AppearanceChangeAction(shape, appOn)));
            }
            if (sizeOff != sizeOn) {
                addListener(new MouseEnteredEventAdapter(
                        new ScaleActionBoolean(this, sizeOn / sizeOff, 100)));
            }
        }
    }

    private class Arrow extends Component3D {
        float width = 0.004f;
        float height = 0.006f;
        
        private Arrow() {
            super();
            addChild(makeShape());
            setPreferredSize(new Vector3f(width, height, 0));
        }

        private Shape3D makeShape() {
            float[] vertices = {
                0.0f, height/2, 0.0f,
                -width/2, -height/2, 0.0f,
                width/2, -height/2, 0.0f,
            };
            TriangleArray geometry = new TriangleArray(vertices.length,
                    TriangleArray.COORDINATES);
            geometry.setCoordinates(0, vertices);
            
            Appearance app = new SimpleAppearance(1.0f, 1.0f, 1.0f, 1.0f);
            Shape3D shape = new Shape3D(geometry, app);
            return shape;
        }
    }
    
    private class Date extends DatePanel{
        private Date(long msec){
            super(msec);
        }
    }
}
