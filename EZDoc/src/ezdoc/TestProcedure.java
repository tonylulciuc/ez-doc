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
public class TestProcedure implements Procedure<Integer, Integer, Integer, String>{
    
    @Override
    public String execute(Integer _i, Integer _j, Integer _k){
        return ("Testing TestProcedure...");
    }
    
}
