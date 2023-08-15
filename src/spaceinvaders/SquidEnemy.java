package spaceinvaders;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import spaceinvaders.animations.BlinkingAnimation;

public class SquidEnemy extends Enemy {

    public static final int sizeX = 80;
    public static final int sizeY = 80;
    public int offset;
    private Player target;

    public SquidEnemy(Player target, Handler handler) {
        super((Window.paneWidth - sizeX) / 2, 0, handler);
        this.target = target;

        MAX_HEALTH = 1000;
        HEALTH = MAX_HEALTH;

        this.scoreValue = 3000;

        velY = 1;
        velX = 1;
        offset = 50;

        attackDelay = 4000;
        timer = System.currentTimeMillis();

        canyon = new Canyon(x + sizeX / 2, y + sizeY / 2, 32, Canyon.DOWN, handler);

        try {
            sprite = ImageIO.read(Game.class.getResourceAsStream("Images/squid.PNG"));

            //sprite = ImageIO.read(new File("squid.png"));
        } catch (IOException e) {

        }
    }

    @Override
    public void tick() {
        int distance;
        y += velY;
        x += velX;

        distance = (target.x + Player.getSizeX() / 2) - (x + sizeX / 2);

        if (distance > 0) {
            velX = Game.delimit(2, 0, distance);
        } else if (distance < 0) {
            velX = Game.delimit(-2, distance, 0);
        } else {
            velX = 0;
        }

        if (velY > 0 && y >= Window.paneHeight / 2 - sizeY) {
            velY *= -1;
        } else if (velY < 0 && y <= 50) {
            velY *= -1;
        }

        canyon.setX(x + sizeX / 2);
        canyon.setY(y + sizeY / 2);

        if (System.currentTimeMillis() - timer >= attackDelay) {
            attack();
            timer = System.currentTimeMillis();
        }
        checkCollision();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(sprite, x, y, sizeX, sizeY, null);

        //g.drawRect(x + 20, y + 365, 60, 60);
    }

    @Override
    public Rectangle getBoxCollider() {
        return new Rectangle(x, y, sizeX, sizeY - 10);
    }

    @Override
    public void checkCollision() {
        for (GameObject object : handler.objects) {
            if (object instanceof Bullet && ((Bullet) object).shooter instanceof Player) {
                if (this.getBoxCollider().intersects(object.getBoxCollider())) {
                    Bullet bullet = (Bullet) object;
                    Player player = (Player) bullet.shooter;

                    handler.removeObject(bullet);

                    this.receiveDamage(bullet.getDamage());

                    if (!this.alive) {
                        player.addScore(this.scoreValue);
                        handler.removeObject(this);
                    }
                }
            }
        }
    }

    @Override
    public void attack() {
        canyon.shoot(this);

    }

    @Override
    public void receiveDamage(int damage) {
        this.animator.animate(new BlinkingAnimation(this, 0f, 500, false));

        this.HEALTH -= damage;

        if (this.HEALTH <= 0) {
            this.alive = false;
        }
    }

}
