package sprites;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ImmunityTimer extends Sprite {

    private int time = 10;
    private int timeInFrames = 600;

    private boolean active = false;

    private Text timer;
    private Circle icon;

    public ImmunityTimer() {

        timer = new Text(0, 0, time + "");
        timer.setFont(new Font(30));
        timer.setFill(Color.TRANSPARENT);

        icon = new Circle(0, -7.5, 15);
        icon.setFill(Color.TRANSPARENT);
        icon.setOpacity(0.25);
        icon.setTranslateX(50);

        getChildren().add(timer);
        getChildren().add(icon);

    }

    @Override
    public void update() {

        if (active) {

            timeInFrames--;

            if (timeInFrames % 60 == 0) {
                time--;
            }

            String s = String.format(time + "");
            timer.setText(s);

            if (time == 0) {
                timer.setFill(Color.TRANSPARENT);
                icon.setFill(Color.TRANSPARENT);
                active = false;
            }

        }

    }

    public void activate() {
        active = true;
        time = 10;
        timeInFrames = 600;
        timer.setFill(Color.BLACK);
        icon.setFill(Color.BLUE);
    }

    public void deactivate() {
        active = false;
        time = 0;
        timeInFrames = 0;
        timer.setFill(Color.TRANSPARENT);
        icon.setFill(Color.TRANSPARENT);
    }

}
