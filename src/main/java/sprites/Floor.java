/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprites;

import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;

/**
 *
 * @author mile
 */
public class Floor extends Sprite {

    private int height;// in pixels
    private int width;// in pixels
    private double velocity;

    public Floor(int width, int height, double velocity) {
        this.height = height;
        this.width = width;
        this.velocity = velocity;
        Scale scale = new Scale(height, height);// floorBlocks should be square and side equals to the height of the floor
        Translate translate = new Translate(0, -height / 2);
        // floor is made up of a number of floorBlocks the following loop only concatenates a big number of blocks to make up a floor
        int i = -1 * width;
        while (i < 50 * width + height) {
            if (Math.random() < 0.075 && i > width) {
                i += height;
            }
            else {
                FloorBlock floorBlock = new FloorBlock();
                floorBlock.setTranslateX(i);
                floorBlock.setTranslateY(-height / 2);
                floorBlock.setScaleX(height);
                floorBlock.setScaleY(height);
                getChildren().add(floorBlock);
                i += height;
            }
        }
    }

    @Override
    public void update() {
        setTranslateX(getTranslateX() - velocity);
    }

}
