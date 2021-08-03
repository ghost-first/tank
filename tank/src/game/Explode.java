package game;

import java.awt.*;

//用来控制爆炸效果的类
public class Explode {
    public static final int EXPLODE_FRAME_COUNT = 12;
    //导入资源
    private static Image[] img;

    //爆炸效果的宽度和高度
    private static int explodeWidth = 40;
    private static int explodeHeight = 40;
    static {
        img = new Image[EXPLODE_FRAME_COUNT / 3];
        for (int i = 0; i < img.length; i++) {
            img[i] = MyUtil.createImage("boom" + i + ".png");
        }
    }

    //爆炸效果的属性
    private int x,y;
    private int index; //当前播放帧数的下标
    private boolean visible;

    public Explode(int x, int y) {
        this.x = x;
        this.y = y;
        index = 0;
        visible = true;
    }

    public Explode() {
        index = 0;
    }

    public void drawBoom(Graphics g){
        if(!visible) return;
        g.drawImage(img[index/3],x-15,y-15, explodeWidth,explodeHeight,null);
        index++;
        if(index >= EXPLODE_FRAME_COUNT) visible = false;
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

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
