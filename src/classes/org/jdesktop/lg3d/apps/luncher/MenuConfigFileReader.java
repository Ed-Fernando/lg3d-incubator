/*
 * MenuConfigFileReader.java
 *
 * Created on August 26, 2004, 12:32 PM
 */

package org.jdesktop.lg3d.apps.luncher;
import java.util.ArrayList;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.utils.component.Pseudo3DShortcut;
import org.jdesktop.lg3d.utils.layoutmanager.HorizontalLayout;

/**
 *
 * @author  Henrik Baastrup
 *
 * This class will read a XML file and transform it to shortcuts for the GlassyCardMenu class.
 *
 * The DTD for the XML input:
 *   <!ELEMENT menu (name, card*)>
 *   <!ELEMENT name (#PCDATA)>
 *   <!ELEMENT card (icon, command)>
 *   <!ELEMENT icon (#PCDATA)>
 *   <!ELEMENT command (#PCDATA)>
 */
public class MenuConfigFileReader extends DefaultHandler {
    private StringBuffer bodyContentBuffer = null;
    private ArrayList shortcuts = new ArrayList();
    private Container3D shortcutsContainer = null;
    private Pseudo3DShortcut shortcut = null;
    private String shortcutIconFile = null;
    private String shortcutCommand = null;
    private float shortcutIconSize = 0.01f;
    
    private boolean readingCardTag = false;
    private boolean readingShortcutTag = false;
    
    public MenuConfigFileReader() {
    }
    
    /**
     * Initialize the object
     */
    public void init() {
        bodyContentBuffer = null;
        readingCardTag = false;
        readingShortcutTag = false;
        
        shortcuts = new ArrayList();
    }//init
    
    /**
     * Extended methods from the DefaultHandler object
     */
    public void startDocument() {
    }
    
    /**
     * Extended methods from the DefaultHandler object
     */
    public void endDocument() {
    }
    
    /**
     * Extended methods from the DefaultHandler object
     */
    public void startElement(String namespaceURI, String sName, String qName, Attributes attrs) throws SAXException {
        String eName = sName; // element name
        if ("".equals(eName)) eName = qName; // not namespace aware

        if (eName.equals("menu")) {
        }
        else if (eName.equals("card")) {
            readingCardTag = true;
            shortcutsContainer = new Container3D();
            shortcutsContainer.setLayout(new HorizontalLayout(HorizontalLayout.AlignmentType.LEFT,  0.0025f));
        }
        else if (eName.equals("name")) {
        }
        else if (eName.equals("shortcut")) {
            if (readingCardTag) readingShortcutTag = true;
        }
        else if (eName.equals("icon")) {
        }
        else if (eName.equals("command")) {
        }
        else throw new SAXException("Wrong tag: "+eName);
    }//startElement
    
    /**
     * Extended methods from the DefaultHandler object
     */
    public void endElement(String namespaceURI, String sName, String qName) throws SAXException {
        String eName = sName; // element name
        if ("".equals(eName)) eName = qName; // not namespace aware
        
        if (eName.equals("menu")) {
        }
        else if (eName.equals("card")) {
            shortcuts.add(shortcutsContainer);
            readingCardTag = false;
        }
        else if (eName.equals("name")) {
            if (readingCardTag) shortcutsContainer.setName(bodyContentBuffer.toString().trim());
        }
        else if (eName.equals("shortcut")) {
            if (readingShortcutTag) {
                // Under the new convention shortcutIconSize is 1cm by default.
//                shortcut = new Pseudo3DShortcut(shortcutIconFile, shortcutIconSize, shortcutCommand);
                shortcut = new Pseudo3DShortcut(shortcutIconFile, shortcutCommand);
                shortcutsContainer.addChild(shortcut);
            }
            readingShortcutTag = false;
        }
        else if (eName.equals("icon")) {
            if (readingShortcutTag) shortcutIconFile = bodyContentBuffer.toString().trim();
        }
        else if (eName.equals("command")) {
            if (readingShortcutTag) shortcutCommand = bodyContentBuffer.toString().trim();
        }
        else {
            bodyContentBuffer = null;
            throw new SAXException("Wrong tag: "+eName);
        }
        bodyContentBuffer = null;
    }//endElement
    
    /**
     * Extended methods from the DefaultHandler object
     */
    public void characters(char buf[], int offset, int len) throws SAXException {
        if (bodyContentBuffer == null) bodyContentBuffer = new StringBuffer(1024);
        bodyContentBuffer.append(buf, offset, len);
    }

    /**
     * Will read the file given and create the shortcuts.
     */
    public void parseConfigFile(String fileName) throws SAXException {
        java.net.URL fileUrl = getClass().getClassLoader().getResource(fileName);
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            saxParser.parse(fileUrl.toString(), this);
        } catch (Exception ex) {
            throw new SAXException(ex);
        }
    }

    /**
     * Return the shortcuts created by the parseConfigFile method
     */
    public ArrayList getShortcuts() {return shortcuts;}
    
    /*
     * Set the icon size used in the shortcuts
     */
    public void setShortcutIconSize(float size) {shortcutIconSize = size;}
}
