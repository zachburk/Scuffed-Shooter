/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zsbwf7finalproject;

import java.util.ArrayList;

/**
 *
 * @author Zach
 */
public class ClassSelectModel {
    
    private ArrayList<String> classes = new ArrayList<String>();
    
    private int idx;
    
    public ClassSelectModel() {
        classes.add("Soldier");
        classes.add("Sniper");
        
        idx = 0;
    }
    
    public String getPlayerClass() {
        return classes.get(idx);
    }
    
    public String changeClassRight() {
        if (idx == classes.size() - 1)
            idx = 0;
        else 
            idx += 1;
            
        
        return classes.get(idx);
    }
    
    public String changeClassLeft() {
        if (idx == 0)
            idx = classes.size() - 1;
        else
            idx -= 1;
        
        return classes.get(idx);
    }
}
