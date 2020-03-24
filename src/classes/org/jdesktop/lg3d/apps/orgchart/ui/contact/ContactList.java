/*
 * ContactList.java
 *
 * Created on June 1, 2005, 11:27 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package org.jdesktop.lg3d.apps.orgchart.ui.contact;

import java.util.List;
import javax.vecmath.Vector3f;
import org.jdesktop.lg3d.apps.orgchart.framework.contact.Contact;
import org.jdesktop.lg3d.apps.orgchart.framework.contact.ContactChannel;
import org.jdesktop.lg3d.utils.action.ActionBoolean;
import org.jdesktop.lg3d.utils.action.ActionChar;
import org.jdesktop.lg3d.utils.eventadapter.KeyTypedEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MousePressedEventAdapter;
import org.jdesktop.lg3d.wg.event.MouseEvent3D.ButtonId;
import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.wg.Toolkit3D;
import org.jdesktop.lg3d.wg.event.LgEventConnector;
import org.jdesktop.lg3d.wg.event.LgEventSource;

/**
 *
 * @author cc144453
 */
public class ContactList extends Container3D {
    
    private int size;
    private List<Contact> contactList;
    private Randomizer presenceRandomizer;
    
    /**
     * Creates a new instance of ContactList
     */
    public ContactList(Contact3D parent, List<Contact> contactList) {
        this.contactList = contactList;
        presenceRandomizer = new Randomizer(parent.getContext());
        
        setPreferredSize(new Vector3f(Toolkit3D.getToolkit3D().getScreenWidth(),
                Toolkit3D.getToolkit3D().getScreenHeight(),
                0.0f));
        
        // layout contact list
        size = parent.getNumContacts();
        setLayout(new ContactListLayout(size));
        
        // create one ContactPanel instance for each contact
        // note: need to properly handle add and remove later
        for (int i = 0; i < size; i++) {
            // make contacPanel the listener for presence events
            ContactChannel contactChannel =
                    new ContactChannel(parent.getContext(), contactList.get(i));
            ContactPanel contactPanel = new ContactPanel(parent, contactChannel);
            contactChannel.init();
            contactChannel.addListener(contactPanel);
            
            // monitor calendar and randomize presence
            presenceRandomizer.add(contactChannel);
            
            // finally add it
            addChild(contactPanel);
        }
        
        // set listener for switching mode with key typed
        LgEventConnector.getLgEventConnector().addListener(
                LgEventSource.ALL_SOURCES,
                new KeyTypedEventAdapter(new ActionChar() {
            public void performAction(LgEventSource source, char c) {
                toggleScreenMode();
            }
        }
        ));
        // right mouse button causes switching of mode
        addListener(new MousePressedEventAdapter(ButtonId.BUTTON3,
                new ActionBoolean() {
            public void performAction(LgEventSource source, boolean state) {
                ContactListLayout layout = (ContactListLayout)getLayout();
                if (layout != null) {
                    layout.setFullScreen(state);
                }
            }
        }));
        
    }
    
    int size() {
        return size;
    }
    
    ContactPanel getContactPanel(int index) {
        return (ContactPanel)getChild(index);
    }
    
    void toggleScreenMode() {
        ContactListLayout layout = (ContactListLayout)getLayout();
        if (layout != null) {
            layout.setFullScreen(!layout.isFullScreen());
        }
    }
    
    boolean isFullScreen() {
        ContactListLayout layout = (ContactListLayout)getLayout();
        return layout.isFullScreen();
    }
}

