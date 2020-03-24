package nu.koidelab.cosmo.util.function.decoration;

/**
 * @author fumi_ss
 */
public class MonthOrbitDecoration extends OrbitDecoration{
    protected float[] monthColor;
    
    public MonthOrbitDecoration(long st, long ed, float[] monthColor){
        super(st, ed);
        this.monthColor = monthColor;        
    }
    
    /**
     * Return Month Color as float array.
     * @return monthcolor
     */
    public float[] getMonthColor() {
        return monthColor;
    }    

}
