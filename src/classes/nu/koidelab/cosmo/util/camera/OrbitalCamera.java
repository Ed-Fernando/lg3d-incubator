/*
 * ºîÀ®Æü: 2005/07/07
 */
package nu.koidelab.cosmo.util.camera;

import java.util.Calendar;

import nu.koidelab.cosmo.manager.CSDGeneralManager;
import nu.koidelab.cosmo.util.function.OrbitFunction;

//public class OrbitalCamera extends CameraNode {
public class OrbitalCamera extends LightweightCameraNode {
    private Calendar currentPosition = Calendar.getInstance();
    
    public OrbitalCamera(){
        super();  
        currentPosition.setTimeInMillis(System.currentTimeMillis());
    }
    
    public OrbitalCamera(float angle, float yaw, float distance){
        super(angle, yaw, distance);
        currentPosition.setTimeInMillis(System.currentTimeMillis());
    }
        
    /** move this current point of regards along with orbit */
    public void movePOR(boolean toFuture){
        OrbitFunction func = CSDGeneralManager.getInstance().getFunction();
        int mode = func.getMode();
        int direction = 1;  
        if(!toFuture) direction = -1;
        switch (mode) {
        case Calendar.YEAR:
            currentPosition.add(Calendar.DAY_OF_MONTH, direction * 10);
            break;
        case Calendar.MONTH:            
            currentPosition.add(Calendar.DAY_OF_MONTH, direction * 1);
            break;
        case Calendar.WEEK_OF_MONTH:
            currentPosition.add(Calendar.HOUR_OF_DAY, direction * 12);
            break;
        case Calendar.DAY_OF_MONTH:
            currentPosition.add(Calendar.HOUR_OF_DAY, direction * 3);
            break;
        default:
            /* Do nothing */
            return;
        }
    }
    
    /** move this camera to argument position */
    public void setCurrentPOR(long msec){
        currentPosition.setTimeInMillis(msec);
    }
    
    /** return this camera's current position time in milli-seconds */
    public long getCurrentPosition(){
        return currentPosition.getTimeInMillis();
    }
}
