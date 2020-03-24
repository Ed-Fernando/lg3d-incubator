package nu.koidelab.cosmo.util.bind2d;

import java.io.File;

import javax.swing.JPanel;
import javax.vecmath.Vector3f;

import nu.koidelab.cosmo.manager.OrbiterEditor;
import nu.koidelab.cosmo.wg.event.MovableAdapter;
import nu.koidelab.cosmo.wg.shape.WindowDecoration;

import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.wg.SwingNode;
import org.jdesktop.lg3d.wg.event.MouseEvent3D;


/**
 * @author seele
 */
public class LgExplorerSwing extends Container3D {
	private float width, height;
	private OrbiterEditor.BabyPlanet source;
	
	public LgExplorerSwing(OrbiterEditor.BabyPlanet source) {
		this.source=source;		
		setName("LgExplorerSwing");

        // make swing panel of file chooser.
		SwingNode swingNode = new SwingNode();
		CosmoFileChooserSwing cf = new CosmoFileChooserSwing(this);
		JPanel panel = cf.getFileChooserPanel("resources");
		swingNode.setPanel(panel);
        addChild(swingNode);

        // make window decoration (GlassyPanel, RectShadow, and buttons)
        float width  = swingNode.getLocalWidth();
        float height = swingNode.getLocalHeight();
        WindowDecoration deco = new WindowDecoration(width, height, getName(), false){
        	public void closeAction() {
        		cancel();
        	}
        	public void minimizeAction() {}
        };        
        addChild(deco);
        
		addListener(new MovableAdapter(MouseEvent3D.ButtonId.BUTTON1));
        Vector3f size = new Vector3f();
        deco.getPreferredSize(size);		
		setPreferredSize(size);				
	}

	public void selection(File file) {
		source.setFilename(file.getAbsolutePath());
	}
	public void cancel(){
		source.cancel();
	}
}
