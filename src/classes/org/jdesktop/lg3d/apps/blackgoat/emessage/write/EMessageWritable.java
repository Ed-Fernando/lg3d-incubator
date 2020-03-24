/*
 * Created on 2005/08/04
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.jdesktop.lg3d.apps.blackgoat.emessage.write;

/**
 * @author dai
 */
/**
 * All interface methosds are regarding message writing.
 */
public interface EMessageWritable {
	
	/**
	 * sends a message.
	 * @param to
	 * @param cc
	 * @param bcc
	 * @param subject
	 * @param content
	 * @return
	 */
	public boolean send(String to, String cc, String bcc, String subject, String content);
}
