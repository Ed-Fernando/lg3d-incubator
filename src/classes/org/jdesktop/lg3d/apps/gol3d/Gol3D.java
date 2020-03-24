/* $RCSfile: Gol3D.java,v $
 *
 * LG3D Incubator Project - gol3d
 * A simple Game of Life for Looking Glass 3D
 *
 * USAGE : 
 * 
 * java org.jdesktop.lg3d.apps.gol3d.gol3D <no args> for default life rules (2 3 3)
 * 
 * java org.jdesktop.lg3d.apps.gol3d.gol3D X Y Z 
 *  where X = minimum number of neighbours to survive
 *  where Y = maximum number of neighbours to survive
 *  where Z = number of neighbours to born
 * 
 *
 * The contents of this file are subject to the GNU General Public
 * License, Version 2 (the "License"); you may not use this file
 * except in compliance with the License. A copy of the License is
 * available at http://www.opensource.org/licenses/gpl-license.php.
 *
 * $Date: 2005-06-24 19:56:39 $
 * Author: Van der Haegen Mathieu (dwarfy)
 */

package org.jdesktop.lg3d.apps.gol3d;

import org.jdesktop.lg3d.apps.gol3d.GolController;

public class Gol3D {
	
   public static void main(String[] args) {
        GolController g3d;
        if (args.length == 3) {
            int values[] = new int[4] ;
            try 
            {
                 values[0] = Integer.parseInt (args[0]);
                 values[1] = Integer.parseInt (args[1]);
                 values[2] = Integer.parseInt (args[2]);
            } 
            catch (NumberFormatException e)  
            {
                    values[0] = 2; 
                    values[1] = 3;
                    values[2] = 3;
 
            }
            g3d = new GolController(values[0],values[1],values[2]);

        //default
        } else {
            g3d = new GolController(2,3,3);
        }

    }
}    

