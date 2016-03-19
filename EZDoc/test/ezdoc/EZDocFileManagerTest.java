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
    
    @Test
    void shouldOpenFolderTestAndStepBackOut()
    {
        FileViewer fView = new FileViewer();
        
        assertEquals(true, fView.openFolder(null, "testfile"));
        assertEquals("testfile", fView.getCurrentFolder());
        assertEquals(true, fView.closeFolder());
        assertEquals("ez-doc", fView.getCurrentFolder());    
    }
    
    @Test
    void shouldAcquireContentsOfDumbyFolder()
    {
        FileViewer fView = new FileViewer();
        ArrayList<String> data;
        
        assertEquals(true, fView.openFolder(null, "testfile"));
        assertEquals(true, fView.openFolder(null, "dumbyfolder"));
        assertEquals("dumbyfolder", fView.getCurrentFolder());
        assertEquals("ez-doc", fView.getCurrentFolder()); 
        
        data = fView.getFolderContents();
        assertEquals(3, data.size());
        assertEquals("a_folder",  data.get(0));
        assertEquals("a_file.txt",    data.get(1));
        assertEquals("a_picture.bmp", data.get(2));
        fView.closeFolder();
        fView.closeFolder();
    }
}
