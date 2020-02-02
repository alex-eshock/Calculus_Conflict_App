package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Enemy extends GameObject {
    Enemy() {
        super(new Circle(15, 15, 15, Color.RED));
    }
}