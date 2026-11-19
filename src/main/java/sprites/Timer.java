package sprites;

import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Timer extends Sprite {

    private int time;
    private int timeInFrames;
    private Text timer;

    public Timer(int time) {
        this.time = time;
        timeInFrames = time * 60;

        int minutes = time / 60;
        int seconds = time % 60;
        String s = String.format("%02d:%02d", minutes, seconds);

        timer = new Text(0, 0, s);
        timer.setFont(new Font(30));

        getChildren().add(timer);

    }

    @Override
    public void update() {

        timeInFrames--;
        if (timeInFrames % 60 == 0) {
            time--;
        }

        int minutes = time / 60;
        int seconds = time % 60;
        String s = String.format("%02d:%02d", minutes, seconds);

        timer.setText(s);

    }

}
