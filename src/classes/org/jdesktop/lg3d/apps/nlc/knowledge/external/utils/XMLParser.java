/**
 * This code was written during my participation in Google's Summer
 * of Code 2005. Thanks to Google for bringing out such a program
 * and there commitment to open-source.
 */
package org.jdesktop.lg3d.apps.nlc.knowledge.external.utils;


import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;
import java.util.logging.Logger;

import org.jdesktop.lg3d.apps.nlc.data.ApplicationConfig;

import nanoxml.XMLElement;
import nanoxml.XMLParseException;

public class XMLParser {

	private static Logger logger = Logger.getLogger("lg.nlc");
	
	private InputStream knowledgeFile;

	private Map<String, Entity> entities;

	private Map<ApplicationConfig, String> appConfigMap;

	private Map<ApplicationConfig, List<String>> specialSynonyms;
	
	private class Entity {
		Entity() {
			synonyms = new ArrayList<String>();
		}

		String name;

		String parent;

		List<String> synonyms;
	}

	public XMLParser(String file) {
		if (file == null) {
			throw new NullPointerException(
					"Argument to method XMLParser is null");
		}
		knowledgeFile = getClass().getResourceAsStream(file);
	}

	private List<String> getFiles() throws XMLParseException, IOException {
		XMLElement xml = new XMLElement();
		xml.parseFromReader(new InputStreamReader(knowledgeFile));
		if (!xml.getName().equals("root"))
			throw new IllegalArgumentException("Illegal file");
		Vector<XMLElement> children = xml.getChildren();
		if (children.size() != 1)
			throw new IllegalArgumentException("Illegal file");
		XMLElement files = children.get(0);
		Vector<XMLElement> fileNames = files.getChildren();
		List<String> names = new ArrayList<String>();
		for (int i = 0; i < fileNames.size(); i++) {
			String fileName = fileNames.get(i).getContent();
			names.add(fileName);
		}
		return names;
	}

	private void extractEntities(XMLElement element) {
		if (element.getName().equals("entities")) {
			for (XMLElement entity : (Vector<XMLElement>) element.getChildren()) {
				Entity e = new Entity();
				for (XMLElement child : (Vector<XMLElement>) entity
						.getChildren()) {
					if (child.getName().equals("name")) {
						e.name = child.getContent();
					} else if (child.getName().equals("parent")) {
						e.parent = child.getContent();
					} else if (child.getName().equals("synonyms")) {
						for (XMLElement synonym : (Vector<XMLElement>) child
								.getChildren()) {
							e.synonyms.add(synonym.getContent());
						}
					}
				}
				entities.put(e.name, e);
			}
		}
	}

	private XMLElement getXMLElement(String file) throws XMLParseException,
			IOException {
		XMLElement xml = new XMLElement();
		
		InputStream in = getClass().getResourceAsStream(file);
		logger.info("Parsing file " + file + " " + getClass().getResource(file).getPath());
		xml.parseFromReader(new InputStreamReader(in));
		if (!xml.getName().equals("root")) {
			throw new IllegalArgumentException("Illegal File");
		}
		return xml;
	}

	private void extractEntities(String file) throws XMLParseException,
			IOException {
		XMLElement element = getXMLElement(file);
		for (XMLElement child : (Vector<XMLElement>) element.getChildren()) {
			extractEntities(child);
		}
	}

	private void processApplications(XMLElement element) {
		if (element.getName().equals("applications")) {
			for (XMLElement app : (Vector<XMLElement>) element.getChildren()) {
				ApplicationConfig config = new ApplicationConfig();
				String entity = null;
				List<String> syns = new ArrayList<String>();
				for (XMLElement child : (Vector<XMLElement>) app.getChildren()) {
					if (child.getName().equals("name")) {
						config.setName(child.getContent());
					} else if (child.getName().equals("command")) {
						config.setCommand(child.getContent());
					} else if (child.getName().equals("entity")) {
						entity = child.getContent();
					} else if (child.getName().equals("synonyms")){
						for (XMLElement synonym : (Vector<XMLElement>) child
								.getChildren()) {
							syns.add(synonym.getContent());
						}
					}
				}
				appConfigMap.put(config, entity);
				specialSynonyms.put(config, syns);
			}
		}
	}

	private void processApplications(String file) throws XMLParseException,
			IOException {
		XMLElement element = getXMLElement(file);
		for (XMLElement child : (Vector<XMLElement>) element.getChildren()) {
			processApplications(child);
		}
	}

	public void parse() throws XMLParseException, IOException {
		entities = new HashMap<String, Entity>();
		appConfigMap = new HashMap<ApplicationConfig, String>();
		specialSynonyms = new HashMap<ApplicationConfig, List<String>>();
		List<String> files = getFiles();
		for (String file : files) {
			extractEntities(file);
		}
		// once all entities are processed we process all the files as well
		for (String file : files) {
			processApplications(file);
		}
	}

	public Set<String> getSynonyms(ApplicationConfig config) {
		Set<String>  entitySyns = getSynonyms(appConfigMap.get(config));
		List<String> special = specialSynonyms.get(config);
		if (special != null)
			entitySyns.addAll(special);
		return entitySyns;
	}

	/**
	 * A recursive function to find all synonyms of entity. Currently flatten
	 * without actually accounting for hypernyms.
	 * 
	 * @param entity
	 * @return
	 */
	private Set<String> getSynonyms(String entity) {
		Entity e = entities.get(entity);
		Set<String> syns = new TreeSet<String>(e.synonyms);
		if (e.parent == null)
			return syns;
		syns.addAll(getSynonyms(e.parent));
		return syns;
	}

	public List<ApplicationConfig> getAllAppConfigs() {
		return new ArrayList<ApplicationConfig>(appConfigMap.keySet());
	}
}
