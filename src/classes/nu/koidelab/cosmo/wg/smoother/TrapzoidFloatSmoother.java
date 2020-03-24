package nu.koidelab.cosmo.wg.smoother;

import org.jdesktop.lg3d.utils.smoother.FloatTransitionSmoother;

/**
 * @author fumi TrapzoidFloatSmoother org.wg
 */
public class TrapzoidFloatSmoother implements FloatTransitionSmoother {
    private float target;
    private float val;
//    private float prevVal;
    private float initValue;
    private float increasePeriod;
    private float decreasePeriod;

    public TrapzoidFloatSmoother() {
        this(0.0f);
    }

    public TrapzoidFloatSmoother(float initValue) {
        this(0.25f, 0.5f, initValue);
    }
    
    public TrapzoidFloatSmoother(float increasePeriod, float decreasePeriod) {
        this(0.25f, 0.5f, 0.0f);
    }
    
    public TrapzoidFloatSmoother(float increasePeriod, float decreasePeriod, float initValue) {
        this.initValue = initValue;

        if((increasePeriod > decreasePeriod) || (increasePeriod <= 0) || (decreasePeriod >1.0f)){
            new IllegalArgumentException("Period argument out of range");
        }
        this.increasePeriod = increasePeriod;
        this.decreasePeriod = decreasePeriod;
    }

    public float getFinalValue() {
        return target;
    }

    public float getLatestValue() {
        return val;
    }

    public void setInternalValue(float value) {
        this.initValue = value;
    }

    public void setTargetValue(float target) {
        this.target = target;
//        System.err.println("setTarget Value :" + cameraNode);
    }
    
    public float getValue(float elapsedTime) {
        if(elapsedTime < increasePeriod){
            val = (target - initValue) /increasePeriod*elapsedTime + initValue;
        } else if( (increasePeriod <= elapsedTime) && (elapsedTime < decreasePeriod) ){
            val = target;
        } else{
            val =  (target -initValue) * ( -1.0f/(1.0f - decreasePeriod)*(elapsedTime - decreasePeriod) + 1 ) + initValue; 
        }

        if(target == 0.1f)
            System.err.println("val : " + val);
        return val;
    }
}
