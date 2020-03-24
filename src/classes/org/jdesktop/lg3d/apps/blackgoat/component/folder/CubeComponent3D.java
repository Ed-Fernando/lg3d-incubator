package org.jdesktop.lg3d.apps.blackgoat.component.folder;

import org.jdesktop.lg3d.wg.Component3D;
/**
 * @author dai
 */

/**
 * One component of CubeBox. Each has ID which is used for when 
 * addiding actionListener.
 */
public class CubeComponent3D extends Component3D {
	private int aspectId;
	
	public CubeComponent3D() {}
	
	/**
	 * returns aspectId.
	 * @return aspectId
	 */
	public int getId(){
		return this.aspectId;
	}
	/**
	 * sets cube aspects Id.
	 * @param aspectId
	 */
	public void setId(int aspectId){
		this.aspectId = aspectId;
	}
}
