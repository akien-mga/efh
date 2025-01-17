package ru.znay.znay.he.gfx.gui;

import ru.znay.znay.he.InputHandler;
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
        public boolean result(int result);
    }

    protected List<GuiTextPanel> panels = new LinkedList<GuiTextPanel>();
    protected Callback callback = null;
    protected int currentCell = -1;

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

        if (visible) {
            this.callback.result(-1);
        }

        GuiManager.isOpenedMenu = true;

        this.callback = callback;
        int i = 0;
        panels.clear();
        for (String s : strings) {
            panels.add(new GuiTextPanel(s, x, y + (i * (Tile.HALF_SIZE * 3)), GuiManager.FONT_COLOR, (i++ == 0) ? GuiManager.MENU_COLOR_SEL : GuiManager.MENU_COLOR));
        }
        currentCell = 0;
        changed = true;
        visible = true;
    }

    public void selectNext() {
        if (!visible) return;

        int lastCell = currentCell;
        currentCell++;

        if (currentCell >= panels.size()) {
            currentCell = 0;
        }

        panels.get(lastCell).setPanelColor(GuiManager.MENU_COLOR);

        panels.get(currentCell).setPanelColor(GuiManager.MENU_COLOR_SEL);

        changed = true;
    }

    public void selectPrev() {
        if (!visible) return;

        int lastCell = currentCell;

        currentCell--;

        if (currentCell < 0)
            currentCell = panels.size() - 1;

        panels.get(lastCell).setPanelColor(GuiManager.MENU_COLOR);

        panels.get(currentCell).setPanelColor(GuiManager.MENU_COLOR_SEL);

        changed = true;
    }

    @Override
    public void tick() {
        if (!visible) return;

        if (InputHandler.getInstance(null).down.clicked) {
            selectNext();
        }

        if (InputHandler.getInstance(null).up.clicked) {
            selectPrev();
        }

        if (InputHandler.getInstance(null).action.clicked) {
            select();
        }
    }

    public void select() {
        if (!visible) return;
        if (!callback.result(currentCell)) {
            setVisible(false);
        }
    }

    @Override
    public void render(Screen screen) {
        if (!visible) return;
        for (GuiTextPanel panel : panels)
            panel.render(screen);
    }
}
