/*
 * ID3TagReader.java
 *
 * Created on 2003/11/28, 11:16
 *
 */

package org.jdesktop.lg3d.apps.trumplayer.mp3util;

import java.io.*;
import java.util.Arrays;

/**
 *
 * @author Yasuhiro Fujitsuki (thaniwa)
 */
public class ID3TagReader {

    private static ID3TagReader reader = null;

    /** encode information */
    private String encode = null;
    
    /** Creates a new instance of ID3TagReader */
    protected ID3TagReader() {
    }

    // Singleton Pattern
    public static ID3TagReader getInstance(){
        getInstance(null);
        return reader;
    }
    
    public static ID3TagReader getInstance(String encode){
        if(reader == null)
        reader = new ID3TagReader();
        if(encode != null && encode.equals(""))
            reader.setEncode(null);
        else
            reader.setEncode(encode);

        return reader;
    }
    
    private void setEncode(String encode){
        this.encode = encode;
    }

    // for Test
    public static void main(String args[]){

        ID3TagReader i = ID3TagReader.getInstance("JISAutoDetect");
        // System.out.println( args.length );
        for(int t=0; args != null && t < args.length; t++){
            ID3Tag tag = i.read( args[t] );
            // System.out.println(tag.toString());
            System.out.println( "INSERT INTO mp3 VALUES ("
                + "'" + tag.getArtist().toUpperCase() + "'" + " , " + "'" + tag.getTitle().toUpperCase() + "'" + " , "
                + "'" + tag.getAlbum().toUpperCase() + "'" + " , " + "'" + tag.getYear() + "'" + " , "
                + "'" + tag.getGenre() + "'" + " , " + "'" + tag.getComment() + "'" + " , "
                + "track:" + tag.getTrack() + ","
                + "'" + "URL:" + args[t]+ "'" +");" );
            if( tag.getTagVersion() == tag.ID3V2 ){
                System.out.println( tag.getImageType() );
            }
        }
    }

    /** simple version of ID3Tag.read() method.
     */
    public static ID3Tag getID3Tag( String filename ){
         ID3TagReader r = ID3TagReader.getInstance();
         return r.read( filename );
    }
    
    /** simple version of ID3Tag.read() method.
     * This method requires encode information.
     */
    public static ID3Tag getID3Tag( String filename, String encode){
         ID3TagReader r = ID3TagReader.getInstance(encode);
         return r.read( filename );
    }

    // This version can read ID3v1 tag only.
    // Cannot read ID3v2 tag...
    public ID3Tag read( String filename ){

        File file = new File( filename );

        ID3Tag result = null;

        if( file.exists() ){
            result = readID3V2(file);
            // read ID3Tag V1 when ID3Tag V2 cannot found.
            if(result == null)
                result = readID3V1(file);
        }

        return result;

    }
    
    private ID3Tag readID3V2(File file){
        if(file == null || file.exists() == false)
            return null;

        ID3Tag result = null;
        
        try{
            BufferedInputStream is = new BufferedInputStream(new FileInputStream(file));
            
            // read ID3Tag header (10byte)
            byte buffer[] = new byte[10];
            is.read(buffer);
            // found ID3 Tag V2
            if( new String(buffer,0,3).trim().equals("ID3") ){
                result = new ID3Tag();

                // ID3 Tag version
                //String version = "2." + new Integer(buffer[3]) +"."+ new Integer(buffer[4]);
                //System.out.println("ID3 Version "+ version );
                int subversion = new Integer(buffer[3]);
                result.setTagVersion(ID3Tag.ID3V2);

                // frame size
                int size = new Integer(buffer[7]).intValue() * 65536 +
                        new Integer(buffer[8]).intValue() * 256 + new Integer(buffer[9]).intValue() + 10;
                
                //System.out.println("buffer = " + size);

                // make new buffer to read id3 tag informations
                buffer = new byte[size];
                is.read(buffer);
                
                if(subversion > 2)
                    readID3V23( result, buffer, size );
                else readID3V22(result, buffer,size );
            }
        } catch(Exception e){
            e.printStackTrace();
            result = null;
        }
        // return result;
        return result;
    }

    private void readID3V22(ID3Tag result, byte[] buffer, int size) throws Exception{
        // track information first byte
        byte tracks[] = "T".getBytes();
        byte track = tracks[0];

        // PIC
        byte tracksPIC[] = "P".getBytes();
        byte trackPIC = tracksPIC[0];
                
        // search track Informations
        int length;
        String string;

        //encode = "JISAutoDetect";
        for(int i=0; i< size-10; i++){
            if( buffer[i] == track ) {
                //System.out.println("search : "+ i);
                String tmpString = null;
                int number = 0;
                if( new String(buffer,i,3).equals("TP1") ){ // Artist Name
                    tmpString = getStringID3V22(buffer,i);
                    result.setArtist( tmpString );
                }
                else if (new String(buffer,i,3).equals("TT2") ){ // Song's Title
                    tmpString = getStringID3V22(buffer,i);
                    result.setTitle( tmpString );
                }
                else if (new String(buffer,i,3).equals("TAL") ){ // Album's Title
                    tmpString = getStringID3V22(buffer,i);
                    result.setAlbum( tmpString );
                }
                else if (new String(buffer,i,3).equals("TRK") ){ // Track Number
                    tmpString = getStringID3V22(buffer,i);
                    result.setTrack( tmpString );
                }
                else if (new String(buffer,i,3).equals("TCO") ){ // Genre
                    tmpString = getStringID3V22(buffer,i);
                    result.setGenre( tmpString );
                }
                else if (new String(buffer,i,3).equals("TYE") ){ // Year
                    tmpString = getStringID3V22(buffer,i);
                    result.setYear( tmpString );
                }

                if(tmpString != null){
                    i = i + tmpString.length() + 5;
                }

            } else if( buffer[i] == trackPIC ){ 
                // for ID3 V2.2 Additional Picture
                if( new String(buffer,i,3).equals("PIC") ){
                    getImageFromID3V22(buffer, i, result);
                }
            }
        }
    }

    private void readID3V23(ID3Tag result, byte[] buffer,int size ) throws Exception {
        // track information first byte
        byte tracks[] = "T".getBytes();
        byte track = tracks[0];

        // PIC
        byte tracksPIC[] = "A".getBytes();
        byte trackPIC = tracksPIC[0];
                
        // search track Informations
        int length;
        String string;

        //encode = "JISAutoDetect";
        for(int i=0; i< size-10; i++){
            if( buffer[i] == track ) {
                //System.out.println("search : "+ i);
                String tmpString = null;
                int number = 0;
                if( new String(buffer,i,4).equals("TPE1") ){ // Artist Name
                    tmpString = getStringID3V23(buffer,i);
                    result.setArtist( tmpString );
                }
                else if (new String(buffer,i,4).equals("TIT2") ){ // Song's Title
                    tmpString = getStringID3V23(buffer,i);
                    result.setTitle( tmpString );
                }
                else if (new String(buffer,i,4).equals("TALB") ){ // Album's Title
                    tmpString = getStringID3V23(buffer,i);
                    result.setAlbum( tmpString );
                }
                else if (new String(buffer,i,4).equals("TRCK") ){ // Track Number
                    tmpString = getStringID3V23(buffer,i);
                    result.setTrack( tmpString );
                }
                else if (new String(buffer,i,4).equals("TCON") ){ // Genre
                    tmpString = getStringID3V23(buffer,i);
                    result.setGenre( tmpString );
                }
                else if (new String(buffer,i,4).equals("TYER") ){ // Year
                    tmpString = getStringID3V23(buffer,i);
                    result.setYear( tmpString );
                }
                
                if(tmpString != null){
                    i = i + tmpString.length() + 9;
                }
                
            } else if( buffer[i] == trackPIC ){ 
                // for ID3 V2.2 Additional Picture
                if( new String(buffer,i,4).equals("APIC") ){
                    getImageFromID3V23(buffer, i, result);
                }
            }
        }
    }

    
    public void getImageFromID3V22(byte[] buffer, int offset, ID3Tag id3){
        int frameSize = 0;
        // analyze frame size
        Byte b1 = new Byte(buffer[offset+3]);
        Byte b2 = new Byte(buffer[offset+4]);
        Byte b3 = new Byte(buffer[offset+5]);
        frameSize = b1.intValue() * 65536 + b2.intValue() * 256 + b3.intValue();
        
        // analyze image type
        String type = new String(buffer,offset+7,3);
        if(type.equalsIgnoreCase("JPG"))
            id3.setImageType(ID3Tag.JPEG);
        else if(type.equalsIgnoreCase("PNG"))
            id3.setImageType(ID3Tag.PNG);
        else if(type.equalsIgnoreCase("-->"))
            id3.setImageType(ID3Tag.URL);
        
        // picture type
        int pictureType = (new Byte(buffer[offset+10])).intValue();

        // skip text strings
        int current = 11;
        for(current=11; current < buffer.length; current++){
            int iValue = (new Byte(buffer[offset+current])).intValue();
            if(iValue == 0){ 
                current++; 
                break;
            }
        }
        // skip when current byte is 00 (end value of text string are 00 or 00 00)
        if( (new Byte(buffer[offset+current+1])).intValue() == 0 )
            current ++;
        
        // copy data
        byte result[] = new byte[ frameSize ];
        
        try{
            System.arraycopy( buffer,offset+current, result, 0, frameSize-1 );
        } catch (Exception e){
            e.printStackTrace();
            System.err.println( id3.getArtist() + " " + id3.getTitle());
        }

        if( ! id3.getImageType().equals(ID3Tag.URL) ){
            id3.setImage(result);
        }
        
    }
    
    public void getImageFromID3V23(byte[] buffer, int offset, ID3Tag id3){
        int frameSize = 0;
        // analyze frame size
        Byte b1 = new Byte(buffer[offset+5]);
        Byte b2 = new Byte(buffer[offset+6]);
        Byte b3 = new Byte(buffer[offset+7]);
        frameSize = b1.intValue() * 65536 + b2.intValue() * 256 + b3.intValue();
        
        int pointer = 11 + offset;
        for( ; pointer < buffer.length; pointer++ ){
            if( (new Byte(buffer[pointer])).intValue() == 0 )
                break;
        }
        
        // analyze image type
        String type = new String(buffer,offset+10,pointer -offset-10);
        if( type.indexOf("jpeg") > 0)
            id3.setImageType(ID3Tag.JPEG);
        else if(type.indexOf("png") > 0)
            id3.setImageType(ID3Tag.PNG);
        else if(type.equalsIgnoreCase("-->"))
            id3.setImageType(ID3Tag.URL);
        
        // picture type
        int pictureType = (new Byte(buffer[pointer+1])).intValue();

        // skip text strings
        int current = pointer+2;
        for( ; current < buffer.length; current++){
            int iValue = (new Byte(buffer[current])).intValue();
            if(iValue == 0){
                current++; 
                break;
            }
        }
        // skip when current byte is 00 (end value of text string are 00 or 00 00)
        if( (new Byte(buffer[offset+current+1])).intValue() == 0 )
            current ++;
        
        // copy data
        byte result[] = new byte[ frameSize ];
        
        try{
            System.arraycopy( buffer, current, result, 0, frameSize-1 );
        } catch (Exception e){
            e.printStackTrace();
            System.err.println( id3.getArtist() + " " + id3.getTitle());
        }

        if( ! id3.getImageType().equals(ID3Tag.URL) ){
            id3.setImage(result);
        }
        
    }
    
    // read string from buffer : for ID3 V2
    private String getStringID3V23(byte[] buffer, int offset){
        String string = null;
        int length = new Integer(buffer[(offset+7)]).intValue();
        int encNo = new Integer(buffer[(offset+10)]).intValue();
        
        try {
            if(encode == null){
                string = (new String(buffer,offset+11,length-1)).trim();
            } else {
                if(encNo == 0)
                    string = (new String(buffer,offset+11,length-1,encode)).trim();
                else if(encNo == 1)
                    string = (new String(buffer,offset+11,length-3,"UTF-16")).trim(); // end string : 00 00
                // for ID3 V2.4 : untested
                else if(encNo == 2)
                    string = (new String(buffer,offset+11,length-3,"UTF-16BE")).trim(); // end string : 00 00
                else if(encNo == 3)
                    string = (new String(buffer,offset+11,length-2,"UTF-8")).trim(); // end string : 00
            }
        } catch (Exception e){
            string = (new String(buffer,offset+11,length-1)).trim();
        }
        
        // offset += 9 + length;
        return string;
    }

    // read string from buffer : for ID3 V2
    private String getStringID3V22(byte[] buffer, int offset){
        String string = null;
        int length = new Integer(buffer[(offset+5)]).intValue() 
            + new Integer(buffer[(offset+4)]).intValue() * 256
                + new Integer(buffer[(offset+3)]).intValue() * 65536 ;
        // if( offset+ 6+length > buffer.length)
        //    length -= offset+6+length - buffer.length ;
        int encNo = new Integer(buffer[(offset+6)]).intValue();

        try {
            if(encode == null){
                string = (new String(buffer,offset+7,length-1)).trim();
            } else {
                if(encNo == 0)
                    string = (new String(buffer,offset+7,length-1,encode)).trim();
                else
                    string = (new String(buffer,offset+7,length-3,"UTF-16")).trim(); // cut end string : 00 00 
            }
        } catch (Exception e){
            string = (new String(buffer,offset+7,length-1)).trim();
        }
        
        // offset += 5 + length;
        return string;
    }

    private ID3Tag readID3V1(File file){

        if(file == null || file.exists() == false)
            return null;
        
        ID3Tag result = null;

        // read ID3Tag Version 1.0/1.1
        long pointer = file.length();
        pointer -= 128;
        try {
            FileInputStream fis = new FileInputStream( file );
            fis.skip(pointer);
            // check whether mp3 file has ID3v1 tag or not.
            byte buffer[] = new byte[3];
            fis.read(buffer,0,3);
            // Read ID3v1 Tag info when buffer is "TAG".
            if( (new String(buffer)).trim().equals("TAG") ){
                result = new ID3Tag();

                // read title
                buffer = new byte[30];
                fis.read(buffer,0, 30);
                result.setTitle( makeString(buffer).trim() );

                // read artist
                buffer = new byte[30];
                fis.read(buffer,0, 30);
                result.setArtist( makeString(buffer).trim() );

                // read album
                buffer = new byte[30];
                fis.read(buffer,0, 30);
                result.setAlbum( makeString(buffer).trim() );

                // read year
                buffer = new byte[4];
                fis.read(buffer,0, 4);
                result.setYear( makeString(buffer).trim() );

                buffer = new byte[30];
                fis.read(buffer,0, 30);

                // check ID3v1.0 or ID3v1.1
                if( buffer[28] == '\0' ){
                    // ID3v1.1
                    if(encode == null){
                        result.setComment( (new String(buffer,0,28)).trim() );
                        result.setTrack( (new Byte(buffer[29])).toString() );
                    } else {
                        result.setComment( (new String(buffer,0,28,encode)).trim() );
                        result.setTrack( (new Byte(buffer[29])).toString() );
                    }
                    result.setTagVersion( ID3Tag.ID3V11 );
                } else{
                    // ID3v1.0
                    result.setComment( makeString(buffer).trim() );
                    result.setTagVersion( ID3Tag.ID3V1 );
                }

                // read genre
                int b = fis.read();
                result.setGenre( gCheck(b) );

            }
            fis.close();
        }
        catch( Exception e ){
            result = null;
        }

        return result;        
    }

    private String makeString(byte[] buffer) throws java.io.UnsupportedEncodingException {
        String result;
        if(encode == null)
            result = new String(buffer);
        else
            result = new String(buffer,encode);
        return result;
    }

    public String gCheck(int i){
        if(i == -1)
            return "none";
        else if(i > genre.length)
            return "unknown";
        else
            return genre[i];
    }

    private final static String genre[] = {
       "Blues",
       "Classic Rock",
       "Country",
       "Dance",
       "Disco",
       "Funk",
       "Grunge",
       "Hip-Hop",
       "Jazz",
       "Metal",
       "New Age",
       "Oldies",
       "Other",
       "Pop",
       "R&B",
       "Rap",
       "Reggae",
       "Rock",
       "Techno",
       "Industrial",
       "Alternative",
       "Ska",
       "Death Metal",
       "Pranks",
       "Soundtrack",
       "Euro-Techno",
       "Ambient",
       "Trip-Hop",
       "Vocal",
       "Jazz+Funk",
       "Fusion",
       "Trance",
       "Classical",
       "Instrumental",
       "Acid",
       "House",
       "Game",
       "Sound Clip",
       "Gospel",
       "Noise",
       "AlternRock",
       "Bass",
       "Soul",
       "Punk",
       "Space",
       "Meditative",
       "nstrumental Pop",
       "Instrumental Rock",
       "Ethnic",
       "Gothic",
       "Darkwave",
       "Techno-Industrial",
       "Electronic",
       "Pop-Folk",
       "Eurodance",
       "Dream",
       "Southern Rock",
       "Comedy",
       "Cult",
       "Gangsta",
       "Top 40",
       "Christian Rap",
       "Pop/Funk",
        "Jungle",
        "Native American",
        "Cabaret",
        "New Wave",
        "Psychadelic",
        "Rave",
        "Showtunes",
        "Trailer",
        "Lo-Fi",
        "Tribal",
        "Acid Punk",
        "Acid Jazz",
        "Polka",
        "Retro",
        "Musical",
        "Rock & Roll",
        "Hard Rock",
        "Folk",
        "Folk-Rock",
        "National Folk",
        "Swing",
        "Fast Fusion",
        "Bebob",
        "Latin",
        "Revival",
        "Celtic",
        "Bluegrass",
        "Avantgarde",
        "Gothic Rock",
        "Progressive Rock",
        "Psychedelic Rock",
        "Symphonic Rock",
        "Slow Rock",
        "Big Band",
        "Chorus",
        "Easy Listening",
        "Acoustic",
        "Humour",
        "Speech",
        "Chanson",
        "Opera",
        "Chamber Music",
        "Sonata",
        "Symphony",
        "Booty Bass",
        "Primus",
        "Porn Groove",
        "Satire",
        "Slow Jam",
        "Club",
        "Tango",
        "Samba",
        "Folklore",
        "Ballad",
        "Power Ballad",
        "Rhythmic Soul",
        "Freestyle",
        "Duet",
        "Punk Rock",
        "Drum Solo",
        "Acapella",
        "Euro-House",
        "Dance Hall"
    };

}
