import java.awt.*;
import java.awt.event.KeyEvent;

class PlayerPlaneObj extends GameObj {
    private int health;

    public PlayerPlaneObj(Image img, int x, int y, int width, int height, double speed, Planewar frame, int health) {
        super(img, x, y, width, height, speed, frame);
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public void reduceHealth(int amount) {
        health -= amount;
        if (health <= 0) {
            health = 0;
        }
    }

    @Override
    public void paintSelf(Graphics gImage) {
        super.paintSelf(gImage);
    }

    public void moveUp() {
        y -= speed;
    }

    public void moveDown() {
        y += speed;
    }

    public void moveLeft() {
        x -= speed;
    }

    public void moveRight() {
        x += speed;
    }

    @Override
    public Rectangle getRec() {
        return new Rectangle(x, y, width, height);
    }
}
