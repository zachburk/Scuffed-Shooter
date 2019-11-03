/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zsbwf7finalproject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

/**
 *
 * @author Zach
 */
public interface FileInterface {
    
    List<Integer> readFile(File file, String fileName);
    void modifyFile(File file, int wins) throws FileNotFoundException;
    
}
