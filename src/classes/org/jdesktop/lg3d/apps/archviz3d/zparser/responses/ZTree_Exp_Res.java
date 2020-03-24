package org.jdesktop.lg3d.apps.archviz3d.zparser.responses;

public class ZTree_Exp_Res {

    public String prolog;
    public String var;

    public ZTree_Exp_Res() {
	prolog="";
	var="";

    }

    public String toString() {
    return "ZTree Expression/// Prolog: " + prolog + "   Variable: " + var;
    }

}
