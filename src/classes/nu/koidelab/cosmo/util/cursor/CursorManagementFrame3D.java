package nu.koidelab.cosmo.util.cursor;

import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.wg.Frame3D;

public class CursorManagementFrame3D extends Frame3D {
	private CursorManager cursorManager;

	public CursorManagementFrame3D(){
		cursorManager = new CursorManager(this);
	}
	
	public CursorManager getCursorManager() {
		return cursorManager;
	}
	
	@Override
	public void setTranslation(float x, float y, float z) {
		move(x, y, z);
		super.setTranslation(x, y, z);
	}
	
	@Override
	public void changeTranslation(float x, float y, float z) {
		move(x, y, z);
		super.changeTranslation(x, y, z);
	}
	
	@Override
	public void changeTranslation(float x, float y, float z, int duration) {
		move(x, y, z);
		super.changeTranslation(x, y, z, duration);
	}
	
	@Override
	public void changeTranslation(Vector3f loc) {
		move(loc.x, loc.y, loc.z);
		super.changeTranslation(loc);
	}
	
	@Override
	public void changeTranslation(Vector3f loc, int duration) {
		move(loc.x, loc.y, loc.z);
		super.changeTranslation(loc, duration);
	}
	
	private void move(float x, float y, float z){
		cursorManager.frame3DMoved(x, y, z);
	}
}
