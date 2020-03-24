package nu.koidelab.cosmo.util.camera;

import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.sg.Node;

public class LightweightCameraNode extends LightweightCameraNodeBase{

	public LightweightCameraNode(){
		this(0f, 0f, 2.41f);		
	}
	
    public LightweightCameraNode(float angle, float yaw, float distance){
        this(angle, yaw, distance, new Vector3f(0, 0, 0));
    }
    
    public LightweightCameraNode(float angle, float yaw, float distance, Vector3f por){
        defaultAngle = angle;
        defaultYaw = yaw;
        System.out.println("DefaultDistance set : "+distance);
        defaultDistance = distance;
        defaultPOR = por;
                
        setDuration(1000);
                
        /* temporary tree */
        super.addChild(offsetChanger);
        offsetChanger.addChild(distanceChanger);
        distanceChanger.addChild(angleChanger);
        angleChanger.addChild(yawChanger);
        yawChanger.addChild(porChanger);
        
        
        setTranslation(0, 0, 2.41f);        
        /* */
        angleChanger.setRotationAxis(1, 0, 0);
        yawChanger.setRotationAxis(0, 1, 0);
        distanceChanger.setTranslation(0, 0, -defaultDistance);
        angleChanger.setRotationAngle(defaultAngle);
        yawChanger.setRotationAngle(defaultYaw);
        porChanger.setTranslation(defaultPOR.x, defaultPOR.y, defaultPOR.z);
        
    }
    
    

    
    @Override
    public void addChild(Node child) {
//    	offsetChanger.addChild(child);
    	porChanger.addChild(child);
    }
} 
