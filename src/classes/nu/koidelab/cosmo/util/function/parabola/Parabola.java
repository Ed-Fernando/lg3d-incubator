package nu.koidelab.cosmo.util.function.parabola;

import java.util.Calendar;

import javax.vecmath.Vector3f;

import nu.koidelab.cosmo.manager.CSDGeneralManager;
import nu.koidelab.cosmo.manager.CosmoConfig;
import nu.koidelab.cosmo.util.camera.cameranode.OrbitalCamera;
import nu.koidelab.cosmo.util.function.OrbitFunction;
import nu.koidelab.cosmo.util.function.decoration.DayOrbitDecoration;
import nu.koidelab.cosmo.util.function.decoration.MonthOrbitDecoration;
import nu.koidelab.cosmo.util.function.decoration.OrbitDecoration;
import nu.koidelab.cosmo.wg.nodes.EditableOrbiter;
import nu.koidelab.cosmo.wg.nodes.Orbiter;
import nu.koidelab.cosmo.wg.nodes.group.TimeGroup.IDPanel;
import nu.koidelab.cosmo.wg.shape.NameTag.Location;

import org.jdesktop.lg3d.wg.Toolkit3D;

/**
 * @author fumi_ss
 * 14:27:11
 */
public class Parabola implements OrbitFunction {        
    private static final int METER_TO_CENTIMETER = 100;    
    private static final float CENTIMETER_TO_METER = 0.01f;    
    private static final long MSEC_OF_A_DAY = 24 * 60 * 60 * 1000;
    private static final float MSEC_RANGE = MSEC_OF_A_DAY;//a day
    
    private float CENTIMETER_RANGE = 0;            
    /*--- centimeter/msec [m/msec] ---*/
    private float MSEC_TO_CENTIMETER_RATIO = CENTIMETER_RANGE / MSEC_RANGE;
    
    
    private int currentMode;    
    private float curvant = 0;
    private float offset = 0.0f;
    
    /* Test implementation*/
    private static final float ORBITS_SPACE = 0.2f;
    String[] categories;

    
    public Parabola(){
        
    	/*-------------------- Now Optimizing... ------------------*/
        Toolkit3D toolkit3d = Toolkit3D.getToolkit3D();
    	int width = toolkit3d.widthPhysicalToNative(CosmoConfig.SCREEN_WIDTH);
    	int height = toolkit3d.heightPhysicalToNative(CosmoConfig.SCREEN_HEIGHT);
//      System.err.println("Window width = " + width);
    	
        if(width < 800)// for screen size 800*600
        	curvant = 0.14f;
        else if(width < 1024)// for screen size 1024*768
        	curvant = 0.08f;
        else if(width < 1280)// for screen size 1280*1024
        	curvant = 0.08f;
        else if(width < 1400)// for screen size 1400*1050
        	curvant = 0.06f;
        else 
        	curvant = 0.04f;
        /*-------------------- Now Optimizing... ------------------*/

        	
    	
    	if(CSDGeneralManager.getInstance().getPropertyManager() == null){
    		categories = new String[0];
    	} else {
    		categories = CSDGeneralManager.getInstance().getPropertyManager().getCategories();    		
    	}
        setMode(Calendar.WEEK_OF_MONTH);
    }
    
    /*
    public Parabola(float curvant, float offset){
        this();
        this.curvant = curvant;
        this.offset = offset;
    }
    */
    
    public void setCategories(String[] categories){
        this.categories = categories;
    }

    public int getMode(){
        return currentMode;
    }
    
    public void setMode(int mode){
        if(currentMode == mode)
            return;
        currentMode = mode;
        
        /*-------------------- Now Optimizing... ------------------*/
        switch (mode) {
        case Calendar.YEAR:
            CENTIMETER_RANGE = 0.5f;
            break;
        case Calendar.MONTH:
            CENTIMETER_RANGE = 4;
            break;
        case Calendar.WEEK_OF_MONTH:
            CENTIMETER_RANGE = 20;
            break;
        case Calendar.DAY_OF_MONTH:
            CENTIMETER_RANGE = 40;
            break;
        case Calendar.HOUR_OF_DAY:
            CENTIMETER_RANGE = 60;
        default:
            break;
        }
        MSEC_TO_CENTIMETER_RATIO = CENTIMETER_RANGE / MSEC_RANGE;
        /*-------------------- Now Optimizing... ------------------*/
        
        
    }

    public float[] getPosition(long msec) {
        long def = System.currentTimeMillis() - msec;
        float x;
        float z = def * MSEC_TO_CENTIMETER_RATIO;        
        
        if(def < 0){
            x = g(-z);        
            x = -x;
            z = -z;
        }else{
            x = g(z);
        }
        
        float[] vec = {x*CENTIMETER_TO_METER, 0.0f, -z*CENTIMETER_TO_METER};
        return vec;
    }
    
    public void setDecoration(OrbitDecoration deco){
        if (deco instanceof MonthOrbitDecoration) {
            setDecoration((MonthOrbitDecoration) deco);
            return;
        } else if (deco instanceof DayOrbitDecoration) {
            setDecoration((DayOrbitDecoration) deco);
            return;
        }
        System.err.println("Parabola : Do Nothing.");
    }
    
    private void setDecoration(DayOrbitDecoration deco){
        if(!(deco.getParts() instanceof ParabolaDayDecoration)){
            long st = deco.getSt();
            long ed = deco.getEd();
            int dayOfWeek = deco.getDayOfWeek();
            deco.setParts( new ParabolaDayDecoration(st, ed, dayOfWeek) );
        }
        
        ParabolaDayDecoration decoration = (ParabolaDayDecoration)(deco.getParts());
        decoration.updateLight(this);
    }
    
    /* Do if decoration is instance of MonthOrbitDecoration. */
    private void setDecoration(MonthOrbitDecoration deco){
    	/* if "deco" doesn't have the instance of the ParabolaMonthDecoration,
    	   it's parts is exchanged with ParabolaMonthDecoration.               */    	 
        if(!(deco.getParts() instanceof ParabolaMonthDecoration)){
            long st = deco.getSt();
            long ed = deco.getEd();
            float[] color = deco.getMonthColor();
            deco.setParts( new ParabolaMonthDecoration(st, ed, color) );
        }
        ParabolaMonthDecoration decoration = (ParabolaMonthDecoration)(deco.getParts());
                                            
        /* Display month color panel only when YEAR-MODE. */
        if(currentMode == Calendar.YEAR){
            decoration.lightComp.setVisible(true);
            decoration.updateLight(this);
        } else {            
            decoration.lightComp.setVisible(false);
        }
               
        if( currentMode != Calendar.YEAR && currentMode != Calendar.MONTH ){
            decoration.linesCont.setVisible(true);
            decoration.updateAllLines(this);
            decoration.setExtDecos(this, true);
        } else {
            decoration.linesCont.setVisible(false);
            decoration.setExtDecos(this, false);
        }

        /* category lines */
        if (categories.length > 0) {
			if (categories.length != decoration.lines.size()) {
				for (int j = categories.length - 1; j >= 0; j--) {
					int index = -1;
					for (int i = decoration.lines.size() - 1; i >= 0; i--) {
						if (decoration.lines.get(i).name.equals(categories[j]))
							index = i;
					}
					if (index < 0)
						decoration.makeNewLine(this, categories[j]);
				}
			}
		}
        /* mainLine */
        decoration.updateMainLine(this);
    }
    
    
    /* Test implementation */
    float getZShift(String category) {
        int index = 0;
        for (int i = categories.length - 1; i >= 0; i--) {
            if(categories[i].equals(category)){
                index = i+1;
            }
        }        
        float zShift = - index * ORBITS_SPACE;
        return zShift;
    }
    
    /* FIXME : Test implementation */
    public String getCategoryOf(boolean up, String category){
        int currentIndex = 0;
        for (int i = categories.length - 1; i >= 0; i--) {
            if(categories[i].equals(category)){
                currentIndex = i+1;
            }
        }        
        if(up){
            if( (currentIndex + 1) <= categories.length)
                return categories[currentIndex];
            else
                return category;
        } else {
            if( (currentIndex - 1) > 0)
                return categories[currentIndex-2];
            else if( currentIndex == 1 )
                return "";
            else return category;
        }
    }
    
    
    public void getPosition(long msec, Vector3f vec) {
        long def = System.currentTimeMillis() - msec;
        float x;
        float z = def * MSEC_TO_CENTIMETER_RATIO;        
        
        if(def < 0){
            x = g(-z);        
            x = -x;
            z = -z;
        }else{
            x = g(z);
        }
        
        vec.set(x*CENTIMETER_TO_METER, 0.0f, -z*CENTIMETER_TO_METER);
    }
    
    public void setPosition(Orbiter orbiter){
        long msec = orbiter.getTime();

        float[] vec = getPosition(msec);
        
        /* Test implementation*/
        float zShift = 0;			
        if (orbiter instanceof EditableOrbiter && currentMode != Calendar.YEAR && currentMode != Calendar.MONTH) {
			EditableOrbiter eo = (EditableOrbiter) orbiter;
//			eo.getNameTag().setTextScale(1f);
//			eo.getNameTag().setLocation(Location.TOP);
	        String category = eo.getPlan().getCategory();
	        zShift = getZShift(category);
//	        orbiter.changeScale(1f, 600);
		} 
        if(orbiter instanceof IDPanel)
            orbiter.changeScale(1.5f, 600);
        if (orbiter instanceof EditableOrbiter) {
			EditableOrbiter eo = (EditableOrbiter) orbiter;
			eo.getNameTag().setTextScale(1f);
			eo.getNameTag().setLocation(Location.TOP);
	        eo.changeScale(1f, 600);
		}

        
        orbiter.changeTranslation(vec[0], vec[1], vec[2] + zShift, 600);
    }

    public float[] getPositionTangentVector(long msec) {
        float x = msec * MSEC_TO_CENTIMETER_RATIO;

        float[] vec = {CENTIMETER_TO_METER, 0, -gDifferential(x) * CENTIMETER_TO_METER}; 
        return vec;
    }
    
    public void initializeCamera(OrbitalCamera camera) {        
        camera.setDefaultAngle( ((float)Math.toRadians(8)) );
        camera.setDefaultYaw(0);
        camera.setDefaultDistance( 2.41f );
        camera.setDefaultOffset(0, -CosmoConfig.SCREEN_HEIGHT * 0.3f, 0);
        camera.resetDefaultValues();
    }

    private Vector3f vec = new Vector3f();
    public void resetCamera(OrbitalCamera camera) {
        getPosition(camera.getCurrentPosition(), vec);
        vec.scale(-1);        
        camera.setPointOfRegard(vec.x, vec.y, vec.z);

        /* Camera Animatio */
        if(vec.z > 3.0f){
        	float ratio = (6.0f - vec.z) / (6.0f - 3.0f);
        	if(ratio > 0)
        		camera.setOffset(0, -CosmoConfig.SCREEN_HEIGHT * 0.45f * ratio, 0);
        	else 
            	camera.setOffset(0, 0, 0);
        } else {
        	camera.setOffset(0, -CosmoConfig.SCREEN_HEIGHT * 0.3f, 0);
        }
        
        if(vec.z > 4.0f){
    		float tmp2 = 7.0f - vec.z;
    		if(tmp2 > 0){
    			float space2 = 7.0f - 3.0f;
    			float ratio2 = tmp2 / space2;
    			camera.setDistance( 2.2f + 0.21f * ratio2);
    			float yaw = (vec.x > 0) ? ((float)Math.toRadians(8))*(1-ratio2) : -((float)Math.toRadians(8))*(1-ratio2);    			
    			camera.setYaw(yaw);
    		} else {
    			camera.setDistance(2.2f);
    			camera.setYaw( (vec.x > 0) ? ((float)Math.toRadians(8)) : -((float)Math.toRadians(8)) );
    		}    	
    	} else {
    		camera.setDistance(2.41f);
    		camera.setYaw(0);
    	}
    }
    
    
    
    
    /* --------------------------------- private functions --------------------------------*/
    private float g(float x) {
        float y = 0;
        y = (float)Math.sqrt( ((x - offset)/curvant) );
        return y;
    }
    
    private float f(float x) {
        float y;
        y = curvant * x * x + offset;
        return -y;
    }
    
    private float gDifferential(float x) {
        float y = 0;
        y = 2*curvant*x; 
        return y;
    }    
    /* ----------------------------------------------------------------------------------------*/
}
