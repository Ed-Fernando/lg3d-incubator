package org.jdesktop.lg3d.apps.archviz3d.zparser.tools;

public class ZToken extends antlr.CommonToken {

	protected int column,line;
        public int getColumn() { return column; }
        public void setColumn(int c) { column=c; }

        public int getLine() { return line; }
        public void setLine(int c) { line=c; }


}
