 /*
  * 3D File Manager - Project Looking Glass 
  * Copyright Sun Microsystems, 2005
  * 
  * Project Course in Human-Computer Interaction
  * Carnegie Mellon University
  * 
  * Aditya Chand, Braden Kowitz, Jake Pierson, Jessica Smith
  */

package edu.cmu.sun.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Abstracted model of the underlying filesystem.
 * 
 * We wanted to hide away Java's view of the filesystem becasue
 * we often had to make changes on our own (such as hiding files
 * that start with a dot).  This class hides any filesystem exception,
 * so it gives our protoype a little more robustness.
 * 
 * It also models the filesystem more as a clener tree structure than before.
 * Finally, it helps to pull out the displayable file names from the type
 * information (which could be the file extension, or some other metatdata)
 * 
 * @author Braden Kowitz, Jake Pierson
 *
 */
public class FileNodeModel {

	// TODO: decide why we need this enum here?
	public enum Filetype {FOLDER, OTHER, IMAGE, TEXT, AUDIO};

	File file;
	
	FileNodeModel parent;
	
	public FileNodeModel(File file)
	{
		parent = null;
		this.file = file;
	}
	
	public FileNodeModel(File file, FileNodeModel parent)
	{
		this.parent = parent;
		this.file = file;
	}
	
	/**
	 * A container node is a dirrectory (a folder).
	 * A container node has other nodes as children
	 * @return
	 */
	public boolean isFolder()
	{
		return (getType() == Filetype.FOLDER);
	}

	/**
	 * returns true if this filesystem node is visible
	 * to the user.
	 * @return
	 */
	public boolean isVisible()
	{
		int dot = file.getName().indexOf('.');
		if (dot == 0) return false;
		return ! file.isHidden();
	}
	
	/**
	 * returns the parent of this node,
	 * or null if this node has no parent
	 * @return
	 */
	public FileNodeModel getParent()
	{
		return parent;
	}
	
	/**
	 * returns the name of the file.
	 * @return
	 */
	public String getName()
	{
		String filename = file.getName();
		int dot = filename.lastIndexOf('.');
		if (dot < 0) return filename;
		return filename.substring(0, dot);
	}
	
	/**
	 * returns the extension of the file,
	 * or an empty string if the file has no extension.
	 * @return
	 */
	public String getExtension()
	{
		String filename = file.getName();
		int dot = filename.lastIndexOf('.');
		if (dot < 0) return "";
		return filename.substring(dot+1);
	}
	
	public Filetype getType()
	{
		if (file.isDirectory()) return Filetype.FOLDER;
		
		// TODO: detect types from the file extension
		
		return Filetype.OTHER;
	}
	
	/**
	 * If this node is a folder, it returns a list
	 * of visible children to this node.  If this node is not 
	 * a folder, it returns null.
	 * @return
	 */
	public List<FileNodeModel> getChildren()
	{
		if (! isFolder() ) return null;
		
		List<FileNodeModel> children = new ArrayList<FileNodeModel>();
		
		File[] fileChildren = file.listFiles();
		for (int i=0; i< fileChildren.length; i++)
		{
			FileNodeModel n = new FileNodeModel(fileChildren[i], this);
			if (n.isVisible()) children.add(n);
		}
		
		return children;
	}
	
	public String toString()
	{
		String str = "---- FileSystemNode ----\n";
		str += "name: " + getName() + "\n";
		str += "ext: " + getExtension() + "\n";
		str += "folder: " + isFolder() + "\n";
		if (getParent() == null) str += "parent: none\n";
		else str += "parent: " + getParent().getName();
		return str;
	}
	
}
