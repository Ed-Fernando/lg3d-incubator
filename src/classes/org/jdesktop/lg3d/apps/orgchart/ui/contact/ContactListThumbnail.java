/*
 * ContactListThumbnail.java
 *
 */

package org.jdesktop.lg3d.apps.orgchart.ui.contact;

import javax.vecmath.Vector3f;
import org.jdesktop.lg3d.apps.orgchart.ui.common.UIUtil;
import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.utils.action.ActionBoolean;
import org.jdesktop.lg3d.utils.eventadapter.MouseEnteredEventAdapter;
import org.jdesktop.lg3d.utils.shape.FuzzyEdgePanel;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.utils.shape.Sphere;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Thumbnail;
import org.jdesktop.lg3d.wg.Toolkit3D;
import org.jdesktop.lg3d.wg.event.LgEventSource;

/**
 *
 * @author cc144453
 */
public class ContactListThumbnail extends Thumbnail {
    
    private static final Vector3f SIZE = new Vector3f(0.08f, 0.06f, 0.04f);
    private static final float DEPTH = 0.0f;
    private static final float BG_EDGE_WIDTH = 0.01f;
    private static final float BG_BORDER_WIDTH = 0.01f;
    private static final float BG_DEPTH = -0.01f;
    private static final Appearance BG_APP =
            new SimpleAppearance(1.0f, 1.0f, 1.0f, 0.5f,
            SimpleAppearance.NO_GLOSS | SimpleAppearance.DISABLE_CULLING);
    private static final float SPHERE_SPACING = 0.005f;
    private static final float SPHERE_RADIUS = 0.02f;
    
    private Contact3D parent;
    private ContactList cont;
    private boolean setFullScreen;
    
    public ContactListThumbnail(Contact3D parent, ContactList cont) {
        this.parent = parent;
        this.cont = cont;
        createUI();
    }
    
    private void createUI() {
        
        float screenWidth = Toolkit3D.getToolkit3D().getScreenWidth();
        float screenHeight = Toolkit3D.getToolkit3D().getScreenHeight();
        
        // compute number of rows
        int numContacts = cont.size();
        int numRows = (int)Math.sqrt((double)numContacts);
        int numPerRow = numContacts / numRows;
        int numLastRow = numContacts % numPerRow;
        if (numLastRow > 0) {
            ++numPerRow;
        } else {
            numLastRow = numPerRow;
        }
        
        // compute size and scaling
        float compSize = SPHERE_RADIUS * 2.0f;
        float maxWidth = (compSize * numPerRow + SPHERE_SPACING * (numPerRow - 1));
        float maxHeight = (compSize * numRows + SPHERE_SPACING * (numRows - 1));
        float compScale = 1.0f;
        if (maxWidth > screenWidth) {
            compScale = screenWidth / maxWidth;
        }
        if (maxHeight > screenHeight) {
            // take the smaller of the height vs width scale
            float heightScale = screenHeight / maxHeight;
            if (heightScale < compScale) {
                compScale = heightScale;
            }
        }
        
        // compute base x, y positions
        float rowX = -0.5f * maxWidth * compScale;
        float lastX = -0.5f * (compSize * numLastRow +  SPHERE_SPACING * (numLastRow - 1)) * compScale;
        
        float x = rowX;
        float y = 0.5f * (compSize + SPHERE_SPACING) * (numRows - 1) * compScale;
        float xAdvance = (compSize + SPHERE_SPACING) * compScale;
        float yAdvance = (compSize + SPHERE_SPACING)  * compScale;
        
        // compute the max size
        float bgWidth = -2.8f * (x + BG_BORDER_WIDTH);
        float bgHeight = -2.5f * (y + BG_BORDER_WIDTH);
        
        // compute position for each sphere
        Component3D comp = new Component3D();
        int row = 1;
        int numCurrentRow = 0;
        for (int i = 0; i < numContacts; ++i) {
            ContactPanel contactPanel = cont.getContactPanel(i);
            ContactSphere sphere = new ContactSphere(contactPanel);
            if (numCurrentRow >= numPerRow) {
                // advanced to the next row
                ++row;
                numCurrentRow = 0;
                // check if last row
                if (row == numRows) {
                    x = lastX;
                } else {
                    x = rowX;
                }
                y -= yAdvance;
            }
            sphere.setScale(compScale);
            sphere.setTranslation(x, y, DEPTH);
            
            x += xAdvance;
            ++numCurrentRow;
            // add to thumbnail
            comp.addChild(sphere);
        }
        
        // toggle full screen mode
        setFullScreen = false;
        MouseEnteredEventAdapter mouseEnteredAdapter =
                new MouseEnteredEventAdapter(new ActionBoolean() {
            public void performAction(LgEventSource source, boolean state) {
                // if mouse enters, go to full screen mode to highlight that
                // however, if it's already in full screen mode, then no op
                if (state && !cont.isFullScreen()) {
                    cont.toggleScreenMode();
                    setFullScreen = true;
                } else if (!state && setFullScreen) {
                    cont.toggleScreenMode();
                    setFullScreen = false;
                }
            }
        });
        addListener(mouseEnteredAdapter);
        
        // add background
        FuzzyEdgePanel bg = new FuzzyEdgePanel(bgWidth, bgHeight, BG_EDGE_WIDTH, BG_APP);
        Component3D bgComp = new Component3D();
        bgComp.addChild(bg);
        bgComp.setTranslation(0.5f * bgWidth + (rowX - SPHERE_RADIUS * compScale - BG_BORDER_WIDTH),
                0.0f, BG_DEPTH);
        
        Component3D scalingComp = new Component3D();
        scalingComp.addChild(comp);
        scalingComp.addChild(bgComp);
        addChild(scalingComp);
        
        // set preferred size
        setPreferredSize(SIZE);
    }
    
    private class ContactSphere extends Component3D {
        private ContactSphere(final ContactPanel contactPanel) {
            Sphere sphere = new Sphere(SPHERE_RADIUS,
                    (Sphere.GENERATE_NORMALS | Sphere.GENERATE_TEXTURE_COORDS),
                    contactPanel.getPresenceAppearance());
        UIUtil.setBoundingBox(sphere.getShape(), SPHERE_RADIUS, SPHERE_RADIUS, 0.05f);
            addChild(sphere);
        }
    }
}
