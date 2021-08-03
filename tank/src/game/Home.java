package game;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

//泉水
public class Home {
    public static final int HOME_X = Constant.FRAME_WIDTH - 3 * MapTile.width>>1;
    public static final int HOME_Y = Constant.FRAME_HEIGHT - 2 * MapTile.width - 15;

    private List<MapTile> tiles = new ArrayList<>();
    public Home() {
        tiles.add(new MapTile(HOME_X,HOME_Y));
        tiles.add(new MapTile(HOME_X + MapTile.width,HOME_Y));
        tiles.add(new MapTile(HOME_X + MapTile.width*2,HOME_Y));
        tiles.add(new MapTile(HOME_X,HOME_Y + MapTile.width));
        tiles.add(new MapTile(HOME_X + MapTile.width,HOME_Y + MapTile.width));
        tiles.add(new MapTile(HOME_X + MapTile.width*2,HOME_Y + MapTile.width));

        for (int i = 0; i < tiles.size(); i++) {
            tiles.get(i).setType(3);
        }
    }

    public void draw(Graphics g){
        for (int i = 0; i < tiles.size(); i++) {
            MapTile tile = tiles.get(i);
            tile.draw(g);
        }
    }

    public List<MapTile> getTiles() {
        return tiles;
    }
}
