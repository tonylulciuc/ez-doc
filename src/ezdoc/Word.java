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
public class Word {
    protected String  strCharSeq;
    protected WordTag wTag;
    
    public Word(String _strCharSeq, WordTag _wTag)
    {
        strCharSeq = _strCharSeq;
        wTag       = _wTag;
    }
    
    /**
     *  Method:         getTag
     *  Description:    Tag associated with string
     *  Parameter:      n/a
     *  Return:         WordTag, value associated with stored string
     */
    public WordTag getTag()
    {
        return (wTag);
    }
    
    /**
     *  Method:         getString
     *  Description:    Inspects string stored
     *  Parameter:      n/a
     *  Return:         String stored in this object
     */
    public String getString()
    {
        return (strCharSeq);
    }
}
