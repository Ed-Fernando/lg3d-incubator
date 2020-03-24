/*
 * ºîÀ®Æü: 2005/08/17
 */
package nu.koidelab.cosmo.wg.shape;

import static nu.koidelab.cosmo.manager.CosmoConfig.SCHEDULE_NAME_FONT;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import nu.koidelab.cosmo.manager.CSDGeneralManager;
import nu.koidelab.cosmo.util.c3danimation.BlinkingAnimation;

import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.sg.LineArray;
import org.jdesktop.lg3d.sg.LineAttributes;
import org.jdesktop.lg3d.sg.OrientedShape3D;
import org.jdesktop.lg3d.sg.TransparencyAttributes;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.Component3D;

public class NameTag extends Component3D {
	public static enum Location {TOP, SIDE, OTHER};

	private static BlinkingAction blinker = new BlinkingAction(0.0f);
	static {
		// LgEventConnector.getLgEventConnector().addListener(
		// StrollLight.class,
		// new GenericEventAdapter(CallOrbiterEvent.class, blinker));
		/* --- TODO : should be implemented in better way. --- */
		Timer timer = CSDGeneralManager.getInstance().getTimer();
		timer.scheduleAtFixedRate(blinker, 1000, 10000);
	}
	private int duration;
	private String name;
	private float radius;
	private int priority;
	private Location currentLoc;	
	private float orbiterScale = 1;
	private float textScale = 1;
	
	public NameTag(float radius, String name, int priority) {
		this.name = name;
		this.radius = radius;
		this.priority = priority;

		/* TODO ------------------ flashing -------------------- */
		/* This should be implemented in better way. */
		duration = 1500 * priority;
		setAnimation(new BlinkingAnimation(duration, 0.3f, 0.65f, 1.0f));
		
		setLocation(Location.TOP);
		setPickable(false);
	}
	
	public void setLocation(Location loc){
		if(currentLoc == loc) return;
		currentLoc = loc;
		repaint();
	}

	
	public void setOrbiterScale(float s){
		if(orbiterScale == s) return;
		orbiterScale = s;
		repaint();
	}
	
	public void setTextScale(float s){
		if(textScale == s) return;
		textScale = s;
		repaint();
	}
	
	private void repaint(){
		if(currentLoc == Location.SIDE)
			setSideTag();
		else if(currentLoc == Location.TOP)
			setTopTag();
	}

	private void setSideTag(){
		float scale = 1/orbiterScale * textScale;

		removeAllChildren();
		
		Component3D comp = new Component3D();
		comp.setScale(scale);
		TextFieldPanel textPanel = new TextFieldPanel(name, false, false,
				SCHEDULE_NAME_FONT, new Color(0.20f, 0.4f, 0.4f, 1.0f),
				new Color(1f, 1f, 1f, 1f), 0.99f);
		float tmpX = (radius * 1.2f) + textPanel.getWidth()/2*scale;
		comp.setTranslation(tmpX, 0, 0.0001f);
		setTransparency(0.0f);
		comp.addChild(textPanel);
		
		blinker.removeTarget(this);
		addChild(comp);		
	}
	
	
	private void setTopTag(){
		float scale = 1/orbiterScale * textScale;

		removeAllChildren();
		
		Component3D textComp = new Component3D();
		textComp.setScale(scale);
		float[] vec = { radius * 1.2f, radius * 1.4f, 0.00001f };
		if (priority < 3)
			vec[0] = -vec[0];

		/* ------ display plan's name ------ */
		OrientedTextFieldPanel panel = new OrientedTextFieldPanel(name, false,
				false, SCHEDULE_NAME_FONT,
				new Color(0.75f, 0.85f, 0.75f, 1.0f), new Color(0, 0, 0, 0),
				0.99f);
		panel.setAlignmentMode(OrientedShape3D.ROTATE_ABOUT_POINT);
		
		float w = panel.getWidth() * scale;
		float h = panel.getHeight() * scale;
		float tmpX = (vec[0] >= 0) ? vec[0] + w/2 : vec[0] - w/2;
		float tmpY = vec[1] + h/2;
		textComp.setTranslation(tmpX, tmpY, vec[2]);
		panel.setRotationPoint(-tmpX/scale, -tmpY/scale, - vec[2]/scale);
		
		textComp.addChild(panel);
		addChild(textComp);
		
		
		Component3D lineComp = new Component3D();
		/* ----- line decoration ----- */  
		tmpX = (vec[0] >= 0) ? vec[0] + w : vec[0] - w;
		float[] vertices = { 
				0,       0,       0, 
				vec[0], vec[1], vec[2],
				vec[0], vec[1], vec[2], 
				tmpX,   vec[1], vec[2], 
		};
		lineComp.addChild(new Line(vertices));

		addChild(lineComp);
		changeTransparency(1.0f, 2000);
		blinker.addTarget(this);
	}
	
	private class Line extends OrientedShape3D {
		private Line(float[] vertices) {			
			setAlignmentMode(OrientedShape3D.ROTATE_ABOUT_POINT);
			setRotationPoint(0, 0, 0);
			
			float r = 0.75f;
			float g = 0.85f;
			float b = 0.75f;
			float[] colors = { r, g, b, r, g, b, r, g, b, r, g, b, };

			LineArray geom = new LineArray(vertices.length,
					LineArray.COORDINATES | LineArray.COLOR_3);
			geom.setCoordinates(0, vertices);
			geom.setColors(0, colors);
			setGeometry(geom);

			SimpleAppearance app = new SimpleAppearance(1.0f, 1.0f, 1.0f, 0.99f);
			app.setCapability(Appearance.ALLOW_TRANSPARENCY_ATTRIBUTES_READ
					| Appearance.ALLOW_TRANSPARENCY_ATTRIBUTES_WRITE);
			TransparencyAttributes ta = app.getTransparencyAttributes();
			ta.setCapability(TransparencyAttributes.ALLOW_BLEND_FUNCTION_WRITE
					| TransparencyAttributes.ALLOW_VALUE_WRITE
					| TransparencyAttributes.ALLOW_VALUE_READ);
			app.setTransparencyAttributes(ta);
			LineAttributes la = new LineAttributes(1,
					LineAttributes.PATTERN_SOLID, true);
			app.setLineAttributes(la);
			setAppearance(app);
		}
	}

	private static class BlinkingAction extends TimerTask {
		private List<NameTag> tagList = new LinkedList<NameTag>();

		private float target;

		private BlinkingAction(float targetTrans) {
			target = targetTrans;
		}

		private void addTarget(NameTag tag) {
			if (tagList.indexOf(tag) < 0)
				tagList.add(tag);
		}

		private void removeTarget(NameTag tag) {
			tagList.remove(tag);
		}

		public void run() {
			for (int i = tagList.size() - 1; i >= 0; i--) {
				NameTag t = tagList.get(i);
				t.changeTransparency(target, t.duration);
			}
		}
	}
	
}
