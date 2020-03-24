package nu.koidelab.cosmo.util.camera;

import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.sg.Node;
import org.jdesktop.lg3d.utils.c3danimation.NaturalMotionAnimation;
import org.jdesktop.lg3d.wg.Component3D;


//class MinitureCameraNode extends CameraNodeBase{
class MinitureCameraNode extends Component3D{
    protected Component3D yawChanger = new Component3D();
    protected Component3D angleChanger = new Component3D();
    protected Component3D distanceChanger = new Component3D();
    protected float defaultAngle;
    protected float defaultYaw;
    protected float defaultDistance;
    
    MinitureCameraNode(float angle, float yaw, float distance){
        defaultAngle = -angle;
        defaultYaw = -yaw;
        defaultDistance = distance/100;
                
        setDuration(1000);
                
        /* create tree */
        /* Tree structure of changers
        this -> yawChanger -> angleChanger -> distanceChanger 
        
        offset:    not used
        distance:  distance between eye's and POR
        angle:     angle of elevation
        yaw:       angle of direction
        por:       not used
        */
        super.addChild(yawChanger);
        yawChanger.addChild(angleChanger);
        angleChanger.addChild(distanceChanger);
        
                      
        angleChanger.setRotationAxis(1, 0, 0);
        yawChanger.setRotationAxis(0, 1, 0);
        distanceChanger.setTranslation(0, 0, -defaultDistance);
        angleChanger.setRotationAngle(defaultAngle);
        yawChanger.setRotationAngle(defaultYaw);        
    }
    
    
    public void resetDefaultValues() {
    	setAngle(defaultAngle);
    	setYaw(defaultYaw);
    	setDistance(defaultDistance);
    }
    
    @Override
    public void addChild(Node child) {
        distanceChanger.addChild(child);
    }

    
    /*================= setter for current values ==================*/        
    public void setDuration(int d) {
        yawChanger.setAnimation(new NaturalMotionAnimation(d));
        angleChanger.setAnimation(new NaturalMotionAnimation(d));
        distanceChanger.setAnimation(new NaturalMotionAnimation(d));
    }
    
    public void setYaw(float radian){
        yawChanger.changeRotationAngle(-radian);
    }
    
    public void setAngle(float radian){
        float rad = - radian;
       if(rad < 1.57079f && rad > -1.57079f){
            angleChanger.changeRotationAngle(rad);
        }
    }
    
    public void setDistance(float distance){
        float dist = distance / 100;
        if(dist < 0.02f) return;
        if(dist > 0.06f) return;
        distanceChanger.changeTranslation(0, 0, dist);
    }

    
    /*================= setter for default values ==================*/        
    public void setDefaultAngle(float radian) {
        defaultAngle = radian;
    }
    
    public void setDefaultDistance(float dist) {
        defaultDistance = dist;
    }
    
    public void setDefaultYaw(float radian) {
        defaultYaw = radian;
    }
    

    
    
    /*================= getter for current values ==================*/
    public float getAngle() {        
        return angleChanger.getRotationAngle();
    }
    
    public float getYaw() {
        return yawChanger.getRotationAngle();
    }

    public float getDistance() {
        Vector3f vec = new Vector3f();
        distanceChanger.getTranslation(vec);
        return -vec.z;
    }
    
    
    
    /*================= getter for default values ==================*/
    public float getDefaultAngle() {
        return defaultAngle;
    }

    public float getDefaultYaw() {
        return defaultYaw;
    }
    
    public float getDefaultDistance() {
        return defaultDistance;
    }

    
    
    /*================= getter for final values ==================*/
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
     

    
    
    
    /* ================================================================== */
    /* ====================== Unable these methods ====================== */
    /* ================================================================== 
    @Deprecated
    public void resetDefaultPosition() {
        throw new RuntimeException("This method cannot be used from MinitureCameraNode");       
    }
    
    @Deprecated
    public Vector3f getFinalOffset() {
        throw new RuntimeException("This method cannot be used from MinitureCameraNode");       
    }
    
    @Deprecated
    public Vector3f getFinalPOR() {
        throw new RuntimeException("This method cannot be used from MinitureCameraNode");       
    }
    
    @Deprecated
    public void setPointOfRegard(float x, float y, float z) {
        throw new RuntimeException("This method cannot be used from MinitureCameraNode");       
    }
    
    @Deprecated
    public void setOffset(float x, float y, float z) {
    	throw new RuntimeException("This method cannot be used from MinitureCameraNode");    	
    }
    
    @Deprecated
    public void setDefaultOffset(float x, float y, float z) {
    	throw new RuntimeException("This method cannot be used from MinitureCameraNode");    	
    }
    
    @Deprecated
    public void setDefaultPOR(float x, float y, float z) {
    	throw new RuntimeException("This method cannot be used from MinitureCameraNode");    	
    }
    
    @Deprecated
    public Vector3f getPointOfRegard() {
    	throw new RuntimeException("This method cannot be used from MinitureCameraNode");    	
    }
    
    @Deprecated
    public Vector3f getOffset() {
    	throw new RuntimeException("This method cannot be used from MinitureCameraNode");    	
    }
    
    @Deprecated
    public Vector3f getDefaultPOR() {
    	throw new RuntimeException("This method cannot be used from MinitureCameraNode");    	
    }
    
    @Deprecated
    public Vector3f getDefaultOffset() {
    	throw new RuntimeException("This method cannot be used from MinitureCameraNode");    	
    }
    
    */    	
}
