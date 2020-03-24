/*
 * 作成日: 2005/02/24
 */
package nu.koidelab.cosmo.util.bind2d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * @author fumi
 * HolidayList
 * nu.koidelab.cosmo.util.bind2d
 */
public class HolidayList {

    private ArrayList<Holiday> list;
    public HolidayList() {
        list = new ArrayList<Holiday>();
    }
    public void add(Holiday item) {
        list.add(item);
    }
    public Holiday get(int i) {
        return list.get(i);
    }
    
    public boolean isHoliday(Calendar cal){
        int index = binarySearch(cal);
        if(index >= 0)
            return true;
        else
            return false;
    }
    
    public String getHolidayName(Calendar cal){
        int index = binarySearch(cal);
        if(index >= 0)
            return get(index).getName();
        else
            return null;
    }
    
    public int size(){
        return list.size();
    }
    
    public HolidayList getHolidayList(Calendar start, Calendar end){
        int st = toInteger(start);
        int ed= toInteger(end);
        return getHolidayList(st, ed);
    }
	
	private HolidayList getHolidayList(int start, int end){
        HolidayList tmpList = new HolidayList();               
        int target = 0;
        for(int i = 0, n = size(); i < n; i++){
            target = toInteger(get(i).getCalendar());
            if( (start <= target) && (target <= end) ){
                tmpList.add(get(i));
            }
        }
        return tmpList;
    }
    
    private int binarySearch(Calendar cal){
      Holiday target = new Holiday((GregorianCalendar)cal);
      sort();
      Object[] tmp = list.toArray();
      Arrays.sort(tmp);
      return Arrays.binarySearch(tmp, (Object) target);
    }
    
    private void sort() {
        Object[] tmp = list.toArray();
        Arrays.sort(tmp);
        list = new ArrayList<Holiday>();
        for (int i = 0; i < tmp.length; i++) {
            list.add((Holiday)tmp[i]);
        }
    }
    
    private int toInteger(Calendar cal){
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        return year*10000 + month*100 + dayOfMonth; 
    }
    
    public static void main(String[] args) {
        HolidayList list = new HolidayList();
        /*list.add(new Holiday("成人の日", "2005/01/10"));
        list.add(new Holiday("敬老の日", "2005/09/19"));
//        System.out.println(list.getHolidayName(new GregorianCalendar(2005, 8,19)));
        CosmoXMLParser.saveHolidays(list, "holidays.xml");*/
        list = CosmoXMLParser.loadHolidays("holidays.xml");
        for(int i = 0; i<list.size(); i++){
            System.out.println( list.get(i) );
        }
    }
}
