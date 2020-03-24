/**
 * This code was written during my participation in Google's Summer
 * of Code 2005. Thanks to Google for bringing out such a program
 * and there commitment to open-source.
 */
package org.jdesktop.lg3d.apps.nlc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.jdesktop.lg3d.apps.nlc.actions.Action;
import org.jdesktop.lg3d.apps.nlc.actions.FocusAction;
import org.jdesktop.lg3d.apps.nlc.data.Application;
import org.jdesktop.lg3d.apps.nlc.data.ApplicationConfig;
import org.jdesktop.lg3d.apps.nlc.filters.BadFilter;
import org.jdesktop.lg3d.apps.nlc.filters.Filter;
import org.jdesktop.lg3d.apps.nlc.filters.FocusFilter;
import org.jdesktop.lg3d.apps.nlc.filters.NounPhraseFilter;
import org.jdesktop.lg3d.apps.nlc.knowledge.CurrentScene;
import org.jdesktop.lg3d.apps.nlc.knowledge.external.AppKnowledge;
import org.jdesktop.lg3d.apps.nlc.knowledge.external.CommandRegistry;
import org.jdesktop.lg3d.apps.nlc.language.CommandAnaylizer;
import org.jdesktop.lg3d.apps.nlc.language.stanford.StanfordAnaylyzer;
import org.jdesktop.lg3d.apps.nlc.language.utils.StanfordFactory;
import org.jdesktop.lg3d.apps.nlc.utils.Setup;

import nanoxml.XMLParseException;

public class Nlc {

	private static Logger logger = Logger.getLogger("lg.nlc");

	private CurrentScene scene;

	private AppKnowledge knowledge;

	private String knowledgeFile;

	private Action defaultAction;

	public Nlc(CurrentScene scene, String knowledgeFile) {
		if (scene == null) {
			throw new NullPointerException("Argument to method init is null");
		}
		this.scene = scene;
		this.knowledgeFile = knowledgeFile;
	}

	public void init() throws XMLParseException, IOException {
		logger.info("Initializing NLC");
		// set up event listeners
		Setup.setupEventListeners(scene);
		StanfordFactory.getInstance();
		this.knowledge = new AppKnowledge(knowledgeFile);
		defaultAction = new FocusAction();
	}

	public void takeAction(String command) throws IOException {
		logger.info("Executing command = " + command);
		// NLP Module
		CommandAnaylizer anaylizer = new StanfordAnaylyzer(command);
		// The command is now parsed using StanfordParser and proper
		// base representation is generated..

		// Action can be taken on following applications
		List<Application> apps = getCandidateApplications();
		logger.info("Candidate Applications :-" + apps);

		// Filter out bad applications
		apps = new BadFilter().filter(apps);
		logger.info("Bad Applications removed :-" + apps);

		// if there is no nounphrase we act on the focussed app
		if (anaylizer.getNumNounPhrases() == 0) {
			logger.info("No Noun phrases. Filtering to the focus application");
			apps = new FocusFilter(scene).filter(apps);
		} else {
			// Time to filter things off in reverse direction
			List<Application> napps = new ArrayList<Application>();
			for (int i = anaylizer.getNumNounPhrases() - 1; i >= 0; i--) {
				napps.addAll(new NounPhraseFilter(anaylizer.getNounPhrase(i),
						knowledge).filter(apps));
			}
			apps = napps;
			logger.info("After noun phrase filtering : " + apps);
		}
		
		// Time to filter according to the action
		Filter actionFilter = CommandRegistry.getInstance().getActionFilter(
				anaylizer.getCommand());
		apps = actionFilter.filter(apps);

		logger.info("After action filtering : " + apps);
		// Time to take action
		Action action = CommandRegistry.getInstance().getAction(
				anaylizer.getCommand());
		if (action == null) {
			logger.warning("No action define. Command = "
					+ anaylizer.getCommand().getVerb());
			action = defaultAction;
		}

		// take action for each application
		for (Application app : apps) {
			logger.info("Taking action:" + action + " on application:" + app);
			action.takeAction(app);
		}
	}

	public void close() {
		Setup.removeEventListeners(scene);
		StanfordFactory.unload();
	}

	private List<Application> getCandidateApplications() {
		List<Application> apps = new ArrayList<Application>();
		apps.addAll(scene.getAllApplications());
		List<ApplicationConfig> configs = knowledge.getAllConfigs();
		for (ApplicationConfig config : configs) {
			apps.add(new Application(config));
		}
		return apps;
	}
}
