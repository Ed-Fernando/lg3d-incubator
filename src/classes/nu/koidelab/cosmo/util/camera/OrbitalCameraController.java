package nu.koidelab.cosmo.util.camera;

import java.awt.event.KeyEvent;

import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import nu.koidelab.cosmo.manager.CSDGeneralManager;
import nu.koidelab.cosmo.util.camera.cameranode.CameraNodeBase;
import nu.koidelab.cosmo.util.camera.cameranode.OrbitalCamera;
import nu.koidelab.cosmo.util.function.OrbitFunction;
import nu.koidelab.cosmo.wg.nodes.Orbiter;

import org.jdesktop.lg3d.wg.event.KeyEvent3D;
import org.jdesktop.lg3d.wg.event.LgEvent;
import org.jdesktop.lg3d.wg.event.LgEventConnector;
import org.jdesktop.lg3d.wg.event.LgEventListener;
import org.jdesktop.lg3d.wg.event.LgEventSource;
import org.jdesktop.lg3d.wg.event.MouseButtonEvent3D;
import org.jdesktop.lg3d.wg.event.MouseEvent3D;
import org.jdesktop.lg3d.wg.event.MouseWheelEvent3D;

public class OrbitalCameraController extends CameraControllerBase {
    private MouseMoveListener listner;
    private boolean isFocused = false;

    public OrbitalCameraController() {
        /* TODO change the KeyListener enable only when CSD is Focused. */
        LgEventConnector.getLgEventConnector().addListener(ALL_SOURCES, new KeyListener());
        listner = new MouseMoveListener();
        setPickable(false);
    }

    @Override
    public void setCamera(CameraNodeBase camera) {
    	System.err.println(camera);
        if (camera instanceof OrbitalCamera)
            super.setCamera(camera);
        else
            throw new IllegalArgumentException(
                    "An argument should be instance of the OrbitalCamera.");
    }

    @Override
    public void setCenter(boolean toCenter) {
        if (toCenter) {
            syncronizeMiniture();
            LgEventConnector.getLgEventConnector().addListener(
                    LgEventSource.ALL_SOURCES, listner);
        } else {
            listner.reset();        	
            LgEventConnector.getLgEventConnector().removeListener(
                    LgEventSource.ALL_SOURCES, listner);
        }
        super.setCenter(toCenter);
    }

    public void movePOR(boolean toFuture) {
        OrbitalCamera oc = (OrbitalCamera) cameraNode;
        oc.movePOR(toFuture);

        CSDGeneralManager.getInstance().getFunction().resetCamera(oc);
        syncronizeMiniture();        
        
        /* When camera move,  repaint planets if needed. */
        CSDGeneralManager.getInstance().postEvent(new CSDGeneralManager.RepaintPlanetsEvent(oc.getCurrentPosition(), true));
    }

    
    public void updateCameraPosition(OrbitFunction func){
        func.resetCamera( (OrbitalCamera)cameraNode );
        syncronizeMiniture();
    }

    
    public void setCameraPosition(long msec, OrbitFunction func){
    	 OrbitalCamera oc = (OrbitalCamera) cameraNode;
        oc.setCurrentPOR(msec);
        updateCameraPosition(func);
    }
    
    public long getCameraPosition(){
    	 OrbitalCamera oc = (OrbitalCamera) cameraNode;
    	 return oc.getCurrentPosition();
    }
    
    public void initializeCamera(OrbitFunction func){
        OrbitalCamera oc = (OrbitalCamera) cameraNode;
        func.initializeCamera(oc);
        syncronizeMiniture();
    }
    
    public void focusTo(Orbiter target){
    	if(isFocused) throw new IllegalStateException("multiple focusing.");
    	isFocused = true;
        Vector3f location = new Vector3f(); 
        target.getFinalTranslation(location);
        location.scale(-1);
        setPOR(location.x, location.y, location.z);
        setOffset(0, 0, 0);
        setDistance(2.3f);
        if(location.x < -0.2f) setYaw((float)Math.toRadians(45));
        else if(location.x > 0.2f) setYaw((float)Math.toRadians(-45));
        else setYaw(0);
        setAngle(0);
    }
    
    public void releaseFocus(){
    	if(!isFocused) throw new IllegalStateException();
    	isFocused = false;
    	resetDefaultValues();
    }

    /* Syncronize MinitureCameraNode with this camera-node. */
    private void syncronizeMiniture(){
        OrbitalCamera oc = (OrbitalCamera) cameraNode;
        minitureCam.setAngle(oc.getFinalAngle());
        minitureCam.setYaw(oc.getFinalYaw());
        minitureCam.setDistance(oc.getFinalDistance());
        
        /* When camera move,  repaint planets if needed. */
        CSDGeneralManager.getInstance().postEvent(new CSDGeneralManager.RepaintPlanetsEvent(oc.getCurrentPosition(), true));
    }
    
    
    
    
    
    
    private class MouseMoveListener implements LgEventListener {
        private Class<LgEvent>[] target = new Class[] { MouseEvent3D.class,
                MouseButtonEvent3D.class, MouseWheelEvent3D.class};
        private Point3f initPos = null;
        private Point3f tmpP3f = new Point3f();
        float prevAngle;
        float prevYaw;
        
        private MouseMoveListener() {}

        public Class<LgEvent>[] getTargetEventClasses() {
            return target;
        }

        public void processEvent(LgEvent evt) {
            if (evt instanceof MouseButtonEvent3D) {
                MouseButtonEvent3D mbe = (MouseButtonEvent3D) evt;
                if (mbe.isPressed()) {
                    initPos = mbe.getCursorPosition(tmpP3f);
                    prevAngle = cameraNode.getFinalAngle();
                    prevYaw = cameraNode.getFinalYaw();
                } else if (mbe.isReleased()) {
                	reset();
                }
                return;
                
            } else if (evt instanceof MouseWheelEvent3D) {
            	if(isFocused) return;
                // move camera along with the orbit.
                MouseWheelEvent3D mwe = (MouseWheelEvent3D) evt;
                int clicks = mwe.getWheelRotation();
                movePOR(clicks < 0);
                reset();
                return;
                
            }
            if (initPos == null)
                return;

            assert (evt instanceof MouseEvent3D);            
            MouseEvent3D me = (MouseEvent3D) evt;
            Point3f pos = me.getCursorPosition(tmpP3f);
            pos.sub(initPos);
            setYaw(prevYaw + pos.x * -10);
            setAngle(prevAngle + pos.y * 15);
        }
        
        private void reset(){
        	initPos = null;
        }

        private void resetCameraAngle() {
            initPos = null;
            cameraNode.resetDefaultValues();
            minitureCam.resetDefaultValues();
        }
    }

    private class KeyListener implements LgEventListener {
        Class<LgEvent>[] target = new Class[] { KeyEvent3D.class };
        private boolean isCtrlDown;

        public KeyListener() {}

        public Class<LgEvent>[] getTargetEventClasses() {
            return target;
        }

        public void processEvent(LgEvent evt) {
            KeyEvent3D ke = (KeyEvent3D) evt;
            switch (ke.getKeyCode()) {
            case (KeyEvent.VK_CONTROL):
                if (ke.isPressed() && !isCtrlDown) {
                    isCtrlDown = true;
                    setCenter(true);
                } else if (ke.isReleased() && isCtrlDown) {
                    isCtrlDown = false;
                    setCenter(false);
                }
                break;
            case (KeyEvent.VK_A):
            	if(isFocused) return;
                if(isCtrlDown){
                    CSDGeneralManager.getInstance().setCameraPosition(System.currentTimeMillis());
                    resetDefaultValues();
                }
                break;
            default:
                break;
            }
        }
    }
}
