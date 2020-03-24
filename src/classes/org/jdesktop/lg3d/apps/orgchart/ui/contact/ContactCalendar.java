package org.jdesktop.lg3d.apps.orgchart.ui.contact;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import org.jdesktop.lg3d.apps.orgchart.framework.Util;
import org.jdesktop.lg3d.apps.orgchart.framework.contact.Contact;
import org.jdesktop.lg3d.apps.orgchart.ui.common.UIUtil;
import org.jdesktop.lg3d.wg.Toolkit3D;
import org.jdesktop.lg3d.utils.shape.ImagePanel;
import org.jdesktop.lg3d.wg.Component3D;


public class ContactCalendar extends Component3D {
    
    private static final int DEFAULT_CELL_WIDTH = 20;
    private static final int DEFAULT_CELL_HEIGHT = 15;
    private static final int DEFAULT_INSET = 3;
    
    private static final int START_HOUR = 8;
    private static final int END_HOUR = 7;
    private static final int MAX_DAYS = 7;
    private static final int MAX_HOURS = 12 - START_HOUR + END_HOUR; // 8am-6pm
    private static final int MAX_COLUMNS = 1 + MAX_DAYS;  // empty + days of week
    private static final int MAX_ROWS = 1 + MAX_HOURS;  // empty + hours + inclusive
    
    private BufferedImage bi;
    private int baseX;
    private int baseY;
    private Contact contact;
    private int maxWidth;
    private int maxHeight;
    private int today;
    private int startHour;
    private long startTime;
    
    public ContactCalendar(Contact3D parent, Contact contact, float width, float height) {
        this.contact = contact;
        
        // create image by computing the bounds first, or loading from cached
        int biWidth;
        int biHeight;
        Toolkit3D toolkit3d = Toolkit3D.getToolkit3D();
        int physicalWidth = toolkit3d.widthPhysicalToNative(width);
        int physicalHeight = toolkit3d.widthPhysicalToNative(height);
        int nativeWidth = 2 * DEFAULT_INSET + DEFAULT_CELL_WIDTH * MAX_COLUMNS;
        int nativeHeight = 2 * DEFAULT_INSET + DEFAULT_CELL_HEIGHT * MAX_ROWS;
        float widthScale = (float)((double)physicalWidth / (double)nativeWidth);
        float heightScale = (float)((double)physicalHeight / (double)nativeHeight);
        if (widthScale > heightScale) {
            biWidth = nativeWidth;
            biHeight = (int)(widthScale * physicalHeight);
        } else {
            biWidth = (int)(heightScale * physicalWidth);
            biHeight = nativeHeight;
        }
        try {
            bi = Util.imageFromResource(parent.getContext(),
                    "ui/images/calendar/cal-" + contact.get(Contact.ATTR_UID) + ".png");
        } catch (IOException ioE) {
            parent.getContext().getLogger().log(Level.WARNING,
                    "Error loading calendar image contact=" + contact.get(Contact.ATTR_UID),
                    ioE);
        }
        if (bi == null) {
            //bi = new BufferedImage(biWidth, biHeight, BufferedImage.TYPE_INT_RGB);
            //baseX = (biWidth - nativeWidth) / 2;
            //baseY = (biHeight - nativeHeight) / 2;
            baseX = 0;
            baseY = 0;
            bi = new BufferedImage(nativeWidth, nativeHeight, BufferedImage.TYPE_INT_RGB);
        }
        ImagePanel imagePanel =
                new ImagePanel(
                            toolkit3d.widthNativeToPhysical(nativeWidth),
                            toolkit3d.heightNativeToPhysical(nativeHeight));
        UIUtil.setTexture(imagePanel, bi);
        //Buz3DUtil.setBoundingForward(imagePanel, width, height);
        addChild(imagePanel);
        
        // scale the component accordingly
        setScale(1.0f);
        //setScale((widthScale > heightScale) ? widthScale : heightScale);
    }
    
}

