/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprites;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import main.Main;
import sprites.playerstates.DeadState;
import sprites.playerstates.IdleState;
import sprites.playerstates.JumpState;
import sprites.playerstates.RunState;
import sprites.playerstates.State;

/**
 *
 * @author mile
 */
public class Player extends Sprite implements EventHandler<KeyEvent> {

    private static final Paint IDLE_COLOR = Color.YELLOW;
    private static final Paint RUN_COLOR = Color.GREEN;
    private static final Paint JUMP_COLOR = Color.RED;
    private static final Paint DEAD_COLOR = Color.PURPLE;

    private static final Paint EYE_COLOR = Color.BLACK;

    public static final double PLAYER_VELOCITY = 7.5f;

    private State state;
    private boolean right = true;
    private boolean onGround = true;
    private boolean onPlatform = false;
    private boolean dying = false;
    private boolean dead = false;
    private boolean hasImmunity = false;

    private int immunityDuration = 0; // in frames

    private double velocityX = PLAYER_VELOCITY;
    private double velocityY = 0;
    public static final double JUMP_VELOCITY = -15.0f;

    private Circle body;
    private Circle eye;
    private Circle immunityCapsule;

    public Player() {

        state = new IdleState(this);
        getChildren().clear();

        body = new Circle(0, 0, 40);
        body.setFill(IDLE_COLOR);
        eye = new Circle(15, 0, 10);
        eye.setFill(EYE_COLOR);
        immunityCapsule = new Circle(0, 0, 50);
        immunityCapsule.setFill(Color.TRANSPARENT);
        immunityCapsule.setOpacity(0.25);

        getChildren().add(body);
        getChildren().add(eye);
        getChildren().add(immunityCapsule);
    }

    public double getVelocityX() {
        if (isFaceRight()) {
            return velocityX;
        } else {
            return -velocityX;
        }
    }

    public double getVelocityY() { return velocityY; }

    public void setVelocityY(double velocityY) { this.velocityY = velocityY; }

    public boolean isDead() { return dead; }

    public boolean isDying() { return dying; }

    public void setOnGround(boolean onGround) { this.onGround = onGround; }

    public boolean getOnGround() { return onGround; }

    public void setOnPlatform(boolean onPlatform) { this.onPlatform = onPlatform; }

    public boolean getOnPlatform() { return onPlatform; }

    public boolean getHasImmunity() { return hasImmunity; }

    public void run() {
        state = new RunState(this);
        body.setFill(RUN_COLOR);
    }

    public void faceRight() {
        if (isFaceLeft()) {
            setScaleX(-getScaleX());
        }
        right = true;
    }

    public void faceLeft() {
        if (isFaceRight()) {
            setScaleX(-getScaleX());
        }
        right = false;
    }

    public void jump() {
        if (!dying && onGround) {
            onGround = false;
            state = new JumpState(this);
            body.setFill(JUMP_COLOR);
        }
    }

    public void stop() {
        if (!dying) {
            state = new IdleState(this);
            body.setFill(IDLE_COLOR);
            velocityY = 0;
        }
    }

    public void die() {
        dying = true;
        state = new DeadState(this);
        body.setFill(DEAD_COLOR);
    }

    public boolean isFaceRight() {
        return right;
    }

    public boolean isFaceLeft() {
        return !right;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public void fallDown() {
        if (onPlatform) {
            onPlatform = false;
            onGround = false;
            setTranslateY(getTranslateY() + 10);
        }
    }

    @Override
    public void update() {

        if (getTranslateY() > Main.WINDOW_HEIGHT - 50) {
            die();
            dead = true;
        }
        else {
            state.move();
        }

        if (immunityDuration > 0) {
            immunityDuration--;
        }
        else {
            loseImmunity();
        }

    }

    // executed on keyboard input to perform particular actions
    @Override
    public void handle(KeyEvent event) {
        if (event.getCode() == KeyCode.RIGHT && event.getEventType() == KeyEvent.KEY_PRESSED) {
            state.rightPressed();
        }
        else if (event.getCode() == KeyCode.RIGHT && event.getEventType() == KeyEvent.KEY_RELEASED) {
            state.rightReleased();
        }
        else if (event.getCode() == KeyCode.LEFT && event.getEventType() == KeyEvent.KEY_PRESSED) {
            state.leftPressed();
        }
        else if (event.getCode() == KeyCode.LEFT && event.getEventType() == KeyEvent.KEY_RELEASED) {
            state.leftReleased();
        }
        else if (event.getCode() == KeyCode.DOWN && event.getEventType() == KeyEvent.KEY_PRESSED && onPlatform) {
            state.downPressed();
        }
        else if (event.getCode() == KeyCode.DOWN && event.getEventType() == KeyEvent.KEY_RELEASED && onPlatform) {
            state.downReleased();
        }
        else if (event.getCode() == KeyCode.SPACE && event.getEventType() == KeyEvent.KEY_PRESSED) {
            state.jumpPressed();
        }
        else if (event.getCode() == KeyCode.SPACE && event.getEventType() == KeyEvent.KEY_RELEASED) {
            state.jumpReleased();
        }
        else if (event.getCode() == KeyCode.DIGIT1 && event.getEventType() == KeyEvent.KEY_PRESSED) {

        }
        else if (event.getCode() == KeyCode.DIGIT2 && event.getEventType() == KeyEvent.KEY_PRESSED) {

        }
        else if (event.getCode() == KeyCode.DIGIT3 && event.getEventType() == KeyEvent.KEY_PRESSED) {

        }
    }

    public void takeHit() {
        die();
    }

    public void getImmunity() {
        immunityCapsule.setFill(Color.BLUE);
        hasImmunity = true;
        immunityDuration = 600;
    }

    public void loseImmunity() {
        immunityCapsule.setFill(Color.TRANSPARENT);
        hasImmunity = false;
        immunityDuration = 0;
    }

    public void restart() {
        state = new IdleState(this);
        faceRight();
        onGround = true;
        onPlatform = false;
        velocityY = 0;
        body.setFill(IDLE_COLOR);
        dead = false;
        dying = false;
        loseImmunity();
        setRotate(0);
    }

}
