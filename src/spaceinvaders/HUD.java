package spaceinvaders;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class HUD extends GameObject {

    public static final int MAX_HEALTH = 200;
    public static int HEALTH;
    public static int SCORE;
    
    private int WIDTH = 200;
    private int HEIGHT = 16;

    public HUD(int x, int y) {
        super(x, y);
        HEALTH = MAX_HEALTH;
        SCORE = 0;
    }

    @Override
    public void tick() {
        HEALTH = Game.delimit(HEALTH, 0, MAX_HEALTH);
    }

    @Override
    public void render(Graphics g) {
        if (HEALTH > MAX_HEALTH / 2) {
            g.setColor(Color.green);
        } else if (HEALTH > MAX_HEALTH / 4) {
            g.setColor(Color.orange);
        } else {
            g.setColor(Color.red);
        }

        float fillFactor = (float) HEALTH / MAX_HEALTH;
        g.fillRect(x, y, (int) (WIDTH * fillFactor), HEIGHT);
        g.setColor(Color.white);
        g.drawRect(x, y, WIDTH, HEIGHT);

        g.drawString(String.format("SCORE   %021d", SCORE), x, y + 30);

    }

    @Override
    public Rectangle getBoxCollider() {
        return new Rectangle(0, 0, 0, 0);
    }

    @Override
    public void checkCollision() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
