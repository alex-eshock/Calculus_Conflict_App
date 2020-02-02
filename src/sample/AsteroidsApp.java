package sample;

import com.studiohartman.jamepad.ControllerManager;
import com.studiohartman.jamepad.ControllerState;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class AsteroidsApp extends Application {

    private Pane root;

    private List<GameObject> bullets = new ArrayList<>();
    private List<GameObject> enemies = new ArrayList<>();

    private GameObject player;
    ControllerManager controllers;
    private int frameCount = 0;
    private int bulletTimer = 0;


    private Parent createContent() {
        controllers = new ControllerManager();
        controllers.initSDLGamepad();



        root = new Pane();
        root.setPrefSize(600, 600);

        player = new Player();
        player.setVelocity(new Point2D(1, 0));
        addGameObject(player, 300, 300);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                onUpdate();
            }
        };
        timer.start();

        return root;
    }

    private void addBullet(GameObject bullet, double x, double y) {
        bullets.add(bullet);
        addGameObject(bullet, x, y);
    }

    private void addEnemy(GameObject enemy, double x, double y) {
        enemies.add(enemy);
        addGameObject(enemy, x, y);
    }

    private void addGameObject(GameObject object, double x, double y) {
        object.getView().setTranslateX(x);
        object.getView().setTranslateY(y);
        root.getChildren().add(object.getView());
    }

    private void onUpdate() {
        for (GameObject bullet : bullets) {
            for (GameObject enemy : enemies) {
                if (bullet.isColliding(enemy)) {
                    bullet.setAlive(false);
                    enemy.setAlive(false);

                    root.getChildren().removeAll(bullet.getView(), enemy.getView());
                }
            }
        }

        bullets.removeIf(GameObject::isDead);
        enemies.removeIf(GameObject::isDead);

        bullets.forEach(GameObject::update);
        enemies.forEach(GameObject::update);


        //asdfasdfasdfasdfasdfasdf
        ControllerState currState = controllers.getState(0);
        if(currState.leftStickMagnitude > 0.25 || currState.leftStickMagnitude < -0.25)  {
            player.setVelocity(player.getVelocity().add(new Point2D(5*currState.leftStickX,5*(currState.leftStickY - (2* currState.leftStickY)))).multiply(.5));

        } else {
            player.setVelocity(player.getVelocity().add(new Point2D(0,0)).multiply(.5));
            //letting go of stick, do momentum calculatio
        }

        if(currState.rightStickMagnitude > 0.25) {
            player.setRotate(180 -currState.rightStickAngle);

            if(bulletTimer == 0 || bulletTimer == 5 || bulletTimer == 10 || bulletTimer == 30 || bulletTimer == 35 || bulletTimer == 40) {
                Bullet bullet = new Bullet();
                addBullet(bullet, player.getView().getTranslateX() + 15, player.getView().getTranslateY() + 5);
                bullet.setVelocity((new Point2D(currState.rightStickX, currState.rightStickY - 2*(currState.rightStickY))).normalize().multiply(8));

            }
            bulletTimer++;
        } else {
            bulletTimer = 0;
        }
        //END CONTYROLLERSDF:SLKDHFGJ;lkj
        player.update();

        if (Math.random() < 0.02) {
            addEnemy(new Enemy(), Math.random() * root.getPrefWidth(), Math.random() * root.getPrefHeight());
        }




        if (bulletTimer >= 60) {
            bulletTimer = 0;
        }

        if (frameCount == 60) {
            frameCount = 0;
        }

        frameCount++;
    }



    @Override
    public void start(Stage stage) throws Exception {

        stage.setScene(new Scene(createContent()));
        stage.getScene().setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.LEFT) {
                player.rotateLeft();
            } else if (e.getCode() == KeyCode.RIGHT) {
                player.rotateRight();
            } else if (e.getCode() == KeyCode.SPACE) {
                Bullet bullet = new Bullet();
                bullet.setVelocity(player.getVelocity().normalize().multiply(5));
                addBullet(bullet, player.getView().getTranslateX(), player.getView().getTranslateY());
            }
        });
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}