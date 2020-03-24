/*
 * ContactListLayout.java
 *
 */

package org.jdesktop.lg3d.apps.orgchart.ui.contact;

import org.jdesktop.lg3d.utils.action.ActionBoolean;
import org.jdesktop.lg3d.utils.c3danimation.Component3DAnimationFactory;
import org.jdesktop.lg3d.utils.eventadapter.MouseEnteredEventAdapter;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.wg.Cursor3D;
import org.jdesktop.lg3d.wg.LayoutManager3D;
import org.jdesktop.lg3d.wg.Component3DAnimation;
import org.jdesktop.lg3d.wg.event.LgEventSource;
import java.util.ArrayList;
import java.util.HashMap;
import javax.vecmath.Vector3f;

/**
 *
 * @author cc144453
 */
public class ContactListLayout implements LayoutManager3D {
    
    private static final float DEFAULT_SPACING = 0.01f;    
    private static final float SIDE_DEPTH = -0.005f;
    private static final float BOUNDARY_TOP = 0.003f; // starting point to skip the top
    private static final float BOUNDARY_BOTTOM = 0.02f; // starting point to skip the task bar
    private static final float BOUNDARY_SIDE = ContactPanel.PANEL_DEPTH * 0.5f + 0.005f;
    private static final float ROTATION_CLOSED = (float)(60.0 / 180.0 * Math.PI); // 60 degrees
    private static final Vector3f ROTATION_AXIS = new Vector3f(0.0f,  1.0f, 0.0f);
    private static final float MAX_SCALE = 1.5f; // max scale when rotating to front
    
    private boolean fullScreenMode;
    private Container3D cont;
    private int size;
    private float spacing;
    private ArrayList<ContactPanel> compList = new ArrayList<ContactPanel>();
    // FIXME -- it is inefficient to have one more HashMap,
    // but works for now...
    private HashMap<ContactPanel, MouseEnteredEventAdapter> mouseEnteredMap = null;
    private Component3D compToSkip = null;
    private boolean alreadyHighlighting = false;
    private boolean originalFullScreenMode = false;
    private Vector3f tmpV3f = new Vector3f();
    
    public ContactListLayout(int size) {
        this(size, DEFAULT_SPACING);
    }
    
    public ContactListLayout(int size, float spacing) {
        this.size = size;
        this.spacing = spacing;
        this.fullScreenMode = true;
        mouseEnteredMap = new HashMap<ContactPanel, MouseEnteredEventAdapter>(size);
    }
    
    public void setContainer(Container3D cont) {
        this.cont = cont;
    }
    
    public void setFullScreen(boolean fullScreen) {
        if (fullScreenMode != fullScreen) {
            fullScreenMode = fullScreen;
            if (cont != null) {
                cont.revalidate(); // force rearrangement
            }
        }
    }
    
    public boolean isFullScreen() {
        return fullScreenMode;
    }
    
    public void layoutContainer() {
        // adjust size if number of items in container > preset size
        int contSize = compList.size();
        if (contSize > size) {
            size = contSize;
        }
        
        // get the container size
        cont.getPreferredSize(tmpV3f);
        float contWidth = tmpV3f.x;
        float contHeight = tmpV3f.y;
        float contDepth = tmpV3f.z;
        
        // layout base on mode
        if (fullScreenMode) {
            layoutFullScreen(contWidth, contHeight, contDepth,
                    ContactPanel.PANEL_WIDTH, ContactPanel.PANEL_HEIGHT);
        } else {
            layoutSide(contWidth, contHeight, contDepth,
                    ContactPanel.PANEL_WIDTH, ContactPanel.PANEL_HEIGHT);
        }
    }
    
    private void layoutFullScreen(float contWidth, float contHeight, float contDepth,
            float compWidth, float compHeight) {
        // compute number of rows
        int numRows = (int)Math.sqrt((double)size);
        int numPerRow = size / numRows;
        int numLastRow = size % numPerRow;
        if (numLastRow > 0) {
            ++numPerRow;
        } else {
            numLastRow = numPerRow;
        }
        
        // compute size and scaling
        contWidth -= 2.0f * BOUNDARY_SIDE; // take off space for borders
        contHeight -= (BOUNDARY_TOP + BOUNDARY_BOTTOM);
        float maxWidth = (compWidth * numPerRow + spacing * (numPerRow - 1));
        float maxHeight = (compHeight * numRows + spacing * (numRows - 1));
        float compScale = 1.0f;
        if (maxWidth > contWidth) {
            compScale = contWidth / maxWidth;
        }
        if (maxHeight > contHeight) {
            // take the smaller of the height vs width scale
            float heightScale = contHeight / maxHeight;
            if (heightScale < compScale) {
                compScale = heightScale;
            }
        }
        
        // compute base x, y positions
        float rowX = -0.5f * maxWidth * compScale;
        float lastX = -0.5f * (compWidth * numLastRow +  spacing * (numLastRow - 1)) * compScale;
        
        float x = rowX;
        float y = 0.5f * (compHeight + spacing) * (numRows - 1) * compScale;
        float xAdvance = (compWidth + spacing) * compScale;
        float yAdvance = (compHeight + spacing)  * compScale;
        
        // compute position for each panel
        int row = 1;
        int numCurrentRow = 0;
        for (ContactPanel comp : compList) {
            if (numCurrentRow >= numPerRow) {
                // advanced to the next row
                ++row;
                numCurrentRow = 0;
                // check if last row
                if (row == numRows) {
                    x = lastX;
                } else {
                    x = rowX;
                }
                y -= yAdvance;
            }
            comp.changeScale(compScale, Contact3D.LAYOUT_DURATION);
            comp.changeTranslation(x, y, contDepth, Contact3D.LAYOUT_DURATION);
            comp.changeRotationAngle(0.0f, Contact3D.LAYOUT_DURATION);
            comp.enableButtons(true);
            
            x += xAdvance;
            ++numCurrentRow;
        }
    }
    
    private void layoutSide(float contWidth, float contHeight, float contDepth,
            float compWidth, float compHeight) {
        // calculate any scaling to ensure every object is in the space
        float compScale = (contHeight - BOUNDARY_BOTTOM - BOUNDARY_TOP) /
                (size * (compHeight + spacing));
        float compSpacing = spacing * compScale;
        
        // compute x position based on left or right side of screen to place panel
        float x = contWidth * -0.5f + BOUNDARY_SIDE;
        float y = (contHeight - compHeight) * 0.5f - compSpacing - BOUNDARY_TOP * compScale;
        float z = SIDE_DEPTH;
        
        // compute y position for each panel
        for (ContactPanel comp : compList) {
            float halfCompHeight = compHeight * 0.5f * compScale;
            if (comp != compToSkip) {
                comp.changeScale(compScale, Contact3D.LAYOUT_DURATION);
                comp.changeTranslation(x, y + halfCompHeight, z, Contact3D.LAYOUT_DURATION);
                comp.changeRotationAxis(ROTATION_AXIS, Contact3D.LAYOUT_DURATION);
                comp.changeRotationAngle(ROTATION_CLOSED, Contact3D.LAYOUT_DURATION);
                comp.enableButtons(false);
            }
            y -= 2.0f * halfCompHeight + compSpacing;
        }
    }
    
    protected void setComponentToSkipLayout(Component3D compToSkip) {
        this.compToSkip = compToSkip;
    }
    
    protected Component3D getComponentToSkipLayout() {
        return compToSkip;
    }
    
    protected Container3D getTargetContainer() {
        return cont;
    }
    
    protected ArrayList<ContactPanel> getManagedComponents() {
        return compList;
    }
    
    public void addLayoutComponent(Component3D comp, Object constraints) {
        // ignore non-ContactPanels
        if (comp instanceof ContactPanel) {
            ContactPanel contactPanel = (ContactPanel)comp;
            contactPanel.setCursor(Cursor3D.SMALL_CURSOR);
            synchronized (compList) {
                if (constraints != null && constraints instanceof Integer) {
                    compList.add((Integer)constraints, contactPanel);
                } else {
                    compList.add(contactPanel);
                }
                // move to foreground on mouse enter
                MouseEnteredEventAdapter mouseEnteredAdapter =
                        new MouseEnteredEventAdapter(new ContactPanelMouseEnterActionBoolean(comp));
                mouseEnteredMap.put(contactPanel, mouseEnteredAdapter);
                contactPanel.addListener(mouseEnteredAdapter);
            }
        }
    }
    
    public void removeLayoutComponent(Component3D comp) {
        synchronized (compList) {
            // remove the mouse enter listener and the component
            MouseEnteredEventAdapter mouseEnteredAdapter =
                    mouseEnteredMap.get(comp);
            if (mouseEnteredAdapter != null) {
                comp.removeListener(mouseEnteredAdapter);
                compList.remove(comp);
            }
        }
    }
    
    public boolean rearrangeLayoutComponent(Component3D comp, Object constraints) {
        assert(compList.contains(comp));
        synchronized (compList) {
            ContactPanel contactPanel = (ContactPanel)comp;
            if (constraints != null && constraints instanceof Integer) {
                int idx = (Integer)constraints;
                if (compList.indexOf(contactPanel) == idx) {
                    return false;
                }
                compList.remove(contactPanel);
                compList.add(idx, contactPanel);
            } else {
                if (compList.indexOf(contactPanel) == compList.size() -1) {
                    return false;
                }
                compList.remove(contactPanel);
                compList.add(contactPanel);
            }
            return true;
        }
    }
    
    private class ContactPanelMouseEnterActionBoolean implements ActionBoolean {
        private Component3D target;
        private boolean originalFullScreen;
        private float baseScale;
        private Vector3f baseTranslation;
        private Vector3f baseSize;
        
        public ContactPanelMouseEnterActionBoolean(Component3D target) {
            this.target = target;
            this.baseTranslation = null;
            this.originalFullScreen = fullScreenMode;
        }
        
        public void performAction(LgEventSource source, boolean state) {
            Vector3f newTrans = null;
            
            // handle translation
            if (state) {
                if (baseTranslation == null) {
                    baseTranslation = new Vector3f();
                    baseSize = new Vector3f();
                }
                baseTranslation = target.getFinalTranslation(baseTranslation);
                if (fullScreenMode) {
                    newTrans = new Vector3f(baseTranslation.x,
                            baseTranslation.y,
                            baseTranslation.z + 0.01f);
                } else { // !fullScreenMode && state
                    baseTranslation = target.getFinalTranslation(baseTranslation);
                    newTrans = new Vector3f(baseTranslation.x,
                            baseTranslation.y,
                            baseTranslation.z);
                    
                }
            } else { // !state
                if (baseTranslation == null ||
                        (originalFullScreen != fullScreenMode)) {
                    // reset state and get out if display layout changed
                    this.originalFullScreen = fullScreenMode;
                    baseTranslation = null;
                    // get out of here if no baseTranslation
                    return;
                }
                newTrans = baseTranslation;
            }
            // handle scaling
            float newScale;
            if (state) {
                baseScale = target.getFinalScale();
                if (fullScreenMode) {
                    newScale = 1.0f / baseScale;
                    // limit scaling up to at most 1.2x
                    if ((newScale / baseScale) > 1.2f ) {
                        newScale = baseScale * 1.2f;
                    } else {
                        newScale = 1.0f;
                    }
                } else {
                    newScale = baseScale * MAX_SCALE; // set to scale no > max scale and 1.0f
                    if (newScale > 1.0f) {
                        newScale = 1.0f;
                    }
                }
            } else {
                newScale = baseScale;
            }
            // make sure we don't draw below or above the screen boundaries
            target.getPreferredSize(baseSize);
            cont.getPreferredSize(tmpV3f);
            float halfHeight = baseSize.y * 0.5f;
            float newWidth = baseSize.x / baseScale;
            float contWidth = tmpV3f.x * 0.5f;
            float contHeight = tmpV3f.y * 0.5f;
            float y = baseTranslation.y;
            if ((newTrans.x + newWidth) > contWidth) {
                newTrans.x = contWidth - newWidth;
            }
            if (y >= 0.0f && ((halfHeight + y) > contHeight)) {
                newTrans.y = contHeight - BOUNDARY_TOP - halfHeight * newScale;
            } else if (y < 0.0f &&
                    ((-halfHeight + baseTranslation.y) < -(contHeight - BOUNDARY_BOTTOM))) {
                newTrans.y = -contHeight + BOUNDARY_BOTTOM + halfHeight * newScale;
            }
            target.changeTranslation(newTrans, Contact3D.ANIMATION_DURATION);
            target.changeScale(newScale, Contact3D.ANIMATION_DURATION);
            
            // handle rotation
            if (!fullScreenMode) {
                float newRotationAngle;
                if (state) {
                    newRotationAngle = 0.0f;
                } else {
                    newScale = baseScale;
                    newRotationAngle = ROTATION_CLOSED;
                }
                target.changeRotationAngle(newRotationAngle, Contact3D.ANIMATION_DURATION);
                if (target instanceof ContactPanel) {
                    ((ContactPanel)target).enableButtons(state);
                }
            }
            
            // change the mode
            this.originalFullScreen = fullScreenMode;
            
            // cancel any highlighting mode if in full screen mode
            if (fullScreenMode) {
                alreadyHighlighting = false;
            }
        }
    }
    
    void highlight(ContactPanel contactPanel, boolean state) {
        if (!alreadyHighlighting) {
            if (state) {
                originalFullScreenMode = isFullScreen();
                if (isFullScreen()) {
                    setFullScreen(true);
                }
                alreadyHighlighting = true;
            } else {
                setFullScreen(originalFullScreenMode);
                alreadyHighlighting = false;
            }
        }
    }
}
