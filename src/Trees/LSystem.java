/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Trees;

import java.util.LinkedList;

/**
 * LSystem creates a string which determines how a tree will be drawn.
 * The LSystem has an initial string which consists of non-terminals and terminals.
 * The non-terminal may be any character except those which define the terminals.
 * The terminals consist of f, l, +, -, <, >, ^, and v. The terminals actual use 
 * are defined within the Tree class. This class can be reused with other 
 * alphabets as the checking of whether a letter is a terminal or not is done 
 * within the Rule class. A new rule class, or a redesign of the current, 
 * would need to be implemented.
 * 
 * @author nelso148
 */
public class LSystem {
    
    //The inital string to start the LSystem
    private String initial;
    //The rule of replacement of the character 'A'
    private RuleMap rules;
    //this linked list holds previously created iterations so work doesn't
    //have to be redone
    private LinkedList<String> savedIter;
    //How many iterations it is currently at
    private int iteration;
    //The current value of the LSystem string
    private String current;
    
    //initialize attributes of a LSystem
    public LSystem(String i, RuleMap r){
        initial = i.toLowerCase();
        iteration = 0;
        current = initial;
        rules = r;
        savedIter = new LinkedList<String>();
        savedIter.add(iteration, initial);
    }
    
    /** 
     * This translates the initial value to the final value by replacing
     * every variable (indicated by A) with the rule. The system's current
     * value will be the initial value transformed over the number of iterations
     */
    public void transform(int iterations){
        //reset the current iteration value
        iteration = iterations;
        //reset the current to the initial value
        current = initial;
        //newCurrent will hold the current state with transformations
        //applied to each of its variables
        String newCurrent = "";
        //will hold a replacement of a variable, used to build newCurrent
        String replacement = "";
        
        //If the iteration requested has already been created, then just use that one
        if(savedIter.size()-1 >= iterations)
            current = savedIter.get(iteration);
        //Otherwise start at the last created iteration
        else
            current = savedIter.get(savedIter.size()-1);
        
        //transform the definition of a rule based on the number of iterations
        //requested. If the requested iteration has been created, then 
        //this loop will be skipped. Otherwise the loop will start at the 
        //highest iteration already created
        for(int i = savedIter.size()-1; i < iterations; i++){
            //Reset the new current
            newCurrent = "";
            for(int j = 0; j < current.length(); j++){
                //grab the next character in current
                char c = current.charAt(j);
                //if the character is a variable, add its rule to the newCurrent
                //otherwise just add the character to the new current
                replacement = RuleMap.isNonterminal(c) ? rules.getDefinition(c) : ""+c;
                newCurrent += replacement;
            }
            //update current
            current = newCurrent;
            //save off iteration
            savedIter.add(current);
        }
    }
    
    /**
     * Returns the system's initial value, which is its starting state.
     * @return 
     */
    public String getInitial(){
        return initial;
    }
    
    /**
     * Returns the how many iterations the current value is at.
     * @return 
     */
    public int getIteration(){
        return iteration;
    }
    
    /**
     * Returns the string representing the current state of the system.
     * @return 
     */
    public String getCurrent(){
        return current;
    }
}
