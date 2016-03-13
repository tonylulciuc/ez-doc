/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ezdoc;
import java.util.Timer;
import java.util.ArrayList;

/**
 *
 * @author Antony
 */
public class BenchmarkEZFile {
    private ArrayList<String> alData;
    private EZFile test;
    private EZFile result;
    private EZFile log;
    private String[] strFilter = { ":", "+", "-"};
    public Boolean run()
    {
        Timer time = new Timer();
        byte[] b;
        result = new EZFile();
        test = new EZFile();
        log = new EZFile();
        
        test.setBufferSize(512);
        test.open("testfile\\", "benchmark_ezfile.txt", IOFlag.READ);
        log.open("", "ezfile_benchmark.txt", IOFlag.WRITE);
        result.open("testfile\\", "parsed_benchmarkfile.txt", IOFlag.WRITE);
        
        test.parseFile(strFilter);
        test.close();
        
        System.out.println("done..");
        try
        {
            b = ("Elements Found : " + test.getParsedBuffer().size() + " in " + -1 + " sec.").getBytes();
            log.write(b, 0, b.length);
            log.close();
        }
        catch (NullPointerException err){}
        
        alData = test.getParsedBuffer();
        
        for (String s : alData)
            result.write((s + "\r\n").getBytes(), 0, s.length() +  "\r\n".length());
        
        result.close();
        
        return (true);
    }
}
