/*
 * ID3Tag.java
 *
 * Created on 2003/11/28, 11:08
 *
 */

package org.jdesktop.lg3d.apps.trumplayer.mp3util;

import java.beans.*;

/**
 * Base Class of ID3 Tag
 * @author Yasuhiro Fujitsuki (thaniwa)
 */
public class ID3Tag extends Object implements java.io.Serializable {

    private PropertyChangeSupport propertySupport;

    /** is not ID3 tag */
    public final static int NONE = 0;
    /** ID3 V1 */
    public final static int ID3V1 = 1;
    /** ID3 V1.1 */
    public final static int ID3V11 = 2;
    /** ID3 V2 */
    public final static int ID3V2 = 3;

    /** Property : title */
    private String title = "";

    /** Property : artist */
    private String artist = "";

    /** Property : album */
    private String album = "";

    /** Property : year */
    private String year = "";

    /** Property : comment */
    private String comment = "";

    /** Property : genre */
    private String genre = "";

    /** Property : track */
    private String track = null;

    /** Property : tagVersion */
    private int tagVersion = NONE;
    
    private byte[] image = null;
    
    private String imageString = "";

    private String imageType = "";

    public final static String JPEG = "JPEG";
    public final static String PNG = "PNG";
    public final static String URL = "URL";
    
    /** Creates new ID3Tag */
    public ID3Tag() {
        propertySupport = new PropertyChangeSupport( this );
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.removePropertyChangeListener(listener);
    }


    
    /** get title of music
     * @return String title
     *
     */
    public String getTitle() {
        return this.title;
    }

    /** set Title : String
     * @param String title
     *
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /** get artist name of music
     * @return String artist
     *
     */
    public String getArtist() {
        return this.artist;
    }

    /** set artist name
     * @param String artist
     *
     */
    public void setArtist(String artist) {
        this.artist = artist;
    }

    /** get album name
     * @return String album
     *
     */
    public String getAlbum() {
        return this.album;
    }

    /** set album name
     * @param String album
     *
     */
    public void setAlbum(String album) {
        this.album = album;
    }

    /** get year
     * @return String year
     *
     */
    public String getYear() {
        return this.year;
    }

    /** set year
     * @param String year
     *
     */
    public void setYear(String year) {
        this.year = year;
    }

    /** get comment
     * @return String comment
     *
     */
    public String getComment() {
        return this.comment;
    }

    /** set comment
     * @param String comment
     *
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /** get genre
     * @return String genre
     *
     */
    public String getGenre() {
        return this.genre;
    }

    /** set genre
     * @param String genre
     *
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    /** get Track number
     * @return String track
     *
     */
    public String getTrack() {
        return this.track;
    }

    /** set Track number
     * @param String track
     *
     */
    public void setTrack(String track) {
        this.track = track;
    }

    /** get tagVersion
     * @return String tagVersion
     *
     */
    public int getTagVersion() {
        return this.tagVersion;
    }

    /** set tagVersion
     * @param String tagVersion
     *
     */
    public void setTagVersion(int tagVersion) {
        this.tagVersion = tagVersion;
    }

    public void setImage(byte[] image){
        this.image = image;
    }
    
    public byte[] getImage(){
        return this.image;
    }

    public void setImageType(String imageType){
        this.imageType = imageType;
    }
    
    public String getImageType(){
        return imageType;
    }
    
    public void setImageString(String imageString){
        this.imageString = imageString;
    }
    
    public String getImageString(){
        return this.imageString;
    }
    
    /** for debug
     */
    public void print(){

        System.out.println("ID3 Tag Information : Basic Information");
        System.out.print("ID3 Tag Version : ");
        if( tagVersion == ID3V1 )
            System.out.println("v1.0");
        else if( tagVersion == ID3V11 )
            System.out.println("v1.1");
        else if( tagVersion == ID3V2 )
            System.out.println("v2.x");
        else
            System.out.println("none");
        System.out.println("Title : " + title);
        System.out.println("Artist : " + artist);
        System.out.println("Album : " + album);
        System.out.println("Year : " + year);
        System.out.println("Comment : " + comment);
        System.out.println("Genre : " + genre );

    }

}
