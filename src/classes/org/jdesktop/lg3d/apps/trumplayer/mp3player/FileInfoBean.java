/*
 * FileInfoBean.java
 *
 * Created on 2003/12/02, 14:17
 * Revised on 2005/11/04
 *
 */

package org.jdesktop.lg3d.apps.trumplayer.mp3player;

import org.jdesktop.lg3d.apps.trumplayer.mp3util.ID3Tag;

/**
 *
 * @author Yasuhiro Fujitsuki (thaniwa)
 */
public class FileInfoBean {

    /** Property : filePath */
    private String filePath;

    /** Property : uRL */
    private String uRL;
    
    /** Property : iD3Tag (mp3util.ID3Tag)
     * ID3Tag version 
     */
    private ID3Tag iD3Tag = null;

    /** Creates a new instance of fileInfoBean */
    public FileInfoBean() {
    }

    /** get filePath
     * @return String filePath
     *
     */
    public String getFilePath() {
        return this.filePath;
    }

    /** set filePath
     * @param String filePath
     *
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /** get uRL (URL) 
     * @return String uRL
     *
     */
    public String getURL() {
        return this.uRL;
    }

    /** set uRL
     * @param String uRL
     *
     */
    public void setURL(String uRL) {
        this.uRL = uRL;
    }

    /** get iD3Tag
     * @return mp3util.ID3Tag id3Tag
     *
     */
    public ID3Tag getID3Tag() {
        return this.iD3Tag;
    }

    /** set iD3Tag
     * @param mp3util.ID3Tag iD3Tag
     *
     */
    public void setID3Tag(ID3Tag iD3Tag) {
        this.iD3Tag = iD3Tag;
    }


}
