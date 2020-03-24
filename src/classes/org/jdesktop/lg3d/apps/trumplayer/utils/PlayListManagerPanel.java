/*
 * PlayListManagerPanel.java
 *
 * Created on 2007/06/03, 18:49
 */

package org.jdesktop.lg3d.apps.trumplayer.utils;

import java.io.File;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.JPanel;
import java.util.Vector;
import java.util.ResourceBundle;

import org.jdesktop.lg3d.apps.trumplayer.base.AlbumInformation;
import org.jdesktop.lg3d.apps.trumplayer.base.AlbumInformationReader;

/**
 *
 * @author  Yasuhiro Fujitsuki(thaniwa)
 */
public class PlayListManagerPanel extends javax.swing.JPanel implements UpdateAction, UpdateActionAdapter {
    
    /** Creates new form PlayListManagerPanel */
    public PlayListManagerPanel(PlayListManagerSwingNode manager) {
        this.manager = manager;
        initComponents();
        setup();
    }
    
    public JPanel getImageChooserPanel(){ return imageChooserPanel; }
    public JPanel getM3UChooserPanel(){ return m3uChooserPanel; }
    
    private PlayListManagerSwingNode manager = null;
    private int NO_COLUMN_SIZE = 50;

    private boolean changedAlbumList = false;
    private boolean updated = false;
    public boolean updatedConfigFile(){ return updated; }
    
    private UpdateAction updateEvent;
    public void setUpdateEvent(UpdateAction updateEvent){
        this.updateEvent = updateEvent;
    }

    // image filter
    private FileFilter imageFilter = new FileFilter() {
        public boolean accept(File f) {
            boolean result = false;
            if( f != null ) {
                if ( f.isDirectory() )
                    result = true;
                else {
                    String name = f.getName();
                    if( name != null && 
                            ( name.toLowerCase().endsWith(".jpg") ||
                              name.toLowerCase().endsWith(".png") ||
                            name.toLowerCase().endsWith(".gif") )
                    )
                        result = true;
                }
            }            
            return result;
        }
        public String getDescription(){
            return "Images(jpg,gif,png)";
        }
    };

    // mp3/m3u filter
    private FileFilter m3uFilter = new FileFilter() {
        public boolean accept(File f) {
            boolean result = false;
            if( f != null ){
                if( f.isDirectory() )
                    result = true;
                else {
                    String name = f.getName();
                    if( name != null && 
                        ( name.toLowerCase().endsWith(".m3u") ||
                          name.toLowerCase().endsWith(".mp3") )
                          )
                        result = true;
                }
            }
            return result;
        }
        public String getDescription(){
            return "m3u, mp3";
        }
    };
    
    private void setup(){

        setupTableHeader();
        
        // setup resource
        
        ResourceBundle resource = ResourceBundle.getBundle("org.jdesktop.lg3d.apps.trumplayer.resources.messages");
        if(resource != null){
            this.artistLabel.setText( resource.getString("PlayListManagerFrame.artistLabel") );
            this.albumLabel.setText( resource.getString("PlayListManagerFrame.albumLabel") );
            /*
            this.jMenu.setText( resource.getString("PlayListManagerFrame.jMenu") );
            this.wizardMenuItem.setText( resource.getString("PlayListManagerFrame.wizardMenuItem") );
            this.closeMenuItem.setText( resource.getString("PlayListManagerFrame.closeMenuItem") );
             */
            this.m3uButton.setText( resource.getString("PlayListManagerFrame.m3uButton") );
            this.imageButton.setText( resource.getString("PlayListManagerFrame.imageButton") );
            this.addButton.setText( resource.getString("PlayListManagerFrame.addButton") );
            this.removeButton.setText( resource.getString("PlayListManagerFrame.removeButton") );
            this.clearButton.setText( resource.getString("PlayListManagerFrame.clearButton") );
            this.wizardButton.setText( resource.getString("PlayListManagerFrame.wizardButton") );
            this.saveButton.setText( resource.getString("PlayListManagerFrame.saveButton") );
        }
        
        this.imageChooser.setFileFilter(this.imageFilter);
        this.m3uChooser.setFileFilter(this.m3uFilter);
        this.playlistTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        readAlbumInformations();
    }

    private void setupTableHeader(){

        playlistTable.getTableHeader().setReorderingAllowed(false);
        
        playlistTable.getTableHeader().addMouseListener(
            new MouseAdapter(){
                public void mouseClicked(MouseEvent e) {
                    int columnIndex=playlistTable.getTableHeader().columnAtPoint(e.getPoint());
                    sortPlaylistTable(columnIndex);
                    tableModel.fireTableDataChanged();
                }
        });
        
    }
    
    private void sortPlaylistTable(int column){
        Vector tableList = tableModel.getDataVector();

        // bubble sort
        for(int i=0; tableList != null && i < tableList.size(); i++){
            for(int t=tableList.size() -1; t > i; t--){
                Vector aList = (Vector)tableList.get(t);
                Vector bList = (Vector)tableList.get(t-1);
                
                Object aObject = aList.get(column);
                Object bObject = bList.get(column);

                int value = 0;
                if( aObject instanceof String ){
                    value = ((String)aObject).compareToIgnoreCase((String)bObject);
                    // System.out.println((String)aObject +" " + (String)bObject + " = " + value);
                } else if ( aObject instanceof Integer ){
                    value = ((Integer)aObject).intValue() - ((Integer)bObject).intValue();
                }
                
                if(value < 0){
                    tableModel.moveRow(t-1,t-1,t);
                    tableList = tableModel.getDataVector();
                    if( this.changedAlbumList == false){
                        this.changedAlbumList = true;
                        this.saveButton.setEnabled(true);
                    }
                }

            }
        }
            
    }

    private Vector<AlbumInformation> albumInformations = null;
    private DefaultTableModel tableModel;
    
    private void readAlbumInformations(){
        tableModel = (DefaultTableModel)playlistTable.getModel();
        AlbumInformationReader reader = AlbumInformationReader.getReader(TrumplayerDefaults.TRUMPLAYER_CONFIG_PATH, TrumplayerDefaults.TRUMPLAYER_LOCALHOME);
        if( reader != null ){
            AlbumInformation[] info = reader.getAlbumInformations();

            albumInformations = new Vector<AlbumInformation>();
            while( tableModel.getRowCount() != 0)
                tableModel.removeRow(0);
        
            if(info != null){
                for(int i=0; i< info.length; i++){
                    albumInformations.add(info[i]);
                    Integer integer = new Integer( i );  
                    // String newRow[] = {info[i].getArtist(), info[i].getTitle()};
                    Object[] newRow = { integer, info[i].getArtist(), info[i].getTitle() };
                    tableModel.addRow( newRow );
                    if(i == 1){
                        TableColumn col = playlistTable.getColumnModel().getColumn(0);
                        col.setMinWidth(NO_COLUMN_SIZE);
                        col.setMaxWidth(NO_COLUMN_SIZE);
                    }
                }
            }
        }
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        imageChooserPanel = new javax.swing.JPanel();
        imageLabel4Chooser = new javax.swing.JLabel();
        imageChooser = new javax.swing.JFileChooser();
        m3uChooserPanel = new javax.swing.JPanel();
        m3uChooser = new javax.swing.JFileChooser();
        jPanel1 = new javax.swing.JPanel();
        wizardButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        downButton = new javax.swing.JButton();
        upButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        playlistTable = new javax.swing.JTable();
        descPanel = new javax.swing.JPanel();
        imageLabel = new javax.swing.JLabel();
        dataPanel = new javax.swing.JPanel();
        artistLabel = new javax.swing.JLabel();
        albumLabel = new javax.swing.JLabel();
        m3uButton = new javax.swing.JButton();
        imageButton = new javax.swing.JButton();
        artistTextField = new javax.swing.JTextField();
        albumTextField = new javax.swing.JTextField();
        m3uTextField = new javax.swing.JTextField();
        imageTextField = new javax.swing.JTextField();
        buttonPanel = new javax.swing.JPanel();
        addButton = new javax.swing.JButton();
        removeButton = new javax.swing.JButton();
        clearButton = new javax.swing.JButton();

        imageChooserPanel.setFont(new java.awt.Font("Dialog", 0, 14));
        imageChooserPanel.setMaximumSize(new java.awt.Dimension(700, 350));
        imageChooserPanel.setMinimumSize(new java.awt.Dimension(700, 350));
        imageChooserPanel.setPreferredSize(new java.awt.Dimension(700, 350));
        imageLabel4Chooser.setFont(new java.awt.Font("Dialog", 1, 18));
        imageLabel4Chooser.setText("No Image");
        imageLabel4Chooser.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        imageLabel4Chooser.setMaximumSize(new java.awt.Dimension(160, 160));
        imageLabel4Chooser.setMinimumSize(new java.awt.Dimension(160, 160));
        imageLabel4Chooser.setPreferredSize(new java.awt.Dimension(160, 160));
        imageLabel4Chooser.setRequestFocusEnabled(false);
        imageChooserPanel.add(imageLabel4Chooser);

        imageChooser.setFont(new java.awt.Font("Dialog", 0, 14));
        imageChooser.setPreferredSize(new java.awt.Dimension(500, 326));
        imageChooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imageChooserActionPerformed(evt);
            }
        });
        imageChooser.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                imageChooserPropertyChange(evt);
            }
        });

        imageChooserPanel.add(imageChooser);

        m3uChooserPanel.setMinimumSize(new java.awt.Dimension(550, 350));
        m3uChooserPanel.setPreferredSize(new java.awt.Dimension(550, 350));
        m3uChooser.setFileSelectionMode(javax.swing.JFileChooser.FILES_AND_DIRECTORIES);
        m3uChooser.setFont(new java.awt.Font("Dialog", 0, 14));
        m3uChooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m3uChooserActionPerformed(evt);
            }
        });

        m3uChooserPanel.add(m3uChooser);

        setLayout(new java.awt.GridBagLayout());

        setMinimumSize(new java.awt.Dimension(700, 450));
        setPreferredSize(new java.awt.Dimension(700, 450));
        wizardButton.setFont(new java.awt.Font("Dialog", 1, 18));
        wizardButton.setText("Run Wizard");
        wizardButton.setMaximumSize(new java.awt.Dimension(160, 25));
        wizardButton.setMinimumSize(new java.awt.Dimension(160, 25));
        wizardButton.setPreferredSize(new java.awt.Dimension(160, 25));
        wizardButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wizardButtonActionPerformed(evt);
            }
        });

        jPanel1.add(wizardButton);

        saveButton.setFont(new java.awt.Font("Dialog", 1, 18));
        saveButton.setText("Save");
        saveButton.setEnabled(false);
        saveButton.setMaximumSize(new java.awt.Dimension(160, 25));
        saveButton.setMinimumSize(new java.awt.Dimension(160, 25));
        saveButton.setPreferredSize(new java.awt.Dimension(160, 25));
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        jPanel1.add(saveButton);

        jPanel2.setMaximumSize(new java.awt.Dimension(240, 25));
        jPanel2.setMinimumSize(new java.awt.Dimension(240, 25));
        jPanel2.setPreferredSize(new java.awt.Dimension(240, 25));
        jPanel1.add(jPanel2);

        downButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/jdesktop/lg3d/apps/trumplayer/resources/down.png")));
        downButton.setEnabled(false);
        downButton.setMaximumSize(new java.awt.Dimension(25, 25));
        downButton.setMinimumSize(new java.awt.Dimension(25, 25));
        downButton.setPreferredSize(new java.awt.Dimension(25, 25));
        downButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downButtonActionPerformed(evt);
            }
        });

        jPanel1.add(downButton);

        upButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/jdesktop/lg3d/apps/trumplayer/resources/up.png")));
        upButton.setEnabled(false);
        upButton.setMaximumSize(new java.awt.Dimension(25, 25));
        upButton.setMinimumSize(new java.awt.Dimension(25, 25));
        upButton.setPreferredSize(new java.awt.Dimension(25, 25));
        upButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                upButtonActionPerformed(evt);
            }
        });

        jPanel1.add(upButton);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        add(jPanel1, gridBagConstraints);

        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jScrollPane1.setAutoscrolls(true);
        jScrollPane1.setMaximumSize(new java.awt.Dimension(560, 180));
        jScrollPane1.setMinimumSize(new java.awt.Dimension(560, 180));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(560, 180));
        playlistTable.setFont(new java.awt.Font("Dialog", 0, 18));
        playlistTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "no", "artist", "album"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        playlistTable.setMinimumSize(new java.awt.Dimension(150, 160));
        playlistTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                playlistTableMousePressed(evt);
            }
        });

        jScrollPane1.setViewportView(playlistTable);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(jScrollPane1, gridBagConstraints);

        descPanel.setLayout(new java.awt.GridBagLayout());

        descPanel.setMaximumSize(new java.awt.Dimension(560, 120));
        descPanel.setMinimumSize(new java.awt.Dimension(560, 120));
        descPanel.setPreferredSize(new java.awt.Dimension(560, 120));
        imageLabel.setFont(new java.awt.Font("Dialog", 1, 18));
        imageLabel.setText("No Image");
        imageLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        imageLabel.setMaximumSize(new java.awt.Dimension(160, 160));
        imageLabel.setMinimumSize(new java.awt.Dimension(160, 160));
        imageLabel.setPreferredSize(new java.awt.Dimension(160, 160));
        imageLabel.setRequestFocusEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        descPanel.add(imageLabel, gridBagConstraints);

        dataPanel.setLayout(new java.awt.GridBagLayout());

        dataPanel.setMaximumSize(new java.awt.Dimension(440, 140));
        dataPanel.setMinimumSize(new java.awt.Dimension(440, 140));
        dataPanel.setOpaque(false);
        dataPanel.setPreferredSize(new java.awt.Dimension(440, 160));
        dataPanel.setRequestFocusEnabled(false);
        artistLabel.setFont(new java.awt.Font("Dialog", 1, 14));
        artistLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        artistLabel.setText("artist");
        artistLabel.setMaximumSize(new java.awt.Dimension(128, 25));
        artistLabel.setMinimumSize(new java.awt.Dimension(128, 25));
        artistLabel.setPreferredSize(new java.awt.Dimension(128, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(4, 6, 4, 6);
        dataPanel.add(artistLabel, gridBagConstraints);

        albumLabel.setFont(new java.awt.Font("Dialog", 1, 14));
        albumLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        albumLabel.setText("album");
        albumLabel.setFocusable(false);
        albumLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        albumLabel.setMaximumSize(new java.awt.Dimension(128, 25));
        albumLabel.setMinimumSize(new java.awt.Dimension(128, 25));
        albumLabel.setPreferredSize(new java.awt.Dimension(128, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(4, 6, 4, 6);
        dataPanel.add(albumLabel, gridBagConstraints);

        m3uButton.setFont(new java.awt.Font("Dialog", 1, 14));
        m3uButton.setText("m3u/folder");
        m3uButton.setMaximumSize(new java.awt.Dimension(160, 25));
        m3uButton.setMinimumSize(new java.awt.Dimension(160, 25));
        m3uButton.setPreferredSize(new java.awt.Dimension(160, 25));
        m3uButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m3uButtonActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(2, 6, 2, 6);
        dataPanel.add(m3uButton, gridBagConstraints);

        imageButton.setFont(new java.awt.Font("Dialog", 1, 14));
        imageButton.setText("image");
        imageButton.setMaximumSize(new java.awt.Dimension(160, 25));
        imageButton.setMinimumSize(new java.awt.Dimension(160, 25));
        imageButton.setPreferredSize(new java.awt.Dimension(160, 25));
        imageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imageButtonActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(2, 6, 2, 6);
        dataPanel.add(imageButton, gridBagConstraints);

        artistTextField.setFont(new java.awt.Font("Dialog", 0, 18));
        artistTextField.setMaximumSize(new java.awt.Dimension(240, 20));
        artistTextField.setMinimumSize(new java.awt.Dimension(240, 20));
        artistTextField.setPreferredSize(new java.awt.Dimension(240, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        dataPanel.add(artistTextField, gridBagConstraints);

        albumTextField.setFont(new java.awt.Font("Dialog", 0, 18));
        albumTextField.setMaximumSize(new java.awt.Dimension(240, 20));
        albumTextField.setMinimumSize(new java.awt.Dimension(240, 20));
        albumTextField.setPreferredSize(new java.awt.Dimension(240, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        dataPanel.add(albumTextField, gridBagConstraints);

        m3uTextField.setFont(new java.awt.Font("Dialog", 0, 18));
        m3uTextField.setMaximumSize(new java.awt.Dimension(240, 20));
        m3uTextField.setMinimumSize(new java.awt.Dimension(240, 20));
        m3uTextField.setPreferredSize(new java.awt.Dimension(240, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        dataPanel.add(m3uTextField, gridBagConstraints);

        imageTextField.setFont(new java.awt.Font("Dialog", 0, 18));
        imageTextField.setMaximumSize(new java.awt.Dimension(240, 20));
        imageTextField.setMinimumSize(new java.awt.Dimension(240, 20));
        imageTextField.setPreferredSize(new java.awt.Dimension(240, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        dataPanel.add(imageTextField, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.insets = new java.awt.Insets(0, 8, 0, 8);
        descPanel.add(dataPanel, gridBagConstraints);

        buttonPanel.setMinimumSize(new java.awt.Dimension(500, 35));
        buttonPanel.setPreferredSize(new java.awt.Dimension(500, 35));
        addButton.setFont(new java.awt.Font("Dialog", 1, 18));
        addButton.setText("add/modify");
        addButton.setMaximumSize(new java.awt.Dimension(160, 25));
        addButton.setMinimumSize(new java.awt.Dimension(160, 25));
        addButton.setPreferredSize(new java.awt.Dimension(160, 25));
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        buttonPanel.add(addButton);

        removeButton.setFont(new java.awt.Font("Dialog", 1, 18));
        removeButton.setText("remove");
        removeButton.setMaximumSize(new java.awt.Dimension(140, 25));
        removeButton.setMinimumSize(new java.awt.Dimension(140, 25));
        removeButton.setPreferredSize(new java.awt.Dimension(140, 25));
        removeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeButtonActionPerformed(evt);
            }
        });

        buttonPanel.add(removeButton);

        clearButton.setFont(new java.awt.Font("Dialog", 1, 18));
        clearButton.setText("clear");
        clearButton.setMaximumSize(new java.awt.Dimension(140, 25));
        clearButton.setMinimumSize(new java.awt.Dimension(140, 25));
        clearButton.setPreferredSize(new java.awt.Dimension(140, 25));
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });

        buttonPanel.add(clearButton);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        descPanel.add(buttonPanel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 2;
        gridBagConstraints.ipady = 75;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        add(descPanel, gridBagConstraints);

    }// </editor-fold>//GEN-END:initComponents

    private void m3uChooserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m3uChooserActionPerformed
        if( evt.getActionCommand().trim().equals("ApproveSelection") ){
            try {
                File file = this.m3uChooser.getSelectedFile();
                if ( file != null ){
                    String path = "";
                    if( file.getName().toLowerCase().endsWith(".mp3"))
                        path = file.getParentFile().getCanonicalPath();
                    else
                        path = file.getCanonicalPath();
                    this.m3uTextField.setText( path );
                    this.m3uTextField.setCaretPosition(0);
                }
            } catch ( java.io.IOException e){
            }
        }

        //m3uChooserDialog.dispose();
        manager.switchToMainMode();
    }//GEN-LAST:event_m3uChooserActionPerformed

    private void imageChooserPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_imageChooserPropertyChange
        File file = imageChooser.getSelectedFile();
        changeImage(this.imageLabel4Chooser, file);
    }//GEN-LAST:event_imageChooserPropertyChange

    private void imageChooserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imageChooserActionPerformed
// imageChooserDialog.setVisible(false);
        if( evt.getActionCommand().trim().equals("ApproveSelection") ){
            try {
                File file = this.imageChooser.getSelectedFile();
                this.changeImage( this.imageLabel, file );
                if ( file != null ){
                    this.imageTextField.setText( file.getCanonicalPath() );
                    this.imageTextField.setCaretPosition(0);
                }
            } catch ( java.io.IOException e){
                this.imageTextField.setText("");
            }
        }
        
        //imageChooserDialog.dispose();
        manager.switchToMainMode();
    }//GEN-LAST:event_imageChooserActionPerformed

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        clearInformationField();
    }//GEN-LAST:event_clearButtonActionPerformed

    private void removeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeButtonActionPerformed
        int rowNumber = this.playlistTable.getSelectedRow();
        int number = ((Integer)playlistTable.getValueAt(rowNumber,0)).intValue();
        
        String artist = this.artistTextField.getText().trim();
        String album = this.albumTextField.getText().trim();
        
        if( number >= 0 ){
            AlbumInformation info = albumInformations.get(number);
            if( info.getArtist().equals(artist) && info.getTitle().equals(album) ){
                this.albumInformations.remove(number);
                this.tableModel.removeRow(rowNumber);
                
                for(int i=0; i < playlistTable.getRowCount(); i++){
                    int no = ((Integer)playlistTable.getValueAt(i,0)).intValue();
                    if( no > number ){
                        no --;
                        playlistTable.setValueAt(new Integer(no),i,0);
                    }
                }
                
                this.changedAlbumList = true;
                this.saveButton.setEnabled(true);
                clearInformationField();
            }
        }
    }//GEN-LAST:event_removeButtonActionPerformed

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        String artist = this.artistTextField.getText().trim();
        String album = this.albumTextField.getText().trim();
        String m3u = this.m3uTextField.getText().trim();
        String image = this.imageTextField.getText().trim();
        
        if( m3u != null && m3u.trim().equals("") == false){
            
            boolean exists = false;
            // modify
            for(int i=0; i < albumInformations.size(); i++){
                AlbumInformation info = albumInformations.get(i);
                if( info.getArtist().equals(artist) && info.getTitle().equals(album) ){
                    exists = true;
                    if(info.getM3U().equals(m3u) == false){
                        info.setM3U(m3u);
                        this.changedAlbumList = true;
                        this.saveButton.setEnabled(true);
                    } else if (info.getImage().equals(image) == false){
                        info.setImage(image);
                        this.changedAlbumList = true;
                        this.saveButton.setEnabled(true);
                    } else break;
                }
            }
            
            // add
            if(exists == false){
                AlbumInformation information = new AlbumInformation();
                information.setArtist(artist);
                information.setTitle(album);
                information.setM3U(m3u);
                information.setImage(image);
                albumInformations.add(information);
                Integer integer = new Integer( this.tableModel.getRowCount() );
                Object[] newAlbum = { integer, artist, album };
                if(integer == 1){
                    TableColumn col = playlistTable.getColumnModel().getColumn(0);
                    col.setMinWidth(NO_COLUMN_SIZE);
                    col.setMaxWidth(NO_COLUMN_SIZE);
                }
                this.tableModel.addRow(newAlbum);
                this.playlistTable.changeSelection(albumInformations.size()-1,0,false,false);
                this.changedAlbumList = true;
                this.saveButton.setEnabled(true);
                this.selectedAlbumNumber = albumInformations.size()-1;
                this.setUpDwonButtonEnable(albumInformations.size()-1);
            }
        }
    }//GEN-LAST:event_addButtonActionPerformed

    private void imageButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imageButtonActionPerformed

        manager.switchToImageSelectMode();

        /*
        imageChooserDialog.setLocation(300,300);
        imageChooserDialog.pack();
        imageChooserDialog.setVisible(true);
         */

    }//GEN-LAST:event_imageButtonActionPerformed

    private void m3uButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m3uButtonActionPerformed

        manager.switchToM3UFileSelectMode();

        /*
        m3uChooserDialog.setLocation(300,300);
        m3uChooserDialog.pack();
        m3uChooserDialog.setVisible(true);
         */

    }//GEN-LAST:event_m3uButtonActionPerformed

    private void playlistTableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_playlistTableMousePressed
        int rowNumber = this.playlistTable.getSelectedRow();
        int number = ((Integer)playlistTable.getValueAt(rowNumber,0)).intValue();
        
        // System.out.println("number = " + number);
        
        AlbumInformation information = albumInformations.get( number );
        this.artistTextField.setText( information.getArtist() );
        this.albumTextField.setText( information.getTitle() );
        this.imageTextField.setText( information.getImage() );
        this.m3uTextField.setText( information.getM3U() );
        
        this.artistTextField.setCaretPosition(0);
        this.albumTextField.setCaretPosition(0);
        this.imageTextField.setCaretPosition(0);
        this.m3uTextField.setCaretPosition(0);
        
        this.selectedAlbumNumber = number;
        setUpDwonButtonEnable(rowNumber);
        
        String imageFile = information.getImage();
        if(imageFile != null && imageFile.trim().equalsIgnoreCase("") == false){
            File file = new File(imageFile);
            this.changeImage( this.imageLabel, file );
        } else changeImage( this.imageLabel, null);
    }//GEN-LAST:event_playlistTableMousePressed

    private void upButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_upButtonActionPerformed
        moveTableComponent(-1);
    }//GEN-LAST:event_upButtonActionPerformed

    private void downButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downButtonActionPerformed
        moveTableComponent(1);
    }//GEN-LAST:event_downButtonActionPerformed

    private void moveTableComponent(int number){
        int rowNumber = this.playlistTable.getSelectedRow();
        int goal = rowNumber + number;
        if( rowNumber >=0 && goal >= 0 && goal < this.playlistTable.getRowCount() ){
            this.tableModel.moveRow(rowNumber, rowNumber, goal);
            this.tableModel.fireTableDataChanged();
            this.playlistTable.changeSelection(goal,0,false,false);
            this.setUpDwonButtonEnable(goal);
            changedAlbumList = true;
            this.saveButton.setEnabled(true);
        }

    }

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        saveAction();
    }//GEN-LAST:event_saveButtonActionPerformed

    private void wizardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wizardButtonActionPerformed
        callWizard();
    }//GEN-LAST:event_wizardButtonActionPerformed
    
    private void saveAction(){
        // replace trumplayer.xml
        if( this.changedAlbumList && albumInformations.size() > 0){
            changedAlbumList = false;
            this.saveButton.setEnabled(false);
            updated = true;
            AlbumInformation[] ainfo = new AlbumInformation[ albumInformations.size() ];
            // this.albumInformations.toArray(ainfo);
            // sort albumlist (ref. playlistTable sort)
            for(int i=0; i < albumInformations.size(); i++){
                int row = ((Integer)playlistTable.getValueAt(i,0)).intValue();
                ainfo[i] = albumInformations.get(row);
            }
            AutomaticConfigurator ac = AutomaticConfigurator.getConfigurator();
            ac.replaceTrumplayerXML(ainfo);
            
            if(this.updateEvent != null)
                this.updateEvent.performUpdateAction();
        }
    }
    
    private void callWizard(){
        if( setupSwingFrame == null || setupSwingFrame.isActive() == false ){
            setupSwingFrame = new SetupSwingFrame(SetupSwingFrame.Mode.ADD);
            // current LG3D does not support WindowListener.windowClosed,
            // therefore use interface UpdateEvent instead of WindowListener.
            // setupSwingFrame.addWindowListener(new SetupSwingFrameCloseListener());
            setupSwingFrame.setUpdateEvent(this);
            setupSwingFrame.setLocation(300,300);
            setupSwingFrame.pack();
            setupSwingFrame.setVisible(true);
        }
    }

    private void clearInformationField(){
        this.albumTextField.setText("");
        this.artistTextField.setText("");
        this.m3uTextField.setText("");
        this.imageTextField.setText("");
        this.changeImage(this.imageLabel,null);
        this.playlistTable.clearSelection();
        this.selectedAlbumNumber = -1;
        this.setUpDwonButtonEnable(-1);
    }
    
    private SetupSwingFrame setupSwingFrame = null;

    // current LG3D does not support WindowListener.windowClosed,
    // therefore use interface UpdateEvent instead of WindowListener.    
    public void performUpdateAction(){
        if( setupSwingFrame.getConfigStatus() == AutomaticConfigurator.Status.SUCCESS &&
                setupSwingFrame.getStage() == SetupSwingFrame.Stage.COMPLETE ){
            readAlbumInformations();
            changedAlbumList = false;
            saveButton.setEnabled(false);
            updated = true;

            if(this.updateEvent != null)
                this.updateEvent.performUpdateAction();
        }
    }


    /**********
    private void windowCloseAction(){
        this.setVisible(false);
        // this.imageChooserDialog.dispose();
        // this.m3uChooserDialog.dispose();
        if(setupSwingFrame != null)
            setupSwingFrame.dispose();
        // frame.dispose();
        
        // saveAction();
    }
    
    // javax.swing.JFrame frame = this;
     ***************/
    
    int IMAGE_SIZE = 160;

    private void changeImage(JLabel target, File file){
        try{
            if( file != null ){
                String path = file.getCanonicalPath();
                
                ImageIcon imageIcon = new ImageIcon(path);
                int width = imageIcon.getIconWidth();
                int height = imageIcon.getIconHeight();
                int newSize =0;
                if( width > height )
                    newSize = width / IMAGE_SIZE;
                else newSize = height / IMAGE_SIZE;
                imageIcon.setImage(
                    imageIcon.getImage().getScaledInstance(IMAGE_SIZE,IMAGE_SIZE,java.awt.Image.SCALE_SMOOTH)
                    );
                target.setIcon(imageIcon);
                // selectedFolder = file;
            } else {
                target.setIcon(null);
                target.setText("No Image");
            }
        } catch (java.io.IOException e){
            target.setIcon(null);
            target.setText("No Image");
        }
        
    }

    private int selectedAlbumNumber;
    
    private void setUpDwonButtonEnable(int number){
        if( number > 0  && number < this.playlistTable.getRowCount() )
            this.upButton.setEnabled(true);
        else this.upButton.setEnabled(false);
        if( number >= 0 && number < this.playlistTable.getRowCount() - 1 )
            this.downButton.setEnabled(true);
        else this.downButton.setEnabled(false);
    }



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JLabel albumLabel;
    private javax.swing.JTextField albumTextField;
    private javax.swing.JLabel artistLabel;
    private javax.swing.JTextField artistTextField;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JButton clearButton;
    private javax.swing.JPanel dataPanel;
    private javax.swing.JPanel descPanel;
    private javax.swing.JButton downButton;
    private javax.swing.JButton imageButton;
    private javax.swing.JFileChooser imageChooser;
    private javax.swing.JPanel imageChooserPanel;
    private javax.swing.JLabel imageLabel;
    private javax.swing.JLabel imageLabel4Chooser;
    private javax.swing.JTextField imageTextField;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton m3uButton;
    private javax.swing.JFileChooser m3uChooser;
    private javax.swing.JPanel m3uChooserPanel;
    private javax.swing.JTextField m3uTextField;
    private javax.swing.JTable playlistTable;
    private javax.swing.JButton removeButton;
    private javax.swing.JButton saveButton;
    private javax.swing.JButton upButton;
    private javax.swing.JButton wizardButton;
    // End of variables declaration//GEN-END:variables
    
}
