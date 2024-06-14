import java.awt.*;
import java.awt.geom.AffineTransform;

class PlayerPlaneObj extends GameObj {
    private int health;
    public int id;

    public PlayerPlaneObj(Image img, int x, int y, int width, int height, double speed, Planewar frame, int health,
            int id) {
        super(img, x, y, width, height, speed, frame);
        this.health = health;
        this.id = id;
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
        Graphics2D g2d = (Graphics2D) gImage;
        AffineTransform old = g2d.getTransform();

        // rotate
        if (this.id == 1) {
            g2d.rotate(Math.toRadians(180), x + width / 2, y + height / 2);
        }
        g2d.drawImage(img, x, y, width, height, null);

        g2d.setTransform(old);
    }

    public void moveUp() {
        if (y - speed >= 0 && (id == 1 || y - speed >= PVPmode.height / 2)) {
            y -= speed;
        }
    }

    public void moveDown() {
        if (y + height + speed <= PVPmode.height && (id == 2 || y + height + speed <= PVPmode.height / 2)) {
            y += speed;
        }
    }

    public void moveLeft() {
        if (x - speed >= 0) {
            x -= speed;
        }
    }

    public void moveRight() {
        if (x + width + speed <= PVPmode.width) {
            x += speed;
        }
    }

    @Override
    public Rectangle getRec() {
        return new Rectangle(x, y, width, height);
    }
}