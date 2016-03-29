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
public class ScriptInterpreterTest {
    
    public ScriptInterpreterTest() {
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
    
    /*This test ensures that the command array is being read in correctly*/
    @Test
    public void shouldReadCommandFileAndCreateArray(){
        
        
        EZFile file = new EZFile();
        
        file.open("testfile\\", "Commands.txt", IOFlag.WRITE);
        
        String str = "Class: classHTML,methodHTML\r\nMethod: method\r\n#:\r\nInput: input\r\nOutput: output\r\nOVariables: ovar";
        assertEquals(true, file.write(str.getBytes(), 0, str.length()));
        
        ScriptInterpreter si = new ScriptInterpreter("Test");
        assertEquals(si.commandsAndMethods[0][0],"Class");
        assertEquals(si.commandsAndMethods[1][0],"Method");
        assertEquals(si.commandsAndMethods[2][0],"#");
        assertEquals(si.commandsAndMethods[3][0],"Input");
        assertEquals(si.commandsAndMethods[4][0],"Output");
        assertEquals(si.commandsAndMethods[5][0],"OVariables");
        
        file.close();
            
    }
    
    @Test
    public void shouldCompareCommandFileAndPassedString(){
        String s = "Class this class is being tested Test File";
        ScriptInterpreter si = new ScriptInterpreter(s);
        
        assertEquals(true,si.commandFound);
    }
    
    
}
