package sprites;

import javafx.scene.paint.Color;
import javafx.scene.shape.QuadCurve;

public class Bird extends Sprite {

    private double velocity;
    private double rotationVelocity = 1;

    private QuadCurve leftWing;
    private QuadCurve rightWing;

    public Bird(double velocity) {

        this.velocity = velocity;

        leftWing = new QuadCurve(-20, 0, -10, -20, 0, 0);
        leftWing.setStroke(Color.BLACK);
        leftWing.setFill(Color.TRANSPARENT);
        leftWing.setStrokeWidth(3);

        rightWing = new QuadCurve(0, 0, 10, -20, 20, 0);
        rightWing.setStroke(Color.BLACK);
        rightWing.setFill(Color.TRANSPARENT);
        rightWing.setStrokeWidth(3);

        getChildren().addAll(leftWing, rightWing);

    }

    @Override
    public void update() {

        setTranslateX(getTranslateX() - velocity);

        if (leftWing.getRotate() <= -15 || leftWing.getRotate() >= 15) {
            rotationVelocity *= -1;
        }
        leftWing.setRotate(leftWing.getRotate() + rotationVelocity);
        rightWing.setRotate(rightWing.getRotate() - rotationVelocity);

    }

}
