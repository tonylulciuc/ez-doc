/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ezdoc;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Matt
 */
public class ScriptMethodsTest {

    public ScriptMethodsTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    /*This test passes a string that represents what will be passed to the classDec method.
    I hand created the expected return based on how the HTML should look, then tested it against
    the return of the method.
    */
    @Test
    public void shouldCallClassDecAndReturnHTML() {
        String classDec = "public class Word {\n" +
"    protected String  strCharSeq;\n" +
"    protected WordTag wTag;\n" +
"    \n" +
"    public Word(String _strCharSeq, WordTag _wTag)\n" +
"    {";
        
        String htmlExpected;
        String htmlActual;
        
        htmlExpected="<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" >\n"
                + "<html>\n"
                + "<head>\n"
                + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n"
                + "<title>EZ-Doc</title>\n"
                + "<link rel=\"stylesheet\" href=\"style.css\" type=\"text/css\">\n"
                + "</head>\n"
                + "<body>"
                +"<div class='Class'><br>"
                +"public class Word "
                + "</div><br>";
        
        htmlActual = ScriptMethods.classDec(classDec);
        
        assertEquals(htmlExpected,htmlActual);
        
        
    }
    
    /*The next two tests simply ensure that our program will not bomb if empty or
    null strings are mistakenly passed to the classDec method. Additionally, the empty
    string that is returned should not cause issues with the rest of the HTML being
    generated.
    */
    @Test
    public void nullStringToClassDecShouldReturnEmptyString(){
    String test = null;
    String expected = "";
    String actual = ScriptMethods.classDec(test);
    
    assertEquals(expected,actual);
        
    }
    
     @Test
    public void emptyStringToClassDecShouldReturnEmptyString(){
    String test = "";
    String expected = "";
    String actual = ScriptMethods.classDec(test);
    
    assertEquals(expected,actual);
        
    }
    
    /*uses example string and HTML I built by hand to ensure that the return from the methodDec
    method creates the HTML expected.
    */
       @Test
    public void shouldCallMethodDecAndReturnHTML() {
        String methodDec = " private boolean findCommands() ";
        
        String htmlExpected;
        String htmlActual;
        
        htmlExpected="<br><div class='method'>}<br>private boolean findCommands() {</div><br>";
        
        htmlActual = ScriptMethods.methodDec(methodDec);
        
        assertEquals(htmlExpected,htmlActual);
        
        
    }
    
    /*The next two tests simply ensure that our program will not bomb if empty or
    null strings are mistakenly passed to the methodDec method. Additionally, the empty
    string that is returned should not cause issues with the rest of the HTML being
    generated.
    */
    @Test
    public void nullStringToMethodDecShouldReturnEmptyString(){
    String test = null;
    String expected = "";
    String actual = ScriptMethods.methodDec(test);
    
    assertEquals(expected,actual);
        
    }
    
     @Test
    public void emptyStringToMethodDecShouldReturnEmptyString(){
    String test = "";
    String expected = "";
    String actual = ScriptMethods.methodDec(test);
    
    assertEquals(expected,actual);
        
    }
    
    /*uses example string and HTML I built by hand to ensure that the return from the param
    method creates the HTML expected.
    */
       @Test
    public void shouldCallParamAndReturnHTML() {
        String param = "Param No parameter is passed";
        
        String htmlExpected;
        String htmlActual;
        
        htmlExpected="<div class='param'>Parameter:<br> No parameter is passed</div><br>";
        
        htmlActual = ScriptMethods.param(param);
        
        assertEquals(htmlExpected,htmlActual);
        
        
    }
    
    /*The next two tests simply ensure that our program will not bomb if empty or
    null strings are mistakenly passed to the param method. Additionally, the empty
    string that is returned should not cause issues with the rest of the HTML being
    generated.
    */
    @Test
    public void nullStringToParamShouldReturnEmptyString(){
    String test = null;
    String expected = "";
    String actual = ScriptMethods.param(test);
    
    assertEquals(expected,actual);
        
    }
    
     @Test
    public void emptyStringToParamShouldReturnEmptyString(){
    String test = "";
    String expected = "";
    String actual = ScriptMethods.param(test);
    
    assertEquals(expected,actual);
        
    }
    
        /*uses example string and HTML I built by hand to ensure that the return from the returns
    method creates the HTML expected.
    */
       @Test
    public void shouldCallReturnsAndReturnHTML() {
        String returns = "return This method returns true if it makes it through, otherwise it returns false indicating an\n" +
"    error occured";
        
        String htmlExpected;
        String htmlActual;
        
        htmlExpected="<div class='return'>Return:<br> This method returns true if it makes it through, otherwise it returns false indicating an\n" +
"    error occured</div><br>";
        
        htmlActual = ScriptMethods.returns(returns);
        
        assertEquals(htmlExpected,htmlActual);
        
        
    }
    
    /*The next two tests simply ensure that our program will not bomb if empty or
    null strings are mistakenly passed to the returns method. Additionally, the empty
    string that is returned should not cause issues with the rest of the HTML being
    generated.
    */
    @Test
    public void nullStringToReturnsShouldReturnEmptyString(){
    String test = null;
    String expected = "";
    String actual = ScriptMethods.returns(test);
    
    assertEquals(expected,actual);
        
    }
    
     @Test
    public void emptyStringToReturnsShouldReturnEmptyString(){
    String test = "";
    String expected = "";
    String actual = ScriptMethods.returns(test);
    
    assertEquals(expected,actual);
        
    }
    
        /*uses example string and HTML I built by hand to ensure that the return from the description
    method creates the HTML expected.
    */
       @Test
    public void shouldCallDescriptionAndReturnHTML() {
        String returns = "This method goes through my array of strings and attempts to build the arrayLists of commands\n" +
"    and arguments to be passed to the ScriptMethods class";
        
        String htmlExpected;
        String htmlActual;
        
        htmlExpected="<div class='description'>Description:<br>This method goes through my array of strings and attempts to build the arrayLists of commands\n" +
"    and arguments to be passed to the ScriptMethods class</div><br>";
        
        htmlActual = ScriptMethods.description(returns);
        
        assertEquals(htmlExpected,htmlActual);
        
        
    }
    
    /*The next two tests simply ensure that our program will not bomb if empty or
    null strings are mistakenly passed to the description method. Additionally, the empty
    string that is returned should not cause issues with the rest of the HTML being
    generated.
    */
    @Test
    public void nullStringToDescriptionShouldReturnEmptyString(){
    String test = null;
    String expected = "";
    String actual = ScriptMethods.description(test);
    
    assertEquals(expected,actual);
        
    }
    
     @Test
    public void emptyStringToDescriptionShouldReturnEmptyString(){
    String test = "";
    String expected = "";
    String actual = ScriptMethods.description(test);
    
    assertEquals(expected,actual);
        
    }
    
    
    
    
    
}
