/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprites;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author mile
 */
public class FloorBlock extends Sprite {

    private Rectangle block;
    private Polygon grass;

    public FloorBlock() {
        block = new Rectangle(-0.5, -0.5, 1, 1);
        block.setFill(Color.SIENNA);
        grass = new Polygon();
        grass.getPoints().addAll(new Double[]{-0.5, -0.5, -0.5, -0.4, -0.4, -0.3, -0.3, -0.4, -0.2, -0.3, -0.1, -0.4, 0.0, -0.3, 0.1, -0.4, 0.2, -0.3, 0.3, -0.4, 0.4, -0.3, 0.5, -0.4, 0.5, -0.5});
        grass.setFill(Color.CHARTREUSE);
        getChildren().addAll(block, grass);
    }

}
