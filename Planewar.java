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
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;

public class Planewar extends JFrame {

    public enum GameState {
        INITIAL,
        GAMING,
        PAUSE,
        GAMEOVER,
        VICTORY    
    }

    public static int width = 1000;
    public static int height = 1000;
    public static Planewar.GameState currentState = GameState.INITIAL;
    public static int score = 0;
    public static int gameLevel = 0;
    public static float volume = 0.8f; 
    public static boolean bossAlive = false;
    public static String backGroundMusic = "sounds/backgroundMusic.wav";
    public static String Dfficulty = "Midium";

    public static int bossSpeed = 5;
    public static int bulletSpeed = 5;
    public static int enemySpeed = 5;
    public static int shellSpeed = 5;
    public static int bulletProductivity = 15; //the smaller the number is, the higher the bullet productivity is
    public static int enemyProductivity = 15; //the smaller the number is, the higher the enemy productivity is
    public static int shellProductivity = 15; //the bigger the number is, the lower the shell productividy is

    static Planewar mainFrame; //argument used to set window size

    int count = 1; //count*25ms
    int enemyCount = 0;

    Image offScreenImage = null;
     BgObj backGround = new BgObj(GameUtil.bgImag, 0, -435, 2);
    public PlaneObj planeObj = new PlaneObj(GameUtil.planeImag, width/2+10, height/3+80, 20, 30, 0, this);
    public BossObj bossObj = null;

    JButton startButton;
    JButton settingButton;
    static JButton retryButton;
    static JButton homeButton;

    boolean bossMusicPlaying = true;
    static Clip backgroundClip = null;
    static Clip lose = null;
    static Clip win = null;
    static Clip open = null; //openning music
    static Clip homestart = null;

    public void launch() {
        open = SoundUtil.playSoundWithVolume("sounds/rick.wav", true, volume);

        this.setVisible(true);
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setTitle("Airplane Battle");
        this.setDefaultCloseOperation(3);

        this.setLayout(null);
        startButton = new JButton(new ImageIcon("imgs/start_button.png"));
        startButton.setBounds(width/2-90, height/3+20, 200, 100);
        startButton.setContentAreaFilled(false);
        this.add(startButton);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SoundUtil.stopSound(open); //stop music when the game start
                currentState = GameState.GAMING;
                startButton.setVisible(false);
                settingButton.setVisible(false);
                startButton = null;
                settingButton = null;
                repaint();
            }
        });

        settingButton = new JButton(new ImageIcon("imgs/setting_button.png"));
        settingButton.setBounds(width/2-90, height/3+130, 200, 100);
        settingButton.setUI(new BasicButtonUI());
        this.add(settingButton);
        settingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SoundUtil.stopSound(open); //stop music when set variables
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        new GameSettings().setVisible(true);
                    }
                });
            }
        }); 
        
        GameUtil.gameObjList.add(backGround);
        GameUtil.gameObjList.add(planeObj);

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 32) {
                    switch (currentState) {
                        case GAMING:
                            currentState = GameState.PAUSE;
                            break;
                        case PAUSE:
                            currentState = GameState.GAMING;
                            break;
                        default:
                    }
                }
            }
        });

        while (true) {
            if (currentState == GameState.GAMING) {
                createObj();
                repaint();
                if(!bossAlive) bossObj = null;
                if(bossObj != null && !bossMusicPlaying) {
                    SoundUtil.stopSound(backgroundClip);
                    backgroundClip = SoundUtil.playSoundWithVolume(backGroundMusic, true, volume);
                    bossMusicPlaying = true;
                } else if(bossObj == null && bossMusicPlaying) {
                    SoundUtil.stopSound(backgroundClip);
                    backgroundClip = SoundUtil.playSoundWithVolume("sounds/backgroundMusic.wav", true, volume);
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
        if (currentState == GameState.INITIAL) {
            gImage.drawImage(GameUtil.bgImag, 0, 0, width, height, this);
            gImage.drawImage(GameUtil.bossRickImag, width/10, height/6, this);
            gImage.drawImage(GameUtil.bossTrumpImag, (width*9)/13, height/9, this);
            gImage.drawImage(GameUtil.explodeImag, width/2-70, height/6, this);
        }
        if (currentState == GameState.GAMING) {
            for (int i = 0; i < GameUtil.gameObjList.size(); i++) {
                GameUtil.gameObjList.get(i).paintSelf(gImage);
            }
            GameUtil.gameObjList.removeAll(GameUtil.removeList);
        }
        if (currentState == GameState.GAMEOVER) {
            SoundUtil.stopSound(backgroundClip);
            SoundUtil.stopSound(homestart);
            gImage.drawImage(GameUtil.explodeImag, planeObj.getX() - 60, planeObj.getY() - 90, null);
            lose = SoundUtil.playSoundWithVolume("sounds/lose.wav", false, volume);
            GameUtil.drawWord(gImage, "GAME OVER", Color.RED, 50, width/2-150, height/3);

            if (retryButton == null) {
                retryButton = new JButton(new ImageIcon("imgs\\retry_button.png"));
                retryButton.setBounds( width/2-90, height/3+20, 174, 68);
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


            if (homeButton == null) {
                homeButton = new JButton(new ImageIcon("imgs\\home_button.png"));
                homeButton.setBounds( width/2-90, height/3+100, 174, 68);
                homeButton.setUI(new BasicButtonUI());

                homeButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        resetGameToInitialState();
                    }
                });
                this.add(homeButton);
            }
            homeButton.setVisible(true);

        }
        if (currentState == GameState.VICTORY) {
            // gImage.drawImage(GameUtil.explodeImag, bossObj.getX() - 30, bossObj.getY() - 40, null);
            win = SoundUtil.playSoundWithVolume("sounds/100score.wav", false, volume);
            GameUtil.drawWord(gImage, "YOU WON", Color.GREEN, 50, width/2-125, height/3);
        }
        if (currentState == GameState.GAMING || currentState == GameState.VICTORY || currentState == GameState.GAMEOVER) {
            GameUtil.drawWord(gImage, "SCORE : " + score, Color.GREEN, 40, 30, 100);
        }        
        g.drawImage(offScreenImage, 0, 0, null);
        count++;
    }

    void createObj() {
        if (count % shellProductivity == 0) {
            GameUtil.shellObjList.add(new ShellObj(GameUtil.shellImag, planeObj.getX() + 3, planeObj.getY() - 16, 14, 29, shellSpeed, this));
            GameUtil.gameObjList.add(GameUtil.shellObjList.get(GameUtil.shellObjList.size() - 1));
            SoundUtil.playSoundWithVolume("sounds/plane_shoot1.wav", false, volume*0.95f);
        }
        if (count % enemyProductivity == 0) {
            GameUtil.enemyObjList.add(new EnemyObj(GameUtil.enemyImag, (int) (Math.random() * (width/50)) * 50, 0, 49, 36, enemySpeed, this));
            GameUtil.gameObjList.add(GameUtil.enemyObjList.get(GameUtil.enemyObjList.size() - 1));
            enemyCount++;
        }
        if (count % bulletProductivity == 0 && bossObj != null) {
            GameUtil.bulletObjList.add(new BulletObj(GameUtil.bulletImag, bossObj.getX() + 40, bossObj.getY() + 85, 15, 25, bulletSpeed, this));
            GameUtil.gameObjList.add(GameUtil.bulletObjList.get(GameUtil.bulletObjList.size() - 1));
        }

        // Control Game Level and Boss Type 
        if(gameLevel == 0 && score > 10 && bossObj == null) {
            SoundUtil.stopSound(homestart);
            gameLevel++;
            bossAlive = true;
            bossObj = new BossObj(GameUtil.bossRickImag, width/2, 20, 176, 155, bossSpeed, gameLevel, this);
            backGroundMusic = "sounds/rick.wav";
            GameUtil.gameObjList.add(bossObj);
        }

        if(gameLevel == 1 && score > 30 && bossObj == null) {
            gameLevel++;
            bossAlive = true;
            bossObj = new BossObj(GameUtil.bossTrumpImag, width/2, 20, 206, 217, bossSpeed, gameLevel, this);
            backGroundMusic = "sounds/shootingStar.wav";
            GameUtil.gameObjList.add(bossObj);
        }
    }

    private void restartGameAfterGameOver() {
        SoundUtil.stopSound(lose);

        GameUtil.gameObjList.clear();
        GameUtil.bulletObjList.clear();
        GameUtil.enemyObjList.clear();
        GameUtil.shellObjList.clear();
        GameUtil.removeList.clear();

        backGround = new BgObj(GameUtil.bgImag, 0, -435, 2);
        planeObj = new PlaneObj(GameUtil.planeImag, width/2+10, height/3+80, 20, 30, 0, this);
        bossObj = null;
        bossAlive = false;
        startButton = null;
        settingButton = null;

        GameUtil.gameObjList.add(backGround);
        GameUtil.gameObjList.add(planeObj);

        currentState = GameState.GAMING;
        score = 0;
        count = 1;
        enemyCount = 0;
        gameLevel=0;

        retryButton.setVisible(false);
        retryButton = null;
        homeButton.setVisible(false);
        homeButton = null;
        backgroundClip = SoundUtil.playSoundWithVolume("sounds\\backgroundMusic.wav", true, volume);
    }

    private void resetGameToInitialState() {
        SoundUtil.stopSound(win);
        SoundUtil.stopSound(lose);

        currentState = GameState.INITIAL;
        score = 0;
        gameLevel = 0;
        bossAlive = false;
        bossMusicPlaying = false;

        GameUtil.gameObjList.clear();
        GameUtil.bulletObjList.clear();
        GameUtil.enemyObjList.clear();
        GameUtil.shellObjList.clear();
        GameUtil.removeList.clear();

        backGround = new BgObj(GameUtil.bgImag, 0, -435, 2);
        planeObj = new PlaneObj(GameUtil.planeImag, width/2+10, height/3+80, 20, 30, 0, this);
        bossObj = null;
        startButton = null;
        settingButton = null;

        GameUtil.gameObjList.add(backGround);
        GameUtil.gameObjList.add(planeObj);

        repaint();

        if (retryButton != null) {
            retryButton.setVisible(false);
            retryButton = null;
        }
        if (homeButton != null) {
            homeButton.setVisible(false);
            homeButton = null;
        }

        initializeButtons();
        open = SoundUtil.playSoundWithVolume("sounds/rick.wav", true, volume);
    }


    private void initializeButtons() {
        startButton = new JButton(new ImageIcon("imgs/start_button.png"));
        startButton.setBounds(width/2-90, height/3+20, 200, 100);
        startButton.setContentAreaFilled(false);
        this.add(startButton);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SoundUtil.stopSound(open);
                currentState = GameState.GAMING;
                startButton.setVisible(false);
                settingButton.setVisible(false);
                startButton = null;
                settingButton = null;
                homestart = SoundUtil.playSoundWithVolume("sounds/backgroundMusic.wav", true, volume);
                repaint();
            }
        });

        settingButton = new JButton(new ImageIcon("imgs/setting_button.png"));
        settingButton.setBounds(width/2-90, height/3+130, 200, 100);
        settingButton.setUI(new BasicButtonUI());
        this.add(settingButton);
        settingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SoundUtil.stopSound(open);
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        new GameSettings().setVisible(true);
                    }
                });
            }
        }); 
    }


    //setting window size change 
    public static void reinitializeComponents(Planewar mainFrame2) {
        GameUtil.gameObjList.clear();
        GameUtil.bulletObjList.clear();
        GameUtil.enemyObjList.clear();
        GameUtil.shellObjList.clear();
        GameUtil.removeList.clear();
    
        mainFrame.setSize(width, height);
        mainFrame.setLocationRelativeTo(null);
            
        BgObj backGround = new BgObj(GameUtil.bgImag, 0, -435, 2);
        PlaneObj planeObj = new PlaneObj(GameUtil.planeImag, width/2+10, height/3+80, 20, 30, 0, mainFrame2);
        GameUtil.gameObjList.add(backGround);
        GameUtil.gameObjList.add(planeObj);

        if (mainFrame2.startButton != null) {
            mainFrame2.startButton.setBounds(width / 2 - 90, height / 3 + 20, 200, 100);
            mainFrame2.add(mainFrame2.startButton);
        }
    
        if (mainFrame2.settingButton != null) {
            mainFrame2.settingButton.setBounds(width / 2 - 90, height / 3 + 130, 200, 100);
            mainFrame2.add(mainFrame2.settingButton);
        }
    
        mainFrame.repaint();
    }

    

    public static void main(String args[]) {
        Planewar gameWin = new Planewar();
        mainFrame = gameWin;
        reinitializeComponents(gameWin);
        gameWin.launch();
    }
}

