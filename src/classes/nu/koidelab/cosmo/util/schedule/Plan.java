/*
 * ºîÀ®Æü: 2005/07/28
 */
package nu.koidelab.cosmo.util.schedule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Plan {
    private static final int DEFAULT_PRIORITY = 2;
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd/HH:mm");
    
    private String name = "";
    private int priority = DEFAULT_PRIORITY;    
    private long startTime = -1;
    private long time;
    private long endtTime = -1;
    private String category = "";
    private String detail = "";    
    private String reference = "";
    private int weight = 0;    //activity duration
    private String imageFile = ""; // location of Image file for texture-mapping 
    
    public Plan(String name, long msec) {
        this.name = name;
        this.time = msec;
    }
    
    /** Create clone Plan object. */
    public Plan(Plan p) {
        name = new String(p.name);
        priority = p.priority;    
        startTime = p.startTime;
        time = p.time;
        endtTime = p.endtTime;
        category = new String(p.category);
        detail = new String(p.detail);    
        reference = new String(p.reference);
        weight = p.weight;
        imageFile = new String(p.imageFile);
    }
    
    /* deprecated */
    public Plan(String name, long msec, int priority, String category) {
        this(name, msec);
        this.priority = priority;
        this.category = category;
    }
    
    public Plan(String name, int priority, long dateTime) {
        this(name, dateTime);
        this.priority = priority;
    }
    
    // ***************************** Getter *************************************
    public String getName() {
        return name;
    }
    
    public long getTime() {
        return time;
    }    
    
    public String getCategory() {
        return category;
    }
    
    public long getStartTime() {
        return startTime;
    }
    
    public long getEndtTime() {
        return endtTime;
    }
    
    public String getDetail() {
        return detail;
    }
    
    public String getImageFile() {
        return imageFile;
    }
    
    public int getPriority() {
        return priority;
    }
    
    public int getWeight() {
        return weight;
    }
    
    public String getReference() {
        return reference;
    }
    
    // ***************************** Setter *************************************
    public void setName(String name) {
        this.name = name;
    }
    
    public void setTime(long time) {
        this.time = time;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
    
    public void setEndtTime(long endtTime) {
        this.endtTime = endtTime;
    }
    
    public void setDetail(String detail) {
        this.detail = detail;
    }
    
    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }
        
    public void setPriority(int priority) {
        this.priority = priority;
    }
        
    public void setWeight(int weight) {
        this.weight = weight;
    }
    
    public void setReference(String reference) {
        this.reference = reference;
    }
    
    // *************************** Utility Method ***********************************
    public static long stringToMsecValue(String date){        
        try {
            long value =  formatter.parse(date).getTime();
            return value;
        } catch (ParseException e) {
            e.printStackTrace();
        } 
        return -1;
    }
    
    public static String msecValueToString(long msec){        
         String str =  formatter.format(new Date(msec));
         return str;
    }
    
    @Override
    public String toString() {
        return name + " : " + time;
    }
    
    @Override
    public boolean equals(Object obj) {
        return (hashCode() == obj.hashCode());
    }
    
    @Override
    public int hashCode() {        
        return name.hashCode();
    }        
}
