package nu.koidelab.cosmo.util.function;

import javax.vecmath.Vector3f;

import nu.koidelab.cosmo.util.camera.cameranode.OrbitalCamera;
import nu.koidelab.cosmo.util.function.decoration.OrbitDecoration;
import nu.koidelab.cosmo.wg.nodes.Orbiter;

/**
 * @author fumi_ss
 */
public interface OrbitFunction {
    public int getMode();
    public float[] getPosition(long msec);
    public void getPosition(long msec, Vector3f vec);
    public float[] getPositionTangentVector(long msec);
    public void setMode(int mode);
    public void setPosition(Orbiter orbiter);
    public void setDecoration(OrbitDecoration deco);
    public void initializeCamera(OrbitalCamera camera);
    public void resetCamera(OrbitalCamera camera);
}
