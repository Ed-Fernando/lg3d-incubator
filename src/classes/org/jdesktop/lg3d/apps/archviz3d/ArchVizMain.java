package org.jdesktop.lg3d.apps.archviz3d;

public class ArchVizMain {

	static {
		// seteo de paths de LG3D
		System.setProperty("lg.displayconfigurl", "file:../LG3D/etc/lg3d/displayconfig/j3d1x1");
		System.setProperty("lg.resourcedir", "../LG3D/resources/");
		System.setProperty("lg.etcdir", "../LG3D/etc/");
		System.setProperty("lg.dir", "../LG3D");
		System.setProperty("j3d.sortShape3DBounds", "true");
		System.setProperty("lg.configurl", "file:../LG3D/etc/lg3d/lgconfig_1p_nox.xml");
		System.setProperty("lg.use3dtoolkit", "true");
		System.setProperty("lg.maxfps", "30");
		System.setProperty("lg.appcodebase", "file:../LG3D/ext/app");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// ejecuto el main
		org.jdesktop.lg3d.displayserver.Main.main(args);
	}

}
