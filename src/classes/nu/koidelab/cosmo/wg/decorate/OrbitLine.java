/*
 * ºîÀ®Æü: 2005/02/03
 */
package nu.koidelab.cosmo.wg.decorate;

import javax.media.j3d.GeometryArray;

import nu.koidelab.cosmo.util.function.OrbitFunction;

import org.jdesktop.lg3d.sg.LineArray;
import org.jdesktop.lg3d.sg.Shape3D;
import org.jdesktop.lg3d.wg.Component3D;

/**
 * @author fumi OrbitLine org.wg
 */
public class OrbitLine extends Component3D {
    private Shape3D shape; 
    private LineArray geom;
    
    public OrbitLine(){
        Shape3D s = new Shape3D();
        // LineArray first argument mean vertex counts
        geom = new LineArray(4002, LineArray.COORDINATES);
        geom.setCapability(GeometryArray.ALLOW_COORDINATE_WRITE);
        s.setGeometry(geom);
        addChild(s);
    }
    
    public void setGeometry(long st, long ed, OrbitFunction func){
//      a-b c-d e-f g-h i-j => nodeNum 4, loop 5, arraySize 10  
//      a-b c-d e-f  => nodeNum 2, loop 3, arraySize 6  
        int nodeNum = 2000;
        float[] coordinates = new float[(nodeNum * 2 + 2)*3];//18
        long space = (ed - st) / (nodeNum+1);
        
        /* which way is better, Vector3f or float array. 
          This method need light weight implementation. */
        
        //        Vector3f vec = new Vector3f();
        float[] vec;
        for(int i = 0; i < coordinates.length/6; i++){
            vec = func.getPosition((st + space*i));//012
            coordinates[6*i] = vec[0];
            coordinates[6*i + 1] = vec[1];
            coordinates[6*i + 2] = vec[2];
            vec = func.getPosition((st + space*(i+1)));//123
            coordinates[6*i + 3] = vec[0];
            coordinates[6*i + 4] = vec[1];
            coordinates[6*i + 5] = vec[2];
        }
        geom.setCoordinates(0, coordinates);        
    }           
}