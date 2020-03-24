package org.jdesktop.lg3d.apps.archviz3d.geons.builders;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jdesktop.lg3d.sg.QuadArray;
import org.jdesktop.lg3d.sg.Shape3D;

import org.jdesktop.lg3d.apps.archviz3d.geons.builders.GeonBuilderSur;

import com.sun.j3d.utils.geometry.GeometryInfo;
import com.sun.j3d.utils.geometry.NormalGenerator;


/**
 * Esta clase se encarga de construir los Geones a partir de los
 * files .sur. 
 * 
 * @author Juan Feldman
 *
 */
public class GeonBuilderSur {
	/** Logger de mensajes.*/
	private static Logger logger = Logger.getLogger("lg.ArchViz3D");	
    /** Contiene las coordenas de los vertices.**/
    private float[] floatVerts;
    /** Contiene los indices que indican el orden en el que se dibujan los vertices.**/
    private int[] indices;
    /** NroI: cantidad de indices (poligonos) de la figura.
     *  NroV: cantidad de vertices de la figura.*/
    private int nroI, nroV;
    /** creaseAngle.*/
    private float creaseAngle;
    /** Mapea el nombre de un Geon a la imagen que lo representa.*/
    private Hashtable<String,Shape3D> mapGeonToGeonImage= new Hashtable<String,Shape3D>();
    /** Esta clase es un Singleton.*/
    private static GeonBuilderSur geonBuilderSur;
    
    /**
     * Cuando se instancia el Singleton se construyen todos los Geones.
     */
    private GeonBuilderSur() {
    	String pathJar = System.getProperty("lg.appcodebase");
    	InputStream jis = null;

    	try {
			URL url = new URL("jar:" + pathJar + "/ArchViz3D.jar!/resources/images/geons-sur/");
			JarURLConnection connection = (JarURLConnection)url.openConnection();
			JarFile jarFile = connection.getJarFile();
			
			Enumeration<JarEntry> entries = jarFile.entries();
            while(entries.hasMoreElements()) {
                JarEntry jarEntry = entries.nextElement();
                jis = jarFile.getInputStream(jarEntry);
                
                if (jarEntry.getName().endsWith(".sur")) {
            		logger.log(Level.INFO, "Building Geon: " + new File(jarEntry.getName()).getName());
	                mapGeonToGeonImage.put(new File(jarEntry.getName()).getName().replace(".sur", ""), buildGeon(jis));
                }
            }
    	} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    /**
     * Retorna la unica instancia de esta clase.
     * @return GeonBuilderSur Unica instancia de esta clasa.
     */
    public synchronized static GeonBuilderSur instance() {
        if (geonBuilderSur == null)
            geonBuilderSur = new GeonBuilderSur();
        return geonBuilderSur;
    }

    /**
     * Lee el Geon del archivo que se recibe como parametro.
     * @param inputStream InputStream del file que contiene el Geon 
     * que se quiere leer.
     */
    private void readFile(InputStream inputStream) {
        try {
            StreamTokenizer t = new StreamTokenizer(new InputStreamReader(inputStream));
            
            // Levanto los encabezados
            for (int i = 1; i <= 5; i++)
                t.nextToken();

            // Levanto la cantidad de poligonos
            t.nextToken();
            nroI = (int)t.nval;

            // Levanto el creaseAngle
            t.nextToken();
            t.nextToken();
            creaseAngle= (float)t.nval;
            
            // Levanto los encabezados
            for (int i = 1; i <= 2; i++)
                t.nextToken();
            
            // Levanto los indices de los vertices
            indices = new int[nroI*4];
            for (int i = 0; i < nroI; i++)
                for (int j = 0; j < 4; j++) {
                    t.nextToken();
                    indices[(i*4) + j] = (int)t.nval;
                }
            
            // Levanto los encabezados
            for (int i = 1; i <= 2; i++)
                t.nextToken();
            
            // Levanto la cantidad de vertices
            t.nextToken();
            nroV = (int)t.nval;
            floatVerts = new float[nroV*3];
            
            // Levanto los vertices
            for (int i = 0; i < nroV; i++) {
                for (int j = 0; j < 3; j++) {
                    t.nextToken();
                    floatVerts[i*3 + j] = (float)t.nval;
                }
            }
        }  catch (IOException e) {
			e.printStackTrace();
        }
    }

    /**
     * Arma el Geon, obteniendo para cada vertice sus coordenadas.
     * @param inputStream InputStream del file que contiene el Geon 
     * que se quiere leer. 
     */
    private void processFile(InputStream inputStream) {
        readFile(inputStream);
        
        float[] aux = new float[nroI*3*4];
        for (int i = 0; i < nroI*4; i++) {
            int pol = indices[i];  
            
            // Levanto las coordenadas en X, Y y Z del vertice pol
            for (int j = 0; j < 3; j++) {
                aux[i*3 + j] = floatVerts[pol*3 + j];
            }
        }
        floatVerts= aux;
    }
    
	/** 
	 * Construye el Geon. 
     * @param inputStream InputStream del file que contiene el Geon 
     * que se quiere leer.
     * @return Shape3D Imagen del Geon.  
     */
    private Shape3D buildGeon(InputStream inputStream) {
	    processFile(inputStream);
        
        // Calculo las normales
        GeometryInfo gi = new GeometryInfo(GeometryInfo.QUAD_ARRAY);
        gi.setCoordinates(floatVerts);
        
        NormalGenerator normalGenerator= new NormalGenerator();
        normalGenerator.setCreaseAngle(creaseAngle);
        normalGenerator.generateNormals(gi);

        // ARMO LA FIGURA
        QuadArray tetra = new QuadArray(nroI*4,
                QuadArray.COORDINATES|
                QuadArray.NORMALS);

        tetra.setCoordinates(0, floatVerts, 0, nroI*4);

        // Seteo normales
        javax.media.j3d.GeometryArray ga= gi.getGeometryArray();
//        for (int i=0; i < nroI*4; i++) {
//      	float[] normals= new float[3];
//          ga.getNormal(i,normals);
//        	tetra.setNormal(i, normals);
//        }
        ga.getNormals(0, floatVerts);
        tetra.setNormals(0, floatVerts);

        Shape3D shape = new Shape3D(tetra);
        
        // Seteo capacidades
        shape.setCapability(Shape3D.ALLOW_APPEARANCE_WRITE);
        shape.setCapability(Shape3D.ALLOW_APPEARANCE_READ);
        shape.setCapability(Shape3D.ALLOW_BOUNDS_READ);
        
        return shape;  
    }

    /** 
     * Construye el Geon cuyo nombre se recibe como parametro.
     * @param geonName Nombre del Geon a construir.
     * @return Shape3D Geon construido. 
     */
    public synchronized Shape3D buildGeonByName(String geonName) {
        geonName = geonName.toLowerCase(); 

        if (mapGeonToGeonImage.containsKey(geonName))
            return copyShape((Shape3D)((Shape3D)mapGeonToGeonImage.get(geonName)));
        else
            return copyShape((Shape3D)((Shape3D)mapGeonToGeonImage.get("barrel")));
    }

    /**
     * Copia el Shape que se recibe como parametro.
     * @param shape Shape a copiar.
     * @return Shape3D Shape copiado.
     */
    private Shape3D copyShape(Shape3D shape) {
    	Shape3D shapeAux = new Shape3D();
        shapeAux.setCapability(Shape3D.ALLOW_APPEARANCE_WRITE);
        shapeAux.setCapability(Shape3D.ALLOW_APPEARANCE_READ);
        shapeAux.setCapability(Shape3D.ALLOW_BOUNDS_READ);

        shapeAux.setGeometry(shape.getGeometry());
        return shapeAux;
    }

}