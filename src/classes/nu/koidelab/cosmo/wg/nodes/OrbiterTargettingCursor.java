package nu.koidelab.cosmo.wg.nodes;

import java.awt.Color;
import java.awt.Font;

import nu.koidelab.cosmo.util.cursor.CursorManager;
import nu.koidelab.cosmo.util.schedule.Plan;
import nu.koidelab.cosmo.wg.shape.TextAreaPanel;
import nu.koidelab.cosmo.wg.shape.TextFieldPanel;

import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.sg.Geometry;
import org.jdesktop.lg3d.sg.GeometryArray;
import org.jdesktop.lg3d.sg.LineArray;
import org.jdesktop.lg3d.sg.LineAttributes;
import org.jdesktop.lg3d.sg.Shape3D;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.Component3D;

/** This class display plan's name and plan's time. */
public class OrbiterTargettingCursor extends CursorManager.Cursor {
	private static final float TARGETTING_START_SIZE = 0.018f;
	private static final float TARGETTING_FINISH_SIZE = 0.008f;
//	private static final float TARGETTING_START_SIZE = CosmoConfig.PLANET_RADIUS*3;
//	private static final float TARGETTING_FINISH_SIZE = CosmoConfig.PLANET_RADIUS*2;
    private GeometryArray geom;

    protected OrbiterTargettingCursor(EditableOrbiter target) {
        super(target);
        setPickable(false);
        setMouseEventEnabled(false);
        
        /* ========== Create plan's name panel ========== */
        TextFieldPanel name = new TextFieldPanel(target.getName(), true,
                false, new Font("MS PGothic", Font.PLAIN, 32), new Color(
                        0.7f, 1f, 0.7f, 1.0f), new Color(0.2f, 0.2f, 0.2f, 0.6f), 1.0f);
        Component3D namePanel = new Component3D();
        namePanel.setScale(0.7f);
        namePanel.addChild(name);
        namePanel.setTranslation(0.0f, -0.005f - (name.getHeight()/2 * namePanel.getFinalScale()), 0);
        addChild(namePanel);

        /* ========== Create plan's start time panel ========= */
        TextFieldPanel date = new TextFieldPanel(Plan
                .msecValueToString(target.plan.getTime()), false,
                false, new Font("MS PGothic", Font.PLAIN, 20), new Color(
                        0.7f, 1f, 0.7f, 1.0f), new Color(0.2f, 0.2f, 0.2f, 0.6f), 1.0f);
        Component3D datePanel = new Component3D();
        datePanel.setScale(0.75f);
        datePanel.addChild(date);
        datePanel.setTranslation(0.005f + (date.getWidth()/2 * datePanel.getFinalScale()), 0.0045f, 0);
        addChild(datePanel);
        
        /* === If this plan has detail data, display it. === */
        if (target.plan.getDetail() != "" || target.plan.getDetail() != null) {
            TextAreaPanel detail 
            = new TextAreaPanel(3, 20, target.plan.getDetail(), 
                    new Font("MS PGothic", Font.PLAIN, 18),
                    new Color(0.7f, 1f, 0.7f, 1.0f), new Color(0.2f, 0.2f, 0.2f, 0.6f));                
            Component3D detailPanel = new Component3D();
            detailPanel.setScale(0.8f);
            detailPanel.addChild(detail);
            detailPanel.setTranslation(0, -0.03f, 0);
            addChild(detailPanel);
        }

        /* Create cursor body. */
        Shape3D s = new Shape3D();
        s.addGeometry(makeGeometry(0.01f, 0.01f));
        Appearance app = new SimpleAppearance(1f, 1f, 1f, 0.8f);
        LineAttributes la = new LineAttributes(3f,
                LineAttributes.PATTERN_SOLID, false);
        app.setLineAttributes(la);
        s.setAppearance(app);
        Component3D cursor = new Component3D();
        cursor.addChild(s);
        addChild(cursor);
    }

    public void startAnimation() {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                for (float i = TARGETTING_START_SIZE; i >= TARGETTING_FINISH_SIZE; i -= 0.0025f) {
                    setCoords(i, i);
                    try {
                        Thread.sleep(45);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.run();
    }

    private void setCoords(float width, float height) {
        float w = width / 2;
        float h = height / 2;
        float[] coords = { 
                -w, h, 0, -w, -h, 0,
                -w, -h, 0, w, -h, 0,
                w, -h, 0, w, h, 0,
                w, h, 0, -w, h, 0, 
        };
        geom.setCoordinates(0, coords);
    }

    private Geometry makeGeometry(float width, float height) {
        float r = 0.7f;
        float g = 1f;
        float b = 0.7f;
        float alpha = 0.3f;
        float[] colors = { r, g, b, alpha, r, g, b, alpha, r, g, b, alpha,
                r, g, b, alpha, r, g, b, alpha, r, g, b, alpha, r, g, b,
                alpha, r, g, b, alpha, };
        geom = new LineArray(8, GeometryArray.COORDINATES
                | GeometryArray.COLOR_4);
        geom.setColors(0, colors);
        geom.setCapability(GeometryArray.ALLOW_COORDINATE_WRITE);
        setCoords(width, height);

        return geom;
    }
}