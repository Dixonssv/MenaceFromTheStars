package spaceinvaders;

public class Canyon {

    public static final int UP = -1;
    public static final int DOWN = 1;
    public static final int LEFT = -1;
    public static final int RIGHT = 1;

    private int x, y;
    private int vel, dirX, dirY;
    private Handler handler;
    

    public Canyon(int x, int y, int vel, int dir, Handler handler) {
        this.handler = handler;
        this.x = x;
        this.y = y;
        this.vel = vel;
        this.dirX=0;
        this.dirY = dir;
    }

    public Canyon(int x, int y, int vel, int dirX, int dirY, Handler handler) {
        this.handler = handler;
        this.x = x;
        this.y = y;
        this.vel = vel;
        this.dirX = dirX;
        this.dirY = dirY;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public synchronized void shoot(GameObject shooter) {
        int damage;

        damage = shooter instanceof Player ? 50 : shooter instanceof SquidEnemy ? 20 : 5;
        Bullet bullet = new Bullet(x, y, vel, dirX, dirY, damage, handler, shooter);
        bullet.setX(x - bullet.getSizeX() / 2);
        handler.addObject(bullet);
    }

}
