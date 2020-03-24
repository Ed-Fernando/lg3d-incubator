package nu.koidelab.cosmo.util.function.parabola;

import java.util.Calendar;

import nu.koidelab.cosmo.manager.CSDGeneralManager;
import nu.koidelab.cosmo.util.function.decoration.OrbitDecoration;
import nu.koidelab.cosmo.util.function.parabola.decoration.OrbitalLightShape;

import org.jdesktop.lg3d.wg.Component3D;

class ParabolaDayDecoration extends OrbitDecoration.DecorationParts {

    private long st;
    private long ed;
    private int dayOfWeek;

    private OrbitalLightShape lightShape = null; // for day of week color.
    private float[] color;    

    ParabolaDayDecoration(long st, long ed, int dayOfWeek) {
        this.st = st;
        this.ed = ed;
        this.dayOfWeek = dayOfWeek;
        
        setPickable(false);
        
        if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
            color = new float[3];
            if (dayOfWeek == Calendar.SATURDAY) {
                color[0] = 0.4f;
                color[1] = 0.4f;
                color[2] = 0.6f;
            } else {
                color[0] = 0.6f;
                color[1] = 0.4f;
                color[2] = 0.4f;
            }
            initLight();

        } else if(st <= System.currentTimeMillis() && System.currentTimeMillis() < ed) {
            color = new float[3];
            color[0] = 0.4f;
            color[1] = 0.6f;
            color[2] = 0.4f;
            initLight();
        }
    }
    
    private void initLight(){
        lightShape = new OrbitalLightShape();
        lightShape.setGeometry(st, ed, color, CSDGeneralManager.getInstance()
                .getFunction());
        lightShape.setAppearance(OrbitalLightShape.getLightAppearance());
        
        Component3D c = new Component3D();
        c.addChild(lightShape);
        addChild(c);        
    }
    
    void updateLight(Parabola p){
        if(lightShape == null)
            return;
        lightShape.setGeometry(st, ed, color, p);
    }
}
