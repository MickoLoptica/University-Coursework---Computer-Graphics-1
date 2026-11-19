/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cameras;

import javafx.scene.Group;
import main.Main;
import sprites.Sprite;

/**
 *
 * @author mile
 */
public class Camera extends Group {

    public Camera() {
        getTransforms().clear();
    }

    public void centerOn(Sprite target) {
        setTranslateX(-target.getTranslateX() + Main.WINDOW_WIDTH / 2);
    }

}
