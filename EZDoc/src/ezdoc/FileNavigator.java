/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ezdoc;

import java.util.ArrayList;
import java.io.File;
import java.util.Arrays;

/**
 * Description: FileNavigator it allows the user to navigate through there 
 *              directory
 * @author Antony
 */
public class FileNavigator implements FileManager {
    private ArrayList<String> alElm;
    private ArrayList<String> alPath;
    static private Boolean bEnd;
    /**
     * Constructor
     */
    public FileNavigator()
    {
        String[] path;
        alPath = new ArrayList<>();
        alElm  = new ArrayList<>();    
        bEnd   = false;
        path   = System.getProperty("user.dir").split("\\\\", 0);
        alPath.addAll(Arrays.asList(path));        
        setFolderList(new File(getCurrentPath()));
    }
    
    /**
     * Opens folder in current directory
     * @param _iIndex[in] index of folder selected
     * @return Boolean, true if folder was successfully opened, else false.
     */
    @Override
    public Boolean openFolder(int _iIndex)
    {
        assert(_iIndex > -1 && _iIndex < alElm.size());
        return (openFolder(alElm.get(_iIndex)));
    }
    
    /**
     * Opens specified folder
     * @param _strFolderName[in] name of folder
     * @return Boolean, true if folder was successfully opened, else false.
     */
    @Override
    public Boolean openFolder(String _strFolderName)
    {
        if (_strFolderName != null && _strFolderName.length() > 0)
        {
            // Folder should not contain this character
            if (_strFolderName.contains("."))
                return (false);
            
            // If name exist in list
            if (alElm.contains(_strFolderName))
            {
                alPath.add(_strFolderName);
                setFolderList(new File(getCurrentPath()));
                return (true);
            }
        }
 
        return (false);
    }
    
    /**
     * Steps out of current folder.
     * @return Boolean, true if folder is able to step out, else false.
     */
    @Override
    public Boolean closeFolder()
    {
        // If trying to open folder in root
        if (alPath.size() == 1)
        {
            if (bEnd == true)
                return (false);
            else
            {
                setFolderList(new File(getCurrentPath() + "\\"));
                return (bEnd = true);
            }
        }
        else
        {
            alPath.remove(alPath.size() - 1);
            setFolderList(new File(getCurrentPath()));
            bEnd = false;
            return (true);
        }
    }
    
    /**
     *
     * @return String, current folder name.
     */
    @Override
    public String getCurrentFolder()
    {
        return (alPath.size() > 1 ? alPath.get(alPath.size() - 1) : "EZDoc");
    }
    
    /**
     * Find "current working directory".
     * @return String, path of "current working directory".
     */
    @Override
    final public String getCurrentPath()
    {
        StringBuilder bld = new StringBuilder();
        
        for (String folder : alPath)
        {
            bld.append(folder);
            bld.append("\\");
        }
        
        return (bld.substring(0, bld.length() - 1));
    }
    
    /**
     *  Get list of elements contained in folder
     * @return ArrayList<String> list of folder contents
     */
    @Override
    public ArrayList<String> getFolderContents()
    {
          return (alElm);
    }
    
    /**
     * Sets list of folder contents
     * @param folder[in] folder to extract data from
     */
    final void setFolderList(File folder)
    {
        File[] files = folder.listFiles();
        alElm.clear();
        
        for (File file : files)
            alElm.add(file.getName());
    }
}
