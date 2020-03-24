/*
 * QueryPanel.java
 *
 * Created on June 20, 2005, 11:37 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package org.jdesktop.lg3d.apps.orgchart.ui.prefuse;

import java.io.Serializable;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import org.jdesktop.lg3d.apps.orgchart.framework.ServiceContext;
import org.jdesktop.lg3d.apps.orgchart.framework.contact.Contact;
import org.jdesktop.lg3d.apps.orgchart.framework.contact.ContactService;
import org.jdesktop.lg3d.apps.orgchart.ui.common.FramedPanel;
import org.jdesktop.lg3d.apps.orgchart.ui.common.UIUtil;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.utils.c3danimation.NaturalMotionAnimation;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.SwingNode;

/**
 *
 * @author cc144453
 */
public class QueryPanel extends Component3D implements ActionListener {
    
    static final float PANEL_WIDTH = 0.05f;
    static final float PANEL_HEIGHT = 0.03f;
    
    private Prefuse3D orgChart;
    private ServiceContext context;
    private Logger logger;
    private String searchText;
    private QueryJPanel queryPanel;
    
    /**
     * Creates a new instance of QueryPanel 
     */
    public QueryPanel(Prefuse3D orgChart) {
        this.orgChart = orgChart;
        this.context = orgChart.getContext();
        this.logger = orgChart.getLogger();
        createUI();
    }
    
    private void createUI() {
        setMouseEventPropagatable(true);
        setAnimation(new NaturalMotionAnimation(Prefuse3D.ANIMATION_DURATION));
        
        queryPanel = new QueryJPanel(this, searchText);
        SwingNode swingNode = new SwingNode();
        swingNode.setPanel(queryPanel);
        swingNode.setTranslation(0.0f, 0.0f, 0.005f);
        addChild(swingNode);
        
        SimpleAppearance app = new SimpleAppearance(0.6f, 1.0f, 0.6f, 1.0f,
                SimpleAppearance.NO_GLOSS | SimpleAppearance.DISABLE_CULLING);
        FramedPanel frame =
                new FramedPanel(PANEL_WIDTH, PANEL_HEIGHT, app, app);
        frame.setTranslation(0.0f, 0.0f, -0.001f);
        addChild(frame);
        
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e) {
        setVisible(false);
        
        // search button pressed
        searchText = queryPanel.getQuery();
        
        // clear the existing org tree if search contains results
        try {
            orgChart.createTree(searchText);
        } catch (Exception ex) {
            UIUtil.errorDialog(orgChart.getContext(), "Error searching, query=" + searchText, ex);
        }
    }
    
    private class QueryJPanel extends JPanel {
        private JTextField searchField;
        
        private QueryJPanel(QueryPanel queryUI, String searchText) {
            super();
            
            String prompt = context.i18n("queryPrompt");
            String buttonName = context.i18n("queryButton");
            
            // layout the panel
            GridBagLayout layout = new GridBagLayout();
            GridBagConstraints c = new GridBagConstraints();
            setLayout(layout);
            
            c.insets = new Insets(3, 3, 3, 3);
            
            c.gridwidth = GridBagConstraints.REMAINDER;
            c.gridheight = 1;
            c.weightx = 1.0;
            add(new JLabel(prompt), c);
            
            c.gridwidth = GridBagConstraints.REMAINDER;
            searchField = new JTextField(12);
            if (searchText != null) {
                searchField.setText(searchText);
            }
            add(searchField, c);
            
            JButton searchButton = new JButton(buttonName);
            searchButton.setDefaultCapable(true);
            add(searchButton, c);
            searchButton.addActionListener(queryUI);
        }
        
        private String getQuery() {
            return searchField.getText();
        }
        
    }
}
