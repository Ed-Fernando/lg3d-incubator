package org.jdesktop.lg3d.apps.archviz3d.geons.frames;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.vecmath.Point3f;

import org.jdesktop.lg3d.utils.shape.ImagePanel;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.SwingNode;

import org.jdesktop.lg3d.apps.archviz3d.geons.GeonAbstract;
import org.jdesktop.lg3d.apps.archviz3d.geons.components.GeonAnimator;
import org.jdesktop.lg3d.apps.archviz3d.geons.frames.GeonFrame;



/**
 * Esta clase permite visualizar, utilizando una tabla, las propiedades 
 * de una arquitectura.
 * 
 * @author Juan Feldman
 *
 */
public class GeonFrameProperties extends GeonFrame {
	/**
	 * Instancia el Frame de propiedades.
	 * 
	 * @param width Ancho del Frame.
	 * @param height Largo del Frame.
	 * @param depth Profundidad del Frame.
	 * @param geonSelected Geon seleccionado por el usuario.
	 * @param caller Llamador del Frame.
	 */
	public GeonFrameProperties(float width, float height, float depth, GeonAbstract geonSelected, Component3D caller) {
        this(width, height, depth, new Point3f(0.0f, 0.0f, 0.0f), geonSelected, caller);
	}

	/**
	 * Instancia el Frame de propiedades.
	 * 
	 * @param width Ancho del Frame.
	 * @param height Largo del Frame.
	 * @param depth Profundidad del Frame.
	 * @param originalPosition Posicion original del Frame.
	 * @param geonSelected Geon seleccionado por el usuario.
	 * @param caller Llamador del Frame.
	 */
	public GeonFrameProperties(float width, float height, float depth, Point3f originalPosition, GeonAbstract geonSelected, Component3D caller) {
        super(width, height, depth, originalPosition, caller);
        createFrame(geonSelected);
        setTitle("Properties - Component " + geonSelected.getName());
	}
    
	/**
	 * Crea el frame de propiedades.
	 * @param geonSelected Geon seleccionado por el usuario.
	 */
	private void createFrame(GeonAbstract geonSelected) {
		// Creo el contenedor de la tabla
		JPanel jPanel = new JPanel(new BorderLayout());

    	// Recupero las propiedades del componente
    	HashMap<String, String> properties = geonSelected.getProperties();
    	
	    // Lleno la tabla
	    if (!properties.keySet().isEmpty()) {
			// Creo el modelo de la tabla
			MyTableModel myModel = new MyTableModel(properties.keySet().size());
		    
			// Creo la tabla		
			JTable jTable = new JTable(myModel);
		    jTable.setPreferredSize(new Dimension(400, 250));
		    jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		    jTable.getColumnModel().getColumn(0).setPreferredWidth(150);

			// Lleno la tabla
		    int row = 0;
			for (Iterator<String> iterator = properties.keySet().iterator(); iterator.hasNext(); ) {
				// Obtengo el nombre de la propiedad
				String property = iterator.next();
	        	// Seteo el nombre del componente de la arquitectura
	        	myModel.setValueAt(property, row, 0);
	        	// Seteo el nombre del Geon que representa al componente de la arquitectura
	        	myModel.setValueAt(properties.get(property), row++, 1);
	        }
			JScrollPane scrollPane = new JScrollPane(jTable);
			jTable.setPreferredScrollableViewportSize(new Dimension(400, 70));
	
			jPanel.add(scrollPane, BorderLayout.CENTER);
			jPanel.setPreferredSize(new Dimension(400, 250));
			
			SwingNode swingNode = new SwingNode();
			swingNode.setPanel(jPanel);
			swingNode.setTranslation(0.01f, 0.021f, 0.001f);
			swingNode.setScale(0.8f);
			this.getFrameContainer().addChild(swingNode);
	    }
	    else {
			try {
				ImagePanel image = new ImagePanel(getClass().getResource("/resources/images/frame/properties/no-properties.png"), 0.09f, 0.07f);
				Component3D container = new Component3D();
				container.setTranslation(0.01f, 0.02f, 0.001f);
				container.addChild(image);
				this.getFrameContainer().addChild(container);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
	    }
	}
	
	/**
	 * Cierra el Frame.
	 */
	public void close() {
	    this.detach();
	    getGeonFrameManager().frameClosed(this);

		// Muestro las vistas
		GeonAnimator.instance().pullAll();
	}
	
	/**
	 * Modelo de la tabla de propiedades.
	 * 
	 * @author Juan Feldman
	 *
	 */
    private class MyTableModel extends AbstractTableModel {
        final String[] columnNames = {"Property Name",
							          "Value"};
        
        final Object[][] data;
        
        public MyTableModel(int numberProperties) {
        	data = new Object[numberProperties][2];
        }

        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return data.length;
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            return data[row][col];
        }

        @SuppressWarnings ("unchecked")
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        public boolean isCellEditable(int row, int col) {
            return false;
        }

        public void setValueAt(Object value, int row, int col) {
            data[row][col] = value;
        }
    }
}
