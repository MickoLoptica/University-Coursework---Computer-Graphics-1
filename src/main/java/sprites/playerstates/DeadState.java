/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprites.playerstates;

import main.Main;
import sprites.Player;

/**
 *
 * @author mile
 */
public class DeadState extends State {

    private static final double ROTATION_VELOCITY = 15.0;

    private double velocity = Player.JUMP_VELOCITY;

    public DeadState(Player player) {
        super(player);
    }

    @Override
    public void move() {
        player.setTranslateX(player.getTranslateX() + player.getVelocityX());
        player.setTranslateY(player.getTranslateY() + velocity);
        player.setRotate(player.getRotate() + ROTATION_VELOCITY);
        velocity += Main.GRAVITY_ACCELERATION;
        if (player.getTranslateY() > Main.WINDOW_HEIGHT - 100) {
            player.stop();
            player.setDead(true);
        }
    }

}
