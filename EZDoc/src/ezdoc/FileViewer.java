/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ezdoc;

import java.util.ArrayList;

/**
 *
 * @author Antony
 */
public class FileViewer implements FileManager {
    
    public Boolean openFolder(int _iIndex)
    {
        return (false);
    }
    
    public Boolean openFolder(String _strPath)
    {
        return (false);
    }
    
    public Boolean closeFolder()
    {
        return (false);
    }
    
    public ArrayList<String> getFolderContents()
    {
          return (new ArrayList<>());
    }
}
