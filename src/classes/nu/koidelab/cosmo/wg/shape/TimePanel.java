/*
 * ��.��: 2005/08/09
 */
package nu.koidelab.cosmo.wg.shape;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.sg.Geometry;
import org.jdesktop.lg3d.sg.GeometryArray;
import org.jdesktop.lg3d.sg.QuadArray;
import org.jdesktop.lg3d.sg.Shape3D;
import org.jdesktop.lg3d.sg.utils.image.TextureLoader;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.Component3D;

public class TimePanel extends Component3D {
    private NumberPanel hour;
    private NumberPanel minute;
    private Colon colon;
    
    public TimePanel(long msec){
        Calendar cal = new GregorianCalendar();
        cal.setTimeInMillis(msec);
        this.hour = new NumberPanel(cal.get(Calendar.HOUR_OF_DAY), -0.0045861113f*1.5f, 0, 0);
        this.minute = new NumberPanel(cal.get(Calendar.MINUTE), 0.0045861113f*1.5f, 0, 0);
        colon = new Colon();
        addChild(this.hour);
        addChild(this.minute);
        addChild(colon);
    }
    
    public TimePanel(int hour, int minute){
        this.hour = new NumberPanel(hour, -0.0045861113f*1.5f, 0, 0);
        this.minute = new NumberPanel(minute, 0.0045861113f*1.5f, 0, 0);
        if(minute == 0)
            this.minute.setNumber("00");
        colon = new Colon();
        addChild(this.hour);
        addChild(this.minute);
        addChild(colon);
    }
    
    public void set(long msec){
        Calendar cal = new GregorianCalendar();
        cal.setTimeInMillis(msec);
        hour.setNumber(cal.get(Calendar.HOUR_OF_DAY));
        int min = cal.get(Calendar.MINUTE);        
        if(min == 0)
            this.minute.setNumber("00");
        else
            this.minute.setNumber(min);
    }
    
    public void set(int hour, int minute){
        this.hour.setNumber(hour);
        if(minute == 0)
            this.minute.setNumber("00");
        else
            this.minute.setNumber(minute);
    }
    
    private static class Colon extends Shape3D{
        private static final Appearance DEFAULT_APPEARANCE;
        static {
            BufferedImage image = NumberPanel.createImage(":", new Color(1f, 1f, 1f));
            TextureLoader loader = new TextureLoader(image);
            DEFAULT_APPEARANCE = new SimpleAppearance(1f, 1f, 1f, 1.0f,
                    SimpleAppearance.ENABLE_TEXTURE
                            | SimpleAppearance.DISABLE_CULLING);
            DEFAULT_APPEARANCE.setTexture(loader.getTexture());
        }
        
        private Colon(){
            float width = 0.0045861113f;
            float height = 0.005291667f;
            addGeometry(makeGeometries(width, height));
            setAppearance(DEFAULT_APPEARANCE);
        }
        
        private Geometry makeGeometries(float width, float height){
            float w = width/2;
            float h = height/2;
            float[] coords = {
                    -w, h, 0,
                    -w, -h, 0,
                    w, -h, 0,
                    w, h, 0,
            };
            float[] normals = {
                    0, 0, 1,
                    0, 0, 1,
                    0, 0, 1,
                    0, 0, 1,
            };
            float[] texCoords = {
                    0, 0,
                    0, 1, 
                    1, 1,
                    1, 0,
            };
            GeometryArray geom = new QuadArray(4,
                    GeometryArray.COORDINATES |
                    GeometryArray.TEXTURE_COORDINATE_2 |
                    GeometryArray.NORMALS);
            geom.setCoordinates(0, coords);
            geom.setNormals(0, normals);
            geom.setTextureCoordinates(0, 0, texCoords);
            
            geom.setCapability(Geometry.ALLOW_INTERSECT);
            geom.setCapability(GeometryArray.ALLOW_COUNT_READ);
            geom.setCapability(GeometryArray.ALLOW_FORMAT_READ);
            geom.setCapability(GeometryArray.ALLOW_COORDINATE_READ);
            
            return geom;
        }
    }
}
