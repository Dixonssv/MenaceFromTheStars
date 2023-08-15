package spaceinvaders.animations;

public class Keyframe {

    private int percentage;
    private Animation.Action[] actions;

    public Keyframe(int percentage, Animation.Action[] actions) {
        this.percentage = percentage;
        this.actions = actions;
    }
    
    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public Animation.Action[] getActions() {
        return actions;
    }

    public void setActions(Animation.Action[] actions) {
        this.actions = actions;
    }

}
