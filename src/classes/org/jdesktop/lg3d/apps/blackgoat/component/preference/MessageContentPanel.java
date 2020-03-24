/*
 * Created on 2005/08/02
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.jdesktop.lg3d.apps.blackgoat.component.preference;

import java.util.StringTokenizer;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

// import org.jdesktop.lg3d.apps.tutorial.TestPanel;

/**
 * @author dai
 */
/**
 * this class is used for sending mail as reply or forward.
 */
public class MessageContentPanel extends javax.swing.JPanel {
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	private javax.swing.JTextField toTextField;

	private javax.swing.JTextField ccTextField;

	private javax.swing.JTextField bccTextField;

	private javax.swing.JTextField subjectTextField;

	private javax.swing.JTextArea contentArea;

	/**
	 * constructs
	 *
	 */
	public MessageContentPanel() {
		initComponents();
	}

	/**
	 * sets swing panels, to panel, cc panel, bcc panel and message contgent panel.
	 *
	 */
	private void initComponents() {

		java.awt.GridBagConstraints gridBagConstraints;
		setSize(200, 200);

		setLayout(new java.awt.GridBagLayout());
		// setLayout(new java.awt.FlowLayout());
		// setLayout(new java.awt.BorderLayout());

		/** To Subject */
		toTextField = new JTextField("", 1);

		toTextField.setColumns(32);
		toTextField.setBorder(new TitledBorder("To"));
		// toTextField.setText("or-all@complex.eng.hokudai.ac.jp");
		toTextField.setText("");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;

		add(toTextField, gridBagConstraints);
		
		/** Cc Subject */
		ccTextField = new JTextField("", 1);

		ccTextField.setColumns(32);
		ccTextField.setBorder(new TitledBorder("Cc"));
		// toTextField.setText("or-all@complex.eng.hokudai.ac.jp");
		ccTextField.setText("");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;

		add(ccTextField, gridBagConstraints);
		
		/** Bcc Subject */
		bccTextField = new JTextField("", 1);

		bccTextField.setColumns(32);
		bccTextField.setBorder(new TitledBorder("Bcc"));
		// toTextField.setText("or-all@complex.eng.hokudai.ac.jp");
		bccTextField.setText("");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;

		add(bccTextField, gridBagConstraints);
		
		
		/** Message Subject */
		subjectTextField = new JTextField("", 1);

		subjectTextField.setColumns(32);
		subjectTextField.setBorder(new TitledBorder("Subject"));
		// toTextField.setText("or-all@complex.eng.hokudai.ac.jp");
		subjectTextField.setText("Sample Mail From BlackGoat");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;

		add(subjectTextField, gridBagConstraints);

		/** Message Content */
		contentArea = new JTextArea("", 14, 46);
	
		contentArea.setText("");

		contentArea.setBorder(new TitledBorder("Message Content"));
		contentArea.setSize(100, 100);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;

		JScrollPane scrollPane = new JScrollPane(contentArea, 
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		add(scrollPane, gridBagConstraints);
	}

	//private int radius = 5;


	/**
	 * returns to
	 * @return to
	 */
	public String getTo() {
		return toTextField.getText();
	}

	/**
	 * set to
	 * @param content
	 */
	public void setTo(String content) {
		this.toTextField.setText(content);
	}
	
	/**
	 * return cc
	 * @return cc
	 */
	public String getCc() {
		return ccTextField.getText();
	}

	/**
	 * sets cc
	 * @param cc
	 */
	public void setCc(String cc) {
		this.ccTextField.setText(cc);
	}
	
	/**
	 * returns bcc
	 * @return bcc
	 */
	public String getBcc() {
		return bccTextField.getText();
	}

	/**
	 * sets bcc
	 * @param cc
	 */
	public void setBcc(String cc) {
		this.bccTextField.setText(cc);
	}
	
	/**
	 * returns subject
	 * @return subject
	 */
	public String getSubject() {
		return subjectTextField.getText();
	}

	/**
	 * sets subject
	 * @param subject
	 */
	public void setSubject(String subject) {
		this.subjectTextField.setText(subject);
	}
	

	/**
	 * returns message content
	 * @return content
	 */
	public String getContent() {
		return contentArea.getText();
	}

	/**
	 * sets message content
	 * @param content
	 */
	public void setContent(String content) {
		this.contentArea.setText(content);
	}

	/**
	 * sets message content on message textfield as reply.
	 * messages appear as "> *".
	 * @param content
	 */
	public void setContentAsReply(String content) {

		StringTokenizer st = new StringTokenizer(content, "\n");

		// String[] lines = new String[st.countTokens()];
		// System.out.println("--Content--");
		StringBuffer sb = new StringBuffer();

		while (st.hasMoreTokens()) {
			// lines[j] = st.nextToken();
			sb.append("> ");
			sb.append(st.nextToken());
			sb.append("\n");
		}

		this.contentArea.setText(sb.toString());
	}

}
