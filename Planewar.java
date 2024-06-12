import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
<<<<<<< HEAD
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
=======

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import java.io.File;
import javax.sound.sampled.*;

public class Planewar extends JFrame {

    // GameState store enum for gaming flow control
    public enum GameState {
        INITIAL,
        GAMING,
        PAUSE,
        GAMEOVER,
        VICTORY
    }

    // Gaming screen size
    static int width = 800;
    static int height = 800;

    // Gaming stage control
    public static Planewar.GameState currentState = GameState.INITIAL;

    // Player score
    public static int score = 0;

    // gameLevel determine how many boss player defeat
    public static int gameLevel = 0;

    /*
     * Because bossObj is non static object
     * Use static bossAlive to record boss is alive or not
     */
    public static boolean bossAlive = false;

    // Init background music is normal background music
    public static String backGroundMusic = "sounds/backGroundMusic.wav";

    /*
     * Time counter
     * When time pass 25ms
     * Player's plane shoot one bullet and add one enemy to the screen
     */
>>>>>>> PVPmode_0613
    int count = 1;
    int enemyCount = 0;

    Image offScreenImage = null;
    BgObj backGround = new BgObj(GameUtil.bgImag, 0, -435, 2);
<<<<<<< HEAD
    public PlaneObj planeObj = new PlaneObj(GameUtil.planeImag, 290, 550, 20, 30, 0, this);
    public BossObj bossObj = null;

    JButton startButton;
    JButton settingButton;

=======

    // Init player's plane object and bossObj
    public PlaneObj planeObj = new PlaneObj(GameUtil.planeImag, width / 2 + 10, height / 3 + 80, 20, 30, 0, this);
    public BossObj bossObj = null;

    // Button before gaming
    JButton startButton;
    JButton settingButton;

    // Button when game over
    JButton retryButton;

    boolean bossMusicPlaying = true;

    // Clip store background music information
    Clip backgroundClip = null;
    Clip lose = null;
    Clip win = null;

    /*
     * This function init Game screen parameter
     * Provide setting option before starting the game
     */
>>>>>>> PVPmode_0613
    public void launch() {
        this.setVisible(true);
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setTitle("Airplane Battle");
        this.setDefaultCloseOperation(3);

        this.setLayout(null);
        startButton = new JButton(new ImageIcon("imgs/start_button.png"));
<<<<<<< HEAD
        startButton.setBounds(185, 250, 200, 100);
=======
        startButton.setBounds(width / 2 - 90, height / 3 + 20, 200, 100);
>>>>>>> PVPmode_0613
        startButton.setContentAreaFilled(false);
        this.add(startButton);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
<<<<<<< HEAD
                state = 1;
                startButton.setVisible(false);
                settingButton.setVisible(false);
=======
                currentState = GameState.GAMING;
                startButton.setVisible(false);
                settingButton.setVisible(false);
                startButton = null;
                settingButton = null;
>>>>>>> PVPmode_0613
                repaint();
            }
        });

        settingButton = new JButton(new ImageIcon("imgs/setting_button.png"));
<<<<<<< HEAD
        settingButton.setBounds(185, 360, 200, 100);
=======
        settingButton.setBounds(width / 2 - 90, height / 3 + 130, 200, 100);
>>>>>>> PVPmode_0613
        settingButton.setUI(new BasicButtonUI());
        this.add(settingButton);
        settingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
<<<<<<< HEAD
                JOptionPane.showMessageDialog(null, "Settings dialog will be here.");
            }
        });

=======
                //
            }
        });

        // Add basic objects to game screen
>>>>>>> PVPmode_0613
        GameUtil.gameObjList.add(backGround);
        GameUtil.gameObjList.add(planeObj);

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
<<<<<<< HEAD
                if (e.getKeyCode() == 32) {
                    switch (state) {
                        case 1:
                            state = 2;
                            break;
                        case 2:
                            state = 1;
=======
                /*
                 * use getKeyCode() to detect keyboard input
                 * For Windows, the spacebar keyCode is 32
                 * For MacOS, the spacebar keyCode is 49
                 * For Linux distro, the spacebar keyCode is 62
                 */
                if (e.getKeyCode() == 32 || e.getKeyCode() == 49 || e.getKeyCode() == 62) {
                    switch (currentState) {
                        case GAMING:
                            currentState = GameState.PAUSE;
                            break;
                        case PAUSE:
                            currentState = GameState.GAMING;
>>>>>>> PVPmode_0613
                            break;
                        default:
                    }
                }
            }
        });

<<<<<<< HEAD
        boolean bossMusicPlaying = true;
        Clip backgroundClip = null;
        while (true) {
            if (state == 1) {
=======
        while (true) {
            if (currentState == GameState.GAMING) {
>>>>>>> PVPmode_0613
                createObj();
                repaint();
                if (!bossAlive)
                    bossObj = null;
                if (bossObj != null && !bossMusicPlaying) {
<<<<<<< HEAD
=======
                    /*
                     * When boss appear
                     * Stop normal background music
                     * Play background music for specific boss
                     */
>>>>>>> PVPmode_0613
                    SoundUtil.stopSound(backgroundClip);
                    backgroundClip = SoundUtil.playSound(backGroundMusic, true);
                    bossMusicPlaying = true;
                } else if (bossObj == null && bossMusicPlaying) {
<<<<<<< HEAD
                    SoundUtil.stopSound(backgroundClip);
                    backgroundClip = SoundUtil.playSound("sounds/backgroundMusic.wav", true);
                    bossMusicPlaying = false;
                }
            } else {
=======
                    /*
                     * When there is no boss
                     * Stop previouse boss background music
                     * Play normal background music
                     */
                    SoundUtil.stopSound(backgroundClip);
                    backgroundClip = SoundUtil.playSound("sounds/backGroundMusic.wav", true);
                    bossMusicPlaying = false;
                }
            } else {
                // If GameOver stop background music
>>>>>>> PVPmode_0613
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
<<<<<<< HEAD
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
=======

        // Create gaming screen border
        if (offScreenImage == null) {
            offScreenImage = createImage(width, height);
        }

        Graphics gImage = offScreenImage.getGraphics();
        gImage.fillRect(0, 0, width, height);

        if (currentState == GameState.INITIAL) {
            gImage.drawImage(GameUtil.bgImag, 0, 0, getWidth(), getHeight(), this);
            gImage.drawImage(GameUtil.bossRickImag, width / 10, height / 6, this);
            gImage.drawImage(GameUtil.bossTrumpImag, (width * 9) / 13, height / 9, this);
            gImage.drawImage(GameUtil.explodeImag, width / 2 - 70, height / 6, this);
        }

        if (currentState == GameState.GAMING) {
>>>>>>> PVPmode_0613
            for (int i = 0; i < GameUtil.gameObjList.size(); i++) {
                GameUtil.gameObjList.get(i).paintSelf(gImage);
            }
            GameUtil.gameObjList.removeAll(GameUtil.removeList);
        }
<<<<<<< HEAD
        if (state == 3) {
            gImage.drawImage(GameUtil.explodeImag, planeObj.getX() - 60, planeObj.getY() - 90, null);
            SoundUtil.playSound("sounds/lose.wav", false);
            GameUtil.drawWord(gImage, "GAME OVER", Color.RED, 50, 155, 300);
        }
        if (state == 4) {
            // gImage.drawImage(GameUtil.explodeImag, bossObj.getX() - 30, bossObj.getY() -
            // 40, null);
            SoundUtil.playSound("sounds/100score.wav", false);
            GameUtil.drawWord(gImage, "YOU WON", Color.GREEN, 50, 155, 300);
=======

        if (currentState == GameState.GAMEOVER) {
            SoundUtil.stopSound(backgroundClip);
            gImage.drawImage(GameUtil.explodeImag, planeObj.getX() - 60, planeObj.getY() - 90, null);
            lose = SoundUtil.playSound("sounds/lose.wav", false);
            GameUtil.drawWord(gImage, "GAME OVER", Color.RED, 50, width / 2 - 150, height / 3);

            if (retryButton == null) {
                retryButton = new JButton(new ImageIcon("imgs/retry_button.png"));
                retryButton.setBounds(width / 2 - 90, height / 3 + 20, 174, 68);
                retryButton.setUI(new BasicButtonUI());

                retryButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        restartGameAfterGameOver();
                    }
                });
                this.add(retryButton);
            }
            retryButton.setVisible(true);
        }

        if (currentState == GameState.VICTORY) {
            // gImage.drawImage(GameUtil.explodeImag, bossObj.getX() - 30, bossObj.getY() -
            // 40, null);
            win = SoundUtil.playSound("sounds/100score.wav", false);
            GameUtil.drawWord(gImage, "YOU WON", Color.GREEN, 50, width / 2 - 125, height / 3);
>>>>>>> PVPmode_0613
        }
        GameUtil.drawWord(gImage, "SCORE : " + score, Color.GREEN, 40, 30, 100);
        g.drawImage(offScreenImage, 0, 0, null);
        count++;
    }

    void createObj() {
        if (count % 15 == 0) {
            GameUtil.shellObjList
                    .add(new ShellObj(GameUtil.shellImag, planeObj.getX() + 3, planeObj.getY() - 16, 14, 29, 5, this));
            GameUtil.gameObjList.add(GameUtil.shellObjList.get(GameUtil.shellObjList.size() - 1));
<<<<<<< HEAD
            SoundUtil.playSound("sounds/plane_shoot1.wav", false);
        }
        if (count % 15 == 0) {
            GameUtil.enemyObjList
                    .add(new EnemyObj(GameUtil.enemyImag, (int) (Math.random() * 12) * 50, 0, 49, 36, 5, this));
            GameUtil.gameObjList.add(GameUtil.enemyObjList.get(GameUtil.enemyObjList.size() - 1));
            enemyCount++;
        }
=======
            SoundUtil.playSound("sounds/plane_shoot.wav", false);
        }

        if (count % 15 == 0) {
            GameUtil.enemyObjList.add(
                    new EnemyObj(GameUtil.enemyImag, (int) (Math.random() * (width / 50)) * 50, 0, 49, 36, 5, this));
            GameUtil.gameObjList.add(GameUtil.enemyObjList.get(GameUtil.enemyObjList.size() - 1));
            enemyCount++;
        }

>>>>>>> PVPmode_0613
        if (count % 15 == 0 && bossObj != null) {
            GameUtil.bulletObjList
                    .add(new BulletObj(GameUtil.bulletImag, bossObj.getX() + 40, bossObj.getY() + 85, 15, 25, 5, this));
            GameUtil.gameObjList.add(GameUtil.bulletObjList.get(GameUtil.bulletObjList.size() - 1));
        }

        // Control Game Level and Boss Type
        if (gameLevel == 0 && score > 10 && bossObj == null) {
            gameLevel++;
            bossAlive = true;
<<<<<<< HEAD
            bossObj = new BossObj(GameUtil.bossRickImag, 250, 20, 109, 109, 5, gameLevel, this);
=======
            bossObj = new BossObj(GameUtil.bossRickImag, width / 2, 20, 176, 155, 5, gameLevel, this);
>>>>>>> PVPmode_0613
            backGroundMusic = "sounds/rick.wav";
            GameUtil.gameObjList.add(bossObj);
        }

        if (gameLevel == 1 && score > 30 && bossObj == null) {
            gameLevel++;
            bossAlive = true;
<<<<<<< HEAD
            bossObj = new BossObj(GameUtil.bossTrumpImag, 250, 20, 109, 109, 5, gameLevel, this);
=======
            bossObj = new BossObj(GameUtil.bossTrumpImag, width / 2, 20, 206, 217, 5, gameLevel, this);
>>>>>>> PVPmode_0613
            backGroundMusic = "sounds/shootingStar.wav";
            GameUtil.gameObjList.add(bossObj);
        }
    }

<<<<<<< HEAD
    public static void main(String args[]) {
        Planewar gameWin = new Planewar();
=======
    private void restartGameAfterGameOver() {
        SoundUtil.stopSound(lose);

        GameUtil.gameObjList.clear();
        GameUtil.bulletObjList.clear();
        GameUtil.enemyObjList.clear();
        GameUtil.shellObjList.clear();
        GameUtil.removeList.clear();

        backGround = new BgObj(GameUtil.bgImag, 0, -435, 2);
        planeObj = new PlaneObj(GameUtil.planeImag, width / 2 + 10, height / 3 + 80, 20, 30, 0, this);
        bossObj = null;
        bossAlive = false;

        GameUtil.gameObjList.add(backGround);
        GameUtil.gameObjList.add(planeObj);

        currentState = GameState.GAMING;
        score = 0;
        count = 1;
        enemyCount = 0;
        gameLevel = 0;

        retryButton.setVisible(false);
        retryButton = null;

        backgroundClip = SoundUtil.playSound("sounds/backGroundMusic.wav", true);
    }

    public static void main(String args[]) {
        Planewar gameWin = new Planewar();

        // detectsize sample is here//
        SwingUtilities.invokeLater(() -> {
            WindowSizeDetector frame = new WindowSizeDetector();
            frame.setVisible(true);
        });
        // the top one is just sample//

>>>>>>> PVPmode_0613
        gameWin.launch();
    }
}
