package com;
import java.awt.*;
//import org.w3c.dom.events.MouseEvent;

public class EnemyObj extends GameObj {
    public EnemyObj() {
        super();
    }
    public EnemyObj(Image img,int x,int y,int width,int height,double speed,GameWin frame) {
        super(img,x,y,width,height,speed,frame);
    }
    @Override
    public void paintSelf(Graphics gImage) {
        super.paintSelf(gImage);
        y += speed;
        if (this.getRec().intersects(this.frame.planeObj.getRec())) {
            GameWin.state = 3;
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
                GameWin.score++;
            }
        }
    }
    public Rectangle getRec() {
        return new Rectangle(x,y,width,height);
    }
}
