/*
 * ºîÀ®Æü: 2005/08/12
 */
package nu.koidelab.cosmo;

import nu.koidelab.cosmo.manager.CSDGeneralManager;

public class CosmoSchedulerD {
    public static void main(String[] args) {
    	if(CSDGeneralManager.hasInstance())
    		return;
    	
        CSDGeneralManager.getInstance().init();
    }
}
