package nu.koidelab.cosmo.wg.shape;

import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.sg.GeometryArray;
import org.jdesktop.lg3d.sg.IndexedGeometryArray;
import org.jdesktop.lg3d.sg.IndexedQuadArray;
import org.jdesktop.lg3d.sg.Shape3D;
import org.jdesktop.lg3d.utils.shape.ShadowAppearance;

/**
 * @author fumi_ss
 * This Rectshadow extends org.jdesktop.lg3d.utils.shape.RectShadow;
 * This class surpports offset values.
 */
public class RectShadow extends Shape3D {
    private static float sin00 = 0.00000000f;
    private static float sin23 = 0.38268343f;
    private static float sin45 = 0.70710677f;
    private static float sin68 = 0.92387950f;
    private static float sin90 = 1.00000000f;

    private IndexedGeometryArray geometry;
    private float north;
    private float east;
    private float south;
    private float west;
    private float inner;
    private float xShift;
    private float yShift;
    private float zShift;


    
    public RectShadow(float width, float height,
           float north, float east, float south, float west, float inner,
           float zShift, float alpha){
        this(width, height, north, east, south, west, inner, 0, 0, zShift, alpha);
    }
    
    
    public RectShadow(float width, float height,
           float north, float east, float south, float west, float inner,
           float xShift, float yShift, float zShift, float alpha){
    this.north = north;
    this.east = east;
    this.south = south;
    this.west = west;
    this.inner = inner;
    this.xShift = xShift;
    this.yShift = yShift;
    this.zShift = zShift;

    setPickable(false);

    Appearance app = new ShadowAppearance();
    setAppearance(app);

    geometry
        = new IndexedQuadArray(
        28,
        GeometryArray.COORDINATES
        | GeometryArray.COLOR_4,
        0, null,
        64);
    geometry.setCapability(GeometryArray.ALLOW_COORDINATE_WRITE);
    setGeometry(geometry);

    setSize(width, height);

    int[] indices = {
         0,  4,  5,  6,
         0,  6,  7,  8,

         0,  8,  9,  1,

         1,  9, 10 ,11,
         1, 11, 12, 13,

         1, 13, 14,  2,

         2, 14, 15, 16,
         2, 16, 17, 18,

         2, 18, 19,  3,

         3, 19, 20, 21,
         3, 21, 22, 23,

         3, 23,  4,  0,

         0,  1, 25, 24,
         1,  2, 26, 25,
         2,  3, 27, 26,
         3,  0, 24, 27,
    };

    float[] colors4 = {
        0.0f, 0.0f, 0.0f, 0.0f,
        0.0f, 0.0f, 0.0f, alpha,
    };

    int[] colorIndices = {
        1, 0, 0, 0,
        1, 0, 0, 0,
        1, 0, 0, 1,

        1, 0, 0, 0,
        1, 0, 0, 0,
        1, 0, 0, 1,

        1, 0, 0, 0,
        1, 0, 0, 0,
        1, 0, 0, 1,

        1, 0, 0, 0,
        1, 0, 0, 0,
        1, 0, 0, 1,

        1, 1, 0, 0,
        1, 1, 0, 0,
        1, 1, 0, 0,
        1, 1, 0, 0,
    };

    geometry.setCoordinateIndices(0, indices);
    geometry.setColors(0, colors4);
    geometry.setColorIndices(0, colorIndices);
    }

    public void setSize(float width, float height) {
    // REMINDER -- think about optimization later
    float w = width / 2.0f;
    float h = height / 2.0f;
    float bn = north;
    float be = east;
    float bs = south;
    float bw = west;
    float bi = inner;
    float x = xShift;
    float y = yShift;
    float z = zShift;

    float[] coords = {
        -w + x, -h + y,  z,
         w + x, -h + y,  z,
         w + x,  h + y,  z,
        -w + x,  h + y,  z,

        -w -bw * sin90 + x, -h -bs * sin00 + y,  z,
        -w -bw * sin68 + x, -h -bs * sin23 + y,  z,
        -w -bw * sin45 + x, -h -bs * sin45 + y,  z,
        -w -bw * sin23 + x, -h -bs * sin68 + y,  z,
        -w -bw * sin00 + x, -h -bs * sin90 + y,  z,

         w +be * sin00 + x, -h -bs * sin90 + y,  z,
         w +be * sin23 + x, -h -bs * sin68 + y,  z,
         w +be * sin45 + x, -h -bs * sin45 + y,  z,
         w +be * sin68 + x, -h -bs * sin23 + y,  z,
         w +be * sin90 + x, -h -bs * sin00 + y,  z,

         w +be * sin90 + x, h +bn * sin00 + y,  z,
         w +be * sin68 + x, h +bn * sin23 + y,  z,
         w +be * sin45 + x, h +bn * sin45 + y,  z,
         w +be * sin23 + x, h +bn * sin68 + y,  z,
         w +be * sin00 + x, h +bn * sin90 + y,  z,

        -w -bw * sin00 + x, h +bn * sin90 + y,  z,
        -w -bw * sin23 + x, h +bn * sin68 + y,  z,
        -w -bw * sin45 + x, h +bn * sin45 + y,  z,
        -w -bw * sin68 + x, h +bn * sin23 + y,  z,
        -w -bw * sin90 + x, h +bn * sin00 + y,  z,

        -w +bi + x, -h +bi + y,  z,
         w -bi + x, -h +bi + y,  z,
         w -bi + x, h -bi + y,  z,
        -w +bi + x, h -bi + y,  z,
    };

    geometry.setCoordinates(0, coords);
    }
}

