package org.jdesktop.lg3d.apps.physics;

import javax.vecmath.Vector3f;

/**
 *  Class used to represent a plane on a rigid box in the physics engine.
 *  Used during the generation of the contact set.
 **/

public class BoxPlane {
    public Vector3f normal;
    public Vector3f corner;
    public Vector3f edge1;
    public Vector3f edge2;
    public float maxDepth;
    
    
    /**
     * Constructs a new BoxPlane object.
     * @param normal the normal of the plane, pointing outwards from the box.
     * @param corner a corner of the box plane.
     * @param edge1 an edge of the box. pointing from the corner.
     * @param edge2 an edge of the box. pointing from the corner.
     * @param maxDepth the maximum penetration depth that can exist and still be considered a penetration of this plane.
     *
     **/
    
    public BoxPlane(Vector3f normal, Vector3f corner, Vector3f edge1, Vector3f edge2, float maxDepth){
        this.normal=new Vector3f(normal);
        this.corner=new Vector3f(corner);
        this.edge1=new Vector3f(edge1);
        this.edge2=new Vector3f(edge2);
        this.maxDepth=maxDepth;
    }
    
    /**
     * Checks the given point for penetration against this plane.
     * @return the penetration depth if a penetration is found, -1 otherwise.
     **/
    
    public float penetratedBy(Vector3f point) {
        float depth=-(normal.dot(point)-normal.dot(corner));
        Vector3f p=new Vector3f();
        p.sub(point,corner);
        
        if(depth<maxDepth && depth>=0.0f) {
             if(p.dot(edge1)/edge1.lengthSquared() >= 0.0f && p.dot(edge1)/edge1.lengthSquared() <= 1.0f) 
                  return depth;
       }
        
       return -1.0f;
    }
}
