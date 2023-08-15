package spaceinvaders;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Bonus extends GameObject {

    public static final int sizeX = 40;
    public static final int sizeY = 20;
    private static int value;
    
    public Bonus(int x, int y, Handler handler) {
        super(x, y);
        this.handler = handler;
        
        value = 50;
        
        velY = 2;
        velX = 2;
        
        try {
            sprite = ImageIO.read(Game.class.getResourceAsStream("Images/ufo.png"));

            //sprite = ImageIO.read(new File("ufo.png"));

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
        if (y <= 0 || y >= Window.paneHeight - sizeY) {
            velY *= -1;
        }
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

    }

    public static int getValue() {
        return value;
    }
    
}
