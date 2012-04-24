package ru.znay.znay.he.gfx.gui;

import ru.znay.znay.he.InputHandler;
import ru.znay.znay.he.gfx.helper.PaletteHelper;
import ru.znay.znay.he.gfx.model.Screen;
import ru.znay.znay.he.model.level.tile.Tile;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Денис Сергеевич
 * Date: 06.04.12
 * Time: 22:58
 * To change this template use File | Settings | File Templates.
 */
public class GuiMenu extends GuiPanel {
    public interface Callback {
        public void result(int result);
    }

    private List<GuiTextPanel> panels = new LinkedList<GuiTextPanel>();
    private Callback callback = null;
    private int currentCell = -1;

    public GuiMenu(int x, int y) {
        this.x = x;
        this.y = y;
        visible = false;
    }

    public void showMenu(List<String> strings, Callback callback) {
        if (strings == null || callback == null || strings.size() == 0) {
            System.out.println("showMenu error - null pointer");
            return;
        }

        if (visible) this.callback.result(-1);

        GuiManager.isOpenedMenu = true;

        this.callback = callback;
        int i = 0;
        panels.clear();
        for (String s : strings)
            panels.add(new GuiTextPanel(s, x, y + (i * (Tile.HALF_SIZE * 3)), PaletteHelper.getColor(-1, -1, -1, 555), (i++ == 0) ? PaletteHelper.getColor(-1, 1, 30, 445) : PaletteHelper.getColor(-1, 1, 5, 445)));
        currentCell = 0;
        changed = true;
        visible = true;
    }

    public void selectNext() {
        if (!visible) return;

        int lastCell = currentCell;
        currentCell++;

        if (currentCell >= panels.size())
            currentCell = 0;

        panels.get(lastCell).setPanelColor(PaletteHelper.getColor(-1, 1, 5, 445));

        panels.get(currentCell).setPanelColor(PaletteHelper.getColor(-1, 1, 30, 445));

        changed = true;
    }

    public void selectPrev() {
        if (!visible) return;

        int lastCell = currentCell;

        currentCell--;

        if (currentCell < 0)
            currentCell = panels.size() - 1;

        panels.get(lastCell).setPanelColor(PaletteHelper.getColor(-1, 1, 5, 445));

        panels.get(currentCell).setPanelColor(PaletteHelper.getColor(-1, 1, 30, 445));

        changed = true;
    }

    @Override
    public void tick() {
        if (!visible) return;

        if (InputHandler.getInstance().down.clicked) {
            selectNext();
        }

        if (InputHandler.getInstance().up.clicked) {
            selectPrev();
        }

        if (InputHandler.getInstance().action.clicked) {
            select();
        }
    }

    public void select() {
        if (!visible) return;
        callback.result(currentCell);
        visible = false;
        GuiManager.isOpenedMenu = false;
    }

    @Override
    public void render(Screen screen) {
        if (!visible) return;
        for (GuiTextPanel panel : panels)
            panel.render(screen);
    }
}