package org.jdesktop.lg3d.apps.orgchart.ui.contact;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.util.EventObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.vecmath.Vector3f;
import org.jdesktop.lg3d.apps.orgchart.framework.Channel;
import org.jdesktop.lg3d.apps.orgchart.framework.ChannelListener;
import org.jdesktop.lg3d.apps.orgchart.framework.ServiceContext;
import org.jdesktop.lg3d.apps.orgchart.framework.Util;
import org.jdesktop.lg3d.apps.orgchart.framework.contact.Contact;
import org.jdesktop.lg3d.apps.orgchart.framework.contact.ContactChannel;
import org.jdesktop.lg3d.apps.orgchart.ui.common.Button;
import org.jdesktop.lg3d.apps.orgchart.ui.common.FramedPanel;
import org.jdesktop.lg3d.apps.orgchart.ui.common.UIUtil;
import org.jdesktop.lg3d.apps.orgchart.ui.common.TextPanel;
import org.jdesktop.lg3d.wg.Toolkit3D;
import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.utils.action.ActionBoolean;
import org.jdesktop.lg3d.utils.action.ActionNoArg;
import org.jdesktop.lg3d.utils.eventadapter.MousePressedEventAdapter;
import org.jdesktop.lg3d.utils.shape.FuzzyEdgePanel;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.utils.c3danimation.NaturalMotionAnimation;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.event.LgEventSource;
import org.jdesktop.lg3d.wg.event.MouseEvent3D.ButtonId;


/**
 * Very very preliminary help app whcih just shows the usage guide
 * on the screen.  Intended to become more sophisticated app as we
 * evolve the system...
 */
public class ContactPanel extends Component3D implements ChannelListener {
    private Logger logger = Logger.getLogger(getClass().getName());
    
    static final float PANEL_WIDTH = 0.05f;
    static final float PANEL_HEIGHT= 0.05f;
    static final float PANEL_DEPTH = 0.01f;
    static final float BORDER_WIDTH = 0.002f;
    
    private static final float NAME_FRONT_DEPTH = 0.003f;
    private static final float NAME_FRONT_HEIGHT = PANEL_DEPTH;
    private static final float NAME_FRONT_X = 0.005f;
    private static final float NAME_FRONT_Y = BORDER_WIDTH + NAME_FRONT_HEIGHT * 0.5f + 0.005f;
    
    private static final float PHOTO_X = PANEL_WIDTH * 0.3f;
    private static final float PHOTO_Y = NAME_FRONT_HEIGHT;
    private static final float PHOTO_WIDTH = PANEL_WIDTH - BORDER_WIDTH - PHOTO_X;
    private static final float PHOTO_HEIGHT = PANEL_HEIGHT - 2.5f * BORDER_WIDTH - PHOTO_Y;
    private static final float PHOTO_DEPTH = 0.005f;
    
    private static String AVAILABLE_FREE;
    private static String AVAILABLE_NOTFREE;
    private static final float AVAILABLE_WIDTH = PANEL_WIDTH;
    private static final float AVAILABLE_HEIGHT = NAME_FRONT_HEIGHT * 0.6f;
    private static final float AVAILABLE_X = NAME_FRONT_X;
    private static final float AVAILABLE_Y = NAME_FRONT_Y + AVAILABLE_HEIGHT;
    private static final float AVAILABLE_DEPTH = NAME_FRONT_DEPTH;
    
    private static final float BUTTON_SIZE = 0.008f;
    private static final float BUTTON_SPACING = 0.003f;
    private static final float BUTTON_DEPTH = BUTTON_SIZE * 0.5f;
    private static BufferedImage[] BUTTON_ICONS;
    private static String[] BUTTON_TEXT;
    private static String[] DISABLED_BUTTON_TEXT;
    
    private static final float[] COLOR_FRAME_ONLINE = new float[] { 0.0f, 1.0f, 0.0f, 1.0f };
    private static final float[] COLOR_FRAME_OFFLINE = new float[] { 1.0f, 0.0f, 0.0f, 1.0f };
    
    private static boolean createdStatics = false;
    private Contact3D parent;
    private ServiceContext context;
    private Contact contact;
    private String contactName;
    private float contactPanelWidth;
    private float contactPanelHeight;
    private SimpleAppearance presenceApp;
    private FramedPanel body;
    private TextPanel availabilityPanel;
    private ContactCalendar calendar;
    private Component3D photo;
    private SimpleAppearance photoApp;
    private Component3D buttonComp;
    private Button[] buttons;
    
    public ContactPanel(Contact3D parent, Contact contact) {
        this.parent = parent;
        this.context = parent.getContext();
        createStatics(context);
        this.contact = contact;
        updateName();
        createUI();
    }
    
    private static synchronized void createStatics(ServiceContext context) {
        if (!createdStatics) {
            try {
                AVAILABLE_FREE = context.i18n("contactAvailFree");
                AVAILABLE_NOTFREE = context.i18n("contactAvailNotFree");
                
                // load the mages
                BUTTON_ICONS = new BufferedImage[] {
                    Util.imageFromResource(context, "ui/images/IconSMS.png"),
                            Util.imageFromResource(context, "ui/images/IconEmail.png"),
                            Util.imageFromResource(context, "ui/images/IconVoIP.png"),
                            Util.imageFromResource(context, "ui/images/IconBlog.png"),
                            Util.imageFromResource(context, "ui/images/IconMeeting.png")
                };
                
                // read the button tip text
                BUTTON_TEXT = new String[] {
                    context.i18n("contactTipSMS"),
                            context.i18n("contactTipEmail"),
                            context.i18n("contactTipVOIP"),
                            context.i18n("contactTipBlog"),
                            context.i18n("contactTipMeeting")
                };
                DISABLED_BUTTON_TEXT = new String[] {
                    context.i18n("contactDisabledTipSMS"),
                            context.i18n("contactDisabledTipEmail"),
                            context.i18n("contactDisabledTipVOIP"),
                            context.i18n("contactDisabledTipBlog"),
                            context.i18n("contactDisabledTipMeeting")
                };
            } catch (Exception e) {
                throw new RuntimeException("Error initializing resources", e);
            }
            createdStatics = true;
        }
    }
    
    private void createUI() {
        contactPanelWidth = PANEL_WIDTH;
        contactPanelHeight = PANEL_HEIGHT;
        setPreferredSize(new Vector3f(contactPanelHeight,
                contactPanelHeight,
                PANEL_DEPTH));
        setMouseEventPropagatable(true);
        setAnimation(new NaturalMotionAnimation(Contact3D.ANIMATION_DURATION));
        
        // setup appearance for presence
        presenceApp = new SimpleAppearance(0.6f, 1.0f, 0.6f, 1.0f,
                SimpleAppearance.NO_GLOSS | SimpleAppearance.DISABLE_CULLING);
        
        // create name on the side
        Component3D spine = new Component3D();
        spine.addChild(new TextPanel(contactName,
                1.0f, // widthScale,
                contactPanelHeight, // maxWidth,
                PANEL_DEPTH * 0.9f, // height
                1, // xShift
                1, // yShift
                true)); // vertical
        spine.setTranslation(contactPanelWidth * -0.5f,
                contactPanelHeight * 0.5f,
                -PANEL_DEPTH);
        spine.setRotationAxis(0.0f, 1.0f, 0.0f);
        spine.setRotationAngle((float) Math.PI); // 180 deg
        
        // create contact's photo
        Component3D photo = new Component3D();
        BufferedImage photoImage = (BufferedImage)contact.get(Contact.ATTR_PHOTO);
        if (photoImage == null) {
            photoImage = parent.getDefaultPhoto();
        }
        Toolkit3D toolkit3d = Toolkit3D.getToolkit3D();
        float photoWidth =
                toolkit3d.widthNativeToPhysical(photoImage.getWidth());
        float photoHeight =
                toolkit3d.widthNativeToPhysical(photoImage.getHeight());
        photoApp = new SimpleAppearance(1.0f, 1.0f, 1.0f, 1.0f,
                SimpleAppearance.ENABLE_TEXTURE | SimpleAppearance.DISABLE_CULLING);
        FuzzyEdgePanel photoPanel =
                new FuzzyEdgePanel(photoWidth, photoHeight, 0.0f, photoApp);
        UIUtil.setTexture(photoApp, photoImage);
        UIUtil.setBoundingBox(photoPanel, photoWidth, photoHeight, 0.06f);
        photo.addChild(photoPanel);
        float photoScale = 1.0f;
        if (photoWidth > PHOTO_WIDTH) {
            // scale photo down if it's too wide or too tall
            photoScale = PHOTO_WIDTH / photoWidth;
        }
        if ((photoHeight * photoScale) > PHOTO_HEIGHT) {
            photoScale = PHOTO_HEIGHT / photoHeight;
        }
        // scale and recompute size for next step
        photo.setScale(photoScale);
        photoWidth *= photoScale;
        photoHeight *= photoScale;
        
        // compute the placement of the photo
        photo.setTranslation(
                (PHOTO_WIDTH - contactPanelWidth) * 0.5f  + PHOTO_X,
                (PHOTO_HEIGHT - contactPanelHeight) * 0.5f + BORDER_WIDTH,
                PHOTO_DEPTH);
        photo.setTransparency(0.0f);
        
        // create contact's name in front
        TextPanel namePanel = new TextPanel(contactName,
                1.0f, // widthScale,
                contactPanelWidth * 0.9f, // maxWidth,
                NAME_FRONT_HEIGHT, // height
                1, // xShift
                1, // yShift
                false);
        UIUtil.setBoundingBox(namePanel, contactPanelWidth * 0.9f, NAME_FRONT_HEIGHT, 0.04f);
        Component3D name = new Component3D();
        name.addChild(namePanel); // vertical
        name.setTranslation(
                contactPanelWidth * -0.5f + NAME_FRONT_X,
                contactPanelHeight * 0.5f - NAME_FRONT_Y,
                NAME_FRONT_DEPTH);
        
        // create contact's availability
        availabilityPanel = new TextPanel(contactName,
                1.0f, // widthScale,
                AVAILABLE_WIDTH, // maxWidth,
                AVAILABLE_HEIGHT, // height
                0, // xShift
                0, // yShift
                false);
        UIUtil.setBoundingBox(availabilityPanel, AVAILABLE_WIDTH, AVAILABLE_HEIGHT, 0.04f);
        Component3D availability = new Component3D();
        availability.addChild(availabilityPanel); // vertical
        availability.setTranslation(
                contactPanelWidth * -0.5f + AVAILABLE_X,
                contactPanelHeight * 0.5f - AVAILABLE_Y,
                AVAILABLE_DEPTH);
        
        // create buttons
        createButtons();
        buttonComp.setTranslation(-0.5f * contactPanelWidth,
                0.5f * contactPanelHeight - BUTTON_SPACING,
                BUTTON_DEPTH);
        
        // create free-busy panel
        calendar = new ContactCalendar(
                parent,
                contact,
                contactPanelWidth,
                contactPanelHeight);
        calendar.setRotationAxis(0.0f, 1.0f, 0.0f);
        calendar.setRotationAngle((float)Math.PI); // put it behind
        calendar.setTranslation(0.0f, 0.0f, -PANEL_DEPTH);
        
        // initialize presence and availability
        updatePresence();
        updateAvailability();
        
        // add them to the body component
        // create framed body
        body = new FramedPanel(
                contactPanelWidth, contactPanelHeight,
                PANEL_DEPTH, BORDER_WIDTH,
                presenceApp,
                new SimpleAppearance(1.0f, 1.0f, 1.0f, 1.0f,
                SimpleAppearance.ENABLE_TEXTURE | SimpleAppearance.DISABLE_CULLING));
        body.setAnimation(new NaturalMotionAnimation(Contact3D.ANIMATION_DURATION));
        body.setTranslation(0.0f,  0.0f, -0.002f);
        body.setRotationAxis(0.0f, 1.0f, 0.0f);
        body.addChild(spine);
        body.addChild(photo);
        body.addChild(name);
        body.addChild(availability);
        body.addChild(calendar);
        
        Component3D comp = new Component3D();
        comp.addChild(body);
        comp.addChild(buttonComp);
        
        // move rotation of body to left edge
        comp.setTranslation(0.5f * contactPanelWidth, 0.0f,  -0.5f * PANEL_DEPTH);
        addChild(comp);
        
        // left mouse button causes calendar to show up
        setRotationAxis(1.0f, 0.0f, 0.0f);
        addListener(new MousePressedEventAdapter(ButtonId.BUTTON1,
                new ActionBoolean() {
            public void performAction(LgEventSource source, boolean state) {
                body.changeRotationAngle(state ? (float)Math.PI : 0.0f);
            }
        }));
    }
    
    private void setPhoto(Component3D photo, BufferedImage photoImage) {
        if (photoApp != null && photoImage != null) {
            UIUtil.setTexture(photoApp, photoImage);
            FuzzyEdgePanel photoPanel = (FuzzyEdgePanel)photo.getChild(0);
            float photoScale = 1.0f;
            Toolkit3D toolkit3d = Toolkit3D.getToolkit3D();
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
    
    private void createButtons() {
        buttonComp = new Component3D();
        
        // enable or disable buttons
        final boolean[] states = new boolean[] {
            (contact.get(Contact.ATTR_PAGERMAIL) != null),
                    (contact.get(Contact.ATTR_EMAIL) != null),
                    (contact.get(Contact.ATTR_PHONE) != null),
                    (contact.get(Contact.ATTR_URL) != null),
                    (contact.get(Contact.ATTR_CALENDAR) != null)
        };
        
        // actions for buttons
        final String launchEmailMsg =  context.i18n("contactLaunchEmail");
        ActionNoArg[] actions = new ActionNoArg[] {
            new ActionNoArg() {
                public void performAction(LgEventSource source) {
                    parent.sendSMS(contact);
                }
            },
                    new ActionNoArg() {
                public void performAction(LgEventSource source) {
                    parent.showMessage(launchEmailMsg);
                    parent.sendEmail(contact);
                    parent.hideMessage();
                }
            },
                    new ActionNoArg() {
                public void performAction(LgEventSource source) {
                    parent.dial(contact);
                }
            },
                    new ActionNoArg() {
                public void performAction(LgEventSource source) {
                    parent.readBlog(contact);
                }
            },
                    new ActionNoArg() {
                public void performAction(LgEventSource source) {
                    float bodyAngle = body.getFinalRotationAngle();
                    float newAngle = (bodyAngle == 0.0f) ? (float)Math.PI : 0.0f;
                    body.changeRotationAngle(newAngle, Contact3D.ANIMATION_DURATION);
                }
            }
        };
        int numButtons = BUTTON_ICONS.length;
        buttons = new Button[numButtons];
        float buttonSpacing = contactPanelHeight / numButtons;
        for (int i = 0; i < numButtons; ++i) {
            Button button = new Button(
                    BUTTON_ICONS[i], BUTTON_SIZE,
                    BUTTON_TEXT[i],
                    DISABLED_BUTTON_TEXT[i],
                    actions[i],
                    states[i],
                    presenceApp);
            button.setTranslation(0.0f, -i * buttonSpacing, BUTTON_SIZE * -0.5f);
            buttonComp.addChild(button);
            buttons[i] = button;
        }
    }
    
    private void updateName() {
        contactName = (String)contact.get(Contact.ATTR_FIRSTNAME);
    }
    
    private void updatePresence() {
        if (body != null) {
            if (contact.get(Contact.ATTR_PRESENCE).equals(Contact.PRESENCE_OPEN)) {
                presenceApp.setColor(COLOR_FRAME_ONLINE[0],
                        COLOR_FRAME_ONLINE[1],
                        COLOR_FRAME_ONLINE[2],
                        COLOR_FRAME_ONLINE[3]);
            } else {
                presenceApp.setColor(COLOR_FRAME_OFFLINE[0],
                        COLOR_FRAME_OFFLINE[1],
                        COLOR_FRAME_OFFLINE[2],
                        COLOR_FRAME_OFFLINE[3]);
            }
        }
    }
    
    private void updateAvailability() {
        if (availabilityPanel != null) {
            boolean isAvailable = (contact.get(Contact.ATTR_CALENDAR_BUSY) == null);
            availabilityPanel.setText(
                    (isAvailable ? AVAILABLE_FREE : AVAILABLE_NOTFREE),
                    null);
        }
    }
    
    Contact getContact() {
        return contact;
    }
    
    void enableButtons(boolean enable) {
        buttonComp.setVisible(enable);
    }
    
    Appearance getPresenceAppearance() {
        return presenceApp;
    }
    
    public void notify(Channel channel, EventObject event) {
        logger.finer("Received id=" + contact.get(Contact.ATTR_UID) + " event=" + event);
        if (event instanceof ContactChannel.ContactChangeEvent) {
            String attr = ((ContactChannel.ContactChangeEvent)event).getAttribute();
            // repaint only if the visible attributes changed.
            if (attr.equals(Contact.ATTR_PHOTO)) {
                BufferedImage photoImage = (BufferedImage)contact.get(Contact.ATTR_PHOTO);
                if (photoImage != null) {
                    setPhoto(photo, photoImage);
                }
            } else if (attr.equals(Contact.ATTR_PRESENCE)) {
                updatePresence();
                // request to repaint
                requestParentToRevalidate();
            } else if (attr.equals(Contact.ATTR_CALENDAR_BUSY)) {
                updateAvailability();
                // request to repaint
                requestParentToRevalidate();
            }
        }
    }
    
}


