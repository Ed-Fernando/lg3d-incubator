/**
 * This code was written during my participation in Google's Summer
 * of Code 2005. Thanks to Google for bringing out such a program
 * and there commitment to open-source.
 */
package org.jdesktop.lg3d.apps.nlc.knowledge.external;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.jdesktop.lg3d.apps.nlc.data.ApplicationConfig;
import org.jdesktop.lg3d.apps.nlc.knowledge.external.utils.XMLParser;

import nanoxml.XMLParseException;

public class AppKnowledge {
	
	private static Logger logger = Logger.getLogger("lg.nlc");

	private Map<ApplicationConfig, List<String>> knowledge;

	private List<ApplicationConfig> appList;

	public AppKnowledge(String confFile) throws XMLParseException, IOException {
		logger.info("Loading the external knowledge from " + confFile);
		XMLParser parser = new XMLParser(confFile);
		parser.parse();
		appList = parser.getAllAppConfigs();
		knowledge = new HashMap<ApplicationConfig, List<String>>();
		for (ApplicationConfig config : appList) {
			knowledge.put(config, new ArrayList<String>(parser
					.getSynonyms(config)));
		}
	}

	/**
	 * 
	 * @return
	 */
	public List<ApplicationConfig> getAllConfigs() {
		return Collections.unmodifiableList(appList);
	}

	public List<String> getSynonyms(ApplicationConfig appConfig) {
		List<String> syns = knowledge.get(appConfig);
		if (syns == null)
			syns = knowledge.get(ApplicationConfig.DEFAULT);
		return Collections.unmodifiableList(syns);
	}

}
