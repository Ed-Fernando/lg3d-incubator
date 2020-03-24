package org.jdesktop.lg3d.apps.physics;
import java.util.Comparator;
/**
 * A Comparator to define the natural order of contacts as the order with the deepest first and the most shallow last.
 * @author Tobias Evert
 */
public class ContactComparator implements Comparator<Contact> {
    
    /** Creates a new instance of ContactComparator */
    public ContactComparator() {
    }
    
    /**
     * Compares two Contact objects byy their depths.
     *
     * @param c1 the first Contact to compare.
     * @param c2 the second Contact to compare.
     *
     * @return 1 if the first Contact is more shallow than the other, -1 otherwise.
     */
    
    public int compare(Contact c1, Contact c2 ) {
          
        if(c1.getDepth()<c2.getDepth())
            return -1;
        else 
            return 1;
       
    }
    
}
