package org.jdesktop.lg3d.apps.blackgoat.component.preference;

import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

/**
 * @author dai
 */
public class PreferencePanel extends javax.swing.JPanel {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	private JTextField popServerName;

	private JTextField popUserAccount;

	private JPasswordField popUserPassword;

	private JTextField smtpServerIp;

	private JTextField smtpUserAccount;

	public PreferencePanel() {
	
		java.awt.GridBagConstraints gridBagConstraints;
		setLayout(new java.awt.GridBagLayout());

		/** for smtp */
		JLabel label = new JLabel();
		// label.setColumns(16);
		// label.setBorder(new TitledBorder("Mailer Setting"));
		label.setText("BlackGoat Mailer Setting");

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		add(label, gridBagConstraints);

		smtpServerIp = new JTextField();
		smtpServerIp.setColumns(16);
		smtpServerIp.setBorder(new TitledBorder("SMTP Server Name"));
		smtpServerIp.setText("");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		add(smtpServerIp, gridBagConstraints);

		/*
		 * smtpServerName = new JTextField(); smtpServerName.setColumns(16);
		 * smtpServerName.setBorder(new TitledBorder("SMTP Server Name"));
		 * smtpServerName.setText(""); gridBagConstraints = new
		 * java.awt.GridBagConstraints(); gridBagConstraints.gridx = 0;
		 * gridBagConstraints.gridy = 1; add(smtpServerName,
		 * gridBagConstraints);
		 */
		smtpUserAccount = new JTextField();
		smtpUserAccount.setColumns(16);
		smtpUserAccount.setBorder(new TitledBorder("Sender Mail Address"));
		smtpUserAccount.setText("");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		add(smtpUserAccount, gridBagConstraints);

		popServerName = new javax.swing.JTextField();
		popUserAccount = new javax.swing.JTextField();
		popUserPassword = new javax.swing.JPasswordField();

		/** for pop */
		popServerName.setColumns(16);
		popServerName.setBorder(new TitledBorder("Pop Server Name"));
		popServerName.setText("");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		add(popServerName, gridBagConstraints);

		popUserAccount.setColumns(16);
		popUserAccount.setBorder(new TitledBorder("Pop User Account"));
		popUserAccount.setText("");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		add(popUserAccount, gridBagConstraints);

		popUserPassword.setColumns(16);
		popUserPassword.setBorder(new TitledBorder("Pop User Password"));
		popUserPassword.setText("");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;
		add(popUserPassword, gridBagConstraints);

	}

	/**
	 * returns pop server name.
	 * @return popServerName
	 */
	public String getPopServerName() {
		return popServerName.getText();
	}

	/**
	 * returns pop user name.
	 * @return popUserName
	 */
	public String getPopUserName() {
		return popUserAccount.getText();
	}

	/**
	 * returns pop user password.
	 * @return popUserPassword
	 */
	public char[] getPopUserPassword() {
		return popUserPassword.getPassword();
	}
	
	/**
	 * returns smtp server ip address.
	 * @return smtpIpAddress
	 */
	public String getSmtpServerIp() {
		return smtpServerIp.getText();
	}

	/**
	 * returns smtp user account.
	 * @return smtpUserAccount.
	 */
	public String getSmtpUserAccount() {
		return smtpUserAccount.getText();
	}

}
