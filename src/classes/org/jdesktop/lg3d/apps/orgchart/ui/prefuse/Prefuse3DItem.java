package org.jdesktop.lg3d.apps.orgchart.ui.prefuse;

import edu.berkeley.guir.prefuse.VisualItem;
import java.awt.image.BufferedImage;
import java.util.EventObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.vecmath.Vector3f;
import org.jdesktop.lg3d.apps.orgchart.framework.Channel;
import org.jdesktop.lg3d.apps.orgchart.framework.ChannelListener;
import org.jdesktop.lg3d.apps.orgchart.framework.contact.Contact;
import org.jdesktop.lg3d.apps.orgchart.framework.contact.ContactChannel;
import org.jdesktop.lg3d.apps.orgchart.ui.common.FramedPanel;
import org.jdesktop.lg3d.apps.orgchart.ui.common.TextPanel;
import org.jdesktop.lg3d.apps.orgchart.ui.common.UIUtil;
import org.jdesktop.lg3d.wg.Toolkit3D;
import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.utils.action.ActionNoArg;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.utils.shape.FuzzyEdgePanel;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.utils.c3danimation.NaturalMotionAnimation;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.event.LgEventSource;



/**
 * Very very preliminary help app whcih just shows the usage guide
 * on the screen.  Intended to become more sophisticated app as we
 * evolve the system...
 */
public class Prefuse3DItem extends Component3D implements ChannelListener {
    private Logger logger = Logger.getLogger(getClass().getName());
    
    static final float PANEL_WIDTH = 0.01f;
    static final float PANEL_HEIGHT = 0.01f;
    static final float PANEL_DEPTH = 0.002f;
    static final float BORDER_WIDTH = 0.001f;
    static final float NAME_HEIGHT = 0.008f;
    static final float PHOTO_WIDTH = PANEL_WIDTH - BORDER_WIDTH;
    static final float PHOTO_HEIGHT = PANEL_HEIGHT - 2.5f * BORDER_WIDTH - NAME_HEIGHT;
    //static final SimpleAppearance APP_MANAGER;
    //static final SimpleAppearance APP_NONMANAGER;
    /*
    static {
        APP_NONMANAGER =
                new SimpleAppearance(1.0f, 0.6f, 1.0f, 1.0f,
                SimpleAppearance.ENABLE_TEXTURE | SimpleAppearance.DISABLE_CULLING);
        APP_MANAGER =
                new SimpleAppearance(1.0f, 1.0f, 1.0f, 1.0f,
                SimpleAppearance.ENABLE_TEXTURE | SimpleAppearance.DISABLE_CULLING);
    }
     */
    
    private Prefuse3D orgChart;
    private VisualItem item;
    private ContactChannel contact;
    private SimpleAppearance presenceApp;
    private SimpleAppearance bodyApp;
    private FramedPanel body;
    private Component3D photo;
    private SimpleAppearance photoApp;
    
    Prefuse3DItem(Prefuse3D orgChart, VisualItem item) {
        this.orgChart = orgChart;
        this.item = item;
        this.contact = (ContactChannel)item.getAttributes();
        contact.addListener(this);
        contact.init();
        createUI();
    }
    
    private void createUI() {
        setPreferredSize(new Vector3f(PANEL_WIDTH, PANEL_HEIGHT, PANEL_DEPTH));
        setMouseEventPropagatable(true);
        setAnimation(new NaturalMotionAnimation(Prefuse3D.ANIMATION_DURATION));
        
        // compute values
        float nameDepth = 0.001f;
        float nameX = PANEL_WIDTH * 0.05f;
        float nameY = BORDER_WIDTH + NAME_HEIGHT + 0.005f;
        
        float photoX = 0.0f; //PANEL_WIDTH * 0.3f;
        float photoY = NAME_HEIGHT;
        float photoWidth = PANEL_WIDTH - BORDER_WIDTH - photoX;
        float photoHeight = PANEL_HEIGHT - 2.5f * BORDER_WIDTH - photoY;
        float photoDepth = nameDepth;
        
        // setup appearance for presence
        presenceApp = new SimpleAppearance(0.6f, 1.0f, 0.6f, 1.0f,
                SimpleAppearance.NO_GLOSS | SimpleAppearance.DISABLE_CULLING);
        bodyApp = Prefuse3D.isManager(item.getEntity())
                ? new SimpleAppearance(1.0f, 0.5f, 1.0f, 1.0f,
                SimpleAppearance.ENABLE_TEXTURE | SimpleAppearance.DISABLE_CULLING)
                : new SimpleAppearance(1.0f, 1.0f, 1.0f, 0.8f,
                SimpleAppearance.ENABLE_TEXTURE | SimpleAppearance.DISABLE_CULLING);
        
        // create name on the side
        Component3D spine = createSpine();
        spine.setTranslation(PANEL_WIDTH * -0.5f,
                PANEL_HEIGHT * 0.5f,
                -PANEL_DEPTH);
        spine.setRotationAxis(0.0f, 1.0f, 0.0f);
        spine.setRotationAngle((float) Math.PI); // 180 deg
        
        // create contact's name in front
        Component3D name = createName(NAME_HEIGHT);
        name.setTranslation(
                PANEL_WIDTH * -0.5f + nameX,
                PANEL_HEIGHT * 0.5f - nameY,
                nameDepth);
        
        // create contact's photo
        photo = createPhoto();
        photo.setTranslation(
                (photoWidth - PANEL_WIDTH) * 0.5f  + photoX,
                (photoHeight - PANEL_HEIGHT) * 0.5f + BORDER_WIDTH,
                photoDepth);
        photo.setTransparency(0.0f);
        
        // create framed body
        body = new FramedPanel(
                PANEL_WIDTH, PANEL_HEIGHT, PANEL_DEPTH, BORDER_WIDTH,
                presenceApp, bodyApp);
        body.setAnimation(new NaturalMotionAnimation(Prefuse3D.ANIMATION_DURATION));
        body.setTranslation(0.0f,  0.0f, -0.002f);
        body.setRotationAxis(0.0f, 1.0f, 0.0f);
        // add them to the body component
        body.addChild(spine);
        body.addChild(photo);
        body.addChild(name);
        addChild(body);
        
        /*
        // move rotation of body to left edge
         Component3D comp = new Component3D();
        comp.addChild(body);
        comp.setTranslation(0.5f * PANEL_WIDTH, 0.0f,  -0.5f * PANEL_DEPTH);
        addChild(comp);
         */
        
        // click to expand
        //setRotationAxis(1.0f, 0.0f, 0.0f);
        addListener(new MouseClickedEventAdapter(
                new ActionNoArg() {
            public void performAction(LgEventSource source) {
                if (orgChart.expandTree(item)) {
                    orgChart.redraw();
                }
            }
        }));
        
        setVisible(true);
    }
    
    private Component3D createSpine() {
        String name = (String)contact.get(Contact.ATTR_FIRSTNAME) + " " +
                (String)contact.get(Contact.ATTR_LASTNAME);
        Component3D spine = new Component3D();
        spine.addChild(new TextPanel(name,
                1.0f, // widthScale,
                PANEL_HEIGHT, // maxWidth,
                PANEL_DEPTH * 0.9f, // height
                1, // xShift
                1, // yShift
                true)); // vertical
        return spine;
    }
    
    private Component3D createName(float nameHeight) {
        String firstName = (String)contact.get(Contact.ATTR_FIRSTNAME);
        String lastName = (String)contact.get(Contact.ATTR_LASTNAME);
        TextPanel namePanel = new TextPanel(firstName, lastName,
                1.0f, // widthScale,
                PANEL_WIDTH * 0.9f, // maxWidth,
                nameHeight * 2.0f, // height
                1, // xShift
                1, // yShift
                false);
        UIUtil.setBoundingBox(namePanel, PANEL_WIDTH * 0.9f, nameHeight * 2.0f, 0.04f);
        Component3D name = new Component3D();
        name.addChild(namePanel);
        return name;
    }
    
    private Component3D createPhoto() {
        BufferedImage photoImage = null;
        try {
            photoImage = (BufferedImage)contact.get(Contact.ATTR_PHOTO);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Exception getting photo for " +
                    (String)contact.get(Contact.ATTR_FIRSTNAME), e);
        }
        if (photoImage == null) {
            photoImage = orgChart.getDefaultPhoto();
        }
        Toolkit3D toolkit3d = Toolkit3D.getToolkit3D();
        float photoWidth =
                toolkit3d.widthNativeToPhysical(photoImage.getWidth());
        float photoHeight =
                toolkit3d.widthNativeToPhysical(photoImage.getHeight());
        
        Component3D photo = new Component3D();
        photoApp = new SimpleAppearance(1.0f, 1.0f, 1.0f, 1.0f,
                SimpleAppearance.ENABLE_TEXTURE | SimpleAppearance.DISABLE_CULLING);
        photoApp.setCapability(Appearance.ALLOW_TEXTURE_WRITE);
        FuzzyEdgePanel photoPanel =
                new FuzzyEdgePanel(photoWidth, photoHeight, 0.0f, photoApp);
        photo.addChild(photoPanel);
        
        setPhoto(photo, photoImage);
        return photo;
    }
    
    private void setPhoto(Component3D photo, BufferedImage photoImage) {
        if (photoApp != null) {
            UIUtil.setTexture(photoApp, photoImage);
            FuzzyEdgePanel photoPanel = (FuzzyEdgePanel)photo.getChild(0);
            Toolkit3D toolkit3d = Toolkit3D.getToolkit3D();
            float photoScale = 1.0f;
            float photoWidth =
                    toolkit3d.widthNativeToPhysical(photoImage.getWidth());
            float photoHeight =
                    toolkit3d.widthNativeToPhysical(photoImage.getHeight());
            if (photoWidth > PHOTO_WIDTH) {
                // scale photo down if it's too wide or too tall
                photoScale = PHOTO_WIDTH / photoWidth;
            }
            if ((photoHeight * photoScale) > PHOTO_HEIGHT) {
                photoScale = PHOTO_HEIGHT / photoHeight;
            }
            // scale and recompute size for next step
            photoPanel.setSize(photoWidth, photoHeight,
                    1.0f, 1.0f,
                    0, 0,
                    photoWidth, photoHeight);
            photo.setScale(photoScale);
            photoWidth *= photoScale;
            photoHeight *= photoScale;
            UIUtil.setBoundingBox(photoPanel, photoWidth, photoHeight, 0.06f);
        }
    }
    
    public void notify(Channel channel, EventObject event) {
        Contact contact = (Contact)event.getSource();
        BufferedImage photoImage = (BufferedImage)contact.get(Contact.ATTR_PHOTO);
        if (photoImage != null) {
            setPhoto(photo, photoImage);
        }
    }
}


