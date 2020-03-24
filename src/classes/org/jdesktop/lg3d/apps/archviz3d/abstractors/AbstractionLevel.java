package org.jdesktop.lg3d.apps.archviz3d.abstractors;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

import org.jdesktop.lg3d.apps.archviz3d.abstractors.AbstractionLevel;

/**
 * 
 * @author teyseyre
 * 
 */
public class AbstractionLevel implements Comparable, Cloneable {
	private Vector<String> scale;

	private int currentLevel;

	private boolean enabled;

	/**
	 * @param s
	 */
	public AbstractionLevel(String[] s) {
		currentLevel = 1;
		enabled = true;
		scale = new Vector<String>();
		for (int i = 0; i < s.length; i++)
			scale.add(s[i]);
	}

	/**
	 * @param i
	 */
	public void incrementLevelBy(int i) {
		currentLevel += i;
		if (currentLevel < 1)
			currentLevel = 1;
		else if (currentLevel > scale.size())
			currentLevel = scale.size();
	}

	/**
	 * @param s
	 */
	public void setScale(Vector<String> s) {
		scale = s;
	}

	/**
	 * @return Vector
	 */
	public Vector getScale() {
		return scale;
	}

	/**
	 * @param b Enabled
	 */
	public void setEnabled(boolean b) {
		enabled = b;
	}

	/**
	 * @return int
	 */
	public int getLevel() {
		return currentLevel;
	}

	/**
	 * @param i nivel.
	 */
	public void setLevel(int i) {
		if (i < 1)
			currentLevel = 1;
		else if (i > scale.size())
			currentLevel = scale.size();
		else
			currentLevel = i;

	}

	/**
	 * @param o Objecto
	 * @return boolean
	 */
	public boolean lessThan(Object o) {
		if (!enabled)
			return false;
		if (o instanceof String)
			return compareTo(o) < 0;
		if (((Vector) o).isEmpty())
			return true;

		for (Iterator i = ((Vector) o).iterator(); i.hasNext();) {
			i.next();
			int index = scale.indexOf(i);
			if (index > 0 && currentLevel < index)
				return true;
		}
		return false;
	}

	/**
	 * @param o Objecto
	 * @return boolean
	 */
	public boolean moreThan(Object o) {
		if (!enabled)
			return false;
		if (o instanceof String)
			return compareTo(o) > 0;
		if (((Vector) o).isEmpty())
			return true;

		for (Iterator i = ((Vector) o).iterator(); i.hasNext();) {
			i.next();
			int index = scale.indexOf(i);
			if (index > 0 && currentLevel > index)
				return true;
		}
		return false;
	}

	/**
	 * @param o Objecto
	 * @return boolean
	 */
	public boolean lessEqualsThan(Object o) {
		if (!enabled)
			return false;
		if (o instanceof String)
			return compareTo(o) <= 0;
		if (((Vector) o).isEmpty())
			return true;

		for (Iterator i = ((Vector) o).iterator(); i.hasNext();) {
			i.next();
			int index = scale.indexOf(i);
			if (index > 0 && currentLevel <= index)
				return true;
		}
		return false;
	}

	/**
	 * @param o Objeto
	 * @return boolean
	 */
	public boolean moreEqualsThan(Object o) {
		if (!enabled)
			return false;
		if (o instanceof String)
			return compareTo(o) >= 0;
		if (((Vector) o).isEmpty())
			return true;

		for (Iterator i = ((Vector) o).iterator(); i.hasNext();) {
			i.next();
			int index = scale.indexOf(i);
			if (index > 0 && currentLevel >= index)
				return true;
		}
		return false;
	}

	/**
	 * 
	 */
	public boolean equals(Object o) {
		if (!enabled)
			return false;
		if (o instanceof String)
			return compareTo(o) == 0;
		if (((Vector) o).isEmpty())
			return true;

		for (Iterator i = ((Vector) o).iterator(); i.hasNext();) {
			i.next();
			int index = scale.indexOf(i);
			if (index > 0 && currentLevel == index)
				return true;
		}
		return false;
	}

	public int compareTo(Object s) {
		return new Integer(currentLevel).compareTo(new Integer(scale.indexOf(s)));
	}
	
	public AbstractionLevel clone() {
		String[] aux = new String[scale.size()];
		int i= 0;
		for (Enumeration e = scale.elements(); e.hasMoreElements(); )
			aux[i++]= (String)e.nextElement();
		
		AbstractionLevel al = new AbstractionLevel(aux);
		al.setLevel(currentLevel);
		
		return al; 
	}

}
