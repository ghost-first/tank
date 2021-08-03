package game;

public class LevelInof {
    //用单例设计模式设计关卡
    //构造方法私有化
    private LevelInof(){}

    //定义静态的本类类型的变量，来指向唯一实例
    private static LevelInof instance;

    //懒汉模式 第一次使用该实例创建唯一的实例
    //该方法具有安全隐患，多线程情况下可能会创建多个实例
    public static LevelInof getInstance(){
        if(instance == null){
            instance = new LevelInof();
        }
        return instance;
    }

    private int Level; //关卡编号
    private int enemyCount; //敌人数量
    private int crossTime; //通关要求时长，-1代表不限时
    private int[] enemyType; //敌人类型
    private int gameLevel; //游戏难度
    public int getLevel() {
        return Level;
    }

    public void setLevel(int level) {
        Level = level;
    }

    public int getEnemyCount() {
        return enemyCount;
    }

    public void setEnemyCount(int enemyCount) {
        this.enemyCount = enemyCount;
    }

    public int getCrossTime() {
        return crossTime;
    }

    public void setCrossTime(int crossTime) {
        this.crossTime = crossTime;
    }

    public int[] getEnemyType() {
        return enemyType;
    }

    public void setEnemyType(int[] enemyType) {
        this.enemyType = enemyType;
    }

    public int getGameLevel() {
        return gameLevel < 0? 1 : gameLevel;
    }

    public void setGameLevel(int gameLevel) {
        this.gameLevel = gameLevel;
    }

    //获得敌人类型数组中随机一个元素
    public int getRandomEnemyType(){
        int index = MyUtil.getRandomNumber(0,enemyType.length);
        return enemyType[index];
    }
}
