package org.jdesktop.lg3d.apps.blackgoat.emessage.read;

import javax.mail.*;

/**
 * @author dai
 */
/**
 * All interface methosds are regarding message readering from mail server.
 */
public interface EMessageReadable {
	/**
	 * connects to mailer server.
	 *
	 */
	public void open();
	
	/**
	 * returns boolean whether already connecting to mail server 
	 * @return isOpened
	 */
	public boolean isOpened();

	/**
	 * retruns a array of messages.
	 * @return Message[]
	 */
	public Message[] getMessage();

	/**
	 * returns a number of messages which arrived at mail server.
	 * @return numMessages
	 */
	public int getNumMessages();

	/**
	 * disconnects to mail server.
	 *
	 */
	public void close();
}
