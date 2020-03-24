package org.jdesktop.lg3d.apps.archviz3d.geons;

import java.awt.Font;
import java.util.Vector;

import javax.vecmath.AxisAngle4f;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.utils.shape.Text2D;

import org.jdesktop.lg3d.apps.archviz3d.abstractors.Abstractor;
import org.jdesktop.lg3d.apps.archviz3d.abstractors.AbstractorComponentMD;
import org.jdesktop.lg3d.apps.archviz3d.geons.GeonSimpleMD;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.Factory3D;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DCompositeComponent;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DSimpleComponent;


/**
 * Esta clase se encarga de representar un conector en las vistas de
 * Modulo y en la de Deployment.
 * 
 * @author Juan Feldman
 *
 */
public class GeonLinkMD extends GeonSimpleMD {
	/** Tamaño de una parte del conector.*/
	private final float SIZE_PART = 0.03f;
	/** Separacion entre las partes del conector.*/
	private final float SIZE_SEPARATION = 0.02f;

	/** Tipo de conector.*/
	public static final int TYPE_DASHED = 0;
	public static final int TYPE_CONTINUOS = 1;

	/** Puntos del conector.*/
	private Vector<Point3f> points = null;

	/**
	 * Se intancia GeonLinkMD .
	 * @param connectorName Nombre del conector.
	 * @param abstractor Abstractor del conector.
	 */
	public GeonLinkMD(String connectorName, Abstractor abstractor) {
		super(connectorName, abstractor);
	}
	
	/**
	 * Crea el conector.
	 * @param abstractor Abstractor del conector.
	 */
	public void createImage(Abstractor abstractor) {
		points = ((AbstractorComponentMD)abstractor).getPoints(); 
		
		// Genero el conector
		createConnector(points, ((AbstractorComponentMD)abstractor).getConnectorType());
		
		// Agrego la punta de la flecha
		if (((AbstractorComponentMD)abstractor).getWithArrow())
			createArrow(points);

		// Agrego el label
		if (((AbstractorComponentMD)abstractor).getNameVisible())
			createLabel(points);
	}
	
	/**
	 * Crea el conector punteado.
	 * @param points Puntos del conector.
	 * @param typeConnector Tipo de conector.
	 */
	private void createConnector(Vector<Point3f> points, int typeConnector) {
		for (int i = 0; i < points.size() - 1; i++) {
			// Obtengo los puntos a unir
			Point3f from = points.elementAt(i);
			Point3f to = points.elementAt(i + 1);

			// Inicializo la posicion y direccion
			float xPosition = from.x;
			float yPosition = from.y;
			float zPosition = from.z;
			int directionX = 1;
			int directionY = 1;
			int directionZ = 1;

			// Calculo la distancia en cada uno de los ejes
			float xLength = to.x - from.x;
			float yLength = to.y - from.y;
			float zLength = to.z - from.z;
			
			// Calculo las direcciones en cada uno de los ejes
			if (xLength < 0)
				directionX = -1;
			if (yLength < 0)
				directionY = -1;
			if (zLength < 0)
				directionZ = -1;

			// Calculo la distancia entre los dos puntos a unir
			float length = Math.max(Math.abs(xLength), Math.abs(yLength));
			length = Math.max(length, Math.abs(zLength));
	
			int partsNumber = Math.abs((int)(length/(SIZE_PART + SIZE_SEPARATION)));
			float sizePart = SIZE_PART; 
			if (typeConnector == TYPE_CONTINUOS) {
				partsNumber = 1;
				xPosition = (to.x + from.x)/2;
				yPosition = (to.y + from.y)/2;
				zPosition = (to.z + from.z)/2;
				sizePart = length;
			}
			for (int j = 0; j < partsNumber; j++) {
				// Creo una parte del conector
				J3DSimpleComponent cylinder = Factory3D.instance().createCylinder(0.01f, sizePart, getAppearance());
				cylinder.setName("cylinder");
				cylinder.setTranslation(xPosition, yPosition, zPosition);
				
				// Roto la parte del conector creado
				// Va por el eje X => hay que poner el cilindro horizontal
				if (yLength == 0 && zLength == 0) 
					cylinder.setRotation(new AxisAngle4f(0, 0, 1, directionX*(float)Math.PI/2));
				// Va por el eje Z => hay que poner el cilindro horizontal
				if (xLength == 0 && yLength == 0) 
					cylinder.addRotation(new AxisAngle4f(1, 0, 0, directionZ*(float)Math.PI/2));
					
				// Agrego la parte del conector
				addComponent(cylinder);
				
				// Agrego el codo (para todas las partes menos la ultima)
				if (i < points.size() - 2) {
					Appearance sphereApearance = Factory3D.instance().createAppearance(new Color3f(0.0f, 0.5f, 0.0f));
					J3DSimpleComponent sphere = Factory3D.instance().createSphere(0.02f, sphereApearance);
					sphere.setTranslation(to.x, to.y, to.z);
					addComponent(sphere);
				}
				
				// Calculo la posicion de la siguiente parte del conector
				if (xLength != 0)
					xPosition += directionX*SIZE_PART + directionX*SIZE_SEPARATION;
				if (yLength != 0)
					yPosition += directionY*SIZE_PART + directionY*SIZE_SEPARATION;
				if (zLength != 0)
					zPosition += directionZ*SIZE_PART + directionZ*SIZE_SEPARATION;
			}
		}
	}
	
	/**
	 * Crea la punta del conector.
	 * @param points Puntos del conector.
	 */
	private void createArrow(Vector<Point3f> points) {
		// Creo la apariencia para la punta del conector
		Appearance arrowAppearance = Factory3D.instance().createAppearance(new Color3f(0.0f, 0.8f, 0.0f));

		// Creol la punta del conector
		J3DSimpleComponent arrow = Factory3D.instance().createCone(0.02f, 0.04f, arrowAppearance);
		
		// Obtengo los puntos a unir
		Point3f from = points.elementAt(points.size() - 2);
		Point3f to = points.elementAt(points.size() - 1);

		// Inicializo direcciones
		int directionX = 1;
		int directionY = 1;
		int directionZ = 1;
		
		// Calculo la distancia en cada uno de los ejes
		float xLength = to.x - from.x;
		float yLength = to.y - from.y;
		float zLength = to.z - from.z;
		
		// Calculo las direcciones en cada uno de los ejes
		if (xLength < 0)
			directionX = -1;
		if (yLength < 0)
			directionY = -1;
		if (zLength < 0)
			directionZ = -1;

		// Roto la parte del conector creado
		// Va por el eje X => hay que poner el cilindro horizontal
		if (yLength == 0 && zLength == 0) 
			arrow.setRotation(new AxisAngle4f(0, 0, 1, -directionX*(float)Math.PI/2));
		// Va por el eje Z => hay que poner el cilindro horizontal
		if (xLength == 0 && yLength == 0) 
			arrow.addRotation(new AxisAngle4f(1, 0, 0, directionZ*(float)Math.PI/2));
		if (directionY < 0)
			arrow.addRotation(new AxisAngle4f(1, 0, 0, directionY*(float)Math.PI));
		arrow.setTranslation(to.x, to.y, to.z);
		
		addComponent(arrow);
	}

	/**
	 * Crea el label para el conector.
	 * @param points Puntos del conector.
	 */
	private void createLabel(Vector<Point3f> points) {
        // Creo el texto y la geometria de fondo
        J3DCompositeComponent label = new J3DCompositeComponent();
        
        // Creo el texto
        Text2D text = new Text2D(getName(), new Color3f(0f, 0f, 0.8f), new Font("Arial", Font.BOLD, 26));

        // Creo la geometria de fondo que se colocara detras del texto
        float width = getName().length() * 0.4f;

        Appearance appearance = new Appearance();
        appearance.setMaterial(Factory3D.rubberYellow);
		J3DSimpleComponent caja = Factory3D.instance().createBox(width, 0.6f, 0.1f, appearance);
        caja.setTranslation(new Vector3f(width/15, 0.06f, -0.1f));

        // Agrego la geometria de fondo
        label.addComponent(caja);
        
        // Agrego el texto
        J3DSimpleComponent text3D = new J3DSimpleComponent(text);
        text3D.setScale(10f);
        text3D.setTranslation(new Vector3f(-width/2 - 0.1f, -0.5f, 0.1f));
        label.addComponent(text3D);
        
        // Posiciona el label centrado
		Point3f from = points.elementAt(0); 
		Point3f to = points.elementAt(1);
		label.setTranslation ((to.x + from.x)/2, (to.y + from.y)/2, (to.z + from.z)/2 + 0.02f);
        label.setScale(0.02f);
        
        addComponent(label);
	}
	
}
