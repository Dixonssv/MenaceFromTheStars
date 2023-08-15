package spaceinvaders.animations;

import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import spaceinvaders.GameObject;

public class Animation<T> implements Runnable {

    public enum Action {
        LOWER_BRIGHTNESS,
        HIGHER_BRIGHTNESS,
    }

    protected GameObject object;
    protected float delay;
    protected int duration; // duracion en milisegundos
    protected boolean loop;

    private ArrayList<Keyframe> keyFrames;
    private PropertyEditor propertyEditor;
    private Thread thread;
    private boolean running;

    public Animation(GameObject object, float delay, int duration, boolean loop) {
        this.object = object;
        this.delay = delay;
        this.duration = duration;
        this.loop = loop;

        this.keyFrames = new ArrayList<Keyframe>();
        this.propertyEditor = new PropertyEditor(this.object);
    }

    public synchronized void start() {  
        //System.out.println("Starting new animation...");
        
        this.thread = new Thread(this);
        this.thread.start();
        
        this.running = true;
    }

    public synchronized void stop() {
        try {
            this.running = false;
            this.thread.join();
        } catch (InterruptedException ex) {
        }
    }

    public void addKeyFrame(Keyframe keyframe) {
        this.keyFrames.add(keyframe);
    }

    @Override
    public void run() {
        long runningTime = 0;

        try {
            do {
                for (Keyframe keyframe : this.keyFrames) {
                    long timeOut = (this.duration * keyframe.getPercentage() / 100) - runningTime;

                    Thread.sleep(timeOut);
                    runningTime += timeOut;

                    for (Action action : keyframe.getActions()) {
                        this.animate(action);
                    }
                }
            } while (this.loop);
            this.stop();
        } catch (InterruptedException ex) {
            System.out.println("Animation interrupted");
            this.propertyEditor.restoreBrightness();
        }
    }
    
    private void animate(Action action) {
        switch (action) {
            case LOWER_BRIGHTNESS:
                this.propertyEditor.decreaseBrightness();
                break;
            case HIGHER_BRIGHTNESS:
                this.propertyEditor.increaseBrightness();
                break;
            default:
                throw new AssertionError();
        }
    }
    
    public boolean isRunning() {
        return this.running;
    }

}
