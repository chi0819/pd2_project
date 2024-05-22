package com;

import javax.swing.*;

import com.GameUtil;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameWin extends JFrame{
    static int width = 600;
    static int height = 600;
    public static int state = 0;
    public static int score = 0;
    int count = 1;
    int enemyCount = 0;

    Image  offScreenImage  = null;
    BgObj obj = new BgObj(GameUtil.bgImag,0,-435,2);
    public PlaneObj planeObj = new PlaneObj(GameUtil.planeImag,290,550,20,30,0,this);
    //public BossObj bossObj = new BossObj(GameUtil.bossImag,250,0,109,109,5,this);
    public BossObj bossObj = null;

    public void launch() {
        this.setVisible(true);
        this.setSize(width,height);
        this.setLocationRelativeTo(null);
        this.setTitle("Airplane Battle");
        this.setDefaultCloseOperation(3);

        GameUtil.gameObjList.add(obj);
        GameUtil.gameObjList.add(planeObj);
        //GameUtil.gameObjList.add(bossObj);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getButton() == 1 && state == 0) {
                    state = 1;
                    repaint();
                }
            }
        });

        //switch to english to stop the game
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed (KeyEvent e) {
                if (e.getKeyCode() == 32) {
                    switch (state) {
                        case 1:
                            state = 2;
                            break;
                        case 2:
                            state = 1;
                            break;
                            default:
                    }
                }
            }
        });

        while (true) {
            if (state == 1) {
                createObj();
                repaint();
            }
            try {
                Thread.sleep(25);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void paint (Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = createImage(width,height);
        }
        Graphics gImage = offScreenImage.getGraphics();
        gImage.fillRect(0,0, width, height);
        if (state == 0) {
            gImage.drawImage(GameUtil.bgImag, 0, 0, getWidth(), getHeight(), this);
            gImage.drawImage(GameUtil.bossImag,225,100,this);
            gImage.drawImage(GameUtil.explodeImag,210,350,this);
            GameUtil.drawWord(gImage,"Start",Color.black,40,235,300);
            //gImage.setFont(new Font("Arial", Font.BOLD, 40));
            //gImage.drawString("Start",235,300);
        }
        if (state == 1) {
            for (int i=0 ; i<GameUtil.gameObjList.size() ; i++) {
                GameUtil.gameObjList.get(i).paintSelf(gImage);
            }
            GameUtil.gameObjList.removeAll(GameUtil.removeList);
        }
        if (state == 3) {
            gImage.drawImage(GameUtil.explodeImag, planeObj.getX() - 60, planeObj.getY() - 90,null);
            GameUtil.drawWord(gImage,"GAME OVER",Color.RED,50,155,300);
            //gImage.setColor(Color.RED);
            //gImage.setFont(new Font("Arial", Font.BOLD, 50));
            //gImage.drawString("GAME OVER",155,300);
        }
        if (state == 4) {
            gImage.drawImage(GameUtil.explodeImag, bossObj.getX() - 30, bossObj.getY() - 40,null);
            GameUtil.drawWord(gImage,"YOU WON",Color.GREEN,50,155,300);
        }
        GameUtil.drawWord(gImage,"SCORE : " + score,Color.GREEN,40,30,100);
        g.drawImage(offScreenImage,0,0,null);
        count++;

        //delete all objects that fly out the frame
        
    }

    void createObj() {
        if (count % 15 == 0) {
            GameUtil.shellObjList.add(new ShellObj(GameUtil.shellImag,planeObj.getX()+3,planeObj.getY()-16,14,29,5,this));
            GameUtil.gameObjList.add(GameUtil.shellObjList.get(GameUtil.shellObjList.size() - 1));
        }
        if (count % 15 == 0) {
            GameUtil.enemyObjList.add(new EnemyObj(GameUtil.enemyImag,(int)(Math.random()*12)*50,0,49,36,5,this));
            GameUtil.gameObjList.add(GameUtil.enemyObjList.get(GameUtil.enemyObjList.size() - 1));
            enemyCount++;
        }
        if (count % 15 == 0 && bossObj != null) {
            GameUtil.bulletObjList.add(new BulletObj(GameUtil.bulletImag,bossObj.getX()+40,bossObj.getY()+85,15,25,5,this));
            GameUtil.gameObjList.add(GameUtil.bulletObjList.get(GameUtil.bulletObjList.size() - 1));
        }
        if (enemyCount > 10 && bossObj == null) {
            bossObj = new BossObj(GameUtil.bossImag,250,0,109,109,5,this);
            GameUtil.gameObjList.add(bossObj);
        }
    }
    public static void main (String args[]) {
        GameWin gameWin = new GameWin();
        gameWin.launch();
    }
}