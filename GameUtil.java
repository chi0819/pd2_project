import java.awt.Font;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Graphics;

import java.util.List;
import java.util.ArrayList;

public class GameUtil {
  
    // Declare images used in Planewar.java
    public static Image bgImag = Toolkit.getDefaultToolkit().getImage("imgs/bgImag.jpg");
    public static Image bossRickImag = Toolkit.getDefaultToolkit().getImage("imgs/rick.png");
    public static Image bossTrumpImag = Toolkit.getDefaultToolkit().getImage("imgs/trump.png");
    public static Image explodeImag = Toolkit.getDefaultToolkit().getImage("imgs/explode.png");
    public static Image planeImag = Toolkit.getDefaultToolkit().getImage("imgs/plane.png");
    public static Image shellImag = Toolkit.getDefaultToolkit().getImage("imgs/shell.png");
    public static Image enemyImag = Toolkit.getDefaultToolkit().getImage("imgs/enemy.png");
    public static Image bulletImag = Toolkit.getDefaultToolkit().getImage("imgs/bullet.png");
    public static Image startButton = Toolkit.getDefaultToolkit().getImage("imgs/start_button.png");
    public static Image settingButton = Toolkit.getDefaultToolkit().getImage("imgs/setting_button.png");
    public static Image retryButton = Toolkit.getDefaultToolkit().getImage("imgs/retry_button.png");
    public static Image homeButoon = Toolkit.getDefaultToolkit().getImage("imgs/home_button.png");

    // Declare sounds path used in Planewar.java
    public static String backGroundMusic = "sounds/backgroundMusic.wav";
    public static String rickMusic = "sounds/rick.wav";
    public static String loseSound = "sounds/lose.wav";
    public static String score100Sound = "sounds/100score.wav";
    public static String planeShootSound = "sounds/plane_shoot1.wav";
    public static String shootingStarSound = "sounds/shootingStar.wav";
    public static String planeExplodeSound = "sounds/plane_explode.wav";
    public static String enemyExplodeSound = "sounds/enemy_explode.wav";
    public static String testSound = "sounds//testsound.wav";

    // ArrayLists store all object ( player's plane, enemy, Boss, bullet )
    public static List<GameObj> gameObjList = new ArrayList<>();
    public static List<ShellObj> shellObjList = new ArrayList<>();
    public static List<EnemyObj> enemyObjList = new ArrayList<>();
    public static List<BulletObj> bulletObjList = new ArrayList<>();

    // If some object eliminated, the object will put into removeList, then remove from screen
    public static List<GameObj> removeList = new ArrayList<>();

    public static void drawWord (Graphics gImage,String str,Color color,int size,int x,int y) {
        gImage.setColor(color);
        gImage.setFont(new Font("Arial",Font.BOLD,size));
        gImage.drawString(str, x, y);
    }
}
