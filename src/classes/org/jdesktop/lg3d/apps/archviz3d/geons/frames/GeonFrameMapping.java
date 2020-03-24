package org.jdesktop.lg3d.apps.archviz3d.geons.frames;

import java.awt.Color;
import java.awt.Font;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.vecmath.Color3f;
import javax.vecmath.Point3f;

import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.sg.BranchGroup;
import org.jdesktop.lg3d.sg.Material;
import org.jdesktop.lg3d.sg.Shape3D;
import org.jdesktop.lg3d.utils.action.ActionNoArg;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.utils.shape.Text2D;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.event.LgEventSource;

import org.jdesktop.lg3d.apps.archviz3d.geons.GeonAbstract;
import org.jdesktop.lg3d.apps.archviz3d.geons.builders.GeonBuilderSur;
import org.jdesktop.lg3d.apps.archviz3d.geons.components.GeonAnimator;
import org.jdesktop.lg3d.apps.archviz3d.geons.frames.GeonFrame;
import org.jdesktop.lg3d.apps.archviz3d.geons.frames.GeonFrameButton;
import org.jdesktop.lg3d.apps.archviz3d.geons.frames.GeonFrameMappingColorChooser;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.Factory3D;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DComponent;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DSimpleComponent;


/**
 * Esta clase se encarga de manejar el Mapping de Geones.
 * 
 * @author Juan Feldman - Lucas Vigneau
 *
 */
public class GeonFrameMapping extends GeonFrame {
	/** Logger de mensajes.*/
	private static Logger logger = Logger.getLogger("lg.ArchViz3D");	

	/** Geon a cambiar.*/
	private GeonAbstract geonToChange;
	/** Geon seleccionado para cambiar la imagen del geonToChange.*/
	private J3DComponent geonSelected;
	/** Label para el Geon seleccionado.*/
	private Component3D compLabel;
	/** Material para los Geones del Frame.*/
	private Material materialGeons = null;
	/** Color para los Geones del Frame.*/
	private Color3f colorGeons = new Color3f();
	/** Frame para seleccionar el color.*/
	private GeonFrameMappingColorChooser geonColorChooser = null;
	
	/**
	 * Instancia el Frame de seleccion.
	 * 
	 * @param width Ancho del Frame.
	 * @param height Largo del Frame.
	 * @param depth Profundidad del Frame.
	 * @param geonToChange Geon seleccionado por el usuario para ser modificado.
	 * @param caller Llamador del Frame.
	 */
	public GeonFrameMapping(float width, float height, float depth, GeonAbstract geonToChange, Component3D caller) {
        this(width, height, depth, new Point3f(0.0f, 0.0f, 0.0f), geonToChange, caller);
	}

	/**
	 * Instancia el Frame de mapping.
	 * 
	 * @param width Ancho del Frame.
	 * @param height Largo del Frame.
	 * @param depth Profundidad del Frame.
	 * @param initialPosition Posicion initial del Frame.
	 * @param geonToChange Geon seleccionado por el usuario para ser modificado.
	 * @param caller Llamador del Frame.
	 */
	public GeonFrameMapping(float width, float height, float depth, Point3f initialPosition, GeonAbstract geonToChange, Component3D caller) {
        super(width, height, depth, initialPosition, caller);
		this.geonToChange = geonToChange;
        
		colorGeons.set(geonToChange.getColor().x, geonToChange.getColor().y, geonToChange.getColor().z);
		materialGeons = new Material();
		materialGeons.setCapability(Material.ALLOW_COMPONENT_WRITE);
		materialGeons.setCapability(Material.ALLOW_COMPONENT_READ);
		materialGeons.setDiffuseColor(colorGeons);
        
        createFrame();
		setTitle("Geons Selection - Component " + geonToChange.getName());
	}
    
	/**
	 * Crea el Frame de mapping.
	 */
	private void createFrame() {
		// Creo el ColorChooser y lo agrego al llamador de este Frame
		geonColorChooser = new GeonFrameMappingColorChooser(this);
		geonColorChooser.setVisible(false);
		getCaller().addChild(geonColorChooser);

		// Creo los botones del Frame
        GeonFrameButton buttonAccept = new GeonFrameButton("buttons/accept.png", new Point3f(getInitialPosition().x + 0.1f * getWidth() + 0.003f, getInitialPosition().y - getUpperHeight() - getCentralHeight() - getBottomHeight()/2f - 2 * GeonFrame.SUBFRAME_SEPARATION, getInitialPosition().z + 0.001f), getWidth()/5f, getBottomHeight() - 0.004f);
        buttonAccept.addListener(new MouseClickedEventAdapter(new ActionNoArg() {
			public void performAction(LgEventSource source) {
				buttonAccept_click();
			}
		}
		));
        
        GeonFrameButton buttonColor = new GeonFrameButton("buttons/color.png", new Point3f(getInitialPosition().x + getWidth()/2f, getInitialPosition().y - getUpperHeight() - getCentralHeight() - getBottomHeight()/2f - 2 * GeonFrame.SUBFRAME_SEPARATION, getInitialPosition().z + 0.001f), getWidth()/5f, getBottomHeight() - 0.004f);
        buttonColor.addListener(new MouseClickedEventAdapter(new ActionNoArg() {
			public void performAction(LgEventSource source) {
				buttonColor_click();
			}
		}
		));
          
        getFrameContainer().addChild(buttonAccept);
        getFrameContainer().addChild(buttonColor);
        getFrameContainer().addChild(createFrameBG());
	}

	/**
	 * Crea el BranchGroup que contiene todos los Geones del frame.
	 * 
	 * @return BranchGroup Contiene todos los Geones del frame
	 */
	private BranchGroup createFrameBG() {
		BranchGroup bg = new BranchGroup();
		J3DComponent compGeon;
		int cantGeons = 1;
		float posX;
		float posY;
		float posZ;

		// Creo el label para identificar el Geon seleccionado
		compLabel = new Component3D();
		Text2D label = new Text2D("Selected", new Color3f(1f, 1f, 1f), new Font("Arial", Font.BOLD, 12));
		compLabel.addChild(label);
		compLabel.setScale(0.1f);
		compLabel.setVisible(false);
		bg.addChild(compLabel);
		
		// Creo la apariencia para los Geones 
    	Appearance apNode = Factory3D.instance().createAppearance(geonToChange.getColor());
    	apNode.setMaterial(materialGeons);

	    // Inicializo la posicion para el primer Geon
	    posX = getInitialPosition().x + 0.125f * getWidth();
	    posY = getInitialPosition().y - getUpperHeight() - 0.16f * getCentralHeight();
	    posZ = getInitialPosition().z + 0.007f;
	    
	    //Creo el contenedor de los Geones
	    Component3D geonContainer = new Component3D(); 
	    
	    for (Iterator i = geonToChange.getGeonsList().iterator(); i.hasNext(); ) {
    		String nameGeon = (String)i.next();
			
	    	// Creo el shape para el Geon y le seteo la apariencia
    		Shape3D geonShape = GeonBuilderSur.instance().buildGeonByName(nameGeon);
			geonShape.setAppearance(apNode);
			
	    	// Creo el contenedor para el Geon
			compGeon = new J3DSimpleComponent(geonShape);
			compGeon.setScale(0.01f);
			compGeon.setTranslation(posX, posY, posZ);
			// Los geones creados en este frame no poseen abstractores por lo tanto
			// no se puede hacer referencia a las variables GeonName, Color y GeonList.
			// Por este motivo guardo el nombre del Geon en UserData
			compGeon.setUserData(nameGeon);
			compGeon.addListener(new MouseClickedEventAdapter(new ActionNoArg() {
				public void performAction(LgEventSource source) {
					geonClicked((J3DComponent)source);
				}
			}));
			geonContainer.addChild(compGeon);
			
			if (nameGeon.equals(geonToChange.getGeonName())){
		        compLabel.setVisible(true);		
		        compLabel.setTranslation(posX - 0.01f, posY - 0.005f, posZ + 0.01f);
		        geonSelected = compGeon;
			}
	        
			if (cantGeons == 4){
		        posX = getInitialPosition().x + 0.125f * getWidth();
		        posY = posY - 0.33f * getCentralHeight();
	            cantGeons = 1; 
	        }else{
		        posX = posX + 0.25f * getWidth();
		        cantGeons = cantGeons + 1;
	        }
    	}
	    
	    bg.addChild(geonContainer);
		return bg;
	}

	/** 
	 * Click para el boton Accept del frame. 
	 */
	public void buttonAccept_click() {
		String nameGeonSelected = (String)geonSelected.getUserData();
		
		logger.log(Level.INFO, "MAPPING - Reemplazando el Geon \"" + geonToChange.getGeonName() + "\" del Componente \"" + geonToChange.getName() + "\" por el Geon \"" + nameGeonSelected + "\"");
		
		// Actualizo el componente
		geonToChange.updateImage(nameGeonSelected, colorGeons);
		
		// Cierro el frame
		close();
	}
    
	/** 
	 * Click para el boton Color del frame. 
	 */
	public void buttonColor_click() {
		// Recupero el color del geon seleccionado
		Color color = new Color(colorGeons.x, colorGeons.y, colorGeons.z);

		// Seteo el color del Geon seleccionado
		geonColorChooser.setColor(color);

		// Corro este frame
		GeonAnimator.instance().push(this, geonColorChooser);
	}
	
	/**
	 * Salva el geon seleccionado por el usuario y le coloca el
	 * texto que indica que fue seleccionado.
	 * 
	 * @param geon Geon seleccionado. 
	 */
	public void geonClicked(J3DComponent geon) {
		// Salvo el Geon seleccionado
		geonSelected = geon;
		
		// Le coloco el texto
		compLabel.setTranslation(geonSelected.getTranslation().x - 0.01f, geonSelected.getTranslation().y - 0.005f, geonSelected.getTranslation().z + 0.01f);
	}
	
	/**
	 * Setea el color a los Geones. 
	 * Este metodo es llamado por el ColorChooser.
	 * 
	 * @param color Color a setear para los Geones.
	 */
	protected void setColor(Color color) {
		// Seteo el color al material de los Geones
		materialGeons.setDiffuseColor(color.getRed()/255.0f, color.getGreen()/255.0f, color.getBlue()/255.0f);
		
		// Salvo el color seleccionado por el usuario 
        colorGeons.set(color.getRed()/255.0f, color.getGreen()/255.0f, color.getBlue()/255.0f);

		// Muestro este frame
		GeonAnimator.instance().pull();
	}
	
	/**
	 * Cierra el Frame.
	 */
	public void close() {
	    this.detach();
	    geonColorChooser.detach();
	    getGeonFrameManager().frameClosed(this);

		// Muestro las vistas
	    GeonAnimator.instance().pullAll();
	}

}
