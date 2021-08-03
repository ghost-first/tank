package game;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

//游戏相关的信息
public class GameInfo {
    private static int levelCount;

    static {
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream("Level/gameinfo"));
            levelCount = Integer.parseInt(prop.getProperty("levelCount"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getLevelCount() {
        return levelCount;
    }
}
