/***************************************************************************
 *   Project Looking Glass                                                 *
 *   Incubator Project - 3D Start Menu                                     *
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 *   This program is distributed in the hope that it will be useful,       *
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of        *
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the         *
 *   GNU General Public License for more details.                          *
 *                                                                         *
 *   Author: Colin M. Bullock                                              *
 *   cmbullock@gmail.com                                                   *
 *                                                                         *
 *   You should have received a copy of the GNU General Public License     *
 *   along with this program; if not, write to the                         *
 *   Free Software Foundation, Inc.,                                       *
 *   59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.             *
 ***************************************************************************/
package org.jdesktop.lg3d.apps.blackgoat.action;

import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.apps.blackgoat.component.PopupTextComponent;
import org.jdesktop.lg3d.utils.action.ActionBoolean;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.event.LgEventSource;

public class PopupAction implements ActionBoolean {
    
    private PopupTextComponent text;
    
    private Component3D comp;
    
    private float offset;
    
    public PopupAction(PopupTextComponent text, Component3D comp, float offset) {
    	this.text= text;
        this.comp= comp;
        this.offset= offset;
        
    }
    
    public void performAction(LgEventSource source, boolean state) {
    	System.out.print("HERERERER");
        if(state) {
            Vector3f pos= new Vector3f();
            comp.getFinalTranslation(pos);
            //pos = comp.getTranslation(new Vector3f());
            text.setTranslation(pos.x + offset, pos.y + offset, pos.z  - 0.0001f);
            text.changeVisible(true);
            System.out.print("HERERERER");
        } else {
            text.changeVisible(false);
        }
    }
    
}