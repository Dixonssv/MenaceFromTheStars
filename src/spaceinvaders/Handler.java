package spaceinvaders;

import java.awt.Graphics;
import java.util.ArrayList;

public class Handler {

    ArrayList<GameObject> objects = new ArrayList();
//    ArrayList<GameObject> buffer = new ArrayList();
    private Player player;

    public void tick() {
        // System.out.println("Hilo: " + Thread.currentThread().getId());
        try {
            for (GameObject object : objects) {
                object.tick();
                if (object instanceof Player) {
                    this.player = (Player) object;
                }
                if ((object.getY() < 0 || object.getY() >= Window.paneHeight) && !(object instanceof BossEnemy)) {
                    removeObject(object);
                    if (object instanceof Enemy) {
                        this.player.setScore(Game.delimit(this.player.getScore() - ((Enemy) object).getScoreValue()*2, 0, Integer.MAX_VALUE));
                    }
                }
            }
        } catch (java.util.ConcurrentModificationException e) {

        }

    }

    public void render(Graphics g) {

        try {

            for (GameObject object : objects) {
                object.render(g);
            }

        } catch (java.util.ConcurrentModificationException e) {

        }
    }

    public void addObject(GameObject object) {
        try {
            this.objects.add(object);
        } catch (java.util.ConcurrentModificationException e) {

        }

    }

    public void removeObject(GameObject object) {
        this.objects.remove(object);
    }

}
