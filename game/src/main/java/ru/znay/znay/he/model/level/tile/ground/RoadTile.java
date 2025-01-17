package ru.znay.znay.he.model.level.tile.ground;

import ru.znay.znay.he.gfx.helper.PaletteHelper;
import ru.znay.znay.he.gfx.model.Screen;
import ru.znay.znay.he.model.level.Level;
import ru.znay.znay.he.model.level.tile.Tile;

/**
 * Created by IntelliJ IDEA.
 * User: ASTarasov
 * Date: 26.03.12
 * Time: 10:56
 * To change this template use File | Settings | File Templates.
 */
public class RoadTile extends Tile {

    public RoadTile(int id) {
        super(id);
        slowPeriod = 50;
    }

    public void render(Screen screen, Level level, int x, int y) {

        int transitionColor = PaletteHelper.getColor(roadMainColor - 110, roadMainColor, roadMainColor - 110, grassMainColor);

        boolean u = (level.getTile(x, y - 1) != Tile.road);
        boolean d = (level.getTile(x, y + 1) != Tile.road);
        boolean l = (level.getTile(x - 1, y) != Tile.road);
        boolean r = (level.getTile(x + 1, y) != Tile.road);

        if (!u && !l) {
            screen.render(x * Tile.SIZE + 0, y * Tile.SIZE + 0, 3 * Tile.HALF_SIZE, 0, Tile.HALF_SIZE, Tile.HALF_SIZE, roadColor, 0);
        } else
            screen.render(x * Tile.SIZE + 0, y * Tile.SIZE + 0, (l ? 11 : 12) * Tile.HALF_SIZE, (u ? 0 : 1) * Tile.HALF_SIZE, Tile.HALF_SIZE, Tile.HALF_SIZE, transitionColor, 0);

        if (!u && !r) {
            screen.render(x * Tile.SIZE + Tile.HALF_SIZE, y * Tile.SIZE + 0, 0, 0, Tile.HALF_SIZE, Tile.HALF_SIZE, roadColor, 0);
        } else
            screen.render(x * Tile.SIZE + Tile.HALF_SIZE, y * Tile.SIZE + 0, (r ? 13 : 12) * Tile.HALF_SIZE, (u ? 0 : 1) * Tile.HALF_SIZE, Tile.HALF_SIZE, Tile.HALF_SIZE, transitionColor, 0);

        if (!d && !l) {
            screen.render(x * Tile.SIZE + 0, y * Tile.SIZE + Tile.HALF_SIZE, 2 * Tile.HALF_SIZE, 0, Tile.HALF_SIZE, Tile.HALF_SIZE, roadColor, 0);
        } else
            screen.render(x * Tile.SIZE + 0, y * Tile.SIZE + Tile.HALF_SIZE, (l ? 11 : 12) * Tile.HALF_SIZE, (d ? 2 : 1) * Tile.HALF_SIZE, Tile.HALF_SIZE, Tile.HALF_SIZE, transitionColor, 0);

        if (!d && !r) {
            screen.render(x * Tile.SIZE + Tile.HALF_SIZE, y * Tile.SIZE + Tile.HALF_SIZE, 1 * Tile.HALF_SIZE, 0, Tile.HALF_SIZE, Tile.HALF_SIZE, roadColor, 0);
        } else
            screen.render(x * Tile.SIZE + Tile.HALF_SIZE, y * Tile.SIZE + Tile.HALF_SIZE, (r ? 13 : 12) * Tile.HALF_SIZE, (d ? 2 : 1) * Tile.HALF_SIZE, Tile.HALF_SIZE, Tile.HALF_SIZE, transitionColor, 0);
    }

}
