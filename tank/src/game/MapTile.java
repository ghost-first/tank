package game;

import java.awt.*;
import java.util.List;

public class MapTile {
    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_COVER = 1;
    public static final int TYPE_HARD = 2;
    public static final int TYPE_HOME = 3;
    public static final int TYPE_BOOM = 4;//TODO

    public static final int width = 30;
    private static Image[] tileImg;
    private boolean visible = true;
    private int type = TYPE_NORMAL;

   // public static int tileW;
    static {
        tileImg = new Image[4];
        tileImg[TYPE_NORMAL] = MyUtil.createImage("wallNormal.png");
        tileImg[TYPE_COVER] = MyUtil.createImage("wallCover.png");
        tileImg[TYPE_HARD] = MyUtil.createImage("wallHard.png");
        tileImg[TYPE_HOME] = MyUtil.createImage("boom4.png");
        //TODO
    }

    private int x,y;
    public MapTile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public MapTile() {
    }

    public void draw(Graphics g){
        if(!visible) return;
        g.drawImage(tileImg[type],x + 10,y + 10,width,width,null);
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
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

    public boolean isCollideBullets(List<Bullet> bullets){
        if(!isVisible() || type == TYPE_COVER){
            return false;
        }
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            int bulletX = bullet.getX();
            int bulletY = bullet.getY();
            boolean collide = MyUtil.isCollide(x + width / 2 + 5, y + width / 2, width / 2, bulletX, bulletY);
            if(collide){
                //子弹的销毁
                bullet.setVisible(false);
                BulletsPool.GiveBack(bullet);
                return true;
            }
        }
        return false;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
