package spaceinvaders.animations;

import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import spaceinvaders.GameObject;

public class PropertyEditor {

    GameObject object;

    float brightness = 1;

    public PropertyEditor(GameObject object) {
        this.object = object;
    }

    public void increaseBrightness() {
        if (brightness < 1) {
            this.setBrightness(2);
        }
    }

    public void decreaseBrightness() {
        if (brightness >= 1) {
            this.setBrightness(0.5f);
        }
    }
    
    public void restoreBrightness() {
        this.increaseBrightness();
        this.decreaseBrightness();
    }

    private void setBrightness(float value) {
        this.brightness = value;
        
        BufferedImage sprite = this.object.getSprite();

        RescaleOp op = new RescaleOp(value, 0.0f, null);
        sprite = op.filter(sprite, null);

        this.object.setSprite(sprite);
    }
}
