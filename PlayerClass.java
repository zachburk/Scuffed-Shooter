/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zsbwf7finalproject;

/**
 *
 * @author Zach
 */
public class PlayerClass {
    
    private int bulletSpeed;
    private double moveSpeed;
    
    public PlayerClass(int bulletSpeed, double moveSpeed){
        this.bulletSpeed = bulletSpeed;
        this.moveSpeed = moveSpeed;
    }
    
    public int getBulletSpeed() {
        return bulletSpeed;
    }
    
    public double getMoveSpeed() {
        return moveSpeed;
    }
}
