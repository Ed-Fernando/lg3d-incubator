/*
 * ºîÀ®Æü: 2005/08/15
 */
package nu.koidelab.cosmo.wg.shape;

import java.util.ArrayList;

import javax.vecmath.Color4f;
import javax.vecmath.Point3f;

import org.jdesktop.lg3d.sg.Geometry;
import org.jdesktop.lg3d.sg.LineArray;
import org.jdesktop.lg3d.sg.Shape3D;

public class GridPanel extends Shape3D{
    public GridPanel(float gridWidth, float gridHeight, int xNum, int zNum) {
        addGeometry(getGeometry(gridWidth, gridHeight, xNum, zNum));
    }

    private Geometry getGeometry(float xDim, float zDim, int xSquareNum,
            int zSquareNum) {
        ArrayList<Point3f> verticesList = new ArrayList<Point3f>();
        float xPos = -xDim * (xSquareNum - 1);
        float zPos = -zDim * (zSquareNum - 1);
        for (int i = 0; i < xSquareNum; i++) {
            for (int j = 0; j < zSquareNum; j++) {
                getSquare(verticesList, xDim, zDim, xPos + 2 * xDim * i,
                        zPos + 2 * zDim * j);
            }
        }
        Point3f[] vertices = new Point3f[verticesList.size()];
        verticesList.toArray(vertices);
        
        Color4f[] colors = new Color4f[verticesList.size()];
        Color4f clr = new Color4f(0.65f, 0.8f, 0.65f, 0.3f);
        for (int i = 0, n = colors.length; i < n; i++) {
            colors[i] = clr;
        }
        
        LineArray geometry = new LineArray(vertices.length,
                LineArray.COORDINATES | LineArray.COLOR_4);
        geometry.setCoordinates(0, vertices);
        geometry.setColors(0, colors);

        return geometry;
    }

    private void getSquare(ArrayList<Point3f> verticesList, float xSize,
            float zSize, float xPos, float zPos) {
        verticesList.add(new Point3f(-xSize + xPos, 0, zSize + zPos));
        verticesList.add(new Point3f(xSize + xPos, 0, zSize + zPos));

        verticesList.add(new Point3f(xSize + xPos, 0, zSize + zPos));
        verticesList.add(new Point3f(xSize + xPos, 0, -zSize + zPos));

        verticesList.add(new Point3f(xSize + xPos, 0, -zSize + zPos));
        verticesList.add(new Point3f(-xSize + xPos, 0, -zSize + zPos));

        verticesList.add(new Point3f(-xSize + xPos, 0, -zSize + zPos));
        verticesList.add(new Point3f(-xSize + xPos, 0, zSize + zPos));
    }
}
