/*
 * OrgChart2D.java
 *
 * Created on August 1, 2005, 1:29 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package org.jdesktop.lg3d.apps.orgchart.ui.prefuse;

import edu.berkeley.guir.prefuse.EdgeItem;
import edu.berkeley.guir.prefuse.ItemRegistry;
import edu.berkeley.guir.prefuse.NodeItem;
import edu.berkeley.guir.prefuse.VisualItem;
import edu.berkeley.guir.prefuse.action.AbstractAction;
import edu.berkeley.guir.prefuse.action.assignment.TreeLayout;
import edu.berkeley.guir.prefuse.action.filter.TreeFilter;
import edu.berkeley.guir.prefuse.activity.ActionList;
import edu.berkeley.guir.prefuse.graph.DefaultEdge;
import edu.berkeley.guir.prefuse.graph.DefaultTree;
import edu.berkeley.guir.prefuse.graph.DefaultTreeNode;
import edu.berkeley.guir.prefuse.graph.Edge;
import edu.berkeley.guir.prefuse.graph.Entity;
import edu.berkeley.guir.prefuse.graph.Tree;
import edu.berkeley.guir.prefuse.graph.TreeNode;
import edu.berkeley.guir.prefuse.graph.io.TreeMLTreeReader;
import edu.berkeley.guir.prefuse.graph.io.XMLGraphWriter;
import edu.berkeley.guir.prefuse.render.DefaultRendererFactory;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Iterator;
import java.util.logging.Logger;
import org.jdesktop.lg3d.apps.orgchart.framework.ServiceContext;
import org.jdesktop.lg3d.apps.orgchart.framework.ServiceContextFactory;
import org.jdesktop.lg3d.apps.orgchart.framework.Util;
import org.jdesktop.lg3d.apps.orgchart.framework.contact.ContactChannel;
import org.jdesktop.lg3d.apps.orgchart.framework.contact.ContactService;
import org.jdesktop.lg3d.apps.orgchart.ui.common.AbstractOrgChartApp;
import org.jdesktop.lg3d.apps.orgchart.ui.common.UIUtil;
import org.jdesktop.lg3d.utils.cursor.WigglingCursor;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Cursor3D;
import org.jdesktop.lg3d.wg.Tapp;


/**
 * Creates a new graph and draws it on the screen.
 */
public class Prefuse3D extends AbstractOrgChartApp {

    private static final String PATH_ORGCHARTDIR = ".orgchart2d";
    private static final String PATH_CHARTFILE = "chart.xml";
    private static final int WIDTH_CONTACT = 128;
    private static final int HEIGHT_CONTACT = 320;

    private static final Font FONT_MANAGER = new Font("serif", Font.BOLD, 12);
    private static final Font FONT_NONMANAGER = new Font("serif", Font.PLAIN, 12);
    private static final Font FONT_ROOTMANAGER = new Font("serif", Font.BOLD, 14);
    private static final Font FONT_ROOTNONMANAGER = new Font("serif", Font.PLAIN, 14);
    static final String ATTR_MANAGER = "__mgr";
    static final String ATTR_QUERY = "__query";
    static final String ATTR_ROOT = "__root";

    static final float LEVEL_GAP = 0.14f;
    static final float LEVEL_DEPTH = 0.04f;
    static final float TREE_ANGLE = (float)(Math.PI / 6);
    static final float BUTTON_SIZE = 0.01f;
    static final float BUTTON_X = -0.04f;

    private QueryPanel queryUI;
    private LGDisplay display;
    private Component3D chartComp;
    private TreeMLTreeReader chartReader = new TreeMLTreeReader();
    private XMLGraphWriter chartWriter = new XMLGraphWriter();
    private File chartFile;
    private ItemRegistry registry;
    private ActionList drawActions;
    private ActionList noFilterDrawActions;
    private Tree tree;
    private TreeFilter treeFilter;

    public static final void main(String[] args) {
        new Prefuse3D();
    }

    public void createUI() throws Exception {
        // set dimensions
        /*
        Dimension size = getToolkit().getScreenSize();
        int width = size.width * 5 / 6;
        int height = size.height * 5 / 6;
        setSize(width, height);
         */

        // create query panel
        queryUI = new QueryPanel(this);
        addChild(queryUI);

        // create chart
        chartComp = new Component3D();
        addChild(chartComp);

        // check for directory, if not create it
        File dir = new File(
                System.getProperty("user.home") + File.separator + PATH_ORGCHARTDIR);
        if (dir.exists() && !dir.isDirectory()) {
            throw new RuntimeException(dir.getPath() + " is not a directory");
        }
        if (!dir.exists()) {
            dir.mkdir();
        }
        chartFile = new File(dir, PATH_CHARTFILE);

        // create a new item registry
        // read chart if saved files exists
        /*
        if (chartFile.exists()) {
            try {
                //tree = (Tree)chartReader.loadGraph(new FileInputStream(chartFile));
                tree = chartReader.loadTree(chartFile);
            } catch (Exception e) {
                logger.log(Level.WARNING, "Error reading tree file=" + chartFile.getPath(), e);
                chartFile.delete(); // remove it
                tree = null;
            }
        }
         */
        boolean noData = (tree == null);
        if (noData) {
            // initialized tree if no chart file or error reading it
            tree = new DefaultTree();
        }
        Prefuse3DRenderer renderer = new Prefuse3DRenderer(this, chartComp);
        registry = new ItemRegistry(tree);
        registry.setRendererFactory(
                new DefaultRendererFactory(renderer, renderer));

        // create a new action list that
        // (a) filters visual representations from the original graph
        // (b) performs a random layout of graph nodes
        // (c) calls repaint on displays so that we can see the result
        initDrawChartActions(null);
        noFilterDrawActions = new ActionList(registry);
        noFilterDrawActions.add(new OrgChartRepaintAction());
        // create a new display component to show the data
        display = new LGDisplay(registry);
        // lets users drag nodes around on screen
        /* display is not wired in LG environment
        display.addControlListener(new ContactControl());
        display.addControlListener(new FocusControl());
        display.addControlListener(new PanControl());
        display.addControlListener(new ZoomControl());
        display.addControlListener(new NeighborHighlightControl(noFilterDrawActions));
                getContentPane().add(display);
         */

        // display query panel if needed
        if (noData) {
            queryUI.setVisible(true);
        } else {
            redraw();
        }

        // set up this JFrame
        setVisible(true);
        changeEnabled(true);
    }


    private void initDrawChartActions(String layoutClassName) {
        // create layout
        TreeLayout treeLayout = new Prefuse3DRadialLayout(this);

        // create a new action list that
        // (a) filters visual representations from the original graph
        // (b) performs a random layout of graph nodes
        // (c) calls repaint on displays so that we can see the result
        treeFilter = new OrgTreeFilter();
        drawActions = new ActionList(registry);
        drawActions.add(treeFilter);
        drawActions.add(treeLayout);
        drawActions.add(new OrgChartRepaintAction());
    }

    void createTree(String searchTerm) throws Exception {
        registry.clear(); // remove all registry elements

        Iterator<Map> contacts = search(searchTerm);
        List<Map> rootList = new ArrayList<Map>(8);
        while (contacts.hasNext()) {
            ContactChannel contact =
                    new ContactChannel(getContext(), contacts.next());
            rootList.add(contact);
        }
        if (rootList.size() == 1) {
            tree.setRoot(createTreeNode(rootList.get(0)));
        } else { // if (rootList.size() > 1 || rootList.size() == 0)
            // use the query as the root
            TreeNode queryNode = new DefaultTreeNode();
            queryNode.setAttribute(ATTR_QUERY, searchTerm);
            tree.setRoot(queryNode);
            for (Map root : rootList) {
                TreeNode rootNode = addTreeChild(tree.getRoot(), root);
                rootNode.setAttribute(ATTR_ROOT, "true");
            }
        }
        redraw();
    }

    private TreeNode createTreeNode(Map contact) {
        DefaultTreeNode treeNode = new DefaultTreeNode();
        treeNode.setAttributes(contact);
        if (getContactService().hasDirectReports(contact)) {
            treeNode.setAttribute(ATTR_MANAGER, Boolean.TRUE.toString());
        }
        return treeNode;
    }

    private TreeNode addTreeChild(TreeNode parentNode, Map child) {
        TreeNode childNode = createTreeNode(child);
        Edge childEdge = new DefaultEdge(parentNode, childNode, false);
        parentNode.addChild(childEdge);
        return childNode;
    }

    boolean expandTree(VisualItem item) {
        try {
            TreeNode parentNode = (TreeNode)item.getEntity();
            if (Prefuse3D.isManager(parentNode)) {
                // fetch children from LDAP server if not done yet
                // but if there are already children, skip fetching
                if (parentNode.getChildCount() == 0) {
                    Iterator<Map> reports =
                            getContactService().searchDirectReports(item.getAttributes());
                    while (reports.hasNext()) {
                        ContactChannel report =
                                new ContactChannel(getContext(), reports.next());
                        addTreeChild(parentNode, report);
                    }
                    return true;
                }
            }
        } catch (Exception e) {
            UIUtil.errorDialog(getContext(), "Error expanding tree", e);
        }
        return false;
    }

    void redraw() {
        drawActions.runNow();
    }

    static final boolean isManager(Entity item) {
        return true ||item.getAttribute(ATTR_MANAGER) != null;
    }

    static final boolean isRoot(Entity item) {
        return item.getAttribute(ATTR_ROOT) != null;
    }

    static final boolean isQuery(Entity item) {
        return item.getAttribute(ATTR_QUERY) != null;
    }

    private class OrgTreeFilter extends TreeFilter {
        private OrgTreeFilter() {
            super(false, true);
        }
        public void run(ItemRegistry registry, double frac) {
            super.run(registry, frac);
            // now make the query root invisible
            Iterator<NodeItem> nodeItems = registry.getNodeItems();
            while (nodeItems.hasNext()) {
                // do not draw the query node
                NodeItem nodeItem = nodeItems.next();
                nodeItem.setVisible(!isQuery(nodeItem));
            }
            Iterator<EdgeItem> edgeItems = registry.getEdgeItems();
            while (edgeItems.hasNext()) {
                // do not draw edges from the query
                EdgeItem edgeItem = edgeItems.next();
                edgeItem.setVisible(!isQuery(edgeItem.getFirstNode()));
            }
        }
    }

    private class OrgChartRepaintAction extends AbstractAction {
        public void run(ItemRegistry registry, double frac) {
            display.paintComponent(null);
            revalidate();
        }
    }

}
