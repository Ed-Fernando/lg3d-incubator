package org.jdesktop.lg3d.apps.blackgoat.utils;

/**
 * @author dai
 */
/**
 * this singleton class is in charge of user mailer setting.
 */
public class UserInfo {
	private String popServerIp;

	private String popUserName;

	private char[] popPassword;
	
	private String smtpServer;
	
	private String smtpUserName;

	static UserInfo uinfo = new UserInfo();;

	/**
	 * private constructor because of keeping from being created from outside.
	 *
	 */
	private UserInfo() {
	}

	/**
	 * returns this singleton class.
	 * @return uinfo
	 */
	public static UserInfo getInstance() {
		
		return uinfo;
	}

	/**
	 * sets pop server name.
	 * @param popServer
	 */
	public void setPopServerIp(String popServer) {
		this.popServerIp = popServer;
	}

	/**
	 * sets pop user name.
	 * @param popUserName
	 */
	public void setPopUserName(String popUserName) {
		this.popUserName = popUserName;
	}

	/**
	 * sets pop user password
	 * @param popPassword
	 */
	public void setPopPassword(String popPassword) {
		this.popPassword = popPassword.toCharArray();
	}

	/**
	 * sets pop user password
	 * @param popPassword
	 */	
	public void setPopPassword(char[] aPass) {
		this.popPassword = aPass;
	}

	/**
	 * returns pop server name.
	 * @return popServer.
	 */
	public String getPopServerIp() {
		return this.popServerIp;
	}

	/**
	 * returns pop user name.
	 * @return pop user name.
	 */
	public String getPopUserName() {
		return this.popUserName;
	}

	/*
	 * public String getPass() { return this.pass; }
	 */
	/**
	 * returns pop user password.
	 * @return popPassword
	 */
	public char[] getPopPassword() {
		return this.popPassword;
	}

	/**
	 * returns pop smtp server name.
	 * @return smtpServer
	 */
	public String getSmtpServer() {
		return smtpServer;
	}

	/**
	 * sets smtp server name.
	 * @param smtpServer
	 */
	public void setSmtpServer(String smtpServer) {
		this.smtpServer = smtpServer;
	}

	/**
	 * returns smtp user account name.
	 * @return smtpUserName
	 */
	public String getSmtpUserName() {
		return smtpUserName;
	}

	/**
	 * sets smtp user account name.
	 * @param smtpUserName
	 */
	public void setSmtpUserName(String smtpUserName) {
		this.smtpUserName = smtpUserName;
	}
}
