/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ezdoc;

import ezdoc.EZFile;
import ezdoc.IOFlag;
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
    //@Test
    /*This test ensures that the command array is being read in correctly*/
    @Test
    public void shouldReadCommandFileAndCreateArray() {

        EZFile file = new EZFile();

        file.open("testfile\\", "Commands.txt", IOFlag.WRITE);

        String str = "@\r\ntest\r\nreturn: returns\r\nparam: param \r\nauthor: author";
        assertEquals(true, file.write(str.getBytes(), 0, str.length()));

        ScriptInterpreter si = new ScriptInterpreter("Test");
         assertEquals(si.commandsAndMethods[0][0], "return");
         assertEquals(si.commandsAndMethods[1][0], "param");
         assertEquals(si.commandsAndMethods[2][0], "author");
         
       
        file.close();

    }

    @Test
    public void shouldCompareCommandFileAndPassedString() {
        String s = "Class this class is being tested Test File";
        ScriptInterpreter si = new ScriptInterpreter(s);

        assertEquals(true, si.commandFound);
    }

    @Test
    public void shouldRemoveAllSouceCodeFromMethods() {
        String input = "package ezdoc;\n"
                + "import java.io.*;\n"
                + "import java.util.ArrayList;\n"
                + "\n"
                + "/**\n"
                + " * Performs the following tasks:\n"
                + " *  -Basic file I/O. \n"
                + " *  -Parses file\n"
                + " *  -Apply basic filter to non-space, new-line, or carriage return characters.\n"
                + " * @author Antony\n"
                + " * February 16, 2016\n"
                + " */\n"
                + "public class EZFile {\n"
                + "    private ArrayList<String> alParsedBuffer;  \n"
                + "    private ArrayList<Integer> alFilterIndex;\n"
                + "    private FileInputStream   fisStream;\n"
                + "    private FileOutputStream  fosStream;\n"
                + "    private StringBuilder     bldExtractor;    \n"
                + "    private String            strFileName;\n"
                + "    private String            strPathName; \n"
                + "    private byte[]            bBuffer;\n"
                + "    private int               iBufferSize;\n"
                + "    private int               iRead;\n"
                + "    private int               iAppendPos;\n"
                + "    private IOFlag            ioFlag;\n"
                + "    \n"
                + "    public EZFile()\n"
                + "    {\n"
                + "        alParsedBuffer  = null;     \n"
                + "        alFilterIndex   = null;\n"
                + "        fisStream       = null;\n"
                + "        strFileName     = null;\n"
                + "        bldExtractor    = null;\n"
                + "        strPathName     = null;\n"
                + "        bBuffer         = null;\n"
                + "        iBufferSize     = 0;\n"
                + "        iRead           = 0;\n"
                + "        iAppendPos      = 0;\n"
                + "        ioFlag          = IOFlag.NONE;\n"
                + "    }\n"
                + "    \n"
                + "    /**\n"
                + "     * Inspect buffer size\n"
                + "     * @return int, size of buffer\n"
                + "     */\n"
                + "    public int getBufferSize()\n"
                + "    {\n"
                + "        return (iBufferSize);\n"
                + "    }\n"
                + "    \n"
                + "    /**\n"
                + "     * Inspect/Mutate Buffer\n"
                + "     * @return byte[], data to write to or read from file\n"
                + "     */\n"
                + "    public byte[] getBuffer()\n"
                + "    {\n"
                + "        return (bBuffer);\n"
                + "    }\n"
                + "    \n"
                + "    /**\n"
                + "     * Inspect/Mutate parsed buffer elements\n"
                + "     * @return List of elements of buffer\n"
                + "     */\n"
                + "    public ArrayList<String> getParsedBuffer()\n"
                + "    {\n"
                + "        return (alParsedBuffer);\n"
                + "    }\n"
                + "    \n"
                + "    /**\n"
                + "     * Inspect/Mutate filter indices\n"
                + "     * @return ArrayList<Integer>, indices of where filters are located in parsed list\n"
                + "     */\n"
                + "    public ArrayList<Integer> getFilterIndex()\n"
                + "    {\n"
                + "        return (alFilterIndex);\n"
                + "    }\n"
                + "    \n"
                + "    /**\n"
                + "     * Current total bytes read from file\n"
                + "     * @return int, bytes read from file\n"
                + "     */\n"
                + "    public int getTotalBytesRead()\n"
                + "    {\n"
                + "        return (iRead);\n"
                + "    }\n"
                + "    \n"
                + "    /**\n"
                + "     * Reallocates buffer to new size\n"
                + "     * @param _iBufSize[in] new buffer size\n"
                + "     * @return Boolean, true if the buffer was reset else false\n"
                + "     */\n"
                + "    public Boolean setBufferSize(int _iBufSize)\n"
                + "    {   \n"
                + "        if (_iBufSize < 1)\n"
                + "            return (false);\n"
                + "        \n"
                + "        // Ensure there is enough memory allocated for buffer\n"
                + "        bBuffer        = new byte[_iBufSize];\n"
                + "        iBufferSize    = _iBufSize;\n"
                + "        return (true);\n"
                + "    }\n"
                + "    \n"
                + "    /**\n"
                + "     * Opens/Creates file for I/O operations\n"
                + "     * @param _strPath[in] file path, exclude if fle in current working dir.\n"
                + "     * @param _strFile[in] file name\n"
                + "     * @param _ioFlag[in] enumeration representing input or output\n"
                + "     * @return Boolean, true on successful file instantiation else false.\n"
                + "     */\n"
                + "    public Boolean open(String _strPath, String _strFile, IOFlag _ioFlag)\n"
                + "    {\n"
                + "        String strFile = _strPath;\n"
                + "        strPathName = _strPath;\n"
                + "        strFileName = _strFile;\n"
                + "        \n"
                + "        // Copy file information\n"
                + "        if (strFile != null)\n"
                + "            strFile += _strFile;\n"
                + "        else\n"
                + "            strFile = _strFile;\n"
                + "        \n"
                + "        // Close file\n"
                + "        this.close();\n"
                + "        \n"
                + "        // Open file\n"
                + "        try\n"
                + "        {\n"
                + "            if ((ioFlag = _ioFlag) == IOFlag.READ)\n"
                + "                fisStream = new FileInputStream(new File(strFile));\n"
                + "            else\n"
                + "                fosStream = new FileOutputStream(new File(strFile));   \n"
                + "            \n"
                + "            // Initialize parsed list\n"
                + "            alParsedBuffer = new ArrayList<>();\n"
                + "            alFilterIndex  = new ArrayList<>();\n"
                + "        }\n"
                + "        catch (FileNotFoundException err)\n"
                + "        {\n"
                + "            System.out.println(\"Error : \" + err.getMessage());\n"
                + "            return (false);\n"
                + "        }\n"
                + "        \n"
                + "        return (true);\n"
                + "    }\n"
                + "    \n"
                + "    /**\n"
                + "     * Closes open file\n"
                + "     */\n"
                + "    public void close()\n"
                + "    {\n"
                + "        try\n"
                + "        {\n"
                + "            if (ioFlag == IOFlag.READ)\n"
                + "            {\n"
                + "                if (fisStream != null)\n"
                + "                    fisStream.close();\n"
                + "            }\n"
                + "            else\n"
                + "            {\n"
                + "                if (ioFlag == IOFlag.WRITE)\n"
                + "                {\n"
                + "                    if (fosStream != null)\n"
                + "                        fosStream.close();\n"
                + "                }\n"
                + "            }\n"
                + "            \n"
                + "            ioFlag = IOFlag.NONE;\n"
                + "        }\n"
                + "        catch (IOException err)\n"
                + "        {\n"
                + "            System.out.println(\"Failed to close : \" + strPathName + strFileName);\n"
                + "        }\n"
                + "    }        \n"
                + "    \n"
                + "    /**\n"
                + "     * Separates buffer into an ArrayList, filter may be applied to buffer to \n"
                + "     * create more buffer elements\n"
                + "     * @param _strFilter[in] character sequences to search for in buffer\n"
                + "     * @return Boolean, true if buffer was organized else false \n"
                + "     */\n"
                + "    public Boolean parseBuffer(String[] _strFilter)\n"
                + "    {\n"
                + "        if (bBuffer != null)\n"
                + "        {\n"
                + "            ArrayList<Integer> alPosition = new ArrayList<>();\n"
                + "            int icStringsFound            = 0;\n"
                + "            int iStartPos                 = 0;\n"
                + "            int iFilterPos                = -1;\n"
                + "            int iSize                     = 0;\n"
                + "            boolean bFoundSpace           = false;\n"
                + "            boolean bCharRead             = false;\n"
                + "            boolean bAppendThisCycle      = false;\n"
                + "            boolean bAppendNextCycle      = false;\n"
                + "            \n"
                + "            // Go through folder and find spaces\n"
                + "            for (int i = 0; i < iBufferSize; i++)\n"
                + "            { \n"
                + "                if (bBuffer[i] == ' '  || bBuffer[i] == '\\n' ||\n"
                + "                    bBuffer[i] == '\\r' || bBuffer[i] == '\\t')\n"
                + "                {\n"
                + "                    bFoundSpace = true;\n"
                + "                    bBuffer[i] = '\\0';\n"
                + "                    \n"
                + "                    if (i == iStartPos)\n"
                + "                        ++iStartPos;\n"
                + "                    \n"
                + "                    if (i > 0 && bBuffer[i - 1] != '\\0')\n"
                + "                        bCharRead = true;\n"
                + "                }\n"
                + "                else\n"
                + "                {\n"
                + "                    // If current byte != <space> and prev char was space\n"
                + "                    if (!bFoundSpace)\n"
                + "                         bCharRead = true;\n"
                + "                    else\n"
                + "                    {               \n"
                + "                        alPosition.add(iStartPos);\n"
                + "                        bCharRead = ((i + 1) >= iBufferSize);\n"
                + "                        bFoundSpace = false;\n"
                + "                        iStartPos = i;\n"
                + "                    }\n"
                + "                }               \n"
                + "            }\n"
                + "            \n"
                + "            // For unhandled sequences\n"
                + "            if (bCharRead)\n"
                + "                alPosition.add(iStartPos);           \n"
                + "            \n"
                + "            icStringsFound = alPosition.size();\n"
                + "            \n"
                + "            // If initial char not null, then append\n"
                + "            if (bBuffer[0] != '\\0' && bldExtractor != null)\n"
                + "                bAppendThisCycle = true;\n"
                + "            \n"
                + "            // Do we need to append next cycle to last string?\n"
                + "            bAppendNextCycle = (bBuffer[iBufferSize - 1] != '\\0');\n"
                + "            \n"
                + "            // Parse Buffer\n"
                + "            for (int i = 0; i < icStringsFound; i++)\n"
                + "            {  \n"
                + "                // Erase data\n"
                + "                if (!bAppendThisCycle)\n"
                + "                    bldExtractor = new StringBuilder();\n"
                + "               \n"
                + "                iStartPos = alPosition.get(i);\n"
                + "                iSize = alParsedBuffer.size() - 1;\n"
                + "                    \n"
                + "                // Apply Filter to buffer\n"
                + "               if ((iFilterPos = applyFilter(_strFilter, iStartPos)) == -1)\n"
                + "               {\n"
                + "                       iFilterPos = iBufferSize;           \n"
                + "                       \n"
                + "                       if (bAppendThisCycle)\n"
                + "                           ++iAppendPos;\n"
                + "                       else\n"
                + "                       {\n"
                + "                            if ((i + 1) >= icStringsFound)\n"
                + "                              iAppendPos = iSize;\n"
                + "                       }                      \n"
                + "               }\n"
                + "               else\n"
                + "               {                \n"
                + "                   if (!bAppendThisCycle)\n"
                + "                        iAppendPos = alParsedBuffer.size() - 1;\n"
                + "                   else\n"
                + "                       ++iAppendPos;               \n"
                + "               }\n"
                + "               \n"
                + "                // Create string of word parsed\n"
                + "                while (iStartPos < iFilterPos && bBuffer[iStartPos] != '\\0')\n"
                + "                {\n"
                + "                    bldExtractor.append((char)bBuffer[iStartPos]);\n"
                + "                    bBuffer[iStartPos++] = 0;\n"
                + "                }\n"
                + "                \n"
                + "                // Add sequence to list\n"
                + "                if (bAppendThisCycle)\n"
                + "                {\n"
                + "                    if (bldExtractor.length() > 0)\n"
                + "                    {\n"
                + "                            alParsedBuffer.set(iAppendPos, bldExtractor.substring(0));\n"
                + "                            \n"
                + "                            // Save filter index\n"
                + "                            if (iFilterPos != iBufferSize)\n"
                + "                                alFilterIndex.add(alParsedBuffer.size() - 2);\n"
                + "                    }\n"
                + "                    \n"
                + "                    bAppendThisCycle = false;\n"
                + "                }\n"
                + "                else\n"
                + "                {\n"
                + "                    if (bldExtractor.length() > 0)\n"
                + "                    {\n"
                + "                        alParsedBuffer.add(iSize + 1, bldExtractor.substring(0));\n"
                + "                        \n"
                + "                        // Save filter index\n"
                + "                        if (iFilterPos != iBufferSize)\n"
                + "                            alFilterIndex.add(alParsedBuffer.size() - 2);\n"
                + "                    }\n"
                + "                }\n"
                + "            }\n"
                + "            \n"
                + "            // Reset for next cycle if no appending neccisary\n"
                + "            if (!bAppendNextCycle)\n"
                + "                bldExtractor = null;\n"
                + "            else\n"
                + "            {               \n"
                + "                if (iAppendPos + 1 < alParsedBuffer.size())\n"
                + "                    bldExtractor = new StringBuilder(alParsedBuffer.get(iAppendPos + 1));\n"
                + "            }\n"
                + "        }\n"
                + "        \n"
                + "        return (true);\n"
                + "    }\n"
                + "    \n"
                + "    /**\n"
                + "     * Read from file\n"
                + "     * @param _iOffSet[in] offset from current position\n"
                + "     * @return boolean, true on successful read else false\n"
                + "     */\n"
                + "    public Boolean read(int _iOffSet)\n"
                + "    {\n"
                + "        // If file was opened for reading\n"
                + "        if (ioFlag == IOFlag.READ && fisStream != null && bBuffer != null)\n"
                + "        {\n"
                + "            // Reset buffer\n"
                + "            setBytes(0, iRead, (byte)0x00);\n"
                + "            \n"
                + "            try\n"
                + "            {\n"
                + "               iRead = fisStream.read(bBuffer, _iOffSet, iBufferSize);\n"
                + "            }\n"
                + "            catch(IOException err)\n"
                + "            {\n"
                + "                System.out.println(\"Error : \" + err.getMessage());\n"
                + "                return (false);\n"
                + "            }\n"
                + "         \n"
                + "            return (iRead > 0);\n"
                + "        }\n"
                + "        \n"
                + "        return (false);\n"
                + "    }\n"
                + "    \n"
                + "     /**\n"
                + "     * Read from file\n"
                + "     * @return boolean, true on successful read else false\n"
                + "     */\n"
                + "    public Boolean read()\n"
                + "    {\n"
                + "        return (read(0));\n"
                + "    }\n"
                + "    \n"
                + "    /**\n"
                + "     * Write to file\n"
                + "     * @param _bData[in] data to write\n"
                + "     * @param _iOffSet[in] offset from current position\n"
                + "     * @param _iLength[in] length of buffer\n"
                + "     * @return Boolean, true on success else false\n"
                + "     */\n"
                + "    public Boolean write(byte[] _bData, int _iOffSet, int _iLength)            \n"
                + "    {\n"
                + "        if (fosStream != null && ioFlag == IOFlag.WRITE)\n"
                + "        {\n"
                + "            try\n"
                + "            {\n"
                + "                fosStream.write(_bData, _iOffSet, _iLength);\n"
                + "            }\n"
                + "            catch (IOException err)\n"
                + "            {\n"
                + "                System.out.println(\"Error : \" + err.getMessage());\n"
                + "                return (false);\n"
                + "            }\n"
                + "            return (true);\n"
                + "        }\n"
                + "        return (false);\n"
                + "    }\n"
                + "    \n"
                + "    /**\n"
                + "     * Reads and Parses entire file with filter\n"
                + "     * @param _strFilter[in] filter for file\n"
                + "     * @return Boolean, true if data was read from file\n"
                + "     */\n"
                + "    public Boolean parseFile(String[] _strFilter)\n"
                + "    {\n"
                + "        boolean bBufferProcessed = false;\n"
                + "        \n"
                + "        // While data is being read\n"
                + "        while (read())\n"
                + "        {\n"
                + "            bBufferProcessed = true;\n"
                + "\n"
                + "            parseBuffer(_strFilter);\n"
                + "        }\n"
                + "        \n"
                + "        return (bBufferProcessed);\n"
                + "    }\n"
                + "    \n"
                + "    /**\n"
                + "     *  Returns file contents in a String object\n"
                + "     *  @return String, file data\n"
                + "     */\n"
                + "    public String readFile()\n"
                + "    {\n"
                + "        StringBuilder bld = new StringBuilder();\n"
                + "       \n"
                + "        // While data is being read\n"
                + "        while (read())\n"
                + "            bld.append(new String(bBuffer));\n"
                + "        \n"
                + "        return (bld.substring(0));\n"
                + "    }\n"
                + "    \n"
                + "    /**\n"
                + "     * Finds Char sequences based on filter specifications and adds to list\n"
                + "     * @param _strFilter[in] buffer filter\n"
                + "     * @param _iStartIndex[in] current position in buffer\n"
                + "     * @return int, position of first character for the first filter found else \n"
                + "     *         -1.\n"
                + "     */\n"
                + "    protected int applyFilter(String[] _strFilter, int _iStartIndex)\n"
                + "    {\n"
                + "        if (_strFilter != null)\n"
                + "        {\n"
                + "            ArrayList<Integer> alIndices = new ArrayList<>();\n"
                + "            ArrayList<Integer> alFilter  = new ArrayList<>();\n"
                + "            StringBuilder strCpy         = null;     \n"
                + "            int icFilterFound            = 0;\n"
                + "            int iFilterLength            = 0;\n"
                + "            int iIndex                   = 0;\n"
                + "            int iReturn                  = -1;\n"
                + "            int icFilter                 = _strFilter.length;\n"
                + "            int[][] iFilter              = new int[icFilter][2];\n"
                + "            byte[][] bFilter             = new byte[icFilter][0];\n"
                + "\n"
                + "            // Initialize filter index.\n"
                + "            for (int i = 0; i < icFilter; i++)\n"
                + "            {\n"
                + "                iFilter[i][0] = -1;\n"
                + "                iFilter[i][1] = 0;\n"
                + "                bFilter[i]    = _strFilter[i].getBytes();\n"
                + "            }\n"
                + "\n"
                + "            // Search through the byte sequence to find substrings.\n"
                + "            while (_iStartIndex < iBufferSize && bBuffer[_iStartIndex] != '\\0')\n"
                + "            {\n"
                + "                for (int i = 0; i < icFilter; i++)\n"
                + "                {\n"
                + "                    if (bFilter[i][iFilter[i][1]] == bBuffer[_iStartIndex])\n"
                + "                    {\n"
                + "                        // If first occurence of the filter was found\n"
                + "                        if (iFilter[i][1] == 0)\n"
                + "                        {\n"
                + "                            if (iReturn == -1)\n"
                + "                                iReturn = _iStartIndex;\n"
                + "                            \n"
                + "                            iFilter[i][0] = _iStartIndex;\n"
                + "                            iFilter[i][1] = 1;\n"
                + "                        }\n"
                + "                        else\n"
                + "                            ++iFilter[i][1];\n"
                + "\n"
                + "                        // If Filter found then add to list for parsing\n"
                + "                        if (iFilter[i][1] == _strFilter[i].length())\n"
                + "                        {\n"
                + "                            alIndices.add(iFilter[i][0]);\n"
                + "                            alFilter.add(i);\n"
                + "                            iFilter[i][0] = -1;\n"
                + "                            iFilter[i][1] = 0;\n"
                + "                        }\n"
                + "                    }\n"
                + "                }\n"
                + "                \n"
                + "                ++_iStartIndex;\n"
                + "            }\n"
                + "\n"
                + "            icFilterFound = alIndices.size();\n"
                + "            \n"
                + "            // Parse byte sequence\n"
                + "            for (int i = 0; i < icFilterFound; i++)\n"
                + "            {\n"
                + "                iFilterLength =  _strFilter[alFilter.get(i)].length();\n"
                + "                iIndex = alIndices.get(i);\n"
                + "\n"
                + "                // Add Filte to the Parsed list.\n"
                + "                alParsedBuffer.add(_strFilter[alFilter.get(i)]);\n"
                + "                \n"
                + "                // Erase Builder data\n"
                + "                strCpy = new StringBuilder();\n"
                + "\n"
                + "                // Erase filter from buffer;\n"
                + "                setBytes(iIndex, iIndex + iFilterLength, (byte)0x00);\n"
                + "\n"
                + "                // Strip Non-Filter type data and insert into final \n"
                + "                // Parsed BufferL List\n"
                + "                if ((i + 1) >= icFilterFound)\n"
                + "                {\n"
                + "                    iIndex += iFilterLength;\n"
                + "                    iFilterLength = 0;\n"
                + "                    \n"
                + "                    for (int j = iIndex; j < iBufferSize && bBuffer[j] != '\\0'; j++, iFilterLength++)\n"
                + "                        strCpy.append((char)bBuffer[j]);\n"
                + "\n"
                + "                    // Erase data\n"
                + "                    setBytes(iIndex, iIndex + iFilterLength, (byte)0x00);\n"
                + "\n"
                + "                    // Save data\n"
                + "                    alParsedBuffer.add(strCpy.substring(0));\n"
                + "                }\n"
                + "                else\n"
                + "                {\n"
                + "                    iIndex += iFilterLength;\n"
                + "                    iFilterLength = alIndices.get(i + 1);\n"
                + "\n"
                + "                    for (int j = iIndex; j < iFilterLength; j++)\n"
                + "                        strCpy.append((char)bBuffer[j]);\n"
                + "\n"
                + "                    // Erase data\n"
                + "                    setBytes(iIndex, iFilterLength, (byte)0x00);\n"
                + "\n"
                + "                    // Save data\n"
                + "                    alParsedBuffer.add(strCpy.substring(0));  \n"
                + "                }\n"
                + "            }\n"
                + "            \n"
                + "            return (iReturn);\n"
                + "        }\n"
                + "        \n"
                + "        return (-1);\n"
                + "    }\n"
                + "\n"
                + "    /**\n"
                + "     * Set buffer block to some value\n"
                + "     * @param _iStart[in] start pos\n"
                + "     * @param _iEnd[in] ens pos\n"
                + "     * @param _bByte[in] value to replace buffer block with\n"
                + "     */\n"
                + "    private void setBytes(int _iStart, int _iEnd, byte _bByte)\n"
                + "    {\n"
                + "        if (_iStart > -1 && _iEnd <= iBufferSize)\n"
                + "        {\n"
                + "            for (int i = _iStart; i < _iEnd; i++)\n"
                + "               bBuffer[i] = _bByte;\n"
                + "        }\n"
                + "    }\n"
                + "}";

        String expectedOutput = "package ezdoc;\n"
                + "import java.io.*;\n"
                + "import java.util.ArrayList;\n"
                + "\n"
                + "/**\n"
                + " * Performs the following tasks:\n"
                + " *  -Basic file I/O. \n"
                + " *  -Parses file\n"
                + " *  -Apply basic filter to non-space, new-line, or carriage return characters.\n"
                + " * @author Antony\n"
                + " * February 16, 2016\n"
                + " */\n"
                + "public class EZFile {\n"
                + "    private ArrayList<String> alParsedBuffer;  \n"
                + "    private ArrayList<Integer> alFilterIndex;\n"
                + "    private FileInputStream   fisStream;\n"
                + "    private FileOutputStream  fosStream;\n"
                + "    private StringBuilder     bldExtractor;    \n"
                + "    private String            strFileName;\n"
                + "    private String            strPathName; \n"
                + "    private byte[]            bBuffer;\n"
                + "    private int               iBufferSize;\n"
                + "    private int               iRead;\n"
                + "    private int               iAppendPos;\n"
                + "    private IOFlag            ioFlag;\n"
                + "    \n"
                + "    public EZFile()\n"
                + "    {}\n"
                + "    \n"
                + "    /**\n"
                + "     * Inspect buffer size\n"
                + "     * @return int, size of buffer\n"
                + "     */\n"
                + "    public int getBufferSize()\n"
                + "    {}\n"
                + "    \n"
                + "    /**\n"
                + "     * Inspect/Mutate Buffer\n"
                + "     * @return byte[], data to write to or read from file\n"
                + "     */\n"
                + "    public byte[] getBuffer()\n"
                + "    {}\n"
                + "    \n"
                + "    /**\n"
                + "     * Inspect/Mutate parsed buffer elements\n"
                + "     * @return List of elements of buffer\n"
                + "     */\n"
                + "    public ArrayList<String> getParsedBuffer()\n"
                + "    {}\n"
                + "    \n"
                + "    /**\n"
                + "     * Inspect/Mutate filter indices\n"
                + "     * @return ArrayList<Integer>, indices of where filters are located in parsed list\n"
                + "     */\n"
                + "    public ArrayList<Integer> getFilterIndex()\n"
                + "    {}\n"
                + "    \n"
                + "    /**\n"
                + "     * Current total bytes read from file\n"
                + "     * @return int, bytes read from file\n"
                + "     */\n"
                + "    public int getTotalBytesRead()\n"
                + "    {}\n"
                + "    \n"
                + "    /**\n"
                + "     * Reallocates buffer to new size\n"
                + "     * @param _iBufSize[in] new buffer size\n"
                + "     * @return Boolean, true if the buffer was reset else false\n"
                + "     */\n"
                + "    public Boolean setBufferSize(int _iBufSize)\n"
                + "    {}\n"
                + "    \n"
                + "    /**\n"
                + "     * Opens/Creates file for I/O operations\n"
                + "     * @param _strPath[in] file path, exclude if fle in current working dir.\n"
                + "     * @param _strFile[in] file name\n"
                + "     * @param _ioFlag[in] enumeration representing input or output\n"
                + "     * @return Boolean, true on successful file instantiation else false.\n"
                + "     */\n"
                + "    public Boolean open(String _strPath, String _strFile, IOFlag _ioFlag)\n"
                + "    {}\n"
                + "    \n"
                + "    /**\n"
                + "     * Closes open file\n"
                + "     */\n"
                + "    public void close()\n"
                + "    {}        \n"
                + "    \n"
                + "    /**\n"
                + "     * Separates buffer into an ArrayList, filter may be applied to buffer to \n"
                + "     * create more buffer elements\n"
                + "     * @param _strFilter[in] character sequences to search for in buffer\n"
                + "     * @return Boolean, true if buffer was organized else false \n"
                + "     */\n"
                + "    public Boolean parseBuffer(String[] _strFilter)\n"
                + "    {}\n"
                + "    \n"
                + "    /**\n"
                + "     * Read from file\n"
                + "     * @param _iOffSet[in] offset from current position\n"
                + "     * @return boolean, true on successful read else false\n"
                + "     */\n"
                + "    public Boolean read(int _iOffSet)\n"
                + "    {}\n"
                + "    \n"
                + "     /**\n"
                + "     * Read from file\n"
                + "     * @return boolean, true on successful read else false\n"
                + "     */\n"
                + "    public Boolean read()\n"
                + "    {}\n"
                + "    \n"
                + "    /**\n"
                + "     * Write to file\n"
                + "     * @param _bData[in] data to write\n"
                + "     * @param _iOffSet[in] offset from current position\n"
                + "     * @param _iLength[in] length of buffer\n"
                + "     * @return Boolean, true on success else false\n"
                + "     */\n"
                + "    public Boolean write(byte[] _bData, int _iOffSet, int _iLength)            \n"
                + "    {}\n"
                + "    \n"
                + "    /**\n"
                + "     * Reads and Parses entire file with filter\n"
                + "     * @param _strFilter[in] filter for file\n"
                + "     * @return Boolean, true if data was read from file\n"
                + "     */\n"
                + "    public Boolean parseFile(String[] _strFilter)\n"
                + "    {}\n"
                + "    \n"
                + "    /**\n"
                + "     *  Returns file contents in a String object\n"
                + "     *  @return String, file data\n"
                + "     */\n"
                + "    public String readFile()\n"
                + "    {}\n"
                + "    \n"
                + "    /**\n"
                + "     * Finds Char sequences based on filter specifications and adds to list\n"
                + "     * @param _strFilter[in] buffer filter\n"
                + "     * @param _iStartIndex[in] current position in buffer\n"
                + "     * @return int, position of first character for the first filter found else \n"
                + "     *         -1.\n"
                + "     */\n"
                + "    protected int applyFilter(String[] _strFilter, int _iStartIndex)\n"
                + "    {}\n"
                + "\n"
                + "    /**\n"
                + "     * Set buffer block to some value\n"
                + "     * @param _iStart[in] start pos\n"
                + "     * @param _iEnd[in] ens pos\n"
                + "     * @param _bByte[in] value to replace buffer block with\n"
                + "     */\n"
                + "    private void setBytes(int _iStart, int _iEnd, byte _bByte)\n"
                + "    {}\n"
                + "}";

        ScriptInterpreter si = new ScriptInterpreter(input);

        assertEquals(expectedOutput, si.cleanedInput);

    }

}
