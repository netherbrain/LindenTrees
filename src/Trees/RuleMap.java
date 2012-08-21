/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Trees;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

/**
 * A rule of growth for trees to be used with the LSystem class. 
 * @author nelso148
 */
public class RuleMap {
    //The character that will identify the rule
    char id;
    //The string at which its id will transform into
    String def;
    //The list of terminals
    final static Character[] INVALID_IDENTIFIERS = {'f', '+', '-', 'v', '^', '>', '<', '[', ']', 'l'};
    //A string of all the terminals, which may be used with regular expression
    final static String TERMINAL_REGEX = "fl\\+\\-<>\\^v\\[\\]";
    HashMap<Character, String> rules;
    //Constructor for the Rule
    public RuleMap(){
        rules = new HashMap<Character, String>();
    }
    
    //Just a few getters
    public String getDefinition(Character identifier){
        return rules.get(identifier);
    }
    
    public void addRule(Character identifier, String definition){
        //If the rule identifier requested is not a terminal
        if(isNonterminal(identifier)){
            String s = identifier+"";
            identifier = s.charAt(0);
            rules.put(identifier, definition.toLowerCase());
        }
        else{
            throw new RuntimeException();
        }
    }
    
    public Set<Entry<Character, String>> getRuleSet(){
        return rules.entrySet();
    }
    
    //Checks if the character is a terminal. Returns true if it is, and false
    //if it is not.
    public static boolean isNonterminal(Character c){
        for(int i = 0; i < INVALID_IDENTIFIERS.length; i++){
            if(c == INVALID_IDENTIFIERS[i])
                return false;
        }
        return true;
    }
}
