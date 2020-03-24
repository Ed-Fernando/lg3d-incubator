package org.jdesktop.lg3d.apps.blackgoat.emessage.read;

import javax.mail.*;
import javax.mail.Session;

import org.jdesktop.lg3d.apps.blackgoat.utils.UserInfo;

/**
 * @author Dai Odahara
 */
public abstract class AbstractEMessageReader implements EMessageReadable {
	protected Session session;
	
	protected Folder popFolder;
	
	protected Store store;

//	private String popServerIp;

//	private String popUserName;

//	private String popUserPassword;

	// private char[] aPass;
/*
	public AbstractEMessageReader() {
	}
*/
	/**
	 * sets user information, pop server name, pop user account, pop user password
	 * when getting mail from pop server. 
	 */
	/*
	public void setUserInfo() {

		UserInfo uinfo = UserInfo.getInstance();

		popServerIp = uinfo.getPopServerIp();
		popUserName = uinfo.getPopUserName();
		popUserPassword = new String(uinfo.getPopPassword());

	}
	*/
	/**
	 * returns a array of messages. Actually call read function.
	 * @param Message[]
	 */
	public Message[] getMessage(){
		//System.out.println("Here in AbstractEMessages");			
			return read();
	}
	
	/**
	 * returns a array of messages.
	 * @return a array fo messages
	 */
	public abstract Message[] read();
	
}
