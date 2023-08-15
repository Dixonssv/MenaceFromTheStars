package spaceinvaders;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

    private Handler handler;
    private boolean keyDownL = false;
    private boolean keyDownR = false;

    public KeyInput(Handler handler) {
        this.handler = handler;
    }

    @Override
    public synchronized void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        Player player = null;

        for (GameObject object : handler.objects) {
            if (object instanceof Player) {
                player = (Player) object;
            }
        }
        if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
            player.setVelX(6);
            keyDownR = true;
        }
        if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
            player.setVelX(-6);
            keyDownL = true;
        }
        if (key == KeyEvent.VK_SPACE) {
            player.attack();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        for (GameObject object : handler.objects) {
            if (object instanceof Player) {
                if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
//                    ((Player) object).setVelX(0);
                    keyDownR = false;
                }
                if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
//                    ((Player) object).setVelX(0);
                    keyDownL = false;
                }
                
                if (!keyDownR && !keyDownL)
                    ((Player) object).setVelX(0);

            }
        }
    }
}
