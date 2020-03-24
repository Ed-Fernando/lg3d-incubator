package nu.koidelab.cosmo.util.function.calendar;

import java.util.Calendar;

import nu.koidelab.cosmo.util.function.decoration.OrbitDecoration;
import nu.koidelab.cosmo.wg.shape.FuzzyEdgePanel;
import nu.koidelab.cosmo.wg.shape.Panel;

import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.Component3D;

class CalendarDayDecoration extends OrbitDecoration.DecorationParts {	
	private long st;
	private boolean isHighLighted = true;
	private Panel panel;

	CalendarDayDecoration(long st, int dayOfWeek, float width, float height) {
		this.st = st;
		setPickable(false);
		panel = new FuzzyEdgePanel(width, height, width/2, -height/2, 0, true, width*0.05f);
		panel.setAlpha(0.8f);
		panel.setAppearance(new SimpleAppearance(0.55f, 0.6f, 0.55f, 0.9f));
		if(dayOfWeek == Calendar.SUNDAY)
			((SimpleAppearance)(panel.getAppearance())).setColor(0.9f, 0.6f, 0.6f);
		else if(dayOfWeek == Calendar.SATURDAY)
			((SimpleAppearance)(panel.getAppearance())).setColor(0.6f, 0.6f, 0.9f);
		Component3D c = new Component3D();
		c.addChild(panel);
		addChild(c);		
	}
	
	void updatePosition(CalendarFunction f){
		float[] v = f.getPosition(st);
		this.changeTranslation(v[0], v[1], v[2]);		
	}
	
	void setHighLight(boolean highLight){
		if(isHighLighted != highLight){
			isHighLighted = highLight;
			if(isHighLighted){
				panel.setAlpha(0.5f);
			} else {
				panel.setAlpha(0.1f);
			}
		}
			
	}
}
