package nu.koidelab.cosmo.util.function.parabola.decoration;

import nu.koidelab.cosmo.manager.CosmoConfig;
import nu.koidelab.cosmo.util.function.OrbitFunction;

import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.sg.GeometryArray;
import org.jdesktop.lg3d.sg.PolygonAttributes;
import org.jdesktop.lg3d.sg.Shape3D;
import org.jdesktop.lg3d.sg.TextureAttributes;
import org.jdesktop.lg3d.sg.TransparencyAttributes;
import org.jdesktop.lg3d.sg.TriangleStripArray;

public class OrbitalLightShape extends Shape3D{
    private float lineWidth;
    private int lineNodeNum;
    private int vertexCounts;
    private TriangleStripArray geom;
    
    public OrbitalLightShape() {
    	this(0.0016f, 20);
    }
    
    public OrbitalLightShape(float lineWidth, int lineNodeNum) {
        this.lineWidth = lineWidth;
        this.lineNodeNum = lineNodeNum;
        this.vertexCounts = (lineNodeNum + 2) * 2;
    	int[] stripVertexCounts = { vertexCounts };
        geom = new TriangleStripArray(vertexCounts,
                GeometryArray.COORDINATES
                        | GeometryArray.TEXTURE_COORDINATE_2
                        | GeometryArray.COLOR_3, stripVertexCounts);
        geom.setCapability(GeometryArray.ALLOW_TEXCOORD_WRITE);
        geom.setCapability(GeometryArray.ALLOW_COORDINATE_WRITE);
        geom.setCapability(GeometryArray.ALLOW_COLOR_WRITE);        
        setGeometry(geom);
    }

    public void setGeometry(long st, long ed, float[] color,
            OrbitFunction func) {
        if (color.length != 3)
            throw new IllegalArgumentException("need color size = 3");
        float colorR = color[0];
        float colorG = color[1];
        float colorB = color[2];

        long space = (ed - st) / (lineNodeNum + 1);
        float[] coordinates = new float[vertexCounts * 3];
        float[] colors = new float[vertexCounts * 3];
        float[] texCoords = new float[vertexCounts * 2];
        float[] vec;
        for (int i = 0, loopNum = lineNodeNum + 2; i < loopNum; i++) {
            vec = func.getPosition(st + (space * i));
            coordinates[6 * i] = vec[0];
            coordinates[6 * i + 1] = vec[1] - lineWidth;
            coordinates[6 * i + 2] = vec[2];
            coordinates[6 * i + 3] = vec[0];
            coordinates[6 * i + 4] = vec[1] + lineWidth;
            coordinates[6 * i + 5] = vec[2];

            colors[6 * i] = colorR;
            colors[6 * i + 1] = colorG;
            colors[6 * i + 2] = colorB;
            colors[6 * i + 3] = colorR;
            colors[6 * i + 4] = colorG;
            colors[6 * i + 5] = colorB;

            texCoords[4 * i] = 0.5f;
            texCoords[4 * i + 1] = 0.0f;
            texCoords[4 * i + 2] = 0.5f;
            texCoords[4 * i + 3] = 1.0f;
        }
        texCoords[0] = 1;
        texCoords[2] = 1;
        texCoords[vertexCounts * 2 - 4] = 0;
        texCoords[vertexCounts * 2 - 2] = 0;

        geom.setCoordinates(0, coordinates);
        geom.setTextureCoordinates(0, 0, texCoords);
        geom.setColors(0, colors);
    }

    
    public static Appearance getLightAppearance(){
        Appearance tmpApp = new Appearance();
        tmpApp.setTexture(CosmoConfig.getConfig().getLightTexture());
        tmpApp.setMaterial(null);
        tmpApp.setCapability(Appearance.ALLOW_TRANSPARENCY_ATTRIBUTES_READ);
        tmpApp.setCapability(Appearance.ALLOW_TRANSPARENCY_ATTRIBUTES_WRITE);
        tmpApp.setPolygonAttributes(new PolygonAttributes(
                PolygonAttributes.POLYGON_FILL, PolygonAttributes.CULL_NONE,
                0.0f, false, 0.0f));
        TransparencyAttributes ta = new TransparencyAttributes(TransparencyAttributes.BLENDED, 0.5f);
        ta.setCapability(TransparencyAttributes.ALLOW_BLEND_FUNCTION_WRITE);
        ta.setCapability(TransparencyAttributes.ALLOW_VALUE_WRITE);
        ta.setCapability(TransparencyAttributes.ALLOW_VALUE_READ);
        ta.setDstBlendFunction(TransparencyAttributes.BLEND_ONE);
        ta.setSrcBlendFunction(TransparencyAttributes.BLEND_SRC_ALPHA);
        tmpApp.setTransparencyAttributes(ta);
        TextureAttributes texa = new TextureAttributes();
        texa.setTextureMode(TextureAttributes.MODULATE);
        tmpApp.setTextureAttributes(texa);
        return tmpApp;
    }
}

