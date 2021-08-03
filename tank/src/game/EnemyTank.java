package game;

import java.awt.*;

import static game.Constant.*;

public class EnemyTank extends Tank{
    public static final int TYPE_A = 0;
    public static final int TYPE_B = 1;
    private int type = TYPE_A;
    private long AITime;
    private static Image[] AImg;
    private static Image[] BImg;
    static{
        AImg = new Image[4];
//        AImg[0] = Myutil.createImage("green0.png");
//        AImg[1] = Myutil.createImage("green1.png");
//        AImg[2] = Myutil.createImage("green2.png");
//        AImg[3] = Myutil.createImage("green3.png");
        AImg[0] = MyUtil.createImage("tankA0.png");
        AImg[1] = MyUtil.createImage("tankA1.png");
        AImg[2] = MyUtil.createImage("tankA2.png");
        AImg[3] = MyUtil.createImage("tankA3.png");
        BImg = new Image[4];
        BImg[0] = MyUtil.createImage("tankB0.png");
        BImg[1] = MyUtil.createImage("tankB1.png");
        BImg[2] = MyUtil.createImage("tankB2.png");
        BImg[3] = MyUtil.createImage("tankB3.png");
    }

    private EnemyTank(int x, int y, int dir) {
        super(x, y, dir);
        AITime = System.currentTimeMillis();//开始计时
        type = MyUtil.getRandomNumber(0,TYPE_B+1);
    }

    public EnemyTank() {
        AITime = System.currentTimeMillis();//开始计时
        type = MyUtil.getRandomNumber(0,TYPE_B+1);
    }

    public void drawImgTank(Graphics g){
        if(type == TYPE_A) {
            g.drawImage(AImg[getDir()], getX(), getY(), RADIO, RADIO, null);
        }
        if(type == TYPE_B) {
            g.drawImage(BImg[getDir()], getX() , getY(), RADIO, RADIO, null);
        }

        AI();
    }
    public static Tank creatEnemy(){
        int x = MyUtil.getRandomNumber(0,2)==0? 2 * RADIO : FRAME_WIDTH - 2 * RADIO;
        int y = 100, dir = DIR_DOWN;
//        Tank enemy = new EnemyTank(x, y, dir);
        EnemyTank enemy = (EnemyTank)EnemyTanksPool.get();
        enemy.setX(x);
        enemy.setY(y);
        enemy.setDir(dir);
        enemy.setEnemy(true);
        enemy.setHp(DEF_HP + DEF_HP/10 *LevelInof.getInstance().getGameLevel());
        //TODO
        enemy.setState(STATE_MOVE);
        //通过关卡信息中的敌人类型 来设置当前出生的敌人类型
        int enemyType = LevelInof.getInstance().getRandomEnemyType();
        enemy.setType(enemyType);
        return enemy;
    }

    //AI
    private void AI(){
        if(System.currentTimeMillis() - AITime > ENEMY_AI_INTERVAL){
            //随机切换状态
            setDir(MyUtil.getRandomNumber(DIR_UP, DIR_RIGHT + 1));
            setState(MyUtil.getRandomNumber(0,2)==0? STATE_STAND : STATE_MOVE);
            AITime = System.currentTimeMillis();
        }
        if(Math.random() < ENEMY_FIRE_PERCENT){
            fire();
        }
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
