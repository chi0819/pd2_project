import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class ShellObj extends GameObj {
    public int playerId;

    @Override
    public Image getImage() {
        return super.getImage();
    }

    public ShellObj() {
        super();
    }

    public ShellObj(Image img, int x, int y, int width, int height, double speed, Planewar frame) {
        super(img, x, y, width, height, speed, frame);
    }

    public ShellObj(Image img, int x, int y, int width, int height, int speed, Planewar frame, int playerId) {
        super(img, x, y, width, height, speed, frame);
        this.playerId = playerId;
    }

    public void paintSelf(Graphics gImage) {
        Graphics2D g2d = (Graphics2D) gImage;
        AffineTransform old = g2d.getTransform();

        // rotate
        if (this.playerId == 1) {
            g2d.rotate(Math.toRadians(180), x + width / 2, y + height / 2);
        }
        g2d.drawImage(img, x, y, null);

        g2d.setTransform(old);
        y -= speed;
        if (y < 0 || y > Planewar.height) { // out of the frame
            this.x = -100;
            this.y = 100;
            GameUtil.removeList.add(this);
        }
    }

    public Rectangle getRec() {
        return new Rectangle(x, y, width, height);
    }
}
