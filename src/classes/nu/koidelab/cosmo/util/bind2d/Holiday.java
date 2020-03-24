/*
 * 作成日: 2005/02/24
 */
package nu.koidelab.cosmo.util.bind2d;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

/**
 * @author fumi
 * Holiday
 * nu.koidelab.cosmo.util.bind2d
 */
public class Holiday implements Comparable{    

    private String name;
    private int year;
    private int month;
    private int day;
    public static final String HOLIDAY_DATE_FORMAT = "yyyy/MM/dd";
    
    public Holiday(String name, String date) {
        this.name = name;
        SimpleDateFormat formatter = new SimpleDateFormat(HOLIDAY_DATE_FORMAT);
        GregorianCalendar tmpCal = new GregorianCalendar();
        try{
            tmpCal.setTime(formatter.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        year = tmpCal.get(GregorianCalendar.YEAR);
        month = tmpCal.get(GregorianCalendar.MONTH) + 1;
        day = tmpCal.get(GregorianCalendar.DAY_OF_MONTH);
    }
    
    Holiday(GregorianCalendar cal){
        year = cal.get(GregorianCalendar.YEAR);
        month = cal.get(GregorianCalendar.MONTH) + 1;
        day = cal.get(GregorianCalendar.DAY_OF_MONTH);        
    }
    
    public String toString() {
        return name + " -- " + year + "/" + month + "/" + day;
    }    
    
    public GregorianCalendar getCalendar(){
        return new GregorianCalendar(year, month-1, day, 12, 0, 0);
    }

    public String getName() {
        return name;
    }
    
    public String getDate() {
        return year + "/" + month + "/" + day;
    }
    
    public int compareTo(Object arg0) {
        Long argCal = ((Holiday)arg0).getCalendar().getTimeInMillis();
        Long thisCal = getCalendar().getTimeInMillis();
        return thisCal.compareTo(argCal);        
    }
    
    public static void main(String[] args) {
        Holiday holiday = new Holiday("前田の日", "1981/05/29");
        System.out.println(holiday);
    }
}
