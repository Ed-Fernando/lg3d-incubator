package org.jdesktop.lg3d.apps.presentoire;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jdesktop.lg3d.apps.presentoire.transition.ScriptTransition;
import org.jdesktop.lg3d.apps.presentoire.transition.Transition;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * This is the Document class.
 * It parses opens the ZIP file, parses the XML file and creates Slides/Transitions as needed. 
 * @author Pierre Ducroquet
 *
 */
public class PresentoireDocument {
	
	private ZipFile ZIP;
	private Vector<Slide> slides;
	private HashMap<String, ScriptTransition> scriptTransitions;
	
	/**
	 * The constructor.
	 * @param doc : the Zip file to open as a presentoire document.
	 * @throws ZipException : thrown when the file is not valid.
	 * @throws IOException : thrown on IO errors.
	 */
	public PresentoireDocument (File doc) throws ZipException, IOException {
		Document XMLDoc;
		ZIP = new ZipFile(doc);
		ZipEntry docEntry;
		slides = new Vector<Slide>();
		scriptTransitions = new HashMap<String, ScriptTransition>();
		System.out.println(ZIP.getName());
		docEntry = ZIP.getEntry("content.xml");
		try {
			XMLDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(ZIP.getInputStream(docEntry));
			Node root;
			root = XMLDoc.getFirstChild();
			while (root != null) {
				if (root.getNodeName().equals("document")) {
					System.out.println(root.getNodeName());
					break;
				}
				root = root.getNextSibling();
			}
			if (root == null) {
				System.out.println("Invalid document !");
				return;
			}
			Node child;
			child = root.getFirstChild();
			while (child != null) {
				if (child.getNodeName().equals("slide")) {
					System.out.println("Found a slide");
					slides.add(new Slide(child, this));
				} else if (child.getNodeName().equals("transition")) {
					ScriptTransition trans = new ScriptTransition(child);
					System.out.println("Found a transition");
					scriptTransitions.put(trans.getId(), trans);
				}
				child = child.getNextSibling();
			}
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Offer an easy access to any file from the ZIP file.
	 * Needed for instance for ImageElement or ScriptTransition.
	 * @param fileName : the file searched.
	 * @return an InputStream for this file
	 * @throws IOException if the file is not found.
	 */
	public InputStream getZIPInputStream (String fileName) throws IOException {
		return ZIP.getInputStream(ZIP.getEntry(fileName));
	}
	
	/**
	 * @return the number of slides in this document.
	 */
	public int getSlideCount () {
		return slides.size();
	}	
	
	/**
	 * @param slideNumber the slide ID.
	 * @return a Slide object for this slide ID.
	 */
	public Slide getSlide (int slideNumber) {
		return slides.get(slideNumber);
	}

	/**
	 * @param name The name of the scripted Transition
	 * @return The ScriptTransition Object.
	 */
	public Transition getScriptTransition (String name) {
		return scriptTransitions.get(name);
	}
}
