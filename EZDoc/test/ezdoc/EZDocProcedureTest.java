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
import java.io.*;
import java.util.ArrayList;

/**
 *
 * @author Antony
 */
public class EZDocProcedureTest {
    
    public EZDocProcedureTest() {
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
    
    /**
     *  Ensure that object of type Word contains expected data.
     */
    @Test
    public void shouldPassTagCheck()
    {
        Word word = new Word("Hello", WordTag.UNKNOWN);  
    }
    
    /**
     * Tests InjectionProcedure
     * @throws FileNotFoundException 
     */
    @Test 
    public void shouldCreateAndWriteToTestDoc()
            throws FileNotFoundException
    {
        InjectionProcedure ip;
        FileOutputStream   fosOutputStream;
        ArrayList<Word>    alWord;
        
        fosOutputStream = new FileOutputStream(new File("testfile\\out1.txt"));
        ip              = new InjectionProcedure();
        alWord          = new ArrayList<>();
        
        alWord.add(new Word("WRITE TEST 1\n\r", WordTag.KEYWORD));
        alWord.add(new Word("WRITE TEST 2\n\r", WordTag.KEYWORD));
        alWord.add(new Word("WRITE TEST 3\n\r", WordTag.KEYWORD));
        alWord.add(new Word("....\n\r", WordTag.KEYWORD));
        
        assertEquals(true, ip.execute(alWord, fosOutputStream, Boolean.TRUE));
        
        try
        {
            fosOutputStream.close();
        }
        catch (IOException err){}
    }
    
    @Test
    public void shouldExecuteDifferentInstructions()
            throws FileNotFoundException
    {
        ArrayList<Procedure> alProcedure;
        ArrayList<Word>      alWord;
        FileOutputStream     fosOutputStream;
        
        alProcedure     = new ArrayList<>();
        alWord          = new ArrayList<>();
        fosOutputStream = new FileOutputStream(new File("testfile\\out2.txt"));
        
        alProcedure.add(new TestProcedure());
        alProcedure.add(new InjectionProcedure());
       
        alWord.add(new Word("WRITE TEST 1\n\r", WordTag.KEYWORD));
        alWord.add(new Word("WRITE TEST 2\n\r", WordTag.KEYWORD));
        alWord.add(new Word("WRITE TEST 3\n\r", WordTag.KEYWORD));
        alWord.add(new Word("....\n\r", WordTag.KEYWORD));
        
        assertEquals("Testing TestProcedure...", alProcedure.get(0).execute(0, 0, 0));
        assertEquals(true, ((InjectionProcedure)alProcedure.get(1)).execute(alWord, fosOutputStream, Boolean.TRUE));
        
        try
        {
            fosOutputStream.close();
        }
        catch (IOException err){}
        
       
    }
}
