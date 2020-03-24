/*
 * ��.��: 2005/03/02
 */
package nu.koidelab.cosmo.wg.shape;

import java.awt.Color;
import java.awt.Font;

import org.jdesktop.lg3d.wg.Toolkit3D;
import org.jdesktop.lg3d.sg.GeometryArray;
import org.jdesktop.lg3d.sg.IndexedQuadArray;
import org.jdesktop.lg3d.sg.Shape3D;

/**
 * @author fumi StringPanel org.wg.shape
 */
public abstract class StringPanel extends Shape3D {
    protected float width;
    protected float height;
    protected boolean shiny = false;
    protected float offsetX = 0;
    protected float offsetY = 0;
    protected float offsetZ = 0;
    protected float scale = 1;
    protected Color fgColor;
    protected Color bgColor;
    protected Font font;
    protected boolean isCentering = false;
    protected IndexedQuadArray geometry; 
    protected int spacer = 15;

    public StringPanel() {
        super();
        setCapability(Shape3D.ALLOW_PICKABLE_READ
                | Shape3D.ALLOW_PICKABLE_WRITE | Shape3D.ENABLE_PICK_REPORTING
                | Shape3D.ENABLE_COLLISION_REPORTING);
    }    

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getSpace() {
//        return spacer * CosmoConfig.UNIT_TRANS_FACTOR;
        return Toolkit3D.getToolkit3D().widthNativeToPhysical(spacer);
    }

    public void setFont(Font font) {
        this.font = font;
    }
    
    public void setForeGround(Color color){
        fgColor = color;                
    }

    public void setBackGround(Color color){
        bgColor = color;                
    }

    public void setCentering(boolean arg){
        isCentering = arg;
    }
    
    public void setOffset(float x, float y, float z){
    	offsetX = x;
    	offsetY = y;
    	offsetZ = z;
    	resetCoords();
    }
    
    public void setScale(float scale){
    	this.scale = scale;
    	resetCoords();
    }
    
    private void resetCoords(){
        float w = width / 2.0f * scale;
        float h = height / 2.0f * scale;
        float[] coords = {
     		   -w + offsetX, -h + offsetY, offsetZ,
     		   w + offsetX, -h + offsetY, offsetZ,
     		   w + offsetX, h + offsetY, offsetZ,
     		   -w + offsetX, h + offsetY, offsetZ,
         };
        geometry.setCoordinates(0, coords);    	
    }

    protected void makePanel(float width, float height, boolean shiny) {
        float w = width / 2.0f;
        float h = height / 2.0f;
 
        float[] coords = { -w, -h, 0.0f, w, -h, 0.0f, w, h, 0.0f, -w, h, 0.0f, };

        int[] indices = { 0, 1, 2, 3, };

        float[] texCoords = { 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, };

        float[] normals = { 0.0f, 0.0f, 1.0f, };

        int[] normalIndices = { 0, 0, 0, 0, };

        IndexedQuadArray geometry = new IndexedQuadArray(4,
                GeometryArray.COORDINATES | GeometryArray.TEXTURE_COORDINATE_2
                        | ((shiny) ? (GeometryArray.NORMALS) : (0)), 1,
                new int[] { 0 }, 4);

        geometry.setCoordinates(0, coords);
        geometry.setCoordinateIndices(0, indices);
        geometry.setTextureCoordinates(0, 0, texCoords);
        geometry.setTextureCoordinateIndices(0, 0, indices);
        if (shiny) {
            geometry.setNormals(0, normals);
            geometry.setNormalIndices(0, normalIndices);
        }
        geometry.setCapability(GeometryArray.ALLOW_COORDINATE_WRITE);
//        geometry.setCapability(Geometry.ALLOW_INTERSECT);
//        geometry.setCapability(GeometryArray.ALLOW_COUNT_READ);
//        geometry.setCapability(GeometryArray.ALLOW_FORMAT_READ);
//        geometry.setCapability(GeometryArray.ALLOW_COORDINATE_READ);
//        geometry.setCapability(IndexedQuadArray.ALLOW_COORDINATE_INDEX_READ);

        this.geometry = geometry;
        setGeometry(this.geometry);
    }
}
