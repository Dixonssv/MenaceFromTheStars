package spaceinvaders;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import spaceinvaders.animations.BlinkingAnimation;

public class SneakerEnemy extends Enemy {

    public static final int sizeX = 20;
    public static final int sizeY = 20;

    public SneakerEnemy(int x, int y, Handler handler) {
        super(x, y, handler);

        MAX_HEALTH = 50;
        HEALTH = MAX_HEALTH;

        this.scoreValue = 1000;
        velY = 1;
        velX = 7;

        attackDelay = 3000;
        timer = System.currentTimeMillis();

        canyon = new Canyon(x + sizeX / 2, y + sizeY, 4, Canyon.DOWN, handler);

        try {
            sprite = ImageIO.read(Game.class.getResourceAsStream("Images/sneaker.png"));

            //sprite = ImageIO.read(new File("sneaker.png"));

        } catch (IOException e) {

        }
    }

    @Override
    public void tick() {
        y += velY;
        x += velX;

        if (x <= 0 || x >= Window.paneWidth - sizeX) {
            velX *= -1;
        }

        canyon.setX(x + sizeX / 2);
        canyon.setY(y + sizeY);

        if (System.currentTimeMillis() - timer >= attackDelay) {
            attack();
            timer = System.currentTimeMillis();
        }
        checkCollision();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(sprite, x, y, sizeX, sizeY, null);
    }

    @Override
    public Rectangle getBoxCollider() {
        return new Rectangle(x, y, sizeX, sizeY);
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
                    
                    if(!this.alive) {
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
        
        if(this.HEALTH <= 0) {
            this.alive = false;
        }
    }

}
