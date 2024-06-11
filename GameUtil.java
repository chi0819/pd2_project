import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.ImageIcon;

import java.util.List;
import java.util.ArrayList;

public class GameUtil {
    // All images used for objects icon
    public static Image bgImag = Toolkit.getDefaultToolkit().getImage("imgs/bgImag.jpg");
    public static Image bossRickImag = Toolkit.getDefaultToolkit().getImage("imgs/rick.png");
    public static Image bossTrumpImag = Toolkit.getDefaultToolkit().getImage("imgs/trump.png");
    public static Image explodeImag = Toolkit.getDefaultToolkit().getImage("imgs/explode.png");
    public static Image planeImag = Toolkit.getDefaultToolkit().getImage("imgs/plane.png");
    public static Image shellImag = Toolkit.getDefaultToolkit().getImage("imgs/shell.png");
    public static Image enemyImag = Toolkit.getDefaultToolkit().getImage("imgs/enemy.png");
    public static Image bulletImag = Toolkit.getDefaultToolkit().getImage("imgs/bullet.png");

    // Store objects show on screen
    public static List<GameObj> gameObjList = new ArrayList<>();
    public static List<ShellObj> shellObjList = new ArrayList<>();
    public static List<EnemyObj> enemyObjList = new ArrayList<>();
    public static List<BulletObj> bulletObjList = new ArrayList<>();
    public static List<GameObj> removeList = new ArrayList<>();

    public static void drawWord (Graphics gImage,String str,Color color,int size,int x,int y) {
        gImage.setColor(color);
        gImage.setFont(new Font("Arial",Font.BOLD,size));
        gImage.drawString(str, x, y);
    }
}
