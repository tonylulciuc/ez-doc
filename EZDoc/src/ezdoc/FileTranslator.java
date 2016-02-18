/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ezdoc;

/**
 *
 * @author Antony
 */
public interface FileTranslator {
    public Boolean loadFile(String _strPath, String _strFileName, Boolean _bWebDoc);
    public void setWebDocument(String _strPath, String _strFileName);
    public Boolean processFile();   
}
