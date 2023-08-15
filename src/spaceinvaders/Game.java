package spaceinvaders;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Game extends Canvas implements Runnable {

    public static final int WIDTH = 640, HEIGHT = WIDTH / 12 * 9;

    private Thread thread;
    private boolean running = false;
    private Handler handler;
    private HUD hud;
    private SpawnManager spawner;

    public Game() {
        handler = new Handler();
        spawner = new SpawnManager(handler);

        hud = new HUD(15, 15);

        this.addKeyListener(new KeyInput(handler));
        new Window(WIDTH, HEIGHT, "Space Invaders", this);

//        handler.addObject(new BossEnemy(handler));
//        handler.addObject(new Player(WIDTH / 2 - Player.getSizeX(), HEIGHT - 64 - Player.getSizeY(), handler));
    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        this.requestFocus();

        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while (delta >= 1) {
                this.tick();
                delta--;
            }

            this.render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
//                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }

    private void tick() {
        spawner.tick();
        handler.tick();
        hud.tick();
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        
        BufferedImage image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        
        Graphics g = bs.getDrawGraphics();
        Graphics2D g2 = image.createGraphics();
        
        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        
        handler.render(g);
        hud.render(g);
        
        /*
        g2.setColor(Color.black);
        g2.fillRect(0, 0, WIDTH, HEIGHT);
        
        handler.render(g2);
        hud.render(g2);
        
        this.reccord(image);
        g2.dispose();
        */
        
        g.dispose();
        bs.show();
        

    }

    public static int delimit(int var, int min, int max) {
        if (var >= max) {
            return max;
        } else if (var <= min) {
            return min;
        } else {
            return var;
        }
    }

    public static void main(String[] args) {
        new Game();
    }

    // ----------------------------------------------
    int scNumber = 0;
    long reccordFrameRate = 24;
    long reccordingTime = 0;

    public void reccord(BufferedImage image) {
        if (System.currentTimeMillis() - reccordingTime > 1000 / reccordFrameRate) {
            
            try {
                if (ImageIO.write(image, "png", new File("./screenshots/space_invaders_screenshot_" + scNumber + ".png"))) {
                    scNumber++;
                    //System.out.println("-- saved");
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            reccordingTime = System.currentTimeMillis();
        } 

    }

}
