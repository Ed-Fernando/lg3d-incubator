package org.jdesktop.lg3d.apps.archviz3d.manifest3D.builders;

import org.jdesktop.lg3d.apps.archviz3d.geons.GeonAbstract;
import org.jdesktop.lg3d.apps.archviz3d.geons.GeonLink;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.BuildStrategy3D;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.Factory3D;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DComponent;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DSimpleComponent;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.objects.ManhatanLink3D;
import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.sg.BoundingBox;
import org.jdesktop.lg3d.utils.shape.Cylinder;



import java.util.Enumeration;
import java.util.Observable;

import javax.vecmath.AxisAngle4f;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

/**
 * Esta clase se encarga de dibujar el arco Manhatan y de sus propiedades graficas.
 * 
 * @author Gaston Peirano & Mauricio Poncio
 * 
 */
public class ManhatanLinkBuilder3D extends BuildStrategy3D {
	/**Flecha del link.*/
	private J3DSimpleComponent flecha;
	/**Contendor del link.*/
	private ManhatanLink3D arco;
	/**Orientacion.*/
	private static Vector3f initVector =  new Vector3f(0f, 1f, 0f);  // default orientation
	/**Radio del cilindro que representa al arco.*/
	protected float radius = 0.02f;

	/**
	 * Genera un ManhatanLinkBuild3D vacio.
	 */
	public ManhatanLinkBuilder3D() {
	}

	/**
	 * Retorna la flecha del link
	 * @return J3DSimpleComponent flecha del link
	 */
	public J3DSimpleComponent flecha() {
		return flecha;
	}

	/**
	 * Setea la flecha del link
	 * @param flecha flecha del link
	 */
	public void setFlecha(J3DSimpleComponent flecha) {
		this.flecha = flecha;
	}

	/**
	 * Crea la flecha que indica la direccion en el extremo final del link.
	 * @param arco arco al cual se le agrega la flecha
	 */
	private void createFlecha(ManhatanLink3D arco) {
		Point3f prin,fin;
		float longF;
		float rot= 0;
		Vector3f trans;

		prin = (Point3f)arco.getVertexs().get(0);
		fin = (Point3f)arco.getVertexs().get(3);
		longF = arco.longitudFlecha();
		Vector3f eje;
		if (prin.x == fin.x) {
			eje = new Vector3f(1,0,0);
			if (prin.y == fin.y) {
				if (prin.z > fin.z) {
					rot = (float)-Math.PI/2;  //-90 grados
					trans = new Vector3f(fin.x,fin.y,fin.z + arco.longitudFlecha());
				}
				else {
					rot = (float)Math.PI/2;  //90 grados
					trans = new Vector3f(fin.x,fin.y,fin.z - arco.longitudFlecha());
				}
			}
			else {
				if (prin.y > fin.y) {
					rot = (float)Math.PI;   //180 grados
					trans = new Vector3f(fin.x,fin.y + arco.longitudFlecha(),fin.z);
				}
				else
					trans = new Vector3f(fin.x,fin.y -  arco.longitudFlecha(),fin.z);
			}
		}
		else {
			eje = new Vector3f(0,0,1);
			if (prin.x < fin.x) {
				rot = (float)-Math.PI/2;  //-90 grados
				trans = new Vector3f(fin.x  - arco.longitudFlecha(),fin.y,fin.z);
			}
			else {
				rot = (float)Math.PI/2;  //90 grados
				trans = new Vector3f(fin.x + arco.longitudFlecha(),fin.y,fin.z);
			}
		}

		// Creo la apariencia de la Flecha
		Appearance apEdge = Factory3D.instance().createAppearance(new Color3f(0.1f,0.4f,0.1f));

		// Creo la flecha
		J3DSimpleComponent aFlecha = Factory3D.instance().createCone(longF/2, longF, apEdge);
		setFlecha(aFlecha);
		aFlecha.setRotation(new AxisAngle4f(eje,rot));
		aFlecha.setTranslation(trans);

		// Agrego la flecha
		arco.addComponent(aFlecha);
	}

	/**
	 * Crea la parte grafica del arco Manhatan.
	 * @param client el arco Manhatan
	 */
	public void build(J3DComponent client) {
		arco = (ManhatanLink3D)client;

		Enumeration enume = arco.getVertexs().elements();
		Point3f prin = (Point3f)enume.nextElement();
		Point3f fin;
		int index = 1;
		
		while (enume.hasMoreElements() ) {
			fin = (Point3f)enume.nextElement();
			if (prin.distance(fin) != 0) {
				// Creo la apriencia para el link
				Color3f color = ((GeonAbstract)client).getColor();
				Appearance apLink = Factory3D.instance().createAppearance(color);

				// Creo el Link y lo agrego 
//				if (GeonRepaired.instance().drawConnector(new String(arco.getName() + index))) {
					GeonLink cylinder = createCylinder(radius, prin.distance(fin), apLink);
					cylinder.setRotation(getPositionAngle(new Vector3f(prin.x-fin.x, prin.y-fin.y, prin.z-fin.z)));
					cylinder.setTranslation(getPositionVector(prin, fin));
					cylinder.setName(arco.getName() + index);
					arco.addComponent(cylinder); 
//				}
				index++;

				prin = fin;

				// Creo la apariencia de los codos del link (los circulos)
				Appearance apEdge = Factory3D.instance().createAppearance(new Color3f(0.1f,0.5f,0.5f));

				// Creo el codo
				J3DSimpleComponent sphere = Factory3D.instance().createSphere(0.035f, apEdge);
				sphere.setTranslation(fin.x, fin.y, fin.z);

				// Agrego el codo
				arco.addComponent(sphere);
			}
		}
		// Creo la flecha
		createFlecha(arco);
	}

	public void update(J3DComponent client, Observable o, Object arg) {
		arco.linePoints();
		arco.removeAllComponents();
		build(client);
	}

	/**
	 * Averigua el vector sobre el que se debe hacer una rotaci�n y el �ngulo de la misma.
	 * @param v vector que representa al arco
	 * @return un AxisAngle4f
	 */
	private AxisAngle4f getPositionAngle(Vector3f v) {
		Vector3f norm = new Vector3f();
		norm.cross(initVector,v);
		return new AxisAngle4f(norm.x,norm.y,norm.z,initVector.angle(v));
	}

	/**
	 * Calcula el vector exacto del arco.
	 * @param prin origen del arco
	 * @param fin fin del arco
	 * @return el vector exacto del arco
	 */
	private Vector3f getPositionVector(Point3f prin,Point3f fin) {
		Vector3f v = new Vector3f(prin.x-fin.x,prin.y-fin.y,prin.z-fin.z);
		v.scaleAdd(0.5f,fin);
		return new Vector3f(v);
	}
	
	/**
	 * Creo una parte del Link.
	 * @param rad radio
	 * @param lon altura
	 * @param ap Apariencia
	 * @return Cilindro
	 */
	private GeonLink createCylinder(float rad, float lon, Appearance ap) {
		Cylinder cylinder = new Cylinder(rad, lon, Cylinder.GENERATE_NORMALS
				| Cylinder.GENERATE_TEXTURE_COORDS, ap);

		BoundingBox bounds = new BoundingBox();
		float lx = -rad;
		float ly = -lon / 2.0f;
		float lz = -rad;

		bounds.setLower(lx, ly, lz);
		bounds.setUpper(-lx, -ly, -lz);

		GeonLink obj = new GeonLink(cylinder, null);
		obj.setBounds(bounds);
		obj.setBoundsAutoCompute(false);

		return obj;
	}
	
}
