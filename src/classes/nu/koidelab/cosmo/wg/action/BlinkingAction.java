/*
 * 作成日： 2005/03/26
 *
 */
package nu.koidelab.cosmo.wg.action;

import org.jdesktop.lg3d.utils.action.ActionNoArg;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.event.LgEventSource;

/**
 * @author fumi_ss
 * 20:30:53
 */
public class BlinkingAction implements ActionNoArg {
    private Component3D comp;
    private int duration;
    private float target;

    public BlinkingAction(Component3D component, float targetTrans, int duration){
        comp = component;
        target = targetTrans;
        this.duration = duration;
    }

    public void performAction(LgEventSource source) {
        comp.changeTransparency(target, duration);
    }
}
