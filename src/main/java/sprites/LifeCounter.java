package sprites;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class LifeCounter extends Sprite {

    private int lifesLeft;
    private Text lifeCounter;

    public LifeCounter(int lifesLeft) {

        this.lifesLeft = lifesLeft;

        lifeCounter = new Text(0, 0, lifesLeft + "");
        lifeCounter.setFont(new Font(30));

        Group icon = new Group();
        Circle body = new Circle(0, -7.5, 15);
        body.setFill(Color.YELLOW);
        Circle eye = new Circle(6, -7.5, 5.625);
        eye.setFill(Color.BLACK);
        icon.getChildren().add(body);
        icon.getChildren().add(eye);
        icon.setTranslateX(40);

        getChildren().add(lifeCounter);
        getChildren().add(icon);

    }

    @Override
    public void update() {

        lifesLeft--;

        lifeCounter.setText(lifesLeft + "");

    }

}
