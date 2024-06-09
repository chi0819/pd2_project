import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.sound.sampled.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.io.*;


//Create a class that play sounds
class SoundUtil {

    private static Clip clip;

    public static Clip playSound(String soundFile, boolean loop) {

        try {
            File soundPath = new File(soundFile);

            if (soundPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(soundPath); //Obtains an audio input stream from the provided input stream
                clip = AudioSystem.getClip(); //Get available sound playback clips
                clip.open(audioInput);

                if (loop) {
                    clip.loop(Clip.LOOP_CONTINUOUSLY); //Looping should continue indefinitely
                }
                clip.start();
            } else {
                System.out.println("Cannot find sound file: " + soundFile);
            }

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }

        return clip;
    }

    public static void stopSound(Clip clip) {

        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }

    }
}

//Gmae Object settings and painting
class GameObj {

    Image img;
    int x;
    int y;
    int width;
    int height;
    double speed;
    Planewar frame;

    public Image getImage() {
        return img;
    }

    public void setImage(Image img) {
        this.img = img;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public Planewar getFrame() {
        return frame;
    }

    public void setFrame(Planewar frame) {
        this.frame = frame;
    }

    public GameObj() {

    }
    public GameObj(Image img, int x, int y, double speed) {
        this.img = img;
        this.x = x;
        this.y = y;
        this.speed = speed;
    }
    public GameObj(Image img,int x,int y,int width,int height,double speed,Planewar frame) {
        this.img = img;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.frame = frame;
    }

    //Drawing image at specific coordinate without using observer
    public void paintSelf (Graphics gImage) {
        gImage.drawImage(img, x, y,null); 
    } 

    public Rectangle getRec() {
        return new Rectangle(x,y,width,height);
    }   
}

//Manage game resources and object lists for easy access and drawing
class GameUtil {

    public static Image bgImag = Toolkit.getDefaultToolkit().getImage("imgs/bgImag.jpg");
    public static Image bossImag = Toolkit.getDefaultToolkit().getImage("imgs/boss.png");
    public static Image bulletImag = Toolkit.getDefaultToolkit().getImage("imgs/bullet.png");
    public static Image enemyImag = Toolkit.getDefaultToolkit().getImage("imgs/enemy.png");
    public static Image explodeImag = Toolkit.getDefaultToolkit().getImage("imgs/explode.png");
    public static Image planeImag = Toolkit.getDefaultToolkit().getImage("imgs/plane.png");
    public static Image shellImag = Toolkit.getDefaultToolkit().getImage("imgs/shell.png");
    
    public static List<BulletObj> bulletObjList = new ArrayList<>();
    public static List<EnemyObj> enemyObjList = new ArrayList<>();
    public static List<GameObj> gameObjList = new ArrayList<>();
    public static List<ShellObj> shellObjList = new ArrayList<>();  
    public static List<GameObj> removeList = new ArrayList<>();

    public static void drawWord (Graphics gImage,String str,Color color,int size,int x,int y) {
        gImage.setColor(color);
        gImage.setFont(new Font("Arial",Font.BOLD,size));
        gImage.drawString(str, x, y);
    }
}

//Background object's settings and drawing
class BgObj extends GameObj {

    public BgObj() {
        super();
    }

    public BgObj(Image img, int x, int y, double speed) {
        super(img,x,y,speed);
    }

    //Implement a scrolling effect 
    @Override
    public void paintSelf(Graphics gImage) {
        super.paintSelf(gImage);
        y += speed;

        if (y >= 0) {
            y = -435;
        }
    }
}

//Boss moving type, detects collisions, and draws health bar.
class BossObj extends GameObj {
    int life = 10;

    public BossObj(Image img,int x,int y,int width,int height,double speed,Planewar frame) {
        super(img,x,y,width,height,speed,frame);
    }


    @Override
    public void paintSelf(Graphics gImage) {
        super.paintSelf(gImage);

        //Boss moves left and right
        if (x > 550 || x < -50) {
            speed = -speed;
        }
        x += speed;

        for (ShellObj shellObj : GameUtil.shellObjList) {

            if (this.getRec().intersects(shellObj.getRec())) {
                shellObj.setX(-100);
                shellObj.setY(100);
                GameUtil.removeList.add(shellObj); //The shell intersects boss should moved out of map and added to removeList
                life--; //If your shell intersects boss, boss life -1 
            }

            if (life <= 0) {
                Planewar.currentState = Planewar.GameState.VICTORY;
            }
        }

        //Painting health bar for boss
        gImage.setColor(Color.white);
        gImage.fillRect(20,40,100,10);
        gImage.setColor(Color.red);
        gImage.fillRect(20,40,(life*100)/10,10);
    }

    public Rectangle getRec() {
        return new Rectangle(x,y,width,height);
    }
}

//Bullet moves, checks collision with you. If out of bounds, removes. If hits you, game over
class BulletObj extends GameObj {

    public BulletObj(Image img,int x,int y,int width,int height,double speed,Planewar frame) {
        super(img,x,y,width,height,speed,frame);
    }

    @Override
    public void paintSelf(Graphics gImage) {

        super.paintSelf(gImage);
        y += speed;

        //Boss bullet added to removeList when it's at the bottom of window 
        if (y > 600) {
            this.x = -300; //this coordinate can't be the same as "shell" and "enemy" (or it'll collide)
            this.y = 300;
            GameUtil.removeList.add(this);
        }

        //If bullet collides with you, the game is over and also play gameover music
        if (/*this.frame.bossObj != null &&*/ this.getRec().intersects(this.frame.planeObj.getRec())) {
            Planewar.currentState = Planewar.GameState.GAMEOVER;
            SoundUtil.playSound("sounds\\plane_explode.wav",false);
        }
    }

    public Rectangle getRec() {
        return new Rectangle(x,y,width,height);
    }
}

//Enemy moves downwards and remove it when it collided with your shell 
class EnemyObj extends GameObj {

    public EnemyObj() {
        super();
    }

    public EnemyObj(Image img,int x,int y,int width,int height,double speed,Planewar frame) {
        super(img,x,y,width,height,speed,frame);
    }

    @Override
    public void paintSelf(Graphics gImage) {
        super.paintSelf(gImage);
        y += speed;

        //If enemies collide with you, gameover and play music
        if (this.getRec().intersects(this.frame.planeObj.getRec())) {
            Planewar.currentState = Planewar.GameState.GAMEOVER;
            SoundUtil.playSound("sounds\\plane_explode.wav", false);
        }

        //If enemies areat the bottom of window, move it out of window and add them to removeList
        if (y > 600) {
            this.x = -200;
            this.y = 200;
            GameUtil.removeList.add(this);
        }

        //If your shell hits enemy, move it out of window and add it into removeList and also score +1
        for (ShellObj shellObj : GameUtil.shellObjList) {
            if (this.getRec().intersects(shellObj.getRec())) {
                shellObj.setX(-100);
                shellObj.setY(100);
                this.x = -200;
                this.y = 200;
                GameUtil.removeList.add(shellObj);
                GameUtil.removeList.add(this);
                Planewar.score++;
                SoundUtil.playSound("sounds\\enemy_explode.wav", false);
            }
        }
    }

    public Rectangle getRec() {
        return new Rectangle(x,y,width,height);
    }
}

//Plane moves with mouse. Game over while you collide with boss
class PlaneObj extends GameObj{

    @Override
    public Image getImage() {
        return super.getImage();
    }

    public PlaneObj() {
        super();
    }

    //Your plane moves with your mouse
    public PlaneObj(Image img,int x,int y,int width,int height,double speed,Planewar frame) {
        super(img,x,y,width,height,speed,frame);

        this.frame.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved (MouseEvent e) {
                PlaneObj.super.x = e.getX() - 11;
                PlaneObj.super.y = e.getY() - 16;
            }
        });
    }

    //If you collide with boss, gameover
    @Override
    public void paintSelf(Graphics gImage) {
        super.paintSelf(gImage);

        if (this.frame.bossObj != null && this.getRec().intersects(this.frame.bossObj.getRec())) {
            Planewar.currentState = Planewar.GameState.GAMEOVER;
        }
    }

    public Rectangle getRec() {
        return new Rectangle(x,y,width,height);
    }
}

//Your shell moves upwards
class ShellObj extends GameObj{

    @Override
    public Image getImage() {
        return super.getImage();
    }

    public ShellObj() {
        super();
    }

    public ShellObj(Image img,int x,int y,int width,int height,double speed,Planewar frame) {
        super(img,x,y,width,height,speed,frame);
    }

    //Shell moves upwards
    public void paintSelf(Graphics gImage) {
        super.paintSelf(gImage);
        y -= speed;

        //If shell at the top of window, move it out of window and add it to removeList
        if (y < 0) { 
            this.x = -100;
            this.y = 100;
            GameUtil.removeList.add(this);
        }
    }

    public Rectangle getRec() {
        return new Rectangle(x,y,width,height);
    }
}


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
    BgObj obj = new BgObj(GameUtil.bgImag,0,-435,2);
    public PlaneObj planeObj = new PlaneObj(GameUtil.planeImag,290,550,20,30,0,this);
    //public BossObj bossObj = new BossObj(GameUtil.bossImag,250,0,109,109,5,this);
    public BossObj bossObj = null;

    JButton startButton;
    JButton settingButton;
    JButton retryButton;
    JButton homeButton;
    JButton exitButton;

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
        //GameUtil.gameObjList.add(bossObj);

        /*this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getButton() == 1 && state == 0) {
                    state = 1;
                    repaint();
                }
            }
        });*/

        //This should be revised to approach the state we want
        //Switch to english to stop the game// 
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
            //GameUtil.drawWord(gImage,"Start",Color.black,40,235,300);
        }

        if (currentState == GameState.GAMING) {

            for (int i=0 ; i<GameUtil.gameObjList.size() ; i++) {
                GameUtil.gameObjList.get(i).paintSelf(gImage);
            }

            GameUtil.gameObjList.removeAll(GameUtil.removeList);
        }

        if (currentState == GameState.GAMEOVER) {

            if(score>highestScore){
                highestScore=score;
            }

            gImage.drawImage(GameUtil.explodeImag, planeObj.getX() - 60, planeObj.getY() - 90,null);
            SoundUtil.playSound("sounds\\lose.wav", false);
            Color customColor = new Color(238, 250, 0);
            GameUtil.drawWord(gImage, "HIGHEST SCORE: "+highestScore, customColor, 45, 100, 200);
            GameUtil.drawWord(gImage,"GAME OVER",Color.RED,50,155,250);
                       
            //gImage.setColor(Color.RED);
            //gImage.setFont(new Font("Arial", Font.BOLD, 50));
            //gImage.drawString("GAME OVER",155,300);

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

            if(score>highestScore){
                highestScore=score;
            }

            SoundUtil.playSound("sounds\\win.wav", false);
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

        SoundUtil.playSound("sounds\\backgroundMusic.wav", true);
    }

    //Reset Lists and variables after victory
    private void restartGameAfterVictory() {

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

        SoundUtil.playSound("sounds\\backgroundMusic.wav", true);
    }

    public static void main (String args[]) {
        Planewar gameWin = new Planewar();
        gameWin.launch();
    }
}
