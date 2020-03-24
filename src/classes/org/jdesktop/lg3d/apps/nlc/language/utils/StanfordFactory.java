/**
 * This code was written during my participation in Google's Summer
 * of Code 2005. Thanks to Google for bringing out such a program
 * and there commitment to open-source.
 */
package org.jdesktop.lg3d.apps.nlc.language.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.logging.Logger;

import edu.stanford.nlp.parser.lexparser.LexicalizedParser;

public class StanfordFactory {
	
	private static Logger logger = Logger.getLogger("lg.nlc");
	
	private LexicalizedParser parser;
	
	private final static String DEFAULT_PARSER_FILE = "etc/lg3d/englishPCFG.ser.gz";
	
	private StanfordFactory(String parserFile) throws IOException{
		//String file = copyToTempFile(StanfordFactory.class.getClassLoader().getResourceAsStream(parserFile));
		String file = System.getProperty("lg.etcdir") + "/lg3d/englishPCFG.ser.gz";
		logger.info("Loading parser from " + file); 
		parser = new LexicalizedParser(file);
	}
	
	private static StanfordFactory instance;
	
	public static StanfordFactory getInstance(String parserFile) throws IOException{
		if (parserFile == null) {
			throw new NullPointerException(
					"Argument to method getInstance is null");
		}
		if (instance == null){
			instance = new StanfordFactory(parserFile);
		}
		return instance;
	}
	
	public static StanfordFactory getInstance() throws IOException{
		return getInstance(DEFAULT_PARSER_FILE);
	}
	
	public LexicalizedParser getParser(){
		return parser;
	}
	
	public static void unload(){
		logger.info("Unloading the parser");
		instance = null;
		//FIXME Ask hideya whether we should run gc here ?
	}
}
