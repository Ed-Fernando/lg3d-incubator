package org.jdesktop.lg3d.apps.blackgoat.utils;

import java.util.Comparator;

/**
 * @author Dai Odahara
 */
/**
 * this class is useed for sorting object desendingly.
 */
public class DesendingSortComparator implements Comparator {
	/**
	 * copmares object which is smaller.
	 * @param object1
	 * @param object2
	 * @return 1...smaller / -1...bigger
	 */
	public int compare( Object object1, Object object2 ){
		 return ( (Comparable)object1 ).compareTo( object2 ) * -1;
	}
}
