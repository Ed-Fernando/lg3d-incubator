package nu.koidelab.cosmo.util.camera;

import nu.koidelab.cosmo.util.camera.cameranode.CameraNodeBase;
import nu.koidelab.cosmo.wg.shape.CameraBody;
import nu.koidelab.cosmo.wg.shape.GridPanel;

import org.jdesktop.lg3d.sg.Shape3D;
import org.jdesktop.lg3d.utils.c3danimation.NaturalMotionAnimation;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.wg.Cursor3D;

public abstract class CameraControllerBase extends Container3D implements CameraControllerInterface{
    protected CameraNodeBase cameraNode;
    protected MinitureCameraNode minitureCam;
    private float orgLocX, orgLocY, orgLocZ;
    private CameraModel cameraModel;

    public CameraControllerBase() {
        cameraModel = new CameraModel();
        addChild(cameraModel);
        addChild(new Floor());
        setAnimation(new NaturalMotionAnimation(1000));
        setRotationAxis(1, 0, 0);
        setVisible(false);
        cameraModel.init();
    }

    public void setYaw(float radian) {
        if(cameraNode != null)
            cameraNode.setYaw(radian);
        minitureCam.setYaw(radian);
    }
    
    public void setPOR(float x, float y, float z) {
        if(cameraNode != null)
            cameraNode.setPointOfRegard(x, y, z);
    }
    
    public void setOffset(float x, float y, float z){
        if(cameraNode != null)
            cameraNode.setOffset(x, y, z);
    }
    
    public void setAngle(float radian) {
        if(cameraNode != null)
            cameraNode.setAngle(radian);
        minitureCam.setAngle(radian);
    }
    
    public void setDistance(float dist) {
        if(cameraNode != null)
            cameraNode.setDistance(dist);
        minitureCam.setDistance(dist);
    }
    
    public void resetDefaultValues() {
        if(cameraNode != null)
            cameraNode.resetDefaultValues();
        minitureCam.resetDefaultValues();
    }
    
    public void resetDefaultPosition() {
        if(cameraNode != null)
            cameraNode.resetDefaultPosition();
    }
    
    

    public void setCenter(boolean toCenter) {
        if (toCenter) {
            changeTranslation(0f, 0f, 0f, 500);
            changeRotationAngle((float) Math.toRadians(25), 500);
            changeVisible(true, 500);
        } else {
            changeTranslation(orgLocX, orgLocY, orgLocZ, 500);
            changeRotationAngle(0, 500);
            setDefaultPosition();            
            changeVisible(false, 500);
        }
    }

    public void setTranslation(float x, float y, float z) {
        orgLocX = x;
        orgLocY = y;
        orgLocZ = z;
        super.setTranslation(x, y, z);
    }

    public void setCamera(CameraNodeBase camera) {
        cameraNode = camera; 
    }

    public void setDefaultPosition() {
        minitureCam.resetDefaultValues();
        cameraModel.init();
    }    
                    
       
    private class CameraModel extends Component3D {
        private Container3D cameraBody;
        private boolean initFlag = false;

        private CameraModel() {
            minitureCam = new MinitureCameraNode(0f, 0f, 3f);
            this.addChild(minitureCam);
            cameraBody = new CameraBody();
            this.addChild(cameraBody);
        }
        
        private void init(){
            if(initFlag) return;
            cameraBody.detach();
            minitureCam.addChild(cameraBody);
            initFlag = true;
        }
    }

    private class Floor extends Component3D {
        private Floor() {
            Shape3D shape = new GridPanel(0.003f, 0.003f, 8, 8);
            addChild(shape);
            setTranslation(0f, -0.005f, 0f);
            setCursor(Cursor3D.N_RESIZE_CURSOR);
        }
    }
    
    
}
