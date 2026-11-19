package main;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import cameras.Camera;

import java.util.LinkedList;
import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import sprites.*;

/**
 *
 * @author mile
 */
public class Main extends Application implements EventHandler<KeyEvent> {

    public static final int WINDOW_WIDTH = 1200;    // originalno je 1600
    public static final int WINDOW_HEIGHT = 676;    // originalno je 900

    public static final int FLOOR_WIDTH = WINDOW_WIDTH;
    public static final int FLOOR_HEIGHT = WINDOW_HEIGHT / 4;

    public static final int ENEMY_WIDTH = 100;
    public static final int ENEMY_HEIGHT = 80;

    public static final String TITLE = "Platformer";

    public static final double GRAVITY_ACCELERATION = 0.5f;
    public static final double ENEMY_SPEED = 2.5f;
    public static final double CLOUD_SPEED = 1.25f;
    public static final double PLATFORM_SPEED = 1.25f;
    public static final double FLOOR_SPEED = 1.25f;
    public static final double BIRD_SPEED = 2.5f;
    public static final double COLLECTIBLE_SPEED = 1.25f;

    public static final int CLOUD_BASE_DISTANCE = 500;
    public static final int BIRD_BASE_DISTANCE = 500;
    public static final int COIN_BASE_DISTANCE = 1000;
    public static final int IMMUNITY_TOKEN_BASE_DISTANCE = 10000;
    public static final int PLATFORM_BASE_DISTANCE = 1000;
    public static final int ENEMY_BASE_DISTANCE = 800;

    private Group sprites;
    private Floor floor;
    private Player player;
    private List<Enemy> enemies;
    private List<Cloud> clouds;
    private List<Platform> platforms;
    private List<Bird> birds;
    private List<Coin> coins;
    private List<ImmunityToken> immunityTokens;

    private static Main instance;
    
    private double time = 0; // game time in seconds
    private static final int gameDuration = 120; // gameDuration in seconds
    private int frameInSecond = 0; // increments to 60, then restarts

    private Score score;
    private Timer timer;
    private LifeCounter lifeCounter;
    private ImmunityTimer immunityTimer;

    private static int lifesLeft = 3;

    private Camera camera; // camera used for applying transformations
    private static boolean centeredCamera = false;

    // returns the instance of the game (Main class)
    public static Main getInstance(){
        return instance;
    }
    
    public Camera getCamera(){
        return camera;
    }
    
    public double getTime() {
        return time;
    }

    public double getGameDuration() { return gameDuration; }

    @Override
    public void start(Stage primaryStage) {
        instance = this;

        clouds = new LinkedList<>();
        birds = new LinkedList<>();
        coins = new LinkedList<>();
        immunityTokens = new LinkedList<>();
        enemies = new LinkedList<>();
        platforms = new LinkedList<>();
        Background bg = new Background(WINDOW_WIDTH, WINDOW_HEIGHT);

        floor = new Floor(FLOOR_WIDTH, FLOOR_HEIGHT, FLOOR_SPEED);
        floor.setTranslateY(WINDOW_HEIGHT);

        camera = new Camera();
        camera.getChildren().add(floor);

        sprites = new Group();
        camera.getChildren().add(sprites);

        for (int i = 0; i < 100; i++) {
            Cloud cloud = new Cloud(CLOUD_SPEED);
            cloud.setTranslateX((Math.random() + i) * CLOUD_BASE_DISTANCE);
            double cloudHeight = Math.random() * (WINDOW_HEIGHT / 2 + 1);
            cloud.setTranslateY(cloudHeight);
            sprites.getChildren().add(cloud);
            clouds.add(cloud);
        }

        for (int i = 0; i < 100; i++) {
            Bird bird = new Bird(BIRD_SPEED);
            bird.setTranslateX((Math.random() + i) * BIRD_BASE_DISTANCE);
            double birdHeight = Math.random() * (WINDOW_HEIGHT / 2 + 1);
            bird.setTranslateY(birdHeight);
            sprites.getChildren().add(bird);
            birds.add(bird);
        }

        for (int i = 0; i < 100; i++) {
            Coin coin = new Coin(COLLECTIBLE_SPEED);
            coin.setTranslateX((Math.random() + i) * COIN_BASE_DISTANCE);
            double coinHeight = WINDOW_HEIGHT - FLOOR_HEIGHT - 35;
            coin.setTranslateY(coinHeight);
            sprites.getChildren().add(coin);
            coins.add(coin);
        }

        for (int i = 0; i < 10; i++) {
            ImmunityToken token = new ImmunityToken(COLLECTIBLE_SPEED);
            token.setTranslateX((Math.random() + i) * IMMUNITY_TOKEN_BASE_DISTANCE);
            double tokenHeight = WINDOW_HEIGHT - FLOOR_HEIGHT - 35;
            token.setTranslateY(tokenHeight);
            sprites.getChildren().add(token);
            immunityTokens.add(token);
        }

        for (int i = 0; i < 100; i++) {
            Platform platform = new Platform(PLATFORM_SPEED);
            platform.setTranslateX((Math.random() + i) * PLATFORM_BASE_DISTANCE);
            double platformHeight = Math.random() * (WINDOW_HEIGHT  - FLOOR_HEIGHT - 200) + 100;
            platform.setTranslateY(platformHeight);
            sprites.getChildren().add(platform);
            platforms.add(platform);
            if (Math.random() > 0.5) {
                Enemy enemy = new Enemy(ENEMY_SPEED);
                enemy.setTranslateX(platform.getTranslateX() + platform.getLength() / 2);
                enemy.setTranslateY(platform.getTranslateY() - ENEMY_HEIGHT / 2);
                sprites.getChildren().add(enemy);
                enemies.add(enemy);
            }
        }

        // making 100 enemies and adding them to the scene
        for (int i = 0; i < 100; i++) {
            Enemy enemy = new Enemy(ENEMY_SPEED);
            enemy.setTranslateX((1 + i) * ENEMY_BASE_DISTANCE);
            enemy.setTranslateY(WINDOW_HEIGHT - FLOOR_HEIGHT - ENEMY_HEIGHT / 2);
            sprites.getChildren().add(enemy);
            enemies.add(enemy);
        }

        player = new Player();
        player.setTranslateX(100);
        player.setTranslateY(WINDOW_HEIGHT - FLOOR_HEIGHT - ENEMY_HEIGHT / 2);
        sprites.getChildren().add(player);

        score = new Score();
        score.setTranslateX(20);
        score.setTranslateY(30);

        timer = new Timer(gameDuration);
        timer.setTranslateX(WINDOW_WIDTH / 2 - 15);
        timer.setTranslateY(30);

        lifeCounter = new LifeCounter(lifesLeft);
        lifeCounter.setTranslateX(WINDOW_WIDTH - 80);
        lifeCounter.setTranslateY(30);

        immunityTimer = new ImmunityTimer();
        immunityTimer.setTranslateX(WINDOW_WIDTH / 2 - 15);
        immunityTimer.setTranslateY(70);

        Group root = new Group();
        root.getChildren().add(bg);
        root.getChildren().add(camera);
        root.getChildren().add(score);
        root.getChildren().add(timer);
        root.getChildren().add(lifeCounter);
        root.getChildren().add(immunityTimer);

        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        scene.setOnKeyPressed(this);
        scene.setOnKeyReleased(player);
        primaryStage.setTitle(TITLE);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);

        // calling update once every frame
        new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                update();
            }
        }.start();
    }

    // called once per frame to update game state
    private void update() {
        if (time < gameDuration && !player.isDead()) {
            clouds.forEach(c -> c.update());
            birds.forEach(b -> b.update());
            coins.forEach(c -> c.update());
            immunityTokens.forEach(t -> t.update());
            platforms.forEach(p -> p.update());
            player.update();
            enemies.forEach(e -> e.update());
            floor.update();

            if (centeredCamera) {
                camera.centerOn(player);
            }

            // checking enemy collisons
            enemies.forEach(e -> {

                if (!e.isDead()) {
                    boolean shouldRotate = true;
                    double enemyStart = e.getBoundsInParent().getMinX();
                    double enemyEnd = e.getBoundsInParent().getMaxX();
                    double enemyBottom = e.getBoundsInParent().getMaxY();
                    double enemyHeight = e.getBoundsInParent().getHeight();
                    double floorTop = floor.getBoundsInParent().getMinY();
                    boolean enemyFloorXOverlap = enemyStart < floor.getBoundsInParent().getMaxX() && enemyEnd > floor.getBoundsInParent().getMinX();
                    if (enemyFloorXOverlap && enemyBottom <= floorTop + 10 && enemyBottom >= floorTop - 10) {
                        for (Node node : floor.getChildren()) {
                            if (node instanceof FloorBlock) {
                                Bounds enemyBounds = e.localToScene(e.getBoundsInLocal());
                                double probeX;
                                if (e.isFaceRight()) {
                                    probeX = enemyBounds.getMaxX();
                                } else {
                                    probeX = enemyBounds.getMinX();
                                }
                                Bounds blockBounds = node.localToScene(node.getBoundsInLocal());
                                double blockStart = blockBounds.getMinX();
                                double blockEnd = blockBounds.getMaxX();
                                if (probeX <= blockEnd && probeX >= blockStart) {
                                    shouldRotate = false;
                                    e.setTranslateY(e.getTranslateY() + floorTop  - enemyBottom);
                                    break;
                                }
                            }
                        }
                    }
                    if (shouldRotate) {
                        for (Platform p : platforms) {
                            double platformTop = p.getBoundsInParent().getMinY();
                            boolean enemyPlatformXOverlap = e.getBoundsInParent().getMaxX() < p.getBoundsInParent().getMaxX() && e.getBoundsInParent().getMinX() > p.getBoundsInParent().getMinX();
                            if (enemyPlatformXOverlap && enemyBottom <= platformTop + 5 && enemyBottom >= platformTop - 5) {
                                shouldRotate = false;
                                e.setTranslateY(platformTop  - enemyHeight / 2);
                                break;
                            }
                        }
                    }
                    e.setShouldRotate(shouldRotate);
                }

                if (player.getBoundsInParent().intersects(e.getBoundsInParent()) && !player.isDying() && !e.isDead()) {
                    if (player.getVelocityY() > 0 || player.getHasImmunity()) {
                        e.takeHit();
                        score.addPoints(10);
                    }
                    else {
                        player.takeHit();
                    }
                }
            });

            coins.forEach(c -> {
                if (player.getBoundsInParent().intersects(c.getBoundsInParent()) && !c.isCollected()) {
                    score.addPoints(10);
                    c.dissapear();
                }
            });

            immunityTokens.forEach(t -> {
                if (player.getBoundsInParent().intersects(t.getBoundsInParent()) && !t.isCollected()) {
                    player.getImmunity();
                    t.dissapear();
                    immunityTimer.activate();
                }
            });

            // collision detection
            if (!player.isDying()) {
                boolean onGround = false;
                boolean onPlatform = false;
                double playerStart = player.getBoundsInParent().getMinX() + 10;
                double playerEnd = player.getBoundsInParent().getMaxX() - 10;
                double playerBottom = player.getBoundsInParent().getMaxY() - 10;
                double floorTop = floor.getBoundsInParent().getMinY();
                boolean playerFloorXOverlap = playerStart < floor.getBoundsInParent().getMaxX() && playerEnd > floor.getBoundsInParent().getMinX();
                if (player.getVelocityY() >= 0 && playerFloorXOverlap && playerBottom <= floorTop + 10 && playerBottom >= floorTop - 10) {
                    for (Node node : floor.getChildren()) {
                        if (node instanceof FloorBlock) {
                            Bounds playerBounds = player.localToScene(player.getBoundsInLocal());
                            playerStart = playerBounds.getMinX();
                            playerEnd = playerBounds.getMaxX();
                            Bounds blockBounds = node.localToScene(node.getBoundsInLocal());
                            double blockStart = blockBounds.getMinX();
                            double blockEnd = blockBounds.getMaxX();
                            if (playerStart < blockEnd && playerEnd > blockStart) {
                                onGround = true;
                                player.setTranslateY(player.getTranslateY() + floorTop  - playerBottom);
                                break;
                            }
                        }
                    }
                }
                if (!onGround) {
                    for (Platform p : platforms) {
                        double platformTop = p.getBoundsInParent().getMinY();
                        boolean playerPlatformXOverlap = player.getBoundsInParent().getMinX() + 10 < p.getBoundsInParent().getMaxX() && player.getBoundsInParent().getMaxX() - 10 > p.getBoundsInParent().getMinX();
                        if (player.getVelocityY() >= 0 && playerPlatformXOverlap && playerBottom <= platformTop + 5 && playerBottom >= platformTop - 5) {
                            onGround = true;
                            onPlatform = true;
                            player.setTranslateY(player.getTranslateY() + platformTop  - playerBottom);
                            break;
                        }
                    }
                }
                player.setOnGround(onGround);
                player.setOnPlatform(onPlatform);
            }

            if (player.isDead()) {
                lifesLeft--;
                lifeCounter.update();
            }
            time += 1. / 60;
            timer.update();
            immunityTimer.update();
            frameInSecond++;
            if (frameInSecond % 60 == 0) {
                score.update();
            }

        }
    }

    public void restart() {

        clouds.clear();
        birds.clear();
        coins.clear();
        immunityTokens.clear();
        enemies.clear();
        platforms.clear();

        sprites.getChildren().clear();

        floor.setTranslateX(0);

        for (int i = 0; i < 100; i++) {
            Cloud cloud = new Cloud(CLOUD_SPEED);
            cloud.setTranslateX((Math.random() + i) * CLOUD_BASE_DISTANCE);
            double cloudHeight = Math.random() * (WINDOW_HEIGHT / 2 + 1);
            cloud.setTranslateY(cloudHeight);
            sprites.getChildren().add(cloud);
            clouds.add(cloud);
        }

        for (int i = 0; i < 100; i++) {
            Bird bird = new Bird(BIRD_SPEED);
            bird.setTranslateX((Math.random() + i) * BIRD_BASE_DISTANCE);
            double birdHeight = Math.random() * (WINDOW_HEIGHT / 2 + 1);
            bird.setTranslateY(birdHeight);
            sprites.getChildren().add(bird);
            birds.add(bird);
        }

        for (int i = 0; i < 100; i++) {
            Coin coin = new Coin(COLLECTIBLE_SPEED);
            coin.setTranslateX((Math.random() + i) * COIN_BASE_DISTANCE);
            double coinHeight = WINDOW_HEIGHT - FLOOR_HEIGHT - 35;
            coin.setTranslateY(coinHeight);
            sprites.getChildren().add(coin);
            coins.add(coin);
        }

        for (int i = 0; i < 10; i++) {
            ImmunityToken token = new ImmunityToken(COLLECTIBLE_SPEED);
            token.setTranslateX((Math.random() + i) * IMMUNITY_TOKEN_BASE_DISTANCE);
            double tokenHeight = WINDOW_HEIGHT - FLOOR_HEIGHT - 35;
            token.setTranslateY(tokenHeight);
            sprites.getChildren().add(token);
            immunityTokens.add(token);
        }

        for (int i = 0; i < 100; i++) {
            Platform platform = new Platform(PLATFORM_SPEED);
            platform.setTranslateX((Math.random() + i) * PLATFORM_BASE_DISTANCE);
            double platformHeight = Math.random() * (WINDOW_HEIGHT  - FLOOR_HEIGHT - 200) + 100;
            platform.setTranslateY(platformHeight);
            sprites.getChildren().add(platform);
            platforms.add(platform);
            if (Math.random() > 0.5) {
                Enemy enemy = new Enemy(ENEMY_SPEED);
                enemy.setTranslateX(platform.getTranslateX() + platform.getLength() / 2);
                enemy.setTranslateY(platform.getTranslateY() - ENEMY_HEIGHT / 2);
                sprites.getChildren().add(enemy);
                enemies.add(enemy);
            }
        }

        // making 100 enemies and adding them to the scene
        for (int i = 0; i < 100; i++) {
            Enemy enemy = new Enemy(ENEMY_SPEED);
            enemy.setTranslateX((1 + i) * ENEMY_BASE_DISTANCE);
            enemy.setTranslateY(WINDOW_HEIGHT - FLOOR_HEIGHT - ENEMY_HEIGHT / 2);
            sprites.getChildren().add(enemy);
            enemies.add(enemy);
        }

        player.restart();
        player.setTranslateX(100);
        player.setTranslateY(WINDOW_HEIGHT - FLOOR_HEIGHT - ENEMY_HEIGHT / 2);
        sprites.getChildren().add(player);

        immunityTimer.deactivate();

    }

    @Override
    public void handle(KeyEvent event) {
        if (event.getCode() == KeyCode.DIGIT1 && event.getEventType() == KeyEvent.KEY_PRESSED) {
            centeredCamera = false;
        }
        else if (event.getCode() == KeyCode.DIGIT2 && event.getEventType() == KeyEvent.KEY_PRESSED) {
            centeredCamera = true;
        }
        else if (event.getCode() == KeyCode.SPACE && event.getEventType() == KeyEvent.KEY_PRESSED && player.isDead() && lifesLeft > 0) {
            restart();
        }
        else {
            player.handle(event);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
