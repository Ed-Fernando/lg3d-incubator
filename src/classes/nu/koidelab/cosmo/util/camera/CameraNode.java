/*
 * ºîÀ®Æü: 2005/07/01
 */
package nu.koidelab.cosmo.util.camera;

import javax.media.j3d.RestrictedAccessException;
import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.sg.Node;
import org.jdesktop.lg3d.sg.Transform3D;
import org.jdesktop.lg3d.sg.TransformGroup;
import org.jdesktop.lg3d.utils.smoother.NaturalFloatSmoother;
import org.jdesktop.lg3d.wg.Component3D;


/**
 * Use LightweightCameraNode.
 * @deprecated
 * @author fumi_ss
 */
//public class CameraNode extends CameraNodeBase implements Runnable{
public class CameraNode extends Component3D implements Runnable{
    protected TransformGroup tg = new TransformGroup();
    protected float currentAngle;    
    protected float currentYaw;
    protected float currentDistance;    
    protected Vector3f pointOfRegards = new Vector3f();
    private float defaultAngle;
    private float defaultYaw;
    private float defaultDistance;
    private Vector3f defaultPOR;

    private int duration;
    private Thread animator;
    
    protected Transform3D l2v;
    protected final Transform3D v2c;
    protected Transform3D distZ;
    protected Transform3D rotX;
    protected Transform3D rotY;
    protected Transform3D trans;
    protected NaturalFloatSmoother smoother;
    protected Changer angleChanger;
    protected YawChanger yawChanger;
    protected Changer distanceChanger;
    protected V3fChanger porChanger;
    protected Vector3f distZVec;
    
    public CameraNode(){
        this(0f, 0f, 2.41f);
    }
    
    public CameraNode(float angle, float yaw, float distance){
        this(angle, yaw, distance, new Vector3f(0, 0, 0));
    }
    
    public CameraNode(float angle, float yaw, float distance, Vector3f por){
        defaultAngle = angle;
        defaultYaw = yaw;
        defaultDistance = distance;
        defaultPOR = por;
        
        tg.setCapability(TransformGroup.ALLOW_LOCAL_TO_VWORLD_READ);
        tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        tg.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        tg.setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);
        tg.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);
        super.addChild(tg);
        setDuration(1200);
                
        l2v = new Transform3D();
        v2c = new Transform3D();
        v2c.setIdentity();
        v2c.set(new Vector3f(0, 0, 2.41f));
        distZ = new Transform3D();
        rotX = new Transform3D();
        rotY = new Transform3D();
        trans = new Transform3D();
        distZVec = new Vector3f();
        
        smoother = new NaturalFloatSmoother();
        smoother.setTargetValue(1.0f);
        
        angleChanger = new Changer(angle);
        yawChanger = new YawChanger(yaw);
        distanceChanger = new Changer(distance);
        porChanger = new V3fChanger(new Vector3f());        
    }
    
    @Override
    public void addChild(Node child) {
        tg.addChild(child);
    }
    
    public void setInitial(float angle, float yaw, float distance, Vector3f por){
        currentAngle = angle;
        currentYaw = yaw;
        currentDistance = distance;
        pointOfRegards.set(por);
        set();
        angleChanger = new Changer(angle);
        yawChanger = new YawChanger(yaw);
        distanceChanger = new Changer(distance);
        porChanger = new V3fChanger(por);        
    }
        
    public void setDuration(int d){
        duration = d;
    }

    public void setYaw(float radian){
        if(radian != yawChanger.getTargetValue()){
            yawChanger.setTargetValue(radian);
            startAnimation();            
        }
    }
    
    public void setAngle(float radian){
        if(radian != angleChanger.getTargetValue()){
            if(radian < 1.57079f && radian > -1.57079f){
                angleChanger.setTargetValue(radian);
                startAnimation();
            }
        }
    }
       
    public void setDistance(float dist){
        if(dist != distanceChanger.getTargetValue()){
            distanceChanger.setTargetValue(dist);
            startAnimation();            
        }
    }
    
    public void setDefaultYaw(float radian){
        defaultYaw = radian;
    }
    
    public void setDefaultAngle(float radian){
        defaultAngle = radian;
    }
    
    public void setDefaultDistance(float dist) {
        defaultDistance = dist;
    }
        
    public void setPointOfRegard(Vector3f vec){
        if(vec != porChanger.getTargetV3f()){
            porChanger.setTargetV3f(vec);
            startAnimation();            
        }
    }
    
    public float getAngle(){
        return currentAngle;
    }
        
    public float getYaw(){
//        return currentYaw;
        return yawChanger.getYaw();
    }
    
    public float getDistance(){
        return currentDistance;
    }
    
    public Vector3f getPointOfRegard(){
        return new Vector3f(pointOfRegards);
    }
    
    public float getFinalAngle(){
        return angleChanger.getTargetValue();
    }
    
    public float getFinalYaw(){
        return yawChanger.getTargetValue();
    }
    
    public float getFinalDistance(){
        return distanceChanger.getTargetValue();
    }
    
    public Vector3f getFinalPOR(){
        return new Vector3f(porChanger.getTargetV3f());
    }

    public float getDefaultAngle() {
        return defaultAngle;
    }
    
    public float getDefaultYaw() {
        return defaultYaw;
    }
    
    public float getDefaultDistance() {
        return defaultDistance;
    }
        
    public void resetDefaultValues(){
        setAngle(defaultAngle);
        setYaw(defaultYaw);
        setDistance(defaultDistance);
    }
    
    public void resetDefaultPosition(){
        setPointOfRegard(defaultPOR);
    }

    /*
    public boolean isAnimating(){
        return (animator != null);
    }
    */
    
    protected void startAnimation(){
        if(animator==null){
            animator = new Thread(this);
            animator.setPriority(7);
            animator.start();
        }
        if(!animator.isAlive()){
            animator = new Thread(this);
            animator.setPriority(7);
            animator.start();
        }
    }
    
    protected void set(){
        l2v.setIdentity();
        try{
            getLocalToVworld(l2v);
        } catch(RestrictedAccessException e){
            /* For testing. This method should be needed. */
//            e.printStackTrace();
            return;
        }
                
        distZ.setIdentity();
        distZVec.set(0, 0, -currentDistance);
        distZ.set(distZVec);        

        rotX.setIdentity();
        rotX.rotX(currentAngle);
        rotY.setIdentity();
        rotY.rotY(currentYaw);                        
        
         trans.setIdentity();
         trans.set(pointOfRegards);
         
         l2v.mul(v2c);
         l2v.mul(distZ);
         l2v.mul(rotX);
         l2v.mul(rotY);
         l2v.mul(trans);
         tg.setTransform(l2v);       
    }
    
    public void run(){
        long now;
        long last;
        last = now = System.currentTimeMillis();
        while(angleChanger.isRunning() || yawChanger.isRunning()
                || distanceChanger.isRunning() || porChanger.isRunning()) {
            now = System.currentTimeMillis();
            if( (now - last) > 16){
                /* TODO frame rate 1/60 ? is this needed??? */
                currentAngle = angleChanger.getValue(now);
                currentYaw = yawChanger.getValue(now);
                currentDistance = distanceChanger.getValue(now);
                pointOfRegards.set(porChanger.getV3f(now));
                set();
                last = now;
            } else {
                try{
                    Thread.sleep(10);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
        animator = null;
    }
    
    protected class Changer{
      protected float value;
      protected float lastValue;
      protected float currentValue;
      protected long startTime;
      protected boolean running = false;
      
      private Changer(float initValue){          
          value =  0;
          lastValue = currentValue = initValue;          
      }
      
      protected float getValue(long currentTime){
          if(!running) return currentValue;
          float elapsedDuration = (float)(currentTime - startTime) / (float)duration;
          if(elapsedDuration >= 1.0f){
              currentValue = value;
              running = false;
          } else {
              currentValue = lastValue + (value - lastValue)*smoother.getValue(elapsedDuration);
          }
          return currentValue;
      }
      
      protected void setTargetValue(float target){          
          this.value = target;
          this.lastValue = currentValue;
          startTime = System.currentTimeMillis();
          running = true;
      }
      
      protected float getTargetValue(){
          return value;
      }
      
      protected boolean isRunning(){
          return running;
      }
    }
    
    protected class YawChanger extends Changer{
        private YawChanger(float initValue){
            super(initValue);
        }
                
        protected float getValue(long currentTime){
            if(!running) return currentValue;
            float elapsedDuration = (float)(currentTime - startTime) / (float)duration;
            if(elapsedDuration >= 1.0f){
                currentValue = value;                
                running = false;
                return currentValue;
            } else {                                
                currentValue = lastValue + (value - lastValue)*smoother.getValue(elapsedDuration);
                return testAngle(limitAngle(value), limitAngle(lastValue), elapsedDuration);
            }
        }
        
        private float getYaw(){            
            return currentValue;
        }

        private float testAngle(float value, float last, float elapsedDuration){
            float ans;
            if(last > value){
               if(last - Math.PI > value){
                   ans = last + ( (value + 2*(float)Math.PI) - last)*smoother.getValue(elapsedDuration);
               } else {
                   ans = last + (value - last)*smoother.getValue(elapsedDuration);
               }                
            } else {
                if(value > last + Math.PI){
                    ans = last + ( (value - 2*(float)Math.PI) - last)*smoother.getValue(elapsedDuration);
                } else {
                    ans = last + (value - last)*smoother.getValue(elapsedDuration);
                }
            }            
            return ans;
        }
        
        private float limitAngle(float angle){
           while(angle <= -Math.PI || Math.PI <= angle){
                   if(angle < -Math.PI)
                        angle += 2*Math.PI;
                    else 
                        angle -= 2*Math.PI;
           }
            return angle;
        }
      }
    
    protected class V3fChanger{
        private Vector3f v3f = new Vector3f();
        private Vector3f lastV3f = new Vector3f();
        private Vector3f currentV3f = new Vector3f();
        private Vector3f temp = new Vector3f();
        private long startTime;
        private boolean running = false;
        
        private V3fChanger(Vector3f initV3f){          
            lastV3f.set(initV3f);
           currentV3f.set(initV3f);          
        }
        
        private Vector3f getV3f(long currentTime){
            if(!running) return currentV3f;
            float elapsedDuration = (float)(currentTime - startTime) / (float)duration;
            if(elapsedDuration >= 1.0f){
                currentV3f.set(v3f);
                running = false;
            } else {
                temp.sub(v3f, lastV3f);
                temp.scale(smoother.getValue(elapsedDuration));
                currentV3f.add(lastV3f, temp);
            }
            return currentV3f;
        }
        
        private void setTargetV3f(Vector3f target){         
            v3f.set(target);
            lastV3f.set(currentV3f);
            startTime = System.currentTimeMillis();
            running = true;
        }
        
        private Vector3f getTargetV3f(){
            return v3f;
        }
        
        private boolean isRunning(){
            return running;
        }
      }
}