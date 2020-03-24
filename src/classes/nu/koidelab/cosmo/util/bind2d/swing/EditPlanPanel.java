package nu.koidelab.cosmo.util.bind2d.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import nu.koidelab.cosmo.manager.CosmoConfig;
import nu.koidelab.cosmo.util.schedule.Plan;
import nu.koidelab.cosmo.wg.nodes.EditableOrbiter;
import nu.koidelab.cosmo.wg.shape.WindowDecoration;

import org.jdesktop.lg3d.wg.Frame3D;
import org.jdesktop.lg3d.wg.SwingNode;

public class EditPlanPanel extends Frame3D {
	private EditableOrbiter eo;
    
    // use in SwingPanel 
    private JButton okB;
    private JTextField name;
    private JTextField category;
    private JTextArea detail;
    private JButton clB;

	public EditPlanPanel(EditableOrbiter o) {
		this.eo = o;
		
		JEditPlanPanel jPanel = new JEditPlanPanel();
		SwingNode sn = new SwingNode();
		sn.setPanel(jPanel);

		Plan plan = eo.getPlan();
		// make glassy decoration
        float width = sn.getLocalWidth();
        float height = sn.getLocalHeight();
		WindowDecoration deco = new WindowDecoration(width, height, plan.getName(), false) {
			public void closeAction() {
			    editCancel();
			}
			public void minimizeAction() {}
		};
		sn.setTranslation(0, 0, plan.getPriority() * CosmoConfig.PLANET_RADIUS
				+ 0.001f);
		deco.setTranslation(0, 0, plan.getPriority() * CosmoConfig.PLANET_RADIUS
				+ 0.001f);
		
        
		addChild(deco);
		addChild(sn);
		setVisible(true);
		setEnabled(true);
	}	 

    private void editOK(){
        // make clone Plan.
        Plan newPlan = new Plan(eo.getPlan());
        newPlan.setName(name.getText());
        newPlan.setCategory(category.getText());
        newPlan.setDetail(detail.getText());
        eo.editOK(newPlan);
        changeEnabled(false);
    }
    
    private void editCancel(){
        eo.editCancel();            
        changeEnabled(false);
    }
    
	public class JEditPlanPanel extends JPanel implements ActionListener, KeyListener{
	    public JEditPlanPanel(){
	        this(eo.getPlan());
	    }
	    
	    public JEditPlanPanel(Plan p){        
	        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	        setBorder(new EtchedBorder(EtchedBorder.RAISED));
	        addKeyListener(this);        
	        
	        name = new JTextField(p.getName(), 20);
	        name.setBorder(new TitledBorder("Schedule Name"));
	        add(name);
	        
	        JTextField time = new JTextField(Plan.msecValueToString(p.getTime()), 20);
	        time.setBorder(new TitledBorder("Time"));
	        time.setEditable(false);
	        add(time);
	        
	        category = new JTextField(p.getCategory(), 20);
	        category.setBorder(new TitledBorder("Category"));
	        add(category);
	      
	        detail = new JTextArea(p.getDetail(), 5, 20);
	        detail.setBorder(new TitledBorder("Detail"));
	        add(detail);
	        
	        JPanel buttons = new JPanel();
	        buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
	        okB = new JButton("OK");
	        okB.addActionListener(this);
	        buttons.add(okB);
	        
	        clB = new JButton("CANCEL");
	        clB.addActionListener(this);
	        buttons.add(clB);
	        add(buttons);
	        
	        name.addActionListener(this);
	        category.addActionListener(this);
	    }
	    	    
	    public void actionPerformed(ActionEvent e) {
	        if(e.getSource().equals(okB)){            
	        	editOK();
	        } else if(e.getSource().equals(clB)) {            
	        	editCancel();
	        }
	    }
	    
	    public void keyPressed(KeyEvent e) {}
	    public void keyReleased(KeyEvent e) {}
	    public void keyTyped(KeyEvent e) { 
	    	int code = e.getKeyCode();
	    	if(code == KeyEvent.VK_ENTER){
	    		editOK();    		
	    	} else if(code == KeyEvent.VK_ESCAPE){
	    		editCancel();
	    	}
	    }
	}
}
