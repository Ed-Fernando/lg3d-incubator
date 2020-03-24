package org.jdesktop.lg3d.apps.physics;
import javax.vecmath.*;
import org.jdesktop.lg3d.wg.Component3D;
import java.util.Vector;
import java.util.PriorityQueue;
import java.util.Comparator;
/**
 * Used to represent a rigid body box in physics simulation.
 *
 */

public class PhysicsBox extends PhysicsBody implements Collideable{
    
    
    /**
     * Class used to represent an overlap between two boxes.
     * Used to sort the overlaps by their depth.
     */
    
    private class DepthPair {
        public float depth;
        public int id;
        
        public DepthPair(float depth, int id) {
            this.depth=depth;
            this.id=id;
        }
    }
    
    /**
     * Class for sorting DepthPairs by their depths.
     **/
    private class DepthPairComparator implements Comparator<DepthPair> {
        public int compare(DepthPair pair1, DepthPair pair2) {
            if (pair1.depth<pair2.depth)
                return -1;
            else 
                return 1;
        }
    }
    
    
    private float[] halfEdges; //boxes dimensions halved. 
    
    /**
     * Creates a new instance of the PhyusicsBox class. 
     * Creates a rigid body box to use in a physics simulation. The box position and attitude will be used to animate the given Component3D.
     * @param xdim the size in X-dimension.
     * @param ydim the size in Y-dimension.
     * @param zdim the size in Z-dimension.
     * @param mass the mass of the box in kilograms.
     * @param position the starting centerpoint of the box.
     * @param attitude the orientation of the box expressed as a Quaternion, will be normalized by constructor.
     * @param restitution the coefficient of restitution used to model the elasticity of the box.
     * @param friction the coefficient of restitution used to model the elasticity of the box.
     * @param graphicalObject the Component3D that will be animated by the box.
     *
     */ 
    public PhysicsBox(float xdim, float ydim, float zdim, 
            float mass, Vector3f position, Quat4f attitude, Component3D graphicalObject, Integrator integrator, float restitution ,float friction) {
        super();
        this.mass=mass;
        invMass=1.0f/mass;
        this.position.set(position);
        halfEdges=new float[3];
        halfEdges[0]=xdim;
        halfEdges[1]=ydim;
        halfEdges[2]=zdim;
        
        
        //Calculate the box iverted inertia from its dimensions and mass 
        float x2=xdim*xdim*4.0f;
        float y2=ydim*ydim*4.0f;
        float z2=zdim*zdim*4.0f;
        float c=mass/12.0f;
        
        inertiaInverse.setRow(0, 1.0f/(c*(y2+z2)),     0,                    0);
        inertiaInverse.setRow(1,  0,                  1.0f/(c*(x2+z2)),      0);
        inertiaInverse.setRow(2,  0,                   0,                  1.0f/(c*(x2+y2)) );
        
        
        this.setAttitude(attitude);
        this.graphicalObject=graphicalObject;
        
        updateGraphics();
        this.integrator=integrator;
        this.friction=friction;
        this.restitution=restitution;
    } 
    
    void update (){
         graphicalObject.setTranslation(position.x,position.y,position.z);
        AxisAngle4f newAngle=new AxisAngle4f();
        newAngle.set(attitude);
        graphicalObject.setRotationAxis(newAngle.x,newAngle.y,newAngle.z);
        graphicalObject.setRotationAngle(newAngle.angle);
    }
    
    /**
     * Returns an array of the box's corners in world coordinates.
     *
     * @return an array of vectors with the box's corners in world coordinates.
     */
    public Vector3f[] getCorners() {
        Vector3f[] corners=new Vector3f[8];
        corners[0]=new Vector3f(halfEdges[0],halfEdges[1], halfEdges[2]);
        corners[1]=new Vector3f(-halfEdges[0],halfEdges[1], halfEdges[2]);
        corners[2]=new Vector3f(halfEdges[0],-halfEdges[1], halfEdges[2]);
        corners[3]=new Vector3f(-halfEdges[0],-halfEdges[1], halfEdges[2]);
        corners[4]=new Vector3f(halfEdges[0],halfEdges[1], -halfEdges[2]);
        corners[5]=new Vector3f(-halfEdges[0],halfEdges[1], -halfEdges[2]);
        corners[6]=new Vector3f(halfEdges[0],-halfEdges[1], -halfEdges[2]);
        corners[7]=new Vector3f(-halfEdges[0],-halfEdges[1], -halfEdges[2]);
        
        //Now we have all corners for a box with no rotation and center at the origin.
  
        Quat4f conjugate=new Quat4f();
        conjugate.conjugate(attitude);
        Quat4f point=new Quat4f();
        Quat4f transformed=new Quat4f();
     
        for(int i=0;i<8;i++) {
            
            point.set(corners[i].x,corners[i].y, corners[i].z, 0.0f);
            transformed.mul(attitude, point);
            transformed.mul(conjugate);
            corners[i].set(transformed.x, transformed.y, transformed.z);
            corners[i].add(position);
        }
            
        return corners;
        
    }
    

    
 
    
    public void collide(Collideable c, Vector<Contact> contacts) {
        c.collideBox(this, contacts);
    }
    /**
     * Calculates the normals for the box's planes.
     * Will write three normals into the supplied array of vectors. 
     * Note that even though a box has six sides they are pairwies parallell and hence only have three 
     * non parallell normals.
     *
     * @param normals an array of vectors with at least three spaces.
     */
    
    public void getNormals(Vector3f[] normals) {
        Quat4f normal=new Quat4f();
        Quat4f conjugate=new Quat4f();
        conjugate.conjugate(attitude);
        
        normal.set(1.0f,0.0f,0.0f,0.0f);
        normal.mul(attitude, normal);
        normal.mul(conjugate);
        normals[0]=new Vector3f(normal.x, normal.y,normal.z);
        
        normal.set(0.0f,1.0f,0.0f,0.0f);
        normal.mul(attitude, normal);
        normal.mul(conjugate);
        normals[1]=new Vector3f(normal.x, normal.y,normal.z);
        
        normal.set(0.0f,0.0f,1.0f,0.0f);
        normal.mul(attitude, normal);
        normal.mul(conjugate);
        normals[2]=new Vector3f(normal.x, normal.y,normal.z);
       
      
    }
    /**
     * Tries to find a separating plane between this box and the given one.
     * If none is found there is an overlap along the normal with the least overlap.
     * If an overlap is found the collision normal is returned, otherwise null is returned.
     * @return the collision normal if there is a collision, null otherwise.
     *
     */
    
    public Vector3f getCollisionNormal(PhysicsBox box) {
        Vector3f[] thisNormals=new Vector3f[3];
        Vector3f[] boxNormals=new Vector3f[3];
        Vector3f[] crossNormals=new Vector3f[9];
        
        Vector3f[] thisCorners=getCorners();
        Vector3f[] boxCorners=box.getCorners();
        
        getNormals(thisNormals);
        box.getNormals(boxNormals);
        for(int i=0;i<9;i++) {
            crossNormals[i]=new Vector3f();
            crossNormals[i].cross(thisNormals[i/3],boxNormals[i%3]);
        }
        
        DepthPair[] normals=new DepthPair[15];
        DepthPair[] corners=new DepthPair[16];
        
        
        for(int i=0;i<15;i++) {
            normals[i]=new DepthPair(1.0f,-1);
            
            corners[i]=new DepthPair(1.0f ,-1);
        }
        corners[15]=new DepthPair(1.0f,-1);
        
        Vector3f currentNormal;
        for(int i=0;i<15;i++) {
            if(i<3)
                currentNormal=thisNormals[i];
            else if (i<6)
                currentNormal=boxNormals[i-3];
            else{
                /* before we start with the cross product normals we check if any of the other ones fit well enough */    
                if (i==6) {
                        java.util.Arrays.sort(normals, new DepthPairComparator());
                        if (normals[0].depth<0.002) {
                            if (normals[0].id<3)
                                return thisNormals[normals[0].id];
                            else
                                return boxNormals[normals[0].id-3];
                        }
                }
                            
                           
                            
            
                currentNormal=crossNormals[i-6];
            }
            //Project all corners onto the current normal
            for(int j=0;j<8;j++) {
                corners[j].depth=thisCorners[j].dot(currentNormal);
                corners[j].id=0;
            }
            for(int j=0;j<8;j++) {
                corners[j+8].depth=boxCorners[j].dot(currentNormal);
                corners[j+8].id=1;
            }
            
            
            java.util.Arrays.sort(corners, new DepthPairComparator());
            //check for overlap
            int[] first=new int[2];
            int[] last=new int[2];
            
            first[0]=first[1]=-1;
            last[0]=last[1]=-1;
            
            
            for(int j=0;j<16;j++) {
                if(first[corners[j].id]==-1)
                    first[corners[j].id]=j;
                last[corners[j].id]=j;
            }
            
            if(last[0] < first[1] || last[1] <first[0])  {
              
                return null; //found separating plane, no collision
                
            }
            normals[i].id=i;
            normals[i].depth=corners[last[0]].depth-corners[first[1]].depth;
            if( normals[i].depth >  corners[last[1]].depth-corners[first[0]].depth)
                normals[i].depth=corners[last[1]].depth-corners[first[0]].depth;
            
            
        }
        //find the least overlapping normal, if that normal is not the zero vector it's our collision normal
        java.util.Arrays.sort(normals,new DepthPairComparator());
       
        int id=0;
        while(true) {
            if(normals[id].id<3) {
              
                return thisNormals[normals[id].id];
            }
            if(normals[id].id<6)
                return boxNormals[normals[id].id-3];
            if(crossNormals[normals[id].id-6].lengthSquared()>0.01)
                return crossNormals[normals[id].id-6];
            else 
                id++;
        }
        
    }
    
    /**
     * Checks this box for collision with another box and stores any contacts in the given Vector.
     * 
     * @param box the box to check collision with.
     * @param contacts the Vector in which any found contacts will be stored.
     */
    
    public void collideBox(PhysicsBox box, Vector<Contact> contacts) {
        Vector3f normal=getCollisionNormal(box);
        boolean[] leftFlags=new boolean[8];
        boolean[] rightFlags=new boolean[8];
        
        if(normal!=null) {
          
           PhysicsBox leftBox=this;
           PhysicsBox rightBox=box;
           
           if(normal.dot(position)<normal.dot(box.getPosition())) {
                rightBox=this;
                leftBox=box;
           }
           Vector3f[] leftCorners=leftBox.getCorners();
           Vector3f[] rightCorners=rightBox.getCorners();
           BoxPlane[] leftPlanes=leftBox.getPlanesWithNormal(normal);
           BoxPlane[] rightPlanes=rightBox.getPlanesWithNormal(normal);
           float depth;
           for(int i=0;i<8;i++) {
               leftFlags[i]=false;
               rightFlags[i]=false;
               if(rightPlanes!=null) {
                    
                    depth=rightPlanes[0].penetratedBy(leftCorners[i]);
                    if(depth>=0.0f) {
                        contacts.add(new Contact(leftBox, rightBox, normal,leftCorners[i],depth));
                        leftFlags[i]=true;
                    }
                    else {
                        depth=rightPlanes[1].penetratedBy(leftCorners[i]);
                        if(depth>=0.0f) {
                            contacts.add(new Contact(leftBox, rightBox,normal,leftCorners[i],depth));
                            leftFlags[i]=true;
                        }
                    }
               }
                if(leftPlanes!=null) {
                    depth=leftPlanes[0].penetratedBy(rightCorners[i]);
                    if(depth>=0.0f)  {
                        contacts.add(new Contact(leftBox, rightBox, normal,rightCorners[i],depth));
                        rightFlags[i]=true;
                    }
                    else {
                        depth=leftPlanes[1].penetratedBy(rightCorners[i]);
                        if(depth>=0.0f) {
                            contacts.add(new Contact(leftBox, rightBox,normal,rightCorners[i],depth));    
                            rightFlags[i]=true;
                        }
               
                    }
                }
            }
           
            //Start testeing edge/edge contacts
            
           // set up a 2d array with the corner-ids of an edge stored on a row.
           int[][] edgeCorners=new int[12][2];
            
           edgeCorners[0][0]=0;
           edgeCorners[0][1]=1;
           edgeCorners[1][0]=0;
           edgeCorners[1][1]=2;
           edgeCorners[2][0]=0;
           edgeCorners[2][1]=4;
           edgeCorners[3][0]=1;
           edgeCorners[3][1]=3;
           edgeCorners[4][0]=1;
           edgeCorners[4][1]=5;
           edgeCorners[5][0]=2;
           edgeCorners[5][1]=3;
           edgeCorners[6][0]=2;
           edgeCorners[6][1]=6;
           edgeCorners[7][0]=3;
           edgeCorners[7][1]=7;
           edgeCorners[8][0]=4;
           edgeCorners[8][1]=5;
           edgeCorners[9][0]=4;
           edgeCorners[9][1]=6;
           edgeCorners[10][0]=5;
           edgeCorners[10][1]=7;
           edgeCorners[11][0]=6;
           edgeCorners[11][1]=7;
           
           Vector3f leftEdge=new Vector3f();
           Vector3f rightEdge=new Vector3f();
           Vector3f leftEdgeNormalized = new Vector3f();
           Vector3f rightEdgeNormalized = new Vector3f();
           Vector3f distance = new Vector3f(); 
           
           for(int i=0;i<12;i++) {
               leftEdge.sub(leftCorners[edgeCorners[i][0]], leftCorners[edgeCorners[i][1]]);
               leftEdgeNormalized.normalize(leftEdge);
               if(java.lang.StrictMath.abs(leftEdgeNormalized.dot(normal))<0.01f &&
                       (!rightFlags[edgeCorners[i][0]] || !rightFlags[edgeCorners[i][1]] )) {
                   
                    for(int j=0;j<12;j++) {
                        rightEdge.sub(rightCorners[edgeCorners[j][0]], rightCorners[edgeCorners[j][1]]);
                        rightEdgeNormalized.normalize(rightEdge);
                        if(java.lang.StrictMath.abs(rightEdgeNormalized.dot(normal))<0.01f && 
                                (!rightFlags[edgeCorners[j][0]] || !rightFlags[edgeCorners[j][1]] ) ) {
                   
                            distance.sub(rightCorners[edgeCorners[j][0]],leftCorners[edgeCorners[i][0]]);
                            if(java.lang.StrictMath.abs(distance.dot(normal))<0.0001f) {
                                Vector3f impactPoint=new Vector3f();
                                Vector3f thirdAxis=new Vector3f();
                                thirdAxis.cross(leftEdgeNormalized, normal);
                                
                                Matrix3f axisMatrix=new Matrix3f();
                                axisMatrix.setColumn(0,leftEdgeNormalized);
                                axisMatrix.setColumn(1, thirdAxis);
                                axisMatrix.setColumn(2, normal);
                                
                                Matrix3f invertAxis=new Matrix3f();
                                invertAxis.invert(axisMatrix);
                                
                                Vector3f boxPoint1=new Vector3f(rightCorners[edgeCorners[j][0]]);
                                Vector3f boxPoint2=new Vector3f(rightCorners[edgeCorners[j][1]]);
                                
                                Vector3f thisPoint1=new Vector3f(leftCorners[edgeCorners[i][0]]);
                                Vector3f thisPoint2=new Vector3f(leftCorners[edgeCorners[i][1]]);
                                
                                invertAxis.transform(boxPoint1);
                                invertAxis.transform(boxPoint2);
                                invertAxis.transform(thisPoint1);
                                invertAxis.transform(thisPoint2);
                                
                                float x=((boxPoint2.x - boxPoint1.x) * (thisPoint1.y - boxPoint1.y) - (boxPoint2.y - boxPoint2.y) * (thisPoint1.x - boxPoint1.x) 
                                                                      )  /  (
                                         (boxPoint2.y - boxPoint1.y) * (thisPoint2.x - thisPoint1.x) - (boxPoint2.x - boxPoint1.x ) * (thisPoint2.y - thisPoint1.y)  );
                                
                                float y=((thisPoint2.x - thisPoint1.x) * (thisPoint1.y - boxPoint1.y) - (thisPoint2.y - thisPoint1.y) * (thisPoint1.x - boxPoint1.x)
                                                                      )  /  (
                                        (boxPoint2.y - boxPoint1.y) * (thisPoint2.x - thisPoint1.x) - (boxPoint2.x - boxPoint1.x) * (thisPoint2.y - thisPoint1.y)); 
                                
                                impactPoint.set(thisPoint1.x+x*(thisPoint2.x - thisPoint1.x), thisPoint1.y + y*(thisPoint2.y - thisPoint1.y), thisPoint1.z);
                                axisMatrix.transform(impactPoint);
                                contacts.add(new Contact(leftBox , rightBox, normal, impactPoint,java.lang.StrictMath.abs(distance.dot(normal))));
                              
                            }
                            
                        }
                    }
                }    
           }
            
            
           
        }
    
    }
    
     /**
      * Checks this box for collision with a rigid body sphere.
      * This box is checked for overlap with a rigid sphere, any contacts found are stored in the supplied Vector. 
      * @param sphere the RigidSphere to check collision with.
      * @param contacts the Vector in which to store any contacts found.
      */
    
    public void collideSphere(PhysicsSphere sphere, Vector<Contact> contacts) {
        //Rotate the sphere and the box so the box becomes axis aligned.
        //In the box's case this is easy, just don't rotate it with it's attitude. 
        //The sphere will be rotated with the attitudes inverse.
      
        Vector3f sphereCenter=sphere.getPosition();
        if( attitude.w>0.0000001f){
            Matrix3f transformation=new Matrix3f();
            transformation.set(attitude);
            transformation.invert();
            
            transformation.transform(sphereCenter);
        }
        float[] distance=new float[3];
        distance[0]=sphereCenter.x-position.x;
        distance[1]=sphereCenter.y-position.y;
        distance[2]=sphereCenter.z-position.z;
        
        boolean[] overlapAtAxis=new boolean[3];
        overlapAtAxis[0]=false;
        overlapAtAxis[1]=false;
        overlapAtAxis[2]=false;
        
        
        Vector3f[] axis=new Vector3f[3];
        axis[0]=new Vector3f(1.0f,0.0f,0.0f);
        axis[1]=new Vector3f(0.0f,1.0f,0.0f);
        axis[2]=new Vector3f(0.0f,0.0f,1.0f);
        
        float distanceSquareSum=0.0f;
        float bodySquareSum=0.0f;
        float[] depths=new float[3];
        
        float least=900.0f;
        int leastId=-1;
       
        
        //Now check for overlap along each axis
        for(int i=0;i<3;i++) {
            float abs;
            abs=java.lang.StrictMath.abs(distance[i])-halfEdges[i]-sphere.getRadius();
            
            if(abs<=0.0f) {
                overlapAtAxis[i]=true;
                //depthsSum+=(abs*abs);
                //foundOverlap=true;   
                depths[i]=-abs;
                if (-abs<least) {
                    least=-abs;
                    leastId=i;
                }
            }
            
        }
        
        if(overlapAtAxis[0] && overlapAtAxis[1] && overlapAtAxis[2]) {
            Vector3f collisionNormal=new Vector3f();
            for(int i=0;i<3;i++) {
                int id;
                id=(leastId+i)%3;
                if (depths[id]-least < 0.00001f) {
                    bodySquareSum+=halfEdges[id]*halfEdges[id];
                    distanceSquareSum+=distance[id]*distance[id];        
                    if(distance[id]>0)
                        collisionNormal.sub(axis[id]);
                    else
                        collisionNormal.add(axis[id]);
                }    
            }
            
            //Do the bodies realy touch?
            if(java.lang.StrictMath.sqrt(distanceSquareSum)-java.lang.StrictMath.sqrt(bodySquareSum)<=sphere.getRadius() ) {
                //Calculate depth and normal
       
                collisionNormal.normalize();
                if(attitude.w>0.00001) {
                    Quat4f normalQuat=new Quat4f();
                    Quat4f conjugate=new Quat4f();
                    normalQuat.set(collisionNormal.x,collisionNormal.y,collisionNormal.z, 0.0f);
                    attitude.mul(normalQuat,normalQuat);
                    conjugate.conjugate(attitude);
                    normalQuat.mul(conjugate);
                    collisionNormal.set(normalQuat.x,normalQuat.y, normalQuat.z);
                }
                Vector3f collisionPoint=new Vector3f();
                collisionPoint.scale(sphere.getRadius()-least/2.0f, collisionNormal);
                collisionPoint.add(sphereCenter);
                
                contacts.add(new Contact(this,sphere,collisionNormal,collisionPoint,least));            
            }
        }
            
        
    }
        
    /**
     *  Checks this box for collision against a general convex polyhedra. 
     *  NOT IMPLEMENTED YET!
     *
     * @param polyhedra the convex polyhedra to check for overlap with.
     * @param contacts the vector in which the contacts found will be stored.
     *
     */
     
     public void collideConvexPolyhedra(ConvexPolyhedra polyhedra, Vector<Contact> contacts) {
     
     }
     
    /**
      * Checks this box for collision with a static unbounded plane.
      * This box is checked for overlap with an unbounded plane, any contacts found are stored in the supplied Vector. 
      * @param plane the plane to preform the collision check with.
      * @param contacts the Vector in which to store any contacts found.
      */
     public void collidePlane(PhysicsPlane plane, Vector<Contact> contacts) {
        Vector3f normal=plane.getNormal();
      
        Vector3f[] corners=getCorners();
        float dot=normal.dot(plane.getPosition());
        PriorityQueue<Contact> pq=new PriorityQueue(8,new ContactComparator());
        
        for(int i=0;i<8;i++) {
            if(corners[i].dot(normal)-dot<0.0f) {
                Contact c=new Contact(this, plane,normal,corners[i],-(corners[i].dot(normal)-dot));
              
                pq.add(c);
            }
        }
           
        if (!pq.isEmpty()){
            Contact[] found;
            found=pq.toArray(new Contact[1]);
            java.util.Arrays.sort(found, new ContactComparator());
            contacts.addAll(java.util.Arrays.asList(found));
        }
    }
   
  
   

    /**
     * Tries to find two planes on this box with normals parallel to the given vector.
     * If two planes are found that are close enough to bieng parallel with the given vector, they are returned.
     * If no planes are found null is returned.
     * 
     * @return an array of two planes if any ary found, null otherwise.
     */
    
    public BoxPlane[] getPlanesWithNormal(Vector3f normal) {
        Vector3f[] corners=getCorners();
        
        Vector3f edge1=new Vector3f();
        Vector3f edge2=new Vector3f();
        Vector3f planeNormal=new Vector3f();
        BoxPlane[] planes=new BoxPlane[2];
        
        edge1.sub(corners[1],corners[0]);
        edge2.sub(corners[4],corners[0]);
        planeNormal.cross(edge1,edge2);
        planeNormal.normalize();
   
        if(java.lang.StrictMath.abs(normal.dot(planeNormal))>0.7) {
            planeNormal.scale(-1.0f);
            
            planes[0]=new BoxPlane(planeNormal, corners[0], edge1, edge2, halfEdges[1]);
            planeNormal.scale(-1.0f);
            
            planes[1]=new BoxPlane(planeNormal, corners[2], edge1, edge2, halfEdges[1]);
            return planes;
        }         
        
        edge1.sub(corners[2],corners[0]);
        edge2.sub(corners[4],corners[0]);
        planeNormal.cross(edge1,edge2);
        planeNormal.normalize();

        if(java.lang.StrictMath.abs(normal.dot(planeNormal))>0.7) {
            planeNormal.scale(-1.0f);
            
            planes[0]=new BoxPlane(planeNormal, corners[1], edge1, edge2, halfEdges[0]);
            planeNormal.scale(-1.0f);
            planes[1]=new BoxPlane(planeNormal, corners[0], edge1, edge2, halfEdges[0]);
            return planes;
        }
        
        edge1.sub(corners[2],corners[0]);
        edge2.sub(corners[1],corners[0]);
        planeNormal.cross(edge1,edge2);
        planeNormal.normalize();
        if(java.lang.StrictMath.abs(normal.dot(planeNormal))>0.7) {
            planeNormal.scale(-1.0f);
            
            planes[0]=new BoxPlane(planeNormal, corners[0], edge1, edge2, halfEdges[2]);
            planeNormal.scale(-1.0f);
            planes[1]=new BoxPlane(planeNormal, corners[4], edge1, edge2, halfEdges[2]);
            return planes;
        }
        return null;
        
        
    } 
    
    /**
     * Returns a copy of the array of half-edge lengths.
     * 
     * @return the half-edge lengths of the box in an array.
     */
    
     public float[] getHalfEdges() {
        float[] array=new float[3];
        
        array[0]=halfEdges[0];
        array[1]=halfEdges[1];
        array[2]=halfEdges[2];
        
        return array;
     }
}
