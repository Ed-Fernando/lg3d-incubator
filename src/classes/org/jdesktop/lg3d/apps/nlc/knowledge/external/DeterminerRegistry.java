/**
 * This code was written during my participation in Google's Summer
 * of Code 2005. Thanks to Google for bringing out such a program
 * and there commitment to open-source.
 */
package org.jdesktop.lg3d.apps.nlc.knowledge.external;

import java.util.Arrays;

import org.jdesktop.lg3d.apps.nlc.filters.AllFilter;
import org.jdesktop.lg3d.apps.nlc.filters.DeterminerFilter;
import org.jdesktop.lg3d.apps.nlc.filters.Filter;
import org.jdesktop.lg3d.apps.nlc.filters.FocusFilter;
import org.jdesktop.lg3d.apps.nlc.filters.TheFilter;
import org.jdesktop.lg3d.apps.nlc.knowledge.SceneFactory;


/**
 * Determiner registry delivers appropriate Determinerfilter 
 * for a determiner. Currently this is hard coded, but later we 
 * will support defining them in a config file, for multilingual 
 * support. 
 * 
 * @author harsh
 */
public class DeterminerRegistry {
	
	private static DeterminerRegistry registry;
	
	public static DeterminerRegistry getInstance(){
		if (registry == null)
			registry = new DeterminerRegistry();
		return registry;
	}
	
	private DeterminerRegistry(){
		//TODO load data from a config file
	}
	
	private final String[] focus = new String[]{
			"this"
	};
	
	private final String[] the = new String[]{
			"the" , "current"
	};
	
	private final String[] all = new String[]{
			"all", "each", "every"
	};
	
	public Filter getFilter(String determiner){
		if (determiner == null){
			return new TheFilter("the");
		}
		if (Arrays.binarySearch(focus, determiner) != -1){
			return new FocusFilter(SceneFactory.getInstance().getCurrentScene());
		}
		if (Arrays.binarySearch(the, determiner) != -1){
			return new TheFilter(determiner);
		} 
		if (Arrays.binarySearch(all, determiner) != -1){
			return new AllFilter(determiner);
		}
		//default is TheFilter for now
		return new TheFilter("the");
	}
}
