/*
 * Created on 2005/07/12
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.jdesktop.lg3d.apps.blackgoat.layout;

import javax.vecmath.Vector3f;
import org.jdesktop.lg3d.wg.LayoutManager3D;
import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.apps.blackgoat.component.folder.CubeComponent3D;
import org.jdesktop.lg3d.utils.shape.GlassyPanel;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;

/**
 * @author dai
 */
/**
 * This class is used for creating cube layout.
 */
public class CubeLayout implements LayoutManager3D {
	private Container3D cont;

	private static float sideLength = CubeInfo.sideLength;

	/*
	public CubeLayout() {
	}
*/
	/**
	 * sets Container3D.
	 */
	public void setContainer(Container3D cont) {
		this.cont = cont;
	}

	/**
	 * sets cube layout.
	 */
	public void layoutContainer() {
		Vector3f location = null;
		Vector3f size = new Vector3f(sideLength, sideLength, sideLength);
		float x;
		float y;
		float z;

		int num = cont.numChildren();
		for (int i = 0; i < 6; i++) {
			CubeComponent3D comp;
			if (i >= num)
				break;
			comp = (CubeComponent3D) cont.getChild(i);
			switch (i) {
			case 0:
				if (location == null)
					location = new Vector3f();
				location = cont.getTranslation(location);
				// size = comp.getPreferredSize(size);
				x = location.x;
				y = location.y;
				z = location.z + size.z * 0.5f;
				comp.setTranslation(x, y, z);
				comp.setId(i);
				break;
			case 1:
				x = location.x;
				y = location.y + size.y * 0.5f;
				z = location.z;
				comp.setTranslation(x, y, z);
				comp.setRotationAxis(1.0f, 0.0f, 0.0f);
				comp.setRotationAngle((float) Math.toRadians(-90));
				comp.setId(i);
				break;
			case 2:
				x = location.x;
				y = location.y;
				z = location.z - size.z * 0.5f;
				comp.setTranslation(x, y, z);
				comp.setRotationAxis(1.0f, 0.0f, 0.0f);
				comp.setRotationAngle((float) Math.toRadians(180));
				comp.setId(i);
				break;
			case 3:
				x = location.x;
				y = location.y - size.y * 0.5f;
				z = location.z;
				comp.setTranslation(x, y, z);
				comp.setRotationAxis(1.0f, 0.0f, 0.0f);
				comp.setRotationAngle((float) Math.toRadians(90));
				comp.setId(i);
				break;
			case 4:
				x = location.x - size.x * 0.5f;
				y = location.y;
				z = location.z;
				comp.setTranslation(x, y, z);
				comp.setRotationAxis(0.0f, 1.0f, 0.0f);
				comp.setRotationAngle((float) Math.toRadians(-90));
				comp.setId(i);
				break;
			case 5:
				x = location.x + size.x * 0.5f;
				y = location.y;
				z = location.z;
				comp.setTranslation(x, y, z);
				comp.setRotationAxis(0.0f, 1.0f, 0.0f);
				comp.setRotationAngle((float) Math.toRadians(90));
				comp.setId(i);
				break;
			}
		}
	}

	/**
	 * overrides layout manager.
	 */
	public void addLayoutComponent(org.jdesktop.lg3d.wg.Component3D comp,
			Object constraints) {
	}

	/**
	 * overrides layout manager.
	 */
	public void removeLayoutComponent(org.jdesktop.lg3d.wg.Component3D comp) {
	}

	/**
	 * overrides layout manager.
	 */
	public boolean rearrangeLayoutComponent(Component3D comp,
			Object newConstraints) {
		return false;
	}
}
