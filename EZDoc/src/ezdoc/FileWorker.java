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
    public void processFile(String _strPath, String _strFileName){
        // TODO: code to translate file to web report goes here
        EZFile file   = new EZFile();
        String[] html = null;
        String data   = null;
        byte[] dhtml  = null;
        int iSize     = 0;
        
        file.open(null, _strPath, IOFlag.READ);
        file.setBufferSize(512);
        
        fsState = FileState.FILE_ACTIVE;
        data = file.readFile();
        
        if (data.length() > 0){
            
            si = new ScriptInterpreter(data);
            file.close();       

            if (strReportPath.length() == 0)
                strReportPath = _strPath;
            
            html = strReportPath.split("\\.", 0);
            dhtml = si.getByteArray();

            file.open(html[0], ".html", IOFlag.WRITE); 
            file.write(dhtml, 0, dhtml.length);
            file.close();
            
            if (file.open(System.getProperty("user.dir") + "\\src\\ezdoc\\", "style.css", IOFlag.READ) == false)
                file.open(System.getProperty("user.dir"), "style.css", IOFlag.READ);
            
            data = file.readFile();
            file.close();
            
            html = strReportPath.split("\\\\", 0);
            iSize = html.length - 1;
            strReportPath = html[0] + "\\\\";
            
            for (int i = 1; i < iSize; i++)
                strReportPath += html[i] + "\\";
            
            
            file.open(strReportPath, "style.css", IOFlag.WRITE);
            file.write(data.getBytes(), 0, data.length());
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
