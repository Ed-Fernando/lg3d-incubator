package nu.koidelab.cosmo.util.camera;

import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.wg.Component3D;

public abstract class CameraNodeBase extends Component3D{

	/* ------------------ Setter ------------------ */
	/**
	 * @param int d : duratin of animation
	 */
	public abstract void setDuration(int d);

	/** 
	 * @param radian : Camera Yaw value
	 */
	public abstract void setYaw(float radian);

	public abstract void setAngle(float radian);

	public abstract void setDistance(float dist);	
	
	public abstract void setPointOfRegard(float x, float y , float z);	
	
	public abstract void setOffset(float x, float y , float z);
	

	public abstract void setDefaultYaw(float radian);

	public abstract void setDefaultAngle(float radian);

	public abstract void setDefaultDistance(float dist);

	public abstract void setDefaultPOR(float x, float y, float z);
	
	public abstract void setDefaultOffset(float x, float y, float z);
	
	
	
	/* ------------------ Getter ------------------ */

	public abstract float getAngle();

	public abstract float getYaw();

	public abstract float getDistance();

	public abstract Vector3f getPointOfRegard();
	
	public abstract Vector3f getOffset();
	

	public abstract float getFinalAngle();

	public abstract float getFinalYaw();

	public abstract float getFinalDistance();

	public abstract Vector3f getFinalPOR();
	
	public abstract Vector3f getFinalOffset();
	

	public abstract float getDefaultAngle();

	public abstract float getDefaultYaw();

	public abstract float getDefaultDistance();
	
	public abstract Vector3f getDefaultPOR();
	
	public abstract Vector3f getDefaultOffset();
	
	
	
	/* ------------------ Others ------------------ */

	public abstract void resetDefaultValues();

	public abstract void resetDefaultPosition();

}