/*
 * AlbumInformationReader.java
 *
 * Created on 2005/11/11, 22:12
 *
 */

package org.jdesktop.lg3d.apps.trumplayer.base;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.NamedNodeMap;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.InputStream;

import java.util.AbstractList;
import java.util.Vector;
import java.util.Arrays;
import java.util.List;

import java.io.FileInputStream;

import org.jdesktop.lg3d.apps.trumplayer.mp3util.ID3Tag;
import org.jdesktop.lg3d.apps.trumplayer.mp3util.ID3TagReader;
import org.jdesktop.lg3d.apps.trumplayer.mp3player.FileInfoBean;
import org.jdesktop.lg3d.apps.trumplayer.mp3player.M3UReader;
import org.jdesktop.lg3d.apps.trumplayer.mp3player.FileInfoBean;


/**
 *
 * @author Yasuhiro Fujitsuki(thaniwa)
 */
public class AlbumInformationReader {
    
    Vector<AlbumInformation> albumInformations = new Vector<AlbumInformation>();
    private String characterCode;
    private String fontName ="Serif";

    private Document document;
    // localhome = extm3u directory
    private String localhome;

    /** Creates a new instance of InformationReader */
    private AlbumInformationReader(String filename, String localhome)
            throws java.io.IOException, javax.xml.parsers.ParserConfigurationException,
            org.xml.sax.SAXException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        this.localhome = localhome;
        File file = new File(filename);
        
        if(file.exists()){
            FileInputStream fis = new FileInputStream(filename);
            document = builder.parse(fis);
            parse();
        } else {
            ClassLoader loader = this.getClass().getClassLoader();
            InputStream is = loader.getResourceAsStream(filename);
            document = builder.parse(is);
            parse();
        }
        
    }
    
    /** get new instance of Information reader */
    public static AlbumInformationReader getReader(String filename, String localhome) {
        AlbumInformationReader reader = null;
        try {
            reader = new AlbumInformationReader(filename, localhome);
        } catch (Exception e){
            // e.printStackTrace();
            reader = null;
        }
        return reader;
    }

    /** parse xml file using DOM */
    private void parse(){
        if(document != null){
            NodeList list = document.getChildNodes();
            for(int i=0; list != null && i < list.getLength(); i++){
                Node node = list.item(i);
                // root node
                if(node.getNodeName().equalsIgnoreCase("root")){
                    NodeList rootList = node.getChildNodes();
                    for(int t=0; rootList != null && t < rootList.getLength(); t++){
                        Node rootNode = rootList.item(t);
                        // charactor encode
                        if(rootNode.getNodeName().equalsIgnoreCase("encode"))
                            characterCode = rootNode.getChildNodes().item(0).getNodeValue();
                        else if(rootNode.getNodeName().equalsIgnoreCase("fontname"))
                            fontName = rootNode.getChildNodes().item(0).getNodeValue();
                        else if(rootNode.getNodeName().equalsIgnoreCase("album"))
                            readAlbumList(rootNode);
                    }
                }
            }
        }
    }
    
    /** read album list from xml file
     *  this class called by InformationReader.pause() .
     */
    private void readAlbumList(Node node){
        NodeList list = node.getChildNodes();
        AlbumInformation albumInformation = new AlbumInformation();

        // search node
        for(int i=0; list != null && i < list.getLength(); i++){
            Node child = list.item(i);
            if(child.getNodeName().equalsIgnoreCase("artist")){
                if( child.hasChildNodes() )
                    albumInformation.setArtist( child.getChildNodes().item(0).getNodeValue() );
                else
                    albumInformation.setArtist("");
            }
            else if(child.getNodeName().equalsIgnoreCase("title")){
                if( child.hasChildNodes() )
                    albumInformation.setTitle( child.getChildNodes().item(0).getNodeValue() );
                else
                    albumInformation.setTitle("");
            }
            else if(child.getNodeName().equalsIgnoreCase("image")){
                if( child.hasChildNodes() )
                    albumInformation.setImage( child.getChildNodes().item(0).getNodeValue() );
                else
                    albumInformation.setImage("");
            }
            else if(child.getNodeName().equalsIgnoreCase("m3u")){
                if( child.hasChildNodes() )
                    albumInformation.setM3U( child.getChildNodes().item(0).getNodeValue() );
                else
                    albumInformation.setM3U("");
            }
            else if(child.getNodeName().equalsIgnoreCase("folder")){
                // new function
                // support folder tag instead of m3u
                boolean recursive = false;
                if( child.hasAttributes() ){
                    NamedNodeMap map = child.getAttributes();
                    Node recursiveAttribute = map.getNamedItem("recursive");
                    if( recursiveAttribute != null){
                        String recursiveValue = recursiveAttribute.getNodeValue();
                        if( recursiveValue != null && recursiveValue.trim().equalsIgnoreCase("true"))
                            recursive = true;
                    }
                }

                // When already set m3u filename, do nothing.(use m3u information).
                if(albumInformation.getM3U() == null || albumInformation.getM3U().equalsIgnoreCase("") == true){
                    String folder = null;
                    if( child.hasChildNodes() )
                        folder = child.getChildNodes().item(0).getNodeValue();
                    // when folder cannot found, do nothing.
                    String m3u = getLocalM3U(folder, recursive, albumInformation.getArtist(), albumInformation.getTitle());
                    if(m3u != null && m3u.equalsIgnoreCase("") == false)
                        albumInformation.setM3U( m3u );
                    else 
                        albumInformation.setM3U( "" );
                }
            }
        }
        //System.out.println("localhome = "+ localhome);
        //System.out.println("albuminfo = "+ albumInformation.getM3U());
        if(albumInformation.getM3U().toLowerCase().startsWith(localhome.toLowerCase())){
            //System.out.println("local cache = true");
            albumInformation.setLocalCache(true);
        }
        albumInformations.add(albumInformation);
        
    }

    /** return extm3u file name */
    private String getLocalM3U(String folder, boolean recursive, String artist, String title){
        String m3u = null;
        File folderFile = null;

        // when folder cannot found, do nothing.
        if(folder == null || folder.equalsIgnoreCase("")) 
            return null;
        else {
            folderFile = new File(folder);
            if(folderFile.isDirectory() == false)
                return null;
        }
        
        // when local folder setting is null, do nothing.
        if(localhome == null || localhome.equalsIgnoreCase("") == true)
            return null;
        
        // when local folder does not exists, make folder
        File localFolder = new File(localhome);
        if ( localFolder.exists() == false )
            localFolder.mkdir();
        else if( localFolder.isDirectory() != true )
            return null;
       
        // extm3u check : simple version
        String localM3uName = artist + "_" + title + ".m3u";
        // filename does not support : in Windows
        localM3uName.replaceAll(":","-");
        m3u = localhome + java.io.File.separator + localM3uName;
        File localM3u = new File(m3u);
        
        // file check
        // when local m3u already exists, trumplayer check directory path of the first track.
        // If it differences folder(String), make new local m3u.
        if( localM3u.exists() == true ) {
            try{ 
                AbstractList<FileInfoBean> alist = M3UReader.getPlayList(localM3u.getCanonicalPath());
                FileInfoBean fileInfo = alist.get(0);
                File tmpfile = new File(fileInfo.getFilePath());
                String dirPath = tmpfile.getParent();
                if( recursive && dirPath.startsWith(folder) )
                    return m3u;
                else if( recursive == false && dirPath.equalsIgnoreCase(folder) )
                    return m3u;
            } catch (Exception e){}
        }

        
        
        // create fileInfoBean
        Vector<FileInfoBean> fibList = createFileInfoBeanList(folderFile,recursive,characterCode);
        
        // sort with track number
        // sortWithTrackNumber(fList,iList);

        // create new extm3u for internal use.
        try {

            if( localM3u.canWrite() || localM3u.createNewFile() ){
                // creating part of internally m3u file.
                // FileWriter writer = new FileWriter(localM3u);
                OutputStreamWriter writer =
                        new OutputStreamWriter( new FileOutputStream(localM3u),"UTF8");
                writer.write("#EXTM3U\n");
                for(int i=0; i<fibList.size();i++){
                    ID3Tag id3tag = fibList.get(i).getID3Tag();
                    writer.write("#EXTINF:0," + id3tag.getArtist()+" - "+id3tag.getTitle()+"\n");
                    writer.write(fibList.get(i).getFilePath()+"\n");
                }
                writer.close();
            } else {
                // if creating file failed.
                m3u = null;
            }

        } catch (java.io.IOException ioe ){
            m3u = null;
        }
        
        return m3u;

    }

    
    // mp3 filter
    private java.io.FilenameFilter mp3Filter = new java.io.FilenameFilter() {
        public boolean accept(File dir,String name) {
            boolean result = false;
            if( name != null && name.toLowerCase().endsWith(".mp3") )
                result = true;
                return result;
            }
    };

    // directory(folder) filter
    private java.io.FileFilter dirFilter = new java.io.FileFilter() {
        public boolean accept(File pathname) {
            return pathname.isDirectory();
        }
    };
    
    private Vector<FileInfoBean> createFileInfoBeanList(File folder, boolean recursive, String encode ){
        Vector<FileInfoBean> result = null;
        
        // make list from mp3 file informations
        String[] fileList = folder.list(mp3Filter);

        if( fileList != null || fileList.length > 0){
            Arrays.sort(fileList);

            // make canonical path : folder + fileList[i] = canonical path
            for (int i=0; i<fileList.length; i++){
                try{
                    fileList[i] = folder.getCanonicalPath() + java.io.File.separator + fileList[i];
                }catch(java.io.IOException ioe){
                }
            }

            // create ID3Tag List
            ID3Tag id3List[] = new ID3Tag[fileList.length];
            if(fileList != null && fileList.length > 0){
                for(int i=0; i< fileList.length;i++)
                    id3List[i] = ID3TagReader.getID3Tag(fileList[i],encode);
            }

            result = new Vector<FileInfoBean>();
            // create FileInfoBean
            for(int i=0; i< fileList.length;i++){
                FileInfoBean fib = new FileInfoBean();
                fib.setFilePath(fileList[i]);
                fib.setID3Tag(id3List[i]);
                result.add(fib);
                result = sortWithTrack(result);
            }

        }


        // recursive search
        if(recursive){
            File folderChildren[] = folder.listFiles(dirFilter);
            if(folderChildren != null){
                for(int i=0; i<folderChildren.length; i++){
                    Vector<FileInfoBean> child = createFileInfoBeanList(folderChildren[i],recursive,encode);
                    if(child != null && child.size() > 0)
                        result.addAll(child);
                }
            }
        }

        if(result.size() == 0)
            result = null;
        return result;
    }
    
    private Vector<FileInfoBean> sortWithTrack(Vector<FileInfoBean> list){
        if(list == null) return null;
        // bubble sort
        for(int i=0; i < list.size()-1; i++){
            for(int t= list.size() -1 ; t > i; t--){
                String a = list.get(t).getID3Tag().getTrack();
                if( a == null || a.trim().equals(""))
                    a = "999";
                String b = list.get(t-1).getID3Tag().getTrack();
                if( b == null || b.trim().equals(""))
                    b = "999";
                
                // for ID3 V2
                if( a.indexOf("/") > 0 )
                    a = a.substring(0,a.indexOf("/"));
                if( b.indexOf("/") > 0)
                    b = b.substring(0,b.indexOf("/"));
                
                int aint = 999;
                int bint = 999;
                try{
                    aint = Integer.parseInt(a);
                    bint = Integer.parseInt(b);
                } catch (Exception e){
                    aint = 999;
                    bint = 999;
                }
                if(aint < bint){
                    FileInfoBean temp_a = list.get(t-1);
                    FileInfoBean temp_b = list.get(t);
                    list.set(t,temp_a);
                    list.set(t-1,temp_b);
                }

            }
        }
        return list;
    }
    
    
    /** return character code of m3u file */
    public String getCharacterCode(){
        return characterCode;
    }
    
    /** return font name */
    public String getFontName(){
        return fontName;
    }

    /** get AlbumInformation list */
    public AlbumInformation[] getAlbumInformations(){
        AlbumInformation[] albumInformations = null;
        if( this.albumInformations.size() != 0 ){
            albumInformations = new AlbumInformation[this.albumInformations.size()];
            this.albumInformations.toArray(albumInformations);
        }
        return albumInformations;
    }    
    
}
