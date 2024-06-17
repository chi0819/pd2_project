import java.awt.Image;
import java.awt.Graphics;
import java.awt.Rectangle;

public class BulletObj extends GameObj {
    public BulletObj(Image img,int x,int y,int width,int height,double speed,Planewar frame) {
        super(img,x,y,width,height,speed,frame);
    }
  
    @Override
    public void paintSelf(Graphics gImage) {
        super.paintSelf(gImage);
        y += speed;
        if (y > Planewar.height) {
            this.x = -300; //this coordinate can't be the same as "shell" and "enemy" (or it'll collide)
            this.y = 300;
            GameUtil.removeList.add(this);
        }

        if (/*this.frame.bossObj != null &&*/ this.getRec().intersects(this.frame.planeObj.getRec())) {
            Planewar.currentState = Planewar.GameState.GAMEOVER;
            Planewar.explode = SoundUtil.playSoundWithVolume(GameUtil.planeExplodeSound, false, Planewar.volume);
        }
    }

    public Rectangle getRec() {
        return new Rectangle(x,y,width,height);
    }
}
