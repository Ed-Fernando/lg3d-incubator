package nu.koidelab.cosmo.util.function.decoration;

import org.jdesktop.lg3d.wg.Container3D;

/**
 * @author fumi_ss
 */
public abstract class OrbitDecoration extends Container3D{
    protected long st;
    protected long ed;
    protected DecorationParts parts = new PseudoParts();
    
    public OrbitDecoration(long st, long ed){
        setPickable(false);
        this.st = st;
        this.ed = ed;
        addChild(parts);
    }
    
    public long getSt() {
        return st;
    }
    
    public long getEd() {
        return ed;
    }
    
    public void setParts(DecorationParts parts){
        addChild(parts);
        removeChild(this.parts);
        this.parts = parts;
    }
    
    public DecorationParts getParts(){
        return parts;
    }
    
    public static abstract class DecorationParts extends Container3D{}
    public static class PseudoParts extends DecorationParts{}
}
