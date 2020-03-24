package nu.koidelab.cosmo.wg.shape;

import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.sg.GeometryArray;
import org.jdesktop.lg3d.sg.IndexedQuadArray;
import org.jdesktop.lg3d.sg.QuadArray;
import org.jdesktop.lg3d.sg.Shape3D;

public class Panel extends Shape3D {
    protected float width;
    protected float height;
    protected float offsetX;
    protected float offsetY;
    protected float offsetZ;
    
    protected IndexedQuadArray geometry;
	protected float red = 1;
	protected float green = 1;
	protected float blue = 1;
	protected float alpha = 1;
	protected boolean shiny;

    public Panel(float width, float height, boolean shiny){
    	this(width, height, 0, 0, 0, shiny);
    }
    
    public Panel(float width, float height, float offsetX, float offsetY, float offsetZ, boolean shiny){
    	this.width = width;
    	this.height = height;
    	this.offsetX = offsetX;
    	this.offsetY = offsetY;
    	this.offsetZ = offsetZ;
    	this.shiny = shiny;
    	
    	initGeometry(
    			QuadArray.COORDINATES |
    			QuadArray.TEXTURE_COORDINATE_2 |
    			((shiny) ? (QuadArray.NORMALS) : (0)));
    	setCapability(Shape3D.ALLOW_GEOMETRY_WRITE);
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
    
    public void setColor(float red, float green, float blue){
    	this.red = red;
    	this.green = green;
    	this.blue = blue;
    	setColors();
    }
    
    public void setAlpha(float alpha){
    	this.alpha = alpha;
    	setColors();
    }
    
    protected void setColors(){
    	initGeometry(
    			QuadArray.COORDINATES |
    			QuadArray.COLOR_4 |
    			QuadArray.TEXTURE_COORDINATE_2 |
    			((shiny) ? (QuadArray.NORMALS) : (0)));
    	float[] colors = {
    			red, green, blue, alpha,
    			red, green, blue, alpha,
    			red, green, blue, alpha,
    			red, green, blue, alpha,
    	};
    	geometry.setColors(0, colors);
    }
    
	protected void setCoords() {
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
	
    protected void initGeometry(int Format){
    	geometry = new IndexedQuadArray(4, 
    			Format,
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
       geometry.setCapability(IndexedQuadArray.ALLOW_COLOR_WRITE);

       setGeometry(geometry);    	
    }
}
