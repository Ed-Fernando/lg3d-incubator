/*
 * ºîÀ®Æü: 2005/02/26
 */
package nu.koidelab.cosmo.util.bind2d;

import java.util.ArrayList;

/**
 * @author fumi
 * CategoryList
 * nu.koidelab.cosmo.util.bind2d
 */
public class CategoryList {
    private ArrayList<String> list; 
    
    public CategoryList() {
        list = new ArrayList<String>();
    }
        
    public boolean isExist(String aCategory){
        if(aCategory == null)
            return true;
        if(list.size() > 0 ){ 
            for(int i = 0; i < list.size(); i++){
                if(get(i).equals(aCategory)){
                    return true;
                }
            }
        }
        return false;
    }
    
    public void addCategory(String aCategory){        
        list.add(aCategory);
    }

    public Object[] toArray(){
        list.add(0, "--");
        return list.toArray();
    }
    
    public String get(int index){
        return (String)list.get(index);
    }
    
    public int size(){
        return list.size();
    }
}
