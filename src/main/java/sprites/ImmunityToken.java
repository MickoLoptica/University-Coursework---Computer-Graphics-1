package sprites;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import main.Main;

public class ImmunityToken extends Sprite {

    private double velocity;
    private double hoveringVelocity = -0.25;

    private boolean collected = false;

    private Circle token;

    public ImmunityToken(double velocity) {

        this.velocity = velocity;

        token = new Circle(0, 0, 25);
        token.setFill(Color.BLUE);
        token.setOpacity(0.25);

        getChildren().add(token);

    }

    public void update() {

        setTranslateX(getTranslateX() - velocity);

        if (getTranslateY() <= Main.WINDOW_HEIGHT - Main.FLOOR_HEIGHT - 40 || getTranslateY() >= Main.WINDOW_HEIGHT - Main.FLOOR_HEIGHT - 30) {
            hoveringVelocity *= -1;
        }
        setTranslateY(getTranslateY() + hoveringVelocity);

    }

    public void dissapear() {
        token.setFill(Color.TRANSPARENT);
        collected = true;
    }

    public boolean isCollected() { return collected; }

}
