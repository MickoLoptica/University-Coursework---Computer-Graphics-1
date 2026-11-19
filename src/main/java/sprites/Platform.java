package sprites;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Platform extends  Sprite {

    private double velocity;
    private double length;
    private double height = 10;

    private Rectangle platform;

    public Platform(double velocity) {

        this.velocity = velocity;

        length = Math.random() * 300 + 200;
        platform = new Rectangle(-0.5, -0.5, length, height);
        platform.setFill(Color.FIREBRICK);
        getChildren().add(platform);

    }

    public double getLength() { return length; }

    public double getHeight() { return height; }

    @Override
    public void update() {
        setTranslateX(getTranslateX() - velocity);
    }

}
