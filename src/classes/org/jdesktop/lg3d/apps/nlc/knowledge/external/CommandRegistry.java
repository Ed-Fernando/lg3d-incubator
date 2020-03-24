/**
 * This code was written during my participation in Google's Summer
 * of Code 2005. Thanks to Google for bringing out such a program
 * and there commitment to open-source.
 */
package org.jdesktop.lg3d.apps.nlc.knowledge.external;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdesktop.lg3d.apps.nlc.actions.Action;
import org.jdesktop.lg3d.apps.nlc.actions.CloseAction;
import org.jdesktop.lg3d.apps.nlc.actions.DefaultAction;
import org.jdesktop.lg3d.apps.nlc.actions.FocusAction;
import org.jdesktop.lg3d.apps.nlc.actions.HideAction;
import org.jdesktop.lg3d.apps.nlc.actions.LaunchAction;
import org.jdesktop.lg3d.apps.nlc.actions.ParkLeftAction;
import org.jdesktop.lg3d.apps.nlc.actions.RotateAction;
import org.jdesktop.lg3d.apps.nlc.conditions.VisibleCondition;
import org.jdesktop.lg3d.apps.nlc.filters.ConditionFilter;
import org.jdesktop.lg3d.apps.nlc.filters.DefaultFilter;
import org.jdesktop.lg3d.apps.nlc.filters.Filter;
import org.jdesktop.lg3d.apps.nlc.filters.HiddenFilter;
import org.jdesktop.lg3d.apps.nlc.filters.LaunchFilter;
import org.jdesktop.lg3d.apps.nlc.filters.RunningFilter;
import org.jdesktop.lg3d.apps.nlc.knowledge.CurrentScene;
import org.jdesktop.lg3d.apps.nlc.language.data.Command;

public class CommandRegistry {
	
	private static final String DEFAULT_FILE = "./conf/commands.xml";
	
	private static CommandRegistry registry;
	
	private Map<String, Action> actions;
	
	private Map<String, Filter> filters;
	
	private CurrentScene scene;
	
	public static CommandRegistry getInstance(){
		if (registry == null)
			registry = new CommandRegistry();
		return registry;
	}
	
	private static final String[] open = {
		"open", "launch", "start", "begin"	
	};
	
	private static final String[] hide = {
			"hide"
	};
	
	private static final String[] focus = {
			"focus", "show", "display"
	};
	
	private static final String[] close = {
			"kill", "close", "destroy", "end", "terminate"
	};
	
	private static final String[] rotate = {
			"rotate", "turn"
	};
	private static final String[] park = {
		"park"
	};
	
	private CommandRegistry(){
		this.scene = scene;
		actions = new HashMap<String, Action>();
		for (int i=0;i<open.length;i++){
			actions.put(open[i], new LaunchAction());
		}
		for (int i=0;i<hide.length;i++){
			actions.put(hide[i], new HideAction());
		}
		for (int i=0;i<close.length;i++){
			actions.put(close[i], new CloseAction());
		}
		for (int i=0;i<focus.length;i++){
			actions.put(focus[i], new FocusAction());
		}
		for (int i=0;i<rotate.length;i++){
			actions.put(rotate[i], new RotateAction());
		}
		for (int i=0;i<park.length;i++){
			actions.put(park[i], new ParkLeftAction());
		}
		actions.put(Command.DEFAULT.getVerb(), new DefaultAction());
		
		filters = new HashMap<String, Filter>();
		for (int i=0;i<open.length;i++){
			filters.put(open[i], new LaunchFilter());
		}
		for (int i=0;i<hide.length;i++){
			filters.put(hide[i], new ConditionFilter(new VisibleCondition()));
		}
		for (int i=0;i<close.length;i++){
			filters.put(close[i], new RunningFilter());
		}
		for (int i=0;i<focus.length;i++){
			filters.put(focus[i], new RunningFilter());
		}
		for (int i=0;i<rotate.length;i++){
			filters.put(rotate[i], new ConditionFilter(new VisibleCondition()));
		}
		for (int i=0;i<park.length;i++){
			filters.put(park[i], new ConditionFilter(new VisibleCondition()));
		}
		filters.put(Command.DEFAULT.getVerb(), new DefaultFilter());
	}
	
	public Action getAction(Command command){
		return actions.get(command.getVerb());
	}
	
	public Filter getActionFilter(Command command){
		return filters.get(command.getVerb());
	}
	
	public List<String> getAllCommands(){
		return new ArrayList<String>(actions.keySet());
	}
}
