package sample;

import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Player extends GameObject {
    Player(ImageView sprite) {
        super(sprite);
        sprite.resize(30,30);
    }
}