package nu.koidelab.cosmo.util.camera;

import nu.koidelab.cosmo.util.camera.cameranode.NaturalMotionCameraNode;

import org.jdesktop.lg3d.wg.Container3D;

/**
 * @author seele, fumi_ss
 *
 */
public class CameraUniverse extends Container3D{
    private Container3D root;
    private Container3D orientedTop;
    
    /* ** initial tree structure ** */
    // parent root -> this
    //             |-> orientedTop 
    public CameraUniverse(){
        orientedTop=new Container3D();
        root=new Container3D();
        root.addChild(this);
        root.addChild(orientedTop);            
    }
        
    public void setParent(Container3D parent){
        root.detach();
        parent.addChild(root);
    }
    
    public Container3D getOrientedContainer(){
        return orientedTop;
    }
    
    public void setCamera(NaturalMotionCameraNode cam){
        //      root -> camera -> this
        //           |-> orientedTop 
        this.detach();
        root.addChild(cam);
        cam.addChild(this);
    }    
}
