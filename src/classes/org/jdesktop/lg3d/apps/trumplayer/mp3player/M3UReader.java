/*
 * M3UReader.java
 *
 * Created on 2005/11/04, 13:33
 *
 */

package org.jdesktop.lg3d.apps.trumplayer.mp3player;

import java.io.*;
import java.util.*;

import org.jdesktop.lg3d.apps.trumplayer.mp3util.ID3Tag;
import org.jdesktop.lg3d.apps.trumplayer.mp3util.ID3TagReader;

/**
 *
 * @author Yasuhiro Fujitsuki (thaniwa)
 */
public class M3UReader {
    
    /** Creates a new instance of M3UReader */
    public M3UReader() {
    }
        
    /** get playlist from m3u file */
    public static AbstractList<FileInfoBean> getPlayList(String fileName){
        return getPlayList(fileName, null);
    }
    
    public static AbstractList<FileInfoBean> getPlayList(String fileName, String encode){
        return getPlayList(fileName,encode,encode);
    }

    public static AbstractList<FileInfoBean> getPlayList(String fileName, String encode, String m3uEncode){

        AbstractList<FileInfoBean> result = new ArrayList<FileInfoBean>();

        boolean unix = false;
        if( File.separator.equals("/") )
            unix = true;
        
        String path = null;
        
        try{
            // get canonical path from file name
            path = new File(fileName).getCanonicalPath();
            File file = new File( path );
            if( file.exists() == false ){
                System.err.println(path + " does not exists.");
                result =  null;
            }
            else {
                // in case of MP3(single file)
                if ( path.toUpperCase().endsWith("MP3") ){
                    FileInfoBean fib = new FileInfoBean();
                    fib.setFilePath( path );
                    result.add( fib );
                }
                // in case of m3u playlist
                else if ( path.toUpperCase().endsWith("M3U") ){
                    boolean localEncode = false;
                    FileInputStream fileInputStream = new FileInputStream(file);
                    InputStreamReader inputStreamReader;
                    if( m3uEncode == null || m3uEncode.trim().equals("") )
                        inputStreamReader = new InputStreamReader(fileInputStream);
                    else
                        inputStreamReader = new InputStreamReader(fileInputStream,m3uEncode);
                    // System.err.println("Encode for M3U : " + m3uEncode);
                    BufferedReader reader = new BufferedReader( inputStreamReader );
                    //BufferedReader reader = new BufferedReader( new FileReader(file) );
                    String s;
                    String parent = file.getAbsoluteFile().getParent();
                    // EXTM3U flag
                    boolean extm3u = false;
                    String title = "";
                    String artist = "";
                    while( (s = reader.readLine()) != null){
                        //s = new String(s.getBytes("UTF8"),"JISAutoDetect");
                        s = s.trim();
                        
                        if(s.equals(""))
                            continue;

                        // EXTM3U 
                        if( s.startsWith("#EXTINF") ){
                            int n1 = s.indexOf(",");
                            int n2 = s.indexOf(" - ");
                            artist = s.substring(n1+1,n2);
                            title = s.substring(n2+3);
                            extm3u = true;
                        } else {
                            // System.out.println(s);
                            // When file separator is windows, 
                            // change it to unix file separator.
                            // while and String.replace() method is used in this source,
                            // because got errors when use String.replaceAll("\\","/").
                            if( unix ){
                                while(s.indexOf("\\") >= 0)
                                    s = s.replace("\\", File.separator);
                            }
                        
                            FileInfoBean fib = new FileInfoBean();
                            File f = new File(s);
                            if( f.exists() ){
                                fib.setFilePath( s );
                                if ( extm3u )
                                    fib.setID3Tag(getReducedID3Tag(artist,title));
                                else 
                                    tagRead(fib,encode);
                                result.add( fib );
                            }
                            else if( s.startsWith("http:") ){
                                fib.setURL( s );
                                if ( extm3u )
                                    fib.setID3Tag(getReducedID3Tag(artist,title));
                                result.add( fib );
                            }
                            else {
                                f = new File( parent + File.separator + s );
                                if( f.exists() ){
                                    fib.setFilePath( parent + File.separator + s );
                                    if ( extm3u )
                                        fib.setID3Tag(getReducedID3Tag(artist,title));
                                    else 
                                        tagRead(fib,encode);
                                    //tagRead(fib,encode);
                                    result.add( fib );
                                }
                            }
                            extm3u = false;
                            artist = "";
                            title = "";
                        }
                    }
                }
                else
                    result = null;                    
            }
        } catch ( Exception e ){
            e.printStackTrace();
            System.err.println(path);
            result = null;
        }
        
        return result;
    }

    private static ID3Tag getReducedID3Tag(String artist, String title){
        ID3Tag id3 = new ID3Tag();
        id3.setArtist(artist);
        id3.setTitle(title);
        return id3;
    }

    
    public static void tagRead(FileInfoBean fib){
        tagRead(fib,null);
    }

    // get ID3Tag information
    public static void tagRead(FileInfoBean fib,String encode){
        if( fib != null && fib.getFilePath() != null ){
            ID3Tag id3 = ID3TagReader.getID3Tag(fib.getFilePath(),encode);
            fib.setID3Tag(id3);
        }
    }

}
