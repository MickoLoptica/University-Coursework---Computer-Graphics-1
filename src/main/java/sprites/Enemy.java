/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprites;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.transform.Scale;
import main.Main;

/**
 *
 * @author mile
 */
public class Enemy extends Sprite {

    private double velocity;
    private boolean shouldRotate = false;
    private boolean right = false;
    private boolean dead = false;

    private Circle leftPupil;
    private Circle rightPupil;
    private double pupilVelocity = 0.005;

    public Enemy(double velocity) {
        this.velocity = velocity;

        Polygon head = new Polygon();
        head.getPoints().addAll(new Double[]{-0.25, -0.5, -0.5, 0.0, -0.4, 0.0, -0.4, 0.3, 0.4, 0.3, 0.4, 0.0, 0.5, 0.0, 0.25, -0.5});
        head.setFill(Color.SADDLEBROWN);

        Rectangle leftLeg = new Rectangle(-0.4, 0.3, 0.2, 0.2);
        leftLeg.setFill(Color.BLACK);

        Rectangle rightLeg = new Rectangle(0.2, 0.3, 0.2, 0.2);
        rightLeg.setFill(Color.BLACK);

        Circle leftEye = new Circle(-0.2f, -0.15, 0.15);
        leftEye.setFill(Color.WHITE);

        Circle rightEye = new Circle(0.2f, -0.15, 0.15);
        rightEye.setFill(Color.WHITE);

        leftPupil = new Circle(-0.25, -0.15, 0.05);
        leftPupil.setFill(Color.BLACK);

        rightPupil = new Circle(0.15, -0.15, 0.05);
        rightPupil.setFill(Color.BLACK);

        Line mouth = new Line(-0.3, 0.15, 0.3, 0.15);
        mouth.setStroke(Color.BLACK);
        mouth.setStrokeWidth(0.03);

        Polygon leftTooth = new Polygon();
        leftTooth.getPoints().addAll(new Double[]{-0.3, 0.15, -0.2, 0.15, -0.25, 0.0});
        leftTooth.setFill(Color.WHITE);

        Polygon rightTooth = new Polygon();
        rightTooth.getPoints().addAll(new Double[]{0.2, 0.15, 0.3, 0.15, 0.25, 0.0});
        rightTooth.setFill(Color.WHITE);

        Rectangle leftBrow = new Rectangle(-0.4, -0.35, 0.4, 0.1);
        leftBrow.setFill(Color.BLACK);
        leftBrow.setRotate(30);

        Rectangle rightBrow = new Rectangle(0.0, -0.35, 0.4, 0.1);
        rightBrow.setFill(Color.BLACK);
        rightBrow.setRotate(-30);

        getChildren().add(head);
        getChildren().add(leftLeg);
        getChildren().add(rightLeg);
        getChildren().add(leftEye);
        getChildren().add(rightEye);
        getChildren().add(leftPupil);
        getChildren().add(rightPupil);
        getChildren().add(mouth);
        getChildren().add(leftTooth);
        getChildren().add(rightTooth);
        getChildren().add(leftBrow);
        getChildren().add(rightBrow);

        Scale scale = new Scale(100, 80);
        getTransforms().add(scale);
    }

    public boolean isDead() { return dead; }

    public boolean isFaceRight() { return right; }

    @Override
    public void update() {

        if (shouldRotate) {
            if (!right) {
                right = true;
                velocity = - Main.ENEMY_SPEED + Main.FLOOR_SPEED;
            }
            else {
                right = false;
                velocity = Main.ENEMY_SPEED;
            }
            //velocity *= -1;
            setTranslateX(getTranslateX() - velocity);
            shouldRotate = false;
        }

        if (!dead) {
            setTranslateX(getTranslateX() - velocity);
        }
        else {
            setTranslateY(getTranslateY() + velocity);
            velocity += Main.GRAVITY_ACCELERATION;
        }


        if (leftPupil.getTranslateX() <= -0.05 || leftPupil.getTranslateX() >= 0.15) {
            pupilVelocity *= -1;
        }
        leftPupil.setTranslateX(leftPupil.getTranslateX() + pupilVelocity);
        rightPupil.setTranslateX(rightPupil.getTranslateX() + pupilVelocity);
    }

    public void setShouldRotate(boolean shouldRotate) {
        this.shouldRotate = shouldRotate;
    }

    public void takeHit() {
        dead = true;
    }

}
