import java.awt.*;

//Enemy moves downwards and remove it when it collided with your shell 
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

        //If enemies collide with you, gameover and play music
        if (this.getRec().intersects(this.frame.planeObj.getRec())) {
            Planewar.currentState = Planewar.GameState.GAMEOVER;
            SoundUtil.playSound("sounds\\plane_explode.wav", false);
        }

        //If enemies areat the bottom of window, move it out of window and add them to removeList
        if (y > 600) {
            this.x = -200;
            this.y = 200;
            GameUtil.removeList.add(this);
        }

        //If your shell hits enemy, move it out of window and add it into removeList and also score +1
        for (ShellObj shellObj : GameUtil.shellObjList) {
            if (this.getRec().intersects(shellObj.getRec())) {
                shellObj.setX(-100);
                shellObj.setY(100);
                this.x = -200;
                this.y = 200;
                GameUtil.removeList.add(shellObj);
                GameUtil.removeList.add(this);
                Planewar.score++;
                SoundUtil.playSound("sounds\\enemy_explode.wav", false);
            }
        }
    }

    public Rectangle getRec() {
        return new Rectangle(x,y,width,height);
    }
}