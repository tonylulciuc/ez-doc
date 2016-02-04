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
public interface Procedure<T1, T2, T3, T4> {
    
    /**
     *  Method:         execute
     *  Description:    Executes code on two objects
     *  Parameter:      T1 _Param1[in] object one
     *                  T1 _Param2[in] object two
     *                  T2 _Extra[in] extra information for method
     *  Return          T3, depends on the method
     */
    public T4 execute(T1 _Param1, T2 _Param2, T3 _Extra);
}
