/**
 */
package org.jdesktop.lg3d.apps.orgchart.ui.contact;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.swing.JOptionPane;
import org.jdesktop.lg3d.apps.orgchart.framework.ServiceContext;
import org.jdesktop.lg3d.apps.orgchart.framework.ServiceContextFactory;
import org.jdesktop.lg3d.apps.orgchart.framework.Util;
import org.jdesktop.lg3d.apps.orgchart.framework.contact.Contact;
import org.jdesktop.lg3d.apps.orgchart.framework.contact.ContactService;
import org.jdesktop.lg3d.apps.orgchart.framework.contact.PreferenceContactService;
import org.jdesktop.lg3d.apps.orgchart.ui.common.MessagePanel;
import org.jdesktop.lg3d.apps.orgchart.ui.common.UIUtil;
import org.jdesktop.lg3d.wg.Frame3D;

/*
import org.jdesktop.jdic.desktop.Desktop;
import org.jdesktop.jdic.desktop.Message;
import javax.vecmath.Vector3f;
import org.jdesktop.lg3d.wg.Component3D;
import com.mnstarfire.loaders3d.Inspector3DS;
import com.mnstarfire.loaders3d.Loader3DS;
import org.jdesktop.lg3d.wg.ModelLoader;
 */

public class Contact3D extends Frame3D {

    static final String PATH_CONTACTS = PreferenceContactService.DEFAULT_ROOT;

    static final int ANIMATION_DURATION = 300; // 300ms
    static final int LAYOUT_DURATION = 750; // 750ms
    static final int MESSAGE_DURATION = 5000; // 5000ms
    static final float MESSAGE_WIDTH = 0.08f;
    static final float MESSAGE_HEIGHT = 0.02f;
    static final int MAX_CONTACTS = 12;


    private static Logger logger = Logger.getLogger(Contact.class.getName());

    // framework members
    private ServiceContext context;
    private ContactService contactService;
    private List contactList;
    private int numContacts;
    private BufferedImage defaultPhoto;

    // UI members
    private ContactList cont;
    private MessagePanel messagePanel;

    public static final void main(String[] args) {
        new Contact3D();
    }

    public Contact3D() {
        super();

        try {
            // create splash screen
            long startTime = System.currentTimeMillis();

            // initialize framework
            initFramework();

            // initialize numContacts
            numContacts = contactList.size();
            if (numContacts > MAX_CONTACTS) {
                numContacts = MAX_CONTACTS;
            }

            // display UI including splash
            createUI();
            setVisible(true);
            changeEnabled(true);

            // launch a separate task to toggle the screen mode
            context.schedule(
                    new Runnable() {
                public void run() {
                    cont.toggleScreenMode();
                }
            },
                    (System.currentTimeMillis() + LAYOUT_DURATION * 2));
        } catch (Exception e) {
            UIUtil.errorDialog(context, "Fatal exception", e);
        }
    }

    private void initFramework() throws Exception {
        // populate the contact information if not done
        if (!Preferences.userRoot().nodeExists(PATH_CONTACTS)) {
            Preferences.userRoot().importPreferences(
                    getClass().getClassLoader().getResourceAsStream(
                    "org/jdesktop/lg3d/apps/orgchart/ui/contact/contacts.xml"));
        } else {
            // however, we always do it just to get the most updated set
            // for development purposes
            Preferences.userRoot().node(PATH_CONTACTS).removeNode();
            Preferences.userRoot().importPreferences(
                    getClass().getClassLoader().getResourceAsStream(
                    "org/jdesktop/lg3d/apps/orgchart/ui/contact/contacts.xml"));
        }

        // create ServiceContext
        context = ServiceContextFactory.getInstance().createServiceContext();
        context.setResourceRoot("org/jdesktop/lg3d/apps/orgchart");

        // launch contact service
        contactService = (ContactService)context.getService("contacts.contact3d");
        contactService.start();

        // setup the list of contacts to put into the contact list
        Preferences contactsRoot = Preferences.userRoot().node(PATH_CONTACTS);
        String[] contactNames = contactsRoot.childrenNames();
        contactList = new ArrayList<Contact>(contactNames.length);
        for (int i = 0; i < contactNames.length; i++) {
            Map contact = contactService.lookup(contactNames[i]);
            if (contact == null) {
                throw new RuntimeException("Error loading contact=" + contactNames[i]);
            }
            contactList.add(contact);
        }
        if (contactList.size() < 1) {
            throw new RuntimeException("No contacts defined.");
        }

        // load default photo
        defaultPhoto = Util.imageFromResource(context, "ui/images/DefaultPhoto.png");

    }

    private void createUI() throws Exception {
        // create and layout contacts
        cont = new ContactList(this, contactList);
        addChild(cont);

        // create message panel
        messagePanel = new MessagePanel(this, null, MESSAGE_WIDTH, MESSAGE_HEIGHT);
        addChild(messagePanel);

        // set thumbnail
        setThumbnail(new ContactListThumbnail(this, cont));

        /*
        ModelLoader model1 = new ModelLoader( "/tmp",
                "pinguin.3DS",
                Class.forName("com.mnstarfire.loaders3d.Loader3DS"));
        model1.resize(new Vector3f(0.0f, 0.0f, 0.0f), 0.03f);
        addChild(model1);
         */
    }

    public ServiceContext getContext() {
        return context;
    }

    public Logger getLogger() {
        return logger;
    }

    public ContactService getContactService() {
        return contactService;
    }

    public int getNumContacts() {
        return numContacts;
    }

    public BufferedImage getDefaultPhoto() {
        return defaultPhoto;
    }

    void showMessage(String text) {
        messagePanel.setText(text);
        messagePanel.show(true, MESSAGE_DURATION);
    }

    void hideMessage() {
        messagePanel.show(false, MESSAGE_DURATION);
    }

    void sendEmail(Contact contact) {
        /*
        Message msg = new Message();
        List<String> toList = new ArrayList<String>();
        toList.add((String)contact.get(Contact.ATTR_EMAIL));
        msg.setToAddrs(toList);
        try {
            // Send mail in UI mode.
            //Desktop.mail(msg);
        } catch (Exception ex) {
            UIUtil.errorDialog(context, context.i18n("contactErrorEmail"), ex);
        }
         */
    }

    void sendSMS(Contact contact) {
        try {
        } catch (Exception ex) {
            UIUtil.errorDialog(context, context.i18n("contactErrorSMS"), ex);
        }
    }

    void readBlog(Contact contact) {
        try {
            //Desktop.browse(new URL((String)contact.get(Contact.ATTR_URL)));
        } catch (Exception ex) {
            UIUtil.errorDialog(context, context.i18n("contactErrorBlog"), ex);
        }
    }
    void dial(Contact contact) {
        try {
        } catch (Exception ex) {
            UIUtil.errorDialog(context, context.i18n("contactErrorVOIP"), ex);
        }
    }
}
