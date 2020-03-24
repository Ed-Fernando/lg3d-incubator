package nu.koidelab.cosmo.util.function.calendar;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.vecmath.Vector3f;

import nu.koidelab.cosmo.manager.CosmoConfig;
import nu.koidelab.cosmo.util.camera.cameranode.OrbitalCamera;
import nu.koidelab.cosmo.util.function.OrbitFunction;
import nu.koidelab.cosmo.util.function.decoration.DayOrbitDecoration;
import nu.koidelab.cosmo.util.function.decoration.MonthOrbitDecoration;
import nu.koidelab.cosmo.util.function.decoration.OrbitDecoration;
import nu.koidelab.cosmo.wg.nodes.EditableOrbiter;
import nu.koidelab.cosmo.wg.nodes.Orbiter;
import nu.koidelab.cosmo.wg.nodes.group.DayGroup;
import nu.koidelab.cosmo.wg.nodes.group.TimeGroup;
import nu.koidelab.cosmo.wg.nodes.group.TimeGroup.IDPanel;
import nu.koidelab.cosmo.wg.shape.NameTag.Location;

public class CalendarFunction implements OrbitFunction {
	private static long LENGTH_OF_WEEK = 1000*60*60*24*7;
	private int currentMode;
	private long cameraPos;
	private int cameraYear;
	private int cameraMonth;
	private int cameraMaxWeekOfMonth;
	private Calendar cal = GregorianCalendar.getInstance();

	public CalendarFunction() {
		setMode(Calendar.WEEK_OF_MONTH);
	}

	public void setMode(int mode) {
		currentMode = mode;
	}

	public int getMode() {
		return currentMode;
	}

	public float[] getPosition(long msec) {
		cal.setTimeInMillis(msec);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int date = cal.get(Calendar.DAY_OF_MONTH);

		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		int weekOfMonth = cal.get(Calendar.WEEK_OF_MONTH);
		int maxWeekOfMonth = cal.getActualMaximum(Calendar.WEEK_OF_MONTH);
		
		cal.set(year, month, date, 0, 0, 0); 
		cal.set(Calendar.MILLISECOND, 0);
		int val = -(dayOfWeek- cal.getFirstDayOfWeek());
		val = val <= 0 ? val : -(7 - val); 
		cal.add(Calendar.DAY_OF_MONTH, val);
		long firstDayOfWeek = cal.getTimeInMillis();

		float x = ( (float)(msec - firstDayOfWeek) / LENGTH_OF_WEEK)*CosmoConfig.SCREEN_WIDTH - CosmoConfig.SCREEN_WIDTH/2;
		
		if(year == cameraYear && month == cameraMonth){
			float y = - ( (float)weekOfMonth / maxWeekOfMonth ) *CosmoConfig.SCREEN_HEIGHT + CosmoConfig.SCREEN_HEIGHT/2;
			float[] ans = {x, y, 0};
			return ans;
		} else {
			float y;
			if(month == cameraMonth - 1){
				y = - ( (float)(weekOfMonth - maxWeekOfMonth + 1) / cameraMaxWeekOfMonth ) *CosmoConfig.SCREEN_HEIGHT + CosmoConfig.SCREEN_HEIGHT/2;
			} else if(month == cameraMonth + 1) { 
				y = - ( (float)(weekOfMonth + cameraMaxWeekOfMonth - 1) / cameraMaxWeekOfMonth ) *CosmoConfig.SCREEN_HEIGHT + CosmoConfig.SCREEN_HEIGHT/2;
			} else if(year != cameraYear){
				if(month > cameraMonth){
					y = - ( (float)(weekOfMonth - maxWeekOfMonth) / cameraMaxWeekOfMonth ) *CosmoConfig.SCREEN_HEIGHT + CosmoConfig.SCREEN_HEIGHT/2;					
				} else {
					y = - ( (float)(weekOfMonth + cameraMaxWeekOfMonth) / cameraMaxWeekOfMonth ) *CosmoConfig.SCREEN_HEIGHT + CosmoConfig.SCREEN_HEIGHT/2;				

				}
			} else {
				float[] ans = {0, 0, -4.0f};
				return ans;
			}
			float[] ans = {x, y, 0};
			return ans;
		}		
	}

	public void getPosition(long msec, Vector3f vec) {
		float[] v = getPosition(msec);
		vec.set(v[0], v[1], v[2]);
	}

	public float[] getPositionTangentVector(long msec) {
		// TODO Auto-generated method stub
		return null;
	}

	public void initializeCamera(OrbitalCamera camera) {
		long msec = camera.getCurrentPosition();
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTimeInMillis(msec);

		cameraYear = cal.get(Calendar.YEAR);
		cameraMonth = cal.get(Calendar.MONTH);
		cameraMaxWeekOfMonth = cal.getActualMaximum(Calendar.WEEK_OF_MONTH);

		camera.setDefaultAngle(0);
		camera.setDefaultYaw(0);
		camera.setDefaultOffset(0, 0, 0);
		camera.setDefaultDistance(2.7f);
		camera.resetDefaultValues();
	}

	public void resetCamera(OrbitalCamera camera) {
		long msec = camera.getCurrentPosition();
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTimeInMillis(msec);
		
		cameraYear = cal.get(Calendar.YEAR);
		cameraMonth = cal.get(Calendar.MONTH);
		cameraMaxWeekOfMonth = cal.getActualMaximum(Calendar.WEEK_OF_MONTH);
		camera.setPointOfRegard(0f, 0f, 0f);
	}

	public void setDecoration(OrbitDecoration deco) {
        if (deco instanceof MonthOrbitDecoration) {
//            setDecoration((MonthOrbitDecoration) deco);
        	if(!(deco.getParts() instanceof OrbitDecoration.PseudoParts))
        		deco.setParts(new OrbitDecoration.PseudoParts());
            return;
        } else if (deco instanceof DayOrbitDecoration) {
            setDecoration((DayOrbitDecoration) deco);
            return;
        }
        System.err.println("CalendarFunction : Do Nothing.");			
	}
	
	public void setDecoration(DayOrbitDecoration deco) {
        if(!(deco.getParts() instanceof CalendarDayDecoration)){
        	float w = CosmoConfig.SCREEN_WIDTH / 7;
        	float h = CosmoConfig.SCREEN_HEIGHT / 6;
        	deco.setParts(new CalendarDayDecoration(deco.getSt(), deco.getDayOfWeek(), w, h));
        }
        ((CalendarDayDecoration)(deco.getParts())).updatePosition(this);
        cal.setTimeInMillis(deco.getSt());
        ((CalendarDayDecoration)(deco.getParts())).setHighLight((cal.get(Calendar.MONTH) == cameraMonth));
	}
	

	public void setPosition(Orbiter orbiter) {
		float[] v = getPosition(orbiter.getTime());		
		orbiter.changeTranslation(v[0], v[1], v[2], 600);
		if(orbiter instanceof TimeGroup.IDPanel)
			orbiter.changeScale(1.5f, 600);
		/*
		if (orbiter instanceof EditableOrbiter) {
			EditableOrbiter eo = (EditableOrbiter) orbiter;
//			eo.getNameTag().setToSide();
			eo.getNameTag().setLocation(Location.SIDE);
			eo.changeScale(0.1f, 600);
			eo.getNameTag().setTextScale(0.5f);
		}*/
	}
	
	public void setPosition(DayGroup d){
		List<Orbiter> orbiters = d.orbiters;
		long st = d.st;
		float[] v = getPosition(st);		
		for(int i = orbiters.size()-1; i >= 0; i--){
			Orbiter o = orbiters.get(i);
			if (o instanceof EditableOrbiter) {
				EditableOrbiter eo = (EditableOrbiter) o;
				eo.getNameTag().setLocation(Location.SIDE);
				eo.changeScale(0.1f, 600);
				eo.getNameTag().setTextScale(0.5f);
				eo.changeTranslation(v[0] + 0.007f, v[1] - 0.012f * (i+1), v[2]+0.001f, 600);
			} 
		}
		IDPanel p = d.getIDPanel();
		if(p != null){
			p.changeTranslation(v[0] + 0.008f, v[1] - 0.006f, v[2]+0.001f, 600);
			p.changeScale(1.5f, 600);
		}
	}
}
