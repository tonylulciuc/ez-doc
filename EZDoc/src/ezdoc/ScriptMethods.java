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

    static String lessThan = "&lt;";
    static String greaterThan = "&gt;";

    public static String classDec(String s) {
        StringBuilder returnHTML = new StringBuilder();
        String temp1;

        int i = 0;

        // ADDED : Sentinel value for while loop
        //         Loops forever or breaks if '{' not found
        int iSize = s.length();
        
        while (i < iSize && s.charAt(i) != '{') {
            i++;
        }

        temp1 = s.substring(0, i);

        /*necessary to create valid HTML document*/
        returnHTML.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" >\n"
                + "<html>\n"
                + "<head>\n"
                + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n"
                + "<title>Fresh Market</title>\n"
                + "<link rel=\"stylesheet\" href=\"style.css\" type=\"text/css\">\n"
                + "</head>\n"
                + "<body>");

        returnHTML.append("<div class='Class'><br>");
        i = 0;
        while (i < temp1.length()) {

            if (temp1.charAt(i) == '<') {
                returnHTML.append(lessThan);
            } else if (temp1.charAt(i) == '>') {
                returnHTML.append(greaterThan);
            } else {
                returnHTML.append(temp1.charAt(i));
            }
            i++;
        }
        returnHTML.append("</div><br>");

        return returnHTML.toString();
    }

    public static String methodDec(String s) {
        String temp1;
        String returnStatement;
        StringBuilder returnHTML = new StringBuilder();
        s.trim();

        int i = 2;
        while (s.charAt(i) != '\n') {
            i++;
        }

        temp1 = s.substring(1, i);
        temp1.replaceAll("\n", "");

        returnHTML.append("<br><div class='method'>}<br>");
        i = 0;
        while (i < temp1.length()) {

            if (temp1.charAt(i) == '<') {
                returnHTML.append(lessThan);
            } else if (temp1.charAt(i) == '>') {
                returnHTML.append(greaterThan);
            } else {
                returnHTML.append(temp1.charAt(i));
            }
            i++;
        }
        returnHTML.append("{</div><br>");

        return returnHTML.toString();
    }

    public static String param(String s) {
        String temp1;
        String temp2;
        String returnStatement;
        StringBuilder returnHTML = new StringBuilder();
        temp1 = s.replaceAll("/", "");
        temp2 = temp1.replaceAll("\\*", "");
        temp2.trim();

        int i = 0;
        while (temp2.charAt(i) != ' ') {
            i++;
        }

        returnStatement = temp2.substring(i, temp2.length());

        returnHTML.append("<div class='param'>Parameter:<br>");
        i = 0;
        while (i < returnStatement.length()) {

            if (returnStatement.charAt(i) == '<') {
                returnHTML.append(lessThan);
            } else if (returnStatement.charAt(i) == '>') {
                returnHTML.append(greaterThan);
            } else {
                returnHTML.append(returnStatement.charAt(i));
            }
            i++;
        }
        returnHTML.append("</div><br>");

        return returnHTML.toString();
    }

    public static String author(String s) {
        return "Successful author call.\n";
    }

    public static String returns(String s) {
        String temp1;
        String temp2;
        String returnStatement;
        StringBuilder returnHTML = new StringBuilder();
        temp1 = s.replaceAll("/", "");
        temp2 = temp1.replaceAll("\\*", "");
        temp2.trim();

        int i = 0;
        while (temp2.charAt(i) != ' ') {
            i++;
        }

        returnStatement = temp2.substring(i, temp2.length());

        returnHTML.append("<div class='return'>Return:<br>");
        i = 0;
        while (i < returnStatement.length()) {

            if (returnStatement.charAt(i) == '<') {
                returnHTML.append(lessThan);
            } else if (returnStatement.charAt(i) == '>') {
                returnHTML.append(greaterThan);
            } else {
                returnHTML.append(returnStatement.charAt(i));
            }
            i++;
        }
        returnHTML.append("</div><br>");

        return returnHTML.toString();
    }

    public static String description(String s) {
        String temp1;
        String temp2;

        StringBuilder returnHTML = new StringBuilder();
        temp1 = s.replace("/", "");
        temp2 = temp1.replaceAll("\\*", "");
        temp2.trim();

        returnHTML.append("<div class='description'>Description:<br>");
        int i = 0;
        while (i < temp2.length()) {

            if (temp2.charAt(i) == '<') {
                returnHTML.append(lessThan);
            } else if (temp2.charAt(i) == '>') {
                returnHTML.append(greaterThan);
            } else {
                returnHTML.append(temp2.charAt(i));
            }
            i++;
        }
        returnHTML.append("</div><br>");

        return returnHTML.toString();

    }

}
