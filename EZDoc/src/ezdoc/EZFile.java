/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ezdoc;
import java.io.*;
import java.util.ArrayList;

/**
 * Performs the following tasks:
 *  -Basic file I/O. 
 *  -Parses file
 *  -Apply basic filter to non-space, new-line, or carriage return characters.
 * @author Antony
 * February 16, 2016
 */
public class EZFile {
    private ArrayList<String> alParsedBuffer;    
    private FileInputStream   fisStream;
    private FileOutputStream  fosStream;
    private StringBuilder     bldExtractor;    
    private String            strFileName;
    private String            strPathName; 
    private byte[]            bBuffer;
    private int               iBufferSize;
    private int               iRead;
    private int               iAppendPos;
    private IOFlag            ioFlag;
    
    public EZFile()
    {
        alParsedBuffer  = null;        
        fisStream       = null;
        strFileName     = null;
        bldExtractor    = null;
        strPathName     = null;
        bBuffer         = null;
        iBufferSize     = 0;
        iRead           = 0;
        iAppendPos      = 0;
        ioFlag          = IOFlag.NONE;
    }
    
    /**
     * Inspect buffer size
     * @return int, size of buffer
     */
    public int getBufferSize()
    {
        return (iBufferSize);
    }
    
    /**
     * Inspect/Mutate Buffer
     * @return byte[], data to write to or read from file
     */
    public byte[] getBuffer()
    {
        return (bBuffer);
    }
    
    /**
     * Inspect/Mutate parsed buffer elements
     * @return List of elements of buffer
     */
    public ArrayList<String> getParsedBuffer()
    {
        return (alParsedBuffer);
    }
    
    /**
     * Current total byts read from file
     * @return int, bytes read from file
     */
    public int getTotalBytesRead()
    {
        return (iRead);
    }
    
    /**
     * Reallocates buffer to new size
     * @param _iBufSize[in] new buffer size
     * @return Boolean, true if the buffer was reset else false
     */
    public Boolean setBufferSize(int _iBufSize)
    {   
        if (_iBufSize < 1)
            return (false);
        
        // Ensure there is enough memory allocated for buffer
        bBuffer        = new byte[_iBufSize];
        iBufferSize    = _iBufSize;
        return (true);
    }
    
    /**
     * Opens/Creates file for I/O operations
     * @param _strPath[in] file path, exclude if fle in current working dir.
     * @param _strFile[in] file name
     * @param _ioFlag[in] enumeration representing input or output
     * @return Boolean, true on successful file instantiation else false.
     */
    public Boolean open(String _strPath, String _strFile, IOFlag _ioFlag)
    {
        String strFile = _strPath;
        strPathName = _strPath;
        strFileName = _strFile;
        
        // Copy file information
        if (strFile != null)
            strFile += _strFile;
        else
            strFile = _strFile;
        
        // Close file
        this.close();
        
        // Open file
        try
        {
            if ((ioFlag = _ioFlag) == IOFlag.READ)
                fisStream = new FileInputStream(new File(strFile));
            else
                fosStream = new FileOutputStream(new File(strFile));   
            
            // Initialize parsed list
            alParsedBuffer = new ArrayList<>();
        }
        catch (FileNotFoundException err)
        {
            System.out.println("Error : " + err.getMessage());
            return (false);
        }
        
        return (true);
    }
    
    /**
     * Closes open file
     */
    public void close()
    {
        try
        {
            if (ioFlag == IOFlag.READ)
            {
                if (fisStream != null)
                    fisStream.close();
            }
            else
            {
                if (ioFlag == IOFlag.WRITE)
                {
                    if (fosStream != null)
                        fosStream.close();
                }
            }
            
            ioFlag = IOFlag.NONE;
        }
        catch (IOException err)
        {
            System.out.println("Failed to close : " + strPathName + strFileName);
        }
    }        
    
    /**
     * Separates buffer into an ArrayList, filter may be applied to buffer to 
     * create more buffer elements
     * @param _strFilter[in] character sequences to search for in buffer
     * @return Boolean, true if buffer was organized else false 
     */
    public Boolean parseBuffer(String[] _strFilter)
    {
        if (bBuffer != null)
        {
            ArrayList<Integer> alPosition = new ArrayList<>();
            int icStringsFound            = 0;
            int iStartPos                 = 0;
            int iFilterPos                = -1;
            int iSize                     = 0;
            boolean bFoundSpace           = false;
            boolean bCharRead             = false;
            boolean bAppendThisCycle      = false;
            boolean bAppendNextCycle      = false;
            
            // Go through folder and find spaces
            for (int i = 0; i < iBufferSize; i++)
            { 
                if (bBuffer[i] == ' '  || bBuffer[i] == '\n' ||
                    bBuffer[i] == '\r' || bBuffer[i] == '\t')
                {
                    bFoundSpace = true;
                    bBuffer[i] = '\0';
                    
                    if (i == iStartPos)
                        ++iStartPos;
                    
                    if (i > 0 && bBuffer[i - 1] != '\0')
                        bCharRead = true;
                }
                else
                {
                    // If current byte != <space> and prev char was space
                    if (!bFoundSpace)
                         bCharRead = true;
                    else
                    {               
                        alPosition.add(iStartPos);
                        bCharRead = ((i + 1) >= iBufferSize);
                        bFoundSpace = false;
                        iStartPos = i;
                    }
                }               
            }
            
            // For unhandled sequences
            if (bCharRead)
                alPosition.add(iStartPos);           
            
            icStringsFound = alPosition.size();
            
            // If initial char not null, then append
            if (bBuffer[0] != '\0' && bldExtractor != null)
                bAppendThisCycle = true;
            
            // Do we need to append next cycle to last string?
            bAppendNextCycle = (bBuffer[iBufferSize - 1] != '\0');
            
            // Parse Buffer
            for (int i = 0; i < icStringsFound; i++)
            {  
                // Erase data
                if (!bAppendThisCycle)
                    bldExtractor = new StringBuilder();
               
                iStartPos = alPosition.get(i);
                iSize = alParsedBuffer.size() - 1;
                    
                // Apply Filter to buffer
               if ((iFilterPos = applyFilter(_strFilter, iStartPos)) == -1)
               {
                       iFilterPos = iBufferSize;           
                       
                       if (bAppendThisCycle)
                           ++iAppendPos;
                       else
                       {
                            if ((i + 1) >= icStringsFound)
                              iAppendPos = iSize;
                       }                      
               }
               else
               {                
                   if (!bAppendThisCycle)
                        iAppendPos = alParsedBuffer.size() - 1;
                   else
                       ++iAppendPos;               
               }
               
                // Create string of word parsed
                while (iStartPos < iFilterPos && bBuffer[iStartPos] != '\0')
                {
                    bldExtractor.append((char)bBuffer[iStartPos]);
                    bBuffer[iStartPos++] = 0;
                }
                
                // Add sequence to list
                if (bAppendThisCycle)
                {
                    if (bldExtractor.length() > 0)
                            alParsedBuffer.set(iAppendPos, bldExtractor.substring(0));
                    
                    bAppendThisCycle = false;
                }
                else
                {
                    if (bldExtractor.length() > 0)
                        alParsedBuffer.add(iSize + 1, bldExtractor.substring(0));
                }
            }
            
            // Reset for next cycle if no appending neccisary
            if (!bAppendNextCycle)
                bldExtractor = null;
            else
            {               
                if (iAppendPos + 1 < alParsedBuffer.size())
                    bldExtractor = new StringBuilder(alParsedBuffer.get(iAppendPos + 1));
            }
        }
        
        return (true);
    }
    
    /**
     * Read from file
     * @param _iOffSet[in] offset from current position
     * @return boolean, true on successful read else false
     */
    public Boolean read(int _iOffSet)
    {
        // If file was opened for reading
        if (ioFlag == IOFlag.READ && fisStream != null && bBuffer != null)
        {
            // Reset buffer
            setBytes(0, iRead, (byte)0x00);
            
            try
            {
               iRead = fisStream.read(bBuffer, _iOffSet, iBufferSize);
            }
            catch(IOException err)
            {
                System.out.println("Error : " + err.getMessage());
                return (false);
            }
         
            return (iRead > 0);
        }
        
        return (false);
    }
    
     /**
     * Read from file
     * @return boolean, true on successful read else false
     */
    public Boolean read()
    {
        return (read(0));
    }
    
    /**
     * Write to file
     * @param _bData[in] data to write
     * @param _iOffSet[in] offset from current position
     * @param _iLength[in] length of buffer
     * @return Boolean, true on success else false
     */
    public Boolean write(byte[] _bData, int _iOffSet, int _iLength)            
    {
        if (fosStream != null && ioFlag == IOFlag.WRITE)
        {
            try
            {
                fosStream.write(_bData, _iOffSet, _iLength);
            }
            catch (IOException err)
            {
                System.out.println("Error : " + err.getMessage());
                return (false);
            }
            return (true);
        }
        return (false);
    }
    
    /**
     * Reads and Parses entire file with filter
     * @param _strFilter[in] filter for file
     * @return Boolean, true if data was read from file
     */
    public Boolean parseFile(String[] _strFilter)
    {
        boolean bBufferProcessed = false;
        
        // While data is being read
        while (read())
        {
            bBufferProcessed = true;

            parseBuffer(_strFilter);
        }
        
        return (bBufferProcessed);
    }
    
    /**
     *  Returns file contents in a String object
     *  @return String, file data
     */
    public String readFile()
    {
        StringBuilder bld = new StringBuilder();
       
        // While data is being read
        while (read())
            bld.append(new String(bBuffer));
        
        return (bld.substring(0));
    }
    
    /**
     * Finds Char sequences based on filter specifications and adds to list
     * @param _strFilter[in] buffer filter
     * @param _iStartIndex[in] current position in buffer
     * @return int, position of first character for the first filter found else 
     *         -1.
     */
    protected int applyFilter(String[] _strFilter, int _iStartIndex)
    {
        if (_strFilter != null)
        {
            ArrayList<Integer> alIndices = new ArrayList<>();
            ArrayList<Integer> alFilter  = new ArrayList<>();
            StringBuilder strCpy         = null;     
            int icFilterFound            = 0;
            int iFilterLength            = 0;
            int iIndex                   = 0;
            int iReturn                  = -1;
            int icFilter                 = _strFilter.length;
            int[][] iFilter              = new int[icFilter][2];
            byte[][] bFilter             = new byte[icFilter][0];

            // Initialize filter index.
            for (int i = 0; i < icFilter; i++)
            {
                iFilter[i][0] = -1;
                iFilter[i][1] = 0;
                bFilter[i]    = _strFilter[i].getBytes();
            }

            // Search through the byte sequence to find substrings.
            while (_iStartIndex < iBufferSize && bBuffer[_iStartIndex] != '\0')
            {
                for (int i = 0; i < icFilter; i++)
                {
                    if (bFilter[i][iFilter[i][1]] == bBuffer[_iStartIndex])
                    {
                        // If first occurence of the filter was found
                        if (iFilter[i][1] == 0)
                        {
                            if (iReturn == -1)
                                iReturn = _iStartIndex;
                            
                            iFilter[i][0] = _iStartIndex;
                            iFilter[i][1] = 1;
                        }
                        else
                            ++iFilter[i][1];

                        // If Filter found then add to list for parsing
                        if (iFilter[i][1] == _strFilter[i].length())
                        {
                            alIndices.add(iFilter[i][0]);
                            alFilter.add(i);
                            iFilter[i][0] = -1;
                            iFilter[i][1] = 0;
                        }
                    }
                }
                
                ++_iStartIndex;
            }

            icFilterFound = alIndices.size();
            
            // Parse byte sequence
            for (int i = 0; i < icFilterFound; i++)
            {
                iFilterLength =  _strFilter[alFilter.get(i)].length();
                iIndex = alIndices.get(i);

                // Add Filte to the Parsed list.
                alParsedBuffer.add(_strFilter[alFilter.get(i)]);

                // Erase Builder data
                strCpy = new StringBuilder();

                // Erase filter from buffer;
                setBytes(iIndex, iIndex + iFilterLength, (byte)0x00);

                // Strip Non-Filter type data and insert into final 
                // Parsed BufferL List
                if ((i + 1) >= icFilterFound)
                {
                    iIndex += iFilterLength;
                    iFilterLength = 0;
                    
                    for (int j = iIndex; j < iBufferSize && bBuffer[j] != '\0'; j++, iFilterLength++)
                        strCpy.append((char)bBuffer[j]);

                    // Erase data
                    setBytes(iIndex, iIndex + iFilterLength, (byte)0x00);

                    // Save data
                    alParsedBuffer.add(strCpy.substring(0));
                }
                else
                {
                    iIndex += iFilterLength;
                    iFilterLength = alIndices.get(i + 1);

                    for (int j = iIndex; j < iFilterLength; j++)
                        strCpy.append((char)bBuffer[j]);

                    // Erase data
                    setBytes(iIndex, iFilterLength, (byte)0x00);

                    // Save data
                    alParsedBuffer.add(strCpy.substring(0));  
                }
            }
            
            return (iReturn);
        }
        
        return (-1);
    }

    /**
     * Set buffer block to some value
     * @param _iStart[in] start pos
     * @param _iEnd[in] ens pos
     * @param _bByte[in] value to replace buffer block with
     */
    private void setBytes(int _iStart, int _iEnd, byte _bByte)
    {
        if (_iStart > -1 && _iEnd <= iBufferSize)
        {
            for (int i = _iStart; i < _iEnd; i++)
               bBuffer[i] = _bByte;
        }
    }
}
