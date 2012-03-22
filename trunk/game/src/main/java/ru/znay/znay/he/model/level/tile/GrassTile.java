package ru.znay.znay.he.model.level.tile;

import ru.znay.znay.he.gfx.helper.PaletteHelper;
import ru.znay.znay.he.gfx.model.Screen;
import ru.znay.znay.he.model.level.Level;

import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: Александр Сергеевич
 * Date: 03.03.12
 * Time: 19:01
 * To change this template use File | Settings | File Templates.
 */
public class GrassTile extends Tile {

    private Random random = new Random();

    public GrassTile(int id) {
        super(id);
        connectsToGrass = true;
    }

    @Override
    public void render(Screen screen, Level level, int x, int y) {

        int transitionColor = PaletteHelper.getColor(grassMainColor - 111, grassMainColor, grassMainColor + 111, dirtMainColor);

        boolean u = !level.getTile(x, y - 1).connectsToGrass;
        boolean d = !level.getTile(x, y + 1).connectsToGrass;
        boolean l = !level.getTile(x - 1, y).connectsToGrass;
        boolean r = !level.getTile(x + 1, y).connectsToGrass;

        if (!u && !l) {
            screen.render(x * Tile.SIZE + 0, y * Tile.SIZE + 0, 0, 0, Tile.HALF_SIZE, Tile.HALF_SIZE, grassColor, 0);
        } else
            screen.render(x * Tile.SIZE + 0, y * Tile.SIZE + 0, (l ? 11 : 12) * Tile.HALF_SIZE, (u ? 0 : 1) * Tile.HALF_SIZE, Tile.HALF_SIZE, Tile.HALF_SIZE, transitionColor, 0);

        if (!u && !r) {
            screen.render(x * Tile.SIZE + Tile.HALF_SIZE, y * Tile.SIZE + 0, 1 * Tile.HALF_SIZE, 0, Tile.HALF_SIZE, Tile.HALF_SIZE, grassColor, 0);
        } else
            screen.render(x * Tile.SIZE + Tile.HALF_SIZE, y * Tile.SIZE + 0, (r ? 13 : 12) * Tile.HALF_SIZE, (u ? 0 : 1) * Tile.HALF_SIZE, Tile.HALF_SIZE, Tile.HALF_SIZE, transitionColor, 0);

        if (!d && !l) {
            screen.render(x * Tile.SIZE + 0, y * Tile.SIZE + Tile.HALF_SIZE, 2 * Tile.HALF_SIZE, 0, Tile.HALF_SIZE, Tile.HALF_SIZE, grassColor, 0);
        } else
            screen.render(x * Tile.SIZE + 0, y * Tile.SIZE + Tile.HALF_SIZE, (l ? 11 : 12) * Tile.HALF_SIZE, (d ? 2 : 1) * Tile.HALF_SIZE, Tile.HALF_SIZE, Tile.HALF_SIZE, transitionColor, 0);
        if (!d && !r) {
            screen.render(x * Tile.SIZE + Tile.HALF_SIZE, y * Tile.SIZE + Tile.HALF_SIZE, 3 * Tile.HALF_SIZE, 0, Tile.HALF_SIZE, Tile.HALF_SIZE, grassColor, 0);
        } else
            screen.render(x * Tile.SIZE + Tile.HALF_SIZE, y * Tile.SIZE + Tile.HALF_SIZE, (r ? 13 : 12) * Tile.HALF_SIZE, (d ? 2 : 1) * Tile.HALF_SIZE, Tile.HALF_SIZE, Tile.HALF_SIZE, transitionColor, 0);
    }

    @Override
    public void tick(Level level, int xt, int yt) {

    }
}
