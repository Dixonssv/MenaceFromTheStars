package spaceinvaders;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Bullet extends GameObject {

    private int sizeX = 2;
    private int sizeY = 4;
    private Handler handler;
    private int damage;
    public GameObject shooter;

    public Bullet(int x, int y, int vel, int dirX,int dirY,int damage ,Handler handler, GameObject shooter) {
        super(x, y);
        velY = vel * dirY;
        velX = vel * dirX;
        this.handler = handler;
        this.damage = damage;
        this.shooter = shooter;
        
        if(shooter instanceof Enemy){
            sizeX =4;
            sizeY =8;
        }
    }

    @Override
    public void tick() {
        y += velY;
        x += velX;

    }

    @Override
    public void render(Graphics g) {
        if (shooter instanceof Enemy) {
            g.setColor(Color.red);
        } else if (shooter instanceof Player) {
            g.setColor(Color.green);
        }

        g.fillRect(x, y, sizeX, sizeY);
    }

    @Override
    public Rectangle getBoxCollider() {
        return new Rectangle(x, y, 2, 4);
    }
 @Override
    public void checkCollision() {
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

   
    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }
    

}
