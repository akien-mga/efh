package ru.znay.znay.he.model.builds.building;

import ru.znay.znay.he.gfx.helper.BitmapHelper;

/**
 * Created by IntelliJ IDEA.
 * User: Денис Сергеевич
 * Date: 16.04.12
 * Time: 19:45
 * To change this template use File | Settings | File Templates.
 */
public class TownHall extends Building {

    public TownHall(int x, int y) {
        super(x, y, 50, 20);

        this.sprite = BitmapHelper.loadBitmapFromResources("/buildings/TownHall.png");
    }
}