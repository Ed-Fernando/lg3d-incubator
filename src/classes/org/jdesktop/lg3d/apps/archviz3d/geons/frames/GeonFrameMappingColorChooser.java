package org.jdesktop.lg3d.apps.archviz3d.geons.frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JColorChooser;
import javax.swing.JPanel;
import javax.vecmath.Point3f;

import org.jdesktop.lg3d.utils.action.ActionNoArg;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MouseDragDistanceAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MousePressedEventAdapter;
import org.jdesktop.lg3d.wg.SwingNode;
import org.jdesktop.lg3d.wg.event.LgEventSource;
import org.jdesktop.lg3d.wg.event.MouseEvent3D;

import org.jdesktop.lg3d.apps.archviz3d.geons.components.GeonAnimator;
import org.jdesktop.lg3d.apps.archviz3d.geons.events.GeonEventTraslation;
import org.jdesktop.lg3d.apps.archviz3d.geons.frames.GeonFrame;
import org.jdesktop.lg3d.apps.archviz3d.geons.frames.GeonFrameButton;
import org.jdesktop.lg3d.apps.archviz3d.geons.frames.GeonFrameMapping;



/**
 * Esta clase encapsula el comportamiento para poder seleccionar un color.
 * 
 * @author Juan Feldman
 *
 */
public class GeonFrameMappingColorChooser extends GeonFrame {
	/** Frame para seleccionar el color.*/
	private JColorChooser jColorChooser = null;
	/** Contenedor del ColorChooser.*/
	private SwingNode swingNode = new SwingNode();

	/**
	 * Instancia el ColorChooser.
	 * @param caller Llamador del ColorChooser
	 */
	public GeonFrameMappingColorChooser(GeonFrame caller) {
		// Creo el Frame y seteo el llamador
		super(0.17f, 0.17f, 0.004f, 0.01f, 0.013f, new Point3f(-0.08f, 0.10f, -0.001f), caller);
		setTitle("Choose a Color", new Font("Arial", Font.BOLD, 36));
		createFrame();
	}

	/**
	 * Crea el Frame de Color Chooser.
	 */
	private void createFrame() {
		// Creo el ColorChooser
		jColorChooser = new JColorChooser();

		JPanel jPanel = new JPanel(new BorderLayout());
		jPanel.add(jColorChooser, BorderLayout.CENTER);
		
		swingNode.setPanel(jPanel);
		swingNode.setTranslation(0.005f, 0.015f, 0);
		this.getFrameContainer().addChild(swingNode);
		
        // Agrego el boton aceptar
		GeonFrameButton acceptButton = new GeonFrameButton("buttons/accept.png", new Point3f(getInitialPosition().x + 0.1f*getWidth() + 0.003f, getInitialPosition().y - getUpperHeight() - getCentralHeight() - getBottomHeight()/2f - 2 * GeonFrame.SUBFRAME_SEPARATION, getInitialPosition().z + 0.001f), getWidth()/5f, getBottomHeight() - 0.004f);
		acceptButton.addListener(new MouseClickedEventAdapter(new ActionNoArg() {
			public void performAction(LgEventSource source) {
				acceptButton_click();
			}
		}
		));
		this.getFrameContainer().addChild(acceptButton);

        // Traslacion con mouse (arrastrando con el boton derecho presionado)
		GeonEventTraslation action = new GeonEventTraslation();
		this.addListener(new MouseDragDistanceAdapter(MouseEvent3D.ButtonId.BUTTON3, null, action));
		this.addListener(new MousePressedEventAdapter(MouseEvent3D.ButtonId.BUTTON3, action));
	}
	
	/**
	 * Click para el boton Accept del ColorChooser.
	 */
	private void acceptButton_click() {
		setVisible(false);

		// Obtengo el color seleccionado por el usuario y
		// llamo al frame que necesita dicho color para
		// que pueda utilizarlo
		((GeonFrameMapping)getCaller()).setColor(jColorChooser.getColor());
	}
    
	/**
	 * Setea el color del ColorChooser.
	 * @param color Color a setear.
	 */
	public void setColor(Color color) {
		jColorChooser.setColor(color);
	}
	
	/**
	 * Cierra el Frame.
	 */
	public void close() {
		setVisible(false);
		
		// Muestro el frame de seleccion
		GeonAnimator.instance().pull();
	}
}
