/*
 * ��.��: 2005/02/26
 */
package nu.koidelab.cosmo.wg;

import java.io.IOException;

import javax.vecmath.Color3f;

import nu.koidelab.cosmo.manager.CSDGeneralManager;
import nu.koidelab.cosmo.manager.CosmoConfig;
import nu.koidelab.cosmo.util.bind2d.swing.Dialog3D;
import nu.koidelab.cosmo.util.function.OrbitFunction;
import nu.koidelab.cosmo.util.function.calendar.CalendarFunction;
import nu.koidelab.cosmo.util.function.parabola.Parabola;
import nu.koidelab.cosmo.util.function.spiral.Spiral;
import nu.koidelab.cosmo.util.gesture.menu.MenuIcon;
import nu.koidelab.cosmo.util.gesture.menu.MenuList;
import nu.koidelab.cosmo.util.gesture.menu.AllowMenu;
import nu.koidelab.cosmo.wg.shape.OrientedPanel;

import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.sg.ColoringAttributes;
import org.jdesktop.lg3d.sg.Material;
import org.jdesktop.lg3d.sg.TextureAttributes;
import org.jdesktop.lg3d.sg.TransparencyAttributes;
import org.jdesktop.lg3d.utils.c3danimation.NaturalMotionAnimation;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.utils.shape.Sphere;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.wg.Cursor3D;
import org.jdesktop.lg3d.wg.event.MouseButtonEvent3D;

/**
 * @author fumi FixedStar org.wg
 */
public class FixedStar extends Container3D implements AllowMenu{
    private MenuList menu;
    
    public FixedStar() {
        setTranslation(0.0f, 0.1f, -5.0f);
        setCursor(Cursor3D.SMALL_CURSOR);
        setAnimation(new NaturalMotionAnimation(500));
        
        FixedStarBody sun = new FixedStarBody(0.05f); 
        /* make parts of the sun */
        addChild(sun);        
        
        /* set gesture menu  */
        menu = new MenuList();
//        menu.add(MenuIcon.NEW);
        menu.add(new MenuIcon("layout", "undo.png"));
        menu.add(new MenuIcon("about", "help.png"));
        sun.setMouseEventPropagatable(true);
        setMouseEventSource(MouseButtonEvent3D.class, true);
    }
    
    public MenuList getMenuList() {
    	return menu;
    }
    
    public void processMenu(MenuIcon menu) {
//    	if(menu.getName().equals("new")){
//            CSDGeneralManager.getInstance().getEditor().launchNewPanel();
    	if(menu.getName().equals("layout")) {
            OrbitFunction func = CSDGeneralManager.getInstance().getFunction();
            if(func instanceof Parabola)
                CSDGeneralManager.getInstance().setFunction(new Spiral());
            else if(func instanceof Spiral)
            	CSDGeneralManager.getInstance().setFunction(new CalendarFunction());
            else if(func instanceof CalendarFunction)
            	CSDGeneralManager.getInstance().setFunction(new Parabola());
    	} else if(menu.getName().equals("about")) {
            String[] str = {"Cosmo Scheduler D - version 0.5.1", 
                    "http://k-www.mickey.ai.kyutech.ac.jp/cosmo/index_e.html"};
            Dialog3D.showConfirmDialog(str, "about CSD", Dialog3D.OK_OPTION, new Dialog3D.ButtonActionListener(){
                public void actionPerformed(int option){
                    //Do nothing.
                }
            });
        }
    }    
    
    private void changeAppearance(Appearance app,
            int textureMode) {
        TransparencyAttributes ta = app.getTransparencyAttributes();
        ta.setDstBlendFunction(TransparencyAttributes.BLEND_ONE);
        ta.setSrcBlendFunction(TransparencyAttributes.BLEND_ONE);
        app.setTransparencyAttributes(ta);

        TextureAttributes texa = app.getTextureAttributes();
        texa.setTextureMode(textureMode);
        app.setTextureAttributes(texa);
    }
    
    public static class FixedStarBody extends Container3D{
        public FixedStarBody(float radius){
            /* make parts of the sun */
            Component3D sun = new Component3D();
            SimpleAppearance sunApp = new SimpleAppearance(1.0f, 0.0f, 0.0f);
            Material mat = sunApp.getMaterial();
            mat.setEmissiveColor(1.0f, 0.85f, 0.65f);
            mat.setSpecularColor(0.0f, 0.0f, 0.0f);
            sunApp.setMaterial(mat);
            sun.addChild(new Sphere(radius, sunApp));
            addChild(sun);
                        
            // set up the sun's flare and glare effects
            try {
                Component3D glare = new Component3D();
                glare.setPickable(false);
                glare.setTranslation(0, 0, -0.0001f);
                OrientedPanel glrImage = new OrientedPanel(3.1f*radius, 3.1f*radius, false);
                glrImage.setAlignmentMode(OrientedPanel.ROTATE_ABOUT_POINT);
                glrImage.setRotationPoint(0, 0, 0);
                SimpleAppearance glrApp = new SimpleAppearance(1, 0.3f, 0.3f, 0.6f, SimpleAppearance.ENABLE_TEXTURE);
                glrApp.setTexture(CosmoConfig.getConfig().getLightTexture());
                changeAppearance(glrApp, new Color3f(1.0f, 0.3f, 0.3f), TextureAttributes.MODULATE);
                glrImage.setAppearance(glrApp);
                glare.addChild(glrImage);
                addChild(glare);

                Component3D flare = new Component3D();
                flare.setPickable(false);
                flare.setTranslation(0, 0, -0.0001f);
                OrientedPanel image = new OrientedPanel(6f*radius, 6f*radius, false);
                image.setAlignmentMode(OrientedPanel.ROTATE_ABOUT_POINT);
                image.setRotationPoint(0, 0, 0);
                SimpleAppearance flrApp = new SimpleAppearance(1, 0.3f, 0.3f, 0.6f, SimpleAppearance.ENABLE_TEXTURE);
                flrApp.setTexture(getClass().getResource("/resources/cosmo/images/light2.bmp"));
                changeAppearance(flrApp, TextureAttributes.DECAL);
                image.setAppearance(flrApp);
                flare.addChild(image);
                addChild(flare);
            } catch (IOException e) {
                System.err.println("File is not found.");
                e.printStackTrace();
            }
        }
        
        private void changeAppearance(Appearance app,
                Color3f color, int textureMode) {
            app.setMaterial(null);
            app.setColoringAttributes(new ColoringAttributes(color.x, color.y,
                    color.z, ColoringAttributes.FASTEST));
            changeAppearance(app, textureMode);
        }
        
        private void changeAppearance(Appearance app,
                int textureMode) {
            TransparencyAttributes ta = app.getTransparencyAttributes();
            ta.setDstBlendFunction(TransparencyAttributes.BLEND_ONE);
            ta.setSrcBlendFunction(TransparencyAttributes.BLEND_ONE);
            ta.setTransparency(0.9f);
            app.setTransparencyAttributes(ta);

            TextureAttributes texa = app.getTextureAttributes();
            texa.setTextureMode(textureMode);
            app.setTextureAttributes(texa);
            
            app.setCapability(Appearance.ALLOW_TRANSPARENCY_ATTRIBUTES_READ);
            app.setCapability(Appearance.ALLOW_TRANSPARENCY_ATTRIBUTES_WRITE);
        }
    }
    
    /*
    private void changeAppearance(Appearance app,
            Color3f color, int textureMode) {
        app.setMaterial(null);
        app.setColoringAttributes(new ColoringAttributes(color.x, color.y,
                color.z, ColoringAttributes.FASTEST));
        changeAppearance(app, textureMode);
    }
    */
}
