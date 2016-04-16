/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor. inherits
 */
package ezdoc;

/**
 *
 * @author Antony
 */
public class FileWorker implements FileTranslator {
    ScriptInterpreter si;   // Used to Translate file to web report
    String strReportPath;   // Location of new file
    FileState fsState;      // current state of file
    
    public FileWorker()
    {
        fsState = FileState.FILE_INACTIVE;
        strReportPath = "";
    }
    
    /**
     * 
     * @param _strPath where to store web report
     */
    @Override
    public void saveWebReportTo(String _strPath)
    {
        strReportPath = _strPath;
    }
    
    /**
     * Translate file
     * @param _strPath [in] location of file
     * @param _strFileName [in] file name
     */
    @Override
    public void processFile(String _strPath, String _strFileName)
    {
        // TODO: code to translate file to web report goes here
        EZFile file = new EZFile();
        String[] html = _strFileName.split("\\.", 0);
        String data = null;
        byte[] dhtml = null;
        int iSize = 0;
        
        file.open(_strPath, _strFileName, IOFlag.READ);
        file.setBufferSize(512);
        
        fsState = FileState.FILE_ACTIVE;
        data = file.readFile();
        
        if (data.length() > 0)
        {
            si = new ScriptInterpreter(data);
            file.close();       

            if (strReportPath.length() == 0)
                strReportPath = _strPath;

            dhtml = new byte[iSize = si.htmlString.length()];

            for (int i = 0; i < iSize; i++)
               dhtml[i] = (byte)si.htmlString.charAt(i);

            file.open(strReportPath, html[0] + ".html", IOFlag.WRITE); 
            file.write(dhtml, 0, iSize);
            file.close();
            fsState = FileState.FILE_SUCCESS;
            
        }else{
            fsState = FileState.FILE_FAIL;
        }
    }
       
    /**
     * 
     * @return  state of file
     */
    @Override
    public FileState getState()
    {
        return (fsState);
    }
}
