package nu.koidelab.cosmo.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

public class PropertyManager {
	static final String DEMO_SCHEDULE_FILE = "resources/cosmo/xml/demoSchedule.xml";
	private static final String CSD_DIR = "/csd/";
	private static final String PROPERTY_FILE_NAME = "csd.properties";
	private static final String SCHEDULE_FILE_NAME = "schedule.xml";
	private HashMap<String, String> propertyMap;
    private String[] categories;
	private String userHome;
	private String themePath;
	
	PropertyManager() {
		Properties props = System.getProperties();
		userHome = props.getProperty("user.home");
		propertyMap = new HashMap<String, String>();
	}
	
	boolean initialize(){
		boolean flag = false;
		flag = checkReadProperties();
		if(flag)
			initializeProperties();
		return flag;
	}
	
	private void initializeProperties(){
	    Properties properties;
		try {
			FileInputStream in = new FileInputStream(new File(getProperty("PropertyFileName")));
			properties = new Properties();
			properties.load(in);
			
			/* ========== Load category data ========= */
			String ctg = properties.getProperty("Categories");
			if (ctg != null)
				categories = ctg.split(",");
			else
				categories = new String[0];

			/* ========== Load theme data ========= */
			themePath = null;
			setFileProperty("ThemePath", properties.getProperty("ThemePath"), "resources/cosmo/images/default/");

			/* ========= Load location of FileAssociation config file. ========= */		
			File tmpFile = new File(userHome + CSD_DIR + "FileAssociation.conf");
			if(tmpFile.canRead()){
				System.out.println("Using user FileAssociation : " + tmpFile.getAbsolutePath());
				propertyMap.put("UserFileAssociation", tmpFile.getAbsolutePath());		
			} 
		} catch(FileNotFoundException fnfe){
			fnfe.printStackTrace();
		} catch(IOException ioe){
			ioe.printStackTrace();
		}
	}
	
	public String getProperty(String key){
		return propertyMap.get(key);
	}

	boolean checkReadProperties(){
		return checkReadFile(userHome + CSD_DIR + PROPERTY_FILE_NAME, "PropertyFileName");
	}
	
	boolean checkReadScheduleFile(){
		return checkReadFile(userHome + CSD_DIR + SCHEDULE_FILE_NAME, "ScheduleFileName");
	}
	
	private boolean checkReadFile(String path, String key){
		File file = new File(path);
		if(file.canRead()){
			propertyMap.put(key, file.getAbsolutePath());
			return true;
		} else {
			return false;
		}
	}
	
	boolean createPropertyFile(){
		copyFileFromSample(userHome + CSD_DIR + "FileAssociation.conf", "resources/cosmo/sample/sampleFileAssociation.conf", "UserFileAssociation");		
		return copyFileFromSample(userHome + CSD_DIR + PROPERTY_FILE_NAME, "resources/cosmo/sample/sampleCsd.properties", "PropertyFileName");
	}
	
	boolean createScheduleFile(){
		return copyFileFromSample(userHome + CSD_DIR + SCHEDULE_FILE_NAME, "resources/cosmo/sample/sampleSchedule.xml", "ScheduleFileName");
	}
	
	
	private boolean copyFileFromSample(String path, String sampleFilename, String key) {
		boolean flag;
		
		File csdDir = new File(userHome + CSD_DIR);
		flag = (csdDir.isDirectory()) ? true : csdDir.mkdir();
		if(!flag) return false;
		copyFileToFile(sampleFilename, path);
		propertyMap.put(key, path);		
		return true;
	}
	
	private void copyFileToFile(String inFilename, String outFilename){
		try {
			InputStream in = getClass().getClassLoader().getResourceAsStream(inFilename);
			FileWriter out = new FileWriter(new File(outFilename), false);
			int c;
			while ((c = in.read()) != -1) 
				out.write(c);
			in.close();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	void setDemonstrationMode() {
		System.out.println();
		System.out.println("     Starting Cosmo Scheduler D - demonstration mode.     ");
		System.out.println();
		categories = new String[2];
		categories[0] = "Java";
		categories[1] = "Business";
		propertyMap.put("ScheduleFileName", DEMO_SCHEDULE_FILE);
		propertyMap.put("ThemePath", "resources/cosmo/images/default/");
	}
	
	public String[] getCategories() {
		return categories;
	}
	
	private void setFileProperty(String key, String path, String defaultValue){
		if(path == null)return;
		String item = path;
		if(item == null){
			item = defaultValue;
		} else {
			File f = new File(item);
			if(!f.canRead()){
				item = defaultValue;
				System.err.println("(EE) : Property " + key + " : " + f.getAbsolutePath() + " is not found.");
				System.err.println("     : Default setting is used.");
			}						
		}
		propertyMap.put(key, item);
	}		
	
	
	
	
	
	/* Not used */
	
//	private boolean createFile(String path, String key){
//		File file = new File(path);		
//		
//		boolean flag = false;		
//		try {
//			File csdDir = new File(userHome + CSD_DIR);
//			if(!csdDir.isDirectory())
//				flag = csdDir.mkdir();
//			else 
//				flag = true;
//			if(!flag) return false;
//			flag = file.createNewFile();
//			if(flag)
//				propertyMap.put(key, file.getAbsolutePath());
//		} catch (IOException e) {
//			e.printStackTrace();
//			flag = false;
//		}		
//		return flag;
//	}
}
