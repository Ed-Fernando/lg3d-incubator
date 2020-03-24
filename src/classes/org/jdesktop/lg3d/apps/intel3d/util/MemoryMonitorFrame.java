/*
 * 16:46:35 12/05/99
 *
 * MemoryMonitorFrame.java - A windowed support for MemoryMonitor
 * Copyright (C) 1999 Romain Guy
 * powerteam@chez.com
 * www.chez.com/powerteam
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package org.jdesktop.lg3d.apps.intel3d.util;

import javax.swing.JFrame;
import javax.swing.JDialog;

public class MemoryMonitorFrame extends JFrame
{
  public MemoryMonitorFrame()
  {
    super("Memory Monitor");
    setResizable(true);
    getContentPane().add("Center", new MemoryMonitor());
    pack();
    //setSize(new Dimension(200, 200));
    setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    // -- show();
  }

  public static void main(String args[])
  {
    new MemoryMonitorFrame();
  }
}

// End of MemoryMonitorFrame.java
