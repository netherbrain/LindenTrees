/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Trees;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.glu.Cylinder;
import org.lwjgl.util.glu.Sphere;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

/**
 * This class holds all of the data to draw a tree, as well as actually draws
 * the trees onto the screen.
 * 
 * @author Adam
 */
public class Tree {
    
    //This L-System holds the rules for drawing, as well as methods to
    //expand on the rules of drawing
    private LSystem system;
    //Stack to hold radii. Stack is neccessary because if there are multiple 
    //branches off of a single parent branch, then one of those branches will
    //be draw completely before another full branch
    private Stack<Float> radii;
    //The amount that the radius is reduced by every level
    private float radReduction;
    //Length of each branch
    private float branchLength;
    private float branchReduction;
    //angles to indicate rotate for drawing branches
    private float xAngle;
    private float yAngle;
    private float zAngle;
    //x and z values to indicate location of base of tree on XZ plane
    private float xLocation;
    private float zLocation;
    //0 = radius, 1 = branchLength
    private float[] initialValues;
    private Stack<Float> branchStack;
    private static final Cylinder cylinder = new Cylinder();
    private static final Sphere sphere = new Sphere();
    private Texture barkTexture;
    private Texture leafTexture;
    private float leafSize = 0.6f;
    private int iteration;
    
    //Constructor for the tree
    //Takes values which define the tree
    public Tree(LSystem sys, float radius, float radReduce, float branchLen, float branchReduction, float locX, float locZ, float x, float y, float z) throws IOException{
        init(sys, radius, radReduce, branchLen, branchReduction, locX, locZ, x, y, z);
    }

    //Constructor to create a tree from a file
    public Tree(File tree, File bark, File leaf, float locX, float locZ) throws FileNotFoundException, IOException{
        //Set up scanner to read from file
        Scanner in = new Scanner(tree);
        //used to hold the next line in the file
        String nextLine = "";
        //Variables to hold read in data to create the tree
        RuleMap rules = new RuleMap();
        LSystem sys = null;
        float radius = 0;
        float radReduce = 0;
        float branchLen = 0;
        float branchReduce = 0;
        float x = 0;
        float y = 0;
        float z = 0;
        
        //While there is more of the file to read from
        while(in.hasNext()){
            //Grab the next line and remove leading whitespace
            nextLine = in.nextLine().trim();
            //Delete any newline character from the string
            nextLine.replaceAll("[\n]", "");
            
            /* Checks if there is a rule to read in, as there may be more than one
             * If there is a single, alphabetical letter, then read that letter
             * in to use as the identifier of the rule. The following line will
             * then be read in, which will contain the definition of the rule.
             * This will continue until there is no longer a single letter
             * defined
             */
            if(nextLine.matches("[^"+RuleMap.TERMINAL_REGEX+"]")){
                System.out.println(nextLine);
                rules.addRule(nextLine.charAt(0), in.nextLine());
            }
            //When the rules run out, read the following data which will define
            //the other attributes of the tree
            else{
                sys = new LSystem(nextLine, rules);
                radius = Float.parseFloat(in.nextLine());
                radReduce = Float.parseFloat(in.nextLine());
                branchLen = Float.parseFloat(in.nextLine());
                branchReduce = Float.parseFloat(in.nextLine());
                x = Float.parseFloat(in.nextLine());
                y = Float.parseFloat(in.nextLine());
                z = Float.parseFloat(in.nextLine());
            }
        } 
        
        this.setBarkTexture(bark);
        this.setLeafTexture(leaf);
        //bark = TextureLoader.getTexture("JPG", new FileInputStream(new File("images/bark_004.jpg")));
        //After reading in all the data, initialize the attributes of the tree
        //so that the tree may be created
        init(sys, radius, radReduce, branchLen, branchReduce, locX, locZ, x, y, z);
    }
    
    /*
     * Initializes all the attributes of the tree with the data that is given
     * to the method. This method is used by both constructors, but designed
     * for use with the constructor which takes a file name
     */
    private void init(LSystem sys, float radius, float radReduce, float branchLen, float branchReduce, float locX, float locZ, float x, float y, float z) throws FileNotFoundException, IOException{
        system = sys;
        radii = new Stack<Float>();
        radii.push(radius);
        radReduction = radReduce;
        branchLength = branchLen;
        branchReduction = branchReduce;
        xLocation = locX;
        zLocation = locZ;
        xAngle = -x;
        yAngle = -y;
        zAngle = -z;
        initialValues = new float[2];
        initialValues[0] = radius;
        initialValues[1] = branchLength;
        branchStack = new Stack<Float>();
        branchStack.push(branchLen);
        cylinder.setTextureFlag(true);
    }
    
    /**
     * Reads each of the characters in the current state and performs actions 
     * accordingly.
     */
    public void draw(){
        iteration = system.getIteration();
        //c will hold the next character read in
        char c;
        //Set up the matrix and move to the location where the tree will be drawn
        GL11.glPushMatrix();
        GL11.glTranslatef(xLocation, 0, zLocation);
        //Grab the current iteration of the LSystem so the tree is built accordingly
        String current = system.getCurrent();
        //remove any variables in the string used for drawing, as variables
        //don't affect the drawing of the tree, just the growth
        current = current.replaceAll("[^"+RuleMap.TERMINAL_REGEX+"]", "");
        GL11.glColor4f(1f, 1f, 1f, 1f);
        //walk through all of the characters and draw the tree accordingly
        for(int i = 0; i < current.length(); i++){
            
            //Grab the next character
            c = current.charAt(i);
            //barkTexture.bind();
            //Perform an action based on the given input (unless the 
            switch(c){
                //draw a branch
                case('f'): 
                    drawBranch(i);
                    break;
                //draw a leaf
                case('l'):
                    drawLeaf();
                    break;
                //rotate postively around the z-axis
                case('+'):
                    GL11.glRotatef(zAngle, 0, 0, 1);
                    break;
                //rotate negatively around the z-axis
                case('-'):
                    GL11.glRotatef(-zAngle, 0, 0, 1);
                    break;
                //rotate positively around x-axis
                case('^'):
                    GL11.glRotatef(xAngle, 1, 0, 0);
                    break;
                //rotate negatively around the x-axis
                case('v'):
                    GL11.glRotatef(-xAngle, 1, 0, 0);
                    break;
                //rotate positvely around the y-axis
                case('>'):
                    GL11.glRotatef(yAngle, 0, 1, 0);
                    break;
                //rotate negatively around the y-axis
                case('<'):
                    GL11.glRotatef(-yAngle, 0, 1, 0);
                    break;
                //push onto the stack, which adds a new subtree
                case('['):
                    push();
                    break;
                //pop off the stack, which stops the definition of the subtree it wraps
                case(']'):
                    pop();
                    break;
            }
        }
        /*
         * Once done drawing the tree, pop the matrix and reinitialize all 
         * the tree's attributes so that tree may be redrawn at a different
         * iteration
         */
        GL11.glPopMatrix();
        radii.clear();
        radii.push(initialValues[0]);
        branchLength = initialValues[1];
        branchStack.clear();
        branchStack.push(initialValues[1]);
    }
    
    //Draws a branch based on the current attributes
    private void drawBranch(int i){
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        //set the color
        barkTexture.bind();
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL14.GL_GENERATE_MIPMAP, GL11.GL_TRUE);
        GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
        GL11.glPushMatrix();
        //translate upwards (relative to the current rotation) a distance of the 
        //current branch length
        GL11.glTranslatef(0, branchStack.peek(), 0);
        //Rotate 90 degrees around the x-axis so the branch is upright (relative to current rotation)
        GL11.glRotatef(90, 1, 0, 0);
        //Draw the branch, with the bottom radius being larger than the top radius, 
        //and the branch length being the current length
        cylinder.draw(radii.peek()*radReduction, radii.peek(), branchStack.peek(), 10, 10);
        //reduce the current radius by popping off the top radius, reducing it and pusing it back on
        radii.push(radii.pop()*radReduction);
        //pop the matrix for drawing
        GL11.glPopMatrix();
        //translate for next drawing
        GL11.glTranslatef(0, branchStack.peek()-branchStack.peek()*.05f, 0);
        //reduce the current branch length
        branchStack.push(branchStack.pop()*branchReduction);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
    }
    
    //Draw a leaf
    private void drawLeaf(){
        GL11.glPushMatrix();
        //move upwards (relative to current rotation) a distance of the size of the leaf
        GL11.glTranslatef(0, leafSize/2, 0);
        //If the leaves were upright in the image, they were being drawn upside
        //in the program, so this make the leaves render correctly
        GL11.glRotatef(180f, 1, 0, 0);
        //draw the quad which the leaf texture will be rendered on
        drawLeafQuad();
        GL11.glPopMatrix();
    }
    
    //draw the quad which the leaf will be textured on
    private void drawLeafQuad(){
        //Enable texture and Blending, which allows the texture to be transparent
        //in some portions, determined by the PNG it is given
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glTexParameteri(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_MODULATE);
        //bind the leaf texture
        leafTexture.bind();
        
        //draw the quad
        GL11.glBegin(GL11.GL_POLYGON);
            GL11.glTexCoord2f(0, 0);
            GL11.glVertex3f(-leafSize/2, -leafSize/2, 0);
            GL11.glTexCoord2f(1, 0);
            GL11.glVertex3f(leafSize/2, -leafSize/2, 0);
            GL11.glTexCoord2f(1, 1);
            GL11.glVertex3f(leafSize/2, leafSize/2, 0);
            GL11.glTexCoord2f(0, 1);
            GL11.glVertex3f(-leafSize/2, leafSize/2, 0);
        GL11.glEnd();
        
        //disable texturing and blending
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
    }
    
    //Pushes a new matrix, the current radius and the current branch length
    //onto the stack for creation of sub-branches.
    private void push(){
        GL11.glPushMatrix();
        radii.push(radii.peek());
        branchStack.push(branchStack.peek());
    }
    
    //Pops data pushed with the push() method. After this method is called 
    //the tree continues to grow from where it was pushed with the radius 
    //and branch length it left off with
    private void pop(){
        GL11.glPopMatrix();
        radii.pop();
        branchStack.pop();
    }
    
    //Set the iteration of the LSystem, which is used to draw that iteration
    //of the tree
    public void setIteration(int i){
        if(i >= 0)
            system.transform(i);
    }
    
    //set the leaf texture from a given file
    public void setLeafTexture(File file) throws FileNotFoundException, IOException{
        leafTexture = TextureLoader.getTexture("PNG", new FileInputStream(file));
    }
    
    //set the bark texture from a given file. The file's type must be determined
    public void setBarkTexture(File file) throws FileNotFoundException, IOException{
        String name = file.getName();
        if(name.toLowerCase().endsWith(".jpg"))
            barkTexture = TextureLoader.getTexture("JPG", new FileInputStream(file));
        if(name.toLowerCase().endsWith(".gif"))
            barkTexture = TextureLoader.getTexture("GIF", new FileInputStream(file));
        if(name.toLowerCase().endsWith(".png"))
            barkTexture = TextureLoader.getTexture("PNG", new FileInputStream(file));
    }
    
    public static void main(String[] args){
        String s = "abcdefghijklmnop+-<>^v";
        String x = s.replaceAll("[^fl+-<>^v]", "");
        System.out.println(x);
    }
}
