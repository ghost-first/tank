package game;

import java.awt.*;

//游戏中的常量
public class Constant {
    public static final String GAME_TITLE = "tank battle";
    public static final int FRAME_WIDTH = 800;
    public static final int FRAME_HEIGHT = 600;

    //动态的获得系统屏幕的宽高
    public static final int SCREEN_W = Toolkit.getDefaultToolkit().getScreenSize().width;
    public static final int SCREEN_H = Toolkit.getDefaultToolkit().getScreenSize().height;

    public static final int FRAME_X = SCREEN_W - FRAME_WIDTH>>1;
    public static final int FRAME_Y = SCREEN_H - FRAME_HEIGHT>>1;

    //游戏菜单相关
    public static final int STATE_MENU = 0;
    public static final int STATE_HELP = 1;
    public static final int STATE_ABOUT = 2;
    public static final int STATE_RUN = 3;
    public static final int STATE_OVER = 4;
    public static final int STATE_WIN = 5;

//    public static final String[] MENUS = {"开始游戏","继续游戏","游戏帮助","游戏关于","退出游戏"};
    public static final String[] MENUS = {"开始游戏","游戏帮助","游戏关于","退出游戏"};

    public static final  Font Game_font = new Font("宋体",Font.BOLD,24);
    public static final  Font Name_font = new Font("宋体",Font.BOLD,12);

    public static final int RRPAINT_Interval = 66;

    public static final int ENEMY_MAX_COUNT = 10;
    public static final int ENEMY_BORN_INTERVAL = 3000;
    public static final int ENEMY_AI_INTERVAL = 3000;
    public static final double ENEMY_FIRE_PERCENT = 0.05;

    public static final String OVER_STR0 = "ESC键退出游戏";
    public static final String OVER_STR1 = "ENTER键回主菜单";
}
