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
public interface FileManager {
    public Boolean openFolder(int _iIndex);
    public Boolean openFolder(String _strPath, String _strFolderName);
    public Boolean closeFolder();
    public String getCurrentFolder();
    public String getCurrentPath();
    public ArrayList<String> getFolderContents();
}
