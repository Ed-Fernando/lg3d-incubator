package org.jdesktop.lg3d.apps.blackgoat.emessage.read;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;
import javax.mail.*;
import javax.mail.internet.*;

//import org.jdesktop.lg3d.apps.blackgoat.draw.letter.LetterContentImpl;
import org.jdesktop.lg3d.apps.blackgoat.draw.letter.Letter;
import org.jdesktop.lg3d.apps.blackgoat.emessage.read.EMessageReader;
import org.jdesktop.lg3d.apps.blackgoat.utils.UserInfo;

/**
 * @author Dai Odahara
 */
/* This class is for verifying pop */

public class PopTest {
	public static void main(String[] args) {

	
		/* Set Variables */
		UserInfo.getInstance().setPopServerIp("");
		UserInfo.getInstance().setPopUserName("");
		UserInfo.getInstance().setPopPassword("");

		System.out.println("Pop Start");

		EMessageReadable em = new EMessageReader();
		Message[] messages = null;

		em.open();
		messages = em.getMessage();
		System.out.println("After Getting Messages??");

		int totalMessages = em.getNumMessages();
		int deli = 59;
		System.out.println("total is : " + totalMessages);

		for (int i = totalMessages - 1; i < totalMessages; i++) {
			// for (int i = 0; i < 3; i++) {
			try {

				System.out.println("--Subject--");

				System.out.println(em.getMessage()[i].getSubject());

				StringTokenizer st = new StringTokenizer((String) em
						.getMessage()[i].getContent(), "\n\r");

				String[] lines = new String[st.countTokens()];
				StringBuffer[] bufs = new StringBuffer[st.countTokens()];
				int j = 0;
				System.out.println("--Content--");
//StringBuffer str2;str2.
				while (st.hasMoreTokens()) {
					//lines[j] = st.nextToken();
					bufs[j] = new StringBuffer(st.nextToken());
					//bufs[j].append(st.nextToken());
					
					//while(lines[j].length() > 49){
					while(bufs[j].length() > deli){
						String str = bufs[j].substring(0, deli);
						//String str = lines[j].substring(0, 49);
					//	lines[j].
					//System.out.println(lines[j]);
						System.out.println(str);
						bufs[j].delete(0, deli);
					} 
					
						//System.out.println(lines[j]);
					bufs[j].toString().replaceFirst(" ", "");
					//bufs[j].toString().replaceFirst("\n\r", "");
					if( 1 != bufs[j].length()){
						System.out.println("" +bufs[j].toString().replaceFirst(" ", ""));
					}
					j++;
				}
			} catch (MessagingException e) {

			} catch (IOException e) {

			}

		}
		em.close();

	}

}