import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.sound.sampled.*;
import java.awt.*;
import java.awt.event.*;

//Main class Planewar
public class Planewar extends JFrame{

    public enum GameState {
        INITIAL,
        GAMING,
        PAUSE,
        GAMEOVER,
        VICTORY,
        RETRY    
    }

    public static int highestScore=0;
    static int width = 600; //Window's size
    static int height = 600;
    public static Planewar.GameState currentState = GameState.INITIAL;
    public static int score = 0;
    int count = 1;
    int enemyCount = 0;

    Image  offScreenImage  = null;
    public BgObj obj = new BgObj(GameUtil.bgImag,0,-435,2);
    public PlaneObj planeObj = new PlaneObj(GameUtil.planeImag,290,550,20,30,0,this);
    public BossObj bossObj = null;

    JButton startButton;
    JButton settingButton;
    JButton retryButton;
    JButton homeButton;
    JButton exitButton;

    Clip backgroundMusic;
    Clip lose;
    Clip win;

    public void launch() {
        this.setVisible(true);
        this.setSize(width,height);
        this.setLocationRelativeTo(null); //Show the window at the center of screen
        this.setTitle("Airplane Battle");
        this.setDefaultCloseOperation(3); //Click "X" on the edge of screen and the game will be turned off  
        this.setLayout(null);

        //Start bottom settings 
        startButton = new JButton(new ImageIcon("imgs\\start_button.png"));
        startButton.setBounds(185, 250, 200, 100);
        startButton.setContentAreaFilled(false);
        this.add(startButton);       

        startButton.addActionListener(new ActionListener() {

            //If click it, currentstate is gaming, and bottoms can't be seen
            @Override
            public void actionPerformed(ActionEvent e) {
                currentState = GameState.GAMING;
                startButton.setVisible(false);
                settingButton.setVisible(false);
                repaint();
            }
        });

        //Setting bottom settings
        settingButton = new JButton(new ImageIcon("imgs/setting_button.png"));
        settingButton.setBounds(185, 360, 200, 100);
        settingButton.setUI(new BasicButtonUI()); 
        this.add(settingButton);

        settingButton.addActionListener(new ActionListener() {

            //need to expand setting fuctions here//
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Settings dialog will be here.");
            }
        });

        GameUtil.gameObjList.add(obj);
        GameUtil.gameObjList.add(planeObj);

        //This should be revised to approach the state we want
        //Press blank to stop the game// 
        this.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed (KeyEvent e) {
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

        boolean isBgMusicPlaying = false;
        Clip backgroundClip = null;

        while (true) {

            //Play music when it is gaming
            if (currentState == GameState.GAMING) {
                createObj();
                repaint();

                if (!isBgMusicPlaying) {
                    backgroundClip = SoundUtil.playSound("sounds\\backgroundMusic.wav", true);
                    isBgMusicPlaying = true;
                }
            } else {
                SoundUtil.stopSound(backgroundClip);
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
        
        if (currentState == GameState.INITIAL) {
            gImage.drawImage(GameUtil.bgImag, 0, 0, getWidth(), getHeight(), this);
            gImage.drawImage(GameUtil.bossImag,225,100,this);
            gImage.drawImage(GameUtil.explodeImag,210,350,this);
        }

        if (currentState == GameState.GAMING) {

            for (int i=0 ; i<GameUtil.gameObjList.size() ; i++) {
                GameUtil.gameObjList.get(i).paintSelf(gImage);
            }

            GameUtil.gameObjList.removeAll(GameUtil.removeList);
        }

        if (currentState == GameState.GAMEOVER) {
           SoundUtil.stopSound(backgroundMusic);
            if(score>highestScore){
                highestScore=score;
            }

            gImage.drawImage(GameUtil.explodeImag, planeObj.getX() - 60, planeObj.getY() - 90,null);
            lose = SoundUtil.playSound("sounds\\lose.wav", false);
            Color customColor = new Color(238, 250, 0);
            GameUtil.drawWord(gImage, "HIGHEST SCORE: "+highestScore, customColor, 45, 100, 200);
            GameUtil.drawWord(gImage,"GAME OVER",Color.RED,50,155,250);

            //retryButton makes you try the game again
            if (retryButton == null) {
                retryButton = new JButton(new ImageIcon("imgs\\retry_button.png"));
                retryButton.setBounds(215, 255, 174, 68);

                //Click it and restart the game
                retryButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        restartGameAfterGameOver();
                    }
                });

                this.add(retryButton);
            }
            retryButton.setVisible(true);

            //homebutton makes you go to home page
            if (homeButton == null) {
                homeButton = new JButton(new ImageIcon("imgs\\home_button.png"));
                homeButton.setBounds(215, 343, 174, 68);

                homeButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        restartGameAfterGameOver(); //it needs to be revised with a function turn it to start page//
                    }
                });

                this.add(homeButton);
            }

            homeButton.setVisible(true);
            
            //exitbutton makes you close the game 
            if (exitButton == null) {
                exitButton = new JButton(new ImageIcon("imgs\\exit_button.png"));
                exitButton.setBounds(215, 431, 174, 68);

                exitButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dispose();
                        System.exit(0); //Add this line to make sure it closed correctly
                    }
                });

                this.add(exitButton);
            }

            exitButton.setVisible(true);  
        }

        if (currentState == GameState.VICTORY) {
            SoundUtil.stopSound(backgroundMusic);

            if(score>highestScore){
                highestScore=score;
            }

            win = SoundUtil.playSound("sounds\\win.wav", false);
            Color customColor = new Color(238, 250, 0); //customize color
            GameUtil.drawWord(gImage, "HIGHEST SCORE: "+highestScore, customColor, 45, 70, 200);
            gImage.drawImage(GameUtil.explodeImag, bossObj.getX() - 30, bossObj.getY() - 40,null);
            GameUtil.drawWord(gImage,"YOU WON",Color.GREEN,50,155,250);

            if (retryButton == null) {
                retryButton = new JButton(new ImageIcon("imgs\\retry_button.png"));
                retryButton.setBounds(180, 343, 174, 68);

                //Click it and restart the game
                retryButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        restartGameAfterVictory();
                    }
                });

                this.add(retryButton);
            }
            retryButton.setVisible(true);

        }

        GameUtil.drawWord(gImage,"SCORE : " + score,Color.GREEN,40,30,100);
        g.drawImage(offScreenImage,0,0,null);
        count++;        
    }

    void createObj() {

        //You and your shell adding
        if (count % 15 == 0) {
            GameUtil.shellObjList.add(new ShellObj(GameUtil.shellImag,planeObj.getX()+3,planeObj.getY()-16,14,29,5,this));
            GameUtil.gameObjList.add(GameUtil.shellObjList.get(GameUtil.shellObjList.size() - 1));
            SoundUtil.playSound("sounds\\plane_shoot1.wav", false);
        }

        //Enemy adding
        if (count % 15 == 0) {
            GameUtil.enemyObjList.add(new EnemyObj(GameUtil.enemyImag,(int)(Math.random()*12)*50,0,49,36,5,this));
            GameUtil.gameObjList.add(GameUtil.enemyObjList.get(GameUtil.enemyObjList.size() - 1));
            enemyCount++;
        }

        //Boss's bullet adding and its coordinate
        if (count % 15 == 0 && bossObj != null) {
            GameUtil.bulletObjList.add(new BulletObj(GameUtil.bulletImag,bossObj.getX()+40,bossObj.getY()+85,15,25,5,this));
            GameUtil.gameObjList.add(GameUtil.bulletObjList.get(GameUtil.bulletObjList.size() - 1));
        }

        //Boss's show up condition
        if (enemyCount > 10 && bossObj == null) {
            bossObj = new BossObj(GameUtil.bossImag,250,0,109,109,5,this);
            GameUtil.gameObjList.add(bossObj);
        }
    }

    //Reset Lists and variables after gameover
    private void restartGameAfterGameOver() {
        SoundUtil.stopSound(lose);

        GameUtil.gameObjList.clear();
        GameUtil.bulletObjList.clear();
        GameUtil.enemyObjList.clear();
        GameUtil.shellObjList.clear();
        GameUtil.removeList.clear();

        obj = new BgObj(GameUtil.bgImag, 0, -435, 2);
        planeObj = new PlaneObj(GameUtil.planeImag, 290, 550, 20, 30, 0, this);
        bossObj = null;

        GameUtil.gameObjList.add(obj);
        GameUtil.gameObjList.add(planeObj);

        currentState = GameState.GAMING;

        score = 0;
        count = 1;
        enemyCount = 0;
    
        retryButton.setVisible(false);
        homeButton.setVisible(false);
        exitButton.setVisible(false);

        retryButton = null;
        homeButton = null;
        exitButton = null;

        backgroundMusic = SoundUtil.playSound("sounds\\backgroundMusic.wav", true);
    }

    //Reset Lists and variables after victory
    private void restartGameAfterVictory() {
        SoundUtil.stopSound(win);

        GameUtil.gameObjList.clear();
        GameUtil.bulletObjList.clear();
        GameUtil.enemyObjList.clear();
        GameUtil.shellObjList.clear();
        GameUtil.removeList.clear();

        obj = new BgObj(GameUtil.bgImag, 0, -435, 2);
        planeObj = new PlaneObj(GameUtil.planeImag, 290, 550, 20, 30, 0, this);
        bossObj = null;

        GameUtil.gameObjList.add(obj);
        GameUtil.gameObjList.add(planeObj);

        currentState = GameState.GAMING;

        score = 0;
        count = 1;
        enemyCount = 0;

        retryButton.setVisible(false);
        
        retryButton = null;

        backgroundMusic = SoundUtil.playSound("sounds\\backgroundMusic.wav", true);
    }

    public static void main (String args[]) {
        Planewar gameWin = new Planewar();
        gameWin.launch();
    }
}
