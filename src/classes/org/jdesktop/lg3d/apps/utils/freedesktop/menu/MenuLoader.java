/***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 *   This program is distributed in the hope that it will be useful,       *
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of        *
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the         *
 *   GNU General Public License for more details.                          *
 *                                                                         *
 *   You should have received a copy of the GNU General Public License     *
 *   along with this program; if not, write to the                         *
 *   Free Software Foundation, Inc.,                                       *
 *   59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.             *
 *                                                                         *
 *   Copyright (C) 2006                                                    * 
 *                  Juan Gonzalez (juan@aga-system.com)                    *
 *                & Sun Microsystems                                       *
 *                                                                         *
 ***************************************************************************
 * MenuLoader.java
 *
 * Created on 26 de julio de 2006, 17:08
 *
 * $Revision: 1.3 $
 * $Date: 2006-08-17 23:22:20 $
 * $State: Exp $
 */

package org.jdesktop.lg3d.apps.utils.freedesktop.menu;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.jdesktop.lg3d.apps.utils.freedesktop.IconFinder;
import org.jdesktop.lg3d.apps.utils.freedesktop.common.FileDesktopEntry;
import org.jdesktop.lg3d.apps.utils.freedesktop.common.XDG;
import org.jdesktop.lg3d.scenemanager.utils.startmenu.StartMenuGroupConfig;
import org.jdesktop.lg3d.scenemanager.utils.startmenu.StartMenuItemConfig;
import org.jdesktop.lg3d.wg.event.LgEventConnector;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.DocumentTraversal;
import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.traversal.NodeIterator;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author opsi
 */
public class MenuLoader {
    private String VENDOR_PREFIX = "";
    private static final String MENU_FILE = "applications";
    /** Creates a new instance of MenuLoader */
    private Logger logger;
    private EntityResolver resolver;
    private DocumentBuilder builder;
    private Hashtable<String,Node> menus;
    private Hashtable<String,FileDesktopEntry> menuDirectoryFiles;
    private Hashtable<String,List<FileDesktopEntry>> menuContents;
    
    public MenuLoader() throws IOException,ParserConfigurationException,SAXException {
        this(null);
    }
    
    public MenuLoader(String vendorPrefix) throws IOException,ParserConfigurationException,SAXException{
        if(vendorPrefix!=null && vendorPrefix.length()>0)
            VENDOR_PREFIX = vendorPrefix+"-";
        logger = Logger.getLogger("lg.menu");
        
        menus = new Hashtable<String,Node>();
        menuDirectoryFiles =  new Hashtable<String,FileDesktopEntry>();
        menuContents = new Hashtable<String,List<FileDesktopEntry>>();
        
        resolver = new EntityResolver() {
            InputSource dtdSource;
            boolean localDtdLoadFailed = false;
            public InputSource resolveEntity(String publicId, String systemId) throws SAXException,IOException {
                if(!localDtdLoadFailed && dtdSource==null && "http://www.freedesktop.org/standards/menu-spec/1.0/menu.dtd".equals(systemId)) {
                    try {
                        InputStream is = getClass().getResourceAsStream("/org/jdesktop/lg3d/apps/utils/freedesktop/menu/menu.dtd");
                        dtdSource = new InputSource(is);
                    } catch (Exception ex) {
                        logger.warning("I can't load the local menu.dtd, i'll try the online version");
                        localDtdLoadFailed = true;
                    }
                }
                return dtdSource;
            }
        };
        
        builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        builder.setEntityResolver(resolver);
        File menuFile = new File(XDG.getValue(XDG.DIR.XDG_CONFIG_DIRS)+"/menus/" + VENDOR_PREFIX+ MENU_FILE + ".menu");
        Document doc = builder.parse(new FileInputStream(menuFile));
                
        firstPass(doc,new File(menuFile.getParent()));
        secondPass(doc);
        thirdPass(doc);
        generateMenus();
        generateItems();        
    }
    private void generateItems() {
        for(String menuName : menuContents.keySet())
        {
            String menuId = menuName.substring(menuName.lastIndexOf("/"));
            List<FileDesktopEntry> items = menuContents.get(menuName);
            for(FileDesktopEntry entry:items)
            {
                StartMenuItemConfig item = new StartMenuItemConfig();
                item.setMenuGroup(menuId);
                item.setCommand(entry.getValueFor("Exec"));
                item.setDesc(entry.getValueFor("Comment"));
                item.setDisplayResourceType("ICON");
                item.setDisplayResourceUrl(IconFinder.getInstance().findIconFor(entry.getValueFor("Icon"),64));
                item.setName(entry.getName(Locale.getDefault()));                
            }
            
        }
    }
    private void generateMenus() {
        List<String> menusAdded = new LinkedList<String>();
        for(String menuName:menuContents.keySet()) {
            String [] parentMenus = menuName.substring(1).split("/");
            StringBuffer currentParent=new StringBuffer();
            for(int i = 0; i < parentMenus.length; i++) {
                currentParent.append("/"+parentMenus[i]);
                if(!menusAdded.contains(currentParent)) {
                    menusAdded.add(currentParent.toString());
                    StartMenuGroupConfig grp = new StartMenuGroupConfig();
                    
                    FileDesktopEntry menuDirFile = menuDirectoryFiles.get(currentParent.toString());
                    if(menuDirFile != null) {
                        grp.setDesc(menuDirFile.getValueFor("Comment"));
                        grp.setName(menuDirFile.getValueFor("Name"));
                    } else {
                        grp.setDesc(menuName);
                        grp.setName(menuName);
                    }
                    if(menusAdded.size()==1)
                        grp.setGroupLinks(new String[] {"Main"});
                    else
                        grp.setGroupLinks(currentParent.toString().substring(1).split("/"));
                    grp.setDefaultGroup(false);
                    //System.out.println("Adding parent : " + currentParent + " to " + menuName);
                    try {
                        LgEventConnector.getLgEventConnector().postEvent(grp,null);
                    } catch(RuntimeException ex) {
                        
                    }
                } else
                    ;//System.err.println("Not adding parent : " + currentParent);
            }
        }
    }
      
    private void firstPass(Document doc,File currentDir) throws IOException {
        NodeIterator iter = ((DocumentTraversal)doc).createNodeIterator(doc,NodeFilter.SHOW_ELEMENT | NodeFilter.SHOW_COMMENT,null,true);
        Node node,parent;
        while((node = iter.nextNode())!=null) {
            parent = node.getParentNode();
            if(node.getNodeType() == Node.COMMENT_NODE) {
                parent.removeChild(node);
                continue;
            }
            MenuFileElements.Possible type = MenuFileElements.toEnum(node.getNodeName());
            switch(type) {
                //First things that are handled in any other place
                case Menu:
                    break;
                case And:
                    break;
                case Or:
                    break;
                case All:
                    break;
                case Include:
                    break;
                case Exclude:
                    break;
                case Name:
                    break;
                case Filename:
                    break;
                case Category:
                    break;
                case Directory:
                    break;
                    // now let's work on things for the first pass
                case DefaultAppDirs:
                    DesktopEntryPool.processDefaultAppDirs();
                    parent.removeChild(node);
                    break;
                case AppDir:
                    DesktopEntryPool.processAppDir(node.getTextContent());
                    parent.removeChild(node);
                    break;
                case DefaultDirectoryDirs:
                    DirectoryEntryPool.processDefaultDirectoryDirs();
                    parent.removeChild(node);
                    break;
                case DirectoryDir:
                    DirectoryEntryPool.processDirectoryDir(node.getTextContent());
                    parent.removeChild(node);
                    break;
                case MergeFile:
                    parent.removeChild(node);
                    File file = new File(currentDir,node.getTextContent());
                    if(!file.canRead())
                        file = new File(System.getProperty("user.home")+"/.config/menus/",node.getTextContent());
                    mergeFile(file,doc,parent);
                    break;
                case MergeDir:
                    parent.removeChild(node);
                    mergeDir(node.getTextContent(),doc,parent);
                    break;
                case DefaultMergeDirs:
                    parent.removeChild(node);
                    String mdir = currentDir.getAbsolutePath()+VENDOR_PREFIX+MENU_FILE+"-merged";
                    mergeDir(mdir,doc,parent);
                    break;
                default:
                    logger.fine(type + " element handling not implemented yet (" + node.getTextContent() + ")");
                    parent.removeChild(node);
            }
        }
    }
    private void secondPass(Document doc) throws IOException {
        //First build the complete slashed menu name and associate it with the Node representing the menu
        NodeFilter filter = new NodeFilter() {
            public short acceptNode(Node n) {
                if(MenuFileElements.Name.equals(n.getNodeName()) || MenuFileElements.Directory.equals(n.getNodeName()))
                    return NodeFilter.FILTER_ACCEPT;
                else
                    return NodeFilter.FILTER_SKIP;
            }
        };
        NodeIterator walker= ((DocumentTraversal)doc).createNodeIterator(doc,NodeFilter.SHOW_ALL,filter,true);
        Node node = null,tmpNode;
        StringBuffer buffer = null;
        while ((node = walker.nextNode()) != null) {
            if(MenuFileElements.Name.equals(node.getNodeName())) {
                buffer = new StringBuffer();
                tmpNode = node;
                while ((tmpNode = tmpNode.getParentNode()) != null && MenuFileElements.Menu.equals(tmpNode.getNodeName())) {
                    buffer.insert(0,"/"+getMenuName(doc,tmpNode));
                }
                menus.put(buffer.toString(),node.getParentNode());
            } else {
                assert(buffer!=null);
                Node parent = node.getParentNode();
                FileDesktopEntry entry = DirectoryEntryPool.getDirectoryEntry(node.getTextContent());
                if(entry!=null) {
                    menuDirectoryFiles.put(buffer.toString(),entry);
                }
                parent.removeChild(node);
            }
        }
    }
    private void thirdPass(Document doc) {
        RuleSet rules = new RuleSet();
        for(String key:menus.keySet()) {
            Node menu = menus.get(key);
            NodeList childs = menu.getChildNodes();
            Node rule;
            List<FileDesktopEntry> entries = menuContents.get(key);
            if(entries == null)
                entries = new LinkedList<FileDesktopEntry>();
            for(int i = 0 ; i < childs.getLength();i++) {
                rule=childs.item(i);
                if(rule.getNodeName().equals(MenuFileElements.Include) && rule.hasChildNodes()) {
                    //System.out.println("Include para " + key);
                    rules.processRuleSet(rule);
                    entries.addAll(DesktopEntryPool.getEntriesFor(rules));
                    
                } else if(rule.getNodeName().equals(MenuFileElements.Exclude)) {
                    //System.out.println("Exclude para " + key);
                    rules.processRuleSet(rule);
                    entries.removeAll(DesktopEntryPool.getEntriesFor(rules));
                }
            }
            if(entries.size()>0) {
                menuContents.put(key,entries);
            }
        }
    }
    private void mergeDir(String dir, Document destDoc, Node baseNode) {
        File mergeDir = new File(dir);
        if(mergeDir.canRead() && mergeDir.isDirectory()) {
            File []files = mergeDir.listFiles(new FileFilter() {
                public boolean accept(File pathname) {
                    return pathname.getName().endsWith(".menu");
                }
            });
            for(File menuFile : files) {
                mergeFile(menuFile,destDoc,baseNode);
            }
        }
    }
    private void mergeFile(File file, Document destDoc, Node baseNode) {
        Document doc;
        try {
            doc = builder.parse(new FileInputStream(file));
            firstPass(doc,new File(file.getParent()));
        } catch (Exception ex) {
            logger.fine("There was a file to merge that wasn't found : " + file.getName() + " " + ex.getMessage());
            //ex.printStackTrace();
            baseNode.getParentNode().removeChild(baseNode);
            return;
        }
        //TreeWalker walker = ((DocumentTraversal)doc).createTreeWalker(doc,NodeFilter.SHOW_ELEMENT,null,true);
        NodeIterator iter = ((DocumentTraversal)doc).createNodeIterator(doc,NodeFilter.SHOW_ALL,null,true);
        NodeList nodes = iter.getRoot().getChildNodes();
        Node tmpnode;
        while((tmpnode = iter.nextNode())!=null) {
            switch (tmpnode.getNodeType()) {
                case Node.ELEMENT_NODE:
                case Node.TEXT_NODE:
                case Node.ATTRIBUTE_NODE:
                    break;
                default:
                    Node parent = tmpnode.getParentNode();
                    if(parent!=null)
                        parent.removeChild(tmpnode);
            }
        }
        for(int i = 0; i < nodes.getLength();i++) {
            NodeList nodes2 = nodes.item(i).getChildNodes();
            for(int j = 0; j < nodes2.getLength();j++) {
                Node newNode = destDoc.importNode(nodes2.item(j),true);
                baseNode.appendChild(newNode);
            }
        }
    }
    private void printDocument(Document doc) {
        OutputFormat format = new OutputFormat(doc);
        format.setLineWidth(65);
        format.setIndenting(true);
        format.setIndent(2);
        
        try {
            XMLSerializer serializer = new XMLSerializer(new FileWriter("menu.xml"), format);
            serializer.serialize(doc);
            
            serializer = new XMLSerializer(System.out, format);
            serializer.serialize(doc);
        } catch (IOException ex) {
            //ex.printStackTrace();
        }
    }
    private String getMenuName(Document doc,Node node) {
        if (node == null)
            return "";
        int whatToShow  = NodeFilter.SHOW_ALL;
        NodeIterator iterator = ((DocumentTraversal)doc).createNodeIterator(node,
                whatToShow, new NodeFilter() {
            public short acceptNode(Node n) {
                if(MenuFileElements.Name.equals(n.getNodeName()))
                    return NodeFilter.FILTER_ACCEPT;
                return NodeFilter.FILTER_SKIP;
            }
        }, true);
        
        return iterator.nextNode().getTextContent();
        
    }
    public static void main(String [] args) throws Exception {
        new MenuLoader();
    }
}
