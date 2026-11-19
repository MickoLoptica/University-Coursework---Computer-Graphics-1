package sprites;

import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Score extends Sprite {

    private int score;
    private Text scoreText;

    public Score() {

        score = 0;

        scoreText = new Text(0, 0, score + "");
        scoreText.setFont(new Font(30));

        getChildren().add(scoreText);

    }

    @Override
    public void update() {

        score++;
        scoreText.setText(score + "");

    }

    public void addPoints(int points) {
        score += points;
    }

}
