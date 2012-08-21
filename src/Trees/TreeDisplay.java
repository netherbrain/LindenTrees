/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Trees;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JOptionPane;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.*;
import org.lwjgl.util.glu.*;

/**
 * TreeDisplay displays a tree drawn within the Tree class and offers a camera
 * for viewing the drawn tree. This class also holds the current iteration
 * of a tree and is able to increment or decrement the current iteration of the
 * tree. The controls are as follows:
 * 
 * WASD: move the camera
 * Arrow Keys: rotate the camera
 * +, - : Increment and decrement current rotation
 * 
 * This program sets up things a little weird for the functionality it gives,
 * like the fact that the tree files are loaded through methods and 
 * the changed boolean is required for the initial drawing of the tree. This
 * was implemented because I was attempting to be able to change the tree's
 * bark and leaf textures while the program was running. I was not able to 
 * accomplish this completely. Once I was able to change the leaf textures on
 * the fly, but the bark texture stopped rendering all together.
 * 
 * @author Adam Nelson
 */

public class TreeDisplay {

    //width and height of the screen
    private int width = 1024;
    private int height = 768;
    //size of the width and length of the ground area
    private int area = 50;
    //Camera for viewing
    private Camera camera;
    //booleans used to control the camera
    private boolean forward, back, left, right;
    //Tree to be drawn
    private Tree tree;
    //holds the current iteration of the tree being displayed
    private int iteration = 0;
    //holds whether the tree has been changed or not
    private boolean changed = false;
    //index of the tree's display list
    private int treeDL = 0;
    private File treeFile;
    private File leafFile;
    private File barkFile;
    private boolean setTree = false;
    
    //Start up of the Display application. Initialize data and start the display
    public void start() throws LWJGLException, FileNotFoundException, IOException {
        try {
            //set the display dimensions and create the display
            Display.setDisplayMode(new DisplayMode(width, height));
            Display.create();
        } catch (LWJGLException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
        
        //instantiate the camera
        camera = new Camera();
        //enable certain OpenGL Settings
        enable();
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GLU.gluPerspective(45, width / height, 0.1f, 1000f);
        GL11.glClearColor(0.2f, 0.2f, 0.5f, 0);
        
        makeTree();
        
        //Program loop. Render objects onto screen, check for input and update
        while (!Display.isCloseRequested()) {
            display();
            pollInput();
            //limits the framerate to a maximum of 60
            Display.sync(40);
            Display.update();
        }

        //Once a close is requested, destroy the display
        Display.destroy();
    }

    //Enables OpenGL settings
    private void enable() {
        //Enable settings for lighting
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_LIGHT0);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glAlphaFunc(GL11.GL_GREATER, 0.5f);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
    }

    //Render objects to the screen
    public void display() throws LWJGLException, IOException {
        //Set perspective
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GLU.gluPerspective(45, width / height, 0.1f, 1000f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        
        //Update the camera based on current position
        camera.update();

        //Draw the ground patch
        GL11.glColor3f(0, 0.6f, 0);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex3f(0, 0, 0);
        GL11.glVertex3f(area, 0, 0);
        GL11.glVertex3f(area, 0, area);
        GL11.glVertex3f(0, 0, area);
        GL11.glEnd();
        
        //If the tree has changed, prerender it again
        if(changed){
            treeDL = GL11.glGenLists(1);
            GL11.glNewList(treeDL, GL11.GL_COMPILE);
            try{tree.draw();} 
            catch(NullPointerException e){}
            GL11.glEndList();
            changed = false;
        }
        //Otherwise, call its display list
        else{
            GL11.glCallList(treeDL);
        }
        //Flush the display buffer
        GL11.glFlush();
    }

    public void pollInput() throws LWJGLException {
        while (Keyboard.next()) {

            //If a key was pressed
            if (Keyboard.getEventKeyState()) {
                //Strafe left if A was pushed
                if (Keyboard.getEventKey() == Keyboard.KEY_A) {
                    camera.setMoveLeft(true);
                }
                //Move forward if W was pushed
                if (Keyboard.getEventKey() == Keyboard.KEY_W) {
                    camera.setMoveForward(true);
                }
                //Move backward if S was pushed
                if (Keyboard.getEventKey() == Keyboard.KEY_S) {
                    camera.setMoveBackward(true);
                }
                //Strafe right of D was pushed
                if (Keyboard.getEventKey() == Keyboard.KEY_D) {
                    camera.setMoveRight(true);
                }
                //Rotate the camera left of the left arrow was pushed
                if (Keyboard.getEventKey() == Keyboard.KEY_LEFT) {
                    camera.setRotateLeft(true);
                }
                //Rotate the camera upwards if the up arrow was pressed
                if (Keyboard.getEventKey() == Keyboard.KEY_UP) {
                    camera.setRotateUp(true);
                }
                //Rotate the camera downwards if the down arrow was pressed
                if (Keyboard.getEventKey() == Keyboard.KEY_DOWN) {
                    camera.setRotateDown(true);
                }
                //Rotate the camera right if the right arrow was pressed
                if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT) {
                    camera.setRotateRight(true);
                }

            } //If a key was released
            else {
                //Stop strafing left if A is released
                if (Keyboard.getEventKey() == Keyboard.KEY_A) {
                    camera.setMoveLeft(false);
                }
                //Stop moving forward if W is released
                if (Keyboard.getEventKey() == Keyboard.KEY_W) {
                    camera.setMoveForward(false);
                }
                //Stop moving backward if S is released
                if (Keyboard.getEventKey() == Keyboard.KEY_S) {
                    camera.setMoveBackward(false);
                }
                //Stop strafing right if D is released
                if (Keyboard.getEventKey() == Keyboard.KEY_D) {
                    camera.setMoveRight(false);
                }
                //Stop rotating left if left arrow is released
                if (Keyboard.getEventKey() == Keyboard.KEY_LEFT) {
                    camera.setRotateLeft(false);
                }
                //Stop rotating upward if up arrow is released
                if (Keyboard.getEventKey() == Keyboard.KEY_UP) {
                    camera.setRotateUp(false);
                }
                //Stop rotating downward if down arrow is released
                if (Keyboard.getEventKey() == Keyboard.KEY_DOWN) {
                    camera.setRotateDown(false);
                }
                //Stop rotating right if right arrow is released
                if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT) {
                    camera.setRotateRight(false);
                }
                //Increment the current rotation
                if (Keyboard.getEventKey() == Keyboard.KEY_ADD) {
                    setIteration(iteration+1);
                }
                //Increment the current rotation
                if (Keyboard.getEventKey() == Keyboard.KEY_SUBTRACT) {
                    setIteration(iteration-1);
                }
            }
        }
    }
    //set the iteration of the tree
    public void setIteration(int i){
        //don't change the iteration if the iteration hasn't actually changed.
        //That would just be wasted work to redraw the tree at the same iteration
        if(i != iteration){
            //set the iteration of the tree within the tree itself
            tree.setIteration(i);
            //change value of iteration held in TreeDisplay
            iteration = i;
            //because tree iteration has changed, the tree has changed
            changed = true;
        }
    }
    
    //Sets the tree file to make the tree from
    public void setTreeFile(File file){
        treeFile = file;
    }
    
    //Sets the bark image file to make the tree from
    public void setBarkTexture(File file){
        barkFile = file;
    }
    
    //Sets the leaf image file to make the tree from
    public void setLeafTexture(File file){
        leafFile = file;
    }
    
    //Makes the tree from a tree file
    public void makeTree() throws FileNotFoundException, IOException{
        if(treeFile != null && barkFile != null && leafFile != null){
            tree = new Tree(treeFile, barkFile, leafFile, area/2, area/2);
        }
        changed = true;
    }
    
    public static void main(String[] args) throws LWJGLException, FileNotFoundException, IOException {
        TreeDisplay treeDisplay = new TreeDisplay();
        treeDisplay.start();
    }
}