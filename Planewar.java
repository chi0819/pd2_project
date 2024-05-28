import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.ArrayList;
import java.util.List;

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

    public void paintSelf (Graphics gImage) {
        gImage.drawImage(img, x, y,null);
    } 
    public Rectangle getRec() {
        return new Rectangle(x,y,width,height);
    }
}

class GameUtil {
    public static Image bgImag = Toolkit.getDefaultToolkit().getImage("imgs/bgImag.jpg");
    public static Image bossImag = Toolkit.getDefaultToolkit().getImage("imgs/boss.png");
    public static Image explodeImag = Toolkit.getDefaultToolkit().getImage("imgs/explode.png");
    public static Image planeImag = Toolkit.getDefaultToolkit().getImage("imgs/plane.png");
    public static Image shellImag = Toolkit.getDefaultToolkit().getImage("imgs/shell.png");
    public static Image enemyImag = Toolkit.getDefaultToolkit().getImage("imgs/enemy.png");
    public static Image bulletImag = Toolkit.getDefaultToolkit().getImage("imgs/bullet.png");

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

class BgObj extends GameObj {
    public BgObj() {
        super();
    }
    public BgObj(Image img, int x, int y, double speed) {
        super(img,x,y,speed);
    }

    @Override
    public void paintSelf(Graphics gImage) {
        super.paintSelf(gImage);
        y += speed;
        if (y >= 0) {
            y = -435;
        }
    }
}

class BossObj extends GameObj {
    int life = 10;
    public BossObj(Image img,int x,int y,int width,int height,double speed,Planewar frame) {
        super(img,x,y,width,height,speed,frame);
    }
    @Override
    public void paintSelf(Graphics gImage) {
        super.paintSelf(gImage);
        if (x > 550 || x < -50) {
            speed = -speed;
        }
        x += speed;
        for (ShellObj shellObj : GameUtil.shellObjList) {
            if (this.getRec().intersects(shellObj.getRec())) {
                shellObj.setX(-100);
                shellObj.setY(100);
                GameUtil.removeList.add(shellObj);
                life--;
            }
            if (life <= 0) {
                Planewar.state = 4;
            }
        }
        gImage.setColor(Color.white);
        gImage.fillRect(20,40,100,10);
        gImage.setColor(Color.red);
        gImage.fillRect(20,40,(life*100)/10,10);
    }
    public Rectangle getRec() {
        return new Rectangle(x,y,width,height);
    }
}

class BulletObj extends GameObj {
    public BulletObj(Image img,int x,int y,int width,int height,double speed,Planewar frame) {
        super(img,x,y,width,height,speed,frame);
    }
    @Override
    public void paintSelf(Graphics gImage) {
        super.paintSelf(gImage);
        y += speed;
        if (y > 600) {
            this.x = -300; //this coordinate can't be the same as "shell" and "enemy" (or it'll collide)
            this.y = 300;
            GameUtil.removeList.add(this);
        }
        if (/*this.frame.bossObj != null &&*/ this.getRec().intersects(this.frame.planeObj.getRec())) {
            Planewar.state = 3;
        }
    }
    public Rectangle getRec() {
        return new Rectangle(x,y,width,height);
    }
}

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
        if (this.getRec().intersects(this.frame.planeObj.getRec())) {
            Planewar.state = 3;
        }
        if (y > 600) {
            this.x = -200;
            this.y = 200;
            GameUtil.removeList.add(this);
        }
        for (ShellObj shellObj : GameUtil.shellObjList) {
            if (this.getRec().intersects(shellObj.getRec())) {
                //System.out.println("haha");
                shellObj.setX(-100);
                shellObj.setY(100);
                this.x = -200;
                this.y = 200;
                GameUtil.removeList.add(shellObj);
                GameUtil.removeList.add(this);
                Planewar.score++;
            }
        }
    }
    public Rectangle getRec() {
        return new Rectangle(x,y,width,height);
    }
}

class PlaneObj extends GameObj{
    @Override
    public Image getImage() {
        return super.getImage();
    }
    public PlaneObj() {
        super();
    }
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
    @Override
    public void paintSelf(Graphics gImage) {
        super.paintSelf(gImage);
        if (this.frame.bossObj != null && this.getRec().intersects(this.frame.bossObj.getRec())) {
            Planewar.state = 3;
        }
    }
    public Rectangle getRec() {
        return new Rectangle(x,y,width,height);
    }
}

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
    public void paintSelf(Graphics gImage) {
        super.paintSelf(gImage);
        y -= speed;
        if (y < 0) { //out of the frame
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
        Planewar gameWin = new Planewar();
        gameWin.launch();
    }
}