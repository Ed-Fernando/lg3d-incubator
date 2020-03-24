package org.jdesktop.lg3d.apps.archviz3d.manifest3D;

import javax.vecmath.AxisAngle4f;
import javax.vecmath.Matrix4f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.sg.BoundingBox;
import org.jdesktop.lg3d.sg.Node;
import org.jdesktop.lg3d.sg.Transform3D;
import org.jdesktop.lg3d.sg.TransformGroup;
import org.jdesktop.lg3d.wg.Component3D;

import org.jdesktop.lg3d.apps.archviz3d.geons.components.GeonNumerator;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.BuildStrategy3D;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.Factory3D;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DComponent;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DObservable;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.UpdateBlock;



import java.util.Observable;
import java.util.Observer;

public abstract class J3DComponent extends Component3D implements Observer {
    /** Modelo (Abstractor)**/
    private Observable subject = null;
    /** Objeto observable para poder agregar dependientes a este objeto (no puedo heredar de observable)**/
    private J3DObservable observable = null;
    /** Builder**/
    protected BuildStrategy3D builder = null;
    /** Padre de este componente.**/
    private J3DComponent parent = null;
    /** Objetos para operaciones basicas: traslacion, escala, rotacion**/
    private TransformGroup transformGroup = null;
    private Transform3D matrix = null;
    /** Comando**/
    private UpdateBlock command = null;
    
    public J3DComponent() {
    	observable = new J3DObservable(this);
        matrix = new Transform3D();
        
        // Seteo capacidades
        setCapability(Component3D.ALLOW_DETACH);
        setCapability(Component3D.ALLOW_CHILDREN_EXTEND);
        setCapability(Component3D.ALLOW_CHILDREN_READ);
        setCapability(Component3D.ALLOW_CHILDREN_WRITE);
        setCapability(Component3D.ALLOW_BOUNDS_READ);
        setCapability(Component3D.ENABLE_PICK_REPORTING);
        
        // Creo la transformacion
        transformGroup = new TransformGroup();
        transformGroup.getTransform(matrix);

        // Seteo capacidades de la transformacion
        transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        transformGroup.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);
        transformGroup.setCapability(TransformGroup.ALLOW_CHILDREN_READ);
        transformGroup.setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);

        super.addChild(transformGroup);
    }

    /**Crea un J3DComponent
     * @param o Abstractor
     */
    public J3DComponent(Observable o) {
        this(o, null);
    }

    /**Crea un J3DComponent 
     * @param o Abstractor
     * @param builder
     */
    public J3DComponent(Observable o, BuildStrategy3D builder) {
        this();
        this.builder = builder;
        setSubject(o);
        build();
    }

    /**
     * Construye el componente y todos sus hijos 
     */
    public void build() {
        //strategy pattern
        if (builder != null) 
        	builder.build(this);
    }
    
    /**
     * Elimina el componente y todos sus hijos 
     */
    public void clearBuild() { 
		GeonNumerator.instance().initialize(); 
    	if (builder != null) 
    		builder.clearBuild(this);
    }
    
    /**
     * Reconstruye el componente y todos sus hijos 
     */
    public void rebuild() {
    	clearBuild();
    	build();
    }
    
/******************************************************************************************************/
/*										METODOS GET Y SET 											  */ 
/******************************************************************************************************/
    /**
     * Retorno el objeto observable (es decir esta clase)
     * @return objeto observable 
     */
    public Observable getObservable() {
    	return observable;
    }
    
    /**
     * Seteo el builder y construyo 
     * @param builder builder a setear
     */
    public void setBuilder(BuildStrategy3D builder) {
        this.builder = builder;
        build();
    }

    /**
     * Retorno el builder
     * @return builder
     */
    public BuildStrategy3D getBuilder() {
        return builder;
    }

    /**Setea el objeto a observar
     * @param o Abstractor
     */
    public void setSubject(Observable o) {
        subject = o;
    }

    /**Retorna el objeto observado (Abstractor)
     * @return subject Abstractor
     */
    public Observable getSubject() {
        return subject;
    }

    public void setTranslation(Vector3f v) {
        matrix.setTranslation(v);
        transformGroup.setTransform(matrix);
    }

    public void setTranslation(float x, float y, float z) {
        setTranslation(new Vector3f(x, y, z));
    }
    
    public Vector3f getTranslation() {
        Vector3f t = new Vector3f();
        matrix.get(t);
        return t;
    }

    public void setPosition(Point3f position) {
        this.setTranslation(new Vector3f(position)); 
        setChanged();
        notifyObservers();
    }

    public Point3f getPosition() {
        return new Point3f(getTranslation());
    }

    public void setRotation(AxisAngle4f a1) {
        matrix.setRotation(a1);
        transformGroup.setTransform(matrix);
    }

    public void addRotation(AxisAngle4f axis) {
    	// Obtengo la matriz de rotacion
    	Matrix4f rotationMatrix = Factory3D.instance().createRotationMatrix(axis);

    	// Obtengo la matriz actual
    	Matrix4f actualMatrix = new Matrix4f();
    	matrix.get(actualMatrix);

    	// Multiplo las matrices
    	actualMatrix.mul(rotationMatrix);
    	matrix.set(actualMatrix);

    	// Seteo la matriz
    	transformGroup.setTransform(matrix);
    }

    public void setScale(float scale) {
        matrix.setScale(scale);
        transformGroup.setTransform(matrix);
    }

    public float getScale() {
        return (float)matrix.getScale();
    }

    public BoundingBox getBoundingBox() {
    	BoundingBox boundingBox;
    	boundingBox = new BoundingBox(this.getBounds());
    	return boundingBox;
    }
    
    public void setTransformGroup(TransformGroup transformGroup) {
    	this.transformGroup = transformGroup;
    }
    
    public TransformGroup getTransformGroup( ) {
    	return transformGroup;
    }
    
	/**
	 * @param command The command to set.
	 */
	public void setCommand(UpdateBlock command) { 
		this.command = command;
	}

    /**
	 * @return Returns the command.
	 */
	public UpdateBlock getCommand() {
		return command;
	}
	
    public void setMatrix(Transform3D matrix) {
    	this.matrix = matrix;
    }
    
    public Transform3D getMatrix( ) {
    	return matrix;
    }
    
    public void setParent(J3DComponent parent) {
    	this.parent = parent;
    }
    
    public J3DComponent getParent() {
      return parent;
    }
    
    public String toString() {
    	return super.toString()+ "obs: " + (subject != null ? subject.toString() : "");
    }

    public void update(Observable o, Object arg) {
        if (builder != null) 
        	builder.update(this, o, arg);
        
        if (command != null) 
        	command.update(o, this, arg);
    }
    
    public  void addObserver(Observer o) {
        if (o == null)
            throw new NullPointerException();
        observable.addObserver(o);
	}
    
    public  void deleteObserver(Observer o) {
    	observable.deleteObserver(o);
    }
    
    public  void deleteObservers() {
    	observable.deleteObservers();
    }
    
    public void notifyObservers() {
    	observable.notifyObservers();
    }
    
    public void notifyObservers(Object arg) {
    	observable.notifyObservers(arg);
    }
    
    public void setChanged() {
    	observable.setChanged();
    }
    
    public void clearChanged() {
    	observable.clearChanged();
    }
    
    public boolean hasChanged() {
    	return observable.hasChanged();
    }
    
    public int countObservers() {
    	return observable.countObservers();
    }
    
    /**
     * No se puede agregar hijos a un J3DComponent.
     */
    @Deprecated
    public void addChild(Node Child) {
    }

}

