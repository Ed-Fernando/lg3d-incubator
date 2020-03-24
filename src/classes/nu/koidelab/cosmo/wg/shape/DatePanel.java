/*
 * ��.��: 2005/08/17
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

public class DatePanel extends Component3D{
    //" 2005 Feb 12 Wed " 
    //"2005 / 02 / 12 (Wed)" 
    //" 13 : 25 " <- TimePanel"

    private NumberPanel year;
    private NumberPanel month;
    private NumberPanel date;
//    private TextFieldPanel dayOfWeek;
//    private NumberPanel hour;
//    private NumberPanel minute;
    
    Calendar cal = new GregorianCalendar();
    
    public DatePanel(long msec){
        cal.setTimeInMillis(msec);
        year = new NumberPanel();
        year.setColumn(4);
        year.setOffset(-0.0145f, 0, 0);
        year.setNumber(cal.get(Calendar.YEAR));
        
        float[] y2mOff = {- 0.003f, 0, 0};
        Slash y2m = new Slash(y2mOff);
        
        month = new NumberPanel();
        month.setColumn(2);
        month.setOffset(0.005f, 0, 0);
        month.setNumber(cal.get(Calendar.MONTH) + 1);
        
        float[] m2dOff = {0.012f, 0, 0};
        Slash m2d = new Slash(m2dOff);
        
        date = new NumberPanel();
        date.setColumn(2);
        date.setOffset(0.019f, 0, 0);
        date.setNumber(cal.get(Calendar.DAY_OF_MONTH));
        
        addChild(year);
        addChild(y2m);
        addChild(month);
        addChild(m2d);
        addChild(date);
        
        TimePanel tp = new TimePanel(msec);
        tp.setTranslation(0, -0.005f, 0);
        addChild(tp);
    }
    
    
    private static class Slash extends Shape3D{
        private static final Appearance DEFAULT_APPEARANCE;
        static {
            BufferedImage image = NumberPanel.createImage("/", new Color(1f, 1f, 1f));
            TextureLoader loader = new TextureLoader(image);
            DEFAULT_APPEARANCE = new SimpleAppearance(1f, 1f, 1f, 1.0f,
                    SimpleAppearance.ENABLE_TEXTURE
                            | SimpleAppearance.DISABLE_CULLING);
            DEFAULT_APPEARANCE.setTexture(loader.getTexture());
        }

        
        private Slash(){            
            float width = 0.0045861113f;
            float height = 0.005291667f;
            float[] offsets = {0,0,0};
            addGeometry(makeGeometries(width, height, offsets));
            setAppearance(DEFAULT_APPEARANCE);
        }
        
        private Slash(float[] offsets){
            float width = 0.0045861113f;
            float height = 0.005291667f;
            addGeometry(makeGeometries(width, height, offsets));
            setAppearance(DEFAULT_APPEARANCE);
        }
        
        private Geometry makeGeometries(float width, float height, float[] offsets){
            float w = width/2;
            float h = height/2;
            float[] coords = {
                    -w + offsets[0], h+offsets[1], 0+offsets[2],
                    -w + offsets[0], -h+offsets[1], 0+offsets[2],
                    w + offsets[0], -h+offsets[1], 0+offsets[2],
                    w + offsets[0], h+offsets[1], 0+offsets[2],
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
