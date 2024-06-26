import java.awt.Image;
import java.awt.Graphics;
import java.awt.Rectangle;

public class EnemyObj extends GameObj {
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
            Planewar.currentState = Planewar.GameState.GAMEOVER;
            Planewar.explode = SoundUtil.playSoundWithVolume(GameUtil.planeExplodeSound, false, Planewar.volume);
        }

        if (y > Planewar.height) {
            this.x = -200;
            this.y = 200;
            GameUtil.removeList.add(this);
        }

        for (ShellObj shellObj : GameUtil.shellObjList) {
            if (this.getRec().intersects(shellObj.getRec())) {
                shellObj.setX(-100);
                shellObj.setY(100);
                this.x = -200;
                this.y = 200;
                GameUtil.removeList.add(shellObj);
                GameUtil.removeList.add(this);
                Planewar.score++;
                SoundUtil.playSoundWithVolume(GameUtil.enemyExplodeSound, false, Planewar.volume*0.9f);
            }
        }
    }

    public Rectangle getRec() {
        return new Rectangle(x,y,width,height);
    }
}
