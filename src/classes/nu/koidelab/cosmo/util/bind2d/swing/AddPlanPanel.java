package nu.koidelab.cosmo.util.bind2d.swing;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.vecmath.Vector3f;

import nu.koidelab.cosmo.manager.CSDGeneralManager;
import nu.koidelab.cosmo.util.gesture.EditNewPlanGestureModule;
import nu.koidelab.cosmo.wg.nodes.group.DayGroup;
import nu.koidelab.cosmo.wg.shape.WindowDecoration;

import org.jdesktop.lg3d.wg.Cursor3D;
import org.jdesktop.lg3d.wg.Frame3D;
import org.jdesktop.lg3d.wg.SwingNode;

public class AddPlanPanel extends Frame3D{
    private JTextField nameTF;
    private DayGroup.DateIDPanel idPanel;

	public AddPlanPanel() {
		init();
	}
	
	public AddPlanPanel(DayGroup.DateIDPanel panel) {
		idPanel = panel;
		init();		
	}
	
    private void init() {
        JPanel facePanel = new JPanel();
        facePanel.setLayout(new GridLayout(2, 1));
        facePanel.add(new JLabel("New Plan Name"));
        nameTF = new JTextField(20);
        nameTF.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e) {
        		createPlanetChild();
        	}
        });
        facePanel.add(nameTF);

        
        // make SwingPanel
        SwingNode faceNode = new SwingNode();
        faceNode.setCursor(Cursor3D.SMALL_CURSOR);
        faceNode.setPanel(facePanel);
        
        // make glassy decoration            
        float width = faceNode.getLocalWidth();
        float height = faceNode.getLocalHeight();
        WindowDecoration deco = new WindowDecoration(width, height, "New Schedule", false){
        	public void closeAction() {
        		changeVisible(false);
        		changeEnabled(false);
        	}
        	public void minimizeAction() {}
        };
        addChild(deco);
        addChild(faceNode);
        
        //Set preferred size of this frame
        Vector3f size = new Vector3f();
        deco.getPreferredSize(size);
        setPreferredSize(size);
        
        //Enable gesture module.  
        addListener(new EditNewPlanGestureModule(AddPlanPanel.class));
        
        setVisible(true);
        setEnabled(true);
    }
    
    public DayGroup.DateIDPanel getIdPanel() {
		return idPanel;
	}

    public void createPlanetChild() {
    	CSDGeneralManager.getInstance().getEditor().createPlanetChild(nameTF.getText(), this);
    }
}
