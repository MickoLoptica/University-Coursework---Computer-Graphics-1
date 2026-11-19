package sprites;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import main.Main;

public class Coin extends Sprite {

    private double velocity;
    private double hoveringVelocity = -0.25;

    private boolean collected = false;

    private Circle edge;
    private Circle middle;

    public Coin(double velocity) {

        this.velocity = velocity;

        edge = new Circle(0, 0, 25);
        edge.setFill(Color.GOLD);

        middle = new Circle(0, 0, 15);
        middle.setFill(Color.YELLOW);

        getChildren().addAll(edge, middle);

    }

    public void update() {

        setTranslateX(getTranslateX() - velocity);

        if (getTranslateY() <= Main.WINDOW_HEIGHT - Main.FLOOR_HEIGHT - 40 || getTranslateY() >= Main.WINDOW_HEIGHT - Main.FLOOR_HEIGHT - 30) {
            hoveringVelocity *= -1;
        }
        setTranslateY(getTranslateY() + hoveringVelocity);

    }

    public void dissapear() {
        edge.setFill(Color.TRANSPARENT);
        middle.setFill(Color.TRANSPARENT);
        collected = true;
    }

    public boolean isCollected() { return collected; }

}
