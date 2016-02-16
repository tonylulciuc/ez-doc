/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ezdoc;
import java.io.*;
import java.util.*;
import java.lang.System;

/**
 * Writes data to file
 * @author Antony
 */
public class InjectionProcedure implements Procedure<ArrayList<Word>, FileOutputStream, Boolean, Boolean>{
    
    /**
     * Method:          execute
     * Description:     Writes data to file output stream
     * Parameter:       _List[in] data to write to file
     *                  _fOut[in] file output stream
     *                  _NotUsed[] not used 
     * Return:          Boolean, true on successful write, else false.
     */
    @Override
    public Boolean execute(ArrayList<Word> _List, FileOutputStream _fOut, Boolean _NotUsed)
    {
        int iSize = _List.size();
        
        // If FileOutputStream was not initialized
        if (_fOut == null)
            return (false);
        
        try
        {
            // Write data to file
            for (int i = 0; i < iSize; i++)
                _fOut.write(_List.get(i).getString().getBytes());
        }
        catch (IOException err)
        {
            System.out.println("IO Error : " + err.getMessage() + "!");
            System.exit(1);
        }
        
        return (true);
    }
}
