package spaceinvaders;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import spaceinvaders.animations.BlinkingAnimation;

public class BossEnemy extends Enemy {

    public static final int sizeX = 600;
    public static final int sizeY = 500;
    public int offset;
    ArrayList<Canyon> canyons;

    public BossEnemy(Handler handler) {
        super((Window.paneWidth - sizeX) / 2, -sizeY, handler);

        MAX_HEALTH = 1500;
        HEALTH = MAX_HEALTH;

        this.scoreValue=5000;
        
        velY = 1;
        velX = 1;

        offset =50;
        attackDelay = 300;
        timer = System.currentTimeMillis();

        canyons = new ArrayList();
        canyons.add(new Canyon(x + sizeX / 2, y, 4, Canyon.DOWN, handler));
        canyons.add(new Canyon(x + sizeX / 2, y, 4, Canyon.LEFT, Canyon.DOWN, handler));
        canyons.add(new Canyon(x + sizeX / 2, y, 4, Canyon.RIGHT, Canyon.DOWN, handler));

        try {
            sprite = ImageIO.read(Game.class.getResourceAsStream("Images/boss.png"));

            //sprite = ImageIO.read(new File("boss.png"));

        } catch (IOException e) {

        }
    }

    @Override
    public void tick() {
        if (y < -(sizeY / 2)) {
            y += velY;

        } else {
            x += velX;
        }

        if (x <= -offset || x >= (Window.paneWidth - sizeX + offset)) {
            velX *= -1;
        }

        for (Canyon c : canyons) {
            c.setX(x + sizeX / 2);
            c.setY(y + 365);
        }

        if (System.currentTimeMillis() - timer >= attackDelay) {
            attack();
            timer = System.currentTimeMillis();
        }
        checkCollision();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(sprite, x, y, sizeX, sizeY, null);
        
//        g.drawRect(x + 270, y + 365, 60, 60);
    }

    @Override
    public Rectangle getBoxCollider() {
        return new Rectangle(x + 270, y + 365, 60, 60);
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
                        SpawnManager.BOSS=false;
                    }
                }
            }
        }
    }

    @Override
    public void attack() {
        for (Canyon c : canyons) {
            c.shoot(this);
        }

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
