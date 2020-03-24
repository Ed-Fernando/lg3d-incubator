/*
 * ºîÀ®Æü: 2005/08/15
 */
package nu.koidelab.cosmo.wg.shape;

import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.utils.shape.Box;
import org.jdesktop.lg3d.utils.shape.Cone;
import org.jdesktop.lg3d.utils.shape.Cylinder;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Container3D;

public class CameraBody extends Container3D {
    public CameraBody() {
        /* set a default appearance */
        this(new SimpleAppearance(0.65f, 0.8f, 0.65f, 0.8f,
                SimpleAppearance.ALLOW_TRANSPARENCY_ATTRIBUTES_READ
                        | SimpleAppearance.ALLOW_TRANSPARENCY_ATTRIBUTES_WRITE));
    }

    public CameraBody(Appearance app) {
        Box box = new Box(0.002f, 0.003f, 0.004f, new SimpleAppearance(0.65f,
                0.8f, 0.65f, 0.8f,
                SimpleAppearance.ALLOW_TRANSPARENCY_ATTRIBUTES_READ
                        | SimpleAppearance.ALLOW_TRANSPARENCY_ATTRIBUTES_WRITE));
        Component3D body = new Component3D();
        body.addChild(box);
        Cone cone = new Cone(0.004f, 0.007f, app);
        Component3D lens = new Component3D();
        lens.addChild(cone);
        lens.setTranslation(0f, 0f, -0.0038f);
        lens.setRotationAxis(1f, 0f, 0f);
        lens.setRotationAngle((float) Math.toRadians(90));

        Cylinder cylinder = new Cylinder(0.0027f, 0.0017f, app);
        Component3D tape1 = new Component3D();
        tape1.addChild(cylinder);
        tape1.setTranslation(0f, 0.005f, 0.0022f);
        tape1.setRotationAxis(0f, 0f, 1f);
        tape1.setRotationAngle((float) Math.toRadians(90));
        Cylinder cylinder2 = new Cylinder(0.0027f, 0.0017f, app);
        Component3D tape2 = new Component3D();
        tape2.addChild(cylinder2);
        tape2.setTranslation(0f, 0.005f, -0.0022f);
        tape2.setRotationAxis(0f, 0f, 1f);
        tape2.setRotationAngle((float) Math.toRadians(90));

        addChild(body);
        addChild(lens);
        addChild(tape1);
        addChild(tape2);
    }
}
