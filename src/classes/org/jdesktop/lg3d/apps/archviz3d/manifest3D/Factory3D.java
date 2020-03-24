package org.jdesktop.lg3d.apps.archviz3d.manifest3D;

import java.util.logging.Level;
import java.util.logging.Logger;

import bsh.EvalError;
import bsh.TargetError;

import javax.vecmath.AxisAngle4f;
import javax.vecmath.Color3f;
import javax.vecmath.Matrix4f;

import org.jdesktop.lg3d.apps.archviz3d.geons.events.GeonEventRotation;
import org.jdesktop.lg3d.apps.archviz3d.geons.events.GeonEventTraslation;
import org.jdesktop.lg3d.apps.archviz3d.geons.events.GeonEventZoom;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.Factory3D;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DSimpleComponent;
import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.sg.BoundingBox;
import org.jdesktop.lg3d.sg.BoundingSphere;
import org.jdesktop.lg3d.sg.Material;
import org.jdesktop.lg3d.utils.c3danimation.NaturalMotionAnimation;
import org.jdesktop.lg3d.utils.eventadapter.KeyPressedEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MouseDragDistanceAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MousePressedEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MouseWheelEventAdapter;
import org.jdesktop.lg3d.utils.shape.Box;
import org.jdesktop.lg3d.utils.shape.Cone;
import org.jdesktop.lg3d.utils.shape.Cylinder;
import org.jdesktop.lg3d.utils.shape.Sphere;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.event.MouseEvent3D;


public class Factory3D {
	/** Logger de mensajes.*/
	private static Logger logger = Logger.getLogger("lg.ArchViz3D");	

	/** Loader.*/
	private ClassLoader classLoader;

	/** Materiales.*/
    public static Material ruby = 	new Material(new Color3f(0.1745f,0.01175f,0.01175f),new Color3f(0.61424f,0.04136f,0.04136f),
			new Color3f(0.727811f,0.626959f,0.626959f),new Color3f(0.0f,0.0f,0.0f),76.8f);

    public static Material rubberWhite = new Material(new Color3f(0.02f, 0.02f,
			0.02f), new Color3f(1f, 1f, 1f), new Color3f(0.4f, 0.4f,
			0.4f), new Color3f(0.0f, 0.0f, 0.0f), 64f);

	public static Material rubberBlack = new Material(new Color3f(0.02f, 0.02f,
			0.02f), new Color3f(0.01f, 0.01f, 0.01f), new Color3f(0.4f, 0.4f,
			0.4f), new Color3f(0.0f, 0.0f, 0.0f), 64f);

	public static Material rubberRed = new Material(new Color3f(0.05f, 0.0f,
			0.0f), new Color3f(0.5f, 0.4f, 0.4f), new Color3f(0.7f, 0.04f,
			0.04f), new Color3f(0.0f, 0.0f, 0.0f), 10f);

	public static Material rubberYellow = new Material(new Color3f(0.05f,
			0.05f, 0.0f), new Color3f(0.5f, 0.5f, 0.4f), new Color3f(0.7f,
			0.7f, 0.04f), new Color3f(0.0f, 0.0f, 0.0f), 10f);

	/** Unica instancia de esta clase.*/
	static Factory3D instance = new Factory3D();

	/**
	 * Esta clase es un Singleton.
	 */
	private Factory3D() {
	}

	/**
	 * Devuelve la unica instancia de esta clase.
	 * @return Factory3D.
	 */
	public static Factory3D instance() {
		return instance;
	}

	/**
	 * Crea un cubo.
	 * 
	 * @param x Ancho en x.
	 * @param y Ancho en y.
	 * @param z Ancho en x.
	 * @param ap Apariencia.
	 * @return J3DSimpleComponent Cubo.
	 */
	public synchronized J3DSimpleComponent createBox(float x, float y, float z, Appearance ap) {
		Box box = new Box(x, y, z, Box.GENERATE_NORMALS
				| Box.GENERATE_TEXTURE_COORDS, ap);

		BoundingBox bounds = new BoundingBox();
		bounds.setLower(-x / 2, -y / 2, -z / 2);
		bounds.setUpper(x / 2, y / 2, z / 2);

		J3DSimpleComponent obj = new J3DSimpleComponent(box);
		obj.setBounds(bounds);
		obj.setBoundsAutoCompute(false);
		return new J3DSimpleComponent(obj);
	}

	/**
	 * Crea un cilindro.
	 * 
	 * @param radius Radio.
	 * @param height Altura.
	 * @param ap Apariencia.
	 * @return J3DSimpleComponent Cilindro.
	 */
	public synchronized J3DSimpleComponent createCylinder(float radius, float height, Appearance ap) {
		Cylinder cylinder = new Cylinder(radius, height, Cylinder.GENERATE_NORMALS
				| Cylinder.GENERATE_TEXTURE_COORDS, ap);
		
		BoundingBox bounds = new BoundingBox();
		float lx = -radius;
		float ly = -height / 2.0f;
		float lz = -radius;

		bounds.setLower(lx, ly, lz);
		bounds.setUpper(-lx, -ly, -lz);

		J3DSimpleComponent obj = new J3DSimpleComponent(cylinder);
		obj.setBounds(bounds);
		obj.setBoundsAutoCompute(false);

		return obj;
	}

	/**
	 * Crea un cono.
	 * @param radius Radio.
	 * @param height Alto.
	 * @param ap Apariencia
	 * @return J3DSimpleComponent Cono.
	 */
	public synchronized J3DSimpleComponent createCone(float radius, float height, Appearance ap) {
		Cone cone = new Cone(radius, height, Cone.GENERATE_NORMALS
				| Cone.GENERATE_TEXTURE_COORDS, ap);

		BoundingBox bounds = new BoundingBox();
		bounds.setLower(-radius, -height / 2.0f, -radius);
		bounds.setUpper(radius, height / 2.0f, radius);

		J3DSimpleComponent obj = new J3DSimpleComponent(cone);
		obj.setBounds(bounds);
		obj.setBoundsAutoCompute(false);
		return new J3DSimpleComponent(obj);
	}

	/**
	 * Crea una esfera.
	 * @param radius Radio.
	 * @param ap Apariencia.
	 * @return J3DSimpleComponent Esfera.
	 */
	public synchronized J3DSimpleComponent createSphere(float radius, Appearance ap) {
		Sphere sphere = new Sphere(radius, Sphere.GENERATE_NORMALS
				| Sphere.GENERATE_TEXTURE_COORDS, ap);

		BoundingSphere bounds = new BoundingSphere();
		bounds.setRadius(radius);

		J3DSimpleComponent obj = new J3DSimpleComponent(sphere);

		obj.setBoundsAutoCompute(false);
		obj.setBounds(bounds);
		return new J3DSimpleComponent(obj);
	}

	/**
	 * Ejecuta el bsh que se recibe como parametro.
	 * @param ScriptName Nombre del file bsh a ejecutar.
	 * @return Object
	 */
	public Object runBeanScript(String ScriptName) {
		try {
			bsh.Interpreter inter = new bsh.Interpreter();
			if (classLoader != null)
				inter.setClassLoader(classLoader);
			Object obj = inter.source(ScriptName);
			return obj;
		} catch (TargetError e) {
			logger.log(Level.INFO, "The script: " + ScriptName
					+ " or code called by the script threw an exception: "
					+ e.getTarget());
			e.printStackTrace();
		} catch (EvalError e2) {
			logger.log(Level.INFO, "There was an error in evaluating the script:", e2);
		} catch (Exception e3) {
			logger.log(Level.INFO, "Script Error", e3);
		}
		return null;
	}
	
	/**
	 * Crea una matriz de rotacion, segun los ejes y el angulo
	 * que se recibe como parametro.
	 * @param axis Ejes de rotacion y angulo.
	 * @return Matrix4f Matriz de rotacion.
	 */
	public synchronized Matrix4f createRotationMatrix(AxisAngle4f axis) {
    	Matrix4f matrix = new Matrix4f();
    	if (axis.x == 1)
	   		 matrix.set(new float[] {
	   				 1, 0, 0, 0, 
	   		         0, (float)Math.cos(axis.angle), -(float)Math.sin(axis.angle), 0, 
	   		         0, (float)Math.sin(axis.angle), (float)Math.cos(axis.angle), 0,
	   		         0, 0, 0, 1});
	   	if (axis.y == 1)
	  		 matrix.set(new float[] {
	  				 (float)Math.cos(axis.angle), 0, (float)Math.sin(axis.angle), 0,  
	  		         0, 1, 0, 0, 
	  		         -(float)Math.sin(axis.angle), 0, (float)Math.cos(axis.angle), 0,
	  		         0, 0, 0, 1});
	   	if (axis.z == 1)
     		 matrix.set(new float[] {
     				 (float)Math.cos(axis.angle), -(float)Math.sin(axis.angle), 0, 0,  
     		         (float)Math.sin(axis.angle), (float)Math.cos(axis.angle), 0, 0,
     		         0, 0, 1, 0,
     		         0, 0, 0, 1});
	   	return matrix;
	}
	
	/**
	 * Crea una apariencia.
	 * @param color Color de la apariencia.
	 * @return Appearance Apariencia.
	 */
	public synchronized Appearance createAppearance(Color3f color) {
    	Appearance apNode = new Appearance();
    	apNode.setCapability(Appearance.ALLOW_MATERIAL_READ);
    	apNode.setCapability(Appearance.ALLOW_MATERIAL_WRITE);
	    Material mNode = new Material();
	    mNode.setCapability(Material.ALLOW_COMPONENT_READ);
	    mNode.setCapability(Material.ALLOW_COMPONENT_WRITE);
	    if (color != null)
	    	mNode.setDiffuseColor(color);
		apNode.setMaterial(mNode);
		
		return apNode;
	}

	/**
	 * Agrega un Listener de traslacion al contenedor comp.
	 * @param comp Contenedor al cual se le agrega el listener.
	 */
	public synchronized void addListenerTranslation(Component3D comp) {
		// Traslacion con mouse (arrastrando con el boton derecho presionado)
		GeonEventTraslation action = new GeonEventTraslation();
		comp.addListener(new MouseDragDistanceAdapter(MouseEvent3D.ButtonId.BUTTON1, null, action));
		comp.addListener(new MousePressedEventAdapter(MouseEvent3D.ButtonId.BUTTON1, action));
	}

	/**
	 * Agrega un Listener de zoom al contenedor comp.
	 * @param comp Contenedor al cual se le agrega el listener.
	 */
	public synchronized void addListenerZoom(Component3D comp) {
		comp.setAnimation(new NaturalMotionAnimation(1000));
		
		// Zoom con rueda del mouse
		comp.addListener(new MouseWheelEventAdapter(new GeonEventZoom()));
		
		// Zoom con teclado (flecha arriba y abajo)
		comp.addListener(new KeyPressedEventAdapter(new GeonEventZoom()));
	}

	/**
	 * Agrega un Listener de rotacion al contenedor comp.
	 * @param comp Contenedor al cual se le agrega el listener.
	 */
	public synchronized void addListenerRotation(Component3D comp) {
		// Rotacion con teclado (flecha derecha e izquierda)
		comp.addListener(new KeyPressedEventAdapter(new GeonEventRotation()));
	}

}
