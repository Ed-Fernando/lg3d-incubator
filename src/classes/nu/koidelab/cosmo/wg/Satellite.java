package nu.koidelab.cosmo.wg;

import java.util.TimerTask;

import nu.koidelab.cosmo.manager.CSDGeneralManager;
import nu.koidelab.cosmo.wg.action.AppLaunchAction;
import nu.koidelab.cosmo.wg.shape.OrientedPanel;

import org.jdesktop.lg3d.utils.action.ActionBoolean;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MouseEnteredEventAdapter;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.wg.Cursor3D;
import org.jdesktop.lg3d.wg.event.LgEventSource;
import org.jdesktop.lg3d.wg.event.MouseEvent3D;

/**
 * @author fumi Satellite org.wg
 */
public class Satellite extends Container3D {
	protected static final float SPACER = 0.007f;

	private String reference;

	private float planetRadius;

	private RotateAction rotateAction;

	private SateliteBody[] childs;

    public Satellite(float planetRadius, String reference) {
        super();
        this.planetRadius = planetRadius;
        this.reference = reference;
        setCursor(Cursor3D.SMALL_CURSOR);
        seperateData();
        initRotateAction();
    }
    
	private void seperateData() {
		String[] datas = reference.split(",");
		childs = new SateliteBody[datas.length];
		for (int i = 0; i < datas.length; i++) {
			childs[i] = new SateliteBody(datas[i]);
			addChild(childs[i]);
		}
	}
	
	private void initRotateAction(){
		this.setRotationAxis(0, 1, 0);
		float radius = planetRadius + SPACER;
		float offset = 360 / childs.length;
		float angle = 0;
		for (int i = 0; i < childs.length; i++) {
			childs[i].setTranslation(((float) Math.cos(angle)) * radius,
					((float) Math.sin(angle)) * radius, 0);
			angle += offset;
//			childs[i].setRotationAxis(0.0f, -1.0f, 0.0f);
		}

		rotateAction = new RotateAction(angle, childs, this);
		CSDGeneralManager.getInstance().getTimer().scheduleAtFixedRate(
				rotateAction, 1000, 60);
        addListener(new MouseEnteredEventAdapter(new RotateStopAction(this)));
	}

	private class SateliteBody extends Component3D {
		private SateliteBody(String filename) {
			/* ======== shape settings ======== */
			SimpleAppearance app = new SimpleAppearance(1.0f, 1.0f, 1.0f, 1.0f,
					SimpleAppearance.ENABLE_TEXTURE);
			OrientedPanel icon = new OrientedPanel(planetRadius * 0.6f,	planetRadius * 0.6f, true);
			icon.setAlignmentMode(OrientedPanel.ROTATE_ABOUT_POINT);
			icon.setRotationPoint(0, 0, 0);

			/* set imagefile and associate application with the extention of the file. */
			String appName = CSDGeneralManager.getInstance().getFileManager().getFileAssociation(filename);
			app.setTexture(CSDGeneralManager.getInstance().getFileManager().getFileIcon(filename));
			icon.setAppearance(app);
			addChild(icon);            
			if(appName != null){
                if(appName.startsWith("java") || 
                        !System.getProperty("os.name").startsWith("Windows")){
                    setAppLauncher(appName + " " + filename);
                } else {
                    setAppLauncher(appName + " \"" + filename + "\"");
                }
			} 
		}
		
		private void setAppLauncher(String app) {
			addListener(new MouseClickedEventAdapter( 
					true, new AppLaunchAction(app)));
		}
	}

	private class RotateStopAction implements ActionBoolean {
		private Container3D axis;

		private float prevAngle = 0;

		private RotateStopAction(Container3D cont) {
			this.axis = cont;
		}

		public void performAction(LgEventSource source, boolean enter) {
			if (enter) {				
				rotateAction.cancel();
				prevAngle = rotateAction.getAngle();
				rotateAction = null;
			} else {
				rotateAction = new RotateAction(prevAngle, childs, axis);
				CSDGeneralManager.getInstance().getTimer().scheduleAtFixedRate(
						rotateAction, 1000, 60);
			}
		}
	}

	private static class RotateAction extends TimerTask {
		private static final float speed = ((float) Math.toRadians(2));
		private static final int interval = 1000;        
        private static final float PI_2 = (float)(Math.toRadians(360));
        
		private float angle;
		private Component3D body[];
		private Container3D axis;

		private RotateAction(float angle, Component3D[] body, Container3D axis) {
			this.angle = angle;
			this.body = body;
			this.axis = axis;
		}

		public void run() {
			angle += speed;
			if (angle > PI_2) {
				angle = angle - PI_2;
			}
//			for (int i = 0; i < body.length; i++) {
//				body[i].changeRotationAngle(angle, interval);
//			}
			axis.changeRotationAngle(angle, interval);
		}

		private float getAngle() {
			return angle;
		}
	}	
}