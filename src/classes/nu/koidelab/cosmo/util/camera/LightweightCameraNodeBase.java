package nu.koidelab.cosmo.util.camera;

import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.utils.c3danimation.NaturalMotionAnimation;
import org.jdesktop.lg3d.wg.Component3D;

public abstract class LightweightCameraNodeBase extends CameraNodeBase {
	protected Component3D yawChanger = new Component3D();
	protected Component3D angleChanger = new Component3D();
	protected Component3D distanceChanger = new Component3D();
	protected Component3D porChanger = new Component3D();
	protected Component3D offsetChanger = new Component3D();
	protected float defaultAngle;
	protected float defaultYaw;
	protected float defaultDistance;
	protected Vector3f defaultPOR = new Vector3f();
	protected Vector3f defaultOffset = new Vector3f();
	
    /*------------------ Getter ------------------------*/
    public float getAngle() {
    	return angleChanger.getRotationAngle();
    }
    
    public float getYaw() {
    	return yawChanger.getFinalRotationAngle();
    }
    
    public float getDistance() {
    	Vector3f vec = new Vector3f();
    	distanceChanger.getTranslation(vec);
    	return -vec.z;
    }
    
    public Vector3f getPointOfRegard() {
    	Vector3f vec = new Vector3f();
    	porChanger.getTranslation(vec);
    	return vec;
    }
    
    public Vector3f getOffset() {
    	Vector3f vec = new Vector3f();
    	porChanger.getTranslation(vec);
    	return vec;
    }
    
    public float getFinalAngle() {
    	return angleChanger.getFinalRotationAngle();
    }
    
    public float getFinalYaw() {
    	return yawChanger.getFinalRotationAngle();
    }
    
    public float getFinalDistance() {
    	Vector3f vec = new Vector3f();
    	distanceChanger.getFinalTranslation(vec);
    	return -vec.z;
    }
    
    public Vector3f getFinalPOR() {
    	Vector3f vec = new Vector3f();
    	porChanger.getFinalTranslation(vec);
    	return vec;
    }
    
    public Vector3f getFinalOffset() {
    	Vector3f vec = new Vector3f();
    	offsetChanger.getFinalTranslation(vec);
    	return vec;
    }
    
    /*--------------- get default values ------------------*/
    public float getDefaultAngle() {
    	return defaultAngle;
    }
    
    public float getDefaultDistance() {
    	return defaultDistance;
    }
    
    public float getDefaultYaw() {
    	return defaultYaw;
    }
    

    /*------------------ Getter END ------------------------*/
    
    public void resetDefaultPosition() {
    	setPointOfRegard(defaultPOR.x, defaultPOR.y, defaultPOR.z);
    }
    
    public void resetDefaultValues() {
    	 setDistance(defaultDistance);
    	 setAngle(defaultAngle);
    	 setYaw(defaultYaw);
    	 setOffset(defaultOffset.x, defaultOffset.y, defaultOffset.z);
    }
    
    public Vector3f getDefaultPOR() {
    	return defaultPOR;
    }
    
    public Vector3f getDefaultOffset() {
    	return defaultOffset;
    }
    
    
    /*------------------ Setter ------------------------*/
    /*------------ set current values ------------------*/
    public void setAngle(float radian) {
    	angleChanger.changeRotationAngle(radian);
    }
    
    public void setYaw(float radian) {
    	yawChanger.changeRotationAngle(radian);
    }
    
    public void setDistance(float dist) {
    	distanceChanger.changeTranslation(0, 0, -dist);
    }
    
    public void setPointOfRegard(float x, float y, float z) {
    	porChanger.changeTranslation(x, y, z);
    }

    public void setOffset(float x, float y, float z) {
    	offsetChanger.changeTranslation(x, y, z);    	
    }

    
    /*------------ set default values ------------------*/
    public void setDefaultAngle(float radian) {
    	defaultAngle = radian;
    }
    
    public void setDefaultYaw(float radian) {
    	defaultYaw = radian;
    }
    
    public void setDefaultDistance(float dist) {
    	defaultDistance = dist;
    }    
    
    public void setDefaultPOR(float x, float y, float z) {
    	defaultPOR.set(x, y, z);
    }
    
    public void setDefaultOffset(float x, float y, float z) {
    	defaultOffset.set(x, y, z);
    }
    
    
    public void setDuration(int d) {
        setAnimation(new NaturalMotionAnimation(d));
        distanceChanger.setAnimation(new NaturalMotionAnimation(d));
        angleChanger.setAnimation(new NaturalMotionAnimation(d));
        yawChanger.setAnimation(new NaturalMotionAnimation(d));
        porChanger.setAnimation(new NaturalMotionAnimation(d));
        offsetChanger.setAnimation(new NaturalMotionAnimation(d));
    }
        
    /*------------------ Setter END ------------------------*/

}
