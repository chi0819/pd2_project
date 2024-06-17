import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

import java.util.List;
import java.util.ArrayList;

public class PVPmode extends Planewar {
    static int width = 600;
    static int height = 600;
    int count = 0;
    public static int state = 0;
    /*
     * 0 : initial
     * 1 : fight
     * 4 : game over
     */

    Image offScreenImage = null;
    BgObj backGround = new BgObj(GameUtil.bgImag, 0, -435, 2);
    PlayerPlaneObj player1; // control : WASD
    PlayerPlaneObj player2; // control : up,down,left,right

    List<GameObj> gameObjList = new ArrayList<>();
    List<GameObj> removeList = new ArrayList<>();

    boolean[] keyState = new boolean[256];

    public static void main(String[] args) {
        new PVPmode().launch();
    }

    public void launch() {
        this.setVisible(true);
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setTitle("PvP Mode");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        player1 = new PlayerPlaneObj(GameUtil.planeImag, 100, 100, 50, 50, 10, this, 100, 1);
        player2 = new PlayerPlaneObj(GameUtil.planeImag, 100, 400, 50, 50, 10, this, 100, 2);

        // init
        if (gameObjList == null) {
            gameObjList = new ArrayList<>();
        }
        gameObjList.add(backGround);
        gameObjList.add(player1);
        gameObjList.add(player2);

        // key status
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                keyState[e.getKeyCode()] = true;
            }

            @Override
            public void keyReleased(KeyEvent e) {
                keyState[e.getKeyCode()] = false;
            }
        });

        state = 1;

        while (true) {
            if (state == 1) {
                handleMovement();
                createObj();
                checkCollisions();
                repaint();
            }
            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleMovement() {
        if (keyState[KeyEvent.VK_W])
            player1.moveUp();
        if (keyState[KeyEvent.VK_S])
            player1.moveDown();
        if (keyState[KeyEvent.VK_A])
            player1.moveLeft();
        if (keyState[KeyEvent.VK_D])
            player1.moveRight();
        if (keyState[KeyEvent.VK_UP])
            player2.moveUp();
        if (keyState[KeyEvent.VK_DOWN])
            player2.moveDown();
        if (keyState[KeyEvent.VK_LEFT])
            player2.moveLeft();
        if (keyState[KeyEvent.VK_RIGHT])
            player2.moveRight();
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
        }

        if (state == 1) {
            for (GameObj obj : gameObjList) {
                obj.paintSelf(gImage);
            }
            GameUtil.gameObjList.removeAll(GameUtil.removeList);
            gameObjList.removeAll(GameUtil.removeList);
        }
        // end game
        if (state == 4) {
            gImage.setColor(Color.WHITE);
            gImage.setFont(new Font("Arial", Font.BOLD, 30));
            if (player1.getHealth() <= 0) {
                gImage.drawString("Player 2 Wins!", width / 2 - 150, height / 2);
            } else if (player2.getHealth() <= 0) {
                gImage.drawString("Player 1 Wins!", width / 2 - 150, height / 2);
            }
        }

        gImage.setColor(Color.RED);
        gImage.drawString("Player 1 Health: " + player1.getHealth(), 50, 50);
        gImage.drawString("Player 2 Health: " + player2.getHealth(), 50, height - 50);

        g.drawImage(offScreenImage, 0, 0, null);

        count++;
    }

    public void createObj() {
        if (count % 15 == 0) {
            createBullet(player1);
            createBullet(player2);
        }

    }

    public void createBullet(PlayerPlaneObj player) {
        int bulletSpeed;
        int bulletY;

        if (player.id == 1) {
            bulletY = player.getY() + player.getHeight();
            bulletSpeed = -5;
        } else {
            bulletY = player.getY() - 16;
            bulletSpeed = 5;
        }

        ShellObj bullet = new ShellObj(GameUtil.shellImag, player.getX() + player.getWidth() / 2 - 3, bulletY, 14, 9,
                bulletSpeed, this, player.id);
        gameObjList.add(bullet);
        GameUtil.shellObjList.add(bullet);
    }

    private void checkCollisions() {
        for (ShellObj shell : new ArrayList<>(GameUtil.shellObjList)) {
            if (shell.playerId != player1.id && shell.getRec().intersects(player1.getRec())) {
                player1.reduceHealth(10);
                removeList.add(shell);
                GameUtil.removeList.add(shell);
            }
            if (shell.playerId != player2.id && shell.getRec().intersects(player2.getRec())) {
                player2.reduceHealth(10);
                removeList.add(shell);
                GameUtil.removeList.add(shell);
            }
        }
        // remove bullets
        gameObjList.removeAll(removeList);
        GameUtil.shellObjList.removeAll(removeList);
        removeList.clear();

        if (player1.getHealth() <= 0 || player2.getHealth() <= 0) {
            state = 4;
        }
    }

}
