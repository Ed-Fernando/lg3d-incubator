package nu.koidelab.cosmo.util.function.decoration;

/**
 * @author fumi_ss
 */
public class DayOrbitDecoration extends OrbitDecoration{
    protected int dayOfWeek;
    
    public DayOrbitDecoration(long st, long ed, int dayOfWeek){
        super(st, ed);
        this.dayOfWeek = dayOfWeek;
    }
    
    /**
     * Return this day of week in int value.
     * See static variables of java.util.Calendar, such as Calendar.MONDAY, Calendar.SUNDAY. 
     * @see java.util.Calendar
     * @return dayOfWeek
     */
    public int getDayOfWeek(){
        return dayOfWeek;
    }
}
