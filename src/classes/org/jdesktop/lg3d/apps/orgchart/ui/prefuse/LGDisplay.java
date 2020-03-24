package org.jdesktop.lg3d.apps.orgchart.ui.prefuse;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.text.JTextComponent;

import edu.berkeley.guir.prefuse.activity.Activity;
import edu.berkeley.guir.prefuse.activity.SlowInSlowOutPacer;
import edu.berkeley.guir.prefuse.event.ControlEventMulticaster;
import edu.berkeley.guir.prefuse.event.ControlListener;
import edu.berkeley.guir.prefuse.render.Renderer;
import edu.berkeley.guir.prefuse.util.ColorLib;
import edu.berkeley.guir.prefuse.util.FontLib;
import edu.berkeley.guir.prefuse.util.display.Clip;
import edu.berkeley.guir.prefuse.util.display.ExportDisplayAction;
import edu.berkeley.guir.prefuse.util.display.ToolTipManager;
import edu.berkeley.guir.prefuse.Display;
import edu.berkeley.guir.prefuse.*;
import org.jdesktop.lg3d.utils.action.ActionChar;
import org.jdesktop.lg3d.utils.action.ActionNoArg;
import org.jdesktop.lg3d.utils.cursor.WigglingCursor;
import org.jdesktop.lg3d.utils.eventadapter.KeyTypedEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.wg.Cursor3D;
import org.jdesktop.lg3d.wg.Tapp;
import org.jdesktop.lg3d.wg.event.LgEventConnector;
import org.jdesktop.lg3d.wg.event.LgEventSource;
import org.jdesktop.lg3d.wg.event.MouseEvent3D;


/**
 * <p>User interface component that provides an interactive visualization
 * of a graph. The Display is responsible for drawing items to the
 * screen and providing callbacks for user interface actions such as
 * mouse and keyboard events. A Display must be associated with an
 * {@link edu.berkeley.guir.prefuse.ItemRegistry ItemRegistry} from
 * which it pulls the items to visualize.</p>
 *
 * <p>The {@link edu.berkeley.guir.prefuse.event.ControlListener ControlListener}
 * interface provides the various available user interface callbacks. The
 * {@link edu.berkeley.guir.prefusex.controls} package contains a number
 * of pre-built <code>ControlListener</code> implementations for common
 * interactions.</p>
 *
 * <p>The Display class also supports arbitrary graphics transforms through
 * the <code>java.awt.geom.AffineTransform</code> class. The
 * {@link #setTransform(java.awt.geom.AffineTransform) setTransform} method
 * allows arbitrary transforms to be applied, while the
 * {@link #pan(double,double) pan} and
 * {@link #zoom(java.awt.geom.Point2D,double) zoom}
 * methods provide convenience methods that appropriately update the current
 * transform to achieve panning and zooming on the presentation space.</p>
 *
 * <p>Additionally, each Display instance also supports use of a text editor
 * to facilitate direct editing of text. See the various
 * {@link #editText(edu.berkeley.guir.prefuse.VisualItem, String) editItem}
 * methods.</p>
 *
 * @version 1.0
 * @author <a href="http://jheer.org">Jeffrey Heer</a> prefuse(AT)jheer.org
 * @see ItemRegistry
 * @see edu.berkeley.guir.prefuse.event.ControlListener
 * @see edu.berkeley.guir.prefusex.controls
 */
public class LGDisplay extends Display {

    protected ControlListener m_listener;
    protected boolean         m_repaint = false;
    protected boolean         m_highQuality = false;

    // transform variables
    protected AffineTransform   m_transform  = new AffineTransform();
    protected AffineTransform   m_itransform = new AffineTransform();
    protected TransformActivity m_transact = new TransformActivity();
    protected Point2D m_tmpPoint = new Point2D.Double();

    // frame count and debugging output
    protected double frameRate;
    protected int  nframes = 0;
    private int  sampleInterval = 10;
    private long mark = -1L;

    // text editing variables
    private VisualItem     m_editItem;
    private String         m_editAttribute;

    private ToolTipManager m_ttipManager;

    /**
     * Constructor. Creates a new display instance. You will need to
     * associate this Display with an ItemRegistry for it to display
     * anything.
     */
    public LGDisplay() {
        this(null);
    } //

    /**
     * Creates a new display instance associated with the given
     * ItemRegistry.
     * @param registry the ItemRegistry from which this Display
     *  should get the items to visualize.
     */
    public LGDisplay(ItemRegistry registry) {
        super(registry);

        // register input event capturer
        InputEventCapturer iec = new InputEventCapturer();
        addMouseListener(iec);
        addMouseMotionListener(iec);
        addMouseWheelListener(iec);
        addKeyListener(iec);

        // add debugging output control
        registerKeyboardAction(
                new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                m_showDebug = !m_showDebug;
            }
        },
                "debug info", KeyStroke.getKeyStroke("ctrl D"), WHEN_FOCUSED);
        // add image output control, if this is not an applet
        try {
            registerKeyboardAction(
                    new ExportDisplayAction(this),
                    "export display", KeyStroke.getKeyStroke("ctrl E"), WHEN_FOCUSED);
        } catch ( SecurityException se )  {}

    } //

    public void setUseCustomTooltips(boolean s) {
        if ( s && m_ttipManager == null ) {
            m_ttipManager = new ToolTipManager(this);
            String text = super.getToolTipText();
            super.setToolTipText(null);
            m_ttipManager.setToolTipText(text);
            this.addMouseMotionListener(m_ttipManager);
        } else if ( !s && m_ttipManager != null ) {
            this.removeMouseMotionListener(m_ttipManager);
            String text = m_ttipManager.getToolTipText();
            m_ttipManager.setToolTipText(null);
            super.setToolTipText(text);
            m_ttipManager = null;
        }
    } //

    public ToolTipManager getToolTipManager() {
        return m_ttipManager;
    } //

    public void setToolTipText(String text) {
        if ( m_ttipManager != null ) {
            m_ttipManager.setToolTipText(text);
        } else {
            super.setToolTipText(text);
        }
    } //

    /**
     * Set the size of the Display.
     * @see java.awt.Component#setSize(int, int)
     */
    public void setSize(int width, int height) {
        m_offscreen = null;
        setPreferredSize(new Dimension(width,height));
        super.setSize(width, height);
    } //

    /**
     * Set the size of the Display.
     * @see java.awt.Component#setSize(java.awt.Dimension)
     */
    public void setSize(Dimension d) {
        m_offscreen = null;
        setPreferredSize(d);
        super.setSize(d);
    } //

    /**
     * Reshapes (moves and resizes) this component.
     */
    public void reshape(int x, int y, int w, int h) {
        m_offscreen = null;
        super.reshape(x,y,w,h);
    } //

    /**
     * Returns the x-coordinate of the top-left of the display,
     * in absolute co-ordinates
     * @return the x co-ord of the top-left corner, in absolute coordinates
     */
    public double getDisplayX() {
        return -m_transform.getTranslateX();
    } //

    /**
     * Returns the y-coordinate of the top-left of the display,
     * in absolute co-ordinates
     * @return the y co-ord of the top-left corner, in absolute coordinates
     */
    public double getDisplayY() {
        return -m_transform.getTranslateY();
    } //

    /**
     * Pans the view provided by this display in screen coordinates.
     * @param dx the amount to pan along the x-dimension, in pixel units
     * @param dy the amount to pan along the y-dimension, in pixel units
     */
    public void pan(double dx, double dy) {
        double panx = ((double)dx) / m_transform.getScaleX();
        double pany = ((double)dy) / m_transform.getScaleY();
        panAbs(panx,pany);
    } //

    /**
     * Pans the view provided by this display in absolute (i.e. non-screen)
     * coordinates.
     * @param dx the amount to pan along the x-dimension, in absolute co-ords
     * @param dy the amount to pan along the y-dimension, in absolute co-ords
     */
    public void panAbs(double dx, double dy) {
        m_transform.translate(dx, dy);
        try {
            m_itransform = m_transform.createInverse();
        } catch ( Exception e ) { /*will never happen here*/ }
    } //

    /**
     * Pans the display view to center on the provided point in
     * screen (pixel) coordinates.
     * @param x the x-point to center on, in screen co-ords
     * @param y the y-point to center on, in screen co-ords
     */
    public void panTo(Point2D p) {
        m_itransform.transform(p, m_tmpPoint);
        panToAbs(m_tmpPoint);
    } //

    /**
     * Pans the display view to center on the provided point in
     * absolute (i.e. non-screen) coordinates.
     * @param x the x-point to center on, in absolute co-ords
     * @param y the y-point to center on, in absolute co-ords
     */
    public void panToAbs(Point2D p) {
        double x = p.getX(); x = (Double.isNaN(x) ? 0 : x);
        double y = p.getY(); y = (Double.isNaN(y) ? 0 : y);
        double w = getWidth() /(2*m_transform.getScaleX());
        double h = getHeight()/(2*m_transform.getScaleY());

        double dx = w-x-m_transform.getTranslateX();
        double dy = h-y-m_transform.getTranslateY();
        m_transform.translate(dx, dy);
        try {
            m_itransform = m_transform.createInverse();
        } catch ( Exception e ) { /*will never happen here*/ }
    } //

    /**
     * Zooms the view provided by this display by the given scale,
     * anchoring the zoom at the specified point in screen coordinates.
     * @param p the anchor point for the zoom, in screen coordinates
     * @param scale the amount to zoom by
     */
    public void zoom(final Point2D p, double scale) {
        m_itransform.transform(p, m_tmpPoint);
        zoomAbs(m_tmpPoint, scale);
    } //

    /**
     * Zooms the view provided by this display by the given scale,
     * anchoring the zoom at the specified point in absolute coordinates.
     * @param p the anchor point for the zoom, in absolute
     *  (i.e. non-screen) co-ordinates
     * @param scale the amount to zoom by
     */
    public void zoomAbs(final Point2D p, double scale) {;
    double zx = p.getX(), zy = p.getY();
    m_transform.translate(zx, zy);
    m_transform.scale(scale,scale);
    m_transform.translate(-zx, -zy);
    try {
        m_itransform = m_transform.createInverse();
    } catch ( Exception e ) { /*will never happen here*/ }
    } //

    public void animatePan(double dx, double dy, long duration) {
        double panx = dx / m_transform.getScaleX();
        double pany = dy / m_transform.getScaleY();
        animatePanAbs(panx,pany,duration);
    } //

    public void animatePanAbs(double dx, double dy, long duration) {
        m_transact.pan(dx,dy,duration);
    } //

    public void animatePanTo(Point2D p, long duration) {
        Point2D pp = new Point2D.Double();
        m_itransform.transform(p,pp);
        animatePanToAbs(pp,duration);
    } //

    public void animatePanToAbs(Point2D p, long duration) {
        m_tmpPoint.setLocation(0,0);
        m_itransform.transform(m_tmpPoint,m_tmpPoint);
        double x = p.getX(); x = (Double.isNaN(x) ? 0 : x);
        double y = p.getY(); y = (Double.isNaN(y) ? 0 : y);
        double w = ((double)getWidth()) /(2*m_transform.getScaleX());
        double h = ((double)getHeight())/(2*m_transform.getScaleY());
        double dx = w-x+m_tmpPoint.getX();
        double dy = h-y+m_tmpPoint.getY();
        animatePanAbs(dx,dy,duration);
    } //

    public void animateZoom(final Point2D p, double scale, long duration) {
        Point2D pp = new Point2D.Double();
        m_itransform.transform(p,pp);
        animateZoomAbs(pp,scale,duration);
    } //

    public void animateZoomAbs(final Point2D p, double scale, long duration) {
        m_transact.zoom(p,scale,duration);
    } //

    public void animatePanAndZoomTo(final Point2D p, double scale, long duration) {
        Point2D pp = new Point2D.Double();
        m_itransform.transform(p,pp);
        animatePanAndZoomToAbs(pp,scale,duration);
    } //

    public void animatePanAndZoomToAbs(final Point2D p, double scale, long duration) {
        m_transact.panAndZoom(p,scale,duration);
    } //

    public boolean isTranformInProgress() {
        return m_transact.isRunning();
    } //

    /**
     * TODO: clean this up to be more general...
     * TODO: change mechanism so that multiple transform
     *        activities can be running at once?
     */
    private class TransformActivity extends Activity {
        private double[] src, dst;
        private AffineTransform m_at;
        public TransformActivity() {
            super(2000,20,0);
            src = new double[6];
            dst = new double[6];
            m_at = new AffineTransform();
            setPacingFunction(new SlowInSlowOutPacer());
        } //
        private AffineTransform getTransform() {
            if ( this.isScheduled() )
                m_at.setTransform(dst[0],dst[1],dst[2],dst[3],dst[4],dst[5]);
            else
                m_at.setTransform(m_transform);
            return m_at;
        } //
        public void panAndZoom(final Point2D p, double scale, long duration) {
            AffineTransform at = getTransform();
            this.cancel();
            setDuration(duration);

            m_tmpPoint.setLocation(0,0);
            m_itransform.transform(m_tmpPoint,m_tmpPoint);
            double x = p.getX(); x = (Double.isNaN(x) ? 0 : x);
            double y = p.getY(); y = (Double.isNaN(y) ? 0 : y);
            double w = ((double)getWidth()) /(2*m_transform.getScaleX());
            double h = ((double)getHeight())/(2*m_transform.getScaleY());
            double dx = w-x+m_tmpPoint.getX();
            double dy = h-y+m_tmpPoint.getY();
            at.translate(dx,dy);

            at.translate(p.getX(), p.getY());
            at.scale(scale,scale);
            at.translate(-p.getX(), -p.getY());

            at.getMatrix(dst);
            m_transform.getMatrix(src);
            this.runNow();
        }
        public void pan(double dx, double dy, long duration) {
            AffineTransform at = getTransform();
            this.cancel();
            setDuration(duration);
            at.translate(dx,dy);
            at.getMatrix(dst);
            m_transform.getMatrix(src);
            this.runNow();
        } //
        public void zoom(final Point2D p, double scale, long duration) {
            AffineTransform at = getTransform();
            this.cancel();
            setDuration(duration);
            double zx = p.getX(), zy = p.getY();
            at.translate(zx, zy);
            at.scale(scale,scale);
            at.translate(-zx, -zy);
            at.getMatrix(dst);
            m_transform.getMatrix(src);
            this.runNow();
        } //
        protected void run(long elapsedTime) {
            double f = getPace(elapsedTime);
            m_transform.setTransform(
                    src[0] + f*(dst[0]-src[0]),
                    src[1] + f*(dst[1]-src[1]),
                    src[2] + f*(dst[2]-src[2]),
                    src[3] + f*(dst[3]-src[3]),
                    src[4] + f*(dst[4]-src[4]),
                    src[5] + f*(dst[5]-src[5])
                    );
            try {
                m_itransform = m_transform.createInverse();
            } catch ( Exception e ) { /* won't happen */ }
            repaint();
        } //
    } // end of inner class TransformActivity

    // ========================================================================
    // == RENDERING METHODS ===================================================

    /**
     * Returns the offscreen buffer used by this component for
     *  double-buffering.
     * @return the offscreen buffer
     */
    public BufferedImage getOffscreenBuffer() {
        return m_offscreen;
    } //

    /**
     * Creates a new buffered image to use as an offscreen buffer.
     */
    protected BufferedImage getNewOffscreenBuffer() {
        return (BufferedImage)createImage(getSize().width, getSize().height);
    } //

    /**
     * Saves a copy of this display as an image to the specified output stream.
     * @param output the output stream to write to.
     * @param format the image format (e.g., "JPG", "PNG").
     * @param scale how much to scale the image by.
     * @return true if image was successfully saved, false if an error occurred.
     */
    public boolean saveImage(OutputStream output, String format, double scale) {
        try {
            Dimension d = new Dimension((int)(scale*getWidth()),(int)(scale*getHeight()));
            BufferedImage img = (BufferedImage) createImage(d.width, d.height);
            Graphics2D g = (Graphics2D)img.getGraphics();
            Point2D p = new Point2D.Double(0,0);
            zoom(p, scale);
            boolean q = isHighQuality();
            setHighQuality(true);
            paintDisplay(g,d);
            setHighQuality(q);
            zoom(p, 1/scale);
            ImageIO.write(img,format,output);
            return true;
        } catch ( Exception e ) {
            e.printStackTrace();
            return false;
        }
    } //

    /**
     * Updates this display
     */
    public void update(Graphics g) {
        paint(g);
    } //

    public void repaint() {
        if ( !m_repaint ) {
            m_repaint = true;
            super.repaint();
        }
    } //

    /**
     * Paints the offscreen buffer to the provided graphics context.
     * @param g the Graphics context to paint to
     */
    protected void paintBufferToScreen(Graphics g) {
        int x = 0, y = 0;
        BufferedImage img = m_offscreen;
        //if ( m_clip != null ) {
        //    x = m_clip.getX();
        //    y = m_clip.getY();
        //    img = m_offscreen.getSubimage(x,y,m_clip.getWidth(),m_clip.getHeight());
        //}
        g.drawImage(img, x, y, null);
    } //

    /**
     * Immediately repaints the contents of the offscreen buffer
     * to the screen. This bypasses the usual rendering loop.
     */
    public void repaintImmediate() {
        Graphics g = this.getGraphics();
        if (g != null && m_offscreen != null) {
            paintBufferToScreen(g);
        }
    } //

    /**
     * Sets the transform of the provided Graphics context to be the
     * transform of this Display and sets the desired rendering hints.
     * @param g the Graphics context to prepare.
     */
    protected void prepareGraphics(Graphics2D g) {
        if ( m_transform != null )
            g.transform(m_transform);
        setRenderingHints(g);
    } //

    /**
     * Sets the rendering hints that should be used while drawing
     * the visualization to the screen. Subclasses can override
     * this method to set hints as desired.
     * @param g the Graphics context on which to set the rendering hints
     */
    protected void setRenderingHints(Graphics2D g) {
        if ( m_highQuality ) {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
        } else {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_OFF);
        }
        g.setRenderingHint(
                RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC);
    } //

    /**
     * Paint routine called <i>before</i> items are drawn. Subclasses should
     * override this method to perform custom drawing.
     * @param g the Graphics context to draw into
     */
    protected void prePaint(Graphics2D g) {
    } //

    /**
     * Paint routine called <i>after</i> items are drawn. Subclasses should
     * override this method to perform custom drawing.
     * @param g the Graphics context to draw into
     */
    protected void postPaint(Graphics2D g) {
    } //

    /**
     * Draws the visualization to the screen. Draws each visible item to the
     * screen in a rendering loop. Rendering order can be controlled by adding
     * the desired Comparator to the Display's ItemRegistry.
     */
    public void paintComponent(Graphics g) {
        paintDisplay(null, null);
    } //

    public void paintDisplay(Graphics2D g2D, Dimension d) {
        synchronized (m_registry) {
            Iterator items = m_registry.getItems();
            while (items.hasNext()) {
                try {
                    VisualItem vi = (VisualItem) items.next();
                    Renderer renderer = vi.getRenderer();
                    renderer.render(g2D, vi);
                } catch ( Exception e ) {
                    e.printStackTrace();
                }
            }
        }
/*
        // paint background
        g2D.setColor(getBackground());
        g2D.fillRect(0, 0, d.width, d.height);

        // show debugging info?
        if ( m_showDebug ) {
            g2D.setFont(getFont());
            g2D.setColor(getForeground());
            g2D.drawString(getDebugString(), 5, 15);
        }

        prepareGraphics(g2D);
        prePaint(g2D);

        postPaint(g2D);
 */
    } //

    /**
     * Paints the graph to the provided graphics context, for output to a
     * printer.  This method does not double buffer the painting, in order to
     * provide the maximum quality.
     *
     * @param g the printer graphics context.
     */
    protected void printComponent(Graphics g) {
        boolean wasHighQuality = m_highQuality;
        try {
            // Set the quality to high for the duration of the printing.
            m_highQuality = true;
            // Paint directly to the print graphics context.
            paintDisplay((Graphics2D) g, getSize());
        } finally {
            // Reset the quality to the state it was in before printing.
            m_highQuality = wasHighQuality;
        }
    } //

    /**
     * Clears the specified region of the display (in screen co-ordinates)
     * in the display's offscreen buffer. The cleared region is replaced
     * with the background color. Call the repaintImmediate() method to
     * have this change directly propagate to the screen.
     * @param r a Rectangle specifying the region to clear, in screen co-ords
     */
    public void clearRegion(Rectangle r) {
    } //

    /**
     * Draws a single item to the <i>offscreen</i> display
     * buffer. Useful for incremental drawing. Call the repaintImmediate()
     * method to have these changes directly propagate to the screen.
     * @param item
     */
    public void drawItem(VisualItem item) {
        item.getRenderer().render(null, item);
    } //

// ========================================================================
// == CONTROL LISTENER METHODS ============================================

    /**
     * Returns the VisualItem located at the given point.
     * @param p the Point at which to look
     * @return the VisualItem located at the given point, if any
     */
    public VisualItem findItem(Point p) {
        Point2D p2 = (m_itransform==null ? p :
            m_itransform.transform(p, m_tmpPoint));
        synchronized (m_registry) {
            Iterator items = m_registry.getItemsReversed();
            while (items.hasNext()) {
                VisualItem vi = (VisualItem) items.next();
                Renderer r = vi.getRenderer();
                if (r != null && vi.isInteractive()
                && r.locatePoint(p2, vi)) {
                    return vi;
                }
            }
        }
        return null;
    } //

    /**
     * Captures all mouse and key events on the display, detects relevant
     * VisualItems, and informs ControlListeners.
     */
    public class InputEventCapturer
            implements MouseMotionListener, MouseWheelListener, MouseListener, KeyListener {

        private VisualItem activeVI = null;
        private boolean mouseDown = false;
        private boolean itemDrag = false;

        public void mouseDragged(MouseEvent e) {
            if (m_listener != null && activeVI != null) {
                m_listener.itemDragged(activeVI, e);
            } else if ( m_listener != null ) {
                m_listener.mouseDragged(e);
            }
        } //

        public void mouseMoved(MouseEvent e) {
            boolean earlyReturn = false;
            //check if we've gone over any item
            VisualItem vi = findItem(e.getPoint());
            if (m_listener != null && activeVI != null && activeVI != vi) {
                m_listener.itemExited(activeVI, e);
                earlyReturn = true;
            }
            if (m_listener != null && vi != null && vi != activeVI) {
                m_listener.itemEntered(vi, e);
                earlyReturn = true;
            }
            activeVI = vi;
            if ( earlyReturn ) return;

            if ( m_listener != null && vi != null && vi == activeVI ) {
                m_listener.itemMoved(vi, e);
            }
            if ( m_listener != null && vi == null ) {
                m_listener.mouseMoved(e);
            }
        } //

        public void mouseWheelMoved(MouseWheelEvent e) {
            if (m_listener != null && activeVI != null) {
                m_listener.itemWheelMoved(activeVI, e);
            } else if ( m_listener != null ) {
                m_listener.mouseWheelMoved(e);
            }
        } //

        public void mouseClicked(MouseEvent e) {
            if (m_listener != null && activeVI != null) {
                m_listener.itemClicked(activeVI, e);
            } else if ( m_listener != null ) {
                m_listener.mouseClicked(e);
            }
        } //

        public void mousePressed(MouseEvent e) {
            mouseDown = true;
            if (m_listener != null && activeVI != null) {
                m_listener.itemPressed(activeVI, e);
            } else if ( m_listener != null ) {
                m_listener.mousePressed(e);
            }
        } //

        public void mouseReleased(MouseEvent e) {
            if (m_listener != null && activeVI != null) {
                m_listener.itemReleased(activeVI, e);
            } else if ( m_listener != null ) {
                m_listener.mouseReleased(e);
            }
            if ( m_listener != null && activeVI != null
                    && mouseDown && isOffComponent(e) ) {
                // mouse was dragged off of the component,
                // then released, so register an exit
                m_listener.itemExited(activeVI, e);
                activeVI = null;
            }
            mouseDown = false;
        } //

        public void mouseEntered(MouseEvent e) {
            if ( m_listener != null ) {
                m_listener.mouseEntered(e);
            }
        } //

        public void mouseExited(MouseEvent e) {
            if (m_listener != null && !mouseDown && activeVI != null) {
                // we've left the component and an item
                // is active but not being dragged, deactivate it
                m_listener.itemExited(activeVI, e);
                activeVI = null;
            }
            if ( m_listener != null ) {
                m_listener.mouseExited(e);
            }
        } //

        public void keyPressed(KeyEvent e) {
            if (m_listener != null && activeVI != null) {
                m_listener.itemKeyPressed(activeVI, e);
            } else if ( m_listener != null ) {
                m_listener.keyPressed(e);
            }
        } //

        public void keyReleased(KeyEvent e) {
            if (m_listener != null && activeVI != null) {
                m_listener.itemKeyReleased(activeVI, e);
            } else if ( m_listener != null ) {
                m_listener.keyReleased(e);
            }
        } //

        public void keyTyped(KeyEvent e) {
            if (m_listener != null && activeVI != null) {
                m_listener.itemKeyTyped(activeVI, e);
            } else if ( m_listener != null ) {
                m_listener.keyTyped(e);
            }
        } //

        private boolean isOffComponent(MouseEvent e) {
            int x = e.getX(), y = e.getY();
            return ( x<0 || x>getWidth() || y<0 || y>getHeight() );
        } //
    } // end of inner class MouseEventCapturer


// ========================================================================
// == TEXT EDITING CONTROL ================================================

    /**
     * Edit text for the given VisualItem and attribute. Presents a text
     * editing widget spaning the item's bounding box. Use stopEditing()
     * to hide the text widget. When stopEditing() is called, the attribute
     * will automatically be updated with the VisualItem.
     * @param item the VisualItem to edit
     * @param attribute the attribute to edit
     */
    public void editText(VisualItem item, String attribute) {
    } //

    /**
     * Edit text for the given VisualItem and attribute. Presents a text
     * editing widget spaning the given bounding box. Use stopEditing()
     * to hide the text widget. When stopEditing() is called, the attribute
     * will automatically be updated with the VisualItem.
     * @param item the VisualItem to edit
     * @param attribute the attribute to edit
     * @param r Rectangle representing the desired bounding box of the text
     *  editing widget
     */
    public void editText(VisualItem item, String attribute, Rectangle r) {
    } //

    /**
     * Show a text editing widget containing the given text and spanning the
     * specified bounding box. Use stopEditing() to hide the text widget. Use
     * the method calls getTextEditor().getText() to get the resulting edited
     * text.
     * @param txt the text string to display in the text widget
     * @param r Rectangle representing the desired bounding box of the text
     *  editing widget
     */
    public void editText(String txt, Rectangle r) {
    } //

    /**
     * Stops text editing on the display, hiding the text editing widget. If
     * the text editor was associated with a specific VisualItem (ie one of the
     * editText() methods which include a VisualItem as an argument was called),
     * the item is updated with the edited text.
     */
    public void stopEditing() {
    } //

} // end of class Display
