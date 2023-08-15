package spaceinvaders.animations;

import java.util.logging.Level;
import java.util.logging.Logger;
import spaceinvaders.GameObject;

public class Animator {

    private GameObject object;

    private Animation currentAnimation;

    public Animator(GameObject object) {
        this.object = object;
        this.currentAnimation = null;
    }

    public void animate(Animation animation) {
        
        if(this.currentAnimation == null || !this.currentAnimation.isRunning()) {
            this.currentAnimation = animation;
            this.currentAnimation.start();
        }
    }
}
