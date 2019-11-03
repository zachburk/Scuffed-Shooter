/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zsbwf7finalproject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
/**
 *
 * @author Zach
 */
public class GameController extends Switchable implements Initializable, FileInterface {
    
    @FXML
    private AnchorPane anchorPane;
    
    @FXML
    private Label player1ScoreLabel;
    
    @FXML
    private Label player2ScoreLabel;
    
    @FXML
    private Label player1TotalWinsLabel;
    
    @FXML
    private Label player2TotalWinsLabel;
    
    @FXML
    private Circle player1Circle;
    
    @FXML
    private Circle player2Circle;
    
    private static String player1Class;
    private static String player2Class;
    
    private final int soldierShootSpeed = 1500;
    private final double soldierMoveSpeed = 2.0;
    private final int sniperShootSpeed = 900;
    private final double sniperMoveSpeed = 2.25;
    
    File file1 = new File("wins1.txt");
    File file2 = new File("wins2.txt");
    
    private final String fileName1 = "wins1.txt";
    private final String fileName2 = "wins2.txt";
    
    private List<Integer> wins1;
    private List<Integer> wins2;
    
    private Scene scene;
    
    private int p1score = 0;
    private int p2score = 0;
    
    private int p1wins;
    private int p2wins;
    
    private double delta1;
    private double delta2;
    
    private double newXP1 = 0;
    private double newYP1 = 0;
    private double newXP2 = 0;
    private double newYP2 = 0;
    
    private Rectangle rect;
    private Rectangle rect2;
    
    private int shotSpeedP1;
    private int shotSpeedP2;
    
    private Timeline gameTimeline;
    private Timeline shootTimeline;
    private Timeline shootTimeline2;
    
    private final double tick = 10;

    private boolean p1movingUp = false, p1movingDown = false, p1movingLeft = false, p1movingRight = false;
    private boolean p1facingUp = false, p1facingDown = false, p1facingLeft = false, p1facingRight = true;
    
    private boolean p2movingUp = false, p2movingDown = false, p2movingLeft = false, p2movingRight = false;
    private boolean p2facingUp = false, p2facingDown = false, p2facingLeft = true, p2facingRight = false;
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
 
        wins1 = readFile(file1, fileName1);
        wins2 = readFile(file2, fileName2);
        
        p1wins = wins1.get(0);
        p2wins = wins2.get(0);
        
        player1TotalWinsLabel.setText(Integer.toString(p1wins));
        player2TotalWinsLabel.setText(Integer.toString(p2wins));
        
        ClassSelectionController csc = new ClassSelectionController();
        
        player1Class = csc.getPlayer1Class();
        player2Class = csc.getPlayer2Class();
        
        
        
        
        
        if ("Sniper".equals(player1Class)) {
            Sniper player1 = new Sniper(sniperShootSpeed, sniperMoveSpeed);
            delta1 = player1.getMoveSpeed();
            shotSpeedP1 = player1.getBulletSpeed();
        }
        else {
            Soldier player1 = new Soldier(soldierShootSpeed, soldierMoveSpeed);
            delta1 = player1.getMoveSpeed();
            shotSpeedP1 = player1.getBulletSpeed();
        }
        
        if ("Sniper".equals(player2Class)) {
            Sniper player2 = new Sniper(sniperShootSpeed, sniperMoveSpeed);
            delta2 = player2.getMoveSpeed();
            shotSpeedP2 = player2.getBulletSpeed();
        }
        else {
            Soldier player2 = new Soldier(soldierShootSpeed, soldierMoveSpeed);
            delta2 = player2.getMoveSpeed();
            shotSpeedP2 = player2.getBulletSpeed();
        }
        
        
        scene = Switchable.scene;
        
        
        gameTimeline = new Timeline(new KeyFrame(Duration.millis(tick), (ActionEvent event) -> {
            
            doMovementP1();
            doMovementP2();
            
            scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case W:
                    startMovementP1(Direction.UP); break;
                case A:
                    startMovementP1(Direction.LEFT); break;
                case S:
                    startMovementP1(Direction.DOWN); break;
                case D:
                    startMovementP1(Direction.RIGHT); break;
                case UP:
                    startMovementP2(Direction.UP); break;
                case LEFT:
                    startMovementP2(Direction.LEFT); break;
                case DOWN:
                    startMovementP2(Direction.DOWN); break;
                case RIGHT:
                    startMovementP2(Direction.RIGHT); break;
                case SPACE:
                    shootP1(shotSpeedP1); break;
                case CONTROL:
                    shootP2(shotSpeedP2); break;
            }
            });
        
        
            scene.setOnKeyReleased(e -> {
                switch (e.getCode()) {
                    case W:
                        stopMovementP1(Direction.UP); break;
                    case A:
                        stopMovementP1(Direction.LEFT); break;
                    case S:
                        stopMovementP1(Direction.DOWN); break;
                    case D:
                        stopMovementP1(Direction.RIGHT); break;
                    case UP:
                        stopMovementP2(Direction.UP); break;
                    case LEFT:
                        stopMovementP2(Direction.LEFT); break;
                    case DOWN:
                        stopMovementP2(Direction.DOWN); break;
                    case RIGHT:
                        stopMovementP2(Direction.RIGHT); break;
            }
            });
        
            try { 
            
                if (rect.intersects(getXP2(), getYP2(), player2Circle.getRadius()*2, player2Circle.getRadius()*2)) {
                    p1score++;
                    anchorPane.getChildren().remove(rect);
                    player1ScoreLabel.setText(Integer.toString(p1score));
                }
                
                if (p1score >= 50) {
                p1wins++;
                player1TotalWinsLabel.setText(Integer.toString(p1wins));
                gameTimeline.stop();
                
                try {
                    modifyFile(file1, p1wins);
                } catch (FileNotFoundException ex) {
                    
                }
                System.exit(0);
        }
                
                
            
            }
            
            catch (NullPointerException ex) {
                
            }
            
            
            try {
                
                if (rect2.intersects(getXP1(), getYP1(), player1Circle.getRadius()*2, player1Circle.getRadius()*2)) {
                    p2score++;
                    anchorPane.getChildren().remove(rect2);
                    player2ScoreLabel.setText(Integer.toString(p2score));
                }
                if (p2score >= 50) {
                p2wins++;
                player2TotalWinsLabel.setText(Integer.toString(p2wins));
                gameTimeline.stop();
                try {
                    modifyFile(file2, p2wins);
                } catch (FileNotFoundException ex) {
                    
                }
                System.exit(0);
                } 
            }
            catch (NullPointerException ex) {
                    
            }
            
             
        }));
        
        
        
        
        
        
        
        
        
        gameTimeline.setCycleCount(Timeline.INDEFINITE);
        gameTimeline.play();
    }    
    
    private double getXP1() {
        return player1Circle.getTranslateX() + 50;
    }
    
    private double getYP1() {
        return player1Circle.getTranslateY() + 400;
    }
    
    private double getXP2() {
        return player2Circle.getTranslateX() + 910;
    }
    
    private double getYP2() {
        return player2Circle.getTranslateY() + 400;
    }
    
    
    private void startMovementP1(Direction d) {
        switch (d) {
            case UP:
                p1movingUp = true;
                break;
            case DOWN:
                p1movingDown = true;
                break;
            case LEFT:
                p1movingLeft = true;
                break;
            case RIGHT:
                p1movingRight = true;
                break;
        }
    }
    
    private void stopMovementP1(Direction d) {
        switch (d) {
            case UP:
                p1movingUp = false; break;
            case DOWN:
                p1movingDown = false; break;
            case LEFT:
                p1movingLeft = false; break;
            case RIGHT:
                p1movingRight = false; break;
        }
    }
    
    
    private void startMovementP2(Direction d) {
        switch (d) {
            case UP:
                p2movingUp = true; break;
            case DOWN:
                p2movingDown = true; break;
            case LEFT:
                p2movingLeft = true; break;
            case RIGHT:
                p2movingRight = true; break;
        }
    }
    
    private void stopMovementP2(Direction d) {
        switch (d) {
            case UP:
                p2movingUp = false; break;
            case DOWN:
                p2movingDown = false; break;
            case LEFT:
                p2movingLeft = false; break;
            case RIGHT:
                p2movingRight = false; break;
        }
    }
    
    
    private void doMovementP1() {
        if (p1movingLeft ^ p1movingRight) { 
            if (p1movingLeft) {
                p1facingLeft = true;
                p1facingRight = false;
                if (player1Circle.getTranslateX() > -50) {
                    newXP1 -= delta1;
                    player1Circle.setTranslateX(newXP1);
                }
                else 
                    player1Circle.setTranslateX(-50);
            }
            if (p1movingRight) {
                p1facingRight = true;
                p1facingLeft = false;
                if (player1Circle.getTranslateX() < (910)) {
                    newXP1 += delta1;
                    player1Circle.setTranslateX(newXP1);
                }
                else 
                    player1Circle.setTranslateX(910);
            }
        }
        if (p1movingUp ^ p1movingDown) {
            if (p1movingUp) {
                p1facingUp = true;
                p1facingDown = false;
                if (player1Circle.getTranslateY() > (-300)) {
                    newYP1 -= delta1;
                    player1Circle.setTranslateY(newYP1);
                }
                else 
                    player1Circle.setTranslateY(-300);
            }
            if (p1movingDown) {
                p1facingDown = true;
                p1facingUp = false;
                if (player1Circle.getTranslateY() < (240)) {
                    newYP1 += delta1;
                    player1Circle.setTranslateY(newYP1);
                }
                else 
                    player1Circle.setTranslateY(240);
            }
        }
        
        
        if (p1movingUp ^ p1movingLeft) {
            if (p1movingUp) {
                p1facingUp = true;
                p1facingLeft = false;
            }
            if (p1movingLeft) {
                p1facingLeft = true;
                p1facingUp = false;
            }
        }
        if (p1movingUp ^ p1movingRight) {
            if (p1movingUp) {
                p1facingUp = true;
                p1facingRight = false;
            }
            if (p1movingRight) {
                p1facingRight = true;
                p1facingUp = false;
            }
        }
        if (p1movingDown ^ p1movingLeft) {
            if (p1movingDown) {
                p1facingDown = true;
                p1facingLeft = false;
            }
            if (p1movingLeft) {
                p1facingLeft = true;
                p1facingDown = false;
            }
        }
        if (p1movingDown ^ p1movingRight) {
            if (p1movingDown) {
                p1facingDown = true;
                p1facingRight = false;
            }
            if (p1movingRight) {
                p1facingRight = true;
                p1facingDown = false;
            }
        }
    }
    
    
    private void doMovementP2() {
        if (p2movingLeft ^ p2movingRight) { 
            if (p2movingLeft) {
                p2facingLeft = true;
                p2facingRight = false;
                if (player2Circle.getTranslateX() > -910) {
                    newXP2 -= delta2;
                    player2Circle.setTranslateX(newXP2);
                }
                else 
                    player2Circle.setTranslateX(-910);
            }
            if (p2movingRight) {
                p2facingRight = true;
                p2facingLeft = false;
                if (player2Circle.getTranslateX() < 50) {
                    newXP2 += delta2;
                    player2Circle.setTranslateX(newXP2);
                }
                else 
                    player2Circle.setTranslateX(50);
            }
        }
        if (p2movingUp ^ p2movingDown) {
            if (p2movingUp) {
                p2facingUp = true;
                p2facingDown = false;
                if (player2Circle.getTranslateY() > -300) {
                    newYP2 -= delta2;
                    player2Circle.setTranslateY(newYP2);
                }
                else 
                    player2Circle.setTranslateY(-300);
            }
            if (p2movingDown) {
                p2facingDown = true;
                p2facingUp = false;
                if (player2Circle.getTranslateY() < 240) {
                    newYP2 += delta2;
                    player2Circle.setTranslateY(newYP2);
                }
                else 
                    player2Circle.setTranslateY(240);
            }
        }
        
        if (p2movingUp ^ p2movingLeft) {
            if (p2movingUp) {
                p2facingUp = true;
                p2facingLeft = false;
            }
            if (p2movingLeft) {
                p2facingLeft = true;
                p2facingUp = false;
            }
        }
        if (p2movingUp ^ p2movingRight) {
            if (p2movingUp) {
                p2facingUp = true;
                p2facingRight = false;
            }
            if (p2movingRight) {
                p2facingRight = true;
                p2facingUp = false;
            }
        }
        if (p2movingDown ^ p2movingLeft) {
            if (p2movingDown) {
                p2facingDown = true;
                p2facingLeft = false;
            }
            if (p2movingLeft) {
                p2facingLeft = true;
                p2facingDown = false;
            }
        }
        if (p2movingDown ^ p2movingRight) {
            if (p2movingDown) {
                p2facingDown = true;
                p2facingRight = false;
            }
            if (p2movingRight) {
                p2facingRight = true;
                p2facingDown = false;
            }
        }
    }
    
    
    private void shootP1(int shotSpeed) {

        
        rect = new Rectangle(getXP1(), getYP1(), 10, 10);
        shootTimeline = new Timeline();
        shootTimeline.setCycleCount(1);
        shootTimeline.setAutoReverse(false);
        
        KeyValue kv = new KeyValue(rect.xProperty(), 960);
        
        
        
        
        anchorPane.getChildren().add(rect);
        
        if (p1facingLeft) {
            kv = new KeyValue(rect.xProperty(), -50);
        }
        
        if (p1facingRight) {
            kv = new KeyValue(rect.xProperty(), 1010);
        }
        
        if (p1facingUp) {
            kv = new KeyValue(rect.yProperty(), -10);
        }
        
        if (p1facingDown) {
            kv = new KeyValue(rect.yProperty(), 690);
        }
        
        KeyFrame kf = new KeyFrame(Duration.millis(shotSpeed), kv);
        shootTimeline.getKeyFrames().add(kf);
        shootTimeline.play();
        
        
            
            
        
        
    }
    
    
    private void shootP2(int shotSpeed) {

        
        rect2 = new Rectangle(getXP2(), getYP2(), 10, 10);
        shootTimeline2 = new Timeline();
            
        
        shootTimeline2.setCycleCount(1);
        shootTimeline2.setAutoReverse(false);
        
        KeyValue kv = new KeyValue(rect2.xProperty(), 0);
        
        
        
        
        anchorPane.getChildren().add(rect2);
        
        if (p2facingLeft) {
            kv = new KeyValue(rect2.xProperty(), -50);
        }
        
        if (p2facingRight) {
            kv = new KeyValue(rect2.xProperty(), 1010);
        }
        
        if (p2facingUp) {
            kv = new KeyValue(rect2.yProperty(), -10);
        }
        
        if (p2facingDown) {
            kv = new KeyValue(rect2.yProperty(), 690);
        }
        
        
        
        KeyFrame kf = new KeyFrame(Duration.millis(shotSpeed), kv);
        shootTimeline2.getKeyFrames().add(kf);
        shootTimeline2.play();
        
        
        
        
        
    }
    
    @Override
    public  List<Integer> readFile(File file, String fileName) {
        
        List<Integer> list = new ArrayList<Integer>();
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(file));
            String text = null;

            while ((text = reader.readLine()) != null) {
                list.add(Integer.parseInt(text));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
            }
        }
        return list;
    }
    
    @Override
     public void modifyFile(File fileName, int wins) throws FileNotFoundException{
        File temp = new File("temp.txt");
        Scanner sc = new Scanner(fileName);
        PrintWriter pw = new PrintWriter(temp);
        
        pw.print(wins);
        
        sc.close();
        pw.close();
        
        fileName.delete();
        temp.renameTo(fileName);
    }
}
