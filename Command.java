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
public class Command implements Instruction{
    protected ArrayList<Procedure> alProcedure;
    
    /**
     *  Method:         addProcedure
     *  Description:    Adds a new procedure for this instruction
     *  Parameter:      Procedure _Procedure[in] code of execution 
     *  Return:         n/a
     */
     @Override
    public void addProcedure(Procedure _Procedure)
    {
        
    }
    
    /**
     *  Method:        input 
     *  Description:   Accepts and validates input
     *  Parameter:     Object _Item[in] input
     *  Return:        boolean, true if input is valid else false.
     */
     @Override
    public Boolean input(Object _Item)
    {
        
        return (false);
    }

    /**
     *  Method:         execute
     *  Description:    Process input based on defined procedures.
     *  Parameter:      n/a
     *  Return:         Object, result of work done.
     */
     @Override
    public Object execute()
    {
        
        return (null);
    }
    
    /**
     *  Method:         clearInput
     *  Description:    Clears all input contained in this Instruction object
     *  Parameter:      n/a
     *  Return:         n/a
     */
     @Override
    public void clearInput()
    {
        
    }
    
    /**
     *  Method:         requestInputType
     *  Description:    Returns required input for successful execution.
     *  Parameter:      n/a
     *  Return:         ArrayList<String>, list of required input.
     */
     @Override
    public ArrayList<String> requestInputType()
    {
        
        return (null);
    }
    
    /**
     *  Method:         inputRequested  
     *  Description:    Determines if instruction needs more input to execute
     *  Parameter:      n/a
     *  Return:         Boolean, true if instruction needs more input else false
     */
     @Override
    public Boolean inputRequested()
    {
        return (false);
    }
    
    /**
     *  Method:         validateInput  
     *  Description:    Determines if input is valid
     *  Parameter:      Object _Input[in] object reference of input 
     *  Return:         Boolean, true if input meets instruction requirements 
     *                  else false
     */
    protected Boolean validateInput(Object _Input)
    {
        return (false);
    }
}
