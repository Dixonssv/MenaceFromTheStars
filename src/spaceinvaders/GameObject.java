package spaceinvaders;

import java.awt.image.BufferedImage;
import spaceinvaders.animations.Animator;

public abstract class GameObject implements GameObjectMethods {

    protected int x, y;
    protected int velX, velY;
    protected BufferedImage sprite = null;
    protected Handler handler;
    public Animator animator;


    public GameObject(int x, int y) {
        this.x = x;
        this.y = y;
        
        this.animator = new Animator(this);
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

    public int getVelX() {
        return velX;
    }

    public void setVelX(int velX) {
        this.velX = velX;
    }

    public int getVelY() {
        return velY;
    }

    public void setVelY(int velY) {
        this.velY = velY;
    }

    public BufferedImage getSprite() {
        return sprite;
    }

    public void setSprite(BufferedImage sprite) {
        this.sprite = sprite;
    }
    
    

}
