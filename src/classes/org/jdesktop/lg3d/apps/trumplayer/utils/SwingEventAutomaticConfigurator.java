/*
 * SwingEventAutomaticConfigurator.java
 *
 * Created on 2006/08/11, 13:01
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.jdesktop.lg3d.apps.trumplayer.utils;

import java.io.File;
import java.util.ResourceBundle;

/**
 *
 * @author Yasuhiro Fujitsuki(thaniwa)
 */
public class SwingEventAutomaticConfigurator extends AutomaticConfigurator {
    
    private ConfiguratorEventTarget target = null;
    private int searchFolderNumber = 0;
    private int currentSearchFolder = 0;
    
    private String message_searching = "Searching";
    private String message_creating_xml = "Creating trumplayer.xml";
    private String message_creating_m3u = "Creating M3U files for internal use.";
    private String message_finished = "Creating trumplayer.xml is finished.";
    private String message_failed = "Failed to create a trumplayer.xml.";

    /** Creates a new instance of LG3DAutomaticConfigurator */
    protected SwingEventAutomaticConfigurator() {
    }

    public static SwingEventAutomaticConfigurator getConfigurator(){
        if(configurator == null)
            configurator = new SwingEventAutomaticConfigurator();
        configurator.initialize();
        return (SwingEventAutomaticConfigurator) configurator;
    }

    protected void initialize(){
        super.initialize();
        searchFolderNumber = 0;
        currentSearchFolder = 0;

        // read resources
        ResourceBundle resource = ResourceBundle.getBundle("org.jdesktop.lg3d.apps.trumplayer.resources.messages");
        if(resource != null){
            message_searching = resource.getString("SwingEventAutomaticConfigurator.search");
            message_creating_xml = resource.getString("SwingEventAutomaticConfigurator.creating_xml");
            message_creating_m3u = resource.getString("SwingEventAutomaticConfigurator.creating_m3u");
            message_finished = resource.getString("SwingEventAutomaticConfigurator.finished");
            message_failed = resource.getString("SwingEventAutomaticConfigurator.failed");
        }
    }
    
    public void setTarget(ConfiguratorEventTarget target){
        this.target = target;
    }

    public AutomaticConfigurator.Status createConfigFiles(File folder, String encode){
        searchFolderNumber = folderLength(folder) + 2;
        return super.createConfigFiles(folder, encode);
    }

    public AutomaticConfigurator.Status createConfigFiles(File folder, String encode, AutomaticConfigurator.Mode mode){
        searchFolderNumber = folderLength(folder) + 2;
        return super.createConfigFiles(folder, encode, mode);
    }
    
    private int folderLength(File folder){
        int result = 1;
        File folderChildren[] = folder.listFiles(dirFilter);
        if(folderChildren != null){
            for(int i=0; i<folderChildren.length; i++)
                result += folderLength(folderChildren[i]);
        }
        return result;
    }

    protected AlbumList createAlbumList(File folder, boolean recursive, String encode){
        // display search informations (progress bar and text)
        String folderName = "";
        try{
            folderName = folder.getCanonicalPath();
        } catch (java.io.IOException e){
        }

        currentSearchFolder++;
        target.setProgress( currentSearchFolder * 100 / searchFolderNumber );
        target.setInformations(message_searching + " " + folder);
        
        return super.createAlbumList(folder,recursive,encode);
    }
    
    protected boolean createInternalM3U(Album album) {
        // display search informations (progress bar and text)
        currentSearchFolder = searchFolderNumber -1;
        target.setProgress( currentSearchFolder * 100 / searchFolderNumber );
        target.setInformations(message_creating_m3u);

        return super.createInternalM3U(album);
    }
    
    protected boolean createTrumplayerXML(AlbumList albumList){
        // display search informations (progress bar and text)
        currentSearchFolder = searchFolderNumber;        
        target.setInformations(message_creating_xml);

        boolean b = super.createTrumplayerXML(albumList);
        updateInformation_TrumplayerXML(b);

        return b;
    }
    
    protected boolean modifyTrumplayerXML(AlbumList albumList){
        // display search informations (progress bar and text)
        currentSearchFolder = searchFolderNumber;        
        target.setInformations(message_creating_xml);

        boolean b = super.modifyTrumplayerXML(albumList);
        updateInformation_TrumplayerXML(b);

        return b;
        
    }
    
    protected void updateInformation_TrumplayerXML(boolean b){
        if( b ){
            target.setProgress( 100 );
            target.setInformations(message_finished);
        } else {
            target.setProgress( -1 );
            target.setInformations(message_failed);            
        }

        target.setProgress( currentSearchFolder * 100 / searchFolderNumber );
    }

}
