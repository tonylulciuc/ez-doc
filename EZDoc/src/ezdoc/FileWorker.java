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
        si = new ScriptInterpreter("");
        fsState = FileState.FILE_INACTIVE;
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
