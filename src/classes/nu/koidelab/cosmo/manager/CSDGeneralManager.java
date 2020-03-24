package nu.koidelab.cosmo.manager;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import nu.koidelab.cosmo.manager.eventmanager.CSDEvent;
import nu.koidelab.cosmo.manager.eventmanager.CSDEventListener;
import nu.koidelab.cosmo.util.bind2d.swing.Dialog3D;
import nu.koidelab.cosmo.util.camera.CameraUniverse;
import nu.koidelab.cosmo.util.camera.OrbitalCameraController;
import nu.koidelab.cosmo.util.camera.cameranode.OrbitalCamera;
import nu.koidelab.cosmo.util.cursor.CursorManager;
import nu.koidelab.cosmo.util.function.OrbitFunction;
import nu.koidelab.cosmo.util.function.parabola.Parabola;
import nu.koidelab.cosmo.util.schedule.Plan;
import nu.koidelab.cosmo.wg.nodes.Orbiter;
import nu.koidelab.cosmo.wg.nodes.SpaceFrame;

import org.jdesktop.lg3d.scenemanager.utils.background.Background;
import org.jdesktop.lg3d.scenemanager.utils.background.LayeredImageBackground;
import org.jdesktop.lg3d.scenemanager.utils.event.BackgroundChangeRequestEvent;


public class CSDGeneralManager {
    /* singleton */
	private static CSDGeneralManager csdManager = null;
    
    private CursorManager cm;        
	private List<CSDEventListener> listeners = new ArrayList<CSDEventListener>();    
    private Timer timer = new Timer();
    private CameraUniverse cameraUniverse;
    private SpaceFrame theSpace;
    private OrbitalCameraController controller;
    private OrbitFunction function;   
    private OrbiterEditor editor;
	private PropertyManager propertyManager;
	private FileManager fileManager;
	
	public static CSDGeneralManager getInstance() {
        if(csdManager == null)
            csdManager = new CSDGeneralManager();
		return csdManager;
	}
    
    private CSDGeneralManager() {}
    
    public static boolean hasInstance(){
    	return (csdManager != null);
    }
    
    public CameraUniverse getCameraUniverse() {
        return cameraUniverse;
    }
    
    /* Initialize Cosmo Scheduler D */
    public void init(){
        cameraUniverse = new CameraUniverse();

//*** For visuale effect ***
//        private FlashBoard flash;
//        flash = new FlashBoard();
//        cameraUniverse.getOrientedContainer().addChild(flash);        
        
        controller = new OrbitalCameraController();
        cameraUniverse.getOrientedContainer().addChild(controller);        
        editor = new OrbiterEditor();
        cameraUniverse.getOrientedContainer().addChild(editor);
        
        /* set initial OrbitFunction */
        function = new Parabola();
//        function = new Spiral();
//        function = new CalendarFunction();
        
        
        theSpace = new SpaceFrame();
        cameraUniverse.setParent(theSpace);
        cameraUniverse.addChild(theSpace.getTheGalaxy());
    	
        OrbitalCamera oc = new OrbitalCamera();
        controller.setCamera(oc);
        cameraUniverse.setCamera(oc);
        
        dispatchEvent(new RepaintPlanetsEvent(System.currentTimeMillis(), true));
        initializeCamera();
        updateCameraPosition();
        
        setCosmoBG();        
        theSpace.changeEnabled(true, 500);
        theSpace.changeVisible(true, 1000);
        
        /* ======= Load property and schedule file ====== */
        
        // if configuration file is not found, it shows confirm dialog and 
        // ask user's permission to create files.
        // propertyManager#loadProperty() 
        // Return value of true means that property file can be read.
        
        // dialog -- "Cosmo Scheduler D configuration file could not be found."
        //           "Do you want to create configuration file into user.home/csd ?"
        //      [Yes or No or cancel]
        // Yes    -> create configuration files.
        // No     -> start demonstration mode.
        // Cancel -> exit CSD : csdManager = null; and return;

        propertyManager = new PropertyManager();
        
//        if (propertyManager.initialize()) {
//            fileManager = new FileManager(propertyManager.getProperty("UserFileAssociation"));
//            loadScheduleFile();
//        } else {
//            // config files not found
//            createConfigFilesIfRequested();
//        }
        
        startInModeUserSelected();
    }
    
    private void startInModeUserSelected() {
        String[] str = {
            "Do you want to run Cosmo Scheduler D in demonstration mode?"};
        
        // First ask if the user want to start CSD in the demo mode,
        // before checking if the config files exit in user's home directory.
        // This is to aoid the common confusion where the user mistakenly 
        // created the config files, but cannot figure out how to run in the
        // demo mode.
        Dialog3D.showConfirmDialog(str, "Information", Dialog3D.YES_NO_CANCEL_OPTION, 
            new Dialog3D.ButtonActionListener() {
                public void actionPerformed(int option) {
                    if (option == Dialog3D.YES_OPTION) {
                        runInDemoMode();
                        
                    } else if (option == Dialog3D.NO_OPTION) {
                        if (propertyManager.initialize()) {
                            fileManager = new FileManager(propertyManager.getProperty("UserFileAssociation"));
                            loadScheduleFile();
                        } else {
                            // config files not found
                            initConfigFilesIfRequested();
                        }
                    }
                }
            });
    }
    
    private void initConfigFilesIfRequested() {
        String[] str = {
            "Cosmo Scheduler D configuration files cannot be found.",
            "Do you want to create configuration files in user.home/csd ?",
            "If you answer NO, Cosmo Scheduler D runs in demonstration mode."};
        
        Dialog3D.showConfirmDialog(str, "Information", Dialog3D.YES_NO_CANCEL_OPTION, 
            new Dialog3D.ButtonActionListener() {
                public void actionPerformed(int option) {
                    if (option == Dialog3D.YES_OPTION) {
                        // configuration manager#createPropertyFiles()
                        //    copy system sample.properties into user.home/csd/csd.properties
                        //    copy system SampleScheduler.xml into user.home/csd/schedule.xml 
                        //    and HelloCSD.png displaying with Zoetrope.
                        propertyManager.createPropertyFile();
                        if (propertyManager.initialize()){
                            fileManager = new FileManager(propertyManager.getProperty("UserFileAssociation"));
                            loadScheduleFile();
                        } else {
                            System.err.println("(EE) : PropertyManager Cannot be initialized.");
                            theSpace.changeEnabled(false, 500);
                            csdManager = null;
                            return;
                        }
                    
                    } else if (option == Dialog3D.NO_OPTION) {
                        runInDemoMode();
                    
                    } else if (option == Dialog3D.CANCEL_OPTION) {
                        theSpace.changeEnabled(false, 500);
                        csdManager = null;
                        return;
                    } else {
                        new RuntimeException("Dialog3D returns unexpected value.");
                    }
                }
            });
    }
    
    private void runInDemoMode() {
        fileManager = new FileManager();
        propertyManager.setDemonstrationMode();
        theSpace.getTheGalaxy().getMySolorSystem().loadSchedule(
        propertyManager.getProperty("ScheduleFileName"));
        if (function instanceof Parabola) {
            Parabola p = (Parabola) function;
            p.setCategories(propertyManager.getCategories());
            postEvent(new RepaintPlanetsEvent());
        }
        
        /* ============= Show help ============== */
        Plan welcomePlan = new Plan("Welcome to the Space!", 3, System.currentTimeMillis());
        welcomePlan.setDetail("Double-click sateli-te to view help.");
        welcomePlan.setReference("resources/cosmo/sample/help.zoe");
        addPlan(welcomePlan);
    }
    
    private void loadScheduleFile(){
		if(!propertyManager.checkReadScheduleFile())
			propertyManager.createScheduleFile();
    	System.err.println("Load : " + propertyManager.getProperty("ScheduleFileName"));
		theSpace.getTheGalaxy().getMySolorSystem().loadSchedule(
				propertyManager.getProperty("ScheduleFileName"));
		if (function instanceof Parabola) {
			Parabola p = (Parabola) function;
			p.setCategories(propertyManager.getCategories());
			postEvent(new RepaintPlanetsEvent());
		}
    }

    /* --------------------------- Getter for Manager  ------------------------------- */
    public FileManager getFileManager() {
		return fileManager;
	}
    
    public PropertyManager getPropertyManager() {
		return propertyManager;
	}
    
    public CursorManager getCursorManager(){
    	return theSpace.getCursorManager();
    }
    
    public OrbiterEditor getEditor() {
        return editor;
    }
    /* ------------------------------------------------------------------------------- */
    
    
    /*
	 * TODO : for Test implementation Should this manager have these methods?
	 */
    public void addPlan(Plan newPlan){
        theSpace.getTheGalaxy().getMySolorSystem().addPlan(newPlan);
    }
    
    public void removePlan(Plan plan){
        theSpace.getTheGalaxy().getMySolorSystem().removePlan(plan);
    }
    
    public void exitMainGalaxy(){
    	final String filename = propertyManager.getProperty("ScheduleFileName");
    	if(filename != null && !filename.equals(PropertyManager.DEMO_SCHEDULE_FILE)) {
    		String[] msg = {"Save?"}; 
    		Dialog3D.showConfirmDialog(msg, "SAVE?", Dialog3D.YES_NO_OPTION, new Dialog3D.ButtonActionListener(){
    			public void actionPerformed(int option) {
    				if(option == Dialog3D.YES_OPTION)
    		    		theSpace.getTheGalaxy().getMySolorSystem().saveSchedule(filename);
    			}
    		});
    	} else 
    		System.err.println("Finish Cosmo Scheduler D - demonstration.");
        csdManager = null;
    }
    

    

    /* ---------------------------- Orbit Function -------------------------------- */
    public void setFunction(OrbitFunction f){
        this.function = f;
        initializeCamera();
        updateCameraPosition();
        dispatchEvent( new RepaintPlanetsEvent());
    }
    
    public OrbitFunction getFunction(){
        return function;
    }    
    
    /** Change displaying mode to arg, and reset camera's POR and 
     *   cursors' position to a position in new Mode. */
    public void setMode(int mode){
        function.setMode(mode);       
        dispatchEvent( new RepaintPlanetsEvent());
        updateCameraPosition();
    }
    /* -------------------------------------------------------------------------------- */

    
    
    
    /* ---------------------------- Camera work -------------------------------- */
    private void initializeCamera(){
        controller.initializeCamera(function);
    }
    
    private void updateCameraPosition(){
        controller.updateCameraPosition(function);
    }
    
    public void setCameraPosition(long msec){
        controller.setCameraPosition(msec, function);
    }
    
    public long getCameraPosition(){
    	return controller.getCameraPosition();
    }
    
    public void focusTo(Orbiter o){
        controller.focusTo(o);
    }
    
    public void releaseFocus(){
    	controller.releaseFocus();
        updateCameraPosition();
    }    
    /* ------------------------------------------------------------------------------- */
        

    

    
    /* --------------------------- Event handling -------------------------------- */
    private void dispatchEvent(CSDEvent evt){
        for(int i=0;i<listeners.size();i++){
            CSDEventListener listener = listeners.get(i);
            Class<CSDEvent>[] classes = listener.getTargetEventClasses();
            for (int j = classes.length - 1; j >= 0; j--) {
                if(evt.getClass().equals(classes[j])){
                    listener.processEvent(evt);          
                }
            }
        }
    }
    
    public void postEvent(CSDEvent evt){
        dispatchEvent(evt);
    }
        
	public void addListener(CSDEventListener listener){
		listeners.add(listener);
	}

    public void removeListener(CSDEventListener listener){
        listeners.remove(listener);
    }
    /* -------------------------------------------------------------------------------- */
      
        
    
    /** Change background images to CosmoSchedulerD default space images */
    private void setCosmoBG(){
        Background bg = new LayeredImageBackground(
                new URL[] {
                        //set CSD original background image
                    getClass().getResource("/resources/cosmo/images/default/" + "background/cosmoD-0.png"),
                    getClass().getResource("/resources/cosmo/images/default/" + "background/cosmoD-1.png"),
                },
                new float[][] {
                    {1.0f, 0.0f, 1.0f}, 
                    {0.5f, -0.22f, 1.2f},
                }
            );
        setBG(bg);
    }
    
    /** Utitility class Change background images to CosmoSchedulerD default space images */
    public void setBG(Background bg){
        cameraUniverse.postEvent(new BackgroundChangeRequestEvent(bg));
    }
    
    public Timer getTimer(){
        return timer;
    }       
    
    /** CosmoSchedulerD Inner Event for repainting planets and any other decorations. */
    public static class RepaintPlanetsEvent extends CSDEvent{
        private long focusTime;
        private boolean isFocusChanged;

        public RepaintPlanetsEvent() {  
            super(null);
            this.isFocusChanged = false;
        }
        
        public RepaintPlanetsEvent(long time, boolean isFocusChanged) {
            super(null);
            focusTime = time;
            this.isFocusChanged = isFocusChanged;
        }
        
        public boolean isFocusChanged(){
            return isFocusChanged;
        }
        
        public long getFocusedTime(){
            return focusTime;
        }
    }
        
}
