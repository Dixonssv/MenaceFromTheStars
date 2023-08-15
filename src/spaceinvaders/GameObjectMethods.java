package spaceinvaders;

import java.awt.Graphics;
import java.awt.Rectangle;

public interface GameObjectMethods {
    public abstract void tick();
    public abstract void render(Graphics g);
    public abstract Rectangle getBoxCollider();
    public abstract void checkCollision();
}
