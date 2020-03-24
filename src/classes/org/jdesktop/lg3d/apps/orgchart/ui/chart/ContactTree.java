/*
 * ContactTree.java
 *
 * Created on June 30, 2005, 2:26 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package org.jdesktop.lg3d.apps.orgchart.ui.chart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jdesktop.lg3d.apps.orgchart.framework.contact.Contact;


/**
 *
 * @author cc144453
 */
public class ContactTree implements Serializable {
    
    public static final int CAPACITY = 32;
    public static final int ROOT_CAPACITY = 8;
    public static final int CHILDREN_CAPACITY = 8;
    
    private int childrenCapacity;
    private Set<Map> rootSet;
    private Set<Map> nonRootSet;
    private Map<Map, ContactNode> parents;
    
    public ContactTree() {
        this(CAPACITY, ROOT_CAPACITY, CHILDREN_CAPACITY);
    }
    
    public ContactTree(int capacity, int rootCapacity, int childrenCapacity) {
        this.childrenCapacity = childrenCapacity;
        rootSet = new HashSet<Map>(rootCapacity);
        nonRootSet = new HashSet<Map>(rootCapacity * 4);
        parents = new HashMap<Map, ContactNode>(capacity);
    }
    
    public void addRoot(Map contact) {
        synchronized (this) {
            rootSet.add(contact);
            parents.put(contact, new ContactNode());
        }
    }
    
    public Set<Map> getRoot() {
        return rootSet;
    }
    
    public boolean isRoot(Map contact) {
        return rootSet.contains(contact);
    }
    
    public boolean contains(Map contact) {
        return nonRootSet.contains(contact);
    }
    
    public Map getParent(Map child) {
        // loop through all the nodes to find parent
        for (Map contact : parents.keySet()) {
            ContactNode node = parents.get(contact);
            if (node.children != null &&
                    node.children.contains(child)) {
                return contact;
            }
        }
        return null;
    }
    
    public List<Map> getChildren(Map parent) {
        ContactNode node = parents.get(parent);
        return (node == null) ? null : node.children;
    }
    
    public boolean addChild(Map parent, Map child) {
        synchronized (this) {
            ContactNode node = parents.get(parent);
            if (node == null) {
                node = new ContactNode();
                parents.put(parent, node);
            }
            return node.addChild(child);
        }
    }
    
    public boolean addChildren(Map parent, Iterator<Map> children) {
        synchronized (this) {
            ContactNode node = parents.get(parent);
            if (node == null) {
                node = new ContactNode();
                parents.put(parent, node);
            }
            boolean somethingAdded = false;
            while (children.hasNext()) {
                Map child = children.next();
                somethingAdded = node.addChild(child) || somethingAdded;
            }
            return somethingAdded;
        }
    }
    
    public void merge(ContactTree source) {
        // merge each member accordingly
        synchronized (this) {
            // keep merging recursively until nothing to add
            boolean somethingAdded = false;
            while (somethingAdded) {
                somethingAdded = false;
                for (Map sourceContact : source.parents.keySet()) {
                    if (contains(sourceContact)) {
                        List<Map> sourceChildren =
                                source.getChildren(sourceContact);
                        for (Map sourceChild : sourceChildren) {
                            somethingAdded =
                                    addChild(sourceContact, sourceChild) || somethingAdded;
                        }
                    }
                }
            }
        }
        //System.out.println("#### merged " + toString());
        /*
        ContactNode node = parents.get(parent);
        if (node != null) {
            synchronized (this) {
                // recursively merge tree into current one
                List<Map> sourceChildren =
                        sourceTree.getChildren(sourceContact);
                if (sourceChildren != null) {
                for (Contact sourceChild : sourceChildren) {
                    addChild(parent, sourceChild);
                    addChildren(sourceChild, sourceTree, sourceChild);
                }
            }
        }
         */
    }
    
    public void remove(Map contact) {
        synchronized (this) {
            // recursively remove children
            removeChildren(contact);
            
            // loop through all the nodes to find parent and remove parent reference
            for (Map parent : parents.keySet()) {
                ContactNode node = parents.get(parent);
                List<Map> children = node.children;
                if (children != null && children.contains(contact)) {
                    children.remove(contact);
                }
            }
        }
    }
    
    private void removeChildren(Map contact) {
        ContactNode node = parents.get(contact);
        if (node != null) {
            if (node.children != null) {
                for (Map child : node.children) {
                    removeChildren(child);
                }
            }
            parents.remove(contact);
        }
    }
    
    public void removeAll() {
        synchronized (this) {
            rootSet.clear();
            parents.clear();
        }
    }
    
    public List<Map> getPathFromRoot(Map contact) {
        if (contains(contact)) {
            List<Map> pathList = new ArrayList<Map>(4);
            do {
                pathList.add(contact);
            } while ((contact = getParent(contact)) != null);
            return pathList;
        } else {
            return null;
        }
    }
    
    private final static String toName(Map contact) {
        return contact.get(Contact.ATTR_FIRSTNAME) + " " + contact.get(Contact.ATTR_LASTNAME);
    }
    
    public String toString() {
        StringBuffer buf = new StringBuffer(512);
        
        buf.append("Root:\n");
        for (Map root : rootSet) {
            buf.append("  ").append(toName(root)).append('\n');
        }
        buf.append("\nNodes:\n");
        for (Map contact : parents.keySet()) {
            buf.append("  ").append(toName(contact)).append('\n');
            List<Map> children = getChildren(contact);
            if (children != null) {
                for (Map child : children) {
                    buf.append("    ").append(toName(child)).append('\n');
                }
            }
        }
        return buf.toString();
    }
    
    private class ContactNode {
        private List<Map> children;
        private ContactNode() {
            this.children = null;
        }
        private boolean addChild(Map child) {
            if (children == null) {
                children = new ArrayList<Map>(childrenCapacity);
            }
            if (children.contains(child)) {
                return false;
            } else {
                children.add(child);
                nonRootSet.add(child);
                return true;
            }
        }
    }
    
}
