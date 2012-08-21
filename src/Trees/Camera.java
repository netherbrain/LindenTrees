/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Trees;

import org.lwjgl.opengl.GL11;

/**
 * Camera for the Trees program
 * @author Adam
 */
public class Camera {
    
    //A tuple to hold the camera's position
    Tuple pos;
    //Holds the x and z rotation
    private float xrot, yrot;
    //Holds the x and z rotation in radians
    private float xrotrad, yrotrad;
    //Holds the speed at which the camera moves
    private float moveSpeed;
    //booleans to hold whether the camera moves forward, back, left and right
    private boolean moveF, moveB, moveL, moveR;
    //booleans to hold whether the camera rotates up, down, left and right
    private boolean rotU, rotD, rotL, rotR;
    
    public Camera(){
        pos = new Tuple(0,-1,0);
        xrot = 0;
        yrot = -224;
        moveSpeed = 0.5f;
        moveF = moveB = moveL = moveR = false;
        rotU = rotD = rotL = rotR = false;
    }
    
    public void update(){
        //Check if the camera should move
        if(moveF)
            moveForward();
        if(moveB)
            moveBackward();
        if(moveL)
            strafeLeft();
        if(moveR)
            strafeRight();
        if(rotU)
            rotateUp();
        if(rotD)
            rotateDown();
        if(rotR)
            rotateRight();
        if(rotL)
            rotateLeft();
        
        //Rotate and translate camera based on current rotation and position
        GL11.glRotatef(xrot, 1f, 0, 0);
        GL11.glRotatef(yrot, 0, 1f, 0);
        GL11.glTranslatef(pos.x, pos.y, pos.z);
    }
    
    //The next eight methods set whether the camera should move and rotate or not
    public void setMoveForward(boolean b){
        moveF = b;
    }
    
    public void setMoveBackward(boolean b){
        moveB = b;
    }
    
    public void setMoveRight(boolean b){
        moveR = b;
    }
    
    public void setMoveLeft(boolean b){
        moveL = b;
    }
    
    public void setRotateUp(boolean b){
        rotU = b;
    }
    
    public void setRotateDown(boolean b){
        rotD = b;
    }
    
    public void setRotateRight(boolean b){
        rotR = b;
    }
    
    public void setRotateLeft(boolean b){
        rotL = b;
    }
    
    /*
     * The strafe and move methods following this comment were taken from a 
     * tutorial by swiftless of swiftless.com 
     * 
     * The are the same ones used in my last program
     */
    public void strafeLeft(){
        yrotrad = (yrot / 180 * 3.141592654f);
        pos.x += (float)(Math.cos(yrotrad)) * 0.5;
	pos.z += (float)(Math.sin(yrotrad)) * 0.5;
    }
    
    public void strafeRight(){
        yrotrad = (yrot / 180 * 3.141592654f);
        pos.x -= (float)(Math.cos(yrotrad)) * 0.5;
	pos.z -= (float)(Math.sin(yrotrad)) * 0.5;
    }
    
    public void moveForward(){
        yrotrad = (yrot / 180 * 3.141592654f);
	xrotrad = (xrot / 180 * 3.141592654f);
	pos.x -= (float)(Math.sin(yrotrad))*0.5;
	pos.y += (float)(Math.sin(xrotrad))*0.5;
	pos.z += (float)(Math.cos(yrotrad))*0.5;
    }
    
    public void moveBackward(){
        yrotrad = (yrot / 180 * 3.141592654f);
	xrotrad = (xrot / 180 * 3.141592654f);
	pos.x += (float)(Math.sin(yrotrad))*0.5;
	pos.y -= (float)(Math.sin(xrotrad))*0.5;
	pos.z -= (float)(Math.cos(yrotrad))*0.5;
    }
    
    public void rotateRight(){
        yrot += 2;
	if(yrot > 360)
            yrot -= 360;
    }
    
    public void rotateLeft(){
        yrot -= 2; 
	if(yrot < -360)
            yrot += 360;
    }
    
    public void rotateUp(){
        xrot -= 2;
	if(xrot < -360)
            xrot += 360;
    }
    
    public void rotateDown(){
        xrot += 1;
	if(xrot > 360)
            xrot -= 360;
    }
    
    //Pretty much a C++ struct to hold an x, y and z to indicate position
    private class Tuple{
        float x, y, z;
        public Tuple(float ex, float why, float zee){
            x = ex;
            y = why;
            z = zee;
        }
    }
}
