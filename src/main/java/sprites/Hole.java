package sprites;

import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;

public class Hole extends  Sprite {

    private double velocity;
    private Rectangle block;

    public Hole(double velocity) {
        this.velocity = velocity;

        block = new Rectangle(-0.5, -0.5, 1, 1);
        Stop[] tacke = new Stop[] {new Stop(0, Color.DEEPSKYBLUE), new Stop(1, Color.BLACK)};
        LinearGradient lg = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, tacke);
        block.setFill(lg);
        getChildren().add(block);
    }

    @Override
    public void update() {
        setTranslateX(getTranslateX() - velocity);
    }

}
