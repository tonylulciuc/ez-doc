/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ezdoc;

/**
 *
 * @author Matt
 */
public class ScriptMethods {
    
    public static String classHTML(String s){
        int i =0;
        StringBuilder htmlString= new StringBuilder();
        String tempString=null;
        
        htmlString.append("<h3>");
        /*grabs the first word*/
        while(s.charAt(i)!=' '){
            i++;
        }
        tempString=s.substring(0,i);
        htmlString.append(tempString);
        htmlString.append("</h3>");
        
        /*loop through the entire string s*/
        while(i<s.length()){
            
            if(s.charAt(i)=='\n'){
            htmlString.append("<br>");
            }
            else{
                htmlString.append(s.charAt(i));
            }
            i++;
        }
        
        
        return htmlString.toString();
    }
    
    public static String methodHTML(String s){
        return "methodHTML called successfully.";
    }
    
}
