package ru.znay.znay.he.model;

import ru.znay.znay.he.gfx.model.Font;
import ru.znay.znay.he.model.level.Level;
import ru.znay.znay.he.model.level.tile.Tile;
import ru.znay.znay.he.model.particle.BloodParticle;
import ru.znay.znay.he.model.particle.FlowText;
import ru.znay.znay.he.model.weapon.arrow.Arrow;
import ru.znay.znay.he.sound.Sound;

/**
 * Created by IntelliJ IDEA.
 * User: Александр Сергеевич
 * Date: 04.03.12
 * Time: 11:53
 * To change this template use File | Settings | File Templates.
 */
public class Mob extends Entity {

    private final static int DEFAULT_KNOCKBACK = 6;
    protected long tickTime = 0;
    protected int hurtTime = 0;
    protected int walkDist = 0;
    protected int dir = 0;
    protected int health = 10;
    protected int xKnockback, yKnockback;
    protected int viewRadius = 4;
    protected Mob target = null;
    protected int bloodColor = 0xcc1100;

    protected CharacterState defaultState = new CharacterState(0, 10, 0, 0, 50);
    protected CharacterState currentState = new CharacterState(0, 10, 0, 0, 50);
    protected CharacterState compareState = new CharacterState(0, 10, 0, 0, 50);

    @Override
    public void tick() {
        tickTime++;
        if (!this.canFly() && level.getTile(x >> 4, y >> 4) == Tile.lava) {
            hurt(this, 4, dir ^ 1);
        }

        if (health <= 0) {
            die();
        }

        if (canRegenerate() && health < currentState.getEndurance() && tickTime % (60 * 7) == 0) {
            int oldHealth = health;
            health = Math.min(currentState.getEndurance(), health + currentState.getEndurance() / 10);
            level.add(new FlowText("+" + (health - oldHealth), x, y, Font.greenColor));
        }

        if (!compareState.match(currentState)) {
            updateState();
            compareState = currentState.mergeStates(new CharacterState());
        }

        if (hurtTime > 0) hurtTime--;
        super.tick();
    }

    public void updateState() {

    }

    @Override
    public boolean move(int xa, int ya) {

        if (xKnockback < 0) {
            move2(-1, 0);
            xKnockback++;
        }
        if (xKnockback > 0) {
            move2(1, 0);
            xKnockback--;
        }
        if (yKnockback < 0) {
            move2(0, -1);
            yKnockback++;
        }
        if (yKnockback > 0) {
            move2(0, 1);
            yKnockback--;
        }
        if (hurtTime > 0) return true;
        if (xa != 0 || ya != 0) {
            walkDist++;
            if (xa < 0) dir = 2;
            if (xa > 0) dir = 3;
            if (ya < 0) dir = 1;
            if (ya > 0) dir = 0;
        }

        if (tickTime % currentState.getSlowPeriod() == 0) {
            return true;
        }

        return super.move(xa, ya);
    }

    public boolean canRegenerate() {
        return false;
    }

    @Override
    public void touchedBy(Entity entity) {
        if (entity instanceof Arrow) {
            Arrow arrow = (Arrow) entity;
            if (this.team != arrow.getOwnerTeam()) {
                hurt(this, arrow.getDamage(), dir ^ 1);
                arrow.setRemoved(true);
                Sound.hit.play();
                //Sound.monsterHurt.play();
            }
        }
    }

    @Override
    public void hurt(Mob mob, int damage, int attackDir) {
        if (this.team == ETeam.NEUTRAL_TEAM) return;
        doHurt(damage, attackDir);
    }

    protected void doHurt(int damage, int attackDir) {
        if (hurtTime > 0) return;

        if (this instanceof Player) {
            Sound.playerHurt.play();
            // level.add(new TextParticle(damage + "", x, y, bloodColor));
        }

        level.add(new FlowText("-" + damage, x, y - Tile.HALF_SIZE, Font.redColor));

        for (int i = 0; i < damage; i++) {
            level.add(new BloodParticle(x, y, bloodColor));
        }

        health -= damage;
        if (attackDir == 0) yKnockback = +DEFAULT_KNOCKBACK;
        if (attackDir == 1) yKnockback = -DEFAULT_KNOCKBACK;
        if (attackDir == 2) xKnockback = -DEFAULT_KNOCKBACK;
        if (attackDir == 3) xKnockback = +DEFAULT_KNOCKBACK;
        hurtTime = 10;
    }

    public boolean findStartPos(Level level) {
        int x = random.nextInt(level.getWidth());
        int y = random.nextInt(level.getHeight());
        int xx = x * Tile.SIZE + Tile.HALF_SIZE;
        int yy = y * Tile.SIZE + Tile.HALF_SIZE;

        if (level.getPlayer() != null) {
            int xd = level.getPlayer().getX() - xx;
            int yd = level.getPlayer().getY() - yy;
            if (xd * xd + yd * yd < 80 * 80) return false;
        }

        this.x = xx;
        this.y = yy;

        if (!this.canFly()) {
            int r = level.getMonsterDensity() * Tile.SIZE;
            if (level.getEntities(xx - r, yy - r, xx + r, yy + r, null).size() > 0) return false;
            if (!level.getTile(x, y).mayPass(level, x, y, this)) return false;
        }

        return true;
    }

    public long getTickTime() {
        return tickTime;
    }

    public int getHurtTime() {
        return hurtTime;
    }

    public int getWalkDist() {
        return walkDist;
    }

    public int getDir() {
        return dir;
    }

    public int getHealth() {
        return health;
    }

    public int getxKnockback() {
        return xKnockback;
    }

    public int getyKnockback() {
        return yKnockback;
    }

    public void setSlowPeriod(int slowPeriod) {
        this.currentState.setSlowPeriod(slowPeriod);
    }

    public int getSlowPeriod() {
        return this.currentState.getSlowPeriod();
    }

    public void setHealth(int health) {
        this.health = Math.min(currentState.getEndurance(), health);
    }
}
