package nu.koidelab.cosmo.util.bind2d.swing;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import nu.koidelab.cosmo.wg.shape.WindowDecoration;

import org.jdesktop.lg3d.wg.Frame3D;
import org.jdesktop.lg3d.wg.SwingNode;

public class Dialog3D {
	public static final int YES_NO_OPTION = 1;
	public static final int YES_NO_CANCEL_OPTION = 2;
	public static final int OK_OPTION = 3;
	public static final int YES_OPTION = 4;
	public static final int NO_OPTION = 5;
	public static final int CANCEL_OPTION = 6;

	/*--- Methods can be called from user. ---*/ 
	public static void showConfirmDialog(String[] message, String title, int type, ButtonActionListener listener){
		makeConfirmPanel(message, title, type, listener); 
	}
	
	/*--- Methods can be called from user. ---*/ 
	public static void showInputDialog(String[] message, String title, InputActionListener listener){
		makeInputPanel(message, title, listener); 
	}
	
	
	/*=================================== Private Methods =========================================*/ 
	private static void makeInputPanel(String[] message, String title, InputActionListener listener){
		final Frame3D f = new Frame3D();
		InputDialogBase panel = new InputDialogBase(f, listener);
		panel.setMessage(message);
		SwingNode s = new SwingNode();				
		s.setPanel(panel);		
		
        float width = s.getLocalWidth();
        float height = s.getLocalHeight();
		WindowDecoration deco = new WindowDecoration(width, height, title, false){			
			public void closeAction() {f.changeEnabled(false);};
			public void minimizeAction() {f.changeVisible(false);};
		};
		setAndLaunch(f, s, deco);
	}
	
	private static void makeConfirmPanel(String[] message, String title, int type, ButtonActionListener listener){
		final Frame3D f = new Frame3D();
		final ButtonActionListener buttonActionListener = listener;
		
		MessageDialogBase panel = new MessageDialogBase(f);
		panel.setMessage(message);
		panel.setButton(type, listener);

		SwingNode s = new SwingNode();				
		s.setPanel(panel);		
        float width = s.getLocalWidth();
        float height = s.getLocalHeight();        
		WindowDecoration deco;
		if(type == YES_NO_CANCEL_OPTION){
			/*=== If cancel button is pressed, it is informed to ButtonActionListener. ===*/  
			deco = new WindowDecoration(width, height, title, false){			
				public void closeAction() {
					f.changeEnabled(false);
					buttonActionListener.actionPerformed(CANCEL_OPTION);
				};
				public void minimizeAction() {f.changeVisible(false);};
			};
		} else {
			deco = new WindowDecoration(width, height, title, false){			
				public void closeAction() {f.changeEnabled(false);};
				public void minimizeAction() {f.changeVisible(false);};
			};
		}
		setAndLaunch(f, s, deco);
	}

	private static void setAndLaunch(Frame3D frame, SwingNode sn, WindowDecoration deco){
		frame.addChild(sn);
		frame.addChild(deco);
		frame.changeEnabled(true);
		frame.changeVisible(true);
	}
	
	
	public static void main(String[] args) {
		String[] str = {"is this sample?"};
		/*
		showConfirmDialog(str, null, MessageDialogBase.YES_NO_CANCEL_OPTION, new ButtonActionListener(){
			public void actionPerformed(String actionCommand) {
				System.out.println(actionCommand);
			}
		});
		*/
		showInputDialog(str, null, new InputActionListener(){
			public void actionPerformed(String text) {
				System.out.println(text);
			}
		});
	}
	
	private static class InputDialogBase extends DialogBase{
		private JTextField textField;
        protected InputActionListener listener;
		
		private InputDialogBase(Frame3D frame, InputActionListener listener){
			super(frame);
			this.listener = listener;
			setLayout(new GridLayout(3, 1));
			labelPanel.setLayout(new GridLayout(1,1));			
			add(labelPanel);
			
			textField = new JTextField(20);
			add(textField);
			
			buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
			JButton ok = new JButton("OK");
			ok.addActionListener(this);
			buttonPanel.add(ok);
			add(buttonPanel);			
		}
		
		public void actionPerformed(ActionEvent e) {
			listener.actionPerformed(textField.getText());			
			frame.changeEnabled(false);
		}
	}

	private static class MessageDialogBase extends DialogBase{
        protected ButtonActionListener listener;
		private MessageDialogBase(Frame3D frame){
			super(frame);
			setLayout(new GridLayout(2, 1));
			labelPanel.setLayout(new GridLayout(1,1));			
			add(labelPanel);
			buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
			add(buttonPanel);
	        
		}		
		
		private void setButton(int type, ButtonActionListener listener){
			this.listener = listener; 
			if(type == OK_OPTION){
				JButton ok = new JButton("OK");
				ok.setActionCommand("ok");
				ok.addActionListener(this);
				buttonPanel.add(ok);				
			} else if ( (type == YES_NO_CANCEL_OPTION) 
					|| (type == YES_NO_OPTION)){
				JButton yes = new JButton("YES");
				yes.setActionCommand("yes");
				yes.addActionListener(this);
				buttonPanel.add(yes);
				JButton no = new JButton("NO");
				no.setActionCommand("no");
				no.addActionListener(this);
				buttonPanel.add(no);
				if (type == YES_NO_CANCEL_OPTION){
					JButton cancel = new JButton("CANCEL");
					cancel.setActionCommand("cancel");
					cancel.addActionListener(this);
					buttonPanel.add(cancel);
				}
			}
		}
		
        public void actionPerformed(ActionEvent e) {
        	String command = e.getActionCommand();
        	int action = 0;
        	if(command.equals("ok"))
        		action = OK_OPTION;
        	else if(command.equals("yes"))
        		action = YES_OPTION;
        	else if(command.equals("no"))
    			action = NO_OPTION;
        	else if(command.equals("cancel"))
    			action = CANCEL_OPTION;
            listener.actionPerformed(action);
            frame.changeEnabled(false);
        }
	
	}
    
    private abstract static class DialogBase extends JPanel implements ActionListener{
    	protected Frame3D frame;
        protected JPanel labelPanel = new JPanel();
        protected JPanel buttonPanel = new JPanel();
        
        protected DialogBase(Frame3D frame){
        	this.frame = frame;
            /*
            String lafName = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
            try{
                UIManager.setLookAndFeel(lafName);
                SwingUtilities.updateComponentTreeUI(this);
            }catch(Exception ex){
                System.out.println("Error L&F Setting");
            }  
            */          
        }
        
        protected void setMessage(String message){
            String[] str = {message};
            setMessage(str);
        }
        
        protected void setMessage(String[] messages){
            ((GridLayout)(labelPanel.getLayout())).setRows(messages.length);
            for(int i = messages.length - 1; i >= 0; i--)               
                labelPanel.add(new JLabel(messages[i]), 0);
        }        
    }
	
    public static interface ButtonActionListener{
		
		/**
		 * @param option : An option user selected is retruned as int. 
		 * This is static value such as YES_OPTION, NO_OPTION, CANCEL_OPTION, OK_OPTION, in Dialog3D
		 */
		public void actionPerformed(int option);
	}
    
    public static interface InputActionListener{
		/**
		 * @param Text : A string is inputed from user.
		 */
		public void actionPerformed(String text);
    }
}
