/*
 * Chart3D.java
 *
 * Created on June 20, 2005, 10:33 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package org.jdesktop.lg3d.apps.orgchart.ui.chart;

import java.awt.image.BufferedImage;
import java.beans.XMLEncoder;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import org.jdesktop.lg3d.apps.orgchart.framework.contact.Contact;
import org.jdesktop.lg3d.apps.orgchart.framework.contact.ContactService;
import org.jdesktop.lg3d.apps.orgchart.framework.ServiceContext;
import org.jdesktop.lg3d.apps.orgchart.framework.ServiceContextFactory;
import org.jdesktop.lg3d.apps.orgchart.ui.common.AbstractOrgChartApp;
import org.jdesktop.lg3d.apps.orgchart.framework.Util;
import org.jdesktop.lg3d.apps.orgchart.ui.common.Button;
import org.jdesktop.lg3d.apps.orgchart.ui.common.UIUtil;
import org.jdesktop.lg3d.utils.action.ActionChar;
import org.jdesktop.lg3d.utils.action.ActionNoArg;
import org.jdesktop.lg3d.utils.cursor.WigglingCursor;
import org.jdesktop.lg3d.utils.eventadapter.KeyTypedEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.wg.Cursor3D;
import org.jdesktop.lg3d.wg.Frame3D;
import org.jdesktop.lg3d.wg.Toolkit3D;
import org.jdesktop.lg3d.wg.event.LgEventConnector;
import org.jdesktop.lg3d.wg.event.LgEventSource;
import org.jdesktop.lg3d.wg.event.MouseEvent3D;
import org.jdesktop.lg3d.apps.orgchart.ui.common.ContactPanel;


/**
 *
 * @author cc144453
 */
public class Chart3D extends AbstractOrgChartApp {

    static final float LEVEL_GAP = 0.14f;
    static final float LEVEL_DEPTH = 0.04f;
    static final float TREE_ANGLE = (float)(Math.PI / 6);
    static final float BUTTON_SIZE = 0.01f;
    static final float BUTTON_X = -0.04f;

    private static final Cursor3D CURSOR_BUSY =
        new WigglingCursor("Waiting", BUTTON_SIZE, true);

    private ContactTree contactTree;
    private Map openedContact;
    private QueryPanel queryUI;
    private Container3D levels;
    private Button upButton;
    private float leftMost;
    private float maxScale = 0.0f;

    public static final void main(String[] args) {
        new Chart3D();
    }

    public void createUI() throws Exception {
        // create/load contact tree
        contactTree = new ContactTree();

        // create query UI
        queryUI = new QueryPanel(this);
        addChild(queryUI);

        // create org levels
        addChild(levels = new Container3D());

        // create button
        upButton = new Button(
                       Util.imageFromResource(getContext(), "ui/images/arrow.png"),
                       BUTTON_SIZE,
                       "Up hierarchy",
                       "Top level",
                       new ActionNoArg() {
                           public void performAction(LgEventSource source) {
                               upLevel();
                           }
                       },
                       true,
                       new SimpleAppearance(1.0f, 1.0f, 1.0f, 1.0f,
                                            SimpleAppearance.ENABLE_TEXTURE | SimpleAppearance.DISABLE_CULLING));
        upButton.setVisible(false);
        levels.addChild(upButton); // add button to levels

        // rotate slightly
        levels.setRotationAxis(1.0f, 0.6f, 0.0f);
        levels.setRotationAngle(TREE_ANGLE);
        levels.addListener(new MouseClickedEventAdapter(
                               MouseEvent3D.ButtonId.BUTTON3,
                               new ActionNoArg() {
                                   public void performAction(LgEventSource source) {
                                       upLevel();
                                   }
                               }
                           ));

        setVisible(true);
        changeEnabled(true);

        // set key and mouse listeners
        LgEventConnector.getLgEventConnector().addListener(
            LgEventSource.ALL_SOURCES,
            new KeyTypedEventAdapter(new ActionChar() {
                                         public void performAction(LgEventSource source, char c) {
                                             switch (c) {
                                             case 's':
                                             case 'S':
                                                 saveOrgChart();
                                                 break;
                                             default:
                                                 // move up one level
                                                 upLevel();
                                                 break;
                                             }
                                         }
                                     }
                                    ));
    }

    void initializeTree(Iterator<Map> contacts) {
        // hide the queryUI
        queryUI.setVisible(false);

        // remove all entries from the contact tree model and UI
        contactTree.removeAll();
        for (int i = levels.numChildren(); --i >= 1; ) {
            levels.removeChild(i);
        }

        // add to the data model
        while (contacts.hasNext()) {
            contactTree.addRoot(contacts.next());
        }

        // now create UI
        if (contactTree.getRoot().size() > 0) {
            OrgLevel rootLevel = createLevel(contactTree.getRoot(), 0.0f,  1.0f);
            leftMost = rootLevel.getLeft();
            maxScale = rootLevel.getContactScale();
            levels.addChild(rootLevel);
        }
        levels.revalidate();

        // position and show button
        adjustButton();
        upButton.setVisible(true);

    }

    private OrgLevel createLevel(Map contact, float depth, float maxScale)
    throws Exception {
        List<Map> contactList = contactTree.getChildren(contact);
        // if tree does not contain children, but contact has reports, fetch it
        if (contactList == null && getContactService().hasDirectReports(contact)) {
            Iterator<Map> reports = getContactService().searchDirectReports(contact);
            contactTree.addChildren(contact, reports);
            contactList = contactTree.getChildren(contact);
        }
        return createLevel(contactList, depth, maxScale);
    }

    private OrgLevel createLevel(
        Collection<Map> contactList, float depth, float maxScale) {
        if (contactList != null) {
            OrgLevel orgLevel = new OrgLevel();
            ContactStackLayout layoutManager =
                new ContactStackLayout(this, depth, leftMost, maxScale,
                                       Toolkit3D.getToolkit3D().getScreenWidth(), 
                                       Toolkit3D.getToolkit3D().getScreenHeight());
            orgLevel.setLayout(layoutManager);
            int count = 0;
for (Map contact : contactList) {
                orgLevel.addChild(new ContactPanel(this, contact));
                ++count;
            }
            orgLevel.revalidate();
            // do not return anything unless there are nodes
            return (count > 0) ? orgLevel : null;
        }
        return null;
    }

    void addLevel(Map contact) {
        try {
            setCursor(CURSOR_BUSY);

            OrgLevel newOrgLevel = createLevel(contact, 0.0f, maxScale);
            levels.addChild(newOrgLevel);
            adjustDepth(newOrgLevel.getContactScale());
            adjustLeftAndButton();

            setCursor(Cursor3D.DEFAULT_CURSOR);
        } catch (Exception e) {
            throw new RuntimeException("Error adding contact level", e);
        }
    }

    void removeLevels(Container3D level) {
        int numChildren = levels.numChildren();
        for (int i = 1; i < numChildren; i++) {
            OrgLevel child = (OrgLevel)levels.getChild(i);
            if (child == level) {
                // remove all subsequent ones
                // gotta do it backwards, or would mess up the indexes
                for (int j = numChildren; --j > i; ) {
                    levels.removeChild(j);
                }
                levels.revalidate();
                break; // get out of here
            }
        }
        // and then move every level back

        float depth = 0.0f;
        for (int i = levels.numChildren(); --i >= 1; ) {
            OrgLevel remainingLevel = (OrgLevel)levels.getChild(i);
            remainingLevel.setDepth(depth);
            remainingLevel.revalidate();
            depth -= LEVEL_GAP;
        }
        adjustLeftAndButton(); // adjust left and button position
    }

    private void upLevel() {
        setCursor(CURSOR_BUSY);
        revalidate();

        int numChildren = levels.numChildren();
        if (numChildren > 0) {
            // get the first level, and pick the first entry if non open
            OrgLevel firstLevel = (OrgLevel)levels.getChild(1);
            Map firstContact = firstLevel.getSelected();
            if (firstContact == null) {
                firstContact = ((ContactPanel)firstLevel.getChild(0)).getContact();
                firstLevel.select(firstContact);
            }
            try {
                Map manager = getContactService().searchManager(firstContact);
                if (manager != null) {
                    ContactTree newTree = new ContactTree();
                    // check to see if boss has peers, if so, add them too
                    Map managerManager = getContactService().searchManager(manager);
                    if (managerManager != null) {
                        Iterator<Map> newRoots =
                            getContactService().searchDirectReports(managerManager);
                        while (newRoots.hasNext()) {
                            newTree.addRoot(newRoots.next());
                        }
                    } else {
                        newTree.addRoot(manager);
                    }
                    // add firstContact's peers
                    Iterator<Map> firstContactPeers =
                        getContactService().searchDirectReports(manager);
                    List<Map> firstContactPeerList = new ArrayList<Map>();
                    while (firstContactPeers.hasNext()) {
                        Map firstContactPeer = firstContactPeers.next();
                        newTree.addChild(manager, firstContactPeer);
                        firstContactPeerList.add(firstContactPeer);
                    }
                    // now merge the tree
                    newTree.merge(contactTree);
                    //System.out.println("---- " +newTree);
                    contactTree = newTree;
                    // remove the old root
                    levels.removeChild(1);
                    // insert the new root and the new child
                    OrgLevel newRootLevel = createLevel(newTree.getRoot(), 0.0f, maxScale);
                    newRootLevel.select(manager);
                    levels.insertChild(newRootLevel, 1);
                    OrgLevel oldRootLevel = createLevel(firstContactPeerList, 0.0f, maxScale);
                    oldRootLevel.select(firstContact);
                    levels.insertChild(oldRootLevel, 2);
                    adjustDepth(newRootLevel.getContactScale());
                    adjustLeftAndButton();
                }
            } catch (Exception e) {
                throw new RuntimeException("Error getting manager for " +
                                           firstContact.get(Contact.ATTR_UID), e);
            }
        }
        setCursor(Cursor3D.DEFAULT_CURSOR);
        revalidate();
        levels.revalidate();
    }

    private void adjustDepth(float newScale) {
        boolean changeScale = false;
        if (newScale > 0.0f && newScale < maxScale) {
            maxScale = newScale;
            changeScale = true;
        }

        // move everyone else out
        int numChildren = levels.numChildren();
        float depth = numChildren * LEVEL_GAP * 0.2f;
        if (depth > LEVEL_DEPTH) {
            depth = LEVEL_DEPTH;
        }
        for (int i = numChildren; --i >= 1; ) {
            Component3D comp = (Component3D)levels.getChild(i);
            OrgLevel orgLevel = (OrgLevel)comp;
            orgLevel.setDepth(depth);
            if (changeScale) {
                orgLevel.setContactScale(maxScale);
            }
            depth -= LEVEL_GAP;
            orgLevel.revalidate();
        }
    }

    private void adjustLeftAndButton() {
        // find the left most position and depth
        int numChildren = levels.numChildren();
        for (int i = numChildren; --i >= 1; ) {
            Component3D comp = (Component3D)levels.getChild(i);
            OrgLevel orgLevel = (OrgLevel)comp;
            float left = orgLevel.getLeft();
            if (left < leftMost) {
                leftMost = left;
            }
        }
        // align everyone to the same left axis
        numChildren = levels.numChildren();
        for (int i = numChildren; --i >= 1; ) {
            Component3D comp = (Component3D)levels.getChild(i);
            OrgLevel orgLevel = (OrgLevel)comp;
            float left = orgLevel.getLeft();
            if (left != leftMost) {
                orgLevel.setLeft(leftMost);
                orgLevel.revalidate();
            }
        }
        adjustButton();
    }

    private void adjustButton() {
        // adjust button position
        if (levels.numChildren() > 1) {
            upButton.setTranslation(
                leftMost + BUTTON_X,
                0.0f,
                ((OrgLevel)levels.getChild(1)).getDepth());
            revalidate();
        }
    }

    private void saveOrgChart() {
        try {
            FileOutputStream fout = new FileOutputStream("./orgchart.xml");
            XMLEncoder encoder = new XMLEncoder(fout);
            encoder.writeObject(contactTree);
            encoder.close();
            fout = new FileOutputStream("./orgchart.ser");
            ObjectOutputStream oout = new ObjectOutputStream(fout);
            oout.writeObject(contactTree);
            oout.close();
        } catch (IOException ioE) {
            throw new RuntimeException("Error writing XML output", ioE);
        }
    }

    private class OrgLevel extends Container3D {
        private void setLeft(float left) {
            ((ContactStackLayout)getLayout()).setLeft(left);
        }
        private float getLeft() {
            return ((ContactStackLayout)getLayout()).getLeft();
        }
        private void setDepth(float depth) {
            ((ContactStackLayout)getLayout()).setDepth(depth);
        }
        private float getDepth() {
            return ((ContactStackLayout)getLayout()).getDepth();
        }
        private void setContactScale(float scale) {
            ((ContactStackLayout)getLayout()).setScale(scale);
        }
        private float getContactScale() {
            return ((ContactStackLayout)getLayout()).getScale();
        }
        private void select(Map contact) {
            ((ContactStackLayout)getLayout()).select(contact);
        }
        private Map getSelected() {
            return ((ContactStackLayout)getLayout()).getSelected();
        }
    }
}
