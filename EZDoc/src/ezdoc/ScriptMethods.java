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

    private static final String lessThan = "&lt;";      // NON-Modifiable data member
    private static final String greaterThan = "&gt;";   // NON-Modifiable data member
    public static String outerFormatTagStart;
    public static String innerFormatTagStart;
    public static String outerFormatTagEnd;
    public static String innerFormatTagEnd;
    public static String tableDataTagStart;
    public static String tableDataTagEnd;
    public static String tableHeaderTagStart;
    public static String tableHeaderTagEnd;
    public static String openingBracket;
    public static String closingBracket;
    private static boolean firstMethod = false;

    public static String classDec(String s) {

        StringBuilder returnHTML = new StringBuilder();
        String temp1;
        int i = 0;
        int iNext = 0;

        /*If the string passed is null, simply return the empty string*/
        if (s == null || s.equals("")) {
            return "";
        }

        // ADDED : Sentinel value for while loop
        //         Loops forever or breaks if '{' not found
        int iSize = s.length();

        while (i < iSize && s.charAt(i) != '{') {
            i++;
        }

        temp1 = s.substring(0, i);

        returnHTML.append("<div class='Class'><br>");

        i = 0;
        while (i < temp1.length()) {
            switch (temp1.charAt(i)) {
                case '<':
                    returnHTML.append(lessThan);
                    break;
                case '>':
                    returnHTML.append(greaterThan);
                    break;
                default:
                    // Check for extra documentation
                    if ((iNext = fileHeaderDocumentation(returnHTML, temp1, i)) == i) {
                        // Check for packages/imports
                        if ((iNext = fileDependencies(returnHTML, temp1, i)) == i) {
                            returnHTML.append(temp1.charAt(i));
                        } else {
                            i = iNext - 1;
                        }
                    } else {
                        i = iNext - 1;
                    }
            }

            i++;
        }
        returnHTML.append("</div><br>");
        returnHTML.append(outerFormatTagStart);

        return returnHTML.toString();
    }

    public static String methodDec(String s) {
        String temp1;
        String returnStatement;
        StringBuilder returnHTML = new StringBuilder();
        boolean openBracket = false;

        /*If the string passed is null, simply return the empty string*/
        if (s == null || s.equals("")) {
            return "";
        }

        s.trim();

        int i = 2;
        while (s.charAt(i) != '\n' && i < s.length() - 1) {
            i++;
        }
        i++;
        temp1 = s.substring(1, i);
        temp1.replaceAll("\n", "");

        returnHTML.append(outerFormatTagEnd);
        returnHTML.append(outerFormatTagStart);
        returnHTML.append("<br><div class='method'>");

        if (firstMethod) {
            returnHTML.append(closingBracket);
        }

        returnHTML.append("<br>");

        i = 0;
        returnHTML.append(tableHeaderTagStart);

        while (i < temp1.length()) {

            if (temp1.charAt(i) == '<') {
                returnHTML.append(lessThan);
            } else if (temp1.charAt(i) == '>') {
                returnHTML.append(greaterThan);
            } else if (temp1.charAt(i) == '{') {
                returnHTML.append(temp1.charAt(i));
                openBracket = true;
            } else {
                returnHTML.append(temp1.charAt(i));

            }
            i++;
        }
        returnHTML.append(tableHeaderTagEnd);
        if (!openBracket) {
            returnHTML.append(openingBracket);
        }
        returnHTML.append("</div><br>");

        firstMethod = true;

        return returnHTML.toString();
    }

    public static String param(String s) {
        String temp1;
        String temp2;
        String returnStatement;
        StringBuilder returnHTML = new StringBuilder();

        /*If the string passed is null, simply return the empty string*/
        if (s == null || s.equals("")) {
            return "";
        }

        temp1 = s.replaceAll("/", "");
        temp2 = temp1.replaceAll("\\*", "");
        temp2.trim();

        int i = 0;
        while (temp2.charAt(i) != ' ') {
            i++;
        }

        returnStatement = temp2.substring(i, temp2.length());

        returnHTML.append("<div class='param'>");
        returnHTML.append(innerFormatTagStart);
        returnHTML.append(tableDataTagStart);
        returnHTML.append("Parameter:<br>");
        returnHTML.append(tableDataTagEnd);

        returnHTML.append(tableDataTagStart);
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
        returnHTML.append(tableDataTagEnd);
        returnHTML.append(innerFormatTagEnd);
        returnHTML.append("</div>");

        returnHTML.append("<br>");

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

        /*If the string passed is null, simply return the empty string*/
        if (s == null || s.equals("")) {
            return "";
        }

        temp1 = s.replaceAll("/", "");
        temp2 = temp1.replaceAll("\\*", "");
        temp2.trim();

        int i = 0;
        while (temp2.charAt(i) != ' ') {
            i++;
        }

        returnStatement = temp2.substring(i, temp2.length());

        returnHTML.append("<div class='return'>");
        returnHTML.append(innerFormatTagStart);
        returnHTML.append(tableDataTagStart);
        returnHTML.append("Return:<br>");
        returnHTML.append(tableDataTagEnd);
        i = 0;
        returnHTML.append(tableDataTagStart);

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

        returnHTML.append(tableDataTagEnd);
        returnHTML.append(innerFormatTagEnd);
        returnHTML.append("</div>");

        returnHTML.append("<br>");

        return returnHTML.toString();
    }

    public static String description(String s) {
        String temp1;
        String temp2;
        
        StringBuilder returnHTML = new StringBuilder();

        /*If the string passed is null, simply return the empty string*/
        if (s == null || s.equals("")) {
            return "";
        }

        temp1 = s.replace("/", "");
        temp2 = temp1.replaceAll("\\*", "");
        temp2.trim();
        
        if(temp2.startsWith("desc")){
        temp2=temp2.replaceFirst("desc", "");
        }

        returnHTML.append("<div class='description'>");
        returnHTML.append(innerFormatTagStart);
        returnHTML.append(tableDataTagStart);
        returnHTML.append("Description:<br>");
        returnHTML.append(tableDataTagEnd);

        returnHTML.append(tableDataTagStart);

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
        returnHTML.append(tableDataTagEnd);
        returnHTML.append(innerFormatTagEnd);
        returnHTML.append("</div>");

        returnHTML.append("<br>");

        return returnHTML.toString();

    }

    /**
     * Attempts to locate extra information provided by user in begining of
     * document.
     *
     * @param _bldHtml[in] HTML data
     * @param _raw[in] Raw file data
     * @param _index [in] current position in raw data
     * @return integer[out] next position in _raw
     */
    private static int fileHeaderDocumentation(StringBuilder _bldHtml, String _raw, int _index) {

        return (_index);
    }

    /**
     * Attempts to locate extra information provided by user in begining of
     * document.
     *
     * @param _bldHtml[in] HTML data
     * @param _raw[in] Raw file data
     * @param _index [in] current position in raw data
     * @return integer[out] next position in _raw
     */
    private static int fileDependencies(StringBuilder _bldHtml, String _raw, int _index) {

        return (_index);
    }
}
