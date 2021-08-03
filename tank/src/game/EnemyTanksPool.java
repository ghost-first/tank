package game;

import java.util.ArrayList;
import java.util.List;

public class EnemyTanksPool {
    public static final int DEFAULT_POOL_SIZE = 20;
    public static final int POOL_MAX_SIZE = 10;

    private static List<Tank> pool = new ArrayList<>();

    static {
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            pool.add(new EnemyTank());
        }
    }
    //从池塘中获取一个子弹对象
    public static Tank get(){
        Tank tank = null;
        if(pool.size() == 0){
            tank = new EnemyTank() {
            };
        }else{//池塘中还有对象，拿走第一个位置的子弹对象
            tank = pool.remove(0);
        }
        //验证
//        System.out.println("pool中剩余：" + pool.size());
        return tank;
    }
    //子弹被销毁的是时候，归还到池塘中
    public static void GiveBack(Tank tank){
        //子弹的个数已经到达了最大值，那就不再归还
        if(pool.size() == POOL_MAX_SIZE){
            return;
        }
        pool.add(tank);
        //验证
        //System.out.println("归还后的对象数：" + pool.size());
    }
}
