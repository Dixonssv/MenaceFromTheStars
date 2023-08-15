package spaceinvaders;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import spaceinvaders.animations.BlinkingAnimation;

public class Player extends SpaceShip implements Damageable {

    private static int sizeX = 32;
    private static int sizeY = 32;
    private Handler handler;
    private long timer;
    private int attackDelay;
    private int score;

    public Player(int x, int y, Handler handler) {
        super(x, y);
        this.handler = handler;
        this.score = 0;

        timer = System.currentTimeMillis();
        this.attackDelay = 250;

        canyon = new Canyon(x + sizeX / 2, y, 8, Canyon.UP, handler);

        /*
        new File("images/grass.png") looks for a directory images on the file system, in the current directory, which is the directory 
        from which the application is started. So that's wrong.

        ImageIO.read() returns a BufferedImage, and takes a URL or an InputStream as argument. To get an URL of InputStream from the classpath, 
        you use Class.getResource() or Class.getResourceAsStream(). And the path starts with a /, and starts at the root of the classpath.

        So, the following code should work if the grass.png file is under the package images in the classpath:
        */
        
        try {          
            sprite = ImageIO.read(Game.class.getResourceAsStream("Images/player.png"));
            //sprite = ImageIO.read(new File("player.png"));

        } catch (IOException e) {

        }

    }

    @Override
    public void tick() {
        x += velX;
        x = Game.delimit(x, 0, Window.paneWidth - sizeX);

        checkCollision();

        canyon.setX(x + sizeX / 2);
        
        HUD.SCORE=this.score;
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
            if (object instanceof Enemy) {
                if (this.getBoxCollider().intersects(object.getBoxCollider())) {
                    this.receiveDamage(30);

                    handler.removeObject(object);
                }
            }
            if (object instanceof Bullet && ((Bullet) object).shooter instanceof Enemy) {
                if (this.getBoxCollider().intersects(object.getBoxCollider())) {
                    Bullet bullet = (Bullet) object;
                    
                    this.receiveDamage(bullet.getDamage());

                    handler.removeObject(object);
                }
            }
            if (object instanceof Bonus) {
                if (this.getBoxCollider().intersects(object.getBoxCollider())) {
                    this.score += Bonus.getValue();

                    handler.removeObject(object);
                }
            }
        }
    }
    
    @Override
    public void receiveDamage(int damage) {
        this.animator.animate(new BlinkingAnimation(this, 0f, 500, false));
        
        HUD.HEALTH -= damage;
        
        if (HUD.HEALTH <= 0) {
            handler.removeObject(this);
        }
    }
    
    public void increaseHealth(int healthIncrease) {
        //System.out.println("HUD Health: " + HUD.HEALTH);
        //System.out.println("Health increase: " + healthIncrease);
        HUD.HEALTH = Game.delimit(HUD.HEALTH += healthIncrease, 0, HUD.MAX_HEALTH);
        
        //System.out.println("HUD Health: " + HUD.HEALTH);
    }

    public void attack() {
        if (System.currentTimeMillis() - timer >= attackDelay) {
            canyon.shoot(this);
            timer = System.currentTimeMillis();
        }
    }

    public static int getSizeX() {
        return sizeX;
    }

    public void setSizeX(int sizeX) {
        this.sizeX = sizeX;
    }

    public static int getSizeY() {
        return sizeY;
    }

    public void setSizeY(int sizeY) {
        this.sizeY = sizeY;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addScore(int value) {
        this.score += value;

    }


}
