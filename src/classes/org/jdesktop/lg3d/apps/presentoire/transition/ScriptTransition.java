package org.jdesktop.lg3d.apps.presentoire.transition;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.jdesktop.lg3d.wg.Component3D;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * Implements a script transition.
 * A script transition can be written using JavaScript or any other script language
 * known by the JVM...
 * @author Pierre Ducroquet
 *
 */
public class ScriptTransition extends Transition {
	private String id, language, scriptCode;
	private static final ScriptEngineManager ser = new ScriptEngineManager();
	private ScriptEngine se;
	private Invocable script;
	
	/**
	 * Parse the XML node to initialize the Script engine...
	 * @param node the XML node to parse.
	 */
	public ScriptTransition (Node node) {
		NamedNodeMap attrs = node.getAttributes();
		id = attrs.getNamedItem("id").getTextContent();
		language = attrs.getNamedItem("language").getTextContent();
		se = ser.getEngineByName(language);
		if (se == null)
			throw new RuntimeException("The language " + language + " is invalid");
		scriptCode = node.getTextContent();
		try {
			se.eval(scriptCode);
			script = (Invocable) se;
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * @return the transition ID.
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Effectively run the transition.
	 * @param mainComponent the main Presentoire component3D
	 * @param slideContainer the container for the slide component3D
	 * @param nextSlide the next slide component3D
	 */
	public void runTransition(Component3D mainComponent, Component3D slideContainer, Component3D nextSlide) {
		try {
			script.invokeFunction("before", mainComponent, slideContainer, nextSlide);
			slideContainer.removeAllChildren();
			script.invokeFunction("after", mainComponent, slideContainer, nextSlide);
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
