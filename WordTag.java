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
public class WordTag {
    public enum Type
    {
        SPECIAL,
        KEYWORD,
        UNKNOWN
    };   
    private Type wTag;
    
    public WordTag(Type _Type)
    {
        wTag = _Type;
    }
    
    /**
     *  Method:         setTag
     *  Description:    Changes enumeration value stored
     *  Parameter:      WordTag.Type _wTag[in] tag value
     *  Return:         n/a
     */
    public void setTag(WordTag.Type _wTag)
    {
        this.wTag = _wTag;
    }
    
    /**
     *  Method:         getTag
     *  Description:    Inspects tag value
     *  Parameter:      n/a
     *  Return:         Tag value
     */
    public WordTag.Type getTag()
    {
        return (wTag);
    }
}
