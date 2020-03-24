package nu.koidelab.cosmo.util.function.spiral;

import java.util.Calendar;

import javax.vecmath.Vector3f;

import nu.koidelab.cosmo.manager.CosmoConfig;
import nu.koidelab.cosmo.util.camera.cameranode.OrbitalCamera;
import nu.koidelab.cosmo.util.function.OrbitFunction;
import nu.koidelab.cosmo.util.function.decoration.DayOrbitDecoration;
import nu.koidelab.cosmo.util.function.decoration.MonthOrbitDecoration;
import nu.koidelab.cosmo.util.function.decoration.OrbitDecoration;
import nu.koidelab.cosmo.wg.nodes.EditableOrbiter;
import nu.koidelab.cosmo.wg.nodes.Orbiter;
import nu.koidelab.cosmo.wg.shape.NameTag.Location;

public class Spiral implements OrbitFunction{
//	private static final float SPIRAL_RADIUS = 0.15f;
	private static final float SPIRAL_RADIUS = CosmoConfig.SCREEN_WIDTH*5/8;
//	private static final float SPIRAL_DISTANCE = 0.12f;   
	private static final float SPIRAL_DISTANCE = CosmoConfig.SCREEN_HEIGHT*2/3;
    public static final int ONEDAY = 1;
    public static final int ONEWEEK = 2;
    public static final int ONEMONTH = 3;
    private static final long SEVEN_DAYS_LENGTH = 7*24*60*60*1000;
    private static Calendar now = Calendar.getInstance();
    
    /* ------ private fields ------ */
	private  double cycle;
	private double heightDistance;    
    private int currentMode;
    
    
	public Spiral() {
		// calculate cycle        
		cycle = 2 * Math.PI / SEVEN_DAYS_LENGTH;

		// calculate height space of a cycle
		heightDistance = SPIRAL_DISTANCE / SEVEN_DAYS_LENGTH;
                
        setMode(Calendar.WEEK_OF_MONTH);
	}
    
    public void setCycle(int flag) {
        Calendar tmp = (Calendar) now.clone();
        long cycleTime = 0;
        switch (flag) {
        case 1:
            tmp.add(Calendar.DATE, 1);
            cycleTime = tmp.getTimeInMillis() - now.getTimeInMillis();
            break;
        case 2:
            tmp.add(Calendar.DATE, 7);
            cycleTime = tmp.getTimeInMillis() - now.getTimeInMillis();
            break;
        case 3:
            tmp.add(Calendar.MONTH, 1);
            cycleTime = tmp.getTimeInMillis() - now.getTimeInMillis();
            break;
        default:
            tmp.add(Calendar.DATE, 7);
            cycleTime = tmp.getTimeInMillis() - now.getTimeInMillis();
        }
        cycle = 2 * Math.PI / cycleTime;

        // 高さを設定する
        heightDistance = SPIRAL_DISTANCE / cycleTime;
    }
    
	public float[] getPosition(long time) {
        long target = time - System.currentTimeMillis();

		double x, y, z;
		x = SPIRAL_RADIUS * Math.sin(cycle * target);
		z = -SPIRAL_RADIUS * Math.cos(cycle * target);
		y = -heightDistance * target;

		float[] position = {(float) x, (float) y, (float) z};
		return position;
	}

    public void getPosition(long time, Vector3f vec) {
        long target = time - System.currentTimeMillis();

        double x, y, z;
        x = SPIRAL_RADIUS * Math.sin(cycle * target);
        z = -SPIRAL_RADIUS * Math.cos(cycle * target);
        y = -heightDistance * target;

        vec.set((float) x, (float) y, (float) z);       
    }
    
    public int getMode() {
        return currentMode;
    }
    
    public void setDecoration(OrbitDecoration deco){
        if (deco instanceof MonthOrbitDecoration) {
            setDecoration((MonthOrbitDecoration) deco);
            return;
        }
       if (deco instanceof DayOrbitDecoration) {
            setDecoration((DayOrbitDecoration) deco);
            return;
        }
        System.err.println("Spiral : Do Nothing.");
    } /* Do Nothing */
    
    private void setDecoration(DayOrbitDecoration deco){
        if(!(deco.getParts() instanceof SpiralDayDecoration)){
        	/* Set null parts */
           deco.setParts( new SpiralDayDecoration() );
        }
    }
        
    private void setDecoration(MonthOrbitDecoration deco) {
        if(!(deco.getParts() instanceof SpiralMonthDecoration)){
            long st = deco.getSt();
            long ed = deco.getEd();
            float[] color = deco.getMonthColor();
            deco.setParts( new SpiralMonthDecoration(st, ed, color) );
        }
        
        SpiralMonthDecoration decoration = (SpiralMonthDecoration)(deco.getParts());
        
        decoration.updateMainLine(this);
    }
    
    public void setMode(int mode) {
        if(currentMode == mode) 
            return;                
        currentMode = mode;
        // TODO mode changing is not implemented. Is this needed?
    }
    
    public void setPosition(Orbiter orbiter) {
        long msec = orbiter.getTime();
        orbiter.changeScale(1, 600);
        float[] vec = getPosition(msec);
        orbiter.changeTranslation(vec[0], vec[1], vec[2], 600);
        if (orbiter instanceof EditableOrbiter) {
			EditableOrbiter eo = (EditableOrbiter) orbiter;
			eo.changeScale(1f, 600);
			eo.getNameTag().setTextScale(1f);
			eo.getNameTag().setLocation(Location.TOP);
		}
    }
        
    public float[] getPositionTangentVector(long msec) {
        throw new RuntimeException("Spiral::getPositionTangentVector Not Implemented");
    }

    /* TODO Optimize later. */
    private Vector3f vec = new Vector3f();
    public void resetCamera(OrbitalCamera camera) {
        getPosition(camera.getCurrentPosition(), vec);
        vec.scale(-1);
        vec.x = 0;
        vec.z = 0;
        camera.setPointOfRegard(vec.x, vec.y, vec.z);
        camera.setDistance(2.8f);
        camera.setOffset(0, 0, 0);
    }
    
    public void initializeCamera(OrbitalCamera camera) {        
//    	camera.setDefaultAngle((float)(Math.PI/2));        
        camera.setDefaultAngle(0);
        camera.setDefaultYaw(0);
        camera.setDefaultOffset(0, 0, 0);
 		 camera.setDefaultDistance(2.9f);
 		 camera.resetDefaultValues();
    }

    
    /* --------------------------------- private functions --------------------------------*/
    private float getAngle(long time) {
      long target = time - System.currentTimeMillis();
      double angle = cycle * target - Math.PI;
      
      return (float)Math.sin(angle);
//    return (float)angle;
  }
    /* ----------------------------------------------------------------------------------------*/

}
