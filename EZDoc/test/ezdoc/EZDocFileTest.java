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
public class EZDocFileTest {
    
    public EZDocFileTest() {
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
     *  Opens and writes to file hello.txt -> "Hello"
     */
    @Test
    public void shouldOpenAndWriteHelloFileToFile()
    {
        EZFile file;
        String hw = "Hello\r\n";
        
        file = new EZFile();
        assertEquals(true, file.open("testfile\\", "hellofile.txt", IOFlag.WRITE));
        assertEquals(true, file.write(hw.getBytes(), 0, 0));      
    }
    
    /**
     *  Opens and writes to a file sentenceFile.txt
     */
    @Test
    public void shouldOpenAndWriteASentenceToFile()
    {
      EZFile file = new EZFile();
      String str = "Hello World! This is a TesT:Test@Test";
      assertEquals(true, file.open("testfile\\", "sentenceFile.txt", IOFlag.WRITE));
      assertEquals(true, file.write(str.getBytes(), 0, 0));   
    }
    
    /**
     *  Sets read write buffer to a 100bytes
     */
    @Test
    public void shouldSetBufferSizeTo100()
    {
        EZFile file = new EZFile();
        assertEquals(0, file.getBufferSize());
        assertEquals(true, file.setBufferSize(100));
        assertEquals(100, file.getBufferSize());
        assertEquals(false, file.setBufferSize(-1));
        assertEquals(100, file.getBufferSize());
    }
    
    /**
     *  Reads data from file and returns the buffer storing the data
     */
    @Test
    public void shouldReadFileContainingHelloWorld()
    {
        EZFile file = new EZFile();
        
        assertEquals(true, file.open("testfile\\", "t1Test.txt", IOFlag.READ));
        assertEquals(true, file.setBufferSize("Hello World!".length()));
        assertEquals(true, file.read(0));
        assertEquals(true, "Hello World!".equals(new String(file.getBuffer())));
        file.close();
    }
    
    /**
     *  Reads file and parses the data based on: non-white, carriage return, or 
     *  new-line feed.
     */
    @Test
    public void shouldParseFileAndReturnAsArrayList()
    {
        EZFile file = new EZFile();
        ArrayList<String> parse = null;
        
        assertEquals(true, file.open("testfile\\", "t2Test.txt", IOFlag.READ));
        assertEquals(true, file.setBufferSize("Hello World! This is a TesT:Test@Test".length()));
        assertEquals(true, file.read());
        assertEquals(true, "Hello World! This is a TesT:Test@Test".equals(new String(file.getBuffer())));
        assertEquals(true, file.parseBuffer(null));
        
        parse = file.getParsedBuffer();
        
        assertEquals(6, parse.size());
        assertEquals("Hello",          parse.get(0));
        assertEquals("World!",         parse.get(1));
        assertEquals("This",           parse.get(2));
        assertEquals("is",             parse.get(3));
        assertEquals("a",              parse.get(4));
        assertEquals("TesT:Test@Test", parse.get(5));        
        file.close();
    }
    
    /**
     *  Reads file and parses the data based on: non-white, carriage return, or 
     *  new-line feed, and applies a filter to extract more meaningful 
     *  information. 
     */
    @Test
    public void shouldParseFileWithFilter()
    {
        EZFile file = new EZFile();
        ArrayList<String> parse = null;
        String[] strFilter = new String[2];
        
        strFilter[0] = ":";
        strFilter[1] = "@";
        
        assertEquals(true, file.open("testfile\\", "t3Test.txt", IOFlag.READ));
        assertEquals(true, file.setBufferSize("Hello World! This is a TesT:Test@Test".length()));
        assertEquals(true, file.read());
        assertEquals(true, "Hello World! This is a TesT:Test@Test".equals(new String(file.getBuffer())));
        assertEquals(true, file.parseBuffer(strFilter));
        
        parse = file.getParsedBuffer();
        
        assertEquals(10, parse.size());
        assertEquals("Hello",          parse.get(0));
        assertEquals("World!",         parse.get(1));
        assertEquals("This",           parse.get(2));
        assertEquals("is",             parse.get(3));
        assertEquals("a",              parse.get(4));
        assertEquals("TesT",           parse.get(5));        
        assertEquals(":",              parse.get(6));
        assertEquals("Test",           parse.get(7));
        assertEquals("@",              parse.get(8));
        assertEquals("Test",           parse.get(9));
        file.close(); 
    }
    
    /**
     *
     */
    @Test 
    public void shouldParseFileWithBufferSmallerThanTotalToRead()
    {
        EZFile file = new EZFile();
        ArrayList<String> parse = null;
        String[] strFilter = new String[2];
        
        strFilter[0] = ":";
        strFilter[1] = "@";
        
        assertEquals(true, file.open("testfile\\", "t3Test.txt", IOFlag.READ));
        assertEquals(true, file.setBufferSize("Hello World! This is a TesT:Test@Test".length() - 21));
        assertEquals(true, file.read());
        assertEquals(true, file.parseBuffer(strFilter));
        assertEquals(true, file.read());
        assertEquals(true, file.parseBuffer(strFilter));
        assertEquals(true, file.read());
        assertEquals(true, file.parseBuffer(strFilter));
        
        parse = file.getParsedBuffer();
        
        assertEquals(10, parse.size());
        assertEquals("Hello",          parse.get(0));
        assertEquals("World!",         parse.get(1));
        assertEquals("This",           parse.get(2));
        assertEquals("is",             parse.get(3));
        assertEquals("a",              parse.get(4));
        assertEquals("TesT",           parse.get(5));        
        assertEquals(":",              parse.get(6));
        assertEquals("Test",           parse.get(7));
        assertEquals("@",              parse.get(8));
        assertEquals("Test",           parse.get(9));
        file.close();      
    }
    
    
    @Test
    public void shouldReadAndParseFileAutomaticallyWithFilter()
    {
       EZFile file = new EZFile();
       String[] strFilter = { ":", "#", "+", "@", "QUIT" };
       ArrayList<String> parse;
       
       file.setBufferSize(10);
       assertEquals(true, file.open("testfile\\", "t5Test.txt", IOFlag.READ));
       assertEquals(true, file.processFile(strFilter));
       parse = file.getParsedBuffer();
       assertEquals(20, parse.size());
       
       for (String s : parse)
           System.out.println(s);
       
       assertEquals("Hello",          parse.get(0));
       assertEquals("World!",         parse.get(1));
       assertEquals("This",           parse.get(2));
       assertEquals("is",             parse.get(3));
       assertEquals("a",              parse.get(4));
       assertEquals("TesT",           parse.get(5));        
       assertEquals(":",              parse.get(6));
       assertEquals("Test",           parse.get(7));
       assertEquals("@",              parse.get(8));
       assertEquals("Test",           parse.get(9));
       assertEquals("Hello",          parse.get(10));
       assertEquals("World!",         parse.get(11));
       assertEquals("This",           parse.get(12));
       assertEquals("is",             parse.get(13));
       assertEquals("a",              parse.get(14));
       assertEquals("TesT",           parse.get(15));        
       assertEquals(":",              parse.get(16));
       assertEquals("Test",           parse.get(17));
       assertEquals("@",              parse.get(18));
       assertEquals("Test",           parse.get(19));
       file.close();         
    }
  
}
