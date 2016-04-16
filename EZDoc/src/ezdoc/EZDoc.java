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
public class EZDoc {
    static BenchmarkEZFile bezf;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //FileViewerFrame display = new FileViewerFrame(System.getProperty("user.dir"));
        FileWorker worker = new FileWorker();
        worker.processFile("src\\ezdoc\\", "FileWorker.java");
 
    }
    
}
