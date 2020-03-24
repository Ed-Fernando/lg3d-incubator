package nu.koidelab.cosmo.wg.nodes;

import javax.vecmath.Vector3f;

import nu.koidelab.cosmo.manager.CSDGeneralManager;
import nu.koidelab.cosmo.util.cursor.CursorManagementFrame3D;
import nu.koidelab.cosmo.util.gesture.menu.MenuContainer;
import nu.koidelab.cosmo.wg.CosmoClock;

import org.jdesktop.lg3d.utils.shape.GlassyPanel;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Thumbnail;

public class SpaceFrame extends CursorManagementFrame3D {
	private GalacticSystem theGalaxy;
	
    public SpaceFrame() {
        setName("Space");
        setPreferredSize(new Vector3f(0.1f, 0.1f, 0.3f));
        setThumbnail(new CosmoThumbnail());
        new MenuContainer();
        
        theGalaxy = new GalacticSystem();
        theGalaxy.addChild(new CosmoClock(this));
    }
    
    public GalacticSystem getTheGalaxy(){
    	return theGalaxy;
    }
    
    @Override
    public void changeEnabled(boolean enabled, int duration) {    	
        if(!enabled) CSDGeneralManager.getInstance().exitMainGalaxy();
        super.changeEnabled(enabled, duration);
    }

    
    private class CosmoThumbnail extends Thumbnail {
        private CosmoThumbnail() {
            Component3D c = new Component3D();
//            TimePanel time = new TimePanel(System.currentTimeMillis());
//            addChild(time);
            c.addChild(new GlassyPanel(0.015f, 0.015f, 0.001f,
                    new SimpleAppearance(0.4f, 0.6f, 0.4f)));
            addChild(c);
            setPreferredSize(new Vector3f(0.015f, 0.015f, 0.001f));
        }
    }
}
