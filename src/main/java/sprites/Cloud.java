package sprites;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Cloud extends Sprite {

    private double velocity;

    public Cloud(double velocity) {
        this.velocity = velocity;

        Circle mainPart = new Circle(0, 0, 60);
        mainPart.setFill(Color.WHITE);

        Circle leftPart = new Circle(-45, 20, 40);
        leftPart.setFill(Color.WHITE);

        Circle rightPart = new Circle(45, 20, 40);
        rightPart.setFill(Color.WHITE);

        getChildren().addAll(mainPart, leftPart, rightPart);

    }

    @Override
    public void update() {
        setTranslateX(getTranslateX() - velocity);
    }

}
