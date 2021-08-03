package game;

import java.util.ArrayList;
import java.util.List;

public class ExplodesPool {
    public static final int DEFAULT_POOL_SIZE = 10;
    public static final int POOL_MAX_SIZE = 10;
    //用于保存所有爆炸效果的容器
    private static List<Explode> pool = new ArrayList<>();

    static {
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            pool.add(new Explode());
        }
    }
    //从池塘中获取一个子弹对象
    public static Explode get(){
        Explode explode = null;
        if(pool.size() == 0){
            explode = new Explode();
        }else{//池塘中还有对象，拿走第一个位置的子弹对象
            explode = pool.remove(0);
        }
//        验证
//        System.out.println("pool中剩余：" + pool.size());
        return explode;

    }
    //爆炸消失是时候，归还到池塘中
    public static void GiveBack(Explode explode){
        if(pool.size() == POOL_MAX_SIZE){
            return;
        }
        pool.add(explode);
        //验证
        //System.out.println("归还后的对象数：" + pool.size());
    }
}
