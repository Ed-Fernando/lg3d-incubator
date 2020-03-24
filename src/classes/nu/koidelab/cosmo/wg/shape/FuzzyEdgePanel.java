package nu.koidelab.cosmo.wg.shape;

import org.jdesktop.lg3d.sg.Geometry;
import org.jdesktop.lg3d.sg.GeometryArray;
import org.jdesktop.lg3d.sg.IndexedQuadArray;
import org.jdesktop.lg3d.sg.QuadArray;

public class FuzzyEdgePanel extends Panel{
	private float edge;
	
    public FuzzyEdgePanel(float width, float height, boolean shiny, float edge) {
    	this(width, height, 0, 0, 0, shiny, edge);
	}	
	
    public FuzzyEdgePanel(float width, float height, float offsetX, float offsetY, float offsetZ, boolean shiny, float edge){
    	super(width, height, offsetX, offsetY, offsetZ, shiny);
    	this.edge = edge;
    }
    
    
	protected void setColors(){
    	initGeometry(
    			QuadArray.COORDINATES |
    			QuadArray.COLOR_4 |
    			QuadArray.TEXTURE_COORDINATE_2 |
    			((shiny) ? (QuadArray.NORMALS) : (0)));
    	float[] colors = {
    			red, green, blue, 0,
    			red, green, blue, alpha,
    	};
    	int[] colorIndices = {
    			0, 0, 1, 1,
    			0, 0, 1, 1,
    			0, 0, 1, 1,
    			0, 0, 1, 1,
    			1, 1, 1, 1,
    			};
    	geometry.setColors(0, colors);
    	geometry.setColorIndices(0, colorIndices);
    }
    
	protected void setCoords() {
		float w = width / 2.0f;
		float h = height / 2.0f;
		float[] coords = {
				-w + offsetX, -h + offsetY, offsetZ,
				w + offsetX, -h + offsetY, offsetZ,
				w + offsetX, h + offsetY, offsetZ,
				-w + offsetX, h + offsetY, offsetZ,
				-w + offsetX + edge, -h + offsetY + edge, offsetZ,
				w + offsetX - edge, -h + offsetY + edge, offsetZ,
				w + offsetX - edge, h + offsetY - edge, offsetZ,
				-w + offsetX + edge, h + offsetY - edge, offsetZ,
		};
		geometry.setCoordinates(0, coords);
	}	
	
    protected void initGeometry(int Format){
    	geometry = new IndexedQuadArray(20, 
    			Format,
    			1, new int[] {0}, 20);
    	setCoords();    	
       int[] indices = {
    		   0, 1, 5, 4,
    		   1, 2, 6, 5,
    		   2, 3, 7, 6,
    		   3, 0, 4, 7,
    		   4, 5, 6, 7
    		   };
       geometry.setCoordinateIndices(0, indices);
       float[] texCoords = { 
    		   0.0f, 1.0f,
    		   1.0f, 1.0f,
    		   1.0f, 0.0f,
    		   0.0f, 0.0f,
    		   /* is this correct? */
    		   0.0f, 0.0f,
    		   0.0f, 0.0f,
    		   0.0f, 0.0f,
    		   0.0f, 0.0f,
    		 	};
       geometry.setTextureCoordinates(0, 0, texCoords);
       geometry.setTextureCoordinateIndices(0, 0, indices);

       float[] normals = { 0.0f, 0.0f, 1.0f, };
       int[] normalIndices = { 
    		   0, 0, 0, 0,
    		   0, 0, 0, 0,
    		   0, 0, 0, 0,
    		   0, 0, 0, 0,
    		   0, 0, 0, 0,
    		   };
       if (shiny) {
           geometry.setNormals(0, normals);
           geometry.setNormalIndices(0, normalIndices);
       }
       geometry.setCapability(Geometry.ALLOW_INTERSECT);
       geometry.setCapability(GeometryArray.ALLOW_COUNT_READ);
       geometry.setCapability(GeometryArray.ALLOW_FORMAT_READ);
       geometry.setCapability(GeometryArray.ALLOW_COORDINATE_READ);
       geometry.setCapability(IndexedQuadArray.ALLOW_COORDINATE_INDEX_READ);
       geometry.setCapability(IndexedQuadArray.ALLOW_COLOR_WRITE);
       geometry.setCapability(IndexedQuadArray.ALLOW_COLOR_INDEX_WRITE);
       setGeometry(geometry);    	
    }

}
