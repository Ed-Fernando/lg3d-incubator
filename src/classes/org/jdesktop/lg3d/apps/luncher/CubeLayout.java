/*
 * CubeLayout.java
 *
 * Created on August 31, 2004, 2:15 PM
 */

package org.jdesktop.lg3d.apps.luncher;
import javax.vecmath.Vector3f;
import org.jdesktop.lg3d.wg.LayoutManager3D;
import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.utils.shape.GlassyPanel;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;

/**
 *
 * @author  hbaastrup
 */
public class CubeLayout implements LayoutManager3D {
    private Container3D cont;
    
    public CubeLayout() {
    }
    
    public void setContainer(Container3D cont) {
        this.cont = cont;
    }
    
    public void layoutContainer() {
        Vector3f location = null;
	Vector3f size = new Vector3f(0.02f, 0.02f, 0.02f);
	float x;
	float y;
	float z;

        int num = cont.numChildren();
        for (int i = 0; i < 6; i++) {
            Component3D comp;
            if (i >= num) break;
            comp = (Component3D)cont.getChild(i);
            
            switch (i) {
                case 0:
		    if ( location == null ) location = new Vector3f();
                    location = cont.getTranslation(location);
//		    size = comp.getPreferredSize(size);
                    x = location.x;
                    y = location.y;
                    z = location.z + size.z*0.5f;
                    comp.setTranslation(x, y, z);
                    break;
                case 1:
                    x = location.x;
                    y = location.y + size.y*0.5f;
                    z = location.z;
                    comp.setTranslation(x, y, z);
                    comp.setRotationAxis(1.0f, 0.0f, 0.0f);
                    comp.setRotationAngle((float)Math.toRadians(-90));
                    break;
                case 2:
                    x = location.x;
                    y = location.y;
                    z = location.z - size.z*0.5f;
                    comp.setTranslation(x, y, z);
                    comp.setRotationAxis(1.0f, 0.0f, 0.0f);
                    comp.setRotationAngle((float)Math.toRadians(180));
                    break;
                case 3:
                    x = location.x;
                    y = location.y - size.y*0.5f;
                    z = location.z;
                    comp.setTranslation(x, y, z);
                    comp.setRotationAxis(1.0f, 0.0f, 0.0f);
                    comp.setRotationAngle((float)Math.toRadians(90));
                    break;
                case 4:
                    x = location.x - size.x*0.5f;
                    y = location.y;
                    z = location.z;
                    comp.setTranslation(x, y, z);
                    comp.setRotationAxis(0.0f, 1.0f, 0.0f);
                    comp.setRotationAngle((float)Math.toRadians(-90));
                    break;
                case 5:
                    x = location.x + size.x*0.5f;
                    y = location.y;
                    z = location.z;
                    comp.setTranslation(x, y, z);
                    comp.setRotationAxis(0.0f, 1.0f, 0.0f);
                    comp.setRotationAngle((float)Math.toRadians(90));
                    break;
            }
        }
    }
    
    public void addLayoutComponent(org.jdesktop.lg3d.wg.Component3D comp, Object constraints) {
    }
    
    public void removeLayoutComponent(org.jdesktop.lg3d.wg.Component3D comp) {
    }
    
    public boolean rearrangeLayoutComponent(Component3D comp, Object newConstraints) {
        return false;
    }
}
