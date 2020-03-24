package org.jdesktop.lg3d.apps.bgmanager.configReaders;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.jdesktop.lg3d.apps.bgmanager.*;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

//Class to load builtin backgrounds

public class BgConfigFileReader extends DefaultHandler{
	  private StringBuffer bodyContentBuffer;
	    private ArrayList listContents;
            private String bgNameString;
	    private String bGfiles[];
	    private int count;
	    public  String configFile;
	    private BgFileReader bgReader;
		
    public BgConfigFileReader(String configFiles)
    {
        bodyContentBuffer = null;
        configFile = configFiles;
        listContents = new ArrayList();
        bodyContentBuffer = null;
        try
        {
            parseConfigFile(configFile);
        }
        catch(SAXException e)
        {
            e.printStackTrace();
        }
    }

    public void startDocument()
    {
    }

    public void endDocument(){

        bGfiles = buildBgArray(listContents);
 
    }

    public void startElement(String namespaceURI, String sName, String qName, Attributes attrs)
        throws SAXException{ 
		
    }

    public void endElement(String namespaceURI, String sName, String qName)
        throws SAXException {
        String eName = sName;
        if("".equals(eName))
            eName = qName;
		 if(!eName.equals("BgManagerConfig"))
	        
	            if(eName.equals("BGURL"))
	            {
	                listContents.add(bodyContentBuffer.toString());
	            } else
	            {
                bodyContentBuffer = null;
                throw new SAXException((new StringBuilder()).append("Wrong tag: ").append(eName).toString());
            }
        bodyContentBuffer = null;
    }

    public void characters(char buf[], int offset, int len)
        throws SAXException
    {
        if(bodyContentBuffer == null)
            bodyContentBuffer = new StringBuffer(1024);
        bodyContentBuffer.append(buf, offset, len);
    }

    public void parseConfigFile(String configFile)
        throws SAXException
    {
        URL fileUrl = getClass().getClassLoader().getResource(configFile);
        try
        {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            configFile.replace('/', File.separatorChar);
            saxParser.parse(fileUrl.toString (), this);
        }
        catch(Exception ex)
        {
            throw new SAXException(ex);
        }
    }

    public String[] buildBgArray(ArrayList list) {
        int size = list.size();
        String BgConf[] = new String[size];
        for(int i = 0; i < size; i++) {
            BgConf[i] = (String)list.get(i);
            count = size;
        }
        return BgConf;
    }

    public String[] getBgFiles()  {
        return bGfiles;
    }

	 public int getNumBg()  {
	        return count;
	    }

	 public String getBgNameString()  {
	        return bgNameString;
	    }
}
