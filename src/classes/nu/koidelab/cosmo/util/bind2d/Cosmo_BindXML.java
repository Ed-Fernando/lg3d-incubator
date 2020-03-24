package nu.koidelab.cosmo.util.bind2d;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
/*
 * --------Schedule-----------
 * <year value="2005">
 * <month value="1">
 * <plan name="SCHEDUEL NAME" priority="PRIORITY">
 *    <date>
 *       <start> SCHEDULE START TIME </start>
 *       <time> SCHEDULE DATE </time>
 *       <end> SCHEDULE END TIME </end>
 *    </date>
 *    <detail>
 *       <memo> SCHEDULE DETAIL MEMO </memo>
 *       <reference> REFERENCE FILE NAME or URL </reference>
 *       <category> CATEGORY </category>
 *    </detail>
 *    <weight> weight </weight>
 * </plan>
 * </year>
 * 
 * ---------��̱�ν���-------------
 * <holiday name="HOLIDAY NAME">
 *    <date> HOLIDAY DATE </date>
 * </holiday>
 * 
 * ---------�Ŀͤε���-------------
 * (ex)�����֤˰��ٲ�������
 * <holiday name="HOLIDAY NAME">
 *    <date> HOLIDAY DATE </date>
 *    <cycle>
 * 		<aDayOfWeek> (ex)Tue </aDayOfWeek>
 *      <cycleTime> 2 </cycleTime>
 *      <unit> month </unit>
 *    </cycle>
 * </holiday>
 */

 /**
 * @author nan, fumi
 * Cosmo_BindXML
 * nu.koidelab.cosmo.util.bind2d
 */
public class Cosmo_BindXML {
    private Document document;

    public Cosmo_BindXML(){}
    
    public void setFileName(String filename){
    	InputStream is = getClass().getClassLoader().getResourceAsStream(filename);
        if(is == null){        	
        	try {
				is = new FileInputStream(new File(filename));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
        }
        
        try{
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
        } catch(IOException ioE){
        	ioE.printStackTrace();
        } catch(SAXException saxE){
//            System.err.println("�׵ᤵ�줿������������ DocumentBuilder �������Ǥ��ޤ���");
			 saxE.printStackTrace();
        } catch(ParserConfigurationException pce){
//            System.err.println("XML�ι�ʸ�˸�꤬����ޤ���");
        	pce.printStackTrace();
        } 
    }

    public NodeList loadListByTagName(String tagName){
        Element root = document.getDocumentElement();
        NodeList childs = root.getElementsByTagName(tagName);        
        return childs;
    }
    
    public Document getNewDocument(String rootName){
        try{
            DOMImplementation domImpl = DocumentBuilderFactory.newInstance().newDocumentBuilder().getDOMImplementation();
            Document document = domImpl.createDocument("", rootName, null);
            return document;
        } catch (ParserConfigurationException e){
            e.printStackTrace();
            return null;
        }
    }
    
    public static void saveDocument(Document document, String filename) {
        try {
            TransformerFactory transFactory = TransformerFactory.newInstance();
            Transformer transformer = transFactory.newTransformer();
//            transformer.setOutputProperty(OutputKeys.ENCODING, "euc-jp");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");

            DOMSource source = new DOMSource(document);
            File newXML = new File(filename);
            FileOutputStream os = new FileOutputStream(newXML);
            StreamResult result = new StreamResult(os);
            transformer.transform(source, result);
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }   
}