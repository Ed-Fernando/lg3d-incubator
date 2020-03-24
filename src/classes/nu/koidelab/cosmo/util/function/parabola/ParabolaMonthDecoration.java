package nu.koidelab.cosmo.util.function.parabola;

import static nu.koidelab.cosmo.manager.CosmoConfig.NAME_TAG_FONT;
import static nu.koidelab.cosmo.manager.CosmoConfig.STR_COLOR_4F;

import java.util.LinkedList;
import java.util.List;

import nu.koidelab.cosmo.manager.CSDGeneralManager;
import nu.koidelab.cosmo.util.function.OrbitFunction;
import nu.koidelab.cosmo.util.function.decoration.OrbitDecoration;
import nu.koidelab.cosmo.util.function.parabola.decoration.OrbitalLightShape;
import nu.koidelab.cosmo.wg.shape.TextFieldPanel;

import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.sg.GeometryArray;
import org.jdesktop.lg3d.sg.LineArray;
import org.jdesktop.lg3d.sg.LineAttributes;
import org.jdesktop.lg3d.sg.Shape3D;
import org.jdesktop.lg3d.sg.TransparencyAttributes;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Container3D;

class ParabolaMonthDecoration extends OrbitDecoration.DecorationParts {
    Component3D lightComp;
    Container3D linesCont;
    List<LineShape> lines = new LinkedList<LineShape>();

    private long st;
    private long ed;
    private float[] monthColor;
	private LineShape mainLineShape;
    private Container3D tagComp = new Container3D();/* The tag component of the categorie name */
	private OrbitalLightShape lightShape;           // for Month color, display only year-mode.

    ParabolaMonthDecoration(long st, long ed, float[] color) {
        this.st = st;
        this.ed = ed;
        this.monthColor = color;

        setPickable(false);
        
        /* ======= Setting of month color light decoration ========== */
        lightComp = new Component3D();
        lightShape = new OrbitalLightShape();
        lightShape.setAppearance(OrbitalLightShape.getLightAppearance());
        lightShape.setGeometry(st, ed, monthColor, CSDGeneralManager.getInstance()
                .getFunction());
        lightComp.addChild(lightShape);
        addChild(lightComp);

        /* ================= Setting of orbit lines ================== */
        Component3D mainLineComp = new Component3D();
        mainLineShape = new LineShape();       
        mainLineShape.setAppearance(getLineAppearance());
        mainLineShape.setGeometry(st, ed, CSDGeneralManager.getInstance()
                .getFunction(), 0f);
        mainLineComp.addChild(mainLineShape);
        addChild(mainLineComp);

        /* Settings of orbit line of categories */
        linesCont = new Container3D();
        addChild(linesCont);
        addChild(tagComp);
    }

    void makeNewLine(Parabola p, String categoryName) {
        Component3D c = new Component3D();        
        LineShape newLineShape = new LineShape(categoryName);
        newLineShape.setAppearance(getLineAppearance());
        newLineShape.setGeometry(st, ed, p, p.getZShift(newLineShape.name));
        lines.add(newLineShape);
        c.addChild(newLineShape);
        linesCont.addChild(c);
    }

    void updateLight(Parabola p) {
    	lightShape.setGeometry(st, ed, monthColor, p);
    }

    void updateMainLine(Parabola p) {
    	mainLineShape.setGeometry(st, ed, p, 0f);
    }

    void updateAllLines(Parabola p) {
        for (int i = lines.size() - 1; i >= 0; i--) {
            LineShape aLine = lines.get(i);
            aLine.setGeometry(st, ed, p, p.getZShift(aLine.name));
        }
    }
    
    private Appearance getLineAppearance(){
        /* ============== Line Attributes settings ============= */
        Appearance app = new Appearance();
        app.setCapability(Appearance.ALLOW_TRANSPARENCY_ATTRIBUTES_READ);
        app.setLineAttributes(new LineAttributes(1f, LineAttributes.PATTERN_SOLID, true));
        app.setTransparencyAttributes(new TransparencyAttributes(TransparencyAttributes.FASTEST, 0.1f));
        return app;
    }
    
    void setExtDecos(Parabola p, boolean visible) {
        long time = System.currentTimeMillis();
        if (st <= time && time < ed) {
        	tagComp.setVisible(visible);
            if (visible) {                
                /* If tagComp does not have any category tags, then do this. */
                if (tagComp.numChildren() <= 0) {
                    for (int i = p.categories.length - 1; i >= 0; i--) {
                        String categoryName = p.categories[i];
                        Component3D c = new Component3D();
                        TextFieldPanel sp = new TextFieldPanel(categoryName,
                                true, NAME_TAG_FONT, STR_COLOR_4F);
                        c.addChild(sp);
                        c.setTranslation(0.0f, sp.getHeight() / 2, p
                                .getZShift(categoryName));
                        c.setPickable(false);
                        tagComp.addChild(c);
                    }
                }
            }
        }
    }

    class LineShape extends Shape3D{
        private LineArray geom;
        String name;
        private static final int NUM_OF_VERTICES = 12000;
        private static final int NUM_OF_EDGES = NUM_OF_VERTICES - 1;

        private LineShape(){
        	this("main");
        }
        
        private LineShape(String categoryName) {
        	super();
            name = categoryName;
            geom = new LineArray(NUM_OF_EDGES*2, LineArray.COORDINATES
                    | LineArray.COLOR_3);
            geom.setCapability(GeometryArray.ALLOW_COORDINATE_WRITE);
            geom.setCapability(GeometryArray.ALLOW_COLOR_WRITE);
            setGeometry(geom);
        }

        private void setGeometry(long st, long ed, OrbitFunction func,
                float zShift) {
            int coordinatesLength = NUM_OF_EDGES * 6;
            float[] coordinates = new float[coordinatesLength];
            
            int colorLength = NUM_OF_EDGES * 6;
            float[] colors = new float[colorLength];// 18
            float colorR = 0.5f;
            float colorG = 0.5f;
            float colorB = 0.5f;
            
            long timeInterval = (ed - st) / NUM_OF_EDGES;

            float[] vec;
            for (int i = 0, loopNum = NUM_OF_EDGES; i < loopNum; i++) {
                vec = func.getPosition((st + timeInterval * i));
                coordinates[6 * i] = vec[0];
                coordinates[6 * i + 1] = vec[1];
                coordinates[6 * i + 2] = vec[2] + zShift;
                colors[6 * i] = colorR;
                colors[6 * i + 1] = colorG;
                colors[6 * i + 2] = colorB;

                vec = func.getPosition((st + timeInterval * (i + 1)));
                coordinates[6 * i + 3] = vec[0];
                coordinates[6 * i + 4] = vec[1];
                coordinates[6 * i + 5] = vec[2] + zShift;
                colors[6 * i + 3] = colorR;
                colors[6 * i + 4] = colorG;
                colors[6 * i + 5] = colorB;
            }
                        
            geom.setCoordinates(0, coordinates);
            geom.setColors(0, colors);
        }
    }    
}
