import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.sound.sampled.*;
import java.awt.*;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

import java.io.File;

public class Planewar extends JFrame {
    static int width = 600;
    static int height = 600;
    public static int state = 0;
    public static int score = 0;
    public static int gameLevel = 0;
    public static boolean bossAlive = false;
    public static String backGroundMusic = "sounds/backgroundMusic.wav";
    int count = 1;
    int enemyCount = 0;

    Image offScreenImage = null;
    BgObj backGround = new BgObj(GameUtil.bgImag, 0, -435, 2);
    public PlaneObj planeObj = new PlaneObj(GameUtil.planeImag, 290, 550, 20, 30, 0, this);
    public BossObj bossObj = null;

    JButton startButton;
    JButton settingButton;

    public void launch() {
        this.setVisible(true);
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setTitle("Airplane Battle");
        this.setDefaultCloseOperation(3);

        this.setLayout(null);
        startButton = new JButton(new ImageIcon("imgs/start_button.png"));
        startButton.setBounds(185, 250, 200, 100);
        startButton.setContentAreaFilled(false);
        this.add(startButton);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                state = 1;
                startButton.setVisible(false);
                settingButton.setVisible(false);
                repaint();
            }
        });

        settingButton = new JButton(new ImageIcon("imgs/setting_button.png"));
        settingButton.setBounds(185, 360, 200, 100);
        settingButton.setUI(new BasicButtonUI());
        this.add(settingButton);
        settingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Settings dialog will be here.");
            }
        });

        GameUtil.gameObjList.add(backGround);
        GameUtil.gameObjList.add(planeObj);

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
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

        boolean bossMusicPlaying = true;
        Clip backgroundClip = null;
        while (true) {
            if (state == 1) {
                createObj();
                repaint();
                if(!bossAlive) bossObj = null;
                if(bossObj != null && !bossMusicPlaying) {
                    SoundUtil.stopSound(backgroundClip);
                    backgroundClip = SoundUtil.playSound(backGroundMusic, true);
                    bossMusicPlaying = true;
                } else if(bossObj == null && bossMusicPlaying) {
                    SoundUtil.stopSound(backgroundClip);
                    backgroundClip = SoundUtil.playSound("sounds/backgroundMusic.wav", true);
                    bossMusicPlaying = false;
                }
           } else {
                SoundUtil.stopSound(backgroundClip);
            }
            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = createImage(width, height);
        }
        Graphics gImage = offScreenImage.getGraphics();
        gImage.fillRect(0, 0, width, height);
        if (state == 0) {
            gImage.drawImage(GameUtil.bgImag, 0, 0, getWidth(), getHeight(), this);
            gImage.drawImage(GameUtil.bossRickImag, 225, 100, this);
            gImage.drawImage(GameUtil.bossTrumpImag, 225, 100, this);
            gImage.drawImage(GameUtil.explodeImag, 210, 350, this);
        }
        if (state == 1) {
            for (int i = 0; i < GameUtil.gameObjList.size(); i++) {
                GameUtil.gameObjList.get(i).paintSelf(gImage);
            }
            GameUtil.gameObjList.removeAll(GameUtil.removeList);
        }
        if (state == 3) {
            gImage.drawImage(GameUtil.explodeImag, planeObj.getX() - 60, planeObj.getY() - 90, null);
            SoundUtil.playSound("sounds/lose.wav", false);
            GameUtil.drawWord(gImage, "GAME OVER", Color.RED, 50, 155, 300);
        }
        if (state == 4) {
            // gImage.drawImage(GameUtil.explodeImag, bossObj.getX() - 30, bossObj.getY() - 40, null);
            SoundUtil.playSound("sounds/100score.wav", false);
            GameUtil.drawWord(gImage, "YOU WON", Color.GREEN, 50, 155, 300);
        }
        GameUtil.drawWord(gImage, "SCORE : " + score, Color.GREEN, 40, 30, 100);
        g.drawImage(offScreenImage, 0, 0, null);
        count++;
    }

    void createObj() {
        if (count % 15 == 0) {
            GameUtil.shellObjList.add(new ShellObj(GameUtil.shellImag, planeObj.getX() + 3, planeObj.getY() - 16, 14, 29, 5, this));
            GameUtil.gameObjList.add(GameUtil.shellObjList.get(GameUtil.shellObjList.size() - 1));
            SoundUtil.playSound("sounds/plane_shoot1.wav", false);
        }
        if (count % 15 == 0) {
            GameUtil.enemyObjList.add(new EnemyObj(GameUtil.enemyImag, (int) (Math.random() * 12) * 50, 0, 49, 36, 5, this));
            GameUtil.gameObjList.add(GameUtil.enemyObjList.get(GameUtil.enemyObjList.size() - 1));
            enemyCount++;
        }
        if (count % 15 == 0 && bossObj != null) {
            GameUtil.bulletObjList.add(new BulletObj(GameUtil.bulletImag, bossObj.getX() + 40, bossObj.getY() + 85, 15, 25, 5, this));
            GameUtil.gameObjList.add(GameUtil.bulletObjList.get(GameUtil.bulletObjList.size() - 1));
        }

        // Control Game Level and Boss Type
        if(gameLevel == 0 && score > 10 && bossObj == null) {
            gameLevel++;
            bossAlive = true;
            bossObj = new BossObj(GameUtil.bossRickImag, 250, 20, 109, 109, 5, gameLevel, this);
            backGroundMusic = "sounds/rick.wav";
            GameUtil.gameObjList.add(bossObj);
        }

        if(gameLevel == 1 && score > 30 && bossObj == null) {
            gameLevel++;
            bossAlive = true;
            bossObj = new BossObj(GameUtil.bossTrumpImag, 250, 20, 109, 109, 5, gameLevel, this);
            backGroundMusic = "sounds/shootingStar.wav";
            GameUtil.gameObjList.add(bossObj);
        }
    }

    public static void main(String args[]) {
        Planewar gameWin = new Planewar();
        gameWin.launch();
    }
}

