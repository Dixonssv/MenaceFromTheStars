package spaceinvaders;

public abstract class Enemy extends SpaceShip implements Damageable {

    protected long timer;
    protected int attackDelay;
    protected boolean alive = true;
    protected int HEALTH, MAX_HEALTH;
    protected int scoreValue;

    public Enemy(int x, int y, Handler handler) {
        super(x, y);
        this.handler = handler;
    }

    public abstract void attack();

    public int getScoreValue() {
        return this.scoreValue;
    }

    public void setScoreValue(int scoreValue) {
        this.scoreValue = scoreValue;
    }
    
}
