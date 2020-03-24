/*
 * SetupSwingFrame.java
 *
 * Created on 2006/08/12, 23:09
 */

package org.jdesktop.lg3d.apps.trumplayer.utils;

import java.io.File;
import java.util.ResourceBundle;
import javax.swing.JFrame;

import org.jdesktop.lg3d.apps.trumplayer.Main;

/**
 *
 * @author  Yasuhiro Fujitsuki(thaniwa)
 */
public class SetupSwingFrame extends javax.swing.JFrame implements ConfiguratorEventTarget, UpdateActionAdapter {
    
    /** Creates new form SetupSwingFrame */
    public SetupSwingFrame() {
        initComponents();
        setup();
    }
    

    public enum Mode { NEW, ADD };
    private Mode mode = Mode.NEW;

    public SetupSwingFrame(Mode mode){
        this.mode = mode;
        initComponents();
        setup();
    }
    
    private UpdateAction updateEvent = null;
    
    public void setUpdateEvent(UpdateAction updateEvent){
        this.updateEvent = updateEvent;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        welcomePanel = new javax.swing.JPanel();
        welcomeTextArea = new javax.swing.JTextArea();
        fileChooserDialog = new javax.swing.JDialog();
        fileChooserPanel = new javax.swing.JPanel();
        fileChooser = new javax.swing.JFileChooser();
        folderSelectPanel = new javax.swing.JPanel();
        folderPanelTextArea = new javax.swing.JTextArea();
        folderPanelTextField = new javax.swing.JTextField();
        filechooserButton = new javax.swing.JButton();
        searchStatePanel = new javax.swing.JPanel();
        searchLabel = new javax.swing.JLabel();
        searchProgressBar = new javax.swing.JProgressBar();
        searchInformationTextArea = new javax.swing.JTextArea();
        completePanel = new javax.swing.JPanel();
        completeTextArea = new javax.swing.JTextArea();
        mainPanel = new javax.swing.JPanel();
        nameLabel = new javax.swing.JLabel();
        buttonPanel = new javax.swing.JPanel();
        actionButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        welcomePanel.setFont(new java.awt.Font("Dialog", 0, 14));
        welcomePanel.setMinimumSize(new java.awt.Dimension(360, 200));
        welcomePanel.setPreferredSize(new java.awt.Dimension(360, 200));
        welcomeTextArea.setBackground(new java.awt.Color(238, 238, 238));
        welcomeTextArea.setColumns(24);
        welcomeTextArea.setEditable(false);
        welcomeTextArea.setFont(new java.awt.Font("Dialog", 0, 14));
        welcomeTextArea.setLineWrap(true);
        welcomeTextArea.setRows(10);
        welcomeTextArea.setText("Configuration file of trumplayer, trumplayer.xml, was not found in  your home directory.\nIf you want to create trumplayer.xml with this wizard, clieck the next button.\n\nIf you want not to create trumplayer.xml or you want to run as demonstration mode, click the cancel button.");
        welcomePanel.add(welcomeTextArea);

        fileChooserDialog.getContentPane().setLayout(new java.awt.FlowLayout());

        fileChooserDialog.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        fileChooserDialog.setTitle("Folder/Directory Chooser for Trumplayer");
        fileChooserDialog.setAlwaysOnTop(true);
        fileChooserDialog.setName("Folder/Directory Chooser");
        fileChooserPanel.setMinimumSize(new java.awt.Dimension(530, 336));
        fileChooserPanel.setPreferredSize(new java.awt.Dimension(530, 336));
        fileChooser.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);
        fileChooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileChooserActionPerformed(evt);
            }
        });

        fileChooserPanel.add(fileChooser);

        fileChooserDialog.getContentPane().add(fileChooserPanel);

        folderSelectPanel.setLayout(new java.awt.GridBagLayout());

        folderSelectPanel.setMinimumSize(new java.awt.Dimension(360, 200));
        folderSelectPanel.setPreferredSize(new java.awt.Dimension(360, 200));
        folderSelectPanel.setRequestFocusEnabled(false);
        folderPanelTextArea.setBackground(new java.awt.Color(238, 238, 238));
        folderPanelTextArea.setColumns(28);
        folderPanelTextArea.setEditable(false);
        folderPanelTextArea.setFont(new java.awt.Font("Dialog", 0, 14));
        folderPanelTextArea.setLineWrap(true);
        folderPanelTextArea.setRows(4);
        folderPanelTextArea.setText("Please input folder which has mp3 files into the field.\nAfter input folder, please click the next button.");
        folderPanelTextArea.setMinimumSize(new java.awt.Dimension(280, 80));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        folderSelectPanel.add(folderPanelTextArea, gridBagConstraints);

        folderPanelTextField.setColumns(40);
        folderPanelTextField.setFont(new java.awt.Font("Dialog", 0, 14));
        folderPanelTextField.setMinimumSize(new java.awt.Dimension(200, 21));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 6;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 2);
        folderSelectPanel.add(folderPanelTextField, gridBagConstraints);

        filechooserButton.setText("File Chooser");
        filechooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filechooserButtonActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        folderSelectPanel.add(filechooserButton, gridBagConstraints);

        searchStatePanel.setMinimumSize(new java.awt.Dimension(360, 200));
        searchStatePanel.setPreferredSize(new java.awt.Dimension(360, 200));
        searchStatePanel.setRequestFocusEnabled(false);
        searchLabel.setFont(new java.awt.Font("Dialog", 1, 14));
        searchLabel.setText("Creating trumplayer.xml...");
        searchLabel.setMaximumSize(new java.awt.Dimension(320, 40));
        searchLabel.setMinimumSize(new java.awt.Dimension(320, 40));
        searchLabel.setPreferredSize(new java.awt.Dimension(320, 40));
        searchStatePanel.add(searchLabel);

        searchProgressBar.setFont(new java.awt.Font("Dialog", 1, 14));
        searchProgressBar.setMinimumSize(new java.awt.Dimension(320, 30));
        searchProgressBar.setPreferredSize(new java.awt.Dimension(320, 30));
        searchProgressBar.setStringPainted(true);
        searchStatePanel.add(searchProgressBar);

        searchInformationTextArea.setBackground(new java.awt.Color(238, 238, 238));
        searchInformationTextArea.setColumns(28);
        searchInformationTextArea.setEditable(false);
        searchInformationTextArea.setLineWrap(true);
        searchInformationTextArea.setRows(5);
        searchInformationTextArea.setText("Searching : ");
        searchStatePanel.add(searchInformationTextArea);

        completePanel.setMinimumSize(new java.awt.Dimension(360, 200));
        completePanel.setOpaque(false);
        completePanel.setPreferredSize(new java.awt.Dimension(320, 200));
        completePanel.setRequestFocusEnabled(false);
        completeTextArea.setBackground(new java.awt.Color(238, 238, 238));
        completeTextArea.setColumns(24);
        completeTextArea.setEditable(false);
        completeTextArea.setFont(new java.awt.Font("Dialog", 0, 14));
        completeTextArea.setLineWrap(true);
        completeTextArea.setRows(10);
        completeTextArea.setText("The setup was completed. \nPlease push the okay button to start trumplayer.\n");
        completePanel.add(completeTextArea);

        getContentPane().setLayout(new java.awt.GridBagLayout());

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Setup wizard for trumplayer");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        mainPanel.setMinimumSize(new java.awt.Dimension(360, 200));
        mainPanel.setPreferredSize(new java.awt.Dimension(360, 200));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        getContentPane().add(mainPanel, gridBagConstraints);

        nameLabel.setFont(new java.awt.Font("Dialog", 1, 18));
        nameLabel.setText("Trumplayer for LG3D");
        nameLabel.setMaximumSize(new java.awt.Dimension(320, 22));
        nameLabel.setMinimumSize(new java.awt.Dimension(240, 22));
        nameLabel.setPreferredSize(new java.awt.Dimension(240, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        getContentPane().add(nameLabel, gridBagConstraints);

        buttonPanel.setMinimumSize(new java.awt.Dimension(200, 40));
        actionButton.setText("next");
        actionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionButtonActionPerformed(evt);
            }
        });

        buttonPanel.add(actionButton);

        cancelButton.setText("cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        buttonPanel.add(cancelButton);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        getContentPane().add(buttonPanel, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void fileChooserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileChooserActionPerformed
        if( evt.getActionCommand().trim().equals("ApproveSelection") ){
            try{
                File file = this.fileChooser.getSelectedFile();
                if( file != null && file.isDirectory() ){
                    String path = file.getCanonicalPath();
                    this.folderPanelTextField.setText( path );
                    selectedFolder = file;
                }
            } catch (java.io.IOException e){
            }
        }
        fileChooserDialog.dispose();
    }//GEN-LAST:event_fileChooserActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        this.windowCloseAction();
    }//GEN-LAST:event_formWindowClosing

    private void filechooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filechooserButtonActionPerformed

        fileChooserDialog.pack();
        fileChooserDialog.setVisible(true);

        /* ****
         * The default way to use JFileChooser is as follows,
         * but LG3D 0.9.0(Sep, 2006) does not support JFileChooser.showOpenDialog.
         * Therefore, trumplayer implements fileChooserActionPerformed() method 
         * and use JDialog.pack(), JDialog.setVisible(true)
         * instead of using JFileChooser.showOpenDialog.
         *
        int status = fileChooser.showOpenDialog(fileChooserDialog);

        if ( status == fileChooser.APPROVE_OPTION ){
            try{
                File file = this.fileChooser.getSelectedFile();
                if( file != null && file.isDirectory() ){
                    String path = file.getCanonicalPath();
                    this.folderPanelTextField.setText( path );
                    selectedFolder = file;
                }
            } catch (java.io.IOException e){
            }
        }
        **** */
    }//GEN-LAST:event_filechooserButtonActionPerformed

    private void windowCloseAction(){
        if( isVisible ){
            isVisible = false;
            this.searchThread = null;
            this.fileChooserDialog.dispose();
            //frame.setVisible(false);
            frame.dispose();
            //System.exit(0);

            if(this.updateEvent != null)
                updateEvent.performUpdateAction();
            
            if( mode == Mode.NEW )
                Main.runTrumplayer();
        }
    }
    
    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        windowCloseAction();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void actionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actionButtonActionPerformed
        if(stage == Stage.WELCOME){
            stage = Stage.FILECHOOSE;
            // remove the welcome message panel
            mainPanel.remove(welcomePanel);
            // add the folder/directory chooser panel
            mainPanel.add(folderSelectPanel);
            pack();
        } else if (stage == Stage.FILECHOOSE){
            if( folderSelected() ){
                stage = Stage.SEARCH;
                // remove the folder/directory chooser panel
                mainPanel.remove(folderSelectPanel);
                // add the search information panel
                mainPanel.add(searchStatePanel);
                actionButton.setEnabled(false);
                pack();
                search();
            }
        } else if (stage == Stage.SEARCH ){
            stage = Stage.COMPLETE;
            
            if( configStatus == AutomaticConfigurator.Status.SUCCESS )
                completeTextArea.setText(message_complete_success);
            else if( configStatus == AutomaticConfigurator.Status.FAILED_XML )
                completeTextArea.setText(message_complete_failed_xml);
            else if( configStatus == AutomaticConfigurator.Status.NOT_FOUND )
                completeTextArea.setText(message_complete_notfound);
            
            // remove the folder/directory chooser panel
            mainPanel.remove(searchStatePanel);
            // add the search information panel
            mainPanel.add(completePanel);
            // change status of buttons
            actionButton.setVisible(false);
            cancelButton.setText(message_okay);
            cancelButton.setEnabled(true);
            pack();
        }

    }//GEN-LAST:event_actionButtonActionPerformed

    public boolean folderSelected(){
        boolean result = false;
        if( selectedFolder == null ){
            String s = this.folderPanelTextField.getText();
            File file = new File(s);
            if( file.isDirectory() ){
                selectedFolder = file;
                result = true;
            }
        } else { result = true; }
        return result;
    }

    private class SearchThread extends Thread {
        private SetupSwingFrame f;
        public SearchThread(SetupSwingFrame f){
            this.f = f;
        }
        public void run(){
            SwingEventAutomaticConfigurator ac = SwingEventAutomaticConfigurator.getConfigurator();
            ac.setTarget(f);
            if( mode == SetupSwingFrame.Mode.ADD )
                configStatus = ac.createConfigFiles(f.selectedFolder,AutomaticConfigurator.Mode.ADD);
            else
                configStatus = ac.createConfigFiles(f.selectedFolder,AutomaticConfigurator.Mode.NEW);
            // searchThread = null;
        }
    };

    private AutomaticConfigurator.Status configStatus = AutomaticConfigurator.Status.SUCCESS;

    public AutomaticConfigurator.Status getConfigStatus(){
        return configStatus;
    }

    private SearchThread searchThread = null;
    
    public void search(){
        searchThread = new SearchThread(this);
        searchThread.start();
    }
    
    public void setProgress(int progress){
        if( progress == -1 ){
            this.searchProgressBar.setValue(0);
            this.searchProgressBar.setString("");
        }
        else {
            this.searchProgressBar.setValue(progress);
            this.searchProgressBar.setString(progress + "%");
        }
        if(progress == 100 || progress == -1){
            this.cancelButton.setEnabled(false);
            this.actionButton.setEnabled(true);
        }
        pack();
    }

    public void setInformations(String information){
        this.searchInformationTextArea.setText(information);
        pack();
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SetupSwingFrame().setVisible(true);
            }
        });
    }


    private boolean isVisible = true;
    private JFrame frame = this;
    
    public enum Stage { WELCOME, FILECHOOSE, SEARCH, COMPLETE };
    private Stage stage = Stage.WELCOME;
    
    public Stage getStage(){ return stage; }
    
    private File selectedFolder = null;
    
    private void setup(){
        mainPanel.add(welcomePanel);
        this.fileChooserDialog.setLocation(300,300);

        ResourceBundle resource = ResourceBundle.getBundle("org.jdesktop.lg3d.apps.trumplayer.resources.messages");
        if(resource != null){
            // okayButton.setText(resource.getString("mp3pluginWarningFrame.okay"));
            // messageArea.setText(resource.getString("mp3pluginWarningFrame.warning"));
            message_okay = resource.getString("SetupSwingFrame.okay");
            
            this.actionButton.setText(resource.getString("SetupSwingFrame.next"));
            this.cancelButton.setText(resource.getString("SetupSwingFrame.cancel"));
            this.filechooserButton.setText(resource.getString("SetupSwingFrame.filechooserButton"));
            this.folderPanelTextArea.setText(resource.getString("SetupSwingFrame.folderPanelTextArea"));
            this.searchLabel.setText(resource.getString("SetupSwingFrame.searchLabel"));
            
            if( mode == mode.ADD ){
                this.welcomeTextArea.setText(resource.getString("SetupSwingFrame.welcomeTextArea_AddMode"));
                message_complete_failed_xml = resource.getString("SetupSwingFrame.completeTextArea_Failed_XML_AddMode");
                message_complete_notfound = resource.getString("SetupSwingFrame.completeTextArea_NotFound_AddMode");
                message_complete_success = resource.getString("SetupSwingFrame.completeTextArea_Success_AddMode");
            }
            else{
                this.welcomeTextArea.setText(resource.getString("SetupSwingFrame.welcomeTextArea"));
                message_complete_failed_xml = resource.getString("SetupSwingFrame.completeTextArea_Failed_XML");
                message_complete_notfound = resource.getString("SetupSwingFrame.completeTextArea_NotFound");
                message_complete_success = resource.getString("SetupSwingFrame.completeTextArea_Success");
            }
            
        }

    }
    
    private String message_okay = "okay";
    private String message_complete_failed_xml;
    private String message_complete_notfound;
    private String message_complete_success;
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton actionButton;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JButton cancelButton;
    private javax.swing.JPanel completePanel;
    private javax.swing.JTextArea completeTextArea;
    private javax.swing.JFileChooser fileChooser;
    private javax.swing.JDialog fileChooserDialog;
    private javax.swing.JPanel fileChooserPanel;
    private javax.swing.JButton filechooserButton;
    private javax.swing.JTextArea folderPanelTextArea;
    private javax.swing.JTextField folderPanelTextField;
    private javax.swing.JPanel folderSelectPanel;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JTextArea searchInformationTextArea;
    private javax.swing.JLabel searchLabel;
    private javax.swing.JProgressBar searchProgressBar;
    private javax.swing.JPanel searchStatePanel;
    private javax.swing.JPanel welcomePanel;
    private javax.swing.JTextArea welcomeTextArea;
    // End of variables declaration//GEN-END:variables
    
}
