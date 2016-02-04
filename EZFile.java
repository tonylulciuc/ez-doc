/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ezdoc;
import java.io.*;

/**
 *
 * @author Antony
 */
public class EZFile {
    private File fHandle; 
    private String strFileName;
    private String strPathName;
    private byte[] bBuffer;
    private byte[][] bParsedBuffer;
    private int iBufferSize;
    
    public EZFile()
    {
        fHandle         = null;
        strFileName     = null;
        strPathName     = null;
        bBuffer         = null;
        bParsedBuffer   = null;
        iBufferSize     = 0;
    }
    
    public Boolean setBufferSize(int _iBufSize)
    {   
        if (_iBufSize < 1)
            return (false);
        
        // Ensure there is enough memory allocated for buffer
        assert (bBuffer = new byte[_iBufSize + 1]) != null : "Error - OUT OF MEMORY!";
        bParsedBuffer = null;
        iBufferSize   = _iBufSize;
        return (true);
    }
    
    public Boolean open(String _strPath, String _strFile)
    {
        return (false);
    }
    
    public void close()
    {
        
    }        
    
    public Boolean parseBufer()
    {
        return (false);
    }
    
    public byte[] getBuffer()
    {
        return (bBuffer);
    }
    
    public byte[][] getParsedBuffer()
    {
        return (bParsedBuffer);
    }
}
