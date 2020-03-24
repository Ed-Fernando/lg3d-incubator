package nu.koidelab.cosmo.wg.nodes;

import org.jdesktop.lg3d.wg.Container3D;

public class GalacticSystem extends Container3D {
    private PlanetSystem theSolorSystem;

    public GalacticSystem(){
        theSolorSystem = new PlanetSystem();
        addChild(theSolorSystem);
    }  
    
    public PlanetSystem getMySolorSystem(){
        return theSolorSystem;
    }
}
