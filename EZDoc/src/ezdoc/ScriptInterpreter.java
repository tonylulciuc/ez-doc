/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ezdoc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 *
 * @author Matt
 */
public class ScriptInterpreter {
    public ArrayList<String> commandsFound = new ArrayList<String>();
    public ArrayList<String> arguments = new ArrayList<String>();
    public ArrayList<String> fileData = new ArrayList<String>();
    public boolean commandFound = false;
    final int numOfCommands = 10;
    String [][] commandsAndMethods = new String[numOfCommands][10];
    String test;
    String htmlString;
    
    
    public ScriptInterpreter(String s){
        EZFile file = new EZFile();
        
        
        /*Loading the Command script into the Script Interpreter*/
        file.open("testfile\\", "Commands.txt", IOFlag.READ);
        file.setBufferSize(250);
        file.read(0);
        test = new String(file.getBuffer());
        file.parseBuffer(null);
        
        
        generateCommandAndMethodArrays();
        
        
        /*These lines separate the String file data that is passed by the File Translator to the Script Interpreter*/
        Pattern pattern = Pattern.compile("\\w+");
        Matcher matcher = pattern.matcher(s);
        while (matcher.find()) {
        fileData.add(matcher.group());
        }
        
        /*The findCommands method builds linked lists of commands and arguments.*/
        commandFound = findCommands();
        
        htmlString=buildHTMLString();
        
    }
    
    
    private boolean findCommands(){
        int i=0;
        int j=0;
        String s;
        StringBuilder argumentString = new StringBuilder();
        boolean command=false;
        
        while(i<fileData.size()){
        s=fileData.get(i);
        
            while(j<numOfCommands){
                if(s.equalsIgnoreCase(commandsAndMethods[j][0])){
                    commandsFound.add(commandsAndMethods[j][0]);
                    if(argumentString.length()>0)
                    arguments.add(argumentString.toString());
                        argumentString.setLength(0);
                        command = true;
                }
                j++;
            }
            
            /*if the word being read was not a command then it must be a part of the substring that will be an
            argument for the instruction method so we append it to the argumentString along with a space.
            */
            if(!command){
                argumentString.append(s);
                argumentString.append(" ");
                
            }
        command = false;    
        j=0;
        i++;
        
        }
        /*This captures the final substring of arguments after the last command*/
        arguments.add(argumentString.toString());
        
        return true;
    }

    /*this method goes through the string retrieved from the command script and generates a multidimensional array. The [k][0]th element
    is always the command and the [k][1 through j] element represent the methods that must be called for that command.*/
    private void generateCommandAndMethodArrays() {
       int i =0;
       int begin=0;
       int k = 0;
       int j = 1;
       String tempString = null;
       
       while(i<test.length()){
           
           if(test.charAt(i)==':'){
               tempString = test.substring(begin,i);
               tempString = tempString.trim();
               commandsAndMethods[k][0]=tempString;
               begin=i+1;
               }
           
           else if(test.charAt(i)==','){
               tempString=test.substring(begin,i);
               tempString = tempString.trim();
               commandsAndMethods[k][j]=tempString;
               j++;
               begin=i+1;
           }
           
           else if(test.charAt(i)=='\n'){
               tempString=test.substring(begin,i);
               tempString = tempString.trim();
               commandsAndMethods[k][j]=tempString;
               k++;
               begin=i+1;
               j=1;
           }
           i++;
           
       }
       tempString = test.substring(begin,i);
       tempString.trim();
       commandsAndMethods[k][j]=tempString; 
        
        
        
    }

    /*The buildHTMLString will use reflection to call the correct Instruction class methods and pass the substrings to perform work.
    The return from these method calls will then be put together utilizing a StringBuilder and returned to the ScriptInterpreter*/
    private String buildHTMLString() {
        int i = 0;
        int j = 0;
        int k =1;
        StringBuilder returnString = new StringBuilder();
        String ret=new String();
        Method m = null;
        Class<?> c = null;
        try {
            c = Class.forName("ezdoc.ScriptMethods");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ScriptInterpreter.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        /*This loop will run once for each command that was found in the passed file data string*/
        while(i<commandsFound.size()){
            
            /*This loop will run once for each command that exists in the command script*/
            while(j<commandsAndMethods.length){
                
                /*Is true when the current command is equal to the jth command in the command script*/
                if(commandsFound.get(i).equalsIgnoreCase(commandsAndMethods[j][0])){
                    
                    /*run this loop once for each method call associated with the current command*/
                    while(commandsAndMethods[j][k]!=null){
                        
                        try {
                            m= c.getDeclaredMethod(commandsAndMethods[j][k], String.class);
                        } catch (NoSuchMethodException ex) {
                            Logger.getLogger(ScriptInterpreter.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (SecurityException ex) {
                            Logger.getLogger(ScriptInterpreter.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        try {
                            ret = (String) m.invoke(null, arguments.get(i));
                        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                            Logger.getLogger(ScriptInterpreter.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        returnString.append(ret);
                        k++;
                    }
                    
                    
                }
               j++; 
            }
            
            
            
            i++;
        }
        
        return returnString.toString();
    }
    
    
    
}
