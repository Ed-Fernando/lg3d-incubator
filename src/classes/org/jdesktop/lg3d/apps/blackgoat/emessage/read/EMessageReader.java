package org.jdesktop.lg3d.apps.blackgoat.emessage.read;

//import java.io.*;
import javax.mail.*;
import javax.mail.Session;
//import javax.mail.internet.*;
//import java.util.*;

//import org.jdesktop.lg3d.apps.blackgoat.emessage.AbstractEMessage;
import org.jdesktop.lg3d.apps.blackgoat.utils.UserInfo;

/**
 * @author dai
 */
/**
 * this class connects to mail server and gets messages.
 */
public class EMessageReader extends AbstractEMessageReader {
	
	private String popServerIp;

	private String popUserName;

	private String popUserPassword;
	
	private int numMessages;
	
	private boolean isOpened;

	public EMessageReader() {
		
	}

	/**
	 * connects to mail server.
	 */
	public void open() {
		try {

			UserInfo uinfo = UserInfo.getInstance();
		
			/**
			 * TODO set
			 */
			popServerIp = uinfo.getPopServerIp();
			popUserName = uinfo.getPopUserName();
			if( null == popServerIp || null == popUserName || null == uinfo.getPopPassword()){
				System.err.println("Please input <ServerIP>, <Id> and <Password>");
				return;
			}
			popUserPassword = new String(uinfo.getPopPassword());
			
			/*
			System.out.println(host);
			System.out.println(user);
			System.out.println(pass);
			*/
			/* Connect to the Mail Server */
			session = Session.getDefaultInstance(System.getProperties(), null);
			
			/* Try to connect to the Mail Server */
			store = session.getStore("pop3");
			store.connect(popServerIp, -1, popUserName, popUserPassword);
			
			/* Open Mail Folder */
			popFolder = store.getFolder("INBOX");
			popFolder.open(Folder.READ_ONLY);
			
			isOpened = true;
			
		}catch(NoSuchProviderException ex) {
			System.err.println("No Such a Provider...");
			return;
		}catch(MessagingException ex) {
			System.err.println("Can not connect Server...");
			System.err.println("Please check <ServerIP>, <Id> and <Password>");
			return;
		}
	}
	
	/**
	 * gets messages from pop mail srver and gets a number of messages.
	 * @param messages[]
	 */
	public Message[] read() {
		Message[] messages = null;

		try {
				numMessages = popFolder.getMessageCount();
				messages = popFolder.getMessages();
				
			} catch (MessagingException ex) {

				System.err.println("Can not open messages...");
				System.err.println("Please check <ServerIP>, <Id> and <Password>");
				//ex.printStackTrace();
				return null;
		} catch (Exception ex) {

			System.err.println("Can not open messages...");
			System.err.println("Please check <ServerIP>, <Id> and <Password>");
			//ex.printStackTrace();
			return null;
	}
		return messages;
	}
	
	/**
	 * returns total number of messages.
	 * @param numMessages
	 */
	public final int getNumMessages(){
		return numMessages;
	}
	
	/**
	 * returns boolean whether connecting to mail server or not.
	 * @param isOpened
	 */
	public final boolean isOpened(){
		return isOpened;
	}
	
	/**
	 * disconnects to mail server.
	 */
	public final void close(){
		try {
			popFolder.close(false);
			store.close();
		}catch(MessagingException ex) {
			ex.printStackTrace();
			return;
		}
	}
}
