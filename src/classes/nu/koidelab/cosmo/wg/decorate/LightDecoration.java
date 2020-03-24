/*
 * 作成日: 2005/02/16
 */
package nu.koidelab.cosmo.wg.decorate;

import nu.koidelab.cosmo.manager.CosmoConfig;
import nu.koidelab.cosmo.util.function.OrbitFunction;

import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.sg.GeometryArray;
import org.jdesktop.lg3d.sg.PolygonAttributes;
import org.jdesktop.lg3d.sg.Shape3D;
import org.jdesktop.lg3d.sg.TextureAttributes;
import org.jdesktop.lg3d.sg.TransparencyAttributes;
import org.jdesktop.lg3d.sg.TriangleStripArray;
import org.jdesktop.lg3d.wg.Component3D;

/**
 * @author fumi LightDecoration org.wg.decorate
 * 
 */
public class LightDecoration extends Component3D {
    private static final float LINE_WIDTH = 0.0016f;// 1.3mm  ラインの太さ
    private static final int LINE_NODE_NUM = 20;
    private static final int VERTEX_COUNTS = (LINE_NODE_NUM + 2)*2;
    public static Appearance app;
    static{
        app = new Appearance();
        app.setTexture(CosmoConfig.getConfig().getLightTexture());
        app.setMaterial(null);
        app.setCapability(Appearance.ALLOW_TRANSPARENCY_ATTRIBUTES_READ);
        app.setCapability(Appearance.ALLOW_TRANSPARENCY_ATTRIBUTES_WRITE);
        app.setPolygonAttributes(new PolygonAttributes(
                PolygonAttributes.POLYGON_FILL, PolygonAttributes.CULL_NONE,
                0.0f, false, 0.0f));
        TransparencyAttributes ta = new TransparencyAttributes(TransparencyAttributes.BLENDED, 0.5f);
        ta.setCapability(TransparencyAttributes.ALLOW_BLEND_FUNCTION_WRITE);
        ta.setCapability(TransparencyAttributes.ALLOW_VALUE_WRITE);
        ta.setCapability(TransparencyAttributes.ALLOW_VALUE_READ);
        ta.setDstBlendFunction(TransparencyAttributes.BLEND_ONE);
        ta.setSrcBlendFunction(TransparencyAttributes.BLEND_SRC_ALPHA);
        app.setTransparencyAttributes(ta);
        TextureAttributes texa = new TextureAttributes();
        texa.setTextureMode(TextureAttributes.MODULATE);
        app.setTextureAttributes(texa);        
    }
    
    private Shape3D shape;
    
    public LightDecoration(){
        shape = new Shape3D();
        shape.setCapability(Shape3D.ALLOW_GEOMETRY_WRITE);
        shape.setAppearance(app);
        addChild(shape);
        setPickable(false);
    }
    
    public void setGeometry(long st, long ed, float[] color, OrbitFunction func){
        if(color.length != 3)
            throw new IllegalArgumentException("need color size = 3");
        float colorR = color[0];
        float colorG = color[1];
        float colorB = color[2];
        

        long space = (ed - st) / (LINE_NODE_NUM + 1);
        float[] coordinates = new float[VERTEX_COUNTS*3];
        float[] colors = new float[VERTEX_COUNTS*3];
        float[] texCoords = new float[VERTEX_COUNTS*2];
        float[] vec;
        for(int i = 0, loopNum = LINE_NODE_NUM + 2; i < loopNum; i++){
            vec = func.getPosition(st+(space*i));
            coordinates[6*i] = vec[0];
            coordinates[6*i + 1] = vec[1] - LINE_WIDTH;
            coordinates[6*i + 2] = vec[2];
            coordinates[6*i + 3] = vec[0];
            coordinates[6*i + 4] = vec[1] + LINE_WIDTH;
            coordinates[6*i + 5] = vec[2];

            colors[6*i] = colorR; 
            colors[6*i + 1] = colorG; 
            colors[6*i + 2] = colorB; 
            colors[6*i + 3] = colorR; 
            colors[6*i + 4] = colorG; 
            colors[6*i + 5] = colorB;
            
            texCoords[4*i] = 0.5f;
            texCoords[4*i + 1] = 0.0f;
            texCoords[4*i + 2] = 0.5f;
            texCoords[4*i + 3] = 1.0f;
        }        
        texCoords[0] = 1;
        texCoords[2] = 1;
        texCoords[VERTEX_COUNTS*2 - 4] = 0;
        texCoords[VERTEX_COUNTS*2 - 2] = 0;
        
        
        int[] stripVertexCounts = {VERTEX_COUNTS};        
        GeometryArray geom = new TriangleStripArray(VERTEX_COUNTS,
                GeometryArray.COORDINATES |
                GeometryArray.TEXTURE_COORDINATE_2 |
                GeometryArray.COLOR_3, stripVertexCounts);
        geom.setCoordinates(0, coordinates);
        geom.setTextureCoordinates(0, 0, texCoords);
        geom.setColors(0, colors);
        geom.setCapability(GeometryArray.ALLOW_COUNT_READ);
        geom.setCapability(GeometryArray.ALLOW_FORMAT_READ);
        geom.setCapability(GeometryArray.ALLOW_COORDINATE_READ);
        shape.removeAllGeometries();        
        shape.addGeometry(geom);
    }
}
