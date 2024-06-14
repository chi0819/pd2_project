import java.io.*;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

import java.util.List;
import java.util.ArrayList;

public class PVPmode extends Planewar {
    static int width = 600;
    static int height = 600;
    public static int state = 0; // 設置初始遊戲狀態為開始

    int count = 0; // 用於記錄更新迴圈次數

    Image offScreenImage = null;
    BgObj backGround = new BgObj(GameUtil.bgImag, 0, -435, 2);
    PlayerPlaneObj player1; // 上 WASD
    PlayerPlaneObj player2; // 下 鍵盤上下左右

    List<GameObj> gameObjList = new ArrayList<>();
    List<GameObj> removeList = new ArrayList<>();

    // 添加按鍵狀態標誌位
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

        // 初始化遊戲物件列表
        if (gameObjList == null) {
            gameObjList = new ArrayList<>();
        }
        gameObjList.add(backGround);
        gameObjList.add(player1);
        gameObjList.add(player2);

        // 鍵盤處理器
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                keyState[e.getKeyCode()] = true; // 記錄按鍵狀態
            }

            @Override
            public void keyReleased(KeyEvent e) {
                keyState[e.getKeyCode()] = false; // 釋放鍵時重置按鍵狀態
            }
        });

        state = 1;

        while (true) {
            if (state == 1) {
                handleMovement(); // 根據按鍵狀態移動飛機
                createObj(); // 每15個循環發射子彈
                checkCollisions(); // 每次更新循環時檢查碰撞
                repaint(); // 觸發繪畫機制，重繪畫面
            }
            try {
                Thread.sleep(25); // 控制更新頻率
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // 處理飛機移動
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
        // 如果 offScreenImage 尚未初始化，初始化並設置大小
        if (offScreenImage == null) {
            offScreenImage = createImage(width, height);
        }
        Graphics gImage = offScreenImage.getGraphics();
        gImage.fillRect(0, 0, width, height); // 設置背景色為黑色

        if (state == 0) {
            gImage.drawImage(GameUtil.bgImag, 0, 0, getWidth(), getHeight(), this);
        }

        if (state == 1) {
            // 繪製背景和玩家物件
            for (GameObj obj : gameObjList) {
                obj.paintSelf(gImage);
            }
            GameUtil.gameObjList.removeAll(GameUtil.removeList);
            gameObjList.removeAll(GameUtil.removeList);
        }

        // 繪製玩家健康狀況
        gImage.setColor(Color.RED);
        gImage.drawString("Player 1 Health: " + player1.getHealth(), 50, 50);
        gImage.drawString("Player 2 Health: " + player2.getHealth(), 50, height - 50);

        // 將緩衝區的畫面繪製到前端
        g.drawImage(offScreenImage, 0, 0, null);

        count++; // 更新計數器
    }

    public void createObj() {
        if (count % 15 == 0) {
            createBullet(player1);
            createBullet(player2);
        }

        // 增加更多物件生成邏輯
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
        // 移除被標記的子彈
        gameObjList.removeAll(removeList);
        GameUtil.shellObjList.removeAll(removeList);
        removeList.clear();
    }

}
