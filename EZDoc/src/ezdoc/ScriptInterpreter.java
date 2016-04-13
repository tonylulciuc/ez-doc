/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ezdoc;

import ezdoc.EZFile;
import ezdoc.IOFlag;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
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
    String[][] commandsAndMethods = new String[numOfCommands][10];
    String test;
    String htmlString;
    public String cleanedInput;
    public String stringArray[];
    String delim;
    String descriptionCommand = "description";

    public ScriptInterpreter(String s) {
        EZFile file = new EZFile();

        cleanedInput = cleanedFileInput(s);

        /*Loading the Command script into the Script Interpreter*/
        file.open("testfile\\", "Commands.txt", IOFlag.READ);
        file.setBufferSize(250);
        file.read(0);
        test = new String(file.getBuffer());
        file.parseBuffer(null);

        generateCommandAndMethodArrays();

        /*the methods array contains the substrings for each array with the commented code removed*/
        stringArray = cleanedInput.split("}");

        int i = 0;
        while (i < stringArray.length) {
            stringArray[i] = stringArray[i].trim();
            i++;
        }

        /*The findCommands method builds linked lists of commands and arguments.*/
        commandFound = findCommands();

        htmlString = buildHTMLString();

    }

    /*this method goes through the methods array*/
    private boolean findCommands() {
        int i = 0;
        int j = 0;
        String s;
        StringBuilder argumentString = new StringBuilder();
        boolean command = false;

        String classArray[] = stringArray[0].split("\\Q*/\\E");

        /*checking whether or not the user commented the source code or if they just have the class declaration*/
        if (classArray.length == 0) {

        } else if (classArray.length == 1) {
            commandsFound.add("classDec");
            arguments.add(classArray[0]);

        } else {
            commandsFound.add("classDec");
            arguments.add(classArray[1]);

            /*String comments[]=classArray[0].split(delim);
             commandsFound.add("classComments");
             arguments.add(classArray[0]);*/
        }
        i++;

        //need to loop through each element of the methods array
        while (i < stringArray.length) {

            /*this line splits each of the strings into two substrings, with element 0 being the commented section
             and element 1 being the method declaration*/
            String splitArray[] = stringArray[i].split("\\Q*/\\E");

            /*we want to add the method declaration to the commandsFound array first*/
            if (splitArray.length == 0) {

            } else if (splitArray.length == 1) {
                commandsFound.add("methodDec");
                arguments.add(classArray[0]);

            } else {
                commandsFound.add("methodDec");
                arguments.add(splitArray[1]);

                String comments[] = splitArray[0].split(delim);

                int k = 0;

                while (k < comments.length) {
                    j = 0;
                    while (j < commandsAndMethods.length && commandsAndMethods[j][0] != null) {

                        if (comments[k].startsWith(commandsAndMethods[j][0])) {
                            commandsFound.add(commandsAndMethods[j][1]);
                            command = true;
                        }
                        j++;
                    }

                    if (!command) {
                        commandsFound.add(descriptionCommand);
                    }

                    arguments.add(comments[k]);
                    k++;
                    command = false;
                }
            }

            i++;
        }

        return true;
    }

    /*this method goes through the string retrieved from the command script and generates a multidimensional array. The [k][0]th element
     is always the command and the [k][1 through j] element represent the methods that must be called for that command.*/
    private void generateCommandAndMethodArrays() {
        int i = 0;
        int begin = 0;
        int k = 0;
        int j = 1;
        String tempString = null;

        /*the first character of the script will always be the delimeter users use to designate areas of there
         comments
         */
        delim = Character.toString(test.charAt(i));

        while (test.charAt(i) != '\n') {
            i++;
        }
        i++;
        begin = i;

        while (i < test.length()) {

            if (test.charAt(i) == ':') {
                tempString = test.substring(begin, i);
                tempString = tempString.trim();
                commandsAndMethods[k][0] = tempString;
                begin = i + 1;
            } else if (test.charAt(i) == ',') {
                tempString = test.substring(begin, i);
                tempString = tempString.trim();
                commandsAndMethods[k][j] = tempString;
                j++;
                begin = i + 1;
            } else if (test.charAt(i) == '\n') {
                tempString = test.substring(begin, i);
                tempString = tempString.trim();
                commandsAndMethods[k][j] = tempString;
                k++;
                begin = i + 1;
                j = 1;
            }
            i++;

        }
        tempString = test.substring(begin, i);
        tempString = tempString.trim();
        commandsAndMethods[k][j] = tempString;

    }

    /*The buildHTMLString will use reflection to call the correct Instruction class methods and pass the substrings to perform work.
     The return from these method calls will then be put together utilizing a StringBuilder and returned to the ScriptInterpreter*/
    private String buildHTMLString() {
        int i = 0;
        int j = 0;
        int k = 1;
        StringBuilder returnString = new StringBuilder();
        String ret = new String();
        Method m = null;
        Class<?> c = null;
        try {
            c = Class.forName("ezdoc.ScriptMethods");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ScriptInterpreter.class.getName()).log(Level.SEVERE, null, ex);
        }

        /*This loop will run once for each command that was found in the passed file data string*/
        while (i < commandsFound.size()) {

            try {
                m = c.getDeclaredMethod(commandsFound.get(i), String.class);
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

            i++;
        }

        /*closing the HTML document properly*/
        returnString.append("</body></html>");

        return returnString.toString();
    }

    /*This method simply goes through the entire input file and assumes we are looking at a class file and removes all of the source code from
     methods. This leaves us with essentially a class file with its methods and the comments that describe the method.
     */
    private String cleanedFileInput(String s) {
        String tempString = new String();
        int i = 0;
        boolean firstBracket = false;
        boolean insideMethod = false;
        Stack stack = new Stack();

        /*loops until the null character is read in, signalling the end of the string*/
        while (i < s.length()) {

            /*if you are inside a method and read in a closing bracket then pop the top opening bracket. If the stack is empty
             that means you have exited the method source code so set insideMethod to false
             */
            if (insideMethod && s.charAt(i) == '}') {
                stack.pop();

                if (stack.isEmpty()) {
                    insideMethod = false;

                }
            } /*if you are inside of a method and you read an opening bracket, push that bracket onto the stack*/ else if (insideMethod && s.charAt(i) == '{') {
                stack.push(s.charAt(i));
            }

            /*if you are not inside of a method and you have not already read in a bracket then set first bracket
             to true and add the bracket character to the tempString. This bracket will be the bracket for the class
             not a specific method.
             */
            if (!insideMethod && !firstBracket && s.charAt(i) == '{') {
                firstBracket = true;
                tempString = tempString.concat(Character.toString(s.charAt(i)));
            } /*This means you are entering a method so we want to ignore the source code in the method*/ else if (!insideMethod && s.charAt(i) == '{' && firstBracket) {
                insideMethod = true;
                stack.push(s.charAt(i));
                tempString = tempString.concat("{");

            } /*if you are not inside of a method then add the character to the tempString*/ else if (!insideMethod) {
                tempString = tempString.concat(Character.toString(s.charAt(i)));
            }

            i++;

        }

        //return the string that was built from the passed document
        return tempString;
    }

}
