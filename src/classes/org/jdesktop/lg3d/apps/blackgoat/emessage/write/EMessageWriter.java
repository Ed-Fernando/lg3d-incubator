/*
 * Created on 2005/08/04
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.jdesktop.lg3d.apps.blackgoat.emessage.write;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @author dai
 */
public class EMessageWriter implements EMessageWritable { 

	private final static String ENCODING_TYPE = "iso-2022-jp";
	
	private String smtpServerIp;
	
	private String smtpServerName;
	
	private String senderAccount;
	
	private String senderName;
	
	/**
	 * sets smtp server ip address, smtp server name, user account name and user message account
	 * @param aSmtpServerIp
	 * @param aSmtpServerName
	 * @param aSenderAccount
	 * @param aSenderName
	 */
	public  EMessageWriter(String aSmtpServerIp, String aSmtpServerName, String aSenderAccount, String aSenderName) {
		this.smtpServerIp = aSmtpServerIp;
		this.smtpServerName = aSmtpServerName;
		this.senderAccount = aSenderAccount;
		this.senderName = aSenderName;
	}
	
	/**
	 * sets smtp server ip address, user account name and user message account.
	 * @param aSmtpServerIp
	 * @param aSmtpServerName
	 * @param aSenderAccount
	 */
	public  EMessageWriter(String aSmtpServerIp, String aSmtpServerName, String aSenderAccount) {
		
		this(aSmtpServerIp, aSmtpServerName, aSenderAccount, "");
		
	}
	
	/**
	 * sets smtp server ip address and user message account.
	 * @param aSmtpServerIp
	 * @param aSenderAccount
	 */
	public  EMessageWriter(String aSmtpServerIp, String aSenderAccount) {
		
		this(aSmtpServerIp, "", aSenderAccount, "");
		
	}
	/*
	public void setMessage(String testMessage) {
		System.out.println("Test Message is " + testMessage);			
	}
	*/


	/**
	 * sends a message and returns boolean wheater sending a message successfully or not
	 * @param to
	 * @param cc
	 * @param bcc
	 * @param subject
	 * @param content
	 * @return true/false
	 */
	public boolean send(String to, String cc, String bcc, String subject, String content) {
		try {
			/*
			System.out.println("Start sending Mail");
			System.out.println("\t" + smtpServerIp);
			System.out.println("\t" + smtpServerName);
			System.out.println("\t" + senderAccount);
			System.out.println("\t" + senderName);
		*/
			
			Properties props = System.getProperties();
			props.put(smtpServerIp, "smtpServerName");
				
			Session session = Session.getDefaultInstance(props, null);

			MimeMessage mime = new MimeMessage(session);
			
			mime.setFrom(new InternetAddress(senderAccount, senderName, ENCODING_TYPE));
			//mime.setFrom(new InternetAddress("d_odahara@complex.eng.hokudai.ac.jp", "Dai Odahara", ENCODING_TYPE));
			//mime.setRecipients(Message.RecipientType.TO, "d_odahara@complex.eng.hokudai.ac.jp");
			mime.setRecipients(Message.RecipientType.TO, to);
			mime.setRecipients(Message.RecipientType.CC, cc);
			mime.setRecipients(Message.RecipientType.BCC, bcc);
			/** Setting Mail body */
			mime.setSubject(subject, ENCODING_TYPE);
			mime.setText(content, ENCODING_TYPE);
			
			mime.setHeader("Content-Type", "text/html");
			mime.setSentDate(new Date());		
			mime.setDescription("BlackGoat");
			
			//System.out.println("\t subjcect\n \t\t" + subject);
			//System.out.println("\t content\n \t\t" + content);
		
			
			Transport.send(mime);
			System.out.println("Sent Mail Successfully");

		} catch (MessagingException e ) {
			e.printStackTrace();
			return false;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
