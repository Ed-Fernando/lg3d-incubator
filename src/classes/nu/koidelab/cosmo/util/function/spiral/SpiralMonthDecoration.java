/*
 * ºîÀ®Æü: 2005/08/18
 */
package nu.koidelab.cosmo.util.function.spiral;

import nu.koidelab.cosmo.manager.CSDGeneralManager;
import nu.koidelab.cosmo.util.function.OrbitFunction;
import nu.koidelab.cosmo.util.function.decoration.OrbitDecoration;
import nu.koidelab.cosmo.wg.decorate.LightDecoration;

import org.jdesktop.lg3d.sg.Geometry;
import org.jdesktop.lg3d.sg.GeometryArray;
import org.jdesktop.lg3d.sg.Shape3D;
import org.jdesktop.lg3d.sg.TriangleStripArray;
import org.jdesktop.lg3d.wg.Component3D;

public class SpiralMonthDecoration extends OrbitDecoration.DecorationParts{            
    private long st;
    private long ed;
    private float[] monthColor;
    private WideLineGeometry mainLine;

    SpiralMonthDecoration(long st, long ed, float[] color){
        this.st = st;
        this.ed = ed;
        this.monthColor = color;
        
        Component3D mainLineComp = new Component3D();
        Shape3D mainLineShape = new Shape3D();
        mainLineShape.setAppearance(LightDecoration.app);
        mainLine = new WideLineGeometry();
        mainLine.setGeometry(st, ed, CSDGeneralManager.getInstance().getFunction());
        mainLineShape.addGeometry(mainLine.getGeometry());
        mainLineComp.addChild(mainLineShape);
        addChild(mainLineComp);
    }
    
    void updateMainLine(Spiral s){
        mainLine.setGeometry(st, ed, s);
    }
    
    public static class WideLineGeometry{
        private static final float LINE_WIDTH = 0.003f;
        private static final int lineNodeNum = 2000;
        private static final int vertexCounts = (lineNodeNum + 2)*2;
        private TriangleStripArray geom;
        
        public WideLineGeometry(){}
        
        public Geometry getGeometry(){
            return geom;
        }
        
        public void updateGeometry(long st, long ed, OrbitFunction func){
            if(geom == null)
                setGeometry(st, ed, func);
            
            long space = (ed - st) / (lineNodeNum + 1);
            float[] coordinates = new float[vertexCounts*3];
            float[] colors = new float[vertexCounts*3];
            float[] vec;
            
            for(int i = 0, loopNum = lineNodeNum + 2; i < loopNum; i++){
                vec = func.getPosition(st+(space*i));
                coordinates[6*i] = vec[0];
                coordinates[6*i + 1] = vec[1] - LINE_WIDTH;
                coordinates[6*i + 2] = vec[2];
                coordinates[6*i + 3] = vec[0];
                coordinates[6*i + 4] = vec[1] + LINE_WIDTH;
                coordinates[6*i + 5] = vec[2];
            }        
                        
            geom.setCoordinates(0, coordinates);
        }
        
        public void setGeometry(long st, long ed, OrbitFunction func){
            float colorR = 1;
            float colorG = 1;
            float colorB = 1;
            
            long space = (ed - st) / (lineNodeNum + 1);
            float[] coordinates = new float[vertexCounts*3];
            float[] colors = new float[vertexCounts*3];
            float[] texCoords = new float[vertexCounts*2];
            float[] vec;
            for(int i = 0, loopNum = lineNodeNum + 2; i < loopNum; i++){
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
            texCoords[vertexCounts*2 - 4] = 0;
            texCoords[vertexCounts*2 - 2] = 0;
            
            
            int[] stripVertexCounts = {vertexCounts};        
            geom = new TriangleStripArray(vertexCounts,
                    GeometryArray.COORDINATES |
                    GeometryArray.TEXTURE_COORDINATE_2 |
                    GeometryArray.COLOR_3, stripVertexCounts);
            geom.setCoordinates(0, coordinates);
            geom.setTextureCoordinates(0, 0, texCoords);
            geom.setColors(0, colors);
            geom.setCapability(GeometryArray.ALLOW_COUNT_READ);
            geom.setCapability(GeometryArray.ALLOW_FORMAT_READ);
            geom.setCapability(GeometryArray.ALLOW_COORDINATE_READ);
            geom.setCapability(GeometryArray.ALLOW_COORDINATE_WRITE);
            geom.setCapability(GeometryArray.ALLOW_COLOR_WRITE);
        }
    }
}
