/*
 * AutomaticConfigurator.java
 *
 * Created on 2006/08/05, 23:45
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.jdesktop.lg3d.apps.trumplayer.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
//import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;

import java.util.ResourceBundle;

import java.util.Vector;
import java.util.Arrays;

import org.jdesktop.lg3d.apps.trumplayer.mp3player.FileInfoBean;
import org.jdesktop.lg3d.apps.trumplayer.mp3util.ID3Tag;
import org.jdesktop.lg3d.apps.trumplayer.mp3util.ID3TagReader;

import org.jdesktop.lg3d.apps.trumplayer.base.AlbumInformation;
import org.jdesktop.lg3d.apps.trumplayer.base.AlbumInformationReader;

/**
 *
 * @author Yasuhiro Fujitsuki(thaniwa)
 */
public class AutomaticConfigurator {
    
    private static String TRUMPLAYER_LOCALHOME = TrumplayerDefaults.TRUMPLAYER_LOCALHOME;
    private static String TRUMPLAYER_CONFIG_PATH = TrumplayerDefaults.TRUMPLAYER_CONFIG_PATH;
    
    protected static AutomaticConfigurator configurator = null;
    
    private String defaultEncode = "ISO-8859-1";
    private String defaultFont = "Arial";

    /** Creates a new instance of AutomaticConfigurator */
    protected AutomaticConfigurator() {
    }
    
    /** initialize method for extended class */
    protected void initialize(){
        ResourceBundle resource = ResourceBundle.getBundle("org.jdesktop.lg3d.apps.trumplayer.resources.messages");
        if(resource != null){
            defaultEncode = resource.getString("AutomaticConfigurator.encode");
            defaultFont = resource.getString("AutomaticConfigurator.fontname");
        }
    }
    
    public static AutomaticConfigurator getConfigurator(){
        if(configurator == null)
            configurator = new AutomaticConfigurator();
        configurator.initialize();
        return configurator;
    }

    public enum Status { SUCCESS, FAILED_XML, FAILED_M3U, NOT_FOUND };
    
    /** search and create trumplayer.xml and internal extm3u files */
    public Status createConfigFiles(File folder, String encode){
        AlbumList albumList = createAlbumList(folder, true, encode);
        Vector<Album> album = albumList.getList();
        
        
        if( album == null || album.size() == 0 )
            return Status.NOT_FOUND;
        
        // run createInternalM3U before run createTrumplayerXML !!
        for(int i=0; i < album.size(); i++){
            Album a = album.get(i);
            // filename does not support to use : (windows)
            a.setAlbum( a.getAlbum().replaceAll(":","-"));
            createInternalM3U(a);
        }
        
        // need to add replaceTrumplayerXML
        // Mode.ADD の時、呼び出すメソッドを変更する。
        boolean b = false;
        if( mode == Mode.ADD )
            b = modifyTrumplayerXML(albumList);
        else 
            b = createTrumplayerXML(albumList);

        if ( b == false ){
            if( albumList.getList().size() == 0)
                return Status.NOT_FOUND;
            else
                return Status.FAILED_XML;
        }
        
        return Status.SUCCESS;
    }
    
    public Status createConfigFiles(File folder){
        return createConfigFiles(folder,defaultEncode);
    }
    
    public Status createConfigFiles(File folder, Mode mode){
        return createConfigFiles(folder,defaultEncode,mode);
    }
    
    public enum Mode { NEW, ADD };
    private Mode mode = Mode.NEW;
    
    public Status createConfigFiles(File folder, String encode, Mode mode){
        this.mode = mode;
        return createConfigFiles(folder,encode);
    }

    public void test(File folder, String encode){
        //createConfigFiles(folder,encode);
        AlbumList albumList = createAlbumList(folder, true, encode);
        Vector<Album> list = albumList.getList();
        for(int i=0; i < list.size(); i++){
            Album a = list.get(i);
            System.out.println("======================================");
            System.out.println("Artist : " + a.getArtist() );
            System.out.println("Album  : "+ a.getAlbum() );
            System.out.println("Folder : " + a.getBaseDir() );
            System.out.println("--------------------------------------");
            Vector<FileInfoBean> fibList = a.getList();
            for(int t=0; fibList != null && t < fibList.size(); t++){
                FileInfoBean fib = fibList.get(t);
                ID3Tag id3 = fib.getID3Tag();
                if(id3 != null)
                    System.out.println("Music Info : " +  id3.getTrack() + " : " + id3.getArtist() + " - " + id3.getTitle());
                System.out.println("File Path  : " + fib.getFilePath() );
            }
            System.out.println("--------------------------------------");
        }
    }


    /* **********
     * for debug
     *********** */
    public static void main(String args[]){
        AutomaticConfigurator ac = AutomaticConfigurator.getConfigurator();
        File folder = new File(args[0]);
        if( folder.isDirectory() == true ){
            if( args.length > 1)
                ac.createConfigFiles(folder, "JISAutoDetect");
            else
                ac.test( folder, "JISAutoDetect" );
        }
        else
            System.out.println(args[0] + " is not a folder/directory.");
    }

    // mp3 filter
    private FilenameFilter mp3Filter = new FilenameFilter() {
        public boolean accept(File dir,String name) {
            boolean result = false;
            if( name != null && name.toLowerCase().endsWith(".mp3") )
                result = true;
                return result;
            }
    };

    // directory(folder) filter
    protected FileFilter dirFilter = new FileFilter() {
        public boolean accept(File pathname) {
            return pathname.isDirectory();
        }
    };
    
    // Album List
    protected class AlbumList {
        private Vector<Album> list = new Vector<Album>();
        public Vector<Album> getList(){ return list; }
        public Album getAlbum(String artist, String album, String path){
            Album result = null;
            for(int i=0; i < list.size(); i++){
                Album a = list.get(i);
                if( a.getArtist().equals(artist) && a.getAlbum().equals(album) ){
                    result = a;
                    break;
                } else if ( a.getAlbum().equals(album) && a.getBaseDir().equals(path) ) {
                    result = a;
                    if( a.getAlbum().equals("Various") == false )
                        a.setArtist("Various");
                    break;
                }
            }
            if(result == null){
                result = new Album();
                result.setArtist(artist);
                result.setAlbum(album);
                result.setBaseDir(path);
                list.add(result);
            }
            
            return result;
        }

        public Album getAlbum(String folderPath){
            Album result = null;
            for( int i=0 ; i < list.size(); i++ ){
                Album a = list.get(i);
                if( a.getArtist().equals("Unknown") && a.getBaseDir().equals( folderPath ) ){
                    result = a;
                }
            }

            if(result == null){
                result = new Album();
                result.setArtist("Unknown");
                result.setAlbum(folderPath.replaceAll(java.io.File.separator,"."));
                result.setBaseDir(folderPath);
                list.add(result);
            }

            return result;
        }
        
        public void removeAlbum(String artist, String album){
            for(int i=0; i < list.size(); i++){
                Album a = list.get(i);
                if( a.getArtist().equals(artist) && a.getAlbum().equals(album) ){
                    list.remove(a);
                    break;
                }
            }
        }
        
    };


    // Album
    protected class Album {
        private String basedir;
        private String artist;
        private String album;
        private Vector<FileInfoBean> list = new Vector<FileInfoBean>();
        
        private String imageFileName;
        
        public String getBaseDir(){ return basedir; }
        public void setBaseDir(String s){ basedir =s; }
        public String getArtist(){ return artist; }
        public void setArtist(String s){ artist = s;}
        public String getAlbum(){ return album; }
        public void setAlbum(String s){ album = s; }
        public void addFileInfoBean(FileInfoBean fib){
            list.add(fib);
        }
        public Vector<FileInfoBean> getList(){ return list; }
        public void setList(Vector<FileInfoBean> list){ this.list = list; }
        
        public String getImageFileName(){ return imageFileName; }
        public void setImageFileName(String s){ imageFileName = s; }
    };


    public boolean replaceTrumplayerXML(AlbumInformation[] albumList){
        File trumplayer_xml = new File(TRUMPLAYER_CONFIG_PATH);

        // modify new trumplayer.xml .
        try {
            if( trumplayer_xml.canWrite() ){
                // creating part of internally m3u file.
                // FileWriter writer = new FileWriter(trumplayer_xml);
                OutputStreamWriter writer =
                        new OutputStreamWriter( new FileOutputStream(trumplayer_xml),"UTF8");
                writeHeader(writer);
                
                if( albumList != null ) {
                    for(int i=0; i < albumList.length; i++){
                        writeAlbumInformation(writer, albumList[i].getArtist(), albumList[i].getTitle(), albumList[i].getImage(), albumList[i].getM3U());
                    }
                }                                
                // need to add creating xml : end
                writeFooter(writer);
                writer.close();
            } else {
                // if creating file failed.
                return false;
            }
        } catch (java.io.IOException ioe ){
            return false;
        }

        return true; // defaultEncode

    }
    
    // modify trumplayer.xml
    protected boolean modifyTrumplayerXML(AlbumList albumList){
        File trumplayer_xml = new File(TRUMPLAYER_CONFIG_PATH);

        if( trumplayer_xml.exists() == false )
            return createTrumplayerXML(albumList);

        AlbumInformationReader reader = AlbumInformationReader.getReader(TRUMPLAYER_CONFIG_PATH,TRUMPLAYER_LOCALHOME);
        AlbumInformation[] albumInformations = reader.getAlbumInformations();

        // remove album information which already exists in the trumplayer.xml
        if( albumInformations != null ){
            for(int i=0; i < albumInformations.length; i++){
                albumList.removeAlbum(albumInformations[i].getArtist(), albumInformations[i].getTitle());
            }
        }

        // return false when list is null;
        Vector<Album> list = albumList.getList();
        
        if(list.size() == 0)
            return false;
        
        // modify new trumplayer.xml .
        try {
            if( trumplayer_xml.canWrite() ){
                // creating part of internally m3u file.
                // FileWriter writer = new FileWriter(trumplayer_xml);
                OutputStreamWriter writer =
                        new OutputStreamWriter( new FileOutputStream(trumplayer_xml),"UTF8");
                writeHeader(writer);
                
                if( albumInformations != null ){
                    for(int i=0; i < albumInformations.length; i++){
                        writeAlbumInformation(writer, albumInformations[i].getArtist(), albumInformations[i].getTitle(), albumInformations[i].getImage(), albumInformations[i].getM3U());
                    }
                }
                
                for(int i=0; i<list.size(); i++){
                    Album a = list.get(i);
                    String internalM3U = TRUMPLAYER_LOCALHOME + java.io.File.separator
                            + a.getArtist() + "_" + a.getAlbum() + ".m3u";
                    // writeAlbumInformation(writer, a.getArtist(), a.getAlbum(), "" ,internalM3U);
                    if( a.getImageFileName() != null )
                        writeAlbumInformation(writer, a.getArtist(), a.getAlbum(), a.getImageFileName(), internalM3U);
                    else
                        writeAlbumInformation(writer, a.getArtist(), a.getAlbum(), "", internalM3U);
                }
                
                // need to add creating xml : end
                writeFooter(writer);
                writer.close();
            } else {
                // if creating file failed.
                return false;
            }
        } catch (java.io.IOException ioe ){
            return false;
        }

        return true; // defaultEncode
    }

    // create trumplayer.xml
    protected boolean createTrumplayerXML(AlbumList albumList){
        File trumplayer_xml = new File(TRUMPLAYER_CONFIG_PATH);
        
        if( trumplayer_xml.exists())
            return false;
        
        // return false when list is null;
        Vector<Album> list = albumList.getList();
        if( list.size() == 0 )
            return false;
        
        // create new trumplayer.xml .
        try {
            if( trumplayer_xml.createNewFile() == true ){
                // creating part of internally m3u file.
                // FileWriter writer = new FileWriter(trumplayer_xml);
                OutputStreamWriter writer =
                        new OutputStreamWriter( new FileOutputStream(trumplayer_xml),"UTF8");
                writeHeader(writer);
                // need to add creating xml : begin
                for(int i=0; i<list.size(); i++){
                    Album a = list.get(i);
                    String internalM3U = TRUMPLAYER_LOCALHOME + java.io.File.separator
                            + a.getArtist() + "_" + a.getAlbum() + ".m3u";
                    if( a.getImageFileName() != null )
                        writeAlbumInformation(writer, a.getArtist(), a.getAlbum(), a.getImageFileName(), internalM3U);
                    else
                        writeAlbumInformation(writer, a.getArtist(), a.getAlbum(), "", internalM3U);
                }
                // need to add creating xml : end
                writeFooter(writer);
                writer.close();
            } else {
                // if creating file failed.
                return false;
            }
        } catch (java.io.IOException ioe ){
            return false;
        }

        return true;
    }
    
    protected void writeHeader(OutputStreamWriter writer) throws java.io.IOException {
        writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n");
        writer.write("<!-- trumplayer configuration file -->\n\n");
        writer.write("<root>\n");
        writer.write("  <encode>" + defaultEncode + "</encode>\n");
        writer.write("  <fontname>"+ defaultFont +"</fontname>\n");        
    }

    protected void writeAlbumInformation(OutputStreamWriter writer, String artist, String title, String image, String m3u) throws java.io.IOException {
        writer.write("    <album>\n");
        writer.write("        <artist>" + artist.replace("&","&amp;") + "</artist>\n");
        writer.write("        <title>" + title.replace("&","&amp;") + "</title>\n");
        writer.write("        <image>" + image.replace("&","&amp;") + "</image>\n");

        if( m3u != null && m3u.equals("") == false){
            File m3uFile = new File(m3u);
            if( m3uFile.isDirectory() )
                writer.write("        <folder>" + m3u.replace("&","&amp;") +"</folder>\n");
            else
                writer.write("        <m3u>" + m3u.replace("&","&amp;") +"</m3u>\n");
        } else
            writer.write("        <m3u>" + m3u.replace("&","&amp;") +"</m3u>\n");
        
        writer.write("    </album>\n");
    }

    protected void writeFooter(OutputStreamWriter writer) throws java.io.IOException {
        writer.write("</root>\n");
    }

    // create m3u file to use internally
    protected boolean createInternalM3U(Album album) {

        // when local folder does not exists, make folder
        File localFolder = new File(TRUMPLAYER_LOCALHOME);
        if ( localFolder.exists() == false )
            localFolder.mkdir();
        else if( localFolder.isDirectory() != true )
            return false;


        // extm3u check : simple version
        String baseName = TRUMPLAYER_LOCALHOME + java.io.File.separator +  album.getArtist() + "_" + album.getAlbum();
        // String internalM3uName =  baseName + ".m3u";
        String m3u = baseName + ".m3u";
        File internalM3u = new File(m3u);

        // create fileInfoBean
        // Vector<FileInfoBean> fibList = createFileInfoBeanList(folderFile,recursive,encode);
        Vector<FileInfoBean> fibList = album.getList();

        
        // search album jacket image first
        for(int i=0; i < fibList.size();i++){
            ID3Tag id3tag = fibList.get(i).getID3Tag();
            if( album.getImageFileName() == null ){
                album.setImageFileName( createJacketImage(baseName,id3tag) );
            }
        }
        if( album.getImageFileName() == null && fibList.size() > 0){
            try{
                FileInfoBean fib = fibList.get(0);
                String filePath = fib.getFilePath();
                File f = new File(filePath);
                File parent = f.getParentFile();
                String list[] = parent.list();
                String albumArt = null;
                if(list != null){
                    for(int i=0; i < list.length; i++){
                        if(list[i].startsWith("AlbumArt") &&
                           list[i].endsWith("Large.jpg")){
                        albumArt = list[i];
                            break;
                        }
                    }
                }
                if(albumArt != null)
                    album.setImageFileName( parent.getCanonicalPath() + java.io.File.separator + albumArt );
            } catch(Exception e){}
        }
        
        // if internal M3U exists, does not create M3U.
        // but return true. (trumplayer thinks no errors)
        if( internalM3u.exists() == true )
            return true;
        

        // create new extm3u for internal use.
        try {
            if( internalM3u.createNewFile() == true ){
                // creating part of internally m3u file.
                // FileWriter writer = new FileWriter(internalM3u);
                OutputStreamWriter writer =
                        new OutputStreamWriter( new FileOutputStream(internalM3u),"UTF8");
                writer.write("#EXTM3U\n");
                for(int i=0; i<fibList.size();i++){
                    ID3Tag id3tag = fibList.get(i).getID3Tag();
                    if(id3tag != null)
                        writer.write("#EXTINF:0," + id3tag.getArtist()+" - "+id3tag.getTitle()+"\n");
                    writer.write(fibList.get(i).getFilePath()+"\n");
                }
                writer.close();

            } else {
                // if creating file failed.
                return false;
            }
        } catch (java.io.IOException ioe ){
            ioe.printStackTrace();
            return false;
        }

        return true;

    }

    protected String createJacketImage(String base, ID3Tag id3){
        String result = null;
        String filename = "";
        if( id3 != null && id3.getImageType() != null && id3.getImageType().equals("") == false && 
                id3.getImageType().equals(ID3Tag.URL) == false ){

            if(id3.getImageType().equals(ID3Tag.JPEG))
                filename = base + ".jpg";
            else if(id3.getImageType().equals(ID3Tag.PNG))
                filename = base + ".png";

            File internalImage = new File(filename);
            result = filename;
            if( internalImage.exists() == false && id3.getImage() != null ){
                try{
                   FileOutputStream stream = new FileOutputStream(internalImage);
                   stream.write(id3.getImage());
                   stream.close();
                } catch(java.io.IOException e){
                }
            }
        }
        
        return result;
    }
    
    // create FileInfoBean List
    protected AlbumList createAlbumList(File folder, boolean recursive, String encode){
        
        AlbumList albumList = new AlbumList();
        
        String folderPath = "";
        
        try{
            folderPath = folder.getCanonicalPath();
        } catch (java.io.IOException ioe){
        }
        
        // make list from mp3 file informations
        String[] fileList = folder.list(mp3Filter);

        if( fileList != null || fileList.length > 0){
            Arrays.sort(fileList);

            // make canonical path : folder + fileList[i] = canonical path
            for (int i=0; i<fileList.length; i++){
                    fileList[i] = folderPath + java.io.File.separator + fileList[i];
            }

            // create ID3Tag List
            ID3Tag id3List[] = new ID3Tag[fileList.length];
            if(fileList != null && fileList.length > 0){
                for(int i=0; i< fileList.length;i++)
                    id3List[i] = ID3TagReader.getID3Tag(fileList[i],encode);
            }

            // create FileInfoBean
            for(int i=0; i< fileList.length;i++){
                FileInfoBean fib = new FileInfoBean();
                fib.setFilePath(fileList[i]);
                Album album = null;
                if(id3List[i] != null){
                    fib.setID3Tag(id3List[i]);
                    album = albumList.getAlbum( id3List[i].getArtist(), id3List[i].getAlbum() , folderPath );
                } else {
                    album = albumList.getAlbum( folderPath );
                }
                Vector<FileInfoBean> fibList = album.getList();
                // if( fibList == null )
                //    fibList = new Vector<FileInfoBean>();
                fibList.add(fib);
            }
            
            // sort with track number
            Vector<Album> alist = albumList.getList();
            for(int i=0; i < alist.size(); i++){
                Album a = alist.get(i);
                a.setList( sortWithTrack(a.getList()) );
            }

        }

        // recursive search

        if(recursive){
            File folderChildren[] = folder.listFiles(dirFilter);
            if(folderChildren != null){
                for(int i=0; i<folderChildren.length; i++){
                    AlbumList childList = createAlbumList(folderChildren[i],recursive,encode);
                    Vector<Album> clist = childList.getList();
                    for(int t=0; t<clist.size(); t++){
                        Album child = clist.get(t);
                        Album a = albumList.getAlbum( child.getArtist(), child.getAlbum(), child.getBaseDir() );
                        if( a.getList() == null )
                            a.setList( child.getList() );
                        else {
                            Vector<FileInfoBean> newfiblist = a.getList();
                            newfiblist.addAll( child.getList() );
                            a.setList(newfiblist);
                        }
                    }
                }
            }
        }

        return albumList;
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

}
