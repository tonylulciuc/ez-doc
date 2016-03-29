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
    
    @Test
    public void shouldCallClassHTMLMethodAndReturnHTML(){
       String ret=null;
        
        String s = "ScriptMethodsTest {\n" +
"    \n" +
"    public ScriptMethodsTest() {\n" +
"    }\n" +
"    \n" +
"    @BeforeClass\n" +
"    public static void setUpClass() {\n" +
"    }\n" +
"    \n" +
"    @AfterClass\n" +
"    public static void tearDownClass() {\n" +
"    }\n" +
"    \n" +
"    @Before\n" +
"    public void setUp() {\n" +
"    }\n" +
"    \n" +
"    @After\n" +
"    public void tearDown() {\n" +
"    }";
        
        String htmls = "<h3>ScriptMethodsTest</h3> {<br>" +
"    <br>" +
"    public ScriptMethodsTest() {<br>" +
"    }<br>" +
"    <br>" +
"    @BeforeClass<br>" +
"    public static void setUpClass() {<br>" +
"    }<br>" +
"    <br>" +
"    @AfterClass<br>" +
"    public static void tearDownClass() {<br>" +
"    }<br>" +
"    <br>" +
"    @Before<br>" +
"    public void setUp() {<br>" +
"    }<br>" +
"    <br>" +
"    @After<br>" +
"    public void tearDown() {<br>" +
"    }";
        
        ret = ScriptMethods.classHTML(s);
        
        assertEquals(ret,htmls);
    }
}
