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
public class JumpState extends State {

    private double velocity = Player.JUMP_VELOCITY;

    public JumpState(Player player) {
        super(player);
    }

    @Override
    public void move() {
        if (!player.getOnGround()) {
            player.setVelocityY(velocity);
            player.setTranslateX(player.getTranslateX() + player.getVelocityX());
            player.setTranslateY(player.getTranslateY() + velocity);
            velocity += Main.GRAVITY_ACCELERATION;
        }
        else {
            player.stop();
        }
    }

}
