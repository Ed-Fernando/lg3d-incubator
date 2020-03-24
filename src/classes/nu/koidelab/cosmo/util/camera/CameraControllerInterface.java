package nu.koidelab.cosmo.util.camera;


public interface CameraControllerInterface {        
    
    public void setYaw(float radian);
    
    public void setAngle(float radian);
    
    public void setDistance(float dist);
    
    public void setPOR(float x, float y, float z);
    
    public void resetDefaultValues();
    
    public void resetDefaultPosition();
}
