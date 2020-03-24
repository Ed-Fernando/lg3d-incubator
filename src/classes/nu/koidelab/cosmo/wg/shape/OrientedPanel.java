package nu.koidelab.cosmo.wg.shape;

import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.sg.GeometryArray;
import org.jdesktop.lg3d.sg.IndexedQuadArray;
import org.jdesktop.lg3d.sg.OrientedShape3D;
import org.jdesktop.lg3d.sg.QuadArray;

public class OrientedPanel extends OrientedShape3D {	
    protected float width;
    protected float height;
    protected float offsetX;
    protected float offsetY;
    protected float offsetZ;
    
    protected IndexedQuadArray geometry;

    public OrientedPanel(float width, float height, boolean shiny){
    	this(width, height, 0, 0, 0, shiny);
    }
    
    public OrientedPanel(float width, float height, float offsetX, float offsetY, float offsetZ, boolean shiny){
    	this.width = width;
    	this.height = height;
    	this.offsetX = offsetX;
    	this.offsetY = offsetY;
    	this.offsetZ = offsetZ;
    	
    	geometry = new IndexedQuadArray(4, 
    			QuadArray.COORDINATES |
    			QuadArray.TEXTURE_COORDINATE_2 |
    			((shiny) ? (QuadArray.NORMALS) : (0)),
    			1, new int[] {0}, 4);
    	setCoords();
       int[] indices = { 0, 1, 2, 3, };
       geometry.setCoordinateIndices(0, indices);

       float[] texCoords = { 
    		   0.0f, 1.0f,
    		   1.0f, 1.0f,
    		   1.0f, 0.0f,
    		   0.0f, 0.0f,
    		 	};
       geometry.setTextureCoordinates(0, 0, texCoords);
       geometry.setTextureCoordinateIndices(0, 0, indices);

       float[] normals = { 0.0f, 0.0f, 1.0f, };
       int[] normalIndices = { 0, 0, 0, 0, };
       if (shiny) {
           geometry.setNormals(0, normals);
           geometry.setNormalIndices(0, normalIndices);
       }
       geometry.setCapability(GeometryArray.ALLOW_COORDINATE_WRITE);
//       geometry.setCapability(Geometry.ALLOW_INTERSECT);
//       geometry.setCapability(GeometryArray.ALLOW_COUNT_READ);
//       geometry.setCapability(GeometryArray.ALLOW_FORMAT_READ);
//       geometry.setCapability(GeometryArray.ALLOW_COORDINATE_READ);
//       geometry.setCapability(IndexedQuadArray.ALLOW_COORDINATE_INDEX_READ);
       setGeometry(geometry);
       
       setAlignmentMode(OrientedShape3D.ROTATE_NONE);
    }
    
    public void setOffset(float x, float y, float z){
    	offsetX = x;
    	offsetY = y;
    	offsetZ = z;
       setCoords();
    }
    
    public void setSize(float width, float height){
    	this.width = width;
    	this.height = height;
    	setCoords();
    }
    
    public float getWidth() {
		return width;
	}
    
    public float getHeight() {
		return height;
	}
    
    public void getOffset(Vector3f vec) {
    	vec.set(offsetX, offsetY, offsetZ);
	}
    
	private void setCoords() {
		float w = width / 2.0f;
		float h = height / 2.0f;
		float[] coords = {
				-w + offsetX, -h + offsetY, offsetZ,
				w + offsetX, -h + offsetY, offsetZ,
				w + offsetX, h + offsetY, offsetZ,
				-w + offsetX, h + offsetY, offsetZ,
		};
		geometry.setCoordinates(0, coords);
	}
	
	
}
