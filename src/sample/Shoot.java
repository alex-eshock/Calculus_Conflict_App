package sample;

import javafx.geometry.Point2D;

import java.util.EventListener;

public class Shoot extends Action implements EventListener {

    Shoot() {

    }

    @Override
    Point2D statePoint2D() {
        return null;
    }

    @Override
    boolean state() {
        return false;
    }
}
