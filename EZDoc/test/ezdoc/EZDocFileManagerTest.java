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
import java.util.ArrayList;

/**
 *
 * @author Antony
 */
public class EZDocFileManagerTest {
    
    public EZDocFileManagerTest() {
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
     * Simple folder Navigation
     */
    @Test
    public void shouldOpenFolderTestAndStepBackOut()
    {
        FileNavigator fView = new FileNavigator();
        
        assertEquals(true, fView.openFolder("testfile"));
        assertEquals("testfile", fView.getCurrentFolder());
        assertEquals(true, fView.closeFolder());
        assertEquals("EZDoc", fView.getCurrentFolder());    
    }
    
    /**
     * Goes to dumbyfolder and acquires contents of folder
     */
    @Test
    public void shouldAcquireContentsOfDumbyFolder()
    {
        FileNavigator fView = new FileNavigator();
        ArrayList<String> data;
        
        assertEquals(true, fView.openFolder("testfile"));
        assertEquals(true, fView.openFolder("dumbyfolder"));
        assertEquals("dumbyfolder", fView.getCurrentFolder());
        
        data = fView.getFolderContents();
        assertEquals(3, data.size());
        assertEquals("a_file.txt",    data.get(0));       
        assertEquals("a_folder",      data.get(1));
        assertEquals("a_picture.bmp", data.get(2));
        assertEquals(true, fView.closeFolder());
        assertEquals(true, fView.closeFolder());
    }
    
    /**
     * Fails attempt to step out of application folder.
     */
    @Test
    public void shouldFailToStepOutOfFolder()
    {
        FileNavigator fView = new FileNavigator();
        assertEquals(true, fView.closeFolder());
        assertEquals(true, fView.closeFolder());
        assertEquals(true, fView.closeFolder());
        assertEquals(true, fView.closeFolder());
        assertEquals(true, fView.closeFolder());
        assertEquals(true, fView.closeFolder());
        assertEquals(true, fView.closeFolder());
        assertEquals(false, fView.closeFolder());
    }
}
