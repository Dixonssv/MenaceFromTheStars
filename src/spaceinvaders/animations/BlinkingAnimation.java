package spaceinvaders.animations;

import spaceinvaders.GameObject;

public class BlinkingAnimation extends Animation{

    public BlinkingAnimation(GameObject object, float delay, int duration, boolean loop) {
        super(object, delay, duration, loop);
        
        // keyframes
        this.addKeyFrame(new Keyframe(0, new Animation.Action[]{Animation.Action.LOWER_BRIGHTNESS}));
        this.addKeyFrame(new Keyframe(25, new Animation.Action[]{Animation.Action.HIGHER_BRIGHTNESS}));
        this.addKeyFrame(new Keyframe(50, new Animation.Action[]{Animation.Action.LOWER_BRIGHTNESS}));
        this.addKeyFrame(new Keyframe(75, new Animation.Action[]{Animation.Action.HIGHER_BRIGHTNESS}));
    }
    
}
